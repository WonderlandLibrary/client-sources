package wwe.modules.movement;

import java.text.*;
import wwe.modules.*;
import net.minecraft.util.text.*;
import wwe.utils.*;
import net.minecraft.entity.item.*;
import wwe.*;

public class BoatFly extends qMIe
{
    private final DecimalFormat PxbF;
    public NumberValue speed;
    public BooleanValue boats;
    
    public BoatFly() {
        super("Boat Fly", "Fly in boats", 0, Category.MOVEMENT);
        this.PxbF = new DecimalFormat("##.#");
    }
    
    @Override
    public void HrDr() {
        this.speed = new NumberValue("Speed", this, "Speed you move", 2.0, 0.0, 10.0);
        this.boats = new BooleanValue("Boats", this, "Only boats", true);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        this.owgW("Boat Fly " + TextFormatting.DARK_GRAY + this.PxbF.format(((IcRI<Object>)this.speed).OnGi()));
        if (!this.boats.OnGi()) {
            if (Wrapper.mc.player.getRidingEntity() != null && Wrapper.mc.gameSettings.keyBindJump.isKeyDown()) {
                Wrapper.mc.player.getRidingEntity().motionY = this.speed.OnGi() / 10.0;
                if (BoatFly.\u2001\u2007) {
                    throw null;
                }
            }
        }
        else if (Wrapper.mc.player.getRidingEntity() != null && Wrapper.mc.player.getRidingEntity() instanceof EntityBoat) {
            ((EntityBoat)Wrapper.mc.player.getRidingEntity()).rotationYaw = Wrapper.mc.player.rotationYaw;
            if (Wrapper.mc.gameSettings.keyBindJump.isKeyDown()) {
                Wrapper.mc.player.getRidingEntity().motionY = this.speed.OnGi() / 10.0;
            }
        }
    }
}
