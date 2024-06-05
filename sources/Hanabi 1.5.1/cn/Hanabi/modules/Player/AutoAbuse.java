package cn.Hanabi.modules.Player;

import cn.Hanabi.value.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.events.*;
import cn.Hanabi.command.*;
import java.io.*;
import ClassSub.*;
import com.darkmagician6.eventapi.*;

public class AutoAbuse extends Mod
{
    public static String prefix;
    Class205 delay;
    double state;
    private Value<Double> spammerdelay;
    int num;
    
    
    public AutoAbuse() {
        super("AutoAbuse", Category.PLAYER);
        this.delay = new Class205();
        this.state = 0.0;
        this.spammerdelay = new Value<Double>("AutoAbuse_Delay", 2000.0, 500.0, 10000.0, 100.0);
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate eventUpdate) {
        this.setDisplayName("Delay:" + (double)this.spammerdelay.getValueState() + " Times:" + this.num);
        try {
            Prefix.loadText();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        if (this.delay.isDelayComplete((long)(Object)this.spammerdelay.getValueState())) {
            ++this.state;
            ++this.num;
            AutoAbuse.mc.field_71439_g.func_71165_d(AutoAbuse.prefix + Class346.getAbuse());
            this.delay.reset();
        }
    }
    
    public void onDisable() {
        this.num = 0;
        this.state = 0.0;
        super.onDisable();
    }
    
    static {
        AutoAbuse.prefix = "[Hanabi]";
    }
}
