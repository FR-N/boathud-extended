package frn.boathud_extended.mixin;

import frn.boathud_extended.Common;
import frn.boathud_extended.HudData;
//? if >=1.21.11 {
/*import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
*///? } else if >=1.21.4 {
/*import net.minecraft.world.entity.vehicle.AbstractBoat;
*///? } else {
import net.minecraft.world.entity.vehicle.Boat;
//? }
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPlayNetworkHandlerMixin {

    @Shadow
    private ClientLevel level;

    @Inject(
        method = {"handleSetEntityPassengersPacket"},
        at = {@At("TAIL")}
    )
    private void checkBoatEntry(ClientboundSetPassengersPacket packet, CallbackInfo info) {
        //? if >=1.21.4 {
        /*if (!(this.level.getEntity(packet.getVehicle()) instanceof AbstractBoat boat)) return;
        *///? } else {
        if (!(this.level.getEntity(packet.getVehicle()) instanceof Boat boat)) return;
        //? }
        if (Minecraft.getInstance().player == null) return;
        // Vanilla applies the new passenger list before this mixin runs at TAIL,
        // so only react when the local player actually mounted the boat.
        if (boat.getFirstPassenger() == Minecraft.getInstance().player) {
            Common.ridingBoat = true;
            Common.hudData = new HudData();
        }
    }
}
