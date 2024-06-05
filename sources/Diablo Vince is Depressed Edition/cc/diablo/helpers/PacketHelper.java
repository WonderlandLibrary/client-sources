/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.helpers;

import cc.diablo.helpers.render.ChatHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

public class PacketHelper {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void sendPacket(Packet packet) {
        mc.getNetHandler().getNetworkManager().sendPacket(packet);
    }

    public static void sendPacket(int amount, Packet packet) {
        for (int i = 0; i < amount; ++i) {
            mc.getNetHandler().getNetworkManager().sendPacket(packet);
            ChatHelper.addChat("Sent");
        }
    }

    public static void sendPacketNoEvent(Packet packet) {
        mc.getNetHandler().getNetworkManager().sendPacketNoEvent(packet);
    }

    public static void sendPacketNoEvent(int amount, Packet packet) {
        for (int i = 0; i < amount; ++i) {
            mc.getNetHandler().getNetworkManager().sendPacketNoEvent(packet);
        }
    }

    public static void sendPacketDelayed(final Packet packet, final long delay) {
        try {
            new Thread(){

                @Override
                public void run() {
                    try {
                        Thread.sleep(delay);
                        mc.getNetHandler().getNetworkManager().sendPacket(packet);
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

    public static void sendPacketNoEventDelayed(final Packet packet, final long delay) {
        try {
            new Thread(){

                @Override
                public void run() {
                    try {
                        Thread.sleep(delay);
                        mc.getNetHandler().getNetworkManager().sendPacketNoEvent(packet);
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

