package net.minecraft.client.gui.inventory;

import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.utility.math.BlurUtility;
import dev.darkmoon.client.utility.render.RenderUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import java.awt.*;
import java.io.IOException;

public class GuiChest extends GuiContainer
{
    /** The ResourceLocation containing the chest GUI texture. */
    private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
    private final IInventory upperChestInventory;
    private int blur;
    /**
     * The chest's inventory. Number of slots will vary based off of the type of chest.
     */
    public final IInventory lowerChestInventory;

    /**
     * Window height is calculated with these values; the more rows, the higher
     */
    private final int inventoryRows;

    public GuiChest(IInventory upperInv, IInventory lowerInv)
    {
        super(new ContainerChest(upperInv, lowerInv, Minecraft.getMinecraft().player));
        this.upperChestInventory = upperInv;
        this.lowerChestInventory = lowerInv;
        this.allowUserInput = false;
        int i = 222;
        int j = 114;
        this.inventoryRows = lowerInv.getSizeInventory() / 9;
        this.ySize = 114 + this.inventoryRows * 18;
    }

    @Override
    public void initGui() {
        String text = this.lowerChestInventory.getDisplayName().getUnformattedText();
        blur = 0;
        if (text.toLowerCase().contains("ύνδεπ") || text.equals(I18n.format("container.enderchest")) || text.equals(I18n.format("container.chestDouble")) || text.equals(I18n.format("container.chest"))) {
            this.buttonList.add(new GuiButton(31, this.width / 2 - 36, this.height / 2 - 105 - ((inventoryRows - 3) * 9), 75, 20, "Store All"));
        }
        super.initGui();
    }

    /**
     * Draws the screen and all the components in it.
     */
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
        this.fontRenderer.drawString(this.lowerChestInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
        this.fontRenderer.drawString(this.upperChestInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draws the background layer of this container (behind the items).
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(CHEST_GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.drawTexturedModalRect(i, j + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 31) {
            this.inventorySlots.inventorySlots.forEach((slot -> this.handleMouseClick(slot, slot.slotNumber, 0, ClickType.QUICK_MOVE)));
        }
        super.actionPerformed(button);
    }
}
