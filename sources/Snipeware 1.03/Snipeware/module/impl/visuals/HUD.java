package Snipeware.module.impl.visuals;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.ToDoubleFunction;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.VanillaFontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

import Snipeware.Client;
import Snipeware.api.annotations.Handler;
import Snipeware.events.player.EventKeyPress;
import Snipeware.events.render.EventRender2D;
import Snipeware.gui.click.Panel;
import Snipeware.module.Module;
import Snipeware.module.Module.ModuleCategory;
import Snipeware.util.other.Logger;
import Snipeware.util.other.PlayerUtil;
import Snipeware.util.visual.RenderUtil;
import Snipeware.util.visual.Translate;
import Snipeware.value.impl.BooleanValue;
import Snipeware.value.impl.ColorValue;
import Snipeware.value.impl.EnumValue;
import Snipeware.value.impl.NumberValue;
import font.FontRenderer;

public class HUD extends Module {
 
private float hue = 1.0F;
  
  private EnumValue<ColorMode> arrayListColor = new EnumValue<>("Module List Color", ColorMode.Normal);
  
  private EnumValue<FontMode> fontMode = new EnumValue<>("Font Mode", FontMode.Normal);

  private BooleanValue pulsing = new BooleanValue("Pulsing", true);
  private BooleanValue background = new BooleanValue("Background", false);
  private BooleanValue tabgui = new BooleanValue("TabGUI", true);
  private BooleanValue fpsproperty = new BooleanValue("Show FPS", true);
  private BooleanValue fpsspoof = new BooleanValue("Spoof FPS", true);
  private BooleanValue arrayList = new BooleanValue("Module List", true);
  private BooleanValue info = new BooleanValue("Info", true);
  private BooleanValue armorStatus = new BooleanValue("Armor Status", true);
  private BooleanValue potionStatus = new BooleanValue("Potion Status", true);
  private BooleanValue toggleSounds = new BooleanValue("Toggle Sounds", true);
  private BooleanValue watermark = new BooleanValue("Watermark", true);
  private BooleanValue sideLine = new BooleanValue("Bar", true);
  
  private NumberValue<Float> rainbowSaturation = new NumberValue<>("Rainbow Saturation", 0.6f, 0.1f, 1.0f);
  
  public NumberValue<Integer> FpsAdder = new NumberValue<>("FPS+", 100, 5000, 10000, 100);
  
  private NumberValue<Float> modListBackgroundAlpha;
  
  private final NumberValue<Integer> X = new NumberValue<>("Radar X", 1, 1, 1920, 5);
  private final NumberValue<Integer> Y = new NumberValue<>("Radar Y", 15, 1, 1920, 2);
  private NumberValue<Integer> size = new NumberValue<>("Radar Size", 60, 50, 130, 5);
  
  private ColorValue colorValue = new ColorValue("Custom Color", new Color(255, 255, 255).getRGB());
  private double animated1;
  private float xOffset;
  private float yOffset;
  private boolean dragging;
  public String bps;
  private int index;
  private int index2;
  private int MaxIndex;
  private boolean expanded;
  public ArrayList<String> ModulesCombat = new ArrayList<String>();
  public ArrayList<String> ModulesMovement = new ArrayList<String>();
  public ArrayList<String> ModulesPlayer = new ArrayList<String>();
  public ArrayList<String> ModulesWorld = new ArrayList<String>();
  public ArrayList<String> ModulesVisuals = new ArrayList<String>();
  
  public HUD() {
	  super("HUD", 0, ModuleCategory.VISUALS);
      modListBackgroundAlpha = new NumberValue("BG Alpha", 0.2f, 0.0f, 1.0f, 0.05f);
      addValues(fontMode, arrayListColor, modListBackgroundAlpha, rainbowSaturation, FpsAdder , X, Y, size, colorValue, arrayList, watermark, fpsproperty ,fpsspoof ,tabgui, sideLine, info, armorStatus, potionStatus, pulsing, toggleSounds, background);
      setHidden(true);
  }
  
  private enum ColorMode {
	  Normal, Custom, Rainbow, Astolfo;
  }
  
  private enum FontMode {
	  Normal, Tahoma, Vanilla;
  }
  
