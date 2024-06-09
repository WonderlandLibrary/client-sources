/*
 * Decompiled with CFR 0_122.
 */
package winter.console.cmds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class Command {
    public String name;
    public String desc;
    public String use;

    public Command(String name) {
        this.name = name;
    }

    public void desc(String desc) {
        this.desc = desc;
    }

    public String desc() {
        return this.desc;
    }

    public void use(String use) {
        this.use = use;
    }

    public String use() {
        return this.use;
    }

    public void run(String cmd2) {
    }

    public void printChat(String print) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00a77[\u00a76Winter\u00a77]: " + (Object)((Object)EnumChatFormatting.RESET) + print));
    }
}

