package zamorozka.modules.COMBAT;

import java.util.ArrayList;
import java.util.Random;

import de.Hero.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCompressedPowered;
import net.minecraft.block.BlockRedSandstone;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.client.CPacketUseEntity;
import zamorozka.event.EventTarget;
import zamorozka.event.events.AttackEvent;
import zamorozka.event.events.EventAttack;
import zamorozka.event.events.EventSendPacket;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class SpawnParticles extends Module {

	@Override
	public void setup() {
    	ArrayList<String> crack = new ArrayList<>();
        Zamorozka.settingsManager.rSetting(new Setting("Crack Mode", this, "Blood", crack));
        crack.add("Enchant");
        crack.add("Criticals");
        crack.add("Blood");
        Zamorozka.settingsManager.rSetting(new Setting("CrackSize", this, 5, 1, 40, true));
	}
	public SpawnParticles() {
		super("SpawnParticles", 0, Category.COMBAT);
	}
	
	@EventTarget
	public void onEnable(EventUpdate event) {
		String mode = Zamorozka.instance.settingsManager.getSettingByName("Crack Mode").getValString();
		String modeput = Character.toUpperCase(mode.charAt(0)) + mode.substring(1);
		this.setDisplayName("SpawnParticles §f§" + " " + modeput);
	}
	
	@EventTarget
    public void onSend(AttackEvent event) {
		double crack = Zamorozka.settingsManager.getSettingByName("CrackSize").getValDouble();
		String mode = Zamorozka.instance.settingsManager.getSettingByName("Crack Mode").getValString();
                for (int i = 0; i < crack; i++) {
                    if (mode.equalsIgnoreCase("Enchant")) {
                        mc.player.onEnchantmentCritical(event.getTargetEntity());
                    }
                    if(mode.equalsIgnoreCase("Criticals")) {
                    	mc.player.onCriticalHit(event.getTargetEntity());
                    }
                    if (mode.equalsIgnoreCase("Blood")) {
                    Random random = new Random();
                    Entity target = event.getTargetEntity();
                    double i22;
                    for (i22 = 0.0D; i22 < target.height; i22 += 0.2D) {
                        for (int i1 = 0; i1 < crack; i1++) {
                            mc.effectRenderer.spawnEffectParticle(37, target.posX, target.posY + i22, target.posZ, ((random.nextInt(6) - 3) / 5), 0.1D, ((random.nextInt(6) - 3) / 5), Block.getIdFromBlock(Blocks.REDSTONE_BLOCK));
                        }
                    }
                    }
                }
	}
}