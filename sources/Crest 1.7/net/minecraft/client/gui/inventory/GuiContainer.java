// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.gui.inventory;

import net.minecraft.entity.player.EntityPlayer;
import java.io.IOException;
import org.lwjgl.input.Keyboard;
import java.util.Iterator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.GuiScreen;

public abstract class GuiContainer extends GuiScreen
{
    protected static final ResourceLocation inventoryBackground;
    protected int xSize;
    protected int ySize;
    public Container inventorySlots;
    protected int guiLeft;
    protected int guiTop;
    private Slot theSlot;
    private Slot clickedSlot;
    private boolean isRightMouseClick;
    private ItemStack draggedStack;
    private int touchUpX;
    private int touchUpY;
    private Slot returningStackDestSlot;
    private long returningStackTime;
    private ItemStack returningStack;
    private Slot currentDragTargetSlot;
    private long dragItemDropDelay;
    protected final Set dragSplittingSlots;
    protected boolean dragSplitting;
    private int dragSplittingLimit;
    private int dragSplittingButton;
    private boolean ignoreMouseUp;
    private int dragSplittingRemnant;
    private long lastClickTime;
    private Slot lastClickSlot;
    private int lastClickButton;
    private boolean doubleClick;
    private ItemStack shiftClickedSlot;
    private static final String __OBFID = "CL_00000737";
    
    static {
        inventoryBackground = new ResourceLocation("textures/gui/container/inventory.png");
    }
    
    public GuiContainer(final Container p_i1072_1_) {
        this.xSize = 176;
        this.ySize = 166;
        this.dragSplittingSlots = Sets.newHashSet();
        this.inventorySlots = p_i1072_1_;
        this.ignoreMouseUp = true;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.mc.thePlayer.openContainer = this.inventorySlots;
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (GuiContainer.height - this.ySize) / 2;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        final int var4 = this.guiLeft;
        final int var5 = this.guiTop;
        this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        super.drawScreen(mouseX, mouseY, partialTicks);
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.pushMatrix();
        GlStateManager.translate(var4, var5, 0.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableRescaleNormal();
        this.theSlot = null;
        final short var6 = 240;
        final short var7 = 240;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0f, var7 / 1.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        for (int var8 = 0; var8 < this.inventorySlots.inventorySlots.size(); ++var8) {
            final Slot var9 = this.inventorySlots.inventorySlots.get(var8);
            this.drawSlot(var9);
            if (this.isMouseOverSlot(var9, mouseX, mouseY) && var9.canBeHovered()) {
                this.theSlot = var9;
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                final int var10 = var9.xDisplayPosition;
                final int var11 = var9.yDisplayPosition;
                GlStateManager.colorMask(true, true, true, false);
                this.drawGradientRect(var10, var11, var10 + 16, var11 + 16, -2130706433, -2130706433);
                GlStateManager.colorMask(true, true, true, true);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }
        }
        RenderHelper.disableStandardItemLighting();
        this.drawGuiContainerForegroundLayer(mouseX, mouseY);
        RenderHelper.enableGUIStandardItemLighting();
        final InventoryPlayer var12 = this.mc.thePlayer.inventory;
        ItemStack var13 = (this.draggedStack == null) ? var12.getItemStack() : this.draggedStack;
        if (var13 != null) {
            final byte var14 = 8;
            final int var11 = (this.draggedStack == null) ? 8 : 16;
            String var15 = null;
            if (this.draggedStack != null && this.isRightMouseClick) {
                var13 = var13.copy();
                var13.stackSize = MathHelper.ceiling_float_int(var13.stackSize / 2.0f);
            }
            else if (this.dragSplitting && this.dragSplittingSlots.size() > 1) {
                var13 = var13.copy();
                var13.stackSize = this.dragSplittingRemnant;
                if (var13.stackSize == 0) {
                    var15 = EnumChatFormatting.YELLOW + "0";
                }
            }
            this.drawItemStack(var13, mouseX - var4 - var14, mouseY - var5 - var11, var15);
        }
        if (this.returningStack != null) {
            float var16 = (Minecraft.getSystemTime() - this.returningStackTime) / 100.0f;
            if (var16 >= 1.0f) {
                var16 = 1.0f;
                this.returningStack = null;
            }
            final int var11 = this.returningStackDestSlot.xDisplayPosition - this.touchUpX;
            final int var17 = this.returningStackDestSlot.yDisplayPosition - this.touchUpY;
            final int var18 = this.touchUpX + (int)(var11 * var16);
            final int var19 = this.touchUpY + (int)(var17 * var16);
            this.drawItemStack(this.returningStack, var18, var19, null);
        }
        GlStateManager.popMatrix();
        if (var12.getItemStack() == null && this.theSlot != null && this.theSlot.getHasStack()) {
            final ItemStack var20 = this.theSlot.getStack();
            this.renderToolTip(var20, mouseX, mouseY);
        }
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
    }
    
    private void drawItemStack(final ItemStack stack, final int x, final int y, final String altText) {
        GlStateManager.translate(0.0f, 0.0f, 32.0f);
        this.zLevel = 200.0f;
        this.itemRender.zLevel = 200.0f;
        this.itemRender.func_180450_b(stack, x, y);
        this.itemRender.func_180453_a(this.fontRendererObj, stack, x, y - ((this.draggedStack == null) ? 0 : 8), altText);
        this.zLevel = 0.0f;
        this.itemRender.zLevel = 0.0f;
    }
    
    protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
    }
    
