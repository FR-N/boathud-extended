package frn.boathud_extended.mixin;

import frn.boathud_extended.Common;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftClientMixin {

    @Shadow public abstract int getFps();

    //? if >=26.1 {
    /*@Inject(method = {"renderFrame"}, at = {@At("TAIL")})
    private void getCurrentFPSNew(boolean renderLevel, CallbackInfo ci) {
        if (renderLevel && Common.ridingBoat && Common.hudData != null) {
            Common.hudData.fps = getFps();
        }

    }
    *///? } else {
    @Inject(method = {"runTick"}, at = {@At("TAIL")})
    private void getCurrentFPS(boolean renderLevel, CallbackInfo ci) {
        if (renderLevel && Common.ridingBoat && Common.hudData != null) {
            Common.hudData.fps = getFps();
        }

    }
    //? }
}
