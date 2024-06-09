/**
 * 
 */
package cafe.kagu.kagu.settings.impl;

import cafe.kagu.kagu.eventBus.impl.EventSettingUpdate;
import cafe.kagu.kagu.settings.Setting;
import net.minecraft.util.MathHelper;

/**
 * @author DistastefulBannock
 *
 */
public class SlotSetting extends Setting<SlotSetting> {

	/**
	 * @param name The name of the setting
	 * @param defaultSlot The default slot, must be between 1 (inclusive) and 9 (inclusive)
	 */
	public SlotSetting(String name, int defaultSlot) {
		super(name);
		this.selectedSlot = MathHelper.clamp_int(defaultSlot, 1, 9);
	}
	
	private int selectedSlot;
	private SlotSetting[] invalidSlots = new SlotSetting[0];
	private int[] animationsForSlots = new int[9];
	
	/**
	 * @return the selectedSlot
	 */
	public int getSelectedSlot() {
		return selectedSlot;
	}
	
	/**
	 * Same as the <code>setSelectedSlot</code> method but doesn't check for invalid slots, used for config loading and saving since the slots may not line up until final load
	 * @param selectedSlot the selectedSlot to set
	 */
	public void setSelectedSlotNoInvalidCheck(int selectedSlot) {
		// Don't do anything if the selected slot is the same as the new slot
		if (selectedSlot == this.selectedSlot)
			return;
		
		// Set slot
		this.selectedSlot = selectedSlot;
		
		// Kagu hook
		{
			EventSettingUpdate eventSettingUpdate = new EventSettingUpdate(this);
			eventSettingUpdate.post();
		}
	}
	
	/**
	 * @param selectedSlot the selectedSlot to set
	 */
	public void setSelectedSlot(int selectedSlot) {
		// Don't do anything if the selected slot is the same as the new slot
		if (selectedSlot == this.selectedSlot)
			return;
		
		// Don't allow the user to select slots that other slot settings are using
		SlotSetting[]  invalidSlots = this.invalidSlots;
		for (SlotSetting invalidSlot : invalidSlots) {
			if (selectedSlot == invalidSlot.getSelectedSlot())
				return;
		}
		
		// Set slot
		this.selectedSlot = selectedSlot;
		
		// Kagu hook
		{
			EventSettingUpdate eventSettingUpdate = new EventSettingUpdate(this);
			eventSettingUpdate.post();
		}
	}
	
	/**
	 * @param invalidSlots the invalidSlots to set
	 */
	public void setInvalidSlots(SlotSetting... invalidSlots) {
		this.invalidSlots = invalidSlots;
	}
	
	/**
	 * @return the invalidSlots
	 */
	public SlotSetting[] getInvalidSlots() {
		return invalidSlots;
	}
	
	/**
	 * @return true if slot is valid, otherwise false
	 */
	public boolean isSlotValid() {
		SlotSetting[]  invalidSlots = this.invalidSlots;
		for (SlotSetting invalidSlot : invalidSlots) {
			if (selectedSlot == invalidSlot.getSelectedSlot())
				return false;
		}
		return true;
	}
	
	/**
	 * @return the animationsForSlots
	 */
	public int[] getAnimationsForSlots() {
		return animationsForSlots;
	}
	
}
