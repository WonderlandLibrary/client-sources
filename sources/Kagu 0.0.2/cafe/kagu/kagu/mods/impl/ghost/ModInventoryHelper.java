/**
 * 
 */
package cafe.kagu.kagu.mods.impl.ghost;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventRender2D;
import cafe.kagu.kagu.eventBus.impl.EventRenderObs;
import cafe.kagu.kagu.eventBus.impl.EventTick;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.mods.ModuleManager;
import cafe.kagu.kagu.settings.impl.IntegerSetting;
import cafe.kagu.kagu.settings.impl.ModeSetting;
import cafe.kagu.kagu.utils.InventoryUtils;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;

/**
 * @author DistastefulBannock
 *
 */
public class ModInventoryHelper extends Module {
	
	public ModInventoryHelper() {
		super("InventoryHelper", Category.GHOST);
		setSettings(mode, red, green, blue, alpha);
	}
	
	private ModeSetting mode = new ModeSetting("Draw Type", "Fill", "Fill", "Outline");
	private IntegerSetting red = new IntegerSetting("Box R", 165, 0, 255, 1);
	private IntegerSetting green = new IntegerSetting("Box G", 224, 0, 255, 1);
	private IntegerSetting blue = new IntegerSetting("Box B", 254, 0, 255, 1);
	private IntegerSetting alpha = new IntegerSetting("Box A", 100, 0, 255, 1);
	
	@Override
	public void onEnable() {
		Kagu.getModuleManager().getModule(ModObsProofUi.class).enable();
		if (Kagu.getModuleManager().getModule(ModObsProofUi.class).isDisabled()) {
			toggle();
			return;
		}
		bestGearSlots = new ArrayList<>();
	}
	
	@Override
	public void onDisable() {
		bestGearSlots = new ArrayList<>();
	}
	
	private ArrayList<Integer[]> squares = new ArrayList<>(), bufferSquares = new ArrayList<>();
	private ArrayList<Slot> bestGearSlots = new ArrayList<>();
	
	@EventHandler
	private Handler<EventRenderObs> onRenderObs = e -> {
		if (e.isPost())
			return;
		Graphics2D graphics2d = e.getGraphics();
		ArrayList<Integer[]> squares = this.squares;
		
		// Draw all the squares
		graphics2d.setStroke(new BasicStroke(2));
		graphics2d.setColor(new Color(red.getValue(), green.getValue(), blue.getValue(), alpha.getValue()));
		boolean shouldFill = mode.is("Fill");
		for (Integer[] square : squares) {
			if (shouldFill)
				graphics2d.fillRect(square[0], square[1], square[2], square[3]);
			else
				graphics2d.drawRect(square[0], square[1], square[2], square[3]);
		}
		
	};
	
	@EventHandler
	private Handler<EventTick> onTick = e -> {
		if (e.isPost())
			return;
		
		ArrayList<Slot> slots = new ArrayList<Slot>();
		
		// Populate slot list
		if (mc.thePlayer.openContainer != null) {
			slots.addAll(mc.thePlayer.inventoryContainer.inventorySlots);
			slots.addAll(mc.thePlayer.openContainer.getInventorySlots());
//			Collections.reverse(slots);
		}else {
			slots.addAll(mc.thePlayer.inventoryContainer.getInventorySlots());
		}
//		Collections.reverse(slots);
		
		// If the player isn't in a screen then clear the square list because there will be nothing to reset it
		if (mc.getCurrentScreen() == null)
			squares.clear();
		
		// Get best gear from both the containers and then add them to the best gear slot array
		bestGearSlots = new ArrayList<>(Arrays.asList(InventoryUtils.getBestGearSetInInventory(slots, true, 0, 0, 0, 0, 0, 0, 0, 0)));
		
	};
	
	@EventHandler
	private Handler<EventRender2D> onRender2D = e -> {
		if (e.isPre())
			return;
		squares = bufferSquares;
		bufferSquares = new ArrayList<>();
	};
	
	/**
	 * @return the bufferSquares
	 */
	public ArrayList<Integer[]> getBufferSquares() {
		return bufferSquares;
	}
	
	/**
	 * @param squares the squares to set
	 */
	public void setSquares(ArrayList<Integer[]> squares) {
		this.squares = squares;
	}
	
	/**
	 * @return the bestGearSlots
	 */
	public ArrayList<Slot> getBestGearSlots() {
		return bestGearSlots;
	}
	
}
