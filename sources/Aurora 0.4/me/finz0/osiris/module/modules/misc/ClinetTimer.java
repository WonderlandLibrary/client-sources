package me.finz0.osiris.module.modules.misc;

import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;

import java.text.DecimalFormat;

public class ClinetTimer extends Module {
    public ClinetTimer() {
        super("Timer", Category.MISC, "Clinet's TimerSwitch");
        AuroraMod.getInstance().settingsManager.rSetting(speedUsual = new Setting("Speed", this, 4.2, 1, 10, false, "ClinetTimerSpeed"));
        AuroraMod.getInstance().settingsManager.rSetting(fastUsual = new Setting("FastSpeed", this, 10, 1, 1000, false, "ClinetTimerFastSpeed"));
        AuroraMod.getInstance().settingsManager.rSetting(tickToFast = new Setting("TickToFast", this, 4, 0, 20, false, "ClinetTimerTickToFast"));
        AuroraMod.getInstance().settingsManager.rSetting(tickToNoFast = new Setting("TickToDisableFast", this, 7, 0, 20, false, "ClinetTimerTickToNoFast"));
    }

    int tickWait = 0;
    float hudInfo = 0;
    Setting speedUsual;
    Setting fastUsual;
    Setting tickToFast;
    Setting tickToNoFast;



    public void onDisable() {
        mc.timer.tickLength = 50.0F;
    }

    public void onUpdate() {
        if ((float)tickWait == (float)tickToFast.getValDouble()) {
            mc.timer.tickLength = 50.0F / (float)fastUsual.getValDouble();
            hudInfo = (float)fastUsual.getValDouble();
        }

        if ((float)this.tickWait >= (float)tickToNoFast.getValDouble()) {
            this.tickWait = 0;
            mc.timer.tickLength = 50.0F / (float)speedUsual.getValDouble();
            hudInfo = (float)speedUsual.getValDouble();
        }

        ++this.tickWait;
    }

    public String getHudInfo(){
        return new DecimalFormat("0.##").format(hudInfo);
    }


}