  @Override
  public void onEnable() {
	  super.onEnable();
  }

  @Override
  public void onDisable() {
	  super.onDisable();
  }
  
  @Handler
  public void onRender(final EventRender2D event) {
	  animated1 = RenderUtil.animate(index, animated1, 0.1);
	  
	
	  if (arrayList.isEnabled()) {
		  draw(event.getScaledResolution());
	  }
	  if (armorStatus.isEnabled()) {
		  drawArmorStatus(event.getScaledResolution());
	  }
	  if (potionStatus.isEnabled()) {
		  drawPotionStatus(event.getScaledResolution());
	  }
	  if(tabgui.isEnabled()) {
		 drawTabGui();
		 
	  }
	  boolean tahoma = fontMode.getValue().equals(FontMode.Tahoma);
	  EntityPlayerSP player = mc.thePlayer;
      double xDist = player.posX - player.lastTickPosX;
      double zDist = player.posZ - player.lastTickPosZ;
      float d = (float) StrictMath.sqrt(xDist * xDist + zDist * zDist);
    
      int fpse = Minecraft.getDebugFPS();
      if(fpsspoof.isEnabled()) {
    	  fpse += FpsAdder.getValue();
      }
     
      bps = String.format(ChatFormatting.WHITE + "%.2f"  , d * 20 * mc.timer.timerSpeed);
     
      final String fps = fpsproperty.isEnabled() ? ChatFormatting.GRAY + " (" + ChatFormatting.WHITE + fpse + " FPS" + ChatFormatting.GRAY + ")" : "";
	  final FontRenderer fr = Client.INSTANCE.getFontManager().getFont(tahoma ? "Tahoma 20" : "Display 20", false);
	  final FontRenderer font = Client.INSTANCE.getFontManager().getFont(tahoma ? "Tahoma 20" : "Display 20", false);
	 
	//  final String text = ChatFormatting.GRAY + "X" + ChatFormatting.WHITE + ": " + MathHelper.floor_double(mc.thePlayer.posX) + " " + ChatFormatting.GRAY + "Y" + ChatFormatting.WHITE + ": " + MathHelper.floor_double(mc.thePlayer.posY) + " " + ChatFormatting.GRAY + "Z" + ChatFormatting.WHITE + ": " + MathHelper.floor_double(mc.thePlayer.posZ);
	  String text = "BPS: " + bps;
      final int ychat = mc.ingameGUI.getChatGUI().getChatOpen() ? 25 : 10;
      int color = 0;
      switch (arrayListColor.getValue()) {
		case Custom:
			color = new Color(colorValue.getValue()).getRGB();
			break;
		case Rainbow:
			color = RenderUtil.getRainbow(6000, (int) (1 * 30), rainbowSaturation.getValue());
			break;
		case Normal:
			color = RenderUtil.getGradientOffset(new Color(Panel.color2), new Color(Panel.color), (Math.abs(((System.currentTimeMillis()) / 10)) / 100D) + (1 / (50))).getRGB();
			break;
		case Astolfo:
			color = RenderUtil.getGradientOffset(new Color(0, 255, 255), new Color(255,105,180), (Math.abs(((System.currentTimeMillis()) / 10)) / 100D) + (1 / (50))).getRGB();
			break;
		default:
			break;
      }
      boolean vanilla = fontMode.getValue().equals(FontMode.Vanilla);
      if (watermark.isEnabled()) {
    	  if (vanilla) {
 
    		  mc.fontRendererObj.drawStringWithShadow("Snipeware" + fps, 1, 1, -1);
    		  
    	  } else {
 
    		  font.drawStringWithShadow("Snipeware" + fps, 1, 1, -1);
    	  }
      }
  	color = RenderUtil.getRainbow(6000, (int) (1 * 30), rainbowSaturation.getValue());
      if (info.isEnabled()) {
    	  if (vanilla) {
    		  mc.fontRendererObj.drawStringWithShadow(text, 1, new ScaledResolution(mc).getScaledHeight() - ychat, color);
    	  } else {
    		  font.drawStringWithShadow(text, 1, new ScaledResolution(mc).getScaledHeight() - ychat - 3, color);
    	  }
      }
  }
  
  
  
