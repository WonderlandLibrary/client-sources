/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.misc;

import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.ModeSetting;
import mpp.venusfr.functions.settings.impl.SliderSetting;

@FunctionRegister(name="ClientSounds", type=Category.Misc)
public class ClientSounds
extends Function {
    public ModeSetting mode = new ModeSetting("\u0422\u0438\u043f", "\u041e\u0431\u044b\u0447\u043d\u044b\u0439", "\u041e\u0431\u044b\u0447\u043d\u044b\u0439", "\u041f\u0443\u0437\u044b\u0440\u044c\u043a\u0438");
    public SliderSetting volume = new SliderSetting("\u0413\u0440\u043e\u043c\u043a\u043e\u0441\u0442\u044c", 70.0f, 0.0f, 100.0f, 1.0f);

    public ClientSounds() {
        this.addSettings(this.mode, this.volume);
    }

    public String getFileName(boolean bl) {
        switch ((String)this.mode.get()) {
            case "\u041e\u0431\u044b\u0447\u043d\u044b\u0439": {
                return bl ? "enable" : "disable".toString();
            }
            case "\u041f\u0443\u0437\u044b\u0440\u044c\u043a\u0438": {
                return bl ? "enableBubbles" : "disableBubbles";
            }
        }
        return "";
    }
}

