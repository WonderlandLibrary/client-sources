package wwe.modules.combat;

import wwe.modules.*;
import net.minecraft.util.text.*;
import wwe.utils.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import wwe.modules.exploits.*;
import wwe.*;
import java.util.*;

public class AutoClicker extends qMIe
{
    public int pcWm;
    double wDfv;
    public boolean yXmg;
    public ModeValue mode;
    public BooleanValue XLmT;
    public BooleanValue 19Delay;
    public BooleanValue triggerbot;
    public NumberValue cps;
    
    public AutoClicker() {
        super("Auto Clicker", "Automatically clicks for you", 0, Category.COMBAT);
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Left");
        list.add("Right");
        this.mode = new ModeValue("Mode", this, "Current mode for the module", "Left", list);
        this.19Delay = new BooleanValue("1.9+ Delay", this, "Use 1.9+ delay", true);
        this.triggerbot = new BooleanValue("Trigger bot", this, "Attack when cross hair is on entity", false);
        this.cps = new NumberValue("CPS", this, "Clicks per second", 11.0, 2.0, 20.0);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        if (this.mode.OnGi().equalsIgnoreCase("Left")) {
            this.owgW("Auto Clicker " + TextFormatting.DARK_GRAY + "Left");
            if (AutoClicker.\u2009\u2007\u2007\u2008\u200c\u2005\u200c\u2001) {
                throw null;
            }
        }
        else {
            this.owgW("Auto Clicker " + TextFormatting.DARK_GRAY + "Right");
        }
        if (this.19Delay.OnGi()) {
            if (this.mode.OnGi().equalsIgnoreCase("Left") && this.triggerbot.OnGi() && Wrapper.mc.player.getCooledAttackStrength(0.0f) == 1.0f && Wrapper.mc.objectMouseOver != null && Wrapper.mc.objectMouseOver.entityHit instanceof EntityLivingBase) {
                Wrapper.mc.playerController.attackEntity((EntityPlayer)Wrapper.mc.player, Wrapper.mc.objectMouseOver.entityHit);
                OffHand.LInL();
                Wrapper.mc.player.resetCooldown();
                return;
            }
            if (this.mode.OnGi().equalsIgnoreCase("Left") && yYGD.dgWY(Wrapper.mc.gameSettings.keyBindAttack) && Wrapper.mc.player.getCooledAttackStrength(0.0f) == 1.0f) {
                if (Wrapper.mc.objectMouseOver != null && Wrapper.mc.objectMouseOver.entityHit instanceof EntityLivingBase) {
                    Wrapper.mc.playerController.attackEntity((EntityPlayer)Wrapper.mc.player, Wrapper.mc.objectMouseOver.entityHit);
                }
                OffHand.LInL();
                Wrapper.mc.player.resetCooldown();
            }
            if (this.mode.OnGi().equalsIgnoreCase("Right") && yYGD.dgWY(Wrapper.mc.gameSettings.keyBindUseItem) && Wrapper.mc.player.getCooledAttackStrength(0.0f) == 1.0f) {
                yYGD.AXbo();
                Wrapper.mc.player.resetCooldown();
            }
        }
        if (!this.19Delay.OnGi()) {
            final Random random = new Random();
            ++this.pcWm;
            if (this.mode.OnGi().equalsIgnoreCase("Left") && this.triggerbot.OnGi() && this.pcWm >= 20.0 / this.cps.OnGi() && Wrapper.mc.objectMouseOver != null && Wrapper.mc.objectMouseOver.entityHit instanceof EntityLivingBase) {
                OffHand.LInL();
                Wrapper.mc.playerController.attackEntity((EntityPlayer)Wrapper.mc.player, Wrapper.mc.objectMouseOver.entityHit);
                this.wDfv = Math.random() * 2.0;
                this.yXmg = random.nextBoolean();
                this.pcWm = 0;
                return;
            }
            if (this.mode.OnGi().equalsIgnoreCase("Left") && yYGD.dgWY(Wrapper.mc.gameSettings.keyBindAttack)) {
                final double n = this.pcWm;
                final double n2 = 20.0;
                final double doubleValue = this.cps.OnGi();
                double wDfv;
                if (this.yXmg) {
                    wDfv = -this.wDfv;
                    if (AutoClicker.\u2009\u2007\u2007\u2008\u200c\u2005\u200c\u2001) {
                        throw null;
                    }
                }
                else {
                    wDfv = this.wDfv;
                }
                if (n >= n2 / (doubleValue + wDfv)) {
                    OffHand.LInL();
                    Wrapper.mc.player.resetCooldown();
                    if (Wrapper.mc.objectMouseOver != null && Wrapper.mc.objectMouseOver.entityHit instanceof EntityLivingBase) {
                        Wrapper.mc.playerController.attackEntity((EntityPlayer)Wrapper.mc.player, Wrapper.mc.objectMouseOver.entityHit);
                    }
                    this.wDfv = Math.random() * 2.0;
                    this.yXmg = random.nextBoolean();
                    this.pcWm = 0;
                }
            }
            if (this.mode.OnGi().equalsIgnoreCase("Right") && yYGD.dgWY(Wrapper.mc.gameSettings.keyBindUseItem) && this.pcWm >= 20.0 / this.cps.OnGi()) {
                yYGD.AXbo();
            }
        }
    }
}
