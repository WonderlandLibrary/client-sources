package cn.Hanabi.modules.Ghost;

import cn.Hanabi.value.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.events.*;
import net.minecraft.item.*;
import cn.Hanabi.injection.interfaces.*;
import net.minecraft.client.entity.*;
import com.darkmagician6.eventapi.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import cn.Hanabi.modules.Combat.*;
import cn.Hanabi.modules.Player.*;
import java.util.*;

public class AimAssist extends Mod
{
    public Value<Double> rotateSpeed;
    public Value<Double> range;
    public Value<Double> fov;
    public Value<Boolean> onlyPlayers;
    public Value<Boolean> clickAim;
    public Value<Boolean> weapon;
    
    
    public AimAssist() {
        super("AimAssist", Category.GHOST);
        this.rotateSpeed = new Value<Double>("AimAssist_RotateSpeed", 35.0, 1.0, 75.0, 1.0);
        this.range = new Value<Double>("AimAssist_Range", 3.8, 3.0, 10.0, 0.1);
        this.fov = new Value<Double>("AimAssist_Fov", 360.0, 1.0, 360.0, 1.0);
        this.onlyPlayers = new Value<Boolean>("AimAssist_PlayersOnly", true);
        this.clickAim = new Value<Boolean>("AimAssist_ClickAim", true);
        this.weapon = new Value<Boolean>("AimAssist_Weapon", false);
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate eventUpdate) {
        if (AimAssist.mc.theWorld != null) {
            if (this.weapon.getValueState()) {
                if (AimAssist.mc.thePlayer.getCurrentEquippedItem() == null) {
                    return;
                }
                if (!(AimAssist.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword)) {
                    return;
                }
            }
            if (this.clickAim.getValueState() && !((IKeyBinding)AimAssist.mc.gameSettings.keyBindAttack).getPress()) {
                return;
            }
            final EntityLivingBase nearestTarget = this.getNearestTarget();
            if (nearestTarget != null && (this.fov(nearestTarget) > 1.0 || this.fov(nearestTarget) < -1.0)) {
                final boolean b = this.fov(nearestTarget) > 0.0;
                final EntityPlayerSP thePlayer = AimAssist.mc.thePlayer;
                thePlayer.rotationYaw += (float)(b ? (-(Math.abs(this.fov(nearestTarget)) / (int)(Object)this.rotateSpeed.getValueState())) : (Math.abs(this.fov(nearestTarget)) / (int)(Object)this.rotateSpeed.getValueState()));
            }
        }
    }
    
    public double fov(final EntityLivingBase entityLivingBase) {
        return ((AimAssist.mc.thePlayer.rotationYaw - this.faceTarget(entityLivingBase)) % 360.0 + 540.0) % 360.0 - 180.0;
    }
    
    public EntityLivingBase getNearestTarget() {
        Object o = null;
        for (final EntityPlayer next : AimAssist.mc.theWorld.loadedEntityList) {
            if (next instanceof EntityPlayer && this.onlyPlayers.getValueState()) {
                final EntityPlayer entityPlayer = next;
                if (entityPlayer == AimAssist.mc.thePlayer || !entityPlayer.isEntityAlive() || entityPlayer.isInvisible() || AimAssist.mc.thePlayer.getDistanceToEntity((Entity)entityPlayer) >= this.range.getValueState() || AntiBot.isBot((Entity)entityPlayer) || Teams.isOnSameTeam((Entity)entityPlayer) || !AimAssist.mc.thePlayer.canEntityBeSeen((Entity)entityPlayer) || !this.inFov((EntityLivingBase)entityPlayer, (int)(Object)this.fov.getValueState())) {
                    continue;
                }
                o = entityPlayer;
            }
            else {
                if (!(next instanceof EntityLivingBase) || this.onlyPlayers.getValueState()) {
                    continue;
                }
                final EntityLivingBase entityLivingBase = (EntityLivingBase)next;
                if (entityLivingBase == AimAssist.mc.thePlayer || !entityLivingBase.isEntityAlive() || entityLivingBase.isInvisible() || AimAssist.mc.thePlayer.getDistanceToEntity((Entity)entityLivingBase) >= this.range.getValueState() || AntiBot.isBot((Entity)entityLivingBase) || Teams.isOnSameTeam((Entity)entityLivingBase) || !AimAssist.mc.thePlayer.canEntityBeSeen((Entity)entityLivingBase) || !this.inFov(entityLivingBase, (int)(Object)this.fov.getValueState())) {
                    continue;
                }
                o = entityLivingBase;
            }
        }
        return (EntityLivingBase)o;
    }
    
    public boolean inFov(final EntityLivingBase entityLivingBase, float n) {
        n *= 0.5;
        final double n2 = ((AimAssist.mc.thePlayer.rotationYaw - this.faceTarget(entityLivingBase)) % 360.0 + 540.0) % 360.0 - 180.0;
        return (n2 > 0.0 && n2 < n) || (-n < n2 && n2 < 0.0);
    }
    
    public float faceTarget(final EntityLivingBase entityLivingBase) {
        final double n = entityLivingBase.posX - AimAssist.mc.thePlayer.posX;
        final double n2 = entityLivingBase.posY - AimAssist.mc.thePlayer.posY;
        final double n3 = entityLivingBase.posZ - AimAssist.mc.thePlayer.posZ;
        final double n4 = -(Math.atan2(n, n3) * 57.29577951308232);
        final double n5 = -(Math.asin(n2 / Math.sqrt(n * n + n2 * n2 + n3 * n3)) * 57.29577951308232);
        return (float)n4;
    }
}
