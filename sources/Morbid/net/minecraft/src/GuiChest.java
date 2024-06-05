package net.minecraft.src;

import me.enrythebest.reborn.cracked.*;
import me.enrythebest.reborn.cracked.mods.manager.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;

public class GuiChest extends GuiContainer
{
    private IInventory upperChestInventory;
    private IInventory lowerChestInventory;
    private int inventoryRows;
    private int MaxSlots;
    private boolean Raiding;
    private boolean Storing;
    private boolean StartSay;
    private boolean Register;
    private int RaidingTick;
    private int StoringTick;
    private int StoringLastTick;
    private int RaidingLastTick;
    
    @Override
    public void initGui() {
        super.initGui();
        Morbid.getManager();
        if (!ModManager.getMod("vanilla").isEnabled()) {
            final int var1 = this.width / 2;
            final int var2 = this.height / 2;
            this.buttonList.add(new GuiButton(1, this.width / 2 + 90, this.height / 2 - 100, 98, 20, "Store"));
            this.buttonList.add(new GuiButton(0, this.width / 2 - 190, this.height / 2 - 100, 98, 20, "Steal"));
        }
    }
    
    public void actionPerformed(final GuiButton var1) {
        super.actionPerformed(var1);
        if (var1.id == 0) {
            this.StartRaiding();
        }
        if (var1.id == 1) {
            this.StartStoring();
        }
    }
    
    public void StartRaiding() {
        if (!this.Storing) {
            this.Raiding = true;
            this.RaidingTick = 0;
            this.RaidingLastTick = 0;
            this.MaxSlots = ((this.inventorySlots.inventorySlots.size() == 90) ? 54 : 27);
        }
    }
    
    @Override
    public void onGuiClosed() {
        this.Raiding = false;
        this.Storing = false;
        this.StartSay = false;
        this.Register = false;
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        if (this.Raiding) {
            if (this.RaidingLastTick > 1) {
                this.RaidingLastTick = 0;
                if (this.RaidingTick < this.MaxSlots) {
                    while (this.inventorySlots.getSlot(this.RaidingTick).getStack() == null && this.RaidingTick < this.MaxSlots) {
                        ++this.RaidingTick;
                    }
                    if (this.RaidingTick < this.MaxSlots) {
                        this.mc.playerController.windowClick(this.inventorySlots.windowId, this.RaidingTick, 0, 1, Minecraft.thePlayer);
                        ++this.RaidingTick;
                    }
                }
                else {
                    this.Raiding = false;
                    this.RaidingTick = 0;
                }
            }
            ++this.RaidingLastTick;
        }
        if (this.Storing) {
            if (this.StoringLastTick > 1) {
                this.StoringLastTick = 0;
                if (this.StoringTick < this.MaxSlots) {
                    while (this.StoringTick < this.MaxSlots && this.inventorySlots.getSlot(this.StoringTick).getStack() == null) {
                        ++this.StoringTick;
                    }
                    if (this.StoringTick < this.MaxSlots) {
                        this.mc.playerController.windowClick(this.inventorySlots.windowId, this.StoringTick, 0, 1, Minecraft.thePlayer);
                        ++this.StoringTick;
                    }
                }
                else {
                    this.Storing = false;
                    this.StoringTick = 0;
                }
            }
            ++this.StoringLastTick;
        }
    }
    
    public void StartStoring() {
        if (!this.Raiding) {
            this.Storing = true;
            this.StoringTick = 0;
            if (this.inventorySlots.inventorySlots.size() == 90) {
                this.StoringTick = 54;
            }
            else {
                this.StoringTick = 27;
            }
            this.MaxSlots = ((this.inventorySlots.inventorySlots.size() == 90) ? 90 : 63);
        }
    }
    
    public GuiChest(final IInventory par1IInventory, final IInventory par2IInventory) {
        super(new ContainerChest(par1IInventory, par2IInventory));
        this.inventoryRows = 0;
        this.upperChestInventory = par1IInventory;
        this.lowerChestInventory = par2IInventory;
        this.allowUserInput = false;
        final short var3 = 222;
        final int var4 = var3 - 108;
        this.inventoryRows = par2IInventory.getSizeInventory() / 9;
        this.ySize = var4 + this.inventoryRows * 18;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int par1, final int par2) {
        this.fontRenderer.drawString(this.lowerChestInventory.isInvNameLocalized() ? this.lowerChestInventory.getInvName() : StatCollector.translateToLocal(this.lowerChestInventory.getInvName()), 8, 6, 4210752);
        this.fontRenderer.drawString(this.upperChestInventory.isInvNameLocalized() ? this.upperChestInventory.getInvName() : StatCollector.translateToLocal(this.upperChestInventory.getInvName()), 8, this.ySize - 96 + 2, 4210752);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float par1, final int par2, final int par3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture("/gui/container.png");
        final int var4 = (this.width - this.xSize) / 2;
        final int var5 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.drawTexturedModalRect(var4, var5 + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }
}
