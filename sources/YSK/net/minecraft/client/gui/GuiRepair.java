package net.minecraft.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.util.*;
import org.lwjgl.input.*;
import io.netty.buffer.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import java.io.*;
import net.minecraft.world.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.*;
import java.util.*;
import net.minecraft.inventory.*;

public class GuiRepair extends GuiContainer implements ICrafting
{
    private InventoryPlayer playerInventory;
    private GuiTextField nameField;
    private ContainerRepair anvil;
    private static final ResourceLocation anvilResource;
    private static final String[] I;
    
    @Override
    public void initGui() {
        super.initGui();
        Keyboard.enableRepeatEvents((boolean)(" ".length() != 0));
        (this.nameField = new GuiTextField("".length(), this.fontRendererObj, (this.width - this.xSize) / "  ".length() + (0x21 ^ 0x1F), (this.height - this.ySize) / "  ".length() + (0xAD ^ 0xB5), 0x64 ^ 0x3, 0x8B ^ 0x87)).setTextColor(-" ".length());
        this.nameField.setDisabledTextColour(-" ".length());
        this.nameField.setEnableBackgroundDrawing("".length() != 0);
        this.nameField.setMaxStringLength(0x2B ^ 0x35);
        this.inventorySlots.removeCraftingFromCrafters(this);
        this.inventorySlots.onCraftGuiOpened(this);
    }
    
