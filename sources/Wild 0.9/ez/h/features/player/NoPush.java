package ez.h.features.player;

import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;

public class NoPush extends Feature
{
    public static OptionBoolean blocks;
    public OptionBoolean water;
    OptionSlider amount;
    
    @EventTarget
    public void onPush(final EventPushOutOfBlocks eventPushOutOfBlocks) {
        eventPushOutOfBlocks.setCancelled(true);
    }
    
    @Override
    public void onDisable() {
        NoPush.mc.h.R = 0.1f;
        super.onDisable();
    }
    
    public NoPush() {
        super("NoPush", "\u0414\u0440\u0443\u0433\u0438\u0435 \u0438\u0433\u0440\u043e\u043a\u0438/\u0432\u043e\u0434\u0430/\u0431\u043b\u043e\u043a\u0438 \u043d\u0435 \u0441\u043c\u043e\u0433\u0443\u0442 \u0432\u0430\u0441 \u0442\u043e\u043b\u043a\u0430\u0442\u044c.", Category.PLAYER);
        this.amount = new OptionSlider(this, "Entities", 1.0f, 0.1f, 1.0f, OptionSlider.SliderType.NULL);
        this.water = new OptionBoolean(this, "Water", true);
        NoPush.blocks = new OptionBoolean(this, "Blocks", true);
        this.addOptions(this.amount, this.water, NoPush.blocks);
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        if (this.water.enabled && NoPush.mc.h.ao() && !NoPush.mc.h.isMoving()) {
            NoPush.mc.h.setMotion(0.0f);
        }
        NoPush.mc.h.R = this.amount.getNum();
    }
}
