package com.redreaper.red_nether_spellbooks.entity.spells.firebolt_shotgun_pellet;

import com.redreaper.red_nether_spellbooks.init.ModDamageTypes;
import com.redreaper.red_nether_spellbooks.init.ModEntities;
import net.jadenxgamer.netherexp.core.entity.AbstractPellet;
import net.jadenxgamer.netherexp.core.keys.JNEDamageSources;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

import java.awt.*;

public class FireboltPellet extends AbstractPellet {

    public FireboltPellet(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public FireboltPellet(double x, double y, double z, Level level) {
        this(ModEntities.FIREBOLT_PELLET.get(), level);
        this.setPos(x, y, z);
    }

    public FireboltPellet(double x, double y, double z, Level level, Entity owner) {
        this(ModEntities.FIREBOLT_PELLET.get(), level);
        this.setPos(x, y, z);
        this.setOwner(owner);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity target = result.getEntity();
        if (getOwner() != null) {
            target.hurt(this.damageSources().source(getDamageSource(), getOwner()), getDamage());
            target.setRemainingFireTicks(60);
        } else target.hurt(this.damageSources().source(getDamageSource()), getDamage());
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte) 40);
            this.hitGroundSound();
            this.discard();
        }
    }

    @Override
    public Color getTrailColor() {
        return new Color(0xD5730B);
    }

    @Override
    public Color getHitColor() {
        return new Color(0xFFD5730B, true);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return Items.AIR.getDefaultInstance();
    }

    @Override
    protected ResourceKey<DamageType> getDamageSource() {
        return ModDamageTypes.FIREBOLT_PELLET;
    }
}
