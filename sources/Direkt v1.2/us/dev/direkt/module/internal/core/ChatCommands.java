package us.dev.direkt.module.internal.core;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import us.dev.api.property.Property;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.gui.screen.EventDisplayGui;
import us.dev.direkt.event.internal.events.game.network.EventPreReceivePacket;
import us.dev.direkt.event.internal.events.game.network.EventSendPacket;
import us.dev.direkt.event.internal.filters.GuiScreenFilter;
import us.dev.direkt.event.internal.filters.PacketFilter;
import us.dev.direkt.gui.minecraft.override.GuiDirektChat;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.Module;
import us.dev.direkt.module.property.ModProperty;
import us.dev.direkt.module.property.Propertied;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

import java.util.Collections;
import java.util.Set;

@ModData(label = "ChatCommands", category = ModCategory.CORE)
public class ChatCommands extends Module implements Propertied {

    private ModProperty<String> prefix = new ModProperty<>(this, new Property<>("Prefix", "."));

    public ChatCommands() {
        super.addCommand(new ModuleCommand(Direkt.getInstance().getCommandManager(), getLabel(), getAliases()) {});
    }

    public String getPrefix() {
        return this.prefix.getValue();
    }

    @Listener
    protected Link<EventSendPacket> onSendPacket = new Link<>(event -> {
        final String message = ((CPacketChatMessage) event.getPacket()).getMessage();
        if (message.startsWith(this.prefix.getValue())) {
            event.setCancelled(true);
            Direkt.getInstance().getCommandManager().execute(message.substring((this.prefix.getValue()).length()))
                    .ifPresent(output -> {
                        final String[] splitOutput = output.split(System.lineSeparator());
                        Wrapper.addChatMessage(splitOutput[0]);
                        for (int i = 1; i < splitOutput.length; i++) {
                            Wrapper.addChatMessage(splitOutput[i]);
                        }
                    });
        }
    }, new PacketFilter<>(CPacketChatMessage.class));

    @Listener
    protected Link<EventDisplayGui> onDisplayGui = new Link<>(event -> {
        event.setGuiScreen(((GuiChat) event.getGuiScreen()).getDefaultInputFieldText().equals("/") ? new GuiDirektChat("/") : new GuiDirektChat());
    }, new GuiScreenFilter(GuiChat.class));

    @Override
    public Set<ModProperty> getProperties() {
        return Collections.singleton(prefix);
    }

    @Listener
    protected Link<EventPreReceivePacket> onReceivePacket = new Link<>(event -> {
       String a = "wallawiga was here";
        String text = ((SPacketChat) event.getPacket()).getChatComponent().getFormattedText();
        if (text.contains(Character.toString((char) 0x058D))) {
            Wrapper.receivePacket(new SPacketTitle(SPacketTitle.Type.TITLE, new TextComponentString(TextFormatting.DARK_PURPLE + a)));
        }
        // MSG
        else if (text.contains(Character.toString((char)0x00A7) + "6 -> " + Character.toString((char)0x00A7) + "r")) {
            Wrapper.addChatMessage(text);
            event.setCancelled(true);
        }
        // TPA
        else if (text.contains(Character.toString((char)0x00A7) + "6 has requested to teleport to you." + Character.toString((char)0x00A7) + "r")) {
            Wrapper.addChatMessage(text);
            event.setCancelled(true);
        }
        else if (text.contains(Character.toString((char)0x00A7) + "6To teleport, type " + Character.toString((char)0x00A7) + "r")) {
            Wrapper.addChatMessage(text);
            event.setCancelled(true);
        }
        else if (text.contains(Character.toString((char)0x00A7) + "6To deny this request, type " + Character.toString((char)0x00A7) + "r")) {
            Wrapper.addChatMessage(text);
            event.setCancelled(true);
        }
        else if (text.contains(Character.toString((char)0x00A7) + "6This request will timeout after" + Character.toString((char)0x00A7) + "r")) {
            Wrapper.addChatMessage(text);
            event.setCancelled(true);
        }
        //System.out.println(text);
    }, new PacketFilter<>(SPacketChat.class));

}
