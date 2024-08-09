package net.minecraft.client.gui.inventory;

import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.utility.math.BlurUtility;
import dev.darkmoon.client.utility.render.RenderUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerShulkerBox;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class GuiShulkerBox extends GuiContainer
{
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("textures/gui/container/shulker_box.png");
    private final IInventory inventory;
    private final InventoryPlayer playerInventory;
    public int blur;

    public GuiShulkerBox(InventoryPlayer playerInventoryIn, IInventory inventoryIn)
    {
        super(new ContainerShulkerBox(playerInventoryIn, inventoryIn, Minecraft.getMinecraft().player));
        this.playerInventory = playerInventoryIn;
        this.inventory = inventoryIn;
        ++this.ySize;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void initGui() {
        blur = 0;
        super.initGui();
    }
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        ++this.blur;
        ScaledResolution sr = new ScaledResolution(this.mc);
        this.blur = MathHelper.clamp(this.blur, 0, DarkMoon.getInstance().getModuleManager().blurContainer.blurStrength.getInt());
        if (DarkMoon.getInstance().getModuleManager().blurContainer.isEnabled()) {
            if (this.mc.gameSettings.ofFastRender) {
                this.mc.gameSettings.ofFastRender = false;
            }
            BlurUtility.renderBlur(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), this.blur / 2);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRenderer.drawString(this.inventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draws the background layer of this container (behind the items).
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }
}
