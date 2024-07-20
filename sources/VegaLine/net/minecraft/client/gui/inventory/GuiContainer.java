/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.inventory;

import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import ru.govno.client.module.modules.ClientColors;
import ru.govno.client.module.modules.ComfortUi;
import ru.govno.client.module.modules.InvWalk;
import ru.govno.client.module.modules.ProContainer;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.utils.Command.impl.Panic;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.Math.TimerHelper;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public abstract class GuiContainer
extends GuiScreen {
    public static final ResourceLocation INVENTORY_BACKGROUND = new ResourceLocation("textures/gui/container/inventory.png");
    protected int xSize = 176;
    protected int ySize = 166;
    public Container inventorySlots;
    protected int guiLeft;
    protected int guiTop;
    private Slot theSlot;
    private Slot clickedSlot;
    private boolean isRightMouseClick;
    public static ItemStack draggedStack = ItemStack.field_190927_a;
    private int touchUpX;
    private int touchUpY;
    private Slot returningStackDestSlot;
    private long returningStackTime;
    private ItemStack returningStack = ItemStack.field_190927_a;
    private Slot currentDragTargetSlot;
    private long dragItemDropDelay;
    protected final Set<Slot> dragSplittingSlots = Sets.newHashSet();
    protected boolean dragSplitting;
    private int dragSplittingLimit;
    private int dragSplittingButton;
    private boolean ignoreMouseUp;
    private int dragSplittingRemnant;
    private long lastClickTime;
    private Slot lastClickSlot;
    private int lastClickButton;
    private boolean doubleClick;
    private ItemStack shiftClickedSlot = ItemStack.field_190927_a;
    public static final AnimationUtils inter = new AnimationUtils(0.0f, 0.0f, 0.1f);
    boolean colose = false;
    boolean drop = false;
    boolean loadEC = false;
    AnimationUtils extButton = new AnimationUtils(0.0f, 0.0f, 0.075f);
    static int stacksPerTick = 0;
    static TimerHelper timePassDrop = new TimerHelper();
    int mx;
    int my;

    public GuiContainer(Container inventorySlotsIn) {
        this.inventorySlots = inventorySlotsIn;
        this.ignoreMouseUp = true;
    }

    @Override
    public void initGui() {
        super.initGui();
        GuiContainer.inter.speed = 0.125f;
        Minecraft.player.openContainer = this.inventorySlots;
        this.guiLeft = (width - this.xSize) / 2;
        this.guiTop = (height - this.ySize) / 2;
        inter.setAnim(Panic.stop || !ComfortUi.get.isContainerAnim() ? 1.0f : 0.0f);
        GuiContainer.inter.to = 1.0f;
        this.colose = false;
        this.drop = false;
        this.mx = 0;
        this.my = 0;
        InvWalk.inInitScreen(this);
    }

    float[] dropButtonPos(boolean isEC) {
        ScaledResolution sr = new ScaledResolution(this.mc);
        float w = Fonts.neverlose500_16.getStringWidth(isEC ? "\u0412\u044b\u043b\u043e\u0436\u0438\u0442\u044c \u0432\u0441\u0451" : "\u0412\u044b\u043a\u0438\u043d\u0443\u0442\u044c \u0432\u0441\u0451") / 2 + 6;
        float x1 = (float)(sr.getScaledWidth() / 2) - w;
        float y1 = sr.getScaledHeight() / 2 - 105 - (isEC ? 30 : 0);
        float x2 = x1 + w * 2.0f;
        float y2 = y1 + 18.0f;
        return new float[]{x1, y1, x2, y2};
    }

    boolean invIsNoEmpty() {
        boolean hasStack = false;
        for (int i1 = 0; i1 < this.inventorySlots.inventorySlots.size(); ++i1) {
            Slot slot = this.inventorySlots.inventorySlots.get(i1);
            if (!slot.getHasStack()) continue;
            hasStack = true;
        }
        return hasStack;
    }

    boolean canHasDropButton() {
        return !Panic.stop && this.invIsNoEmpty();
    }

    void drawButton(boolean hover, boolean isEC) {
        float ext;
        float[] pos = this.dropButtonPos(isEC);
        this.extButton.to = ext = hover && !this.colose ? 1.5f : 0.0f;
        ext = this.extButton.getAnim();
        float x1 = pos[0] - ext;
        float y1 = pos[1] - ext;
        float x2 = pos[2] + ext;
        float y2 = pos[3] + ext;
        String str = isEC ? "\u0412\u044b\u043b\u043e\u0436\u0438\u0442\u044c \u0432\u0441\u0451" : "\u0412\u044b\u043a\u0438\u043d\u0443\u0442\u044c \u0432\u0441\u0451";
        float aPC = GuiInventory.containerAlpha;
        aPC *= aPC;
        float w = x1 + (x2 - x1) / 2.0f;
        int cli1 = ClientColors.getColor1(0, aPC);
        int cli2 = ClientColors.getColor2(-324, aPC);
        int cli3 = ClientColors.getColor2(0, aPC);
        int cli4 = ClientColors.getColor1(972, aPC);
        int cc1 = ColorUtils.swapAlpha(cli1, (float)RenderUtils.alpha(cli1) * aPC / 5.0f);
        int cc2 = ColorUtils.swapAlpha(cli2, (float)RenderUtils.alpha(cli2) * aPC / 5.0f);
        int cc3 = ColorUtils.swapAlpha(cli3, (float)RenderUtils.alpha(cli3) * aPC / 5.0f);
        int cc4 = ColorUtils.swapAlpha(cli4, (float)RenderUtils.alpha(cli4) * aPC / 5.0f);
        RenderUtils.drawOutsideAndInsideFullRoundedFullGradientShadowRectWithBloomBool(x1, y1, x2, y2, 4.0f, cli1, cli2, cli3, cli4, true);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x1, y1, x2, y2, 4.0f, 1.0f, cc1, cc2, cc3, cc4, true, true, true);
        if (255.0f * aPC >= 26.0f) {
            Fonts.neverlose500_16.drawStringWithShadow(str, x1 / 2.0f + w / 2.0f - (float)Fonts.neverlose500_16.getStringWidth(str) / 4.0f + 2.0f + ext * 0.5f, y1 + (y2 - y1) / 2.0f - 2.0f, ColorUtils.swapAlpha(ColorUtils.getOverallColorFrom(ColorUtils.getFixedWhiteColor(), ColorUtils.getOverallColorFrom(ColorUtils.getOverallColorFrom(cli1, cli2), ColorUtils.getOverallColorFrom(cli3, cli4)), 0.3333f), 255.0f * aPC));
        }
        GL11.glEnable(2929);
        GL11.glDisable(2896);
        GL11.glDepthMask(true);
    }

    boolean isHoverButton(int mouseX, int mouseY, boolean isEC) {
        float[] pos = this.dropButtonPos(isEC);
        return RenderUtils.isHovered(mouseX, mouseY, pos[0], pos[1], pos[2] - pos[0], pos[3] - pos[1]);
    }

    void droperInventory() {
        if (this.drop) {
            int maxPerTick = 2;
            boolean hasItem = false;
            for (int i1 = 0; i1 < this.inventorySlots.inventorySlots.size(); ++i1) {
                Slot slot = this.inventorySlots.inventorySlots.get(i1);
                if (!slot.getHasStack() || slot.getStack().getItem() == Items.air) continue;
                hasItem = true;
                if (stacksPerTick <= maxPerTick) {
                    if (!timePassDrop.hasReached(50.0)) continue;
                    this.mc.playerController.windowClick(Minecraft.player.inventoryContainer.windowId, slot.slotNumber, 1, ClickType.THROW, Minecraft.player);
                    ++stacksPerTick;
                    continue;
                }
                stacksPerTick = 0;
                if (0 != 0) continue;
                timePassDrop.reset();
            }
            if (hasItem) {
                return;
            }
            stacksPerTick = 0;
            this.drop = false;
            this.colose = true;
            GuiContainer.inter.to = 0.0f;
        }
    }

    void loadChest() {
        if (this.loadEC) {
            int maxPerTick = 3;
            boolean hasItem = false;
            if (Minecraft.player.openContainer != null && Minecraft.player.openContainer instanceof ContainerChest) {
                for (int i1 = 0; i1 < this.inventorySlots.inventorySlots.size(); ++i1) {
                    boolean pcm;
                    Slot slot;
                    if (this.inventorySlots.inventorySlots.get(i1) == null || (slot = this.inventorySlots.inventorySlots.get(i1)) == null || !(slot.inventory instanceof InventoryPlayer) || !slot.getHasStack() || slot.getStack().getItem() == Items.air) continue;
                    hasItem = true;
                    boolean bl = pcm = slot.getStack().stackSize == 1;
                    if (stacksPerTick <= maxPerTick) {
                        if (!timePassDrop.hasReached(50.0) || slot == null) continue;
                        this.mc.playerController.windowClick(Minecraft.player.openContainer.windowId, slot.slotNumber, 1, ClickType.QUICK_MOVE, Minecraft.player);
                        ++stacksPerTick;
                        continue;
                    }
                    stacksPerTick = 0;
                    if (0 != 0) continue;
                    timePassDrop.reset();
                }
            }
            if (hasItem) {
                return;
            }
            stacksPerTick = 0;
            this.loadEC = false;
            this.colose = true;
            inter.setAnim(Panic.stop || !ComfortUi.get.isContainerAnim() ? 0.0f : 1.0f);
            GuiContainer.inter.to = 0.0f;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GuiInventory.containerAlpha = inter.getAnim();
        if (this.colose && (double)inter.getAnim() < 0.1) {
            Minecraft.player.closeScreen();
        }
        this.mx = mouseX;
        this.my = mouseY;
        if (this.canHasDropButton() && ComfortUi.get.isAddClientButtons()) {
            this.drawButton(this.isHoverButton(mouseX, mouseY, this.isECCH()), this.isECCH());
        }
        this.droperInventory();
        this.loadChest();
        GlStateManager.pushMatrix();
        int i = this.guiLeft;
        int j = this.guiTop;
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        super.drawScreen(mouseX, mouseY, partialTicks);
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.pushMatrix();
        GlStateManager.translate(i, j, 0.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.enableRescaleNormal();
        this.theSlot = null;
        int k = 240;
        int l = 240;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        for (int i1 = 0; i1 < this.inventorySlots.inventorySlots.size(); ++i1) {
            Slot slot = this.inventorySlots.inventorySlots.get(i1);
            if (slot.canBeHovered()) {
                this.drawSlot(slot);
            }
            if (this.isMouseOverSlot(slot, mouseX, mouseY) && slot.canBeHovered()) {
                if (ProContainer.get.actived && ProContainer.get.ScrollItems.bValue && Mouse.hasWheel()) {
                    int whell = Mouse.getDWheel();
                    if (this.mc.currentScreen instanceof GuiInventory && whell > 0) {
                        int craftnumber = -10001;
                        if (Minecraft.player.inventory.getStackInSlot(1).getItem() == slot.getStack().getItem() && Minecraft.player.inventory.getStackInSlot(1) != null && Minecraft.player.inventory.getStackInSlot(1).getCount2() < Minecraft.player.inventory.getStackInSlot(1).getMaxStackSize() || Minecraft.player.inventory.getStackInSlot(1).getItem() == Items.air || Minecraft.player.inventory.getStackInSlot(1) == null) {
                            craftnumber = 1;
                        } else if (Minecraft.player.inventory.getStackInSlot(2).getItem() == slot.getStack().getItem() && Minecraft.player.inventory.getStackInSlot(2) != null && Minecraft.player.inventory.getStackInSlot(2).getCount2() < Minecraft.player.inventory.getStackInSlot(1).getMaxStackSize() || Minecraft.player.inventory.getStackInSlot(2).getItem() == Items.air || Minecraft.player.inventory.getStackInSlot(2) == null) {
                            craftnumber = 2;
                        } else if (Minecraft.player.inventory.getStackInSlot(3).getItem() == slot.getStack().getItem() && Minecraft.player.inventory.getStackInSlot(3) != null && Minecraft.player.inventory.getStackInSlot(3).getCount2() < Minecraft.player.inventory.getStackInSlot(1).getMaxStackSize() || Minecraft.player.inventory.getStackInSlot(3).getItem() == Items.air || Minecraft.player.inventory.getStackInSlot(3) == null) {
                            craftnumber = 3;
                        } else if (Minecraft.player.inventory.getStackInSlot(4).getItem() == slot.getStack().getItem() && Minecraft.player.inventory.getStackInSlot(4) != null && Minecraft.player.inventory.getStackInSlot(4).getCount2() < Minecraft.player.inventory.getStackInSlot(1).getMaxStackSize() || Minecraft.player.inventory.getStackInSlot(4).getItem() == Items.air || Minecraft.player.inventory.getStackInSlot(4) == null) {
                            craftnumber = 4;
                        }
                        if (craftnumber != -10001 && slot.getStack().getItem() != Items.air) {
                            if (slot.canBeHovered() && slot.slotNumber == craftnumber) {
                                this.handleMouseClick(this.inventorySlots.inventorySlots.get(craftnumber), craftnumber, 0, ClickType.PICKUP);
                                this.handleMouseClick(slot, slot.slotNumber, 1, ClickType.PICKUP);
                                this.handleMouseClick(slot, slot.slotNumber, Keyboard.isKeyDown(this.mc.gameSettings.keyBindSneak.getKeyCode()) ? 0 : 1, ClickType.QUICK_MOVE);
                                this.handleMouseClick(this.inventorySlots.inventorySlots.get(craftnumber), craftnumber, 0, ClickType.PICKUP);
                            } else if (slot.getStack().getItem() != Items.air) {
                                this.handleMouseClick(slot, slot.slotNumber, 0, ClickType.PICKUP);
                                this.handleMouseClick(this.inventorySlots.inventorySlots.get(craftnumber), craftnumber, Keyboard.isKeyDown(this.mc.gameSettings.keyBindSneak.getKeyCode()) ? 0 : 1, ClickType.PICKUP);
                                this.handleMouseClick(slot, slot.slotNumber, 0, ClickType.PICKUP);
                            }
                        }
                    } else if (slot.getStack().getItem() != Items.air && whell < 0 && !(this.mc.currentScreen instanceof GuiContainerCreative)) {
                        this.handleMouseClick(slot, slot.slotNumber, 0, ClickType.PICKUP);
                        this.handleMouseClick(slot, slot.slotNumber, 1, ClickType.PICKUP);
                        this.handleMouseClick(slot, slot.slotNumber, 0, ClickType.QUICK_MOVE);
                        this.handleMouseClick(slot, slot.slotNumber, 0, ClickType.PICKUP);
                    }
                }
                if (Mouse.isButtonDown(0) && Keyboard.isKeyDown(this.mc.gameSettings.keyBindSneak.getKeyCode()) && ProContainer.get.actived && ProContainer.get.MouseTweaks.bValue && this.mc.currentScreen != null && !(slot.getStack().getItem() instanceof ItemAir)) {
                    this.handleMouseClick(slot, slot.slotNumber, 1, ClickType.QUICK_MOVE);
                }
            }
            if (!this.isMouseOverSlot(slot, mouseX, mouseY) || !slot.canBeHovered()) continue;
            this.theSlot = slot;
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            int j1 = slot.xDisplayPosition;
            int k1 = slot.yDisplayPosition;
            GlStateManager.colorMask(true, true, true, false);
            this.drawGradientRect(j1, k1, j1 + 16, k1 + 16, ColorUtils.swapAlpha(-2130706433, (float)ColorUtils.getAlphaFromColor(-2130706433) * GuiContainer.inter.anim), ColorUtils.swapAlpha(-2130706433, (float)ColorUtils.getAlphaFromColor(-2130706433) * GuiContainer.inter.anim));
            GlStateManager.colorMask(true, true, true, true);
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
        }
        RenderHelper.disableStandardItemLighting();
        this.drawGuiContainerForegroundLayer(mouseX, mouseY);
        RenderHelper.enableGUIStandardItemLighting();
        InventoryPlayer inventoryplayer = Minecraft.player.inventory;
        ItemStack itemstack = draggedStack.isEmpty() ? inventoryplayer.getItemStack() : draggedStack;
        ItemStack itemStack = itemstack;
        if (!itemstack.isEmpty()) {
            int j2 = 8;
            int k2 = draggedStack.isEmpty() ? 8 : 16;
            String s = null;
            if (!draggedStack.isEmpty() && this.isRightMouseClick) {
                itemstack = itemstack.copy();
                itemstack.func_190920_e(MathHelper.ceil((float)itemstack.getCount2() / 2.0f));
            } else if (this.dragSplitting && this.dragSplittingSlots.size() > 1) {
                itemstack = itemstack.copy();
                itemstack.func_190920_e(this.dragSplittingRemnant);
                if (itemstack.isEmpty()) {
                    s = TextFormatting.YELLOW + "0";
                }
            }
            this.drawItemStack(itemstack, mouseX - i - 8, mouseY - j - k2, s);
        }
        if (!this.returningStack.isEmpty()) {
            float f = (float)(Minecraft.getSystemTime() - this.returningStackTime) / 100.0f;
            if (f >= 1.0f) {
                f = 1.0f;
                this.returningStack = ItemStack.field_190927_a;
            }
            int l2 = this.returningStackDestSlot.xDisplayPosition - this.touchUpX;
            int i3 = this.returningStackDestSlot.yDisplayPosition - this.touchUpY;
            int l1 = this.touchUpX + (int)((float)l2 * f);
            int i2 = this.touchUpY + (int)((float)i3 * f);
            this.drawItemStack(this.returningStack, l1, i2, null);
        }
        GlStateManager.popMatrix();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    protected void func_191948_b(int p_191948_1_, int p_191948_2_) {
        if (Minecraft.player.inventory.getItemStack().isEmpty() && this.theSlot != null && this.theSlot.getHasStack()) {
            this.renderToolTip(this.theSlot.getStack(), p_191948_1_, p_191948_2_);
        }
    }

    private void drawItemStack(ItemStack stack, int x, int y, String altText) {
        GlStateManager.translate(0.0f, 0.0f, 32.0f);
        this.zLevel = 200.0f;
        this.itemRender.zLevel = 200.0f;
        this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        this.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, stack, x, y - (draggedStack.isEmpty() ? 0 : 8), altText);
        this.zLevel = 0.0f;
        this.itemRender.zLevel = 0.0f;
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    }

    protected abstract void drawGuiContainerBackgroundLayer(float var1, int var2, int var3);

    private void drawSlot(Slot slotIn) {
        String s1;
        int i = slotIn.xDisplayPosition;
        int j = slotIn.yDisplayPosition;
        ItemStack itemstack = slotIn.getStack();
        boolean flag = false;
        boolean flag1 = slotIn == this.clickedSlot && !draggedStack.isEmpty() && !this.isRightMouseClick;
        ItemStack itemstack1 = Minecraft.player.inventory.getItemStack();
        String s = null;
        if (slotIn == this.clickedSlot && !draggedStack.isEmpty() && this.isRightMouseClick && !itemstack.isEmpty()) {
            itemstack = itemstack.copy();
            itemstack.func_190920_e(itemstack.getCount2() / 2);
        } else if (this.dragSplitting && this.dragSplittingSlots.contains(slotIn) && !itemstack1.isEmpty()) {
            if (this.dragSplittingSlots.size() == 1) {
                return;
            }
            if (Container.canAddItemToSlot(slotIn, itemstack1, true) && this.inventorySlots.canDragIntoSlot(slotIn)) {
                itemstack = itemstack1.copy();
                flag = true;
                Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, itemstack, slotIn.getStack().isEmpty() ? 0 : slotIn.getStack().getCount2());
                int k = Math.min(itemstack.getMaxStackSize(), slotIn.getItemStackLimit(itemstack));
                if (itemstack.getCount2() > k) {
                    s = TextFormatting.YELLOW.toString() + k;
                    itemstack.func_190920_e(k);
                }
            } else {
                this.dragSplittingSlots.remove(slotIn);
                this.updateDragSplitting();
            }
        }
        this.zLevel = 100.0f;
        this.itemRender.zLevel = 100.0f;
        if (itemstack.isEmpty() && slotIn.canBeHovered() && (s1 = slotIn.getSlotTexture()) != null) {
            float scale;
            TextureAtlasSprite textureatlassprite = this.mc.getTextureMapBlocks().getAtlasSprite(s1);
            GlStateManager.disableLighting();
            this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            float f = scale = !ComfortUi.get.isContainerAnim() ? 1.0f : MathUtils.clamp(inter.getAnim() * 1.25f, 0.01f, 1.0f);
            if (!Panic.stop && scale != 1.0f) {
                RenderUtils.customScaledObject2D(i, j, 16.0f, 16.0f, scale);
            }
            this.drawTexturedModalRect(i, j, textureatlassprite, 16, 16);
            if (!Panic.stop && scale != 1.0f) {
                RenderUtils.customScaledObject2D(i, j, 16.0f, 16.0f, 1.0f / scale);
            }
            GlStateManager.enableLighting();
            flag1 = true;
        }
        if (!flag1) {
            float scale;
            if (flag) {
                GuiContainer.drawRect(i, (double)j, (double)(i + 16), (double)(j + 16), -2130706433);
            }
            GlStateManager.enableDepth();
            float f = !ComfortUi.get.isContainerAnim() ? 1.0f : (MathUtils.getDifferenceOf(0.0f, inter.getAnim()) < (double)0.1f ? 0.0f : (scale = MathUtils.getDifferenceOf(1.0f, inter.getAnim()) < (double)0.1f ? 1.0f : inter.getAnim()));
            if (!Panic.stop && scale != 1.0f && itemstack != draggedStack) {
                GlStateManager.pushMatrix();
                RenderUtils.customScaledObject2D(i, j, 16.0f, 16.0f, scale);
            }
            this.itemRender.renderItemAndEffectIntoGUI(Minecraft.player, itemstack, i, j);
            this.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, itemstack, i, j, s);
            if (!Panic.stop && scale != 1.0f && itemstack != draggedStack) {
                RenderUtils.customScaledObject2D(i, j, 16.0f, 16.0f, 1.0f / scale);
                GlStateManager.popMatrix();
            }
        }
        this.itemRender.zLevel = 0.0f;
        this.zLevel = 0.0f;
    }

    private void updateDragSplitting() {
        ItemStack itemstack = Minecraft.player.inventory.getItemStack();
        if (!itemstack.isEmpty() && this.dragSplitting) {
            if (this.dragSplittingLimit == 2) {
                this.dragSplittingRemnant = itemstack.getMaxStackSize();
            } else {
                this.dragSplittingRemnant = itemstack.getCount2();
                for (Slot slot : this.dragSplittingSlots) {
                    ItemStack itemstack1 = itemstack.copy();
                    ItemStack itemstack2 = slot.getStack();
                    int i = itemstack2.isEmpty() ? 0 : itemstack2.getCount2();
                    Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, itemstack1, i);
                    int j = Math.min(itemstack1.getMaxStackSize(), slot.getItemStackLimit(itemstack1));
                    if (itemstack1.getCount2() > j) {
                        itemstack1.func_190920_e(j);
                    }
                    this.dragSplittingRemnant -= itemstack1.getCount2() - i;
                }
            }
        }
    }

    private Slot getSlotAtPosition(int x, int y) {
        for (int i = 0; i < this.inventorySlots.inventorySlots.size(); ++i) {
            Slot slot = this.inventorySlots.inventorySlots.get(i);
            if (!this.isMouseOverSlot(slot, x, y) || !slot.canBeHovered()) continue;
            return slot;
        }
        return null;
    }

    private boolean isECCH() {
        return !(this instanceof GuiInventory);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0 && this.canHasDropButton() && ComfortUi.get.isAddClientButtons() && this.isHoverButton(mouseX, mouseY, this.isECCH())) {
            if (this.isECCH()) {
                this.loadEC = true;
            } else {
                this.drop = true;
            }
        }
        boolean flag = mouseButton == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100;
        Slot slot = this.getSlotAtPosition(mouseX, mouseY);
        long i = Minecraft.getSystemTime();
        this.doubleClick = this.lastClickSlot == slot && i - this.lastClickTime < 250L && this.lastClickButton == mouseButton;
        this.ignoreMouseUp = false;
        if (mouseButton == 0 || mouseButton == 1 || flag) {
            int j = this.guiLeft;
            int k = this.guiTop;
            boolean flag1 = this.func_193983_c(mouseX, mouseY, j, k);
            int l = -1;
            if (slot != null) {
                l = slot.slotNumber;
            }
            if (flag1) {
                l = -999;
            }
            if (this.mc.gameSettings.touchscreen && flag1 && Minecraft.player.inventory.getItemStack().isEmpty()) {
                this.mc.displayGuiScreen(null);
                return;
            }
            if (l != -1) {
                if (this.mc.gameSettings.touchscreen) {
                    if (slot != null && slot.getHasStack()) {
                        this.clickedSlot = slot;
                        draggedStack = ItemStack.field_190927_a;
                        this.isRightMouseClick = mouseButton == 1;
                    } else {
                        this.clickedSlot = null;
                    }
                } else if (!this.dragSplitting) {
                    if (Minecraft.player.inventory.getItemStack().isEmpty()) {
                        if (mouseButton == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
                            this.handleMouseClick(slot, l, mouseButton, ClickType.CLONE);
                        } else {
                            boolean flag2 = l != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
                            ClickType clicktype = ClickType.PICKUP;
                            if (flag2) {
                                this.shiftClickedSlot = slot != null && slot.getHasStack() ? slot.getStack().copy() : ItemStack.field_190927_a;
                                clicktype = ClickType.QUICK_MOVE;
                            } else if (l == -999) {
                                clicktype = ClickType.THROW;
                            }
                            this.handleMouseClick(slot, l, mouseButton, clicktype);
                        }
                        this.ignoreMouseUp = true;
                    } else {
                        this.dragSplitting = true;
                        this.dragSplittingButton = mouseButton;
                        this.dragSplittingSlots.clear();
                        if (mouseButton == 0) {
                            this.dragSplittingLimit = 0;
                        } else if (mouseButton == 1) {
                            this.dragSplittingLimit = 1;
                        } else if (mouseButton == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
                            this.dragSplittingLimit = 2;
                        }
                    }
                }
            }
        }
        this.lastClickSlot = slot;
        this.lastClickTime = i;
        this.lastClickButton = mouseButton;
    }

    protected boolean func_193983_c(int p_193983_1_, int p_193983_2_, int p_193983_3_, int p_193983_4_) {
        return p_193983_1_ < p_193983_3_ || p_193983_2_ < p_193983_4_ || p_193983_1_ >= p_193983_3_ + this.xSize || p_193983_2_ >= p_193983_4_ + this.ySize;
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        Slot slot = this.getSlotAtPosition(mouseX, mouseY);
        ItemStack itemstack = Minecraft.player.inventory.getItemStack();
        if (this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
            if (clickedMouseButton == 0 || clickedMouseButton == 1) {
                if (draggedStack.isEmpty()) {
                    if (slot != this.clickedSlot && !this.clickedSlot.getStack().isEmpty()) {
                        draggedStack = this.clickedSlot.getStack().copy();
                    }
                } else if (draggedStack.getCount2() > 1 && slot != null && Container.canAddItemToSlot(slot, draggedStack, false)) {
                    long i = Minecraft.getSystemTime();
                    if (this.currentDragTargetSlot == slot) {
                        if (i - this.dragItemDropDelay > 500L) {
                            this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, ClickType.PICKUP);
                            this.handleMouseClick(slot, slot.slotNumber, 1, ClickType.PICKUP);
                            this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, ClickType.PICKUP);
                            this.dragItemDropDelay = i + 750L;
                            draggedStack.func_190918_g(1);
                        }
                    } else {
                        this.currentDragTargetSlot = slot;
                        this.dragItemDropDelay = i;
                    }
                }
            }
        } else if (this.dragSplitting && slot != null && !itemstack.isEmpty() && (itemstack.getCount2() > this.dragSplittingSlots.size() || this.dragSplittingLimit == 2) && Container.canAddItemToSlot(slot, itemstack, true) && slot.isItemValid(itemstack) && this.inventorySlots.canDragIntoSlot(slot)) {
            this.dragSplittingSlots.add(slot);
            this.updateDragSplitting();
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        Slot slot = this.getSlotAtPosition(mouseX, mouseY);
        int i = this.guiLeft;
        int j = this.guiTop;
        boolean flag = this.func_193983_c(mouseX, mouseY, i, j);
        int k = -1;
        if (slot != null) {
            k = slot.slotNumber;
        }
        if (flag) {
            k = -999;
        }
        if (this.doubleClick && slot != null && state == 0 && this.inventorySlots.canMergeSlot(ItemStack.field_190927_a, slot)) {
            if (GuiContainer.isShiftKeyDown()) {
                if (!this.shiftClickedSlot.isEmpty()) {
                    for (Slot slot2 : this.inventorySlots.inventorySlots) {
                        if (slot2 == null || !slot2.canTakeStack(Minecraft.player) || !slot2.getHasStack() || slot2.inventory != slot.inventory || !Container.canAddItemToSlot(slot2, this.shiftClickedSlot, true)) continue;
                        this.handleMouseClick(slot2, slot2.slotNumber, state, ClickType.QUICK_MOVE);
                    }
                }
            } else {
                this.handleMouseClick(slot, k, state, ClickType.PICKUP_ALL);
            }
            this.doubleClick = false;
            this.lastClickTime = 0L;
        } else {
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
                    if (draggedStack.isEmpty() && slot != this.clickedSlot) {
                        draggedStack = this.clickedSlot.getStack();
                    }
                    boolean flag2 = Container.canAddItemToSlot(slot, draggedStack, false);
                    if (k != -1 && !draggedStack.isEmpty() && flag2) {
                        this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, state, ClickType.PICKUP);
                        this.handleMouseClick(slot, k, 0, ClickType.PICKUP);
                        if (Minecraft.player.inventory.getItemStack().isEmpty()) {
                            this.returningStack = ItemStack.field_190927_a;
                        } else {
                            this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, state, ClickType.PICKUP);
                            this.touchUpX = mouseX - i;
                            this.touchUpY = mouseY - j;
                            this.returningStackDestSlot = this.clickedSlot;
                            this.returningStack = draggedStack;
                            this.returningStackTime = Minecraft.getSystemTime();
                        }
                    } else if (!draggedStack.isEmpty()) {
                        this.touchUpX = mouseX - i;
                        this.touchUpY = mouseY - j;
                        this.returningStackDestSlot = this.clickedSlot;
                        this.returningStack = draggedStack;
                        this.returningStackTime = Minecraft.getSystemTime();
                    }
                    draggedStack = ItemStack.field_190927_a;
                    this.clickedSlot = null;
                }
            } else if (this.dragSplitting && !this.dragSplittingSlots.isEmpty()) {
                this.handleMouseClick(null, -999, Container.getQuickcraftMask(0, this.dragSplittingLimit), ClickType.QUICK_CRAFT);
                for (Slot slot1 : this.dragSplittingSlots) {
                    this.handleMouseClick(slot1, slot1.slotNumber, Container.getQuickcraftMask(1, this.dragSplittingLimit), ClickType.QUICK_CRAFT);
                }
                this.handleMouseClick(null, -999, Container.getQuickcraftMask(2, this.dragSplittingLimit), ClickType.QUICK_CRAFT);
            } else if (!Minecraft.player.inventory.getItemStack().isEmpty()) {
                if (state == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100) {
                    this.handleMouseClick(slot, k, state, ClickType.CLONE);
                } else {
                    boolean flag1 = k != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
                    boolean bl = flag1;
                    if (flag1) {
                        this.shiftClickedSlot = slot != null && slot.getHasStack() ? slot.getStack().copy() : ItemStack.field_190927_a;
                    }
                    this.handleMouseClick(slot, k, state, flag1 ? ClickType.QUICK_MOVE : ClickType.PICKUP);
                }
            }
        }
        if (Minecraft.player.inventory.getItemStack().isEmpty()) {
            this.lastClickTime = 0L;
        }
        this.dragSplitting = false;
    }

    private boolean isMouseOverSlot(Slot slotIn, int mouseX, int mouseY) {
        return this.isPointInRegion(slotIn.xDisplayPosition, slotIn.yDisplayPosition, 16, 16, mouseX, mouseY);
    }

    protected boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY) {
        int i = this.guiLeft;
        int j = this.guiTop;
        return (pointX -= i) >= rectX - 1 && pointX < rectX + rectWidth + 1 && (pointY -= j) >= rectY - 1 && pointY < rectY + rectHeight + 1;
    }

    public void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type2) {
        ItemStack stack;
        if (this.inventorySlots.windowId == 0 && slotId >= 0 && slotId <= 36 && ProContainer.get.isActived() && ProContainer.get.AutoArmor.bValue && (stack = Minecraft.player.inventory.getStackInSlot(slotId)) != null) {
            boolean armorClickedDEQ;
            boolean armorClickedEQ = 8 - slotId >= 0 && 8 - slotId <= 3 && Minecraft.player.inventory.armorItemInSlot(8 - slotId).getItem() instanceof ItemArmor;
            boolean bl = armorClickedDEQ = !armorClickedEQ && slotId >= 0 && slotId <= 36 && Minecraft.player.inventory.getStackInSlot(slotId).getItem() instanceof ItemArmor;
            if (armorClickedEQ) {
                ProContainer.autoArmorOFF = true;
            }
            if (armorClickedDEQ) {
                ProContainer.autoArmorOFF = false;
            }
            ProContainer.get.timer.reset();
        }
        if (slotIn != null) {
            slotId = slotIn.slotNumber;
        }
        this.mc.playerController.windowClick(this.inventorySlots.windowId, slotId, mouseButton, type2, Minecraft.player);
    }

    public boolean itemOne(int slotIn) {
        if (Minecraft.player.inventoryContainer.getSlot(slotIn) != null && Minecraft.player.inventoryContainer.getSlot(slotIn).getStack() != null) {
            ItemStack itemStack = Minecraft.player.inventoryContainer.getSlot(slotIn).getStack();
            return itemStack.stackSize == 1;
        }
        return false;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == this.mc.gameSettings.keyBindSwapHands.getKeyCode() && !Panic.stop && this.mc.currentScreen != null && this.mc.currentScreen instanceof GuiInventory && ProContainer.get.actived && ProContainer.get.QuickSwap.bValue) {
            int dragSlot = -1;
            for (int i1 = 0; i1 < this.inventorySlots.inventorySlots.size(); ++i1) {
                Slot slot = this.inventorySlots.inventorySlots.get(i1);
                if (!this.isMouseOverSlot(slot, this.mx, this.my) || !slot.canBeHovered()) continue;
                dragSlot = i1;
            }
            if (!(dragSlot == -1 || dragSlot == 45 || Minecraft.player.inventoryContainer.getSlot(dragSlot).getStack().getItem() instanceof ItemAir && Minecraft.player.inventoryContainer.getSlot(45).getStack().getItem() instanceof ItemAir)) {
                if (!Minecraft.player.getHeldItemOffhand().isEmpty()) {
                    this.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, Minecraft.player);
                }
                boolean aired = Minecraft.player.inventoryContainer.getSlot(dragSlot).getStack().getItem() instanceof ItemAir;
                this.mc.playerController.windowClick(0, dragSlot, !aired && dragSlot != -1 && this.itemOne(dragSlot) ? 1 : 0, ClickType.PICKUP, Minecraft.player);
                this.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, Minecraft.player);
            }
        }
        if (keyCode == 1 || keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
            if (Panic.stop) {
                Minecraft.player.closeScreen();
            } else {
                this.colose = true;
                GuiContainer.inter.to = 0.0f;
                inter.setAnim(Panic.stop || !ComfortUi.get.isContainerAnim() ? 0.0f : 1.0f);
                this.mc.setIngameFocus();
                Mouse.setGrabbed(true);
                this.drop = false;
            }
            if (Minecraft.player != null) {
                Minecraft.player.setSneaking(this.mc.gameSettings.keyBindSneak.isKeyDown());
            }
        }
        if (ProContainer.get.actived && ProContainer.get.CtrlRDroper.bValue && Keyboard.isKeyDown(29) && keyCode == 19 && this.canHasDropButton()) {
            if (this.isECCH()) {
                this.loadEC = true;
            } else {
                this.drop = true;
            }
        }
        this.checkHotbarKeys(keyCode);
        if (this.theSlot != null && this.theSlot.getHasStack()) {
            if (keyCode == this.mc.gameSettings.keyBindPickBlock.getKeyCode()) {
                this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, 0, ClickType.CLONE);
            } else if (keyCode == this.mc.gameSettings.keyBindDrop.getKeyCode()) {
                this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, GuiContainer.isCtrlKeyDown() ? 1 : 0, ClickType.THROW);
            }
        }
    }

    protected boolean checkHotbarKeys(int keyCode) {
        if (Minecraft.player.inventory.getItemStack().isEmpty() && this.theSlot != null) {
            for (int i = 0; i < 9; ++i) {
                if (keyCode != this.mc.gameSettings.keyBindsHotbar[i].getKeyCode()) continue;
                this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, i, ClickType.SWAP);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onGuiClosed() {
        this.mx = 0;
        this.my = 0;
        this.colose = false;
        this.drop = false;
        if (!Panic.stop && InvWalk.get.actived && InvWalk.get.MouseMove.bValue) {
            Mouse.setGrabbed(true);
        }
        if (Minecraft.player != null) {
            this.inventorySlots.onContainerClosed(Minecraft.player);
        }
        if (Minecraft.player != null) {
            Minecraft.player.setSneaking(false);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if (!Minecraft.player.isEntityAlive() || Minecraft.player.isDead) {
            Minecraft.player.closeScreen();
        }
    }
}

