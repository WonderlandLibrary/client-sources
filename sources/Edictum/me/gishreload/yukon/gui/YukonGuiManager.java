/*
 * Copyright (c) 2013, DarkStorm (darkstorm@evilminecraft.net)
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met: 
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer. 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package me.gishreload.yukon.gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.darkstorm.minecraft.gui.AbstractGuiManager;
import org.darkstorm.minecraft.gui.component.BoundedRangeComponent.ValueDisplay;
import org.darkstorm.minecraft.gui.component.Button;
import org.darkstorm.minecraft.gui.component.ComboBox;
import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.component.Frame;
import org.darkstorm.minecraft.gui.component.Slider;
import org.darkstorm.minecraft.gui.component.basic.BasicButton;
import org.darkstorm.minecraft.gui.component.basic.BasicCheckButton;
import org.darkstorm.minecraft.gui.component.basic.BasicComboBox;
import org.darkstorm.minecraft.gui.component.basic.BasicFrame;
import org.darkstorm.minecraft.gui.component.basic.BasicLabel;
import org.darkstorm.minecraft.gui.component.basic.BasicProgressBar;
import org.darkstorm.minecraft.gui.component.basic.BasicSlider;
import org.darkstorm.minecraft.gui.layout.GridLayoutManager;
import org.darkstorm.minecraft.gui.layout.GridLayoutManager.HorizontalGridConstraint;
import org.darkstorm.minecraft.gui.listener.ButtonListener;
import org.darkstorm.minecraft.gui.listener.ComboBoxListener;
import org.darkstorm.minecraft.gui.theme.Theme;
import org.darkstorm.minecraft.gui.theme.simple.SimpleTheme;

import me.gishreload.yukon.Category;
import me.gishreload.yukon.CategorySett;
import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.Meanings;
import me.gishreload.yukon.module.Module;
import net.minecraft.client.Minecraft;

/**
 * Minecraft GUI API
 * 
 * This class is not actually intended for use; rather, you should use this as a
 * template for your actual GuiManager, as the creation of frames is highly
 * implementation-specific.
 * 
 * @author DarkStorm (darkstorm@evilminecraft.net)
 * @author GishReloaded
 */
public final class YukonGuiManager extends AbstractGuiManager {
	private class ModuleFrame extends BasicFrame {
		private ModuleFrame() {
		}

		private ModuleFrame(String title) {
			super(title);
		}
	}

	private final AtomicBoolean setup;

	public YukonGuiManager() {
		setup = new AtomicBoolean();
	}

	@Override
	public void setup() {
		
		final Map<Category, ModuleFrame> categoryFrames = new HashMap<Category, ModuleFrame>();
		final Map<CategorySett, ModuleFrame> categoryFrames2 = new HashMap<CategorySett, ModuleFrame>();
		for(Module m: Edictum.getModules()){
			ModuleFrame frame = categoryFrames.get(m.getCategory());
			ModuleFrame sett = categoryFrames2.get(m.getCategorySett());
			if(sett == null){
				sett = new ModuleFrame();
				sett.setTheme(new SimpleTheme());
				sett.setVisible(true);
				sett.setClosable(false);
				sett.setMinimized(true);
				sett.add(new BasicLabel("Killaura"));
				sett.add(new BasicSlider("Range", Minecraft.hitrange, Minecraft.hs2, Minecraft.hs1,0.1,ValueDisplay.INTEGER));
				addFrame(sett);
				categoryFrames2.put(m.getCategorySett(), sett);
			sett.add(new BasicLabel("Mode"));
			final Module updateModuleKa = m;
			Button buttonsett = new BasicButton(Meanings.killaura10 ? "2" : "1"){
				@Override
				public void update(){
					setText(Meanings.killaura10 ? "2" : "1");
				}
			};
			buttonsett.addButtonListener(new ButtonListener(){
				@Override
				public void onButtonPress(Button buttonsett){
					Meanings.killaura10 = !Meanings.killaura10;
					buttonsett.setText(Meanings.killaura10 ? "2" : "1");
				}
			});
			sett.add(buttonsett, HorizontalGridConstraint.RIGHT);
			sett.add(new BasicLabel("Scaffold"));
			Button buttonsett2 = new BasicButton(Meanings.SafeWalk2 ? "Enabled" : "Disabled"){
				@Override
				public void update(){
					setText(Meanings.SafeWalk2 ? "Enabled" : "Disabled");
				}
			};
			buttonsett2.addButtonListener(new ButtonListener(){
				@Override
				public void onButtonPress(Button buttonsett2){
					Meanings.SafeWalk2 = !Meanings.SafeWalk2;
					buttonsett2.setText(Meanings.SafeWalk2 ? "Enabled" : "Disabled");
				}
			});
			sett.add(new BasicLabel("Safe"));
			sett.add(buttonsett2, HorizontalGridConstraint.RIGHT);
			sett.add(new BasicLabel("Flight"));
			
			//sett.add(new BasicSlider("Speed", Minecraft.flightspeed, 1, 9,0.1,ValueDisplay.INTEGER));
			}
			if(frame == null){
				String name = m.getCategory().toString().toLowerCase();
				name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
				frame = new ModuleFrame(name);
				frame.setTheme(new SimpleTheme());
				frame.setLayoutManager(new GridLayoutManager(2, 0));
				frame.setVisible(true);
				frame.setClosable(false);
				frame.setMinimized(true);
				addFrame(frame);
				categoryFrames.put(m.getCategory(), frame);
			}
			frame.add(new BasicLabel(m.getName()));
			final Module updateModule = m;
			Button button = new BasicButton(m.isToggled() ? "ON" : "OFF"){
				@Override
				public void update(){
					setText(updateModule.isToggled() ? "ON" : "OFF");
				}
			};
			button.addButtonListener(new ButtonListener(){
				@Override
				public void onButtonPress(Button button){
					updateModule.toggle();
					button.setText(updateModule.isToggled() ? "ON" : "OFF");
				}
			});
			frame.add(button, HorizontalGridConstraint.RIGHT);
		}
		// Optional equal sizing and auto-positioning
		resizeComponents();
		Minecraft minecraft = Minecraft.getMinecraft();
		Dimension maxSize = recalculateSizes();
		int offsetX = 5, offsetY = 5;
		int scale = minecraft.gameSettings.guiScale;
		if(scale == 0)
			scale = 1000;
		int scaleFactor = 0;
		while(scaleFactor < scale && minecraft.displayWidth / (scaleFactor + 1) >= 320 && minecraft.displayHeight / (scaleFactor + 1) >= 240)
			scaleFactor++;
		for(Frame frame : getFrames()) {
			frame.setX(offsetX);
			frame.setY(offsetY);
			offsetX += maxSize.width + 5;
			if(offsetX + maxSize.width + 5 > minecraft.displayWidth / scaleFactor) {
				offsetX = 5;
				offsetY += maxSize.height + 5;
			}
		}
	}

