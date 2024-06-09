/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.other;

import java.util.ArrayList;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.TickEvent;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

@Module.Mod(displayName="AntiBot")
public class AntiBot
extends Module {
    public static Minecraft mc = Minecraft.getMinecraft();
    public static ArrayList<String> groundTimes = new ArrayList();
    public static int timeCap = 3;
    @Option.Op(name="Gwen")
    public static boolean gwen;
    @Option.Op(name="WatchDog")
    public static boolean doge;

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        try {
            for (Entity e2 : ClientUtils.loadedEntityList()) {
                if (e2 == Minecraft.thePlayer) continue;
                if (e2.isInvisible()) {
                    ClientUtils.world().removeEntity(e2);
                }
                if (!gwen || !(e2 instanceof EntityPlayer) || !e2.onGround || AntiBot.getTimeOnGround((EntityPlayer)e2) >= (double)timeCap) continue;
                AntiBot.TimeOnGroundAdd((EntityPlayer)e2, 1.0);
            }
        }
        catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    public static boolean isGwenBot(EntityLivingBase e2) {
        return e2 instanceof EntityPlayer && AntiBot.getTimeOnGround((EntityPlayer)e2) < (double)timeCap;
    }

    public static double getTimeOnGround(EntityPlayer p2) {
        String id2 = String.valueOf(p2.getEntityId());
        for (String s : groundTimes) {
            if (!s.contains(id2)) continue;
            String[] split = s.split(":");
            return Double.parseDouble(split[1]);
        }
        return 0.0;
    }

    public static void TimeOnGroundAdd(EntityPlayer p2, double amount) {
        String id2 = String.valueOf(p2.getEntityId());
        for (String s : groundTimes) {
            if (!s.contains(id2)) continue;
            String[] split = s.split(":");
            double foo = Double.parseDouble(split[1]) + amount;
            groundTimes.remove(s);
            groundTimes.add(String.valueOf(String.valueOf(id2)) + ":" + foo);
            return;
        }
        groundTimes.add(String.valueOf(String.valueOf(id2)) + ":" + amount);
    }

    @EventTarget
    private void onTick(TickEvent event) {
        this.updateTick();
    }

    private void updateTick() {
        if (gwen) {
            this.setSuffix("Gwen");
        } else if (doge) {
            this.setSuffix("WatchDog");
        }
    }
}

