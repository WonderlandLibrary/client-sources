// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.misc;

import java.util.Iterator;
import net.minecraft.util.IChatComponent;
import net.minecraft.event.ClickEvent;
import net.minecraft.network.play.server.S02PacketChat;
import gg.childtrafficking.smokex.event.events.network.EventReceivePacket;
import gg.childtrafficking.smokex.event.EventListener;
import gg.childtrafficking.smokex.property.properties.BooleanProperty;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "AutoHypixel", renderName = "Auto Hypixel", description = "Automatically do hypixel related things", aliases = { "autoplay", "autogg", "hypixel" }, category = ModuleCategory.MISC)
public final class AutoHypixelModule extends Module
{
    private BooleanProperty autoPlayProperty;
    private BooleanProperty autoGG;
    private EventListener<EventReceivePacket> eventReceivePacketEventListener;
    
    public AutoHypixelModule() {
        this.autoPlayProperty = new BooleanProperty("Auto Play", true);
        this.autoGG = new BooleanProperty("Auto GG", true);
        this.eventReceivePacketEventListener = (event -> {
            if (event.getPacket() instanceof S02PacketChat) {
                final IChatComponent component = ((S02PacketChat)event.getPacket()).getChatComponent();
                if (this.autoPlayProperty.getValue()) {
                    if (component.getChatStyle().getChatClickEvent() != null && component.getChatStyle().getChatClickEvent().getAction() == ClickEvent.Action.RUN_COMMAND && component.getChatStyle().getChatClickEvent().getValue().startsWith("/play")) {
                        this.mc.thePlayer.sendChatMessage(component.getChatStyle().getChatClickEvent().getValue());
                    }
                    component.getSiblings().iterator();
                    final Iterator iterator;
                    while (iterator.hasNext()) {
                        final IChatComponent comp = iterator.next();
                        if (comp.getChatStyle().getChatClickEvent() != null && comp.getChatStyle().getChatClickEvent().getAction() == ClickEvent.Action.RUN_COMMAND && comp.getChatStyle().getChatClickEvent().getValue().startsWith("/play")) {
                            this.mc.thePlayer.sendChatMessage(comp.getChatStyle().getChatClickEvent().getValue());
                        }
                    }
                }
                if (this.autoGG.getValue() && component.getUnformattedText().contains("You Won!")) {
                    this.mc.thePlayer.sendChatMessage("gg #SMOKE");
                }
            }
        });
    }
}
