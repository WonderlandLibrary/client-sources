/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.movement;

import cc.diablo.event.impl.PacketEvent;
import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.helpers.HypixelHelper;
import cc.diablo.helpers.PacketHelper;
import cc.diablo.helpers.player.KillAuraHelper;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.ModeSetting;
import cc.diablo.setting.impl.NumberSetting;
import com.google.common.eventbus.Subscribe;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class Highjump
extends Module {
    public ModeSetting mode = new ModeSetting("Mode", "Verus", "Verus", "HypixelSlime", "Matrix");
    public NumberSetting speed = new NumberSetting("Speed", 3.0, 1.0, 5.0, 1.0);
    public boolean damaged;

    public Highjump() {
        super("Highjump", "Extends your jump height", 0, Category.Movement);
        this.addSettings(this.mode, this.speed);
    }

    @Subscribe
    public void onPacket(PacketEvent e) {
        switch (this.mode.getMode()) {
            case "Verus": 
            case "HypixelSlime": {
                if (!(e.getPacket() instanceof S12PacketEntityVelocity) || Highjump.mc.thePlayer.getEntityId() != ((S12PacketEntityVelocity)e.getPacket()).getEntityID()) break;
                Highjump.mc.thePlayer.motionY = this.speed.getVal();
                this.damaged = true;
                break;
            }
            case "Matrix": {
                if (!(e.getPacket() instanceof S12PacketEntityVelocity) || Highjump.mc.thePlayer.getEntityId() != ((S12PacketEntityVelocity)e.getPacket()).getEntityID()) break;
                Highjump.mc.thePlayer.motionY = this.speed.getVal();
                this.damaged = true;
            }
        }
    }

    @Override
    public void onEnable() {
        this.damaged = false;
        switch (this.mode.getMode()) {
            case "HypixelSlime": {
                HypixelHelper.slimeDisable();
                break;
            }
            case "Verus": {
                double x = Highjump.mc.thePlayer.posX;
                double y = Highjump.mc.thePlayer.posY;
                double z = Highjump.mc.thePlayer.posZ;
                Highjump.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 3.1, z, false));
                Highjump.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 1.0E-4, z, false));
                Highjump.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + 0.01, z, true));
                break;
            }
            case "Matrix": {
                PacketHelper.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(Highjump.mc.thePlayer.getHeldItem()));
                Highjump.mc.playerController.onPlayerRightClick(Highjump.mc.thePlayer, Minecraft.theWorld, Highjump.mc.thePlayer.getHeldItem(), Highjump.mc.thePlayer.getPosition(), Block.getFacingDirection(Highjump.mc.thePlayer.getPosition()), Highjump.mc.thePlayer.getLookVec());
            }
        }
        super.onEnable();
    }

    @Subscribe
    public void onUpdate(UpdateEvent e) {
        switch (this.mode.getMode()) {
            case "Verus": 
            case "HypixelSlime": {
                if (this.damaged) {
                    Highjump.mc.thePlayer.motionY = this.speed.getVal();
                    this.damaged = false;
                    break;
                }
                if (!(Highjump.mc.thePlayer.lastTickPosY > Highjump.mc.thePlayer.posY) || !Highjump.mc.thePlayer.isCollided) break;
                this.setToggled(false);
                break;
            }
            case "Matrix": {
                Highjump.mc.thePlayer.motionY = 1.3f;
                this.damaged = false;
                KillAuraHelper.setRotations(e, Highjump.mc.thePlayer.rotationYaw, 8.0f);
                if (!(Highjump.mc.thePlayer.lastTickPosY > Highjump.mc.thePlayer.posY) || !Highjump.mc.thePlayer.isCollided) break;
                this.setToggled(false);
            }
        }
    }
}

