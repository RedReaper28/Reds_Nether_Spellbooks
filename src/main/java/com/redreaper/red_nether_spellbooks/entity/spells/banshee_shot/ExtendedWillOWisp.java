package com.redreaper.red_nether_spellbooks.entity.spells.banshee_shot;

import com.redreaper.red_nether_spellbooks.init.ModEntities;
import com.redreaper.red_nether_spellbooks.init.ModSpells;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.entity.WillOWisp;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.windcharge.WindCharge;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class ExtendedWillOWisp extends WillOWisp {
    public AnimationState loopAnimation;
    private LivingEntity target;
    private float currentSpeed;
    private float damage;
    private Vec3 currentDirection;
    private Vec3 smoothedDirection;
    private Vec3 lastParticlePos;
    private double distanceSinceLastParticle;
    private boolean particleTrailInitialized;
    private float manoeuvrability;

    public ExtendedWillOWisp(EntityType<? extends WillOWisp> entityType, Level level) {
        super(entityType, level);
        this.loopAnimation = new AnimationState();
        this.distanceSinceLastParticle = (double)0.0F;
        this.particleTrailInitialized = false;
        this.manoeuvrability = (float) JNEConfigs.GENERIC_WILL_O_WISP_MANEUVERABILITY.getAsDouble();
        this.noPhysics = true;
        this.initialize();
    }

    public ExtendedWillOWisp(LivingEntity shooter, Level level, LivingEntity target) {
        this(shooter, level, target, shooter.getX(), shooter.getY() + (double)1.0F, shooter.getZ());
    }

    public ExtendedWillOWisp(LivingEntity shooter, Level level, LivingEntity target, double x, double y, double z) {
        super(ModEntities.EXTENDED_WILL_O_WISP.get(), level);
        this.loopAnimation = new AnimationState();
        this.distanceSinceLastParticle = (double)0.0F;
        this.particleTrailInitialized = false;
        this.manoeuvrability = (float)JNEConfigs.GENERIC_WILL_O_WISP_MANEUVERABILITY.getAsDouble();
        this.noPhysics = true;
        this.target = target;
        this.setPos(x, y, z);
        this.initialize();
        this.initializeDirection(shooter);
    }



    private void initialize() {
        this.currentSpeed = 0.005F;
        this.currentDirection = new Vec3((double)1.0F, (double)0.0F, (double)0.0F);
        this.smoothedDirection = this.currentDirection;
        this.particleTrailInitialized = false;
    }

    private void initializeDirection(LivingEntity shooter) {
        Vec3 lookAngle = shooter.getLookAngle().normalize();
        if (this.target != null && this.target.isAlive()) {
            Vec3 toTarget = new Vec3(this.target.getX() - this.getX(), this.target.getY() + (double)1.0F - this.getY(), this.target.getZ() - this.getZ());
            if (toTarget.lengthSqr() > 0.001) {
                this.currentDirection = lookAngle.scale(0.3).add(toTarget.normalize().scale(0.7)).normalize();
            } else {
                this.currentDirection = lookAngle;
            }
        } else {
            this.currentDirection = lookAngle;
        }

        this.smoothedDirection = this.currentDirection;
        this.setDeltaMovement(this.currentDirection.scale((double)this.currentSpeed));
        this.updateRotationFromDirection();
    }

    private void updateRotationFromDirection() {
        double horizontalDistance = Math.sqrt(this.currentDirection.x * this.currentDirection.x + this.currentDirection.z * this.currentDirection.z);
        this.setYRot((float)(Math.atan2(this.currentDirection.x, this.currentDirection.z) * (180D / Math.PI)));
        this.setXRot((float)(Math.atan2(this.currentDirection.y, horizontalDistance) * (180D / Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }


    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        if (entity instanceof WindCharge) return; // Sometimes the wind-charge if hit just right breaks the will o' wisp. this prevents that
        if (entity instanceof LivingEntity living) {
            if (living.isBlocking()) {
                this.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                        SoundEvents.SHIELD_BLOCK, SoundSource.NEUTRAL, 1f, 1f);
                living.getUseItem().hurtAndBreak(5, living, LivingEntity.getSlotForHand(living.getUsedItemHand()));
            } else DamageSources.applyDamage(entity, getDamage(), ModSpells.BANSHEE_SHOT.get().getDamageSource(this, getOwner()));
        }
        super.onHitEntity(result);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("Damage", this.getDamage());
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


}
