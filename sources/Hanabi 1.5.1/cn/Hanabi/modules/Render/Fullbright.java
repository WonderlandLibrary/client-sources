package cn.Hanabi.modules.Render;

import cn.Hanabi.value.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.events.*;
import net.minecraft.potion.*;
import com.darkmagician6.eventapi.*;

public class Fullbright extends Mod
{
    private Value<String> mode;
    public float oldGammaSetting;
    
    
    public Fullbright() {
        super("Fullbright", Category.RENDER);
        (this.mode = new Value<String>("Fullbright", "Mode", 0)).addValue("Gamma");
        this.mode.addValue("Potion");
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate eventUpdate) {
        if (this.mode.isCurrentMode("Gamma")) {
            this.oldGammaSetting = Fullbright.mc.gameSettings.gammaSetting;
            Fullbright.mc.gameSettings.gammaSetting = 1000.0f;
        }
        if (this.mode.isCurrentMode("Potion")) {
            Fullbright.mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 4000, 1));
        }
    }
    
    public void onEnable() {
        super.onEnable();
    }
    
    public void onDisable() {
        Fullbright.mc.gameSettings.gammaSetting = this.oldGammaSetting;
        super.onDisable();
    }
}
