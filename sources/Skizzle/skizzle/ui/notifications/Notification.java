/*
 * Decompiled with CFR 0.150.
 */
package skizzle.ui.notifications;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import skizzle.Client;
import skizzle.util.Timer;

public class Notification {
    public String name;
    public int maxLength;
    public notificationType type;
    public Timer timer3;
    public float time3;
    public Timer timer = new Timer();
    public String description;
    public int anim;
    public float time;
    public float startTimeTime;
    public Timer timer2 = new Timer();
    public float animTop;
    public float time2;
    public long startTime;
    public boolean animIn;

    public static {
        throw throwable;
    }

    public Notification(String Nigga, String Nigga2, float Nigga3, float Nigga4, notificationType Nigga5) {
        int Nigga6;
        Notification Nigga7;
        Nigga7.timer3 = new Timer();
        Nigga7.startTime = System.currentTimeMillis();
        Nigga7.name = Nigga;
        Nigga7.description = Nigga2;
        Nigga7.time = Nigga3 * Float.intBitsToFloat(1.03699456E9f ^ 0x7F07481E);
        Nigga7.time2 = Nigga3 * Float.intBitsToFloat(1.015248E9f ^ 0x7E4B748B);
        Nigga7.time3 = Nigga3 * Float.intBitsToFloat(1.03780742E9f ^ 0x7F13AF4F);
        Nigga7.anim = 45;
        Nigga7.startTimeTime = Nigga3 * Float.intBitsToFloat(9.5279635E8f ^ 0x7CB084BF);
        Nigga7.animTop = Float.intBitsToFloat(-1.11945741E9f ^ 0x7F766FA1);
        Nigga7.type = Nigga5;
        if (Nigga7.type.equals((Object)notificationType.TOGGLE)) {
            Nigga7.anim = 100;
        }
        if (Nigga4 == Float.intBitsToFloat(1.10600998E9f ^ 0x7EF5C63D)) {
            for (Nigga6 = 0; Nigga6 < 10; ++Nigga6) {
                Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation(Qprot0.0("\u3cdd\u71ce\u07a7\ua7e8\u1e6b\ub942\u8c21")), Float.intBitsToFloat(1.08671539E9f ^ 0x7F45F5CC)));
            }
        } else if (Nigga4 == Float.intBitsToFloat(1.07474726E9f ^ 0x7F0F5762)) {
            for (Nigga6 = 0; Nigga6 < 10; ++Nigga6) {
                Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation(Qprot0.0("\u3cdd\u71ce\u07a7\ua7e8\u1e6b\ub942\u8c29\u5094")), Float.intBitsToFloat(1.10657792E9f ^ 0x7E7509EF)));
            }
        }
        if (Nigga5.equals((Object)notificationType.WARNING)) {
            for (Nigga6 = 0; Nigga6 < 10; ++Nigga6) {
            }
        }
        Client.notifications.notifs.add(Nigga7);
    }

    public static enum notificationType {
        TOGGLE,
        WARNING,
        PROFILE,
        INFO;


        public notificationType() {
            notificationType Nigga;
        }
    }
}

