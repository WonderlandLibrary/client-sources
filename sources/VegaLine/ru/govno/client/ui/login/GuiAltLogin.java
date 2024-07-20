/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.ui.login;

import dev.intave.viamcp.ViaMCP;
import java.io.IOException;
import java.security.SecureRandom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.SoundEvents;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import ru.govno.client.Client;
import ru.govno.client.newfont.CFontRenderer;
import ru.govno.client.newfont.Fonts;
import ru.govno.client.ui.login.AltLoginThread;
import ru.govno.client.utils.CTextField;
import ru.govno.client.utils.ClientRP;
import ru.govno.client.utils.Command.impl.Panic;
import ru.govno.client.utils.Render.AnimationUtils;
import ru.govno.client.utils.Render.ColorUtils;
import ru.govno.client.utils.Render.RenderUtils;
import ru.govno.client.utils.Render.StencilUtil;

public class GuiAltLogin
extends GuiScreen {
    String oldName = "";
    String newName = "";
    private final GuiScreen previousScreen;
    private AltLoginThread thread;
    private static final String alphabet = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
    private final long initTime = System.currentTimeMillis();
    private static final SecureRandom secureRandom = new SecureRandom();
    static CTextField textFieldName = new CTextField(0, Fonts.mntsb_20, 0, 0, 0, 0);
    static CTextField textFieldPass = new CTextField(1, Fonts.mntsb_20, 1, 1, 1, 1);
    AnimationUtils alphaName = new AnimationUtils(0.35f, 0.35f, 0.05f);
    AnimationUtils alphaPass = new AnimationUtils(0.35f, 0.35f, 0.05f);
    AnimationUtils keyLogin = new AnimationUtils(0.0f, 0.0f, 0.1f);
    AnimationUtils keyRandom = new AnimationUtils(0.0f, 0.0f, 0.1f);
    AnimationUtils keyExit = new AnimationUtils(0.0f, 0.0f, 0.1f);
    int mouseX;
    int mouseY;

    public GuiAltLogin(GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
    }

    public static String randomString(int strLength) {
        StringBuilder stringBuilder = new StringBuilder(strLength);
        for (int i = 0; i < strLength; ++i) {
            stringBuilder.append(alphabet.charAt(secureRandom.nextInt(alphabet.length())));
        }
        return stringBuilder.toString();
    }

    void clickReader() {
        if (textFieldName.isFocused()) {
            textFieldPass.setFocused(false);
            textFieldPass.setText("");
        }
        if (textFieldPass.isFocused()) {
            textFieldName.setFocused(false);
        } else if (!textFieldName.isFocused()) {
            textFieldName.setText("");
        }
    }

    void nameClickReader(float x, float y, float xPw, float yPw, int mouseX, int mouseY) {
        if (RenderUtils.isHovered(mouseX, mouseY, (int)x, (int)y, (int)xPw, (int)yPw)) {
            textFieldName.setFocused(!textFieldName.isFocused());
            textFieldPass.setFocused(false);
            if (!textFieldName.isFocused()) {
                textFieldName.setText("");
            }
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
        }
    }

    void passClickReader(float x, float y, float xPw, float yPw, int mouseX, int mouseY) {
        if (RenderUtils.isHovered(mouseX, mouseY, (int)x, (int)y, (int)xPw, (int)yPw)) {
            textFieldPass.setFocused(!textFieldPass.isFocused());
            textFieldName.setFocused(false);
            if (!textFieldPass.isFocused()) {
                textFieldPass.setText("");
            }
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
        }
    }

    void updateFieldsAlpha() {
        this.alphaName.to = textFieldName.isFocused() ? 1.0f : 0.35f;
        this.alphaPass.to = textFieldPass.isFocused() ? 1.0f : 0.35f;
    }

    void drawKey(float x, float y, float w, float h, float anim, String name, int color1, int color2) {
        int c1 = ColorUtils.swapAlpha(color1, (155.0f + anim * 100.0f) / 3.0f);
        int c2 = ColorUtils.swapAlpha(color2, (155.0f + anim * 100.0f) / 3.0f);
        float n = anim;
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x - n, y - n, x + w + n, y + h + n, 5.0f + anim * 2.01f, 0.5f + anim * 1.51f, c1, c2, c2, c1, false, true, true);
        Fonts.comfortaaRegular_18.drawStringWithShadow(name, x + w / 2.0f - (float)(Fonts.comfortaaRegular_18.getStringWidth(name) / 2), y + h / 2.0f - 3.0f, -1);
    }

    void drawKeyGhost(float x, float y, float w, float h, float anim) {
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x - anim, y - anim, x + w + anim, y + h + anim, 5.0f + anim * 2.0f, 0.5f + anim, -1, -1, -1, -1, false, true, false);
    }

    void onKeyLogin() {
        if (!textFieldName.getText().isEmpty()) {
            this.newName = textFieldName.getText();
            this.thread = new AltLoginThread(textFieldName.getText(), textFieldPass.getText());
            this.thread.start();
        }
        for (int i = 0; i < 120; ++i) {
            System.out.println(Minecraft.getMinecraft().session.getUsername());
        }
        this.mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
    }

    void onKeyRandom() {
        textFieldName.setFocused(true);
        String name = Client.randomNickname();
        textFieldName.setText(name);
        this.mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
    }

    void onKeyExit() {
        this.mc.displayGuiScreen(new GuiMainMenu());
        this.mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        ScaledResolution sr = new ScaledResolution(this.mc);
        if (!Panic.stop) {
            RenderUtils.drawScreenShaderBackground(sr, mouseX, mouseY);
        }
        float w = 380.0f;
        float h = 260.0f;
        float x = (float)(sr.getScaledWidth() / 2) - w / 2.0f;
        float y = (float)(sr.getScaledHeight() / 2) - h / 2.0f;
        float x2 = x + w;
        float y2 = y + h;
        int bgc1 = ColorUtils.getColor(0, 0, 0, 160);
        int bgc2 = ColorUtils.getColor(0, 0, 0, 80);
        CFontRenderer altFont = Fonts.mntsb_20;
        RenderUtils.fullRoundFG(x, y, x2, y2, 16.0f, bgc1, bgc1, bgc1, bgc1, false);
        RenderUtils.drawRoundedShadow(x, y, x2, y2, 34.0f, 16.0f, bgc2, false);
        GL11.glEnable(3089);
        RenderUtils.scissor(x, y, x2 - x, y2 - y);
        RenderUtils.fullRoundFG(x + w / 2.0f - (float)(altFont.getStringWidth("\u041c\u0435\u043d\u0435\u0434\u0436\u0435\u0440 \u0430\u043a\u043a\u0430\u0443\u043d\u0442\u043e\u0432") / 2) - 8.0f, y - 8.0f, x + w / 2.0f + (float)(altFont.getStringWidth("\u041c\u0435\u043d\u0435\u0434\u0436\u0435\u0440 \u0430\u043a\u043a\u0430\u0443\u043d\u0442\u043e\u0432") / 2) + 8.0f, y + 30.0f, 16.0f, 0, 0, bgc2, bgc2, false);
        RenderUtils.fullRoundFG(x + w / 2.0f - 60.0f, y + 165.0f, x + w / 2.0f + 60.0f, y2 + 8.0f, 16.0f, bgc2, bgc2, 0, 0, false);
        GL11.glDisable(3089);
        StencilUtil.uninitStencilBuffer();
        altFont.drawVGradientString("\u041c\u0435\u043d\u0435\u0434\u0436\u0435\u0440 \u0430\u043a\u043a\u0430\u0443\u043d\u0442\u043e\u0432", x + w / 2.0f - (float)(altFont.getStringWidth("\u041c\u0435\u043d\u0435\u0434\u0436\u0435\u0440 \u0430\u043a\u043a\u0430\u0443\u043d\u0442\u043e\u0432") / 2), y + 14.0f, ColorUtils.getColor(170), ColorUtils.getColor(255));
        int gee = ColorUtils.swapAlpha(-1, 50.0f);
        StencilUtil.initStencilToWrite();
        this.drawKeyGhost(x + w / 2.0f - 50.0f - 0.5f, y + 175.0f - 0.5f, 100.0f, 20.0f, this.keyLogin.getAnim());
        this.drawKeyGhost(x + w / 2.0f - 50.0f - 0.5f, y + 200.0f - 0.5f, 100.0f, 20.0f, this.keyRandom.getAnim());
        this.drawKeyGhost(x + w / 2.0f - 50.0f - 0.5f, y + 225.0f - 0.5f, 100.0f, 20.0f, this.keyExit.getAnim());
        StencilUtil.readStencilBuffer(1);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(mouseX - 10, mouseY, mouseX + 10, mouseY, 0.0f, 40.0f, gee, gee, gee, gee, true, false, true);
        StencilUtil.uninitStencilBuffer();
        this.keyLogin.to = RenderUtils.isHovered(mouseX, mouseY, x + w / 2.0f - 50.0f, y + 175.0f, 100.0f, 20.0f) ? 1.0f : 0.0f;
        this.keyRandom.to = RenderUtils.isHovered(mouseX, mouseY, x + w / 2.0f - 50.0f, y + 200.0f, 100.0f, 20.0f) ? 1.0f : 0.0f;
        this.keyExit.to = RenderUtils.isHovered(mouseX, mouseY, x + w / 2.0f - 50.0f, y + 225.0f, 100.0f, 20.0f) ? 1.0f : 0.0f;
        this.drawKey(x + w / 2.0f - 50.0f, y + 175.0f, 100.0f, 20.0f, this.keyLogin.getAnim(), "\u0412\u043e\u0439\u0442\u0438", ColorUtils.getColor(115, 253, 255), ColorUtils.getColor(60, 255, 161));
        this.drawKey(x + w / 2.0f - 50.0f, y + 200.0f, 100.0f, 20.0f, this.keyRandom.getAnim(), "\u0421\u043b\u0443\u0447\u0430\u0439\u043d\u043e\u0435 \u0438\u043c\u044f", ColorUtils.getColor(255, 226, 89), ColorUtils.getColor(255, 126, 0));
        this.drawKey(x + w / 2.0f - 50.0f, y + 225.0f, 100.0f, 20.0f, this.keyExit.getAnim(), "\u0412\u044b\u0439\u0442\u0438 \u0438\u0437 \u043c\u0435\u043d\u044e", ColorUtils.getColor(255, 135, 219), ColorUtils.getColor(219, 42, 255));
        this.clickReader();
        this.updateFieldsAlpha();
        int cd = ColorUtils.getColor(0, 0, 0, 110);
        int c0 = ColorUtils.getColor(0, 0, 0, 60);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + w / 2.0f - 110.0f, y + 50.0f, x + w / 2.0f + 110.0f, y + 75.0f, 6.0f, 2.0f, c0, c0, c0, c0, false, true, false);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + w / 2.0f - 110.0f, y + 50.0f, x + w / 2.0f + 110.0f, y + 75.0f, 6.0f, 2.0f, cd, cd, cd, cd, false, false, true);
        RenderUtils.drawRect(x + w / 2.0f - 1.0f, y + 50.0f, x + w / 2.0f + 1.0f, y + 75.0f, c0);
        Fonts.comfortaaRegular_17.drawStringWithShadow("\u041f\u0440\u0435\u0434\u044b\u0434\u0443\u0449\u0435\u0435 \u0438\u043c\u044f:", x + w / 2.0f - 1.0f + 5.0f - 110.0f, y + 50.0f + 4.0f, -1);
        Fonts.comfortaaRegular_14.drawStringWithShadow(this.oldName, x + w / 2.0f - 1.0f + 5.0f - 110.0f, y + 50.0f + 16.0f, -1);
        Fonts.comfortaaRegular_17.drawStringWithShadow("\u0422\u0435\u043a\u0443\u0449\u0435\u0435 \u0438\u043c\u044f:", x + w / 2.0f - 1.0f + 5.0f, y + 50.0f + 4.0f, -1);
        Fonts.comfortaaRegular_14.drawStringWithShadow(this.newName, x + w / 2.0f - 1.0f + 5.0f, y + 50.0f + 16.0f, -1);
        textFieldName.setMaxStringLength(16);
        textFieldPass.setMaxStringLength(32);
        String split = System.currentTimeMillis() % 1000L >= 500L ? "_" : "";
        int c1 = ColorUtils.getColor(91, 95, 255, 90);
        int c2 = ColorUtils.getColor(44, 37, 173, 30);
        int c3 = ColorUtils.getColor(0, 0, 0, 60);
        StencilUtil.initStencilToWrite();
        RenderUtils.drawRect(x + w / 2.0f - 110.0f - 3.0f, y + 90.0f - 3.0f, x + w / 2.0f - 110.0f + 32.0f - 3.0f, y + 115.0f + 3.0f, -1);
        StencilUtil.readStencilBuffer(1);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + w / 2.0f - 110.0f, y + 90.0f, x + w / 2.0f + 110.0f, y + 115.0f, 6.0f, 2.0f, c1, c1, c1, c1, true, true, true);
        StencilUtil.readStencilBuffer(0);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + w / 2.0f - 110.0f, y + 90.0f, x + w / 2.0f + 110.0f, y + 115.0f, 6.0f, 2.0f, c1, c2, c2, c1, true, true, true);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + w / 2.0f - 110.0f + 1.0f, y + 90.0f + 1.0f, x + w / 2.0f + 110.0f - 1.0f, y + 115.0f - 1.0f, 5.0f, 0.5f, c3, c3, c3, c3, false, true, true);
        StencilUtil.uninitStencilBuffer();
        Fonts.stylesicons_24.drawStringWithOutline(textFieldName.isFocused() ? "I" : "B", x + w / 2.0f - 110.0f + 9.0f, y + 90.0f + 9.5f, ColorUtils.swapAlpha(c1, 255.0f));
        if (textFieldName.isFocused() || !textFieldName.getText().isEmpty() && textFieldPass.isFocused()) {
            Fonts.comfortaaRegular_18.drawStringWithShadow(textFieldName.getText() + (textFieldName.isFocused() && textFieldName.getText().length() <= 16 && (textFieldName.getText().isEmpty() || !textFieldPass.isFocused()) ? split : ""), x + w / 2.0f - 110.0f + 34.0f, y + 90.0f + 10.0f, ColorUtils.swapAlpha(-1, 255.0f * this.alphaName.getAnim()));
        } else if (!textFieldPass.isFocused()) {
            Fonts.comfortaaBold_18.drawStringWithShadow("\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u043d\u0438\u043a\u043d\u0435\u0439\u043c", x + w / 2.0f - 110.0f + 34.0f, y + 90.0f + 9.5f, ColorUtils.getColor(255, 255, 255, 60));
        }
        int c4 = ColorUtils.getColor(230, 45, 45, 90);
        int c5 = ColorUtils.getColor(157, 36, 36, 30);
        StencilUtil.initStencilToWrite();
        RenderUtils.drawRect(x + w / 2.0f - 110.0f - 3.0f, y + 130.0f - 3.0f, x + w / 2.0f - 110.0f + 32.0f - 3.0f, y + 155.0f + 3.0f, -1);
        StencilUtil.readStencilBuffer(1);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + w / 2.0f - 110.0f, y + 130.0f, x + w / 2.0f + 110.0f, y + 155.0f, 6.0f, 2.0f, c4, c4, c4, c4, true, true, true);
        StencilUtil.readStencilBuffer(0);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + w / 2.0f - 110.0f, y + 130.0f, x + w / 2.0f + 110.0f, y + 155.0f, 6.0f, 2.0f, c4, c5, c5, c4, true, true, true);
        RenderUtils.drawRoundedFullGradientShadowFullGradientRoundedFullGradientRectWithBloomBool(x + w / 2.0f - 110.0f + 1.0f, y + 130.0f + 1.0f, x + w / 2.0f + 110.0f - 1.0f, y + 155.0f - 1.0f, 5.0f, 0.5f, c3, c3, c3, c3, false, true, true);
        StencilUtil.uninitStencilBuffer();
        Fonts.stylesicons_24.drawStringWithOutline(textFieldPass.isFocused() ? "I" : "L", x + w / 2.0f - 110.0f + 9.0f, y + 130.0f + 11.0f, ColorUtils.swapAlpha(c4, 255.0f));
        Object pass = "";
        for (int i = 0; i < textFieldPass.getText().length(); ++i) {
            pass = (String)pass + "*";
        }
        if (textFieldPass.isFocused()) {
            Fonts.comfortaaRegular_18.drawStringWithShadow((String)pass + (textFieldPass.isFocused() && textFieldPass.getText().length() <= 32 ? split : ""), x + w / 2.0f - 110.0f + 34.0f, y + 130.0f + 9.5f, ColorUtils.swapAlpha(-1, 255.0f * this.alphaPass.getAnim()));
        } else {
            Fonts.comfortaaBold_18.drawStringWithShadow("\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u043f\u0430\u0440\u043e\u043b\u044c (\u043b\u0438\u0446\u0435\u043d\u0437\u0438\u044f)", x + w / 2.0f - 110.0f + 34.0f, y + 130.0f + 9.5f, ColorUtils.getColor(255, 255, 255, 60));
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (!Panic.stop) {
            ViaMCP.INSTANCE.getViaPanel().drawPanel(1.0f, mouseX, mouseY);
        }
    }

    @Override
    public void initGui() {
        ClientRP.getInstance().getDiscordRP().update("\u0412 \u043c\u0435\u043d\u044e \u043c\u0435\u043d\u0435\u0434\u0436\u0435\u0440\u0430 \u0430\u043a\u043a\u0430\u0443\u043d\u0442\u043e\u0432", "\u041c\u0435\u043d\u044f\u0435\u0442 \u043d\u0438\u043a\u043d\u0435\u0439\u043c");
        this.newName = "None";
        this.oldName = this.mc.session.getUsername();
        Keyboard.enableRepeatEvents(true);
        textFieldName.setFocused(true);
        textFieldPass.setFocused(false);
        textFieldName.setText("");
        textFieldPass.setText("");
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        textFieldName.textboxKeyTyped(typedChar, keyCode);
        textFieldPass.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int x2, int y2, int button) {
        ScaledResolution sr = new ScaledResolution(this.mc);
        float w = 380.0f;
        float h = 260.0f;
        float x = (float)(sr.getScaledWidth() / 2) - w / 2.0f;
        float y = (float)(sr.getScaledHeight() / 2) - h / 2.0f;
        if (button == 0) {
            if (RenderUtils.isHovered(x2, y2, x + w / 2.0f - 50.0f, y + 175.0f, 100.0f, 20.0f)) {
                this.onKeyLogin();
            } else if (RenderUtils.isHovered(x2, y2, x + w / 2.0f - 50.0f, y + 200.0f, 100.0f, 20.0f)) {
                this.onKeyRandom();
            } else if (RenderUtils.isHovered(x2, y2, x + w / 2.0f - 50.0f, y + 225.0f, 100.0f, 20.0f)) {
                this.onKeyExit();
            } else {
                this.nameClickReader(x + w / 2.0f - 110.0f + 1.0f, y + 90.0f + 1.0f, 25.0f, 25.0f, x2, y2);
                this.passClickReader(x + w / 2.0f - 110.0f + 1.0f, y + 130.0f + 1.0f, 25.0f, 25.0f, x2, y2);
            }
        }
        try {
            super.mouseClicked(x2, y2, button);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!Panic.stop) {
            ViaMCP.INSTANCE.getViaPanel().mouseClick(this.mouseX, this.mouseY, button);
        }
    }

    @Override
    public void onGuiClosed() {
    }

    @Override
    public void updateScreen() {
        textFieldName.updateCursorCounter();
        textFieldPass.updateCursorCounter();
    }
}

