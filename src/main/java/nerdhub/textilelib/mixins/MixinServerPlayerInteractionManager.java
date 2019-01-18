package nerdhub.textilelib.mixins;

import nerdhub.textilelib.eventhandlers.EventRegistry;
import nerdhub.textilelib.events.block.BlockBreakEvent;
import nerdhub.textilelib.events.entity.player.PlayerInteractBlockEvent;

import net.minecraft.client.network.packet.BlockUpdateClientPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class MixinServerPlayerInteractionManager {

    @Shadow public World world;

    @Shadow public ServerPlayerEntity player;

    @Inject(method = "destroyBlock", at = @At("HEAD"), cancellable = true)
    private void destroyBlock(BlockPos blockPos_1, CallbackInfoReturnable<Boolean> cir) {
        BlockBreakEvent blockBreakEvent = new BlockBreakEvent(world, blockPos_1, world.getBlockState(blockPos_1), player);
        EventRegistry.INSTANCE.fireEvent(blockBreakEvent);
        if(blockBreakEvent.isCanceled()) {
            cir.setReturnValue(false);
            cir.cancel();
            // Update client as they will believe they have broken the block.
            if(player != null) {
                this.player.networkHandler.sendPacket(new BlockUpdateClientPacket(world, blockPos_1));
            }
        }
    }

    @Inject(method = "interactBlock", at = @At("HEAD"), cancellable = true)
    private void interactBlock(PlayerEntity playerEntity_1, World world_1, ItemStack itemStack_1, Hand hand_1, BlockHitResult blockHitResult_1, CallbackInfoReturnable<ActionResult> cir) {
        PlayerInteractBlockEvent interactBlockEvent = new PlayerInteractBlockEvent(playerEntity_1, hand_1, world_1.getBlockState(blockHitResult_1.getBlockPos()), blockHitResult_1.getBlockPos());
        EventRegistry.INSTANCE.fireEvent(interactBlockEvent);
        if(interactBlockEvent.isCanceled()) {
            cir.setReturnValue(ActionResult.FAILURE);
            cir.cancel();
        }
    }
}
