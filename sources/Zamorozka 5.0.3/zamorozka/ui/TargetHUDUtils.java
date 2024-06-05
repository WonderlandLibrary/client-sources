package zamorozka.ui;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import zamorozka.modules.COMBAT.KillAura;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;

public class TargetHUDUtils {
    public static Minecraft mc = Minecraft.getMinecraft();
    
		public static void onScreenDraw(ScaledResolution res) {
			int x = res.getScaledWidth() /2 ;
			int y = res.getScaledHeight()/2;
	        final EntityLivingBase player = KillAura.target;
	         if (player != null) {
	            GlStateManager.pushMatrix();
	            RenderUtils.rectangleBordered(x+0.0, y+0.0, x+125.0, y+36.0, 0.5, Colors.getColor(0, 150), Colors.getColor(255));
	            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(player.getName(), x+38.0f, y+2.0f, -1);
	       
	            BigDecimal bigDecimal = new BigDecimal((double)player.getHealth());
	    		bigDecimal = bigDecimal.setScale(1, RoundingMode.HALF_UP);
	    		double HEALTH = bigDecimal.doubleValue();
	    		
	            BigDecimal DT = new BigDecimal((double)Minecraft.getMinecraft().player.getDistanceToEntity(player));
	    		DT = DT.setScale(1, RoundingMode.HALF_UP);
	    		double Dis = DT.doubleValue();
	    		
	            final float health = player.getHealth();
	            final float[] fractions = { 0.0f, 0.5f, 1.0f };
	            final Color[] colors = { Color.RED, Color.YELLOW, Color.GREEN };
	            final float progress = health / player.getMaxHealth();
	            final Color customColor = (health >= 0.0f) ? blendColors(fractions, colors, progress).brighter() : Color.RED;
	            double width = (double)Minecraft.getMinecraft().fontRendererObj.getStringWidth(player.getName());
	            width = MathUtils.getIncremental(width, 10.0);
	            if (width < 50.0) {
	                width = 50.0;
	            }
	            final double healthLocation = width * progress;
	            RenderUtils.drawRect(x+37.5, y+9.5, x+38.0 + healthLocation + 0.5, y+16.5, customColor.getRGB());
	            RenderUtils.rectangleBordered(x+37.0, y+9.0, x+39.0 + width, y+17.0, 0.5, Colors.getColor(0, 0), Colors.getColor(0));
	            for (int i = 1; i < 10; ++i) {
	                final double dThing = width / 10.0 * i;
	         //       RenderUtils.drawRect(x+38.0 + dThing, y+10.0, x+38.0 + dThing + 0.5, y+16.0, Colors.getColor(0));
	            }
	            String COLOR1;
	            if (health > 20.0D) {
	               COLOR1 = " \2479";
	            } else if (health >= 10.0D) {
	               COLOR1 = " \247a";
	            } else if (health >= 3.0D) {
	               COLOR1 = " \247e";
	            } else {
	               COLOR1 = " \2474";
	            }
	            
	            
	            RotationUtils2.drawEntityOnScreen(x +18, y +32, 14, 0.0f, 9.0f, player);
	            renderStuffStatus(res);
	            GlStateManager.scale(0.5, 0.5, 0.5);
	            final String str = "HP: "+COLOR1 + HEALTH + "\247r | Dist: " + Dis ;
	            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(str, x*2+76.0f, y*2+35.0f, -1);
	            final String str2 = String.format("Yaw: %s Pitch: %s BodyYaw: %s", (int)player.rotationYaw, (int)player.rotationPitch, (int)player.renderYawOffset);
	        //    Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(str2, x*2+76.0f, y*2+47.0f, -1);
	            final String str3 = String.format("G: %s HURT: %s TE: %s", player.onGround, player.hurtTime, player.ticksExisted);
	       //     Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(str3, x*2+76.0f, y*2+59.0f, -1);

	            GlStateManager.scale(2.0f, 2.0f, 2.0f);
	            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
	            GlStateManager.enableAlpha();
	            GlStateManager.enableBlend();
	            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
	       

	            
	            GlStateManager.popMatrix();
	        }
		}
		 public static int[] getFractionIndicies(final float[] fractions, final float progress) {
		        final int[] range = new int[2];
		        int startPoint;
		        for (startPoint = 0; startPoint < fractions.length && fractions[startPoint] <= progress; ++startPoint) {}
		        if (startPoint >= fractions.length) {
		            startPoint = fractions.length - 1;
		        }
		        range[0] = startPoint - 1;
		        range[1] = startPoint;
		        return range;
		    }
		 public static Color blendColors(final float[] fractions, final Color[] colors, final float progress) {
		        Color color = null;
		        if (fractions == null) {
		            throw new IllegalArgumentException("Fractions can't be null");
		        }
		        if (colors == null) {
		            throw new IllegalArgumentException("Colours can't be null");
		        }
		        if (fractions.length == colors.length) {
		            final int[] indicies = getFractionIndicies(fractions, progress);
		            final float[] range = { fractions[indicies[0]], fractions[indicies[1]] };
		            final Color[] colorRange = { colors[indicies[0]], colors[indicies[1]] };
		            final float max = range[1] - range[0];
		            final float value = progress - range[0];
		            final float weight = value / max;
		            color = blend(colorRange[0], colorRange[1], 1.0f - weight);
		            return color;
		        }
		        throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
		    }
		 public static Color blend(final Color color1, final Color color2, final double ratio) {
		        final float r = (float)ratio;
		        final float ir = 1.0f - r;
		        final float[] rgb1 = new float[3];
		        final float[] rgb2 = new float[3];
		        color1.getColorComponents(rgb1);
		        color2.getColorComponents(rgb2);
		        float red = rgb1[0] * r + rgb2[0] * ir;
		        float green = rgb1[1] * r + rgb2[1] * ir;
		        float blue = rgb1[2] * r + rgb2[2] * ir;
		        if (red < 0.0f) {
		            red = 0.0f;
		        }
		        else if (red > 255.0f) {
		            red = 255.0f;
		        }
		        if (green < 0.0f) {
		            green = 0.0f;
		        }
		        else if (green > 255.0f) {
		            green = 255.0f;
		        }
		        if (blue < 0.0f) {
		            blue = 0.0f;
		        }
		        else if (blue > 255.0f) {
		            blue = 255.0f;
		        }
		        Color color3 = null;
		        try {
		            color3 = new Color(red, green, blue);
		        }
		        catch (IllegalArgumentException exp) {
		            final NumberFormat nf = NumberFormat.getNumberInstance();
		            System.out.println(nf.format((double)red) + "; " + nf.format((double)green) + "; " + nf.format((double)blue));
		            exp.printStackTrace();
		        }
		        return color3;
		    }
		 
