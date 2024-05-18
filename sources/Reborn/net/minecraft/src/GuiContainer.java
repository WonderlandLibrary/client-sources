package net.minecraft.src;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import java.util.*;
import org.lwjgl.input.*;

public abstract class GuiContainer extends GuiScreen
{
    protected static RenderItem itemRenderer;
    protected int xSize;
    protected int ySize;
    public Container inventorySlots;
    protected int guiLeft;
    protected int guiTop;
    private Slot theSlot;
    private Slot clickedSlot;
    private boolean isRightMouseClick;
    private ItemStack draggedStack;
    private int field_85049_r;
    private int field_85048_s;
    private Slot returningStackDestSlot;
    private long returningStackTime;
    private ItemStack returningStack;
    private Slot field_92033_y;
    private long field_92032_z;
    protected final Set field_94077_p;
    protected boolean field_94076_q;
    private int field_94071_C;
    private int field_94067_D;
    private boolean field_94068_E;
    private int field_94069_F;
    private long field_94070_G;
    private Slot field_94072_H;
    private int field_94073_I;
    private boolean field_94074_J;
    private ItemStack field_94075_K;
    
    static {
        GuiContainer.itemRenderer = new RenderItem();
    }
    
    public GuiContainer(final Container par1Container) {
        this.xSize = 176;
        this.ySize = 166;
        this.clickedSlot = null;
        this.isRightMouseClick = false;
        this.draggedStack = null;
        this.field_85049_r = 0;
        this.field_85048_s = 0;
        this.returningStackDestSlot = null;
        this.returningStackTime = 0L;
        this.returningStack = null;
        this.field_92033_y = null;
        this.field_92032_z = 0L;
        this.field_94077_p = new HashSet();
        this.field_94071_C = 0;
        this.field_94067_D = 0;
        this.field_94068_E = false;
        this.field_94070_G = 0L;
        this.field_94072_H = null;
        this.field_94073_I = 0;
        this.field_94075_K = null;
        this.inventorySlots = par1Container;
        this.field_94068_E = true;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        Minecraft.thePlayer.openContainer = this.inventorySlots;
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.drawDefaultBackground();
        final int var4 = this.guiLeft;
        final int var5 = this.guiTop;
        this.drawGuiContainerBackgroundLayer(par3, par1, par2);
        GL11.glDisable(32826);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        super.drawScreen(par1, par2, par3);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glPushMatrix();
        GL11.glTranslatef(var4, var5, 0.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(32826);
        this.theSlot = null;
        final short var6 = 240;
        final short var7 = 240;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0f, var7 / 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        for (int var8 = 0; var8 < this.inventorySlots.inventorySlots.size(); ++var8) {
            final Slot var9 = this.inventorySlots.inventorySlots.get(var8);
            this.drawSlotInventory(var9);
            if (this.isMouseOverSlot(var9, par1, par2)) {
                this.theSlot = var9;
                GL11.glDisable(2896);
                GL11.glDisable(2929);
                final int var10 = var9.xDisplayPosition;
                final int var11 = var9.yDisplayPosition;
                this.drawGradientRect(var10, var11, var10 + 16, var11 + 16, -2130706433, -2130706433);
                GL11.glEnable(2896);
                GL11.glEnable(2929);
            }
        }
        this.drawGuiContainerForegroundLayer(par1, par2);
        final InventoryPlayer var12 = Minecraft.thePlayer.inventory;
        ItemStack var13 = (this.draggedStack == null) ? var12.getItemStack() : this.draggedStack;
        if (var13 != null) {
            final byte var14 = 8;
            final int var11 = (this.draggedStack == null) ? 8 : 16;
            String var15 = null;
            if (this.draggedStack != null && this.isRightMouseClick) {
                var13 = var13.copy();
                var13.stackSize = MathHelper.ceiling_float_int(var13.stackSize / 2.0f);
            }
            else if (this.field_94076_q && this.field_94077_p.size() > 1) {
                var13 = var13.copy();
                var13.stackSize = this.field_94069_F;
                if (var13.stackSize == 0) {
                    var15 = EnumChatFormatting.YELLOW + "0";
                }
            }
            this.drawItemStack(var13, par1 - var4 - var14, par2 - var5 - var11, var15);
        }
        if (this.returningStack != null) {
            float var16 = (Minecraft.getSystemTime() - this.returningStackTime) / 100.0f;
            if (var16 >= 1.0f) {
                var16 = 1.0f;
                this.returningStack = null;
            }
            final int var11 = this.returningStackDestSlot.xDisplayPosition - this.field_85049_r;
            final int var17 = this.returningStackDestSlot.yDisplayPosition - this.field_85048_s;
            final int var18 = this.field_85049_r + (int)(var11 * var16);
            final int var19 = this.field_85048_s + (int)(var17 * var16);
            this.drawItemStack(this.returningStack, var18, var19, null);
        }
        GL11.glPopMatrix();
        if (var12.getItemStack() == null && this.theSlot != null && this.theSlot.getHasStack()) {
            final ItemStack var20 = this.theSlot.getStack();
            this.drawItemStackTooltip(var20, par1, par2);
        }
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        RenderHelper.enableStandardItemLighting();
    }
    
    private void drawItemStack(final ItemStack par1ItemStack, final int par2, final int par3, final String par4Str) {
        GL11.glTranslatef(0.0f, 0.0f, 32.0f);
        this.zLevel = 200.0f;
        RenderItem.zLevel = 200.0f;
        RenderItem.renderItemAndEffectIntoGUI(this.fontRenderer, this.mc.renderEngine, par1ItemStack, par2, par3);
        RenderItem.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, par1ItemStack, par2, par3 - ((this.draggedStack == null) ? 0 : 8), par4Str);
        this.zLevel = 0.0f;
        RenderItem.zLevel = 0.0f;
    }
    
