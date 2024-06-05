/*
 * Decompiled with CFR 0_132.
 *
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package me.superskidder.lune.login;

import me.superskidder.lune.font.FontLoaders;
import me.superskidder.lune.guis.utils.InputBox;
import me.superskidder.lune.guis.utils.LuneButton;
import me.superskidder.lune.guis.utils.PasswordInputBox;
import me.superskidder.lune.utils.client.DevUtils;
import me.superskidder.lune.utils.render.RenderUtils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.IOException;

public class GuiAltLogin
        extends GuiScreen {
    private PasswordInputBox password;
    private final GuiScreen previousScreen;
    private AltLoginThread thread;
    private InputBox username;

    private LuneButton login;
    private LuneButton back;

    /**
     * 如果该参数为true 那么用于登录Lune的账号
     */
    private static boolean test;

    /**
     *
     * @param previousScreen
     * @param test 如果该参数为true 那么用于登录Lune的账号
     */
    public GuiAltLogin(GuiScreen previousScreen, boolean test) {
        this.previousScreen = previousScreen;
        this.test = test;
    }

    @Override
    public void drawScreen(int x, int y, float z) {
        ScaledResolution sr = new ScaledResolution(mc);
        RenderUtils.drawImage(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), new ResourceLocation("client/UI/bg_blur.png"));
        int var3 = this.height / 4 + 24;
        RenderUtils.drawRect(this.width / 2 - 110, 50, this.width / 2 + 110, ((int) (sr.getScaledHeight_double()/1.1)), -1);
        login.drawButton(mc, x, y, new Color(83, 100, 255), new Color(103, 120, 255));
        back.drawButton(mc, x, y, new Color(255,74,74), new Color(255,94,94));

        FontLoaders.F30.drawCenteredString("Account Login", this.width / 2 + 1, 56, new Color(200, 200, 200).getRGB());
        FontLoaders.F30.drawCenteredString("Account Login", this.width / 2, 55, new Color(50, 50, 50).getRGB());
        this.username.drawTextBox();
        this.password.drawTextBox();
        String str = this.thread == null ? "\u00a7eWaiting..." : this.thread.getStatus();
        FontLoaders.F16.drawCenteredStringWithShadow(str, this.width / 2, 80, new Color(255, 255, 50).getRGB());
        if (this.username.getText().isEmpty()) {
            FontLoaders.F18.drawString("Username / E-Mail", this.width / 2 - 96, 105, -7829368);
        }
        if (this.password.getText().isEmpty()) {
            FontLoaders.F18.drawString("Password", this.width / 2 - 96, 135, -7829368);
        }
        super.drawScreen(x, y, z);
    }

    @Override
    public void initGui() {
        int var3 = this.height / 4 + 24;

        login = new LuneButton(0, this.width / 2 - 100, var3 + 75, "Login");
        back = new LuneButton(1, this.width / 2 - 100, var3 + 75 + 24, "Back");

        this.username = new InputBox(1, this.mc.fontRendererObj, this.width / 2 - 100, 100, 200, 20);
        this.password = new PasswordInputBox(this.mc.fontRendererObj, this.width / 2 - 100, 130, 200, 20);
        this.username.setFocused(true);
        this.username.setMaxStringLength(200);
        this.password.func_146203_f(200);
        Keyboard.enableRepeatEvents((boolean) true);

        if(getClipboardString() != null && getClipboardString().contains(":") && getClipboardString().length() < 80 && !test){
            username.setText(getClipboardString().split(":")[0]);
            password.setText(getClipboardString().split(":")[1]);
        }
    }

    public static String getClipboardString() {
        // 获取系统剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        // 获取剪贴板中的内容
        Transferable trans = clipboard.getContents(null);

        if (trans != null) {
            // 判断剪贴板中的内容是否支持文本
            if (trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    // 获取剪贴板中的文本内容
                    String text = (String) trans.getTransferData(DataFlavor.stringFlavor);
                    return text;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    @Override
    protected void keyTyped(char character, int key) {
        if (character == '\t' && (this.username.isFocused() || this.password.isFocused())) {
            this.username.setFocused(!this.username.isFocused());
            this.password.setFocused(!this.password.isFocused());
        }
        this.username.textboxKeyTyped(character, key);
        this.password.textboxKeyTyped(character, key);

        if (key == Keyboard.KEY_RETURN) {
            this.thread = new AltLoginThread(this.username.getText(), this.password.getText(), test);
            this.thread.start();
        }
    }

    @Override
    protected void mouseClicked(int x, int y, int button) {
        if(login.isMouseOver() && button == 0){
            this.thread = new AltLoginThread(this.username.getText(), this.password.getText(), test);
            this.thread.start();
        }

        if(back.isMouseOver() && button == 0){
            if (DevUtils.isDev()) {
                this.mc.displayGuiScreen(this.previousScreen);
                return;
            }
            System.exit(0);
            JOptionPane.showMessageDialog(null, "你做的很棒，你已经破解掉了这个啥b验证！");
            this.mc.displayGuiScreen(this.previousScreen);
        }

        try {
            super.mouseClicked(x, y, button);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.username.mouseClicked(x, y, button);
        this.password.mouseClicked(x, y, button);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean) false);
    }

    @Override
    public void updateScreen() {
        this.username.updateCursorCounter();
        this.password.updateCursorCounter();
    }
}

