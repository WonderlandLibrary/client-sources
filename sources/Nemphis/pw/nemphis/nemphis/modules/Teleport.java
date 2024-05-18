/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import pw.vertexcode.nemphis.events.UpdateEvent;
import pw.vertexcode.nemphis.module.Category;
import pw.vertexcode.util.event.EventListener;
import pw.vertexcode.util.module.ModuleInformation;
import pw.vertexcode.util.module.types.ToggleableModule;

@ModuleInformation(category=Category.PLAYER, color=-12657535, name="Teleport")
public class Teleport
extends ToggleableModule {
    private boolean teleporting = false;
    private int count = 0;
    private double x = 0.0;
    private double y = 0.0;
    private double z = 0.0;

    @Override
    public void onEnabled() {
        this.teleporting = false;
        this.count = 0;
    }

    @EventListener
    public void onUpdate(UpdateEvent e) {
        if (Teleport.mc.gameSettings.keyBindUseItem.getIsKeyPressed()) {
            if (Teleport.mc.objectMouseOver != null && Teleport.mc.objectMouseOver.func_178782_a() != null) {
                this.setX(Teleport.mc.objectMouseOver.func_178782_a().getX());
                this.setY(Teleport.mc.objectMouseOver.func_178782_a().getY());
                this.setZ(Teleport.mc.objectMouseOver.func_178782_a().getZ());
            }
            this.sendMessage("Set Teleport-Position to: \u00a73" + this.x + " " + this.y + " " + this.z, true);
        }
        if (Teleport.mc.thePlayer.isSneaking() && !this.teleporting) {
            this.teleporting = true;
        }
        if (Teleport.mc.thePlayer.getPosition().distanceSq(this.x, this.y, this.z) <= 2.0 || this.count >= 30) {
            this.count = 0;
            this.teleporting = false;
        }
        if (this.teleporting) {
            if (this.count >= 8) {
                Teleport.mc.thePlayer.setPositionAndUpdate(this.x, this.y + 1.0, this.z);
                Teleport.mc.thePlayer.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.x, this.y - 1.0, this.z, true));
                Teleport.mc.thePlayer.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Teleport.mc.thePlayer.posX, Teleport.mc.thePlayer.posY - 1.0, Teleport.mc.thePlayer.posZ, true));
                Teleport.mc.thePlayer.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(this.x, this.y - 1.0, this.z, true));
            }
            ++this.count;
        }
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }
}

