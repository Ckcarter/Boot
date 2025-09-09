package net.rem.boot.entity.custom;



import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.rem.boot.BootMod;

import static net.rem.boot.BootMod.BOOT_DAMAGE_TYPE;
import static org.openjdk.nashorn.internal.objects.NativeRegExp.source;

public class BootEntity extends ThrowableItemProjectile {
    public BootEntity(EntityType<? extends BootEntity> entityType, Level level) {
        super(entityType, level);
    }

    public BootEntity(Level level, LivingEntity shooter) {
        super(BootMod.FIREBALL_ENTITY.get(), shooter, level);
    }

    public BootEntity(Level level, double x, double y, double z) {
        super(BootMod.FIREBALL_ENTITY.get(), x, y, z, level);
    }

    @Override
    protected Item getDefaultItem() {
        return BootMod.BOOT.get();
    }

    private ParticleOptions getParticle() {
        ItemStack stack = this.getItemRaw();
        return stack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemParticleOption(ParticleTypes.ITEM, stack);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            ParticleOptions particle = this.getParticle();

            for (int i = 0; i < 8; ++i) {
                this.level().addParticle(particle, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            Entity entity = result.getEntity();

            entity.hurt(this.damageSources().thrown(this, this.getOwner()),1000.0f);
            //this.level().explode(this, entity.getX(), entity.getY(), entity.getZ(), 0.5F, Level.ExplosionInteraction.TNT);
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }
}