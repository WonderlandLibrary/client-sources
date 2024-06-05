package net.minecraft.src;

import org.lwjgl.input.*;
import java.io.*;
import org.lwjgl.opengl.*;

public class GuiScreenBook extends GuiScreen
{
    private final EntityPlayer editingPlayer;
    private final ItemStack itemstackBook;
    private final boolean bookIsUnsigned;
    private boolean bookModified;
    private boolean editingTitle;
    private int updateCount;
    private int bookImageWidth;
    private int bookImageHeight;
    private int bookTotalPages;
    private int currPage;
    private NBTTagList bookPages;
    private String bookTitle;
    private GuiButtonNextPage buttonNextPage;
    private GuiButtonNextPage buttonPreviousPage;
    private GuiButton buttonDone;
    private GuiButton buttonSign;
    private GuiButton buttonFinalize;
    private GuiButton buttonCancel;
    
    public GuiScreenBook(final EntityPlayer par1EntityPlayer, final ItemStack par2ItemStack, final boolean par3) {
        this.bookImageWidth = 192;
        this.bookImageHeight = 192;
        this.bookTotalPages = 1;
        this.bookTitle = "";
        this.editingPlayer = par1EntityPlayer;
        this.itemstackBook = par2ItemStack;
        this.bookIsUnsigned = par3;
        if (par2ItemStack.hasTagCompound()) {
            final NBTTagCompound var4 = par2ItemStack.getTagCompound();
            this.bookPages = var4.getTagList("pages");
            if (this.bookPages != null) {
                this.bookPages = (NBTTagList)this.bookPages.copy();
                this.bookTotalPages = this.bookPages.tagCount();
                if (this.bookTotalPages < 1) {
                    this.bookTotalPages = 1;
                }
            }
        }
        if (this.bookPages == null && par3) {
            (this.bookPages = new NBTTagList("pages")).appendTag(new NBTTagString("1", ""));
            this.bookTotalPages = 1;
        }
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        ++this.updateCount;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        if (this.bookIsUnsigned) {
            this.buttonList.add(this.buttonSign = new GuiButton(3, this.width / 2 - 100, 4 + this.bookImageHeight, 98, 20, StatCollector.translateToLocal("book.signButton")));
            this.buttonList.add(this.buttonDone = new GuiButton(0, this.width / 2 + 2, 4 + this.bookImageHeight, 98, 20, StatCollector.translateToLocal("gui.done")));
            this.buttonList.add(this.buttonFinalize = new GuiButton(5, this.width / 2 - 100, 4 + this.bookImageHeight, 98, 20, StatCollector.translateToLocal("book.finalizeButton")));
            this.buttonList.add(this.buttonCancel = new GuiButton(4, this.width / 2 + 2, 4 + this.bookImageHeight, 98, 20, StatCollector.translateToLocal("gui.cancel")));
        }
        else {
            this.buttonList.add(this.buttonDone = new GuiButton(0, this.width / 2 - 100, 4 + this.bookImageHeight, 200, 20, StatCollector.translateToLocal("gui.done")));
        }
        final int var1 = (this.width - this.bookImageWidth) / 2;
        final byte var2 = 2;
        this.buttonList.add(this.buttonNextPage = new GuiButtonNextPage(1, var1 + 120, var2 + 154, true));
        this.buttonList.add(this.buttonPreviousPage = new GuiButtonNextPage(2, var1 + 38, var2 + 154, false));
        this.updateButtons();
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    private void updateButtons() {
        this.buttonNextPage.drawButton = (!this.editingTitle && (this.currPage < this.bookTotalPages - 1 || this.bookIsUnsigned));
        this.buttonPreviousPage.drawButton = (!this.editingTitle && this.currPage > 0);
        this.buttonDone.drawButton = (!this.bookIsUnsigned || !this.editingTitle);
        if (this.bookIsUnsigned) {
            this.buttonSign.drawButton = !this.editingTitle;
            this.buttonCancel.drawButton = this.editingTitle;
            this.buttonFinalize.drawButton = this.editingTitle;
            this.buttonFinalize.enabled = (this.bookTitle.trim().length() > 0);
        }
    }
    
    private void sendBookToServer(final boolean par1) {
        if (this.bookIsUnsigned && this.bookModified && this.bookPages != null) {
            while (this.bookPages.tagCount() > 1) {
                final NBTTagString var2 = (NBTTagString)this.bookPages.tagAt(this.bookPages.tagCount() - 1);
                if (var2.data != null && var2.data.length() != 0) {
                    break;
                }
                this.bookPages.removeTag(this.bookPages.tagCount() - 1);
            }
            if (this.itemstackBook.hasTagCompound()) {
                final NBTTagCompound var3 = this.itemstackBook.getTagCompound();
                var3.setTag("pages", this.bookPages);
            }
            else {
                this.itemstackBook.setTagInfo("pages", this.bookPages);
            }
            String var4 = "MC|BEdit";
            if (par1) {
                var4 = "MC|BSign";
                this.itemstackBook.setTagInfo("author", new NBTTagString("author", this.editingPlayer.username));
                this.itemstackBook.setTagInfo("title", new NBTTagString("title", this.bookTitle.trim()));
                this.itemstackBook.itemID = Item.writtenBook.itemID;
            }
            final ByteArrayOutputStream var5 = new ByteArrayOutputStream();
            final DataOutputStream var6 = new DataOutputStream(var5);
            try {
                Packet.writeItemStack(this.itemstackBook, var6);
                this.mc.getNetHandler().addToSendQueue(new Packet250CustomPayload(var4, var5.toByteArray()));
            }
            catch (Exception var7) {
                var7.printStackTrace();
            }
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id == 0) {
                this.mc.displayGuiScreen(null);
                this.sendBookToServer(false);
            }
            else if (par1GuiButton.id == 3 && this.bookIsUnsigned) {
                this.editingTitle = true;
            }
            else if (par1GuiButton.id == 1) {
                if (this.currPage < this.bookTotalPages - 1) {
                    ++this.currPage;
                }
                else if (this.bookIsUnsigned) {
                    this.addNewPage();
                    if (this.currPage < this.bookTotalPages - 1) {
                        ++this.currPage;
                    }
                }
            }
            else if (par1GuiButton.id == 2) {
                if (this.currPage > 0) {
                    --this.currPage;
                }
            }
            else if (par1GuiButton.id == 5 && this.editingTitle) {
                this.sendBookToServer(true);
                this.mc.displayGuiScreen(null);
            }
            else if (par1GuiButton.id == 4 && this.editingTitle) {
                this.editingTitle = false;
            }
            this.updateButtons();
        }
    }
    
