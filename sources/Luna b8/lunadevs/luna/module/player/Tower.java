package lunadevs.luna.module.player;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;
import lunadevs.luna.module.movement.Scaffold.BlockData;
import lunadevs.luna.utils.TimeHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class Tower extends Module {

	private boolean active;
	private List<Block> invalid = Arrays.asList(new Block[] { Blocks.air, Blocks.water, Blocks.fire, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava });
	  private TimeHelper timer = new TimeHelper();
	  private BlockData blockData;

	public Tower() {
		super("Tower", Keyboard.KEY_NONE, Category.PLAYER, true);
	}

	@Override
	public void onUpdate() {
		if (!this.isEnabled) return;
		    	if (this.blockData != null) {
		            if (this.timer.hasReached(65L)) {
		                mc.rightClickDelayTimer = 0;
		                if (mc.gameSettings.keyBindJump.getIsKeyPressed()) {
		                    mc.thePlayer.motionY = 0.42;
		                    if (timer.hasReached(1500)) {
		                        mc.thePlayer.motionY = -0.28;
		                        timer.reset();
		                        if (timer.hasReached(2L)) {
		                            mc.thePlayer.motionY = 0.42;
		                        }}		                    }
		                }
		            }
		    	super.onUpdate();
		    }

	
	@Override
	public void onEnable() {
		active=true;
	}
	@Override
	public void onDisable() {
		active=false;
		super.onDisable();
	}

	@Override
	public String getValue() {
		return "Jump";
	}

}
