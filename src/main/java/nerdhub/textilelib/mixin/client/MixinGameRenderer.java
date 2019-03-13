package nerdhub.textilelib.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;

import nerdhub.textilelib.event.client.render.DrawScreenCallback;
import nerdhub.textilelib.event.client.render.RenderWorldCallback;
import nerdhub.textilelib.event.client.render.hud.DrawHudCallback;
import nerdhub.textilelib.event.client.render.hud.HudTypes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer {

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(at = @At(value = "INVOKE_STRING", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", args = "ldc=entities", shift = At.Shift.BEFORE), method = "renderCenter")
    private void renderCenter(float deltaTime, long long_1, CallbackInfo ci) {
        this.client.getProfiler().push("textilelib:renderWorld");
        RenderWorldCallback.EVENT.invoker().render(this.client.worldRenderer, deltaTime);
        this.client.getProfiler().pop();
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Screen;draw(IIF)V", shift = At.Shift.AFTER), method = "render")
    private void renderScreen(float deltaTime, long long_1, boolean boolean_1, CallbackInfo ci) {
        this.client.getProfiler().push("textilelib:renderScreen");
        DrawScreenCallback.EVENT.invoker().drawScreen(this.client.currentScreen, this.getMouseX(), this.getMouseY(), deltaTime);
        this.client.getProfiler().pop();
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;draw(F)V", shift = At.Shift.AFTER), method = "render")
    private void renderInGameHud(float deltaTime, long long_1, boolean boolean_1, CallbackInfo ci) {
        this.client.getProfiler().push("textilelib:renderHudIngame");
        DrawHudCallback.EVENT.invoker().drawHud(HudTypes.INGAME, this.client.inGameHud, this.getMouseX(), this.getMouseY(), deltaTime);
        this.client.getProfiler().pop();
    }

    private int getMouseX() {
        return (int) (this.client.mouse.getX() * (double) this.client.window.getScaledWidth() / (double) this.client.window.getWidth());
    }

    private int getMouseY() {
        return (int) (this.client.mouse.getY() * (double) this.client.window.getScaledHeight() / (double) this.client.window.getHeight());
    }
}
