package nerdhub.textilelib.mixins;

import nerdhub.textilelib.eventhandlers.EventRegistry;
import nerdhub.textilelib.events.tick.ServerTickEvent;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {

    //TODO map to ??? figure out name for it as update doesnt fit and tick() is already mapped to another method
    @Inject(method = "method_3748", at = @At("HEAD"))
    protected void method_3748(BooleanSupplier booleanSupplier_1, CallbackInfo ci) {
        ServerTickEvent serverTickEvent = new ServerTickEvent((MinecraftServer) (Object) this);
        EventRegistry.INSTANCE.fireEvent(serverTickEvent);
    }
}
