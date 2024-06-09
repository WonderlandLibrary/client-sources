package us.dev.direkt.module.internal.render;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import us.dev.api.property.BoundedProperty;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.EventGameTick;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.direkt.module.annotations.traits.TransientStatus;
import us.dev.direkt.module.property.annotations.Exposed;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

@ModData(label = "X-Ray", aliases = {"xray", "wallhack"}, category = ModCategory.RENDER)
@TransientStatus
public class Xray extends ToggleableModule {

    @Exposed(description = "The opacity of all unselected blocks")
    private BoundedProperty<Integer> opacity = new BoundedProperty<>("Opacity", 0, 55, 255);

	private static ImmutableSet<Block> xrayBlocks = new ImmutableSet.Builder<Block>()
            .add(Blocks.DIAMOND_ORE)
            .add(Blocks.GOLD_ORE)
            .add(Blocks.REDSTONE_ORE)
            .add(Blocks.LIT_REDSTONE_ORE)
            .add(Blocks.IRON_ORE)
            .add(Blocks.COAL_ORE)
            .add(Blocks.EMERALD_ORE)
            .add(Blocks.LAPIS_ORE)
            .add(Blocks.TNT)
            .add(Blocks.COMMAND_BLOCK)
            .add(Blocks.CHAIN_COMMAND_BLOCK)
            .add(Blocks.REPEATING_COMMAND_BLOCK)

            //.add(Blocks.WATER)
            //.add(Blocks.FLOWING_WATER)
            .build();

	private float saveGamma;
	private int lastOpcatiy;
	
	@Override
	public void onEnable(){
		this.lastOpcatiy = this.opacity.getValue();
		this.saveGamma = Wrapper.getGameSettings().gammaSetting;
		Wrapper.getGameSettings().gammaSetting = 10F;
		Wrapper.getMinecraft().renderGlobal.loadRenderers();
	}
	
	@Listener
	protected Link<EventGameTick> onGameTick = new Link<>(event -> {
		if(this.lastOpcatiy != this.opacity.getValue()){
			this.lastOpcatiy = this.opacity.getValue();
			Wrapper.getMinecraft().renderGlobal.loadRenderers();
		}
	});
	
	@Override
	public void onDisable(){
		Wrapper.getGameSettings().gammaSetting = this.saveGamma;
		Wrapper.getMinecraft().renderGlobal.loadRenderers();
	}
	
	public static ImmutableSet<Block> getXrayBlocks(){
		return xrayBlocks;
	}
	
}
