package eze.modules.combat;

import eze.modules.*;
import eze.util.*;
import eze.events.*;
import eze.events.listeners.*;
import java.util.function.*;
import java.util.stream.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.entity.player.*;

public class Aimbot extends Module
{
    public float oldSens;
    public Timer timer;
    
    public Aimbot() {
        super("Aimbot", 0, Category.COMBAT);
        this.timer = new Timer();
    }
    
    @Override
    public void onEnable() {
        this.oldSens = this.mc.gameSettings.mouseSensitivity;
    }
    
    @Override
    public void onDisable() {
        this.mc.gameSettings.mouseSensitivity = this.oldSens;
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventMotion && e.isPre()) {
            final EventMotion event = (EventMotion)e;
            List<EntityLivingBase> targets = (List<EntityLivingBase>)this.mc.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
            targets = targets.stream().filter(entity -> entity.getDistanceToEntity(this.mc.thePlayer) < 5.0f && entity != this.mc.thePlayer && !entity.isDead && !entity.isInvisible()).collect((Collector<? super Object, ?, List<EntityLivingBase>>)Collectors.toList());
            targets.sort(Comparator.comparingDouble(entity -> entity.getDistanceToEntity(this.mc.thePlayer)));
            targets = targets.stream().filter(EntityPlayer.class::isInstance).collect((Collector<? super Object, ?, List<EntityLivingBase>>)Collectors.toList());
            if (!targets.isEmpty()) {
                final EntityLivingBase target = targets.get(0);
                this.mc.thePlayer.rotationYaw = this.getRotations(target)[0];
                this.mc.thePlayer.rotationPitch = this.getRotations(target)[1];
                this.timer.hasTimeElapsed(100L, true);
            }
        }
    }
    
    public float[] getRotations(final Entity e) {
        final double deltaX = e.posX + (e.posX - e.lastTickPosX) - this.mc.thePlayer.posX;
        final double deltaY = e.posY - 3.5 + e.getEyeHeight() - this.mc.thePlayer.posY + this.mc.thePlayer.getEyeHeight();
        final double deltaZ = e.posZ + (e.posZ - e.lastTickPosZ) - this.mc.thePlayer.posZ;
        final double distance = Math.sqrt(Math.pow(deltaX, 2.0) + Math.pow(deltaZ, 2.0));
        float yaw = (float)Math.toDegrees(-Math.atan(deltaX / deltaZ));
        final float pitch = (float)(-Math.toDegrees(Math.atan(deltaY / distance)));
        if (deltaX < 0.0 && deltaZ < 0.0) {
            yaw = (float)(90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        }
        else if (deltaX > 0.0 && deltaZ < 0.0) {
            yaw = (float)(-90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
        }
        return new float[] { yaw, pitch };
    }
}
