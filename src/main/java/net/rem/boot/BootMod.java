package net.rem.boot;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;


import net.rem.boot.entity.custom.BootEntity;
import net.rem.boot.item.custom.BootItem;
import org.slf4j.Logger;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BootMod.MOD_ID)
public class BootMod
{
    public static final String MOD_ID = "bootmod";
    public BootMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        ENTITY_TYPES.register(modEventBus);
        ITEMS.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BootMod.MOD_ID);

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, BootMod.MOD_ID);


    public static final RegistryObject<Item> BOOT =
            ITEMS.register("boot", () -> new BootItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> LIGHT_BOOT =
            ITEMS.register("light_boot", () -> new BootItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<EntityType<BootEntity>> FIREBALL_ENTITY =
            ENTITY_TYPES.register("boot",
                    () -> EntityType.Builder.<BootEntity>of(BootEntity::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .build("boot"));


    public static final ResourceKey<DamageType> BOOT_DAMAGE_TYPE =
            ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(BootMod.MOD_ID, "boot"));



    // Define mod id in a common place for everything to reference

    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();


    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(BOOT);
            event.accept(LIGHT_BOOT);
        }

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

            event.enqueueWork(() ->
                    EntityRenderers.register(BootMod.FIREBALL_ENTITY.get(), ThrownItemRenderer::new)
            );

        }

        private static final String PROTOCOL_VERSION = "1";
        public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MOD_ID, MOD_ID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
        private static int messageID = 0;

        public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
            PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
            messageID++;
        }

        private static final Collection<AbstractMap.SimpleEntry<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

        public static void queueServerWork(int tick, Runnable action) {
            workQueue.add(new AbstractMap.SimpleEntry(action, tick));
        }

        @SubscribeEvent
        public void tick(TickEvent.ServerTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                List<AbstractMap.SimpleEntry<Runnable, Integer>> actions = new ArrayList<>();
                workQueue.forEach(work -> {
                    work.setValue(work.getValue() - 1);
                    if (work.getValue() == 0)
                        actions.add(work);
                });
                actions.forEach(e -> e.getKey().run());
                workQueue.removeAll(actions);
            }
        }


    }


}
