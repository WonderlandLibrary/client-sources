package fr.dog.module.impl.render;

import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.property.impl.ModeProperty;
import fr.dog.ui.ImGuiScreen;
import fr.dog.ui.clickgui.dropdown.component.ClickGuiScreen;
import fr.dog.ui.clickgui.test.MainScreen;
import org.lwjglx.input.Keyboard;

public class ClickGui extends Module {
    private final ModeProperty mode = ModeProperty.newInstance("Mode", new String[] {"ImGui Compact", "Dropdown", "Test"}, "Dropdown");

    private ImGuiScreen imGuiCompactClickGui;
    private ClickGuiScreen dropdownClickGui;
    private MainScreen main;

    public ClickGui() {
        super("Click Gui", ModuleCategory.RENDER, Keyboard.KEY_RSHIFT);

        this.registerProperties(mode);
    }

    @Override
    protected void onEnable() {
        if (imGuiCompactClickGui == null)
            imGuiCompactClickGui = new ImGuiScreen();

        if (dropdownClickGui == null)
            dropdownClickGui = new ClickGuiScreen();

        if(main == null)
            main = new MainScreen();

        switch (mode.getValue().toLowerCase()) {
            case "imgui compact" -> mc.displayGuiScreen(imGuiCompactClickGui);
            case "dropdown" -> mc.displayGuiScreen(dropdownClickGui);
            case "test" -> mc.displayGuiScreen(main);
        }
        this.toggle();
    }
}
