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
                Player killer = null;
                if (source.getEntity() instanceof Player p) {
                    killer = p;
                } else if (source.getEntity() instanceof net.rem.boot.entity.custom.BootEntity boot && boot.getOwner() instanceof Player p) {
                    killer = p;
                }

                Component message = killer != null
                        ? Component.translatable("death.attack.bootmod.boot.player", player.getDisplayName(), killer.getDisplayName())
                        : Component.translatable("death.attack.bootmod.boot", player.getDisplayName());
                event.setDeathMessage(message);
            }
        }
    }
}