		   public static void renderStuffStatus(ScaledResolution sr){
			   final EntityLivingBase player =  KillAura.target;
		        if (!(KillAura.target instanceof EntityPlayer)) 
		        	return;
		    	GL11.glPushMatrix();
		        ArrayList<ItemStack> stuff = new ArrayList<ItemStack>();
		        boolean onwater =  player.isEntityAlive() && player.isInsideOfMaterial(Material.WATER);
		    	int x = sr.getScaledWidth() /2+20;
		        int y = sr.getScaledHeight()/2+60;

		        /*for (int index = 3; index >= 0; --index) {
		            Object armer = ((EntityPlayer)player).inventory.armorInventory[index];
		            if (armer == null) continue;
		            stuff.add(armer);
		        }
		        if (((EntityPlayer) player).getHeldEquipment() != null) {
		            stuff.add(((EntityPlayer) player).getHeldEquipment());
		        */
		        
		        for (ItemStack errything : stuff) {
		            if (mc.world != null) {
		               RenderHelper.enableGUIStandardItemLighting();
		                x += 16;        
		            }
		            GlStateManager.pushMatrix();
		            GlStateManager.disableAlpha();
		            GlStateManager.clear(256);
		            mc.getRenderItem().zLevel = -150.0f;
		            mc.getRenderItem().renderItemAndEffectIntoGUI(errything, x, y-(onwater ? 65 : mc.playerController.shouldDrawHUD()? 40:40));
		            mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, errything, x , y-(onwater ? 65 : mc.playerController.shouldDrawHUD()? 40:40));
		            mc.getRenderItem().zLevel = 0.0f;
		            GlStateManager.disableBlend();
		            GlStateManager.scale(0.5, 0.5, 0.5);
		            GlStateManager.disableDepth();
		            GlStateManager.disableLighting();
		            GlStateManager.enableDepth();
		            GlStateManager.scale(2.0f, 2.0f, 2.0f);
		            GlStateManager.enableAlpha();
		            GlStateManager.popMatrix();
		            errything.getEnchantmentTagList();
		        }
		        GL11.glPopMatrix();
		    
		   }
}