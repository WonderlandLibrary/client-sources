/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import digital.rbq.core.Autumn;

public final class Logger {
    protected static final Minecraft mc = Minecraft.getMinecraft();

    public static void log(String msg) {
        if (Logger.mc.thePlayer != null && Logger.mc.theWorld != null) {
            StringBuilder tempMsg = new StringBuilder();
            for (String line : msg.split("\n")) {
                tempMsg.append(line).append("\u00a77");
            }
            Logger.mc.thePlayer.addChatMessage(new ChatComponentText("\u00a7c[" + Autumn.INSTANCE.getName() + "]\u00a77: " + tempMsg.toString()));
        }
    }
}

