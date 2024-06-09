// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.gui.skeet;

import net.minecraft.client.gui.GuiButton;
import java.io.IOException;
import java.util.Iterator;
import net.minecraft.util.ResourceLocation;
import intent.AquaDev.aqua.utils.RenderUtil;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import net.minecraft.client.gui.ScaledResolution;
import intent.AquaDev.aqua.modules.Category;
import java.util.ArrayList;
import intent.AquaDev.aqua.gui.skeet.components.CategoryPaneSkeet;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;

public class ClickGuiScreenSkeet extends GuiScreen
{
    private final List<CategoryPaneSkeet> categoryPanes;
    
    public ClickGuiScreenSkeet() {
        this.categoryPanes = new ArrayList<CategoryPaneSkeet>();
    }
    
    @Override
    public void initGui() {
        int x = 120;
        for (final Category category : Category.values()) {
            final CategoryPaneSkeet categoryPane = new CategoryPaneSkeet(x, 10, 100, 20, category, this);
            this.categoryPanes.add(categoryPane);
            x += 60;
        }
        super.initGui();
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        final int posX = (int)(sr.getScaledWidth() / 2.0f - 170.0f);
        final int posY = 120;
        final int width = (int)(sr.getScaledWidth() / 2.0f + 170.0f);
        final int height = (int)(sr.getScaledHeight() / 2.0f + 150.0f);
        Gui.drawRect2(posX - 2, posY - 2, width + 2, height + 1.5, new Color(60, 60, 60, 255).getRGB());
        GL11.glEnable(3089);
        RenderUtil.scissor(posX, posY, width - posX, height - posY);
        Gui.drawRect2(posX, posY, width, height, new Color(20, 20, 20, 255).getRGB());
        RenderUtil.drawImageDarker(0, 0, this.mc.displayWidth, this.mc.displayHeight, new ResourceLocation("Aqua/gui/skeet.png"));
        RenderUtil.drawImage(posX + 5, posY + 5, 40, 40, new ResourceLocation("Aqua/gui/1.png"));
        RenderUtil.drawImage(posX + 2, posY + 85, 40, 40, new ResourceLocation("Aqua/gui/2.png"));
        RenderUtil.drawImage(posX + 9, posY + 135, 30, 30, new ResourceLocation("Aqua/gui/4.png"));
        RenderUtil.drawImage(posX + 9, posY + 175, 30, 30, new ResourceLocation("Aqua/gui/3.png"));
        GL11.glDisable(3089);
        RenderUtil.drawRGBLineHorizontal(posX + 0.5f, posY + 1, width - posX - 1, 2.0f, 0.2f, true);
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (CategoryPaneSkeet categoryPaneSkeet : this.categoryPanes) {}
    }
    
    @Override
    protected void mouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        for (final CategoryPaneSkeet categoryPane : this.categoryPanes) {
            categoryPane.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        }
    }
    
    @Override
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }
    
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }
    
    private boolean mouseOver(final int x, final int y, final int modX, final int modY, final int modWidth, final int modHeight) {
        return x >= modX && x <= modX + modWidth && y >= modY && y <= modY + modHeight;
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) throws IOException {
    }
    
    public List<CategoryPaneSkeet> getCategoryPanes() {
        return this.categoryPanes;
    }
}
