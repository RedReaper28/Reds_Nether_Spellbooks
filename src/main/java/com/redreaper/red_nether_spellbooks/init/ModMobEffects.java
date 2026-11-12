package com.redreaper.red_nether_spellbooks.init;

import com.redreaper.red_nether_spellbooks.RedsNetherSpellbooks;
import com.redreaper.red_nether_spellbooks.effect.IceShatterMobEffect;
import com.redreaper.red_nether_spellbooks.effect.WraithingWoundsEffect;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMobEffects {
    public static final DeferredRegister<MobEffect>MOB_EFFECT_DEFERRED_REGISTER = DeferredRegister.create(Registries.MOB_EFFECT, RedsNetherSpellbooks.MOD_ID);

    public static final DeferredHolder<MobEffect, MobEffect> WRAITHING_WOUNDS = MOB_EFFECT_DEFERRED_REGISTER.register("wraithing_wounds", () -> new WraithingWoundsEffect(MobEffectCategory.HARMFUL, 2906215));

    public static final DeferredHolder<MobEffect, MobEffect> ICE_SHATTER = MOB_EFFECT_DEFERRED_REGISTER.register("ice_shatter", () -> new IceShatterMobEffect(MobEffectCategory.HARMFUL, 0xc8fffe)
            .addAttributeModifier(AttributeRegistry.ICE_MAGIC_RESIST, RedsNetherSpellbooks.id("mobeffect_ice_shatter"), IceShatterMobEffect.ICE_ARMOR_PER_LEVEL, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
            .addAttributeModifier(Attributes.ARMOR, RedsNetherSpellbooks.id("mobeffect_ice_shatter"), IceShatterMobEffect.ARMOR_PER_LEVEL, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));


    public static void register(IEventBus eventBus) {
        MOB_EFFECT_DEFERRED_REGISTER.register(eventBus);
    }
}
