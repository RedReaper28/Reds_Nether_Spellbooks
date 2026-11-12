package com.redreaper.red_nether_spellbooks.entity.spells.ebony_shatter;

import com.redreaper.red_nether_spellbooks.init.ModEntities;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class EbonyShatterEntity extends AoeEntity {
    public EbonyShatterEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public final int ticksPerFrame = 3;
    public final int deathTime = ticksPerFrame * 4;

    public EbonyShatterEntity(Level level, boolean mirrored)
    {
        this(ModEntities.EBONY_SHATTER.get(), level);
    }

    @Override
    public void tick() {
        if (!firstTick)
        {
            checkHits();
            firstTick = true;
        }
        if (tickCount >= deathTime)
        {
            discard();
        }
    }

    @Override
    public void applyEffect(LivingEntity target) {
    }

    public Optional<ParticleOptions> getParticle() {
        return Optional.empty();
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    public float getParticleCount() {
        return 0;
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket pPacket) {
        super.recreateFromPacket(pPacket);
        this.xRotO = this.getXRot();
        this.yRotO = this.getYRot();
    }
}
