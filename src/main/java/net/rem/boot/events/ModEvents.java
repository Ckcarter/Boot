package net.rem.boot.events;



import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.IModBusEvent;
import net.rem.boot.BootMod;
import net.rem.boot.item.ModItems;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = BootMod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void livingDamage(LivingDamageEvent event){
        if(event.getEntity() instanceof Sheep){
            if(event.getSource().getDirectEntity() instanceof Player player){
                if(player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == ModItems.BOOT.get()) {
                    BootMod.LOGGER.info("Sheep was hit with Alexandrite Axe by " + player.getName().getString());
                }
                if(player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == Items.DIAMOND) {
                    BootMod.LOGGER.info("Sheep was hit with a DIAMOND by " + player.getName().getString());
                }else{
                    BootMod.LOGGER.info("Sheep was hit with a U.F.O. by " + player.getName().getString());
                }
            }
        }


    }


}