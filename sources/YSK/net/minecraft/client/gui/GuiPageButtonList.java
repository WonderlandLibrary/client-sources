package net.minecraft.client.gui;

import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.*;
import com.google.common.collect.*;
import com.google.common.base.*;

public class GuiPageButtonList extends GuiListExtended
{
    private final IntHashMap<Gui> field_178073_v;
    private GuiResponder field_178076_z;
    private static final String[] I;
    private final List<GuiTextField> field_178072_w;
    private int field_178077_y;
    private final GuiListEntry[][] field_178078_x;
    private Gui field_178075_A;
    private final List<GuiEntry> field_178074_u;
    
    public int getSize() {
        return this.field_178074_u.size();
    }
    
    @Override
    public boolean mouseClicked(final int n, final int n2, final int n3) {
        final boolean mouseClicked = super.mouseClicked(n, n2, n3);
        final int slotIndexFromScreenCoords = this.getSlotIndexFromScreenCoords(n, n2);
        if (slotIndexFromScreenCoords >= 0) {
            final GuiEntry listEntry = this.getListEntry(slotIndexFromScreenCoords);
            if (this.field_178075_A != GuiEntry.access$2(listEntry) && this.field_178075_A != null && this.field_178075_A instanceof GuiTextField) {
                ((GuiTextField)this.field_178075_A).setFocused("".length() != 0);
            }
            this.field_178075_A = GuiEntry.access$2(listEntry);
        }
        return mouseClicked;
    }
    
    public Gui func_178061_c(final int n) {
        return this.field_178073_v.lookup(n);
    }
    
