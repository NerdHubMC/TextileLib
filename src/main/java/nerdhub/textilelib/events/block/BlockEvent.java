package nerdhub.textilelib.events.block;

import nerdhub.textilelib.events.CancelableEvent;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockEvent extends CancelableEvent {

    private World world;
    private BlockPos pos;
    private BlockState state;

    public BlockEvent(World world, BlockPos pos, BlockState state) {
        this.world = world;
        this.pos = pos;
        this.state = state;
    }

    public World getWorld() {
        return world;
    }

    public BlockPos getPos() {
        return pos;
    }

    public BlockState getState() {
        return state;
    }
}
