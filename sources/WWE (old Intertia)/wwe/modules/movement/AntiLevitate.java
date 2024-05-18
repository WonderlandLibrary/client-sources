package wwe.modules.movement;

import net.minecraft.entity.projectile.*;
import wwe.modules.*;
import net.minecraft.util.text.*;
import wwe.utils.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.entity.player.*;
import wwe.modules.exploits.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import wwe.*;

public class AntiLevitate extends qMIe
{
    public static EntityShulkerBullet uLQg;
    public ModeValue mode;
    
    public AntiLevitate() {
        super("Anti Levitate", "Removes Levitation", 0, Category.MOVEMENT);
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Potion");
        list.add("Motion");
        list.add("Aura");
        this.mode = new ModeValue("Mode", this, "Current mode for the module", "Aura", list);
    }
    
    @oqrr
    public void MtRB(final omqH omqH) {
        AntiLevitate.uLQg = null;
        if (this.mode.OnGi().equalsIgnoreCase("Potion")) {
            this.owgW("Anti Levitate " + TextFormatting.DARK_GRAY + "Potion");
            if (Wrapper.mc.player.isPotionActive(Potion.getPotionById(25))) {
                Wrapper.mc.player.removeActivePotionEffect(Potion.getPotionById(25));
            }
        }
        if (this.mode.OnGi().equalsIgnoreCase("Motion")) {
            this.owgW("Anti Levitate " + TextFormatting.DARK_GRAY + "Motion");
            Wrapper.mc.player.motionY = 0.0;
        }
        if (this.mode.OnGi().equalsIgnoreCase("Aura")) {
            this.owgW("Anti Levitate " + TextFormatting.DARK_GRAY + "Aura");
            double n = 6.0;
            for (final Entity entity : Wrapper.mc.world.loadedEntityList) {
                if (entity instanceof EntityShulkerBullet) {
                    final EntityShulkerBullet ulQg = (EntityShulkerBullet)entity;
                    if (Wrapper.mc.player.getDistance((Entity)ulQg) < n) {
                        n = Wrapper.mc.player.getDistance((Entity)ulQg);
                        AntiLevitate.uLQg = ulQg;
                    }
                }
                if (AntiLevitate.\u2002\u200f\u2009) {
                    throw null;
                }
            }
            if (AntiLevitate.uLQg != null) {
                omqH.prFd = this.aADM((Entity)AntiLevitate.uLQg, Float.MAX_VALUE, Float.MAX_VALUE)[0];
                omqH.OjyD = this.aADM((Entity)AntiLevitate.uLQg, Float.MAX_VALUE, Float.MAX_VALUE)[1];
            }
        }
    }
    
    @oqrr
    public void uEyI(final ILBH ilbh) {
        if (this.mode.OnGi().equalsIgnoreCase("Aura") && AntiLevitate.uLQg != null && Wrapper.mc.player.getCooledAttackStrength(0.0f) == 1.0f) {
            Wrapper.mc.playerController.attackEntity((EntityPlayer)Wrapper.mc.player, (Entity)AntiLevitate.uLQg);
            OffHand.LInL();
        }
    }
    
    public float[] aADM(final Entity entity, final float n, final float n2) {
        final double n3 = entity.posX - Wrapper.mc.player.posX;
        final double n4 = entity.posZ - Wrapper.mc.player.posZ;
        double n5;
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            n5 = entityLivingBase.posY + entityLivingBase.getEyeHeight() - (Wrapper.mc.player.posY + Wrapper.mc.player.getEyeHeight());
            if (AntiLevitate.\u2002\u200f\u2009) {
                throw null;
            }
        }
        else {
            n5 = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0 - (Wrapper.mc.player.posY + Wrapper.mc.player.getEyeHeight());
        }
        final double n6 = MathHelper.sqrt(n3 * n3 + n4 * n4);
        final float n7 = (float)(Math.atan2(n4, n3) * 180.0 / 3.141592653589793) - 90.0f;
        final double n8 = n5;
        float n9;
        if (entity instanceof EntityPlayer) {
            n9 = 0.5f;
            if (AntiLevitate.\u2002\u200f\u2009) {
                throw null;
            }
        }
        else {
            n9 = 0.0f;
        }
        final float n10 = (float)(-(Math.atan2(n8 - n9, n6) * 180.0 / 3.141592653589793));
        Wrapper.mc.player.rotationYawHead = qxzm(Wrapper.mc.player.rotationYaw, n7, n);
        return new float[] { qxzm(Wrapper.mc.player.rotationYaw, n7, n), qxzm(Wrapper.mc.player.rotationPitch, n10, n2) };
    }
    
    public static float qxzm(final float n, final float n2, final float n3) {
        float wrapDegrees = MathHelper.wrapDegrees(n2 - n);
        if (wrapDegrees > n3) {
            wrapDegrees = n3;
        }
        if (wrapDegrees < -n3) {
            wrapDegrees = -n3;
        }
        return n + wrapDegrees;
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
        super.LPxx();
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
        super.qBmy();
    }
}
