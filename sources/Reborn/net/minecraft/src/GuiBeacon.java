package net.minecraft.src;

import java.io.*;
import java.util.*;
import org.lwjgl.opengl.*;

public class GuiBeacon extends GuiContainer
{
    private TileEntityBeacon beacon;
    private GuiBeaconButtonConfirm beaconConfirmButton;
    private boolean buttonsNotDrawn;
    
    public GuiBeacon(final InventoryPlayer par1, final TileEntityBeacon par2) {
        super(new ContainerBeacon(par1, par2));
        this.beacon = par2;
        this.xSize = 230;
        this.ySize = 219;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(this.beaconConfirmButton = new GuiBeaconButtonConfirm(this, -1, this.guiLeft + 164, this.guiTop + 107));
        this.buttonList.add(new GuiBeaconButtonCancel(this, -2, this.guiLeft + 190, this.guiTop + 107));
        this.buttonsNotDrawn = true;
        this.beaconConfirmButton.enabled = false;
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        if (this.buttonsNotDrawn && this.beacon.getLevels() >= 0) {
            this.buttonsNotDrawn = false;
            for (int var6 = 0; var6 <= 2; ++var6) {
                final int var7 = TileEntityBeacon.effectsList[var6].length;
                final int var8 = var7 * 22 + (var7 - 1) * 2;
                for (int var9 = 0; var9 < var7; ++var9) {
                    final int var10 = TileEntityBeacon.effectsList[var6][var9].id;
                    final GuiBeaconButtonPower var11 = new GuiBeaconButtonPower(this, var6 << 8 | var10, this.guiLeft + 76 + var9 * 24 - var8 / 2, this.guiTop + 22 + var6 * 25, var10, var6);
                    this.buttonList.add(var11);
                    if (var6 >= this.beacon.getLevels()) {
                        var11.enabled = false;
                    }
                    else if (var10 == this.beacon.getPrimaryEffect()) {
                        var11.func_82254_b(true);
                    }
                }
            }
            final byte var12 = 3;
            final int var7 = TileEntityBeacon.effectsList[var12].length + 1;
            final int var8 = var7 * 22 + (var7 - 1) * 2;
            for (int var9 = 0; var9 < var7 - 1; ++var9) {
                final int var10 = TileEntityBeacon.effectsList[var12][var9].id;
                final GuiBeaconButtonPower var11 = new GuiBeaconButtonPower(this, var12 << 8 | var10, this.guiLeft + 167 + var9 * 24 - var8 / 2, this.guiTop + 47, var10, var12);
                this.buttonList.add(var11);
                if (var12 >= this.beacon.getLevels()) {
                    var11.enabled = false;
                }
                else if (var10 == this.beacon.getSecondaryEffect()) {
                    var11.func_82254_b(true);
                }
            }
            if (this.beacon.getPrimaryEffect() > 0) {
                final GuiBeaconButtonPower var13 = new GuiBeaconButtonPower(this, var12 << 8 | this.beacon.getPrimaryEffect(), this.guiLeft + 167 + (var7 - 1) * 24 - var8 / 2, this.guiTop + 47, this.beacon.getPrimaryEffect(), var12);
                this.buttonList.add(var13);
                if (var12 >= this.beacon.getLevels()) {
                    var13.enabled = false;
                }
                else if (this.beacon.getPrimaryEffect() == this.beacon.getSecondaryEffect()) {
                    var13.func_82254_b(true);
                }
            }
        }
        this.beaconConfirmButton.enabled = (this.beacon.getStackInSlot(0) != null && this.beacon.getPrimaryEffect() > 0);
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.id == -2) {
            this.mc.displayGuiScreen(null);
        }
        else if (par1GuiButton.id == -1) {
            final String var2 = "MC|Beacon";
            final ByteArrayOutputStream var3 = new ByteArrayOutputStream();
            final DataOutputStream var4 = new DataOutputStream(var3);
            try {
                var4.writeInt(this.beacon.getPrimaryEffect());
                var4.writeInt(this.beacon.getSecondaryEffect());
                this.mc.getNetHandler().addToSendQueue(new Packet250CustomPayload(var2, var3.toByteArray()));
            }
            catch (Exception var5) {
                var5.printStackTrace();
            }
            this.mc.displayGuiScreen(null);
        }
        else if (par1GuiButton instanceof GuiBeaconButtonPower) {
            if (((GuiBeaconButtonPower)par1GuiButton).func_82255_b()) {
                return;
            }
            final int var6 = par1GuiButton.id;
            final int var7 = var6 & 0xFF;
            final int var8 = var6 >> 8;
            if (var8 < 3) {
                this.beacon.setPrimaryEffect(var7);
            }
            else {
                this.beacon.setSecondaryEffect(var7);
            }
            this.buttonList.clear();
            this.initGui();
            this.updateScreen();
        }
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int par1, final int par2) {
        RenderHelper.disableStandardItemLighting();
        this.drawCenteredString(this.fontRenderer, StatCollector.translateToLocal("tile.beacon.primary"), 62, 10, 14737632);
        this.drawCenteredString(this.fontRenderer, StatCollector.translateToLocal("tile.beacon.secondary"), 169, 10, 14737632);
        for (final GuiButton var4 : this.buttonList) {
            if (var4.func_82252_a()) {
                var4.func_82251_b(par1 - this.guiLeft, par2 - this.guiTop);
                break;
            }
        }
        RenderHelper.enableGUIStandardItemLighting();
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float par1, final int par2, final int par3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture("/gui/beacon.png");
        final int var4 = (this.width - this.xSize) / 2;
        final int var5 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.xSize, this.ySize);
        final RenderItem itemRenderer = GuiBeacon.itemRenderer;
        RenderItem.zLevel = 100.0f;
        final RenderItem itemRenderer2 = GuiBeacon.itemRenderer;
        RenderItem.renderItemAndEffectIntoGUI(this.fontRenderer, this.mc.renderEngine, new ItemStack(Item.emerald), var4 + 42, var5 + 109);
        final RenderItem itemRenderer3 = GuiBeacon.itemRenderer;
        RenderItem.renderItemAndEffectIntoGUI(this.fontRenderer, this.mc.renderEngine, new ItemStack(Item.diamond), var4 + 42 + 22, var5 + 109);
        final RenderItem itemRenderer4 = GuiBeacon.itemRenderer;
        RenderItem.renderItemAndEffectIntoGUI(this.fontRenderer, this.mc.renderEngine, new ItemStack(Item.ingotGold), var4 + 42 + 44, var5 + 109);
        final RenderItem itemRenderer5 = GuiBeacon.itemRenderer;
        RenderItem.renderItemAndEffectIntoGUI(this.fontRenderer, this.mc.renderEngine, new ItemStack(Item.ingotIron), var4 + 42 + 66, var5 + 109);
        final RenderItem itemRenderer6 = GuiBeacon.itemRenderer;
        RenderItem.zLevel = 0.0f;
    }
}
