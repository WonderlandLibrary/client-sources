package zamorozka.modules.TRAFFIC;

import java.util.ArrayList;

import de.Hero.settings.Setting;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.ClientUtils;
import zamorozka.ui.MovementUtilis;
import zamorozka.ui.PlayerUtil;

public class ElytraFly extends Module{

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("ElytraSpeedXZ", this, 2, 0.5, 20, true));
		Zamorozka.settingsManager.rSetting(new Setting("ElytraSpeedY", this, 5, 0.1, 20, true));
	}
	
	public ElytraFly() {
		super("ElytraFly", 0, Category.TRAFFIC);
	}  
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
        if (mc.player.isElytraFlying()) {
            mc.player.setVelocity(0.0, 0.0, 0.0);
            mc.player.motionY = 0;
            mc.player.setPosition(ElytraFly.mc.player.posX, ElytraFly.mc.player.posY - 5.0000002374872565E-5, ElytraFly.mc.player.posZ);
            float ff = (float) Zamorozka.settingsManager.getSettingByName("ElytraSpeedXZ").getValDouble();
            if(mc.gameSettings.keyBindSneak.isKeyDown()) {
            	ff = (float) Zamorozka.settingsManager.getSettingByName("ElytraSpeedY").getValDouble();
            mc.player.motionY = - ff;
            }
            if(mc.gameSettings.keyBindJump.isKeyDown()) {
            	ff =  (float) Zamorozka.settingsManager.getSettingByName("ElytraSpeedY").getValDouble();
            mc.player.motionY =	ff;
            }
            if(mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown()) {
            	ff = (float) Zamorozka.settingsManager.getSettingByName("ElytraSpeedXZ").getValDouble();
            	MovementUtilis.setMotion(ff);
            }
            if(mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown()) {
            	ff = (float) Zamorozka.settingsManager.getSettingByName("ElytraSpeedXZ").getValDouble();
            	MovementUtilis.setMotion(ff);
            }
        }
    }
	
	@Override
	public void onDisable() {
        ElytraFly.mc.player.capabilities.isFlying = false;
        ElytraFly.mc.player.capabilities.setFlySpeed(0.05f);
        if (!ElytraFly.mc.player.capabilities.isCreativeMode) {
            ElytraFly.mc.player.capabilities.allowFlying = false;
        }
		super.onDisable();
	}
	
}