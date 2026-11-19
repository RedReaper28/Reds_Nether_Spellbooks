package com.redreaper.red_nether_spellbooks.item.curios.ring;

import com.redreaper.red_nether_spellbooks.init.ModItems;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.compat.Curios;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.network.particles.FieryExplosionParticlesPacket;
import io.redspace.ironsspellbooks.network.particles.ShockwaveParticlesPacket;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import net.acetheeldritchking.aces_spell_utils.items.curios.FlatCooldownPassiveAbilityCurio;
import net.acetheeldritchking.aces_spell_utils.utils.ASRarities;
import net.acetheeldritchking.aces_spell_utils.utils.ASUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber
public class CombustionRing extends FlatCooldownPassiveAbilityCurio {
    public static int COOLDOWN = 10 * 20;
    public static final int RADIUS = 4;
    public static final int RADIUS_SQR = RADIUS * RADIUS;

    public CombustionRing() {
        super(new Properties().fireResistant().stacksTo(1).rarity(ASRarities.ARID_RARITY_PROXY.getValue()).fireResistant(), Curios.RING_SLOT);
    }

    @SubscribeEvent
    public static void handleAbility(LivingIncomingDamageEvent event) {
        var sourceEntity = event.getSource().getEntity();
        var target = event.getEntity();

        if (target instanceof Player serverPlayer) {
            if (ASUtils.hasCurio(serverPlayer, ModItems.COMBUSTION_RING.get()) && (!serverPlayer.getCooldowns().isOnCooldown(ModItems.COMBUSTION_RING.get()))) {
                var vec = serverPlayer.getBoundingBox().getCenter();

                //Effect
                serverPlayer.level().getEntities(serverPlayer, serverPlayer.getBoundingBox().inflate(3)).forEach(entity -> {
                    var d = Math.max(entity.distanceToSqr(serverPlayer), .2);
                    if (d < RADIUS_SQR && !DamageSources.isFriendlyFireBetween(serverPlayer, entity)) {
                        var f = 1 - d / (RADIUS_SQR) + .6f * (entity instanceof LivingEntity living ? 1 - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) : 1);
                        var impulse = entity.getBoundingBox().getCenter().subtract(serverPlayer.getBoundingBox().getCenter()).normalize().add(0, 0.1, 0).scale(f);
                        float damage = 5;
                        DamageSources.applyDamage(entity, damage, SpellRegistry.FIREBALL_SPELL.get().getDamageSource(serverPlayer, serverPlayer));
                        entity.hurtMarked = true;
                    }
                });

                //Visual Explosion
                serverPlayer.level().playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.NEUTRAL, .8F, 1.3F);
                PacketDistributor.sendToPlayersTrackingEntity(serverPlayer, new FieryExplosionParticlesPacket(serverPlayer.position().subtract(serverPlayer.getDeltaMovement().scale(0.5)), RADIUS_SQR));
                PacketDistributor.sendToPlayersTrackingEntityAndSelf(serverPlayer, new ShockwaveParticlesPacket(new Vec3(serverPlayer.getX(), serverPlayer.getY() + .165f, serverPlayer.getZ()), RADIUS, ParticleRegistry.FIRE_PARTICLE.get()));

            }
        }
    }

    @Override
    protected int getCooldownTicks() {
        return 10*20;
    }
}
