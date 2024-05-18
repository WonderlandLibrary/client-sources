package de.labystudio.utils;

import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.biome.BiomeGenBase;

public class ModGui
{
    public static int mainList = 0;
    public static int offList = 2;
    public static Entity pointedEntity = null;
    public static int frames = 0;
    public static int fps = 0;
    public static long frameTimer = 0L;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    static int smoothFPS = 0;

    public static String translateTimer(int time)
    {
        String s = time / 60 < 10 ? "0" + time / 60 : Integer.toString(time / 60);
        String s1 = time % 60 < 10 ? "0" + time % 60 : Integer.toString(time % 60);
        return s + ":" + s1;
    }

    public static String shortString(String s, int i)
    {
        if (s.length() > i)
        {
            s = s.substring(0, i);
        }

        return s;
    }

    public static void smoothFPS()
    {
        if (ConfigManager.settings.smoothFPS)
        {
            try
            {
                int i = getRealFPS();

                if (smoothFPS == 0)
                {
                    smoothFPS = i;
                }

                if (i > smoothFPS)
                {
                    ++smoothFPS;
                }

                if (i < smoothFPS)
                {
                    --smoothFPS;
                }
            }
            catch (Exception var1)
            {
                smoothFPS = 0;
            }
        }
    }

    public static int getFPS()
    {
        return ConfigManager.settings.smoothFPS ? smoothFPS : getRealFPS();
    }

    public static String getF()
    {
        if (!LabyMod.getInstance().isInGame())
        {
            return "0.0 ";
        }
        else
        {
            double d0 = (double)MathHelper.wrapAngleTo180_float(Minecraft.getMinecraft().thePlayer.rotationYaw);

            if (d0 <= 0.0D)
            {
                d0 += 360.0D;
            }

            d0 = d0 / 8.0D;
            d0 = d0 / 11.0D;
            String s = "" + String.valueOf(d0).charAt(0) + String.valueOf(d0).charAt(1) + String.valueOf(d0).charAt(2);

            if (s.equals("4.0") || s.startsWith("9"))
            {
                s = "0.0";
            }

            return s + " ";
        }
    }

    public static String getD()
    {
        String s = getXZD();
        return s.contains("Z-") ? "North " : (s.contains("X+") ? "East " : (s.contains("Z+") ? "South " : (s.contains("X-") ? "West " : "")));
    }

    public static String getDesignD()
    {
        String s = getXZD();
        return s.contains("X-") && s.contains("Z-") ? "WN" : (s.contains("Z-") && s.contains("X+") ? "NE" : (s.contains("X+") && s.contains("Z+") ? "ES" : (s.contains("Z+") && s.contains("X-") ? "SW" : (s.contains("Z-") ? "North " : (s.contains("X+") ? "East" : (s.contains("Z+") ? "South" : (s.contains("X-") ? "West" : "")))))));
    }

    public static String getXZD()
    {
        double d0 = Double.parseDouble(getF());
        String s = "[";
        String s1 = ", ";
        String s2 = "]";
        String s3 = "X+";
        String s4 = "X-";
        String s5 = "Z+";
        String s6 = "Z-";

        if (ConfigManager.settings.layout == 1)
        {
            s = "";
            s1 = " ";
            s2 = "";
        }

        if (ConfigManager.settings.layout == 2)
        {
            s = "[";
            s1 = ", ";
            s2 = "]";
        }

        if (ConfigManager.settings.layout == 3)
        {
            s = "<";
            s1 = ", ";
            s2 = ">";
        }

        if (ConfigManager.settings.layout == 4)
        {
            s = "(";
            s1 = ", ";
            s2 = ")";
        }

        s = Color.c(2) + s + Color.c(1);
        s1 = Color.c(2) + s1 + Color.c(1);
        s2 = Color.c(2) + s2;
        return d0 >= 0.0D && d0 < 0.3D ? s + s5 + s2 : (d0 > 0.2D && d0 < 0.8D ? s + s4 + s1 + s5 + s2 : (d0 > 0.7D && d0 < 1.4D ? s + s4 + s2 : (d0 > 1.3D && d0 < 1.8D ? s + s4 + s1 + s6 + s2 : (d0 > 1.7D && d0 < 2.4D ? s + s6 + s2 : (d0 > 2.3D && d0 < 2.8D ? s + s3 + s1 + s6 + s2 : (d0 > 2.7D && d0 < 3.4D ? s + s3 + s2 : (d0 > 3.3D && d0 < 3.8D ? s + s3 + s1 + s5 + s2 : (d0 > 3.7D && d0 <= 4.0D ? s + s5 + s2 : ""))))))));
    }

