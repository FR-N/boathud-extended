package frn.boathud_extended;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.Objects;

import net.minecraft.client.multiplayer.PlayerInfo;
//? if >=1.21.11 {
/*import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
*///? } else if >=1.21.4 {
/*import net.minecraft.world.entity.vehicle.AbstractBoat;
*///? } else {
import net.minecraft.world.entity.vehicle.Boat;
//? }
import net.minecraft.world.phys.Vec3;

public class HudData {
    public int ping;
    public int fps;
    public final String name;
    private PlayerInfo listEntry;
    public double xPos;
    public double zPos;
    public double yPos;
    private double xPosLast;
    private double zPosLast;
    public double speed;
    private double speedLast;
    public double gLon;
    public double gLat;
    public double slipAngle;
    public double angularVelocity;
    private double angleFacing;
    private double angleTravelling;
    public double steering;
    public double throttle;
    public Deque<Double> steeringTrace = new ArrayDeque<>(Collections.nCopies(40, 0.0D));
    public Deque<Double> throttleTrace = new ArrayDeque<>(Collections.nCopies(40, 0.0D));
    public double time = 0.0D;
    private String fileName;
    private Boolean telemetryStart = false;
    public int cp = 0;
    public double delta = 0.0D;
    public double speedDiff = 0.0D;
    private double startTime = 0.0D;

    public HudData() {
        assert Common.client.player != null;
        this.name = Objects.requireNonNull(Common.client.player.getDisplayName()).getString();
        this.listEntry = Objects.requireNonNull(Common.client.getConnection()).getPlayerInfo(Common.client.player.getUUID());
        if (Config.telemetryEnabled) {
            this.telemetryFileInit();
        }

        if (Config.checkpointEnabled) {
            this.checkpointInit();
        }

    }

    public void update() {
        assert Common.client.player != null;
        //? if >=1.21.4 {
        /*AbstractBoat boat = (AbstractBoat) Common.client.player.getVehicle();
        *///? } else {
        Boat boat = (Boat) Common.client.player.getVehicle();
        //? }
        assert boat != null;
        Vec3 velocity = boat.getDeltaMovement().multiply(1, 0, 1);
        this.xPosLast = this.xPos;
        this.zPosLast = this.zPos;
        this.xPos = boat.getX();
        this.zPos = boat.getZ();
        this.yPos = boat.getY();
        this.speedLast = this.speed;
        this.speed = velocity.length() * 20.0D;
        this.gLon = (this.speed - this.speedLast) * 20.0D;
        double angleFacingLast = this.angleFacing;
        this.angleFacing = (double) boat.getYRot();
        this.angularVelocity = (this.angleFacing - angleFacingLast) * 20.0D;
        double angleTravellingLast = this.angleTravelling;
        this.angleTravelling = Math.toDegrees(Math.atan2(-velocity.x, velocity.z));
        this.gLat = Math.sin(Math.toRadians(this.angleTravelling - angleTravellingLast) / 2.0D) * this.speedLast * 2.0D * 20.0D;
        this.slipAngle = this.speed == 0.0D ? 0.0D : normaliseAngle(this.angleFacing - this.angleTravelling);
        this.steering = (Common.client.options.keyRight.isDown() ? -1.0D : 0.0D) + (Common.client.options.keyLeft.isDown() ? 1.0D : 0.0D);
        this.throttle = (Common.client.options.keyUp.isDown() ? 1.0D : 0.0D) + (Common.client.options.keyDown.isDown() ? -0.125D : 0.0D);
        this.updateTrace();
        this.ping = this.getLatencySafe();
        if (Config.telemetryEnabled && this.throttle > 0.01D) {
            this.telemetryStart = true;
        }

        if (this.telemetryStart) {
            this.telemetryWrite();
        }

        if (Config.checkpointEnabled) {
            this.checkpoint();
        }

        this.time += 0.05D;
    }

    private static double normaliseAngle(double angle) {
        angle = (angle % 360.0D + 360.0D) % 360.0D;
        return angle > 180.0D ? angle - 360.0D : angle;
    }

    /**
     * Resolves the local player's list entry lazily: right after joining a server the
     * entry may not have arrived yet (or may be missing entirely), so the cached value
     * can be null. Re-query it each tick until it becomes available and keep the last
     * known ping instead of crashing.
     */
    private int getLatencySafe() {
        if (this.listEntry == null && Common.client != null && Common.client.getConnection() != null && Common.client.player != null) {
            this.listEntry = Common.client.getConnection().getPlayerInfo(Common.client.player.getUUID());
        }

        return this.listEntry != null ? this.listEntry.getLatency() : this.ping;
    }

    private void telemetryFileInit() {
        String var10001 = Config.telemetryDirectory;
        this.fileName = var10001 + DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss").format(LocalDateTime.now()) + ".csv";
        try {
            FileWriter fw = new FileWriter(this.fileName);
            fw.write("time,speed,gLon,gLat,slipAngle,angularVelocity,steering,throttle,xPos,zPos,yPos\n");
            fw.close();
        } catch (IOException ignored) {}
    }

    private void telemetryWrite() {
        try {
            FileWriter fw = new FileWriter(this.fileName, true);
            fw.write(String.format("%.2f" + ",%.4f".repeat(10) + "\n", this.time, this.speed, this.gLon, this.gLat, this.slipAngle, this.angularVelocity, this.steering, this.throttle, this.xPos, this.zPos, this.yPos));
            fw.close();
        } catch (IOException ignored) {}
    }

    private void updateTrace() {
        this.steeringTrace.removeFirst();
        this.steeringTrace.addLast(this.steering);
        this.throttleTrace.removeFirst();
        this.throttleTrace.addLast(this.throttle);
    }

    private void checkpointInit() {
        if (!Objects.equals(Config.checkpointFileLoaded, Config.checkpointFile)) {
            Config.loadCheckpoints();
        }

    }

    private void checkpoint() {
        if (this.cp < Config.checkpoints) {
            for(double dot = this.dotProduct(this.xPos, this.zPos); dot > ((Double[])Config.checkpointdata.get(this.cp))[4]; dot = this.dotProduct(this.xPos, this.zPos)) {
                double dotLast = this.dotProduct(this.xPosLast, this.zPosLast);
                double subtick = Math.min(Math.max((((Double[])Config.checkpointdata.get(this.cp))[4] - dotLast) / (dot - dotLast), 0.0D), 1.0D);
                double subtickTime = this.time - 0.05D + subtick * 0.05D;
                if (this.cp == 0) {
                    this.startTime = subtickTime;
                }

                this.delta = subtickTime - this.startTime - ((Double[])Config.checkpointdata.get(this.cp))[0];
                this.speedDiff = this.speedLast * subtick + this.speed * (1.0D - subtick) - ((Double[])Config.checkpointdata.get(this.cp))[1];
                ++this.cp;
                if (this.cp >= Config.checkpoints) {
                    if (!Config.circularTrack) {
                        break;
                    }

                    this.cp -= Config.checkpoints;
                }
            }

        }
    }

    private double dotProduct(double x, double z) {
        return x * ((Double[])Config.checkpointdata.get(this.cp))[2] + z * ((Double[])Config.checkpointdata.get(this.cp))[3];
    }
}
