package zamorozka.modules.GUIMenu;

import java.awt.Color;

import de.Hero.settings.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.item.ItemStack;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventRender2D;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.RenderUtils;

public class InvViewer extends Module {

	
	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("Horizontal", this, 30, 0, 1000, true));
		Zamorozka.settingsManager.rSetting(new Setting("Vertical", this, 30, 0, 1000, true));
	}
	public InvViewer() {
		super("InvViewer",0,Category.Hud);
	}
	
	@EventTarget
	public void onRender(EventRender2D event) {
		int x =  (int) Zamorozka.settingsManager.getSettingByName("Horizontal").getValDouble();
		int y = (int) Zamorozka.settingsManager.getSettingByName("Vertical").getValDouble();
		GlStateManager.pushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        RenderUtils.drawRectStatic(x, y, x + 145, y + 48, new Color(0, 0, 0, 125));
        for (int i = 0; i < 27; i++) {
            final ItemStack itemStack = mc.player.inventory.mainInventory.get(i + 9);
            int offsetX = (x + (i % 9) * 16);
            int offsetY = (y + (i / 9) * 16);
            mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, offsetX, offsetY);
            mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRendererObj, itemStack, offsetX, offsetY, null);
        }

        RenderHelper.disableStandardItemLighting();
        mc.getRenderItem().zLevel = 0.0F;
        GlStateManager.popMatrix();
    }
}