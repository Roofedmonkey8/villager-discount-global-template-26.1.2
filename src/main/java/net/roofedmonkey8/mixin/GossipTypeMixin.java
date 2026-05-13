package net.roofedmonkey8.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.world.entity.ai.gossip.GossipType;


@Mixin(GossipType.class)
public class GossipTypeMixin {
    @Shadow
        private GossipType max;

    @Inject(method = "GossipType" at = @At("TAIL"))
    private void changeCap(String enumName, int ordinal, String id, int weight, int max, int decayPerDay, int decayPerTransfer, CallbackInfo ci) {
        GossipType self = (GossipType)(Object)this;
        if (self == GossipType.MAJOR_POSITIVE) {
            this.max = 100; // 5 cures * 20
        } else if (self == GossipType.MINOR_POSITIVE) {
            this.max = 125; // 5 cures * 25
        }
    }
}