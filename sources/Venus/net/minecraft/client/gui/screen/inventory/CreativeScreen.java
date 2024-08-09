/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen.inventory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.client.gui.screen.inventory.CreativeCraftingListener;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.settings.CreativeSettings;
import net.minecraft.client.settings.HotbarSnapshot;
import net.minecraft.client.util.IMutableSearchTree;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.client.util.SearchTreeManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class CreativeScreen
extends DisplayEffectsScreen<CreativeContainer> {
    private static final ResourceLocation CREATIVE_INVENTORY_TABS = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
    private static final Inventory TMP_INVENTORY = new Inventory(45);
    private static final ITextComponent field_243345_D = new TranslationTextComponent("inventory.binSlot");
    private static int selectedTabIndex = ItemGroup.BUILDING_BLOCKS.getIndex();
    private float currentScroll;
    private boolean isScrolling;
    private TextFieldWidget searchField;
    @Nullable
    private List<Slot> originalSlots;
    @Nullable
    private Slot destroyItemSlot;
    private CreativeCraftingListener listener;
    private boolean field_195377_F;
    private boolean field_199506_G;
    private final Map<ResourceLocation, ITag<Item>> tagSearchResults = Maps.newTreeMap();

    public CreativeScreen(PlayerEntity playerEntity) {
        super(new CreativeContainer(playerEntity), playerEntity.inventory, StringTextComponent.EMPTY);
        playerEntity.openContainer = this.container;
        this.passEvents = true;
        this.ySize = 136;
        this.xSize = 195;
    }

    @Override
    public void tick() {
        if (!this.minecraft.playerController.isInCreativeMode()) {
            this.minecraft.displayGuiScreen(new InventoryScreen(this.minecraft.player));
        } else if (this.searchField != null) {
            this.searchField.tick();
        }
    }

    @Override
    protected void handleMouseClick(@Nullable Slot slot, int n, int n2, ClickType clickType) {
        if (this.hasTmpInventory(slot)) {
            this.searchField.setCursorPositionEnd();
            this.searchField.setSelectionPos(0);
        }
        boolean bl = clickType == ClickType.QUICK_MOVE;
        ClickType clickType2 = clickType = n == -999 && clickType == ClickType.PICKUP ? ClickType.THROW : clickType;
        if (slot == null && selectedTabIndex != ItemGroup.INVENTORY.getIndex() && clickType != ClickType.QUICK_CRAFT) {
            PlayerInventory playerInventory = this.minecraft.player.inventory;
            if (!playerInventory.getItemStack().isEmpty() && this.field_199506_G) {
                if (n2 == 0) {
                    this.minecraft.player.dropItem(playerInventory.getItemStack(), false);
                    this.minecraft.playerController.sendPacketDropItem(playerInventory.getItemStack());
                    playerInventory.setItemStack(ItemStack.EMPTY);
                }
                if (n2 == 1) {
                    ItemStack itemStack = playerInventory.getItemStack().split(1);
                    this.minecraft.player.dropItem(itemStack, false);
                    this.minecraft.playerController.sendPacketDropItem(itemStack);
                }
            }
        } else {
            if (slot != null && !slot.canTakeStack(this.minecraft.player)) {
                return;
            }
            if (slot == this.destroyItemSlot && bl) {
                for (int i = 0; i < this.minecraft.player.container.getInventory().size(); ++i) {
                    this.minecraft.playerController.sendSlotPacket(ItemStack.EMPTY, i);
                }
            } else if (selectedTabIndex == ItemGroup.INVENTORY.getIndex()) {
                if (slot == this.destroyItemSlot) {
                    this.minecraft.player.inventory.setItemStack(ItemStack.EMPTY);
                } else if (clickType == ClickType.THROW && slot != null && slot.getHasStack()) {
                    ItemStack itemStack = slot.decrStackSize(n2 == 0 ? 1 : slot.getStack().getMaxStackSize());
                    ItemStack itemStack2 = slot.getStack();
                    this.minecraft.player.dropItem(itemStack, false);
                    this.minecraft.playerController.sendPacketDropItem(itemStack);
                    this.minecraft.playerController.sendSlotPacket(itemStack2, ((CreativeSlot)slot).slot.slotNumber);
                } else if (clickType == ClickType.THROW && !this.minecraft.player.inventory.getItemStack().isEmpty()) {
                    this.minecraft.player.dropItem(this.minecraft.player.inventory.getItemStack(), false);
                    this.minecraft.playerController.sendPacketDropItem(this.minecraft.player.inventory.getItemStack());
                    this.minecraft.player.inventory.setItemStack(ItemStack.EMPTY);
                } else {
                    this.minecraft.player.container.slotClick(slot == null ? n : ((CreativeSlot)slot).slot.slotNumber, n2, clickType, this.minecraft.player);
                    this.minecraft.player.container.detectAndSendChanges();
                }
            } else if (clickType != ClickType.QUICK_CRAFT && slot.inventory == TMP_INVENTORY) {
                PlayerInventory playerInventory = this.minecraft.player.inventory;
                ItemStack itemStack = playerInventory.getItemStack();
                ItemStack itemStack3 = slot.getStack();
                if (clickType == ClickType.SWAP) {
                    if (!itemStack3.isEmpty()) {
                        ItemStack itemStack4 = itemStack3.copy();
                        itemStack4.setCount(itemStack4.getMaxStackSize());
                        this.minecraft.player.inventory.setInventorySlotContents(n2, itemStack4);
                        this.minecraft.player.container.detectAndSendChanges();
                    }
                    return;
                }
                if (clickType == ClickType.CLONE) {
                    if (playerInventory.getItemStack().isEmpty() && slot.getHasStack()) {
                        ItemStack itemStack5 = slot.getStack().copy();
                        itemStack5.setCount(itemStack5.getMaxStackSize());
                        playerInventory.setItemStack(itemStack5);
                    }
                    return;
                }
                if (clickType == ClickType.THROW) {
                    if (!itemStack3.isEmpty()) {
                        ItemStack itemStack6 = itemStack3.copy();
                        itemStack6.setCount(n2 == 0 ? 1 : itemStack6.getMaxStackSize());
                        this.minecraft.player.dropItem(itemStack6, false);
                        this.minecraft.playerController.sendPacketDropItem(itemStack6);
                    }
                    return;
                }
                if (!itemStack.isEmpty() && !itemStack3.isEmpty() && itemStack.isItemEqual(itemStack3) && ItemStack.areItemStackTagsEqual(itemStack, itemStack3)) {
                    if (n2 == 0) {
                        if (bl) {
                            itemStack.setCount(itemStack.getMaxStackSize());
                        } else if (itemStack.getCount() < itemStack.getMaxStackSize()) {
                            itemStack.grow(1);
                        }
                    } else {
                        itemStack.shrink(1);
                    }
                } else if (!itemStack3.isEmpty() && itemStack.isEmpty()) {
                    playerInventory.setItemStack(itemStack3.copy());
                    itemStack = playerInventory.getItemStack();
                    if (bl) {
                        itemStack.setCount(itemStack.getMaxStackSize());
                    }
                } else if (n2 == 0) {
                    playerInventory.setItemStack(ItemStack.EMPTY);
                } else {
                    playerInventory.getItemStack().shrink(1);
                }
            } else if (this.container != null) {
                ItemStack itemStack = slot == null ? ItemStack.EMPTY : ((CreativeContainer)this.container).getSlot(slot.slotNumber).getStack();
                ((CreativeContainer)this.container).slotClick(slot == null ? n : slot.slotNumber, n2, clickType, this.minecraft.player);
                if (Container.getDragEvent(n2) == 2) {
                    for (int i = 0; i < 9; ++i) {
                        this.minecraft.playerController.sendSlotPacket(((CreativeContainer)this.container).getSlot(45 + i).getStack(), 36 + i);
                    }
                } else if (slot != null) {
                    ItemStack itemStack7 = ((CreativeContainer)this.container).getSlot(slot.slotNumber).getStack();
                    this.minecraft.playerController.sendSlotPacket(itemStack7, slot.slotNumber - ((CreativeContainer)this.container).inventorySlots.size() + 9 + 36);
                    int n3 = 45 + n2;
                    if (clickType == ClickType.SWAP) {
                        this.minecraft.playerController.sendSlotPacket(itemStack, n3 - ((CreativeContainer)this.container).inventorySlots.size() + 9 + 36);
                    } else if (clickType == ClickType.THROW && !itemStack.isEmpty()) {
                        ItemStack itemStack8 = itemStack.copy();
                        itemStack8.setCount(n2 == 0 ? 1 : itemStack8.getMaxStackSize());
                        this.minecraft.player.dropItem(itemStack8, false);
                        this.minecraft.playerController.sendPacketDropItem(itemStack8);
                    }
                    this.minecraft.player.container.detectAndSendChanges();
                }
            }
        }
    }

    private boolean hasTmpInventory(@Nullable Slot slot) {
        return slot != null && slot.inventory == TMP_INVENTORY;
    }

    @Override
    protected void updateActivePotionEffects() {
        int n = this.guiLeft;
        super.updateActivePotionEffects();
        if (this.searchField != null && this.guiLeft != n) {
            this.searchField.setX(this.guiLeft + 82);
        }
    }

    @Override
    protected void init() {
        if (this.minecraft.playerController.isInCreativeMode()) {
            super.init();
            this.minecraft.keyboardListener.enableRepeatEvents(false);
            this.searchField = new TextFieldWidget(this.font, this.guiLeft + 82, this.guiTop + 6, 80, 9, new TranslationTextComponent("itemGroup.search"));
            this.searchField.setMaxStringLength(50);
            this.searchField.setEnableBackgroundDrawing(true);
            this.searchField.setVisible(true);
            this.searchField.setTextColor(0xFFFFFF);
            this.children.add(this.searchField);
            int n = selectedTabIndex;
            selectedTabIndex = -1;
            this.setCurrentCreativeTab(ItemGroup.GROUPS[n]);
            this.minecraft.player.container.removeListener(this.listener);
            this.listener = new CreativeCraftingListener(this.minecraft);
            this.minecraft.player.container.addListener(this.listener);
        } else {
            this.minecraft.displayGuiScreen(new InventoryScreen(this.minecraft.player));
        }
    }

    @Override
    public void resize(Minecraft minecraft, int n, int n2) {
        String string = this.searchField.getText();
        this.init(minecraft, n, n2);
        this.searchField.setText(string);
        if (!this.searchField.getText().isEmpty()) {
            this.updateCreativeSearch();
        }
    }

    @Override
    public void onClose() {
        super.onClose();
        if (this.minecraft.player != null && this.minecraft.player.inventory != null) {
            this.minecraft.player.container.removeListener(this.listener);
        }
        this.minecraft.keyboardListener.enableRepeatEvents(true);
    }

    @Override
    public boolean charTyped(char c, int n) {
        if (this.field_195377_F) {
            return true;
        }
        if (selectedTabIndex != ItemGroup.SEARCH.getIndex()) {
            return true;
        }
        String string = this.searchField.getText();
        if (this.searchField.charTyped(c, n)) {
            if (!Objects.equals(string, this.searchField.getText())) {
                this.updateCreativeSearch();
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        this.field_195377_F = false;
        if (selectedTabIndex != ItemGroup.SEARCH.getIndex()) {
            if (this.minecraft.gameSettings.keyBindChat.matchesKey(n, n2)) {
                this.field_195377_F = true;
                this.setCurrentCreativeTab(ItemGroup.SEARCH);
                return false;
            }
            return super.keyPressed(n, n2, n3);
        }
        boolean bl = !this.hasTmpInventory(this.hoveredSlot) || this.hoveredSlot.getHasStack();
        boolean bl2 = InputMappings.getInputByCode(n, n2).func_241552_e_().isPresent();
        if (bl && bl2 && this.itemStackMoved(n, n2)) {
            this.field_195377_F = true;
            return false;
        }
        String string = this.searchField.getText();
        if (this.searchField.keyPressed(n, n2, n3)) {
            if (!Objects.equals(string, this.searchField.getText())) {
                this.updateCreativeSearch();
            }
            return false;
        }
        return this.searchField.isFocused() && this.searchField.getVisible() && n != 256 ? true : super.keyPressed(n, n2, n3);
    }

    @Override
    public boolean keyReleased(int n, int n2, int n3) {
        this.field_195377_F = false;
        return super.keyReleased(n, n2, n3);
    }

    private void updateCreativeSearch() {
        ((CreativeContainer)this.container).itemList.clear();
        this.tagSearchResults.clear();
        String string = this.searchField.getText();
        if (string.isEmpty()) {
            for (Item item : Registry.ITEM) {
                item.fillItemGroup(ItemGroup.SEARCH, ((CreativeContainer)this.container).itemList);
            }
        } else {
            IMutableSearchTree<ItemStack> iMutableSearchTree;
            if (string.startsWith("#")) {
                string = string.substring(1);
                iMutableSearchTree = this.minecraft.getSearchTree(SearchTreeManager.TAGS);
                this.searchTags(string);
            } else {
                iMutableSearchTree = this.minecraft.getSearchTree(SearchTreeManager.ITEMS);
            }
            ((CreativeContainer)this.container).itemList.addAll(iMutableSearchTree.search(string.toLowerCase(Locale.ROOT)));
        }
        this.currentScroll = 0.0f;
        ((CreativeContainer)this.container).scrollTo(0.0f);
    }

    private void searchTags(String string) {
        Object object;
        Predicate<ResourceLocation> predicate;
        int n = string.indexOf(58);
        if (n == -1) {
            predicate = arg_0 -> CreativeScreen.lambda$searchTags$0(string, arg_0);
        } else {
            object = string.substring(0, n).trim();
            String string2 = string.substring(n + 1).trim();
            predicate = arg_0 -> CreativeScreen.lambda$searchTags$1((String)object, string2, arg_0);
        }
        object = ItemTags.getCollection();
        object.getRegisteredTags().stream().filter(predicate).forEach(arg_0 -> this.lambda$searchTags$2((ITagCollection)object, arg_0));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int n, int n2) {
        ItemGroup itemGroup = ItemGroup.GROUPS[selectedTabIndex];
        if (itemGroup.drawInForegroundOfTab()) {
            RenderSystem.disableBlend();
            this.font.func_243248_b(matrixStack, itemGroup.getGroupName(), 8.0f, 6.0f, 0x404040);
        }
    }

    @Override
    public boolean mouseClicked(double d, double d2, int n) {
        if (n == 0) {
            double d3 = d - (double)this.guiLeft;
            double d4 = d2 - (double)this.guiTop;
            for (ItemGroup itemGroup : ItemGroup.GROUPS) {
                if (!this.isMouseOverGroup(itemGroup, d3, d4)) continue;
                return false;
            }
            if (selectedTabIndex != ItemGroup.INVENTORY.getIndex() && this.func_195376_a(d, d2)) {
                this.isScrolling = this.needsScrollBars();
                return false;
            }
        }
        return super.mouseClicked(d, d2, n);
    }

    @Override
    public boolean mouseReleased(double d, double d2, int n) {
        if (n == 0) {
            double d3 = d - (double)this.guiLeft;
            double d4 = d2 - (double)this.guiTop;
            this.isScrolling = false;
            for (ItemGroup itemGroup : ItemGroup.GROUPS) {
                if (!this.isMouseOverGroup(itemGroup, d3, d4)) continue;
                this.setCurrentCreativeTab(itemGroup);
                return false;
            }
        }
        return super.mouseReleased(d, d2, n);
    }

    private boolean needsScrollBars() {
        return selectedTabIndex != ItemGroup.INVENTORY.getIndex() && ItemGroup.GROUPS[selectedTabIndex].hasScrollbar() && ((CreativeContainer)this.container).canScroll();
    }

    private void setCurrentCreativeTab(ItemGroup itemGroup) {
        Object object;
        int n;
        int n2;
        Object object2;
        int n3 = selectedTabIndex;
        selectedTabIndex = itemGroup.getIndex();
        this.dragSplittingSlots.clear();
        ((CreativeContainer)this.container).itemList.clear();
        if (itemGroup == ItemGroup.HOTBAR) {
            object2 = this.minecraft.getCreativeSettings();
            for (n2 = 0; n2 < 9; ++n2) {
                HotbarSnapshot hotbarSnapshot = ((CreativeSettings)object2).getHotbarSnapshot(n2);
                if (hotbarSnapshot.isEmpty()) {
                    for (n = 0; n < 9; ++n) {
                        if (n == n2) {
                            object = new ItemStack(Items.PAPER);
                            ((ItemStack)object).getOrCreateChildTag("CustomCreativeLock");
                            ITextComponent iTextComponent = this.minecraft.gameSettings.keyBindsHotbar[n2].func_238171_j_();
                            ITextComponent iTextComponent2 = this.minecraft.gameSettings.keyBindSaveToolbar.func_238171_j_();
                            ((ItemStack)object).setDisplayName(new TranslationTextComponent("inventory.hotbarInfo", iTextComponent2, iTextComponent));
                            ((CreativeContainer)this.container).itemList.add((ItemStack)object);
                            continue;
                        }
                        ((CreativeContainer)this.container).itemList.add(ItemStack.EMPTY);
                    }
                    continue;
                }
                ((CreativeContainer)this.container).itemList.addAll(hotbarSnapshot);
            }
        } else if (itemGroup != ItemGroup.SEARCH) {
            itemGroup.fill(((CreativeContainer)this.container).itemList);
        }
        if (itemGroup == ItemGroup.INVENTORY) {
            object2 = this.minecraft.player.container;
            if (this.originalSlots == null) {
                this.originalSlots = ImmutableList.copyOf(((CreativeContainer)this.container).inventorySlots);
            }
            ((CreativeContainer)this.container).inventorySlots.clear();
            for (n2 = 0; n2 < ((Container)object2).inventorySlots.size(); ++n2) {
                int n4;
                if (n2 >= 5 && n2 < 9) {
                    int n5 = n2 - 5;
                    var8_12 = n5 / 2;
                    var9_14 = n5 % 2;
                    n4 = 54 + var8_12 * 54;
                    n = 6 + var9_14 * 27;
                } else if (n2 >= 0 && n2 < 5) {
                    n4 = -2000;
                    n = -2000;
                } else if (n2 == 45) {
                    n4 = 35;
                    n = 20;
                } else {
                    int n6 = n2 - 9;
                    var8_12 = n6 % 9;
                    var9_14 = n6 / 9;
                    n4 = 9 + var8_12 * 18;
                    n = n2 >= 36 ? 112 : 54 + var9_14 * 18;
                }
                object = new CreativeSlot(((Container)object2).inventorySlots.get(n2), n2, n4, n);
                ((CreativeContainer)this.container).inventorySlots.add(object);
            }
            this.destroyItemSlot = new Slot(TMP_INVENTORY, 0, 173, 112);
            ((CreativeContainer)this.container).inventorySlots.add(this.destroyItemSlot);
        } else if (n3 == ItemGroup.INVENTORY.getIndex()) {
            ((CreativeContainer)this.container).inventorySlots.clear();
            ((CreativeContainer)this.container).inventorySlots.addAll(this.originalSlots);
            this.originalSlots = null;
        }
        if (this.searchField != null) {
            if (itemGroup == ItemGroup.SEARCH) {
                this.searchField.setVisible(false);
                this.searchField.setCanLoseFocus(true);
                this.searchField.setFocused2(false);
                if (n3 != itemGroup.getIndex()) {
                    this.searchField.setText("");
                }
                this.updateCreativeSearch();
            } else {
                this.searchField.setVisible(true);
                this.searchField.setCanLoseFocus(false);
                this.searchField.setFocused2(true);
                this.searchField.setText("");
            }
        }
        this.currentScroll = 0.0f;
        ((CreativeContainer)this.container).scrollTo(0.0f);
    }

    @Override
    public boolean mouseScrolled(double d, double d2, double d3) {
        if (!this.needsScrollBars()) {
            return true;
        }
        int n = (((CreativeContainer)this.container).itemList.size() + 9 - 1) / 9 - 5;
        this.currentScroll = (float)((double)this.currentScroll - d3 / (double)n);
        this.currentScroll = MathHelper.clamp(this.currentScroll, 0.0f, 1.0f);
        ((CreativeContainer)this.container).scrollTo(this.currentScroll);
        return false;
    }

    @Override
    protected boolean hasClickedOutside(double d, double d2, int n, int n2, int n3) {
        boolean bl = d < (double)n || d2 < (double)n2 || d >= (double)(n + this.xSize) || d2 >= (double)(n2 + this.ySize);
        this.field_199506_G = bl && !this.isMouseOverGroup(ItemGroup.GROUPS[selectedTabIndex], d, d2);
        return this.field_199506_G;
    }

    protected boolean func_195376_a(double d, double d2) {
        int n = this.guiLeft;
        int n2 = this.guiTop;
        int n3 = n + 175;
        int n4 = n2 + 18;
        int n5 = n3 + 14;
        int n6 = n4 + 112;
        return d >= (double)n3 && d2 >= (double)n4 && d < (double)n5 && d2 < (double)n6;
    }

    @Override
    public boolean mouseDragged(double d, double d2, int n, double d3, double d4) {
        if (this.isScrolling) {
            int n2 = this.guiTop + 18;
            int n3 = n2 + 112;
            this.currentScroll = ((float)d2 - (float)n2 - 7.5f) / ((float)(n3 - n2) - 15.0f);
            this.currentScroll = MathHelper.clamp(this.currentScroll, 0.0f, 1.0f);
            ((CreativeContainer)this.container).scrollTo(this.currentScroll);
            return false;
        }
        return super.mouseDragged(d, d2, n, d3, d4);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, n, n2, f);
        for (ItemGroup itemGroup : ItemGroup.GROUPS) {
            if (this.func_238809_a_(matrixStack, itemGroup, n, n2)) break;
        }
        if (this.destroyItemSlot != null && selectedTabIndex == ItemGroup.INVENTORY.getIndex() && this.isPointInRegion(this.destroyItemSlot.xPos, this.destroyItemSlot.yPos, 16, 16, n, n2)) {
            this.renderTooltip(matrixStack, field_243345_D, n, n2);
        }
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.renderHoveredTooltip(matrixStack, n, n2);
    }

    @Override
    protected void renderTooltip(MatrixStack matrixStack, ItemStack itemStack, int n, int n2) {
        if (selectedTabIndex == ItemGroup.SEARCH.getIndex()) {
            Map<Enchantment, Integer> map;
            List<ITextComponent> list = itemStack.getTooltip(this.minecraft.player, this.minecraft.gameSettings.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL);
            ArrayList<ITextComponent> arrayList = Lists.newArrayList(list);
            Item item = itemStack.getItem();
            ItemGroup itemGroup = item.getGroup();
            if (itemGroup == null && item == Items.ENCHANTED_BOOK && (map = EnchantmentHelper.getEnchantments(itemStack)).size() == 1) {
                Enchantment enchantment = map.keySet().iterator().next();
                for (ItemGroup itemGroup2 : ItemGroup.GROUPS) {
                    if (!itemGroup2.hasRelevantEnchantmentType(enchantment.type)) continue;
                    itemGroup = itemGroup2;
                    break;
                }
            }
            this.tagSearchResults.forEach((arg_0, arg_1) -> CreativeScreen.lambda$renderTooltip$3(item, arrayList, arg_0, arg_1));
            if (itemGroup != null) {
                arrayList.add(1, itemGroup.getGroupName().deepCopy().mergeStyle(TextFormatting.BLUE));
            }
            this.func_243308_b(matrixStack, arrayList, n, n2);
        } else {
            super.renderTooltip(matrixStack, itemStack, n, n2);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float f, int n, int n2) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        ItemGroup itemGroup = ItemGroup.GROUPS[selectedTabIndex];
        for (ItemGroup itemGroup2 : ItemGroup.GROUPS) {
            this.minecraft.getTextureManager().bindTexture(CREATIVE_INVENTORY_TABS);
            if (itemGroup2.getIndex() == selectedTabIndex) continue;
            this.func_238808_a_(matrixStack, itemGroup2);
        }
        this.minecraft.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/creative_inventory/tab_" + itemGroup.getBackgroundImageName()));
        this.blit(matrixStack, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        this.searchField.render(matrixStack, n, n2, f);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        int n3 = this.guiLeft + 175;
        int n4 = this.guiTop + 18;
        int n5 = n4 + 112;
        this.minecraft.getTextureManager().bindTexture(CREATIVE_INVENTORY_TABS);
        if (itemGroup.hasScrollbar()) {
            this.blit(matrixStack, n3, n4 + (int)((float)(n5 - n4 - 17) * this.currentScroll), 232 + (this.needsScrollBars() ? 0 : 12), 0, 12, 15);
        }
        this.func_238808_a_(matrixStack, itemGroup);
        if (itemGroup == ItemGroup.INVENTORY) {
            InventoryScreen.drawEntityOnScreen(this.guiLeft + 88, this.guiTop + 45, 20, this.guiLeft + 88 - n, this.guiTop + 45 - 30 - n2, this.minecraft.player);
        }
    }

    protected boolean isMouseOverGroup(ItemGroup itemGroup, double d, double d2) {
        int n = itemGroup.getColumn();
        int n2 = 28 * n;
        int n3 = 0;
        if (itemGroup.isAlignedRight()) {
            n2 = this.xSize - 28 * (6 - n) + 2;
        } else if (n > 0) {
            n2 += n;
        }
        n3 = itemGroup.isOnTopRow() ? (n3 -= 32) : (n3 += this.ySize);
        return d >= (double)n2 && d <= (double)(n2 + 28) && d2 >= (double)n3 && d2 <= (double)(n3 + 32);
    }

    protected boolean func_238809_a_(MatrixStack matrixStack, ItemGroup itemGroup, int n, int n2) {
        int n3 = itemGroup.getColumn();
        int n4 = 28 * n3;
        int n5 = 0;
        if (itemGroup.isAlignedRight()) {
            n4 = this.xSize - 28 * (6 - n3) + 2;
        } else if (n3 > 0) {
            n4 += n3;
        }
        n5 = itemGroup.isOnTopRow() ? (n5 -= 32) : (n5 += this.ySize);
        if (this.isPointInRegion(n4 + 3, n5 + 3, 23, 27, n, n2)) {
            this.renderTooltip(matrixStack, itemGroup.getGroupName(), n, n2);
            return false;
        }
        return true;
    }

    protected void func_238808_a_(MatrixStack matrixStack, ItemGroup itemGroup) {
        boolean bl = itemGroup.getIndex() == selectedTabIndex;
        boolean bl2 = itemGroup.isOnTopRow();
        int n = itemGroup.getColumn();
        int n2 = n * 28;
        int n3 = 0;
        int n4 = this.guiLeft + 28 * n;
        int n5 = this.guiTop;
        int n6 = 32;
        if (bl) {
            n3 += 32;
        }
        if (itemGroup.isAlignedRight()) {
            n4 = this.guiLeft + this.xSize - 28 * (6 - n);
        } else if (n > 0) {
            n4 += n;
        }
        if (bl2) {
            n5 -= 28;
        } else {
            n3 += 64;
            n5 += this.ySize - 4;
        }
        this.blit(matrixStack, n4, n5, n2, n3, 28, 32);
        this.itemRenderer.zLevel = 100.0f;
        n5 = n5 + 8 + (bl2 ? 1 : -1);
        RenderSystem.enableRescaleNormal();
        ItemStack itemStack = itemGroup.getIcon();
        this.itemRenderer.renderItemAndEffectIntoGUI(itemStack, n4 += 6, n5);
        this.itemRenderer.renderItemOverlays(this.font, itemStack, n4, n5);
        this.itemRenderer.zLevel = 0.0f;
    }

    public int getSelectedTabIndex() {
        return selectedTabIndex;
    }

    public static void handleHotbarSnapshots(Minecraft minecraft, int n, boolean bl, boolean bl2) {
        ClientPlayerEntity clientPlayerEntity = minecraft.player;
        CreativeSettings creativeSettings = minecraft.getCreativeSettings();
        HotbarSnapshot hotbarSnapshot = creativeSettings.getHotbarSnapshot(n);
        if (bl) {
            for (int i = 0; i < PlayerInventory.getHotbarSize(); ++i) {
                ItemStack itemStack = ((ItemStack)hotbarSnapshot.get(i)).copy();
                clientPlayerEntity.inventory.setInventorySlotContents(i, itemStack);
                minecraft.playerController.sendSlotPacket(itemStack, 36 + i);
            }
            clientPlayerEntity.container.detectAndSendChanges();
        } else if (bl2) {
            for (int i = 0; i < PlayerInventory.getHotbarSize(); ++i) {
                hotbarSnapshot.set(i, clientPlayerEntity.inventory.getStackInSlot(i).copy());
            }
            ITextComponent iTextComponent = minecraft.gameSettings.keyBindsHotbar[n].func_238171_j_();
            ITextComponent iTextComponent2 = minecraft.gameSettings.keyBindLoadToolbar.func_238171_j_();
            minecraft.ingameGUI.setOverlayMessage(new TranslationTextComponent("inventory.hotbarSaved", iTextComponent2, iTextComponent), true);
            creativeSettings.save();
        }
    }

    private static void lambda$renderTooltip$3(Item item, List list, ResourceLocation resourceLocation, ITag iTag) {
        if (iTag.contains(item)) {
            list.add(1, new StringTextComponent("#" + resourceLocation).mergeStyle(TextFormatting.DARK_PURPLE));
        }
    }

    private void lambda$searchTags$2(ITagCollection iTagCollection, ResourceLocation resourceLocation) {
        ITag iTag = this.tagSearchResults.put(resourceLocation, iTagCollection.get(resourceLocation));
    }

    private static boolean lambda$searchTags$1(String string, String string2, ResourceLocation resourceLocation) {
        return resourceLocation.getNamespace().contains(string) && resourceLocation.getPath().contains(string2);
    }

    private static boolean lambda$searchTags$0(String string, ResourceLocation resourceLocation) {
        return resourceLocation.getPath().contains(string);
    }

    public static class CreativeContainer
    extends Container {
        public final NonNullList<ItemStack> itemList = NonNullList.create();

        public CreativeContainer(PlayerEntity playerEntity) {
            super(null, 0);
            int n;
            PlayerInventory playerInventory = playerEntity.inventory;
            for (n = 0; n < 5; ++n) {
                for (int i = 0; i < 9; ++i) {
                    this.addSlot(new LockedSlot(TMP_INVENTORY, n * 9 + i, 9 + i * 18, 18 + n * 18));
                }
            }
            for (n = 0; n < 9; ++n) {
                this.addSlot(new Slot(playerInventory, n, 9 + n * 18, 112));
            }
            this.scrollTo(0.0f);
        }

        @Override
        public boolean canInteractWith(PlayerEntity playerEntity) {
            return false;
        }

        public void scrollTo(float f) {
            int n = (this.itemList.size() + 9 - 1) / 9 - 5;
            int n2 = (int)((double)(f * (float)n) + 0.5);
            if (n2 < 0) {
                n2 = 0;
            }
            for (int i = 0; i < 5; ++i) {
                for (int j = 0; j < 9; ++j) {
                    int n3 = j + (i + n2) * 9;
                    if (n3 >= 0 && n3 < this.itemList.size()) {
                        TMP_INVENTORY.setInventorySlotContents(j + i * 9, this.itemList.get(n3));
                        continue;
                    }
                    TMP_INVENTORY.setInventorySlotContents(j + i * 9, ItemStack.EMPTY);
                }
            }
        }

        public boolean canScroll() {
            return this.itemList.size() > 45;
        }

        @Override
        public ItemStack transferStackInSlot(PlayerEntity playerEntity, int n) {
            Slot slot;
            if (n >= this.inventorySlots.size() - 9 && n < this.inventorySlots.size() && (slot = (Slot)this.inventorySlots.get(n)) != null && slot.getHasStack()) {
                slot.putStack(ItemStack.EMPTY);
            }
            return ItemStack.EMPTY;
        }

        @Override
        public boolean canMergeSlot(ItemStack itemStack, Slot slot) {
            return slot.inventory != TMP_INVENTORY;
        }

        @Override
        public boolean canDragIntoSlot(Slot slot) {
            return slot.inventory != TMP_INVENTORY;
        }
    }

    static class CreativeSlot
    extends Slot {
        private final Slot slot;

        public CreativeSlot(Slot slot, int n, int n2, int n3) {
            super(slot.inventory, n, n2, n3);
            this.slot = slot;
        }

        @Override
        public ItemStack onTake(PlayerEntity playerEntity, ItemStack itemStack) {
            return this.slot.onTake(playerEntity, itemStack);
        }

        @Override
        public boolean isItemValid(ItemStack itemStack) {
            return this.slot.isItemValid(itemStack);
        }

        @Override
        public ItemStack getStack() {
            return this.slot.getStack();
        }

        @Override
        public boolean getHasStack() {
            return this.slot.getHasStack();
        }

        @Override
        public void putStack(ItemStack itemStack) {
            this.slot.putStack(itemStack);
        }

        @Override
        public void onSlotChanged() {
            this.slot.onSlotChanged();
        }

        @Override
        public int getSlotStackLimit() {
            return this.slot.getSlotStackLimit();
        }

        @Override
        public int getItemStackLimit(ItemStack itemStack) {
            return this.slot.getItemStackLimit(itemStack);
        }

        @Override
        @Nullable
        public Pair<ResourceLocation, ResourceLocation> getBackground() {
            return this.slot.getBackground();
        }

        @Override
        public ItemStack decrStackSize(int n) {
            return this.slot.decrStackSize(n);
        }

        @Override
        public boolean isEnabled() {
            return this.slot.isEnabled();
        }

        @Override
        public boolean canTakeStack(PlayerEntity playerEntity) {
            return this.slot.canTakeStack(playerEntity);
        }
    }

    static class LockedSlot
    extends Slot {
        public LockedSlot(IInventory iInventory, int n, int n2, int n3) {
            super(iInventory, n, n2, n3);
        }

        @Override
        public boolean canTakeStack(PlayerEntity playerEntity) {
            if (super.canTakeStack(playerEntity) && this.getHasStack()) {
                return this.getStack().getChildTag("CustomCreativeLock") == null;
            }
            return !this.getHasStack();
        }
    }
}

