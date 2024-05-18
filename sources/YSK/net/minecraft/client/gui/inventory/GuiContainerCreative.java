package net.minecraft.client.gui.inventory;

import net.minecraft.creativetab.*;
import net.minecraft.client.settings.*;
import java.io.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.client.resources.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.achievement.*;
import org.lwjgl.input.*;
import net.minecraft.enchantment.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.inventory.*;

public class GuiContainerCreative extends InventoryEffectRenderer
{
    private boolean isScrolling;
    private boolean wasClicking;
    private Slot field_147064_C;
    private float currentScroll;
    private List<Slot> field_147063_B;
    private static InventoryBasic field_147060_v;
    private boolean field_147057_D;
    private CreativeCrafting field_147059_E;
    private GuiTextField searchField;
    private static final String[] I;
    private static int selectedTabIndex;
    private static final ResourceLocation creativeInventoryTabs;
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (GuiContainerCreative.selectedTabIndex != CreativeTabs.tabAllSearch.getTabIndex()) {
            if (GameSettings.isKeyDown(this.mc.gameSettings.keyBindChat)) {
                this.setCurrentCreativeTab(CreativeTabs.tabAllSearch);
                "".length();
                if (4 < 1) {
                    throw null;
                }
            }
            else {
                super.keyTyped(c, n);
                "".length();
                if (0 < -1) {
                    throw null;
                }
            }
        }
        else {
            if (this.field_147057_D) {
                this.field_147057_D = ("".length() != 0);
                this.searchField.setText(GuiContainerCreative.I["  ".length()]);
            }
            if (!this.checkHotbarKeys(n)) {
                if (this.searchField.textboxKeyTyped(c, n)) {
                    this.updateCreativeSearch();
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
                else {
                    super.keyTyped(c, n);
                }
            }
        }
    }
    
