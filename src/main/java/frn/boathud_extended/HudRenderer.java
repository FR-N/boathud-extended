package frn.boathud_extended;

//? if <1.21.5 {
import com.mojang.blaze3d.systems.RenderSystem;
//? }
//? if >=1.21.8 {
/*import net.minecraft.client.renderer.RenderPipelines;
*///? }
//? if >=1.21.2 && <1.21.8 {
/*import net.minecraft.client.renderer.RenderType;
*///? }
//? if >=26.1 {
/*import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
*///? } else if >=1.21.11 {
/*import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.Identifier;
*///? } else {
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
//? }
import java.util.Iterator;

public class HudRenderer {
    //? if >=1.21.11 {
    /*private static final Identifier WIDGETS_TEXTURE = Identifier.fromNamespaceAndPath("boathud_extended", "textures/widgets.png");
    *///? } else {
    private static final ResourceLocation WIDGETS_TEXTURE = ResourceLocation.fromNamespaceAndPath("boathud_extended", "textures/widgets.png");
    //? }
    private static final int TEXTURE_CANVAS = 256;
    private static final double[] MIN_V = new double[]{0.0D, 0.0D, 40.0D};
    private static final double[] MAX_V = new double[]{40.0D, 72.0D, 72.7D};
    private static final double[] SCALE_V = new double[]{5.4D, 3.0D, 6.6D};
    private static final double[] SCALE_V_COMPACT = new double[]{3.6D, 2.0D, 4.4D};
    private static final int[] BAR_OFF = new int[]{0, 10, 20};
    private static final int[] BAR_ON = new int[]{5, 15, 25};


    public enum DisplayType {
        SPEED,
        SLIPANGLE,
        PING,
        FPS,
        ACCELERATION,
        DELTA,
        SPEED_DIFF;
    }

    public HudRenderer() {}

    /** Version-agnostic texture draw. Coordinates are widgets.png atlas coords on a 256x256 canvas. */
    private static void tex(Object context, int x, int y, int u, int v, int w, int h) {
        //? if >=26.1 {
        /*((GuiGraphicsExtractor) context).blit(RenderPipelines.GUI_TEXTURED, WIDGETS_TEXTURE, x, y, (float) u, (float) v, w, h, TEXTURE_CANVAS, TEXTURE_CANVAS);
        *///? } else if >=1.21.11 {
        /*((GuiGraphics) context).blit(RenderPipelines.GUI_TEXTURED, WIDGETS_TEXTURE, x, y, (float) u, (float) v, w, h, TEXTURE_CANVAS, TEXTURE_CANVAS);
        *///? } else if >=1.21.8 {
        /*((GuiGraphics) context).blit(RenderPipelines.GUI_TEXTURED, WIDGETS_TEXTURE, x, y, (float) u, (float) v, w, h, TEXTURE_CANVAS, TEXTURE_CANVAS);
        *///? } else if >=1.21.2 {
        /*((GuiGraphics) context).blit(RenderType::guiTextured, WIDGETS_TEXTURE, x, y, (float) u, (float) v, w, h, TEXTURE_CANVAS, TEXTURE_CANVAS);
        *///? } else {
        ((GuiGraphics) context).blit(WIDGETS_TEXTURE, x, y, u, v, w, h);
        //? }
    }

    /** Version-agnostic shadowed text draw. */
    private static void txt(Object context, String text, int x, int y, int align) {
        int width = Common.client.font.width(text);
        int drawX = x - width * align / 2;
        //? if >=26.1 {
        /*((GuiGraphicsExtractor) context).text(Common.client.font, text, drawX, y, 0xFFFFFFFF);
        *///? } else {
        ((GuiGraphics) context).drawString(Common.client.font, text, drawX, y, 16777215);
        //? }
    }

