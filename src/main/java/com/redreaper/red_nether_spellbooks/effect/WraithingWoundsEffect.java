package com.redreaper.red_nether_spellbooks.effect;

import com.redreaper.red_nether_spellbooks.init.ModMobEffects;
import io.redspace.ironsspellbooks.effect.ISyncedMobEffect;
import io.redspace.ironsspellbooks.effect.MagicMobEffect;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber
public class WraithingWoundsEffect extends MagicMobEffect implements ISyncedMobEffect {
    public WraithingWoundsEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingIncomingDamageEvent event) {
        LivingEntity entityTarget = event.getEntity();
        MobEffectInstance inst = event.getEntity().getEffect(ModMobEffects.WRAITHING_WOUNDS);

        if (inst == null) {
            return;
        }

        if (entityTarget instanceof Player player) {
            player.addEffect(new MobEffectInstance(MobEffectRegistry.SLOWED, 10*20, 9));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 5*20, 1));

        }
    }
}
