package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.renderer.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;

public class GuiNewChat extends Gui
{
    private boolean isScrolled;
    private final Minecraft mc;
    private static final String[] I;
    private final List<String> sentMessages;
    private final List<ChatLine> chatLines;
    private static final Logger logger;
    private int scrollPos;
    private final List<ChatLine> field_146253_i;
    
    public void printChatMessage(final IChatComponent chatComponent) {
        this.printChatMessageWithOptionalDeletion(chatComponent, "".length());
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\n'.\u0017\u0017\fD", "QdfVC");
    }
    
    public void refreshChat() {
        this.field_146253_i.clear();
        this.resetScroll();
        int i = this.chatLines.size() - " ".length();
        "".length();
        if (1 >= 3) {
            throw null;
        }
        while (i >= 0) {
            final ChatLine chatLine = this.chatLines.get(i);
            this.setChatLine(chatLine.getChatComponent(), chatLine.getChatLineID(), chatLine.getUpdatedCounter(), " ".length() != 0);
            --i;
        }
    }
    
    public void drawChat(final int n) {
        if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
            final int lineCount = this.getLineCount();
            int n2 = "".length();
            int length = "".length();
            final int size = this.field_146253_i.size();
            final float n3 = this.mc.gameSettings.chatOpacity * 0.9f + 0.1f;
            if (size > 0) {
                if (this.getChatOpen()) {
                    n2 = " ".length();
                }
                final float chatScale = this.getChatScale();
                final int ceiling_float_int = MathHelper.ceiling_float_int(this.getChatWidth() / chatScale);
                GlStateManager.pushMatrix();
                GlStateManager.translate(2.0f, 20.0f, 0.0f);
                GlStateManager.scale(chatScale, chatScale, 1.0f);
                int length2 = "".length();
                "".length();
                if (false) {
                    throw null;
                }
                while (length2 + this.scrollPos < this.field_146253_i.size() && length2 < lineCount) {
                    final ChatLine chatLine = this.field_146253_i.get(length2 + this.scrollPos);
                    if (chatLine != null) {
                        final int n4 = n - chatLine.getUpdatedCounter();
                        if (n4 < 63 + 144 - 140 + 133 || n2 != 0) {
                            final double clamp_double = MathHelper.clamp_double((1.0 - n4 / 200.0) * 10.0, 0.0, 1.0);
                            int n5 = (int)(255.0 * (clamp_double * clamp_double));
                            if (n2 != 0) {
                                n5 = 190 + 184 - 231 + 112;
                            }
                            final int n6 = (int)(n5 * n3);
                            ++length;
                            if (n6 > "   ".length()) {
                                final int length3 = "".length();
                                final int n7 = -length2 * (0x4 ^ 0xD);
                                Gui.drawRect(length3, n7 - (0xCD ^ 0xC4), length3 + ceiling_float_int + (0xAE ^ 0xAA), n7, n6 / "  ".length() << (0xA8 ^ 0xB0));
                                final String formattedText = chatLine.getChatComponent().getFormattedText();
                                GlStateManager.enableBlend();
                                this.mc.fontRendererObj.drawStringWithShadow(formattedText, length3, n7 - (0xBC ^ 0xB4), 11938531 + 3458141 - 3817177 + 5197720 + (n6 << (0x40 ^ 0x58)));
                                GlStateManager.disableAlpha();
                                GlStateManager.disableBlend();
                            }
                        }
                    }
                    ++length2;
                }
                if (n2 != 0) {
                    final int font_HEIGHT = this.mc.fontRendererObj.FONT_HEIGHT;
                    GlStateManager.translate(-3.0f, 0.0f, 0.0f);
                    final int n8 = size * font_HEIGHT + size;
                    final int n9 = length * font_HEIGHT + length;
                    final int n10 = this.scrollPos * n9 / size;
                    final int n11 = n9 * n9 / n8;
                    if (n8 != n9) {
                        int n12;
                        if (n10 > 0) {
                            n12 = 147 + 64 - 88 + 47;
                            "".length();
                            if (4 == 3) {
                                throw null;
                            }
                        }
                        else {
                            n12 = (0xF8 ^ 0x98);
                        }
                        final int n13 = n12;
                        int n14;
                        if (this.isScrolled) {
                            n14 = 1818605 + 3189404 - 885601 + 9260043;
                            "".length();
                            if (1 < 1) {
                                throw null;
                            }
                        }
                        else {
                            n14 = 2144522 + 1204318 - 993914 + 1000636;
                        }
                        Gui.drawRect("".length(), -n10, "  ".length(), -n10 - n11, n14 + (n13 << (0x8 ^ 0x10)));
                        Gui.drawRect("  ".length(), -n10, " ".length(), -n10 - n11, 6516406 + 541048 + 2824361 + 3539957 + (n13 << (0xA7 ^ 0xBF)));
                    }
                }
                GlStateManager.popMatrix();
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void scroll(final int n) {
        this.scrollPos += n;
        final int size = this.field_146253_i.size();
        if (this.scrollPos > size - this.getLineCount()) {
            this.scrollPos = size - this.getLineCount();
        }
        if (this.scrollPos <= 0) {
            this.scrollPos = "".length();
            this.isScrolled = ("".length() != 0);
        }
    }
    
    public void deleteChatLine(final int n) {
        final Iterator<ChatLine> iterator = this.field_146253_i.iterator();
        "".length();
        if (3 == -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            if (iterator.next().getChatLineID() == n) {
                iterator.remove();
            }
        }
        final Iterator<ChatLine> iterator2 = this.chatLines.iterator();
        "".length();
        if (3 == 1) {
            throw null;
        }
        while (iterator2.hasNext()) {
            if (iterator2.next().getChatLineID() == n) {
                iterator2.remove();
                "".length();
                if (2 != 2) {
                    throw null;
                }
                break;
            }
        }
    }
    
    public static int calculateChatboxHeight(final float n) {
        final int n2 = 93 + 129 - 171 + 129;
        final int n3 = 0x26 ^ 0x32;
        return MathHelper.floor_float(n * (n2 - n3) + n3);
    }
    
    public GuiNewChat(final Minecraft mc) {
        this.sentMessages = (List<String>)Lists.newArrayList();
        this.chatLines = (List<ChatLine>)Lists.newArrayList();
        this.field_146253_i = (List<ChatLine>)Lists.newArrayList();
        this.mc = mc;
    }
    
    public void printChatMessageWithOptionalDeletion(final IChatComponent chatComponent, final int n) {
        this.setChatLine(chatComponent, n, this.mc.ingameGUI.getUpdateCounter(), "".length() != 0);
        GuiNewChat.logger.info(GuiNewChat.I["".length()] + chatComponent.getUnformattedText());
    }
    
    public IChatComponent getChatComponent(final int n, final int n2) {
        if (!this.getChatOpen()) {
            return null;
        }
        final int scaleFactor = new ScaledResolution(this.mc).getScaleFactor();
        final float chatScale = this.getChatScale();
        final int n3 = n / scaleFactor - "   ".length();
        final int n4 = n2 / scaleFactor - (0x7D ^ 0x66);
        final int floor_float = MathHelper.floor_float(n3 / chatScale);
        final int floor_float2 = MathHelper.floor_float(n4 / chatScale);
        if (floor_float < 0 || floor_float2 < 0) {
            return null;
        }
        final int min = Math.min(this.getLineCount(), this.field_146253_i.size());
        if (floor_float <= MathHelper.floor_float(this.getChatWidth() / this.getChatScale()) && floor_float2 < this.mc.fontRendererObj.FONT_HEIGHT * min + min) {
            final int n5 = floor_float2 / this.mc.fontRendererObj.FONT_HEIGHT + this.scrollPos;
            if (n5 >= 0 && n5 < this.field_146253_i.size()) {
                final ChatLine chatLine = this.field_146253_i.get(n5);
                int length = "".length();
                final Iterator<IChatComponent> iterator = chatLine.getChatComponent().iterator();
                "".length();
                if (2 >= 4) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final IChatComponent chatComponent = iterator.next();
                    if (chatComponent instanceof ChatComponentText) {
                        length += this.mc.fontRendererObj.getStringWidth(GuiUtilRenderComponents.func_178909_a(((ChatComponentText)chatComponent).getChatComponentText_TextValue(), (boolean)("".length() != 0)));
                        if (length > floor_float) {
                            return chatComponent;
                        }
                        continue;
                    }
                }
            }
            return null;
        }
        return null;
    }
    
    public static int calculateChatboxWidth(final float n) {
        final int n2 = 197 + 305 - 290 + 108;
        final int n3 = 0x9B ^ 0xB3;
        return MathHelper.floor_float(n * (n2 - n3) + n3);
    }
    
    public boolean getChatOpen() {
        return this.mc.currentScreen instanceof GuiChat;
    }
    
    public int getChatWidth() {
        return calculateChatboxWidth(this.mc.gameSettings.chatWidth);
    }
    
    public void resetScroll() {
        this.scrollPos = "".length();
        this.isScrolled = ("".length() != 0);
    }
    
    public int getChatHeight() {
        float n;
        if (this.getChatOpen()) {
            n = this.mc.gameSettings.chatHeightFocused;
            "".length();
            if (3 == 4) {
                throw null;
            }
        }
        else {
            n = this.mc.gameSettings.chatHeightUnfocused;
        }
        return calculateChatboxHeight(n);
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    public List<String> getSentMessages() {
        return this.sentMessages;
    }
    
    public float getChatScale() {
        return this.mc.gameSettings.chatScale;
    }
    
    public int getLineCount() {
        return this.getChatHeight() / (0x91 ^ 0x98);
    }
    
    public void addToSentMessages(final String s) {
        if (this.sentMessages.isEmpty() || !this.sentMessages.get(this.sentMessages.size() - " ".length()).equals(s)) {
            this.sentMessages.add(s);
        }
    }
    
    private void setChatLine(final IChatComponent chatComponent, final int n, final int n2, final boolean b) {
        if (n != 0) {
            this.deleteChatLine(n);
        }
        final List<IChatComponent> func_178908_a = GuiUtilRenderComponents.func_178908_a(chatComponent, MathHelper.floor_float(this.getChatWidth() / this.getChatScale()), this.mc.fontRendererObj, "".length() != 0, "".length() != 0);
        final boolean chatOpen = this.getChatOpen();
        final Iterator<IChatComponent> iterator = func_178908_a.iterator();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final IChatComponent chatComponent2 = iterator.next();
            if (chatOpen && this.scrollPos > 0) {
                this.isScrolled = (" ".length() != 0);
                this.scroll(" ".length());
            }
            this.field_146253_i.add("".length(), new ChatLine(n2, chatComponent2, n));
        }
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (this.field_146253_i.size() > (0xC5 ^ 0xA1)) {
            this.field_146253_i.remove(this.field_146253_i.size() - " ".length());
        }
        if (!b) {
            this.chatLines.add("".length(), new ChatLine(n2, chatComponent, n));
            "".length();
            if (4 <= 1) {
                throw null;
            }
            while (this.chatLines.size() > (0x4C ^ 0x28)) {
                this.chatLines.remove(this.chatLines.size() - " ".length());
            }
        }
    }
    
    public void clearChatMessages() {
        this.field_146253_i.clear();
        this.chatLines.clear();
        this.sentMessages.clear();
    }
}
