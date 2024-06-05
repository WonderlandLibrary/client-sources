/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.math.NumberUtils
 */
package digital.rbq.command.impl;

import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockSign;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import digital.rbq.command.AbstractCommand;
import digital.rbq.core.Autumn;
import digital.rbq.events.game.TickEvent;
import digital.rbq.events.packet.SendPacketEvent;
import digital.rbq.events.player.MotionUpdateEvent;
import digital.rbq.utils.Logger;
import digital.rbq.utils.Stopwatch;
import digital.rbq.utils.pathfinding.CustomVec3;
import digital.rbq.utils.pathfinding.PathfindingUtils;

public final class TeleportCommand
extends AbstractCommand {
    private int x;
    private int y;
    private int z;
    private boolean gotonigga;
    private boolean niggay;
    private final Minecraft mc = Minecraft.getMinecraft();
    private boolean packet = true;
    private int moveUnder;
    private Stopwatch stopwatchUtil = new Stopwatch();

    public TeleportCommand() {
        super("Teleport", "Teleports to players and factions on Faithful.", "teleport <player/faction>", "teleport", "tp");
    }

    @Override
    public void execute(String ... arguments) {
        switch (arguments.length) {
            case 2: {
                if (arguments[1].toLowerCase().equals("stop")) {
                    if (this.gotonigga) {
                        this.stopTP();
                        Logger.log("Stopped.");
                        break;
                    }
                    Logger.log("Not running.");
                    break;
                }
                if (arguments[1].toLowerCase().equals("help")) {
                    Logger.log(".teleport stop/packet/xz/xyz/waypointname/playername/factionname");
                    break;
                }
                if (arguments[1].toLowerCase().equals("packet")) {
                    this.packet ^= true;
                    Logger.log("Packet set to " + this.packet);
                    break;
                }
                if (this.gotonigga) {
                    Logger.log("Already going.");
                    break;
                }
                Logger.log("jew");
                for (EntityPlayer e : this.mc.theWorld.playerEntities) {
                    if (!e.getName().toLowerCase().equals(arguments[1].toLowerCase())) continue;
                    this.startTP(MathHelper.floor_double(e.posX), MathHelper.floor_double(e.posY), MathHelper.floor_double(e.posZ), true);
                    return;
                }
                Autumn.EVENT_BUS_REGISTRY.eventBus.subscribe(this);
                this.mc.getNetHandler().addToSendQueueSilent(new C01PacketChatMessage("/f who " + arguments[1]));
                break;
            }
            case 3: {
                if (this.gotonigga) {
                    Logger.log("Already going.");
                    break;
                }
                if (NumberUtils.isNumber((String)arguments[1]) && NumberUtils.isNumber((String)arguments[2])) {
                    if (!this.isUnderBlock() || this.packet) {
                        this.startTP(Integer.parseInt(arguments[1]), 255, Integer.parseInt(arguments[2]), true);
                        break;
                    }
                    Logger.log("You are under a block!");
                    break;
                }
                Logger.log("Invalid arguments.");
                Logger.log("try .teleport stop/packet/xz/xyz/waypointname/playername/factionname");
                break;
            }
            case 4: {
                if (this.gotonigga) {
                    Logger.log("Already going.");
                    break;
                }
                if (NumberUtils.isNumber((String)arguments[1]) && NumberUtils.isNumber((String)arguments[2]) && NumberUtils.isNumber((String)arguments[3])) {
                    if (!this.isUnderBlock() || this.packet) {
                        this.startTP(Integer.parseInt(arguments[1]), Integer.parseInt(arguments[2]), Integer.parseInt(arguments[3]), true);
                        break;
                    }
                    Logger.log("You are under a block!");
                    break;
                }
                this.usage();
                break;
            }
            default: {
                this.usage();
            }
        }
    }

    @Listener(value=MotionUpdateEvent.class)
    public void onUpdate(MotionUpdateEvent event) {
        if (this.gotonigga && !this.packet) {
            float storedangles = this.getRotationFromPosition(this.x, this.z);
            double distancex = -3.0 * Math.sin(storedangles);
            double distancez = 3.0 * Math.cos(storedangles);
            if (this.mc.thePlayer.ticksExisted % 3 == 0) {
                if (this.mc.thePlayer.posY < 250.0) {
                    this.mc.thePlayer.motionY = 5.0;
                } else {
                    this.mc.thePlayer.motionY = 0.0;
                    this.niggay = true;
                }
                if (this.mc.thePlayer.getDistanceSq(this.x, this.mc.thePlayer.posY, this.z) >= 32.0) {
                    if (this.niggay) {
                        this.mc.thePlayer.motionX = distancex;
                        this.mc.thePlayer.motionZ = distancez;
                    }
                } else {
                    this.mc.thePlayer.motionX = 0.0;
                    this.mc.thePlayer.motionZ = 0.0;
                    Logger.log("Finished you have arrived at x:" + (int)this.mc.thePlayer.posX + " z:" + (int)this.mc.thePlayer.posZ);
                    this.gotonigga = false;
                    this.niggay = false;
                    this.mc.renderGlobal.loadRenderers();
                    Autumn.EVENT_BUS_REGISTRY.eventBus.unsubscribe(this);
                }
            }
        }
    }

    @Listener(value=SendPacketEvent.class)
    public void onPacket(SendPacketEvent event) {
        if (this.packet) {
            if (this.gotonigga) {
                if (event.getPacket() instanceof C03PacketPlayer && !this.niggay) {
                    event.setCancelled();
                    for (CustomVec3 pos : PathfindingUtils.computePath(new CustomVec3(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ), new CustomVec3(this.x, this.y, this.z))) {
                        this.mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(pos.getX(), pos.getY(), pos.getZ(), true));
                    }
                    this.mc.thePlayer.setPosition(this.x, this.y, this.z);
                    this.niggay = true;
                    this.moveUnder = 2;
                }
                if (this.stopwatchUtil.elapsed(500L)) {
                    Logger.log("Finished you have arrived at x:" + this.x + " z:" + this.z);
                    this.gotonigga = false;
                    this.niggay = false;
                    this.mc.renderGlobal.loadRenderers();
                    Autumn.EVENT_BUS_REGISTRY.eventBus.unsubscribe(this);
                    this.stopwatchUtil.reset();
                }
            }
        } else {
            if (event.getPacket() instanceof S08PacketPlayerPosLook && this.moveUnder == 2) {
                this.moveUnder = 1;
            }
            if (event.getPacket() instanceof S02PacketChat) {
                S02PacketChat packet = (S02PacketChat)event.getPacket();
                String text = packet.getChatComponent().getUnformattedText();
                if (text.contains("You cannot go past the border.")) {
                    event.setCancelled();
                }
                if (text.contains("Home: ")) {
                    if (text.contains("Not set")) {
                        this.stopTP();
                        Logger.log("Player or faction found but f home was not set.");
                        return;
                    }
                    try {
                        int x = Integer.parseInt(StringUtils.substringBetween((String)text, (String)"Home: ", (String)", "));
                        int z = Integer.parseInt(text.split(", ")[1]);
                        this.startTP(x, 255, z, false);
                    }
                    catch (Exception e) {
                        this.stopTP();
                    }
                } else if (text.contains(" not found.")) {
                    this.stopTP();
                    Logger.log("Player or faction not found.");
                }
            }
        }
    }

    @Listener(value=TickEvent.class)
    public void onTick(TickEvent event) {
        if (this.mc.thePlayer != null && this.moveUnder == 1 && this.packet && this.mc.thePlayer.getDistanceSq(this.x, this.mc.thePlayer.posY, this.z) > 1.0) {
            this.mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(this.x, this.y, this.z, false));
            this.mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(Double.NEGATIVE_INFINITY, this.y, Double.NEGATIVE_INFINITY, true));
            this.moveUnder = 0;
        }
    }

    private void startTP(int x, int y, int z, boolean register) {
        if (this.gotonigga) {
            Logger.log("Already active!");
            return;
        }
        this.x = x;
        this.y = y;
        this.z = z;
        this.gotonigga = true;
        Logger.log("Teleporting to x:" + x + " y:" + y + " z:" + z + ".");
        if (register) {
            Autumn.EVENT_BUS_REGISTRY.eventBus.subscribe(this);
        }
        this.stopwatchUtil.reset();
    }

    private void stopTP() {
        this.z = 0;
        this.y = 0;
        this.x = 0;
        this.gotonigga = false;
        this.niggay = false;
        Autumn.EVENT_BUS_REGISTRY.eventBus.unsubscribe(this);
    }

    private boolean isUnderBlock() {
        for (int i = (int)(Minecraft.getMinecraft().thePlayer.posY + 2.0); i < 255; ++i) {
            BlockPos pos = new BlockPos(Minecraft.getMinecraft().thePlayer.posX, (double)i, Minecraft.getMinecraft().thePlayer.posZ);
            if (Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock() instanceof BlockAir || Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock() instanceof BlockFenceGate || Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock() instanceof BlockSign || Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock() instanceof BlockButton) continue;
            return true;
        }
        return false;
    }

    private float getRotationFromPosition(double x, double z) {
        double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
        double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
        float yaw = (float)Math.atan2(zDiff, xDiff) - 1.5707964f;
        return yaw;
    }
}

