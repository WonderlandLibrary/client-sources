// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.misc;

import net.minecraft.block.BlockAir;
import net.minecraft.util.BlockPos;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import intent.AquaDev.aqua.modules.combat.Killaura;
import net.minecraft.entity.Entity;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C18PacketSpectate;
import java.util.UUID;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import events.listeners.EventUpdate;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.util.MathHelper;
import java.util.Random;
import net.minecraft.network.login.server.S01PacketEncryptionRequest;
import java.util.function.Consumer;
import intent.AquaDev.aqua.utils.PacketUtils;
import net.minecraft.network.play.client.C03PacketPlayer;
import events.listeners.EventPacket;
import events.listeners.EventTick;
import net.minecraft.network.play.server.S07PacketRespawn;
import events.listeners.EventReceivedPacket;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import events.listeners.EventTimerDisabler;
import events.Event;
import java.util.Iterator;
import intent.AquaDev.aqua.utils.PathFinder;
import net.minecraft.util.Vec3;
import net.minecraft.network.INetHandler;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import java.util.concurrent.ConcurrentLinkedDeque;
import intent.AquaDev.aqua.modules.Category;
import java.util.List;
import java.util.ArrayList;
import intent.AquaDev.aqua.utils.TimeUtil;
import java.util.LinkedList;
import net.minecraft.network.Packet;
import java.util.Queue;
import intent.AquaDev.aqua.modules.Module;

public class Disabler extends Module
{
    public static int worldChanges;
    private final Queue<Packet<?>> packet;
    public LinkedList packetQueue;
    public int state;
    public int state2;
    public int state3;
    public int stage;
    public int stage2;
    public int stage3;
    public TimeUtil timer;
    public TimeUtil timer2;
    public ArrayList<Packet> packets;
    public TimeUtil helper3;
    public TimeUtil helper;
    public TimeUtil helper2;
    private final boolean inGround = true;
    private final boolean cancelFlag = true;
    private final boolean transaction = true;
    private final boolean transactionMultiply = true;
    private final boolean transactionSend = true;
    private boolean teleported;
    private boolean cancel;
    private TimeUtil delay;
    private TimeUtil release;
    private boolean releasePacket;
    private List<Packet> packetList;
    
    public Disabler() {
        super("Disabler", Type.Misc, "Disabler", 0, Category.Misc);
        this.packet = new ConcurrentLinkedDeque<Packet<?>>();
        this.packetQueue = new LinkedList();
        this.timer = new TimeUtil();
        this.timer2 = new TimeUtil();
        this.packets = new ArrayList<Packet>();
        this.helper3 = new TimeUtil();
        this.helper = new TimeUtil();
        this.helper2 = new TimeUtil();
        this.delay = new TimeUtil();
        this.release = new TimeUtil();
        this.releasePacket = false;
        this.packetList = new ArrayList<Packet>();
        Aqua.setmgr.register(new Setting("WatchdogRandom", this, false));
        Aqua.setmgr.register(new Setting("Modes", this, "Ghostly", new String[] { "Ghostly", "BMC", "Cubecraft", "SentinelNew", "BMC2", "Minebox", "Watchdog", "Watchdog2", "HycraftCombat", "Matrix", "HypixelDev", "Intave", "Hycraft", "Timer" }));
    }
    
    public static void sendPacketUnlogged(final Packet<? extends INetHandler> packet) {
        Disabler.mc.getNetHandler().getNetworkManager().sendPacket(packet);
    }
    
