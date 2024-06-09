// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.movement;

import xyz.niggfaclient.utils.player.PlayerUtil;
import net.minecraft.network.Packet;
import xyz.niggfaclient.utils.network.PacketUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.init.Blocks;
import xyz.niggfaclient.module.ModuleManager;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockAir;
import xyz.niggfaclient.events.impl.MotionEvent;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.CollideEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.property.impl.EnumProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "NoFall", description = "Prevents you from taking fall damage", cat = Category.MOVEMENT)
public class NoFall extends Module
{
    private final EnumProperty<Mode> mode;
    @EventLink
    private final Listener<CollideEvent> collideEventListener;
    @EventLink
    private final Listener<MotionEvent> motionEventListener;
    
    public NoFall() {
        this.mode = new EnumProperty<Mode>("Mode", Mode.Packet);
        this.collideEventListener = (e -> {
            if (this.mode.getValue() == Mode.Collide && this.mc.thePlayer != null && !this.mc.thePlayer.isSneaking() && this.mc.thePlayer.fallDistance >= 2.6 && this.canFall() && (e.getBlock() instanceof BlockAir || e.getBlock() instanceof BlockLiquid) && !ModuleManager.getModule(Flight.class).isEnabled() && e.getBlock() == Blocks.air) {
                e.setBoundingBox(new AxisAlignedBB(-100.0, -2.0, -100.0, 100.0, 1.0, 100.0).offset(e.getX(), e.getY(), e.getZ()));
            }
            return;
        });
        this.motionEventListener = (e -> {
            this.setDisplayName(this.getName() + " §7" + this.mode.getValue());
            if (this.canFall()) {
                switch (this.mode.getValue()) {
                    case Packet: {
                        if (this.mc.thePlayer.fallDistance > 2.5) {
                            PacketUtil.sendPacketNoEvent(new C03PacketPlayer(true));
                            this.mc.thePlayer.fallDistance = 0.0f;
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case Spoof: {
                        if (this.mc.thePlayer.fallDistance > 2.0f && this.mc.thePlayer.ticksExisted % 2 == 0) {
                            e.setOnGround(true);
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case Rounded: {
                        if (this.mc.thePlayer.fallDistance > 2.5) {
                            e.setY((double)Math.round(e.getY()));
                            e.setOnGround(true);
                            this.mc.thePlayer.fallDistance = 0.0f;
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                }
            }
        });
    }
    
    private boolean canFall() {
        return this.mc.thePlayer.isEntityAlive() && this.mc.theWorld != null && !this.mc.thePlayer.isInWater() && !this.mc.thePlayer.isInLava() && PlayerUtil.isBlockUnderNoCollisions();
    }
    
    public enum Mode
    {
        Packet, 
        Spoof, 
        Collide, 
        Rounded;
    }
}
