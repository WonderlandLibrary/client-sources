package wwe.modules.player;

import wwe.utils.*;
import wwe.modules.*;
import java.util.*;
import net.minecraft.util.text.*;
import net.minecraft.network.play.client.*;
import wwe.*;

public class FancyChat extends qMIe
{
    public ModeValue mode;
    
    public FancyChat() {
        super("Fancy Chat+", "Take like a cool person in chat", 0, Category.PLAYER);
    }
    
    @Override
    public void HrDr() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Classic");
        list.add("Derp");
        list.add("Braille");
        list.add("MoorseCode");
        list.add("L33T");
        list.add("Reverse");
        this.mode = new ModeValue("Mode", this, "Current mode for the module", "Derp", list);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        if (this.mode.OnGi().equalsIgnoreCase("Classic")) {
            this.owgW("Fancy Chat+ " + TextFormatting.DARK_GRAY + "Classic");
        }
        if (this.mode.OnGi().equalsIgnoreCase("Derp")) {
            this.owgW("Fancy Chat+ " + TextFormatting.DARK_GRAY + "Derp");
        }
        if (this.mode.OnGi().equalsIgnoreCase("Braille")) {
            this.owgW("Fancy Chat+ " + TextFormatting.DARK_GRAY + "Braille");
        }
        if (this.mode.OnGi().equalsIgnoreCase("L33T")) {
            this.owgW("Fancy Chat+ " + TextFormatting.DARK_GRAY + "L33T");
        }
        if (this.mode.OnGi().equalsIgnoreCase("Reverse")) {
            this.owgW("Fancy Chat+ " + TextFormatting.DARK_GRAY + "Reverse");
        }
    }
    
    @oqrr
    public void gbjy(final uqzS uqzS) {
        if (uqzS.MYlK() instanceof CPacketChatMessage) {
            if (((CPacketChatMessage)uqzS.MYlK()).getMessage().startsWith("/")) {
                return;
            }
            if (this.mode.OnGi().equalsIgnoreCase("Classic")) {
                String s = "";
                final char[] array;
                final int length = (array = ((CPacketChatMessage)uqzS.MYlK()).getMessage().toCharArray()).length;
                int i = 0;
                while (i < length) {
                    final char c = array[i];
                    if (c >= '!' && c <= '\u0080' && !"(){}[]|".contains(Character.toString(c))) {
                        s = String.valueOf(s) + new String(Character.toChars(c + '\ufee0'));
                        if (FancyChat.\u2002\u2004\u2003\u2009\u2001\u2001\u200d\u2005\u2004) {
                            throw null;
                        }
                    }
                    else {
                        s = String.valueOf(s) + c;
                    }
                    ++i;
                    if (FancyChat.\u2002\u2004\u2003\u2009\u2001\u2001\u200d\u2005\u2004) {
                        throw null;
                    }
                }
                yYGD.NyBC((CPacketChatMessage)uqzS.MYlK(), s);
            }
            if (this.mode.OnGi().equalsIgnoreCase("Derp")) {
                final String message = ((CPacketChatMessage)uqzS.MYlK()).getMessage();
                final char[] array2 = message.toCharArray();
                int j = 0;
                while (j < message.length()) {
                    final char[] array3 = array2;
                    final int n = j;
                    char c2;
                    if (Character.isUpperCase(array2[j])) {
                        c2 = Character.toLowerCase(array2[j]);
                        if (FancyChat.\u2002\u2004\u2003\u2009\u2001\u2001\u200d\u2005\u2004) {
                            throw null;
                        }
                    }
                    else {
                        c2 = Character.toUpperCase(array2[j]);
                    }
                    array3[n] = c2;
                    j += 2;
                    if (FancyChat.\u2002\u2004\u2003\u2009\u2001\u2001\u200d\u2005\u2004) {
                        throw null;
                    }
                }
                yYGD.NyBC((CPacketChatMessage)uqzS.MYlK(), new String(array2));
            }
            if (this.mode.OnGi().equalsIgnoreCase("L33T")) {
                yYGD.NyBC((CPacketChatMessage)uqzS.MYlK(), ((CPacketChatMessage)uqzS.MYlK()).getMessage().replaceAll("o", "0").replaceAll("l", "1").replaceAll("a", "4").replaceAll("e", "3").replaceAll("i", "!").replaceAll("s", "$"));
            }
            if (this.mode.OnGi().equalsIgnoreCase("Reverse")) {
                final StringBuilder sb = new StringBuilder();
                sb.append(((CPacketChatMessage)uqzS.MYlK()).getMessage());
                yYGD.NyBC((CPacketChatMessage)uqzS.MYlK(), sb.reverse().toString());
            }
        }
        if (this.mode.OnGi().equalsIgnoreCase("Braille")) {
            yYGD.NyBC((CPacketChatMessage)uqzS.MYlK(), ((CPacketChatMessage)uqzS.MYlK()).getMessage().replaceAll("a", "\u2801").replaceAll("b", "\u2803").replaceAll("c", "\u2809").replaceAll("d", "\u2819").replaceAll("e", "\u2811").replaceAll("f", "\u280b").replaceAll("g", "\u281b").replaceAll("h", "\u2813").replaceAll("i", "\u280a").replaceAll("j", "\u281a").replaceAll("k", "\u2805").replaceAll("l", "\u2807").replaceAll("m", "\u280d").replaceAll("n", "\u281d").replaceAll("o", "\u2815").replaceAll("p", "\u280f").replaceAll("q", "\u281f").replaceAll("r", "\u2817").replaceAll("s", "\u280e").replaceAll("t", "\u281e").replaceAll("u", "\u2825").replaceAll("v", "\u2827").replaceAll("w", "\u283a").replaceAll("x", "\u282d").replaceAll("y", "\u283d").replaceAll("z", "\u2835"));
        }
        if (this.mode.OnGi().equalsIgnoreCase("MoorseCode")) {
            yYGD.NyBC((CPacketChatMessage)uqzS.MYlK(), ((CPacketChatMessage)uqzS.MYlK()).getMessage().replaceAll("a", ".-").replaceAll("b", "-...").replaceAll("c", "-.-.").replaceAll("d", "-..").replaceAll("e", ".").replaceAll("d", "-..").replaceAll("g", "--.").replaceAll("h", "....").replaceAll("i", "..").replaceAll("j", ".---").replaceAll("k", "-.-").replaceAll("l", ".-..").replaceAll("m", "--").replaceAll("n", "-.").replaceAll("o", "---").replaceAll("p", ".--.").replaceAll("q", "--.-").replaceAll("r", ".-.").replaceAll("s", "...").replaceAll("t", "-").replaceAll("u", "..-").replaceAll("v", "...-").replaceAll("w", ".--").replaceAll("x", "-..-").replaceAll("y", "-.--").replaceAll("z", "--.."));
        }
    }
    
    @Override
    public void LPxx() {
        hSNE.lyvR(this);
        super.LPxx();
    }
    
    @Override
    public void qBmy() {
        hSNE.kduK(this);
        super.qBmy();
    }
}
