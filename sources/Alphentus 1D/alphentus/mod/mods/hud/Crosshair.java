package alphentus.mod.mods.hud;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import alphentus.settings.Setting;
import alphentus.utils.RenderUtils;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.awt.*;

/**
 * @author avox | lmao
 * @since on 12.08.2020.
 */
public class Crosshair extends Mod {

    GuiIngame guiIngame = new GuiIngame(mc);
    public Setting gap = new Setting("Gap", 0, 5, 3, true, this);
    public Setting length = new Setting("Length", 0, 5, 3, true, this);
    public Setting red = new Setting("Red", 0, 255, 255, true, this);
    public Setting green = new Setting("Green", 0, 255, 255, true, this);
    public Setting blue = new Setting("Blue", 0, 255, 255, true, this);
    public Setting dynamic = new Setting("Dynamic Gap", false, this);
    Color color;
    int start, end;

    public Crosshair() {
        super("Crosshair", Keyboard.KEY_NONE, false, ModCategory.HUD);

        Init.getInstance().settingManager.addSetting(gap);
        Init.getInstance().settingManager.addSetting(length);
        Init.getInstance().settingManager.addSetting(red);
        Init.getInstance().settingManager.addSetting(green);
        Init.getInstance().settingManager.addSetting(blue);
        Init.getInstance().settingManager.addSetting(dynamic);
    }

    @EventTarget
    public void event(Event event) {
        if (event.getType() != Type.RENDER2D)
            return;

        ScaledResolution sr = new ScaledResolution(mc);
        int midWidth = sr.getScaledWidth() / 2;
        int midHeight = sr.getScaledHeight() / 2;
        int add = mc.thePlayer.isMoving() && !mc.thePlayer.isSprinting() ? 1 : mc.thePlayer.isMoving() && mc.thePlayer.isSprinting() ? 2 : 0;
        start = (int) gap.getCurrent() + (dynamic.isState() ? add : 0);
        end = (int) (start + length.getCurrent());

        color = new Color((int) red.getCurrent(), (int) green.getCurrent(), (int) blue.getCurrent(), 255);

        if (guiIngame.showCrosshair() && mc.gameSettings.thirdPersonView < 1 && getState()) {
            // Left
            Gui.drawRect(midWidth - start + 1, midHeight, midWidth - end, midHeight + 1, color.getRGB());
            // Right
            Gui.drawRect(midWidth + start, midHeight, midWidth + end + 1, midHeight + 1, color.getRGB());
            // Top
            Gui.drawRect(midWidth, midHeight - start + 1, midWidth + 1, midHeight - end, color.getRGB());
            // Bottom
            Gui.drawRect(midWidth, midHeight + start, midWidth + 1, midHeight + end + 1, color.getRGB());
        }
    }
}