package net.minecraft.client.resources;

import net.minecraft.client.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;

public abstract class ResourcePackListEntry implements GuiListExtended.IGuiListEntry
{
    private static final String[] I;
    private static final ResourceLocation RESOURCE_PACKS_TEXTURE;
    private static final IChatComponent field_183021_e;
    protected final GuiScreenResourcePacks resourcePacksGUI;
    protected final Minecraft mc;
    private static final IChatComponent field_183020_d;
    private static final IChatComponent field_183022_f;
    
    protected boolean func_148308_f() {
        return this.resourcePacksGUI.hasResourcePackEntry(this);
    }
    
    private static void I() {
        (I = new String[0x50 ^ 0x5A])["".length()] = I("\u00063\u000f;\u0012\u00003\u0004`\u0000\u0007?X=\u0002\u00019\u0002=\u0004\u0017\t\u0007.\u0004\u0019%Y?\t\u0015", "rVwOg");
        ResourcePackListEntry.I[" ".length()] = I("\u0005&*\t=\u0005 <6)\u0014(w\u000f&\u0014,4\u0016)\u0003*;\n-", "wCYfH");
        ResourcePackListEntry.I["  ".length()] = I("\u0005\u0007\u0007\u0003>\u0005\u0001\u0011<*\u0014\tZ\u0005%\u0014\r\u0019\u001c*\u0003\u000b\u0016\u0000.Y\r\u0018\b", "wbtlK");
        ResourcePackListEntry.I["   ".length()] = I("\u000662\t>\u00060$6*\u00178o\u000f%\u0017<,\u0016*\u0000:#\n.Z=$\u0011", "tSAfK");
        ResourcePackListEntry.I[0x2A ^ 0x2E] = I("A][", "osuyi");
        ResourcePackListEntry.I[0x95 ^ 0x90] = I("f[T", "HuziZ");
        ResourcePackListEntry.I[0x33 ^ 0x35] = I("\"\u0015\u0011)\u0000\"\u0013\u0007\u0016\u00143\u001bL/\u001b3\u001f\u000f6\u0014$\u0019\u0000*\u0010~\u0013\r(\u00139\u0002\u000fh\u00019\u0004\u000e#", "PpbFu");
        ResourcePackListEntry.I[0x34 ^ 0x33] = I("&'*\u00078&!<8,7)w\u0001#7-4\u0018, +;\u0004(z!6\u0006+=04F", "TBYhM");
        ResourcePackListEntry.I[0x95 ^ 0x9D] = I("!\u0016\u0007", "OspWD");
        ResourcePackListEntry.I[0x54 ^ 0x5D] = I("\n*\u0011", "eFuGz");
    }
    
    @Override
    public void mouseReleased(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
    }
    