    public static String getDesignXZD(boolean x)
    {
        double d0 = Double.parseDouble(getF());
        String s = "+";
        String s1 = "-";
        return d0 >= 0.0D && d0 < 0.3D ? (x ? "" : s) : (d0 > 0.2D && d0 < 0.8D ? (x ? s1 : s) : (d0 > 0.7D && d0 < 1.4D ? (x ? s1 : "") : (d0 > 1.3D && d0 < 1.8D ? (x ? s1 : s1) : (d0 > 1.7D && d0 < 2.4D ? (x ? "" : s1) : (d0 > 2.3D && d0 < 2.8D ? (x ? s : s1) : (d0 > 2.7D && d0 < 3.4D ? (x ? s : "") : (d0 > 3.3D && d0 < 3.8D ? (x ? s : s) : (d0 > 3.7D && d0 <= 4.0D ? (x ? "" : s) : ""))))))));
    }

    public static int getRealFPS()
    {
        return fps;
    }

    public static String getBiom()
    {
        if (Minecraft.getMinecraft().theWorld == null)
        {
            return "?";
        }
        else if (Minecraft.getMinecraft().thePlayer == null)
        {
            return "?";
        }
        else if (Minecraft.getMinecraft().thePlayer.getPosition() == null)
        {
            return "?";
        }
        else
        {
            BiomeGenBase biomegenbase = Minecraft.getMinecraft().theWorld.getBiomeGenForCoords(Minecraft.getMinecraft().thePlayer.getPosition());
            return biomegenbase == null ? "?" : biomegenbase.biomeName;
        }
    }

    public static String getX()
    {
        return !LabyMod.getInstance().isInGame() ? "?" : (ConfigManager.settings.truncateCoords == 0 ? "" + Minecraft.getMinecraft().thePlayer.getPosition().getX() : truncateCoords(Minecraft.getMinecraft().thePlayer.posX));
    }

    public static String getY()
    {
        return !LabyMod.getInstance().isInGame() ? "?" : (ConfigManager.settings.truncateCoords == 0 ? "" + Minecraft.getMinecraft().thePlayer.getPosition().getY() : truncateCoords(Minecraft.getMinecraft().thePlayer.posY));
    }

    public static String getZ()
    {
        return !LabyMod.getInstance().isInGame() ? "?" : (ConfigManager.settings.truncateCoords == 0 ? "" + Minecraft.getMinecraft().thePlayer.getPosition().getZ() : truncateCoords(Minecraft.getMinecraft().thePlayer.posZ));
    }

    public static String truncateCoords(double i)
    {
        return ConfigManager.settings.truncateCoords != 0 ? truncateDecimal(i, ConfigManager.settings.truncateCoords) + "" : (int)i + "";
    }

    private static BigDecimal truncateDecimal(double x, int numberofDecimals)
    {
        try
        {
            return x > 0.0D ? (new BigDecimal(String.valueOf(x))).setScale(numberofDecimals, 3) : (new BigDecimal(String.valueOf(x))).setScale(numberofDecimals, 2);
        }
        catch (Exception var4)
        {
            return new BigDecimal(0);
        }
    }

    public static double truncateTo(double number, int amount)
    {
        int i = (int)(number * Math.pow(10.0D, (double)amount));
        double d0 = (double)i / Math.pow(10.0D, (double)amount);
        return d0;
    }

    public static String createLabel(String get, String set)
    {
        return ConfigManager.settings.layout == 0 ? "" : (ConfigManager.settings.layout == 1 ? Color.c(1) + get + Color.c(2) + ": " + Color.c(3) + set : (ConfigManager.settings.layout == 2 ? Color.c(2) + "[" + Color.c(1) + get + Color.c(2) + "] " + Color.c(3) + set : (ConfigManager.settings.layout == 3 ? Color.c(1) + get + Color.c(2) + "> " + Color.c(3) + set : (ConfigManager.settings.layout == 4 ? Color.c(2) + "(" + Color.c(1) + get + Color.c(2) + ") " + Color.c(3) + set : "Error"))));
    }

    public static void addMainLabel(String prefix, String text, int y)
    {
        if (isSwitch())
        {
            LabyMod.getInstance().draw.addRightLabel(prefix, text, y);
        }
        else
        {
            LabyMod.getInstance().draw.addLabel(prefix, text, y);
        }

        mainListNext();
    }

    public static void addOffLabel(String prefix, String text, int y)
    {
        if (isSwitch())
        {
            LabyMod.getInstance().draw.addLabel(prefix, text, y);
        }
        else
        {
            LabyMod.getInstance().draw.addRightLabel(prefix, text, y);
        }

        offListNext();
    }

    public static void addBoxLabel(String prefix, String text, int y)
    {
        if (isSwitch())
        {
            LabyMod.getInstance().draw.drawCenteredString(prefix + text, LabyMod.getInstance().draw.getWidth() - 60, y);
        }
        else
        {
            LabyMod.getInstance().draw.drawCenteredString(prefix + text, 60, y);
        }

        mainListNext();
    }

