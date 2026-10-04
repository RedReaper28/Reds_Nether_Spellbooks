package com.redreaper.red_nether_spellbooks.events;

import com.redreaper.red_nether_spellbooks.RedsNetherSpellbooks;
import com.redreaper.red_nether_spellbooks.init.ModEntities;
import net.jadenxgamer.netherexp.core.entity.Banshee;
import net.jadenxgamer.netherexp.core.entity.Vessel;
import net.minecraft.world.entity.monster.Blaze;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = RedsNetherSpellbooks.MOD_ID)
public class CommonSetup {

    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(ModEntities.SUMMONED_BLAZE.get(), Blaze.createAttributes().build());
        event.put(ModEntities.SUMMONED_VESSEL.get(), Vessel.createAttributes().build());
        event.put(ModEntities.SUMMONED_BANSHEE.get(), Banshee.createAttributes().build());
    }
}
