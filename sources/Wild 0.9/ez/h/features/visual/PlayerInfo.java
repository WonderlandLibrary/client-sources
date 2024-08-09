package ez.h.features.visual;

import ez.h.event.events.*;
import ez.h.event.*;
import ez.h.features.*;
import java.awt.*;
import ez.h.ui.clickgui.options.*;

public class PlayerInfo extends Feature
{
    public OptionBoolean coords;
    public OptionBoolean ping;
    public OptionColor color;
    ez.h.ui.hudeditor.registry.PlayerInfo playerInfo;
    public OptionBoolean fps;
    
    @EventTarget
    public void render(final EventRenderDraggable eventRenderDraggable) {
        this.playerInfo.render(eventRenderDraggable.mouseX, eventRenderDraggable.mouseY, eventRenderDraggable.ticks, false);
    }
    
    public PlayerInfo() {
        super("PlayerInfo", "\u041e\u0442\u043e\u0431\u0440\u0430\u0436\u0430\u0435\u0442 \u0438\u043d\u0444\u043e\u0440\u043c\u0430\u0446\u0438\u044e \u043e \u0438\u0433\u0440\u043e\u043a\u0435.", Category.VISUAL);
        this.playerInfo = new ez.h.ui.hudeditor.registry.PlayerInfo();
        this.fps = new OptionBoolean(this, "FPS", true);
        this.ping = new OptionBoolean(this, "Ping", true);
        this.coords = new OptionBoolean(this, "Coords", true);
        this.color = new OptionColor(this, "Color", new Color(-1));
        this.addOptions(this.fps, this.ping, this.coords, this.color);
    }
}
