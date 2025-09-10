package net.rem.boot.item;

import net.minecraft.world.item.*;
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

//    public static final RegistryObject<Item> LIGHT_BOOT = ITEMS.register("light_boot",
//            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LIGHT_BOOT = ITEMS.register("light_boot",
            () -> new SwordItem(ModToolsTiers.ModToolTiers.LIGHT_BOOT, 0, 3, new Item.Properties()));

    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, BootMod.MOD_ID);



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }





}
