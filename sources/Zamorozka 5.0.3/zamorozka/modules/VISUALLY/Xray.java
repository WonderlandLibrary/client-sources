package zamorozka.modules.VISUALLY;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import de.Hero.settings.Setting;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.BiomeCache.Block;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.XrayUtils;

public class Xray extends Module{
	
	public ArrayList<Block> xrayBlocks = new ArrayList<Block>();
	
	public Xray(){
		super("X-Ray", 0, Category.VISUALLY);
	}
    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Enable");
        options.add("Disable");
        Zamorozka.settingsManager.rSetting(new Setting("FullBright", this, "Enable", options));    
    }
    
	
	public void onEnable(){
		XrayUtils.isXRay = true;
		mc.renderGlobal.loadRenderers();
		if(Zamorozka.settingsManager.getSettingByName("FullBright").getValString().equalsIgnoreCase("Enable")){
		if (this.getState()) {
			mc.gameSettings.gammaSetting = 10f;
		} else {
			mc.gameSettings.gammaSetting = 1f;
		}
		}else{
			return;
		}
	}
	
	public void onDisable(){
		XrayUtils.isXRay = false;
		mc.renderGlobal.loadRenderers();
		if(Zamorozka.settingsManager.getSettingByName("FullBright").getValString().equalsIgnoreCase("Disable")){
		if (this.getState()) {
			mc.gameSettings.gammaSetting = 1f;
		}
		}else{
			return;
		}
	}
	
	public boolean isXrayBlock(Block blockToCheck){
		if(this.xrayBlocks.contains(blockToCheck)){
			return true;
		}
		return false;
	}

}