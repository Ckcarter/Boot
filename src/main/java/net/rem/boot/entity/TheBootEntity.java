package net.rem.boot.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.rem.boot.BootMod;


public class TheBootEntity extends ThrowableItemProjectile {
    public TheBootEntity(EntityType<? extends TheBootEntity> type, Level level) {
        super(type, level);
    }
    public TheBootEntity(Level level, LivingEntity thrower) {
        super(BootMod.THEBOOT.get(), thrower, level);
    }
    public TheBootEntity(Level level, double x, double y, double z) {
        super(BootMod.THEBOOT.get(), x, y, z, level);
    }

    @Override
    protected Item getDefaultItem() {
        return BootMod.THEBOOT_ITEM.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        result.getEntity().hurt(damageSources().thrown(this, getOwner()), 4.0F);
    }
}
