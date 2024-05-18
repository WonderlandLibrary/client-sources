/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.command;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import pw.vertexcode.nemphis.Nemphis;

public abstract class Command {
    public Minecraft mc = Minecraft.getMinecraft();

    public abstract String getName();

    public abstract String getDescription();

    public abstract void execute(String[] var1);

    public void sendMessage(String text, boolean prefix) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(prefix ? String.valueOf(String.valueOf(Nemphis.instance.getPrefix())) + " " + text : text));
    }
}

