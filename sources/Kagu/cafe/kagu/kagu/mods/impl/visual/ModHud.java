/**
 * 
 */
package cafe.kagu.kagu.mods.impl.visual;

import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.settings.impl.BooleanSetting;
import cafe.kagu.kagu.settings.impl.DoubleSetting;
import cafe.kagu.kagu.settings.impl.ModeSetting;

/**
 * @author lavaflowglow
 *
 */
public class ModHud extends Module {
	
	public ModHud() {
		super("Hud", Category.VISUAL);
		setSettings(hudEnabled, visual3dEnabled, arraylistAnimation, arraylistColors, alternateStackSizeFont,
				itemInventoryTransform, rotate3DInventoryItems, itemRotateMode, rotateSpeed);
	}
	
	private BooleanSetting hudEnabled = new BooleanSetting("HUD Enabled", true);
	private BooleanSetting visual3dEnabled = new BooleanSetting("3D Visuals Enabled", true);
	
	// ArrayList options
	private ModeSetting arraylistAnimation = new ModeSetting("ArrayList Animation", "Squeeze", "Squeeze", "Slide");
	private ModeSetting arraylistColors = new ModeSetting("ArrayList Colors", "White", "White", "Category Colors");
	
	// General ui options
	private BooleanSetting alternateStackSizeFont = new BooleanSetting("Alternate Stack Size Font", false);
	private ModeSetting itemInventoryTransform = new ModeSetting("Item Inventory Transform", "Unchanged", "Unchanged", "All 3D", "All 2D");
	private BooleanSetting rotate3DInventoryItems = new BooleanSetting("Rotate 3D Inventory Items", false);
	private ModeSetting itemRotateMode = new ModeSetting("Item Rotate Mode", "Spin", "Spin", "Float").setDependency(rotate3DInventoryItems::isEnabled);
	private DoubleSetting rotateSpeed = new DoubleSetting("3D Inv Item Spin Speed", 0.05, 0.01, 1, 0.01).setDependency(rotate3DInventoryItems::isEnabled);
	
	/**
	 * @return the hudEnabled
	 */
	public BooleanSetting getHudEnabled() {
		return hudEnabled;
	}
	
	/**
	 * @return the visual3dEnabled
	 */
	public BooleanSetting getVisual3dEnabled() {
		return visual3dEnabled;
	}
	
	/**
	 * @return the arraylistAnimation
	 */
	public ModeSetting getArraylistAnimationModeSetting() {
		return arraylistAnimation;
	}
	
	/**
	 * @return the arraylistColors
	 */
	public ModeSetting getArraylistColors() {
		return arraylistColors;
	}
	
	/**
	 * @return the alternateStackSizeFont
	 */
	public BooleanSetting getAlternateStackSizeFont() {
		return alternateStackSizeFont;
	}
	
	/**
	 * @return the itemInventoryTransform
	 */
	public ModeSetting getItemInventoryTransform() {
		return itemInventoryTransform;
	}
	
	/**
	 * @return the rotate3DInventoryItems
	 */
	public BooleanSetting getRotate3DInventoryItems() {
		return rotate3DInventoryItems;
	}
	
	/**
	 * @return the itemRotateMode
	 */
	public ModeSetting getItemRotateMode() {
		return itemRotateMode;
	}
	
	/**
	 * @return the rotateSpeed
	 */
	public DoubleSetting getRotateSpeed() {
		return rotateSpeed;
	}
	
	@Override
	public double getArraylistAnimation() {
		return 0;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public boolean isDisabled() {
		return false;
	}
	
	@Override
	public void toggle() {
		
	}
	
	@Override
	public void enable() {
		
	}
	
	@Override
	public void disable() {
		
	}
	
}
