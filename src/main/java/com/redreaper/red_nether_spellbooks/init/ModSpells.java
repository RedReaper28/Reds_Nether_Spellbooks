package com.redreaper.red_nether_spellbooks.init;

import com.redreaper.red_nether_spellbooks.RedsNetherSpellbooks;
import com.redreaper.red_nether_spellbooks.spells.fire.SummonBlazesSpell;
import com.redreaper.red_nether_spellbooks.spells.ice.BlackIcicleSpell;
import com.redreaper.red_nether_spellbooks.spells.necro.*;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static io.redspace.ironsspellbooks.api.registry.SpellRegistry.SPELL_REGISTRY_KEY;

public class ModSpells {
    public static final DeferredRegister<AbstractSpell> SPELLS = DeferredRegister.create(SPELL_REGISTRY_KEY, RedsNetherSpellbooks.MOD_ID);


    public static Supplier<AbstractSpell> registerSpell(AbstractSpell spell) {
        return SPELLS.register(spell.getSpellName(), () -> spell);
    }

    public static final Supplier<AbstractSpell> BANSHEE_SHOT = registerSpell(new BansheeShotSpell());
    public static final Supplier<AbstractSpell> ECTO_BOMB = registerSpell(new EctoBombSpell());
    public static final Supplier<AbstractSpell> SUMMON_BANSHEE = registerSpell(new SummonBansheeSpell());
    public static final Supplier<AbstractSpell> SUMMON_VESSEL = registerSpell(new SummonVesselSpell());

    public static final Supplier<AbstractSpell> SUMMON_BLAZE = registerSpell(new SummonBlazesSpell());

    public static final Supplier<AbstractSpell> BLACK_ICICLE = registerSpell(new BlackIcicleSpell());


    public static void register(IEventBus eventBus)
    {
        SPELLS.register(eventBus);
    }
}
