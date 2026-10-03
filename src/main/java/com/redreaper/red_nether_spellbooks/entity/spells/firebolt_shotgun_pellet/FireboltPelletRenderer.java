package com.redreaper.red_nether_spellbooks.entity.spells.firebolt_shotgun_pellet;

import com.redreaper.red_nether_spellbooks.RedsNetherSpellbooks;
import net.jadenxgamer.netherexp.client.rendering.entity.PelletRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FireboltPelletRenderer extends PelletRenderer<FireboltPellet> {

    public FireboltPelletRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(FireboltPellet entity) {
        return RedsNetherSpellbooks.id("textures/entity/spell/firebolt_pellet.png");
    }
}
