package Snipeware.module.impl.visuals;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.VanillaFontRenderer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import Snipeware.Client;
import Snipeware.api.annotations.Handler;
import Snipeware.events.packet.EventPacketReceive;
import Snipeware.events.render.EventRender2D;
import Snipeware.gui.notification.Notifications;
import Snipeware.module.Module;
import Snipeware.module.impl.combat.AntiBot;
import Snipeware.module.impl.combat.KillAura;
import Snipeware.util.other.Logger;
import Snipeware.util.other.MathUtils;
import Snipeware.util.other.PlayerUtil;
import Snipeware.util.other.TimeHelper;
import Snipeware.util.visual.RenderUtil;
import Snipeware.value.impl.BooleanValue;
import Snipeware.value.impl.ColorValue;
import Snipeware.value.impl.EnumValue;
import font.FontRenderer;

public final class TargetHUD extends Module {

	private final TimeHelper animationStopwatch = new TimeHelper();
	private double healthBarWidth;
	private EnumValue<Mode> mode = new EnumValue<>("Mode", Mode.Normal);
	private EnumValue<FontMode> fontMode = new EnumValue<>("Font Mode", FontMode.Normal);
	private final BooleanValue showPlayers = new BooleanValue("Show Players", true);
	private final BooleanValue showMonsters = new BooleanValue("Show Monsters", true);
	private final BooleanValue showInvisibles = new BooleanValue("Show Invisibles", true);
	private final BooleanValue showAnimals = new BooleanValue("Show Animals", true);
	private final BooleanValue showPassives = new BooleanValue("Show Passives", true);
	  private ColorValue colorValue = new ColorValue("Color", new Color(220, 220, 220).getRGB());
	private double x, y;

	public TargetHUD() {
		super("TargetHUD", 0, ModuleCategory.VISUALS);
		setHidden(true);
		addValues(mode,fontMode,showPlayers, showMonsters, showAnimals, showInvisibles, showPassives, colorValue);
	}

	 private enum Mode {
		  Normal, Exhibition;
	  }
	
	  private enum FontMode {
		  Normal, Vanilla;
	  }
	  
	 
	  