    public void render(Object context) {
        int horizontalCentre;
        int heightWithOffset;
        //? if >=26.1 {
        /*horizontalCentre = ((GuiGraphicsExtractor) context).guiWidth() / 2;
        heightWithOffset = ((GuiGraphicsExtractor) context).guiHeight() - Config.yOffset + 6;
        *///? } else {
        horizontalCentre = Common.client.getWindow().getGuiScaledWidth() / 2;
        heightWithOffset = Common.client.getWindow().getGuiScaledHeight() - Config.yOffset + 6;
        //? }
        int[] horizontal = new int[]{horizontalCentre - 104, horizontalCentre - 14, horizontalCentre + 14, horizontalCentre + 104};
        int[] horizontal_compact = new int[]{horizontalCentre - 68, horizontalCentre, horizontalCentre + 68};
        int[] vertical = new int[]{heightWithOffset - 14, heightWithOffset - 4};
        //? if <1.21.5 {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        //? }
        drawBackground(context, horizontalCentre, heightWithOffset);
        drawSpeedBar(context, horizontalCentre, heightWithOffset);
        if (Config.extended) {
            drawGMeter(context, horizontalCentre, heightWithOffset);
            drawThrottleTrace(context, horizontal[1], vertical[0]);
            drawSteeringTrace(context, horizontal[2], vertical[0]);
        } else {
            drawKeyInputs(context, horizontal_compact[1] - 6, vertical[1]);
        }
        if (Config.extended) {
            drawTextAlign(context, getString(HudRenderer.DisplayType.SPEED), horizontal[0], vertical[0], 0);
            drawTextAlign(context, getString(HudRenderer.DisplayType.SLIPANGLE), horizontal[3], vertical[0], 2);
            drawTextAlign(context, getString(HudRenderer.DisplayType.PING), horizontal[2], vertical[1], 0);
            drawTextAlign(context, getString(HudRenderer.DisplayType.FPS), horizontal[3], vertical[1], 2);
            if (Config.checkpointEnabled) {
                drawTextAlign(context, getString(HudRenderer.DisplayType.DELTA), horizontal[0], vertical[1], 0);
                drawTextAlign(context, getString(HudRenderer.DisplayType.SPEED_DIFF), horizontal[1], vertical[1], 2);
            } else {
                drawTextAlign(context, getString(HudRenderer.DisplayType.ACCELERATION), horizontal[1] - 1, vertical[1], 2);
            }
        } else {
            drawTextAlign(context, getString(HudRenderer.DisplayType.SPEED), horizontal_compact[0], vertical[0], 0);
            drawTextAlign(context, getString(HudRenderer.DisplayType.SLIPANGLE), horizontal_compact[2], vertical[0], 2);
            drawTextAlign(context, getString(HudRenderer.DisplayType.PING), horizontal_compact[0], vertical[1], 0);
            drawTextAlign(context, getString(HudRenderer.DisplayType.FPS), horizontal_compact[2], vertical[1], 2);
            if (Config.checkpointEnabled) {
                drawTextAlign(context, getString(HudRenderer.DisplayType.DELTA), horizontal_compact[1] + 6, vertical[0], 1);
            } else {
                drawTextAlign(context, getString(HudRenderer.DisplayType.ACCELERATION), horizontal_compact[1] + 6, vertical[1], 1);
            }
        }
        //? if <1.21.5 {
        RenderSystem.disableBlend();
        //? }
    }

    private static void drawBackground(Object context, int x, int y) {
        tex(context, x - getTextureWidth() / 2, y - 20, 0, getTextureOffset(), getTextureWidth(), 26);
    }

    private static void drawSpeedBar(Object context, int x, int y) {
        tex(context, x - getTextureWidth() / 2, y - 20, 0, 26 + getTextureOffset() + BAR_OFF[Config.barType], getTextureWidth(), 5);
        if (!(Common.hudData.speed < MIN_V[Config.barType])) {
            if (Common.hudData.speed > MAX_V[Config.barType]) {
                 assert Common.client.level != null;
                 if (Common.client.level.getGameTime() % 2 == 0) {
                    tex(context, x - getTextureWidth() / 2, y - 20, 0, 26 + getTextureOffset() + BAR_ON[Config.barType], getTextureWidth(), 5);
                }

            } else {
                tex(context, x - getTextureWidth() / 2, y - 20, 0, 26 + getTextureOffset() + BAR_ON[Config.barType], getBarLength(), 5);
            }
        }
    }

    private static int getTextureOffset() {
        return Config.extended ? 0 : 56;
    }

    private static int getTextureWidth() {
        return Config.extended ? 218 : 146;
    }

    private static int getBarLength() {
        return (int)((Common.hudData.speed - MIN_V[Config.barType]) * (Config.extended ? SCALE_V[Config.barType] : SCALE_V_COMPACT[Config.barType])) + 1;
    }

    private static void drawGMeter(Object context, int x, int y) {
        tex(context, x - 9, y - 14, 218, 0, 18, 18);
        tex(context, x - 1 + getGPosition(Common.hudData.gLat), y - 6 - getGPosition(Common.hudData.gLon), getGColour(), 0, 2, 2);
    }

    private static int getGPosition(double g) {
        return Math.min(Math.max((int)(g / 2.5D), -8), 8);
    }

