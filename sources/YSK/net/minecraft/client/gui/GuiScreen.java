package net.minecraft.client.gui;

import com.google.common.base.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.stats.*;
import org.apache.commons.lang3.*;
import java.awt.*;
import net.minecraft.entity.player.*;
import net.minecraft.event.*;
import java.net.*;
import java.io.*;
import net.minecraft.client.gui.stream.*;
import tv.twitch.chat.*;
import java.lang.reflect.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import java.awt.datatransfer.*;
import org.lwjgl.input.*;
import java.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public abstract class GuiScreen extends Gui implements GuiYesNoCallback
{
    private int touchValue;
    private long lastMouseEvent;
    private GuiButton selectedButton;
    private URI clickedLinkURI;
    public int height;
    private static final Logger LOGGER;
    public boolean allowUserInput;
    protected List<GuiLabel> labelList;
    protected List<GuiButton> buttonList;
    private static final String[] I;
    public int width;
    private int eventButton;
    private static final Splitter NEWLINE_SPLITTER;
    private static final Set<String> PROTOCOLS;
    protected FontRenderer fontRendererObj;
    protected RenderItem itemRender;
    protected Minecraft mc;
    
    protected void handleComponentHover(final IChatComponent chatComponent, final int n, final int n2) {
        if (chatComponent != null && chatComponent.getChatStyle().getChatHoverEvent() != null) {
            final HoverEvent chatHoverEvent = chatComponent.getChatStyle().getChatHoverEvent();
            Label_0842: {
                if (chatHoverEvent.getAction() == HoverEvent.Action.SHOW_ITEM) {
                    ItemStack loadItemStackFromNBT = null;
                    try {
                        final NBTTagCompound tagFromJson = JsonToNBT.getTagFromJson(chatHoverEvent.getValue().getUnformattedText());
                        if (tagFromJson instanceof NBTTagCompound) {
                            loadItemStackFromNBT = ItemStack.loadItemStackFromNBT(tagFromJson);
                            "".length();
                            if (false) {
                                throw null;
                            }
                        }
                    }
                    catch (NBTException ex) {}
                    if (loadItemStackFromNBT != null) {
                        this.renderToolTip(loadItemStackFromNBT, n, n2);
                        "".length();
                        if (3 < 3) {
                            throw null;
                        }
                    }
                    else {
                        this.drawCreativeTabHoveringText(EnumChatFormatting.RED + GuiScreen.I["   ".length()], n, n2);
                        "".length();
                        if (4 != 4) {
                            throw null;
                        }
                    }
                }
                else {
                    if (chatHoverEvent.getAction() == HoverEvent.Action.SHOW_ENTITY) {
                        if (!this.mc.gameSettings.advancedItemTooltips) {
                            break Label_0842;
                        }
                        try {
                            final NBTTagCompound tagFromJson2 = JsonToNBT.getTagFromJson(chatHoverEvent.getValue().getUnformattedText());
                            if (tagFromJson2 instanceof NBTTagCompound) {
                                final ArrayList arrayList = Lists.newArrayList();
                                final NBTTagCompound nbtTagCompound = tagFromJson2;
                                arrayList.add(nbtTagCompound.getString(GuiScreen.I[0xB5 ^ 0xB1]));
                                if (nbtTagCompound.hasKey(GuiScreen.I[0xA ^ 0xF], 0x44 ^ 0x4C)) {
                                    final String string = nbtTagCompound.getString(GuiScreen.I[0x13 ^ 0x15]);
                                    arrayList.add(GuiScreen.I[0xAE ^ 0xA9] + string + GuiScreen.I[0x92 ^ 0x9A] + EntityList.getIDFromString(string) + GuiScreen.I[0xAA ^ 0xA3]);
                                }
                                arrayList.add(nbtTagCompound.getString(GuiScreen.I[0xCE ^ 0xC4]));
                                this.drawHoveringText(arrayList, n, n2);
                                "".length();
                                if (false) {
                                    throw null;
                                }
                                break Label_0842;
                            }
                            else {
                                this.drawCreativeTabHoveringText(EnumChatFormatting.RED + GuiScreen.I[0x32 ^ 0x39], n, n2);
                                "".length();
                                if (3 >= 4) {
                                    throw null;
                                }
                                break Label_0842;
                            }
                        }
                        catch (NBTException ex2) {
                            this.drawCreativeTabHoveringText(EnumChatFormatting.RED + GuiScreen.I[0xA6 ^ 0xAA], n, n2);
                            "".length();
                            if (3 < 0) {
                                throw null;
                            }
                            break Label_0842;
                        }
                    }
                    if (chatHoverEvent.getAction() == HoverEvent.Action.SHOW_TEXT) {
                        this.drawHoveringText(GuiScreen.NEWLINE_SPLITTER.splitToList((CharSequence)chatHoverEvent.getValue().getFormattedText()), n, n2);
                        "".length();
                        if (0 <= -1) {
                            throw null;
                        }
                    }
                    else if (chatHoverEvent.getAction() == HoverEvent.Action.SHOW_ACHIEVEMENT) {
                        final StatBase oneShotStat = StatList.getOneShotStat(chatHoverEvent.getValue().getUnformattedText());
                        if (oneShotStat != null) {
                            final IChatComponent statName = oneShotStat.getStatName();
                            final StringBuilder sb = new StringBuilder(GuiScreen.I[0x3D ^ 0x30]);
                            String s;
                            if (oneShotStat.isAchievement()) {
                                s = GuiScreen.I[0x3B ^ 0x35];
                                "".length();
                                if (2 >= 3) {
                                    throw null;
                                }
                            }
                            else {
                                s = GuiScreen.I[0xA4 ^ 0xAB];
                            }
                            final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation(sb.append(s).toString(), new Object["".length()]);
                            chatComponentTranslation.getChatStyle().setItalic(" ".length() != 0);
                            String description;
                            if (oneShotStat instanceof Achievement) {
                                description = ((Achievement)oneShotStat).getDescription();
                                "".length();
                                if (2 <= -1) {
                                    throw null;
                                }
                            }
                            else {
                                description = null;
                            }
                            final String s2 = description;
                            final String[] array = new String["  ".length()];
                            array["".length()] = statName.getFormattedText();
                            array[" ".length()] = chatComponentTranslation.getFormattedText();
                            final ArrayList arrayList2 = Lists.newArrayList((Object[])array);
                            if (s2 != null) {
                                arrayList2.addAll(this.fontRendererObj.listFormattedStringToWidth(s2, 100 + 77 - 107 + 80));
                            }
                            this.drawHoveringText(arrayList2, n, n2);
                            "".length();
                            if (4 != 4) {
                                throw null;
                            }
                        }
                        else {
                            this.drawCreativeTabHoveringText(EnumChatFormatting.RED + GuiScreen.I[0x93 ^ 0x83], n, n2);
                        }
                    }
                }
            }
            GlStateManager.disableLighting();
        }
    }
    
    public void handleKeyboardInput() throws IOException {
        if (Keyboard.getEventKeyState()) {
            this.keyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
        }
        this.mc.dispatchKeypresses();
    }
    
    public void updateScreen() {
    }
    
    protected void setText(final String s, final boolean b) {
    }
    
    public static boolean isKeyComboCtrlA(final int n) {
        if (n == (0x5 ^ 0x1B) && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void confirmClicked(final boolean b, final int n) {
        if (n == 20665897 + 23176119 - 34025293 + 21285286) {
            if (b) {
                this.openWebLink(this.clickedLinkURI);
            }
            this.clickedLinkURI = null;
            this.mc.displayGuiScreen(this);
        }
    }
    
    public static void setClipboardString(final String s) {
        if (!StringUtils.isEmpty((CharSequence)s)) {
            try {
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(s), null);
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
            catch (Exception ex) {}
        }
    }
    
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        if (n3 == 0) {
            int i = "".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
            while (i < this.buttonList.size()) {
                final GuiButton selectedButton = this.buttonList.get(i);
                if (selectedButton.mousePressed(this.mc, n, n2)) {
                    (this.selectedButton = selectedButton).playPressSound(this.mc.getSoundHandler());
                    this.actionPerformed(selectedButton);
                }
                ++i;
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
            if (0 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void onGuiClosed() {
    }
    
    public void drawWorldBackground(final int n) {
        if (this.mc.theWorld != null) {
            this.drawGradientRect("".length(), "".length(), this.width, this.height, -(434621916 + 1022473490 - 1209327042 + 824920772), -(436949106 + 264906447 + 70190262 + 32207865));
            "".length();
            if (0 == 2) {
                throw null;
            }
        }
        else {
            this.drawBackground(n);
        }
    }
    
    public static boolean isKeyComboCtrlC(final int n) {
        if (n == (0x4C ^ 0x62) && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void sendChatMessage(final String s) {
        this.sendChatMessage(s, " ".length() != 0);
    }
    
    public static boolean isShiftKeyDown() {
        if (!Keyboard.isKeyDown(0xEE ^ 0xC4) && !Keyboard.isKeyDown(0x82 ^ 0xB4)) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public void drawScreen(final int n, final int n2, final float n3) {
        int i = "".length();
        "".length();
        if (3 < 0) {
            throw null;
        }
        while (i < this.buttonList.size()) {
            this.buttonList.get(i).drawButton(this.mc, n, n2);
            ++i;
        }
        int j = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (j < this.labelList.size()) {
            this.labelList.get(j).drawLabel(this.mc, n, n2);
            ++j;
        }
    }
    
    protected void renderToolTip(final ItemStack itemStack, final int n, final int n2) {
        final List<String> tooltip = itemStack.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
        int i = "".length();
        "".length();
        if (2 < 1) {
            throw null;
        }
        while (i < tooltip.size()) {
            if (i == 0) {
                tooltip.set(i, itemStack.getRarity().rarityColor + tooltip.get(i));
                "".length();
                if (4 < 2) {
                    throw null;
                }
            }
            else {
                tooltip.set(i, EnumChatFormatting.GRAY + tooltip.get(i));
            }
            ++i;
        }
        this.drawHoveringText(tooltip, n, n2);
    }
    
    protected void drawCreativeTabHoveringText(final String s, final int n, final int n2) {
        final String[] array = new String[" ".length()];
        array["".length()] = s;
        this.drawHoveringText(Arrays.asList(array), n, n2);
    }
    
    protected boolean handleComponentClick(final IChatComponent chatComponent) {
        if (chatComponent == null) {
            return "".length() != 0;
        }
        final ClickEvent chatClickEvent = chatComponent.getChatStyle().getChatClickEvent();
        if (isShiftKeyDown()) {
            if (chatComponent.getChatStyle().getInsertion() != null) {
                this.setText(chatComponent.getChatStyle().getInsertion(), "".length() != 0);
                "".length();
                if (4 < 0) {
                    throw null;
                }
            }
        }
        else if (chatClickEvent != null) {
            if (chatClickEvent.getAction() == ClickEvent.Action.OPEN_URL) {
                if (!this.mc.gameSettings.chatLinks) {
                    return "".length() != 0;
                }
                try {
                    final URI clickedLinkURI = new URI(chatClickEvent.getValue());
                    final String scheme = clickedLinkURI.getScheme();
                    if (scheme == null) {
                        throw new URISyntaxException(chatClickEvent.getValue(), GuiScreen.I[0x99 ^ 0x88]);
                    }
                    if (!GuiScreen.PROTOCOLS.contains(scheme.toLowerCase())) {
                        throw new URISyntaxException(chatClickEvent.getValue(), GuiScreen.I[0x14 ^ 0x6] + scheme.toLowerCase());
                    }
                    if (this.mc.gameSettings.chatLinksPrompt) {
                        this.clickedLinkURI = clickedLinkURI;
                        this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, chatClickEvent.getValue(), 16085726 + 19601957 - 28993898 + 24408224, (boolean)("".length() != 0)));
                        "".length();
                        if (2 == 1) {
                            throw null;
                        }
                        return " ".length() != 0;
                    }
                    else {
                        this.openWebLink(clickedLinkURI);
                        "".length();
                        if (3 != 3) {
                            throw null;
                        }
                        return " ".length() != 0;
                    }
                }
                catch (URISyntaxException ex) {
                    GuiScreen.LOGGER.error(GuiScreen.I[0x33 ^ 0x20] + chatClickEvent, (Throwable)ex);
                    "".length();
                    if (1 == 3) {
                        throw null;
                    }
                    return " ".length() != 0;
                }
            }
            if (chatClickEvent.getAction() == ClickEvent.Action.OPEN_FILE) {
                this.openWebLink(new File(chatClickEvent.getValue()).toURI());
                "".length();
                if (3 <= -1) {
                    throw null;
                }
            }
            else if (chatClickEvent.getAction() == ClickEvent.Action.SUGGEST_COMMAND) {
                this.setText(chatClickEvent.getValue(), " ".length() != 0);
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
            else if (chatClickEvent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                this.sendChatMessage(chatClickEvent.getValue(), "".length() != 0);
                "".length();
                if (3 >= 4) {
                    throw null;
                }
            }
            else if (chatClickEvent.getAction() == ClickEvent.Action.TWITCH_USER_INFO) {
                final ChatUserInfo func_152926_a = this.mc.getTwitchStream().func_152926_a(chatClickEvent.getValue());
                if (func_152926_a != null) {
                    this.mc.displayGuiScreen(new GuiTwitchUserMode(this.mc.getTwitchStream(), func_152926_a));
                    "".length();
                    if (-1 >= 2) {
                        throw null;
                    }
                }
                else {
                    GuiScreen.LOGGER.error(GuiScreen.I[0x8A ^ 0x9E]);
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
            }
            else {
                GuiScreen.LOGGER.error(GuiScreen.I[0x61 ^ 0x74] + chatClickEvent);
            }
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void setWorldAndResolution(final Minecraft mc, final int width, final int height) {
        this.mc = mc;
        this.itemRender = mc.getRenderItem();
        this.fontRendererObj = mc.fontRendererObj;
        this.width = width;
        this.height = height;
        this.buttonList.clear();
        this.initGui();
    }
    
    private void openWebLink(final URI uri) {
        try {
            final Class<?> forName = Class.forName(GuiScreen.I[0x77 ^ 0x61]);
            final Object invoke = forName.getMethod(GuiScreen.I[0xBB ^ 0xAC], (Class<?>[])new Class["".length()]).invoke(null, new Object["".length()]);
            final Class<?> clazz = forName;
            final String s = GuiScreen.I[0x84 ^ 0x9C];
            final Class[] array = new Class[" ".length()];
            array["".length()] = URI.class;
            final Method method = clazz.getMethod(s, (Class<?>[])array);
            final Object o = invoke;
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = uri;
            method.invoke(o, array2);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        catch (Throwable t) {
            GuiScreen.LOGGER.error(GuiScreen.I[0x4E ^ 0x57], t);
        }
    }
    
    protected void keyTyped(final char c, final int n) throws IOException {
        if (n == " ".length()) {
            this.mc.displayGuiScreen(null);
            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }
        }
    }
    
    public void sendChatMessage(final String s, final boolean b) {
        if (b) {
            this.mc.ingameGUI.getChatGUI().addToSentMessages(s);
        }
        this.mc.thePlayer.sendChatMessage(s);
    }
    
    public static boolean isKeyComboCtrlV(final int n) {
        if (n == (0x70 ^ 0x5F) && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static boolean isKeyComboCtrlX(final int n) {
        if (n == (0x56 ^ 0x7B) && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static boolean isAltKeyDown() {
        if (!Keyboard.isKeyDown(0x56 ^ 0x6E) && !Keyboard.isKeyDown(156 + 58 - 93 + 63)) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public void drawDefaultBackground() {
        this.drawWorldBackground("".length());
    }
    
    static {
        I();
        LOGGER = LogManager.getLogger();
        final String[] array = new String["  ".length()];
        array["".length()] = GuiScreen.I["".length()];
        array[" ".length()] = GuiScreen.I[" ".length()];
        PROTOCOLS = Sets.newHashSet((Object[])array);
        NEWLINE_SPLITTER = Splitter.on((char)(0x67 ^ 0x6D));
    }
    
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
    }
    
    public static boolean isCtrlKeyDown() {
        int n;
        if (Minecraft.isRunningOnMac) {
            if (!Keyboard.isKeyDown(180 + 210 - 205 + 34) && !Keyboard.isKeyDown(44 + 135 - 167 + 208)) {
                n = "".length();
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                n = " ".length();
                "".length();
                if (-1 == 1) {
                    throw null;
                }
            }
        }
        else if (!Keyboard.isKeyDown(0x9B ^ 0x86) && !Keyboard.isKeyDown(44 + 80 - 63 + 96)) {
            n = "".length();
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    public boolean doesGuiPauseGame() {
        return " ".length() != 0;
    }
    
    public GuiScreen() {
        this.buttonList = (List<GuiButton>)Lists.newArrayList();
        this.labelList = (List<GuiLabel>)Lists.newArrayList();
    }
    
    public static String getClipboardString() {
        try {
            final Transferable contents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                return (String)contents.getTransferData(DataFlavor.stringFlavor);
            }
        }
        catch (Exception ex) {}
        return GuiScreen.I["  ".length()];
    }
    
    public void handleMouseInput() throws IOException {
        final int n = Mouse.getEventX() * this.width / this.mc.displayWidth;
        final int n2 = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - " ".length();
        final int eventButton = Mouse.getEventButton();
        if (Mouse.getEventButtonState()) {
            if (this.mc.gameSettings.touchscreen) {
                final int touchValue = this.touchValue;
                this.touchValue = touchValue + " ".length();
                if (touchValue > 0) {
                    return;
                }
            }
            this.eventButton = eventButton;
            this.lastMouseEvent = Minecraft.getSystemTime();
            this.mouseClicked(n, n2, this.eventButton);
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (eventButton != -" ".length()) {
            if (this.mc.gameSettings.touchscreen && (this.touchValue -= " ".length()) > 0) {
                return;
            }
            this.eventButton = -" ".length();
            this.mouseReleased(n, n2, eventButton);
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        else if (this.eventButton != -" ".length() && this.lastMouseEvent > 0L) {
            this.mouseClickMove(n, n2, this.eventButton, Minecraft.getSystemTime() - this.lastMouseEvent);
        }
    }
    
    protected void mouseClickMove(final int n, final int n2, final int n3, final long n4) {
    }
    
    public void initGui() {
    }
    
    protected void drawHoveringText(final List<String> list, final int n, final int n2) {
        if (!list.isEmpty()) {
            GlStateManager.disableRescaleNormal();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            int length = "".length();
            final Iterator<String> iterator = list.iterator();
            "".length();
            if (1 >= 3) {
                throw null;
            }
            while (iterator.hasNext()) {
                final int stringWidth = this.fontRendererObj.getStringWidth(iterator.next());
                if (stringWidth > length) {
                    length = stringWidth;
                }
            }
            int n3 = n + (0x43 ^ 0x4F);
            int n4 = n2 - (0x8A ^ 0x86);
            int n5 = 0x4A ^ 0x42;
            if (list.size() > " ".length()) {
                n5 += "  ".length() + (list.size() - " ".length()) * (0xA5 ^ 0xAF);
            }
            if (n3 + length > this.width) {
                n3 -= (0xA7 ^ 0xBB) + length;
            }
            if (n4 + n5 + (0x9B ^ 0x9D) > this.height) {
                n4 = this.height - n5 - (0x13 ^ 0x15);
            }
            this.zLevel = 300.0f;
            this.itemRender.zLevel = 300.0f;
            final int n6 = -(122571516 + 132241241 - 113122656 + 125696763);
            this.drawGradientRect(n3 - "   ".length(), n4 - (0x50 ^ 0x54), n3 + length + "   ".length(), n4 - "   ".length(), n6, n6);
            this.drawGradientRect(n3 - "   ".length(), n4 + n5 + "   ".length(), n3 + length + "   ".length(), n4 + n5 + (0x51 ^ 0x55), n6, n6);
            this.drawGradientRect(n3 - "   ".length(), n4 - "   ".length(), n3 + length + "   ".length(), n4 + n5 + "   ".length(), n6, n6);
            this.drawGradientRect(n3 - (0x30 ^ 0x34), n4 - "   ".length(), n3 - "   ".length(), n4 + n5 + "   ".length(), n6, n6);
            this.drawGradientRect(n3 + length + "   ".length(), n4 - "   ".length(), n3 + length + (0xC7 ^ 0xC3), n4 + n5 + "   ".length(), n6, n6);
            final int n7 = 849912359 + 886709067 - 520082390 + 130881379;
            final int n8 = (n7 & 11699410 + 698609 - 2899178 + 7212581) >> " ".length() | (n7 & -(3789665 + 2530572 - 4867017 + 15323996));
            this.drawGradientRect(n3 - "   ".length(), n4 - "   ".length() + " ".length(), n3 - "   ".length() + " ".length(), n4 + n5 + "   ".length() - " ".length(), n7, n8);
            this.drawGradientRect(n3 + length + "  ".length(), n4 - "   ".length() + " ".length(), n3 + length + "   ".length(), n4 + n5 + "   ".length() - " ".length(), n7, n8);
            this.drawGradientRect(n3 - "   ".length(), n4 - "   ".length(), n3 + length + "   ".length(), n4 - "   ".length() + " ".length(), n7, n7);
            this.drawGradientRect(n3 - "   ".length(), n4 + n5 + "  ".length(), n3 + length + "   ".length(), n4 + n5 + "   ".length(), n8, n8);
            int i = "".length();
            "".length();
            if (3 == 2) {
                throw null;
            }
            while (i < list.size()) {
                this.fontRendererObj.drawStringWithShadow(list.get(i), n3, n4, -" ".length());
                if (i == 0) {
                    n4 += 2;
                }
                n4 += 10;
                ++i;
            }
            this.zLevel = 0.0f;
            this.itemRender.zLevel = 0.0f;
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.enableRescaleNormal();
        }
    }
    
    public void drawBackground(final int n) {
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        this.mc.getTextureManager().bindTexture(GuiScreen.optionsBackground);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        worldRenderer.begin(0x8B ^ 0x8C, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldRenderer.pos(0.0, this.height, 0.0).tex(0.0, this.height / 32.0f + n).color(0x34 ^ 0x74, 0x34 ^ 0x74, 0xDC ^ 0x9C, 77 + 89 - 149 + 238).endVertex();
        worldRenderer.pos(this.width, this.height, 0.0).tex(this.width / 32.0f, this.height / 32.0f + n).color(0x75 ^ 0x35, 0x59 ^ 0x19, 0x83 ^ 0xC3, 43 + 9 + 22 + 181).endVertex();
        worldRenderer.pos(this.width, 0.0, 0.0).tex(this.width / 32.0f, n).color(0x6B ^ 0x2B, 0x71 ^ 0x31, 0x56 ^ 0x16, 28 + 169 - 120 + 178).endVertex();
        worldRenderer.pos(0.0, 0.0, 0.0).tex(0.0, n).color(0x19 ^ 0x59, 0xE0 ^ 0xA0, 0x2F ^ 0x6F, 217 + 244 - 267 + 61).endVertex();
        instance.draw();
    }
    
    public void onResize(final Minecraft minecraft, final int n, final int n2) {
        this.setWorldAndResolution(minecraft, n, n2);
    }
    
    protected void mouseReleased(final int n, final int n2, final int n3) {
        if (this.selectedButton != null && n3 == 0) {
            this.selectedButton.mouseReleased(n, n2);
            this.selectedButton = null;
        }
    }
    
    private static void I() {
        (I = new String[0x4D ^ 0x57])["".length()] = I("\u0006\u000e\r\u0013", "nzycD");
        GuiScreen.I[" ".length()] = I("\u001d#646", "uWBDE");
        GuiScreen.I["  ".length()] = I("", "KQogi");
        GuiScreen.I["   ".length()] = I("\u0004\u000676\t$\fa\u001e\u0011(\u0005`", "MhAWe");
        GuiScreen.I[0x22 ^ 0x26] = I(" +\u001e\n", "NJsoe");
        GuiScreen.I[0x88 ^ 0x8D] = I("5\r>\u000e", "AtNkL");
        GuiScreen.I[0x15 ^ 0x13] = I("\u0012 (6", "fYXSK");
        GuiScreen.I[0x9E ^ 0x99] = I("\u0012\u0013(\u0001Rf", "FjXdh");
        GuiScreen.I[0x43 ^ 0x4B] = I("EQ", "eykJk");
        GuiScreen.I[0x8E ^ 0x87] = I("_", "vQgaG");
        GuiScreen.I[0xA5 ^ 0xAF] = I("\u001f3", "vWDPv");
        GuiScreen.I[0x6F ^ 0x64] = I("0\u00062\u0010'\u0010\fd4%\r\u00010\bj", "yhDqK");
        GuiScreen.I[0x9D ^ 0x91] = I("\u0007\u0005\u001a\u0014\u0006'\u000fL0\u0004:\u0002\u0018\fK", "Nkluj");
        GuiScreen.I[0xA3 ^ 0xAE] = I("$\u0010\u0000\u0003\u001cy\u0010\u000e\u0018\u0003#\r\u0011Y\u001b.\u0014\u0004Y", "Wdawo");
        GuiScreen.I[0xA8 ^ 0xA6] = I("*6*\u000e\u000b=0/\u0002\u0000?", "KUBgn");
        GuiScreen.I[0x1A ^ 0x15] = I("\u0016\r\u0013\u0011\u0010\u0016\r\u001b\u0006", "eyrey");
        GuiScreen.I[0x98 ^ 0x88] = I("\f\u0016\u001f \u001c,\u001cI2\u0004$\f\u00002\u0004,\u001bF \u0013-\u0011\f7\u0015(\u001d\u00075Q", "ExiAp");
        GuiScreen.I[0x7 ^ 0x16] = I("5\u00135\u0016&\u0016\u001df\u0015=\u0017\u000e)\u0006 \u0014", "xzFeO");
        GuiScreen.I[0x3A ^ 0x28] = I("\u0011\r58\u00164\f49\u0003 C6?\t0\f%\"\n~C", "DcFMf");
        GuiScreen.I[0x7A ^ 0x69] = I("!\r!@>B\u0003?\u0002$B\u0019=\u000bj\u0004\u0003=G", "blOgJ");
        GuiScreen.I[0x11 ^ 0x5] = I("6\u001f\u00190*B\u0019\u001fu&\u0003\u0003\u00149+B\u0019\u0007<:\u0001\u0005P =\u0007\u001fP7;\u0016M\u0013:;\u000e\t\u001er:B\u000b\u0019;*B\u0019\u00180#C", "bmpUN");
        GuiScreen.I[0x2E ^ 0x3B] = I("\u0005\u000e\fd\u001fa\n\f,\u001ca\t\r4K5\u000eB+\n/\u0005\u000e&K", "AabCk");
        GuiScreen.I[0x14 ^ 0x2] = I("\u00130#6C\u0018&!y)\u001c\">#\u0002\t", "yQUWm");
        GuiScreen.I[0x72 ^ 0x65] = I("\t\u0014\u0007\u0017*\u001d\u001a\u0007<?", "nqsSO");
        GuiScreen.I[0xB7 ^ 0xAF] = I("\u001a>=\u000f7\u001d", "xLRxD");
        GuiScreen.I[0x2C ^ 0x35] = I(" \u0015=\n\r\r]<F\u0006\u0013\u001f&F\u0005\n\u0014#", "czHfi");
    }
    
    public void handleInput() throws IOException {
        if (Mouse.isCreated()) {
            "".length();
            if (2 <= -1) {
                throw null;
            }
            while (Mouse.next()) {
                this.handleMouseInput();
            }
        }
        if (Keyboard.isCreated()) {
            "".length();
            if (4 <= -1) {
                throw null;
            }
            while (Keyboard.next()) {
                this.handleKeyboardInput();
            }
        }
    }
}
