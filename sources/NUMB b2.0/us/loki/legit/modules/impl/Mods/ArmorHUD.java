package us.loki.legit.modules.impl.Mods;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import de.Hero.settings.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.item.ItemStack;
import us.loki.legit.Client;
import us.loki.legit.modules.*;
import us.loki.legit.utils.DrawUtils;

public class ArmorHUD extends Module {

	public ArmorHUD() {
		super("ArmorHUD", "ArmorHUD", Keyboard.KEY_NONE, Category.MODS);
		Client.instance.setmgr.rSetting(new Setting("Hotbar", this, false));
		Client.instance.setmgr.rSetting(new Setting("Urushibara (Side)", this, true));
		Client.instance.setmgr.rSetting(new Setting("Kilo (Near Hotbar)", this, false));
	}
	public void render2D() {
		GlStateManager.enableDepth();
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(0, -1, 0);
		GlStateManager.scale(2f, 2f, 2f);
		
		int hbw = 100*mc.gameSettings.guiScale;
		
		if (ModuleManager.getModuleByName("ArmorHUD").isEnabled() && Client.instance.setmgr.getSettingByName("Kilo (Near Hotbar)").getValBoolean()){
			DrawUtils.rectBorder(Display.getWidth()/4+(hbw/2)-2, Display.getHeight()/2-71, Display.getWidth()/4+(hbw/2)+83, Display.getHeight()/2-51, 0x88FFFFFF);
			DrawUtils.rect(Display.getWidth()/4+(hbw/2)-1, Display.getHeight()/2-70, Display.getWidth()/4+(hbw/2)+82, Display.getHeight()/2-52, 0x88010101);
			
	        RenderHelper.enableStandardItemLighting();
			
			ItemStack item = mc.thePlayer.getCurrentEquippedItem();
			if (item != null) {
				mc.getRenderItem().func_180450_b(item, Display.getWidth()/4+(hbw/2), Display.getHeight()/2-70);
			        GlStateManager.pushMatrix();
			        GlStateManager.scale(0.5f, 0.5f, 0.5f);
			        GlStateManager.disableLighting();
			        GlStateManager.disableDepth();
			        mc.fontRendererObj.drawString(item.getItemDamage()+"", ((Display.getWidth()/4+(hbw/2)+8)*2)-(mc.fontRendererObj.getStringWidth(item.getItemDamage()+"")/2), (Display.getHeight()/2-57)*2, -1);
			        GlStateManager.enableDepth();
			        GlStateManager.enableLighting();
			        GlStateManager.popMatrix();
			}
			for(int i = 0; i <= 3; i++) {
				ItemStack piece = mc.thePlayer.getCurrentArmor(3-i);
				mc.getRenderItem().func_180450_b(piece, Display.getWidth()/4+(hbw/2)+((i+1)*16), Display.getHeight()/2-70);
			        GlStateManager.pushMatrix();
			        GlStateManager.scale(0.5f, 0.5f, 0.5f);
			        GlStateManager.disableLighting();
			        GlStateManager.disableDepth();
			        mc.fontRendererObj.drawString(piece.getItemDamage()+"", ((Display.getWidth()/4+(hbw/2)+((i+1)*16)+8)*2)-(mc.fontRendererObj.getStringWidth(piece.getItemDamage()+"")/2), (Display.getHeight()/2-57)*2, -1);
			        GlStateManager.enableDepth();
			        GlStateManager.enableLighting();
			        GlStateManager.popMatrix();
			}
			
	        RenderHelper.disableStandardItemLighting();
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