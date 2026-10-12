package com.redreaper.red_nether_spellbooks.entity.spells.phantasm_bolt;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.jadenxgamer.netherexp.registry.JNEAttachmentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PhasmoBoltEntity extends AbstractMagicProjectile {
    protected final List<UUID> victims = new ArrayList<>();
    protected int blockHits;
    protected BlockPos lastHitBlock;

    public PhasmoBoltEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setNoGravity(true);
        this.setInfinitePiercing();
    }

    public PhasmoBoltEntity(Level levelIn, LivingEntity shooter) {
        this(EntityRegistry.MAGIC_ARROW_PROJECTILE.get(), levelIn);
        setOwner(shooter);
    }

    @Override
    public void trailParticles() {
    }

    @Override
    public void impactParticles(double x, double y, double z) {
        MagicManager.spawnParticles(level(), ParticleHelper.SOULFIRE_SPARKS, x, y, z, 10, .1, .1, .1, .4, false);
    }

    @Override
    public float getSpeed() {
        return 2.7f;
    }

    @Override
    public Optional<Holder<SoundEvent>> getImpactSound() {
        return Optional.of(SoundRegistry.FORCE_IMPACT);
    }

    @Override
    protected void doImpactSound(Holder<SoundEvent> sound) {
        level().playSound(null, getX(), getY(), getZ(), sound, SoundSource.NEUTRAL, 2, .55f + Utils.random.nextFloat() * .1f);
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult pResult) {
        var blockPos = BlockPos.containing(pResult.getLocation());
        if (pResult.getType() == HitResult.Type.BLOCK && !blockPos.equals(lastHitBlock)) {
            lastHitBlock = blockPos;
            if (blockHits++ > 5) {
                discard();
            }
        }

    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (!victims.contains(entity.getUUID())) {
            DamageSources.applyDamage(entity, damage, SpellRegistry.MAGIC_ARROW_SPELL.get().getDamageSource(this, getOwner()));
                entity.setRemainingFireTicks(60);
                entity.setData(JNEAttachmentTypes.LAST_FIRE.get(), ResourceLocation.parse("minecraft:soul_fire"));
            victims.add(entity.getUUID());
        }
        consumeEntityImpact(entityHitResult, true);
    }


    @Override
    protected boolean shouldPierceShields() {
        return true;
    }

    @Override
    public boolean collidesWithBlocks() {
        return false;
    }
}
