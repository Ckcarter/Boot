package net.rem.boot;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BootMod.MOD_ID)
public class DeathMessages {
    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            DamageSource source = event.getSource();
            if (source.is(BootMod.BOOT_DAMAGE_TYPE)) {
                Component message;
                if (source.getEntity() instanceof Player killer) {
                    message = Component.translatable(
                            "death.attack.bootmod.boot.player",
                            player.getDisplayName(),
                            killer.getDisplayName());
                } else {
                    message = Component.translatable(
                            "death.attack.bootmod.boot",
                            player.getDisplayName());
                }
            }

        }
    }
}