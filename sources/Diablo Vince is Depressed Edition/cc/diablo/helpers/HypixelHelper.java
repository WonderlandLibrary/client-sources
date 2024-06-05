/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.helpers;

import net.minecraft.client.Minecraft;

public class HypixelHelper {
    public static void slimeDisable() {
        try {
            new Thread(){

                @Override
                public void run() {
                    try {
                        Minecraft.getMinecraft().gameSettings.keyBindJump.pressed = true;
                        Thread.sleep(75L);
                        Minecraft.getMinecraft().gameSettings.keyBindJump.pressed = false;
                        Thread.sleep(75L);
                        Minecraft.getMinecraft().gameSettings.keyBindJump.pressed = true;
                        Thread.sleep(75L);
                        Minecraft.getMinecraft().gameSettings.keyBindJump.pressed = false;
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

