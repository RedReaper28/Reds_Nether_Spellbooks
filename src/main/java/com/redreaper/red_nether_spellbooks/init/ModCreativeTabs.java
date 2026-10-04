package com.redreaper.red_nether_spellbooks.init;

import com.redreaper.red_nether_spellbooks.RedsNetherSpellbooks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB=
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RedsNetherSpellbooks.MOD_ID);

    public static final Supplier<CreativeModeTab> REDS_NETHER_SPELLBOOKS =CREATIVE_MODE_TAB.register("reds_nether_spellbooks",
            ()-> CreativeModeTab.builder().icon(()->new ItemStack(ModItems.BANSHEE_SPELL_BOOK.get()))
                    .title(Component.translatable("creative_tab.reds_nether_spellbooks.reds_nether_spellbooks"))
                    .displayItems((itemDisplayParameters, output) ->{
                        output.accept(ModItems.FIREBOLT_SHELL.get());
                        output.accept(ModItems.BANSHEE_SPELL_BOOK.get());

                    }).build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }

}
