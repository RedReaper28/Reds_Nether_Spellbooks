package com.redreaper.red_nether_spellbooks.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class IceShatterMobEffect extends MobEffect {
    public static final float ARMOR_PER_LEVEL = -.05f;
    public static final float ICE_ARMOR_PER_LEVEL = -.025f;

    public IceShatterMobEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }
}
