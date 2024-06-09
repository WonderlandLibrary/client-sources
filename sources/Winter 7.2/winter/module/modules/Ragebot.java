/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IChatComponent;
import winter.event.EventListener;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.utils.friend.FriendUtil;

public class Ragebot
extends Module {
    public static Entity target;
    private float velocity;
    public static Minecraft mc;

    static {
        mc = Minecraft.getMinecraft();
    }

    public Ragebot() {
        super("Ragebot", Module.Category.Other, 16720731);
    }

    @Override
    public void onDisable() {
        Ragebot.mc.gameSettings.keyBindUseItem.pressed = false;
        target = null;
    }

    @Override
    public void onEnable() {
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
        if (event.isPre()) {
            target = this.findEnemy();
            if (target != null) {
                Ragebot.mc.thePlayer.motionX *= 0.0;
                Ragebot.mc.thePlayer.motionZ *= 0.0;
                int xd = 240;
                this.velocity = (float)xd / 20.0f;
                this.velocity = (this.velocity * this.velocity + this.velocity * 2.0f) / 3.0f;
                if ((double)this.velocity < 0.1) {
                    return;
                }
                if (this.velocity > 1.0f) {
                    this.velocity = 1.0f;
                }
                double distanceToEnt = Ragebot.mc.thePlayer.getDistanceToEntity(target);
                double predictX = Ragebot.target.posX + (Ragebot.target.posX - Ragebot.target.lastTickPosX) * (distanceToEnt / (double)this.getVelocity() + this.getPingMoveTicks(target));
                double predictZ = Ragebot.target.posZ + (Ragebot.target.posZ - Ragebot.target.lastTickPosZ) * (distanceToEnt / (double)this.getVelocity() + this.getPingMoveTicks(target));
                double x2 = predictX - Ragebot.mc.thePlayer.posX;
                double z2 = predictZ - Ragebot.mc.thePlayer.posZ;
                double h2 = Ragebot.target.posY + (double)target.getEyeHeight() - (Ragebot.mc.thePlayer.posY + 0.9 + (double)Ragebot.mc.thePlayer.getEyeHeight());
                double h22 = Math.sqrt(x2 * x2 + z2 * z2);
                double h3 = Math.sqrt(h22 * h22 + h2 * h2);
                float yaw = (float)(Math.atan2(z2, x2) * 180.0 / 3.141592653589793) - 90.0f;
                float pitch = - Ragebot.getTrajAngleSolutionLow((float)h22, (float)h2, this.velocity);
                event.setYaw(yaw);
                event.setPitch(Ragebot.getPitch(target, Ragebot.mc.thePlayer.posX, Ragebot.mc.thePlayer.posY, Ragebot.mc.thePlayer.posZ));
            }
        } else {
            Ragebot.mc.gameSettings.keyBindUseItem.pressed = target != null;
        }
    }

    public static float getTrajAngleSolutionLow(float d3, float d1, float velocity) {
        float g2 = 0.006f;
        float sqrt = velocity * velocity * velocity * velocity - 0.006f * (0.006f * (d3 * d3) + 2.0f * d1 * (velocity * velocity));
        return (float)Math.toDegrees(Math.atan(((double)(velocity * velocity) - Math.sqrt(sqrt)) / (double)(0.006f * d3)));
    }

    private float getVelocity() {
        float vel = this.velocity;
        return vel * 2.0f;
    }

    private double getPingMoveTicks(Entity e2) {
        if (e2 instanceof EntityOtherPlayerMP) {
            EntityOtherPlayerMP player = (EntityOtherPlayerMP)e2;
            return this.getPlayerPing(player.getName()) / 20;
        }
        return 0.0;
    }

    public int getPlayerPing(String name) {
        EntityPlayer player = mc.theWorld.getPlayerEntityByName(name);
        if(player instanceof EntityOtherPlayerMP);
        
        return 0;
    }

    public Entity findEnemy() {
        double r2 = 100.0;
        Entity e2 = null;
        for (Object o : Ragebot.mc.theWorld.loadedEntityList) {
            Entity ent = (Entity)o;
            if (ent == Ragebot.mc.thePlayer || (double)Ragebot.mc.thePlayer.getDistanceToEntity(ent) > r2 || !(ent instanceof EntityPlayer) || !ent.isEntityAlive() || FriendUtil.isAFriend(ent.getName()) || ent.isInvisible() || this.isTeamed(ent) || !Ragebot.mc.thePlayer.canEntityBeSeen(ent)) continue;
            e2 = ent;
        }
        return e2;
    }

    public static float getPitch(Entity ent, double eX, double eY, double eZ) {
        double x2 = ent.posX - eX;
        double y2 = ent.posY - eY;
        double z2 = ent.posZ - eZ;
        double pitch = Math.asin(y2 /= (double)Ragebot.mc.thePlayer.getDistanceToEntity(ent)) * 57.29577951308232;
        pitch = - pitch;
        return (float)pitch;
    }

    public static float getYaw(Entity ent, double eX, double eY, double eZ) {
        double x2 = ent.posX - eX;
        double y2 = ent.posY - eY;
        double z2 = ent.posZ - eZ;
        double yaw = Math.atan2(x2, z2) * 57.29577951308232;
        yaw = - yaw;
        return (float)yaw;
    }

    public boolean isTeamed(Entity e2) {
        boolean team = false;
        if (Ragebot.mc.thePlayer.getDisplayName().getFormattedText().contains("\u00a7b") && e2.getDisplayName().getFormattedText().contains("\u00a7b")) {
            team = true;
        }
        if (Ragebot.mc.thePlayer.getDisplayName().getFormattedText().contains("\u00a7c") && e2.getDisplayName().getFormattedText().contains("\u00a7c")) {
            team = true;
        }
        return team;
    }
}

