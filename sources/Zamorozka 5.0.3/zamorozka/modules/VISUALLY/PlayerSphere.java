package zamorozka.modules.VISUALLY;

import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.RenderUtils2;
import zamorozka.ui.Wrapper;

public class PlayerSphere extends Module {

	public PlayerSphere() {
		super("PlayerSphere", Keyboard.KEY_NONE, Category.VISUALLY);
	}
	
	@Override
    public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("Size", this, 2, 1, 10, true));
	}
	
	@Override
	public void onRender() {
		if(getState()) {
			
			for(Entity ep : Wrapper.getWorld().loadedEntityList) {
				if(ep instanceof EntityPlayerSP) continue;
				if(ep instanceof EntityPlayer) {
					double d = ep.lastTickPosX + (ep.posX - ep.lastTickPosX) * (double)Wrapper.getMinecraft().timer.renderPartialTicks;
					double d1 = ep.lastTickPosY + (ep.posY - ep.lastTickPosY) * (double)Wrapper.getMinecraft().timer.renderPartialTicks;
					double d2 = ep.lastTickPosZ + (ep.posZ - ep.lastTickPosZ) * (double)Wrapper.getMinecraft().timer.renderPartialTicks;
				
					RenderUtils2.drawSphere(d, d1, d2,  (float) Zamorozka.instance.settingsManager.getSettingByName("Size").getValDouble(), 20, 15);
				}
			}
		
	}
	}
}