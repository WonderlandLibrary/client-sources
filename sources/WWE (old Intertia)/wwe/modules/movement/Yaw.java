package wwe.modules.movement;

import wwe.*;
import wwe.modules.*;
import java.util.*;
import net.minecraft.util.text.*;
import wwe.utils.*;

public class Yaw extends qMIe
{
    public ModeValue yaw;
    
    public Yaw() {
        super("Yaw", "Changes your yaw to one of 4 cardinal directions", 0, Category.MOVEMENT);
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("0");
        list.add("90");
        list.add("180");
        list.add("-90");
        this.yaw = new ModeValue("Yaw", this, "What yaw degrees you'll use", "0", list);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        if (this.yaw.OnGi().equalsIgnoreCase("0")) {
            this.owgW("Yaw " + TextFormatting.DARK_GRAY + "0");
            Wrapper.mc.player.rotationYaw = 0.0f;
        }
        if (this.yaw.OnGi().equalsIgnoreCase("90")) {
            this.owgW("Yaw " + TextFormatting.DARK_GRAY + "90");
            Wrapper.mc.player.rotationYaw = 90.0f;
        }
        if (this.yaw.OnGi().equalsIgnoreCase("180")) {
            this.owgW("Yaw " + TextFormatting.DARK_GRAY + "180");
            Wrapper.mc.player.rotationYaw = 180.0f;
        }
        if (this.yaw.OnGi().equalsIgnoreCase("-90")) {
            this.owgW("Yaw " + TextFormatting.DARK_GRAY + "-90");
            Wrapper.mc.player.rotationYaw = -90.0f;
        }
    }
}
