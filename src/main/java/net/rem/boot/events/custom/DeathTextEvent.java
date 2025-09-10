package net.rem.boot.events.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;

public class DeathTextEvent extends LivingEvent {
    private Component text;

    public DeathTextEvent(LivingEntity entity, Component text) {
        super(entity);


        this.text = text;
    }

    public Component getText() {
        return text;
    }

    public void setText(Component text) {
        this.text = text;
    }
}
