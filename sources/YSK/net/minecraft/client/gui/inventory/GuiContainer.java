package net.minecraft.client.gui.inventory;

import net.minecraft.inventory.*;
import net.minecraft.item.*;
import java.io.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.texture.*;
import org.lwjgl.input.*;
import java.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.gui.*;
import com.google.common.collect.*;

public abstract class GuiContainer extends GuiScreen
{
    private int lastClickButton;
    public Container inventorySlots;
    private int dragSplittingButton;
    protected final Set<Slot> dragSplittingSlots;
    protected int xSize;
    private Slot currentDragTargetSlot;
    private boolean doubleClick;
    private ItemStack shiftClickedSlot;
    private Slot lastClickSlot;
    private int touchUpY;
    private ItemStack returningStack;
    private Slot clickedSlot;
    private boolean ignoreMouseUp;
    private boolean isRightMouseClick;
    protected int guiTop;
    protected int guiLeft;
    protected int ySize;
    private Slot returningStackDestSlot;
    private long returningStackTime;
    private static final String[] I;
    private long lastClickTime;
    private ItemStack draggedStack;
    private int touchUpX;
    private int dragSplittingLimit;
    private Slot theSlot;
    private int dragSplittingRemnant;
    protected boolean dragSplitting;
    protected static final ResourceLocation inventoryBackground;
    private long dragItemDropDelay;
    
    private boolean isMouseOverSlot(final Slot slot, final int n, final int n2) {
        return this.isPointInRegion(slot.xDisplayPosition, slot.yDisplayPosition, 0x19 ^ 0x9, 0x5D ^ 0x4D, n, n2);
    }
    
    protected void handleMouseClick(final Slot slot, int slotNumber, final int n, final int n2) {
        if (slot != null) {
            slotNumber = slot.slotNumber;
        }
        this.mc.playerController.windowClick(this.inventorySlots.windowId, slotNumber, n, n2, this.mc.thePlayer);
    }
    