    public static void addDoubleBoxLabel(String prefix, String text, int y)
    {
        if (isSwitch())
        {
            LabyMod.getInstance().draw.drawCenteredString(prefix, LabyMod.getInstance().draw.getWidth() - 60 - 4, y);
            mainListNext();
            LabyMod.getInstance().draw.drawCenteredString(text, LabyMod.getInstance().draw.getWidth() - 60 - 4, y + 10);
            mainListNext();
        }
        else
        {
            LabyMod.getInstance().draw.drawCenteredString(prefix, 64, y);
            mainListNext();
            LabyMod.getInstance().draw.drawCenteredString(text, 64, y + 10);
            mainListNext();
        }
    }

    public static void reset()
    {
        offList = 0;
        mainList = 0;
    }

    public static void offListNext()
    {
        offList += 10;
    }

    public static void offListNext(int i)
    {
        offList += i;
    }

    public static void mainListNext()
    {
        mainList += 10;
    }

    public static void mainListNext(int i)
    {
        mainList += i;
    }

    public static boolean isSwitch()
    {
        return ConfigManager.settings.guiPositionRight;
    }

    public static void drawEntityOnScreen(double x, double y, double size, EntityLivingBase entity)
    {
        if (entity != null)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)x, (float)y, 50.0F);
            GlStateManager.scale((float)(-size) - 25.0F, (float)size + 25.0F, (float)size);
            GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
            float f = entity.renderYawOffset;
            float f1 = entity.rotationYaw;
            float f2 = entity.rotationPitch;
            float f3 = entity.prevRotationYawHead;
            float f4 = entity.rotationYawHead;
            GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, 0.0F, 0.0F);
            RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
            rendermanager.setPlayerViewY(180.0F);
            rendermanager.setRenderShadow(false);
            rendermanager.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
            rendermanager.setRenderShadow(true);
            entity.renderYawOffset = f;
            entity.rotationYaw = f1;
            entity.rotationPitch = f2;
            entity.prevRotationYawHead = f3;
            entity.rotationYawHead = f4;
            GlStateManager.popMatrix();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.disableTexture2D();
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        }
    }

    public static void getMouseOver(float partialTicks)
    {
        Entity entity = LabyMod.getInstance().mc.getRenderViewEntity();

        if (entity != null && LabyMod.getInstance().mc.theWorld != null)
        {
            LabyMod.getInstance().mc.pointedEntity = null;
            double d0 = 30.0D;
            LabyMod.getInstance().mc.objectMouseOver = entity.rayTrace(d0, partialTicks);
            double d1 = d0;
            Vec3 vec3 = entity.getLook(partialTicks);

            if (LabyMod.getInstance().mc.objectMouseOver != null)
            {
                d1 = LabyMod.getInstance().mc.objectMouseOver.hitVec.distanceTo(vec3);
            }

            Vec3 vec31 = entity.getLook(partialTicks);
            Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
            pointedEntity = null;
            Vec3 vec33 = null;
            float f = 1.0F;
            List list = LabyMod.getInstance().mc.theWorld.getEntitiesWithinAABBExcludingEntity(entity, entity.getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand((double)f, (double)f, (double)f));
            double d2 = d1;

            for (int i = 0; i < list.size(); ++i)
            {
                Entity entity1 = (Entity)list.get(i);

                if (entity1.canBeCollidedWith())
                {
                    float f1 = entity1.getCollisionBorderSize();
                    AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand((double)f1, (double)f1, (double)f1);
                    MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

                    if (axisalignedbb.isVecInside(vec3))
                    {
                        if (0.0D < d2 || d2 == 0.0D)
                        {
                            pointedEntity = entity1;
                            vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                            d2 = 0.0D;
                        }
                    }
                    else if (movingobjectposition != null)
                    {
                        double d3 = vec3.distanceTo(movingobjectposition.hitVec);

                        if (d3 < d2 || d2 == 0.0D)
                        {
                            if (entity1 == entity.ridingEntity)
                            {
                                if (d2 == 0.0D)
                                {
                                    pointedEntity = entity1;
                                    vec33 = movingobjectposition.hitVec;
                                }
                            }
                            else
                            {
                                pointedEntity = entity1;
                                vec33 = movingobjectposition.hitVec;
                                d2 = d3;
                            }
                        }
                    }
                }
            }

            if (pointedEntity != null && (d2 < d1 || LabyMod.getInstance().mc.objectMouseOver == null))
            {
                LabyMod.getInstance().mc.objectMouseOver = new MovingObjectPosition(pointedEntity, vec33);

                if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame)
                {
                    LabyMod.getInstance().mc.pointedEntity = pointedEntity;
                }
            }
        }
    }

    public static String getDate()
    {
        return dateFormat.format(new Date());
    }
}
