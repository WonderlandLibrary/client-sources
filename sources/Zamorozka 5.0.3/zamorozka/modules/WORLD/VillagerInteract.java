package zamorozka.modules.WORLD;

import java.util.ArrayList;

import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemShears;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.Village;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class VillagerInteract extends Module {
	
	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("HealthAmount", this, 7.5, 1, 20, true));
	}
	
	public VillagerInteract() {
		super("VillagerInteract", 0, Category.WORLD);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
            for(Entity e : mc.world.loadedEntityList) {
                if(e instanceof EntityVillager) {
                    EntityVillager villager = (EntityVillager) e;
                        if (mc.player.getDistanceToEntity(villager) <= 7f && mc.player.getHealth()<=(float)Zamorozka.settingsManager.getSettingByName("HealthAmount").getValDouble()) {
                        	mc.playerController.interactWithEntity(mc.player, villager, EnumHand.MAIN_HAND);
                        }
                }
            }
	}
}