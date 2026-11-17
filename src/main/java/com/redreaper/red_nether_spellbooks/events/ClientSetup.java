package com.redreaper.red_nether_spellbooks.events;

import com.redreaper.red_nether_spellbooks.RedsNetherSpellbooks;
import com.redreaper.red_nether_spellbooks.entity.living.soul_keeper.SoulKeeperRenderer;
import com.redreaper.red_nether_spellbooks.entity.spells.ecto_bomb.SoulBombRenderer;
import com.redreaper.red_nether_spellbooks.entity.spells.firebolt_shotgun_pellet.FireboltPelletRenderer;
import com.redreaper.red_nether_spellbooks.entity.spells.phantasm_bolt.PhasmoBoltRenderer;
import com.redreaper.red_nether_spellbooks.entity.spells.pyroclast_daggers.PyroclastDaggerRenderer;
import com.redreaper.red_nether_spellbooks.entity.spells.soul_firebolt.SoulFireboltRenderer;
import com.redreaper.red_nether_spellbooks.init.ModEntities;
import com.redreaper.red_nether_spellbooks.init.ModParticleTypes;
import com.redreaper.red_nether_spellbooks.particle.SoulEmberParticle;
import net.jadenxgamer.netherexp.client.rendering.entity.*;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = RedsNetherSpellbooks.MOD_ID, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {


    }

    @SubscribeEvent
    public static void registerRenderer(EntityRenderersEvent.RegisterRenderers event){
        EntityRenderers.register(ModEntities.FIREBOLT_PELLET.get(), FireboltPelletRenderer::new);
        EntityRenderers.register(ModEntities.SOUL_FIREBOLT_PROJECTILE.get(), SoulFireboltRenderer::new);
        EntityRenderers.register(ModEntities.ECTO_BOMB.get(), SoulBombRenderer::new);
        EntityRenderers.register(ModEntities.SOUL_FIELD.get(), NoopRenderer::new);
        EntityRenderers.register(ModEntities.PHASMO_BOLT.get(), PhasmoBoltRenderer::new);
        EntityRenderers.register(ModEntities.PYROCLAST_DAGGER.get(), PyroclastDaggerRenderer::new);
        EntityRenderers.register(ModEntities.EBONY_SHATTER.get(), NoopRenderer::new);

        EntityRenderers.register(ModEntities.EXTENDED_BLACK_ICICLE.get(), BlackIcicleRenderer::new);
        EntityRenderers.register(ModEntities.EXTENDED_WILL_O_WISP.get(), WillOWispRenderer::new);

        EntityRenderers.register(ModEntities.SUMMONED_BLAZE.get(), JNEBlazeRenderer::new);
        EntityRenderers.register(ModEntities.SUMMONED_BANSHEE.get(), BansheeRenderer::new);
        EntityRenderers.register(ModEntities.SUMMONED_VESSEL.get(), VesselRenderer::new);

        EntityRenderers.register(ModEntities.SOUL_KEEPER.get(), SoulKeeperRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event)
    {
        event.registerSpriteSet(ModParticleTypes.SOUL_EMBERS.get(), SoulEmberParticle.Provider::new);

    }
}
