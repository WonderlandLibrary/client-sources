package net.minecraft.client.main.neptune.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.darkstorm.minecraft.gui.AbstractGuiManager;
import org.darkstorm.minecraft.gui.component.BoundedRangeComponent;
import org.darkstorm.minecraft.gui.component.BoundedRangeComponent.ValueDisplay;
import org.darkstorm.minecraft.gui.component.Button;
import org.darkstorm.minecraft.gui.component.CheckButton;
import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.component.Frame;
import org.darkstorm.minecraft.gui.component.Slider;
import org.darkstorm.minecraft.gui.component.basic.BasicButton;
import org.darkstorm.minecraft.gui.component.basic.BasicCheckButton;
import org.darkstorm.minecraft.gui.component.basic.BasicFrame;
import org.darkstorm.minecraft.gui.component.basic.BasicSlider;
import org.darkstorm.minecraft.gui.layout.GridLayoutManager;
import org.darkstorm.minecraft.gui.layout.GridLayoutManager.HorizontalGridConstraint;
import org.darkstorm.minecraft.gui.listener.ButtonListener;
import org.darkstorm.minecraft.gui.listener.SliderListener;
import org.darkstorm.minecraft.gui.theme.Theme;

import net.minecraft.client.Minecraft;
import net.minecraft.client.main.neptune.Neptune;
import net.minecraft.client.main.neptune.Wrapper;
import net.minecraft.client.main.neptune.Mod.BoolOption;
import net.minecraft.client.main.neptune.Mod.Category;
import net.minecraft.client.main.neptune.Mod.Mod;
import net.minecraft.client.main.neptune.Mod.NumValue;
import net.minecraft.client.main.neptune.Utils.MathUtils;
import net.minecraft.client.main.neptune.Utils.RenderUtils;

public final class Mememan extends AbstractGuiManager {
	private class ModuleFrame extends BasicFrame {
		private ModuleFrame() {
		}

		private ModuleFrame(String title) {
			super(title);
		}
	}

	private final AtomicBoolean setup;

	public Mememan() {
		setup = new AtomicBoolean();
	}

	@Override
	public void setup() {
		if (!setup.compareAndSet(false, true))
			return;
		this.createSettingsFrame();
		final Map<Category, ModuleFrame> categoryFrames = new HashMap<Category, ModuleFrame>();
		for (Mod mod : Neptune.getWinter().theMods.getMods()) {
			ModuleFrame frame = categoryFrames.get(mod.getCategory());
			if (frame == null) {
				String name = mod.getCategory().name().toLowerCase();
				name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
				frame = new ModuleFrame(name);
				frame.setTheme(theme);
				frame.setLayoutManager(new GridLayoutManager(1, 0));
				frame.setVisible(true);
				frame.setClosable(false);
				frame.setMinimized(true);
				frame.setPinnable(false);
				addFrame(frame);
				categoryFrames.put(mod.getCategory(), frame);
			}
			final Mod updateMod = mod;
			if(mod.getName() != "GUI" && mod.getName() != "Hud") {
			Button button = new BasicButton(mod.getModName()) {
				@Override
				public void update() {
					Color green = new Color(50, 20, 20, 170);
					Color gray = new Color(30, 20, 20, 170);
					this.setBackgroundColor(updateMod.isEnabled() ? green : gray);
				}
			};
			button.addButtonListener(new ButtonListener() {
				@Override
				public void onButtonPress(Button button) {
					updateMod.toggle();
				}
			});
			frame.add(button, HorizontalGridConstraint.FILL);
		}
	}
		resizeComponents();
		Minecraft minecraft = Minecraft.getMinecraft();
		Dimension maxSize = recalculateSizes();
		int offsetX = 5, offsetY = 5;
		int scale = minecraft.gameSettings.guiScale;
		if (scale == 0)
			scale = 1000;
		int scaleFactor = 0;
		while (scaleFactor < scale && minecraft.displayWidth / (scaleFactor + 1) >= 320
				&& minecraft.displayHeight / (scaleFactor + 1) >= 240)
			scaleFactor++;
		for (Frame frame : getFrames()) {
			frame.setX(offsetX);
			frame.setY(offsetY);
			offsetX += maxSize.width + 5;
			if (offsetX + maxSize.width + 5 > minecraft.displayWidth / scaleFactor) {
				offsetX = 5;
				offsetY += maxSize.height + 7;
			}
		}
	}

