/**
 * 
 */
package cafe.kagu.kagu.mods.impl.visual;

import java.awt.Color;
import java.util.HashMap;

import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventCheatProcessTick;
import cafe.kagu.kagu.eventBus.impl.EventPacketReceive;
import cafe.kagu.kagu.eventBus.impl.EventSettingUpdate;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.settings.impl.BooleanSetting;
import cafe.kagu.kagu.settings.impl.IntegerSetting;
import cafe.kagu.kagu.settings.impl.ModeSetting;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.Sys;

/**
 * @author lavaflowglow
 *
 */
public class ModAmbience extends Module {
	
	public ModAmbience() {
		super("Ambience", Category.VISUAL);
		setSettings(blockLighting, worldTime, disableNightVision, rainbow, customSkyColor, skyRed, skyGreen, skyBlue,
				customBlockColorMult, blockMultRed, blockMultGreen, blockMultBlue, customBlockColor, blockColorRed, 
				blockColorGreen, blockColorBlue);
		for (ResourceLocation key : Block.blockRegistry.getKeys()) {
			lightValues.put(Block.blockRegistry.getObject(key), Block.blockRegistry.getObject(key).getLightValue());
		}
		blockMultRed.setValue(blockMultRed.getValue()); // Force refresh because setting change event isn't triggered when the object is created
		blockColorRed.setValue(blockColorRed.getValue()); // Force refresh because setting change event isn't triggered when the object is created
	}
	
	private ModeSetting blockLighting = new ModeSetting("Block Lighting", "Unchanged", "Unchanged", "Midnight", "Dusk", "Day");
	private ModeSetting worldTime = new ModeSetting("World Time", "Unchanged", "Unchanged", "Midnight", "Dusk", "Day");
	private BooleanSetting disableNightVision = new BooleanSetting("Disable Night Vision", true);
	private BooleanSetting rainbow = new BooleanSetting("Rainbow", false);
	private BooleanSetting customSkyColor = new BooleanSetting("Custom Sky Color", true);
	private IntegerSetting skyRed = new IntegerSetting("Sky R", 213, 0, 255, 1).setDependency(customSkyColor::isEnabled);
	private IntegerSetting skyGreen = new IntegerSetting("Sky G", 140, 0, 255, 1).setDependency(customSkyColor::isEnabled);
	private IntegerSetting skyBlue = new IntegerSetting("Sky B", 255, 0, 255, 1).setDependency(customSkyColor::isEnabled);
	private BooleanSetting customBlockColorMult = new BooleanSetting("Custom Plant Color", true);
	private IntegerSetting blockMultRed = new IntegerSetting("Block Tint R", 213, 0, 255, 1).setDependency(customBlockColorMult::isEnabled);
	private IntegerSetting blockMultGreen = new IntegerSetting("Block Tint G", 140, 0, 255, 1).setDependency(customBlockColorMult::isEnabled);
	private IntegerSetting blockMultBlue = new IntegerSetting("Block Tint B", 255, 0, 255, 1).setDependency(customBlockColorMult::isEnabled);
	private BooleanSetting customBlockColor = new BooleanSetting("Custom Block Color", true);
	private IntegerSetting blockColorRed = new IntegerSetting("Block Color R", 165, 0, 255, 1).setDependency(customBlockColor::isEnabled);
	private IntegerSetting blockColorGreen = new IntegerSetting("Block Color G", 224, 0, 255, 1).setDependency(customBlockColor::isEnabled);
	private IntegerSetting blockColorBlue = new IntegerSetting("Block Color B", 254, 0, 255, 1).setDependency(customBlockColor::isEnabled);
	
	private HashMap<Block, Integer> lightValues = new HashMap<>();
	private float[] customBlockMult = new float[] {1, 1, 1}, blockColor = new float[] {1, 1, 1};
	private int[] lastPlantColorValues = new int[] {-1, -1, -1}, lastBlockColorValues = new int[] {-1, -1, -1};
	
	@Override
	public void onEnable() {
		for (Block block : lightValues.keySet()) {
			switch (blockLighting.getMode()) {
        		case "Unchanged":block.setLightValue(lightValues.get(block));break;
        		case "Midnight":block.setLightValue(0);break;
        		case "Dusk":block.setLightValue(8);break;
        		case "Day":block.setLightValue(15);break;
			}
		}
		customBlockMult[0] = blockMultRed.getValue() / 255f;
		customBlockMult[1] = blockMultGreen.getValue() / 255f;
		customBlockMult[2] = blockMultBlue.getValue() / 255f;
		blockColor[0] = blockColorRed.getValue() / 255f;
		blockColor[1] = blockColorGreen.getValue() / 255f;
		blockColor[2] = blockColorBlue.getValue() / 255f;
		mc.renderGlobal.loadRenderers();
	}
	
	@Override
	public void onDisable() {
		for (Block block : lightValues.keySet()) {
			block.setLightValue(lightValues.get(block));
		}
		mc.renderGlobal.loadRenderers();
	}