  private void drawArmorStatus(ScaledResolution sr) {
      GL11.glPushMatrix();
      List stuff = new ArrayList();
      boolean onwater = mc.thePlayer.isEntityAlive() && mc.thePlayer.isInsideOfMaterial(Material.water);
      int split = -3;

      ItemStack errything;
      for(int index = 3; index >= 0; --index) {
         errything = mc.thePlayer.inventory.armorInventory[index];
         if (errything != null) {
            stuff.add(errything);
         }
      }

      if (mc.thePlayer.getCurrentEquippedItem() != null) {
         stuff.add(mc.thePlayer.getCurrentEquippedItem());
      }

      Iterator var8 = stuff.iterator();

      while(var8.hasNext()) {
         errything = (ItemStack)var8.next();
         if (mc.theWorld != null) {
            RenderHelper.enableGUIStandardItemLighting();
            split += 16;
         }

         GlStateManager.pushMatrix();
         GlStateManager.disableAlpha();
         GlStateManager.clear(256);
         mc.getRenderItem().zLevel = -150.0F;
         int s = mc.thePlayer.capabilities.isCreativeMode ? 15 : 0;
		 mc.getRenderItem().renderItemAndEffectIntoGUI(errything, split + sr.getScaledWidth() / 2 - 4, sr.getScaledHeight() - (onwater ? 65 : 55) + s);
         mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, errything, split + sr.getScaledWidth() / 2 - 4, sr.getScaledHeight() - (onwater ? 65 : 55) + s);
         mc.getRenderItem().zLevel = 0.0F;
         GlStateManager.disableBlend();
         GlStateManager.scale(0.5D, 0.5D, 0.5D);
         GlStateManager.disableDepth();
         GlStateManager.disableLighting();
         GlStateManager.enableDepth();
         GlStateManager.scale(2.0F, 2.0F, 2.0F);
         GlStateManager.enableAlpha();
         GlStateManager.popMatrix();
         errything.getEnchantmentTagList();
      }

