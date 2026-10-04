package com.redreaper.red_nether_spellbooks.init;

import com.redreaper.red_nether_spellbooks.RedsNetherSpellbooks;
import com.redreaper.red_nether_spellbooks.entity.living.summon.SummonBansheeEntity;
import com.redreaper.red_nether_spellbooks.entity.living.summon.SummonVesselEntity;
import com.redreaper.red_nether_spellbooks.entity.living.summon.SummonedBlazeEntity;
import com.redreaper.red_nether_spellbooks.entity.spells.banshee_shot.ExtendedWillOWisp;
import com.redreaper.red_nether_spellbooks.entity.spells.firebolt_shotgun_pellet.FireboltPellet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.minecraft.core.registries.Registries.ENTITY_TYPE;

public class ModEntities {
    private static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ENTITY_TYPE, RedsNetherSpellbooks.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<FireboltPellet>> FIREBOLT_PELLET =
            ENTITIES.register("firebolt_pellet", () -> EntityType.Builder.<FireboltPellet>of(FireboltPellet::new, MobCategory.MISC).fireImmune()
                    .sized(0.7F, 0.6F).eyeHeight(0.3F)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(RedsNetherSpellbooks.MOD_ID, "firebolt_pellet").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<ExtendedWillOWisp>> EXTENDED_WILL_O_WISP  =
            ENTITIES.register("extended_will_o_wisp", () -> EntityType.Builder.<ExtendedWillOWisp>of(ExtendedWillOWisp::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(8)
                    .build(ResourceLocation.fromNamespaceAndPath(RedsNetherSpellbooks.MOD_ID, "extended_will_o_wisp").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<SummonedBlazeEntity>> SUMMONED_BLAZE =
            ENTITIES.register("summoned_blaze", () -> EntityType.Builder.<SummonedBlazeEntity>of(SummonedBlazeEntity::new, MobCategory.MONSTER).
                    sized(1.4F, 1.9F).eyeHeight(1.45F).fireImmune()
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(RedsNetherSpellbooks.MOD_ID, "summoned_blaze").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<SummonVesselEntity>> SUMMONED_VESSEL =
            ENTITIES.register("summoned_vessel", () -> EntityType.Builder.<SummonVesselEntity>of(SummonVesselEntity::new, MobCategory.MONSTER).
                    sized(0.8f, 2.6f).fireImmune()
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(RedsNetherSpellbooks.MOD_ID, "summoned_vessel").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<SummonBansheeEntity>> SUMMONED_BANSHEE =
            ENTITIES.register("summoned_banshee", () -> EntityType.Builder.<SummonBansheeEntity>of(SummonBansheeEntity::new, MobCategory.MONSTER).
                    sized(1.25f, 2.375f).fireImmune()
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(RedsNetherSpellbooks.MOD_ID, "summoned_banshee").toString()));


    public static void register(IEventBus eventBus)
    {
        ENTITIES.register(eventBus);
    }

}
