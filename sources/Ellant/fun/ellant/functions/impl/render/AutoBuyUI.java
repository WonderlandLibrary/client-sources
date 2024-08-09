
package fun.ellant.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventKey;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.BindSetting;
import fun.ellant.utils.math.StopWatch;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;

@FunctionRegister(name="AutoBuy", type=Category.RENDER, desc = "Открывает меню автобая")
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
    public void onEventKey(EventKey e) {
        if (e.getKey() == ((Integer)this.ah.get()).intValue() && this.timer.isReached(200L)) {
            this.ahs = true;
            Minecraft.player.sendChatMessage("/ah");
        }
        if (e.getKey() == ((Integer)this.rct.get()).intValue() && this.timer.isReached(200L)) {
            this.rcts = true;
            Minecraft.player.sendChatMessage(".rct");
        }
    }

    @Override
    public boolean onEnable() {
        super.onEnable();
        this.print(TextFormatting.GREEN + "\u0425\u0437 \u043a\u043d\u043e\u043f\u043a\u0438 \u043d\u0435\u0442\u0443. \u041d\u0430\u0432\u0435\u0440\u043d\u043e\u0435 \u043d\u0443\u0436\u043d\u043e \u043d\u0435 \u043f\u0438\u0441\u0430\u0442\u044c \u0432 \u0434\u0441 \u0433\u0434\u0435 \u0430\u0432\u0442\u043e \u0431\u0430\u0439, \u0430 \u0447\u0438\u0442\u0430\u0442\u044c \u043d\u043e\u0432\u043e\u0441\u0442\u0438.");
        return false;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.print(TextFormatting.RED + " \u0410\u0432\u0442\u043e\u0411\u0430\u0439 \u0432\u044b\u043a\u043b\u044e\u0447\u0435\u043d!");
    }
}

