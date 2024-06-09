/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules.modes.phase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import winter.event.events.BBEvent;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.module.modules.Phase;
import winter.module.modules.modes.Mode;

public class Skip
extends Mode {
    public Phase parent;

    public Skip(Module part, String name) {
        super(part, name);
        this.parent = (Phase)part;
    }

    @Override
    public void enable() {
        if (Skip.mc.theWorld != null && Skip.mc.thePlayer.isSneaking()) {
            float dir = Skip.mc.thePlayer.rotationYaw;
            if (Skip.mc.thePlayer.moveForward < 0.0f) {
                dir += 180.0f;
            }
            if (Skip.mc.thePlayer.moveStrafing > 0.0f) {
                dir -= 90.0f * (Skip.mc.thePlayer.moveForward > 0.0f ? 0.5f : (Skip.mc.thePlayer.moveForward < 0.0f ? -0.5f : 1.0f));
            }
            if (Skip.mc.thePlayer.moveStrafing < 0.0f) {
                dir += 90.0f * (Skip.mc.thePlayer.moveForward > 0.0f ? 0.5f : (Skip.mc.thePlayer.moveForward < 0.0f ? -0.5f : 1.0f));
            }
            double hOff = 0.3;
            float xD = (float)((double)((float)Math.cos((double)(dir + 90.0f) * 3.141592653589793 / 180.0)) * hOff);
            float zD = (float)((double)((float)Math.sin((double)(dir + 90.0f) * 3.141592653589793 / 180.0)) * hOff);
            double[] topkek = new double[]{-0.02500000037252903, -0.028571428997176036, -0.033333333830038704, -0.04000000059604645, -0.05000000074505806, -0.06666666766007741, -0.10000000149011612, -0.20000000298023224, -0.04000000059604645, -0.033333333830038704, -0.028571428997176036, -0.02500000037252903};
            int i2 = 0;
            while (i2 < topkek.length) {
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Skip.mc.thePlayer.posX, Skip.mc.thePlayer.posY + topkek[i2], Skip.mc.thePlayer.posZ, true));
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Skip.mc.thePlayer.posX + (double)(xD * (float)i2), Skip.mc.thePlayer.posY, Skip.mc.thePlayer.posZ + (double)(zD * (float)i2), true));
                ++i2;
            }
            Skip.mc.thePlayer.setPosition(Skip.mc.thePlayer.posX + (double)(xD * 3.0f), Skip.mc.thePlayer.posY, Skip.mc.thePlayer.posZ + (double)(zD * 3.0f));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Skip.mc.thePlayer.posX + (double)(xD * 3.0f), Skip.mc.thePlayer.posY, Skip.mc.thePlayer.posZ + (double)(zD * 3.0f), true));
        }
    }

    @Override
    public void onBB(BBEvent event) {
        if (event.getBounds() != null && event.getBounds().maxY > Skip.mc.thePlayer.boundingBox.minY && (!Skip.mc.thePlayer.onGround || Phase.isInsideBlock())) {
            event.setBounds(null);
        }
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        if (event.isPre()) {
            if (Skip.mc.thePlayer.isCollidedHorizontally && (!Phase.isInsideBlock() || Skip.mc.thePlayer.isSneaking()) && Skip.mc.thePlayer.onGround) {
                float dir = Skip.mc.thePlayer.rotationYaw;
                if (Skip.mc.thePlayer.moveForward < 0.0f) {
                    dir += 180.0f;
                }
                if (Skip.mc.thePlayer.moveStrafing > 0.0f) {
                    dir -= 90.0f * (Skip.mc.thePlayer.moveForward > 0.0f ? 0.5f : (Skip.mc.thePlayer.moveForward < 0.0f ? -0.5f : 1.0f));
                }
                if (Skip.mc.thePlayer.moveStrafing < 0.0f) {
                    dir += 90.0f * (Skip.mc.thePlayer.moveForward > 0.0f ? 0.5f : (Skip.mc.thePlayer.moveForward < 0.0f ? -0.5f : 1.0f));
                }
                double hOff = 0.3;
                float xD = (float)((double)((float)Math.cos((double)(dir + 90.0f) * 3.141592653589793 / 180.0)) * hOff);
                float zD = (float)((double)((float)Math.sin((double)(dir + 90.0f) * 3.141592653589793 / 180.0)) * hOff);
                double[] topkek = new double[]{-0.02500000037252903, -0.028571428997176036, -0.033333333830038704, -0.04000000059604645, -0.05000000074505806, -0.06666666766007741, -0.10000000149011612, -0.20000000298023224, -0.04000000059604645, -0.033333333830038704, -0.028571428997176036, -0.02500000037252903};
                int i2 = 0;
                while (i2 < topkek.length) {
                    mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Skip.mc.thePlayer.posX, Skip.mc.thePlayer.posY + topkek[i2], Skip.mc.thePlayer.posZ, true));
                    mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Skip.mc.thePlayer.posX + (double)(xD * (float)i2), Skip.mc.thePlayer.posY, Skip.mc.thePlayer.posZ + (double)(zD * (float)i2), true));
                    ++i2;
                }
                if (!Skip.mc.thePlayer.isSneaking()) {
                    Skip.mc.thePlayer.setPosition(Skip.mc.thePlayer.posX + (double)(xD * 3.0f), Skip.mc.thePlayer.posY, Skip.mc.thePlayer.posZ + (double)(zD * 3.0f));
                }
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Skip.mc.thePlayer.posX + (double)(xD * 3.0f), Skip.mc.thePlayer.posY, Skip.mc.thePlayer.posZ + (double)(zD * 3.0f), true));
            }
            if (Phase.isInsideBlock() && Skip.mc.gameSettings.keyBindSprint.pressed) {
                Skip.mc.thePlayer.setPosition(Skip.mc.thePlayer.posX, Skip.mc.thePlayer.posY - 1.0E-11, Skip.mc.thePlayer.posZ);
            }
        }
    }
}

