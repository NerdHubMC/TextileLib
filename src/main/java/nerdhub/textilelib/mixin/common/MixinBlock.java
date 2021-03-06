package nerdhub.textilelib.mixin.common;

import nerdhub.textilelib.event.block.BlockDropsCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

//FIXME this needs a better implementation to catch all of a block's drops, not just those from getDroppedStacks
@Mixin(Block.class)
public abstract class MixinBlock {

    @Inject(method = "afterBreak", at = @At("HEAD"), cancellable = true)
    private void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack, CallbackInfo ci) {
        if(!world.isClient && BlockDropsCallback.EVENT.invoker().onDrop(world, pos, state, Block.getDroppedStacks(state, (ServerWorld) world, pos, blockEntity), player, stack)) {
            ci.cancel();
        }
    }
}
