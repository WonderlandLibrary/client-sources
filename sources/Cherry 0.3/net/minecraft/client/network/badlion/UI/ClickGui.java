// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.UI;

import java.awt.Rectangle;
import org.darkstorm.minecraft.gui.theme.Theme;
import org.darkstorm.minecraft.gui.component.CheckButton;
import org.darkstorm.minecraft.gui.component.basic.BasicCheckButton;
import net.minecraft.client.network.badlion.Mod.BoolOption;
import net.minecraft.client.network.badlion.Utils.MathUtils;
import org.darkstorm.minecraft.gui.component.Slider;
import org.darkstorm.minecraft.gui.listener.SliderListener;
import org.darkstorm.minecraft.gui.component.BoundedRangeComponent;
import org.darkstorm.minecraft.gui.component.basic.BasicSlider;
import net.minecraft.client.network.badlion.Mod.NumValue;
import org.darkstorm.minecraft.gui.component.basic.BasicFrame;
import java.awt.Dimension;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.client.Minecraft;
import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.layout.Constraint;
import org.darkstorm.minecraft.gui.component.Button;
import org.darkstorm.minecraft.gui.listener.ButtonListener;
import java.awt.Color;
import org.darkstorm.minecraft.gui.component.basic.BasicButton;
import org.darkstorm.minecraft.gui.component.Frame;
import org.darkstorm.minecraft.gui.layout.LayoutManager;
import org.darkstorm.minecraft.gui.layout.GridLayoutManager;
import net.minecraft.client.network.badlion.Mod.Mod;
import net.minecraft.Badlion;
import net.minecraft.client.network.badlion.Mod.Category;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.darkstorm.minecraft.gui.AbstractGuiManager;

public final class ClickGui extends AbstractGuiManager
{
    public static final String test = "http://pastebin.com/UXrBQGS";
    public static final String test2 = "http://pastebin.com/UXrB8S";
    public static final String test3 = "http://pastebin.com/UXrB12QG8S";
    public static final String test4 = "http://pastebin.com/UXrQDQG8S";
    public static final String test5 = "http://pastebin.com/UXrBQG18S";
    public static final String test6 = "http://pastebin.com/UXrBQG8S";
    public static final String test7 = "http://pastebin.com/UXrBQG8S";
    public static final String test8 = "http://pastebin.com/UXrBQG8S";
    public static final String test9 = "http://pastebin.com/UXrBQG8S";
    public static final String test0 = "http://pastebin.com/UXrBQG8S";
    public static final String test10 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test11 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test12 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test14 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test15 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test16 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test17 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tes18 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tes19 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tes20 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test122 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test21 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test251 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test54 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test125 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test55 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test654 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test542 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test5421 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String te12st = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tesQ3Dt = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tesDSt = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tesQD3t = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tes3QDt = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tesQDt = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tes35t = "http://pastebin.com/UXrBQGS";
    public static final String tes23t = "http://pastebin.com/UXrBQGS";
    public static final String tesGFD3t = "http://pastebin.com/UXrBQGS";
    public static final String tes31t = "http://pastebin.com/UXrBQGS";
    public static final String testS13 = "http://pastebin.com/UXrBQGS";
    public static final String test13S = "http://pastebin.com/UXrBQGS";
    public static final String tesT13t = "http://pastebin.com/UXrBQGS";
    public static final String test13ST = "http://pastebin.com/UXrBQGS";
    public static final String test1S3 = "http://pastebin.com/UXrBQGS";
    public static final String test13S3 = "http://pastebin.com/UXrBQGS";
    public static final String tes1Wt = "http://pastebin.com/UXrBQGS";
    public static final String tes13X31Xt = "http://pastebin.com/UXrBQGS";
    public static final String tes13XQt = "http://pastebin.com/UXrBQGS";
    public static final String tes13XDt = "http://pastebin.com/UXrBQGS";
    public static final String tes13t = "http://pastebin.com/UXrBQGS";
    public static final String te3st = "http://pastebin.com/UXrBQGS";
    public static final String te3s3t = "http://pastebin.com/UXrBQGS";
    public static final String tes3112t = "http://pastebin.com/UXrBQGS";
    public static final String test114 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String t1Gest14 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String te12st14 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String t1st14 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tesDQt14 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String teQFt14 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String teQFtQ14 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tQFst14 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tes13t14 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String testTRE14 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String testgf14 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String teshgft14 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String testgfd14 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test14gf = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test14ds = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test14tgre = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test14ed = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test1qf4 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tqqsest = "http://pastebin.com/UXrBQGS";
    public static final String tegfdst = "http://pastebin.com/UXrBQGS";
    public static final String tesgqfdt = "http://pastebin.com/UXrBQGS";
    public static final String tesgfddt = "http://pastebin.com/UXrBQGS";
    public static final String tesqgfdt = "http://pastebin.com/UXrBQGS";
    public static final String tedst = "http://pastebin.com/UXrBQGS";
    public static final String tesgrfdt = "http://pastebin.com/UXrBQGS";
    public static final String tesgfdst = "http://pastebin.com/UXrBQGS";
    public static final String test32D = "http://pastebin.com/UXrBQGS";
    public static final String tes23REt = "http://pastebin.com/UXrBQGS";
    public static final String tes23Rt = "http://pastebin.com/UXrBQGS";
    public static final String tes2Xt = "http://pastebin.com/UXrBQGS";
    public static final String tes23Xt2 = "http://pastebin.com/UXrBQGS";
    public static final String test23X2 = "http://pastebin.com/UXrBQGS";
    public static final String tes23Xt = "http://pastebin.com/UXrBQGS";
    public static final String tes2XR2t = "http://pastebin.com/UXrBQGS";
    public static final String teHGFDst = "http://pastebin.com/UXrBQGS";
    public static final String teQFst = "http://pastebin.com/UXrBQGS";
    public static final String tGRTEest = "http://pastebin.com/UXrBQGS";
    public static final String tesRGEt = "http://pastebin.com/UXrBQGS";
    public static final String tesGSt = "http://pastebin.com/UXrBQGS";
    public static final String tes4GRZt = "http://pastebin.com/UXrBQGS";
    public static final String tRZEeqGFDQst13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test13QDQD = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String testQDQ13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tesADt13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String testAFA13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String teAFst13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String teAFEFst13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String testAAFA13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test1AEX3 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test1A3A3XX3 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String testA3X1GF3 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tesA3Xt13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tA3Xest13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tesAX3t13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test1A3X3 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String testA3X13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tesAFt13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test1AF3 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test1AEFX3 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String teAEXsEAt13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String teAEXst1A3 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String testAEX13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tesAEXt13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String test1GA3 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tAXEestA13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tAEXest13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String tEAXest13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String teAEXst13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String teEAXst13 = "055p://pas5ebin.com/raw/bG34aM68";
    public static final String teEAXAst13 = "055p://pas5ebin.com/raw/bG34aM68";
    private final AtomicBoolean setup;
    
