package com.redreaper.red_nether_spellbooks.entity.spells.pyroclast_daggers;

import com.redreaper.red_nether_spellbooks.RedsNetherSpellbooks;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class PyroclastDaggerModel extends GeoModel<PyroclastDaggerProjectile> {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(RedsNetherSpellbooks.MOD_ID, "textures/entity/spell/pyroclast_dagger.png");
    public static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath(IronsSpellbooks.MODID, "geo/fiery_dagger.geo.json");

    @Override
    public ResourceLocation getModelResource(PyroclastDaggerProjectile animatable) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(PyroclastDaggerProjectile animatable) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(PyroclastDaggerProjectile animatable) {
        return AbstractSpellCastingMob.animationInstantCast;
    }
}
