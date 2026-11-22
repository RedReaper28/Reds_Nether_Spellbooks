package com.redreaper.red_nether_spellbooks.mixin.entity;

import com.redreaper.red_nether_spellbooks.events.custom.StandOnFluidEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(LivingEntity.class)
public class EntityMixin {
    @Inject(method = "canStandOnFluid", at = @At("HEAD"), cancellable = true)
    private void standOnLiquid(FluidState fluidState, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;

        StandOnFluidEvent event = new StandOnFluidEvent(self, fluidState);
        NeoForge.EVENT_BUS.post(event);

        if (event.isCanceled()) {
            cir.setReturnValue(true);
        }
    }


}