    protected boolean func_148314_g() {
        final List<ResourcePackListEntry> listContaining = this.resourcePacksGUI.getListContaining(this);
        final int index = listContaining.indexOf(this);
        if (index > 0 && listContaining.get(index - " ".length()).func_148310_d()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        if (this.func_148310_d() && n5 <= (0x39 ^ 0x19)) {
            if (this.func_148309_e()) {
                this.resourcePacksGUI.markChanged();
                final int func_183019_a = this.func_183019_a();
                if (func_183019_a != " ".length()) {
                    final String format = I18n.format(ResourcePackListEntry.I[0x3A ^ 0x3C], new Object["".length()]);
                    final StringBuilder sb = new StringBuilder(ResourcePackListEntry.I[0x4E ^ 0x49]);
                    String s;
                    if (func_183019_a > " ".length()) {
                        s = ResourcePackListEntry.I[0xB6 ^ 0xBE];
                        "".length();
                        if (4 == -1) {
                            throw null;
                        }
                    }
                    else {
                        s = ResourcePackListEntry.I[0x78 ^ 0x71];
                    }
                    this.mc.displayGuiScreen(new GuiYesNo(new GuiYesNoCallback(this) {
                        final ResourcePackListEntry this$0;
                        
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
                                if (3 >= 4) {
                                    throw null;
                                }
                            }
                            return sb.toString();
                        }
                        
                        @Override
                        public void confirmClicked(final boolean b, final int n) {
                            final List<ResourcePackListEntry> listContaining = this.this$0.resourcePacksGUI.getListContaining(this.this$0);
                            this.this$0.mc.displayGuiScreen(this.this$0.resourcePacksGUI);
                            if (b) {
                                listContaining.remove(this.this$0);
                                this.this$0.resourcePacksGUI.getSelectedResourcePacks().add("".length(), this.this$0);
                            }
                        }
                    }, format, I18n.format(sb.append(s).toString(), new Object["".length()]), "".length()));
                    "".length();
                    if (0 >= 3) {
                        throw null;
                    }
                }
                else {
                    this.resourcePacksGUI.getListContaining(this).remove(this);
                    this.resourcePacksGUI.getSelectedResourcePacks().add("".length(), this);
                }
                return " ".length() != 0;
            }
            if (n5 < (0x9D ^ 0x8D) && this.func_148308_f()) {
                this.resourcePacksGUI.getListContaining(this).remove(this);
                this.resourcePacksGUI.getAvailableResourcePacks().add("".length(), this);
                this.resourcePacksGUI.markChanged();
                return " ".length() != 0;
            }
            if (n5 > (0xBC ^ 0xAC) && n6 < (0x21 ^ 0x31) && this.func_148314_g()) {
                final List<ResourcePackListEntry> listContaining = this.resourcePacksGUI.getListContaining(this);
                final int index = listContaining.indexOf(this);
                listContaining.remove(this);
                listContaining.add(index - " ".length(), this);
                this.resourcePacksGUI.markChanged();
                return " ".length() != 0;
            }
            if (n5 > (0xBD ^ 0xAD) && n6 > (0x8E ^ 0x9E) && this.func_148307_h()) {
                final List<ResourcePackListEntry> listContaining2 = this.resourcePacksGUI.getListContaining(this);
                final int index2 = listContaining2.indexOf(this);
                listContaining2.remove(this);
                listContaining2.add(index2 + " ".length(), this);
                this.resourcePacksGUI.markChanged();
                return " ".length() != 0;
            }
        }
        return "".length() != 0;
    }
    
    protected boolean func_148307_h() {
        final List<ResourcePackListEntry> listContaining = this.resourcePacksGUI.getListContaining(this);
        final int index = listContaining.indexOf(this);
        if (index >= 0 && index < listContaining.size() - " ".length() && listContaining.get(index + " ".length()).func_148310_d()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public ResourcePackListEntry(final GuiScreenResourcePacks resourcePacksGUI) {
        this.resourcePacksGUI = resourcePacksGUI;
        this.mc = Minecraft.getMinecraft();
    }
    
    @Override
    public void drawEntry(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
        final int func_183019_a = this.func_183019_a();
        if (func_183019_a != " ".length()) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            Gui.drawRect(n2 - " ".length(), n3 - " ".length(), n2 + n4 - (0xBD ^ 0xB4), n3 + n5 + " ".length(), -(1059242 + 8386414 - 4670221 + 4202997));
        }
        this.func_148313_c();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        Gui.drawModalRectWithCustomSizedTexture(n2, n3, 0.0f, 0.0f, 0x2C ^ 0xC, 0x67 ^ 0x47, 32.0f, 32.0f);
        String s = this.func_148312_b();
        String s2 = this.func_148311_a();
        if ((this.mc.gameSettings.touchscreen || b) && this.func_148310_d()) {
            this.mc.getTextureManager().bindTexture(ResourcePackListEntry.RESOURCE_PACKS_TEXTURE);
            Gui.drawRect(n2, n3, n2 + (0x19 ^ 0x39), n3 + (0x23 ^ 0x3), -(67332512 + 870384342 + 511998156 + 151423534));
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            final int n8 = n6 - n2;
            final int n9 = n7 - n3;
            if (func_183019_a < " ".length()) {
                s = ResourcePackListEntry.field_183020_d.getFormattedText();
                s2 = ResourcePackListEntry.field_183021_e.getFormattedText();
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            else if (func_183019_a > " ".length()) {
                s = ResourcePackListEntry.field_183020_d.getFormattedText();
                s2 = ResourcePackListEntry.field_183022_f.getFormattedText();
            }
            if (this.func_148309_e()) {
                if (n8 < (0x5A ^ 0x7A)) {
                    Gui.drawModalRectWithCustomSizedTexture(n2, n3, 0.0f, 32.0f, 0x70 ^ 0x50, 0x92 ^ 0xB2, 256.0f, 256.0f);
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                }
                else {
                    Gui.drawModalRectWithCustomSizedTexture(n2, n3, 0.0f, 0.0f, 0xA8 ^ 0x88, 0x85 ^ 0xA5, 256.0f, 256.0f);
                    "".length();
                    if (3 < 0) {
                        throw null;
                    }
                }
            }
            else {
                if (this.func_148308_f()) {
                    if (n8 < (0x24 ^ 0x34)) {
                        Gui.drawModalRectWithCustomSizedTexture(n2, n3, 32.0f, 32.0f, 0x4 ^ 0x24, 0xE1 ^ 0xC1, 256.0f, 256.0f);
                        "".length();
                        if (2 == 4) {
                            throw null;
                        }
                    }
                    else {
                        Gui.drawModalRectWithCustomSizedTexture(n2, n3, 32.0f, 0.0f, 0x73 ^ 0x53, 0x6E ^ 0x4E, 256.0f, 256.0f);
                    }
                }
                if (this.func_148314_g()) {
                    if (n8 < (0x76 ^ 0x56) && n8 > (0x6F ^ 0x7F) && n9 < (0x7F ^ 0x6F)) {
                        Gui.drawModalRectWithCustomSizedTexture(n2, n3, 96.0f, 32.0f, 0x47 ^ 0x67, 0xB ^ 0x2B, 256.0f, 256.0f);
                        "".length();
                        if (1 == -1) {
                            throw null;
                        }
                    }
                    else {
                        Gui.drawModalRectWithCustomSizedTexture(n2, n3, 96.0f, 0.0f, 0x81 ^ 0xA1, 0x5A ^ 0x7A, 256.0f, 256.0f);
                    }
                }
                if (this.func_148307_h()) {
                    if (n8 < (0x27 ^ 0x7) && n8 > (0x40 ^ 0x50) && n9 > (0x0 ^ 0x10)) {
                        Gui.drawModalRectWithCustomSizedTexture(n2, n3, 64.0f, 32.0f, 0x59 ^ 0x79, 0xE4 ^ 0xC4, 256.0f, 256.0f);
                        "".length();
                        if (1 < 0) {
                            throw null;
                        }
                    }
                    else {
                        Gui.drawModalRectWithCustomSizedTexture(n2, n3, 64.0f, 0.0f, 0x4E ^ 0x6E, 0x3 ^ 0x23, 256.0f, 256.0f);
                    }
                }
            }
        }
        if (this.mc.fontRendererObj.getStringWidth(s) > 101 + 151 - 248 + 153) {
            s = String.valueOf(this.mc.fontRendererObj.trimStringToWidth(s, 41 + 124 - 138 + 130 - this.mc.fontRendererObj.getStringWidth(ResourcePackListEntry.I[0x5A ^ 0x5E]))) + ResourcePackListEntry.I[0x18 ^ 0x1D];
        }
        this.mc.fontRendererObj.drawStringWithShadow(s, n2 + (0xA4 ^ 0x84) + "  ".length(), n3 + " ".length(), 12161725 + 1799362 - 3677275 + 6493403);
        final List listFormattedStringToWidth = this.mc.fontRendererObj.listFormattedStringToWidth(s2, 35 + 6 - 6 + 122);
        int length = "".length();
        "".length();
        if (3 <= 0) {
            throw null;
        }
        while (length < "  ".length() && length < listFormattedStringToWidth.size()) {
            this.mc.fontRendererObj.drawStringWithShadow(listFormattedStringToWidth.get(length), n2 + (0x39 ^ 0x19) + "  ".length(), n3 + (0x1B ^ 0x17) + (0x35 ^ 0x3F) * length, 7137243 + 9868 - 2844684 + 4119077);
            ++length;
        }
    }
    
    protected abstract int func_183019_a();
    
    @Override
    public void setSelected(final int n, final int n2, final int n3) {
    }
    
    protected boolean func_148310_d() {
        return " ".length() != 0;
    }
    
    protected abstract String func_148312_b();
    
    static {
        I();
        RESOURCE_PACKS_TEXTURE = new ResourceLocation(ResourcePackListEntry.I["".length()]);
        field_183020_d = new ChatComponentTranslation(ResourcePackListEntry.I[" ".length()], new Object["".length()]);
        field_183021_e = new ChatComponentTranslation(ResourcePackListEntry.I["  ".length()], new Object["".length()]);
        field_183022_f = new ChatComponentTranslation(ResourcePackListEntry.I["   ".length()], new Object["".length()]);
    }
    
    protected abstract String func_148311_a();
    
    protected boolean func_148309_e() {
        int n;
        if (this.resourcePacksGUI.hasResourcePackEntry(this)) {
            n = "".length();
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    protected abstract void func_148313_c();
    
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
            if (2 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
}