    private static int getGColour() {
        return !(Math.abs(Common.hudData.gLon) > 22.5D) && !(Math.abs(Common.hudData.gLat) > 22.5D) ? 236 : 238;
    }

    public static void drawTextAlign(Object context, String text, int x, int y, int align) {
        txt(context, text, x, y, align);
    }

    private static String getString(HudRenderer.DisplayType type) {
        String var10000;
        return switch (type) {
            case SPEED -> String.format("%" + threeSigFig(Common.hudData.speed * Config.speedRate) + Config.speedUnit, Common.hudData.speed * Config.speedRate);
            case SLIPANGLE -> String.format("%" + threeSigFig(Common.hudData.slipAngle) + Config.angleUnit, Math.abs(Common.hudData.slipAngle));
            case PING -> {
                var10000 = getStringColour(type);
                yield var10000 + String.format("%03d §fms", Common.hudData.ping);
            }
            case FPS -> {
                var10000 = getStringColour(type);
                yield var10000 + String.format("%03d §ffps", Common.hudData.fps);
            }
            case ACCELERATION -> {
                if (!Config.extended) {
                    yield String.format(getAccelerationFormat(), Common.hudData.gLon * Config.accelerationRate);
                }
                yield String.format(getAccelerationFormat(), Common.hudData.gLon * Config.accelerationRate, Math.abs(Common.hudData.gLat * Config.accelerationRate));
            }
            case DELTA -> {
                var10000 = getStringColour(type);
                yield var10000 + String.format("%+" + threeSigFig(Common.hudData.delta) + Config.timeUnit, Common.hudData.delta);
            }
            case SPEED_DIFF -> {
                var10000 = getStringColour(type);
                yield var10000 + String.format("%+" + threeSigFig(Common.hudData.speedDiff * Config.speedRate) + Config.speedUnit, Common.hudData.speedDiff * Config.speedRate);
            }
            default -> "";
        };
    }

    private static String threeSigFig(double value) {
        return Math.abs(value) >= 99.95D ? ".0f" : (Math.abs(value) >= 9.95D ? ".1f" : ".2f");
    }

    private static String getAccelerationFormat() {
        String s = "%+" + threeSigFig(Common.hudData.gLon * Config.accelerationRate);
        if (Config.extended) {
            s = s + " / %" + threeSigFig(Common.hudData.gLat * Config.accelerationRate);
        }

        return s + Config.accelerationUnit;
    }

    private static String getStringColour(HudRenderer.DisplayType type) {
        return switch (type) {
            case PING -> Common.hudData.ping > 500 ? "§c" : (Common.hudData.ping < 50 ? "§a" : "§f");
            case FPS -> Common.hudData.fps < 120 ? "§c" : (Common.hudData.fps > 240 ? "§a" : "§f");
            case DELTA -> Common.hudData.delta > 0.025D ? "§c" : (Common.hudData.delta < -0.025D ? "§a" : "§f");
            case SPEED_DIFF -> Common.hudData.speedDiff < -0.4D ? "§c" : (Common.hudData.speedDiff > 0.4D ? "§a" : "§f");
            default -> "";
        };
    }

    private static void drawThrottleTrace(Object context, int x, int y) {
        int i = 0;

        for(Iterator<Double> var4 = Common.hudData.throttleTrace.iterator(); var4.hasNext(); ++i) {
            double throttle = var4.next();
            tex(context, x - 40 + i, y + 4 + getTracePosition(throttle), getThrottleColour(throttle), 0, 1, 1);
        }

    }

    private static void drawSteeringTrace(Object context, int x, int y) {
        int i = 0;

        for(Iterator<Double> var4 = Common.hudData.steeringTrace.iterator(); var4.hasNext(); ++i) {
            double steering = var4.next();
            tex(context, x + i, y + 4 + getTracePosition(steering), 242, 0, 1, 1);
        }

    }

    private static int getThrottleColour(double throttle) {
        return throttle > 0.0D ? 240 : (throttle < 0.0D ? 241 : 242);
    }

    private static int getTracePosition(double value) {
        return (int)Math.signum(value) * -4;
    }

    private static void drawKeyInputs(Object context, int x, int y) {
        tex(context, x - 21, y, 146, 56, 42, 9);
        Boolean[] inputs = new Boolean[] {Common.client.options.keyLeft.isDown(), Common.client.options.keyDown.isDown(), Common.client.options.keyUp.isDown(), Common.client.options.keyRight.isDown()};

        for(int i = 0; i < 4; ++i) {
            if (inputs[i]) {
                tex(context, x - 21 + 11 * i, y, 146 + 11 * i, 65, 9, 9);
            }
        }

    }
}
