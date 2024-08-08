package me.xatzdevelopments.xatz.client.Unused.superherofx;

import java.awt.Color;
import java.lang.reflect.Method;

import javax.vecmath.Vector2d;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import me.xatzdevelopments.xatz.client.Unused.superherofx.utils.GlyphPageFontRenderer;
import me.xatzdevelopments.xatz.client.Unused.superherofx.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.Timer;
import net.minecraft.world.World;

public class TextFX extends FX {
	
	private String text;
    private int color;
    final GlyphPageFontRenderer frn = GlyphPageFontRenderer.create("Verdana", 18, false, false, false);
    public TextFX(final EntityLivingBase idk, final String text) {
        super(idk);
        this.text = text;
        switch (this.random.nextInt(5)) {
            case 1: {
                this.text = "BOOM";
                break;
            }
            case 2: {
                this.text = "OOF";
                break;
            }
            case 3: {
                this.text = "BAM";
                break;
            }
            case 4: {
                this.text = "SMASH";
                break;
            }
            case 5: {
                this.text = "POW";
                break;
            }
            default: {
                this.text = "OWNED";
                break;
            }
        }
        final float hue = this.random.nextFloat();
        final float saturation = (this.random.nextInt(2000) + 8000) / 10000.0f;
        final float luminance = 0.9f;
        final Color col = Color.getHSBColor(hue, saturation, 0.9f);
        this.color = col.getRGB();
    }
	
		
	public void onEnable() {
		
	}
	
	public void onDisable() {
		
	}
	
	@Override
	public void onRender() {
		
			 	final WorldClient world = Minecraft.getMinecraft().theWorld;
		        final EntitySnowball tempEntity = new EntitySnowball((World)world);
		        tempEntity.posX = this.x;
		        tempEntity.posY = this.y;
		        tempEntity.posZ = this.z;
		        if (!Minecraft.getMinecraft().thePlayer.canEntityBeSeen((Entity)tempEntity)) {
		            return;
		        }
		        final double rX = this.x - Minecraft.getMinecraft().getRenderManager().viewerPosX;
		        final double rY = this.y - Minecraft.getMinecraft().getRenderManager().viewerPosY;
		        final double rZ = this.z - Minecraft.getMinecraft().getRenderManager().viewerPosZ;
		        final Minecraft minecraft = Minecraft.getMinecraft();
		        try {
		            final Timer timer = new net.minecraft.util.Timer(maxTicksAlive);
		            final Method method = EntityRenderer.class.getDeclaredMethod("setupCameraTransform", Float.TYPE, Integer.TYPE);
		            method.setAccessible(true);
		            method.invoke(minecraft.entityRenderer, timer.renderPartialTicks, 0);
		            method.setAccessible(false);
		        }
		        catch (Exception e) {
		            e.printStackTrace();
		            this.ticksAlive = this.maxTicksAlive;
		            return;
		        }
		        final Vector2d screenPos = RenderUtils.worldToScreen(rX, rY, rZ);
		        screenPos.y = Display.getHeight() / 2.0 - screenPos.y;
		        if (screenPos.x <= 0.0 || screenPos.x >= minecraft.displayWidth || screenPos.y <= 0.0 || screenPos.y >= minecraft.displayHeight) {
		            return;
		        }
		        final GameSettings gameSettings = minecraft.gameSettings;
		        final int guiScale = gameSettings.guiScale;
		        gameSettings.guiScale = 2;
		        Minecraft.getMinecraft().entityRenderer.setupOverlayRendering();
		        gameSettings.guiScale = guiScale;
		        frn.drawStringwithShadow(this.text, (float)(int)screenPos.x, (float)(int)screenPos.y, this.color);
		
	}



	
	
}
