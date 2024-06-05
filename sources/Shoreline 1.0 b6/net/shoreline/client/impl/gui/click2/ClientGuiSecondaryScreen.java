package net.shoreline.client.impl.gui.click2;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec2f;
import net.shoreline.client.Shoreline;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.impl.gui.click2.component.CategoryFrame;
import net.shoreline.client.impl.gui.click2.impl.config.ConfigCategoryFrame;
import net.shoreline.client.impl.gui.click2.impl.module.ModuleCategoryFrame;
import net.shoreline.client.impl.module.client.ClickGuiModule;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

/**
 * @author xgraza
 * @since 03/30/24
 */
public final class ClientGuiSecondaryScreen extends Screen {

    private final ClickGuiModule module;
    private final List<CategoryFrame> frames = new LinkedList<>();
    private final ConfigCategoryFrame categoryFrame;
    private Vec2f mouse;

    public ClientGuiSecondaryScreen(final ClickGuiModule module) {
        super(Text.of("Click GUI"));
        this.module = module;

        // Add all of our module category frames
        float posX = 4.0f;
        for (final ModuleCategory category : ModuleCategory.values()) {
            final ModuleCategoryFrame frame = new ModuleCategoryFrame(category);
            frame.setX(posX);
            frame.setY(20.0f);
            frame.setWidth(120.0f);
            frame.setHeight(16.0f);

            posX += frame.getWidth() + 4.0f;
            frames.add(frame);
        }

        // Add our silly little config thing
        categoryFrame = new ConfigCategoryFrame();
        categoryFrame.setX(posX);
        categoryFrame.setY(20.0f);
        categoryFrame.setWidth(120.0f);
        categoryFrame.setHeight(16.0f);
        frames.add(categoryFrame);
    }

    @Override
    protected void init() {
        super.init();
        categoryFrame.populateChildren();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        for (final CategoryFrame frame : frames) {
            frame.draw(context, mouse, delta);
        }
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        mouse = new Vec2f((float) mouseX, (float) mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void filesDragged(List<Path> paths) {
        super.filesDragged(paths);
        // TODO: load configs :)
    }

    @Override
    public void close() {
        module.disable();
        Shoreline.CONFIG.saveModuleConfiguration("Modules");
        super.close();
    }
}
