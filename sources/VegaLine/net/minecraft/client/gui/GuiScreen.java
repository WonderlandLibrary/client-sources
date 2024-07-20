/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import ru.govno.client.cfg.GuiConfig;
import ru.govno.client.clickgui.ClickGuiScreen;
import ru.govno.client.module.modules.ClickGui;
import ru.govno.client.module.modules.ProContainer;
import ru.govno.client.utils.Command.impl.Panic;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;

public abstract class GuiScreen
extends Gui
implements GuiYesNoCallback {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Set<String> PROTOCOLS = Sets.newHashSet("http", "https");
    private static final Splitter NEWLINE_SPLITTER = Splitter.on('\n');
    protected Minecraft mc;
    protected RenderItem itemRender;
    public static int width;
    public static int height;
    protected List<GuiButton> buttonList = Lists.newArrayList();
    protected List<GuiLabel> labelList = Lists.newArrayList();
    public boolean allowUserInput;
    protected FontRenderer fontRendererObj;
    protected GuiButton selectedButton;
    private int eventButton;
    private long lastMouseEvent;
    private int touchValue;
    private URI clickedLinkURI;
    private boolean field_193977_u;

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (int i = 0; i < this.buttonList.size(); ++i) {
            this.buttonList.get(i).func_191745_a(this.mc, mouseX, mouseY, partialTicks);
        }
        for (int j = 0; j < this.labelList.size(); ++j) {
            this.labelList.get(j).drawLabel(this.mc, mouseX, mouseY);
        }
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1 && (!(this.mc.currentScreen instanceof ClickGuiScreen) || this.mc.currentScreen instanceof ClickGuiScreen && !ClickGuiScreen.colose) && !(this.mc.currentScreen instanceof GuiConfig)) {
            this.mc.displayGuiScreen(null);
            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }
        }
    }

    protected <T extends GuiButton> T addButton(T p_189646_1_) {
        this.buttonList.add(p_189646_1_);
        return p_189646_1_;
    }

    public static String getClipboardString() {
        try {
            Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                return (String)transferable.getTransferData(DataFlavor.stringFlavor);
            }
        } catch (Exception exception) {
            // empty catch block
        }
        return "";
    }

    public static void setClipboardString(String copyText) {
        if (!StringUtils.isEmpty(copyText)) {
            try {
                StringSelection stringselection = new StringSelection(copyText);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, null);
            } catch (Exception exception) {
                // empty catch block
            }
        }
    }

    protected void renderToolTip(ItemStack stack, int x, int y) {
        NBTTagCompound blockEntityTag;
        NBTTagCompound tagCompound;
        GL11.glPushMatrix();
        if (!Panic.stop && ProContainer.get != null && ProContainer.get.actived && ProContainer.get.ContainerInfo.bValue && (stack.getItem() instanceof ItemShulkerBox || stack.getItem() == Item.getItemFromBlock(Blocks.CHEST) || stack.getItem() == Item.getItemFromBlock(Blocks.TRAPPED_CHEST)) && (tagCompound = stack.getTagCompound()) != null && tagCompound.hasKey("BlockEntityTag", 10) && (blockEntityTag = tagCompound.getCompoundTag("BlockEntityTag")).hasKey("Items", 9)) {
            GlStateManager.translate(0.0f, -10.0f, 0.0f);
            NonNullList<ItemStack> nonnulllist = NonNullList.func_191197_a(27, ItemStack.field_190927_a);
            ItemStackHelper.func_191283_b(blockEntityTag, nonnulllist);
            GlStateManager.enableBlend();
            GlStateManager.disableRescaleNormal();
            RenderUtils.disableStandardItemLighting();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            int width = 144;
            int x1 = x + 12;
            int y1 = y - 2;
            int height = 47;
            this.mc.getRenderItem().zLevel = 300.0f;
            GlStateManager.enableBlend();
            RenderUtils.drawMinecraftRect(x1 - 3, y1 - 4, x1 + 144 + 3, y1 - 3, -267386864, -267386864);
            RenderUtils.drawMinecraftRect(x1 - 3, y1 + 47 + 3, x1 + 144 + 3, y1 + 47 + 4, -267386864, -267386864);
            RenderUtils.drawMinecraftRect(x1 - 3, y1 - 3, x1 + 144 + 3, y1 + 47 + 3, -267386864, -267386864);
            RenderUtils.drawMinecraftRect(x1 - 4, y1 - 3, x1 - 3, y1 + 47 + 3, -267386864, -267386864);
            RenderUtils.drawMinecraftRect(x1 + 144 + 3, y1 - 3, x1 + 144 + 4, y1 + 47 + 3, -267386864, -267386864);
            RenderUtils.drawMinecraftRect(x1 - 3, y1 - 3 + 1, x1 - 3 + 1, y1 + 47 + 3 - 1, 0x505000FF, 1344798847);
            RenderUtils.drawMinecraftRect(x1 + 144 + 2, y1 - 3 + 1, x1 + 144 + 3, y1 + 47 + 3 - 1, 0x505000FF, 1344798847);
            RenderUtils.drawMinecraftRect(x1 - 3, y1 - 3, x1 + 144 + 3, y1 - 3 + 1, 0x505000FF, 0x505000FF);
            RenderUtils.drawMinecraftRect(x1 - 3, y1 + 47 + 2, x1 + 144 + 3, y1 + 47 + 3, 1344798847, 1344798847);
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.enableTexture2D();
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            RenderUtils.enableGUIStandardItemLighting();
            for (int i = 0; i < nonnulllist.size(); ++i) {
                int iX = x + i % 9 * 16 + 11;
                int iY = y + i / 9 * 16 - 11 + 8;
                ItemStack itemStack = nonnulllist.get(i);
                this.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, iX, iY);
                this.mc.getRenderItem().renderItemOverlayIntoGUI(this.mc.fontRendererObj, itemStack, iX, iY, null);
            }
            RenderUtils.disableStandardItemLighting();
            this.mc.getRenderItem().zLevel = 0.0f;
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            RenderUtils.enableStandardItemLighting();
            GlStateManager.enableRescaleNormal();
            GlStateManager.translate(0.0f, 66.0f, 0.0f);
        }
        this.drawHoveringText(this.func_191927_a(stack), x, y);
        GL11.glPopMatrix();
    }

    public List<String> func_191927_a(ItemStack p_191927_1_) {
        List<String> list = p_191927_1_.getTooltip(Minecraft.player, this.mc.gameSettings.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL);
        for (int i = 0; i < list.size(); ++i) {
            if (i == 0) {
                list.set(i, p_191927_1_.getRarity().rarityColor + list.get(i));
                continue;
            }
            list.set(i, TextFormatting.GRAY + list.get(i));
        }
        return list;
    }

    public void drawCreativeTabHoveringText(String tabName, int mouseX, int mouseY) {
        this.drawHoveringText(List.of((Object)tabName), mouseX, mouseY);
    }

    public void func_193975_a(boolean p_193975_1_) {
        this.field_193977_u = p_193975_1_;
    }

    public boolean func_193976_p() {
        return this.field_193977_u;
    }

    public void drawHoveringText(List<String> textLines, int x, int y) {
        if (!textLines.isEmpty()) {
            GlStateManager.disableRescaleNormal();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            int i = 0;
            for (String s : textLines) {
                int j = this.fontRendererObj.getStringWidth(s);
                if (j <= i) continue;
                i = j;
            }
            int l1 = x + 12;
            int i2 = y - 12;
            int k = 8;
            if (textLines.size() > 1) {
                k += 2 + (textLines.size() - 1) * 10;
            }
            if (l1 + i > width) {
                l1 -= 28 + i;
            }
            if (i2 + k + 6 > height) {
                i2 = height - k - 6;
            }
            this.zLevel = 300.0f;
            this.itemRender.zLevel = 300.0f;
            int l = -267386864;
            this.drawGradientRect(l1 - 3, i2 - 4, l1 + i + 3, i2 - 3, -267386864, -267386864);
            this.drawGradientRect(l1 - 3, i2 + k + 3, l1 + i + 3, i2 + k + 4, -267386864, -267386864);
            this.drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 + k + 3, -267386864, -267386864);
            this.drawGradientRect(l1 - 4, i2 - 3, l1 - 3, i2 + k + 3, -267386864, -267386864);
            this.drawGradientRect(l1 + i + 3, i2 - 3, l1 + i + 4, i2 + k + 3, -267386864, -267386864);
            int i1 = 0x505000FF;
            int j1 = 1344798847;
            this.drawGradientRect(l1 - 3, i2 - 3 + 1, l1 - 3 + 1, i2 + k + 3 - 1, 0x505000FF, 1344798847);
            this.drawGradientRect(l1 + i + 2, i2 - 3 + 1, l1 + i + 3, i2 + k + 3 - 1, 0x505000FF, 1344798847);
            this.drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 - 3 + 1, 0x505000FF, 0x505000FF);
            this.drawGradientRect(l1 - 3, i2 + k + 2, l1 + i + 3, i2 + k + 3, 1344798847, 1344798847);
            for (int k1 = 0; k1 < textLines.size(); ++k1) {
                String s1 = textLines.get(k1);
                this.fontRendererObj.drawStringWithShadow(s1, l1, i2, -1);
                if (k1 == 0) {
                    i2 += 2;
                }
                i2 += 10;
            }
            this.zLevel = 0.0f;
            this.itemRender.zLevel = 0.0f;
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.enableRescaleNormal();
        }
    }

    protected void handleComponentHover(ITextComponent component, int x, int y) {
        if (component != null && component.getStyle().getHoverEvent() != null) {
            HoverEvent hoverevent = component.getStyle().getHoverEvent();
            if (hoverevent.getAction() == HoverEvent.Action.SHOW_ITEM) {
                ItemStack itemstack = ItemStack.field_190927_a;
                try {
                    NBTTagCompound nbtbase = JsonToNBT.getTagFromJson(hoverevent.getValue().getUnformattedText());
                    if (nbtbase instanceof NBTTagCompound) {
                        itemstack = new ItemStack(nbtbase);
                    }
                } catch (NBTException nbtbase) {
                    // empty catch block
                }
                if (itemstack.func_190926_b()) {
                    this.drawCreativeTabHoveringText(TextFormatting.RED + "Invalid Item!", x, y);
                } else {
                    this.renderToolTip(itemstack, x, y);
                }
            } else if (hoverevent.getAction() == HoverEvent.Action.SHOW_ENTITY) {
                if (this.mc.gameSettings.advancedItemTooltips) {
                    try {
                        NBTTagCompound nbttagcompound = JsonToNBT.getTagFromJson(hoverevent.getValue().getUnformattedText());
                        ArrayList<String> list = Lists.newArrayList();
                        list.add(nbttagcompound.getString("name"));
                        if (nbttagcompound.hasKey("type", 8)) {
                            String s = nbttagcompound.getString("type");
                            list.add("Type: " + s);
                        }
                        list.add(nbttagcompound.getString("id"));
                        this.drawHoveringText(list, x, y);
                    } catch (NBTException var8) {
                        this.drawCreativeTabHoveringText(TextFormatting.RED + "Invalid Entity!", x, y);
                    }
                }
            } else if (hoverevent.getAction() == HoverEvent.Action.SHOW_TEXT) {
                this.drawHoveringText(this.mc.fontRendererObj.listFormattedStringToWidth(hoverevent.getValue().getFormattedText(), Math.max(width / 2, 200)), x, y);
            }
            GlStateManager.disableLighting();
        }
    }

    protected void setText(String newChatText, boolean shouldOverwrite) {
    }

    public boolean handleComponentClick(ITextComponent component) {
        if (component == null) {
            return false;
        }
        ClickEvent clickevent = component.getStyle().getClickEvent();
        if (GuiScreen.isShiftKeyDown()) {
            if (component.getStyle().getInsertion() != null) {
                this.setText(component.getStyle().getInsertion(), false);
            }
        } else if (clickevent != null) {
            block19: {
                if (clickevent.getAction() == ClickEvent.Action.OPEN_URL) {
                    if (!this.mc.gameSettings.chatLinks) {
                        return false;
                    }
                    try {
                        URI uri = new URI(clickevent.getValue());
                        String s = uri.getScheme();
                        if (s == null) {
                            throw new URISyntaxException(clickevent.getValue(), "Missing protocol");
                        }
                        if (!PROTOCOLS.contains(s.toLowerCase(Locale.ROOT))) {
                            throw new URISyntaxException(clickevent.getValue(), "Unsupported protocol: " + s.toLowerCase(Locale.ROOT));
                        }
                        if (this.mc.gameSettings.chatLinksPrompt) {
                            this.clickedLinkURI = uri;
                            this.mc.displayGuiScreen(new GuiConfirmOpenLink((GuiYesNoCallback)this, clickevent.getValue(), 31102009, false));
                            break block19;
                        }
                        this.openWebLink(uri);
                    } catch (URISyntaxException urisyntaxexception) {
                        LOGGER.error("Can't open url for {}", (Object)clickevent, (Object)urisyntaxexception);
                    }
                } else if (clickevent.getAction() == ClickEvent.Action.OPEN_FILE) {
                    URI uri1 = new File(clickevent.getValue()).toURI();
                    this.openWebLink(uri1);
                } else if (clickevent.getAction() == ClickEvent.Action.SUGGEST_COMMAND) {
                    this.setText(clickevent.getValue(), true);
                } else if (clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                    this.sendChatMessage(clickevent.getValue(), false);
                } else {
                    LOGGER.error("Don't know how to handle {}", (Object)clickevent);
                }
            }
            return true;
        }
        return false;
    }

    public void sendChatMessage(String msg) {
        this.sendChatMessage(msg, true);
    }

    public void sendChatMessage(String msg, boolean addToChat) {
        if (addToChat) {
            this.mc.ingameGUI.getChatGUI().addToSentMessages(msg);
        }
        Minecraft.player.sendChatMessage(msg);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0) {
            for (int i = 0; i < this.buttonList.size(); ++i) {
                GuiButton guibutton = this.buttonList.get(i);
                if (!guibutton.mousePressed(this.mc, mouseX, mouseY)) continue;
                this.selectedButton = guibutton;
                guibutton.playPressSound(this.mc.getSoundHandler());
                this.actionPerformed(guibutton);
            }
        }
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (this.selectedButton != null && state == 0) {
            this.selectedButton.mouseReleased(mouseX, mouseY);
            this.selectedButton = null;
        }
    }

    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
    }

    protected void actionPerformed(GuiButton button) throws IOException {
    }

    public void setWorldAndResolution(Minecraft mc, int width, int height) {
        this.mc = mc;
        this.itemRender = mc.getRenderItem();
        this.fontRendererObj = mc.fontRendererObj;
        GuiScreen.width = width;
        GuiScreen.height = height;
        this.buttonList.clear();
        if (this instanceof ClickGuiScreen && !Keyboard.isKeyDown(ClickGui.instance.getBind())) {
            return;
        }
        this.initGui();
    }

    public void setGuiSize(int w, int h) {
        width = w;
        height = h;
    }

    public void initGui() {
    }

    public void handleInput() throws IOException {
        if (Mouse.isCreated()) {
            while (Mouse.next()) {
                this.handleMouseInput();
            }
        }
        if (Keyboard.isCreated()) {
            while (Keyboard.next()) {
                this.handleKeyboardInput();
            }
        }
    }

    public void handleMouseInput() throws IOException {
        int i = Mouse.getEventX() * width / this.mc.displayWidth;
        int j = height - Mouse.getEventY() * height / this.mc.displayHeight - 1;
        int k = Mouse.getEventButton();
        if (Mouse.getEventButtonState()) {
            if (this.mc.gameSettings.touchscreen && this.touchValue++ > 0) {
                return;
            }
            this.eventButton = k;
            this.lastMouseEvent = Minecraft.getSystemTime();
            this.mouseClicked(i, j, this.eventButton);
        } else if (k != -1) {
            if (this.mc.gameSettings.touchscreen && --this.touchValue > 0) {
                return;
            }
            this.eventButton = -1;
            this.mouseReleased(i, j, k);
        } else if (this.eventButton != -1 && this.lastMouseEvent > 0L) {
            long l = Minecraft.getSystemTime() - this.lastMouseEvent;
            this.mouseClickMove(i, j, this.eventButton, l);
        }
    }

    public void handleKeyboardInput() throws IOException {
        char c0 = Keyboard.getEventCharacter();
        if (Keyboard.getEventKey() == 0 && c0 >= ' ' || Keyboard.getEventKeyState()) {
            this.keyTyped(c0, Keyboard.getEventKey());
        }
        this.mc.dispatchKeypresses();
    }

    public void updateScreen() {
    }

    public void onGuiClosed() {
    }

    public void drawDefaultBackground() {
        this.drawWorldBackground(0);
    }

    public void drawWorldBackground(int tint) {
        if (this.mc.world != null) {
            if (this.mc.currentScreen instanceof GuiContainer) {
                this.drawGradientRect(0, 0, width, height, ColorUtils.getColor(0, 0, 0, 0), ColorUtils.getColor(0, 0, 0, 0));
            } else {
                this.drawGradientRect(0, 0, width, height, -1072689136, -804253680);
            }
        } else {
            this.drawBackground(tint);
        }
    }

    public void drawBackground(int tint) {
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        this.mc.getTextureManager().bindTexture(OPTIONS_BACKGROUND);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        float f = 32.0f;
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferbuilder.pos(0.0, height, 0.0).tex(0.0, (float)height / 32.0f + (float)tint).color(64, 64, 64, 255).endVertex();
        bufferbuilder.pos(width, height, 0.0).tex((float)width / 32.0f, (float)height / 32.0f + (float)tint).color(64, 64, 64, 255).endVertex();
        bufferbuilder.pos(width, 0.0, 0.0).tex((float)width / 32.0f, tint).color(64, 64, 64, 255).endVertex();
        bufferbuilder.pos(0.0, 0.0, 0.0).tex(0.0, tint).color(64, 64, 64, 255).endVertex();
        tessellator.draw();
    }

    public boolean doesGuiPauseGame() {
        return true;
    }

    @Override
    public void confirmClicked(boolean result, int id) {
        if (id == 31102009) {
            if (result) {
                this.openWebLink(this.clickedLinkURI);
            }
            this.clickedLinkURI = null;
            this.mc.displayGuiScreen(this);
        }
    }

    private void openWebLink(URI url) {
        try {
            Class<?> oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
            oclass.getMethod("browse", URI.class).invoke(object, url);
        } catch (Throwable throwable1) {
            Throwable throwable = throwable1.getCause();
            LOGGER.error("Couldn't open link: {}", (Object)(throwable == null ? "<UNKNOWN>" : throwable.getMessage()));
        }
    }

    public static boolean isCtrlKeyDown() {
        if (Minecraft.IS_RUNNING_ON_MAC) {
            return Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220);
        }
        return Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
    }

    public static boolean isShiftKeyDown() {
        return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
    }

    public static boolean isAltKeyDown() {
        return Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184);
    }

    public static boolean isKeyComboCtrlX(int keyID) {
        return keyID == 45 && GuiScreen.isCtrlKeyDown() && !GuiScreen.isShiftKeyDown() && !GuiScreen.isAltKeyDown();
    }

    public static boolean isKeyComboCtrlV(int keyID) {
        return keyID == 47 && GuiScreen.isCtrlKeyDown() && !GuiScreen.isShiftKeyDown() && !GuiScreen.isAltKeyDown();
    }

    public static boolean isKeyComboCtrlC(int keyID) {
        return keyID == 46 && GuiScreen.isCtrlKeyDown() && !GuiScreen.isShiftKeyDown() && !GuiScreen.isAltKeyDown();
    }

    public static boolean isKeyComboCtrlA(int keyID) {
        return keyID == 30 && GuiScreen.isCtrlKeyDown() && !GuiScreen.isShiftKeyDown() && !GuiScreen.isAltKeyDown();
    }

    public void onResize(Minecraft mcIn, int w, int h) {
        this.setWorldAndResolution(mcIn, w, h);
    }
}

