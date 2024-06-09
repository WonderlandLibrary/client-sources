// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.command.impl.commands;

import net.minecraft.client.gui.GuiScreen;
import intent.AquaDev.aqua.utils.ChatUtil;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.command.Command;

public class Ign extends Command
{
    public Ign() {
        super("ign");
    }
    
    @Override
    public void execute(final String[] args) {
        ChatUtil.sendChatMessageWithPrefix("§bYour Username: " + Aqua.INSTANCE.ircClient.getIngameName());
        ChatUtil.sendChatMessageWithPrefix("§bCopyt to Clipoard");
        GuiScreen.setClipboardString(Aqua.INSTANCE.ircClient.getIngameName());
    }
}