	private void createTestFrame() {
		Theme theme = getTheme();
		Frame testFrame = new BasicFrame("Frame");
		testFrame.setTheme(theme);

		testFrame.add(new BasicLabel("TEST LOL"));
		testFrame.add(new BasicLabel("TEST 23423"));
		testFrame.add(new BasicLabel("TE123123123ST LOL"));
		testFrame.add(new BasicLabel("31243 LO3242L432"));
		BasicButton testButton = new BasicButton("Duplicate this frame!");
		testButton.addButtonListener(new ButtonListener() {

			@Override
			public void onButtonPress(Button button) {
				createTestFrame();
			}
		});
		testFrame.add(new BasicCheckButton("This is a checkbox"));
		testFrame.add(testButton);
		ComboBox comboBox = new BasicComboBox("Simple theme", "Other theme", "Other theme 2");
		comboBox.addComboBoxListener(new ComboBoxListener() {

			@Override
			public void onComboBoxSelectionChanged(ComboBox comboBox) {
				Theme theme;
				switch(comboBox.getSelectedIndex()) {
				case 0:
					theme = new SimpleTheme();
					break;
				case 1:
					// Some other theme
					// break;
				case 2:
					// Another theme
					// break;
				default:
					return;
				}
				setTheme(theme);
			}
		});
		testFrame.add(comboBox);
		Slider slider = new BasicSlider("Test");
		slider.setContentSuffix("things");
		slider.setValueDisplay(ValueDisplay.INTEGER);
		testFrame.add(slider);
		testFrame.add(new BasicProgressBar(50, 0, 100, 1, ValueDisplay.PERCENTAGE));

		testFrame.setX(50);
		testFrame.setY(50);
		Dimension defaultDimension = theme.getUIForComponent(testFrame).getDefaultSize(testFrame);
		testFrame.setWidth(defaultDimension.width);
		testFrame.setHeight(defaultDimension.height);
		testFrame.layoutChildren();
		testFrame.setVisible(true);
		testFrame.setMinimized(true);
		addFrame(testFrame);
	}

	@Override
	protected void resizeComponents() {
		Theme theme = getTheme();
		Frame[] frames = getFrames();
		Button enable = new BasicButton("Enable");
		Button disable = new BasicButton("Disable");
		Dimension enableSize = theme.getUIForComponent(enable).getDefaultSize(enable);
		Dimension disableSize = theme.getUIForComponent(disable).getDefaultSize(disable);
		int buttonWidth = Math.max(enableSize.width, disableSize.width);
		int buttonHeight = Math.max(enableSize.height, disableSize.height);
		for(Frame frame : frames) {
			if(frame instanceof ModuleFrame) {
				for(Component component : frame.getChildren()) {
					if(component instanceof Button) {
						component.setWidth(buttonWidth);
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
		for(Frame frame : frames) {
			Dimension defaultDimension = frame.getTheme().getUIForComponent(frame).getDefaultSize(frame);
			maxWidth = Math.max(maxWidth, defaultDimension.width);
			frame.setHeight(defaultDimension.height);
			if(frame.isMinimized()) {
				for(Rectangle area : frame.getTheme().getUIForComponent(frame).getInteractableRegions(frame))
					maxHeight = Math.max(maxHeight, area.height);
			} else
				maxHeight = Math.max(maxHeight, defaultDimension.height);
		}
		for(Frame frame : frames) {
			frame.setWidth(maxWidth);
			frame.layoutChildren();
		}
		return new Dimension(maxWidth, maxHeight);
	}
}
