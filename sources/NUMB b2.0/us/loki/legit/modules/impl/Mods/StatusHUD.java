package us.loki.legit.modules.impl.Mods;

import java.util.Collection;
import java.util.Iterator;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import us.loki.legit.Client;
import us.loki.legit.modules.*;

public class StatusHUD extends Module {
	
	private final ResourceLocation inventoryBackground = new ResourceLocation("textures/gui/container/inventory.png");
    
    Minecraft mc = Minecraft.getMinecraft();

	public StatusHUD() {
		super("StatusHUD", "StatusHUD", Keyboard.KEY_NONE, Category.MODS);
		Client.instance.setmgr.rSetting(new Setting("Text", this, false));
		Client.instance.setmgr.rSetting(new Setting("Icon", this, false));
	}
	public void render2D() {
		GlStateManager.enableDepth();
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(0, -1, 0);
		GlStateManager.scale(2f, 2f, 2f);
		if (Client.instance.setmgr.getSettingByName("Icon").getValBoolean()){
			int var1 = (Display.getWidth()/2)-70;
	        boolean var3 = true;
	        Collection var4 = this.mc.thePlayer.getActivePotionEffects();
	        int var2 = (Display.getHeight()/2)-(var4.size()*20)-90;

	        if (!var4.isEmpty())
	        {
	            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	            GlStateManager.disableLighting();
	            int var5 = 20;

	            for (Iterator var6 = this.mc.thePlayer.getActivePotionEffects().iterator(); var6.hasNext(); var2 += var5)
	            {
	                PotionEffect var7 = (PotionEffect)var6.next();
	                Potion var8 = Potion.potionTypes[var7.getPotionID()];
	                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	                mc.getTextureManager().bindTexture(inventoryBackground);

	                if (var8.hasStatusIcon())
	                {
	                    int var9 = var8.getStatusIconIndex();
	                    this.drawTexturedModalRect(var1 + 6, var2 + 7, 0 + var9 % 8 * 18, 198 + var9 / 8 * 18, 18, 18);
	                }

	                String var11 = "";//I18n.format(var8.getName(), new Object[0]);

	                if (var7.getAmplifier() == 1)
	                {
	                    var11 = var11 + " " + I18n.format("enchantment.level.2", new Object[0]);
	                }
	                else if (var7.getAmplifier() == 2)
	                {
	                    var11 = var11 + " " + I18n.format("enchantment.level.3", new Object[0]);
	                }
	                else if (var7.getAmplifier() == 3)
	                {
	                    var11 = var11 + " " + I18n.format("enchantment.level.4", new Object[0]);
	                }

	                mc.fontRendererObj.drawStringWithShadow(var11, (float)(var1-8), (float)(var2+12), 16777215);
	                String var10 = Potion.getDurationString(var7);
	                mc.fontRendererObj.drawStringWithShadow(var10, (float)(var1 + 10 + 18), (float)(var2+12), 16777215);
	            }
	        }
		}
	GlStateManager.popMatrix();
	GlStateManager.enableAlpha();
	GlStateManager.disableDepth();
	}
	public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
        float var7 = 0.00390625F;
        float var8 = 0.00390625F;
        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer var10 = var9.getWorldRenderer();
        var10.startDrawingQuads();
        var10.addVertexWithUV((double)(x + 0), (double)(y + height), 0, (double)((float)(textureX + 0) * var7), (double)((float)(textureY + height) * var8));
        var10.addVertexWithUV((double)(x + width), (double)(y + height), 0, (double)((float)(textureX + width) * var7), (double)((float)(textureY + height) * var8));
        var10.addVertexWithUV((double)(x + width), (double)(y + 0), 0, (double)((float)(textureX + width) * var7), (double)((float)(textureY + 0) * var8));
        var10.addVertexWithUV((double)(x + 0), (double)(y + 0), 0, (double)((float)(textureX + 0) * var7), (double)((float)(textureY + 0) * var8));
        var9.draw();
    }

}