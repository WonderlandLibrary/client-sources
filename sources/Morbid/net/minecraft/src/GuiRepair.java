package net.minecraft.src;

import net.minecraft.client.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import java.util.*;

public class GuiRepair extends GuiContainer implements ICrafting
{
    private ContainerRepair repairContainer;
    private GuiTextField itemNameField;
    private InventoryPlayer field_82325_q;
    
    public GuiRepair(final InventoryPlayer par1, final World par2World, final int par3, final int par4, final int par5) {
        Minecraft.getMinecraft();
        super(new ContainerRepair(par1, par2World, par3, par4, par5, Minecraft.thePlayer));
        this.field_82325_q = par1;
        this.repairContainer = (ContainerRepair)this.inventorySlots;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        Keyboard.enableRepeatEvents(true);
        final int var1 = (this.width - this.xSize) / 2;
        final int var2 = (this.height - this.ySize) / 2;
        (this.itemNameField = new GuiTextField(this.fontRenderer, var1 + 62, var2 + 24, 103, 12)).setTextColor(-1);
        this.itemNameField.setDisabledTextColour(-1);
        this.itemNameField.setEnableBackgroundDrawing(false);
        this.itemNameField.setMaxStringLength(30);
        this.inventorySlots.removeCraftingFromCrafters(this);
        this.inventorySlots.addCraftingToCrafters(this);
    }
    
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
        this.inventorySlots.removeCraftingFromCrafters(this);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int par1, final int par2) {
        GL11.glDisable(2896);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.repair"), 60, 6, 4210752);
        if (this.repairContainer.maximumCost > 0) {
            int var3 = 8453920;
            boolean var4 = true;
            String var5 = StatCollector.translateToLocalFormatted("container.repair.cost", this.repairContainer.maximumCost);
            if (this.repairContainer.maximumCost >= 40 && !Minecraft.thePlayer.capabilities.isCreativeMode) {
                var5 = StatCollector.translateToLocal("container.repair.expensive");
                var3 = 16736352;
            }
            else if (!this.repairContainer.getSlot(2).getHasStack()) {
                var4 = false;
            }
            else if (!this.repairContainer.getSlot(2).canTakeStack(this.field_82325_q.player)) {
                var3 = 16736352;
            }
            if (var4) {
                final int var6 = 0xFF000000 | (var3 & 0xFCFCFC) >> 2 | (var3 & 0xFF000000);
                final int var7 = this.xSize - 8 - this.fontRenderer.getStringWidth(var5);
                final byte var8 = 67;
                if (this.fontRenderer.getUnicodeFlag()) {
                    Gui.drawRect(var7 - 3, var8 - 2, this.xSize - 7, var8 + 10, -16777216);
                    Gui.drawRect(var7 - 2, var8 - 1, this.xSize - 8, var8 + 9, -12895429);
                }
                else {
                    this.fontRenderer.drawString(var5, var7, var8 + 1, var6);
                    this.fontRenderer.drawString(var5, var7 + 1, var8, var6);
                    this.fontRenderer.drawString(var5, var7 + 1, var8 + 1, var6);
                }
                this.fontRenderer.drawString(var5, var7, var8, var3);
            }
        }
        GL11.glEnable(2896);
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
        if (this.itemNameField.textboxKeyTyped(par1, par2)) {
            this.repairContainer.updateItemName(this.itemNameField.getText());
            Minecraft.thePlayer.sendQueue.addToSendQueue(new Packet250CustomPayload("MC|ItemName", this.itemNameField.getText().getBytes()));
        }
        else {
            super.keyTyped(par1, par2);
        }
    }
    
    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) {
        super.mouseClicked(par1, par2, par3);
        this.itemNameField.mouseClicked(par1, par2, par3);
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        super.drawScreen(par1, par2, par3);
        GL11.glDisable(2896);
        this.itemNameField.drawTextBox();
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float par1, final int par2, final int par3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture("/gui/repair.png");
        final int var4 = (this.width - this.xSize) / 2;
        final int var5 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.xSize, this.ySize);
        this.drawTexturedModalRect(var4 + 59, var5 + 20, 0, this.ySize + (this.repairContainer.getSlot(0).getHasStack() ? 0 : 16), 110, 16);
        if ((this.repairContainer.getSlot(0).getHasStack() || this.repairContainer.getSlot(1).getHasStack()) && !this.repairContainer.getSlot(2).getHasStack()) {
            this.drawTexturedModalRect(var4 + 99, var5 + 45, this.xSize, 0, 28, 21);
        }
    }
    
    @Override
    public void sendContainerAndContentsToPlayer(final Container par1Container, final List par2List) {
        this.sendSlotContents(par1Container, 0, par1Container.getSlot(0).getStack());
    }
    
    @Override
    public void sendSlotContents(final Container par1Container, final int par2, final ItemStack par3ItemStack) {
        if (par2 == 0) {
            this.itemNameField.setText((par3ItemStack == null) ? "" : par3ItemStack.getDisplayName());
            this.itemNameField.setEnabled(par3ItemStack != null);
            if (par3ItemStack != null) {
                this.repairContainer.updateItemName(this.itemNameField.getText());
                Minecraft.thePlayer.sendQueue.addToSendQueue(new Packet250CustomPayload("MC|ItemName", this.itemNameField.getText().getBytes()));
            }
        }
    }
    
    @Override
    public void sendProgressBarUpdate(final Container par1Container, final int par2, final int par3) {
    }
}
