/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.command;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import us.amerikan.amerikan;

public abstract class Command {
    private String name;
    private String dicription;
    private Minecraft mc;

    public Command(String name, String description) {
        this.name = name;
        this.dicription = description;
    }

    public abstract void execute(String[] var1);

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDicription() {
        return this.dicription;
    }

    public void setDicription(String dicription) {
        this.dicription = dicription;
    }

    public static void messageWithoutPrefix(String msg) {
        ChatComponentText chat = new ChatComponentText(msg);
        if (msg != null) {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(chat);
        }
    }

    public static void messageWithPrefix(String msg) {
        Command.messageWithoutPrefix(String.valueOf(amerikan.instance.Client_Prefix) + msg);
    }
}

