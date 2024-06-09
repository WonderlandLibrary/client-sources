/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.combat;

import java.util.Iterator;
import java.util.List;
import me.thekirkayt.client.friend.FriendManager;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.RotationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;

@Module.Mod(displayName="BowAimbot")
public class BowAimbot
extends Module {
    private Minecraft mc = Minecraft.getMinecraft();
    private float velocity;
    private int state;
    private EntityLivingBase target;
    @Option.Op(min=0.0, max=360.0, increment=0.25)
    public double angle;
    @Option.Op
    public boolean lockview;

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        if (event.getState() == Event.State.PRE) {
            if (Minecraft.thePlayer.rotationPitch > -80.0f && Minecraft.thePlayer.getCurrentEquippedItem() != null && Minecraft.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow) {
                this.loadEntity();
                int bowCurrentCharge = Minecraft.thePlayer.getItemInUseDuration();
                this.velocity = (float)bowCurrentCharge / 20.0f;
                this.velocity = (this.velocity * this.velocity + this.velocity * 2.0f) / 3.0f;
                if ((double)this.velocity >= 0.1) {
                    if (this.velocity > 1.0f) {
                        this.velocity = 1.0f;
                    }
                    double distanceToEnt = Minecraft.thePlayer.getDistanceToEntity(this.target);
                    double predictX = this.target.posX + (this.target.posX - this.target.lastTickPosX) * (distanceToEnt / (double)this.getVelocity() + this.getPingMoveTicks(this.target));
                    double predictZ = this.target.posZ + (this.target.posZ - this.target.lastTickPosZ) * (distanceToEnt / (double)this.getVelocity() + this.getPingMoveTicks(this.target));
                    double x = predictX - Minecraft.thePlayer.posX;
                    double z = predictZ - Minecraft.thePlayer.posZ;
                    double h = this.target.posY + (double)this.target.getEyeHeight() - (Minecraft.thePlayer.posY + 0.9 + (double)Minecraft.thePlayer.getEyeHeight());
                    double h1 = Math.sqrt(x * x + z * z);
                    Math.sqrt(h1 * h1 + h * h);
                    float yaw = (float)(Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
                    float pitch = -RotationUtils.getTrajAngleSolutionLow((float)h1, (float)h, this.velocity);
                    event.yaw = yaw;
                    event.pitch = pitch;
                    if (this.lockview) {
                        Minecraft.thePlayer.rotationYaw = yaw;
                        Minecraft.thePlayer.rotationPitch = pitch;
                    }
                }
            } else {
                this.target = null;
            }
        }
    }

    private void loadEntity() {
        EntityLivingBase loadEntity = null;
        double distance = 2.147483647E9;
        Iterator var5 = Minecraft.theWorld.loadedEntityList.iterator();
        do {
            double yDist;
            if (!var5.hasNext()) {
                this.target = loadEntity;
                return;
            }
            Entity e = (Entity)var5.next();
            if (!(e instanceof EntityLivingBase) || e == Minecraft.thePlayer || !e.isEntityAlive() || !((double)e.getDistanceToEntity(Minecraft.thePlayer) >= 1.5) || !(e instanceof EntityOtherPlayerMP) && !Minecraft.thePlayer.canEntityBeSeen(e) && FriendManager.isFriend(e.getName())) continue;
            double x = e.posX - Minecraft.thePlayer.posX;
            double z = e.posZ - Minecraft.thePlayer.posZ;
            double h = Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight() - (e.posY + (double)e.getEyeHeight());
            double h1 = Math.sqrt(x * x + z * z);
            float yaw = (float)(Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
            float pitch = (float)(Math.atan2(h, h1) * 180.0 / 3.141592653589793);
            double xDist = RotationUtils.getDistanceBetweenAngles(yaw, Minecraft.thePlayer.rotationYaw % 360.0f);
            double dist = Math.sqrt(xDist * xDist + (yDist = (double)RotationUtils.getDistanceBetweenAngles(pitch, Minecraft.thePlayer.rotationPitch % 360.0f)) * yDist);
            if (!(dist <= Double.valueOf(this.angle)) || !(dist < distance)) continue;
            loadEntity = (EntityLivingBase)e;
            distance = dist;
        } while (true);
    }

    private float getVelocity() {
        float vel = this.velocity;
        return vel * 2.0f;
    }

    private double getPingMoveTicks(EntityLivingBase e) {
        if (e instanceof EntityOtherPlayerMP) {
            EntityOtherPlayerMP entityOtherPlayerMP = (EntityOtherPlayerMP)e;
        }
        return 0.0;
    }
}

