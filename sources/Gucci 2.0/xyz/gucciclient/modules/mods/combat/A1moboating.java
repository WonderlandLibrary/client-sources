package xyz.gucciclient.modules.mods.combat;

import xyz.gucciclient.modules.*;
import xyz.gucciclient.values.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.gameevent.*;
import xyz.gucciclient.utils.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.*;
import java.util.*;

public class A1moboating extends Module
{
    private NumberValue yawspeed;
    private NumberValue pitchspeed;
    private NumberValue distance;
    private NumberValue fov;
    private BooleanValue ClickAim;
    private BooleanValue Invisibles;
    private BooleanValue increaseonstrafe;
    private Random rand;
    private EntityPlayer Entity;
    
    public A1moboating() {
        super(Modules.SmoothAim.name(), 0, Category.COMBAT);
        this.yawspeed = new NumberValue("Horizontal Speed", 4.0, 0.0, 5.0);
        this.pitchspeed = new NumberValue("Vertical Speed", 0.0, 0.0, 5.0);
        this.distance = new NumberValue("Range", 4.2, 3.0, 6.0);
        this.fov = new NumberValue("FOV", 80.0, 30.0, 360.0);
        this.ClickAim = new BooleanValue("ClickAim", true);
        this.Invisibles = new BooleanValue("Invisibles", false);
        this.increaseonstrafe = new BooleanValue("Increase on strafe", false);
        this.addValue(this.yawspeed);
        this.addValue(this.pitchspeed);
        this.addValue(this.distance);
        this.addValue(this.fov);
        this.addBoolean(this.ClickAim);
        this.addBoolean(this.Invisibles);
        this.addBoolean(this.increaseonstrafe);
    }
    
    @SubscribeEvent
    public void clientTick(final TickEvent event) {
        if (Wrapper.getPlayer() != null) {
            if (this.Entity == null || Wrapper.getPlayer().getDistanceToEntity((Entity)this.Entity) > this.distance.getValue()) {
                this.Entity = this.findEntity();
            }
            else if (this.IsValidEntity(this.Entity)) {
                float hspeed = (float)(this.yawspeed.getValue() * 3.0);
                float vspeed = (float)(this.pitchspeed.getValue() * 3.0);
                if (this.Entity != null && this.IsValidEntity(this.Entity)) {
                    hspeed *= (float)0.05;
                    vspeed *= (float)0.02;
                }
                if (this.increaseonstrafe.getState() && Wrapper.getPlayer().moveStrafing != 0.0f && Wrapper.getMinecraft().objectMouseOver == null && Wrapper.getMinecraft().objectMouseOver.entityHit == null) {
                    hspeed += 1.5;
                }
                if (Wrapper.getMinecraft().objectMouseOver != null && Wrapper.getMinecraft().objectMouseOver.entityHit != null) {
                    hspeed = 0.05f;
                    vspeed = 0.0f;
                }
                this.faceTarget((Entity)this.Entity, hspeed, vspeed);
            }
        }
    }
    
    private boolean IsValidEntity(final EntityPlayer entity) {
        return (!entity.isInvisible() || this.Invisibles.getState()) && Wrapper.getMinecraft().currentScreen == null && Wrapper.getPlayer().isEntityAlive() && entity.isEntityAlive() && Wrapper.getPlayer().getDistanceToEntity((Entity)entity) <= this.distance.getValue() && (Wrapper.getMinecraft().gameSettings.keyBindAttack.isKeyDown() || !this.ClickAim.getState()) && this.fov((Entity)entity) <= this.fov.getValue();
    }
    
    protected float getRotation(final float currentRotation, final float targetRotation, final float maxIncrement) {
        float deltaAngle = MathHelper.wrapAngleTo180_float(targetRotation - currentRotation);
        if (deltaAngle > maxIncrement) {
            deltaAngle = maxIncrement;
        }
        if (deltaAngle < -maxIncrement) {
            deltaAngle = -maxIncrement;
        }
        return currentRotation + deltaAngle / 2.0f;
    }
    
    private void faceTarget(final Entity target, final float yawspeed, final float pitchspeed) {
        final EntityPlayer player = (EntityPlayer)Wrapper.getPlayer();
        final float yaw = getAngles(target)[1];
        final float pitch = getAngles(target)[0];
        player.rotationYaw = this.getRotation(player.rotationYaw, yaw, yawspeed);
        player.rotationPitch = this.getRotation(player.rotationPitch, pitch, pitchspeed);
    }
    
    private EntityPlayer findEntity() {
        if (Wrapper.getWorld() != null) {
            for (final Object object : Wrapper.getWorld().playerEntities) {
                final EntityPlayer player = (EntityPlayer)object;
                if (player.getCommandSenderEntity().equals((Object)Wrapper.getPlayer().getCommandSenderEntity())) {
                    continue;
                }
                if (this.IsValidEntity(player)) {
                    return player;
                }
            }
        }
        return null;
    }
    
    private int fov(final Entity entity) {
        final float[] neededRotations = getAngles(entity);
        if (neededRotations != null) {
            final float distanceFromMouse = MathHelper.sqrt_float(Wrapper.getPlayer().rotationYaw - neededRotations[0] * Wrapper.getPlayer().rotationYaw - neededRotations[0] + Wrapper.getPlayer().rotationPitch - neededRotations[1] * Wrapper.getPlayer().rotationPitch - neededRotations[1]);
            return (int)distanceFromMouse;
        }
        return -1;
    }
    
    public static float[] getAngles(final Entity entity) {
        final double x = entity.posX - Wrapper.getPlayer().posX;
        final double z = entity.posZ - Wrapper.getPlayer().posZ;
        final double y = entity.posY - 0.2 + entity.getEyeHeight() - 0.4 - Wrapper.getPlayer().posY;
        final double helper = MathHelper.sqrt_double(x * x + z * z);
        float newYaw = (float)Math.toDegrees(-Math.atan(x / z));
        final float newPitch = (float)(-Math.toDegrees(Math.atan(y / helper)));
        if (z < 0.0 && x < 0.0) {
            newYaw = (float)(90.0 + Math.toDegrees(Math.atan(z / x)));
        }
        else if (z < 0.0 && x > 0.0) {
            newYaw = (float)(-90.0 + Math.toDegrees(Math.atan(z / x)));
        }
        return new float[] { newPitch, newYaw };
    }
}