	private void createSettingsFrame() {
		Frame optionsFrame = new BasicFrame("Settings");
		optionsFrame.setTheme(this.getTheme());
		optionsFrame.setLayoutManager(new GridLayoutManager(1, 0));
		optionsFrame.setVisible(true);
		optionsFrame.setClosable(false);
		optionsFrame.setMinimized(true);
		optionsFrame.setPinnable(false);
		for (final NumValue v : NumValue.getVals()) {
			if (v.getValueDisplay() == null) {
				continue;
			}
			Slider slider = new BasicSlider(v.getName());
			slider.setMinimumValue(v.getMin());
			slider.setMaximumValue(v.getMax());
			slider.setValue(v.getValue());
			slider.setValueDisplay(v.getValueDisplay());
			slider.setIncrement(1);
			if (v.getValueDisplay() == BoundedRangeComponent.ValueDisplay.DECIMAL) {
				slider.setIncrement(0.5);
			}
			slider.setEnabled(true);
			slider.addSliderListener(new SliderListener() {
				@Override
				public void onSliderValueChanged(Slider slider) {
					if (slider.getValueDisplay() != ValueDisplay.DECIMAL) {
						v.setValue(MathUtils.round(slider.getValue(), 0));
					} else {
						v.setValue(MathUtils.round(slider.getValue(), 0));
					}
				}
			});
			optionsFrame.add(slider, HorizontalGridConstraint.FILL);
		}
		for (final BoolOption op : BoolOption.getVals()) {
			CheckButton cb = new BasicCheckButton(op.getName());
			cb.addButtonListener(new ButtonListener() {
				@Override
				public void onButtonPress(Button button) {
					op.setEnabled(!op.isEnabled());
				}
			});
			optionsFrame.add(cb, HorizontalGridConstraint.FILL);
		}
		this.addFrame(optionsFrame);
	}

	@Override
	protected void resizeComponents() {
		Theme theme = getTheme();
		Frame[] frames = getFrames();
		Button enable = new BasicButton("Enable");
		Button disable = new BasicButton("Disable");
		Dimension enableSize = theme.getUIForComponent(enable).getDefaultSize(enable);
		Dimension disableSize = theme.getUIForComponent(disable).getDefaultSize(disable);
		int buttonWidth = Math.max(enableSize.width + 1, disableSize.width + 1);
		int buttonHeight = Math.max(enableSize.height - 1, disableSize.height - 1);
		for (Frame frame : frames) {
			if (frame instanceof ModuleFrame) {
				for (Component component : frame.getChildren()) {
					if (component instanceof Button) {
						component.setWidth(buttonWidth + 3);
						component.setHeight(buttonHeight);
					}
				}
			}
		}
		recalculateSizes();
	}

	private Dimension recalculateSizes() {
		Frame[] frames = getFrames();
		int maxWidth = 0, maxHeight = 0;
		for (Frame frame : frames) {
			Dimension defaultDimension = frame.getTheme().getUIForComponent(frame).getDefaultSize(frame);
			maxWidth = Math.max(maxWidth - 20, defaultDimension.width - 20);
		//	RenderUtils.drawBorderedRect(maxWidth - 5, maxHeight + 5, maxWidth + maxWidth, maxHeight + maxHeight, 1, 0xFF6d6d6d, 0x999999);
			frame.setHeight(defaultDimension.height - 1);
			if (frame.isMinimized()) {
				for (Rectangle area : frame.getTheme().getUIForComponent(frame).getInteractableRegions(frame))
					maxHeight = Math.max(maxHeight, area.height);
			} else
				maxHeight = Math.max(maxHeight, defaultDimension.height);
		}
		for (Frame frame : frames) {
			frame.setWidth(maxWidth + 10);
			frame.layoutChildren();
		}
		return new Dimension(maxWidth, maxHeight);
	}
}