    protected void drawItemStackTooltip(final ItemStack par1ItemStack, final int par2, final int par3) {
        final List var4 = par1ItemStack.getTooltip(Minecraft.thePlayer, this.mc.gameSettings.advancedItemTooltips);
        for (int var5 = 0; var5 < var4.size(); ++var5) {
            if (var5 == 0) {
                var4.set(var5, "§" + Integer.toHexString(par1ItemStack.getRarity().rarityColor) + var4.get(var5));
            }
            else {
                var4.set(var5, EnumChatFormatting.GRAY + var4.get(var5));
            }
        }
        this.func_102021_a(var4, par2, par3);
    }
    
    protected void drawCreativeTabHoveringText(final String par1Str, final int par2, final int par3) {
        this.func_102021_a(Arrays.asList(par1Str), par2, par3);
    }
    
    protected void func_102021_a(final List par1List, final int par2, final int par3) {
        if (!par1List.isEmpty()) {
            GL11.glDisable(32826);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            int var4 = 0;
            for (final String var6 : par1List) {
                final int var7 = this.fontRenderer.getStringWidth(var6);
                if (var7 > var4) {
                    var4 = var7;
                }
            }
            int var8 = par2 + 12;
            int var7 = par3 - 12;
            int var9 = 8;
            if (par1List.size() > 1) {
                var9 += 2 + (par1List.size() - 1) * 10;
            }
            if (var8 + var4 > this.width) {
                var8 -= 28 + var4;
            }
            if (var7 + var9 + 6 > this.height) {
                var7 = this.height - var9 - 6;
            }
            this.zLevel = 300.0f;
            RenderItem.zLevel = 300.0f;
            final int var10 = -267386864;
            this.drawGradientRect(var8 - 3, var7 - 4, var8 + var4 + 3, var7 - 3, var10, var10);
            this.drawGradientRect(var8 - 3, var7 + var9 + 3, var8 + var4 + 3, var7 + var9 + 4, var10, var10);
            this.drawGradientRect(var8 - 3, var7 - 3, var8 + var4 + 3, var7 + var9 + 3, var10, var10);
            this.drawGradientRect(var8 - 4, var7 - 3, var8 - 3, var7 + var9 + 3, var10, var10);
            this.drawGradientRect(var8 + var4 + 3, var7 - 3, var8 + var4 + 4, var7 + var9 + 3, var10, var10);
            final int var11 = 1347420415;
            final int var12 = (var11 & 0xFEFEFE) >> 1 | (var11 & 0xFF000000);
            this.drawGradientRect(var8 - 3, var7 - 3 + 1, var8 - 3 + 1, var7 + var9 + 3 - 1, var11, var12);
            this.drawGradientRect(var8 + var4 + 2, var7 - 3 + 1, var8 + var4 + 3, var7 + var9 + 3 - 1, var11, var12);
            this.drawGradientRect(var8 - 3, var7 - 3, var8 + var4 + 3, var7 - 3 + 1, var11, var11);
            this.drawGradientRect(var8 - 3, var7 + var9 + 2, var8 + var4 + 3, var7 + var9 + 3, var12, var12);
            for (int var13 = 0; var13 < par1List.size(); ++var13) {
                final String var14 = par1List.get(var13);
                this.fontRenderer.drawStringWithShadow(var14, var8, var7, -1);
                if (var13 == 0) {
                    var7 += 2;
                }
                var7 += 10;
            }
            this.zLevel = 0.0f;
            RenderItem.zLevel = 0.0f;
            GL11.glEnable(2896);
            GL11.glEnable(2929);
            RenderHelper.enableStandardItemLighting();
            GL11.glEnable(32826);
        }
    }
    
