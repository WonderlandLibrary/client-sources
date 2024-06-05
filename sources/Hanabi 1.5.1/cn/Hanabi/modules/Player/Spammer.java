package cn.Hanabi.modules.Player;

import ClassSub.*;
import java.util.*;
import cn.Hanabi.value.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.events.*;
import cn.Hanabi.command.*;
import java.io.*;
import com.darkmagician6.eventapi.*;

public class Spammer extends Mod
{
    public static String text;
    public static String prefix;
    Class205 delay;
    Random random;
    double state;
    private Value<Double> spammerdelay;
    private Value<Boolean> randomstring;
    private int num;
    
    
    public Spammer() {
        super("Spammer", Category.PLAYER);
        this.delay = new Class205();
        this.random = new Random();
        this.state = 0.0;
        this.spammerdelay = new Value<Double>("Spammer_Delay", 2000.0, 500.0, 10000.0, 100.0);
        this.randomstring = new Value<Boolean>("Spammer_RandomString", true);
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate eventUpdate) {
        this.setDisplayName("Delay:" + this.spammerdelay.getValueState() + " Times:" + this.num);
        try {
            cn.Hanabi.command.Spammer.loadText();
            Prefix.loadText();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        if (this.delay.isDelayComplete((long)(Object)this.spammerdelay.getValueState())) {
            ++this.state;
            ++this.num;
            String s = Spammer.prefix + Spammer.text;
            if (this.randomstring.getValueState()) {
                s = s + " >" + new Class290().getStringRandom(6) + "<";
            }
            Spammer.mc.field_71439_g.func_71165_d(s);
            this.delay.reset();
        }
    }
    
    public void onDisable() {
        this.state = 0.0;
        this.num = 0;
        super.onDisable();
    }
    
    static {
        Spammer.text = "Hanabi Hacked Client Code by Margele.";
        Spammer.prefix = "[Hanabi]";
    }
    
    public class Class290
    {
        final Spammer this$0;
        
        
        public Class290(final Spammer this$0) {
            ((Class290)this).this$0 = this$0;
        }
        
        public String getStringRandom(final int n) {
            String s = "";
            final Random random = new Random();
            for (int i = 0; i < n; ++i) {
                final String s2 = (random.nextInt(2) % 2 == 0) ? "char" : "num";
                if ("char".equalsIgnoreCase(s2)) {
                    s += (char)(random.nextInt(26) + ((random.nextInt(2) % 2 == 0) ? 65 : 97));
                }
                else if ("num".equalsIgnoreCase(s2)) {
                    s += String.valueOf(random.nextInt(10));
                }
            }
            return s;
        }
    }
}
