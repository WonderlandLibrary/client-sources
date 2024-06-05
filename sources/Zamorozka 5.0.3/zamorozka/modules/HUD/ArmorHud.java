package zamorozka.modules.HUD;

import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventRender2D;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class ArmorHud extends Module {

	public boolean on;
	
	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("Ez", this, true));	
	}
	
	public ArmorHud() {
		super("ArmorHud", 0, Category.Hud);
	}
	
	private static final RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();

 	@EventTarget
	public void renderOverlay(EventRender2D event) {
		if(on) {
	    GlStateManager.enableTexture2D();
	
	    ScaledResolution resolution = new ScaledResolution(mc);
	    int i = resolution.getScaledWidth() / 2;
	    int iteration = 0;
	    int y = resolution.getScaledHeight() - 55 - (mc.player.isInWater() ? 10 : 0);
	    for (ItemStack is : mc.player.inventory.armorInventory) {
	        iteration++;
	        if (is.func_190926_b()) continue;
	        int x = i - 90 + (9 - iteration) * 24 - 25;
	        GlStateManager.enableDepth();
	        itemRender.zLevel = 200F;
	        itemRender.renderItemAndEffectIntoGUI(is, x, y);
	        if(Zamorozka.settingsManager.getSettingByName("Ez").getValBoolean()) {
	        itemRender.renderItemOverlayIntoGUI(mc.fontRendererObj, is, x, y, "ez");
	        }else {
	        	itemRender.renderItemOverlayIntoGUI(mc.fontRendererObj, is, x, y, "");
	        }
	        itemRender.zLevel = 0F;
	
	        GlStateManager.enableTexture2D();
	        GlStateManager.disableLighting();
	        GlStateManager.disableDepth();
	
	        mc.fontRendererObj.drawStringWithShadow("S", x + 19 - 2 - mc.fontRendererObj.getStringWidth("S"), y + 9, 0xffffffff);
	        float green = ((float) is.getMaxDamage() - (float) is.getItemDamage()) / (float) is.getMaxDamage();
	        float red = 1 - green;
	        int dmg = 100 - (int) (red * 100);
	        mc.fontRendererObj.drawStringWithShadow(dmg + "" + "%", x + 8 - mc.fontRendererObj.getStringWidth(dmg + "" + "%") / 2, y - 8, 0xffffffff);
	    }
	
	    GlStateManager.enableDepth();
	    GlStateManager.disableLighting();
		}
	}
 	
 	public void onEnable() {
 		super.onEnable();
 		on = true;
 	}
 	
 	public void onDisable() {
 		super.onDisable();
 		on = false;
 	}

}