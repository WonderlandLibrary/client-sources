/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen.inventory;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.util.Set;
import javax.annotation.Nullable;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.functions.impl.misc.ItemScroller;
import mpp.venusfr.venusfr;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.glfw.GLFW;

public abstract class ContainerScreen<T extends Container>
extends Screen
implements IHasContainer<T> {
    public static final ResourceLocation INVENTORY_BACKGROUND = new ResourceLocation("textures/gui/container/inventory.png");
    protected int xSize = 176;
    protected int ySize = 166;
    protected int titleX;
    protected int titleY;
    protected int playerInventoryTitleX;
    protected int playerInventoryTitleY;
    protected final T container;
    protected final PlayerInventory playerInventory;
    @Nullable
    protected Slot hoveredSlot;
    @Nullable
    private Slot clickedSlot;
    @Nullable
    private Slot returningStackDestSlot;
    @Nullable
    private Slot currentDragTargetSlot;
    @Nullable
    private Slot lastClickSlot;
    protected int guiLeft;
    protected int guiTop;
    private boolean isRightMouseClick;
    private ItemStack draggedStack = ItemStack.EMPTY;
    private int touchUpX;
    private int touchUpY;
    private long returningStackTime;
    private ItemStack returningStack = ItemStack.EMPTY;
    private long dragItemDropDelay;
    protected final Set<Slot> dragSplittingSlots = Sets.newHashSet();
    protected boolean dragSplitting;
    private int dragSplittingLimit;
    private int dragSplittingButton;
    private boolean ignoreMouseUp;
    private int dragSplittingRemnant;
    private long lastClickTime;
    private int lastClickButton;
    private boolean doubleClick;
    private ItemStack shiftClickedSlot = ItemStack.EMPTY;

    public ContainerScreen(T t, PlayerInventory playerInventory, ITextComponent iTextComponent) {
        super(iTextComponent);
        this.container = t;
        this.playerInventory = playerInventory;
        this.ignoreMouseUp = true;
        this.titleX = 8;
        this.titleY = 6;
        this.playerInventoryTitleX = 8;
        this.playerInventoryTitleY = this.ySize - 94;
    }

    @Override
    protected void init() {
        super.init();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        int n3;
        Object object;
        int n4 = this.guiLeft;
        int n5 = this.guiTop;
        this.drawGuiContainerBackgroundLayer(matrixStack, f, n, n2);
        RenderSystem.disableRescaleNormal();
        RenderSystem.disableDepthTest();
        super.render(matrixStack, n, n2, f);
        RenderSystem.pushMatrix();
        RenderSystem.translatef(n4, n5, 0.0f);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableRescaleNormal();
        this.hoveredSlot = null;
        int n6 = 240;
        int n7 = 240;
        RenderSystem.glMultiTexCoord2f(33986, 240.0f, 240.0f);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        for (int i = 0; i < ((Container)this.container).inventorySlots.size(); ++i) {
            object = ((Container)this.container).inventorySlots.get(i);
            if (((Slot)object).isEnabled()) {
                this.moveItems(matrixStack, (Slot)object);
            }
            if (!this.isSlotSelected((Slot)object, n, n2) || !((Slot)object).isEnabled()) continue;
            FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
            ItemScroller itemScroller = functionRegistry.getItemScroller();
            if (venusfr.getInstance().getFunctionRegistry().getItemScroller().isState() && GLFW.glfwGetMouseButton(Minecraft.getInstance().getMainWindow().getHandle(), 0) == 1 && GLFW.glfwGetKey(Minecraft.getInstance().getMainWindow().getHandle(), 340) == 1 && ((Slot)object).getStack().getItem() != Items.AIR) {
                this.handleMouseClick((Slot)object, ((Slot)object).slotNumber, 0, ClickType.QUICK_MOVE);
            }
            this.hoveredSlot = object;
            RenderSystem.disableDepthTest();
            int n8 = ((Slot)object).xPos;
            n3 = ((Slot)object).yPos;
            RenderSystem.colorMask(true, true, true, false);
            this.fillGradient(matrixStack, n8, n3, n8 + 16, n3 + 16, -2130706433, -2130706433);
            RenderSystem.colorMask(true, true, true, true);
            RenderSystem.enableDepthTest();
        }
        this.drawGuiContainerForegroundLayer(matrixStack, n, n2);
        PlayerInventory playerInventory = this.minecraft.player.inventory;
        Object object2 = object = this.draggedStack.isEmpty() ? playerInventory.getItemStack() : this.draggedStack;
        if (!((ItemStack)object).isEmpty()) {
            int n9 = 8;
            int n10 = this.draggedStack.isEmpty() ? 8 : 16;
            String string = null;
            if (!this.draggedStack.isEmpty() && this.isRightMouseClick) {
                object = ((ItemStack)object).copy();
                ((ItemStack)object).setCount(MathHelper.ceil((float)((ItemStack)object).getCount() / 2.0f));
            } else if (this.dragSplitting && this.dragSplittingSlots.size() > 1) {
                object = ((ItemStack)object).copy();
                ((ItemStack)object).setCount(this.dragSplittingRemnant);
                if (((ItemStack)object).isEmpty()) {
                    string = TextFormatting.YELLOW + "0";
                }
            }
            this.drawItemStack((ItemStack)object, n - n4 - 8, n2 - n5 - n10, string);
        }
        if (!this.returningStack.isEmpty()) {
            float f2 = (float)(Util.milliTime() - this.returningStackTime) / 100.0f;
            if (f2 >= 1.0f) {
                f2 = 1.0f;
                this.returningStack = ItemStack.EMPTY;
            }
            int n11 = this.returningStackDestSlot.xPos - this.touchUpX;
            int n12 = this.returningStackDestSlot.yPos - this.touchUpY;
            n3 = this.touchUpX + (int)((float)n11 * f2);
            int n13 = this.touchUpY + (int)((float)n12 * f2);
            this.drawItemStack(this.returningStack, n3, n13, null);
        }
        RenderSystem.popMatrix();
        RenderSystem.enableDepthTest();
    }

    protected void renderHoveredTooltip(MatrixStack matrixStack, int n, int n2) {
        if (this.minecraft.player.inventory.getItemStack().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.getHasStack()) {
            this.renderTooltip(matrixStack, this.hoveredSlot.getStack(), n, n2);
        }
    }

    private void drawItemStack(ItemStack itemStack, int n, int n2, String string) {
        RenderSystem.translatef(0.0f, 0.0f, 32.0f);
        this.setBlitOffset(200);
        this.itemRenderer.zLevel = 200.0f;
        this.itemRenderer.renderItemAndEffectIntoGUI(itemStack, n, n2);
        this.itemRenderer.renderItemOverlayIntoGUI(this.font, itemStack, n, n2 - (this.draggedStack.isEmpty() ? 0 : 8), string);
        this.setBlitOffset(0);
        this.itemRenderer.zLevel = 0.0f;
    }

    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int n, int n2) {
        this.font.func_243248_b(matrixStack, this.title, this.titleX, this.titleY, 0x404040);
        this.font.func_243248_b(matrixStack, this.playerInventory.getDisplayName(), this.playerInventoryTitleX, this.playerInventoryTitleY, 0x404040);
    }

    protected abstract void drawGuiContainerBackgroundLayer(MatrixStack var1, float var2, int var3, int var4);

    private void moveItems(MatrixStack matrixStack, Slot slot) {
        Pair<ResourceLocation, ResourceLocation> pair;
        int n = slot.xPos;
        int n2 = slot.yPos;
        ItemStack itemStack = slot.getStack();
        boolean bl = false;
        boolean bl2 = slot == this.clickedSlot && !this.draggedStack.isEmpty() && !this.isRightMouseClick;
        ItemStack itemStack2 = this.minecraft.player.inventory.getItemStack();
        String string = null;
        if (slot == this.clickedSlot && !this.draggedStack.isEmpty() && this.isRightMouseClick && !itemStack.isEmpty()) {
            itemStack = itemStack.copy();
            itemStack.setCount(itemStack.getCount() / 2);
        } else if (this.dragSplitting && this.dragSplittingSlots.contains(slot) && !itemStack2.isEmpty()) {
            if (this.dragSplittingSlots.size() == 1) {
                return;
            }
            if (Container.canAddItemToSlot(slot, itemStack2, true) && ((Container)this.container).canDragIntoSlot(slot)) {
                itemStack = itemStack2.copy();
                bl = true;
                Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, itemStack, slot.getStack().isEmpty() ? 0 : slot.getStack().getCount());
                int n3 = Math.min(itemStack.getMaxStackSize(), slot.getItemStackLimit(itemStack));
                if (itemStack.getCount() > n3) {
                    string = TextFormatting.YELLOW.toString() + n3;
                    itemStack.setCount(n3);
                }
            } else {
                this.dragSplittingSlots.remove(slot);
                this.updateDragSplitting();
            }
        }
        this.setBlitOffset(100);
        this.itemRenderer.zLevel = 100.0f;
        if (itemStack.isEmpty() && slot.isEnabled() && (pair = slot.getBackground()) != null) {
            TextureAtlasSprite textureAtlasSprite = this.minecraft.getAtlasSpriteGetter(pair.getFirst()).apply(pair.getSecond());
            this.minecraft.getTextureManager().bindTexture(textureAtlasSprite.getAtlasTexture().getTextureLocation());
            ContainerScreen.blit(matrixStack, n, n2, this.getBlitOffset(), 16, 16, textureAtlasSprite);
            bl2 = true;
        }
        if (!bl2) {
            if (bl) {
                ContainerScreen.fill(matrixStack, n, n2, n + 16, n2 + 16, -2130706433);
            }
            RenderSystem.enableDepthTest();
            this.itemRenderer.renderItemAndEffectIntoGUI(this.minecraft.player, itemStack, n, n2);
            this.itemRenderer.renderItemOverlayIntoGUI(this.font, itemStack, n, n2, string);
        }
        this.itemRenderer.zLevel = 0.0f;
        this.setBlitOffset(0);
    }

    private void updateDragSplitting() {
        ItemStack itemStack = this.minecraft.player.inventory.getItemStack();
        if (!itemStack.isEmpty() && this.dragSplitting) {
            if (this.dragSplittingLimit == 2) {
                this.dragSplittingRemnant = itemStack.getMaxStackSize();
            } else {
                this.dragSplittingRemnant = itemStack.getCount();
                for (Slot slot : this.dragSplittingSlots) {
                    ItemStack itemStack2 = itemStack.copy();
                    ItemStack itemStack3 = slot.getStack();
                    int n = itemStack3.isEmpty() ? 0 : itemStack3.getCount();
                    Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, itemStack2, n);
                    int n2 = Math.min(itemStack2.getMaxStackSize(), slot.getItemStackLimit(itemStack2));
                    if (itemStack2.getCount() > n2) {
                        itemStack2.setCount(n2);
                    }
                    this.dragSplittingRemnant -= itemStack2.getCount() - n;
                }
            }
        }
    }

    @Nullable
    private Slot getSelectedSlot(double d, double d2) {
        for (int i = 0; i < ((Container)this.container).inventorySlots.size(); ++i) {
            Slot slot = ((Container)this.container).inventorySlots.get(i);
            if (!this.isSlotSelected(slot, d, d2) || !slot.isEnabled()) continue;
            return slot;
        }
        return null;
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        if (super.mouseClicked(d, d2, n)) {
            return false;
        }
        boolean bl = this.minecraft.gameSettings.keyBindPickBlock.matchesMouseKey(n);
        Slot slot = this.getSelectedSlot(d, d2);
        long l = Util.milliTime();
        this.doubleClick = this.lastClickSlot == slot && l - this.lastClickTime < 250L && this.lastClickButton == n;
        this.ignoreMouseUp = false;
        if (n != 0 && n != 1 && !bl) {
            this.hotkeySwapItems(n);
        } else {
            int n2 = this.guiLeft;
            int n3 = this.guiTop;
            boolean bl2 = this.hasClickedOutside(d, d2, n2, n3, n);
            int n4 = -1;
            if (slot != null) {
                n4 = slot.slotNumber;
            }
            if (bl2) {
                n4 = -999;
            }
            if (this.minecraft.gameSettings.touchscreen && bl2 && this.minecraft.player.inventory.getItemStack().isEmpty()) {
                this.minecraft.displayGuiScreen(null);
                return false;
            }
            if (n4 != -1) {
                if (this.minecraft.gameSettings.touchscreen) {
                    if (slot != null && slot.getHasStack()) {
                        this.clickedSlot = slot;
                        this.draggedStack = ItemStack.EMPTY;
                        this.isRightMouseClick = n == 1;
                    } else {
                        this.clickedSlot = null;
                    }
                } else if (!this.dragSplitting) {
                    if (this.minecraft.player.inventory.getItemStack().isEmpty()) {
                        if (this.minecraft.gameSettings.keyBindPickBlock.matchesMouseKey(n)) {
                            this.handleMouseClick(slot, n4, n, ClickType.CLONE);
                        } else {
                            boolean bl3 = n4 != -999 && (InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 340) || InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 344));
                            ClickType clickType = ClickType.PICKUP;
                            if (bl3) {
                                this.shiftClickedSlot = slot != null && slot.getHasStack() ? slot.getStack().copy() : ItemStack.EMPTY;
                                clickType = ClickType.QUICK_MOVE;
                            } else if (n4 == -999) {
                                clickType = ClickType.THROW;
                            }
                            this.handleMouseClick(slot, n4, n, clickType);
                        }
                        this.ignoreMouseUp = true;
                    } else {
                        this.dragSplitting = true;
                        this.dragSplittingButton = n;
                        this.dragSplittingSlots.clear();
                        if (n == 0) {
                            this.dragSplittingLimit = 0;
                        } else if (n == 1) {
                            this.dragSplittingLimit = 1;
                        } else if (this.minecraft.gameSettings.keyBindPickBlock.matchesMouseKey(n)) {
                            this.dragSplittingLimit = 2;
                        }
                    }
                }
            }
        }
        this.lastClickSlot = slot;
        this.lastClickTime = l;
        this.lastClickButton = n;
        return false;
    }

    private void hotkeySwapItems(int n) {
        if (this.hoveredSlot != null && this.minecraft.player.inventory.getItemStack().isEmpty()) {
            if (this.minecraft.gameSettings.keyBindSwapHands.matchesMouseKey(n)) {
                this.handleMouseClick(this.hoveredSlot, this.hoveredSlot.slotNumber, 40, ClickType.SWAP);
                return;
            }
            for (int i = 0; i < 9; ++i) {
                if (!this.minecraft.gameSettings.keyBindsHotbar[i].matchesMouseKey(n)) continue;
                this.handleMouseClick(this.hoveredSlot, this.hoveredSlot.slotNumber, i, ClickType.SWAP);
            }
        }
    }

    protected boolean hasClickedOutside(double d, double d2, int n, int n2, int n3) {
        return d < (double)n || d2 < (double)n2 || d >= (double)(n + this.xSize) || d2 >= (double)(n2 + this.ySize);
    }

    @Override
    public boolean mouseDragged(double d, double d2, int n, double d3, double d4) {
        Slot slot = this.getSelectedSlot(d, d2);
        ItemStack itemStack = this.minecraft.player.inventory.getItemStack();
        if (this.clickedSlot != null && this.minecraft.gameSettings.touchscreen) {
            if (n == 0 || n == 1) {
                if (this.draggedStack.isEmpty()) {
                    if (slot != this.clickedSlot && !this.clickedSlot.getStack().isEmpty()) {
                        this.draggedStack = this.clickedSlot.getStack().copy();
                    }
                } else if (this.draggedStack.getCount() > 1 && slot != null && Container.canAddItemToSlot(slot, this.draggedStack, false)) {
                    long l = Util.milliTime();
                    if (this.currentDragTargetSlot == slot) {
                        if (l - this.dragItemDropDelay > 500L) {
                            this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, ClickType.PICKUP);
                            this.handleMouseClick(slot, slot.slotNumber, 1, ClickType.PICKUP);
                            this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, ClickType.PICKUP);
                            this.dragItemDropDelay = l + 750L;
                            this.draggedStack.shrink(1);
                        }
                    } else {
                        this.currentDragTargetSlot = slot;
                        this.dragItemDropDelay = l;
                    }
                }
            }
        } else if (this.dragSplitting && slot != null && !itemStack.isEmpty() && (itemStack.getCount() > this.dragSplittingSlots.size() || this.dragSplittingLimit == 2) && Container.canAddItemToSlot(slot, itemStack, true) && slot.isItemValid(itemStack) && ((Container)this.container).canDragIntoSlot(slot)) {
            this.dragSplittingSlots.add(slot);
            this.updateDragSplitting();
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double d, double d2, int n) {
        Slot slot = this.getSelectedSlot(d, d2);
        int n2 = this.guiLeft;
        int n3 = this.guiTop;
        boolean bl = this.hasClickedOutside(d, d2, n2, n3, n);
        int n4 = -1;
        if (slot != null) {
            n4 = slot.slotNumber;
        }
        if (bl) {
            n4 = -999;
        }
        if (this.doubleClick && slot != null && n == 0 && ((Container)this.container).canMergeSlot(ItemStack.EMPTY, slot)) {
            if (ContainerScreen.hasShiftDown()) {
                if (!this.shiftClickedSlot.isEmpty()) {
                    for (Slot slot2 : ((Container)this.container).inventorySlots) {
                        if (slot2 == null || !slot2.canTakeStack(this.minecraft.player) || !slot2.getHasStack() || slot2.inventory != slot.inventory || !Container.canAddItemToSlot(slot2, this.shiftClickedSlot, true)) continue;
                        this.handleMouseClick(slot2, slot2.slotNumber, n, ClickType.QUICK_MOVE);
                    }
                }
            } else {
                this.handleMouseClick(slot, n4, n, ClickType.PICKUP_ALL);
            }
            this.doubleClick = false;
            this.lastClickTime = 0L;
        } else {
            if (this.dragSplitting && this.dragSplittingButton != n) {
                this.dragSplitting = false;
                this.dragSplittingSlots.clear();
                this.ignoreMouseUp = true;
                return false;
            }
            if (this.ignoreMouseUp) {
                this.ignoreMouseUp = false;
                return false;
            }
            if (this.clickedSlot != null && this.minecraft.gameSettings.touchscreen) {
                if (n == 0 || n == 1) {
                    if (this.draggedStack.isEmpty() && slot != this.clickedSlot) {
                        this.draggedStack = this.clickedSlot.getStack();
                    }
                    boolean bl2 = Container.canAddItemToSlot(slot, this.draggedStack, false);
                    if (n4 != -1 && !this.draggedStack.isEmpty() && bl2) {
                        this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, n, ClickType.PICKUP);
                        this.handleMouseClick(slot, n4, 0, ClickType.PICKUP);
                        if (this.minecraft.player.inventory.getItemStack().isEmpty()) {
                            this.returningStack = ItemStack.EMPTY;
                        } else {
                            this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, n, ClickType.PICKUP);
                            this.touchUpX = MathHelper.floor(d - (double)n2);
                            this.touchUpY = MathHelper.floor(d2 - (double)n3);
                            this.returningStackDestSlot = this.clickedSlot;
                            this.returningStack = this.draggedStack;
                            this.returningStackTime = Util.milliTime();
                        }
                    } else if (!this.draggedStack.isEmpty()) {
                        this.touchUpX = MathHelper.floor(d - (double)n2);
                        this.touchUpY = MathHelper.floor(d2 - (double)n3);
                        this.returningStackDestSlot = this.clickedSlot;
                        this.returningStack = this.draggedStack;
                        this.returningStackTime = Util.milliTime();
                    }
                    this.draggedStack = ItemStack.EMPTY;
                    this.clickedSlot = null;
                }
            } else if (this.dragSplitting && !this.dragSplittingSlots.isEmpty()) {
                this.handleMouseClick(null, -999, Container.getQuickcraftMask(0, this.dragSplittingLimit), ClickType.QUICK_CRAFT);
                for (Slot slot3 : this.dragSplittingSlots) {
                    this.handleMouseClick(slot3, slot3.slotNumber, Container.getQuickcraftMask(1, this.dragSplittingLimit), ClickType.QUICK_CRAFT);
                }
                this.handleMouseClick(null, -999, Container.getQuickcraftMask(2, this.dragSplittingLimit), ClickType.QUICK_CRAFT);
            } else if (!this.minecraft.player.inventory.getItemStack().isEmpty()) {
                if (this.minecraft.gameSettings.keyBindPickBlock.matchesMouseKey(n)) {
                    this.handleMouseClick(slot, n4, n, ClickType.CLONE);
                } else {
                    boolean bl3;
                    boolean bl4 = bl3 = n4 != -999 && (InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 340) || InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 344));
                    if (bl3) {
                        this.shiftClickedSlot = slot != null && slot.getHasStack() ? slot.getStack().copy() : ItemStack.EMPTY;
                    }
                    this.handleMouseClick(slot, n4, n, bl3 ? ClickType.QUICK_MOVE : ClickType.PICKUP);
                }
            }
        }
        if (this.minecraft.player.inventory.getItemStack().isEmpty()) {
            this.lastClickTime = 0L;
        }
        this.dragSplitting = false;
        return false;
    }

    private boolean isSlotSelected(Slot slot, double d, double d2) {
        return this.isPointInRegion(slot.xPos, slot.yPos, 16, 16, d, d2);
    }

    protected boolean isPointInRegion(int n, int n2, int n3, int n4, double d, double d2) {
        int n5 = this.guiLeft;
        int n6 = this.guiTop;
        return (d -= (double)n5) >= (double)(n - 1) && d < (double)(n + n3 + 1) && (d2 -= (double)n6) >= (double)(n2 - 1) && d2 < (double)(n2 + n4 + 1);
    }

    protected void handleMouseClick(Slot slot, int n, int n2, ClickType clickType) {
        if (slot != null) {
            n = slot.slotNumber;
        }
        this.minecraft.playerController.windowClick(((Container)this.container).windowId, n, n2, clickType, this.minecraft.player);
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (super.keyPressed(n, n2, n3)) {
            return false;
        }
        if (this.minecraft.gameSettings.keyBindInventory.matchesKey(n, n2)) {
            this.closeScreen();
            return false;
        }
        this.itemStackMoved(n, n2);
        if (this.hoveredSlot != null && this.hoveredSlot.getHasStack()) {
            if (this.minecraft.gameSettings.keyBindPickBlock.matchesKey(n, n2)) {
                this.handleMouseClick(this.hoveredSlot, this.hoveredSlot.slotNumber, 0, ClickType.CLONE);
            } else if (this.minecraft.gameSettings.keyBindDrop.matchesKey(n, n2)) {
                this.handleMouseClick(this.hoveredSlot, this.hoveredSlot.slotNumber, ContainerScreen.hasControlDown() ? 1 : 0, ClickType.THROW);
            }
        }
        return false;
    }

    protected boolean itemStackMoved(int n, int n2) {
        if (this.minecraft.player.inventory.getItemStack().isEmpty() && this.hoveredSlot != null) {
            if (this.minecraft.gameSettings.keyBindSwapHands.matchesKey(n, n2)) {
                this.handleMouseClick(this.hoveredSlot, this.hoveredSlot.slotNumber, 40, ClickType.SWAP);
                return false;
            }
            for (int i = 0; i < 9; ++i) {
                if (!this.minecraft.gameSettings.keyBindsHotbar[i].matchesKey(n, n2)) continue;
                this.handleMouseClick(this.hoveredSlot, this.hoveredSlot.slotNumber, i, ClickType.SWAP);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClose() {
        if (this.minecraft.player != null) {
            ((Container)this.container).onContainerClosed(this.minecraft.player);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.minecraft.player.isAlive() || this.minecraft.player.removed) {
            this.minecraft.player.closeScreen();
        }
    }

    @Override
    public T getContainer() {
        return this.container;
    }

    @Override
    public void closeScreen() {
        this.minecraft.player.closeScreen();
        super.closeScreen();
    }
}

