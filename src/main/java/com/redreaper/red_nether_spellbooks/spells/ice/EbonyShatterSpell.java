package com.redreaper.red_nether_spellbooks.spells.ice;

import com.redreaper.red_nether_spellbooks.RedsNetherSpellbooks;
import com.redreaper.red_nether_spellbooks.init.ModMobEffects;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.acetheeldritchking.aces_spell_utils.spells.ASSpellAnimations;
import net.acetheeldritchking.aces_spell_utils.utils.ASUtils;
import net.acetheeldritchking.aces_spell_utils.utils.ImpactFrameHandler;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

public class EbonyShatterSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(RedsNetherSpellbooks.MOD_ID, "ebony_shatter");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.damage", getDamageText(spellLevel, caster)),
                Component.translatable("ui.irons_spellbooks.rend", Utils.stringTruncation((getRendAmplifier(spellLevel, caster) + 1) * 5, 1)),
                Component.translatable("ui.irons_spellbooks.effect_length", Utils.timeFromTicks(getRendDuration(spellLevel, caster), 1)));
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.LEGENDARY)
            .setSchoolResource(SchoolRegistry.ICE_RESOURCE)
            .setMaxLevel(8)
            .setCooldownSeconds(15)
            .build();

    public EbonyShatterSpell()
    {
        this.manaCostPerLevel = 10;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 0;
        this.castTime = 15;
        this.baseManaCost = 40;
    }

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public CastType getCastType() {
        return CastType.INSTANT;
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return ASSpellAnimations.ANIMATION_SPELL_PUNCH;
    }

    public Optional<SoundEvent> getCastStartSound() {return Optional.of(SoundRegistry.ICE_CAST.get());}

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        float radius = 3;
        float distance = 5F;
        Vec3 hitLocation = entity.position().add(0, entity.getBbHeight() * 0.7F, 0).add(entity.getForward().multiply(distance, 0.35F, distance));
        var entities = entity.level().getEntities(entity, AABB.ofSize(hitLocation, radius * 2, radius, radius * 2));

        for (Entity target : entities)
        {
            var damageSource = this.getDamageSource(entity);
            if (target instanceof LivingEntity && entity.isAlive() && entity.isPickable() && entity.distanceToSqr(target) < radius * radius && Utils.hasLineOfSight(level, entity.getEyePosition(), target.getBoundingBox().getCenter(), true))
            {
                int i = getRendDuration(spellLevel, entity);
                if (DamageSources.applyDamage(target, getDamage(spellLevel, entity), this.getDamageSource(entity)))
                {
                    MagicManager.spawnParticles(level, JNEParticleTypes.BLACK_FLAKE.get(), target.getX(), target.getY() + target.getBbHeight() * .5f, target.getZ(), 50, target.getBbWidth() * .5f, target.getBbHeight() * .5f, target.getBbWidth() * .5f, .03, false);

                    if (target.isFullyFrozen())
                    {
                        target.setTicksFrozen(0);
                        EnchantmentHelper.doPostAttackEffects((ServerLevel) level, target, damageSource);
                        ((LivingEntity) target).addEffect(new MobEffectInstance(ModMobEffects.ICE_SHATTER,i, getRendAmplifier(spellLevel , entity), true, true, true));
                            if (target.isAlive() && target.isPickable() && Utils.hasLineOfSight(entity.level(), hitLocation.add((double)0.0F, (double)1.0F, (double)0.0F), target.getBoundingBox().getCenter(), true)) {
                                this.handleKnockback(entity, (LivingEntity)target, spellLevel);
                                triggerImpactFrame(entity);
                            }

                    }
                    else {
                        ((LivingEntity) target).addEffect(new MobEffectInstance(MobEffectRegistry.REND,i, getRendAmplifier(spellLevel , entity), true, true, true));
                    }
                }
            }
        }


        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private void triggerImpactFrame(LivingEntity entity) {
        if (entity instanceof ServerPlayer serverPlayer) {
            ImpactFrameHandler.trigger(serverPlayer, 0xc8fffe, 2834514, 0.8f, ImpactFrameHandler.DEFAULT_THRESHOLD, 10, 1, 0.1f);
        }
    }

    public void handleKnockback(LivingEntity entity, LivingEntity targetEntity, int spellLevel) {
        Vec3 angleVector = entity.position().subtract(targetEntity.position()).normalize();
        Vec3 vec = angleVector.multiply(2.0F, 1.0F, 2.0F).normalize().scale(-1.0F * this.getForce(spellLevel, entity));
        if (targetEntity instanceof ServerPlayer serverPlayer) {
            serverPlayer.hurtMarked = true;
        }

        targetEntity.setDeltaMovement(targetEntity.getDeltaMovement().add(vec));
        targetEntity.addEffect(new MobEffectInstance(MobEffectRegistry.AIRBORNE, 60, 1));
        Vec3 particleLocation = targetEntity.position();
        MagicManager.spawnParticles(entity.level(), JNEParticleTypes.BLACK_SMOKE.get(), particleLocation.x, particleLocation.y + (double)(targetEntity.getBbHeight() / 2.0F), particleLocation.z, 50, (double)0.0F, (double)0.0F, (double)0.0F, 0.2, false);
    }

    private float getForce(int spellLevel, LivingEntity entity) {return this.getSpellPower(spellLevel, entity) * 0.1F;}


    public int getRendAmplifier(int spellLevel, LivingEntity caster) {
        return spellLevel + 2;
    }

    public int getRendDuration(int spellLevel, LivingEntity caster) {
        return (int) (getSpellPower(spellLevel, caster) * 20 * 20);
    }

    private float getDamage(int spellLevel, LivingEntity caster)
    {
        float baseDamage = ASUtils.getDamageForAttributes(this, caster, spellLevel, Attributes.ATTACK_DAMAGE, 0.35F);

        return baseDamage + getWeaponDamage(caster);
    }

    private float getWeaponDamage(LivingEntity caster)
    {
        return Utils.getWeaponDamage(caster);
    }

    private String getDamageText(int spellLevel, LivingEntity caster)
    {
        if (caster != null)
        {
            float weaponDamage = Utils.getWeaponDamage(caster);
            String plus = "";
            if (weaponDamage > 0)
            {
                plus = String.format(" (+%s)", Utils.stringTruncation(weaponDamage, 1));
            }
            String damage = Utils.stringTruncation(getDamage(spellLevel, caster), 1);
            return damage + plus;
        }
        return "" + getSpellPower(spellLevel, caster);
    }
}