    private void renameItem() {
        String text = this.nameField.getText();
        final Slot slot = this.anvil.getSlot("".length());
        if (slot != null && slot.getHasStack() && !slot.getStack().hasDisplayName() && text.equals(slot.getStack().getDisplayName())) {
            text = GuiRepair.I[0x64 ^ 0x60];
        }
        this.anvil.updateItemName(text);
        this.mc.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload(GuiRepair.I[0x5 ^ 0x0], new PacketBuffer(Unpooled.buffer()).writeString(text)));
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (this.nameField.textboxKeyTyped(c, n)) {
            this.renameItem();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            super.keyTyped(c, n);
        }
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
            if (3 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public GuiRepair(final InventoryPlayer playerInventory, final World world) {
        super(new ContainerRepair(playerInventory, world, Minecraft.getMinecraft().thePlayer));
        this.playerInventory = playerInventory;
        this.anvil = (ContainerRepair)this.inventorySlots;
    }
    
    @Override
    public void sendSlotContents(final Container container, final int n, final ItemStack itemStack) {
        if (n == 0) {
            final GuiTextField nameField = this.nameField;
            String displayName;
            if (itemStack == null) {
                displayName = GuiRepair.I[0x86 ^ 0x80];
                "".length();
                if (1 >= 2) {
                    throw null;
                }
            }
            else {
                displayName = itemStack.getDisplayName();
            }
            nameField.setText(displayName);
            final GuiTextField nameField2 = this.nameField;
            int enabled;
            if (itemStack != null) {
                enabled = " ".length();
                "".length();
                if (false == true) {
                    throw null;
                }
            }
            else {
                enabled = "".length();
            }
            nameField2.setEnabled(enabled != 0);
            if (itemStack != null) {
                this.renameItem();
            }
        }
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
        this.fontRendererObj.drawString(I18n.format(GuiRepair.I[" ".length()], new Object["".length()]), 0x4A ^ 0x76, 0x81 ^ 0x87, 1119497 + 1525950 + 1399903 + 165402);
        if (this.anvil.maximumCost > 0) {
            int n3 = 1753406 + 5309213 - 1688902 + 3080203;
            int n4 = " ".length();
            final String s = GuiRepair.I["  ".length()];
            final Object[] array = new Object[" ".length()];
            array["".length()] = this.anvil.maximumCost;
            String s2 = I18n.format(s, array);
            if (this.anvil.maximumCost >= (0x1C ^ 0x34) && !this.mc.thePlayer.capabilities.isCreativeMode) {
                s2 = I18n.format(GuiRepair.I["   ".length()], new Object["".length()]);
                n3 = 10983462 + 11818813 - 8168429 + 2102506;
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
            }
            else if (!this.anvil.getSlot("  ".length()).getHasStack()) {
                n4 = "".length();
                "".length();
                if (2 < 0) {
                    throw null;
                }
            }
            else if (!this.anvil.getSlot("  ".length()).canTakeStack(this.playerInventory.player)) {
                n3 = 467860 + 14829818 - 685819 + 2124493;
            }
            if (n4 != 0) {
                final int n5 = -(2103310 + 12223323 - 13342736 + 15793319) | (n3 & 16045080 + 7880924 - 11077555 + 3731387) >> "  ".length() | (n3 & -(7283066 + 11262115 - 7059676 + 5291711));
                final int n6 = this.xSize - (0x24 ^ 0x2C) - this.fontRendererObj.getStringWidth(s2);
                final int n7 = 0x69 ^ 0x2A;
                if (this.fontRendererObj.getUnicodeFlag()) {
                    Gui.drawRect(n6 - "   ".length(), n7 - "  ".length(), this.xSize - (0x6B ^ 0x6C), n7 + (0x58 ^ 0x52), -(3527962 + 4171916 - 4819835 + 13897173));
                    Gui.drawRect(n6 - "  ".length(), n7 - " ".length(), this.xSize - (0x6 ^ 0xE), n7 + (0x92 ^ 0x9B), -(10745469 + 5846304 - 3761108 + 64764));
                    "".length();
                    if (3 < 3) {
                        throw null;
                    }
                }
                else {
                    this.fontRendererObj.drawString(s2, n6, n7 + " ".length(), n5);
                    this.fontRendererObj.drawString(s2, n6 + " ".length(), n7, n5);
                    this.fontRendererObj.drawString(s2, n6 + " ".length(), n7 + " ".length(), n5);
                }
                this.fontRendererObj.drawString(s2, n6, n7, n3);
            }
        }
        GlStateManager.enableLighting();
    }
    
    @Override
    public void updateCraftingInventory(final Container container, final List<ItemStack> list) {
        this.sendSlotContents(container, "".length(), container.getSlot("".length()).getStack());
    }
    
    private static void I() {
        (I = new String[0x8A ^ 0x8D])["".length()] = I("#\u000b2\u001d\f%\u000b9F\u001e\"\u0007e\n\u00169\u001a+\u0000\u00172\u001ce\b\u0017!\u0007&G\t9\t", "WnJiy");
        GuiRepair.I[" ".length()] = I("\u0013\u0018 \u00076\u0019\u0019+\u0001y\u0002\u0012>\u0012>\u0002", "pwNsW");
        GuiRepair.I["  ".length()] = I("\u001a)\n\u0006\u000b\u0010(\u0001\u0000D\u000b#\u0014\u0013\u0003\u000bh\u0007\u001d\u0019\r", "yFdrj");
        GuiRepair.I["   ".length()] = I("\b\u0002\u001f\u001f\t\u0002\u0003\u0014\u0019F\u0019\b\u0001\n\u0001\u0019C\u0014\u0013\u0018\u000e\u0003\u0002\u0002\u001e\u000e", "kmqkh");
        GuiRepair.I[0x90 ^ 0x94] = I("", "KxfvG");
        GuiRepair.I[0x75 ^ 0x70] = I("\u001e &/.6\u000e\u0014\u000776", "ScZfZ");
        GuiRepair.I[0xC ^ 0xA] = I("", "uSjvm");
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        super.drawScreen(n, n2, n3);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
        this.nameField.drawTextBox();
    }
    
    @Override
    public void sendProgressBarUpdate(final Container container, final int n, final int n2) {
    }
    
    static {
        I();
        anvilResource = new ResourceLocation(GuiRepair.I["".length()]);
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        this.nameField.mouseClicked(n, n2, n3);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float n, final int n2, final int n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiRepair.anvilResource);
        final int n4 = (this.width - this.xSize) / "  ".length();
        final int n5 = (this.height - this.ySize) / "  ".length();
        this.drawTexturedModalRect(n4, n5, "".length(), "".length(), this.xSize, this.ySize);
        final int n6 = n4 + (0x5F ^ 0x64);
        final int n7 = n5 + (0x1A ^ 0xE);
        final int length = "".length();
        final int ySize = this.ySize;
        int length2;
        if (this.anvil.getSlot("".length()).getHasStack()) {
            length2 = "".length();
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            length2 = (0x54 ^ 0x44);
        }
        this.drawTexturedModalRect(n6, n7, length, ySize + length2, 0x51 ^ 0x3F, 0x93 ^ 0x83);
        if ((this.anvil.getSlot("".length()).getHasStack() || this.anvil.getSlot(" ".length()).getHasStack()) && !this.anvil.getSlot("  ".length()).getHasStack()) {
            this.drawTexturedModalRect(n4 + (0x79 ^ 0x1A), n5 + (0xB4 ^ 0x99), this.xSize, "".length(), 0x44 ^ 0x58, 0xBE ^ 0xAB);
        }
    }
    
    @Override
    public void func_175173_a(final Container container, final IInventory inventory) {
    }
    
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents((boolean)("".length() != 0));
        this.inventorySlots.removeCraftingFromCrafters(this);
    }
}
