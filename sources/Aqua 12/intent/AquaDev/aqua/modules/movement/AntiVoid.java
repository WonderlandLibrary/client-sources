// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.movement;

import net.minecraft.block.BlockAir;
import net.minecraft.util.BlockPos;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import intent.AquaDev.aqua.Aqua;
import events.listeners.EventUpdate;
import events.listeners.EventPacket;
import net.minecraft.util.MathHelper;
import java.util.Random;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import events.listeners.EventTimerDisabler;
import events.Event;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class AntiVoid extends Module
{
    public AntiVoid() {
        super("AntiVoid", Type.Movement, "AntiVoid", 0, Category.Movement);
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
    public void onEvent(final Event e) {
        if (e instanceof EventTimerDisabler) {
            final Packet packet = EventTimerDisabler.getPacket();
            if (packet instanceof C0FPacketConfirmTransaction || packet instanceof C00PacketKeepAlive) {
                final float fallDistance = (float)MathHelper.getRandomDoubleInRange(new Random(), 2.5, 3.0);
                if (AntiVoid.mc.thePlayer.fallDistance > fallDistance) {}
            }
        }
        if (e instanceof EventPacket) {
            final Packet packet = EventPacket.getPacket();
            if (packet instanceof C0FPacketConfirmTransaction || packet instanceof C00PacketKeepAlive) {
                final float fallDistance = (float)MathHelper.getRandomDoubleInRange(new Random(), 2.5, 3.0);
                if (AntiVoid.mc.thePlayer.fallDistance > fallDistance) {}
            }
        }
        if (e instanceof EventUpdate && !this.isBlockUnder()) {
            final float fallDistance2 = (float)MathHelper.getRandomDoubleInRange(new Random(), 2.95, 3.0);
            if (AntiVoid.mc.thePlayer.fallDistance > fallDistance2 && !Aqua.moduleManager.getModuleByName("Longjump").isToggled()) {
                final EntityPlayerSP thePlayer = AntiVoid.mc.thePlayer;
                thePlayer.motionY -= 0.42;
            }
        }
    }
    
    public boolean isBlockUnder() {
        for (int i = (int)AntiVoid.mc.thePlayer.posY; i >= 0; --i) {
            final BlockPos position = new BlockPos(AntiVoid.mc.thePlayer.posX, i, AntiVoid.mc.thePlayer.posZ);
            if (!(AntiVoid.mc.theWorld.getBlockState(position).getBlock() instanceof BlockAir)) {
                return true;
            }
        }
        return false;
    }
}