	@Handler
    public void onRender2D(final EventRender2D event) {
		if(mode.getValueAsString() == "Normal") {
	        final VanillaFontRenderer fr = mc.fontRendererObj;
	        final KillAura killAura = (KillAura) Client.INSTANCE.getModuleManager().getModule(KillAura.class);
	     
	    	  final FontRenderer font = Client.INSTANCE.getFontManager().getFont("Display 20", false);
	  	  final FontRenderer font2 = Client.INSTANCE.getFontManager().getFont("Display 30", false);
	        boolean guichat = mc.currentScreen instanceof GuiChat;
	        EntityLivingBase entityPlayer = guichat ? mc.thePlayer : killAura.target;
	        if (mc.thePlayer != null && killAura.target != null || guichat) {
	        	if (isValidEntity(entityPlayer) || entityPlayer == mc.thePlayer) {
		            GlStateManager.pushMatrix();
		            GlStateManager.translate(x, y, 0);
		            final ScaledResolution scaledResolution2;
		            final ScaledResolution scaledResolution = scaledResolution2 = new ScaledResolution(mc);
		            int var141 = scaledResolution.getScaledWidth();
		            int var151 = scaledResolution.getScaledHeight();
		            int mouseX = Mouse.getX() * var141 / mc.displayWidth;
		            int mouseY = var151 - Mouse.getY() * var151 / mc.displayHeight - 1;
		            
		
		            
		            final float n = 2;
		            scaledResolution2.scaledWidth *= (int)(1.0f / n);
		            final ScaledResolution scaledResolution3 = scaledResolution;
		            scaledResolution3.scaledHeight *= (int)(1.0f / n);
		            final int n2 = scaledResolution.getScaledHeight() / 2 + 200;
		            final int n3 = scaledResolution.getScaledWidth() / 2 + 300;
		            RenderUtil.drawRect(n3 + 1f, n2 + 1, 140.0, 37.6, new Color(25, 25, 25, 210).getRGB());
		          
		         
		            if (Mouse.isButtonDown(0) && guichat) {
		            	
		            	setX(mouseX - 300);
		            	setY(mouseY - 200);
		            	
		            	
		            }
		            String string = String.format("%.1f", entityPlayer.getHealth() / 2.0f);
		            
		    
		            if(!(fontMode.getValue() == fontMode.getValue().Vanilla)) {
		      	  font2.drawStringWithShadow(string.replace(".0", ""), (float)(n3 + 35), (float)(n2 + 15),colorValue.getValue());
		            }else {
		            	GlStateManager.pushMatrix();
		            	GlStateManager.scale(n, n, n);
		        
		            mc.fontRendererObj.drawStringWithShadow(string.replace(".0", ""), (float)(n3 - 132), (float)(n2 - 93), colorValue.getValue());
		            GlStateManager.popMatrix();
		            }
	
		            final double n4 = 137.0f / entityPlayer.getMaxHealth() * (double)Math.min(entityPlayer.getHealth(), entityPlayer.getMaxHealth());
		            if (animationStopwatch.isDelayComplete(15)) {
		                healthBarWidth = RenderUtil.animate(n4, healthBarWidth, 0.05);
		                animationStopwatch.reset();
		            }
		            RenderUtil.drawRect((float)n3 + 2f, (float)n2 + (float)34, 138, (float)3.5, RenderUtil.darker(colorValue.getColor(), 0.35f).getRGB());
		            float less = entityPlayer.getHealth() == 0 || entityPlayer.getHealth() == entityPlayer.getMaxHealth() ? 0 : 4f;
		            RenderUtil.drawRect((float)n3 + 2f, (float)n2 + (float)34, (float)healthBarWidth + (float)0.9, (float)3.5 , RenderUtil.brighter(colorValue.getColor(), 0.35f).getRGB());
		            RenderUtil.drawRect((float)n3 + 2f, (float)n2 + (float)34, n4 + (float)0.9, (float)3.5, (colorValue.getValue()));
		            
		            final String name = entityPlayer instanceof EntityPlayer ? ((EntityPlayer) entityPlayer).getGameProfile().getName() : killAura.target.getDisplayName().getFormattedText();
		            GlStateManager.enableBlend();
		            if(!(fontMode.getValue() == fontMode.getValue().Vanilla)) {
		            	font.drawStringWithShadow(name, (float)(n3 + 35), (float)(n2 + 3), -855638017);
		            }else {
		                mc.fontRendererObj.drawStringWithShadow(name, (float)(n3 + 35), (float)(n2 + 3), -855638017);
		            }
		            if (entityPlayer instanceof EntityPlayer) {
		            	mc.getTextureManager().bindTexture(((AbstractClientPlayer) entityPlayer).getLocationSkin());
			            GlStateManager.enableBlend();
			            GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.8f);
			            Gui.drawScaledCustomSizeModalRect(n3 + 2, n2 + 2, 8.0f, 8.0f, 8, 8, 31, 31, 64.0f, 64.0f);
		            } 
		            GlStateManager.disableBlend();
		            GlStateManager.popMatrix();
	        	}
	        	if (!isValidEntity(entityPlayer)) {
	        		entityPlayer = null;
	        	}
	        }
		}else if(mode.getValueAsString() == "Exhibition"){
			final KillAura killAura = (KillAura) Client.INSTANCE.getModuleManager().getModule(KillAura.class);
			final FontRenderer fr = Client.INSTANCE.getFontManager().getFont("Calibri 22", false);
			final FontRenderer fr2 = Client.INSTANCE.getFontManager().getFont("Calibri 12", false);
		    final ScaledResolution scaledResolution2;
	        final ScaledResolution scaledResolution = scaledResolution2 = new ScaledResolution(mc);
			EntityLivingBase entityPlayer = killAura.target;
			final String name = entityPlayer instanceof EntityPlayer ? ((EntityPlayer) entityPlayer).getGameProfile().getName() : killAura.target.getDisplayName().getFormattedText();
			if(killAura.target != null) {
				//RenderUtil.drawRect(scaledResolution.getScaledWidth() / 1.95 - 8, scaledResolution.getScaledHeight() / 1.88f - 8, 161, 66, new Color(28,28,28).getRGB());
				//RenderUtil.drawRect(scaledResolution.getScaledWidth() / 1.95 - 10, scaledResolution.getScaledHeight() / 1.88f - 10, 165, 70, new Color(35,35,35).getRGB());
				//RenderUtil.drawRect(scaledResolution.getScaledWidth() / 1.95 - 6, scaledResolution.getScaledHeight() / 1.88f - 6, 157, 62, new Color(20,20,20).getRGB());
				RenderUtil.drawRect(scaledResolution.getScaledWidth() / 1.95 - 1, scaledResolution.getScaledHeight() / 1.88f - 1, 147, 52, new Color(39,39,39).getRGB());
				RenderUtil.drawRect(scaledResolution.getScaledWidth() / 1.95, scaledResolution.getScaledHeight() / 1.88f, 145, 50, new Color(21,21,21).getRGB());
				GuiInventory.drawEntityOnScreen((int) (scaledResolution.getScaledWidth() / 1.95 + 17 + 4), (int) (scaledResolution.getScaledHeight() / 1.88f + 45), 19, (int)getX(), (int)getY(), killAura.target);
				fr.drawString(name, (float) (scaledResolution.getScaledWidth() / 1.96f) + 40.5f, scaledResolution.getScaledHeight() / 1.88f + 4, -855638017);
				String distance = String.format("%.1f", killAura.target.getDistanceToEntity(mc.thePlayer));
				String Health = String.format("%.1f", killAura.target.getHealth());
				fr2.drawString("HP:" + Health + " | Dist:" +  distance, (float) (scaledResolution.getScaledWidth() / 1.96f + 42), scaledResolution.getScaledHeight() / 1.88f + 24, -855638017);
				  
				if(killAura.target.getHealth() > 2) {
					  RenderUtil.drawRect(scaledResolution.getScaledWidth() /  1.96 + 42, scaledResolution.getScaledHeight() / 1.88f + 17, 9, 4, RenderUtil.drawHealth(killAura.target));
				  }
				  if(killAura.target.getHealth() > 4) {
					  RenderUtil.drawRect(scaledResolution.getScaledWidth() / 1.96 + 42 + 10, scaledResolution.getScaledHeight() / 1.88f + 17, 9, 4,  RenderUtil.drawHealth(killAura.target));
				  }
				  if(killAura.target.getHealth() > 6) {
					  RenderUtil.drawRect(scaledResolution.getScaledWidth() /  1.96 + 42 + 20, scaledResolution.getScaledHeight() / 1.88f + 17, 9, 4,  RenderUtil.drawHealth(killAura.target));
				  }
				  if(killAura.target.getHealth() > 8) {
					  RenderUtil.drawRect(scaledResolution.getScaledWidth() / 1.96 + 42 + 30,scaledResolution.getScaledHeight() / 1.88f + 17, 9, 4,  RenderUtil.drawHealth(killAura.target));
				  }
				  if(killAura.target.getHealth() > 10) {
					  RenderUtil.drawRect(scaledResolution.getScaledWidth() / 1.96 + 42 + 40, scaledResolution.getScaledHeight() / 1.88f + 17, 9, 4,  RenderUtil.drawHealth(killAura.target));
				  }
				  if(killAura.target.getHealth() > 12) {
					  RenderUtil.drawRect(scaledResolution.getScaledWidth() / 1.96 + 42 + 50, scaledResolution.getScaledHeight() / 1.88f + 17, 9, 4,  RenderUtil.drawHealth(killAura.target));
				  }
				  if(killAura.target.getHealth() > 14) {
					  RenderUtil.drawRect(scaledResolution.getScaledWidth() / 1.96 + 42 + 60, scaledResolution.getScaledHeight() / 1.88f + 17, 9, 4,  RenderUtil.drawHealth(killAura.target));
				  }
				  if(killAura.target.getHealth() > 16) {
					  RenderUtil.drawRect(scaledResolution.getScaledWidth() / 1.96 + 42 + 70, scaledResolution.getScaledHeight() / 1.88f + 17, 9, 4,  RenderUtil.drawHealth(killAura.target));
				  }
				  if(killAura.target.getHealth() > 18) {
					  RenderUtil.drawRect(scaledResolution.getScaledWidth() / 1.96 + 42 + 80, scaledResolution.getScaledHeight() / 1.88f + 17, 9, 4,  RenderUtil.drawHealth(killAura.target));
				  }
				  if(killAura.target.getHealth() > 20) {
					  RenderUtil.drawRect(scaledResolution.getScaledWidth() / 1.96 + 42 + 90, scaledResolution.getScaledHeight() / 1.88f + 17, 9, 4,  RenderUtil.drawHealth(killAura.target));
				  }
				  for(int index = 3; index >= 0; --index) {
					  mc.getRenderItem().renderItemAndEffectIntoGUI(killAura.target.getCurrentArmor(index), (int) (scaledResolution.getScaledWidth() / 1.95 + 30 + 8 + index * 14), (int) (scaledResolution.getScaledHeight() / 1.88f + 29));
				  }
				  //mc.getRenderItem().renderItemOverlays(mc.fontRendererObj,killAura.target.getHeldItem(), (int) (scaledResolution.getScaledWidth() / 1.95), (int) (scaledResolution.getScaledHeight() / 1.88f + 10));
			}
		}
    }
	
	
	public void onEnable() {
		super.onEnable();
	 
	
		}
		
	

	public void onDisable() {
		super.onDisable();
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	private boolean isValidEntity(final EntityLivingBase target) {
		if (target instanceof EntityMob && !showMonsters.isEnabled()) {
			return false;
		}
		if (target.isInvisible() && target instanceof EntityPlayer && !showInvisibles.isEnabled()) {
			return false;
		}
		if (target instanceof EntityPlayer && !showPlayers.isEnabled()) {
			return false;
		}
		if (target instanceof EntityGolem || target instanceof EntityVillager && !showPassives.isEnabled()) {
			return false;
		}
		if (target instanceof EntityAnimal && !showAnimals.isEnabled()) {
			return false;
		}
		return true;
	}
}
