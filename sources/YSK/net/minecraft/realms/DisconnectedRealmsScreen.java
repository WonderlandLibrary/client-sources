package net.minecraft.realms;

import net.minecraft.util.*;
import java.util.*;

public class DisconnectedRealmsScreen extends RealmsScreen
{
    private List<String> lines;
    private static final String[] I;
    private IChatComponent reason;
    private int textHeight;
    private String title;
    private final RealmsScreen parent;
    
    public DisconnectedRealmsScreen(final RealmsScreen parent, final String s, final IChatComponent reason) {
        this.parent = parent;
        this.title = RealmsScreen.getLocalizedString(s);
        this.reason = reason;
    }
    
    @Override
    public void keyPressed(final char c, final int n) {
        if (n == " ".length()) {
            Realms.setScreen(this.parent);
        }
    }
    
    @Override
    public void init() {
        Realms.setConnectedToRealms("".length() != 0);
        this.buttonsClear();
        this.lines = this.fontSplit(this.reason.getFormattedText(), this.width() - (0x72 ^ 0x40));
        this.textHeight = this.lines.size() * this.fontLineHeight();
        this.buttonsAdd(RealmsScreen.newButton("".length(), this.width() / "  ".length() - (0xF2 ^ 0x96), this.height() / "  ".length() + this.textHeight / "  ".length() + this.fontLineHeight(), RealmsScreen.getLocalizedString(DisconnectedRealmsScreen.I["".length()])));
    }
    
    static {
        I();
    }
    
    @Override
    public void render(final int n, final int n2, final float n3) {
        this.renderBackground();
        this.drawCenteredString(this.title, this.width() / "  ".length(), this.height() / "  ".length() - this.textHeight / "  ".length() - this.fontLineHeight() * "  ".length(), 4448149 + 4277334 - 2423541 + 4882868);
        int n4 = this.height() / "  ".length() - this.textHeight / "  ".length();
        if (this.lines != null) {
            final Iterator<String> iterator = this.lines.iterator();
            "".length();
            if (0 == -1) {
                throw null;
            }
            while (iterator.hasNext()) {
                this.drawCenteredString(iterator.next(), this.width() / "  ".length(), n4, 15780959 + 4519174 - 19329147 + 15806229);
                n4 += this.fontLineHeight();
            }
        }
        super.render(n, n2, n3);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("#<\u0019Y\u0014%*\u001b", "DIpwv");
    }
    
    @Override
    public void buttonClicked(final RealmsButton realmsButton) {
        if (realmsButton.id() == 0) {
            Realms.setScreen(this.parent);
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
            if (3 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
