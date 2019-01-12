package nerdhub.textilelib.mixins;

import nerdhub.textilelib.eventhandlers.EventRegistry;
import nerdhub.textilelib.events.BlockEvents;
import nerdhub.textilelib.events.PlayerEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerInteractionManager.class)
public class MixinServerPlayerInteractionManager {

    @Shadow
    public World world;

    @Shadow
    public ServerPlayerEntity player;

    @Inject(method = "destroyBlock", at = @At("HEAD"), cancellable = true)
    private void destroyBlock(BlockPos blockPos_1, CallbackInfoReturnable cir) {
        BlockEvents.BlockBreakEvent blockBreakEvent = new BlockEvents.BlockBreakEvent(world, blockPos_1, world.getBlockState(blockPos_1), player);
        EventRegistry.INSTANCE.fireEvent(blockBreakEvent);

        if (blockBreakEvent.isCanceled()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(method = "interactBlock", at = @At("HEAD"), cancellable = true)
    public void interactBlock(PlayerEntity playerEntity_1, World world_1, ItemStack itemStack_1, Hand hand_1, BlockPos blockPos_1, Direction direction_1, float float_1, float float_2, float float_3, CallbackInfoReturnable cir) {
        PlayerEvents.InteractBlockEvent interactBlockEvent = new PlayerEvents.InteractBlockEvent(playerEntity_1, playerEntity_1.getActiveHand(), world_1.getBlockState(blockPos_1), blockPos_1);
        EventRegistry.fireEvent(interactBlockEvent);

        if(interactBlockEvent.isCanceled()) {
            cir.setReturnValue(ActionResult.FAILURE);
            cir.cancel();
        }
    }
}