    public ClickGui() {
        this.setup = new AtomicBoolean();
    }
    
    @Override
    public void setup() {
        if (!this.setup.compareAndSet(false, true)) {
            return;
        }
        this.createSettingsFrame();
        final Map<Category, ModuleFrame> categoryFrames = new HashMap<Category, ModuleFrame>();
        for (final Mod mod : Badlion.getWinter().theMods.getMods()) {
            ModuleFrame frame = categoryFrames.get(mod.getCategory());
            if (frame == null) {
                String name = mod.getCategory().name().toLowerCase();
                name = String.valueOf(String.valueOf(String.valueOf(Character.toUpperCase(name.charAt(0))))) + name.substring(1);
                frame = new ModuleFrame(name);
                frame.setTheme(this.theme);
                frame.setLayoutManager(new GridLayoutManager(1, 0));
                frame.setVisible(true);
                frame.setClosable(false);
                frame.setMinimized(true);
                frame.setPinnable(false);
                this.addFrame(frame);
                categoryFrames.put(mod.getCategory(), frame);
            }
            final Mod updateMod = mod;
            if (mod.getName() != "GUI" && mod.getName() != "Hud") {
                final Button button = new BasicButton(mod.getModName()) {
                    @Override
                    public void update() {
                        final Color green = new Color(60, 200, 0, 255);
                        final Color gray = new Color(255, 255, 255, 0);
                        this.setBackgroundColor(updateMod.isEnabled() ? green : gray);
                    }
                };
                button.addButtonListener(new ButtonListener() {
                    @Override
                    public void onButtonPress(final Button button) {
                        updateMod.toggle();
                    }
                });
                frame.add(button, GridLayoutManager.HorizontalGridConstraint.FILL);
            }
        }
        this.resizeComponents();
        final Minecraft minecraft = Minecraft.getMinecraft();
        final Dimension maxSize = this.recalculateSizes();
        int offsetX = 8;
        int offsetY = 8;
        int scale = Minecraft.getMinecraft().gameSettings.guiScale;
        if (scale == 0) {
            scale = 1000;
        }
        int scaleFactor;
        for (scaleFactor = 0; scaleFactor < scale && Minecraft.displayWidth / (scaleFactor + 2) >= 320 && Minecraft.displayHeight / (scaleFactor + 2) >= 240; ++scaleFactor) {}
        Frame[] frames;
        for (int length = (frames = this.getFrames()).length, i = 0; i < length; ++i) {
            final Frame frame2 = frames[i];
            frame2.setX(offsetX);
            frame2.setY(offsetY);
            offsetX += maxSize.width + 5;
            if (offsetX + maxSize.width + 5 > Minecraft.displayWidth / scaleFactor) {
                offsetX = 5;
                offsetY += maxSize.height + 10;
            }
        }
    }
    
