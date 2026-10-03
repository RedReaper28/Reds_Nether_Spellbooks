package com.redreaper.red_nether_spellbooks.events;

import com.redreaper.red_nether_spellbooks.RedsNetherSpellbooks;
import com.redreaper.red_nether_spellbooks.entity.spells.firebolt_shotgun_pellet.FireboltPelletRenderer;
import com.redreaper.red_nether_spellbooks.init.ModEntities;
import net.jadenxgamer.netherexp.client.rendering.entity.JNEBlazeRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = RedsNetherSpellbooks.MOD_ID, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        EntityRenderers.register(ModEntities.FIREBOLT_PELLET.get(), FireboltPelletRenderer::new);

        EntityRenderers.register(ModEntities.SUMMONED_BLAZE.get(), JNEBlazeRenderer::new);

    }

    @SubscribeEvent
    public static void registerRenderer(EntityRenderersEvent.RegisterRenderers event){

    }
}
