package net.minecraft.client.gui.inventory;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiChest extends GuiContainer
{
    /** The ResourceLocation containing the chest GUI texture. */
    private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
    private IInventory upperChestInventory;
    private IInventory lowerChestInventory;

    private boolean stealing, storing, shutdown;
    
    /**
     * window height is calculated with these values; the more rows, the heigher
     */
    private int inventoryRows;

    public GuiChest(IInventory upperInv, IInventory lowerInv)
    {
        super(new ContainerChest(upperInv, lowerInv, Minecraft.getMinecraft().thePlayer));
        this.upperChestInventory = upperInv;
        this.lowerChestInventory = lowerInv;
        this.allowUserInput = false;
        short var3 = 222;
        int var4 = var3 - 108;
        this.inventoryRows = lowerInv.getSizeInventory() / 9;
        this.ySize = var4 + this.inventoryRows * 18;
    }


    @Override
	public void initGui()
    {
        this.buttonList.clear();

        super.initGui();
        
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.guiLeft, this.guiTop-20, (this.xSize/2)-2, 20, "Steal"));
        this.buttonList.add(new GuiButton(1, this.guiLeft+(this.xSize/2)+2, this.guiTop-20, (this.xSize/2)-2, 20, "Store"));
    }

    @Override
	protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 0) {
        	if (!stealing) {
        		stealing = true;
        		new Thread() {
        			@Override
        			public void run() {
        				int size = GuiChest.this.inventorySlots.inventorySlots.size();
        	        	for(int i = 0; i < size-35; i++) {
        	        		if (shutdown) {
        	        			return;
        	        		}
        	        		if (GuiChest.this.inventorySlots.getSlot(i).getHasStack()) {
        						mc.playerController.windowClick(GuiChest.this.inventorySlots.windowId, i, 0, 1, mc.thePlayer);
        						try {
        							sleep(100);
        						} catch (Exception e) {}
        	        		}
        	        	}
        			}
        		}.start();
        	}
        } else if (button.id == 1) {
        	if (!stealing) {
        		stealing = true;
        		new Thread() {
        			@Override
        			public void run() {
        				int size = GuiChest.this.inventorySlots.inventorySlots.size();
        	        	for(int i = size-35; i < size; i++) {
        	        		if (shutdown) {
        	        			return;
        	        		}
        	        		if (GuiChest.this.inventorySlots.getSlot(i).getHasStack()) {
        						mc.playerController.windowClick(GuiChest.this.inventorySlots.windowId, i, 0, 1, mc.thePlayer);
        						try {
        							sleep(100);
        						} catch (Exception e) {}
        	        		}
        	        	}
        			}
        		}.start();
        	}
        }
    }
    
    @Override
	public void onGuiClosed()
    {
    	shutdown = true;
        super.onGuiClosed();
    }
    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items). Args : mouseX, mouseY
     */
    @Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRendererObj.drawString(this.lowerChestInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
        this.fontRendererObj.drawString(this.upperChestInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Args : renderPartialTicks, mouseX, mouseY
     */
    @Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(CHEST_GUI_TEXTURE);
        int var4 = (this.width - this.xSize) / 2;
        int var5 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.drawTexturedModalRect(var4, var5 + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }
}
