package me.dinozoid.strife.ui.menu;

import com.thealtening.auth.service.AlteningServiceType;
import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.account.Account;
import me.dinozoid.strife.account.AccountInfo;
import me.dinozoid.strife.font.CustomFont;
import me.dinozoid.strife.font.CustomFontRenderer;
import me.dinozoid.strife.shader.Shader;
import me.dinozoid.strife.shader.implementations.MenuShader;
import me.dinozoid.strife.ui.element.Position;
import me.dinozoid.strife.ui.element.StrifeButton;
import me.dinozoid.strife.ui.element.StrifeTextField;
import me.dinozoid.strife.util.player.AltService;
import me.dinozoid.strife.util.render.RenderUtil;
import me.dinozoid.strife.util.system.StringUtil;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;

public class StrifeAccountManager extends GuiScreen {

    private CustomFontRenderer font30, font24, font20, font19, font15;
    private float hypixelOpacity, mineplexOpacity;
    private ResourceLocation hypixel, mineplex;
    private Position menuPos, altsPos;
    private float hypixelY, mineplexY;
    private GuiScreen parent;
    private Shader shader;

    private StrifeTextField emailField, passwordField;
    private boolean addingAlt;
    private Account selectedAccount;
    private StrifeButton addButton;

    private StrifeButton clipboardButton, directLoginButton, addAccountButton, importListButton;

    private float scroll;
    private float yOffset;

