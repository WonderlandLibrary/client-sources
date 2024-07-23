package io.github.liticane.monoxide.module.impl.hud;

import java.util.function.Supplier;

import io.github.liticane.monoxide.ui.screens.clickgui.astolfo.AstolfoClickGuiScreen;
import io.github.liticane.monoxide.ui.screens.clickgui.AtaniClickGuiScreen;
import io.github.liticane.monoxide.ui.screens.clickgui.AugustusClickGuiScreen;
import io.github.liticane.monoxide.ui.screens.clickgui.monoxide.MonoxideClickGuiScreen;
import io.github.liticane.monoxide.ui.screens.clickgui.oldaugustus.OldAugustusClickGuiScreen;
import io.github.liticane.monoxide.ui.screens.clickgui.simple.SimpleClickGuiScreen;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.value.impl.BooleanValue;
import io.github.liticane.monoxide.value.impl.NumberValue;
import io.github.liticane.monoxide.value.impl.ModeValue;
import org.lwjglx.input.Keyboard;

@ModuleData(name = "ClickGui", description = "A clicky gui", category = ModuleCategory.HUD, key = Keyboard.KEY_RSHIFT)
public class ClickGuiModule extends Module {

    public final ModeValue mode = new ModeValue("Mode", this, new String[]{"Atani Simple", "Atani CS:GO", "Augustus", "Augustus 2.6", "Astolfo", "Monoxide"});
    public final BooleanValue openingAnimation = new BooleanValue("Opening Animation", this, true);
    public final ModeValue dropdownAnimation = new ModeValue("Animation Mode", this, new String[]{"Scale-In", "Frame Scale-In", "Left to Right", "Right to Left", "Up to Down", "Down to Up"}, new Supplier[]{() ->
            mode.getValue().equalsIgnoreCase("Atani Simple") || // Dropdown ui go here
                    mode.getValue().equalsIgnoreCase("Augustus 2.6") ||
                    mode.getValue().equalsIgnoreCase("Astolfo")}).setIdName("Dropdown Animation Mode");
    public final ModeValue nonDropdownAnimation = new ModeValue("Animation Mode", this, new String[]{"Left to Right", "Right to Left", "Up to Down", "Down to Up"}, new Supplier[]{() ->
            mode.getValue().equalsIgnoreCase("Atani CS:GO") || // Non-Dropdown ui go here
                    mode.getValue().equalsIgnoreCase("Augustus")}).setIdName("Non-Dropdown Animation Mode");

    public final NumberValue<Integer> red = new NumberValue<Integer>("Red", this, 255, 0, 255, 0, new Supplier[]{() -> mode.getValue().equalsIgnoreCase("Augustus")});
    public final NumberValue<Integer> green = new NumberValue<Integer>("Green", this, 197, 0, 255, 0, new Supplier[]{() -> mode.getValue().equalsIgnoreCase("Augustus")});
    public final NumberValue<Integer> blue = new NumberValue<Integer>("Blue", this, 188, 0, 255, 0, new Supplier[]{() -> mode.getValue().equalsIgnoreCase("Augustus")});

    public static SimpleClickGuiScreen clickGuiScreenSimple;
    public static AtaniClickGuiScreen clickGuiScreenAtani;
    public static AugustusClickGuiScreen clickGuiScreenAugustus;
    public static OldAugustusClickGuiScreen clickGuiScreenOldAugustus;
    public static AstolfoClickGuiScreen clickGuiScreenAstolfo;
    public static MonoxideClickGuiScreen monoxideClickGuiScreen;

    @Override
    public void onEnable() {
        switch (this.mode.getValue()) {
            case "Astolfo":
                if (clickGuiScreenAstolfo == null) {
                    clickGuiScreenAstolfo = new AstolfoClickGuiScreen();
                }
                mc.displayGuiScreen(clickGuiScreenAstolfo);
                break;
            case "Augustus":
                if (clickGuiScreenAugustus == null) {
                    clickGuiScreenAugustus = new AugustusClickGuiScreen();
                }
                mc.displayGuiScreen(clickGuiScreenAugustus);
                break;
            case "Atani CS:GO":
                if (clickGuiScreenAtani == null) {
                    clickGuiScreenAtani = new AtaniClickGuiScreen();
                }
                mc.displayGuiScreen(clickGuiScreenAtani);
                break;
            case "Atani Simple":
                if (clickGuiScreenSimple == null) {
                    clickGuiScreenSimple = new SimpleClickGuiScreen();
                }
                mc.displayGuiScreen(clickGuiScreenSimple);
                break;
            case "Augustus 2.6":
                if (clickGuiScreenOldAugustus == null) {
                    clickGuiScreenOldAugustus = new OldAugustusClickGuiScreen();
                }
                mc.displayGuiScreen(clickGuiScreenOldAugustus);
                break;
            case "Monoxide":
                if (monoxideClickGuiScreen == null) {
                    monoxideClickGuiScreen = new MonoxideClickGuiScreen();
                }
                mc.displayGuiScreen(monoxideClickGuiScreen);
                break;
            default: {
                this.mode.setValue("Atani Simple");
                break;
            }

        }
        this.setEnabled(false);
    }

}