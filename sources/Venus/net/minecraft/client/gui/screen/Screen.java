/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FocusableGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.screen.ConfirmOpenLinkScreen;
import net.minecraft.client.gui.screen.IScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Screen
extends FocusableGui
implements IScreen,
IRenderable {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Set<String> ALLOWED_PROTOCOLS = Sets.newHashSet("http", "https");
    protected final ITextComponent title;
    protected final List<IGuiEventListener> children = Lists.newArrayList();
    @Nullable
    protected Minecraft minecraft;
    protected ItemRenderer itemRenderer;
    public int width;
    public int height;
    protected final List<Widget> buttons = Lists.newArrayList();
    public boolean passEvents;
    protected FontRenderer font;
    private URI clickedLink;

    protected Screen(ITextComponent iTextComponent) {
        this.title = iTextComponent;
    }

    public ITextComponent getTitle() {
        return this.title;
    }

    public String getNarrationMessage() {
        return this.getTitle().getString();
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        for (int i = 0; i < this.buttons.size(); ++i) {
            this.buttons.get(i).render(matrixStack, n, n2, f);
        }
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (n == 256 && this.shouldCloseOnEsc()) {
            this.closeScreen();
            return false;
        }
        if (n == 258) {
            boolean bl;
            boolean bl2 = bl = !Screen.hasShiftDown();
            if (!this.changeFocus(bl)) {
                this.changeFocus(bl);
            }
            return true;
        }
        return super.keyPressed(n, n2, n3);
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    public void closeScreen() {
        this.minecraft.displayGuiScreen(null);
    }

    protected <T extends Widget> T addButton(T t) {
        this.buttons.add(t);
        return this.addListener(t);
    }

    protected <T extends IGuiEventListener> T addListener(T t) {
        this.children.add(t);
        return t;
    }

    protected void renderTooltip(MatrixStack matrixStack, ItemStack itemStack, int n, int n2) {
        this.func_243308_b(matrixStack, this.getTooltipFromItem(itemStack), n, n2);
    }

    public List<ITextComponent> getTooltipFromItem(ItemStack itemStack) {
        return itemStack.getTooltip(this.minecraft.player, this.minecraft.gameSettings.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL);
    }

    public void renderTooltip(MatrixStack matrixStack, ITextComponent iTextComponent, int n, int n2) {
        this.renderTooltip(matrixStack, Arrays.asList(iTextComponent.func_241878_f()), n, n2);
    }

    public void func_243308_b(MatrixStack matrixStack, List<ITextComponent> list, int n, int n2) {
        this.renderTooltip(matrixStack, Lists.transform(list, ITextComponent::func_241878_f), n, n2);
    }

    /*
     * WARNING - void declaration
     */
    public void renderTooltip(MatrixStack matrixStack, List<? extends IReorderingProcessor> list, int n, int n2) {
        if (!list.isEmpty()) {
            int n3;
            int n4;
            int n42 = 0;
            for (IReorderingProcessor iReorderingProcessor : list) {
                n4 = this.font.func_243245_a(iReorderingProcessor);
                if (n4 <= n42) continue;
                n42 = n4;
            }
            int n5 = n + 12;
            int n6 = n2 - 12;
            n4 = 8;
            if (list.size() > 1) {
                n4 += 2 + (list.size() - 1) * 10;
            }
            if (n5 + n42 > this.width) {
                n5 -= 28 + n42;
            }
            if (n6 + n4 + 6 > this.height) {
                n3 = this.height - n4 - 6;
            }
            matrixStack.push();
            int n7 = -267386864;
            int n8 = 0x505000FF;
            int n9 = 1344798847;
            int n10 = 400;
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
            Matrix4f matrix4f = matrixStack.getLast().getMatrix();
            Screen.fillGradient(matrix4f, bufferBuilder, n5 - 3, n3 - 4, n5 + n42 + 3, n3 - 3, 400, -267386864, -267386864);
            Screen.fillGradient(matrix4f, bufferBuilder, n5 - 3, n3 + n4 + 3, n5 + n42 + 3, n3 + n4 + 4, 400, -267386864, -267386864);
            Screen.fillGradient(matrix4f, bufferBuilder, n5 - 3, n3 - 3, n5 + n42 + 3, n3 + n4 + 3, 400, -267386864, -267386864);
            Screen.fillGradient(matrix4f, bufferBuilder, n5 - 4, n3 - 3, n5 - 3, n3 + n4 + 3, 400, -267386864, -267386864);
            Screen.fillGradient(matrix4f, bufferBuilder, n5 + n42 + 3, n3 - 3, n5 + n42 + 4, n3 + n4 + 3, 400, -267386864, -267386864);
            Screen.fillGradient(matrix4f, bufferBuilder, n5 - 3, n3 - 3 + 1, n5 - 3 + 1, n3 + n4 + 3 - 1, 400, 0x505000FF, 1344798847);
            Screen.fillGradient(matrix4f, bufferBuilder, n5 + n42 + 2, n3 - 3 + 1, n5 + n42 + 3, n3 + n4 + 3 - 1, 400, 0x505000FF, 1344798847);
            Screen.fillGradient(matrix4f, bufferBuilder, n5 - 3, n3 - 3, n5 + n42 + 3, n3 - 3 + 1, 400, 0x505000FF, 0x505000FF);
            Screen.fillGradient(matrix4f, bufferBuilder, n5 - 3, n3 + n4 + 2, n5 + n42 + 3, n3 + n4 + 3, 400, 1344798847, 1344798847);
            RenderSystem.enableDepthTest();
            RenderSystem.disableTexture();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.shadeModel(7425);
            bufferBuilder.finishDrawing();
            WorldVertexBufferUploader.draw(bufferBuilder);
            RenderSystem.shadeModel(7424);
            RenderSystem.disableBlend();
            RenderSystem.enableTexture();
            IRenderTypeBuffer.Impl impl = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
            matrixStack.translate(0.0, 0.0, 400.0);
            for (int i = 0; i < list.size(); ++i) {
                IReorderingProcessor iReorderingProcessor = list.get(i);
                if (iReorderingProcessor != null) {
                    void var7_11;
                    this.font.func_238416_a_(iReorderingProcessor, n5, (float)var7_11, -1, true, matrix4f, impl, false, 0, 1);
                }
                if (i == 0) {
                    var7_11 += 2;
                }
                var7_11 += 10;
            }
            impl.finish();
            matrixStack.pop();
        }
    }

    protected void renderComponentHoverEffect(MatrixStack matrixStack, @Nullable Style style, int n, int n2) {
        if (style != null && style.getHoverEvent() != null) {
            HoverEvent hoverEvent = style.getHoverEvent();
            HoverEvent.ItemHover itemHover = hoverEvent.getParameter(HoverEvent.Action.SHOW_ITEM);
            if (itemHover != null) {
                this.renderTooltip(matrixStack, itemHover.createStack(), n, n2);
            } else {
                HoverEvent.EntityHover entityHover = hoverEvent.getParameter(HoverEvent.Action.SHOW_ENTITY);
                if (entityHover != null) {
                    if (this.minecraft.gameSettings.advancedItemTooltips) {
                        this.func_243308_b(matrixStack, entityHover.getTooltip(), n, n2);
                    }
                } else {
                    ITextComponent iTextComponent = hoverEvent.getParameter(HoverEvent.Action.SHOW_TEXT);
                    if (iTextComponent != null) {
                        this.renderTooltip(matrixStack, this.minecraft.fontRenderer.trimStringToWidth(iTextComponent, Math.max(this.width / 2, 200)), n, n2);
                    }
                }
            }
        }
    }

    protected void insertText(String string, boolean bl) {
    }

    public boolean handleComponentClicked(@Nullable Style style) {
        if (style == null) {
            return true;
        }
        ClickEvent clickEvent = style.getClickEvent();
        if (Screen.hasShiftDown()) {
            if (style.getInsertion() != null) {
                this.insertText(style.getInsertion(), true);
            }
        } else if (clickEvent != null) {
            block21: {
                if (clickEvent.getAction() == ClickEvent.Action.OPEN_URL) {
                    if (!this.minecraft.gameSettings.chatLinks) {
                        return true;
                    }
                    try {
                        URI uRI = new URI(clickEvent.getValue());
                        String string = uRI.getScheme();
                        if (string == null) {
                            throw new URISyntaxException(clickEvent.getValue(), "Missing protocol");
                        }
                        if (!ALLOWED_PROTOCOLS.contains(string.toLowerCase(Locale.ROOT))) {
                            throw new URISyntaxException(clickEvent.getValue(), "Unsupported protocol: " + string.toLowerCase(Locale.ROOT));
                        }
                        if (this.minecraft.gameSettings.chatLinksPrompt) {
                            this.clickedLink = uRI;
                            this.minecraft.displayGuiScreen(new ConfirmOpenLinkScreen(this::confirmLink, clickEvent.getValue(), false));
                            break block21;
                        }
                        this.openLink(uRI);
                    } catch (URISyntaxException uRISyntaxException) {
                        LOGGER.error("Can't open url for {}", (Object)clickEvent, (Object)uRISyntaxException);
                    }
                } else if (clickEvent.getAction() == ClickEvent.Action.OPEN_FILE) {
                    URI uRI = new File(clickEvent.getValue()).toURI();
                    this.openLink(uRI);
                } else if (clickEvent.getAction() == ClickEvent.Action.SUGGEST_COMMAND) {
                    this.insertText(clickEvent.getValue(), false);
                } else if (clickEvent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                    this.sendMessage(clickEvent.getValue(), true);
                } else if (clickEvent.getAction() == ClickEvent.Action.COPY_TO_CLIPBOARD) {
                    this.minecraft.keyboardListener.setClipboardString(clickEvent.getValue());
                } else {
                    LOGGER.error("Don't know how to handle {}", (Object)clickEvent);
                }
            }
            return false;
        }
        return true;
    }

    public void sendMessage(String string) {
        this.sendMessage(string, false);
    }

    public void sendMessage(String string, boolean bl) {
        if (bl) {
            this.minecraft.ingameGUI.getChatGUI().addToSentMessages(string);
        }
        this.minecraft.player.sendChatMessage(string);
    }

    public void init(Minecraft minecraft, int n, int n2) {
        this.minecraft = minecraft;
        this.itemRenderer = minecraft.getItemRenderer();
        this.font = minecraft.fontRenderer;
        this.width = n;
        this.height = n2;
        this.buttons.clear();
        this.children.clear();
        this.setListener(null);
        this.init();
    }

    @Override
    public List<? extends IGuiEventListener> getEventListeners() {
        return this.children;
    }

    protected void init() {
    }

    @Override
    public void tick() {
    }

    public void onClose() {
    }

    public void renderBackground(MatrixStack matrixStack) {
        this.renderBackground(matrixStack, 0);
    }

    public void renderBackground(MatrixStack matrixStack, int n) {
        if (this.minecraft.world != null) {
            this.fillGradient(matrixStack, 0, 0, this.width, this.height, -1072689136, -804253680);
        } else {
            this.renderDirtBackground(n);
        }
    }

    public void renderDirtBackground(int n) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        this.minecraft.getTextureManager().bindTexture(BACKGROUND_LOCATION);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        float f = 32.0f;
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferBuilder.pos(0.0, this.height, 0.0).tex(0.0f, (float)this.height / 32.0f + (float)n).color(64, 64, 64, 255).endVertex();
        bufferBuilder.pos(this.width, this.height, 0.0).tex((float)this.width / 32.0f, (float)this.height / 32.0f + (float)n).color(64, 64, 64, 255).endVertex();
        bufferBuilder.pos(this.width, 0.0, 0.0).tex((float)this.width / 32.0f, n).color(64, 64, 64, 255).endVertex();
        bufferBuilder.pos(0.0, 0.0, 0.0).tex(0.0f, n).color(64, 64, 64, 255).endVertex();
        tessellator.draw();
    }

    public boolean isPauseScreen() {
        return false;
    }

    private void confirmLink(boolean bl) {
        if (bl) {
            this.openLink(this.clickedLink);
        }
        this.clickedLink = null;
        this.minecraft.displayGuiScreen(this);
    }

    private void openLink(URI uRI) {
        Util.getOSType().openURI(uRI);
    }

    public static boolean hasControlDown() {
        if (Minecraft.IS_RUNNING_ON_MAC) {
            return InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 343) || InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 347);
        }
        return InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 341) || InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 345);
    }

    public static boolean hasShiftDown() {
        return InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 340) || InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 344);
    }

    public static boolean hasAltDown() {
        return InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 342) || InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 346);
    }

    public static boolean isCut(int n) {
        return n == 88 && Screen.hasControlDown() && !Screen.hasShiftDown() && !Screen.hasAltDown();
    }

    public static boolean isPaste(int n) {
        return n == 86 && Screen.hasControlDown() && !Screen.hasShiftDown() && !Screen.hasAltDown();
    }

    public static boolean isCopy(int n) {
        return n == 67 && Screen.hasControlDown() && !Screen.hasShiftDown() && !Screen.hasAltDown();
    }

    public static boolean isSelectAll(int n) {
        return n == 65 && Screen.hasControlDown() && !Screen.hasShiftDown() && !Screen.hasAltDown();
    }

    public void resize(Minecraft minecraft, int n, int n2) {
        this.init(minecraft, n, n2);
    }

    public static void wrapScreenError(Runnable runnable, String string, String string2) {
        try {
            runnable.run();
        } catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, string);
            CrashReportCategory crashReportCategory = crashReport.makeCategory("Affected screen");
            crashReportCategory.addDetail("Screen name", () -> Screen.lambda$wrapScreenError$0(string2));
            throw new ReportedException(crashReport);
        }
    }

    protected boolean isValidCharacterForName(String string, char c, int n) {
        int n2 = string.indexOf(58);
        int n3 = string.indexOf(47);
        if (c == ':') {
            return (n3 == -1 || n <= n3) && n2 == -1;
        }
        if (c == '/') {
            return n > n2;
        }
        return c == '_' || c == '-' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9' || c == '.';
    }

    @Override
    public boolean isMouseOver(double d, double d2) {
        return false;
    }

    public void addPacks(List<Path> list) {
    }

    private static String lambda$wrapScreenError$0(String string) throws Exception {
        return string;
    }
}

