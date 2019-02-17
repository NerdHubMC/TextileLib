package nerdhub.textilelib.mixins;

import nerdhub.textilelib.eventhandlers.EventRegistry;
import nerdhub.textilelib.events.entity.player.PlayerInteractBlockEvent;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class MixinClientPlayerInteractionManager {

    @SuppressWarnings("Duplicates")
    @Inject(method = "interactBlock", at = @At("HEAD"), cancellable = true)
    private void interactBlock(ClientPlayerEntity clientPlayerEntity_1, ClientWorld clientWorld_1, Hand hand_1, BlockHitResult blockHitResult_1, CallbackInfoReturnable<ActionResult> cir) {
        PlayerInteractBlockEvent interactBlockEvent = new PlayerInteractBlockEvent(clientPlayerEntity_1, hand_1, clientWorld_1.getBlockState(blockHitResult_1.getBlockPos()), blockHitResult_1.getBlockPos());
        EventRegistry.INSTANCE.fireEvent(interactBlockEvent);
        if(interactBlockEvent.isCanceled()) {
            cir.setReturnValue(ActionResult.FAILURE);
            cir.cancel();
        }
    }
}
