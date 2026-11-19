package com.redreaper.red_nether_spellbooks.entity.spells.banshee_shot;

import com.redreaper.red_nether_spellbooks.init.ModEntities;
import com.redreaper.red_nether_spellbooks.init.ModSpells;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.jadenxgamer.netherexp.core.entity.WillOWisp;
import net.jadenxgamer.netherexp.registry.JNEEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class ExtendedWillOWisp extends WillOWisp {
    protected float damage;
    LivingEntity target;

    public ExtendedWillOWisp(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
        this.damage = 3;
        this.setNoGravity(true);
    }

    public ExtendedWillOWisp(LivingEntity shooter, Level level, LivingEntity target) {
        this(shooter, level, target, shooter.getX(), shooter.getY() + (double)1.0F, shooter.getZ(), 3);
    }

    public ExtendedWillOWisp(LivingEntity shooter, Level level, LivingEntity target, double x, double y, double z, int damage) {
        super(ModEntities.EXTENDED_WILL_O_WISP.get(), level);
        this.damage = 3;
        this.noPhysics = true;
        this.target = target;
        this.damage = damage;
        this.setPos(x, y, z);
        setOwner(shooter);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        var target = entityHitResult.getEntity();
        if (target instanceof LivingEntity living) {
            DamageSources.applyDamage(target, getDamage(), ModSpells.SOUL_FIREBOLT.get().getDamageSource(this, getOwner()));
        }
    }

    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }



    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("Damage", this.getDamage());
        tag.put("Target", (Tag) this.getTarget());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.damage = tag.getFloat("Damage");
        this.target = (LivingEntity) tag.get("Target");
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }

    public void setTarget(LivingEntity damage) {
        this.target = target;
    }

    public LivingEntity getTarget() {
        return target;
    }

}