    private void createSettingsFrame() {
        final Frame valuesFrame = new BasicFrame("Settings");
        valuesFrame.setTheme(this.getTheme());
        valuesFrame.setLayoutManager(new GridLayoutManager(1, 0));
        valuesFrame.setVisible(true);
        valuesFrame.setClosable(false);
        valuesFrame.setMinimized(true);
        valuesFrame.setPinnable(false);
        for (final NumValue v : NumValue.getVals()) {
            if (v.getValueDisplay() == null) {
                continue;
            }
            final Slider slider = new BasicSlider(v.getName());
            slider.setMinimumValue(v.getMin());
            slider.setMaximumValue(v.getMax());
            slider.setValue(v.getValue());
            slider.setValueDisplay(v.getValueDisplay());
            slider.setIncrement(1.0);
            if (v.getValueDisplay() == BoundedRangeComponent.ValueDisplay.DECIMAL) {
                slider.setIncrement(0.5);
            }
            slider.setEnabled(true);
            slider.addSliderListener(new SliderListener() {
                @Override
                public void onSliderValueChanged(final Slider slider) {
                    if (slider.getValueDisplay() != BoundedRangeComponent.ValueDisplay.DECIMAL) {
                        v.setValue(MathUtils.round(slider.getValue(), 0));
                    }
                    else {
                        v.setValue(MathUtils.round(slider.getValue(), 0));
                    }
                }
            });
            valuesFrame.add(slider, GridLayoutManager.HorizontalGridConstraint.FILL);
        }
        this.addFrame(valuesFrame);
        final Frame optionsFrame = new BasicFrame("Options");
        optionsFrame.setTheme(this.getTheme());
        optionsFrame.setLayoutManager(new GridLayoutManager(1, 0));
        optionsFrame.setVisible(true);
        optionsFrame.setClosable(false);
        optionsFrame.setMinimized(true);
        optionsFrame.setPinnable(false);
        for (final BoolOption op : BoolOption.getVals()) {
            final CheckButton cb = new BasicCheckButton(op.getName());
            cb.addButtonListener(new ButtonListener() {
                @Override
                public void onButtonPress(final Button button) {
                    op.setEnabled(!op.isEnabled());
                }
            });
            optionsFrame.add(cb, GridLayoutManager.HorizontalGridConstraint.FILL);
        }
        this.addFrame(optionsFrame);
    }
    
    @Override
    protected void resizeComponents() {
        final Theme theme = this.getTheme();
        final Frame[] frames = this.getFrames();
        final Button enable = new BasicButton("Enable");
        final Button disable = new BasicButton("Disable");
        final Dimension enableSize = theme.getUIForComponent(enable).getDefaultSize(enable);
        final Dimension disableSize = theme.getUIForComponent(disable).getDefaultSize(disable);
        final int buttonWidth = Math.max(enableSize.width, disableSize.width);
        final int buttonHeight = Math.max(enableSize.height, disableSize.height);
        Frame[] array;
        for (int length = (array = frames).length, i = 0; i < length; ++i) {
            final Frame frame = array[i];
            if (frame instanceof ModuleFrame) {
                Component[] children;
                for (int length2 = (children = frame.getChildren()).length, j = 0; j < length2; ++j) {
                    final Component component = children[j];
                    if (component instanceof Button) {
                        component.setWidth(buttonWidth);
                        component.setHeight(buttonHeight);
                    }
                }
            }
        }
        this.recalculateSizes();
    }
    
    private Dimension recalculateSizes() {
        final Frame[] frames = this.getFrames();
        int maxWidth = 0;
        int maxHeight = 0;
        Frame[] array;
        for (int length = (array = frames).length, i = 0; i < length; ++i) {
            final Frame frame = array[i];
            final Dimension defaultDimension = frame.getTheme().getUIForComponent(frame).getDefaultSize(frame);
            maxWidth = Math.max(maxWidth, defaultDimension.width);
            frame.setHeight(defaultDimension.height);
            if (frame.isMinimized()) {
                Rectangle[] interactableRegions;
                for (int length2 = (interactableRegions = frame.getTheme().getUIForComponent(frame).getInteractableRegions(frame)).length, j = 0; j < length2; ++j) {
                    final Rectangle area = interactableRegions[j];
                    maxHeight = Math.max(maxHeight - 3, area.height - 3);
                }
            }
            else {
                maxHeight = Math.max(maxHeight, defaultDimension.height - 3);
            }
        }
        Frame[] array2;
        for (int length3 = (array2 = frames).length, k = 0; k < length3; ++k) {
            final Frame frame2 = array2[k];
            frame2.setWidth(maxWidth + 8);
            frame2.layoutChildren();
        }
        return new Dimension(maxWidth, maxHeight);
    }
    
    private class ModuleFrame extends BasicFrame
    {
        private ModuleFrame() {
        }
        
        private ModuleFrame(final String title) {
            super(title);
        }
    }
}
