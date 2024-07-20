/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ITabCompleter;
import net.minecraft.util.TabCompleter;
import net.minecraft.util.Vec2f;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import ru.govno.client.Client;
import ru.govno.client.module.modules.ClientColors;
import ru.govno.client.module.modules.ComfortUi;
import ru.govno.client.module.modules.CommandGui;
import ru.govno.client.module.modules.Hud;
import ru.govno.client.module.modules.TargetHUD;
import ru.govno.client.module.modules.Timer;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.utils.Command.impl.Clip;
import ru.govno.client.utils.Command.impl.Panic;
import ru.govno.client.utils.HoverUtils;
import ru.govno.client.utils.InventoryUtil;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.PaintUI;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;
import ru.govno.client.utils.Render.StencilUtil;

public class GuiChat
extends GuiScreen
implements ITabCompleter {
    static final PaintUI paintUI = new PaintUI();
    private final AnimationUtils alphaPC = new AnimationUtils(0.0f, 1.0f, 0.0825f);
    private String historyBuffer = "";
    boolean dragging = false;
    boolean dragging2 = false;
    boolean dragging3 = false;
    boolean dragging4 = false;
    boolean dragging5 = false;
    boolean dragging6 = false;
    boolean dragging7 = false;
    boolean dragging8 = false;
    boolean dragging9 = false;
    boolean dragging10 = false;
    boolean dragging11 = false;
    int dragX;
    int dragY;
    int dragX2;
    int dragY2;
    int dragX3;
    int dragY3;
    int dragX4;
    int dragY4;
    int dragX5;
    int dragY5;
    int dragX6;
    int dragY6;
    int dragX7;
    int dragY7;
    int dragX8;
    int dragY8;
    int dragX9;
    int dragY9;
    int dragX10;
    int dragY10;
    int dragX11;
    int dragY11;
    float animSpeed = 0.1f;
    AnimationUtils dragAnim = new AnimationUtils(0.0f, 0.0f, this.animSpeed);
    AnimationUtils dragAnim2 = new AnimationUtils(0.0f, 0.0f, this.animSpeed);
    AnimationUtils dragAnim3 = new AnimationUtils(0.0f, 0.0f, this.animSpeed);
    AnimationUtils dragAnim4 = new AnimationUtils(0.0f, 0.0f, this.animSpeed);
    AnimationUtils dragAnim5 = new AnimationUtils(0.0f, 0.0f, this.animSpeed);
    AnimationUtils dragAnim6 = new AnimationUtils(0.0f, 0.0f, this.animSpeed);
    AnimationUtils dragAnim7 = new AnimationUtils(0.0f, 0.0f, this.animSpeed);
    AnimationUtils dragAnim8 = new AnimationUtils(0.0f, 0.0f, this.animSpeed);
    AnimationUtils dragAnim9 = new AnimationUtils(0.0f, 0.0f, this.animSpeed);
    AnimationUtils dragAnim10 = new AnimationUtils(0.0f, 0.0f, this.animSpeed);
    AnimationUtils dragAnim11 = new AnimationUtils(0.0f, 0.0f, this.animSpeed);
    private int sentHistoryCursor = -1;
    private TabCompleter tabCompleter;
    protected GuiTextField inputField;
    private String defaultInputFieldText = "";
    float textwidth;
    float textwidthSmooth;
    static boolean keyHide;
    boolean clickHider;
    float x = TargetHUD.xPosHud;
    float y = TargetHUD.yPosHud;
    float x2 = Hud.wmPosX;
    float y2 = Hud.wmPosY;
    float x4 = Hud.potPosX;
    float y4 = Hud.potPosY;
    float x5 = Hud.armPosX;
    float y5 = Hud.armPosY;
    float x6 = Hud.stPosX;
    float y6 = Hud.stPosY;
    float x7 = Hud.listPosX;
    float y7 = Hud.listPosY;
    float x8 = CommandGui.getWindowCoord()[0];
    float y8 = CommandGui.getWindowCoord()[1];
    float x9 = 0.0f;
    float y9 = 0.0f;
    float x10 = Hud.kbPosX;
    float y10 = Hud.kbPosY;
    float x11 = Hud.pcPosX;
    float y11 = Hud.pcPosY;
    static int scrollInt;
    static AnimationUtils scroll;
    static boolean click;

    public GuiChat() {
    }

    public GuiChat(String defaultText) {
        this.defaultInputFieldText = defaultText;
    }

    public float lerp(float start, float end, float step) {
        return start + step * (end - start);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (state == 0) {
            this.dragging = false;
            this.dragging2 = false;
            this.dragging3 = false;
            this.dragging4 = false;
            this.dragging5 = false;
            this.dragging6 = false;
            this.dragging7 = false;
            this.dragging8 = false;
            this.dragging9 = false;
            this.dragging10 = false;
            this.dragging11 = false;
            this.dragAnim.to = 0.0f;
            this.dragAnim2.to = 0.0f;
            this.dragAnim3.to = 0.0f;
            this.dragAnim4.to = 0.0f;
            this.dragAnim5.to = 0.0f;
            this.dragAnim6.to = 0.0f;
            this.dragAnim7.to = 0.0f;
            this.dragAnim8.to = 0.0f;
            this.dragAnim9.to = 0.0f;
            this.dragAnim10.to = 0.0f;
            this.dragAnim11.to = 0.0f;
        }
        if (ComfortUi.get.isPaintInChat()) {
            paintUI.mouseReleased(mouseX, mouseY, state);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void initGui() {
        if (ComfortUi.get.isPaintInChat()) {
            paintUI.onCloseOrInit(true);
        }
        this.textwidth = 10.0f;
        Keyboard.enableRepeatEvents(true);
        this.sentHistoryCursor = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
        this.inputField = new GuiTextField(0, this.fontRendererObj, 4, height - 12, width - 4, 12);
        this.inputField.setMaxStringLength(4086);
        this.inputField.setEnableBackgroundDrawing(false);
        this.inputField.setFocused(true);
        this.inputField.setText(this.defaultInputFieldText);
        this.inputField.setCanLoseFocus(false);
        this.tabCompleter = new ChatTabCompleter(this.inputField);
        this.alphaPC.setAnim(0.0f);
        super.initGui();
    }

    @Override
    public void onGuiClosed() {
        if (ComfortUi.get.isPaintInChat()) {
            paintUI.onCloseOrInit(true);
        }
        Keyboard.enableRepeatEvents(false);
        this.mc.ingameGUI.getChatGUI().resetScroll();
    }

    @Override
    public void updateScreen() {
        this.inputField.updateCursorCounter();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.tabCompleter.resetRequested();
        if (keyCode == 15) {
            this.tabCompleter.complete();
        } else {
            this.tabCompleter.resetDidComplete();
        }
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
        } else if (keyCode != 28 && keyCode != 156) {
            if (keyCode == 200) {
                this.getSentHistory(-1);
            } else if (keyCode == 208) {
                this.getSentHistory(1);
            } else if (keyCode == 201) {
                this.mc.ingameGUI.getChatGUI().scroll(this.mc.ingameGUI.getChatGUI().getLineCount() - 1);
            } else if (keyCode == 209) {
                this.mc.ingameGUI.getChatGUI().scroll(-this.mc.ingameGUI.getChatGUI().getLineCount() + 1);
            } else {
                this.inputField.textboxKeyTyped(typedChar, keyCode);
            }
        } else {
            String s = this.inputField.getText().trim();
            if (!s.isEmpty()) {
                this.sendChatMessage(s);
            }
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();
        if (i != 0) {
            CommandGui.callWhell(i < 0);
            if (i > 1) {
                i = 1;
            }
            if (i < -1) {
                i = -1;
            }
            if (!GuiChat.isShiftKeyDown()) {
                i *= 7;
            }
            if (!this.hoveredOnClip() && !CommandGui.isHoveredToPanel(false)) {
                this.mc.ingameGUI.getChatGUI().scroll(i);
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        ITextComponent itextcomponent;
        CommandGui.callClick(mouseX, mouseY, mouseButton);
        if (ComfortUi.get.isPaintInChat()) {
            paintUI.mouseClicked(mouseX, mouseY, mouseButton);
        }
        if (mouseButton == 0 && (itextcomponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY())) != null && this.handleComponentClick(itextcomponent)) {
            return;
        }
        this.inputField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void setText(String newChatText, boolean shouldOverwrite) {
        if (shouldOverwrite) {
            this.inputField.setText(newChatText);
        } else {
            this.inputField.writeText(newChatText);
        }
    }

    public void getSentHistory(int msgPos) {
        int i = this.sentHistoryCursor + msgPos;
        int j = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
        if ((i = MathHelper.clamp(i, 0, j)) != this.sentHistoryCursor) {
            if (i == j) {
                this.sentHistoryCursor = j;
                this.inputField.setText(this.historyBuffer);
            } else {
                if (this.sentHistoryCursor == j) {
                    this.historyBuffer = this.inputField.getText();
                }
                this.inputField.setText(this.mc.ingameGUI.getChatGUI().getSentMessages().get(i));
                this.sentHistoryCursor = i;
            }
        }
    }

    boolean hoveredOnClip() {
        ScaledResolution scaled = new ScaledResolution(this.mc);
        int yPort = -(MathUtils.getDifferenceOf(scrollInt, 0) < 10.0 ? scrollInt : (scrollInt > 0 ? 10 : -10) + (scrollInt - (scrollInt > 0 ? 10 : -10)) * 10);
        float x = scaled.getScaledWidth() / 2 + 70;
        float h = 60.0f;
        float w = MathUtils.clamp(12 + Fonts.mntsb_12.getStringWidth("" + yPort), 23, 100);
        float y = (float)(scaled.getScaledHeight() / 2) - 30.0f;
        return HoverUtils.isHovered((int)x, (int)y - 10, (int)(x + w), (int)(y + 60.0f) + 10, Mouse.getX() / 2, scaled.getScaledHeight() - Mouse.getY() / 2);
    }

    void cliper(ScaledResolution scaled, int mouseX, int mouseY) {
        if (Mouse.hasWheel() && this.hoveredOnClip()) {
            scrollInt += MathUtils.clamp(Mouse.getDWheel(), -1, 1);
        }
        int yPort = -(MathUtils.getDifferenceOf(scrollInt = MathUtils.clamp(scrollInt, -5000, 5000), 0) < 10.0 ? scrollInt : (scrollInt > 0 ? 10 : -10) + (scrollInt - (scrollInt > 0 ? 10 : -10)) * 10);
        float x = scaled.getScaledWidth() / 2 + 70;
        float h = 60.0f;
        float w = MathUtils.clamp(12 + Fonts.mntsb_12.getStringWidth("" + yPort), 23, 100);
        float y = (float)(scaled.getScaledHeight() / 2) - 30.0f;
        GuiChat.scroll.to = scrollInt * 7;
        RenderUtils.drawRect(x, y, x + w, y + 60.0f, ColorUtils.getColor(0, 0, 0, 100));
        RenderUtils.drawRect(x, y - 10.0f, x + w, y, ColorUtils.getColor(0, 180));
        Fonts.comfortaaRegular_13.drawStringWithShadow("clip", x + w / 2.0f - (float)Fonts.comfortaaRegular_13.getStringWidth("clip") / 2.0f, y - 7.0f, ColorUtils.getOverallColorFrom(ColorUtils.getFixedWhiteColor(), ClientColors.getColor1(), 0.2f));
        RenderUtils.drawRect(x, y + 60.0f, x + w, y + 60.0f + 10.0f, ColorUtils.getColor(0, 180));
        Fonts.comfortaaRegular_13.drawStringWithShadow("reset", x + w / 2.0f - (float)Fonts.comfortaaRegular_13.getStringWidth("reset") / 2.0f, y + 60.0f + 3.0f, ColorUtils.getOverallColorFrom(ColorUtils.getFixedWhiteColor(), ClientColors.getColor2(), 0.2f));
        RenderUtils.drawRoundedFullGradientShadow(x, y - 10.0f, x + w, y + 60.0f + 10.0f, 2.0f, 5.0f, ClientColors.getColor1(0, 0.15f), ClientColors.getColor1(0, 0.15f), ClientColors.getColor2(0, 0.15f), ClientColors.getColor2(0, 0.15f), true);
        RenderUtils.resetBlender();
        if (Mouse.isButtonDown(0)) {
            if (click && this.hoveredOnClip()) {
                if (HoverUtils.isHovered((int)x, (int)y - 10, (int)(x + w), (int)y, mouseX, mouseY)) {
                    if (yPort != 0) {
                        Clip.runClip(yPort, 0.0, InventoryUtil.getElytra() != -1);
                    }
                } else if (HoverUtils.isHovered((int)x, (int)(y + 60.0f), (int)(x + w), (int)(y + 60.0f + 10.0f), mouseX, mouseY)) {
                    scrollInt = 0;
                }
            }
            click = false;
        } else {
            click = true;
        }
        ArrayList<Vec2f> vecs = new ArrayList<Vec2f>();
        vecs.add(new Vec2f(x + 0.5f, y + 30.0f - 2.0f));
        vecs.add(new Vec2f(x + 4.5f, y + 30.0f));
        vecs.add(new Vec2f(x + 0.5f, y + 30.0f + 2.0f));
        RenderUtils.drawSome(vecs, ColorUtils.fadeColor(ColorUtils.getOverallColorFrom(ClientColors.getColor1(0, 0.35f), ClientColors.getColor2(0, 0.35f)), ColorUtils.getColor(255, 100), 1.0f));
        vecs.clear();
        vecs.add(new Vec2f(x, y + 30.0f - 2.0f));
        vecs.add(new Vec2f(x + 4.0f, y + 30.0f));
        vecs.add(new Vec2f(x, y + 30.0f + 2.0f));
        RenderUtils.drawSome(vecs, ColorUtils.fadeColor(ColorUtils.getOverallColorFrom(ClientColors.getColor1(0, 0.7f), ClientColors.getColor2(0, 0.7f)), ColorUtils.getColor(255, 170), 1.0f));
        StencilUtil.initStencilToWrite();
        RenderUtils.drawRect(x + 1.0f, y + 1.0f, x + w - 1.0f, y + 60.0f - 1.0f, ColorUtils.getColor(0, 0, 0, 100));
        StencilUtil.readStencilBuffer(1);
        for (int i = -5000; i < 5000; ++i) {
            float sY = y + 30.0f - (float)(i * 7) + scroll.getAnim() - 3.0f;
            if (!(sY > y - 7.0f) || !(sY < y + 60.0f + 7.0f)) continue;
            int yP = MathUtils.getDifferenceOf(i, 0) < 10.0 ? i : (i > 0 ? 10 : -10) + (i - (i > 0 ? 10 : -10)) * 10;
            float alpha = (int)MathUtils.clamp(255.0 * (1.0 - MathUtils.getDifferenceOf((double)sY, (double)(y + 30.0f) - 2.5) / 36.3636360168457), 26.0, 255.0);
            float yDiff = (float)MathUtils.getDifferenceOf(sY + 3.0f, y + 30.0f);
            int c = ColorUtils.getOverallColorFrom(ColorUtils.getFixedWhiteColor(), ColorUtils.getOverallColorFrom(ClientColors.getColor2(), ClientColors.getColor1(), (float)MathUtils.clamp(MathUtils.getDifferenceOf(sY + 3.0f, y + 60.0f) / 60.0, 0.0, 1.0)), 0.35f);
            Fonts.mntsb_12.drawStringWithOutline("" + -yP, (double)(x + 2.0f + 7.0f) - Math.sin(Math.toRadians(yDiff)) * 14.0, sY + 2.0f, ColorUtils.swapAlpha(c, alpha));
        }
        StencilUtil.uninitStencilBuffer();
        RenderUtils.drawTwoAlphedSideways(x, y, x + w, y + 0.5f, ClientColors.getColor1(0, 0.0f), ClientColors.getColor1(0, 0.3f), true);
        RenderUtils.drawTwoAlphedSideways(x, y + 60.0f - 0.5f, x + w, y + 60.0f, ClientColors.getColor2(0, 0.0f), ClientColors.getColor2(0, 0.3f), true);
    }

    /*
     * Unable to fully structure code
     */
    public void drawDrags(ScaledResolution sr) {
        block12: {
            if ((double)this.dragAnim.getAnim() > 0.05) {
                c = ColorUtils.getColor(255, 255, 255, 255.0f * this.dragAnim.getAnim());
                ext = 2.0f + this.dragAnim.getAnim() + (float)(TargetHUD.get.Mode.currentMode.equalsIgnoreCase("WetWorn") != false);
                RenderUtils.drawRoundOutline(TargetHUD.xPosHud - ext, TargetHUD.yPosHud - ext, TargetHUD.widthHud + ext * 2.0f, TargetHUD.heightHud + ext * 2.0f, 8.0f * (TargetHUD.get.Mode.currentMode.equalsIgnoreCase("Bushy") != false ? 0.25f : 1.0f), this.dragAnim.getAnim(), 0, c);
            }
            if ((double)this.dragAnim2.getAnim() > 0.05) {
                c = ColorUtils.getColor(255, 255, 255, 255.0f * this.dragAnim2.getAnim());
                ext = 1.0f + this.dragAnim2.getAnim();
                RenderUtils.drawRoundOutline(Hud.wmPosX - ext, Hud.wmPosY - ext, Hud.wmWidth + ext * 2.0f, Hud.wmHeight + ext * 2.0f, 2.0f, this.dragAnim2.getAnim(), 0, c);
            }
            if ((double)this.dragAnim4.getAnim() > 0.05) {
                c = ColorUtils.getColor(255, 255, 255, 255.0f * this.dragAnim4.getAnim());
                ext = 2.0f + this.dragAnim4.getAnim();
                RenderUtils.drawRoundOutline(Hud.potPosX - ext, Hud.potPosY - ext, Hud.potWidth + ext * 2.0f, Hud.potHeight + ext * 2.0f, 4.0f, this.dragAnim4.getAnim(), 0, c);
            }
            if (!((double)this.dragAnim5.getAnim() > 0.05)) break block12;
            c = ColorUtils.getColor(255, 255, 255, 255.0f * this.dragAnim5.getAnim());
            ext = 2.0f;
            if (!(Hud.armPosY > 6.0f)) ** GOTO lbl-1000
            v0 = new ScaledResolution(this.mc);
            if (Hud.armPosY > (float)(v0.getScaledHeight() - 58) && MathUtils.getDifferenceOf(Hud.armPosX, (float)new ScaledResolution(this.mc).getScaledWidth() / 2.0f) < 94.0 || !(Hud.armWidth > Hud.armHeight)) lbl-1000:
            // 2 sources

            {
                v1 = true;
            } else {
                v1 = false;
            }
            verticalMode = v1;
            RenderUtils.drawRoundOutline(Hud.armPosX - ext, Hud.armPosY - ext - (float)(verticalMode != false ? 0 : 6), Hud.armWidth + ext * 2.0f, Hud.armHeight + ext * 2.0f + (float)(verticalMode != false ? 0 : 6), 2.0f, this.dragAnim5.getAnim(), 0, c);
        }
        if ((double)this.dragAnim6.getAnim() > 0.05) {
            c = ColorUtils.getColor(255, 255, 255, 255.0f * this.dragAnim6.getAnim());
            ext = 2.0f + this.dragAnim6.getAnim();
            RenderUtils.drawRoundOutline(Hud.stPosX - ext, Hud.stPosY - ext, Hud.stWidth + ext * 2.0f, Hud.stHeight + ext * 2.0f, 3.0f, this.dragAnim6.getAnim(), 0, c);
        }
        if ((double)this.dragAnim7.getAnim() > 0.05) {
            c = ColorUtils.getColor(255, 255, 255, 255.0f * this.dragAnim7.getAnim());
            ext = 1.0f + this.dragAnim7.getAnim();
            RenderUtils.drawRoundOutline(Hud.listPosX - ext - Hud.get.getArrayWidth(sr), Hud.listPosY - ext, Hud.get.getArrayWidth(sr) + ext * 2.0f, Hud.get.getArrayHeight() + ext * 2.0f, 1.0f, this.dragAnim7.getAnim() / 4.0f, 0, c);
        }
        if ((double)this.dragAnim8.getAnim() > 0.05) {
            c = ColorUtils.getColor(255, 255, 255, 255.0f * this.dragAnim8.getAnim());
            ext = 2.0f + this.dragAnim8.getAnim();
            pos = CommandGui.getWindowCoord();
            RenderUtils.drawRoundOutline(pos[0] - ext, pos[1] - ext, CommandGui.getWindowWidth() + ext * 2.0f, CommandGui.getWindowHeight() + 2.0f + ext * 2.0f, 3.0f, this.dragAnim8.getAnim(), 0, c);
        }
        if ((double)this.dragAnim9.getAnim() > 0.05) {
            c = ColorUtils.getColor(255, 255, 255, 255.0f * this.dragAnim9.getAnim());
            mode = Timer.get.Render.currentMode;
            isCircle = mode.equalsIgnoreCase("Circle");
            isLine = mode.equalsIgnoreCase("Line");
            isSmoothNine = mode.equalsIgnoreCase("SmoothNine");
            ext = this.dragAnim8.getAnim() + (isCircle != false ? 2.5f : (isLine != false ? 2.0f : (isSmoothNine != false ? 3.0f : 0.0f)));
            x = Timer.getX(sr);
            y = Timer.getY(sr);
            w = Timer.getWidth();
            h = Timer.getHeight();
            dx = (float)sr.getScaledWidth() / 2.0f - (x + w / 2.0f);
            dy = (float)sr.getScaledHeight() / 2.0f - (y + h / 2.0f);
            v2 = middle = Math.sqrt(dx * dx + dy * dy) < 2.0 && mode.equalsIgnoreCase("Plate") == false;
            if (middle) {
                x = (float)sr.getScaledWidth() / 2.0f - w / 2.0f;
                y = (float)sr.getScaledHeight() / 2.0f - w / 2.0f;
            }
            RenderUtils.drawRoundOutline(x - ext, y - ext, w + ext * 2.0f, h + ext * 2.0f, isCircle != false || isSmoothNine != false ? h / 2.0f + (float)(isSmoothNine != false ? 3 : 2) : 3.0f, this.dragAnim9.getAnim(), 0, c);
        }
        if ((double)this.dragAnim10.getAnim() > 0.05) {
            c = ColorUtils.getColor(255, 255, 255, 255.0f * this.dragAnim10.getAnim());
            ext = 3.0f + this.dragAnim10.getAnim();
            RenderUtils.drawRoundOutline(Hud.kbPosX - ext, Hud.kbPosY - ext, Hud.kbWidth + ext * 2.0f, Hud.kbHeight + ext * 2.0f, 3.0f, this.dragAnim10.getAnim(), 0, c);
        }
        if ((double)this.dragAnim11.getAnim() > 0.05) {
            c = ColorUtils.getColor(255, 255, 255, 255.0f * this.dragAnim11.getAnim());
            ext = 2.0f + this.dragAnim11.getAnim();
            RenderUtils.drawRoundOutline(Hud.pcPosX - ext, Hud.pcPosY - ext, Hud.pcWidth + ext * 2.0f, Hud.pcHeight + ext * 2.0f, 3.0f, this.dragAnim11.getAnim(), 0, c);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (Panic.stop) {
            GuiChat.drawRect(2, (double)(height - 14), (double)(width - 2), (double)(height - 2), Integer.MIN_VALUE);
            this.inputField.drawTextBox();
            ITextComponent itextcomponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
            if (itextcomponent != null && itextcomponent.getStyle().getHoverEvent() != null) {
                this.handleComponentHover(itextcomponent, mouseX, mouseY);
            }
            super.drawScreen(mouseX, mouseY, partialTicks);
        } else {
            ITextComponent itextcomponent;
            int cocuXyu;
            float curY;
            float curX;
            if (ComfortUi.get.isPaintInChat()) {
                paintUI.renderUpdatePanel(mouseX, mouseY);
            }
            CommandGui.updateMousePos(mouseX, mouseY);
            ScaledResolution sr = new ScaledResolution(this.mc);
            this.drawDrags(sr);
            if (ComfortUi.get.isClipHelperInChat()) {
                this.cliper(sr, mouseX, mouseY);
            }
            float scW = sr.getScaledWidth();
            float scH = sr.getScaledHeight();
            this.x = ((Settings)TargetHUD.get.settings.get((int)1)).fValue * scW;
            this.y = ((Settings)TargetHUD.get.settings.get((int)2)).fValue * scH;
            this.x2 = ((Settings)Hud.get.settings.get((int)0)).fValue * scW;
            this.y2 = ((Settings)Hud.get.settings.get((int)1)).fValue * scH;
            this.x4 = ((Settings)Hud.get.settings.get((int)7)).fValue * scW;
            this.y4 = ((Settings)Hud.get.settings.get((int)8)).fValue * scH;
            this.x5 = ((Settings)Hud.get.settings.get((int)12)).fValue * scW;
            this.y5 = ((Settings)Hud.get.settings.get((int)13)).fValue * scH;
            this.x5 = ((Settings)Hud.get.settings.get((int)15)).fValue * scW;
            this.y5 = ((Settings)Hud.get.settings.get((int)16)).fValue * scH;
            this.x6 = ((Settings)Hud.get.settings.get((int)18)).fValue * scW;
            this.y6 = ((Settings)Hud.get.settings.get((int)19)).fValue * scH;
            this.x8 = ((Settings)CommandGui.get.settings.get((int)0)).fValue * scW;
            this.y8 = ((Settings)CommandGui.get.settings.get((int)1)).fValue * scH;
            this.x9 = Timer.getCoordsSettings()[0] * scW;
            this.y9 = Timer.getCoordsSettings()[1] * scH;
            this.x10 = ((Settings)Hud.get.settings.get((int)22)).fValue * scW;
            this.y10 = ((Settings)Hud.get.settings.get((int)23)).fValue * scH;
            if (this.dragging) {
                this.x = mouseX - this.dragX;
                this.y = mouseY - this.dragY;
                ((Settings)TargetHUD.get.settings.get((int)1)).fValue = MathUtils.clamp(this.x / scW, (3.0f + TargetHUD.widthHud / 2.0f) / scW, 1.0f - (3.0f + TargetHUD.widthHud / 2.0f) / scW);
                ((Settings)TargetHUD.get.settings.get((int)2)).fValue = MathUtils.clamp(this.y / scH, (3.0f + TargetHUD.heightHud / 2.0f) / scH, 1.0f - (3.0f + TargetHUD.heightHud / 2.0f) / scH);
            } else {
                this.dragX = (int)((float)mouseX - ((Settings)TargetHUD.get.settings.get((int)1)).fValue * scW);
                this.dragY = (int)((float)mouseY - ((Settings)TargetHUD.get.settings.get((int)2)).fValue * scH);
            }
            if (this.dragging2) {
                this.x2 = mouseX - this.dragX2;
                this.y2 = mouseY - this.dragY2;
                curX = MathUtils.clamp(this.x2 / scW, 1.0f / scW, 1.0f - Hud.wmWidth / scW - 1.0f / scW);
                curY = MathUtils.clamp(this.y2 / scH, 1.0f / scH, 1.0f - Hud.wmHeight / scH - 1.0f / scH);
                ((Settings)Hud.get.settings.get((int)0)).fValue = curX;
                ((Settings)Hud.get.settings.get((int)1)).fValue = curY;
            } else {
                this.dragX2 = (int)((float)mouseX - ((Settings)Hud.get.settings.get((int)0)).fValue * scW);
                this.dragY2 = (int)((float)mouseY - ((Settings)Hud.get.settings.get((int)1)).fValue * scH);
            }
            if (this.dragging4) {
                this.x4 = mouseX - this.dragX4;
                this.y4 = mouseY - this.dragY4;
                curX = MathUtils.clamp(this.x4 / scW, 2.0f / scW, 1.0f - Hud.potWidth / scW - 2.0f / scW);
                curY = MathUtils.clamp(this.y4 / scH, 2.0f / scH, 1.0f - Hud.potHeight / scH - 2.0f / scH);
                ((Settings)Hud.get.settings.get((int)7)).fValue = curX;
                ((Settings)Hud.get.settings.get((int)8)).fValue = curY;
            } else {
                this.dragX4 = (int)((float)mouseX - ((Settings)Hud.get.settings.get((int)7)).fValue * scW);
                this.dragY4 = (int)((float)mouseY - ((Settings)Hud.get.settings.get((int)8)).fValue * scH);
            }
            if (this.dragging5) {
                this.x5 = mouseX - this.dragX5;
                this.y5 = mouseY - this.dragY5;
                curX = MathUtils.clamp(this.x5 / scW, 2.0f / scW, 1.0f - Hud.armWidth / scW - 2.0f / scW);
                curY = MathUtils.clamp(this.y5 / scH, 2.0f / scH, 1.0f - Hud.armHeight / scH - 2.0f / scH);
                ((Settings)Hud.get.settings.get((int)12)).fValue = curX;
                ((Settings)Hud.get.settings.get((int)13)).fValue = curY;
            } else {
                this.dragX5 = (int)((float)mouseX - ((Settings)Hud.get.settings.get((int)12)).fValue * scW);
                this.dragY5 = (int)((float)mouseY - ((Settings)Hud.get.settings.get((int)13)).fValue * scH);
            }
            if (this.dragging6) {
                this.x6 = mouseX - this.dragX6;
                this.y6 = mouseY - this.dragY6;
                curX = MathUtils.clamp(this.x6 / scW, 2.0f / scW, 1.0f - Hud.stWidth / scW - 2.0f / scW);
                curY = MathUtils.clamp(this.y6 / scH, 2.0f / scH, 1.0f - Hud.stHeight / scH - 2.0f / scH);
                ((Settings)Hud.get.settings.get((int)15)).fValue = curX;
                ((Settings)Hud.get.settings.get((int)16)).fValue = curY;
            } else {
                this.dragX6 = (int)((float)mouseX - ((Settings)Hud.get.settings.get((int)15)).fValue * scW);
                this.dragY6 = (int)((float)mouseY - ((Settings)Hud.get.settings.get((int)16)).fValue * scH);
            }
            if (this.dragging7) {
                this.x7 = mouseX - this.dragX7;
                this.y7 = mouseY - this.dragY7;
                ((Settings)Hud.get.settings.get((int)18)).fValue = MathUtils.clamp(this.x7 / scW, (Hud.get.getArrayWidth(sr) + 0.5f) / scW, 1.0f);
                ((Settings)Hud.get.settings.get((int)19)).fValue = MathUtils.clamp(this.y7 / scH, 0.0f, 1.0f - (1.0f - (scH - Hud.get.getArrayHeight()) / scH));
            } else {
                this.dragX7 = (int)((float)mouseX - ((Settings)Hud.get.settings.get((int)18)).fValue * scW);
                this.dragY7 = (int)((float)mouseY - ((Settings)Hud.get.settings.get((int)19)).fValue * scH);
            }
            if (this.dragging8) {
                this.x8 = mouseX - this.dragX8;
                this.y8 = mouseY - this.dragY8;
                curX = MathUtils.clamp(this.x8 / scW, 2.0f / scW, 1.0f - CommandGui.getWindowWidth() / scW - 2.0f / scW);
                curY = MathUtils.clamp(this.y8 / scH, 2.0f / scH, 1.0f - (CommandGui.getWindowHeight() + 3.0f) / scH - 2.0f / scH);
                float oldX = ((Settings)CommandGui.get.settings.get((int)0)).fValue;
                float oldY = ((Settings)CommandGui.get.settings.get((int)1)).fValue;
                ((Settings)CommandGui.get.settings.get((int)0)).fValue = MathUtils.lerp(oldX, curX, 0.03f * (float)Minecraft.frameTime);
                ((Settings)CommandGui.get.settings.get((int)1)).fValue = MathUtils.lerp(oldY, curY, 0.03f * (float)Minecraft.frameTime);
            } else {
                this.dragX8 = (int)((float)mouseX - ((Settings)CommandGui.get.settings.get((int)0)).fValue * scW);
                this.dragY8 = (int)((float)mouseY - ((Settings)CommandGui.get.settings.get((int)1)).fValue * scH);
            }
            if (this.dragging9) {
                this.x9 = mouseX - this.dragX9;
                this.y9 = mouseY - this.dragY9;
                float oldX = Timer.getCoordsSettings()[0];
                float oldY = Timer.getCoordsSettings()[1];
                float curX2 = MathUtils.clamp(this.x9 / scW, (Timer.getWidth() / 2.0f + 2.0f) / scW, 1.0f - Timer.getWidth() / 2.0f / scW - 2.0f / scW);
                float curY2 = MathUtils.clamp(this.y9 / scH, (Timer.getHeight() / 2.0f + 2.0f) / scH, 1.0f - Timer.getHeight() / 2.0f / scH - 2.0f / scH);
                Timer.setSetsX(MathUtils.lerp(oldX, curX2, 0.02f * (float)Minecraft.frameTime));
                Timer.setSetsY(MathUtils.lerp(oldY, curY2, 0.02f * (float)Minecraft.frameTime));
            } else {
                this.dragX9 = (int)((float)mouseX - Timer.getCoordsSettings()[0] * scW);
                this.dragY9 = (int)((float)mouseY - Timer.getCoordsSettings()[1] * scH);
            }
            if (this.dragging10) {
                this.x10 = mouseX - this.dragX10;
                this.y10 = mouseY - this.dragY10;
                curX = MathUtils.clamp(this.x10 / scW, 2.0f / scW, 1.0f - Hud.kbWidth / scW - 2.0f / scW);
                curY = MathUtils.clamp(this.y10 / scH, 2.0f / scH, 1.0f - Hud.kbHeight / scH - 2.0f / scH);
                ((Settings)Hud.get.settings.get((int)21)).fValue = curX;
                ((Settings)Hud.get.settings.get((int)22)).fValue = curY;
            } else {
                this.dragX10 = (int)((float)mouseX - Hud.get.currentFloatValue("KX") * scW);
                this.dragY10 = (int)((float)mouseY - Hud.get.currentFloatValue("KY") * scH);
            }
            if (this.dragging11) {
                this.x11 = mouseX - this.dragX11;
                this.y11 = mouseY - this.dragY11;
                curX = MathUtils.clamp(this.x11 / scW, 2.0f / scW, 1.0f - Hud.pcWidth / scW - 2.0f / scW);
                curY = MathUtils.clamp(this.y11 / scH, 2.0f / scH, 1.0f - Hud.pcHeight / scH - 2.0f / scH);
                ((Settings)Hud.get.settings.get((int)24)).fValue = curX;
                ((Settings)Hud.get.settings.get((int)25)).fValue = curY;
            } else {
                this.dragX11 = (int)((float)mouseX - Hud.get.currentFloatValue("PCX") * scW);
                this.dragY11 = (int)((float)mouseY - Hud.get.currentFloatValue("PCY") * scH);
            }
            boolean lb = Mouse.isButtonDown(0);
            if (GuiChat.paintUI.isDragging) {
                this.dragging11 = false;
                this.dragging10 = false;
                this.dragging9 = false;
                this.dragging8 = false;
                this.dragging7 = false;
                this.dragging6 = false;
                this.dragging5 = false;
                this.dragging4 = false;
                this.dragging3 = false;
                this.dragging2 = false;
                this.dragging = false;
                this.dragAnim11.to = 0.0f;
                this.dragAnim10.to = 0.0f;
                this.dragAnim9.to = 0.0f;
                this.dragAnim8.to = 0.0f;
                this.dragAnim7.to = 0.0f;
                this.dragAnim6.to = 0.0f;
                this.dragAnim5.to = 0.0f;
                this.dragAnim4.to = 0.0f;
                this.dragAnim3.to = 0.0f;
                this.dragAnim2.to = 0.0f;
                this.dragAnim.to = 0.0f;
            } else if (Hud.get.isHoveredPickupsHUD(mouseX, mouseY)) {
                this.dragging11 = lb && !this.dragging4 && !this.dragging && !this.dragging2 && !this.dragging3 && !this.dragging5 && !this.dragging7 && !this.dragging8 && !this.dragging9 && !this.dragging6 && !this.dragging10;
                this.dragAnim11.to = this.dragging11 ? 1.0f : 0.0f;
            } else if (Hud.get.isHoveredKeyBindsHUD(mouseX, mouseY)) {
                this.dragging10 = lb && !this.dragging4 && !this.dragging && !this.dragging2 && !this.dragging3 && !this.dragging5 && !this.dragging7 && !this.dragging8 && !this.dragging9 && !this.dragging6 && !this.dragging11;
                this.dragAnim10.to = this.dragging10 ? 1.0f : 0.0f;
            } else if (Hud.get.isHoveredStaffListHUD(mouseX, mouseY)) {
                this.dragging6 = lb && !this.dragging4 && !this.dragging && !this.dragging2 && !this.dragging3 && !this.dragging5 && !this.dragging7 && !this.dragging8 && !this.dragging9 && !this.dragging10 && !this.dragging11;
                this.dragAnim6.to = this.dragging6 ? 1.0f : 0.0f;
            } else if (Hud.get.isHoveredToArmorHUD(mouseX, mouseY)) {
                this.dragging5 = lb && !this.dragging4 && !this.dragging && !this.dragging2 && !this.dragging3 && !this.dragging6 && !this.dragging7 && !this.dragging8 && !this.dragging9 && !this.dragging10 && !this.dragging11;
                this.dragAnim5.to = this.dragging5 ? 1.0f : 0.0f;
            } else if (Hud.get.isHoveredToPotionsHUD(mouseX, mouseY)) {
                this.dragging4 = lb && !this.dragging2 && !this.dragging && !this.dragging3 && !this.dragging5 && !this.dragging6 && !this.dragging7 && !this.dragging8 && !this.dragging9 && !this.dragging10 && !this.dragging11;
                this.dragAnim4.to = this.dragging4 ? 1.0f : 0.0f;
            } else if (Hud.get.isHoverToArrayList(mouseX, mouseY, sr)) {
                this.dragging7 = lb && !this.dragging4 && !this.dragging && !this.dragging2 && !this.dragging3 && !this.dragging5 && !this.dragging6 && !this.dragging8 && !this.dragging9 && !this.dragging10 && !this.dragging11;
                this.dragAnim7.to = this.dragging7 ? 1.0f : 0.0f;
            } else if (Timer.isHoveredToTimer(mouseX, mouseY, sr)) {
                this.dragging9 = lb && !this.dragging4 && !this.dragging && !this.dragging2 && !this.dragging3 && !this.dragging5 && !this.dragging6 && !this.dragging8 && !this.dragging7 && !this.dragging10 && !this.dragging11;
                this.dragAnim9.to = this.dragging9 ? 1.0f : 0.0f;
            } else if (HoverUtils.isHovered((int)(((Settings)Hud.get.settings.get((int)0)).fValue * scW), (int)(((Settings)Hud.get.settings.get((int)1)).fValue * scH), (int)(((Settings)Hud.get.settings.get((int)0)).fValue * scW + Hud.wmWidth), (int)(((Settings)Hud.get.settings.get((int)1)).fValue * scH + Hud.wmHeight), mouseX, mouseY)) {
                this.dragging2 = lb && !this.dragging && !this.dragging3 && !this.dragging4 && !this.dragging5 && !this.dragging6 && !this.dragging7 && !this.dragging8 && !this.dragging9 && !this.dragging10 && !this.dragging11;
                this.dragAnim2.to = this.dragging2 ? 1.0f : 0.0f;
            } else if (CommandGui.isHoveredToPanel(false)) {
                if (CommandGui.isHoveredToPanel(true)) {
                    this.dragging8 = lb && !this.dragging4 && !this.dragging && !this.dragging2 && !this.dragging3 && !this.dragging5 && !this.dragging6 && !this.dragging7 && !this.dragging9 && !this.dragging10 && !this.dragging11;
                }
                this.dragAnim8.to = this.dragging8 ? 1.0f : 0.0f;
            } else if (HoverUtils.isHovered((int)(((Settings)TargetHUD.get.settings.get((int)1)).fValue * scW - TargetHUD.widthHud / 2.0f - 1.5f), (int)(((Settings)TargetHUD.get.settings.get((int)2)).fValue * scH - TargetHUD.heightHud / 2.0f - 1.5f), (int)(((Settings)TargetHUD.get.settings.get((int)1)).fValue * scW + TargetHUD.widthHud / 2.0f + 1.5f), (int)(((Settings)TargetHUD.get.settings.get((int)2)).fValue * scH + TargetHUD.heightHud / 2.0f), mouseX, mouseY)) {
                this.dragging = lb && !this.dragging2 && !this.dragging3 && !this.dragging4 && !this.dragging5 && !this.dragging6 && !this.dragging7 && !this.dragging8 && !this.dragging9 && !this.dragging10 && !this.dragging11;
                this.dragAnim.to = this.dragging ? 1.0f : 0.0f;
            }
            float width = this.mc.fontRendererObj.getStringWidth(this.inputField.getText() + "__") < 10 ? 3.0f : (float)this.mc.fontRendererObj.getStringWidth(this.inputField.getText() + "__");
            this.textwidth = MathUtils.lerp(this.textwidth, width, (float)Minecraft.frameTime * 0.01f);
            this.textwidthSmooth = MathUtils.lerp(this.textwidthSmooth, this.textwidth + MathUtils.clamp(width * 5.0f - this.textwidth * 5.0f, 0.0f, this.textwidth), (float)Minecraft.frameTime * 0.015f);
            float finalWidth = MathUtils.clamp(this.textwidthSmooth, 0.0f, (float)(GuiScreen.width - 20));
            GL11.glPushMatrix();
            if (!ComfortUi.get.isBetterChatline()) {
                GL11.glTranslated(0.0, 20.0f - this.alphaPC.getAnim() * 20.0f, 0.0);
            }
            if (ComfortUi.get.isAddClientButtons()) {
                if (Mouse.isButtonDown(0)) {
                    if (this.clickHider && HoverUtils.isHovered(scW - (float)Fonts.mntsb_20.getStringWidth("Hide info") - 6.0f, scH - 14.0f, scW - 2.0f, scH - 2.0f, mouseX, mouseY)) {
                        keyHide = !keyHide;
                    }
                    this.clickHider = false;
                } else {
                    this.clickHider = true;
                }
            }
            int n = cocuXyu = keyHide ? ColorUtils.getColor(100, 255, 100) : ColorUtils.getColor(100, 100, 100);
            if (ComfortUi.get.isAddClientButtons()) {
                RenderUtils.drawRect(scW - (float)Fonts.mntsb_20.getStringWidth("Hide info") - 6.0f, scH - 14.0f, scW - 2.0f, scH - 2.0f, keyHide ? ColorUtils.getColor(255, 255, 255, 130) : ColorUtils.getColor(255, 255, 255, 40));
                Fonts.mntsb_20.drawString("Hide info", scW - (float)Fonts.mntsb_20.getStringWidth("Hide info") - 3.5f, scH - (float)Fonts.mntsb_20.getHeight() - 2.5f, keyHide ? ColorUtils.getColor(255, 255, 255, 150) : ColorUtils.getColor(255, 255, 255, 70));
            }
            String s = this.inputField.getText();
            if (ComfortUi.get.isBetterChatline()) {
                int bgC = -1;
                int bgC2 = ColorUtils.getColor(0, 0, 0, 160);
                RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(4.0f, (float)height - 13.5f, 16.0f + finalWidth + 6.0f, (float)height - 3.5f, 2.0f, 2.0f, bgC2, bgC2, bgC2, bgC2, false, true, true);
                RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(5.0f, height - 12, 6.0f, height - 5, 0.5f, 1.0f, bgC, bgC, bgC, bgC, false, true, true);
                RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(13.5f + finalWidth + 6.0f, height - 12, 14.5f + finalWidth + 6.0f, height - 5, 0.5f, 1.0f, bgC, bgC, bgC, bgC, false, true, true);
                GL11.glTranslated(3.0, 0.0, 0.0);
                this.inputField.drawTextBox();
                GL11.glTranslated(-3.0, 0.0, 0.0);
            } else {
                GuiChat.drawRect(2, (double)(height - 14), (double)(GuiScreen.width - 7 - Fonts.mntsb_20.getStringWidth("Hide info")), (double)(height - 2), Integer.MIN_VALUE);
                this.inputField.drawTextBox();
            }
            String hideStart = "";
            float w = 0.0f;
            if (this.inputField.getText().startsWith("/") && keyHide) {
                if (s.startsWith("/warp ")) {
                    hideStart = "warp";
                }
                if (s.startsWith("/reg ")) {
                    hideStart = "reg";
                }
                if (s.startsWith("/register ")) {
                    hideStart = "register";
                }
                if (s.startsWith("/changepassword ")) {
                    hideStart = "changepassword";
                }
                if (s.startsWith("/l ")) {
                    hideStart = "l";
                }
                if (s.startsWith("/login ")) {
                    hideStart = "login";
                }
                if (s.startsWith("/home ")) {
                    hideStart = "home";
                }
                if (!hideStart.equalsIgnoreCase("")) {
                    w = this.mc.fontRendererObj.getStringWidth(hideStart);
                    Client.glass.glass(13.0f + w, scH - 14.0f, 14 + this.mc.fontRendererObj.getStringWidth(this.inputField.getText()), scH - 3.0f, 5.0f, 1.0f, 0.25f);
                }
            }
            if ((itextcomponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY())) != null && itextcomponent.getStyle().getHoverEvent() != null) {
                this.handleComponentHover(itextcomponent, mouseX, mouseY);
            }
            GL11.glPopMatrix();
            super.drawScreen(mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void setCompletions(String ... newCompletions) {
        this.tabCompleter.setCompletions(newCompletions);
    }

    static {
        scrollInt = 0;
        scroll = new AnimationUtils(0.0f, 0.0f, 0.1f);
        click = true;
    }

    public static class ChatTabCompleter
    extends TabCompleter {
        private final Minecraft clientInstance = Minecraft.getMinecraft();

        public ChatTabCompleter(GuiTextField p_i46749_1_) {
            super(p_i46749_1_, false);
        }

        @Override
        public void complete() {
            super.complete();
            if (this.completions.size() > 1) {
                StringBuilder stringbuilder = new StringBuilder();
                for (String s : this.completions) {
                    if (stringbuilder.length() > 0) {
                        stringbuilder.append(", ");
                    }
                    stringbuilder.append(s);
                }
                this.clientInstance.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new TextComponentString(stringbuilder.toString()), 1);
            }
        }

        @Override
        @Nullable
        public BlockPos getTargetBlockPos() {
            BlockPos blockpos = null;
            if (this.clientInstance.objectMouseOver != null && this.clientInstance.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
                blockpos = this.clientInstance.objectMouseOver.getBlockPos();
            }
            return blockpos;
        }
    }
}

