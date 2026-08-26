package frn.boathud_extended.mixin;

//? if >=1.21.8 && <26.1 {
/*import frn.boathud_extended.Common;
import frn.boathud_extended.Config;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.contextualbar.ExperienceBarRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperienceBarRenderer.class)
public class ExperienceBarMixin {

    @Inject(method = {"render"}, at = {@At("HEAD")}, cancellable = true)
    private void hideExperienceBar(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (Config.enabled && Common.ridingBoat) {
            ci.cancel();
        }
    }

    @Inject(method = {"renderBackground"}, at = {@At("HEAD")}, cancellable = true)
    private void hideExperienceBarBackground(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (Config.enabled && Common.ridingBoat) {
            ci.cancel();
        }
    }
}
*///? } else {
// 1.21.1~1.21.7 的经验条由 InGameHudMixin 处理；26.x 的经验条为 Hud 内联 contextual bar，暂不隐藏
public class ExperienceBarMixin {
}
//? }