    public static List<Vec3> calculatePath(final Vec3 startPos, final Vec3 endPos) {
        System.out.println("Test-1");
        final PathFinder pathfinder = new PathFinder(startPos, endPos);
        System.out.println("Test");
        pathfinder.calculatePath(5000);
        System.out.println("Test2");
        int i = 0;
        Vec3 lastLoc = null;
        Vec3 lastDashLoc = null;
        final List<Vec3> path = new ArrayList<Vec3>();
        final List<Vec3> pathFinderPath = pathfinder.getPath();
        for (final Vec3 pathElm : pathFinderPath) {
            if (i == 0 || i == pathFinderPath.size() - 1) {
                if (lastLoc != null) {
                    path.add(lastLoc.addVector(0.5, 0.0, 0.5));
                }
                path.add(pathElm.addVector(0.5, 0.0, 0.5));
                lastDashLoc = pathElm;
            }
            else {
                boolean canContinue = true;
                Label_0356: {
                    if (pathElm.squareDistanceTo(lastDashLoc) > 30.0) {
                        canContinue = false;
                    }
                    else {
                        final double smallX = Math.min(lastDashLoc.xCoord, pathElm.xCoord);
                        final double smallY = Math.min(lastDashLoc.yCoord, pathElm.yCoord);
                        final double smallZ = Math.min(lastDashLoc.zCoord, pathElm.zCoord);
                        final double bigX = Math.max(lastDashLoc.xCoord, pathElm.xCoord);
                        final double bigY = Math.max(lastDashLoc.yCoord, pathElm.yCoord);
                        final double bigZ = Math.max(lastDashLoc.zCoord, pathElm.zCoord);
                        for (int x = (int)smallX; x <= bigX; ++x) {
                            for (int y = (int)smallY; y <= bigY; ++y) {
                                for (int z = (int)smallZ; z <= bigZ; ++z) {
                                    if (!PathFinder.checkPositionValidity(x, y, z, false)) {
                                        canContinue = false;
                                        break Label_0356;
                                    }
                                }
                            }
                        }
                    }
                }
                if (!canContinue) {
                    path.add(lastLoc.addVector(0.5, 0.0, 0.5));
                    lastDashLoc = lastLoc;
                }
            }
            lastLoc = pathElm;
            ++i;
        }
        return path;
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventTimerDisabler) {
            final Packet packet = EventTimerDisabler.getPacket();
            if (Aqua.setmgr.getSetting("DisablerModes").getCurrentMode().equalsIgnoreCase("Intave")) {
                sendPacketUnlogged(new C00PacketKeepAlive());
            }
            if (Aqua.setmgr.getSetting("DisablerModes").getCurrentMode().equalsIgnoreCase("Matrix")) {
                if (packet instanceof C00PacketKeepAlive && Disabler.mc.thePlayer.ticksExisted % 5 == 0) {
                    e.setCancelled(true);
                }
                if (packet instanceof C0FPacketConfirmTransaction && Disabler.mc.thePlayer.ticksExisted % 2 == 0) {
                    e.setCancelled(true);
                }
                if (packet instanceof C16PacketClientStatus) {
                    final C16PacketClientStatus clientStatus = (C16PacketClientStatus)packet;
                    if (clientStatus.getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT) {
                        e.setCancelled(true);
                    }
                }
            }
        }
        if (e instanceof EventReceivedPacket && Aqua.setmgr.getSetting("DisablerModes").getCurrentMode().equalsIgnoreCase("SentinelNew")) {
            if (this.packet instanceof S07PacketRespawn) {
                e.setCancelled(true);
                final boolean respawned = true;
            }
            else {
                final boolean respawned = false;
            }
            if (this.packet instanceof C0FPacketConfirmTransaction) {
                e.setCancelled(true);
            }
        }
        if (e instanceof EventTick && Aqua.setmgr.getSetting("DisablerModes").getCurrentMode().equalsIgnoreCase("Timer")) {
            final Packet packet = EventPacket.getPacket();
            if (packet instanceof C0FPacketConfirmTransaction) {
                e.setCancelled(true);
                this.cancel = false;
            }
            else {
                this.cancel = true;
            }
            if (packet instanceof C03PacketPlayer.C06PacketPlayerPosLook && !Aqua.moduleManager.getModuleByName("Scaffold").isToggled()) {
                e.setCancelled(true);
            }
            if (!this.cancel) {
                this.packets.forEach(PacketUtils::sendPacketNoEvent);
                this.packets.clear();
                this.cancel = false;
            }
        }
        if (e instanceof EventPacket) {
            if (Aqua.setmgr.getSetting("DisablerModes").getCurrentMode().equalsIgnoreCase("Timer") && this.packet instanceof C03PacketPlayer) {
                final C03PacketPlayer c03 = (C03PacketPlayer)this.packet;
                if (!c03.isMoving() && !Disabler.mc.thePlayer.isUsingItem()) {
                    e.setCancelled(true);
                }
            }
            if (Aqua.setmgr.getSetting("DisablerModes").getCurrentMode().equalsIgnoreCase("SentinelNew")) {
                final Packet packet = EventPacket.getPacket();
                if (packet instanceof S01PacketEncryptionRequest) {
                    e.setCancelled(true);
                }
                if (packet instanceof C03PacketPlayer) {
                    final C03PacketPlayer c4 = (C03PacketPlayer)packet;
                    if (c4.isMoving() || !Disabler.mc.thePlayer.isUsingItem()) {}
                }
                final float yawRandom = (float)MathHelper.getRandomDoubleInRange(new Random(), 2.0, 4.0);
                if (packet instanceof C00PacketKeepAlive) {
                    e.setCancelled(true);
                }
                if (packet instanceof C00PacketServerQuery && Disabler.mc.thePlayer.ticksExisted % 2 == 0) {
                    e.setCancelled(true);
                }
                if (packet instanceof C01PacketEncryptionResponse && Disabler.mc.thePlayer.ticksExisted % 2 == 0) {
                    e.setCancelled(true);
                }
            }
        }
        if (e instanceof EventUpdate && Disabler.mc.isSingleplayer()) {
            return;
        }
        if (!(e instanceof EventUpdate) || !Aqua.setmgr.getSetting("DisablerModes").getCurrentMode().equalsIgnoreCase("SentinelNew") || Disabler.mc.thePlayer.ticksExisted % 150 == 0) {}
        if (e instanceof EventPacket) {
            if (Aqua.setmgr.getSetting("DisablerModes").getCurrentMode().equalsIgnoreCase("Hycraft") && EventPacket.getPacket() instanceof S08PacketPlayerPosLook) {
                if (((S08PacketPlayerPosLook)EventPacket.getPacket()).getYaw() > 680.0f || ((S08PacketPlayerPosLook)EventPacket.getPacket()).getYaw() < -680.0f || ((S08PacketPlayerPosLook)EventPacket.getPacket()).getPitch() < -410.0f || ((S08PacketPlayerPosLook)EventPacket.getPacket()).getPitch() > 590.0f) {
                    e.setCancelled(true);
                }
                if (((S08PacketPlayerPosLook)EventPacket.getPacket()).getX() > 2.0E7 || ((S08PacketPlayerPosLook)EventPacket.getPacket()).getY() > 2.0E7 || ((S08PacketPlayerPosLook)EventPacket.getPacket()).getZ() > 2.0E7) {
                    e.setCancelled(true);
                }
            }
            if (Aqua.setmgr.getSetting("DisablerModes").getCurrentMode().equalsIgnoreCase("Intave")) {
                final Packet packet = EventPacket.getPacket();
                if (packet instanceof C0FPacketConfirmTransaction && Disabler.mc.thePlayer.ticksExisted % 2 == 0 && !Aqua.moduleManager.getModuleByName("Scaffold").isToggled()) {
                    e.setCancelled(true);
                }
                if (Aqua.moduleManager.getModuleByName("Scaffold").isToggled() && packet instanceof C0BPacketEntityAction) {
                    e.setCancelled(true);
                }
            }
            final Packet p = EventPacket.getPacket();
            if (Aqua.setmgr.getSetting("DisablerModes").getCurrentMode().equalsIgnoreCase("Matrix")) {
                if (p instanceof C00PacketKeepAlive && Disabler.mc.thePlayer.ticksExisted % 5 == 0) {
                    e.setCancelled(true);
                }
                if (p instanceof C0FPacketConfirmTransaction) {
                    e.setCancelled(true);
                }
                if (p instanceof C16PacketClientStatus) {
                    final C16PacketClientStatus clientStatus = (C16PacketClientStatus)p;
                    if (clientStatus.getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT) {
                        e.setCancelled(true);
                    }
                }
                if (p instanceof C0BPacketEntityAction) {}
            }
            if (Aqua.setmgr.getSetting("DisablerModes").getCurrentMode().equalsIgnoreCase("BMC2")) {
                if (p instanceof C01PacketPing) {
                    sendPacketUnlogged(new C18PacketSpectate(UUID.randomUUID()));
                    final float voidTP = (float)MathHelper.getRandomDoubleInRange(new Random(), 0.78, 0.98);
                    sendPacketUnlogged(new C18PacketSpectate(UUID.randomUUID()));
                    sendPacketUnlogged(new C0CPacketInput(voidTP, voidTP, false, false));
                    e.setCancelled(true);
                }
                if (p instanceof C03PacketPlayer.C06PacketPlayerPosLook) {
                    e.setCancelled(true);
                }
            }
            if (Aqua.setmgr.getSetting("DisablerModes").getCurrentMode().equalsIgnoreCase("Watchdog")) {
                final boolean worldChange = this.packet instanceof S07PacketRespawn;
                if (worldChange && p instanceof S08PacketPlayerPosLook) {
                    e.setCancelled(true);
                }
                if (worldChange && p instanceof C0FPacketConfirmTransaction) {
                    e.setCancelled(true);
                }
                if (worldChange && p instanceof C00PacketKeepAlive) {
                    e.setCancelled(true);
                }
                if (Disabler.mc.thePlayer.ticksExisted < 50 && p instanceof S08PacketPlayerPosLook) {
                    e.setCancelled(true);
                    final S08PacketPlayerPosLook s08 = (S08PacketPlayerPosLook)p;
                    final float watchdogRandom1 = (float)MathHelper.getRandomDoubleInRange(new Random(), 1.0, 1.1213131123);
                    final S08PacketPlayerPosLook s08PacketPlayerPosLook = s08;
                    s08PacketPlayerPosLook.y += watchdogRandom1;
                    Disabler.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(s08.getX(), s08.getY(), s08.getZ(), s08.getYaw(), s08.getPitch(), false));
                    Disabler.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(s08.getX(), s08.getY() + 1.0, s08.getZ(), s08.getYaw(), s08.getPitch(), false));
                }
            }
            if (Aqua.setmgr.getSetting("DisablerModes").getCurrentMode().equalsIgnoreCase("Minebox")) {
                if (p instanceof C0FPacketConfirmTransaction) {
                    e.setCancelled(true);
                }
                if (p instanceof C0BPacketEntityAction) {
                    final C0BPacketEntityAction c0B = (C0BPacketEntityAction)p;
                    if (c0B.getAction().equals(C0BPacketEntityAction.Action.START_SPRINTING)) {
                        if (EntityPlayerSP.serverSprintState) {
                            this.sendPacketSilent(new C0BPacketEntityAction(Disabler.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                            EntityPlayerSP.serverSprintState = false;
                        }
                        e.setCancelled(true);
                    }
                    if (c0B.getAction().equals(C0BPacketEntityAction.Action.STOP_SPRINTING)) {
                        e.setCancelled(true);
                    }
                }
            }
            if (Aqua.setmgr.getSetting("DisablerModes").getCurrentMode().equalsIgnoreCase("Cubecraft")) {
                if (p instanceof C03PacketPlayer.C06PacketPlayerPosLook && !Aqua.moduleManager.getModuleByName("Scaffold").isToggled()) {
                    e.setCancelled(true);
                }
                if (p instanceof C03PacketPlayer) {
                    final C03PacketPlayer c4 = (C03PacketPlayer)p;
                    if (!c4.isMoving() && !Disabler.mc.thePlayer.isUsingItem()) {
                        e.setCancelled(true);
                    }
                }
                if (p instanceof C00PacketKeepAlive) {
                    e.setCancelled(true);
                }
                if (p instanceof C0FPacketConfirmTransaction) {
                    e.setCancelled(true);
                }
                if (p instanceof C0CPacketInput) {
                    e.setCancelled(true);
                }
                if (p instanceof C01PacketPing) {
                    e.setCancelled(true);
                }
                if (Killaura.target == null) {
                    assert p instanceof C0BPacketEntityAction;
                    final C0BPacketEntityAction c0B = (C0BPacketEntityAction)p;
                    if (c0B.getAction().equals(C0BPacketEntityAction.Action.START_SPRINTING)) {
                        if (EntityPlayerSP.serverSprintState) {
                            this.sendPacketSilent(new C0BPacketEntityAction(Disabler.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                            EntityPlayerSP.serverSprintState = false;
                        }
                        e.setCancelled(true);
                    }
                }
            }
        }
        if (Aqua.setmgr.getSetting("DisablerModes").getCurrentMode().equalsIgnoreCase("BMC")) {
            final Packet p = EventPacket.getPacket();
            if (Disabler.mc.thePlayer.ticksExisted < 200 && p instanceof S07PacketRespawn) {
                this.teleported = false;
            }
            if (Disabler.mc.thePlayer.ticksExisted < 250 && p instanceof S07PacketRespawn) {
                this.packet.clear();
                return;
            }
            if (p instanceof C0BPacketEntityAction) {
                final C0BPacketEntityAction c0B = (C0BPacketEntityAction)p;
                if (c0B.getAction().equals(C0BPacketEntityAction.Action.START_SPRINTING)) {
                    if (EntityPlayerSP.serverSprintState) {
                        this.sendPacketSilent(new C0BPacketEntityAction(Disabler.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                        EntityPlayerSP.serverSprintState = false;
                    }
                    e.setCancelled(true);
                }
                if (c0B.getAction().equals(C0BPacketEntityAction.Action.STOP_SPRINTING)) {
                    e.setCancelled(true);
                }
            }
            if (p instanceof C00PacketKeepAlive || p instanceof C0FPacketConfirmTransaction) {
                this.packet.add(p);
                e.setCancelled(true);
                if (this.packet.size() > 500) {
                    this.sendPacketSilent(this.packet.poll());
                    e.setCancelled(true);
                }
                final C0FPacketConfirmTransaction packetConfirmTransaction = (C0FPacketConfirmTransaction)this.packet;
                final double x = ((S08PacketPlayerPosLook)this.packet).getX() - Disabler.mc.thePlayer.posX;
                final double y = ((S08PacketPlayerPosLook)this.packet).getY() - Disabler.mc.thePlayer.posY;
                final double z = ((S08PacketPlayerPosLook)this.packet).getZ() - Disabler.mc.thePlayer.posZ;
                final double diff = Math.sqrt(x * x + y * y + z * z);
                if (diff > 0.0) {
                    e.setCancelled(true);
                }
            }
            if (p instanceof C03PacketPlayer) {
                final C03PacketPlayer c4 = (C03PacketPlayer)p;
                if (Disabler.mc.thePlayer.ticksExisted % 50 == 0) {
                    sendPacketUnlogged(new C18PacketSpectate(UUID.randomUUID()));
                    final float voidTP2 = (float)MathHelper.getRandomDoubleInRange(new Random(), 0.78, 0.98);
                    sendPacketUnlogged(new C0CPacketInput(voidTP2, voidTP2, false, false));
                }
                if (Disabler.mc.thePlayer.ticksExisted % 120 == 0) {
                    final float n = (float)MathHelper.getRandomDoubleInRange(new Random(), 0.01, 20.0);
                }
            }
        }
        if (Aqua.setmgr.getSetting("DisablerModes").getCurrentMode().equalsIgnoreCase("Ghostly")) {
            if (Disabler.mc.thePlayer != null && Disabler.mc.thePlayer.ticksExisted == 1) {
                this.packetQueue.clear();
            }
            final Packet packet = EventPacket.getPacket();
            if (packet instanceof S08PacketPlayerPosLook) {
                this.getClass();
                final C0FPacketConfirmTransaction packetConfirmTransaction = (C0FPacketConfirmTransaction)packet;
                final double x = ((S08PacketPlayerPosLook)packet).getX() - Disabler.mc.thePlayer.posX;
                final double y = ((S08PacketPlayerPosLook)packet).getY() - Disabler.mc.thePlayer.posY;
                final double z = ((S08PacketPlayerPosLook)packet).getZ() - Disabler.mc.thePlayer.posZ;
                final double diff = Math.sqrt(x * x + y * y + z * z);
                if (diff > 0.0) {
                    e.setCancelled(true);
                }
            }
            else if (packet instanceof C03PacketPlayer) {
                this.getClass();
                if (packet instanceof C01PacketPing) {}
                if (packet instanceof C13PacketPlayerAbilities) {}
                if (Disabler.mc.thePlayer.ticksExisted % 75 == 0) {
                    System.out.println("Sda");
                    final float yValue = (float)MathHelper.getRandomDoubleInRange(new Random(), 1.052, 0.5213);
                    this.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(Disabler.mc.thePlayer.posX, Disabler.mc.thePlayer.posY, Disabler.mc.thePlayer.posZ, false));
                    this.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(Disabler.mc.thePlayer.posX, Disabler.mc.thePlayer.posY - 11.0, Disabler.mc.thePlayer.posZ, true));
                    this.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(Disabler.mc.thePlayer.posX, Disabler.mc.thePlayer.posY, Disabler.mc.thePlayer.posZ, false));
                    e.setCancelled(true);
                }
            }
            else if (packet instanceof C0FPacketConfirmTransaction) {
                this.getClass();
                final boolean c0f = true;
                this.getClass();
                final boolean c0fMultiply = true;
                if (c0f) {
                    if (!c0fMultiply) {
                        this.packetQueue.add(packet);
                        e.setCancelled(true);
                    }
                    else {
                        for (int i = 0; i < 1; ++i) {
                            this.packetQueue.add(packet);
                        }
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
    
    public void sendPacketSilent(final Packet packet) {
        Disabler.mc.getNetHandler().getNetworkManager().sendPacket(packet, null, (GenericFutureListener<? extends Future<? super Void>>[])new GenericFutureListener[0]);
    }
    
    @Override
    public void onDisable() {
        if (Disabler.mc.isSingleplayer()) {
            return;
        }
        if (this.packets != null && this.packets.size() > 0) {
            this.packets.clear();
        }
        if (this.packetQueue != null && this.packetQueue.size() > 0) {
            this.packetQueue.clear();
        }
        this.timer.reset();
        this.timer2.reset();
        this.state = 0;
        this.state2 = 0;
        this.state3 = 0;
        this.stage = 0;
        this.stage2 = 0;
        this.stage3 = 0;
    }
    
    @Override
    public void onEnable() {
        if (Disabler.mc.isSingleplayer()) {
            return;
        }
        if (Disabler.mc.thePlayer != null) {
            Disabler.mc.thePlayer.ticksExisted = 0;
        }
    }
    
    public boolean isBlockUnder() {
        for (int i = (int)Disabler.mc.thePlayer.posY; i >= 0; --i) {
            final BlockPos position = new BlockPos(Disabler.mc.thePlayer.posX, i, Disabler.mc.thePlayer.posZ);
            if (!(Disabler.mc.theWorld.getBlockState(position).getBlock() instanceof BlockAir)) {
                return true;
            }
        }
        return false;
    }
}
