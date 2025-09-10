package net.rem.boot.item;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.rem.boot.BootMod;

public class ModItems {






    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, BootMod.MOD_ID);

    public static final RegistryObject<Item> BOOT = ITEMS.register("boot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LIGHT_BOOT = ITEMS.register("light_boot",
            () -> new Item(new Item.Properties()));


    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, BootMod.MOD_ID);



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }





}
