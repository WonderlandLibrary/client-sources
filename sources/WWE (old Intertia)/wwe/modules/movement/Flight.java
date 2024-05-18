package wwe.modules.movement;

import wwe.modules.*;
import java.util.*;
import net.minecraft.util.text.*;
import wwe.utils.*;
import net.minecraft.util.math.*;
import net.minecraft.client.entity.*;
import wwe.*;

public class Flight extends qMIe
{
    private int eFyB;
    public ModeValue mode;
    public NumberValue speed;
    
    public Flight() {
        super("Flight", "Allows you to fly", 34, Category.MOVEMENT);
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Vanilla");
        list.add("Hypixel");
        this.mode = new ModeValue("Mode", this, "Current mode for the module", "Vanilla", list);
        this.speed = new NumberValue("Speed", this, "The speed you fly", 1.0, 0.0, 10.0);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        if (this.mode.OnGi().equalsIgnoreCase("Vanilla")) {
            this.owgW("Flight " + TextFormatting.DARK_GRAY + "Vanilla");
            Wrapper.mc.player.capabilities.isFlying = true;
            Wrapper.mc.player.capabilities.setFlySpeed(this.speed.OnGi().floatValue() / 10.0f);
        }
    }
    
    @oqrr
    public void MtRB(final omqH omqH) {
        if (this.mode.OnGi().equalsIgnoreCase("Hypixel")) {
            this.owgW("Flight " + TextFormatting.DARK_GRAY + "Hypixel");
            float sqrt = MathHelper.sqrt(Wrapper.mc.player.motionX * Wrapper.mc.player.motionX + Wrapper.mc.player.motionZ * Wrapper.mc.player.motionZ);
            float n = (float)Math.atan(-Wrapper.mc.player.motionY * 0.3000000029802322) * 15.0f;
            if (sqrt > 0.1f) {
                sqrt = 0.1f;
            }
            if (Wrapper.mc.player.getHealth() <= 0.0f) {
                n = 0.0f;
            }
            final EntityPlayerSP player = Wrapper.mc.player;
            player.cameraYaw += (sqrt - Wrapper.mc.player.cameraYaw) * 0.4f;
            final EntityPlayerSP player2 = Wrapper.mc.player;
            player2.cameraPitch += (n - Wrapper.mc.player.cameraPitch) * 0.8f;
            Wrapper.mc.player.motionY = 0.0;
            Wrapper.mc.player.onGround = true;
            int i = 0;
            while (i < 3) {
                Wrapper.mc.player.setPosition(Wrapper.mc.player.posX, Wrapper.mc.player.posY + 1.0E-9, Wrapper.mc.player.posZ);
                if (Wrapper.mc.player.ticksExisted % 3 == 0) {
                    Wrapper.mc.player.setPosition(Wrapper.mc.player.posX, Wrapper.mc.player.posY - 1.0E-9, Wrapper.mc.player.posZ);
                }
                ++i;
                if (Flight.\u2003\u200d\u2004\u2006\u2000) {
                    throw null;
                }
            }
        }
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
        super.LPxx();
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
        Wrapper.mc.player.capabilities.isFlying = false;
        super.qBmy();
    }
}
