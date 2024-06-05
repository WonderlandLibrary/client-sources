// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.event;

import java.util.Iterator;
import gg.childtrafficking.smokex.gui.element.Element;
import gg.childtrafficking.smokex.module.Module;
import gg.childtrafficking.smokex.SmokeXClient;
import net.minecraft.client.renderer.GlStateManager;
import gg.childtrafficking.smokex.utils.render.RenderingUtils;
import net.minecraft.network.play.server.S07PacketRespawn;
import gg.childtrafficking.smokex.module.ModuleManager;
import gg.childtrafficking.smokex.module.modules.visuals.HUDModule;
import gg.childtrafficking.smokex.event.events.render.EventRender2D;
import gg.childtrafficking.smokex.event.events.network.EventReceivePacket;
import gg.childtrafficking.smokex.event.events.network.EventSendPacket;
import gg.childtrafficking.smokex.event.events.system.EventLoadWorld;
import gg.childtrafficking.smokex.event.events.system.EventKeyPress;
import gg.childtrafficking.smokex.event.events.player.EventSendMessage;
import gg.childtrafficking.smokex.event.events.player.EventMove;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;

public final class DefaultListener
{
    private final EventListener<EventUpdate> eventUpdate;
    private final EventListener<EventMove> eventMove;
    private final EventListener<EventSendMessage> eventSendMessage;
    private final EventListener<EventKeyPress> eventKeyPress;
    private final EventListener<EventLoadWorld> eventloadWorld;
    private final EventListener<EventSendPacket> eventSendPacket;
    private final EventListener<EventReceivePacket> eventRecievePacket;
    private final EventListener<EventRender2D> eventRender2D;
    
    public DefaultListener() {
        this.eventUpdate = (event -> {
            if (!ModuleManager.getInstance(HUDModule.class).isToggled() && ModuleManager.getInstance(HUDModule.class).panic) {
                ModuleManager.getInstance(HUDModule.class).toggle();
            }
            return;
        });
        this.eventMove = (event -> {});
        this.eventSendMessage = (event -> {});
        this.eventKeyPress = (event -> {});
        this.eventloadWorld = (event -> {});
        this.eventSendPacket = (event -> {});
        this.eventRecievePacket = (event -> {
            if (event.getPacket() instanceof S07PacketRespawn) {}
            return;
        });
        this.eventRender2D = (event -> {
            final double scale = 2.0f / RenderingUtils.getScaledFactor();
            GlStateManager.scale(scale, scale, scale);
            SmokeXClient.getInstance().getModuleManager().getModules().iterator();
            final Iterator iterator;
            while (iterator.hasNext()) {
                final Module module = iterator.next();
                if (module.isToggled() && module.renderElements) {
                    module.getElements().iterator();
                    final Iterator iterator2;
                    while (iterator2.hasNext()) {
                        final Element element = iterator2.next();
                        if (element.visible) {
                            element.render(event.getPartialTicks());
                        }
                    }
                }
            }
            final double scale2 = RenderingUtils.getScaledFactor() / 2.0f;
            GlStateManager.scale(scale2, scale2, scale2);
        });
    }
}
