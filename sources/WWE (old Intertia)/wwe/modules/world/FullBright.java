package wwe.modules.world;

import wwe.*;
import wwe.modules.*;
import java.util.*;
import net.minecraft.util.text.*;
import wwe.utils.*;
import net.minecraft.potion.*;

public class FullBright extends qMIe
{
    public ModeValue mode;
    public float EPvV;
    
    public FullBright() {
        super("Full Bright", "Makes the brightness brighter", 48, Category.WORLD);
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Gamma");
        list.add("Potion");
        this.mode = new ModeValue("Mode", this, "Current mode for the module", "Potion", list);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        if (this.mode.OnGi().equalsIgnoreCase("Gamma")) {
            this.owgW("FullBright" + TextFormatting.DARK_GRAY + " Gamma");
            Wrapper.mc.gameSettings.gammaSetting = 1000.0f;
        }
        if (this.mode.OnGi().equalsIgnoreCase("Potion")) {
            this.owgW("FullBright" + TextFormatting.DARK_GRAY + " Potion");
            final PotionEffect potionEffect = new PotionEffect(Potion.getPotionById(16), 123456789, 5);
            potionEffect.setPotionDurationMax(true);
            Wrapper.mc.player.addPotionEffect(potionEffect);
        }
    }
    
    @Override
    public void LPxx() {
        this.EPvV = Wrapper.mc.gameSettings.gammaSetting;
        super.LPxx();
    }
    
    @Override
    public void qBmy() {
        Wrapper.mc.player.removeActivePotionEffect(Potion.getPotionById(16));
        Wrapper.mc.gameSettings.gammaSetting = this.EPvV;
        super.qBmy();
    }
}
