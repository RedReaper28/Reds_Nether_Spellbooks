package com.redreaper.red_nether_spellbooks.init;

import com.redreaper.red_nether_spellbooks.RedsNetherSpellbooks;
import com.redreaper.red_nether_spellbooks.entity.spells.firebolt_shotgun_pellet.FireboltPellet;
import com.redreaper.red_nether_spellbooks.item.curios.spellbooks.BansheeSpellBookIte;
import net.jadenxgamer.netherexp.core.item.ShotgunShellItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister.Items ITEMS= DeferredRegister.createItems(RedsNetherSpellbooks.MOD_ID);

    public static final Supplier<Item> FIREBOLT_SHELL = ITEMS.register("firebolt_shotgun_shell", () ->
            new ShotgunShellItem(FireboltPellet.class, new Item.Properties()));

    public static final DeferredHolder<Item, Item> BANSHEE_SPELL_BOOK = ITEMS.register("banshee_spell_book",
            BansheeSpellBookIte::new);

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
