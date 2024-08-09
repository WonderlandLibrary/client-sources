/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventKey;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.BindSetting;
import mpp.venusfr.utils.math.StopWatch;
import net.minecraft.util.text.TextFormatting;

@FunctionRegister(name="AutoBuy", type=Category.Misc)
public class AutoBuyUI
extends Function {
    public BindSetting setting = new BindSetting("\u041a\u043d\u043e\u043f\u043a\u0430 \u043e\u0442\u043a\u0440\u044b\u0442\u0438\u044f", -1);
    public BindSetting ah = new BindSetting("\u041e\u0442\u043a\u0440\u044b\u0442\u044c \u0430\u0443\u043a\u0446\u0438\u043e\u043d", -1);
    public BindSetting rct = new BindSetting("\u041f\u0435\u0440\u0435\u0437\u0430\u0439\u0442\u0438", -1);
    StopWatch timer = new StopWatch();
    boolean ahs;
    boolean rcts;

    public AutoBuyUI() {
        this.addSettings(this.setting, this.ah, this.rct);
    }

    @Subscribe
    public void onEventKey(EventKey eventKey) {
        if (eventKey.getKey() == ((Integer)this.ah.get()).intValue() && this.timer.isReached(200L)) {
            this.ahs = true;
            AutoBuyUI.mc.player.sendChatMessage("/ah");
        }
        if (eventKey.getKey() == ((Integer)this.rct.get()).intValue() && this.timer.isReached(200L)) {
            this.rcts = true;
            AutoBuyUI.mc.player.sendChatMessage(".rct");
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.print(TextFormatting.GREEN + "\u0410\u0432\u0442\u043e\u0431\u0430\u0439 \u0432\u043a\u043b\u044e\u0447\u0435\u043d!");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.print(TextFormatting.RED + "\u0410\u0432\u0442\u043e\u0431\u0430\u0439 \u0432\u044b\u043a\u043b\u044e\u0447\u0435\u043d!");
    }
}

