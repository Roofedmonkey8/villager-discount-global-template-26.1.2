package net.roofedmonkey8.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.gossip.GossipContainer;
import net.minecraft.world.entity.ai.gossip.GossipType;
import net.minecraft.world.entity.ai.village.ReputationEventType;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;


@Mixin(net.minecraft.world.entity.npc.villager.Villager.class)
public class VillagerMixin {
	
	private static final UUID FAKE_UUID = UUID.fromString("c2795f72-10ea-487b-be27-e55ade0ab522");
  @Shadow
    private GossipContainer gossips;

	
  @Inject(method = "onReputationEventFrom", at = @At("HEAD"), cancellable = true)
    private void writeRep(ReputationEventType type, Entity source, CallbackInfo ci) {
        if (type == ReputationEventType.ZOMBIE_VILLAGER_CURED) {
            this.gossips.add(FAKE_UUID, GossipType.MAJOR_POSITIVE, 20);
            this.gossips.add(FAKE_UUID, GossipType.MINOR_POSITIVE, 25);
            ci.cancel();
        }
    }

  @Inject(method = "getPlayerReputation", at = @At("HEAD"), cancellable = true)
    private void getPlayerReputation(Player player, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(this.gossips.getReputation(FAKE_UUID, t -> true));
    }

}
