package com.redreaper.red_nether_spellbooks.events;

import com.redreaper.red_nether_spellbooks.RedsNetherSpellbooks;
import com.redreaper.red_nether_spellbooks.entity.spells.ecto_bomb.SoulBombRenderer;
import com.redreaper.red_nether_spellbooks.entity.spells.firebolt_shotgun_pellet.FireboltPelletRenderer;
import com.redreaper.red_nether_spellbooks.init.ModEntities;
import net.jadenxgamer.netherexp.client.rendering.entity.*;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = RedsNetherSpellbooks.MOD_ID, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        EntityRenderers.register(ModEntities.FIREBOLT_PELLET.get(), FireboltPelletRenderer::new);
        EntityRenderers.register(ModEntities.ECTO_BOMB.get(), SoulBombRenderer::new);
        EntityRenderers.register(ModEntities.SOUL_FIELD.get(), NoopRenderer::new);
        EntityRenderers.register(ModEntities.EXTENDED_BLACK_ICICLE.get(), BlackIcicleRenderer::new);
        EntityRenderers.register(ModEntities.EXTENDED_WILL_O_WISP.get(), WillOWispRenderer::new);

        EntityRenderers.register(ModEntities.SUMMONED_BLAZE.get(), JNEBlazeRenderer::new);
        EntityRenderers.register(ModEntities.SUMMONED_BANSHEE.get(), BansheeRenderer::new);
        EntityRenderers.register(ModEntities.SUMMONED_CARCASS.get(), CarcassRenderer::new);
        EntityRenderers.register(ModEntities.SUMMONED_VESSEL.get(), VesselRenderer::new);

    }

    @SubscribeEvent
    public static void registerRenderer(EntityRenderersEvent.RegisterRenderers event){

    }
}
