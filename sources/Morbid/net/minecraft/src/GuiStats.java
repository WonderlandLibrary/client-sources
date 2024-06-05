package net.minecraft.src;

import org.lwjgl.opengl.*;
import net.minecraft.client.*;

public class GuiStats extends GuiScreen
{
    private static RenderItem renderItem;
    protected GuiScreen parentGui;
    protected String statsTitle;
    private GuiSlotStatsGeneral slotGeneral;
    private GuiSlotStatsItem slotItem;
    private GuiSlotStatsBlock slotBlock;
    private StatFileWriter statFileWriter;
    private GuiSlot selectedSlot;
    
    static {
        GuiStats.renderItem = new RenderItem();
    }
    
    public GuiStats(final GuiScreen par1GuiScreen, final StatFileWriter par2StatFileWriter) {
        this.statsTitle = "Select world";
        this.selectedSlot = null;
        this.parentGui = par1GuiScreen;
        this.statFileWriter = par2StatFileWriter;
    }
    
    @Override
    public void initGui() {
        this.statsTitle = StatCollector.translateToLocal("gui.stats");
        (this.slotGeneral = new GuiSlotStatsGeneral(this)).registerScrollButtons(this.buttonList, 1, 1);
        (this.slotItem = new GuiSlotStatsItem(this)).registerScrollButtons(this.buttonList, 1, 1);
        (this.slotBlock = new GuiSlotStatsBlock(this)).registerScrollButtons(this.buttonList, 1, 1);
        this.selectedSlot = this.slotGeneral;
        this.addHeaderButtons();
    }
    
    public void addHeaderButtons() {
        final StringTranslate var1 = StringTranslate.getInstance();
        this.buttonList.add(new GuiButton(0, this.width / 2 + 4, this.height - 28, 150, 20, var1.translateKey("gui.done")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 154, this.height - 52, 100, 20, var1.translateKey("stat.generalButton")));
        final GuiButton var2;
        this.buttonList.add(var2 = new GuiButton(2, this.width / 2 - 46, this.height - 52, 100, 20, var1.translateKey("stat.blocksButton")));
        final GuiButton var3;
        this.buttonList.add(var3 = new GuiButton(3, this.width / 2 + 62, this.height - 52, 100, 20, var1.translateKey("stat.itemsButton")));
        if (this.slotBlock.getSize() == 0) {
            var2.enabled = false;
        }
        if (this.slotItem.getSize() == 0) {
            var3.enabled = false;
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id == 0) {
                this.mc.displayGuiScreen(this.parentGui);
            }
            else if (par1GuiButton.id == 1) {
                this.selectedSlot = this.slotGeneral;
            }
            else if (par1GuiButton.id == 3) {
                this.selectedSlot = this.slotItem;
            }
            else if (par1GuiButton.id == 2) {
                this.selectedSlot = this.slotBlock;
            }
            else {
                this.selectedSlot.actionPerformed(par1GuiButton);
            }
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.selectedSlot.drawScreen(par1, par2, par3);
        this.drawCenteredString(this.fontRenderer, this.statsTitle, this.width / 2, 20, 16777215);
        super.drawScreen(par1, par2, par3);
    }
    
    private void drawItemSprite(final int par1, final int par2, final int par3) {
        this.drawButtonBackground(par1 + 1, par2 + 1);
        GL11.glEnable(32826);
        RenderHelper.enableGUIStandardItemLighting();
        RenderItem.renderItemIntoGUI(this.fontRenderer, this.mc.renderEngine, new ItemStack(par3, 1, 0), par1 + 2, par2 + 2);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(32826);
    }
    
    private void drawButtonBackground(final int par1, final int par2) {
        this.drawSprite(par1, par2, 0, 0);
    }
    
    private void drawSprite(final int par1, final int par2, final int par3, final int par4) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture("/gui/slot.png");
        final Tessellator var5 = Tessellator.instance;
        var5.startDrawingQuads();
        var5.addVertexWithUV(par1 + 0, par2 + 18, this.zLevel, (par3 + 0) * 0.0078125f, (par4 + 18) * 0.0078125f);
        var5.addVertexWithUV(par1 + 18, par2 + 18, this.zLevel, (par3 + 18) * 0.0078125f, (par4 + 18) * 0.0078125f);
        var5.addVertexWithUV(par1 + 18, par2 + 0, this.zLevel, (par3 + 18) * 0.0078125f, (par4 + 0) * 0.0078125f);
        var5.addVertexWithUV(par1 + 0, par2 + 0, this.zLevel, (par3 + 0) * 0.0078125f, (par4 + 0) * 0.0078125f);
        var5.draw();
    }
    
    static Minecraft getMinecraft(final GuiStats par0GuiStats) {
        return par0GuiStats.mc;
    }
    
    static FontRenderer getFontRenderer1(final GuiStats par0GuiStats) {
        return par0GuiStats.fontRenderer;
    }
    
    static StatFileWriter getStatsFileWriter(final GuiStats par0GuiStats) {
        return par0GuiStats.statFileWriter;
    }
    
    static FontRenderer getFontRenderer2(final GuiStats par0GuiStats) {
        return par0GuiStats.fontRenderer;
    }
    
    static FontRenderer getFontRenderer3(final GuiStats par0GuiStats) {
        return par0GuiStats.fontRenderer;
    }
    
    static Minecraft getMinecraft1(final GuiStats par0GuiStats) {
        return par0GuiStats.mc;
    }
    
    static void drawSprite(final GuiStats par0GuiStats, final int par1, final int par2, final int par3, final int par4) {
        par0GuiStats.drawSprite(par1, par2, par3, par4);
    }
    
    static Minecraft getMinecraft2(final GuiStats par0GuiStats) {
        return par0GuiStats.mc;
    }
    
    static FontRenderer getFontRenderer4(final GuiStats par0GuiStats) {
        return par0GuiStats.fontRenderer;
    }
    
    static FontRenderer getFontRenderer5(final GuiStats par0GuiStats) {
        return par0GuiStats.fontRenderer;
    }
    
    static FontRenderer getFontRenderer6(final GuiStats par0GuiStats) {
        return par0GuiStats.fontRenderer;
    }
    
    static FontRenderer getFontRenderer7(final GuiStats par0GuiStats) {
        return par0GuiStats.fontRenderer;
    }
    
    static FontRenderer getFontRenderer8(final GuiStats par0GuiStats) {
        return par0GuiStats.fontRenderer;
    }
    
    static void drawGradientRect(final GuiStats par0GuiStats, final int par1, final int par2, final int par3, final int par4, final int par5, final int par6) {
        par0GuiStats.drawGradientRect(par1, par2, par3, par4, par5, par6);
    }
    
    static FontRenderer getFontRenderer9(final GuiStats par0GuiStats) {
        return par0GuiStats.fontRenderer;
    }
    
    static FontRenderer getFontRenderer10(final GuiStats par0GuiStats) {
        return par0GuiStats.fontRenderer;
    }
    
    static void drawGradientRect1(final GuiStats par0GuiStats, final int par1, final int par2, final int par3, final int par4, final int par5, final int par6) {
        par0GuiStats.drawGradientRect(par1, par2, par3, par4, par5, par6);
    }
    
    static FontRenderer getFontRenderer11(final GuiStats par0GuiStats) {
        return par0GuiStats.fontRenderer;
    }
    
    static void drawItemSprite(final GuiStats par0GuiStats, final int par1, final int par2, final int par3) {
        par0GuiStats.drawItemSprite(par1, par2, par3);
    }
}
