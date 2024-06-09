package dev.vertic.ui.click.dropdown;

import dev.vertic.Client;
import dev.vertic.Utils;
import dev.vertic.event.impl.input.SettingUpdateEvent;
import dev.vertic.module.Module;
import dev.vertic.module.impl.render.Blur;
import dev.vertic.util.render.BlurUtil;
import dev.vertic.util.render.DrawUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import dev.vertic.module.api.Category;
import dev.vertic.module.impl.render.ClickGUI;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DropUI extends GuiScreen {


    public static float scrollHorizontal;
    public final List<CategoryFrame> frames = new ArrayList<>();
    private float lastScrollHorizontal;
    public Module focusedModule;

    public void updateScroll() {
        if (GuiInventory.isCtrlKeyDown()) {
            final float partialTicks = mc == null || mc.timer == null ? 1.0F : mc.timer.renderPartialTicks;

            final float lastLastScrollHorizontal = lastScrollHorizontal;
            lastScrollHorizontal = scrollHorizontal;
            final float wheel = Mouse.getDWheel();
            scrollHorizontal += wheel / 10.0F;
            if (wheel == 0) scrollHorizontal -= (lastLastScrollHorizontal - scrollHorizontal) * 0.6 * partialTicks;
        }
    }

    @Override
    public void initGui() {
        if (frames.size() <= 0) {
            int index = -1;
            for (final Category category : Category.values()) {
                final CategoryFrame frame = new CategoryFrame(category, 5 + (++index * (CategoryFrame.entryWidth + 10)), 5);
                frames.add(frame);
            }
            Client.instance.getConfigManager().loadClient();
        }
        frames.forEach(CategoryFrame::init);
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        int blurRadius = Client.instance.getModuleManager().getModule(Blur.class).radius.getInt();
        boolean doBlur = Client.instance.getModuleManager().getModule(Blur.class).isEnabled() && Client.instance.getModuleManager().getModule(Blur.class).clickgui.isEnabled();
        if (doBlur) {
            ScaledResolution sr = new ScaledResolution(mc);
            BlurUtil.doBlur(
                    blurRadius,
                    () -> DrawUtil.rect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), true, new Color(0x0))
            );
        }
        frames.forEach(frame -> frame.draw(this, mouseX, mouseY));
        frames.forEach(frame -> frame.drawDescriptions(mouseX, mouseY, partialTicks));
    }

    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        frames.forEach(frame -> frame.mouseClicked(mouseX, mouseY, mouseButton));
    }

    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        frames.forEach(frame -> frame.mouseReleased(mouseX, mouseY, mouseButton));
    }

    @Override
    public void mouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        frames.forEach(frame -> frame.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick));
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (focusedModule != null) {
            focusedModule.setKey(keyCode == Keyboard.KEY_ESCAPE ? Keyboard.KEY_NONE : keyCode);
            focusedModule = null;
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    public void onGuiClosed() {
        Client.instance.getModuleManager().getModule(ClickGUI.class).disable();
        Client.instance.getConfigManager().saveClient();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
