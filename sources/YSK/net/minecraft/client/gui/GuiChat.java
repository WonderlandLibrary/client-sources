package net.minecraft.client.gui;

import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import java.util.*;
import org.apache.logging.log4j.*;
import net.minecraft.util.*;
import org.apache.commons.lang3.*;
import com.google.common.collect.*;
import org.lwjgl.input.*;
import java.io.*;

public class GuiChat extends GuiScreen
{
    private boolean playerNamesFound;
    private static final Logger logger;
    protected GuiTextField inputField;
    private static final String[] I;
    private boolean waitingOnAutocomplete;
    private List<String> foundPlayerNames;
    private int autocompleteIndex;
    private String defaultInputFieldText;
    private String historyBuffer;
    private int sentHistoryCursor;
    
    @Override
    public boolean doesGuiPauseGame() {
        return "".length() != 0;
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        Gui.drawRect("  ".length(), this.height - (0x89 ^ 0x87), this.width - "  ".length(), this.height - "  ".length(), -"".length());
        this.inputField.drawTextBox();
        final IChatComponent chatComponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
        if (chatComponent != null && chatComponent.getChatStyle().getChatHoverEvent() != null) {
            this.handleComponentHover(chatComponent, n, n2);
        }
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        this.waitingOnAutocomplete = ("".length() != 0);
        if (n == (0x52 ^ 0x5D)) {
            this.autocompletePlayerNames();
            "".length();
            if (0 == 3) {
                throw null;
            }
        }
        else {
            this.playerNamesFound = ("".length() != 0);
        }
        if (n == " ".length()) {
            this.mc.displayGuiScreen(null);
            "".length();
            if (1 == 2) {
                throw null;
            }
        }
        else if (n != (0xD ^ 0x11) && n != 137 + 73 - 197 + 143) {
            if (n == 113 + 0 + 20 + 67) {
                this.getSentHistory(-" ".length());
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
            else if (n == 111 + 87 - 122 + 132) {
                this.getSentHistory(" ".length());
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
            else if (n == 191 + 114 - 113 + 9) {
                this.mc.ingameGUI.getChatGUI().scroll(this.mc.ingameGUI.getChatGUI().getLineCount() - " ".length());
                "".length();
                if (3 < 0) {
                    throw null;
                }
            }
            else if (n == 186 + 60 - 94 + 57) {
                this.mc.ingameGUI.getChatGUI().scroll(-this.mc.ingameGUI.getChatGUI().getLineCount() + " ".length());
                "".length();
                if (2 <= -1) {
                    throw null;
                }
            }
            else {
                this.inputField.textboxKeyTyped(c, n);
                "".length();
                if (3 <= 2) {
                    throw null;
                }
            }
        }
        else {
            final String trim = this.inputField.getText().trim();
            if (trim.length() > 0) {
                this.sendChatMessage(trim);
            }
            this.mc.displayGuiScreen(null);
        }
    }
    
    private void sendAutocompleteRequest(final String s, final String s2) {
        if (s.length() >= " ".length()) {
            BlockPos blockPos = null;
            if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                blockPos = this.mc.objectMouseOver.getBlockPos();
            }
            this.mc.thePlayer.sendQueue.addToSendQueue(new C14PacketTabComplete(s, blockPos));
            this.waitingOnAutocomplete = (" ".length() != 0);
        }
    }
    
    public void autocompletePlayerNames() {
        if (this.playerNamesFound) {
            this.inputField.deleteFromCursor(this.inputField.func_146197_a(-" ".length(), this.inputField.getCursorPosition(), "".length() != 0) - this.inputField.getCursorPosition());
            if (this.autocompleteIndex >= this.foundPlayerNames.size()) {
                this.autocompleteIndex = "".length();
                "".length();
                if (false) {
                    throw null;
                }
            }
        }
        else {
            final int func_146197_a = this.inputField.func_146197_a(-" ".length(), this.inputField.getCursorPosition(), "".length() != 0);
            this.foundPlayerNames.clear();
            this.autocompleteIndex = "".length();
            this.sendAutocompleteRequest(this.inputField.getText().substring("".length(), this.inputField.getCursorPosition()), this.inputField.getText().substring(func_146197_a).toLowerCase());
            if (this.foundPlayerNames.isEmpty()) {
                return;
            }
            this.playerNamesFound = (" ".length() != 0);
            this.inputField.deleteFromCursor(func_146197_a - this.inputField.getCursorPosition());
        }
        if (this.foundPlayerNames.size() > " ".length()) {
            final StringBuilder sb = new StringBuilder();
            final Iterator<String> iterator = this.foundPlayerNames.iterator();
            "".length();
            if (3 == 2) {
                throw null;
            }
            while (iterator.hasNext()) {
                final String s = iterator.next();
                if (sb.length() > 0) {
                    sb.append(GuiChat.I[0x97 ^ 0x93]);
                }
                sb.append(s);
            }
            this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new ChatComponentText(sb.toString()), " ".length());
        }
        final GuiTextField inputField = this.inputField;
        final List<String> foundPlayerNames = this.foundPlayerNames;
        final int autocompleteIndex = this.autocompleteIndex;
        this.autocompleteIndex = autocompleteIndex + " ".length();
        inputField.writeText(foundPlayerNames.get(autocompleteIndex));
    }
    
