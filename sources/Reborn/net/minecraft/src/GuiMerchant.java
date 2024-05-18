package net.minecraft.src;

import net.minecraft.client.*;
import java.io.*;
import org.lwjgl.opengl.*;

public class GuiMerchant extends GuiContainer
{
    private IMerchant theIMerchant;
    private GuiButtonMerchant nextRecipeButtonIndex;
    private GuiButtonMerchant previousRecipeButtonIndex;
    private int currentRecipeIndex;
    private String field_94082_v;
    
    public GuiMerchant(final InventoryPlayer par1, final IMerchant par2, final World par3World, final String par4) {
        super(new ContainerMerchant(par1, par2, par3World));
        this.currentRecipeIndex = 0;
        this.theIMerchant = par2;
        this.field_94082_v = ((par4 != null && par4.length() >= 1) ? par4 : StatCollector.translateToLocal("entity.Villager.name"));
    }
    
    @Override
    public void initGui() {
        super.initGui();
        final int var1 = (this.width - this.xSize) / 2;
        final int var2 = (this.height - this.ySize) / 2;
        this.buttonList.add(this.nextRecipeButtonIndex = new GuiButtonMerchant(1, var1 + 120 + 27, var2 + 24 - 1, true));
        this.buttonList.add(this.previousRecipeButtonIndex = new GuiButtonMerchant(2, var1 + 36 - 19, var2 + 24 - 1, false));
        this.nextRecipeButtonIndex.enabled = false;
        this.previousRecipeButtonIndex.enabled = false;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int par1, final int par2) {
        this.fontRenderer.drawString(this.field_94082_v, this.xSize / 2 - this.fontRenderer.getStringWidth(this.field_94082_v) / 2, 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        final MerchantRecipeList var1 = this.theIMerchant.getRecipes(Minecraft.thePlayer);
        if (var1 != null) {
            this.nextRecipeButtonIndex.enabled = (this.currentRecipeIndex < var1.size() - 1);
            this.previousRecipeButtonIndex.enabled = (this.currentRecipeIndex > 0);
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        boolean var2 = false;
        if (par1GuiButton == this.nextRecipeButtonIndex) {
            ++this.currentRecipeIndex;
            var2 = true;
        }
        else if (par1GuiButton == this.previousRecipeButtonIndex) {
            --this.currentRecipeIndex;
            var2 = true;
        }
        if (var2) {
            ((ContainerMerchant)this.inventorySlots).setCurrentRecipeIndex(this.currentRecipeIndex);
            final ByteArrayOutputStream var3 = new ByteArrayOutputStream();
            final DataOutputStream var4 = new DataOutputStream(var3);
            try {
                var4.writeInt(this.currentRecipeIndex);
                this.mc.getNetHandler().addToSendQueue(new Packet250CustomPayload("MC|TrSel", var3.toByteArray()));
            }
            catch (Exception var5) {
                var5.printStackTrace();
            }
        }
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float par1, final int par2, final int par3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture("/gui/trading.png");
        final int var4 = (this.width - this.xSize) / 2;
        final int var5 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.xSize, this.ySize);
        final MerchantRecipeList var6 = this.theIMerchant.getRecipes(Minecraft.thePlayer);
        if (var6 != null && !var6.isEmpty()) {
            final int var7 = this.currentRecipeIndex;
            final MerchantRecipe var8 = var6.get(var7);
            if (var8.func_82784_g()) {
                this.mc.renderEngine.bindTexture("/gui/trading.png");
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glDisable(2896);
                this.drawTexturedModalRect(this.guiLeft + 83, this.guiTop + 21, 212, 0, 28, 21);
                this.drawTexturedModalRect(this.guiLeft + 83, this.guiTop + 51, 212, 0, 28, 21);
            }
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        super.drawScreen(par1, par2, par3);
        final MerchantRecipeList var4 = this.theIMerchant.getRecipes(Minecraft.thePlayer);
        if (var4 != null && !var4.isEmpty()) {
            final int var5 = (this.width - this.xSize) / 2;
            final int var6 = (this.height - this.ySize) / 2;
            final int var7 = this.currentRecipeIndex;
            final MerchantRecipe var8 = var4.get(var7);
            GL11.glPushMatrix();
            final ItemStack var9 = var8.getItemToBuy();
            final ItemStack var10 = var8.getSecondItemToBuy();
            final ItemStack var11 = var8.getItemToSell();
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glDisable(2896);
            GL11.glEnable(32826);
            GL11.glEnable(2903);
            GL11.glEnable(2896);
            final RenderItem itemRenderer = GuiMerchant.itemRenderer;
            RenderItem.zLevel = 100.0f;
            final RenderItem itemRenderer2 = GuiMerchant.itemRenderer;
            RenderItem.renderItemAndEffectIntoGUI(this.fontRenderer, this.mc.renderEngine, var9, var5 + 36, var6 + 24);
            final RenderItem itemRenderer3 = GuiMerchant.itemRenderer;
            RenderItem.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, var9, var5 + 36, var6 + 24);
            if (var10 != null) {
                final RenderItem itemRenderer4 = GuiMerchant.itemRenderer;
                RenderItem.renderItemAndEffectIntoGUI(this.fontRenderer, this.mc.renderEngine, var10, var5 + 62, var6 + 24);
                final RenderItem itemRenderer5 = GuiMerchant.itemRenderer;
                RenderItem.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, var10, var5 + 62, var6 + 24);
            }
            final RenderItem itemRenderer6 = GuiMerchant.itemRenderer;
            RenderItem.renderItemAndEffectIntoGUI(this.fontRenderer, this.mc.renderEngine, var11, var5 + 120, var6 + 24);
            final RenderItem itemRenderer7 = GuiMerchant.itemRenderer;
            RenderItem.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, var11, var5 + 120, var6 + 24);
            final RenderItem itemRenderer8 = GuiMerchant.itemRenderer;
            RenderItem.zLevel = 0.0f;
            GL11.glDisable(2896);
            if (this.isPointInRegion(36, 24, 16, 16, par1, par2)) {
                this.drawItemStackTooltip(var9, par1, par2);
            }
            else if (var10 != null && this.isPointInRegion(62, 24, 16, 16, par1, par2)) {
                this.drawItemStackTooltip(var10, par1, par2);
            }
            else if (this.isPointInRegion(120, 24, 16, 16, par1, par2)) {
                this.drawItemStackTooltip(var11, par1, par2);
            }
            GL11.glPopMatrix();
            GL11.glEnable(2896);
            GL11.glEnable(2929);
            RenderHelper.enableStandardItemLighting();
        }
    }
    
    public IMerchant getIMerchant() {
        return this.theIMerchant;
    }
}
