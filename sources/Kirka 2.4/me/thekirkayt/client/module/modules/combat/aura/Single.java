/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.combat.aura;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.combat.Aura;
import me.thekirkayt.client.module.modules.combat.aura.AuraMode;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.RotationUtils;
import me.thekirkayt.utils.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MovingObjectPosition;

public class Single
extends AuraMode {
    private EntityLivingBase target;
    private Timer timer = new Timer();

    public Single(String name, boolean value, Module module) {
        super(name, value, module);
    }

    @Override
    public boolean enable() {
        this.target = null;
        return super.enable();
    }

    @EventTarget
    @Override
    public boolean onUpdate(UpdateEvent event) {
        if (super.onUpdate(event) && event.getState().equals((Object)Event.State.PRE)) {
            Aura auraModule = (Aura)this.getModule();
            if (ClientUtils.player().isEntityAlive()) {
                Entity hit;
                this.loadEntity();
                if (this.target != null) {
                    double x = this.target.posX - ClientUtils.player().posX;
                    double z = this.target.posZ - ClientUtils.player().posZ;
                    double h = ClientUtils.y() + (double)ClientUtils.player().getEyeHeight() - (this.target.posY + (double)this.target.getEyeHeight());
                    double h2 = Math.sqrt(x * x + z * z);
                    float yaw = (float)(Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
                    float pitch = (float)(Math.atan2(h, h2) * 180.0 / 3.141592653589793);
                    double xDist = RotationUtils.getDistanceBetweenAngles(yaw, ClientUtils.player().rotationYaw % 360.0f);
                    double yDist = RotationUtils.getDistanceBetweenAngles(pitch, ClientUtils.player().rotationPitch % 360.0f);
                    double d = Math.sqrt(xDist * xDist + yDist * yDist);
                }
                if (ClientUtils.mc().objectMouseOver.entityHit != null && auraModule.isEntityValid(hit = ClientUtils.mc().objectMouseOver.entityHit)) {
                    this.target = (EntityLivingBase)hit;
                }
            }
            if (this.target != null && Timer.delay((float)(1000.0 / auraModule.speed)) && (double)this.target.getDistanceToEntity(ClientUtils.player()) <= 3.0) {
                auraModule.attack(this.target, false);
                this.timer.reset();
            }
        }
        return true;
    }

    private void crit() {
        ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y() + 0.0624, ClientUtils.z(), true));
        ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y(), ClientUtils.z(), false));
        ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y() + 1.11E-4, ClientUtils.z(), false));
        ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y(), ClientUtils.z(), false));
    }

    private void loadEntity() {
        Aura auraModule = (Aura)this.getModule();
        EntityLivingBase loadEntity = null;
        double distance = 2.147483647E9;
        for (Entity e : ClientUtils.loadedEntityList()) {
            if (!(e instanceof EntityLivingBase) || !auraModule.isEntityValid(e)) continue;
            double x = e.posX - ClientUtils.player().posX;
            double z = e.posZ - ClientUtils.player().posZ;
            double h = ClientUtils.player().posY + (double)ClientUtils.player().getEyeHeight() - (e.posY + (double)e.getEyeHeight());
            double h2 = Math.sqrt(x * x + z * z);
            float yaw = (float)(Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
            float pitch = (float)(Math.atan2(h, h2) * 180.0 / 3.141592653589793);
            double xDist = RotationUtils.getDistanceBetweenAngles(yaw, ClientUtils.player().rotationYaw % 360.0f);
            double yDist = RotationUtils.getDistanceBetweenAngles(pitch, ClientUtils.player().rotationPitch % 360.0f);
            double angleDistance = Math.sqrt(xDist * xDist + yDist * yDist);
            if (!((double)ClientUtils.player().getDistanceToEntity(e) < distance)) continue;
            loadEntity = (EntityLivingBase)e;
            distance = ClientUtils.player().getDistanceToEntity(e);
        }
        this.target = loadEntity;
    }
}