      GL11.glPopMatrix();
  }
  
  private void drawTabGui() {
		final ScaledResolution scaledResolution2;
		final ScaledResolution scaledResolution = scaledResolution2 = new ScaledResolution(mc);
		final FontRenderer font = Client.INSTANCE.getFontManager().getFont("Display 20", false);
	    double x = scaledResolution2.getScaledWidth() / 2;
		double y = scaledResolution2.getScaledHeight() / 2;
		double rectwidth = 65;
		double rectheight = 73;
		int rectX = 5;
		int rectY = 20;
		for (ModuleCategory cat : ModuleCategory.values()) {
		for (Module m : Client.INSTANCE.getModuleManager().getModules()) {
			if (cat == m.getCategory()) {
				if(m.getCategory() == m.getCategory().COMBAT) {
					ModulesCombat.add(m.getName());
				}
				if(m.getCategory() == m.getCategory().MOVEMENT) {
					ModulesMovement.add(m.getName());
				}
				if(m.getCategory() == m.getCategory().PLAYER) {
					ModulesPlayer.add(m.getName());
				}
				if(m.getCategory() == m.getCategory().WORLD) {
					ModulesWorld.add(m.getName());
				}
				if(m.getCategory() == m.getCategory().VISUALS) {
					ModulesVisuals.add(m.getName());
				}
			}
		}
		}
		RenderUtil.drawRect(rectX - 2.5f, rectY - 2.5f, rectwidth + 5, rectheight + 5,new Color(10,10,10,195).getRGB());
		RenderUtil.drawRect(rectX -2.5f,rectY - 2.5f + index * 15, rectwidth + 5, 16, new Color(38,43,226, 200).getRGB());
		if(index == 0) {
			font.drawString("Combat", rectX + 3, rectY , -1);
		}else {
			font.drawString("Combat", rectX, rectY, -1);
		}
		if(index == 1) {
			font.drawString("Movement", rectX + 3, rectY  + 15, -1);
		}else {
			font.drawString("Movement", rectX, rectY + 15, -1);
		}
		if(index == 2) {
			font.drawString("Player", rectX + 3, rectY + 30, -1);
		}else {
			font.drawString("Player", rectX, rectY + 30, -1);
		}
		if(index == 3) {
			font.drawString("World", rectX + 3, rectY  + 45, -1);
		}else {
			font.drawString("World", rectX, rectY + 45, -1);
		}
		if(index == 4) {
			font.drawString("Visuals", rectX + 5, rectY  + 60, -1);
		}else {
			font.drawString("Visuals", rectX, rectY  + 60, -1);
		}
		
		if(expanded) {
			if(index == 0) {
				MaxIndex = 15;
				RenderUtil.drawRect(rectX + rectwidth + 3, rectY - 2.5f, rectwidth + 5, rectheight + 5 + 105,new Color(10,10,10,195).getRGB());
				RenderUtil.drawRect(rectX + rectwidth + 3,rectY  + index2 * 12 - 1, rectwidth + 5, 12, new Color(38,43,226, 200).getRGB());
				for(int i = 0; i < 15; i++) {
					font.drawString(ModulesCombat.get(i), rectX + 5 + (int) rectwidth, rectY + i * 12, -1);
				}
			}
			if(index == 1) {
				MaxIndex = 9;
				RenderUtil.drawRect(rectX + rectwidth + 3, rectY - 2.5f, rectwidth + 5, rectheight + 5 + 35,new Color(10,10,10,195).getRGB());
				RenderUtil.drawRect(rectX + rectwidth + 3,rectY  + index2 * 12 - 1, rectwidth + 5, 12, new Color(38,43,226, 200).getRGB());
				for(int i = 0; i < 9; i++) {
					font.drawString(ModulesMovement.get(i), rectX + 5 + (int) rectwidth, rectY + i * 12, -1);
				}
			}
			if(index == 2) {
				MaxIndex = 14;
				RenderUtil.drawRect(rectX + rectwidth + 3, rectY - 2.5f, rectwidth + 5, rectheight + 5 + 95,new Color(10,10,10,195).getRGB());
				RenderUtil.drawRect(rectX + rectwidth + 3,rectY  + index2 * 12 - 1, rectwidth + 5, 12, new Color(38,43,226, 200).getRGB());
				for(int i = 0; i < 14; i++) {
					font.drawString(ModulesPlayer.get(i), rectX + 5 + (int) rectwidth, rectY  + i * 12, -1);
				}
			}
			if(index == 3) {
				MaxIndex = 13;
				RenderUtil.drawRect(rectX + rectwidth + 3, rectY - 2.5f, rectwidth + 5, rectheight + 5 + 80,new Color(10,10,10,195).getRGB());
				RenderUtil.drawRect(rectX + rectwidth + 3,rectY  + index2 * 12 - 1, rectwidth + 5, 12, new Color(38,43,226, 200).getRGB());
				for(int i = 0; i < 13; i++) {
					font.drawString(ModulesWorld.get(i), rectX + 5 + (int) rectwidth, rectY  + i * 12, -1);
				}
			}
			if(index == 4) {
				MaxIndex = 20;
				RenderUtil.drawRect(rectX + rectwidth + 3, rectY - 2.5f, rectwidth + 5, rectheight + 5 + 165,new Color(10,10,10,195).getRGB());
				RenderUtil.drawRect(rectX + rectwidth + 3,rectY  + index2 * 12 - 1, rectwidth + 5, 12, new Color(38,43,226, 200).getRGB());
				for(int i = 0; i < 20; i++) {
					font.drawString(ModulesVisuals.get(i), rectX + 5 + (int) rectwidth, rectY + i * 12, -1);
				}
			}
		}
		
  }
  @Handler
	public void onKeyPress(final EventKeyPress event) {
	  //System.out.println(event.getKey());
	  if(tabgui.isEnabled()) {
		  if(event.getKey() == 28) {
			 if(expanded) {
				 if(index == 0) {
					 Client.getInstance().getModuleManager().getModule(ModulesCombat.get(index2)).toggle();;
				 }
				 if(index == 1) {
					 Client.getInstance().getModuleManager().getModule(ModulesMovement.get(index2)).toggle();;
				 }
				 if(index == 2) {
					 Client.getInstance().getModuleManager().getModule(ModulesPlayer.get(index2)).toggle();;
				 }
				 if(index == 3) {
					 Client.getInstance().getModuleManager().getModule(ModulesWorld.get(index2)).toggle();;
				 }
				 if(index == 4) {
					 Client.getInstance().getModuleManager().getModule(ModulesVisuals.get(index2)).toggle();;
				 }
			 }
			 
		  }
		  if(event.getKey() == 200) {
			  if(!expanded) {
			  index--;
			  index2 = 0;
			  }
		  }
		  if(event.getKey() == 208) {
			  if(!expanded) {
			  index++;
			  index2 = 0;
			  }
		  }
		  if(event.getKey() == 200) {
			  if(expanded) {
			  index2--;
			  }
		  }
		  if(event.getKey() == 208) {
			  if(expanded) {
			  index2++;
			  }
		  }
		  if(event.getKey() == 205) {
			  expanded = true;
		  }
		  if(event.getKey() == 203) {
			  expanded = false;
		  }
		  if(index >= 5) {
			  index = 0;
		  }
		  if(index <= -1) {
			  index = 4;
		  }
		  if(index2 >= MaxIndex) {
			  index2 = 0;
		  }
		  if(index2 <= -1) {
			  index2 = MaxIndex - 1;
		  }
	  }
  }
  
  private void drawPotionStatus(ScaledResolution sr) {
      int y = 0;
      for (final PotionEffect effect : (Collection<PotionEffect>) this.mc.thePlayer.getActivePotionEffects()) {
          Potion potion = Potion.potionTypes[effect.getPotionID()];
          String PType = I18n.format(potion.getName());
          switch (effect.getAmplifier()) {
              case 1:
                  PType = PType + " II";
                  break;
              case 2:
                  PType = PType + " III";
                  break;
              case 3:
                  PType = PType + " IV";
                  break;
              default:
                  break;
          }
          if (effect.getDuration() < 600 && effect.getDuration() > 300) {
              PType = PType + "\2477:\2476 " + Potion.getDurationString(effect);
          } else if (effect.getDuration() < 300) {
              PType = PType + "\2477:\247c " + Potion.getDurationString(effect);
          } else if (effect.getDuration() > 600) {
              PType = PType + "\2477:\2477 " + Potion.getDurationString(effect);
          }
          int ychat = mc.ingameGUI.getChatGUI().getChatOpen() ? 5 : 2;
          mc.fontRendererObj.drawStringWithShadow(PType, sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(PType) - 1, sr.getScaledHeight() - mc.fontRendererObj.FONT_HEIGHT + y - 12 - ychat, new Color(255, 255, 255).getRGB());
          y -= 10;
      }
  }
  
  public void draw(ScaledResolution sr) {
    int width = sr.getScaledWidth();
    int height = sr.getScaledHeight();
    
    boolean tahoma = fontMode.getValue().equals(FontMode.Tahoma);
    final FontRenderer fr = tahoma ? Client.INSTANCE.getFontManager().getFont("Tahoma 18", true) : Client.INSTANCE.getFontManager().getFont("Display 18", true);
    
    final VanillaFontRenderer fro = mc.fontRendererObj;
    
    final boolean bottom = false;
    
    boolean vanilla = fontMode.getValue().equals(FontMode.Vanilla);
    
    List<Module> sortedList = getSortedModules(fr, fro);
    float translationFactor = 40.4F / Minecraft.getDebugFPS();
    double listOffset = 11, y = bottom ? (height - listOffset) : 0;
    hue += translationFactor / 255.0F;
    if (hue > 1.0F)
      hue = 0.0F; 
    float h = hue;
    GL11.glEnable(3042);
    for (int i = 0, sortedListSize = sortedList.size(); i < sortedListSize; i++) {
      Module module = sortedList.get(i);
      Translate translate = module.translate;
      String moduleLabel = module.getName() + (module.getSuffix() != null ? ChatFormatting.GRAY + " " + module.getSuffix() : "");
      float length = vanilla ? fro.getStringWidth(moduleLabel) : fr.getWidth(moduleLabel);
      float featureX = width - length;
      boolean enable = module.isEnabled();
      if (bottom) {
        if (enable) {
          translate.interpolate(featureX, (y + 1), translationFactor);
        } 
        else {
          translate.interpolate(width, (height + 1), translationFactor);
        } 
      } 
      else if (enable) {
        translate.interpolate(featureX, (y + 1), translationFactor);
      } 
      else {
        translate.interpolate(width * width, (-listOffset + 1), translationFactor);
      } 
      double translateX = translate.getX();
      double translateY = translate.getY();
      boolean visible = bottom ? ((translateY < height)) : ((translateY > -listOffset));
      if (visible) {
        int color;
        color = 0;
		switch (arrayListColor.getValue()) {
		case Custom:
			color = new Color(colorValue.getValue()).getRGB();
			boolean pulsing = this.pulsing.isEnabled();
			if (pulsing) {
				float colorWidth = vanilla ? fro.getStringWidth(moduleLabel) * 2 + 10 : fr.getWidth(moduleLabel) * 2 + 10;
				color = RenderUtil.fade(new Color(this.colorValue.getValue()), 100, (int) colorWidth).getRGB();
			}
			break;
		case Rainbow:
			color = RenderUtil.getRainbow(6000, (int) (y * 30), rainbowSaturation.getValue());
			break;
		case Normal:
			color = RenderUtil.getGradientOffset(new Color(Panel.color2), new Color(Panel.color), (Math.abs(((System.currentTimeMillis()) / 10)) / 100D) + (y / (50))).getRGB();
			break;
		case Astolfo:
			color = RenderUtil.getGradientOffset(new Color(0, 255, 255), new Color(255,105,180), (Math.abs(((System.currentTimeMillis()) / 10)) / 100D) + (y / (50))).getRGB();
			break;
		default:
			break;
        }
        int nextIndex = sortedList.indexOf(module) + 1;
        Module nextModule = null;
        final int outlineColor = arrayListColor.getValue().equals(ColorMode.Custom) ? new Color(colorValue.getValue()).getRGB()  : color;
        if (sortedList.size() > nextIndex)
          nextModule = getNextEnabledModule(sortedList, nextIndex); 
        if (background.isEnabled()) {
          Gui.drawRect(translateX - 2.0D, translateY - 1.0D, width, translateY + listOffset - 1.0D, (new Color(13, 13, 13, (int)(255.0F * modListBackgroundAlpha.getValue()))).getRGB());
        }
        if (sideLine.isEnabled()) {
        	Gui.drawRect(translateX + length - 1.0, translateY - 1.0D, width, translateY + listOffset - 1.0D, color);
        	Gui.drawRect(translateX + length - 1.0, translateY - 1.0D, width, translateY + listOffset - 1.0D, Color.TRANSLUCENT);
        } else {
        	Gui.drawRect(translateX + length - 1.0, translateY - 1.0D, width, translateY + listOffset - 1.0D, Color.TRANSLUCENT);
        }
        if (vanilla) {
        	fro.drawStringWithShadow(moduleLabel, (float)translateX - 1.4f, (float)translateY + 1, color);
        }
        else {
        	fr.drawStringWithShadow(moduleLabel, (float)translateX - 1f, (float)translateY, color);
        }
        if (module.isEnabled())
          y += bottom ? -listOffset : listOffset; 
        h += translationFactor / 6.0F;
      } 
    } 
  }
  

  
  public boolean hasToggleSoundsEnabled() {
	  return toggleSounds.isEnabled();
  }

  private Module getNextEnabledModule(List<Module> sortedList, int startingIndex) {
    for (int i = startingIndex, modulesSize = sortedList.size(); i < modulesSize; i++) {
    	Module module = sortedList.get(i);
      if (module.isEnabled())
        return module; 
    } 
    return null;
  }
  
  private List<Module> getSortedModules(final FontRenderer fr, VanillaFontRenderer fro) {
	boolean vanilla = fontMode.getValue().equals(FontMode.Vanilla);
    List<Module> sortedList = new ArrayList(Client.INSTANCE.getModuleManager().getModules());
    sortedList.removeIf(Module::isHidden);
    sortedList.sort(Comparator.comparingDouble(e -> vanilla ? -fro.getStringWidth(e.getName() + (e.getSuffix() != null ? ChatFormatting.GRAY + " " + e.getSuffix() : "")) : -fr.getWidth(e.getName() + (e.getSuffix() != null ? ChatFormatting.GRAY + " " + e.getSuffix() : ""))));
    return sortedList;
  }
}
