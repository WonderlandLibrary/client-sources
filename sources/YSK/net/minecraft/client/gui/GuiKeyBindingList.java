package net.minecraft.client.gui;

import net.minecraft.client.*;
import org.apache.commons.lang3.*;
import java.util.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.settings.*;
import net.minecraft.util.*;

public class GuiKeyBindingList extends GuiListExtended
{
    private final IGuiListEntry[] listEntries;
    private final Minecraft mc;
    private int maxListLabelWidth;
    private final GuiControls field_148191_k;
    
    static int access$2(final GuiKeyBindingList list) {
        return list.maxListLabelWidth;
    }
    
    @Override
    public int getListWidth() {
        return super.getListWidth() + (0x4E ^ 0x6E);
    }
    
    @Override
    protected int getScrollBarX() {
        return super.getScrollBarX() + (0x72 ^ 0x7D);
    }
    
    @Override
    public IGuiListEntry getListEntry(final int n) {
        return this.listEntries[n];
    }
    
    static Minecraft access$0(final GuiKeyBindingList list) {
        return list.mc;
    }
    
    @Override
    protected int getSize() {
        return this.listEntries.length;
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static GuiControls access$1(final GuiKeyBindingList list) {
        return list.field_148191_k;
    }
    
    public GuiKeyBindingList(final GuiControls field_148191_k, final Minecraft mc) {
        super(mc, field_148191_k.width, field_148191_k.height, 0x53 ^ 0x6C, field_148191_k.height - (0x7A ^ 0x5A), 0x5F ^ 0x4B);
        this.maxListLabelWidth = "".length();
        this.field_148191_k = field_148191_k;
        this.mc = mc;
        final KeyBinding[] array = (KeyBinding[])ArrayUtils.clone((Object[])mc.gameSettings.keyBindings);
        this.listEntries = new IGuiListEntry[array.length + KeyBinding.getKeybinds().size()];
        Arrays.sort(array);
        int length = "".length();
        Object o = null;
        final KeyBinding[] array2;
        final int length2 = (array2 = array).length;
        int i = "".length();
        "".length();
        if (true != true) {
            throw null;
        }
        while (i < length2) {
            final KeyBinding keyBinding = array2[i];
            final String keyCategory = keyBinding.getKeyCategory();
            if (!keyCategory.equals(o)) {
                o = keyCategory;
                this.listEntries[length++] = new CategoryEntry(keyCategory);
            }
            final int stringWidth = mc.fontRendererObj.getStringWidth(I18n.format(keyBinding.getKeyDescription(), new Object["".length()]));
            if (stringWidth > this.maxListLabelWidth) {
                this.maxListLabelWidth = stringWidth;
            }
            this.listEntries[length++] = new KeyEntry(keyBinding, null);
            ++i;
        }
    }
    
    public class KeyEntry implements IGuiListEntry
    {
        private final GuiButton btnReset;
        private static final String[] I;
        private final KeyBinding keybinding;
        private final String keyDesc;
        private final GuiButton btnChangeKeyBinding;
        final GuiKeyBindingList this$0;
        
        KeyEntry(final GuiKeyBindingList list, final KeyBinding keyBinding, final KeyEntry keyEntry) {
            this(list, keyBinding);
        }
        
        private static void I() {
            (I = new String["   ".length()])["".length()] = I("$9%'\u001a(:8}\u001a\"%.'", "GVKSh");
            KeyEntry.I[" ".length()] = I("LV", "rvdNp");
            KeyEntry.I["  ".length()] = I("kt", "KHzWB");
        }
        
        @Override
        public void mouseReleased(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            this.btnChangeKeyBinding.mouseReleased(n2, n3);
            this.btnReset.mouseReleased(n2, n3);
        }
        
        @Override
        public void setSelected(final int n, final int n2, final int n3) {
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
                if (4 <= -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            if (this.btnChangeKeyBinding.mousePressed(GuiKeyBindingList.access$0(this.this$0), n2, n3)) {
                GuiKeyBindingList.access$1(this.this$0).buttonId = this.keybinding;
                return " ".length() != 0;
            }
            if (this.btnReset.mousePressed(GuiKeyBindingList.access$0(this.this$0), n2, n3)) {
                GuiKeyBindingList.access$0(this.this$0).gameSettings.setOptionKeyBinding(this.keybinding, this.keybinding.getKeyCodeDefault());
                KeyBinding.resetKeyBindingArrayAndHash();
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        @Override
        public void drawEntry(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
            int n8;
            if (GuiKeyBindingList.access$1(this.this$0).buttonId == this.keybinding) {
                n8 = " ".length();
                "".length();
                if (0 >= 1) {
                    throw null;
                }
            }
            else {
                n8 = "".length();
            }
            final int n9 = n8;
            GuiKeyBindingList.access$0(this.this$0).fontRendererObj.drawString(this.keyDesc, n2 + (0x66 ^ 0x3C) - GuiKeyBindingList.access$2(this.this$0), n3 + n5 / "  ".length() - GuiKeyBindingList.access$0(this.this$0).fontRendererObj.FONT_HEIGHT / "  ".length(), 11603745 + 6997777 - 6330425 + 4506118);
            this.btnReset.xPosition = n2 + (8 + 20 - 12 + 174);
            this.btnReset.yPosition = n3;
            final GuiButton btnReset = this.btnReset;
            int enabled;
            if (this.keybinding.getKeyCode() != this.keybinding.getKeyCodeDefault()) {
                enabled = " ".length();
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else {
                enabled = "".length();
            }
            btnReset.enabled = (enabled != 0);
            this.btnReset.drawButton(GuiKeyBindingList.access$0(this.this$0), n6, n7);
            this.btnChangeKeyBinding.xPosition = n2 + (0x3 ^ 0x6A);
            this.btnChangeKeyBinding.yPosition = n3;
            this.btnChangeKeyBinding.displayString = GameSettings.getKeyDisplayString(this.keybinding.getKeyCode());
            int n10 = "".length();
            if (this.keybinding.getKeyCode() != 0) {
                final KeyBinding[] keyBindings;
                final int length = (keyBindings = GuiKeyBindingList.access$0(this.this$0).gameSettings.keyBindings).length;
                int i = "".length();
                "".length();
                if (1 <= -1) {
                    throw null;
                }
                while (i < length) {
                    final KeyBinding keyBinding = keyBindings[i];
                    if (keyBinding != this.keybinding && keyBinding.getKeyCode() == this.keybinding.getKeyCode()) {
                        n10 = " ".length();
                        "".length();
                        if (4 <= 2) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        ++i;
                    }
                }
            }
            if (n9 != 0) {
                this.btnChangeKeyBinding.displayString = EnumChatFormatting.WHITE + KeyEntry.I[" ".length()] + EnumChatFormatting.YELLOW + this.btnChangeKeyBinding.displayString + EnumChatFormatting.WHITE + KeyEntry.I["  ".length()];
                "".length();
                if (0 == 2) {
                    throw null;
                }
            }
            else if (n10 != 0) {
                this.btnChangeKeyBinding.displayString = EnumChatFormatting.RED + this.btnChangeKeyBinding.displayString;
            }
            this.btnChangeKeyBinding.drawButton(GuiKeyBindingList.access$0(this.this$0), n6, n7);
        }
        
        private KeyEntry(final GuiKeyBindingList this$0, final KeyBinding keybinding) {
            this.this$0 = this$0;
            this.keybinding = keybinding;
            this.keyDesc = I18n.format(keybinding.getKeyDescription(), new Object["".length()]);
            this.btnChangeKeyBinding = new GuiButton("".length(), "".length(), "".length(), 0x1 ^ 0x4A, 0xD2 ^ 0xC6, I18n.format(keybinding.getKeyDescription(), new Object["".length()]));
            this.btnReset = new GuiButton("".length(), "".length(), "".length(), 0x9B ^ 0xA9, 0x8B ^ 0x9F, I18n.format(KeyEntry.I["".length()], new Object["".length()]));
        }
        
        static {
            I();
        }
    }
    
    public class CategoryEntry implements IGuiListEntry
    {
        final GuiKeyBindingList this$0;
        private final int labelWidth;
        private final String labelText;
        
        @Override
        public void drawEntry(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
            GuiKeyBindingList.access$0(this.this$0).fontRendererObj.drawString(this.labelText, GuiKeyBindingList.access$0(this.this$0).currentScreen.width / "  ".length() - this.labelWidth / "  ".length(), n3 + n5 - GuiKeyBindingList.access$0(this.this$0).fontRendererObj.FONT_HEIGHT - " ".length(), 14548249 + 6297170 - 9686267 + 5618063);
        }
        
        @Override
        public void setSelected(final int n, final int n2, final int n3) {
        }
        
        public CategoryEntry(final GuiKeyBindingList this$0, final String s) {
            this.this$0 = this$0;
            this.labelText = I18n.format(s, new Object["".length()]);
            this.labelWidth = GuiKeyBindingList.access$0(this$0).fontRendererObj.getStringWidth(this.labelText);
        }
        
        @Override
        public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
            return "".length() != 0;
        }
        
        @Override
        public void mouseReleased(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
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
                if (3 < 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
