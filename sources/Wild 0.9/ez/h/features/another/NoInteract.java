package ez.h.features.another;

import ez.h.event.events.*;
import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class NoInteract extends Feature
{
    public static OptionBoolean armorStand;
    public static OptionBoolean workbench;
    public static OptionBoolean players;
    public static OptionBoolean trapdoors;
    public static OptionBoolean furnace;
    public static OptionBoolean storage;
    public static OptionBoolean doors;
    
    public static boolean isIgnored(final aow aow) {
        return (NoInteract.workbench.enabled && aow == aox.ai) || (NoInteract.furnace.enabled && aow == aox.al) || (NoInteract.storage.enabled && (aow == aox.ae || aow == aox.bQ)) || (NoInteract.doors.enabled && aow instanceof aqa) || (NoInteract.trapdoors.enabled && aow instanceof aur);
    }
    
    @EventTarget
    public void onRightClick(final EventPacketSend eventPacketSend) {
        if (!(eventPacketSend.getPacket() instanceof ma)) {
            return;
        }
        if (isIgnored(NoInteract.mc.f.o(NoInteract.mc.s.a()).u())) {
            eventPacketSend.setCancelled(true);
        }
    }
    
    public NoInteract() {
        super("NoInteract", "\u041e\u0442\u043c\u0435\u043d\u044f\u0435\u0442 \u0432\u0437\u0430\u0438\u043c\u043e\u0434\u0435\u0439\u0441\u0442\u0432\u0438\u0435 \u0441 \u043d\u0435\u043a\u043e\u0442\u043e\u0440\u044b\u043c\u0438 \u0431\u043b\u043e\u043a\u0430\u043c\u0438.", Category.ANOTHER);
        NoInteract.workbench = new OptionBoolean(this, "Crafting Table", true);
        NoInteract.furnace = new OptionBoolean(this, "Furnace", true);
        NoInteract.armorStand = new OptionBoolean(this, "Armor Stands", true);
        NoInteract.players = new OptionBoolean(this, "Players", true);
        NoInteract.storage = new OptionBoolean(this, "Storage", true);
        NoInteract.doors = new OptionBoolean(this, "Doors", true);
        NoInteract.trapdoors = new OptionBoolean(this, "Trapdoors", true);
        this.addOptions(NoInteract.workbench, NoInteract.furnace, NoInteract.armorStand, NoInteract.players, NoInteract.storage, NoInteract.doors, NoInteract.trapdoors);
    }
    
    public static boolean isIgnoredEntity(final vg vg) {
        return (vg instanceof aed && NoInteract.players.enabled) || (vg instanceof abz && NoInteract.armorStand.enabled);
    }
}
