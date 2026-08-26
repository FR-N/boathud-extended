package frn.boathud_extended.mixin;

//? if >=26.2 {
/*import frn.boathud_extended.Common;
import frn.boathud_extended.Config;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.gui.screens.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Hud.class)
public class InGameHudMixin {

    @Inject(method = {"extractHotbarAndDecorations"}, at = {@At("TAIL")})
    private void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (Config.enabled && Common.ridingBoat && !(Common.client.gui.screen() instanceof ChatScreen) && !(Common.client.options.keyPlayerList.isDown())) {
            Common.hudRenderer.render(graphics);
        }
    }

    @Inject(method = {"extractPlayerHealth"}, at = {@At("HEAD")}, cancellable = true)
    private void hideStatusBars(GuiGraphicsExtractor guiGraphics, CallbackInfo ci) {
        if (Config.enabled && Common.ridingBoat) {
            ci.cancel();
        }
    }

    @Inject(method = {"extractVehicleHealth"}, at = {@At("HEAD")}, cancellable = true)
    private void hideVehicleHealth(GuiGraphicsExtractor guiGraphics, CallbackInfo ci) {
        if (Config.enabled && Common.ridingBoat) {
            ci.cancel();
        }
    }

    @Inject(method = {"extractItemHotbar"}, at = {@At("HEAD")}, cancellable = true)
    private void hideHotbar(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (Config.enabled && Common.ridingBoat) {
            ci.cancel();
        }
    }

    @Inject(method = {"extractSelectedItemName"}, at = {@At("HEAD")}, cancellable = true)
    private void hideSelectedItemName(GuiGraphicsExtractor guiGraphics, CallbackInfo ci) {
        if (Config.enabled && Common.ridingBoat) {
            ci.cancel();
        }
    }
}
*///? } else if >=26.1 {
/*import frn.boathud_extended.Common;
import frn.boathud_extended.Config;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class InGameHudMixin {

    @Inject(method = {"extractHotbarAndDecorations"}, at = {@At("TAIL")})
    private void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (Config.enabled && Common.ridingBoat && !(Common.client.screen instanceof ChatScreen) && !(Common.client.options.keyPlayerList.isDown())) {
            Common.hudRenderer.render(graphics);
        }
    }

    @Inject(method = {"extractPlayerHealth"}, at = {@At("HEAD")}, cancellable = true)
    private void hideStatusBars(GuiGraphicsExtractor guiGraphics, CallbackInfo ci) {
        if (Config.enabled && Common.ridingBoat) {
            ci.cancel();
        }
    }

    @Inject(method = {"extractVehicleHealth"}, at = {@At("HEAD")}, cancellable = true)
    private void hideVehicleHealth(GuiGraphicsExtractor guiGraphics, CallbackInfo ci) {
        if (Config.enabled && Common.ridingBoat) {
            ci.cancel();
        }
    }

    @Inject(method = {"extractItemHotbar"}, at = {@At("HEAD")}, cancellable = true)
    private void hideHotbar(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (Config.enabled && Common.ridingBoat) {
            ci.cancel();
        }
    }

    @Inject(method = {"extractSelectedItemName"}, at = {@At("HEAD")}, cancellable = true)
    private void hideSelectedItemName(GuiGraphicsExtractor guiGraphics, CallbackInfo ci) {
        if (Config.enabled && Common.ridingBoat) {
            ci.cancel();
        }
    }
}
*///? } else {
import frn.boathud_extended.Common;
import frn.boathud_extended.Config;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class InGameHudMixin {
    @Inject(method = {"renderPlayerHealth"}, at = {@At("HEAD")}, cancellable = true)
    private void renderStatusBars(GuiGraphics guiGraphics, CallbackInfo ci) {
        if (Config.enabled && Common.ridingBoat && !(Common.client.screen instanceof ChatScreen) && !(Common.client.options.keyPlayerList.isDown())) {
            ci.cancel();
        }
    }

    //? if <1.21.8 {
    @Inject(method = {"renderExperienceBar"}, at = {@At("HEAD")}, cancellable = true)
    private void renderExperienceBar(GuiGraphics guiGraphics, int x, CallbackInfo ci) {
        if (Config.enabled && Common.ridingBoat && !(Common.client.screen instanceof ChatScreen) && !(Common.client.options.keyPlayerList.isDown())) {
            ci.cancel();
        }
    }

    @Inject(method = {"renderExperienceLevel"}, at = {@At("HEAD")}, cancellable = true)
    private void onRenderExperienceLevel(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (Config.enabled && Common.ridingBoat && !(Common.client.screen instanceof ChatScreen) && !(Common.client.options.keyPlayerList.isDown())) {
            ci.cancel();
        }
    }
    //? }
    // 1.21.8 起经验条迁出 Gui（contextualbar.ExperienceBarRenderer），由 ExperienceBarMixin 处理

    @Inject(method = {"renderHotbarAndDecorations"}, at = {@At("HEAD")}, cancellable = true)
    private void onRenderHotbar(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (Config.enabled && Common.ridingBoat && !(Common.client.screen instanceof ChatScreen) && !(Common.client.options.keyPlayerList.isDown())) {
            ci.cancel();
        }
    }
}
//? }
