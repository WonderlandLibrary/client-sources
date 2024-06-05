// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.visuals;

import gg.childtrafficking.smokex.gui.element.VAlignment;
import gg.childtrafficking.smokex.gui.element.Element;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S01PacketJoinGame;
import gg.childtrafficking.smokex.module.ModuleManager;
import gg.childtrafficking.smokex.gui.element.elements.RectElement;
import gg.childtrafficking.smokex.gui.element.elements.TextElement;
import gg.childtrafficking.smokex.event.events.network.EventReceivePacket;
import gg.childtrafficking.smokex.event.events.render.EventRender2D;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.EventListener;
import gg.childtrafficking.smokex.utils.system.TimerUtil;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "SessionInformation", renderName = "Session Information", description = "Displays current session information", aliases = { "session", "seesioninfo" }, category = ModuleCategory.VISUALS)
public final class SessionInformationModule extends Module
{
    private final TimerUtil serverTime;
    private String lastServer;
    private String currentServer;
    private int flags;
    private final EventListener<EventUpdate> updateEventListener;
    private final EventListener<EventRender2D> render2DEventListener;
    private final EventListener<EventReceivePacket> sendPacketEventListener;
    
    public SessionInformationModule() {
        this.serverTime = new TimerUtil();
        this.flags = 0;
        this.updateEventListener = (event -> {
            final TextElement timeOnServer = (TextElement)this.getElement("TimeOnServer");
            return;
        });
        this.render2DEventListener = (event -> {
            final RectElement backgroundBar = (RectElement)this.getElement("background").getElement("backgroundBar");
            backgroundBar.setColor(ModuleManager.getInstance(HUDModule.class).getTopColor());
            final TextElement timeOnServer2 = (TextElement)this.getElement("background").getElement("timeOnServer");
            final TextElement flags = (TextElement)this.getElement("background").getElement("flags");
            flags.setText("Flags: " + this.flags);
            if (this.mc.isSingleplayer()) {
                timeOnServer2.setText("Time On Server: N/A");
            }
            else {
                timeOnServer2.setText("Time On Server: " + this.serverTime.formatTime());
            }
            return;
        });
        this.sendPacketEventListener = (event -> {
            if (event.getPacket() instanceof S01PacketJoinGame) {
                if (!this.mc.getCurrentServerData().serverIP.toLowerCase().equals(this.lastServer)) {
                    this.serverTime.reset();
                }
                this.lastServer = this.mc.getCurrentServerData().serverIP.toLowerCase();
            }
            if (event.getPacket() instanceof S07PacketRespawn) {
                this.flags = 0;
            }
        });
    }
    
    @Override
    public void init() {
        this.addElement(new RectElement("background", 5.0f, 30.0f, 175.0f, 60.0f, 1184274)).addElement(new RectElement("backgroundBar", 0.0f, 0.0f, 175.0f, 2.0f, 1184274)).getParent().addElement(new TextElement("timeOnServer", 5.0f, 7.0f, "Time On Server", -1)).getParent().addElement(new TextElement("flags", 5.0f, 17.0f, "Flags", -1)).setVAlignment(VAlignment.BOTTOM);
        super.init();
    }
    
    public int getFlags() {
        return this.flags;
    }
    
    public void setFlags(final int flags) {
        this.flags = flags;
    }
}
