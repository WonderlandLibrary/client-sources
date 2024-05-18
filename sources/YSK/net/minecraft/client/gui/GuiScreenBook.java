package net.minecraft.client.gui;

import net.minecraft.entity.player.*;
import org.lwjgl.input.*;
import java.util.*;
import net.minecraft.event.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.*;
import net.minecraft.item.*;
import com.google.gson.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import java.io.*;
import net.minecraft.nbt.*;
import net.minecraft.init.*;
import io.netty.buffer.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.*;

public class GuiScreenBook extends GuiScreen
{
    private static final Logger logger;
    private static final String[] I;
    private NextPageButton buttonNextPage;
    private final EntityPlayer editingPlayer;
    private final ItemStack bookObj;
    private GuiButton buttonFinalize;
    private List<IChatComponent> field_175386_A;
    private int currPage;
    private GuiButton buttonSign;
    private int bookImageWidth;
    private int field_175387_B;
    private int bookImageHeight;
    private boolean bookGettingSigned;
    private static final ResourceLocation bookGuiTextures;
    private String bookTitle;
    private int bookTotalPages;
    private GuiButton buttonDone;
    private int updateCount;
    private NBTTagList bookPages;
    private GuiButton buttonCancel;
    private NextPageButton buttonPreviousPage;
    private final boolean bookIsUnsigned;
    private boolean bookIsModified;
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)("".length() != 0));
    }
    
    public IChatComponent func_175385_b(final int n, final int n2) {
        if (this.field_175386_A == null) {
            return null;
        }
        final int n3 = n - (this.width - this.bookImageWidth) / "  ".length() - (0x50 ^ 0x74);
        final int n4 = n2 - "  ".length() - (0x7E ^ 0x6E) - (0x19 ^ 0x9);
        if (n3 < 0 || n4 < 0) {
            return null;
        }
        final int min = Math.min((1 + 69 - 47 + 105) / this.fontRendererObj.FONT_HEIGHT, this.field_175386_A.size());
        if (n3 <= (0xDD ^ 0xA9) && n4 < this.mc.fontRendererObj.FONT_HEIGHT * min + min) {
            final int n5 = n4 / this.mc.fontRendererObj.FONT_HEIGHT;
            if (n5 >= 0 && n5 < this.field_175386_A.size()) {
                final IChatComponent chatComponent = this.field_175386_A.get(n5);
                int length = "".length();
                final Iterator<IChatComponent> iterator = chatComponent.iterator();
                "".length();
                if (2 != 2) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final IChatComponent chatComponent2 = iterator.next();
                    if (chatComponent2 instanceof ChatComponentText) {
                        length += this.mc.fontRendererObj.getStringWidth(((ChatComponentText)chatComponent2).getChatComponentText_TextValue());
                        if (length > n3) {
                            return chatComponent2;
                        }
                        continue;
                    }
                }
            }
            return null;
        }
        return null;
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        this.updateCount += " ".length();
    }
    
    @Override
    protected boolean handleComponentClick(final IChatComponent chatComponent) {
        ClickEvent chatClickEvent;
        if (chatComponent == null) {
            chatClickEvent = null;
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else {
            chatClickEvent = chatComponent.getChatStyle().getChatClickEvent();
        }
        final ClickEvent clickEvent = chatClickEvent;
        if (clickEvent == null) {
            return "".length() != 0;
        }
        if (clickEvent.getAction() == ClickEvent.Action.CHANGE_PAGE) {
            final String value = clickEvent.getValue();
            try {
                final int currPage = Integer.parseInt(value) - " ".length();
                if (currPage >= 0 && currPage < this.bookTotalPages && currPage != this.currPage) {
                    this.currPage = currPage;
                    this.updateButtons();
                    return " ".length() != 0;
                }
            }
            catch (Throwable t) {}
            return "".length() != 0;
        }
        final boolean handleComponentClick = super.handleComponentClick(chatComponent);
        if (handleComponentClick && clickEvent.getAction() == ClickEvent.Action.RUN_COMMAND) {
            this.mc.displayGuiScreen(null);
        }
        return handleComponentClick;
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiScreenBook.bookGuiTextures);
        final int n4 = (this.width - this.bookImageWidth) / "  ".length();
        final int length = "  ".length();
        this.drawTexturedModalRect(n4, length, "".length(), "".length(), this.bookImageWidth, this.bookImageHeight);
        if (this.bookGettingSigned) {
            String s = this.bookTitle;
            if (this.bookIsUnsigned) {
                if (this.updateCount / (0x24 ^ 0x22) % "  ".length() == 0) {
                    s = String.valueOf(s) + EnumChatFormatting.BLACK + GuiScreenBook.I[0x5E ^ 0x4D];
                    "".length();
                    if (2 <= 0) {
                        throw null;
                    }
                }
                else {
                    s = String.valueOf(s) + EnumChatFormatting.GRAY + GuiScreenBook.I[0x75 ^ 0x61];
                }
            }
            final String format = I18n.format(GuiScreenBook.I[0x84 ^ 0x91], new Object["".length()]);
            this.fontRendererObj.drawString(format, n4 + (0x28 ^ 0xC) + ((0xFE ^ 0x8A) - this.fontRendererObj.getStringWidth(format)) / "  ".length(), length + (0x27 ^ 0x37) + (0x57 ^ 0x47), "".length());
            this.fontRendererObj.drawString(s, n4 + (0x75 ^ 0x51) + ((0x4E ^ 0x3A) - this.fontRendererObj.getStringWidth(s)) / "  ".length(), length + (0xBD ^ 0x8D), "".length());
            final String s2 = GuiScreenBook.I[0x39 ^ 0x2F];
            final Object[] array = new Object[" ".length()];
            array["".length()] = this.editingPlayer.getName();
            final String format2 = I18n.format(s2, array);
            this.fontRendererObj.drawString(EnumChatFormatting.DARK_GRAY + format2, n4 + (0x9F ^ 0xBB) + ((0x46 ^ 0x32) - this.fontRendererObj.getStringWidth(format2)) / "  ".length(), length + (0x57 ^ 0x67) + (0x1C ^ 0x16), "".length());
            this.fontRendererObj.drawSplitString(I18n.format(GuiScreenBook.I[0x82 ^ 0x95], new Object["".length()]), n4 + (0x62 ^ 0x46), length + (0x26 ^ 0x76), 0xF ^ 0x7B, "".length());
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        else {
            final String s3 = GuiScreenBook.I[0x55 ^ 0x4D];
            final Object[] array2 = new Object["  ".length()];
            array2["".length()] = this.currPage + " ".length();
            array2[" ".length()] = this.bookTotalPages;
            final String format3 = I18n.format(s3, array2);
            String s4 = GuiScreenBook.I[0xAB ^ 0xB2];
            if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount()) {
                s4 = this.bookPages.getStringTagAt(this.currPage);
            }
            if (this.bookIsUnsigned) {
                if (this.fontRendererObj.getBidiFlag()) {
                    s4 = String.valueOf(s4) + GuiScreenBook.I[0x7B ^ 0x61];
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
                else if (this.updateCount / (0xA2 ^ 0xA4) % "  ".length() == 0) {
                    s4 = String.valueOf(s4) + EnumChatFormatting.BLACK + GuiScreenBook.I[0x19 ^ 0x2];
                    "".length();
                    if (1 <= -1) {
                        throw null;
                    }
                }
                else {
                    s4 = String.valueOf(s4) + EnumChatFormatting.GRAY + GuiScreenBook.I[0x1A ^ 0x6];
                    "".length();
                    if (2 < 2) {
                        throw null;
                    }
                }
            }
            else if (this.field_175387_B != this.currPage) {
                Label_0981: {
                    if (ItemEditableBook.validBookTagContents(this.bookObj.getTagCompound())) {
                        try {
                            final IChatComponent jsonToComponent = IChatComponent.Serializer.jsonToComponent(s4);
                            List<IChatComponent> func_178908_a;
                            if (jsonToComponent != null) {
                                func_178908_a = GuiUtilRenderComponents.func_178908_a(jsonToComponent, 0xB4 ^ 0xC0, this.fontRendererObj, " ".length() != 0, " ".length() != 0);
                                "".length();
                                if (3 < -1) {
                                    throw null;
                                }
                            }
                            else {
                                func_178908_a = null;
                            }
                            this.field_175386_A = func_178908_a;
                            "".length();
                            if (4 <= -1) {
                                throw null;
                            }
                            break Label_0981;
                        }
                        catch (JsonParseException ex) {
                            this.field_175386_A = null;
                            "".length();
                            if (false) {
                                throw null;
                            }
                            break Label_0981;
                        }
                    }
                    this.field_175386_A = (List<IChatComponent>)Lists.newArrayList((Iterable)new ChatComponentText(String.valueOf(EnumChatFormatting.DARK_RED.toString()) + GuiScreenBook.I[0xB4 ^ 0xA9]));
                }
                this.field_175387_B = this.currPage;
            }
            this.fontRendererObj.drawString(format3, n4 - this.fontRendererObj.getStringWidth(format3) + this.bookImageWidth - (0x90 ^ 0xBC), length + (0xF ^ 0x1F), "".length());
            if (this.field_175386_A == null) {
                this.fontRendererObj.drawSplitString(s4, n4 + (0x2B ^ 0xF), length + (0x68 ^ 0x78) + (0xAF ^ 0xBF), 0xF6 ^ 0x82, "".length());
                "".length();
                if (4 < 0) {
                    throw null;
                }
            }
            else {
                final int min = Math.min((25 + 117 - 93 + 79) / this.fontRendererObj.FONT_HEIGHT, this.field_175386_A.size());
                int i = "".length();
                "".length();
                if (2 < 0) {
                    throw null;
                }
                while (i < min) {
                    this.fontRendererObj.drawString(this.field_175386_A.get(i).getUnformattedText(), n4 + (0x68 ^ 0x4C), length + (0x6D ^ 0x7D) + (0x51 ^ 0x41) + i * this.fontRendererObj.FONT_HEIGHT, "".length());
                    ++i;
                }
                final IChatComponent func_175385_b = this.func_175385_b(n, n2);
                if (func_175385_b != null) {
                    this.handleComponentHover(func_175385_b, n, n2);
                }
            }
        }
        super.drawScreen(n, n2, n3);
    }
    
    private void keyTypedInBook(final char c, final int n) {
        if (GuiScreen.isKeyComboCtrlV(n)) {
            this.pageInsertIntoCurrent(GuiScreen.getClipboardString());
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else {
            switch (n) {
                case 14: {
                    final String pageGetCurrent = this.pageGetCurrent();
                    if (pageGetCurrent.length() > 0) {
                        this.pageSetCurrent(pageGetCurrent.substring("".length(), pageGetCurrent.length() - " ".length()));
                    }
                }
                case 28:
                case 156: {
                    this.pageInsertIntoCurrent(GuiScreenBook.I[0x2C ^ 0x3C]);
                }
                default: {
                    if (ChatAllowedCharacters.isAllowedCharacter(c)) {
                        this.pageInsertIntoCurrent(Character.toString(c));
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        super.keyTyped(c, n);
        if (this.bookIsUnsigned) {
            if (this.bookGettingSigned) {
                this.keyTypedInTitle(c, n);
                "".length();
                if (0 >= 4) {
                    throw null;
                }
            }
            else {
                this.keyTypedInBook(c, n);
            }
        }
    }
    
    private void sendBookToServer(final boolean b) throws IOException {
        if (this.bookIsUnsigned && this.bookIsModified && this.bookPages != null) {
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (this.bookPages.tagCount() > " ".length()) {
                if (this.bookPages.getStringTagAt(this.bookPages.tagCount() - " ".length()).length() != 0) {
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                    break;
                }
                else {
                    this.bookPages.removeTag(this.bookPages.tagCount() - " ".length());
                }
            }
            if (this.bookObj.hasTagCompound()) {
                this.bookObj.getTagCompound().setTag(GuiScreenBook.I[0x23 ^ 0x2A], this.bookPages);
                "".length();
                if (4 <= 1) {
                    throw null;
                }
            }
            else {
                this.bookObj.setTagInfo(GuiScreenBook.I[0xB ^ 0x1], this.bookPages);
            }
            String s = GuiScreenBook.I[0x60 ^ 0x6B];
            if (b) {
                s = GuiScreenBook.I[0xA4 ^ 0xA8];
                this.bookObj.setTagInfo(GuiScreenBook.I[0x62 ^ 0x6F], new NBTTagString(this.editingPlayer.getName()));
                this.bookObj.setTagInfo(GuiScreenBook.I[0x9C ^ 0x92], new NBTTagString(this.bookTitle.trim()));
                int i = "".length();
                "".length();
                if (4 <= 0) {
                    throw null;
                }
                while (i < this.bookPages.tagCount()) {
                    this.bookPages.set(i, new NBTTagString(IChatComponent.Serializer.componentToJson(new ChatComponentText(this.bookPages.getStringTagAt(i)))));
                    ++i;
                }
                this.bookObj.setItem(Items.written_book);
            }
            final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
            packetBuffer.writeItemStackToBuffer(this.bookObj);
            this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload(s, packetBuffer));
        }
    }
    
    public GuiScreenBook(final EntityPlayer editingPlayer, final ItemStack bookObj, final boolean bookIsUnsigned) {
        this.bookImageWidth = 186 + 86 - 182 + 102;
        this.bookImageHeight = 71 + 187 - 256 + 190;
        this.bookTotalPages = " ".length();
        this.bookTitle = GuiScreenBook.I[" ".length()];
        this.field_175387_B = -" ".length();
        this.editingPlayer = editingPlayer;
        this.bookObj = bookObj;
        this.bookIsUnsigned = bookIsUnsigned;
        if (bookObj.hasTagCompound()) {
            this.bookPages = bookObj.getTagCompound().getTagList(GuiScreenBook.I["  ".length()], 0x45 ^ 0x4D);
            if (this.bookPages != null) {
                this.bookPages = (NBTTagList)this.bookPages.copy();
                this.bookTotalPages = this.bookPages.tagCount();
                if (this.bookTotalPages < " ".length()) {
                    this.bookTotalPages = " ".length();
                }
            }
        }
        if (this.bookPages == null && bookIsUnsigned) {
            (this.bookPages = new NBTTagList()).appendTag(new NBTTagString(GuiScreenBook.I["   ".length()]));
            this.bookTotalPages = " ".length();
        }
    }
    
    private void keyTypedInTitle(final char c, final int n) throws IOException {
        switch (n) {
            case 14: {
                if (!this.bookTitle.isEmpty()) {
                    this.bookTitle = this.bookTitle.substring("".length(), this.bookTitle.length() - " ".length());
                    this.updateButtons();
                }
            }
            case 28:
            case 156: {
                if (!this.bookTitle.isEmpty()) {
                    this.sendBookToServer(" ".length() != 0);
                    this.mc.displayGuiScreen(null);
                }
            }
            default: {
                if (this.bookTitle.length() < (0x30 ^ 0x20) && ChatAllowedCharacters.isAllowedCharacter(c)) {
                    this.bookTitle = String.valueOf(this.bookTitle) + Character.toString(c);
                    this.updateButtons();
                    this.bookIsModified = (" ".length() != 0);
                }
            }
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private void pageSetCurrent(final String s) {
        if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount()) {
            this.bookPages.set(this.currPage, new NBTTagString(s));
            this.bookIsModified = (" ".length() != 0);
        }
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        if (n3 == 0 && this.handleComponentClick(this.func_175385_b(n, n2))) {
            return;
        }
        super.mouseClicked(n, n2, n3);
    }
    
    static ResourceLocation access$0() {
        return GuiScreenBook.bookGuiTextures;
    }
    
    private void updateButtons() {
        final NextPageButton buttonNextPage = this.buttonNextPage;
        int visible;
        if (!this.bookGettingSigned && (this.currPage < this.bookTotalPages - " ".length() || this.bookIsUnsigned)) {
            visible = " ".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            visible = "".length();
        }
        buttonNextPage.visible = (visible != 0);
        final NextPageButton buttonPreviousPage = this.buttonPreviousPage;
        int visible2;
        if (!this.bookGettingSigned && this.currPage > 0) {
            visible2 = " ".length();
            "".length();
            if (0 == 3) {
                throw null;
            }
        }
        else {
            visible2 = "".length();
        }
        buttonPreviousPage.visible = (visible2 != 0);
        final GuiButton buttonDone = this.buttonDone;
        int visible3;
        if (this.bookIsUnsigned && this.bookGettingSigned) {
            visible3 = "".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            visible3 = " ".length();
        }
        buttonDone.visible = (visible3 != 0);
        if (this.bookIsUnsigned) {
            final GuiButton buttonSign = this.buttonSign;
            int visible4;
            if (this.bookGettingSigned) {
                visible4 = "".length();
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            else {
                visible4 = " ".length();
            }
            buttonSign.visible = (visible4 != 0);
            this.buttonCancel.visible = this.bookGettingSigned;
            this.buttonFinalize.visible = this.bookGettingSigned;
            final GuiButton buttonFinalize = this.buttonFinalize;
            int enabled;
            if (this.bookTitle.trim().length() > 0) {
                enabled = " ".length();
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                enabled = "".length();
            }
            buttonFinalize.enabled = (enabled != 0);
        }
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents((boolean)(" ".length() != 0));
        if (this.bookIsUnsigned) {
            this.buttonList.add(this.buttonSign = new GuiButton("   ".length(), this.width / "  ".length() - (0x2C ^ 0x48), (0xBB ^ 0xBF) + this.bookImageHeight, 0x4A ^ 0x28, 0x2F ^ 0x3B, I18n.format(GuiScreenBook.I[0x2B ^ 0x2F], new Object["".length()])));
            this.buttonList.add(this.buttonDone = new GuiButton("".length(), this.width / "  ".length() + "  ".length(), (0xAD ^ 0xA9) + this.bookImageHeight, 0xF5 ^ 0x97, 0x23 ^ 0x37, I18n.format(GuiScreenBook.I[0x2B ^ 0x2E], new Object["".length()])));
            this.buttonList.add(this.buttonFinalize = new GuiButton(0xAF ^ 0xAA, this.width / "  ".length() - (0x6C ^ 0x8), (0x8D ^ 0x89) + this.bookImageHeight, 0xDA ^ 0xB8, 0x81 ^ 0x95, I18n.format(GuiScreenBook.I[0x44 ^ 0x42], new Object["".length()])));
            this.buttonList.add(this.buttonCancel = new GuiButton(0x8 ^ 0xC, this.width / "  ".length() + "  ".length(), (0x2F ^ 0x2B) + this.bookImageHeight, 0xEA ^ 0x88, 0x17 ^ 0x3, I18n.format(GuiScreenBook.I[0x3A ^ 0x3D], new Object["".length()])));
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            this.buttonList.add(this.buttonDone = new GuiButton("".length(), this.width / "  ".length() - (0x27 ^ 0x43), (0x4B ^ 0x4F) + this.bookImageHeight, 164 + 33 - 57 + 60, 0x5F ^ 0x4B, I18n.format(GuiScreenBook.I[0x38 ^ 0x30], new Object["".length()])));
        }
        final int n = (this.width - this.bookImageWidth) / "  ".length();
        final int length = "  ".length();
        this.buttonList.add(this.buttonNextPage = new NextPageButton(" ".length(), n + (0xDD ^ 0xA5), length + (2 + 58 - 24 + 118), " ".length() != 0));
        this.buttonList.add(this.buttonPreviousPage = new NextPageButton("  ".length(), n + (0x19 ^ 0x3F), length + (42 + 66 + 32 + 14), "".length() != 0));
        this.updateButtons();
    }
    
    private String pageGetCurrent() {
        String stringTag;
        if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount()) {
            stringTag = this.bookPages.getStringTagAt(this.currPage);
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        else {
            stringTag = GuiScreenBook.I[0x6D ^ 0x7C];
        }
        return stringTag;
    }
    
    private static void I() {
        (I = new String[0x46 ^ 0x58])["".length()] = I("\u0004,+\u001a?\u0002, A-\u0005 |\f%\u001f\"}\u001e$\u0017", "pISnJ");
        GuiScreenBook.I[" ".length()] = I("", "HWEOl");
        GuiScreenBook.I["  ".length()] = I("$\u0018!\u0012\u0007", "TyFwt");
        GuiScreenBook.I["   ".length()] = I("", "dJrHi");
        GuiScreenBook.I[0x96 ^ 0x92] = I("\u0015%\u001a<}\u0004#\u00129\u0011\u0002>\u00018=", "wJuWS");
        GuiScreenBook.I[0x3E ^ 0x3B] = I("-\u0004\u0019^\n%\u001f\u0015", "Jqppn");
        GuiScreenBook.I[0x1C ^ 0x1A] = I("6\f\u0006$i2\n\u0007.+=\u0019\f\r2 \u0017\u0006!", "TciOG");
        GuiScreenBook.I[0x44 ^ 0x43] = I(")3\u0001T\u0014/(\u000b\u001f\u001b", "NFhzw");
        GuiScreenBook.I[0x13 ^ 0x1B] = I("5\u0019\u000f]<=\u0002\u0003", "RlfsX");
        GuiScreenBook.I[0x33 ^ 0x3A] = I("\u00192\u00064\u0006", "iSaQu");
        GuiScreenBook.I[0x0 ^ 0xA] = I("?\u0014,!*", "OuKDY");
        GuiScreenBook.I[0x2A ^ 0x21] = I("=91\u0018\u0004\u0014\u00139", "pzMZA");
        GuiScreenBook.I[0xCB ^ 0xC7] = I("\u001e\u000e7 8:*%", "SMKbk");
        GuiScreenBook.I[0x3F ^ 0x32] = I("5\u0019\u001f+5&", "TlkCZ");
        GuiScreenBook.I[0x82 ^ 0x8C] = I("0=\u001c\u0007\u0010", "DThku");
        GuiScreenBook.I[0x37 ^ 0x38] = I("", "gfrkd");
        GuiScreenBook.I[0xD3 ^ 0xC3] = I("~", "tGjeb");
        GuiScreenBook.I[0xBE ^ 0xAF] = I("", "yQivZ");
        GuiScreenBook.I[0x60 ^ 0x72] = I("\u0007", "XKCdc");
        GuiScreenBook.I[0x72 ^ 0x61] = I("\u0014", "KRnMi");
        GuiScreenBook.I[0x2D ^ 0x39] = I("0", "oXXDS");
        GuiScreenBook.I[0x7C ^ 0x69] = I("0$<\u001c[7/:\u0003!;??\u0012", "RKSwu");
        GuiScreenBook.I[0x50 ^ 0x46] = I("\u0000\u0018\u001c/I\u0000\u000e21\u0013\n\u0018\u0001", "bwsDg");
        GuiScreenBook.I[0x2C ^ 0x3B] = I("\u0017\u001661J\u0013\u00107;\b\u001c\u0003<\r\u0005\u0007\u001704\u0003", "uyYZd");
        GuiScreenBook.I[0x1D ^ 0x5] = I("\u001b\u0017\u0006$h\t\u0019\u000e*\u000f\u0017\u001c\u0000,'\r\u0017\u001b", "yxiOF");
        GuiScreenBook.I[0x5 ^ 0x1C] = I("", "EIwzy");
        GuiScreenBook.I[0x8F ^ 0x95] = I("\t", "VwCDD");
        GuiScreenBook.I[0x2 ^ 0x19] = I("6", "islWU");
        GuiScreenBook.I[0x44 ^ 0x58] = I("\u0013", "LROld");
        GuiScreenBook.I[0x97 ^ 0x8A] = I("al-?=* \r5k)#\u000b:k?-\u0003qa", "KLdQK");
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 0) {
                this.mc.displayGuiScreen(null);
                this.sendBookToServer("".length() != 0);
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
            else if (guiButton.id == "   ".length() && this.bookIsUnsigned) {
                this.bookGettingSigned = (" ".length() != 0);
                "".length();
                if (2 < 1) {
                    throw null;
                }
            }
            else if (guiButton.id == " ".length()) {
                if (this.currPage < this.bookTotalPages - " ".length()) {
                    this.currPage += " ".length();
                    "".length();
                    if (1 < 0) {
                        throw null;
                    }
                }
                else if (this.bookIsUnsigned) {
                    this.addNewPage();
                    if (this.currPage < this.bookTotalPages - " ".length()) {
                        this.currPage += " ".length();
                        "".length();
                        if (-1 >= 1) {
                            throw null;
                        }
                    }
                }
            }
            else if (guiButton.id == "  ".length()) {
                if (this.currPage > 0) {
                    this.currPage -= " ".length();
                    "".length();
                    if (4 <= 1) {
                        throw null;
                    }
                }
            }
            else if (guiButton.id == (0xA ^ 0xF) && this.bookGettingSigned) {
                this.sendBookToServer(" ".length() != 0);
                this.mc.displayGuiScreen(null);
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
            }
            else if (guiButton.id == (0xA ^ 0xE) && this.bookGettingSigned) {
                this.bookGettingSigned = ("".length() != 0);
            }
            this.updateButtons();
        }
    }
    
    static {
        I();
        logger = LogManager.getLogger();
        bookGuiTextures = new ResourceLocation(GuiScreenBook.I["".length()]);
    }
    
    private void pageInsertIntoCurrent(final String s) {
        final String string = String.valueOf(this.pageGetCurrent()) + s;
        if (this.fontRendererObj.splitStringWidth(String.valueOf(string) + EnumChatFormatting.BLACK + GuiScreenBook.I[0x1F ^ 0xD], 0xCC ^ 0xBA) <= 4 + 124 - 63 + 63 && string.length() < 216 + 35 - 20 + 25) {
            this.pageSetCurrent(string);
        }
    }
    
    private void addNewPage() {
        if (this.bookPages != null && this.bookPages.tagCount() < (0xF4 ^ 0xC6)) {
            this.bookPages.appendTag(new NBTTagString(GuiScreenBook.I[0x8F ^ 0x80]));
            this.bookTotalPages += " ".length();
            this.bookIsModified = (" ".length() != 0);
        }
    }
    
    static class NextPageButton extends GuiButton
    {
        private static final String[] I;
        private final boolean field_146151_o;
        
        @Override
        public void drawButton(final Minecraft minecraft, final int n, final int n2) {
            if (this.visible) {
                int n3;
                if (n >= this.xPosition && n2 >= this.yPosition && n < this.xPosition + this.width && n2 < this.yPosition + this.height) {
                    n3 = " ".length();
                    "".length();
                    if (4 <= 3) {
                        throw null;
                    }
                }
                else {
                    n3 = "".length();
                }
                final int n4 = n3;
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                minecraft.getTextureManager().bindTexture(GuiScreenBook.access$0());
                int length = "".length();
                int n5 = 121 + 159 - 258 + 170;
                if (n4 != 0) {
                    length += 23;
                }
                if (!this.field_146151_o) {
                    n5 += 13;
                }
                this.drawTexturedModalRect(this.xPosition, this.yPosition, length, n5, 0x2C ^ 0x3B, 0x31 ^ 0x3C);
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
                if (4 <= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("", "oATjD");
        }
        
        static {
            I();
        }
        
        public NextPageButton(final int n, final int n2, final int n3, final boolean field_146151_o) {
            super(n, n2, n3, 0x1E ^ 0x9, 0xA2 ^ 0xAF, NextPageButton.I["".length()]);
            this.field_146151_o = field_146151_o;
        }
    }
}