    private void addNewPage() {
        if (this.bookPages != null && this.bookPages.tagCount() < 50) {
            this.bookPages.appendTag(new NBTTagString(new StringBuilder().append(this.bookTotalPages + 1).toString(), ""));
            ++this.bookTotalPages;
            this.bookModified = true;
        }
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
        super.keyTyped(par1, par2);
        if (this.bookIsUnsigned) {
            if (this.editingTitle) {
                this.func_74162_c(par1, par2);
            }
            else {
                this.keyTypedInBook(par1, par2);
            }
        }
    }
    
    private void keyTypedInBook(final char par1, final int par2) {
        switch (par1) {
            case '\u0016': {
                this.func_74160_b(GuiScreen.getClipboardString());
            }
            default: {
                switch (par2) {
                    case 14: {
                        final String var3 = this.func_74158_i();
                        if (var3.length() > 0) {
                            this.func_74159_a(var3.substring(0, var3.length() - 1));
                        }
                        return;
                    }
                    case 28: {
                        this.func_74160_b("\n");
                        return;
                    }
                    default: {
                        if (ChatAllowedCharacters.isAllowedCharacter(par1)) {
                            this.func_74160_b(Character.toString(par1));
                        }
                        return;
                    }
                }
                break;
            }
        }
    }
    
