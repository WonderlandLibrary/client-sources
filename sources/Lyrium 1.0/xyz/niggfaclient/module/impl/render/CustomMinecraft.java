// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.render;

import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.PacketEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "CustomMinecraft", description = "CustomMinecraft", cat = Category.RENDER)
public class CustomMinecraft extends Module
{
    public final Property<Boolean> betterScoreboardText;
    public final Property<Boolean> scoreboardFont;
    public final Property<Boolean> scoreboardLine;
    public final Property<Boolean> betterChat;
    public final Property<Boolean> showAnimationModes;
    public final Property<Boolean> containerAnimation;
    public final Property<Boolean> menuAnimation;
    public final Property<Boolean> chatAnimation;
    public String lastMessage;
    public int amount;
    public int line;
    @EventLink
    private final Listener<PacketEvent> packetEventListener;
    
    public CustomMinecraft() {
        this.betterScoreboardText = new Property<Boolean>("Better Scoreboard Text", false);
        this.scoreboardFont = new Property<Boolean>("Scoreboard Font", false);
        this.scoreboardLine = new Property<Boolean>("Scoreboard Line", false);
        this.betterChat = new Property<Boolean>("Better Chat", false);
        this.showAnimationModes = new Property<Boolean>("Show Animation Modes..", false);
        this.containerAnimation = new Property<Boolean>("Container Animation", false, this.showAnimationModes::getValue);
        this.menuAnimation = new Property<Boolean>("Menu Animation", false, this.showAnimationModes::getValue);
        this.chatAnimation = new Property<Boolean>("Chat Animation", false, this.showAnimationModes::getValue);
        this.lastMessage = "";
        S02PacketChat s02PacketChat;
        IChatComponent message;
        String rawMessage;
        GuiNewChat chatGui;
        this.packetEventListener = (e -> {
            if (e.getState() == PacketEvent.State.RECEIVE && this.betterChat.getValue()) {
                if (e.getPacket() instanceof S2EPacketCloseWindow && this.mc.currentScreen instanceof GuiChat) {
                    e.setCancelled();
                }
                if (e.getPacket() instanceof S02PacketChat) {
                    s02PacketChat = (S02PacketChat)e.getPacket();
                    if (s02PacketChat.getType() == 0) {
                        message = s02PacketChat.getChatComponent();
                        rawMessage = message.getFormattedText();
                        chatGui = this.mc.ingameGUI.getChatGUI();
                        if (this.lastMessage.equals(rawMessage)) {
                            chatGui.deleteChatLine(this.line);
                            ++this.amount;
                            s02PacketChat.getChatComponent().appendText(EnumChatFormatting.GRAY + " [x" + this.amount + "]");
                        }
                        else {
                            this.amount = 1;
                        }
                        ++this.line;
                        this.lastMessage = rawMessage;
                        chatGui.printChatMessageWithOptionalDeletion(message, this.line);
                        if (this.line > 256) {
                            this.line = 0;
                        }
                        e.setCancelled();
                    }
                }
            }
        });
    }
}
