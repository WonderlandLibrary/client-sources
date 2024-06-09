// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.world;

import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.block.BlockCake;
import net.minecraft.block.BlockBed;
import events.listeners.EventPacket;
import events.Event;
import intent.AquaDev.aqua.utils.RotationUtil;
import net.minecraft.client.Minecraft;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.utils.TimeUtil;
import net.minecraft.util.BlockPos;
import intent.AquaDev.aqua.modules.Module;

public class BedFucker extends Module
{
    public static BlockPos pos;
    TimeUtil time;
    double range;
    
    public BedFucker() {
        super("BedFucker", Type.World, "BedFucker", 0, Category.World);
        this.time = new TimeUtil();
        this.range = 4.5;
    }
    
    public static void lookAtPos(final double x, final double y, final double z) {
        double dirx = BedFucker.mc.thePlayer.posX - x;
        double diry = BedFucker.mc.thePlayer.posY - y;
        double dirz = BedFucker.mc.thePlayer.posZ - z;
        final double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
        dirx /= len;
        diry /= len;
        dirz /= len;
        float yaw = (float)Math.atan2(dirz, dirx);
        float pitch = (float)Math.asin(diry);
        pitch = (float)(pitch * 180.0 / 3.141592653589793);
        yaw = (float)(yaw * 180.0 / 3.141592653589793);
        yaw += 90.0;
        final float f2 = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6f + 0.2f;
        final float f3 = f2 * f2 * f2 * 1.2f;
        yaw -= yaw % f3;
        pitch -= pitch % (f3 * f2);
        RotationUtil.setYaw(yaw, 180.0f);
        RotationUtil.setPitch(pitch, 90.0f);
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventPacket) {
            for (int y = (int)this.range; y >= -this.range; --y) {
                for (int x = (int)(-this.range); x <= this.range; ++x) {
                    for (int z = (int)(-this.range); z <= this.range; ++z) {
                        final int posX = (int)(BedFucker.mc.thePlayer.posX - 0.5 + x);
                        final int posZ = (int)(BedFucker.mc.thePlayer.posZ - 0.5 + z);
                        final int posY = (int)(BedFucker.mc.thePlayer.posY - 0.5 + y);
                        BedFucker.pos = new BlockPos(posX, posY, posZ);
                        final Block block = BedFucker.mc.theWorld.getBlockState(BedFucker.pos).getBlock();
                        if (block instanceof BlockBed || block instanceof BlockCake || block instanceof BlockDragonEgg) {
                            final PlayerControllerMP playerController = BedFucker.mc.playerController;
                            final long timeLeft = (long)(PlayerControllerMP.curBlockDamageMP / 2.0f);
                            if (this.time.hasReached(800L)) {
                                BedFucker.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                                BedFucker.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, BedFucker.pos, EnumFacing.DOWN));
                                BedFucker.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, BedFucker.pos, EnumFacing.DOWN));
                                BedFucker.mc.getNetHandler().addToSendQueue(new C0APacketAnimation());
                                this.time.reset();
                            }
                        }
                    }
                }
            }
        }
    }
}
