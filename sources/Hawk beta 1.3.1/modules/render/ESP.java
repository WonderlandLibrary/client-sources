package eze.modules.render;

import eze.modules.*;
import eze.events.*;
import eze.events.listeners.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import java.awt.*;
import eze.util.*;
import java.util.*;

public class ESP extends Module
{
    public ESP() {
        super("ESP", 0, Category.RENDER);
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventRender3D) {
            for (final Object O : this.mc.theWorld.loadedEntityList) {
                if (O instanceof EntityLivingBase) {
                    final EntityLivingBase entity = (EntityLivingBase)O;
                    final AxisAlignedBB bb = new AxisAlignedBB(entity.boundingBox.minX - entity.posX + (entity.posX - this.mc.getRenderManager().renderPosX), entity.boundingBox.minY - entity.posY + (entity.posY - this.mc.getRenderManager().renderPosY), entity.boundingBox.minZ - entity.posZ + (entity.posZ - this.mc.getRenderManager().renderPosZ), entity.boundingBox.maxX - entity.posX + (entity.posX - this.mc.getRenderManager().renderPosX), entity.boundingBox.maxY - entity.posY + (entity.posY - this.mc.getRenderManager().renderPosY), entity.boundingBox.maxZ - entity.posZ + (entity.posZ - this.mc.getRenderManager().renderPosZ));
                    if (entity == this.mc.thePlayer || !(entity instanceof EntityPlayer)) {
                        continue;
                    }
                    final float hue = System.currentTimeMillis() % 4500L / 4500.0f;
                    final int rainbowColor = Color.HSBtoRGB(hue, 1.0f, 1.0f);
                    final Color color = new Color(rainbowColor);
                    final float red = (float)color.getRed();
                    final float green = (float)color.getGreen();
                    final float blue = (float)color.getBlue();
                    final float alpha = (float)color.getAlpha();
                    RenderUtil.drawBoundingBox(bb, red / 255.0f, green / 255.0f, blue / 255.0f, 0.75f);
                }
            }
        }
    }
}
