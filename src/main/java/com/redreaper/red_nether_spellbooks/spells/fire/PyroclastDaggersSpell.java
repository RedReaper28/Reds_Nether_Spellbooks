package com.redreaper.red_nether_spellbooks.spells.fire;

import com.redreaper.red_nether_spellbooks.RedsNetherSpellbooks;
import com.redreaper.red_nether_spellbooks.entity.spells.pyroclast_daggers.PyroclastDaggerProjectile;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.RaycastBuilder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import net.acetheeldritchking.aces_spell_utils.utils.RibbonHandler;
import net.acetheeldritchking.aces_spell_utils.vfx.ribbon.ColorRamp;
import net.acetheeldritchking.aces_spell_utils.vfx.ribbon.Curve;
import net.acetheeldritchking.aces_spell_utils.vfx.ribbon.Easing;
import net.acetheeldritchking.aces_spell_utils.vfx.ribbon.RibbonConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class PyroclastDaggersSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(RedsNetherSpellbooks.MOD_ID, "pyroclast_daggers");

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.UNCOMMON)
            .setSchoolResource(SchoolRegistry.FIRE_RESOURCE)
            .setMaxLevel(6)
            .setCooldownSeconds(150)
            .build();

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.irons_spellbooks.damage", Utils.stringTruncation(getSpellPower(spellLevel, caster), 1)));
    }

    public PyroclastDaggersSpell() {
        this.manaCostPerLevel = 10;
        this.baseSpellPower = 10;
        this.spellPowerPerLevel = 3;
        this.castTime = 30-5;
        this.baseManaCost = 50;
    }

    public CastType getCastType() {
        return CastType.CONTINUOUS;
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
            return SpellAnimations.ANIMATION_LONG_CAST;
    }


    public DefaultConfig getDefaultConfig() {
        return this.defaultConfig;
    }

    public ResourceLocation getSpellResource() {
        return this.spellId;
    }

    private boolean hasCinderous;

    public void onCast(Level world, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        if (world.isClientSide) return;
        Vec3 eyePos = entity.getEyePosition();
        HitResult hitResult = RaycastBuilder.begin(world, entity)
                .range(40.0F)
                .checkForBlocks(true)
                .build();
        Vec3 targetPos;
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            targetPos = hitResult.getLocation();
        } else {
            targetPos = hitResult.getLocation();
        }

        int count = 4;
        double arcAngle = Math.toRadians(120);
        double radius = 1.6;
        double height = 1.2;

        Vec3 forward = entity.getLookAngle().normalize();
        Vec3 up = new Vec3(0, 0.5, 0);
        Vec3 right = forward.cross(up).normalize();
        up = right.cross(forward).normalize();

        Vec3 arcCenter = entity.position()
                .add(0, entity.getEyeHeight() + height, 0)
                .add(forward.scale(0.5));

        for (int i = 0; i < count; i++) {
            double t = (double) i / (count - 1);
            double angle = -arcAngle / 2 + t * arcAngle;

            Vec3 offset = right.scale(Math.sin(angle) * radius)
                    .add(up.scale(Math.cos(angle) * radius * 0.7));

            Vec3 spawnPos = arcCenter.add(offset);

            PyroclastDaggerProjectile dagger = new PyroclastDaggerProjectile(world,entity);
            RibbonHandler.attach(dagger, new RibbonConfig(
                    new ColorRamp(new int[]{0xD5730B}, Easing.LINEAR),
                    new Curve(0.18F, 0.0F, Easing.EASE_OUT_QUAD),
                    new Curve(1.0F, 0.0F, Easing.LINEAR),
                    24,
                    true));
            dagger.setOwner(entity);
            dagger.setDamage(this.getDamage(spellLevel, entity));
            dagger.setExplosionRadius(0f);
            dagger.setPos(spawnPos);

            Vec3 aimDirection = targetPos.subtract(spawnPos);
            if (aimDirection.lengthSqr() < 1.0E-7) {
                aimDirection = forward;
            }

            dagger.setDeltaMovement(forward.scale(dagger.getSpeed()));
            dagger.deltaMovementOld = dagger.getDeltaMovement();

            world.addFreshEntity(dagger);
        }
        super.onCast(world, spellLevel, entity, castSource, playerMagicData);
    }


    public SpellDamageSource getDamageSource(@Nullable Entity projectile, Entity attacker) {
        return super.getDamageSource(projectile, attacker)
                .setFireTicks(60)
                .setIFrames(0)
                ;
    }

    private float getDamage(int spellLevel, LivingEntity entity) {
        return this.getSpellPower(spellLevel, entity) * 0.5F;
    }
}
