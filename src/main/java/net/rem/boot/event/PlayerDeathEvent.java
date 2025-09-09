package net.rem.boot.event;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.rem.boot.BootMod;

public class PlayerDeathEvent {

    @Mod.EventBusSubscriber(modid = BootMod.MOD_ID)
    public class DeathMessages {
        @SubscribeEvent
        public static void onDeath(LivingDeathEvent event) {
            if (event.getEntity() instanceof Player player) {
                Component msg =
                        Component.translatable("death.bootmod.boot",
                                player.getDisplayName());
                //event.setDeathMessage(msg);
            }
        }
    }



}
