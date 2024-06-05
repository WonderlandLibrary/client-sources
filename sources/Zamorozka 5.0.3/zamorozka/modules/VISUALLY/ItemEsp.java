package zamorozka.modules.VISUALLY;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventRender3D;
import zamorozka.event.events.RenderEvent3D;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.RenderUtils;
import zamorozka.ui.RenderUtils2;

public class ItemEsp extends Module {

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("ItemRenderDistance", this, 50, 1, 100, true));
	}

	public ItemEsp() {
		super("ItemEsp", 0, Category.VISUALLY);
	}
	
	@EventTarget
	public void onRender(EventRender3D event) {
		if (!getState()) {
			return;
		}
		for (Entity entities : mc.world.loadedEntityList) {
			if (entities instanceof EntityItem) {
				double ranggg = Zamorozka.settingsManager.getSettingByName("ItemRenderDistance").getValDouble();
				if (mc.player.getDistanceToEntity(entities) <= ranggg)
					RenderUtils2.drawEntityESP(entities, Zamorozka.getClientColors());
			}
		}
	}
}