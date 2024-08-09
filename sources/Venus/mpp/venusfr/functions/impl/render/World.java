/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import mpp.venusfr.functions.settings.impl.ColorSetting;
import mpp.venusfr.functions.settings.impl.ModeListSetting;
import mpp.venusfr.functions.settings.impl.ModeSetting;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SUpdateTimePacket;

@FunctionRegister(name="CustomWorld", type=Category.Visual)
public class World
extends Function {
    final ModeListSetting modes = new ModeListSetting("\u0418\u0437\u043c\u0435\u043d\u044f\u0442\u044c", new BooleanSetting("\u0412\u0440\u0435\u043c\u044f", true), new BooleanSetting("\u0422\u0443\u043c\u0430\u043d", true));
    private ModeSetting timeOfDay = new ModeSetting("\u0412\u0440\u0435\u043c\u044f \u0441\u0443\u0442\u043e\u043a", "\u041d\u043e\u0447\u044c", "\u0414\u0435\u043d\u044c", "\u0417\u0430\u043a\u0430\u0442", "\u0420\u0430\u0441\u0441\u0432\u0435\u0442", "\u041d\u043e\u0447\u044c", "\u041f\u043e\u043b\u043d\u043e\u0447\u044c", "\u041f\u043e\u043b\u0434\u0435\u043d\u044c").setVisible(this::lambda$new$0);
    public ColorSetting colorFog = new ColorSetting("\u0426\u0432\u0435\u0442 \u0442\u0443\u043c\u0430\u043d\u0430", -1).setVisible(this::lambda$new$1);
    public SliderSetting distanceFog = new SliderSetting("\u0414\u0430\u043b\u044c\u043d\u043e\u0441\u0442\u044c \u0442\u0443\u043c\u0430\u043d\u0430", 4.0f, 1.1f, 30.0f, 0.01f).setVisible(this::lambda$new$2);

    public World() {
        this.addSettings(this.modes, this.timeOfDay, this.colorFog, this.distanceFog);
    }

    @Subscribe
    public void onPacket(EventPacket eventPacket) {
        IPacket<?> iPacket = eventPacket.getPacket();
        if (iPacket instanceof SUpdateTimePacket) {
            SUpdateTimePacket sUpdateTimePacket = (SUpdateTimePacket)iPacket;
            if (((Boolean)this.modes.getValueByName("\u0412\u0440\u0435\u043c\u044f").get()).booleanValue()) {
                if (this.timeOfDay.is("\u0414\u0435\u043d\u044c")) {
                    sUpdateTimePacket.worldTime = 1000L;
                } else if (this.timeOfDay.is("\u0417\u0430\u043a\u0430\u0442")) {
                    sUpdateTimePacket.worldTime = 12000L;
                } else if (this.timeOfDay.is("\u0420\u0430\u0441\u0441\u0432\u0435\u0442")) {
                    sUpdateTimePacket.worldTime = 23000L;
                } else if (this.timeOfDay.is("\u041d\u043e\u0447\u044c")) {
                    sUpdateTimePacket.worldTime = 13000L;
                } else if (this.timeOfDay.is("\u041f\u043e\u043b\u043d\u043e\u0447\u044c")) {
                    sUpdateTimePacket.worldTime = 18000L;
                } else if (this.timeOfDay.is("\u041f\u043e\u043b\u0434\u0435\u043d\u044c")) {
                    sUpdateTimePacket.worldTime = 6000L;
                }
            }
        }
    }

    public ModeListSetting getModes() {
        return this.modes;
    }

    public ModeSetting getTimeOfDay() {
        return this.timeOfDay;
    }

    public ColorSetting getColorFog() {
        return this.colorFog;
    }

    public SliderSetting getDistanceFog() {
        return this.distanceFog;
    }

    private Boolean lambda$new$2() {
        return (Boolean)this.modes.getValueByName("\u0422\u0443\u043c\u0430\u043d").get();
    }

    private Boolean lambda$new$1() {
        return (Boolean)this.modes.getValueByName("\u0422\u0443\u043c\u0430\u043d").get();
    }

    private Boolean lambda$new$0() {
        return (Boolean)this.modes.getValueByName("\u0412\u0440\u0435\u043c\u044f").get();
    }
}