    public StrifeAccountManager(GuiScreen parent, int pass) {
        shader = new MenuShader(pass);
        this.parent = parent;
        hypixel = new ResourceLocation("strife/gui/accountmanager/hypixel.png");
        mineplex = new ResourceLocation("strife/gui/accountmanager/mineplex.png");
        CustomFont font = StrifeClient.INSTANCE.fontRepository().defaultFont();
        font15 = font.size(15);
        font19 = font.size(19);
        font20 = font.size(20);
        font24 = font.size(24);
        font30 = font.size(30);
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        ScaledResolution sc = new ScaledResolution(mc);
        menuPos = new Position(60, 60, sc.getScaledWidth() - 60, sc.getScaledHeight() - 60);
        altsPos = new Position(menuPos.x() + 3, menuPos.y() + 25, menuPos.width() / 2 - 2, menuPos.height() - 28);
        addButton = new StrifeButton(altsPos.x(), altsPos.height() + 2, 100f, menuPos.height() - altsPos.height() - 4, "Add/Import", 19, 0xff4f5052);
        emailField = new StrifeTextField(19, menuPos.x() + menuPos.width() / 2 - 120 / 2f, menuPos.y() + menuPos.height() / 2 - 100, 120, 15, 0xff242424);
        passwordField = new StrifeTextField(19, menuPos.x() + menuPos.width() / 2 - 120 / 2f, menuPos.y() + menuPos.height() / 2 - 100 + 25, 120, 15, 0xff242424);

        clipboardButton = new StrifeButton(menuPos.x() + menuPos.width() / 2 - 120 / 2f, emailField.y() + 50, 120, 12, "Clipboard", 19, 0xff4f5052);
        directLoginButton = new StrifeButton(menuPos.x() + menuPos.width() / 2 - 120 / 2f, emailField.y() + 64, 120, 12, "Direct Login", 19, 0xff4f5052);
        addAccountButton = new StrifeButton(menuPos.x() + menuPos.width() / 2 - 120 / 2f, emailField.y() + 76, 120, 12, "Add Account", 19, 0xff4f5052);
        importListButton = new StrifeButton(menuPos.x() + menuPos.width() / 2 - 120 / 2f, emailField.y() + 88, 120, 12, "Import List", 19, 0xff4f5052);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sc = new ScaledResolution(mc);
        shader.render(sc.getScaledWidth(), sc.getScaledHeight());
        if (!addingAlt) {
            menuPos.width(RenderUtil.animate(sc.getScaledWidth() - 60, menuPos.width(), 0.3f) - 0.1F);
            menuPos.x(RenderUtil.animate(menuPos.originalX(), menuPos.x(), 0.3f) - 0.1F);
            altsPos.x(menuPos.x() + 3);
            altsPos.y(menuPos.y() + 25);
            altsPos.width(menuPos.width() / 2 - 2);
            altsPos.height(menuPos.height() - 28);
            emailField.setPosition(menuPos.x() + menuPos.width() / 2 - 200f / 2, menuPos.y() + menuPos.height() / 2 - 100, 200, 15);
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            RenderUtil.prepareScissorBox(menuPos.x(), menuPos.y(), menuPos.x() + menuPos.width(), menuPos.y() + menuPos.height());
            Gui.drawRect(menuPos.x(), menuPos.y(), menuPos.width(), menuPos.height(), 0xff2b2b2b);
            addButton.drawScreen(mouseX, mouseY);
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            GL11.glPopMatrix();

            int offset = 3;
            int accountHeight = 40;

            yOffset = -43;
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            RenderUtil.prepareScissorBox(altsPos.x(), altsPos.y(), altsPos.width(), altsPos.height());
            for (int i = 0; i < StrifeClient.INSTANCE.accountRepository().accounts().size(); i++) {
                Account account = StrifeClient.INSTANCE.accountRepository().accounts().get(i);
                account.y(RenderUtil.animate(((accountHeight + offset) * i) + offset, account.y(), 0.15f) - 0.1F);
                account.width(-RenderUtil.animate(altsPos.width() - offset, account.width(), 0.1f) - 0.1F);
                AccountInfo info = account.info();
                float altsY = altsPos.y() + account.y();
                String name = info.username() == null ? "<Unknown>" : info.hypixelRank().representation() + info.username();
                if (RenderUtil.isHoveredFull(altsPos.x() + account.width() + offset, altsY, altsPos.width() - offset, altsY + accountHeight, mouseX, mouseY) || selectedAccount == account) {
                    RenderUtil.drawRect(altsPos.x() + account.width() + offset, altsY, altsPos.width() - offset, altsY + accountHeight, new Color(0xff242424).brighter().getRGB());
                } else
                    RenderUtil.drawRect(altsPos.x() + account.width(), altsY, altsPos.width() - offset, altsY + accountHeight, 0xff242424);
                RenderUtil.drawDynamicTexture(account.avatar(), altsPos.x() + offset * 2, altsY + offset + 1, 32, 32);
                font19.drawStringWithShadow(name, altsPos.x() + 32 + offset * 2 + 2, altsY + offset + 1, -1);
                font15.drawStringWithShadow("Email: " + account.email(), altsPos.x() + 32 + offset * 2 + 2, altsY + offset + 1 + font19.getHeight(name) + 2, 0xff787878);
                font15.drawStringWithShadow("Capes: " + info.capes(), altsPos.x() + 32 + offset * 2 + 1.8f, altsY + offset + 1 + font19.getHeight(name) + font15.getHeight(info.capes().toString()) + 3, 0xff787878);
                yOffset += account.y();
            }
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            GL11.glPopMatrix();

            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            RenderUtil.prepareScissorBox(menuPos.x(), menuPos.y(), menuPos.width(), menuPos.height());
            if(selectedAccount != null) {
                AccountInfo info = selectedAccount.info();
                
                hypixelOpacity = RenderUtil.animate(255, hypixelOpacity, 0.1F) + 0.1F;
                mineplexOpacity = RenderUtil.animate(255, mineplexOpacity, 0.1F) + 0.1F;
                if(hypixelOpacity > 255) hypixelOpacity = 255;
                if(mineplexOpacity > 255) mineplexOpacity = 255;

                hypixelY = RenderUtil.animate(menuPos.y() + 28, hypixelY, 0.2F) - 0.1F;
                mineplexY = RenderUtil.animate(menuPos.y() + 28, mineplexY, 0.2F) - 0.1F;

                RenderUtil.drawImage(hypixel, altsPos.width() + 5, hypixelY, 207, 116, hypixelOpacity);
                RenderUtil.drawImage(mineplex, altsPos.width() + 14 + 207, mineplexY, 207, 116, mineplexOpacity);
            }
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            GL11.glPopMatrix();

        } else {
            float menuWidth = 300;
            menuPos.height(RenderUtil.animate(sc.getScaledHeight() - 60, menuPos.height(), 0.3f) - 0.1F);
            menuPos.y(RenderUtil.animate(menuPos.originalY(), menuPos.y(), 0.3f) - 0.1F);
            menuPos.width(RenderUtil.animate((sc.getScaledWidth() - 60) / 2f + menuWidth / 2, menuPos.width(), 0.3f) - 0.1F);
            menuPos.x(RenderUtil.animate((sc.getScaledWidth() - 60) / 2f - menuWidth / 2, menuPos.x(), 0.3f) - 0.1F);
            emailField.setPosition(menuPos.x() + menuWidth / 2 - 120 / 2f, menuPos.y() + menuPos.height() / 2 - (150 / sc.getScaleFactor()), 120, 15);
            passwordField.setPosition(emailField.x(), emailField.y() + 25, 120, 15);
            clipboardButton.setPosition(emailField.x(), emailField.y() + 50, 120, 12);
            directLoginButton.setPosition(emailField.x(), emailField.y() + 65, 120, 12);
            addAccountButton.setPosition(emailField.x(), emailField.y() + 80, 120, 12);
            importListButton.setPosition(emailField.x(), emailField.y() + 95, 120, 12);
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            RenderUtil.prepareScissorBox(menuPos.x(), menuPos.y(), menuPos.x() + menuPos.width(), menuPos.y() + menuPos.height());
            Gui.drawRect(menuPos.x(), menuPos.y(), menuPos.width(), menuPos.height(), 0xff2b2b2b);
            emailField.drawField(mouseX, mouseY);
            passwordField.drawField(mouseX, mouseY);
            font20.drawStringWithShadow(AltService.loginStatus(), emailField.x(), emailField.y() - 20, -1);
            clipboardButton.drawScreen(mouseX, mouseY);
            directLoginButton.drawScreen(mouseX, mouseY);
            addAccountButton.drawScreen(mouseX, mouseY);
            importListButton.drawScreen(mouseX, mouseY);
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            GL11.glPopMatrix();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (addingAlt) {
            emailField.mouseClicked(mouseX, mouseY, mouseButton);
            passwordField.mouseClicked(mouseX, mouseY, mouseButton);
            clipboardButton.mouseClicked(mouseX, mouseY, mouseButton, button -> {
                if (button == 0) {
                    mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                    String clipboardContents = StringUtil.getTrimmedClipboardContents();
                    String[] split = clipboardContents.split(":");
                    if(split[0] != null) emailField.text(split[0]);
                    if(split.length > 1) passwordField.text(split[1]);
                }
            });
            directLoginButton.mouseClicked(mouseX, mouseY, mouseButton, button -> {
                if (button == 0) {
                    mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                    String email = emailField.text();
                    String password = passwordField.text();
                    if(email != null)
                        AltService.login(email, password);
                }
            });
            addAccountButton.mouseClicked(mouseX, mouseY, mouseButton, button -> {
                if (button == 0) {
                    mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                    String email = emailField.text();
                    String password = passwordField.text();
                    if(email != null && !email.isEmpty()) {
                        StrifeClient.INSTANCE.accountRepository().addAccount(email.contains("@alt.com") ? AlteningServiceType.THEALTENING : AlteningServiceType.MOJANG, email, password);
                        addingAlt = false;
                    }
                }
            });
        } else {
            addButton.mouseClicked(mouseX, mouseY, mouseButton, button -> {
                if (button == 0) {
                    mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                    addingAlt = true;
                }
            });
            int offset = 3;
            int accountHeight = 40;
            for (int i = 0; i < StrifeClient.INSTANCE.accountRepository().accounts().size(); i++) {
                Account account = StrifeClient.INSTANCE.accountRepository().accounts().get(i);
                if(RenderUtil.isHoveredFull(altsPos.x(), altsPos.y() + account.y(), altsPos.width(), altsPos.y() + account.y() + accountHeight, mouseX, mouseY)) {
                    if(mouseButton == 0) {
                        mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
                        hypixelY = menuPos.y();
                        hypixelOpacity = 100;
                        mineplexY = hypixelY + 50;
                        mineplexOpacity = 100;
                        selectedAccount = account;
                    } else {
                        account.login();
                    }
                }
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (addingAlt) {
            emailField.keyTyped(typedChar, keyCode);
            passwordField.keyTyped(typedChar, keyCode);
            if (keyCode == 1)
                addingAlt = false;
        } else {
            if (keyCode == 1) {
                if (parent != null) {
                    if (parent instanceof StrifeMainMenu) ((StrifeMainMenu) parent).pass(shader.pass());
                    mc.displayGuiScreen(parent);
                } else mc.displayGuiScreen(new StrifeMainMenu(shader.pass()));
            }
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        if (Mouse.hasWheel()) {
            int pixels = 40;
            int direction = Mouse.getEventDWheel();
            if(direction > 0) {
                direction = 1;
            } else {
                direction = 0;
            }
            scroll = scroll - direction / pixels;
            scroll = MathHelper.clamp_float(scroll, 0.0F, 1.0F);
        }
    }

    @Override
    public void updateScreen() {
        emailField.updateScreen();
        passwordField.updateScreen();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public void pass(int pass) {
        shader.pass(pass);
    }
}
