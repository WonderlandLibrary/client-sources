package wwe.modules.player;

import wwe.modules.*;
import java.util.*;
import net.minecraft.util.text.*;
import wwe.*;
import wwe.utils.*;
import net.minecraft.entity.passive.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;

public class HorseJump extends qMIe
{
    public NumberValue height;
    public ModeValue mode;
    
    public HorseJump() {
        super("Horse Jump", "Allows you to do a full horse jump", 0, Category.PLAYER);
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Normal");
        list.add("Boost");
        this.height = new NumberValue("Height", this, "The jump height of the horse", 1.2, 0.0, 3.0);
        this.mode = new ModeValue("Mode", this, "Current mode for the module", "Normal", list);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        if (this.mode.OnGi().equalsIgnoreCase("Normal")) {
            this.owgW("Horse Jump" + TextFormatting.DARK_GRAY + " Normal");
            yYGD.olBF(1.0f);
        }
        if (this.mode.OnGi().equalsIgnoreCase("Boost")) {
            this.owgW("Horse Jump" + TextFormatting.DARK_GRAY + " Boost");
            if (Wrapper.mc.player.getRidingEntity() != null && Wrapper.mc.player.isRidingHorse()) {
                final AbstractHorse abstractHorse = (AbstractHorse)Wrapper.mc.player.getRidingEntity();
                yYGD.olBF(1.0f);
                if (yYGD.dgWY(Wrapper.mc.gameSettings.keyBindJump) && !abstractHorse.isHorseJumping() && abstractHorse.onGround) {
                    final AbstractHorse abstractHorse2 = abstractHorse;
                    abstractHorse2.motionY += this.height.OnGi();
                    Wrapper.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)abstractHorse, CPacketEntityAction.Action.START_RIDING_JUMP, 900));
                }
            }
        }
    }
}
