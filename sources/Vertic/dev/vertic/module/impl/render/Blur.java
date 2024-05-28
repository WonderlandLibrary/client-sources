package dev.vertic.module.impl.render;

import dev.vertic.event.api.EventLink;
import dev.vertic.event.impl.other.TickEvent;
import dev.vertic.module.Module;
import dev.vertic.module.api.Category;
import dev.vertic.setting.impl.BooleanSetting;
import dev.vertic.setting.impl.NumberSetting;
import dev.vertic.util.player.ChatUtil;

public class Blur extends Module {

    public final NumberSetting radius = new NumberSetting("Radius", 10, 5, 40, 5);
    public final BooleanSetting arrayList = new BooleanSetting("ArrayList", true);
    public final BooleanSetting clickgui = new BooleanSetting("ClickGUI", true);
    public final BooleanSetting hotbar = new BooleanSetting("Hotbar", true);

    public Blur() {
        super("Blur", "Blurs selected interfaces.", Category.RENDER);
        this.addSettings(radius, arrayList, clickgui);
    }

    @Override
    protected void onEnable() {
        if (mc.gameSettings.ofFastRender) {
            ChatUtil.addChatMessage("Disable fast render for blur to work.");
            disable();
        }
    }
}
