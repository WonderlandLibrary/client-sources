package digital.rbq.module.implement.Render;

import org.lwjgl.input.Keyboard;
import digital.rbq.Lycoris;
import digital.rbq.gui.clickgui.classic.ClickGUI;
import digital.rbq.gui.clickgui.skeet.SkeetClickGUI;
import digital.rbq.module.Category;
import digital.rbq.module.Module;
import digital.rbq.module.value.ColorValue;
import digital.rbq.module.value.ModeValue;

import java.awt.*;

public class CGUI extends Module {
    public static ModeValue GuiMode = new ModeValue("ClickGUI", "Mode", "New", "New", "Classical", "Skeet");

    public CGUI() {
        super("ClickGUI", Category.Render, false);
        this.setBind(Keyboard.KEY_RSHIFT);
        this.setSilent(true);
    }

    public static ColorValue skeetGUiColours = new ColorValue("ClickGUI", "Skeet GUI", Color.RED);
    public static ColorValue cursorColours = new ColorValue("ClickGUI", "Cursor", Color.RED);

    @Override
    public void onEnable() {
        if(mc.currentScreen instanceof digital.rbq.gui.crink.NewClickGUI || mc.currentScreen instanceof ClickGUI) {
            this.disable();
            return;
        }

        if (CGUI.GuiMode.isCurrentMode("New")) {
            if (Lycoris.INSTANCE.getNewClickGUI() == null) {
            	Lycoris.INSTANCE.setNewClickGUI(new digital.rbq.gui.crink.NewClickGUI());
            }

            mc.displayGuiScreen(Lycoris.INSTANCE.getNewClickGUI());
        } else if (CGUI.GuiMode.isCurrentMode("Classical")) {

            if (Lycoris.INSTANCE.getClickGUI() == null) {
                Lycoris.INSTANCE.setClickGUI(new ClickGUI());
            }

            mc.displayGuiScreen(Lycoris.INSTANCE.getClickGUI());
        } else {
            if (Lycoris.INSTANCE.getSkeetClickGUI() == null) {
                Lycoris.INSTANCE.setSkeetClickGUI(new SkeetClickGUI());
            }

            mc.displayGuiScreen(Lycoris.INSTANCE.getSkeetClickGUI());
            SkeetClickGUI.alpha = 0.0;
            SkeetClickGUI.open = true;
            Lycoris.INSTANCE.getSkeetClickGUI().targetAlpha = 255.0;
            Lycoris.INSTANCE.getSkeetClickGUI().closed = false;
        }
        this.disable();
    }
}
