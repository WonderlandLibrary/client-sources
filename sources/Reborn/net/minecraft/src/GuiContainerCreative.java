package net.minecraft.src;

import net.minecraft.client.*;
import java.util.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;

public class GuiContainerCreative extends InventoryEffectRenderer
{
    private static InventoryBasic inventory;
    private static int selectedTabIndex;
    private float currentScroll;
    private boolean isScrolling;
    private boolean wasClicking;
    private GuiTextField searchField;
    private List backupContainerSlots;
    private Slot field_74235_v;
    private boolean field_74234_w;
    private CreativeCrafting field_82324_x;
    
    static {
        GuiContainerCreative.inventory = new InventoryBasic("tmp", true, 45);
        GuiContainerCreative.selectedTabIndex = CreativeTabs.tabBlock.getTabIndex();
    }
    
    public GuiContainerCreative(final EntityPlayer par1EntityPlayer) {
        super(new ContainerCreative(par1EntityPlayer));
        this.currentScroll = 0.0f;
        this.isScrolling = false;
        this.field_74235_v = null;
        this.field_74234_w = false;
        par1EntityPlayer.openContainer = this.inventorySlots;
        this.allowUserInput = true;
        par1EntityPlayer.addStat(AchievementList.openInventory, 1);
        this.ySize = 136;
        this.xSize = 195;
    }
    
    @Override
    public void updateScreen() {
        if (!this.mc.playerController.isInCreativeMode()) {
            this.mc.displayGuiScreen(new GuiInventory(Minecraft.thePlayer));
        }
    }
    
    @Override
    protected void handleMouseClick(final Slot par1Slot, final int par2, final int par3, int par4) {
        this.field_74234_w = true;
        final boolean var5 = par4 == 1;
        par4 = ((par2 == -999 && par4 == 0) ? 4 : par4);
        if (par1Slot == null && GuiContainerCreative.selectedTabIndex != CreativeTabs.tabInventory.getTabIndex() && par4 != 5) {
            final InventoryPlayer var6 = Minecraft.thePlayer.inventory;
            if (var6.getItemStack() != null) {
                if (par3 == 0) {
                    Minecraft.thePlayer.dropPlayerItem(var6.getItemStack());
                    this.mc.playerController.func_78752_a(var6.getItemStack());
                    var6.setItemStack(null);
                }
                if (par3 == 1) {
                    final ItemStack var7 = var6.getItemStack().splitStack(1);
                    Minecraft.thePlayer.dropPlayerItem(var7);
                    this.mc.playerController.func_78752_a(var7);
                    if (var6.getItemStack().stackSize == 0) {
                        var6.setItemStack(null);
                    }
                }
            }
        }
        else if (par1Slot == this.field_74235_v && var5) {
            for (int var8 = 0; var8 < Minecraft.thePlayer.inventoryContainer.getInventory().size(); ++var8) {
                this.mc.playerController.sendSlotPacket(null, var8);
            }
        }
        else if (GuiContainerCreative.selectedTabIndex == CreativeTabs.tabInventory.getTabIndex()) {
            if (par1Slot == this.field_74235_v) {
                Minecraft.thePlayer.inventory.setItemStack(null);
            }
            else if (par4 == 4 && par1Slot != null && par1Slot.getHasStack()) {
                final ItemStack var9 = par1Slot.decrStackSize((par3 == 0) ? 1 : par1Slot.getStack().getMaxStackSize());
                Minecraft.thePlayer.dropPlayerItem(var9);
                this.mc.playerController.func_78752_a(var9);
            }
            else if (par4 == 4 && Minecraft.thePlayer.inventory.getItemStack() != null) {
                Minecraft.thePlayer.dropPlayerItem(Minecraft.thePlayer.inventory.getItemStack());
                this.mc.playerController.func_78752_a(Minecraft.thePlayer.inventory.getItemStack());
                Minecraft.thePlayer.inventory.setItemStack(null);
            }
            else {
                Minecraft.thePlayer.inventoryContainer.slotClick((par1Slot == null) ? par2 : SlotCreativeInventory.func_75240_a((SlotCreativeInventory)par1Slot).slotNumber, par3, par4, Minecraft.thePlayer);
                Minecraft.thePlayer.inventoryContainer.detectAndSendChanges();
            }
        }
        else if (par4 != 5 && par1Slot.inventory == GuiContainerCreative.inventory) {
            final InventoryPlayer var6 = Minecraft.thePlayer.inventory;
            ItemStack var7 = var6.getItemStack();
            final ItemStack var10 = par1Slot.getStack();
            if (par4 == 2) {
                if (var10 != null && par3 >= 0 && par3 < 9) {
                    final ItemStack var11 = var10.copy();
                    var11.stackSize = var11.getMaxStackSize();
                    Minecraft.thePlayer.inventory.setInventorySlotContents(par3, var11);
                    Minecraft.thePlayer.inventoryContainer.detectAndSendChanges();
                }
                return;
            }
            if (par4 == 3) {
                if (var6.getItemStack() == null && par1Slot.getHasStack()) {
                    final ItemStack var11 = par1Slot.getStack().copy();
                    var11.stackSize = var11.getMaxStackSize();
                    var6.setItemStack(var11);
                }
                return;
            }
            if (par4 == 4) {
                if (var10 != null) {
                    final ItemStack var11 = var10.copy();
                    var11.stackSize = ((par3 == 0) ? 1 : var11.getMaxStackSize());
                    Minecraft.thePlayer.dropPlayerItem(var11);
                    this.mc.playerController.func_78752_a(var11);
                }
                return;
            }
            if (var7 != null && var10 != null && var7.isItemEqual(var10)) {
                if (par3 == 0) {
                    if (var5) {
                        var7.stackSize = var7.getMaxStackSize();
                    }
                    else if (var7.stackSize < var7.getMaxStackSize()) {
                        final ItemStack itemStack = var7;
                        ++itemStack.stackSize;
                    }
                }
                else if (var7.stackSize <= 1) {
                    var6.setItemStack(null);
                }
                else {
                    final ItemStack itemStack2 = var7;
                    --itemStack2.stackSize;
                }
            }
            else if (var10 != null && var7 == null) {
                var6.setItemStack(ItemStack.copyItemStack(var10));
                var7 = var6.getItemStack();
                if (var5) {
                    var7.stackSize = var7.getMaxStackSize();
                }
            }
            else {
                var6.setItemStack(null);
            }
        }
        else {
            this.inventorySlots.slotClick((par1Slot == null) ? par2 : par1Slot.slotNumber, par3, par4, Minecraft.thePlayer);
            if (Container.func_94532_c(par3) == 2) {
                for (int var8 = 0; var8 < 9; ++var8) {
                    this.mc.playerController.sendSlotPacket(this.inventorySlots.getSlot(45 + var8).getStack(), 36 + var8);
                }
            }
            else if (par1Slot != null) {
                final ItemStack var9 = this.inventorySlots.getSlot(par1Slot.slotNumber).getStack();
                this.mc.playerController.sendSlotPacket(var9, par1Slot.slotNumber - this.inventorySlots.inventorySlots.size() + 9 + 36);
            }
        }
    }
    