    @Override
    public void updateScreen() {
        this.inputField.updateCursorCounter();
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    public void getSentHistory(final int n) {
        final int n2 = this.sentHistoryCursor + n;
        final int size = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
        final int clamp_int = MathHelper.clamp_int(n2, "".length(), size);
        if (clamp_int != this.sentHistoryCursor) {
            if (clamp_int == size) {
                this.sentHistoryCursor = size;
                this.inputField.setText(this.historyBuffer);
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else {
                if (this.sentHistoryCursor == size) {
                    this.historyBuffer = this.inputField.getText();
                }
                this.inputField.setText(this.mc.ingameGUI.getChatGUI().getSentMessages().get(clamp_int));
                this.sentHistoryCursor = clamp_int;
            }
        }
    }
    
    @Override
    protected void setText(final String text, final boolean b) {
        if (b) {
            this.inputField.setText(text);
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            this.inputField.writeText(text);
        }
    }
    
    public void onAutocompleteResponse(final String[] array) {
        if (this.waitingOnAutocomplete) {
            this.playerNamesFound = ("".length() != 0);
            this.foundPlayerNames.clear();
            final int length = array.length;
            int i = "".length();
            "".length();
            if (4 < 3) {
                throw null;
            }
            while (i < length) {
                final String s = array[i];
                if (s.length() > 0) {
                    this.foundPlayerNames.add(s);
                }
                ++i;
            }
            final String substring = this.inputField.getText().substring(this.inputField.func_146197_a(-" ".length(), this.inputField.getCursorPosition(), (boolean)("".length() != 0)));
            final String commonPrefix = StringUtils.getCommonPrefix(array);
            if (commonPrefix.length() > 0 && !substring.equalsIgnoreCase(commonPrefix)) {
                this.inputField.deleteFromCursor(this.inputField.func_146197_a(-" ".length(), this.inputField.getCursorPosition(), "".length() != 0) - this.inputField.getCursorPosition());
                this.inputField.writeText(commonPrefix);
                "".length();
                if (1 < 0) {
                    throw null;
                }
            }
            else if (this.foundPlayerNames.size() > 0) {
                this.playerNamesFound = (" ".length() != 0);
                this.autocompletePlayerNames();
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
            if (1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[0x98 ^ 0x9D])["".length()] = I("", "lZyts");
        GuiChat.I[" ".length()] = I("", "EcvXg");
        GuiChat.I["  ".length()] = I("", "jCgHH");
        GuiChat.I["   ".length()] = I("", "MmAIa");
        GuiChat.I[0x50 ^ 0x54] = I("gY", "KyYEb");
    }
    
    public GuiChat() {
        this.historyBuffer = GuiChat.I["".length()];
        this.sentHistoryCursor = -" ".length();
        this.foundPlayerNames = (List<String>)Lists.newArrayList();
        this.defaultInputFieldText = GuiChat.I[" ".length()];
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)(" ".length() != 0));
        this.sentHistoryCursor = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
        (this.inputField = new GuiTextField("".length(), this.fontRendererObj, 0x89 ^ 0x8D, this.height - (0xA3 ^ 0xAF), this.width - (0x61 ^ 0x65), 0x14 ^ 0x18)).setMaxStringLength(0x79 ^ 0x1D);
        this.inputField.setEnableBackgroundDrawing("".length() != 0);
        this.inputField.setFocused(" ".length() != 0);
        this.inputField.setText(this.defaultInputFieldText);
        this.inputField.setCanLoseFocus("".length() != 0);
    }
    
    public GuiChat(final String defaultInputFieldText) {
        this.historyBuffer = GuiChat.I["  ".length()];
        this.sentHistoryCursor = -" ".length();
        this.foundPlayerNames = (List<String>)Lists.newArrayList();
        this.defaultInputFieldText = GuiChat.I["   ".length()];
        this.defaultInputFieldText = defaultInputFieldText;
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        if (n3 == 0 && this.handleComponentClick(this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY()))) {
            return;
        }
        this.inputField.mouseClicked(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)("".length() != 0));
        this.mc.ingameGUI.getChatGUI().resetScroll();
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int n = Mouse.getEventDWheel();
        if (n != 0) {
            if (n > " ".length()) {
                n = " ".length();
            }
            if (n < -" ".length()) {
                n = -" ".length();
            }
            if (!GuiScreen.isShiftKeyDown()) {
                n *= (0x11 ^ 0x16);
            }
            this.mc.ingameGUI.getChatGUI().scroll(n);
        }
    }
}