    protected void drawGuiContainerForegroundLayer(final int par1, final int par2) {
    }
    
    protected abstract void drawGuiContainerBackgroundLayer(final float p0, final int p1, final int p2);
    
    private void drawSlotInventory(final Slot par1Slot) {
        final int var2 = par1Slot.xDisplayPosition;
        final int var3 = par1Slot.yDisplayPosition;
        ItemStack var4 = par1Slot.getStack();
        boolean var5 = false;
        boolean var6 = par1Slot == this.clickedSlot && this.draggedStack != null && !this.isRightMouseClick;
        final ItemStack var7 = Minecraft.thePlayer.inventory.getItemStack();
        String var8 = null;
        if (par1Slot == this.clickedSlot && this.draggedStack != null && this.isRightMouseClick && var4 != null) {
            final ItemStack copy;
            var4 = (copy = var4.copy());
            copy.stackSize /= 2;
        }
        else if (this.field_94076_q && this.field_94077_p.contains(par1Slot) && var7 != null) {
            if (this.field_94077_p.size() == 1) {
                return;
            }
            if (Container.func_94527_a(par1Slot, var7, true) && this.inventorySlots.func_94531_b(par1Slot)) {
                var4 = var7.copy();
                var5 = true;
                Container.func_94525_a(this.field_94077_p, this.field_94071_C, var4, (par1Slot.getStack() == null) ? 0 : par1Slot.getStack().stackSize);
                if (var4.stackSize > var4.getMaxStackSize()) {
                    var8 = new StringBuilder().append(EnumChatFormatting.YELLOW).append(var4.getMaxStackSize()).toString();
                    var4.stackSize = var4.getMaxStackSize();
                }
                if (var4.stackSize > par1Slot.getSlotStackLimit()) {
                    var8 = new StringBuilder().append(EnumChatFormatting.YELLOW).append(par1Slot.getSlotStackLimit()).toString();
                    var4.stackSize = par1Slot.getSlotStackLimit();
                }
            }
            else {
                this.field_94077_p.remove(par1Slot);
                this.func_94066_g();
            }
        }
        this.zLevel = 100.0f;
        RenderItem.zLevel = 100.0f;
        if (var4 == null) {
            final Icon var9 = par1Slot.getBackgroundIconIndex();
            if (var9 != null) {
                GL11.glDisable(2896);
                this.mc.renderEngine.bindTexture("/gui/items.png");
                this.drawTexturedModelRectFromIcon(var2, var3, var9, 16, 16);
                GL11.glEnable(2896);
                var6 = true;
            }
        }
        if (!var6) {
            if (var5) {
                Gui.drawRect(var2, var3, var2 + 16, var3 + 16, -2130706433);
            }
            GL11.glEnable(2929);
            RenderItem.renderItemAndEffectIntoGUI(this.fontRenderer, this.mc.renderEngine, var4, var2, var3);
            RenderItem.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, var4, var2, var3, var8);
        }
        RenderItem.zLevel = 0.0f;
        this.zLevel = 0.0f;
    }
    
    private void func_94066_g() {
        final ItemStack var1 = Minecraft.thePlayer.inventory.getItemStack();
        if (var1 != null && this.field_94076_q) {
            this.field_94069_F = var1.stackSize;
            for (final Slot var3 : this.field_94077_p) {
                final ItemStack var4 = var1.copy();
                final int var5 = (var3.getStack() == null) ? 0 : var3.getStack().stackSize;
                Container.func_94525_a(this.field_94077_p, this.field_94071_C, var4, var5);
                if (var4.stackSize > var4.getMaxStackSize()) {
                    var4.stackSize = var4.getMaxStackSize();
                }
                if (var4.stackSize > var3.getSlotStackLimit()) {
                    var4.stackSize = var3.getSlotStackLimit();
                }
                this.field_94069_F -= var4.stackSize - var5;
            }
        }
    }
    
    private Slot getSlotAtPosition(final int par1, final int par2) {
        for (int var3 = 0; var3 < this.inventorySlots.inventorySlots.size(); ++var3) {
            final Slot var4 = this.inventorySlots.inventorySlots.get(var3);
            if (this.isMouseOverSlot(var4, par1, par2)) {
                return var4;
            }
        }
        return null;
    }
    
    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) {
        super.mouseClicked(par1, par2, par3);
        final boolean var4 = par3 == this.mc.gameSettings.keyBindPickBlock.keyCode + 100;
        final Slot var5 = this.getSlotAtPosition(par1, par2);
        final long var6 = Minecraft.getSystemTime();
        this.field_94074_J = (this.field_94072_H == var5 && var6 - this.field_94070_G < 250L && this.field_94073_I == par3);
        this.field_94068_E = false;
        if (par3 == 0 || par3 == 1 || var4) {
            final int var7 = this.guiLeft;
            final int var8 = this.guiTop;
            final boolean var9 = par1 < var7 || par2 < var8 || par1 >= var7 + this.xSize || par2 >= var8 + this.ySize;
            int var10 = -1;
            if (var5 != null) {
                var10 = var5.slotNumber;
            }
            if (var9) {
                var10 = -999;
            }
            if (this.mc.gameSettings.touchscreen && var9 && Minecraft.thePlayer.inventory.getItemStack() == null) {
                this.mc.displayGuiScreen(null);
                return;
            }
            if (var10 != -1) {
                if (this.mc.gameSettings.touchscreen) {
                    if (var5 != null && var5.getHasStack()) {
                        this.clickedSlot = var5;
                        this.draggedStack = null;
                        this.isRightMouseClick = (par3 == 1);
                    }
                    else {
                        this.clickedSlot = null;
                    }
                }
                else if (!this.field_94076_q) {
                    if (Minecraft.thePlayer.inventory.getItemStack() == null) {
                        if (par3 == this.mc.gameSettings.keyBindPickBlock.keyCode + 100) {
                            this.handleMouseClick(var5, var10, par3, 3);
                        }
                        else {
                            final boolean var11 = var10 != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
                            byte var12 = 0;
                            if (var11) {
                                this.field_94075_K = ((var5 != null && var5.getHasStack()) ? var5.getStack() : null);
                                var12 = 1;
                            }
                            else if (var10 == -999) {
                                var12 = 4;
                            }
                            this.handleMouseClick(var5, var10, par3, var12);
                        }
                        this.field_94068_E = true;
                    }
                    else {
                        this.field_94076_q = true;
                        this.field_94067_D = par3;
                        this.field_94077_p.clear();
                        if (par3 == 0) {
                            this.field_94071_C = 0;
                        }
                        else if (par3 == 1) {
                            this.field_94071_C = 1;
                        }
                    }
                }
            }
        }
        this.field_94072_H = var5;
        this.field_94070_G = var6;
        this.field_94073_I = par3;
    }
    
    @Override
    protected void func_85041_a(final int par1, final int par2, final int par3, final long par4) {
        final Slot var6 = this.getSlotAtPosition(par1, par2);
        final ItemStack var7 = Minecraft.thePlayer.inventory.getItemStack();
        if (this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
            if (par3 == 0 || par3 == 1) {
                if (this.draggedStack == null) {
                    if (var6 != this.clickedSlot) {
                        this.draggedStack = this.clickedSlot.getStack().copy();
                    }
                }
                else if (this.draggedStack.stackSize > 1 && var6 != null && Container.func_94527_a(var6, this.draggedStack, false)) {
                    final long var8 = Minecraft.getSystemTime();
                    if (this.field_92033_y == var6) {
                        if (var8 - this.field_92032_z > 500L) {
                            this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, 0);
                            this.handleMouseClick(var6, var6.slotNumber, 1, 0);
                            this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, 0);
                            this.field_92032_z = var8 + 750L;
                            final ItemStack draggedStack = this.draggedStack;
                            --draggedStack.stackSize;
                        }
                    }
                    else {
                        this.field_92033_y = var6;
                        this.field_92032_z = var8;
                    }
                }
            }
        }
        else if (this.field_94076_q && var6 != null && var7 != null && var7.stackSize > this.field_94077_p.size() && Container.func_94527_a(var6, var7, true) && var6.isItemValid(var7) && this.inventorySlots.func_94531_b(var6)) {
            this.field_94077_p.add(var6);
            this.func_94066_g();
        }
    }
    
    @Override
    protected void mouseMovedOrUp(final int par1, final int par2, final int par3) {
        final Slot var4 = this.getSlotAtPosition(par1, par2);
        final int var5 = this.guiLeft;
        final int var6 = this.guiTop;
        final boolean var7 = par1 < var5 || par2 < var6 || par1 >= var5 + this.xSize || par2 >= var6 + this.ySize;
        int var8 = -1;
        if (var4 != null) {
            var8 = var4.slotNumber;
        }
        if (var7) {
            var8 = -999;
        }
        if (this.field_94074_J && var4 != null && par3 == 0 && this.inventorySlots.func_94530_a(null, var4)) {
            if (isShiftKeyDown()) {
                if (var4 != null && var4.inventory != null && this.field_94075_K != null) {
                    for (final Slot var10 : this.inventorySlots.inventorySlots) {
                        if (var10 != null && var10.canTakeStack(Minecraft.thePlayer) && var10.getHasStack() && var10.inventory == var4.inventory && Container.func_94527_a(var10, this.field_94075_K, true)) {
                            this.handleMouseClick(var10, var10.slotNumber, par3, 1);
                        }
                    }
                }
            }
            else {
                this.handleMouseClick(var4, var8, par3, 6);
            }
            this.field_94074_J = false;
            this.field_94070_G = 0L;
        }
        else {
            if (this.field_94076_q && this.field_94067_D != par3) {
                this.field_94076_q = false;
                this.field_94077_p.clear();
                this.field_94068_E = true;
                return;
            }
            if (this.field_94068_E) {
                this.field_94068_E = false;
                return;
            }
            if (this.clickedSlot != null && this.mc.gameSettings.touchscreen) {
                if (par3 == 0 || par3 == 1) {
                    if (this.draggedStack == null && var4 != this.clickedSlot) {
                        this.draggedStack = this.clickedSlot.getStack();
                    }
                    final boolean var11 = Container.func_94527_a(var4, this.draggedStack, false);
                    if (var8 != -1 && this.draggedStack != null && var11) {
                        this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, par3, 0);
                        this.handleMouseClick(var4, var8, 0, 0);
                        if (Minecraft.thePlayer.inventory.getItemStack() != null) {
                            this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, par3, 0);
                            this.field_85049_r = par1 - var5;
                            this.field_85048_s = par2 - var6;
                            this.returningStackDestSlot = this.clickedSlot;
                            this.returningStack = this.draggedStack;
                            this.returningStackTime = Minecraft.getSystemTime();
                        }
                        else {
                            this.returningStack = null;
                        }
                    }
                    else if (this.draggedStack != null) {
                        this.field_85049_r = par1 - var5;
                        this.field_85048_s = par2 - var6;
                        this.returningStackDestSlot = this.clickedSlot;
                        this.returningStack = this.draggedStack;
                        this.returningStackTime = Minecraft.getSystemTime();
                    }
                    this.draggedStack = null;
                    this.clickedSlot = null;
                }
            }
            else if (this.field_94076_q && !this.field_94077_p.isEmpty()) {
                this.handleMouseClick(null, -999, Container.func_94534_d(0, this.field_94071_C), 5);
                for (final Slot var10 : this.field_94077_p) {
                    this.handleMouseClick(var10, var10.slotNumber, Container.func_94534_d(1, this.field_94071_C), 5);
                }
                this.handleMouseClick(null, -999, Container.func_94534_d(2, this.field_94071_C), 5);
            }
            else if (Minecraft.thePlayer.inventory.getItemStack() != null) {
                if (par3 == this.mc.gameSettings.keyBindPickBlock.keyCode + 100) {
                    this.handleMouseClick(var4, var8, par3, 3);
                }
                else {
                    final boolean var11 = var8 != -999 && (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
                    if (var11) {
                        this.field_94075_K = ((var4 != null && var4.getHasStack()) ? var4.getStack() : null);
                    }
                    this.handleMouseClick(var4, var8, par3, var11 ? 1 : 0);
                }
            }
        }
        if (Minecraft.thePlayer.inventory.getItemStack() == null) {
            this.field_94070_G = 0L;
        }
        this.field_94076_q = false;
    }
    
    private boolean isMouseOverSlot(final Slot par1Slot, final int par2, final int par3) {
        return this.isPointInRegion(par1Slot.xDisplayPosition, par1Slot.yDisplayPosition, 16, 16, par2, par3);
    }
    
    protected boolean isPointInRegion(final int par1, final int par2, final int par3, final int par4, int par5, int par6) {
        final int var7 = this.guiLeft;
        final int var8 = this.guiTop;
        par5 -= var7;
        par6 -= var8;
        return par5 >= par1 - 1 && par5 < par1 + par3 + 1 && par6 >= par2 - 1 && par6 < par2 + par4 + 1;
    }
    
    protected void handleMouseClick(final Slot par1Slot, int par2, final int par3, final int par4) {
        if (par1Slot != null) {
            par2 = par1Slot.slotNumber;
        }
        this.mc.playerController.windowClick(this.inventorySlots.windowId, par2, par3, par4, Minecraft.thePlayer);
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
        if (par2 == 1 || par2 == this.mc.gameSettings.keyBindInventory.keyCode) {
            Minecraft.thePlayer.closeScreen();
        }
        this.checkHotbarKeys(par2);
        if (this.theSlot != null && this.theSlot.getHasStack()) {
            if (par2 == this.mc.gameSettings.keyBindPickBlock.keyCode) {
                this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, 0, 3);
            }
            else if (par2 == this.mc.gameSettings.keyBindDrop.keyCode) {
                this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, GuiScreen.isCtrlKeyDown() ? 1 : 0, 4);
            }
        }
    }
    
    protected boolean checkHotbarKeys(final int par1) {
        if (Minecraft.thePlayer.inventory.getItemStack() == null && this.theSlot != null) {
            for (int var2 = 0; var2 < 9; ++var2) {
                if (par1 == 2 + var2) {
                    this.handleMouseClick(this.theSlot, this.theSlot.slotNumber, var2, 2);
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public void onGuiClosed() {
        if (Minecraft.thePlayer != null) {
            this.inventorySlots.onCraftGuiClosed(Minecraft.thePlayer);
        }
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        if (!Minecraft.thePlayer.isEntityAlive() || Minecraft.thePlayer.isDead) {
            Minecraft.thePlayer.closeScreen();
        }
    }
}
