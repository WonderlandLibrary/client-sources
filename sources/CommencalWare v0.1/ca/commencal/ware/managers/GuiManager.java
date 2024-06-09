package ca.commencal.ware.managers;

import ca.commencal.ware.gui.click.ClickGui;
import ca.commencal.ware.gui.click.base.Component;
import ca.commencal.ware.gui.click.elements.Frame;
import ca.commencal.ware.gui.click.elements.*;
import ca.commencal.ware.gui.click.listener.CheckButtonClickListener;
import ca.commencal.ware.gui.click.listener.ComponentClickListener;
import ca.commencal.ware.gui.click.listener.SliderChangeListener;
import ca.commencal.ware.module.Module;
import ca.commencal.ware.module.ModuleCategory;
import ca.commencal.ware.utils.visual.GLUtils;
import ca.commencal.ware.value.*;

import java.awt.*;

public class GuiManager extends ClickGui {

    public void Initialization() {
        addCategoryPanels();
    }

    private void addCategoryPanels() {
        int right = GLUtils.getScreenWidth();
        int framePosX = 20;
        int framePosY = 20;

        for (ModuleCategory category : ModuleCategory.values()) {
        	int frameHeight = 300;
        	int frameWidth = 95;
        	int hacksCount = 0;
                String name = Character.toString(category.toString().toLowerCase().charAt(0)).toUpperCase() + category.toString().toLowerCase().substring(1);
                Frame frame = new Frame(framePosX, framePosY, frameWidth, frameHeight, name);

                for (final Module mod : ModuleManager.getModules()) {
                    if (mod.getCategory() == category) {
                        final ExpandingButton expandingButton = new ExpandingButton(0, 0, 95, 14, frame, mod.getName(), mod) {

                            @Override
                            public void onUpdate() {
                                setEnabled(mod.isToggled());
                            }
                        };
                        expandingButton.addListner(new ComponentClickListener() {

							@Override
							public void onComponenetClick(Component component, int button) {
								mod.toggle();	
							}
                        });
                        expandingButton.setEnabled(mod.isToggled());
                        
                        if (!mod.getValues().isEmpty()) {
                            for (Value value : mod.getValues()) {
                                if (value instanceof BooleanValue) {
                                    final BooleanValue booleanValue = (BooleanValue) value;
                                    CheckButton button = new CheckButton(0, 0, expandingButton.getDimension().width, 14, expandingButton, booleanValue.getName(), booleanValue.getValue(), null);
                                    button.addListeners(new CheckButtonClickListener() {

										@Override
										public void onCheckButtonClick(CheckButton checkButton) {
											for (Value value1 : mod.getValues()) {
	                                            if (value1.getName().equals(booleanValue.getName())) {
	                                                value1.setValue(checkButton.isEnabled());
	                                            }
	                                        }
										}
                                    	
                                    });
                                    expandingButton.addComponent(button);
                                
                                } else if (value instanceof NumberValue) {
                                    final NumberValue doubleValue = (NumberValue) value;
                                    Slider slider = new Slider(doubleValue.getMin(), doubleValue.getMax(), doubleValue.getValue(), expandingButton, doubleValue.getName());
                                    slider.addListener(new SliderChangeListener() {
										@Override
										public void onSliderChange(Slider slider) {
											for (Value value1 : mod.getValues()) {
	                                            if (value1.getName().equals(value.getName())) {
	                                                value1.setValue(slider.getValue());
	                                            }
	                                        }
										}
                                    	
                                    });

                                    expandingButton.addComponent(slider);
                                
                                
                            } else if (value instanceof ModeValue) {
                            	Dropdown dropdown = new Dropdown(0, 0, 90, 14, frame, value.getName());
                            	
                            	final ModeValue modeValue = (ModeValue) value;
                            	
                            	for(Mode mode : modeValue.getModes()) {
                            		CheckButton button = new CheckButton(0, 0, 
                            				expandingButton.getDimension().width, 14, expandingButton, 
                            				mode.getName(), mode.isToggled(), modeValue);
                            		
                            			button.addListeners(checkButton -> {
                            				for(Mode mode1 : modeValue.getModes()) {
                            					if (mode1.getName().equals(mode.getName())) {
                            						mode1.setToggled(checkButton.isEnabled());
                            					}
                            				}
                 
                                    	});
                            			dropdown.addComponent(button);
                            		}
                            		expandingButton.addComponent(dropdown);
                            	}
                            }
                        }
                        KeybindMods keybind = new KeybindMods(0, 0, 8, 14, expandingButton, mod);
                        expandingButton.addComponent(keybind);
                        frame.addComponent(expandingButton);
                        hacksCount++;
                    }
                }
                
                if(hacksCount < 6) {
                	frameHeight = hacksCount * 50 + 200;
                }
                
                frame.setDimension(new Dimension(frameWidth, frameHeight));
                
                if (framePosX + 100 < right) {
                    framePosX += 100;
                } else {
                    framePosX = 20;
                    framePosY += 60;
                }

                frame.setMaximizible(true);
                frame.setPinnable(true);
                this.addFrame(frame);
        }
    }
}