    private Slot getSlotAtPosition(final int n, final int n2) {
        int i = "".length();
        "".length();
        if (3 <= 1) {
            throw null;
        }
        while (i < this.inventorySlots.inventorySlots.size()) {
            final Slot slot = this.inventorySlots.inventorySlots.get(i);
            if (this.isMouseOverSlot(slot, n, n2)) {
                return slot;
            }
            ++i;
        }
        return null;
    }
    
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == " ".length() || n == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
            this.mc.thePlayer.closeScreen();
        }
        this.checkHotbarKeys(n);
        if (this.theSlot != null && this.theSlot.getHasStack()) {
            if (n == this.mc.gameSettings.keyBindPickBlock.getKeyCode()) {
                this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, "".length(), "   ".length());
                "".length();
                if (3 <= 2) {
                    throw null;
                }
            }
            else if (n == this.mc.gameSettings.keyBindDrop.getKeyCode()) {
                final Slot theSlot = this.theSlot;
                final int slotNumber = this.theSlot.slotNumber;
                int n2;
                if (isCtrlKeyDown()) {
                    n2 = " ".length();
                    "".length();
                    if (3 == 4) {
                        throw null;
                    }
                }
                else {
                    n2 = "".length();
                }
                this.handleMouseClick(theSlot, slotNumber, n2, 0x73 ^ 0x77);
            }
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        final int guiLeft = this.guiLeft;
        final int guiTop = this.guiTop;
        this.drawGuiContainerBackgroundLayer(n3, n, n2);
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        super.drawScreen(n, n2, n3);
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.pushMatrix();
        GlStateManager.translate(guiLeft, guiTop, 0.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableRescaleNormal();
        this.theSlot = null;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (14 + 220 - 23 + 29) / 1.0f, (210 + 58 - 234 + 206) / 1.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        int i = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (i < this.inventorySlots.inventorySlots.size()) {
            final Slot theSlot = this.inventorySlots.inventorySlots.get(i);
            this.drawSlot(theSlot);
            if (this.isMouseOverSlot(theSlot, n, n2) && theSlot.canBeHovered()) {
                this.theSlot = theSlot;
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                final int xDisplayPosition = theSlot.xDisplayPosition;
                final int yDisplayPosition = theSlot.yDisplayPosition;
                GlStateManager.colorMask(" ".length() != 0, " ".length() != 0, " ".length() != 0, "".length() != 0);
                this.drawGradientRect(xDisplayPosition, yDisplayPosition, xDisplayPosition + (0x70 ^ 0x60), yDisplayPosition + (0xB1 ^ 0xA1), -(72856915 + 1449311946 + 84458106 + 524079466), -(1981122023 + 1796108143 - 1840830139 + 194306406));
                GlStateManager.colorMask(" ".length() != 0, " ".length() != 0, " ".length() != 0, " ".length() != 0);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }
            ++i;
        }
        RenderHelper.disableStandardItemLighting();
        this.drawGuiContainerForegroundLayer(n, n2);
        RenderHelper.enableGUIStandardItemLighting();
        final InventoryPlayer inventory = this.mc.thePlayer.inventory;
        ItemStack itemStack;
        if (this.draggedStack == null) {
            itemStack = inventory.getItemStack();
            "".length();
            if (-1 < -1) {
                throw null;
            }
        }
        else {
            itemStack = this.draggedStack;
        }
        ItemStack itemStack2 = itemStack;
        if (itemStack2 != null) {
            final int n4 = 0x14 ^ 0x1C;
            int n5;
            if (this.draggedStack == null) {
                n5 = (0x70 ^ 0x78);
                "".length();
                if (4 < 2) {
                    throw null;
                }
            }
            else {
                n5 = (0xD6 ^ 0xC6);
            }
            final int n6 = n5;
            String string = null;
            if (this.draggedStack != null && this.isRightMouseClick) {
                itemStack2 = itemStack2.copy();
                itemStack2.stackSize = MathHelper.ceiling_float_int(itemStack2.stackSize / 2.0f);
                "".length();
                if (1 == 2) {
                    throw null;
                }
            }
            else if (this.dragSplitting && this.dragSplittingSlots.size() > " ".length()) {
                itemStack2 = itemStack2.copy();
                itemStack2.stackSize = this.dragSplittingRemnant;
                if (itemStack2.stackSize == 0) {
                    string = EnumChatFormatting.YELLOW + GuiContainer.I[" ".length()];
                }
            }
            this.drawItemStack(itemStack2, n - guiLeft - n4, n2 - guiTop - n6, string);
        }
        if (this.returningStack != null) {
            float n7 = (Minecraft.getSystemTime() - this.returningStackTime) / 100.0f;
            if (n7 >= 1.0f) {
                n7 = 1.0f;
                this.returningStack = null;
            }
            this.drawItemStack(this.returningStack, this.touchUpX + (int)((this.returningStackDestSlot.xDisplayPosition - this.touchUpX) * n7), this.touchUpY + (int)((this.returningStackDestSlot.yDisplayPosition - this.touchUpY) * n7), null);
        }
        GlStateManager.popMatrix();
        if (inventory.getItemStack() == null && this.theSlot != null && this.theSlot.getHasStack()) {
            this.renderToolTip(this.theSlot.getStack(), n, n2);
        }
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return "".length() != 0;
    }
    
    private void drawSlot(final Slot slot) {
        final int xDisplayPosition = slot.xDisplayPosition;
        final int yDisplayPosition = slot.yDisplayPosition;
        ItemStack itemStack = slot.getStack();
        int n = "".length();
        int n2;
        if (slot == this.clickedSlot && this.draggedStack != null && !this.isRightMouseClick) {
            n2 = " ".length();
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        int length = n2;
        final ItemStack itemStack2 = this.mc.thePlayer.inventory.getItemStack();
        String s = null;
        if (slot == this.clickedSlot && this.draggedStack != null && this.isRightMouseClick && itemStack != null) {
            final ItemStack copy;
            itemStack = (copy = itemStack.copy());
            copy.stackSize /= "  ".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (this.dragSplitting && this.dragSplittingSlots.contains(slot) && itemStack2 != null) {
            if (this.dragSplittingSlots.size() == " ".length()) {
                return;
            }
            if (Container.canAddItemToSlot(slot, itemStack2, " ".length() != 0) && this.inventorySlots.canDragIntoSlot(slot)) {
                itemStack = itemStack2.copy();
                n = " ".length();
                final Set<Slot> dragSplittingSlots = this.dragSplittingSlots;
                final int dragSplittingLimit = this.dragSplittingLimit;
                final ItemStack itemStack3 = itemStack;
                int n3;
                if (slot.getStack() == null) {
                    n3 = "".length();
                    "".length();
                    if (4 < 2) {
                        throw null;
                    }
                }
                else {
                    n3 = slot.getStack().stackSize;
                }
                Container.computeStackSize(dragSplittingSlots, dragSplittingLimit, itemStack3, n3);
                if (itemStack.stackSize > itemStack.getMaxStackSize()) {
                    s = new StringBuilder().append(EnumChatFormatting.YELLOW).append(itemStack.getMaxStackSize()).toString();
                    itemStack.stackSize = itemStack.getMaxStackSize();
                }
                if (itemStack.stackSize > slot.getItemStackLimit(itemStack)) {
                    s = new StringBuilder().append(EnumChatFormatting.YELLOW).append(slot.getItemStackLimit(itemStack)).toString();
                    itemStack.stackSize = slot.getItemStackLimit(itemStack);
                    "".length();
                    if (1 <= -1) {
                        throw null;
                    }
                }
            }
            else {
                this.dragSplittingSlots.remove(slot);
                this.updateDragSplitting();
            }
        }
        this.zLevel = 100.0f;
        this.itemRender.zLevel = 100.0f;
        if (itemStack == null) {
            final String slotTexture = slot.getSlotTexture();
            if (slotTexture != null) {
                final TextureAtlasSprite atlasSprite = this.mc.getTextureMapBlocks().getAtlasSprite(slotTexture);
                GlStateManager.disableLighting();
                this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
                this.drawTexturedModalRect(xDisplayPosition, yDisplayPosition, atlasSprite, 0x81 ^ 0x91, 0xAE ^ 0xBE);
                GlStateManager.enableLighting();
                length = " ".length();
            }
        }
        if (length == 0) {
            if (n != 0) {
                Gui.drawRect(xDisplayPosition, yDisplayPosition, xDisplayPosition + (0xAE ^ 0xBE), yDisplayPosition + (0x33 ^ 0x23), -(360224773 + 2049304569 - 1800733860 + 1521910951));
            }
            GlStateManager.enableDepth();
            this.itemRender.renderItemAndEffectIntoGUI(itemStack, xDisplayPosition, yDisplayPosition);
            this.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, itemStack, xDisplayPosition, yDisplayPosition, s);
        }
        this.itemRender.zLevel = 0.0f;
        this.zLevel = 0.0f;
    }
    
    protected boolean isPointInRegion(final int n, final int n2, final int n3, final int n4, int n5, int n6) {
        final int guiLeft = this.guiLeft;
        final int guiTop = this.guiTop;
        n5 -= guiLeft;
        n6 -= guiTop;
        if (n5 >= n - " ".length() && n5 < n + n3 + " ".length() && n6 >= n2 - " ".length() && n6 < n2 + n4 + " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    protected void mouseReleased(final int n, final int n2, final int n3) {
        final Slot slotAtPosition = this.getSlotAtPosition(n, n2);
        final int guiLeft = this.guiLeft;
        final int guiTop = this.guiTop;
        int n4;
        if (n >= guiLeft && n2 >= guiTop && n < guiLeft + this.xSize && n2 < guiTop + this.ySize) {
            n4 = "".length();
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        else {
            n4 = " ".length();
        }
        final int n5 = n4;
        int slotNumber = -" ".length();
        if (slotAtPosition != null) {
            slotNumber = slotAtPosition.slotNumber;
        }
        if (n5 != 0) {
            slotNumber = -(429 + 476 - 598 + 692);
        }
        if (this.doubleClick && slotAtPosition != null && n3 == 0 && this.inventorySlots.canMergeSlot(null, slotAtPosition)) {
            if (isShiftKeyDown()) {
                if (slotAtPosition != null && slotAtPosition.inventory != null && this.shiftClickedSlot != null) {
                    final Iterator<Slot> iterator = this.inventorySlots.inventorySlots.iterator();
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                    while (iterator.hasNext()) {
                        final Slot slot = iterator.next();
                        if (slot != null && slot.canTakeStack(this.mc.thePlayer) && slot.getHasStack() && slot.inventory == slotAtPosition.inventory && Container.canAddItemToSlot(slot, this.shiftClickedSlot, " ".length() != 0)) {
                            this.handleMouseClick(slot, slot.slotNumber, n3, " ".length());
                        }
                    }
                    "".length();
                    if (3 < 3) {
                        throw null;
                    }
                }
            }
            else {
                this.handleMouseClick(slotAtPosition, slotNumber, n3, 0x18 ^ 0x1E);
            }
            this.doubleClick = ("".length() != 0);
            this.lastClickTime = 0L;
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else {
            if (this.dragSplitting && this.dragSplittingButton != n3) {
                this.dragSplitting = ("".length() != 0);
                this.dragSplittingSlots.clear();
                this.ignoreMouseUp = (" ".length() != 0);
                return;
            }
            if (this.ignoreMouseUp) {
                this.ignoreMouseUp = ("".length() != 0);
                return;
            }
            if (this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
                if (n3 == 0 || n3 == " ".length()) {
                    if (this.draggedStack == null && slotAtPosition != this.clickedSlot) {
                        this.draggedStack = this.clickedSlot.getStack();
                    }
                    final boolean canAddItemToSlot = Container.canAddItemToSlot(slotAtPosition, this.draggedStack, "".length() != 0);
                    if (slotNumber != -" ".length() && this.draggedStack != null && canAddItemToSlot) {
                        this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, n3, "".length());
                        this.handleMouseClick(slotAtPosition, slotNumber, "".length(), "".length());
                        if (this.mc.thePlayer.inventory.getItemStack() != null) {
                            this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, n3, "".length());
                            this.touchUpX = n - guiLeft;
                            this.touchUpY = n2 - guiTop;
                            this.returningStackDestSlot = this.clickedSlot;
                            this.returningStack = this.draggedStack;
                            this.returningStackTime = Minecraft.getSystemTime();
                            "".length();
                            if (4 < 3) {
                                throw null;
                            }
                        }
                        else {
                            this.returningStack = null;
                            "".length();
                            if (3 <= -1) {
                                throw null;
                            }
                        }
                    }
                    else if (this.draggedStack != null) {
                        this.touchUpX = n - guiLeft;
                        this.touchUpY = n2 - guiTop;
                        this.returningStackDestSlot = this.clickedSlot;
                        this.returningStack = this.draggedStack;
                        this.returningStackTime = Minecraft.getSystemTime();
                    }
                    this.draggedStack = null;
                    this.clickedSlot = null;
                    "".length();
                    if (2 < 1) {
                        throw null;
                    }
                }
            }
            else if (this.dragSplitting && !this.dragSplittingSlots.isEmpty()) {
                this.handleMouseClick(null, -(864 + 406 - 356 + 85), Container.func_94534_d("".length(), this.dragSplittingLimit), 0x17 ^ 0x12);
                final Iterator<Slot> iterator2 = this.dragSplittingSlots.iterator();
                "".length();
                if (0 == 4) {
                    throw null;
                }
                while (iterator2.hasNext()) {
                    final Slot slot2 = iterator2.next();
                    this.handleMouseClick(slot2, slot2.slotNumber, Container.func_94534_d(" ".length(), this.dragSplittingLimit), 0x8C ^ 0x89);
                }
                this.handleMouseClick(null, -(309 + 787 - 645 + 548), Container.func_94534_d("  ".length(), this.dragSplittingLimit), 0x7A ^ 0x7F);
                "".length();
                if (2 <= -1) {
                    throw null;
                }
            }
            else if (this.mc.thePlayer.inventory.getItemStack() != null) {
                if (n3 == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + (0x76 ^ 0x12)) {
                    this.handleMouseClick(slotAtPosition, slotNumber, n3, "   ".length());
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
                else {
                    int n6;
                    if (slotNumber != -(711 + 383 - 652 + 557) && (Keyboard.isKeyDown(0xB4 ^ 0x9E) || Keyboard.isKeyDown(0x6E ^ 0x58))) {
                        n6 = " ".length();
                        "".length();
                        if (3 < 2) {
                            throw null;
                        }
                    }
                    else {
                        n6 = "".length();
                    }
                    final int n7 = n6;
                    if (n7 != 0) {
                        ItemStack stack;
                        if (slotAtPosition != null && slotAtPosition.getHasStack()) {
                            stack = slotAtPosition.getStack();
                            "".length();
                            if (4 < -1) {
                                throw null;
                            }
                        }
                        else {
                            stack = null;
                        }
                        this.shiftClickedSlot = stack;
                    }
                    final Slot slot3 = slotAtPosition;
                    final int n8 = slotNumber;
                    int n9;
                    if (n7 != 0) {
                        n9 = " ".length();
                        "".length();
                        if (1 >= 3) {
                            throw null;
                        }
                    }
                    else {
                        n9 = "".length();
                    }
                    this.handleMouseClick(slot3, n8, n3, n9);
                }
            }
        }
        if (this.mc.thePlayer.inventory.getItemStack() == null) {
            this.lastClickTime = 0L;
        }
        this.dragSplitting = ("".length() != 0);
    }
    
    private void updateDragSplitting() {
        final ItemStack itemStack = this.mc.thePlayer.inventory.getItemStack();
        if (itemStack != null && this.dragSplitting) {
            this.dragSplittingRemnant = itemStack.stackSize;
            final Iterator<Slot> iterator = this.dragSplittingSlots.iterator();
            "".length();
            if (2 < 0) {
                throw null;
            }
            while (iterator.hasNext()) {
                final Slot slot = iterator.next();
                final ItemStack copy = itemStack.copy();
                int n;
                if (slot.getStack() == null) {
                    n = "".length();
                    "".length();
                    if (3 < 2) {
                        throw null;
                    }
                }
                else {
                    n = slot.getStack().stackSize;
                }
                final int n2 = n;
                Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, copy, n2);
                if (copy.stackSize > copy.getMaxStackSize()) {
                    copy.stackSize = copy.getMaxStackSize();
                }
                if (copy.stackSize > slot.getItemStackLimit(copy)) {
                    copy.stackSize = slot.getItemStackLimit(copy);
                }
                this.dragSplittingRemnant -= copy.stackSize - n2;
            }
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        int n4;
        if (n3 == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + (0xEE ^ 0x8A)) {
            n4 = " ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            n4 = "".length();
        }
        final int n5 = n4;
        final Slot slotAtPosition = this.getSlotAtPosition(n, n2);
        final long systemTime = Minecraft.getSystemTime();
        int doubleClick;
        if (this.lastClickSlot == slotAtPosition && systemTime - this.lastClickTime < 250L && this.lastClickButton == n3) {
            doubleClick = " ".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            doubleClick = "".length();
        }
        this.doubleClick = (doubleClick != 0);
        this.ignoreMouseUp = ("".length() != 0);
        if (n3 == 0 || n3 == " ".length() || n5 != 0) {
            final int guiLeft = this.guiLeft;
            final int guiTop = this.guiTop;
            int n6;
            if (n >= guiLeft && n2 >= guiTop && n < guiLeft + this.xSize && n2 < guiTop + this.ySize) {
                n6 = "".length();
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else {
                n6 = " ".length();
            }
            final int n7 = n6;
            int slotNumber = -" ".length();
            if (slotAtPosition != null) {
                slotNumber = slotAtPosition.slotNumber;
            }
            if (n7 != 0) {
                slotNumber = -(339 + 345 - 671 + 986);
            }
            if (this.mc.gameSettings.touchscreen && n7 != 0 && this.mc.thePlayer.inventory.getItemStack() == null) {
                this.mc.displayGuiScreen(null);
                return;
            }
            if (slotNumber != -" ".length()) {
                if (this.mc.gameSettings.touchscreen) {
                    if (slotAtPosition != null && slotAtPosition.getHasStack()) {
                        this.clickedSlot = slotAtPosition;
                        this.draggedStack = null;
                        int isRightMouseClick;
                        if (n3 == " ".length()) {
                            isRightMouseClick = " ".length();
                            "".length();
                            if (1 == -1) {
                                throw null;
                            }
                        }
                        else {
                            isRightMouseClick = "".length();
                        }
                        this.isRightMouseClick = (isRightMouseClick != 0);
                        "".length();
                        if (-1 >= 4) {
                            throw null;
                        }
                    }
                    else {
                        this.clickedSlot = null;
                        "".length();
                        if (1 <= 0) {
                            throw null;
                        }
                    }
                }
                else if (!this.dragSplitting) {
                    if (this.mc.thePlayer.inventory.getItemStack() == null) {
                        if (n3 == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + (0xED ^ 0x89)) {
                            this.handleMouseClick(slotAtPosition, slotNumber, n3, "   ".length());
                            "".length();
                            if (3 != 3) {
                                throw null;
                            }
                        }
                        else {
                            int n8;
                            if (slotNumber != -(332 + 670 - 323 + 320) && (Keyboard.isKeyDown(0xE9 ^ 0xC3) || Keyboard.isKeyDown(0x18 ^ 0x2E))) {
                                n8 = " ".length();
                                "".length();
                                if (-1 == 3) {
                                    throw null;
                                }
                            }
                            else {
                                n8 = "".length();
                            }
                            final int n9 = n8;
                            int n10 = "".length();
                            if (n9 != 0) {
                                ItemStack stack;
                                if (slotAtPosition != null && slotAtPosition.getHasStack()) {
                                    stack = slotAtPosition.getStack();
                                    "".length();
                                    if (-1 >= 0) {
                                        throw null;
                                    }
                                }
                                else {
                                    stack = null;
                                }
                                this.shiftClickedSlot = stack;
                                n10 = " ".length();
                                "".length();
                                if (1 == 2) {
                                    throw null;
                                }
                            }
                            else if (slotNumber == -(186 + 832 - 301 + 282)) {
                                n10 = (0x6E ^ 0x6A);
                            }
                            this.handleMouseClick(slotAtPosition, slotNumber, n3, n10);
                        }
                        this.ignoreMouseUp = (" ".length() != 0);
                        "".length();
                        if (4 != 4) {
                            throw null;
                        }
                    }
                    else {
                        this.dragSplitting = (" ".length() != 0);
                        this.dragSplittingButton = n3;
                        this.dragSplittingSlots.clear();
                        if (n3 == 0) {
                            this.dragSplittingLimit = "".length();
                            "".length();
                            if (-1 != -1) {
                                throw null;
                            }
                        }
                        else if (n3 == " ".length()) {
                            this.dragSplittingLimit = " ".length();
                            "".length();
                            if (3 != 3) {
                                throw null;
                            }
                        }
                        else if (n3 == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + (0x24 ^ 0x40)) {
                            this.dragSplittingLimit = "  ".length();
                        }
                    }
                }
            }
        }
        this.lastClickSlot = slotAtPosition;
        this.lastClickTime = systemTime;
        this.lastClickButton = n3;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        if (!this.mc.thePlayer.isEntityAlive() || this.mc.thePlayer.isDead) {
            this.mc.thePlayer.closeScreen();
        }
    }
    
    @Override
    protected void mouseClickMove(final int n, final int n2, final int n3, final long n4) {
        final Slot slotAtPosition = this.getSlotAtPosition(n, n2);
        final ItemStack itemStack = this.mc.thePlayer.inventory.getItemStack();
        if (this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
            if (n3 == 0 || n3 == " ".length()) {
                if (this.draggedStack == null) {
                    if (slotAtPosition != this.clickedSlot && this.clickedSlot.getStack() != null) {
                        this.draggedStack = this.clickedSlot.getStack().copy();
                        "".length();
                        if (3 < 2) {
                            throw null;
                        }
                    }
                }
                else if (this.draggedStack.stackSize > " ".length() && slotAtPosition != null && Container.canAddItemToSlot(slotAtPosition, this.draggedStack, "".length() != 0)) {
                    final long systemTime = Minecraft.getSystemTime();
                    if (this.currentDragTargetSlot == slotAtPosition) {
                        if (systemTime - this.dragItemDropDelay > 500L) {
                            this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, "".length(), "".length());
                            this.handleMouseClick(slotAtPosition, slotAtPosition.slotNumber, " ".length(), "".length());
                            this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, "".length(), "".length());
                            this.dragItemDropDelay = systemTime + 750L;
                            final ItemStack draggedStack = this.draggedStack;
                            draggedStack.stackSize -= " ".length();
                            "".length();
                            if (3 == 0) {
                                throw null;
                            }
                        }
                    }
                    else {
                        this.currentDragTargetSlot = slotAtPosition;
                        this.dragItemDropDelay = systemTime;
                        "".length();
                        if (2 < 0) {
                            throw null;
                        }
                    }
                }
            }
        }
        else if (this.dragSplitting && slotAtPosition != null && itemStack != null && itemStack.stackSize > this.dragSplittingSlots.size() && Container.canAddItemToSlot(slotAtPosition, itemStack, " ".length() != 0) && slotAtPosition.isItemValid(itemStack) && this.inventorySlots.canDragIntoSlot(slotAtPosition)) {
            this.dragSplittingSlots.add(slotAtPosition);
            this.updateDragSplitting();
        }
    }
    
    protected abstract void drawGuiContainerBackgroundLayer(final float p0, final int p1, final int p2);
    
    private void drawItemStack(final ItemStack itemStack, final int n, final int n2, final String s) {
        GlStateManager.translate(0.0f, 0.0f, 32.0f);
        this.zLevel = 200.0f;
        this.itemRender.zLevel = 200.0f;
        this.itemRender.renderItemAndEffectIntoGUI(itemStack, n, n2);
        final RenderItem itemRender = this.itemRender;
        final FontRenderer fontRendererObj = this.fontRendererObj;
        int length;
        if (this.draggedStack == null) {
            length = "".length();
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            length = (0x31 ^ 0x39);
        }
        itemRender.renderItemOverlayIntoGUI(fontRendererObj, itemStack, n, n2 - length, s);
        this.zLevel = 0.0f;
        this.itemRender.zLevel = 0.0f;
    }
    
    @Override
    public void onGuiClosed() {
        if (this.mc.thePlayer != null) {
            this.inventorySlots.onContainerClosed(this.mc.thePlayer);
        }
    }
    
    static {
        I();
        inventoryBackground = new ResourceLocation(GuiContainer.I["".length()]);
    }
    
    @Override
    public void initGui() {
        super.initGui();
        this.mc.thePlayer.openContainer = this.inventorySlots;
        this.guiLeft = (this.width - this.xSize) / "  ".length();
        this.guiTop = (this.height - this.ySize) / "  ".length();
    }
    
    protected boolean checkHotbarKeys(final int n) {
        if (this.mc.thePlayer.inventory.getItemStack() == null && this.theSlot != null) {
            int i = "".length();
            "".length();
            if (3 < 3) {
                throw null;
            }
            while (i < (0x9D ^ 0x94)) {
                if (n == this.mc.gameSettings.keyBindsHotbar[i].getKeyCode()) {
                    this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, i, "  ".length());
                    return " ".length() != 0;
                }
                ++i;
            }
        }
        return "".length() != 0;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u0004$\u0015\u0018 \u0002$\u001eC2\u0005(B\u000f:\u001e5\f\u0005;\u00153B\u0005;\u0006$\u0003\u0018:\u00028C\u001c;\u0017", "pAmlU");
        GuiContainer.I[" ".length()] = I("]", "miokJ");
    }
    
    public GuiContainer(final Container inventorySlots) {
        this.xSize = 147 + 115 - 221 + 135;
        this.ySize = 143 + 100 - 105 + 28;
        this.dragSplittingSlots = (Set<Slot>)Sets.newHashSet();
        this.inventorySlots = inventorySlots;
        this.ignoreMouseUp = (" ".length() != 0);
    }
}