    public void func_181155_a(final boolean b) {
        final Iterator<GuiEntry> iterator = this.field_178074_u.iterator();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final GuiEntry guiEntry = iterator.next();
            if (GuiEntry.access$0(guiEntry) instanceof GuiButton) {
                ((GuiButton)GuiEntry.access$0(guiEntry)).enabled = b;
            }
            if (GuiEntry.access$1(guiEntry) instanceof GuiButton) {
                ((GuiButton)GuiEntry.access$1(guiEntry)).enabled = b;
            }
        }
    }
    
    public void func_178071_h() {
        if (this.field_178077_y > 0) {
            this.func_181156_c(this.field_178077_y - " ".length());
        }
    }
    
    private void func_178066_a(final Gui gui, final boolean visible) {
        if (gui instanceof GuiButton) {
            ((GuiButton)gui).visible = visible;
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        else if (gui instanceof GuiTextField) {
            ((GuiTextField)gui).setVisible(visible);
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (gui instanceof GuiLabel) {
            ((GuiLabel)gui).visible = visible;
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private void func_178069_s() {
        final GuiListEntry[][] field_178078_x;
        final int length = (field_178078_x = this.field_178078_x).length;
        int i = "".length();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (i < length) {
            final GuiListEntry[] array = field_178078_x[i];
            int j = "".length();
            "".length();
            if (1 < 1) {
                throw null;
            }
            while (j < array.length) {
                final GuiListEntry guiListEntry = array[j];
                GuiListEntry guiListEntry2;
                if (j < array.length - " ".length()) {
                    guiListEntry2 = array[j + " ".length()];
                    "".length();
                    if (4 < 2) {
                        throw null;
                    }
                }
                else {
                    guiListEntry2 = null;
                }
                final GuiListEntry guiListEntry3 = guiListEntry2;
                final GuiListEntry guiListEntry4 = guiListEntry;
                final int length2 = "".length();
                int n;
                if (guiListEntry3 == null) {
                    n = " ".length();
                    "".length();
                    if (2 >= 3) {
                        throw null;
                    }
                }
                else {
                    n = "".length();
                }
                final Gui func_178058_a = this.func_178058_a(guiListEntry4, length2, n != 0);
                final GuiListEntry guiListEntry5 = guiListEntry3;
                final int n2 = 76 + 12 + 51 + 21;
                int n3;
                if (guiListEntry == null) {
                    n3 = " ".length();
                    "".length();
                    if (2 <= 0) {
                        throw null;
                    }
                }
                else {
                    n3 = "".length();
                }
                final Gui func_178058_a2 = this.func_178058_a(guiListEntry5, n2, n3 != 0);
                this.field_178074_u.add(new GuiEntry(func_178058_a, func_178058_a2));
                if (guiListEntry != null && func_178058_a != null) {
                    this.field_178073_v.addKey(guiListEntry.func_178935_b(), func_178058_a);
                    if (func_178058_a instanceof GuiTextField) {
                        this.field_178072_w.add((GuiTextField)func_178058_a);
                    }
                }
                if (guiListEntry3 != null && func_178058_a2 != null) {
                    this.field_178073_v.addKey(guiListEntry3.func_178935_b(), func_178058_a2);
                    if (func_178058_a2 instanceof GuiTextField) {
                        this.field_178072_w.add((GuiTextField)func_178058_a2);
                    }
                }
                j += 2;
            }
            ++i;
        }
    }
    
    private void func_178055_t() {
        this.field_178074_u.clear();
        int i = "".length();
        "".length();
        if (-1 == 4) {
            throw null;
        }
        while (i < this.field_178078_x[this.field_178077_y].length) {
            final GuiListEntry guiListEntry = this.field_178078_x[this.field_178077_y][i];
            GuiListEntry guiListEntry2;
            if (i < this.field_178078_x[this.field_178077_y].length - " ".length()) {
                guiListEntry2 = this.field_178078_x[this.field_178077_y][i + " ".length()];
                "".length();
                if (3 < 2) {
                    throw null;
                }
            }
            else {
                guiListEntry2 = null;
            }
            final GuiListEntry guiListEntry3 = guiListEntry2;
            final Gui gui = this.field_178073_v.lookup(guiListEntry.func_178935_b());
            Gui gui2;
            if (guiListEntry3 != null) {
                gui2 = this.field_178073_v.lookup(guiListEntry3.func_178935_b());
                "".length();
                if (0 == 4) {
                    throw null;
                }
            }
            else {
                gui2 = null;
            }
            this.field_178074_u.add(new GuiEntry(gui, gui2));
            i += 2;
        }
    }
    
    public void func_178064_i() {
        if (this.field_178077_y < this.field_178078_x.length - " ".length()) {
            this.func_181156_c(this.field_178077_y + " ".length());
        }
    }
    
    @Override
    public int getListWidth() {
        return 372 + 196 - 482 + 314;
    }
    
    static {
        I();
    }
    
    public int func_178057_f() {
        return this.field_178078_x.length;
    }
    
    private void func_178060_e(final int n, final int n2) {
        final GuiListEntry[] array;
        final int length = (array = this.field_178078_x[n]).length;
        int i = "".length();
        "".length();
        if (1 <= 0) {
            throw null;
        }
        while (i < length) {
            final GuiListEntry guiListEntry = array[i];
            if (guiListEntry != null) {
                this.func_178066_a(this.field_178073_v.lookup(guiListEntry.func_178935_b()), "".length() != 0);
            }
            ++i;
        }
        final GuiListEntry[] array2;
        final int length2 = (array2 = this.field_178078_x[n2]).length;
        int j = "".length();
        "".length();
        if (true != true) {
            throw null;
        }
        while (j < length2) {
            final GuiListEntry guiListEntry2 = array2[j];
            if (guiListEntry2 != null) {
                this.func_178066_a(this.field_178073_v.lookup(guiListEntry2.func_178935_b()), " ".length() != 0);
            }
            ++j;
        }
    }
    
    public void func_181156_c(final int field_178077_y) {
        if (field_178077_y != this.field_178077_y) {
            final int field_178077_y2 = this.field_178077_y;
            this.field_178077_y = field_178077_y;
            this.func_178055_t();
            this.func_178060_e(field_178077_y2, field_178077_y);
            this.amountScrolled = 0.0f;
        }
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("J", "qczKS");
    }
    
    private GuiTextField func_178068_a(final int n, final int n2, final EditBoxEntry editBoxEntry) {
        final GuiTextField guiTextField = new GuiTextField(editBoxEntry.func_178935_b(), this.mc.fontRendererObj, n, n2, 134 + 52 - 78 + 42, 0xB0 ^ 0xA4);
        guiTextField.setText(editBoxEntry.func_178936_c());
        guiTextField.func_175207_a(this.field_178076_z);
        guiTextField.setVisible(editBoxEntry.func_178934_d());
        guiTextField.func_175205_a(editBoxEntry.func_178950_a());
        return guiTextField;
    }
    
    private GuiListButton func_178065_a(final int n, final int n2, final GuiButtonEntry guiButtonEntry) {
        final GuiListButton guiListButton = new GuiListButton(this.field_178076_z, guiButtonEntry.func_178935_b(), n, n2, guiButtonEntry.func_178936_c(), guiButtonEntry.func_178940_a());
        guiListButton.visible = guiButtonEntry.func_178934_d();
        return guiListButton;
    }
    
    @Override
    protected int getScrollBarX() {
        return super.getScrollBarX() + (0x6B ^ 0x4B);
    }
    
    private GuiLabel func_178063_a(final int n, final int n2, final GuiLabelEntry guiLabelEntry, final boolean b) {
        GuiLabel guiLabel;
        if (b) {
            guiLabel = new GuiLabel(this.mc.fontRendererObj, guiLabelEntry.func_178935_b(), n, n2, this.width - n * "  ".length(), 0xB5 ^ 0xA1, -" ".length());
            "".length();
            if (2 < -1) {
                throw null;
            }
        }
        else {
            guiLabel = new GuiLabel(this.mc.fontRendererObj, guiLabelEntry.func_178935_b(), n, n2, 85 + 12 - 52 + 105, 0x60 ^ 0x74, -" ".length());
        }
        guiLabel.visible = guiLabelEntry.func_178934_d();
        guiLabel.func_175202_a(guiLabelEntry.func_178936_c());
        guiLabel.setCentered();
        return guiLabel;
    }
    
    public GuiPageButtonList(final Minecraft minecraft, final int n, final int n2, final int n3, final int n4, final int n5, final GuiResponder field_178076_z, final GuiListEntry[]... field_178078_x) {
        super(minecraft, n, n2, n3, n4, n5);
        this.field_178074_u = (List<GuiEntry>)Lists.newArrayList();
        this.field_178073_v = new IntHashMap<Gui>();
        this.field_178072_w = (List<GuiTextField>)Lists.newArrayList();
        this.field_178076_z = field_178076_z;
        this.field_178078_x = field_178078_x;
        this.field_148163_i = ("".length() != 0);
        this.func_178069_s();
        this.func_178055_t();
    }
    
    @Override
    public GuiEntry getListEntry(final int n) {
        return this.field_178074_u.get(n);
    }
    
    public void func_178062_a(final char c, final int n) {
        if (this.field_178075_A instanceof GuiTextField) {
            final GuiTextField guiTextField = (GuiTextField)this.field_178075_A;
            if (!GuiScreen.isKeyComboCtrlV(n)) {
                if (n == (0x46 ^ 0x49)) {
                    guiTextField.setFocused("".length() != 0);
                    int n2 = this.field_178072_w.indexOf(this.field_178075_A);
                    if (GuiScreen.isShiftKeyDown()) {
                        if (n2 == 0) {
                            n2 = this.field_178072_w.size() - " ".length();
                            "".length();
                            if (3 != 3) {
                                throw null;
                            }
                        }
                        else {
                            --n2;
                            "".length();
                            if (4 <= -1) {
                                throw null;
                            }
                        }
                    }
                    else if (n2 == this.field_178072_w.size() - " ".length()) {
                        n2 = "".length();
                        "".length();
                        if (4 != 4) {
                            throw null;
                        }
                    }
                    else {
                        ++n2;
                    }
                    this.field_178075_A = this.field_178072_w.get(n2);
                    final GuiTextField guiTextField2 = (GuiTextField)this.field_178075_A;
                    guiTextField2.setFocused(" ".length() != 0);
                    final int n3 = guiTextField2.yPosition + this.slotHeight;
                    final int yPosition = guiTextField2.yPosition;
                    if (n3 > this.bottom) {
                        this.amountScrolled += n3 - this.bottom;
                        "".length();
                        if (3 <= -1) {
                            throw null;
                        }
                    }
                    else if (yPosition < this.top) {
                        this.amountScrolled = yPosition;
                        "".length();
                        if (4 <= 0) {
                            throw null;
                        }
                    }
                }
                else {
                    guiTextField.textboxKeyTyped(c, n);
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
            }
            else {
                final String[] split = GuiScreen.getClipboardString().split(GuiPageButtonList.I["".length()]);
                int n5;
                final int n4 = n5 = this.field_178072_w.indexOf(this.field_178075_A);
                final String[] array;
                final int length = (array = split).length;
                int i = "".length();
                "".length();
                if (3 >= 4) {
                    throw null;
                }
                while (i < length) {
                    this.field_178072_w.get(n5).setText(array[i]);
                    if (n5 == this.field_178072_w.size() - " ".length()) {
                        n5 = "".length();
                        "".length();
                        if (3 == -1) {
                            throw null;
                        }
                    }
                    else {
                        ++n5;
                    }
                    if (n5 == n4) {
                        "".length();
                        if (2 < 2) {
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
    }
    
    public Gui func_178056_g() {
        return this.field_178075_A;
    }
    
    @Override
    public IGuiListEntry getListEntry(final int n) {
        return this.getListEntry(n);
    }
    
    public int func_178059_e() {
        return this.field_178077_y;
    }
    
    private GuiSlider func_178067_a(final int n, final int n2, final GuiSlideEntry guiSlideEntry) {
        final GuiSlider guiSlider = new GuiSlider(this.field_178076_z, guiSlideEntry.func_178935_b(), n, n2, guiSlideEntry.func_178936_c(), guiSlideEntry.func_178943_e(), guiSlideEntry.func_178944_f(), guiSlideEntry.func_178942_g(), guiSlideEntry.func_178945_a());
        guiSlider.visible = guiSlideEntry.func_178934_d();
        return guiSlider;
    }
    
    private Gui func_178058_a(final GuiListEntry guiListEntry, final int n, final boolean b) {
        Gui gui;
        if (guiListEntry instanceof GuiSlideEntry) {
            gui = this.func_178067_a(this.width / "  ".length() - (62 + 17 - 16 + 92) + n, "".length(), (GuiSlideEntry)guiListEntry);
            "".length();
            if (0 == 2) {
                throw null;
            }
        }
        else if (guiListEntry instanceof GuiButtonEntry) {
            gui = this.func_178065_a(this.width / "  ".length() - (63 + 45 - 16 + 63) + n, "".length(), (GuiButtonEntry)guiListEntry);
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else if (guiListEntry instanceof EditBoxEntry) {
            gui = this.func_178068_a(this.width / "  ".length() - (87 + 35 - 7 + 40) + n, "".length(), (EditBoxEntry)guiListEntry);
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        else if (guiListEntry instanceof GuiLabelEntry) {
            gui = this.func_178063_a(this.width / "  ".length() - (61 + 58 + 31 + 5) + n, "".length(), (GuiLabelEntry)guiListEntry, b);
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            gui = null;
        }
        return gui;
    }
    
    public static class GuiEntry implements IGuiListEntry
    {
        private final Gui field_178029_b;
        private Gui field_178028_d;
        private final Minecraft field_178031_a;
        private final Gui field_178030_c;
        
        private void func_178025_a(final GuiLabel guiLabel, final int field_146174_h, final int n, final int n2, final boolean b) {
            guiLabel.field_146174_h = field_146174_h;
            if (!b) {
                guiLabel.drawLabel(this.field_178031_a, n, n2);
            }
        }
        
        @Override
        public void setSelected(final int n, final int n2, final int n3) {
            this.func_178017_a(this.field_178029_b, n3, "".length(), "".length(), " ".length() != 0);
            this.func_178017_a(this.field_178030_c, n3, "".length(), "".length(), " ".length() != 0);
        }
        
        private void func_178019_b(final GuiButton guiButton, final int n, final int n2, final int n3) {
            guiButton.mouseReleased(n, n2);
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
                if (-1 >= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static Gui access$0(final GuiEntry guiEntry) {
            return guiEntry.field_178029_b;
        }
        
        public GuiEntry(final Gui field_178029_b, final Gui field_178030_c) {
            this.field_178031_a = Minecraft.getMinecraft();
            this.field_178029_b = field_178029_b;
            this.field_178030_c = field_178030_c;
        }
        
        static Gui access$1(final GuiEntry guiEntry) {
            return guiEntry.field_178030_c;
        }
        
        private void func_178018_a(final GuiTextField field_178028_d, final int n, final int n2, final int n3) {
            field_178028_d.mouseClicked(n, n2, n3);
            if (field_178028_d.isFocused()) {
                this.field_178028_d = field_178028_d;
            }
        }
        
        static Gui access$2(final GuiEntry guiEntry) {
            return guiEntry.field_178028_d;
        }
        
        @Override
        public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            final boolean func_178026_a = this.func_178026_a(this.field_178029_b, n2, n3, n4);
            final boolean func_178026_a2 = this.func_178026_a(this.field_178030_c, n2, n3, n4);
            if (!func_178026_a && !func_178026_a2) {
                return "".length() != 0;
            }
            return " ".length() != 0;
        }
        
        private void func_178024_a(final GuiButton guiButton, final int yPosition, final int n, final int n2, final boolean b) {
            guiButton.yPosition = yPosition;
            if (!b) {
                guiButton.drawButton(this.field_178031_a, n, n2);
            }
        }
        
        private void func_178017_a(final Gui gui, final int n, final int n2, final int n3, final boolean b) {
            if (gui != null) {
                if (gui instanceof GuiButton) {
                    this.func_178024_a((GuiButton)gui, n, n2, n3, b);
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
                else if (gui instanceof GuiTextField) {
                    this.func_178027_a((GuiTextField)gui, n, b);
                    "".length();
                    if (1 < 1) {
                        throw null;
                    }
                }
                else if (gui instanceof GuiLabel) {
                    this.func_178025_a((GuiLabel)gui, n, n2, n3, b);
                }
            }
        }
        
        public Gui func_178021_b() {
            return this.field_178030_c;
        }
        
        public Gui func_178022_a() {
            return this.field_178029_b;
        }
        
        private boolean func_178026_a(final Gui gui, final int n, final int n2, final int n3) {
            if (gui == null) {
                return "".length() != 0;
            }
            if (gui instanceof GuiButton) {
                return this.func_178023_a((GuiButton)gui, n, n2, n3);
            }
            if (gui instanceof GuiTextField) {
                this.func_178018_a((GuiTextField)gui, n, n2, n3);
            }
            return "".length() != 0;
        }
        
        @Override
        public void drawEntry(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
            this.func_178017_a(this.field_178029_b, n3, n6, n7, "".length() != 0);
            this.func_178017_a(this.field_178030_c, n3, n6, n7, "".length() != 0);
        }
        
        private void func_178016_b(final Gui gui, final int n, final int n2, final int n3) {
            if (gui != null && gui instanceof GuiButton) {
                this.func_178019_b((GuiButton)gui, n, n2, n3);
            }
        }
        
        @Override
        public void mouseReleased(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            this.func_178016_b(this.field_178029_b, n2, n3, n4);
            this.func_178016_b(this.field_178030_c, n2, n3, n4);
        }
        
        private boolean func_178023_a(final GuiButton field_178028_d, final int n, final int n2, final int n3) {
            final boolean mousePressed = field_178028_d.mousePressed(this.field_178031_a, n, n2);
            if (mousePressed) {
                this.field_178028_d = field_178028_d;
            }
            return mousePressed;
        }
        
        private void func_178027_a(final GuiTextField guiTextField, final int yPosition, final boolean b) {
            guiTextField.yPosition = yPosition;
            if (!b) {
                guiTextField.drawTextBox();
            }
        }
    }
    
    public static class EditBoxEntry extends GuiListEntry
    {
        private final Predicate<String> field_178951_a;
        
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
        
        public Predicate<String> func_178950_a() {
            return this.field_178951_a;
        }
        
        public EditBoxEntry(final int n, final String s, final boolean b, final Predicate<String> predicate) {
            super(n, s, b);
            this.field_178951_a = (Predicate<String>)Objects.firstNonNull((Object)predicate, (Object)Predicates.alwaysTrue());
        }
    }
    
    public static class GuiListEntry
    {
        private final boolean field_178938_c;
        private final String field_178937_b;
        private final int field_178939_a;
        
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
                if (-1 != -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public GuiListEntry(final int field_178939_a, final String field_178937_b, final boolean field_178938_c) {
            this.field_178939_a = field_178939_a;
            this.field_178937_b = field_178937_b;
            this.field_178938_c = field_178938_c;
        }
        
        public boolean func_178934_d() {
            return this.field_178938_c;
        }
        
        public int func_178935_b() {
            return this.field_178939_a;
        }
        
        public String func_178936_c() {
            return this.field_178937_b;
        }
    }
    
    public interface GuiResponder
    {
        void onTick(final int p0, final float p1);
        
        void func_175321_a(final int p0, final boolean p1);
        
        void func_175319_a(final int p0, final String p1);
    }
    
    public static class GuiButtonEntry extends GuiListEntry
    {
        private final boolean field_178941_a;
        
        public boolean func_178940_a() {
            return this.field_178941_a;
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
                if (-1 != -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public GuiButtonEntry(final int n, final String s, final boolean b, final boolean field_178941_a) {
            super(n, s, b);
            this.field_178941_a = field_178941_a;
        }
    }
    
    public static class GuiSlideEntry extends GuiListEntry
    {
        private final GuiSlider.FormatHelper field_178949_a;
        private final float field_178946_d;
        private final float field_178947_b;
        private final float field_178948_c;
        
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
                if (4 <= 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public GuiSlideEntry(final int n, final String s, final boolean b, final GuiSlider.FormatHelper field_178949_a, final float field_178947_b, final float field_178948_c, final float field_178946_d) {
            super(n, s, b);
            this.field_178949_a = field_178949_a;
            this.field_178947_b = field_178947_b;
            this.field_178948_c = field_178948_c;
            this.field_178946_d = field_178946_d;
        }
        
        public float func_178942_g() {
            return this.field_178946_d;
        }
        
        public GuiSlider.FormatHelper func_178945_a() {
            return this.field_178949_a;
        }
        
        public float func_178944_f() {
            return this.field_178948_c;
        }
        
        public float func_178943_e() {
            return this.field_178947_b;
        }
    }
    
    public static class GuiLabelEntry extends GuiListEntry
    {
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
                if (1 <= -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public GuiLabelEntry(final int n, final String s, final boolean b) {
            super(n, s, b);
        }
    }
}
