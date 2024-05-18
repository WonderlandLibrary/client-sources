package net.minecraft.client.gui;

import java.util.*;
import net.minecraft.client.*;
import net.minecraft.client.settings.*;
import com.google.common.collect.*;

public class GuiOptionsRowList extends GuiListExtended
{
    private final List<Row> field_148184_k;
    
    private GuiButton func_148182_a(final Minecraft minecraft, final int n, final int n2, final GameSettings.Options options) {
        if (options == null) {
            return null;
        }
        final int returnEnumOrdinal = options.returnEnumOrdinal();
        GuiButton guiButton;
        if (options.getEnumFloat()) {
            guiButton = new GuiOptionSlider(returnEnumOrdinal, n, n2, options);
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            guiButton = new GuiOptionButton(returnEnumOrdinal, n, n2, options, minecraft.gameSettings.getKeyBinding(options));
        }
        return guiButton;
    }
    
    @Override
    public IGuiListEntry getListEntry(final int n) {
        return this.getListEntry(n);
    }
    
    public GuiOptionsRowList(final Minecraft minecraft, final int n, final int n2, final int n3, final int n4, final int n5, final GameSettings.Options... array) {
        super(minecraft, n, n2, n3, n4, n5);
        this.field_148184_k = (List<Row>)Lists.newArrayList();
        this.field_148163_i = ("".length() != 0);
        int i = "".length();
        "".length();
        if (4 <= 0) {
            throw null;
        }
        while (i < array.length) {
            final GameSettings.Options options = array[i];
            GameSettings.Options options2;
            if (i < array.length - " ".length()) {
                options2 = array[i + " ".length()];
                "".length();
                if (4 < 2) {
                    throw null;
                }
            }
            else {
                options2 = null;
            }
            this.field_148184_k.add(new Row(this.func_148182_a(minecraft, n / "  ".length() - (76 + 75 + 3 + 1), "".length(), options), this.func_148182_a(minecraft, n / "  ".length() - (73 + 16 + 23 + 43) + (95 + 0 - 80 + 145), "".length(), options2)));
            i += 2;
        }
    }
    
    @Override
    public Row getListEntry(final int n) {
        return this.field_148184_k.get(n);
    }
    
    @Override
    public int getListWidth() {
        return 268 + 128 - 388 + 392;
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
            if (-1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected int getScrollBarX() {
        return super.getScrollBarX() + (0x3F ^ 0x1F);
    }
    
    @Override
    protected int getSize() {
        return this.field_148184_k.size();
    }
    
    public static class Row implements IGuiListEntry
    {
        private final GuiButton field_148324_c;
        private final Minecraft field_148325_a;
        private final GuiButton field_148323_b;
        
        @Override
        public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            if (this.field_148323_b.mousePressed(this.field_148325_a, n2, n3)) {
                if (this.field_148323_b instanceof GuiOptionButton) {
                    this.field_148325_a.gameSettings.setOptionValue(((GuiOptionButton)this.field_148323_b).returnEnumOptions(), " ".length());
                    this.field_148323_b.displayString = this.field_148325_a.gameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(this.field_148323_b.id));
                }
                return " ".length() != 0;
            }
            if (this.field_148324_c != null && this.field_148324_c.mousePressed(this.field_148325_a, n2, n3)) {
                if (this.field_148324_c instanceof GuiOptionButton) {
                    this.field_148325_a.gameSettings.setOptionValue(((GuiOptionButton)this.field_148324_c).returnEnumOptions(), " ".length());
                    this.field_148324_c.displayString = this.field_148325_a.gameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(this.field_148324_c.id));
                }
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        public Row(final GuiButton field_148323_b, final GuiButton field_148324_c) {
            this.field_148325_a = Minecraft.getMinecraft();
            this.field_148323_b = field_148323_b;
            this.field_148324_c = field_148324_c;
        }
        
        @Override
        public void setSelected(final int n, final int n2, final int n3) {
        }
        
        @Override
        public void mouseReleased(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            if (this.field_148323_b != null) {
                this.field_148323_b.mouseReleased(n2, n3);
            }
            if (this.field_148324_c != null) {
                this.field_148324_c.mouseReleased(n2, n3);
            }
        }
        
        @Override
        public void drawEntry(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
            if (this.field_148323_b != null) {
                this.field_148323_b.yPosition = n3;
                this.field_148323_b.drawButton(this.field_148325_a, n6, n7);
            }
            if (this.field_148324_c != null) {
                this.field_148324_c.yPosition = n3;
                this.field_148324_c.drawButton(this.field_148325_a, n6, n7);
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
                if (2 != 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
