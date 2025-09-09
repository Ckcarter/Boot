package net.rem.boot;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BootMod.MOD_ID)
public class DeathMessages {
    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            Component message = Component.translatable("death.attack.bootmod.boot", player.getDisplayName());
            //event.setPhase(message);
        }
    }
}