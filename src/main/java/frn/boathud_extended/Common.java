package frn.boathud_extended;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
//? if <26.1 {
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
//? }
//? if >=1.21.11 {
/*import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
*///? } else if >=1.21.4 {
/*import net.minecraft.world.entity.vehicle.AbstractBoat;
*///? } else {
import net.minecraft.world.entity.vehicle.Boat;
//? }
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;

public class Common implements ClientModInitializer {
    public static HudData hudData;
    public static Minecraft client = null;
    public static boolean ridingBoat = false;
    public static HudRenderer hudRenderer;

    public void onInitializeClient() {
        client = Minecraft.getInstance();
        hudRenderer = new HudRenderer();
        Config.load();
        //? if >=26.1 {
        /*ClientTickEvents.END_LEVEL_TICK.register((clientLevel) -> {
        *///? } else {
        ClientTickEvents.END_WORLD_TICK.register((clientLevel) -> {
        //? }
            if (client.player != null) {
                //? if >=1.21.4 {
                /*if (client.player.getVehicle() instanceof AbstractBoat boat && boat.getFirstPassenger() == client.player && hudData != null) {
                *///? } else {
                if (client.player.getVehicle() instanceof Boat boat && boat.getFirstPassenger() == client.player && hudData != null) {
                //? }
                    hudData.update();
                } else if (ridingBoat) {
                    ridingBoat = false;
                }
            }
        });
        //? if <26.1 {
        HudRenderCallback.EVENT.register((graphics, tickDelta) -> {
            if (client.player != null) {
                if (Config.enabled && Common.ridingBoat && !(client.screen instanceof ChatScreen) && !(client.options.keyPlayerList.isDown())) {
                    Common.hudRenderer.render(graphics);
                }
            }
        });
        //? }
        ClientPlayConnectionEvents.DISCONNECT.register((handler, mc) -> {
            Common.ridingBoat = false;
            Common.hudData = null;
        });
    }
}