    private void func_74162_c(final char par1, final int par2) {
        switch (par2) {
            case 14: {
                if (this.bookTitle.length() > 0) {
                    this.bookTitle = this.bookTitle.substring(0, this.bookTitle.length() - 1);
                    this.updateButtons();
                }
            }
            case 28: {
                if (this.bookTitle.length() > 0) {
                    this.sendBookToServer(true);
                    this.mc.displayGuiScreen(null);
                }
            }
            default: {
                if (this.bookTitle.length() < 16 && ChatAllowedCharacters.isAllowedCharacter(par1)) {
                    this.bookTitle = String.valueOf(this.bookTitle) + Character.toString(par1);
                    this.updateButtons();
                    this.bookModified = true;
                }
            }
        }
    }
    
    private String func_74158_i() {
        if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount()) {
            final NBTTagString var1 = (NBTTagString)this.bookPages.tagAt(this.currPage);
            return var1.toString();
        }
        return "";
    }
    
    private void func_74159_a(final String par1Str) {
        if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount()) {
            final NBTTagString var2 = (NBTTagString)this.bookPages.tagAt(this.currPage);
            var2.data = par1Str;
            this.bookModified = true;
        }
    }
    
    private void func_74160_b(final String par1Str) {
        final String var2 = this.func_74158_i();
        final String var3 = String.valueOf(var2) + par1Str;
        final int var4 = this.fontRenderer.splitStringWidth(String.valueOf(var3) + EnumChatFormatting.BLACK + "_", 118);
        if (var4 <= 118 && var3.length() < 256) {
            this.func_74159_a(var3);
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture("/gui/book.png");
        final int var4 = (this.width - this.bookImageWidth) / 2;
        final byte var5 = 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.bookImageWidth, this.bookImageHeight);
        if (this.editingTitle) {
            String var6 = this.bookTitle;
            if (this.bookIsUnsigned) {
                if (this.updateCount / 6 % 2 == 0) {
                    var6 = String.valueOf(var6) + EnumChatFormatting.BLACK + "_";
                }
                else {
                    var6 = String.valueOf(var6) + EnumChatFormatting.GRAY + "_";
                }
            }
            final String var7 = StatCollector.translateToLocal("book.editTitle");
            final int var8 = this.fontRenderer.getStringWidth(var7);
            this.fontRenderer.drawString(var7, var4 + 36 + (116 - var8) / 2, var5 + 16 + 16, 0);
            final int var9 = this.fontRenderer.getStringWidth(var6);
            this.fontRenderer.drawString(var6, var4 + 36 + (116 - var9) / 2, var5 + 48, 0);
            final String var10 = String.format(StatCollector.translateToLocal("book.byAuthor"), this.editingPlayer.username);
            final int var11 = this.fontRenderer.getStringWidth(var10);
            this.fontRenderer.drawString(EnumChatFormatting.DARK_GRAY + var10, var4 + 36 + (116 - var11) / 2, var5 + 48 + 10, 0);
            final String var12 = StatCollector.translateToLocal("book.finalizeWarning");
            this.fontRenderer.drawSplitString(var12, var4 + 36, var5 + 80, 116, 0);
        }
        else {
            final String var6 = String.format(StatCollector.translateToLocal("book.pageIndicator"), this.currPage + 1, this.bookTotalPages);
            String var7 = "";
            if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount()) {
                final NBTTagString var13 = (NBTTagString)this.bookPages.tagAt(this.currPage);
                var7 = var13.toString();
            }
            if (this.bookIsUnsigned) {
                if (this.fontRenderer.getBidiFlag()) {
                    var7 = String.valueOf(var7) + "_";
                }
                else if (this.updateCount / 6 % 2 == 0) {
                    var7 = String.valueOf(var7) + EnumChatFormatting.BLACK + "_";
                }
                else {
                    var7 = String.valueOf(var7) + EnumChatFormatting.GRAY + "_";
                }
            }
            final int var8 = this.fontRenderer.getStringWidth(var6);
            this.fontRenderer.drawString(var6, var4 - var8 + this.bookImageWidth - 44, var5 + 16, 0);
            this.fontRenderer.drawSplitString(var7, var4 + 36, var5 + 16 + 16, 116, 0);
        }
        super.drawScreen(par1, par2, par3);
    }
}