	@EventHandler
	private Handler<EventCheatProcessTick> onCheatTick = e -> {
		if (rainbow.isDisabled() || e.isPost())
			return;

		float hue = (System.currentTimeMillis() % 720) / 720f;
		Color color = new Color(Color.HSBtoRGB(hue, 0.5f, 1.0f));

		skyRed.setValue(color.getRed());
		skyGreen.setValue(color.getGreen());
		skyBlue.setValue(color.getBlue());

		blockColorRed.setValue(color.getRed());
		blockColorGreen.setValue(color.getGreen());
		blockColorBlue.setValue(color.getBlue());

		blockMultRed.setValue(color.getRed());
		blockMultGreen.setValue(color.getGreen());
		blockMultBlue.setValue(color.getBlue());
	};

	@EventHandler
	private Handler<EventSettingUpdate> onSettingUpdate = e -> {
		if (e.getSetting() == blockLighting) {
			for (Block block : lightValues.keySet()) {
				switch (blockLighting.getMode()) {
	        		case "Unchanged":block.setLightValue(lightValues.get(block));break;
	        		case "Midnight":block.setLightValue(0);break;
	        		case "Dusk":block.setLightValue(8);break;
	        		case "Day":block.setLightValue(15);break;
				}
			}
			mc.renderGlobal.loadRenderers();
		}
		else if (e.getSetting() == customBlockColorMult || e.getSetting() == blockMultRed 
				|| e.getSetting() == blockMultGreen || e.getSetting() == blockMultBlue) {
			customBlockMult[0] = blockMultRed.getValue() / 255f;
			customBlockMult[1] = blockMultGreen.getValue() / 255f;
			customBlockMult[2] = blockMultBlue.getValue() / 255f;
			if (lastPlantColorValues[0] != blockMultRed.getValue() 
					|| lastPlantColorValues[1] != blockMultGreen.getValue() 
					|| lastPlantColorValues[2] != blockMultBlue.getValue() 
					|| e.getSetting() == customBlockColorMult) {
				mc.renderGlobal.loadRenderers();
				lastPlantColorValues = new int[] {blockMultRed.getValue(), blockMultGreen.getValue(), blockMultBlue.getValue()};
			}
		}
		else if (e.getSetting() == customBlockColor || e.getSetting() == blockColorRed 
				|| e.getSetting() == blockColorGreen || e.getSetting() == blockColorBlue) {
			blockColor[0] = blockColorRed.getValue() / 255f;
			blockColor[1] = blockColorGreen.getValue() / 255f;
			blockColor[2] = blockColorBlue.getValue() / 255f;
			if (lastBlockColorValues[0] != blockColorRed.getValue() 
					|| lastBlockColorValues[1] != blockColorGreen.getValue() 
					|| lastBlockColorValues[2] != blockColorBlue.getValue() 
					|| e.getSetting() == customBlockColor) {
				mc.renderGlobal.loadRenderers();
				lastBlockColorValues = new int[] {blockColorRed.getValue(), blockColorGreen.getValue(), blockColorBlue.getValue()};
			}
		}
	};
	
	@EventHandler
	private Handler<EventPacketReceive> onPacketReceive = e -> {
		if (e.isPost() || worldTime.is("Unchanged") || !(e.getPacket() instanceof S03PacketTimeUpdate))
			return;
		e.cancel();
	};
	
	/**
	 * @return the blockLighting
	 */
	public ModeSetting getBlockLighting() {
		return blockLighting;
	}
	
	/**
	 * @return the worldTime
	 */
	public ModeSetting getWorldTime() {
		return worldTime;
	}
	
	/**
	 * @return the disableNightVision
	 */
	public BooleanSetting getDisableNightVision() {
		return disableNightVision;
	}
	
	/**
	 * @return the customSkyColor
	 */
	public BooleanSetting getCustomSkyColor() {
		return customSkyColor;
	}
	
	/**
	 * @return the skyRed
	 */
	public IntegerSetting getSkyRed() {
		return skyRed;
	}
	
	/**
	 * @return the skyGreen
	 */
	public IntegerSetting getSkyGreen() {
		return skyGreen;
	}
	
	/**
	 * @return the skyBlue
	 */
	public IntegerSetting getSkyBlue() {
		return skyBlue;
	}
	
	/**
	 * @return the customBlockColorMult
	 */
	public BooleanSetting getCustomBlockColorMult() {
		return customBlockColorMult;
	}
	
	/**
	 * @return the customBlockMult
	 */
	public float[] getCustomBlockMult() {
		return customBlockMult;
	}
	
	/**
	 * @return the customBlockColor
	 */
	public BooleanSetting getCustomBlockColor() {
		return customBlockColor;
	}
	
	/**
	 * @return the blockColor
	 */
	public float[] getBlockColor() {
		return blockColor;
	}
	
}
