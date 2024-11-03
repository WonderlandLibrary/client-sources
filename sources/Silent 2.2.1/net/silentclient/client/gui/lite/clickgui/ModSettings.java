package net.silentclient.client.gui.lite.clickgui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.gui.SilentScreen;
import net.silentclient.client.gui.animation.SimpleAnimation;
import net.silentclient.client.gui.animation.normal.Direction;
import net.silentclient.client.gui.elements.Button;
import net.silentclient.client.gui.elements.*;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.hud.HUDConfigScreen;
import net.silentclient.client.gui.lite.clickgui.utils.GlUtils;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.modmenu.CellGrid;
import net.silentclient.client.gui.theme.Theme;
import net.silentclient.client.gui.theme.input.DefaultInputTheme;
import net.silentclient.client.gui.util.ColorPickerAction;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.mods.Setting;
import net.silentclient.client.mods.render.crosshair.CrosshairMod;
import net.silentclient.client.mods.world.TimeChangerMod;
import net.silentclient.client.utils.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class ModSettings extends SilentScreen {
	private final Mod mod;
    private final GuiScreen parentScreen;
    private long initTime;
    
    public double scrollY;
	public static SimpleAnimation scrollAnimation = new SimpleAnimation(0.0F);
    private ScrollHelper scrollHelper = new ScrollHelper();

    public ModSettings(Mod mod, GuiScreen parent) {
        if (mod == null) throw new IllegalArgumentException("Mod is null");

        this.mod = mod;
        this.parentScreen = parent;
    }

    @Override
    public void initGui() {
        this.initTime = System.currentTimeMillis();
    	scrollAnimation.setValue(0);
        MenuBlurUtils.loadBlur();
        defaultCursor = false;
        int addX = 190;
        int addY = 110;
        int x = (width / 2) - addX;
        int y = (height / 2) - addY;
        int height = addY * 2;
        this.buttonList.add(new Button(1, x + 5, y + 25, 75, 20, "< Back"));
        this.buttonList.add(new Button(2, x + 5, (y + height) - 26, 75, 20, "Edit HUD"));
        for (Setting setting : Client.getInstance().getSettingsManager().getSettingByMod(mod)) {
            if(setting.isInput()) {
                this.silentInputs.add(new Input(setting.getName(), setting.getValString()));
            }

            if(setting.isKeybind() && !setting.getName().startsWith("Quickplay Mode")) {
                this.silentInputs.add(new Input(setting.getName(), setting.getKeybind()));
            }
        }
    }
    
    @Override
    public void onGuiClosed() {
    	Client.getInstance().configManager.save();
    	MenuBlurUtils.unloadBlur();
        int inputIndex = 0;
        for (Setting setting : Client.getInstance().getSettingsManager().getSettingByMod(mod)) {
            if (setting.isInput()) {
            	if(setting.getName() == "Text After Value" && !Client.getInstance().getAccount().isPremiumPlus()) {
            		inputIndex++;
            		continue;
            	}
                setting.setValString(this.silentInputs.get(inputIndex).getValue().length() != 0 ? this.silentInputs.get(inputIndex).getValue() : setting.defaultsval);
                inputIndex++;
            }
            if (setting.isKeybind() && !setting.getName().startsWith("Quickplay Mode")) {
                setting.setKeybind(this.silentInputs.get(inputIndex).getKey() != 0 ? this.silentInputs.get(inputIndex).getKey() : setting.defaultkval);
                inputIndex++;
            }
        }
    	super.onGuiClosed();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if(button.id == 1) {
        	mc.displayGuiScreen(parentScreen);
        }

        if(button.id == 2) {
        	mc.displayGuiScreen(new HUDConfigScreen(this));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        MenuBlurUtils.renderBackground(this);
        MouseCursorHandler.CursorType cursorType = getCursor(silentInputs, buttonList);
        int addX = 190;
        int addY = 110;

        int x = (width / 2) - addX;
        int y = (height / 2) - addY;
        int width = addX * 2;
        int height = addY * 2;
        
        GlStateManager.pushMatrix();
		GlUtils.startScale(((x) + (x) + width) / 2, ((y) + (y + height)) / 2, (float) ClickGUI.introAnimation.getValue());

        RenderUtil.drawRoundedRect(x, y, width, height, 10, Theme.backgroundColor().getRGB());
		
		GL11.glPushMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        scrollHelper.setStep(5);
        scrollHelper.setElementsHeight(25 + mod.customComponentHeight() + (Client.getInstance().getSettingsManager().getSettingByMod(mod).size() * 25));
        scrollHelper.setMaxScroll(height);
        scrollHelper.setSpeed(200);
        scrollHelper.setFlag(true);
        float scrollY = scrollHelper.getScroll();
        int settingY = (int) (y + 25 + scrollY + mod.customComponentLiteHeight());
        
        GL11.glPopMatrix();
        
		RenderUtil.drawImage(new ResourceLocation("silentclient/logos/logo.png"), x + 5, y + 5, 77, 15);
		
		int settingIndex = 0;
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		ScaledResolution r = new ScaledResolution(mc);
        int s = r.getScaleFactor();
        int translatedY = r.getScaledHeight() - y - height;
        GL11.glScissor(x * s, translatedY * s, this.width * s, height * s);
		
		Client.getInstance().getSilentFontRenderer().drawString(mod.getName(), x + 100, (y + 5) + scrollY, 14, SilentFontRenderer.FontType.TITLE);
		MouseCursorHandler.CursorType cursorTypeCustom = mod.renderCustomLiteComponent(x + 100, (int) (y + 25 + scrollY), width, height, mouseX, mouseY);
        if(cursorTypeCustom != null) {
            cursorType = cursorTypeCustom;
        }
        int inputIndex = 0;
        for (Setting setting : Client.getInstance().getSettingsManager().getSettingByMod(mod)) {
            if(mod.getName() == "Crosshair" && Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset Crosshair").getValBoolean() && setting.getName() != "Scale" && setting.getName() != "Crosshair Color" && setting.getName() != "Vanilla Blendering") {
                continue;
            }
            if(mod.getName() == "Crosshair" && (setting.getName() == "Preset ID" || setting.getName() == "Preset Crosshair")) {
                continue;
            }
            if(mod.getName() == "Crosshair" && setting.getName() == "Vanilla Blendering" && !Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset Crosshair").getValBoolean()) {
                continue;
            }
			int settingHeight = 10 + 5;
            if(setting.isInput()) {
                Client.getInstance().getSilentFontRenderer().drawString(setting.getName() + ":", x + 100, settingY + 1, 12, SilentFontRenderer.FontType.TITLE);
                if(setting.getName() == "Text After Value" && !Client.getInstance().getAccount().isPremiumPlus()) {
                    StaticButton.render(x + 310, settingY + (4), 65, 12, "BUY PREMIUM+");
                } else {
            		this.silentInputs.get(inputIndex).render(mouseX, mouseY, x + 100 + ((190 * 2) - 108) / 2, settingY, ((190 * 2) - 108) / 2, true);
                }
                ColorUtils.setColor(-1);
                settingY += 5;
                inputIndex++;
            }

            if(setting.isKeybind() && !setting.getName().startsWith("Quickplay Mode")) {
                Client.getInstance().getSilentFontRenderer().drawString(setting.getName() + ":", x + 100, settingY + 1, 12, SilentFontRenderer.FontType.TITLE);
                this.silentInputs.get(inputIndex).render(mouseX, mouseY, x + width - 50 - 5, settingY, 50, true, new DefaultInputTheme(), true);
                ColorUtils.setColor(-1);
                settingY += 5;
                inputIndex++;
            }
            
            if(setting.isCombo()) {
                Select.render(mouseX, mouseY, x, settingY, width, setting.getName(), setting.getValString());
                if(Select.nextHovered(mouseX, mouseY, x, settingY, width) || Select.prevHovered(mouseX, mouseY, x, settingY, width)) {
                    cursorType = MouseCursorHandler.CursorType.POINTER;
                }
            }
            
            if (setting.isColor()) {
                ColorPicker.render(x, settingY - 1, width, setting.getName(), setting.getValColor().getRGB());
                if(ColorPicker.isHovered(mouseX, mouseY, x, settingY - 1, width)) {
                    cursorType = MouseCursorHandler.CursorType.POINTER;
                }
            }
            
            if (setting.isCheck()) {
                Switch.render(mouseX, mouseY, x + 100, settingY - 1, setting.switchAnimation, setting.getValBoolean(), false);
                if(Switch.isHovered(mouseX, mouseY, x + 100, settingY - 1)) {
                    cursorType = MouseCursorHandler.CursorType.POINTER;
                }

                Client.getInstance().getSilentFontRenderer().drawString(setting.getName(), x + 100 + 18, settingY + (((float) 8 / 2) - ((float) 12 / 2)) - 1, 12, SilentFontRenderer.FontType.TITLE);
            }
            if(setting.isCellGrid()) {
                MouseCursorHandler.CursorType cellGridCursor = CellGrid.render(mouseX, mouseY, x + 100, settingY, setting);
                if(cellGridCursor != null) {
                    cursorType = cellGridCursor;
                }
                settingY += 135;
            }
            if (setting.isSlider()) {
                Slider.render(x, settingY - 1, width, setting.getName(), setting.getMax(), setting.getValDouble());

                if (Slider.isDrag(mouseX, mouseY, x, settingY - 1, width) && (System.currentTimeMillis() - initTime) > 500) {
                    double diff = setting.getMax() - setting.getMin();
                    double mouse = MathHelper.clamp_double((mouseX - Slider.getLeft(x, width)) / 90D, 0, 1);
                    double newVal = setting.getMin() + mouse * diff;
                    if(newVal != setting.getValDouble()) {
                    	setting.setValDouble(newVal);
                    	mod.onChangeSettingValue(setting);
                    }
                }
            }
            
            settingIndex++;

            settingY += settingHeight;
        }
        if(mod.getCategory() == ModCategory.MODS) {
            RenderUtil.drawImage(new ResourceLocation("silentclient/icons/reset_settings.png"), x + width - (10 + 8) - 15, y + 5 + scrollY, 10, 10);
            Tooltip.render(mouseX, mouseY, x + width - (10 + 8) - 15, y + 5 + scrollY, 10, 10, "Reset");
            if(MouseUtils.isInside(mouseX, mouseY, x + width - (10 + 8) - 15, y + 5 + scrollY, 10, 10)) {
                cursorType = MouseCursorHandler.CursorType.POINTER;
            }
            Switch.render(mouseX, mouseY, x + width - (10 + 8), y + 6 + scrollY, mod.simpleAnimation, mod.isEnabled(), mod.isForceDisabled(), mod.isForceDisabled() ? "Force disabled" : null);
            if(Switch.isHovered(mouseX, mouseY, x + width - (10 + 8), y + 6 + scrollY)) {
                cursorType = MouseCursorHandler.CursorType.POINTER;
            }
        }
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		GL11.glPopMatrix();

        super.drawScreen(mouseX, mouseY, partialTicks);

//		final Scroll scroll = MouseUtils.scroll();
//
//		if(scroll != null) {
//        	switch (scroll) {
//        	case DOWN:
//        		if(scrollY > -((settingIndex - 13.5) * 38)) {
//            		scrollY -=12;
//        		}
//
//        		if(settingIndex > 13) {
//            		if(scrollY < -((settingIndex - 15) * 38)) {
//            			scrollY = -((settingIndex - 14.1) * 38);
//            		}
//        		}
//        		if(mod.customComponentLiteHeight() > height - 30) {
//        			if(scrollY > -((mod.customComponentLiteHeight() - 13.5) * 38)) {
//                		scrollY -=12;
//            		}
//        			if(scrollY < -((mod.customComponentLiteHeight() - 15) * 38)) {
//            			scrollY = -((mod.customComponentLiteHeight() - 14.1) * 38);
//            		}
//        		}
//        		break;
//            case UP:
//        		if(scrollY < -10) {
//            		scrollY +=12;
//        		}else {
//            		if(settingIndex > 13) {
//            			scrollY = 0;
//            		}
//            		if(mod.customComponentLiteHeight() > height - 30) {
//            			scrollY = 0;
//            		}
//        		}
//        		break;
//        	}
//        }
//
//        scrollAnimation.setAnimation((float) scrollY, 16);

		if(ClickGUI.close) {
			ClickGUI.introAnimation.setDirection(Direction.BACKWARDS);
			if(ClickGUI.introAnimation.isDone(Direction.BACKWARDS)) {
				mc.displayGuiScreen(null);
			}
            Client.getInstance().getMouseCursorHandler().disableCursor();
        } else {
            Client.getInstance().getMouseCursorHandler().enableCursor(cursorType);
        }
	
		GlUtils.stopScale();
		GlStateManager.popMatrix();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        float scrollY = scrollHelper.getScroll();
        int addX = 190;
        int addY = 110;

        int x = (width / 2) - addX;
        int y = (height / 2) - addY;
        int width = addX * 2;
        int settingY = (int) (y + 25 + scrollY + mod.customComponentLiteHeight());
        String category = "";

        if(mod.getCategory() == ModCategory.MODS && MouseUtils.isInside(mouseX, mouseY, x + width - (10 + 8) - 15, y + 5 + scrollY, 10, 10)) {
            Sounds.playButtonSound();
            mod.reset(false);
        }
        
        if(mod.getCategory() == ModCategory.MODS && Switch.isHovered(mouseX, mouseY, x + width - (10 + 8), y + 6 + scrollY)) {
            Sounds.playButtonSound();
            mod.toggle();
        }

        int inputIndex = 0;

        for (Setting setting : Client.getInstance().getSettingsManager().getSettingByMod(mod)) {
            if(mod.getName() == "Crosshair" && Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset Crosshair").getValBoolean() && setting.getName() != "Scale" && setting.getName() != "Crosshair Color" && setting.getName() != "Vanilla Blendering") {
                continue;
            }
            if(mod.getName() == "Crosshair" && (setting.getName() == "Preset ID" || setting.getName() == "Preset Crosshair")) {
                continue;
            }
            if(mod.getName() == "Crosshair" && setting.getName() == "Vanilla Blendering" && !Client.getInstance().getSettingsManager().getSettingByClass(CrosshairMod.class, "Preset Crosshair").getValBoolean()) {
                continue;
            }
        	int settingHeight = 10 + 5;
            if (!category.equals(setting.getCategory())) {
                settingY += 5;
                settingY += 15f;
                settingY += 5;

                category = setting.getCategory();
            }

            if(setting.isInput()) {
            	if(setting.getName() == "Text After Value" && !Client.getInstance().getAccount().isPremiumPlus()) {
            		if(StaticButton.isHovered(mouseX, mouseY, x + 310, settingY + (4), 65, 12)) {
            			Sounds.playButtonSound();
            			try {
            				Class<?> oclass = Class.forName("java.awt.Desktop");
            				Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
            				oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {new URI("https://store.silentclient.net/premium")});
            			} catch (Throwable err) {
            				err.printStackTrace();
            			}
            		}
            	} else {
                    this.silentInputs.get(inputIndex).onClick(mouseX, mouseY, x + 100 + ((190 * 2) - 108) / 2, settingY, ((190 * 2) - 108) / 2, true);
            	}
            	settingY += 5;
                inputIndex++;
            }

            if(setting.isKeybind() && !setting.getName().startsWith("Quickplay Mode")) {
                this.silentInputs.get(inputIndex).onClick(mouseX, mouseY, x + width - 50 - 5, settingY, 50, true);
                settingY += 5;
                inputIndex++;
            }
            if(setting.isCellGrid()) {
                CellGrid.click(mouseX, mouseY, mouseButton, setting);
                settingY += 135;
            }
            if(setting.isCombo()) {
            	int index = 0;
            	String curr = setting.getValString();
            	String next = "";
            	String prev = "";
            	
            	for(int i=0;i<setting.getOptions().size();i++) {
            	    if(curr == setting.getOptions().get(i)) {
            	    	index = i;
            	    }
            	}
            	if((index + 1) > (setting.getOptions().size() - 1)) {
        	    	next = setting.getOptions().get(0);
        	    } else {
        	    	next = setting.getOptions().get(index + 1);
        	    }
            	
            	if(!((index - 1) > (setting.getOptions().size() - 1)) && (index - 1) != -1) {
            		prev = setting.getOptions().get(index - 1);
            	} else {
            		prev = setting.getOptions().get(setting.getOptions().size() - 1);
            	}

            	if(Select.prevHovered(mouseX, mouseY, x, settingY, width)) {
            		Sounds.playButtonSound();
            		setting.setValString(prev);
            		mod.onChangeSettingValue(setting);
            	}
            	
            	if(Select.nextHovered(mouseX, mouseY, x, settingY, width)) {
            		Sounds.playButtonSound();
            		setting.setValString(next);
            		mod.onChangeSettingValue(setting);
            	}
            }

            if (setting.isCheck()) {
                if(Switch.isHovered(mouseX, mouseY, x + 100, settingY - 1)) {
                	Sounds.playButtonSound();
                	setting.setValBoolean(!setting.getValBoolean());
                    mod.onChangeSettingValue(setting);
                }
            }
            
            if (setting.isColor() && ColorPicker.isHovered(mouseX, mouseY, x, settingY - 1, width)) {
                Sounds.playButtonSound();
                this.mc.displayGuiScreen(new GuiColorPicker(setting.getValColor(true), setting.isChroma(), setting.isCanChangeOpacity(), setting.getOpacity(), new ColorPickerAction() {
                    @Override
                    public void onChange(Color color, boolean chroma, int opacity) {
                        setting.setValColor(color);
                        setting.setChroma(chroma);
                        if(setting.isCanChangeOpacity()) {
                            setting.setOpacity(opacity);
                        }
                    }
                }, this));
            }

            settingY += settingHeight;
        }
        
        mod.customLiteComponentClick(x + 100, (int) (y + 30 + scrollY), mouseX, mouseY, mouseButton, this);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            ClickGUI.close = true;
            return;
        }

        int inputIndex = 0;

        for (Setting setting : Client.getInstance().getSettingsManager().getSettingByMod(mod)) {
            if (setting.isInput()) {
            	if(setting.getName() == "Text After Value" && !Client.getInstance().getAccount().isPremiumPlus()) {
            		inputIndex++;
            		continue;
            	}
                this.silentInputs.get(inputIndex).onKeyTyped(typedChar, keyCode);
                setting.setValString(this.silentInputs.get(inputIndex).getValue());
                mod.onChangeSettingValue(setting);
                inputIndex++;
            }
            if (setting.isKeybind() && !setting.getName().startsWith("Quickplay Mode")) {
                this.silentInputs.get(inputIndex).onKeyTyped(typedChar, keyCode);
                setting.setKeybind(this.silentInputs.get(inputIndex).getKey());
                mod.onChangeSettingValue(setting);
                inputIndex++;
            }
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (Setting setting : Client.getInstance().getSettingsManager().getSettingByMod(mod)) {
            if(setting.isCellGrid()) {
                CellGrid.release();
            }
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public boolean doesGuiPauseGame() {
    	return !(this.mod instanceof TimeChangerMod);
    }
}
