package net.minecraft.client.gui;

import net.minecraft.client.settings.*;
import java.io.*;
import com.google.common.collect.*;
import net.minecraft.client.resources.*;
import java.util.*;

public class GuiSnooper extends GuiScreen
{
    private GuiButton field_146605_t;
    private final GameSettings game_settings_2;
    private List field_146606_s;
    private final GuiScreen field_146608_a;
    private String field_146610_i;
    private final java.util.List<String> field_146609_h;
    private final java.util.List<String> field_146604_g;
    private static final String[] I;
    private String[] field_146607_r;
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == "  ".length()) {
                this.game_settings_2.saveOptions();
                this.game_settings_2.saveOptions();
                this.mc.displayGuiScreen(this.field_146608_a);
            }
            if (guiButton.id == " ".length()) {
                this.game_settings_2.setOptionValue(GameSettings.Options.SNOOPER_ENABLED, " ".length());
                this.field_146605_t.displayString = this.game_settings_2.getKeyBinding(GameSettings.Options.SNOOPER_ENABLED);
            }
        }
    }
    
    static java.util.List access$0(final GuiSnooper guiSnooper) {
        return guiSnooper.field_146604_g;
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
            if (1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public GuiSnooper(final GuiScreen field_146608_a, final GameSettings game_settings_2) {
        this.field_146604_g = (java.util.List<String>)Lists.newArrayList();
        this.field_146609_h = (java.util.List<String>)Lists.newArrayList();
        this.field_146608_a = field_146608_a;
        this.game_settings_2 = game_settings_2;
    }
    
    static {
        I();
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.field_146606_s.handleMouseInput();
    }
    
    @Override
    public void initGui() {
        this.field_146610_i = I18n.format(GuiSnooper.I["".length()], new Object["".length()]);
        final String format = I18n.format(GuiSnooper.I[" ".length()], new Object["".length()]);
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator iterator = this.fontRendererObj.listFormattedStringToWidth(format, this.width - (0x4F ^ 0x51)).iterator();
        "".length();
        if (2 >= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            arrayList.add(iterator.next());
        }
        this.field_146607_r = arrayList.toArray(new String[arrayList.size()]);
        this.field_146604_g.clear();
        this.field_146609_h.clear();
        this.buttonList.add(this.field_146605_t = new GuiButton(" ".length(), this.width / "  ".length() - (21 + 15 - 6 + 122), this.height - (0xDC ^ 0xC2), 134 + 4 - 50 + 62, 0x5C ^ 0x48, this.game_settings_2.getKeyBinding(GameSettings.Options.SNOOPER_ENABLED)));
        this.buttonList.add(new GuiButton("  ".length(), this.width / "  ".length() + "  ".length(), this.height - (0x3C ^ 0x22), 46 + 91 + 11 + 2, 0x42 ^ 0x56, I18n.format(GuiSnooper.I["  ".length()], new Object["".length()])));
        int n;
        if (this.mc.getIntegratedServer() != null && this.mc.getIntegratedServer().getPlayerUsageSnooper() != null) {
            n = " ".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final int n2 = n;
        final Iterator<Map.Entry<Object, String>> iterator2 = new TreeMap<Object, String>(this.mc.getPlayerUsageSnooper().getCurrentStats()).entrySet().iterator();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (iterator2.hasNext()) {
            final Map.Entry<Object, String> entry = iterator2.next();
            final java.util.List<String> field_146604_g = this.field_146604_g;
            String s;
            if (n2 != 0) {
                s = GuiSnooper.I["   ".length()];
                "".length();
                if (false == true) {
                    throw null;
                }
            }
            else {
                s = GuiSnooper.I[0xBE ^ 0xBA];
            }
            field_146604_g.add(String.valueOf(s) + entry.getKey());
            this.field_146609_h.add(this.fontRendererObj.trimStringToWidth(entry.getValue(), this.width - (119 + 136 - 64 + 29)));
        }
        if (n2 != 0) {
            final Iterator<Map.Entry<String, Object>> iterator3 = new TreeMap<String, Object>(this.mc.getIntegratedServer().getPlayerUsageSnooper().getCurrentStats()).entrySet().iterator();
            "".length();
            if (2 == 0) {
                throw null;
            }
            while (iterator3.hasNext()) {
                final Map.Entry<String, Object> entry2 = iterator3.next();
                this.field_146604_g.add(GuiSnooper.I[0x6D ^ 0x68] + entry2.getKey());
                this.field_146609_h.add(this.fontRendererObj.trimStringToWidth(entry2.getValue(), this.width - (180 + 41 - 61 + 60)));
            }
        }
        this.field_146606_s = new List();
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        this.field_146606_s.drawScreen(n, n2, n3);
        this.drawCenteredString(this.fontRendererObj, this.field_146610_i, this.width / "  ".length(), 0xBC ^ 0xB4, 2568905 + 9264506 - 10488117 + 15431921);
        int n4 = 0x76 ^ 0x60;
        final String[] field_146607_r;
        final int length = (field_146607_r = this.field_146607_r).length;
        int i = "".length();
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while (i < length) {
            this.drawCenteredString(this.fontRendererObj, field_146607_r[i], this.width / "  ".length(), n4, 4580159 + 4331857 - 5373854 + 4883342);
            n4 += this.fontRendererObj.FONT_HEIGHT;
            ++i;
        }
        super.drawScreen(n, n2, n3);
    }
    
    static java.util.List access$1(final GuiSnooper guiSnooper) {
        return guiSnooper.field_146609_h;
    }
    
    private static void I() {
        (I = new String[0x80 ^ 0x86])["".length()] = I("\u0019\u0011\u0018\u0011\u001c\u0018\u0012B\u000b\u001d\u0019\u000e\u001c\u001d\u0001X\u0015\u0005\f\u001f\u0013", "valxs");
        GuiSnooper.I[" ".length()] = I("\u0002\u0012\u001c=%\u0003\u0011F'$\u0002\r\u001818C\u0006\r')", "mbhTJ");
        GuiSnooper.I["  ".length()] = I("\u000f2\u001d]!\u0007)\u0011", "hGtsE");
        GuiSnooper.I["   ".length()] = I("\ns", "ISKjZ");
        GuiSnooper.I[0x5B ^ 0x5F] = I("", "hxEvQ");
        GuiSnooper.I[0xA2 ^ 0xA7] = I("5c", "fCyeU");
    }
    
    class List extends GuiSlot
    {
        final GuiSnooper this$0;
        
        @Override
        protected int getSize() {
            return GuiSnooper.access$0(this.this$0).size();
        }
        
        @Override
        protected void drawSlot(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            this.this$0.fontRendererObj.drawString(GuiSnooper.access$0(this.this$0).get(n), 0x5A ^ 0x50, n3, 5028939 + 5575483 - 4598970 + 10771763);
            this.this$0.fontRendererObj.drawString(GuiSnooper.access$1(this.this$0).get(n), 143 + 14 + 7 + 66, n3, 5701588 + 5535187 + 3919590 + 1620850);
        }
        
        @Override
        protected int getScrollBarX() {
            return this.width - (0x4D ^ 0x47);
        }
        
        public List(final GuiSnooper this$0) {
            this.this$0 = this$0;
            super(this$0.mc, this$0.width, this$0.height, 0x7 ^ 0x57, this$0.height - (0xB9 ^ 0x91), this$0.fontRendererObj.FONT_HEIGHT + " ".length());
        }
        
        @Override
        protected void elementClicked(final int n, final boolean b, final int n2, final int n3) {
        }
        
        @Override
        protected boolean isSelected(final int n) {
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
                if (-1 >= 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        protected void drawBackground() {
        }
    }
}
