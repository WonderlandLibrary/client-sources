package cn.Hanabi.modules.Player;

import net.minecraft.util.*;
import cn.Hanabi.value.*;
import cn.Hanabi.modules.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import java.util.*;
import com.darkmagician6.eventapi.*;
import cn.Hanabi.injection.interfaces.*;
import cn.Hanabi.events.*;
import net.minecraft.client.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import ClassSub.*;

public class TP2Bed extends Mod
{
    public BlockPos playerBed;
    public BlockPos fuckingBed;
    public ArrayList<BlockPos> posList;
    Class205 timer;
    public Value<Double> delay;
    private ArrayList<Class148> path;
    
    
    public TP2Bed() {
        super("TP2Bed", Category.PLAYER);
        this.timer = new Class205();
        this.delay = new Value<Double>("TP2Bed_Delay", 600.0, 200.0, 3000.0, 100.0);
        this.path = new ArrayList<Class148>();
    }
    
    public void onEnable() {
        try {
            (this.posList = new ArrayList<BlockPos>(Class190.list)).sort(this::lambda$onEnable$0);
            if (this.posList.size() < 3) {
                this.set(false);
            }
            final ArrayList<BlockPos> list = new ArrayList<BlockPos>(this.posList);
            int n = 1;
            for (final BlockPos blockPos : list) {
                if (++n % 2 == 1) {
                    this.posList.remove(blockPos);
                }
            }
            this.playerBed = this.posList.get(0);
            this.posList.remove(0);
            if (TP2Bed.mc.thePlayer.onGround && TP2Bed.mc.thePlayer.isCollidedVertically && Class180.isOnGround(0.01)) {
                for (int i = 0; i < 49; ++i) {
                    TP2Bed.mc.thePlayer.sendQueue.getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(TP2Bed.mc.thePlayer.posX, TP2Bed.mc.thePlayer.posY + 0.06249, TP2Bed.mc.thePlayer.posZ, false));
                    TP2Bed.mc.thePlayer.sendQueue.getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(TP2Bed.mc.thePlayer.posX, TP2Bed.mc.thePlayer.posY, TP2Bed.mc.thePlayer.posZ, false));
                }
                TP2Bed.mc.thePlayer.onGround = false;
                Class180.setMotion(0.0);
                TP2Bed.mc.thePlayer.jumpMovementFactor = 0.0f;
            }
            this.fuckingBed = this.posList.get(0);
        }
        catch (Throwable t) {
            this.set(false);
        }
    }
    
    @EventTarget
    public void onPacket(final EventPacket eventPacket) {
        if (eventPacket.getPacket() instanceof C03PacketPlayer) {}
    }
    
    @EventTarget
    public void onRender(final EventRender eventRender) {
        try {
            for (final Class148 class148 : this.path) {
                TP2Bed.mc.getRenderManager();
                final double n = class148.getX() - ((IRenderManager)TP2Bed.mc.getRenderManager()).getRenderPosX();
                TP2Bed.mc.getRenderManager();
                final double n2 = class148.getY() - ((IRenderManager)TP2Bed.mc.getRenderManager()).getRenderPosY();
                TP2Bed.mc.getRenderManager();
                Class246.drawEntityESP(n, n2, class148.getZ() - ((IRenderManager)TP2Bed.mc.getRenderManager()).getRenderPosZ(), TP2Bed.mc.thePlayer.getEntityBoundingBox().maxX - TP2Bed.mc.thePlayer.getEntityBoundingBox().minX, TP2Bed.mc.thePlayer.getEntityBoundingBox().maxY - TP2Bed.mc.thePlayer.getEntityBoundingBox().minY + 0.25, 0.0f, 1.0f, 0.0f, 0.2f, 0.0f, 0.0f, 0.0f, 1.0f, 2.0f);
            }
        }
        catch (Exception ex) {}
    }
    
    @Override
    protected void onDisable() {
        Class211.canSendMotionPacket = true;
        super.onDisable();
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate eventUpdate) {
        for (final BlockPos blockPos : this.posList) {
            if (!(TP2Bed.mc.theWorld.getBlockState(blockPos).getBlock() instanceof BlockBed)) {
                Class200.tellPlayer("§b[Hanabi]Destory!" + blockPos);
                this.posList.remove(blockPos);
                this.posList.sort(this::lambda$onUpdate$1);
                this.fuckingBed = this.posList.get(0);
            }
        }
        if (TP2Bed.mc.thePlayer.getDistance((double)this.fuckingBed.getX(), (double)this.fuckingBed.getY(), (double)this.fuckingBed.getZ()) < 4.0) {
            Class211.canSendMotionPacket = true;
            Class200.tellPlayer("§b[Hanabi]Teleported! :3");
            this.set(false);
        }
        if (this.timer.isDelayComplete(this.delay.getValueState())) {
            this.path = this.computePath(new Class148(TP2Bed.mc.thePlayer.posX, TP2Bed.mc.thePlayer.posY, TP2Bed.mc.thePlayer.posZ), new Class148(this.fuckingBed.getX() + 1, this.fuckingBed.getY(), this.fuckingBed.getZ() + 1));
            if (TP2Bed.mc.thePlayer.getDistance((double)this.fuckingBed.getX(), (double)this.fuckingBed.getY(), (double)this.fuckingBed.getZ()) > 4.0) {
                Class200.tellPlayer("§b[Hanabi]Trying to teleport...");
                Class211.canSendMotionPacket = false;
                for (final Class148 class148 : this.path) {
                    TP2Bed.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(class148.getX(), class148.getY(), class148.getZ(), true));
                }
            }
            this.timer.reset();
        }
        if (this.posList.size() == 0) {
            this.set(false);
        }
    }
    
    public double getDistanceToBlock(final BlockPos blockPos) {
        return TP2Bed.mc.thePlayer.getDistance((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ());
    }
    
    private boolean canPassThrow(final BlockPos blockPos) {
        final Block getBlock = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ())).getBlock();
        return getBlock.getMaterial() == Material.air || getBlock.getMaterial() == Material.plants || getBlock.getMaterial() == Material.vine || getBlock == Blocks.ladder || getBlock == Blocks.water || getBlock == Blocks.flowing_water || getBlock == Blocks.wall_sign || getBlock == Blocks.standing_sign;
    }
    
    private ArrayList<Class148> computePath(Class148 addVector, final Class148 class148) {
        if (!this.canPassThrow(new BlockPos(addVector.mc()))) {
            addVector = addVector.addVector(0.0, 1.0, 0.0);
        }
        final Class171 class149 = new Class171(addVector, class148);
        class149.compute();
        int n = 0;
        Class148 class150 = null;
        Class148 class151 = null;
        final ArrayList<Class148> list = new ArrayList<Class148>();
        final ArrayList<Class148> path = class149.getPath();
        for (final Class148 class152 : path) {
            if (n == 0 || n == path.size() - 1) {
                if (class150 != null) {
                    list.add(class150.addVector(0.5, 0.0, 0.5));
                }
                list.add(class152.addVector(0.5, 0.0, 0.5));
                class151 = class152;
            }
            else {
                boolean b = true;
                Label_0403: {
                    if (class152.squareDistanceTo(class151) > 25.0) {
                        b = false;
                    }
                    else {
                        final double min = Math.min(class151.getX(), class152.getX());
                        final double min2 = Math.min(class151.getY(), class152.getY());
                        final double min3 = Math.min(class151.getZ(), class152.getZ());
                        final double max = Math.max(class151.getX(), class152.getX());
                        final double max2 = Math.max(class151.getY(), class152.getY());
                        final double max3 = Math.max(class151.getZ(), class152.getZ());
                        for (int n2 = (int)min; n2 <= max; ++n2) {
                            for (int n3 = (int)min2; n3 <= max2; ++n3) {
                                for (int n4 = (int)min3; n4 <= max3; ++n4) {
                                    if (!Class171.checkPositionValidity(n2, n3, n4, false)) {
                                        b = false;
                                        break Label_0403;
                                    }
                                }
                            }
                        }
                    }
                }
                if (!b) {
                    list.add(class150.addVector(0.5, 0.0, 0.5));
                    class151 = class150;
                }
            }
            class150 = class152;
            ++n;
        }
        return list;
    }
    
    private int lambda$onUpdate$1(final BlockPos blockPos, final BlockPos blockPos2) {
        return (int)(this.getDistanceToBlock(blockPos) - this.getDistanceToBlock(blockPos2));
    }
    
    private int lambda$onEnable$0(final BlockPos blockPos, final BlockPos blockPos2) {
        return (int)(this.getDistanceToBlock(blockPos) - this.getDistanceToBlock(blockPos2));
    }
}