    private void updateCreativeSearch() {
        final ContainerCreative containerCreative = (ContainerCreative)this.inventorySlots;
        containerCreative.itemList.clear();
        final Iterator<Item> iterator = Item.itemRegistry.iterator();
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Item item = iterator.next();
            if (item != null && item.getCreativeTab() != null) {
                item.getSubItems(item, null, containerCreative.itemList);
            }
        }
        final Enchantment[] enchantmentsBookList;
        final int length = (enchantmentsBookList = Enchantment.enchantmentsBookList).length;
        int i = "".length();
        "".length();
        if (3 == 1) {
            throw null;
        }
        while (i < length) {
            final Enchantment enchantment = enchantmentsBookList[i];
            if (enchantment != null && enchantment.type != null) {
                Items.enchanted_book.getAll(enchantment, containerCreative.itemList);
            }
            ++i;
        }
        final Iterator<ItemStack> iterator2 = containerCreative.itemList.iterator();
        final String lowerCase = this.searchField.getText().toLowerCase();
        "".length();
        if (4 < 4) {
            throw null;
        }
        while (iterator2.hasNext()) {
            final ItemStack itemStack = iterator2.next();
            int n = "".length();
            final Iterator<String> iterator3 = itemStack.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips).iterator();
            "".length();
            if (1 < 0) {
                throw null;
            }
            while (iterator3.hasNext()) {
                if (EnumChatFormatting.getTextWithoutFormattingCodes(iterator3.next()).toLowerCase().contains(lowerCase)) {
                    n = " ".length();
                    "".length();
                    if (2 <= 1) {
                        throw null;
                    }
                    break;
                }
            }
            if (n != 0) {
                continue;
            }
            iterator2.remove();
        }
        containerCreative.scrollTo(this.currentScroll = 0.0f);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        final CreativeTabs creativeTabs = CreativeTabs.creativeTabArray[GuiContainerCreative.selectedTabIndex];
        if (creativeTabs.drawInForegroundOfTab()) {
            GlStateManager.disableBlend();
            this.fontRendererObj.drawString(I18n.format(creativeTabs.getTranslatedTabLabel(), new Object["".length()]), 0xA6 ^ 0xAE, 0x76 ^ 0x70, 238653 + 4114547 - 3560953 + 3418505);
        }
    }
    
    @Override
    protected void handleMouseClick(final Slot slot, final int n, final int n2, int n3) {
        this.field_147057_D = (" ".length() != 0);
        int n4;
        if (n3 == " ".length()) {
            n4 = " ".length();
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        else {
            n4 = "".length();
        }
        final int n5 = n4;
        int n6;
        if (n == -(280 + 15 + 469 + 235) && n3 == 0) {
            n6 = (0xB ^ 0xF);
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else {
            n6 = n3;
        }
        n3 = n6;
        if (slot == null && GuiContainerCreative.selectedTabIndex != CreativeTabs.tabInventory.getTabIndex() && n3 != (0xBA ^ 0xBF)) {
            final InventoryPlayer inventory = this.mc.thePlayer.inventory;
            if (inventory.getItemStack() != null) {
                if (n2 == 0) {
                    this.mc.thePlayer.dropPlayerItemWithRandomChoice(inventory.getItemStack(), " ".length() != 0);
                    this.mc.playerController.sendPacketDropItem(inventory.getItemStack());
                    inventory.setItemStack(null);
                }
                if (n2 == " ".length()) {
                    final ItemStack splitStack = inventory.getItemStack().splitStack(" ".length());
                    this.mc.thePlayer.dropPlayerItemWithRandomChoice(splitStack, " ".length() != 0);
                    this.mc.playerController.sendPacketDropItem(splitStack);
                    if (inventory.getItemStack().stackSize == 0) {
                        inventory.setItemStack(null);
                        "".length();
                        if (3 <= -1) {
                            throw null;
                        }
                    }
                }
            }
        }
        else if (slot == this.field_147064_C && n5 != 0) {
            int i = "".length();
            "".length();
            if (1 == 2) {
                throw null;
            }
            while (i < this.mc.thePlayer.inventoryContainer.getInventory().size()) {
                this.mc.playerController.sendSlotPacket(null, i);
                ++i;
            }
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else if (GuiContainerCreative.selectedTabIndex == CreativeTabs.tabInventory.getTabIndex()) {
            if (slot == this.field_147064_C) {
                this.mc.thePlayer.inventory.setItemStack(null);
                "".length();
                if (3 <= -1) {
                    throw null;
                }
            }
            else if (n3 == (0x6A ^ 0x6E) && slot != null && slot.getHasStack()) {
                int n7;
                if (n2 == 0) {
                    n7 = " ".length();
                    "".length();
                    if (0 >= 2) {
                        throw null;
                    }
                }
                else {
                    n7 = slot.getStack().getMaxStackSize();
                }
                final ItemStack decrStackSize = slot.decrStackSize(n7);
                this.mc.thePlayer.dropPlayerItemWithRandomChoice(decrStackSize, " ".length() != 0);
                this.mc.playerController.sendPacketDropItem(decrStackSize);
                "".length();
                if (4 == 3) {
                    throw null;
                }
            }
            else if (n3 == (0x81 ^ 0x85) && this.mc.thePlayer.inventory.getItemStack() != null) {
                this.mc.thePlayer.dropPlayerItemWithRandomChoice(this.mc.thePlayer.inventory.getItemStack(), " ".length() != 0);
                this.mc.playerController.sendPacketDropItem(this.mc.thePlayer.inventory.getItemStack());
                this.mc.thePlayer.inventory.setItemStack(null);
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else {
                final Container inventoryContainer = this.mc.thePlayer.inventoryContainer;
                int slotNumber;
                if (slot == null) {
                    slotNumber = n;
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                else {
                    slotNumber = CreativeSlot.access$0((CreativeSlot)slot).slotNumber;
                }
                inventoryContainer.slotClick(slotNumber, n2, n3, this.mc.thePlayer);
                this.mc.thePlayer.inventoryContainer.detectAndSendChanges();
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
        }
        else if (n3 != (0x18 ^ 0x1D) && slot.inventory == GuiContainerCreative.field_147060_v) {
            final InventoryPlayer inventory2 = this.mc.thePlayer.inventory;
            final ItemStack itemStack = inventory2.getItemStack();
            final ItemStack stack = slot.getStack();
            if (n3 == "  ".length()) {
                if (stack != null && n2 >= 0 && n2 < (0x5F ^ 0x56)) {
                    final ItemStack copy = stack.copy();
                    copy.stackSize = copy.getMaxStackSize();
                    this.mc.thePlayer.inventory.setInventorySlotContents(n2, copy);
                    this.mc.thePlayer.inventoryContainer.detectAndSendChanges();
                }
                return;
            }
            if (n3 == "   ".length()) {
                if (inventory2.getItemStack() == null && slot.getHasStack()) {
                    final ItemStack copy2 = slot.getStack().copy();
                    copy2.stackSize = copy2.getMaxStackSize();
                    inventory2.setItemStack(copy2);
                }
                return;
            }
            if (n3 == (0x4A ^ 0x4E)) {
                if (stack != null) {
                    final ItemStack copy3;
                    final ItemStack itemStack2 = copy3 = stack.copy();
                    int stackSize;
                    if (n2 == 0) {
                        stackSize = " ".length();
                        "".length();
                        if (3 < 3) {
                            throw null;
                        }
                    }
                    else {
                        stackSize = itemStack2.getMaxStackSize();
                    }
                    copy3.stackSize = stackSize;
                    this.mc.thePlayer.dropPlayerItemWithRandomChoice(itemStack2, " ".length() != 0);
                    this.mc.playerController.sendPacketDropItem(itemStack2);
                }
                return;
            }
            if (itemStack != null && stack != null && itemStack.isItemEqual(stack)) {
                if (n2 == 0) {
                    if (n5 != 0) {
                        itemStack.stackSize = itemStack.getMaxStackSize();
                        "".length();
                        if (3 <= 2) {
                            throw null;
                        }
                    }
                    else if (itemStack.stackSize < itemStack.getMaxStackSize()) {
                        final ItemStack itemStack3 = itemStack;
                        itemStack3.stackSize += " ".length();
                        "".length();
                        if (1 <= 0) {
                            throw null;
                        }
                    }
                }
                else if (itemStack.stackSize <= " ".length()) {
                    inventory2.setItemStack(null);
                    "".length();
                    if (0 >= 3) {
                        throw null;
                    }
                }
                else {
                    final ItemStack itemStack4 = itemStack;
                    itemStack4.stackSize -= " ".length();
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
            }
            else if (stack != null && itemStack == null) {
                inventory2.setItemStack(ItemStack.copyItemStack(stack));
                final ItemStack itemStack5 = inventory2.getItemStack();
                if (n5 != 0) {
                    itemStack5.stackSize = itemStack5.getMaxStackSize();
                    "".length();
                    if (2 <= 1) {
                        throw null;
                    }
                }
            }
            else {
                inventory2.setItemStack(null);
                "".length();
                if (2 == 3) {
                    throw null;
                }
            }
        }
        else {
            final Container inventorySlots = this.inventorySlots;
            int slotNumber2;
            if (slot == null) {
                slotNumber2 = n;
                "".length();
                if (1 < -1) {
                    throw null;
                }
            }
            else {
                slotNumber2 = slot.slotNumber;
            }
            inventorySlots.slotClick(slotNumber2, n2, n3, this.mc.thePlayer);
            if (Container.getDragEvent(n2) == "  ".length()) {
                int j = "".length();
                "".length();
                if (-1 == 0) {
                    throw null;
                }
                while (j < (0x60 ^ 0x69)) {
                    this.mc.playerController.sendSlotPacket(this.inventorySlots.getSlot((0x7E ^ 0x53) + j).getStack(), (0x7A ^ 0x5E) + j);
                    ++j;
                }
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            else if (slot != null) {
                this.mc.playerController.sendSlotPacket(this.inventorySlots.getSlot(slot.slotNumber).getStack(), slot.slotNumber - this.inventorySlots.inventorySlots.size() + (0x31 ^ 0x38) + (0x3 ^ 0x27));
            }
        }
    }
    
    @Override
    public void updateScreen() {
        if (!this.mc.playerController.isInCreativeMode()) {
            this.mc.displayGuiScreen(new GuiInventory(this.mc.thePlayer));
        }
        this.updateActivePotionEffects();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        final boolean buttonDown = Mouse.isButtonDown("".length());
        final int guiLeft = this.guiLeft;
        final int guiTop = this.guiTop;
        final int n4 = guiLeft + (76 + 172 - 116 + 43);
        final int n5 = guiTop + (0x6F ^ 0x7D);
        final int n6 = n4 + (0xAF ^ 0xA1);
        final int n7 = n5 + (0xF3 ^ 0x83);
        if (!this.wasClicking && buttonDown && n >= n4 && n2 >= n5 && n < n6 && n2 < n7) {
            this.isScrolling = this.needsScrollBars();
        }
        if (!buttonDown) {
            this.isScrolling = ("".length() != 0);
        }
        this.wasClicking = buttonDown;
        if (this.isScrolling) {
            this.currentScroll = (n2 - n5 - 7.5f) / (n7 - n5 - 15.0f);
            this.currentScroll = MathHelper.clamp_float(this.currentScroll, 0.0f, 1.0f);
            ((ContainerCreative)this.inventorySlots).scrollTo(this.currentScroll);
        }
        super.drawScreen(n, n2, n3);
        final CreativeTabs[] creativeTabArray;
        final int length = (creativeTabArray = CreativeTabs.creativeTabArray).length;
        int i = "".length();
        "".length();
        if (3 <= 2) {
            throw null;
        }
        while (i < length) {
            if (this.renderCreativeInventoryHoveringText(creativeTabArray[i], n, n2)) {
                "".length();
                if (3 >= 4) {
                    throw null;
                }
                break;
            }
            else {
                ++i;
            }
        }
        if (this.field_147064_C != null && GuiContainerCreative.selectedTabIndex == CreativeTabs.tabInventory.getTabIndex() && this.isPointInRegion(this.field_147064_C.xDisplayPosition, this.field_147064_C.yDisplayPosition, 0x3D ^ 0x2D, 0x77 ^ 0x67, n, n2)) {
            this.drawCreativeTabHoveringText(I18n.format(GuiContainerCreative.I[0xC7 ^ 0xC3], new Object["".length()]), n, n2);
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableLighting();
    }
    
    public GuiContainerCreative(final EntityPlayer entityPlayer) {
        super(new ContainerCreative(entityPlayer));
        entityPlayer.openContainer = this.inventorySlots;
        this.allowUserInput = (" ".length() != 0);
        this.ySize = 70 + 94 - 28 + 0;
        this.xSize = 111 + 119 - 93 + 58;
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        RenderHelper.enableGUIStandardItemLighting();
        final CreativeTabs creativeTabs = CreativeTabs.creativeTabArray[GuiContainerCreative.selectedTabIndex];
        final CreativeTabs[] creativeTabArray;
        final int length = (creativeTabArray = CreativeTabs.creativeTabArray).length;
        int i = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (i < length) {
            final CreativeTabs creativeTabs2 = creativeTabArray[i];
            this.mc.getTextureManager().bindTexture(GuiContainerCreative.creativeInventoryTabs);
            if (creativeTabs2.getTabIndex() != GuiContainerCreative.selectedTabIndex) {
                this.func_147051_a(creativeTabs2);
            }
            ++i;
        }
        this.mc.getTextureManager().bindTexture(new ResourceLocation(GuiContainerCreative.I[0x19 ^ 0x1C] + creativeTabs.getBackgroundImageName()));
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, "".length(), "".length(), this.xSize, this.ySize);
        this.searchField.drawTextBox();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final int n4 = this.guiLeft + (72 + 137 - 114 + 80);
        final int n5 = this.guiTop + (0x4E ^ 0x5C);
        final int n6 = n5 + (0x72 ^ 0x2);
        this.mc.getTextureManager().bindTexture(GuiContainerCreative.creativeInventoryTabs);
        if (creativeTabs.shouldHidePlayerInventory()) {
            final int n7 = n4;
            final int n8 = n5 + (int)((n6 - n5 - (0x8C ^ 0x9D)) * this.currentScroll);
            final int n9 = 197 + 111 - 131 + 55;
            int length2;
            if (this.needsScrollBars()) {
                length2 = "".length();
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                length2 = (0x24 ^ 0x28);
            }
            this.drawTexturedModalRect(n7, n8, n9 + length2, "".length(), 0x84 ^ 0x88, 0x7B ^ 0x74);
        }
        this.func_147051_a(creativeTabs);
        if (creativeTabs == CreativeTabs.tabInventory) {
            GuiInventory.drawEntityOnScreen(this.guiLeft + (0xAC ^ 0x87), this.guiTop + (0x54 ^ 0x79), 0x3E ^ 0x2A, this.guiLeft + (0xEA ^ 0xC1) - n2, this.guiTop + (0xE ^ 0x23) - (0xC ^ 0x12) - n3, this.mc.thePlayer);
        }
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int n = Mouse.getEventDWheel();
        if (n != 0 && this.needsScrollBars()) {
            final int n2 = ((ContainerCreative)this.inventorySlots).itemList.size() / (0x99 ^ 0x90) - (0x54 ^ 0x51);
            if (n > 0) {
                n = " ".length();
            }
            if (n < 0) {
                n = -" ".length();
            }
            this.currentScroll -= n / n2;
            this.currentScroll = MathHelper.clamp_float(this.currentScroll, 0.0f, 1.0f);
            ((ContainerCreative)this.inventorySlots).scrollTo(this.currentScroll);
        }
    }
    
    @Override
    protected void updateActivePotionEffects() {
        final int guiLeft = this.guiLeft;
        super.updateActivePotionEffects();
        if (this.searchField != null && this.guiLeft != guiLeft) {
            this.searchField.xPosition = this.guiLeft + (0xD8 ^ 0x8A);
        }
    }
    
    private boolean needsScrollBars() {
        if (GuiContainerCreative.selectedTabIndex != CreativeTabs.tabInventory.getTabIndex() && CreativeTabs.creativeTabArray[GuiContainerCreative.selectedTabIndex].shouldHidePlayerInventory() && ((ContainerCreative)this.inventorySlots).func_148328_e()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
        }
        if (guiButton.id == " ".length()) {
            this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
        }
    }
    
    static {
        I();
        creativeInventoryTabs = new ResourceLocation(GuiContainerCreative.I["".length()]);
        GuiContainerCreative.field_147060_v = new InventoryBasic(GuiContainerCreative.I[" ".length()], " ".length() != 0, 0x26 ^ 0xB);
        GuiContainerCreative.selectedTabIndex = CreativeTabs.tabBlock.getTabIndex();
    }
    
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        if (this.mc.thePlayer != null && this.mc.thePlayer.inventory != null) {
            this.mc.thePlayer.inventoryContainer.removeCraftingFromCrafters(this.field_147059_E);
        }
        Keyboard.enableRepeatEvents((boolean)("".length() != 0));
    }
    
    private static void I() {
        (I = new String[0x1C ^ 0x1A])["".length()] = I("6&\u0011?\u001d0&\u001ad\u000f7*F(\u0007,7\b\"\u0006'1F(\u001a'\"\u001d\"\u001e'\u001c\u0000%\u001e'-\u001d$\u001a;l\u001d*\n1m\u0019%\u000f", "BCiKh");
        GuiContainerCreative.I[" ".length()] = I("&7\u0004", "RZtjk");
        GuiContainerCreative.I["  ".length()] = I("", "EwGeQ");
        GuiContainerCreative.I["   ".length()] = I("", "zMtQF");
        GuiContainerCreative.I[0x38 ^ 0x3C] = I("=%&\u00014 $\"\u001dt6\">76;?", "TKPdZ");
        GuiContainerCreative.I[0x9B ^ 0x9E] = I("3\u0004\u0017\u001a\u00115\u0004\u001cA\u00032\b@\r\u000b)\u0015\u000e\u0007\n\"\u0013@\r\u0016\"\u0000\u001b\u0007\u0012\">\u0006\u0000\u0012\"\u000f\u001b\u0001\u0016>N\u001b\u000f\u0006\u0018", "Gaond");
    }
    
    @Override
    protected void renderToolTip(final ItemStack itemStack, final int n, final int n2) {
        if (GuiContainerCreative.selectedTabIndex == CreativeTabs.tabAllSearch.getTabIndex()) {
            final List<String> tooltip = itemStack.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
            CreativeTabs creativeTab = itemStack.getItem().getCreativeTab();
            if (creativeTab == null && itemStack.getItem() == Items.enchanted_book) {
                final Map<Integer, Integer> enchantments = EnchantmentHelper.getEnchantments(itemStack);
                if (enchantments.size() == " ".length()) {
                    final Enchantment enchantmentById = Enchantment.getEnchantmentById(enchantments.keySet().iterator().next());
                    final CreativeTabs[] creativeTabArray;
                    final int length = (creativeTabArray = CreativeTabs.creativeTabArray).length;
                    int i = "".length();
                    "".length();
                    if (4 < 2) {
                        throw null;
                    }
                    while (i < length) {
                        final CreativeTabs creativeTabs = creativeTabArray[i];
                        if (creativeTabs.hasRelevantEnchantmentType(enchantmentById.type)) {
                            creativeTab = creativeTabs;
                            "".length();
                            if (3 < 1) {
                                throw null;
                            }
                            break;
                        }
                        else {
                            ++i;
                        }
                    }
                }
            }
            if (creativeTab != null) {
                tooltip.add(" ".length(), new StringBuilder().append(EnumChatFormatting.BOLD).append(EnumChatFormatting.BLUE).append(I18n.format(creativeTab.getTranslatedTabLabel(), new Object["".length()])).toString());
            }
            int j = "".length();
            "".length();
            if (false) {
                throw null;
            }
            while (j < tooltip.size()) {
                if (j == 0) {
                    tooltip.set(j, itemStack.getRarity().rarityColor + tooltip.get(j));
                    "".length();
                    if (2 == 0) {
                        throw null;
                    }
                }
                else {
                    tooltip.set(j, EnumChatFormatting.GRAY + tooltip.get(j));
                }
                ++j;
            }
            this.drawHoveringText(tooltip, n, n2);
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else {
            super.renderToolTip(itemStack, n, n2);
        }
    }
    
    private void setCurrentCreativeTab(final CreativeTabs creativeTabs) {
        final int selectedTabIndex = GuiContainerCreative.selectedTabIndex;
        GuiContainerCreative.selectedTabIndex = creativeTabs.getTabIndex();
        final ContainerCreative containerCreative = (ContainerCreative)this.inventorySlots;
        this.dragSplittingSlots.clear();
        containerCreative.itemList.clear();
        creativeTabs.displayAllReleventItems(containerCreative.itemList);
        if (creativeTabs == CreativeTabs.tabInventory) {
            final Container inventoryContainer = this.mc.thePlayer.inventoryContainer;
            if (this.field_147063_B == null) {
                this.field_147063_B = containerCreative.inventorySlots;
            }
            containerCreative.inventorySlots = (List<Slot>)Lists.newArrayList();
            int i = "".length();
            "".length();
            if (0 < -1) {
                throw null;
            }
            while (i < inventoryContainer.inventorySlots.size()) {
                final CreativeSlot creativeSlot = new CreativeSlot(inventoryContainer.inventorySlots.get(i), i);
                containerCreative.inventorySlots.add(creativeSlot);
                if (i >= (0x25 ^ 0x20) && i < (0x7C ^ 0x75)) {
                    final int n = i - (0x86 ^ 0x83);
                    final int n2 = n / "  ".length();
                    final int n3 = n % "  ".length();
                    creativeSlot.xDisplayPosition = (0x92 ^ 0x9B) + n2 * (0x5 ^ 0x33);
                    creativeSlot.yDisplayPosition = (0x82 ^ 0x84) + n3 * (0x2B ^ 0x30);
                    "".length();
                    if (0 >= 2) {
                        throw null;
                    }
                }
                else if (i >= 0 && i < (0x77 ^ 0x72)) {
                    creativeSlot.yDisplayPosition = -(1631 + 688 - 1790 + 1471);
                    creativeSlot.xDisplayPosition = -(717 + 1279 - 1416 + 1420);
                    "".length();
                    if (4 == 1) {
                        throw null;
                    }
                }
                else if (i < inventoryContainer.inventorySlots.size()) {
                    final int n4 = i - (0x10 ^ 0x19);
                    final int n5 = n4 % (0xC8 ^ 0xC1);
                    final int n6 = n4 / (0x7C ^ 0x75);
                    creativeSlot.xDisplayPosition = (0xB7 ^ 0xBE) + n5 * (0xAC ^ 0xBE);
                    if (i >= (0x8D ^ 0xA9)) {
                        creativeSlot.yDisplayPosition = (0xB1 ^ 0xC1);
                        "".length();
                        if (0 <= -1) {
                            throw null;
                        }
                    }
                    else {
                        creativeSlot.yDisplayPosition = (0x76 ^ 0x40) + n6 * (0x55 ^ 0x47);
                    }
                }
                ++i;
            }
            this.field_147064_C = new Slot(GuiContainerCreative.field_147060_v, "".length(), 158 + 5 - 42 + 52, 0xEC ^ 0x9C);
            containerCreative.inventorySlots.add(this.field_147064_C);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else if (selectedTabIndex == CreativeTabs.tabInventory.getTabIndex()) {
            containerCreative.inventorySlots = this.field_147063_B;
            this.field_147063_B = null;
        }
        if (this.searchField != null) {
            if (creativeTabs == CreativeTabs.tabAllSearch) {
                this.searchField.setVisible(" ".length() != 0);
                this.searchField.setCanLoseFocus("".length() != 0);
                this.searchField.setFocused(" ".length() != 0);
                this.searchField.setText(GuiContainerCreative.I["   ".length()]);
                this.updateCreativeSearch();
                "".length();
                if (3 <= 0) {
                    throw null;
                }
            }
            else {
                this.searchField.setVisible("".length() != 0);
                this.searchField.setCanLoseFocus(" ".length() != 0);
                this.searchField.setFocused("".length() != 0);
            }
        }
        containerCreative.scrollTo(this.currentScroll = 0.0f);
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        if (n3 == 0) {
            final int n4 = n - this.guiLeft;
            final int n5 = n2 - this.guiTop;
            final CreativeTabs[] creativeTabArray;
            final int length = (creativeTabArray = CreativeTabs.creativeTabArray).length;
            int i = "".length();
            "".length();
            if (1 <= 0) {
                throw null;
            }
            while (i < length) {
                if (this.func_147049_a(creativeTabArray[i], n4, n5)) {
                    return;
                }
                ++i;
            }
        }
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    protected void mouseReleased(final int n, final int n2, final int n3) {
        if (n3 == 0) {
            final int n4 = n - this.guiLeft;
            final int n5 = n2 - this.guiTop;
            final CreativeTabs[] creativeTabArray;
            final int length = (creativeTabArray = CreativeTabs.creativeTabArray).length;
            int i = "".length();
            "".length();
            if (2 == -1) {
                throw null;
            }
            while (i < length) {
                final CreativeTabs currentCreativeTab = creativeTabArray[i];
                if (this.func_147049_a(currentCreativeTab, n4, n5)) {
                    this.setCurrentCreativeTab(currentCreativeTab);
                    return;
                }
                ++i;
            }
        }
        super.mouseReleased(n, n2, n3);
    }
    
    protected boolean renderCreativeInventoryHoveringText(final CreativeTabs creativeTabs, final int n, final int n2) {
        final int tabColumn = creativeTabs.getTabColumn();
        int n3 = (0x89 ^ 0x95) * tabColumn;
        int length = "".length();
        if (tabColumn == (0x63 ^ 0x66)) {
            n3 = this.xSize - (0xB0 ^ 0xAC) + "  ".length();
            "".length();
            if (!true) {
                throw null;
            }
        }
        else if (tabColumn > 0) {
            n3 += tabColumn;
        }
        if (creativeTabs.isTabInFirstRow()) {
            length -= 32;
            "".length();
            if (4 == 2) {
                throw null;
            }
        }
        else {
            length += this.ySize;
        }
        if (this.isPointInRegion(n3 + "   ".length(), length + "   ".length(), 0x95 ^ 0x82, 0x5E ^ 0x45, n, n2)) {
            this.drawCreativeTabHoveringText(I18n.format(creativeTabs.getTranslatedTabLabel(), new Object["".length()]), n, n2);
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    static InventoryBasic access$0() {
        return GuiContainerCreative.field_147060_v;
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected void func_147051_a(final CreativeTabs creativeTabs) {
        int n;
        if (creativeTabs.getTabIndex() == GuiContainerCreative.selectedTabIndex) {
            n = " ".length();
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final int n2 = n;
        final boolean tabInFirstRow = creativeTabs.isTabInFirstRow();
        final int tabColumn = creativeTabs.getTabColumn();
        final int n3 = tabColumn * (0x3C ^ 0x20);
        int length = "".length();
        int n4 = this.guiLeft + (0x82 ^ 0x9E) * tabColumn;
        int guiTop = this.guiTop;
        final int n5 = 0x49 ^ 0x69;
        if (n2 != 0) {
            length += 32;
        }
        if (tabColumn == (0xAD ^ 0xA8)) {
            n4 = this.guiLeft + this.xSize - (0xB6 ^ 0xAA);
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else if (tabColumn > 0) {
            n4 += tabColumn;
        }
        if (tabInFirstRow) {
            guiTop -= 28;
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        else {
            length += 64;
            guiTop += this.ySize - (0x6F ^ 0x6B);
        }
        GlStateManager.disableLighting();
        this.drawTexturedModalRect(n4, guiTop, n3, length, 0x5F ^ 0x43, n5);
        this.zLevel = 100.0f;
        this.itemRender.zLevel = 100.0f;
        n4 += 6;
        final int n6 = guiTop + (0x8F ^ 0x87);
        int length2;
        if (tabInFirstRow) {
            length2 = " ".length();
            "".length();
            if (2 == 1) {
                throw null;
            }
        }
        else {
            length2 = -" ".length();
        }
        final int n7 = n6 + length2;
        GlStateManager.enableLighting();
        GlStateManager.enableRescaleNormal();
        final ItemStack iconItemStack = creativeTabs.getIconItemStack();
        this.itemRender.renderItemAndEffectIntoGUI(iconItemStack, n4, n7);
        this.itemRender.renderItemOverlays(this.fontRendererObj, iconItemStack, n4, n7);
        GlStateManager.disableLighting();
        this.itemRender.zLevel = 0.0f;
        this.zLevel = 0.0f;
    }
    
    protected boolean func_147049_a(final CreativeTabs creativeTabs, final int n, final int n2) {
        final int tabColumn = creativeTabs.getTabColumn();
        int n3 = (0x1 ^ 0x1D) * tabColumn;
        int length = "".length();
        if (tabColumn == (0xA7 ^ 0xA2)) {
            n3 = this.xSize - (0x4F ^ 0x53) + "  ".length();
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else if (tabColumn > 0) {
            n3 += tabColumn;
        }
        if (creativeTabs.isTabInFirstRow()) {
            length -= 32;
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            length += this.ySize;
        }
        if (n >= n3 && n <= n3 + (0x45 ^ 0x59) && n2 >= length && n2 <= length + (0x18 ^ 0x38)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void initGui() {
        if (this.mc.playerController.isInCreativeMode()) {
            super.initGui();
            this.buttonList.clear();
            Keyboard.enableRepeatEvents((boolean)(" ".length() != 0));
            (this.searchField = new GuiTextField("".length(), this.fontRendererObj, this.guiLeft + (0xC6 ^ 0x94), this.guiTop + (0x6D ^ 0x6B), 0x7A ^ 0x23, this.fontRendererObj.FONT_HEIGHT)).setMaxStringLength(0x9E ^ 0x91);
            this.searchField.setEnableBackgroundDrawing("".length() != 0);
            this.searchField.setVisible("".length() != 0);
            this.searchField.setTextColor(7593971 + 15186530 - 16397293 + 10394007);
            final int selectedTabIndex = GuiContainerCreative.selectedTabIndex;
            GuiContainerCreative.selectedTabIndex = -" ".length();
            this.setCurrentCreativeTab(CreativeTabs.creativeTabArray[selectedTabIndex]);
            this.field_147059_E = new CreativeCrafting(this.mc);
            this.mc.thePlayer.inventoryContainer.onCraftGuiOpened(this.field_147059_E);
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else {
            this.mc.displayGuiScreen(new GuiInventory(this.mc.thePlayer));
        }
    }
    
    public int getSelectedTabIndex() {
        return GuiContainerCreative.selectedTabIndex;
    }
    
    class CreativeSlot extends Slot
    {
        final GuiContainerCreative this$0;
        private final Slot slot;
        
        @Override
        public void putStack(final ItemStack itemStack) {
            this.slot.putStack(itemStack);
        }
        
        @Override
        public ItemStack getStack() {
            return this.slot.getStack();
        }
        
        @Override
        public int getItemStackLimit(final ItemStack itemStack) {
            return this.slot.getItemStackLimit(itemStack);
        }
        
        @Override
        public int getSlotStackLimit() {
            return this.slot.getSlotStackLimit();
        }
        
        @Override
        public boolean isItemValid(final ItemStack itemStack) {
            return this.slot.isItemValid(itemStack);
        }
        
        @Override
        public void onSlotChanged() {
            this.slot.onSlotChanged();
        }
        
        @Override
        public boolean getHasStack() {
            return this.slot.getHasStack();
        }
        
        @Override
        public String getSlotTexture() {
            return this.slot.getSlotTexture();
        }
        
        @Override
        public ItemStack decrStackSize(final int n) {
            return this.slot.decrStackSize(n);
        }
        
        @Override
        public boolean isHere(final IInventory inventory, final int n) {
            return this.slot.isHere(inventory, n);
        }
        
        static Slot access$0(final CreativeSlot creativeSlot) {
            return creativeSlot.slot;
        }
        
        public CreativeSlot(final GuiContainerCreative this$0, final Slot slot, final int n) {
            this.this$0 = this$0;
            super(slot.inventory, n, "".length(), "".length());
            this.slot = slot;
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
                if (-1 == 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public void onPickupFromSlot(final EntityPlayer entityPlayer, final ItemStack itemStack) {
            this.slot.onPickupFromSlot(entityPlayer, itemStack);
        }
    }
    
    static class ContainerCreative extends Container
    {
        public List<ItemStack> itemList;
        
        @Override
        public boolean canMergeSlot(final ItemStack itemStack, final Slot slot) {
            if (slot.yDisplayPosition > (0xD7 ^ 0x8D)) {
                return " ".length() != 0;
            }
            return "".length() != 0;
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
                if (3 == 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public ContainerCreative(final EntityPlayer entityPlayer) {
            this.itemList = (List<ItemStack>)Lists.newArrayList();
            final InventoryPlayer inventory = entityPlayer.inventory;
            int i = "".length();
            "".length();
            if (2 == 0) {
                throw null;
            }
            while (i < (0x3F ^ 0x3A)) {
                int j = "".length();
                "".length();
                if (2 <= -1) {
                    throw null;
                }
                while (j < (0x5C ^ 0x55)) {
                    this.addSlotToContainer(new Slot(GuiContainerCreative.access$0(), i * (0xAA ^ 0xA3) + j, (0x67 ^ 0x6E) + j * (0x90 ^ 0x82), (0x1D ^ 0xF) + i * (0x50 ^ 0x42)));
                    ++j;
                }
                ++i;
            }
            int k = "".length();
            "".length();
            if (0 <= -1) {
                throw null;
            }
            while (k < (0x21 ^ 0x28)) {
                this.addSlotToContainer(new Slot(inventory, k, (0x58 ^ 0x51) + k * (0xA ^ 0x18), 0x3D ^ 0x4D));
                ++k;
            }
            this.scrollTo(0.0f);
        }
        
        @Override
        public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
            if (n >= this.inventorySlots.size() - (0xA4 ^ 0xAD) && n < this.inventorySlots.size()) {
                final Slot slot = this.inventorySlots.get(n);
                if (slot != null && slot.getHasStack()) {
                    slot.putStack(null);
                }
            }
            return null;
        }
        
        @Override
        public boolean canInteractWith(final EntityPlayer entityPlayer) {
            return " ".length() != 0;
        }
        
        @Override
        public boolean canDragIntoSlot(final Slot slot) {
            if (!(slot.inventory instanceof InventoryPlayer) && (slot.yDisplayPosition <= (0x9F ^ 0xC5) || slot.xDisplayPosition > 115 + 4 - 36 + 79)) {
                return "".length() != 0;
            }
            return " ".length() != 0;
        }
        
        public boolean func_148328_e() {
            if (this.itemList.size() > (0x7C ^ 0x51)) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        public void scrollTo(final float n) {
            int length = (int)(n * ((this.itemList.size() + (0x23 ^ 0x2A) - " ".length()) / (0xA3 ^ 0xAA) - (0x7D ^ 0x78)) + 0.5);
            if (length < 0) {
                length = "".length();
            }
            int i = "".length();
            "".length();
            if (0 >= 3) {
                throw null;
            }
            while (i < (0x43 ^ 0x46)) {
                int j = "".length();
                "".length();
                if (1 >= 4) {
                    throw null;
                }
                while (j < (0x85 ^ 0x8C)) {
                    final int n2 = j + (i + length) * (0x13 ^ 0x1A);
                    if (n2 >= 0 && n2 < this.itemList.size()) {
                        GuiContainerCreative.access$0().setInventorySlotContents(j + i * (0x22 ^ 0x2B), this.itemList.get(n2));
                        "".length();
                        if (2 < -1) {
                            throw null;
                        }
                    }
                    else {
                        GuiContainerCreative.access$0().setInventorySlotContents(j + i * (0x2E ^ 0x27), null);
                    }
                    ++j;
                }
                ++i;
            }
        }
        
        @Override
        protected void retrySlotClick(final int n, final int n2, final boolean b, final EntityPlayer entityPlayer) {
        }
    }
}
