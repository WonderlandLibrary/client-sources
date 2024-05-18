package wwe.modules.combat;

import wwe.modules.*;
import java.util.*;
import net.minecraft.util.text.*;
import wwe.utils.*;
import net.minecraft.network.play.server.*;
import wwe.*;

public class AntiKnockback extends qMIe
{
    public ModeValue mode;
    
    public AntiKnockback() {
        super("Anti-Knockback", "Makes you take no knockback", 0, Category.COMBAT);
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("New");
        list.add("Old");
        this.mode = new ModeValue("Mode", this, "Current mode for the module", "New", list);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
    }
    
    @oqrr
    public void gjqw(final LqJY lqJY) {
        if (!this.IuiN()) {
            return;
        }
        if (this.mode.OnGi().equalsIgnoreCase("New")) {
            this.owgW("Anti-Knockback" + TextFormatting.DARK_GRAY + " New");
            if (lqJY.MYlK() instanceof SPacketEntityVelocity && ((SPacketEntityVelocity)lqJY.MYlK()).getEntityID() == Wrapper.mc.player.getEntityId()) {
                lqJY.Ggez(true);
            }
            if (lqJY.MYlK() instanceof SPacketExplosion) {
                yYGD.Sgwm((SPacketExplosion)lqJY.MYlK(), 0.0f);
                yYGD.UPvs((SPacketExplosion)lqJY.MYlK(), 0.0f);
                yYGD.yMfH((SPacketExplosion)lqJY.MYlK(), 0.0f);
            }
        }
        if (this.mode.OnGi().equalsIgnoreCase("Old")) {
            this.owgW("Anti-Knockback" + TextFormatting.DARK_GRAY + " Old");
            if (lqJY.MYlK() instanceof SPacketEntityVelocity || lqJY.MYlK() instanceof SPacketExplosion) {
                lqJY.Ggez(true);
            }
        }
    }
    
    @oqrr
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
        super.LPxx();
    }
    
    @oqrr
    @Override
    public void qBmy() {
        hSNE.kduK(this);
        super.qBmy();
    }
}
