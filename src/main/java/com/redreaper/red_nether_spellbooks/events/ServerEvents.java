package com.redreaper.red_nether_spellbooks.events;

import com.redreaper.red_nether_spellbooks.init.ModItems;
import com.redreaper.red_nether_spellbooks.item.curios.necklace.SoulBurnNecklace;
import com.redreaper.red_nether_spellbooks.item.curios.necklace.TreacherusNecklace;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.acetheeldritchking.aces_spell_utils.utils.ASUtils;
import net.jadenxgamer.netherexp.registry.JNEAttachmentTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber
public class ServerEvents {

    @SubscribeEvent
    public static void increaseDamage(LivingIncomingDamageEvent event) {
        var sourceEntity = event.getSource().getEntity();
        var target = event.getEntity();
        var projectile = event.getSource().getDirectEntity();

        // Curios
        if (sourceEntity != null) {
            if (sourceEntity instanceof Player player) {

                // Treacherous Necklace - Entities with lower 50% of their health receive double(? damage
                if (ASUtils.hasCurio(player, ModItems.TREACHEROUS_NECKLACE.get())) {
                    final float MAX_HEALTH = target.getMaxHealth();
                    float baseHealth = target.getHealth();
                    double percent = (baseHealth / MAX_HEALTH) * 100;
                    if (percent < 50) {
                        if (event.getSource().is(ISSDamageTypes.FIRE_MAGIC)) {
                            target.setData(JNEAttachmentTypes.LAST_FIRE.get(), ResourceLocation.parse("netherexp:treacherous_fire"));
                            event.setAmount(event.getAmount() * 2);
                            player.getCooldowns().addCooldown(ModItems.TREACHEROUS_NECKLACE.get(), TreacherusNecklace.COOLDOWN);
                        }
                    }
                }

                if (ASUtils.hasCurio(player, ModItems.SOUL_BURN_NECKLACE.get())) {
                    if (event.getSource().is(ISSDamageTypes.FIRE_MAGIC)) {
                        target.setData(JNEAttachmentTypes.LAST_FIRE.get(), ResourceLocation.parse("minecraft:soul_fire"));
                        Utils.addEffectStack(target, MobEffectRegistry.SOUL_BURN, 10, 30 * 20);
                        player.getCooldowns().addCooldown(ModItems.SOUL_BURN_NECKLACE.get(), SoulBurnNecklace.COOLDOWN);
                    }
                }

            }
        }
    }

}
