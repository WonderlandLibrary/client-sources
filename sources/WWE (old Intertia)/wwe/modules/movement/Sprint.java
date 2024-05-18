package wwe.modules.movement;

import wwe.*;
import wwe.modules.*;
import java.util.*;
import net.minecraft.util.text.*;
import wwe.utils.*;

public class Sprint extends qMIe
{
    public ModeValue mode;
    
    public Sprint() {
        super("Sprint", "Automatically makes you sprint", 0, Category.MOVEMENT);
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Normal");
        list.add("Multi-Directional");
        this.mode = new ModeValue("Mode", this, "Current mode for the module", "Normal", list);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        if (this.mode.OnGi().equalsIgnoreCase("Normal")) {
            this.owgW("Sprint " + TextFormatting.DARK_GRAY + "Normal");
        }
        if (this.mode.OnGi().equalsIgnoreCase("Multi-Directional")) {
            this.owgW("Sprint " + TextFormatting.DARK_GRAY + "Multi-Directional");
        }
        if (vqpo()) {
            if (this.mode.OnGi().equalsIgnoreCase("Normal")) {
                if (Wrapper.mc.player.movementInput.moveForward <= 0.0f) {
                    return;
                }
                if (Sprint.\u2007\u2007\u200c) {
                    throw null;
                }
            }
            else if (Wrapper.mc.player.movementInput.moveForward == 0.0f && Wrapper.mc.player.movementInput.moveStrafe == 0.0f) {
                return;
            }
            Wrapper.mc.player.setSprinting(true);
        }
    }
    
    public static boolean vqpo() {
        boolean b;
        if (Wrapper.mc.player.getFoodStats().getFoodLevel() > 6) {
            b = true;
            if (Sprint.\u2007\u2007\u200c) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
}
