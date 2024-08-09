package ez.h.features.visual;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class NoOverlay extends Feature
{
    public static OptionBoolean fire;
    public OptionBoolean pumpkin;
    public static OptionBoolean liquid;
    public static OptionBoolean bossbar;
    public OptionBoolean portal;
    public static OptionBoolean blocks;
    
    public NoOverlay() {
        super("NoOverlay", "\u0423\u0431\u0438\u0440\u0430\u0435\u0442 \u043d\u0430\u043b\u043e\u0436\u0435\u043d\u0438\u044f \u0442\u0435\u043a\u0441\u0442\u0443\u0440 \u0432 \u044d\u043a\u0440\u0430\u043d\u0435.", Category.VISUAL);
        NoOverlay.fire = new OptionBoolean(this, "Fire", true);
        this.pumpkin = new OptionBoolean(this, "Pumpkin", true);
        this.portal = new OptionBoolean(this, "Portal", true);
        NoOverlay.liquid = new OptionBoolean(this, "Liquid", true);
        NoOverlay.blocks = new OptionBoolean(this, "Blocks", true);
        NoOverlay.bossbar = new OptionBoolean(this, "BossBar", false);
        this.addOptions(NoOverlay.fire, this.pumpkin, this.portal, NoOverlay.liquid, NoOverlay.blocks, NoOverlay.bossbar);
    }
}
