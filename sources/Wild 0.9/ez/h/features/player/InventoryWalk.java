package ez.h.features.player;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;
import org.lwjgl.input.*;
import ez.h.event.*;

public class InventoryWalk extends Feature
{
    OptionBoolean sneak;
    
    public InventoryWalk() {
        super("InventoryWalk", "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0432\u0430\u043c \u0445\u043e\u0434\u0438\u0442\u044c \u0432 \u0438\u043d\u0432\u0435\u043d\u0442\u0430\u0440\u0435.", Category.PLAYER);
        this.sneak = new OptionBoolean(this, "Sneak", true);
        this.addOptions(this.sneak);
    }
    
    @EventTarget
    public void onUpdate(final EventMotion eventMotion) {
        if (InventoryWalk.mc.m instanceof bkn) {
            return;
        }
        if (InventoryWalk.mc.m != null) {
            for (final bhy bhy : new bhy[] { InventoryWalk.mc.t.T, InventoryWalk.mc.t.V, InventoryWalk.mc.t.U, InventoryWalk.mc.t.W, InventoryWalk.mc.t.X, InventoryWalk.mc.t.Y }) {
                if (this.sneak.enabled || bhy != InventoryWalk.mc.t.Y) {
                    bhy.i = Keyboard.isKeyDown(bhy.j());
                }
            }
        }
    }
}
