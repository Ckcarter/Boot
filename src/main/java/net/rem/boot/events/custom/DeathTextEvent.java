package net.rem.boot.events.custom;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;

public class DeathTextEvent extends LivingEvent {
    public DeathTextEvent(LivingEntity entity) {
        super(entity);
    }
}
