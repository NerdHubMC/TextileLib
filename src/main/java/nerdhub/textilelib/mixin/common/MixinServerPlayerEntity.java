package nerdhub.textilelib.mixin.common;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import nerdhub.textilelib.event.entity.EntityDeathCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class MixinServerPlayerEntity {

    @Shadow
    public abstract ServerWorld getServerWorld();

    @Inject(method = "onDeath", at = @At("HEAD"), cancellable = true)
    private void onDeath(DamageSource damageSource, CallbackInfo ci) {
        if (!EntityDeathCallback.EVENT.invoker().onEntityDeath(this.getServerWorld(), (ServerPlayerEntity) (Object) this, damageSource)) {
            ci.cancel();
        }
    }
}
