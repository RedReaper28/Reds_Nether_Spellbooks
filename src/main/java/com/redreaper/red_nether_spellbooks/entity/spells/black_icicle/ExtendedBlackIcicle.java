package com.redreaper.red_nether_spellbooks.entity.spells.black_icicle;

import com.redreaper.red_nether_spellbooks.init.ModEntities;
import com.redreaper.red_nether_spellbooks.init.ModSpells;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.jadenxgamer.netherexp.core.entity.BlackIcicle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ExtendedBlackIcicle extends BlackIcicle {
    protected float damage;
    public ExtendedBlackIcicle(EntityType type, double x, double y, double z, Level worldIn) {
        super(type, worldIn);
        this.setNoGravity(true);
        this.setPos(x, y, z);
    }

    public ExtendedBlackIcicle(EntityType type, Level worldIn) {
        super(type, worldIn);
    }


    public ExtendedBlackIcicle(Level worldIn, LivingEntity shooter) {
        this(ModEntities.EXTENDED_BLACK_ICICLE.get(), shooter.getX(), shooter.getEyeY(), shooter.getZ(), worldIn);
        this.setOwner(shooter);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        var target = entityHitResult.getEntity();
        if (target instanceof LivingEntity living) {
            DamageSources.applyDamage(target, getDamage(), ModSpells.BLACK_ICICLE.get().getDamageSource(this, getOwner()));
            living.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 7 * 20, 1));
        }
    }

    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

    }

    public float getSpeed() {
        return 1.75f;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("Damage", this.getDamage());
        tag.putInt("Age", tickCount);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.damage = tag.getFloat("Damage");
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }

    public void shoot(Vec3 rotation) {
        setDeltaMovement(rotation.scale(getSpeed()));
    }
}