    @Override
    public void initGui() {
        if (this.mc.playerController.isInCreativeMode()) {
            super.initGui();
            this.buttonList.clear();
            Keyboard.enableRepeatEvents(true);
            (this.searchField = new GuiTextField(this.fontRenderer, this.guiLeft + 82, this.guiTop + 6, 89, this.fontRenderer.FONT_HEIGHT)).setMaxStringLength(15);
            this.searchField.setEnableBackgroundDrawing(false);
            this.searchField.setVisible(false);
            this.searchField.setTextColor(16777215);
            final int var1 = GuiContainerCreative.selectedTabIndex;
            GuiContainerCreative.selectedTabIndex = -1;
            this.setCurrentCreativeTab(CreativeTabs.creativeTabArray[var1]);
            this.field_82324_x = new CreativeCrafting(this.mc);
            Minecraft.thePlayer.inventoryContainer.addCraftingToCrafters(this.field_82324_x);
        }
        else {
            this.mc.displayGuiScreen(new GuiInventory(Minecraft.thePlayer));
        }
    }
    
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        if (Minecraft.thePlayer != null && Minecraft.thePlayer.inventory != null) {
            Minecraft.thePlayer.inventoryContainer.removeCraftingFromCrafters(this.field_82324_x);
        }
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
        if (GuiContainerCreative.selectedTabIndex != CreativeTabs.tabAllSearch.getTabIndex()) {
            if (GameSettings.isKeyDown(this.mc.gameSettings.keyBindChat)) {
                this.setCurrentCreativeTab(CreativeTabs.tabAllSearch);
            }
            else {
                super.keyTyped(par1, par2);
            }
        }
        else {
            if (this.field_74234_w) {
                this.field_74234_w = false;
                this.searchField.setText("");
            }
            if (!this.checkHotbarKeys(par2)) {
                if (this.searchField.textboxKeyTyped(par1, par2)) {
                    this.updateCreativeSearch();
                }
                else {
                    super.keyTyped(par1, par2);
                }
            }
        }
    }
    
    private void updateCreativeSearch() {
        final ContainerCreative var1 = (ContainerCreative)this.inventorySlots;
        var1.itemList.clear();
        for (final Item var5 : Item.itemsList) {
            if (var5 != null && var5.getCreativeTab() != null) {
                var5.getSubItems(var5.itemID, null, var1.itemList);
            }
        }
        for (final Enchantment var7 : Enchantment.enchantmentsList) {
            if (var7 != null && var7.type != null) {
                Item.enchantedBook.func_92113_a(var7, var1.itemList);
            }
        }
        final Iterator var8 = var1.itemList.iterator();
        final String var9 = this.searchField.getText().toLowerCase();
        while (var8.hasNext()) {
            final ItemStack var10 = var8.next();
            boolean var11 = false;
            for (final String var13 : var10.getTooltip(Minecraft.thePlayer, this.mc.gameSettings.advancedItemTooltips)) {
                if (!var13.toLowerCase().contains(var9)) {
                    continue;
                }
                var11 = true;
                break;
            }
            if (!var11) {
                var8.remove();
            }
        }
        var1.scrollTo(this.currentScroll = 0.0f);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int par1, final int par2) {
        final CreativeTabs var3 = CreativeTabs.creativeTabArray[GuiContainerCreative.selectedTabIndex];
        if (var3.drawInForegroundOfTab()) {
            this.fontRenderer.drawString(var3.getTranslatedTabLabel(), 8, 6, 4210752);
        }
    }
    
    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) {
        if (par3 == 0) {
            final int var4 = par1 - this.guiLeft;
            final int var5 = par2 - this.guiTop;
            for (final CreativeTabs var9 : CreativeTabs.creativeTabArray) {
                if (this.func_74232_a(var9, var4, var5)) {
                    return;
                }
            }
        }
        super.mouseClicked(par1, par2, par3);
    }
    
    @Override
    protected void mouseMovedOrUp(final int par1, final int par2, final int par3) {
        if (par3 == 0) {
            final int var4 = par1 - this.guiLeft;
            final int var5 = par2 - this.guiTop;
            for (final CreativeTabs var9 : CreativeTabs.creativeTabArray) {
                if (this.func_74232_a(var9, var4, var5)) {
                    this.setCurrentCreativeTab(var9);
                    return;
                }
            }
        }
        super.mouseMovedOrUp(par1, par2, par3);
    }
    
    private boolean needsScrollBars() {
        return GuiContainerCreative.selectedTabIndex != CreativeTabs.tabInventory.getTabIndex() && CreativeTabs.creativeTabArray[GuiContainerCreative.selectedTabIndex].shouldHidePlayerInventory() && ((ContainerCreative)this.inventorySlots).hasMoreThan1PageOfItemsInList();
    }
    
    private void setCurrentCreativeTab(final CreativeTabs par1CreativeTabs) {
        final int var2 = GuiContainerCreative.selectedTabIndex;
        GuiContainerCreative.selectedTabIndex = par1CreativeTabs.getTabIndex();
        final ContainerCreative var3 = (ContainerCreative)this.inventorySlots;
        this.field_94077_p.clear();
        var3.itemList.clear();
        par1CreativeTabs.displayAllReleventItems(var3.itemList);
        if (par1CreativeTabs == CreativeTabs.tabInventory) {
            final Container var4 = Minecraft.thePlayer.inventoryContainer;
            if (this.backupContainerSlots == null) {
                this.backupContainerSlots = var3.inventorySlots;
            }
            var3.inventorySlots = new ArrayList();
            for (int var5 = 0; var5 < var4.inventorySlots.size(); ++var5) {
                final SlotCreativeInventory var6 = new SlotCreativeInventory(this, var4.inventorySlots.get(var5), var5);
                var3.inventorySlots.add(var6);
                if (var5 >= 5 && var5 < 9) {
                    final int var7 = var5 - 5;
                    final int var8 = var7 / 2;
                    final int var9 = var7 % 2;
                    var6.xDisplayPosition = 9 + var8 * 54;
                    var6.yDisplayPosition = 6 + var9 * 27;
                }
                else if (var5 >= 0 && var5 < 5) {
                    var6.yDisplayPosition = -2000;
                    var6.xDisplayPosition = -2000;
                }
                else if (var5 < var4.inventorySlots.size()) {
                    final int var7 = var5 - 9;
                    final int var8 = var7 % 9;
                    final int var9 = var7 / 9;
                    var6.xDisplayPosition = 9 + var8 * 18;
                    if (var5 >= 36) {
                        var6.yDisplayPosition = 112;
                    }
                    else {
                        var6.yDisplayPosition = 54 + var9 * 18;
                    }
                }
            }
            this.field_74235_v = new Slot(GuiContainerCreative.inventory, 0, 173, 112);
            var3.inventorySlots.add(this.field_74235_v);
        }
        else if (var2 == CreativeTabs.tabInventory.getTabIndex()) {
            var3.inventorySlots = this.backupContainerSlots;
            this.backupContainerSlots = null;
        }
        if (this.searchField != null) {
            if (par1CreativeTabs == CreativeTabs.tabAllSearch) {
                this.searchField.setVisible(true);
                this.searchField.setCanLoseFocus(false);
                this.searchField.setFocused(true);
                this.searchField.setText("");
                this.updateCreativeSearch();
            }
            else {
                this.searchField.setVisible(false);
                this.searchField.setCanLoseFocus(true);
                this.searchField.setFocused(false);
            }
        }
        var3.scrollTo(this.currentScroll = 0.0f);
    }
    
    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        int var1 = Mouse.getEventDWheel();
        if (var1 != 0 && this.needsScrollBars()) {
            final int var2 = ((ContainerCreative)this.inventorySlots).itemList.size() / 9 - 5 + 1;
            if (var1 > 0) {
                var1 = 1;
            }
            if (var1 < 0) {
                var1 = -1;
            }
            this.currentScroll -= var1 / var2;
            if (this.currentScroll < 0.0f) {
                this.currentScroll = 0.0f;
            }
            if (this.currentScroll > 1.0f) {
                this.currentScroll = 1.0f;
            }
            ((ContainerCreative)this.inventorySlots).scrollTo(this.currentScroll);
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        final boolean var4 = Mouse.isButtonDown(0);
        final int var5 = this.guiLeft;
        final int var6 = this.guiTop;
        final int var7 = var5 + 175;
        final int var8 = var6 + 18;
        final int var9 = var7 + 14;
        final int var10 = var8 + 112;
        if (!this.wasClicking && var4 && par1 >= var7 && par2 >= var8 && par1 < var9 && par2 < var10) {
            this.isScrolling = this.needsScrollBars();
        }
        if (!var4) {
            this.isScrolling = false;
        }
        this.wasClicking = var4;
        if (this.isScrolling) {
            this.currentScroll = (par2 - var8 - 7.5f) / (var10 - var8 - 15.0f);
            if (this.currentScroll < 0.0f) {
                this.currentScroll = 0.0f;
            }
            if (this.currentScroll > 1.0f) {
                this.currentScroll = 1.0f;
            }
            ((ContainerCreative)this.inventorySlots).scrollTo(this.currentScroll);
        }
        super.drawScreen(par1, par2, par3);
        for (final CreativeTabs var14 : CreativeTabs.creativeTabArray) {
            if (this.renderCreativeInventoryHoveringText(var14, par1, par2)) {
                break;
            }
        }
        if (this.field_74235_v != null && GuiContainerCreative.selectedTabIndex == CreativeTabs.tabInventory.getTabIndex() && this.isPointInRegion(this.field_74235_v.xDisplayPosition, this.field_74235_v.yDisplayPosition, 16, 16, par1, par2)) {
            this.drawCreativeTabHoveringText(StringTranslate.getInstance().translateKey("inventory.binSlot"), par1, par2);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(2896);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float par1, final int par2, final int par3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderHelper.enableGUIStandardItemLighting();
        final CreativeTabs var4 = CreativeTabs.creativeTabArray[GuiContainerCreative.selectedTabIndex];
        for (final CreativeTabs var8 : CreativeTabs.creativeTabArray) {
            this.mc.renderEngine.bindTexture("/gui/allitems.png");
            if (var8.getTabIndex() != GuiContainerCreative.selectedTabIndex) {
                this.renderCreativeTab(var8);
            }
        }
        this.mc.renderEngine.bindTexture("/gui/creative_inv/" + var4.getBackgroundImageName());
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        this.searchField.drawTextBox();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final int var9 = this.guiLeft + 175;
        final int var6 = this.guiTop + 18;
        final int var7 = var6 + 112;
        this.mc.renderEngine.bindTexture("/gui/allitems.png");
        if (var4.shouldHidePlayerInventory()) {
            this.drawTexturedModalRect(var9, var6 + (int)((var7 - var6 - 17) * this.currentScroll), 232 + (this.needsScrollBars() ? 0 : 12), 0, 12, 15);
        }
        this.renderCreativeTab(var4);
        if (var4 == CreativeTabs.tabInventory) {
            GuiInventory.drawPlayerOnGui(this.mc, this.guiLeft + 43, this.guiTop + 45, 20, this.guiLeft + 43 - par2, this.guiTop + 45 - 30 - par3);
        }
    }
    
    protected boolean func_74232_a(final CreativeTabs par1CreativeTabs, final int par2, final int par3) {
        final int var4 = par1CreativeTabs.getTabColumn();
        int var5 = 28 * var4;
        final byte var6 = 0;
        if (var4 == 5) {
            var5 = this.xSize - 28 + 2;
        }
        else if (var4 > 0) {
            var5 += var4;
        }
        int var7;
        if (par1CreativeTabs.isTabInFirstRow()) {
            var7 = var6 - 32;
        }
        else {
            var7 = var6 + this.ySize;
        }
        return par2 >= var5 && par2 <= var5 + 28 && par3 >= var7 && par3 <= var7 + 32;
    }
    
    protected boolean renderCreativeInventoryHoveringText(final CreativeTabs par1CreativeTabs, final int par2, final int par3) {
        final int var4 = par1CreativeTabs.getTabColumn();
        int var5 = 28 * var4;
        final byte var6 = 0;
        if (var4 == 5) {
            var5 = this.xSize - 28 + 2;
        }
        else if (var4 > 0) {
            var5 += var4;
        }
        int var7;
        if (par1CreativeTabs.isTabInFirstRow()) {
            var7 = var6 - 32;
        }
        else {
            var7 = var6 + this.ySize;
        }
        if (this.isPointInRegion(var5 + 3, var7 + 3, 23, 27, par2, par3)) {
            this.drawCreativeTabHoveringText(par1CreativeTabs.getTranslatedTabLabel(), par2, par3);
            return true;
        }
        return false;
    }
    
    protected void renderCreativeTab(final CreativeTabs par1CreativeTabs) {
        final boolean var2 = par1CreativeTabs.getTabIndex() == GuiContainerCreative.selectedTabIndex;
        final boolean var3 = par1CreativeTabs.isTabInFirstRow();
        final int var4 = par1CreativeTabs.getTabColumn();
        final int var5 = var4 * 28;
        int var6 = 0;
        int var7 = this.guiLeft + 28 * var4;
        int var8 = this.guiTop;
        final byte var9 = 32;
        if (var2) {
            var6 += 32;
        }
        if (var4 == 5) {
            var7 = this.guiLeft + this.xSize - 28;
        }
        else if (var4 > 0) {
            var7 += var4;
        }
        if (var3) {
            var8 -= 28;
        }
        else {
            var6 += 64;
            var8 += this.ySize - 4;
        }
        GL11.glDisable(2896);
        this.drawTexturedModalRect(var7, var8, var5, var6, 28, var9);
        this.zLevel = 100.0f;
        final RenderItem itemRenderer = GuiContainerCreative.itemRenderer;
        RenderItem.zLevel = 100.0f;
        var7 += 6;
        var8 += 8 + (var3 ? 1 : -1);
        GL11.glEnable(2896);
        GL11.glEnable(32826);
        final ItemStack var10 = new ItemStack(par1CreativeTabs.getTabIconItem());
        final RenderItem itemRenderer2 = GuiContainerCreative.itemRenderer;
        RenderItem.renderItemAndEffectIntoGUI(this.fontRenderer, this.mc.renderEngine, var10, var7, var8);
        final RenderItem itemRenderer3 = GuiContainerCreative.itemRenderer;
        RenderItem.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, var10, var7, var8);
        GL11.glDisable(2896);
        final RenderItem itemRenderer4 = GuiContainerCreative.itemRenderer;
        RenderItem.zLevel = 0.0f;
        this.zLevel = 0.0f;
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.id == 0) {
            this.mc.displayGuiScreen(new GuiAchievements(this.mc.statFileWriter));
        }
        if (par1GuiButton.id == 1) {
            this.mc.displayGuiScreen(new GuiStats(this, this.mc.statFileWriter));
        }
    }
    
    public int func_74230_h() {
        return GuiContainerCreative.selectedTabIndex;
    }
    
    static InventoryBasic getInventory() {
        return GuiContainerCreative.inventory;
    }
}
