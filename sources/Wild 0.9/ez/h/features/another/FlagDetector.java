package ez.h.features.another;

import java.util.*;
import ez.h.event.events.*;
import ez.h.utils.*;
import ez.h.event.*;
import ez.h.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class FlagDetector extends Feature
{
    OptionBoolean flight;
    public static Map<Feature, Integer> flags;
    OptionBoolean waterleave;
    OptionBoolean waterspeed;
    OptionBoolean timer;
    OptionBoolean jesus;
    OptionBoolean strafe;
    OptionBoolean speed;
    OptionSlider maxFlags;
    
    static {
        FlagDetector.flags = new HashMap<Feature, Integer>();
    }
    
    @EventTarget
    public void onPacketReceive(final EventPacketReceive eventPacketReceive) {
        this.setSuffix(Utils.format("##", this.maxFlags.getNum()));
        if (eventPacketReceive.getPacket() instanceof jq) {
            final Integer n;
            FlagDetector.flags.entrySet().stream().filter(entry -> entry.getKey().isEnabled()).forEach(entry2 -> n = entry2.setValue(entry2.getValue() + 1));
            FlagDetector.flags.entrySet().stream().filter(entry4 -> entry4.getValue() >= this.maxFlags.getNum()).forEach(entry3 -> {
                entry3.setValue(0);
                ((Feature)entry3.getKey()).setEnabled(false);
            });
        }
    }
    
    public static void init() {
        FlagDetector.flags.put(Main.getFeatureByName("Flight"), 0);
        FlagDetector.flags.put(Main.getFeatureByName("Speed"), 0);
        FlagDetector.flags.put(Main.getFeatureByName("Jesus"), 0);
        FlagDetector.flags.put(Main.getFeatureByName("Strafe"), 0);
        FlagDetector.flags.put(Main.getFeatureByName("WaterLeave"), 0);
        FlagDetector.flags.put(Main.getFeatureByName("WaterSpeed"), 0);
        FlagDetector.flags.put(Main.getFeatureByName("Timer"), 0);
    }
    
    public FlagDetector() {
        super("FlagDetector", "\u041e\u0442\u043a\u043b\u044e\u0447\u0430\u0435\u0442 \u0444\u0443\u043d\u043a\u0446\u0438\u0438 \u043f\u0440\u0438 \u0441\u0440\u0430\u0431\u0430\u0442\u044b\u0432\u0430\u043d\u0438\u0438 \u0430\u043d\u0442\u0438\u0447\u0438\u0442\u0430.", Category.ANOTHER);
        this.maxFlags = new OptionSlider(this, "Max Flags", 4.0f, 1.0f, 10.0f, OptionSlider.SliderType.NULLINT);
        this.flight = new OptionBoolean(this, "Flight", true);
        this.speed = new OptionBoolean(this, "Speed", true);
        this.jesus = new OptionBoolean(this, "Jesus", true);
        this.strafe = new OptionBoolean(this, "Strafe", true);
        this.waterleave = new OptionBoolean(this, "WaterLeave", true);
        this.waterspeed = new OptionBoolean(this, "WaterSpeed", true);
        this.timer = new OptionBoolean(this, "Timer", true);
        this.addOptions(this.maxFlags, this.flight, this.speed, this.jesus, this.strafe, this.waterleave, this.waterspeed, this.timer);
    }
}