    protected abstract void drawGuiContainerBackgroundLayer(final float p0, final int p1, final int p2);
    
    private void drawSlot(final Slot slotIn) {
        final int var2 = slotIn.xDisplayPosition;
        final int var3 = slotIn.yDisplayPosition;
        ItemStack var4 = slotIn.getStack();
        boolean var5 = false;
        boolean var6 = slotIn == this.clickedSlot && this.draggedStack != null && !this.isRightMouseClick;
        final ItemStack var7 = this.mc.thePlayer.inventory.getItemStack();
        String var8 = null;
        if (slotIn == this.clickedSlot && this.draggedStack != null && this.isRightMouseClick && var4 != null) {
            final ItemStack copy;
            var4 = (copy = var4.copy());
            copy.stackSize /= 2;
        }
        else if (this.dragSplitting && this.dragSplittingSlots.contains(slotIn) && var7 != null) {
            if (this.dragSplittingSlots.size() == 1) {
                return;
            }
            if (Container.canAddItemToSlot(slotIn, var7, true) && this.inventorySlots.canDragIntoSlot(slotIn)) {
                var4 = var7.copy();
                var5 = true;
                Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, var4, (slotIn.getStack() == null) ? 0 : slotIn.getStack().stackSize);
                if (var4.stackSize > var4.getMaxStackSize()) {
                    var8 = new StringBuilder().append(EnumChatFormatting.YELLOW).append(var4.getMaxStackSize()).toString();
                    var4.stackSize = var4.getMaxStackSize();
                }
                if (var4.stackSize > slotIn.func_178170_b(var4)) {
                    var8 = new StringBuilder().append(EnumChatFormatting.YELLOW).append(slotIn.func_178170_b(var4)).toString();
                    var4.stackSize = slotIn.func_178170_b(var4);
                }
            }
            else {
                this.dragSplittingSlots.remove(slotIn);
                this.updateDragSplitting();
            }
        }
        this.zLevel = 100.0f;
        this.itemRender.zLevel = 100.0f;
        if (var4 == null) {
            final String var9 = slotIn.func_178171_c();
            if (var9 != null) {
                final TextureAtlasSprite var10 = this.mc.getTextureMapBlocks().getAtlasSprite(var9);
                GlStateManager.disableLighting();
                this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
                this.func_175175_a(var2, var3, var10, 16, 16);
                GlStateManager.enableLighting();
                var6 = true;
            }
        }
        if (!var6) {
            if (var5) {
                Gui.drawRect(var2, var3, var2 + 16, var3 + 16, -2130706433);
            }
            GlStateManager.enableDepth();
            this.itemRender.func_180450_b(var4, var2, var3);
            this.itemRender.func_180453_a(this.fontRendererObj, var4, var2, var3, var8);
        }
        this.itemRender.zLevel = 0.0f;
        this.zLevel = 0.0f;
    }
    
    private void updateDragSplitting() {
        final ItemStack var1 = this.mc.thePlayer.inventory.getItemStack();
        if (var1 != null && this.dragSplitting) {
            this.dragSplittingRemnant = var1.stackSize;
            for (final Slot var3 : this.dragSplittingSlots) {
                final ItemStack var4 = var1.copy();
                final int var5 = (var3.getStack() == null) ? 0 : var3.getStack().stackSize;
                Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, var4, var5);
                if (var4.stackSize > var4.getMaxStackSize()) {
                    var4.stackSize = var4.getMaxStackSize();
                }
                if (var4.stackSize > var3.func_178170_b(var4)) {
                    var4.stackSize = var3.func_178170_b(var4);
                }
                this.dragSplittingRemnant -= var4.stackSize - var5;
            }
        }
    }
    
    private Slot getSlotAtPosition(final int x, final int y) {
        for (int var3 = 0; var3 < this.inventorySlots.inventorySlots.size(); ++var3) {
            final Slot var4 = this.inventorySlots.inventorySlots.get(var3);
            if (this.isMouseOverSlot(var4, x, y)) {
                return var4;
            }
        }
        return null;
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        final boolean var4 = mouseButton == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100;
        final Slot var5 = this.getSlotAtPosition(mouseX, mouseY);
        final long var6 = Minecraft.getSystemTime();
        this.doubleClick = (this.lastClickSlot == var5 && var6 - this.lastClickTime < 250L && this.lastClickButton == mouseButton);
        this.ignoreMouseUp = false;
        if (mouseButton == 0 || mouseButton == 1 || var4) {
            final int var7 = this.guiLeft;
            final int var8 = this.guiTop;
            final boolean var9 = mouseX < var7 || mouseY < var8 || mouseX >= var7 + this.xSize || mouseY >= var8 + this.ySize;
            int var10 = -1;
            if (var5 != null) {
                var10 = var5.slotNumber;
            }
            if (var9) {
                var10 = -999;
            }
            if (this.mc.gameSettings.touchscreen && var9 && this.mc.thePlayer.inventory.getItemStack() == null) {
                this.mc.displayGuiScreen(null);
                return;
            }
            if (var10 != -1) {
                if (this.mc.gameSettings.touchscreen) {
                    if (var5 != null && var5.getHasStack()) {
                        this.clickedSlot = var5;
                        this.draggedStack = null;
                        this.isRightMouseClick = (mouseButton == 1);
                    }
                    else {
                        this.clickedSlot = null;
                    }
                }
                else if (!this.dragSplitting) {
                    if (this.mc.thePlayer.inventory.getItemStack() == null) {
                        if (mouseButton == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
                            this.handleMouseClick(var5, var10, mouseButton, 3);
                        }
                        else {
                            final boolean var11 = var10 != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
                            byte var12 = 0;
                            if (var11) {
                                this.shiftClickedSlot = ((var5 != null && var5.getHasStack()) ? var5.getStack() : null);
                                var12 = 1;
                            }
                            else if (var10 == -999) {
                                var12 = 4;
                            }
                            this.handleMouseClick(var5, var10, mouseButton, var12);
                        }
                        this.ignoreMouseUp = true;
                    }
                    else {
                        this.dragSplitting = true;
                        this.dragSplittingButton = mouseButton;
                        this.dragSplittingSlots.clear();
                        if (mouseButton == 0) {
                            this.dragSplittingLimit = 0;
                        }
                        else if (mouseButton == 1) {
                            this.dragSplittingLimit = 1;
                        }
                        else if (mouseButton == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
                            this.dragSplittingLimit = 2;
                        }
                    }
                }
            }
        }
        this.lastClickSlot = var5;
        this.lastClickTime = var6;
        this.lastClickButton = mouseButton;
    }
    
    @Override
    protected void mouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        final Slot var6 = this.getSlotAtPosition(mouseX, mouseY);
        final ItemStack var7 = this.mc.thePlayer.inventory.getItemStack();
        if (this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
            if (clickedMouseButton == 0 || clickedMouseButton == 1) {
                if (this.draggedStack == null) {
                    if (var6 != this.clickedSlot) {
                        this.draggedStack = this.clickedSlot.getStack().copy();
                    }
                }
                else if (this.draggedStack.stackSize > 1 && var6 != null && Container.canAddItemToSlot(var6, this.draggedStack, false)) {
                    final long var8 = Minecraft.getSystemTime();
                    if (this.currentDragTargetSlot == var6) {
                        if (var8 - this.dragItemDropDelay > 500L) {
                            this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, 0);
                            this.handleMouseClick(var6, var6.slotNumber, 1, 0);
                            this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, 0);
                            this.dragItemDropDelay = var8 + 750L;
                            final ItemStack draggedStack = this.draggedStack;
                            --draggedStack.stackSize;
                        }
                    }
                    else {
                        this.currentDragTargetSlot = var6;
                        this.dragItemDropDelay = var8;
                    }
                }
            }
        }
        else if (this.dragSplitting && var6 != null && var7 != null && var7.stackSize > this.dragSplittingSlots.size() && Container.canAddItemToSlot(var6, var7, true) && var6.isItemValid(var7) && this.inventorySlots.canDragIntoSlot(var6)) {
            this.dragSplittingSlots.add(var6);
            this.updateDragSplitting();
        }
    }
    
    @Override
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        final Slot var4 = this.getSlotAtPosition(mouseX, mouseY);
        final int var5 = this.guiLeft;
        final int var6 = this.guiTop;
        final boolean var7 = mouseX < var5 || mouseY < var6 || mouseX >= var5 + this.xSize || mouseY >= var6 + this.ySize;
        int var8 = -1;
        if (var4 != null) {
            var8 = var4.slotNumber;
        }
        if (var7) {
            var8 = -999;
        }
        if (this.doubleClick && var4 != null && state == 0 && this.inventorySlots.func_94530_a(null, var4)) {
            if (isShiftKeyDown()) {
                if (var4 != null && var4.inventory != null && this.shiftClickedSlot != null) {
                    for (final Slot var10 : this.inventorySlots.inventorySlots) {
                        if (var10 != null && var10.canTakeStack(this.mc.thePlayer) && var10.getHasStack() && var10.inventory == var4.inventory && Container.canAddItemToSlot(var10, this.shiftClickedSlot, true)) {
                            this.handleMouseClick(var10, var10.slotNumber, state, 1);
                        }
                    }
                }
            }
            else {
                this.handleMouseClick(var4, var8, state, 6);
            }
            this.doubleClick = false;
            this.lastClickTime = 0L;
        }
        else {
            if (this.dragSplitting && this.dragSplittingButton != state) {
                this.dragSplitting = false;
                this.dragSplittingSlots.clear();
                this.ignoreMouseUp = true;
                return;
            }
            if (this.ignoreMouseUp) {
                this.ignoreMouseUp = false;
                return;
            }
            if (this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
                if (state == 0 || state == 1) {
                    if (this.draggedStack == null && var4 != this.clickedSlot) {
                        this.draggedStack = this.clickedSlot.getStack();
                    }
                    final boolean var11 = Container.canAddItemToSlot(var4, this.draggedStack, false);
                    if (var8 != -1 && this.draggedStack != null && var11) {
                        this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, state, 0);
                        this.handleMouseClick(var4, var8, 0, 0);
                        if (this.mc.thePlayer.inventory.getItemStack() != null) {
                            this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, state, 0);
                            this.touchUpX = mouseX - var5;
                            this.touchUpY = mouseY - var6;
                            this.returningStackDestSlot = this.clickedSlot;
                            this.returningStack = this.draggedStack;
                            this.returningStackTime = Minecraft.getSystemTime();
                        }
                        else {
                            this.returningStack = null;
                        }
                    }
                    else if (this.draggedStack != null) {
                        this.touchUpX = mouseX - var5;
                        this.touchUpY = mouseY - var6;
                        this.returningStackDestSlot = this.clickedSlot;
                        this.returningStack = this.draggedStack;
                        this.returningStackTime = Minecraft.getSystemTime();
                    }
                    this.draggedStack = null;
                    this.clickedSlot = null;
                }
            }
            else if (this.dragSplitting && !this.dragSplittingSlots.isEmpty()) {
                this.handleMouseClick(null, -999, Container.func_94534_d(0, this.dragSplittingLimit), 5);
                for (final Slot var10 : this.dragSplittingSlots) {
                    this.handleMouseClick(var10, var10.slotNumber, Container.func_94534_d(1, this.dragSplittingLimit), 5);
                }
                this.handleMouseClick(null, -999, Container.func_94534_d(2, this.dragSplittingLimit), 5);
            }
            else if (this.mc.thePlayer.inventory.getItemStack() != null) {
                if (state == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
                    this.handleMouseClick(var4, var8, state, 3);
                }
                else {
                    final boolean var11 = var8 != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
                    if (var11) {
                        this.shiftClickedSlot = ((var4 != null && var4.getHasStack()) ? var4.getStack() : null);
                    }
                    this.handleMouseClick(var4, var8, state, var11 ? 1 : 0);
                }
            }
        }
        if (this.mc.thePlayer.inventory.getItemStack() == null) {
            this.lastClickTime = 0L;
        }
        this.dragSplitting = false;
    }
    
    private boolean isMouseOverSlot(final Slot slotIn, final int mouseX, final int mouseY) {
        return this.isPointInRegion(slotIn.xDisplayPosition, slotIn.yDisplayPosition, 16, 16, mouseX, mouseY);
    }
    
    protected boolean isPointInRegion(final int left, final int top, final int right, final int bottom, int pointX, int pointY) {
        final int var7 = this.guiLeft;
        final int var8 = this.guiTop;
        pointX -= var7;
        pointY -= var8;
        return pointX >= left - 1 && pointX < left + right + 1 && pointY >= top - 1 && pointY < top + bottom + 1;
    }
    
    protected void handleMouseClick(final Slot slotIn, int slotId, final int clickedButton, final int clickType) {
        if (slotIn != null) {
            slotId = slotIn.slotNumber;
        }
        this.mc.playerController.windowClick(this.inventorySlots.windowId, slotId, clickedButton, clickType, this.mc.thePlayer);
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
            this.mc.thePlayer.closeScreen();
        }
        this.checkHotbarKeys(keyCode);
        if (this.theSlot != null && this.theSlot.getHasStack()) {
            if (keyCode == this.mc.gameSettings.keyBindPickBlock.getKeyCode()) {
                this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, 0, 3);
            }
            else if (keyCode == this.mc.gameSettings.keyBindDrop.getKeyCode()) {
                this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, GuiScreen.isCtrlKeyDown() ? 1 : 0, 4);
            }
        }
    }
    
    protected boolean checkHotbarKeys(final int keyCode) {
        if (this.mc.thePlayer.inventory.getItemStack() == null && this.theSlot != null) {
            for (int var2 = 0; var2 < 9; ++var2) {
                if (keyCode == this.mc.gameSettings.keyBindsHotbar[var2].getKeyCode()) {
                    this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, var2, 2);
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public void onGuiClosed() {
        if (this.mc.thePlayer != null) {
            this.inventorySlots.onContainerClosed(this.mc.thePlayer);
        }
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        if (!this.mc.thePlayer.isEntityAlive() || this.mc.thePlayer.isDead) {
            this.mc.thePlayer.closeScreen();
        }
    }
}
