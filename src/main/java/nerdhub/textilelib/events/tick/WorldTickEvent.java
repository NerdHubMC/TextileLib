package nerdhub.textilelib.events.tick;

import nerdhub.textilelib.events.tick.TickEvent;
import net.minecraft.world.World;

public class WorldTickEvent extends TickEvent {

    private World world;

    public WorldTickEvent(World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }
}
