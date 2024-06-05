package cn.Hanabi.modules.World;

import cn.Hanabi.modules.*;
import com.darkmagician6.eventapi.*;
import java.awt.*;
import cn.Hanabi.injection.interfaces.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import cn.Hanabi.events.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.boss.*;
import ClassSub.*;
import java.util.*;

public class HideAndSeek extends Mod
{
    public static List<EntityLivingBase> kids;
    public Class205 timer;
    
    
    public HideAndSeek() {
        super("HideAndSeek", Category.WORLD);
        this.timer = new Class205();
    }
    
    public void onEnable() {
        HideAndSeek.kids.clear();
    }
    
    public void onDisable() {
        HideAndSeek.kids.clear();
    }
    
    @EventTarget
    public void onChat(final EventChat eventChat) {
        if (eventChat.getMessage().contains("躲猫�?")) {
            this.timer.reset();
        }
    }
    
    @EventTarget
    public void onRender(final EventRender eventRender) {
        for (final EntityLivingBase entityLivingBase : HideAndSeek.kids) {
            if (entityLivingBase == null) {
                return;
            }
            final Color color = new Color(Class15.DARKRED.c);
            HideAndSeek.mc.getRenderManager();
            final double n = entityLivingBase.lastTickPosX + (entityLivingBase.posX - entityLivingBase.lastTickPosX) * Class211.getTimer().renderPartialTicks - ((IRenderManager)HideAndSeek.mc.getRenderManager()).getRenderPosX();
            HideAndSeek.mc.getRenderManager();
            final double n2 = entityLivingBase.lastTickPosY + (entityLivingBase.posY - entityLivingBase.lastTickPosY) * Class211.getTimer().renderPartialTicks - ((IRenderManager)HideAndSeek.mc.getRenderManager()).getRenderPosY();
            HideAndSeek.mc.getRenderManager();
            final double n3 = entityLivingBase.lastTickPosZ + (entityLivingBase.posZ - entityLivingBase.lastTickPosZ) * Class211.getTimer().renderPartialTicks - ((IRenderManager)HideAndSeek.mc.getRenderManager()).getRenderPosZ();
            if (entityLivingBase instanceof EntityPlayer) {
                final double n4 = entityLivingBase.isSneaking() ? 0.25 : 0.0;
                GL11.glPushMatrix();
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glTranslated(0.0, -0.25 * (Math.abs(entityLivingBase.rotationPitch) / 90.0f), 0.0);
                final double n5;
                final double n6;
                final double n7;
                GL11.glTranslated((n5 = n - 0.275) + 0.275, (n6 = n2 + (entityLivingBase.getEyeHeight() - 0.225 - n4)) + 0.275, (n7 = n3 - 0.275) + 0.275);
                GL11.glRotated((double)(-entityLivingBase.rotationYaw % 360.0f), 0.0, 1.0, 0.0);
                GL11.glTranslated(-(n5 + 0.275), -(n6 + 0.275), -(n7 + 0.275));
                GL11.glTranslated(n5 + 0.275, n6 + 0.275, n7 + 0.275);
                GL11.glRotated((double)entityLivingBase.rotationPitch, 1.0, 0.0, 0.0);
                GL11.glTranslated(-(n5 + 0.275), -(n6 + 0.275), -(n7 + 0.275));
                GL11.glDisable(3553);
                GL11.glEnable(2848);
                GL11.glDisable(2929);
                GL11.glDepthMask(false);
                GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 1.0f);
                GL11.glLineWidth(1.0f);
                Class246.drawOutlinedBoundingBox(new AxisAlignedBB(n5 - 0.0025, n6 - 0.0025, n7 - 0.0025, n5 + 0.55 + 0.0025, n6 + 0.55 + 0.0025, n7 + 0.55 + 0.0025));
                GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 0.5f);
                Class246.drawBoundingBox(new AxisAlignedBB(n5 - 0.0025, n6 - 0.0025, n7 - 0.0025, n5 + 0.55 + 0.0025, n6 + 0.55 + 0.0025, n7 + 0.55 + 0.0025));
                GL11.glDisable(2848);
                GL11.glEnable(3553);
                GL11.glEnable(2929);
                GL11.glDepthMask(true);
                GL11.glDisable(3042);
                GL11.glPopMatrix();
            }
            else {
                Class246.drawEntityESP(n, n2, n3, entityLivingBase.getEntityBoundingBox().maxX - entityLivingBase.getEntityBoundingBox().minX, entityLivingBase.getEntityBoundingBox().maxY - entityLivingBase.getEntityBoundingBox().minY + 0.25, 1.0f, 0.0f, 0.0f, 0.5f, 0.0f, 0.5f, 1.0f, 1.0f, 2.0f);
            }
        }
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate eventUpdate) {
        for (final Entity entity : HideAndSeek.mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityLivingBase && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArmorStand) && !(entity instanceof EntityWither) && !HideAndSeek.kids.contains(entity) && !entity.getName().contains("§c§l") && this.timer.isDelayComplete(5000L)) {
                final double n = entity.posY - (int)entity.posY;
                if (n <= 0.1 || (n + "").length() <= 8) {
                    continue;
                }
                HideAndSeek.kids.add((EntityLivingBase)entity);
                Class200.tellPlayer("§b[Hanabi]§a�?测到�?个异常动�?:" + entity.getName());
            }
        }
        for (final EntityLivingBase entityLivingBase : HideAndSeek.kids) {
            if (entityLivingBase.isDead || entityLivingBase.getHealth() < 0.0f) {
                HideAndSeek.kids.remove(entityLivingBase);
            }
        }
    }
    
    static {
        HideAndSeek.kids = new ArrayList<EntityLivingBase>();
    }
}
