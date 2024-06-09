/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.gui.account;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import me.thekirkayt.client.account.AccountManager;
import me.thekirkayt.client.account.Alt;
import me.thekirkayt.client.account.LoginThread;
import me.thekirkayt.client.gui.account.component.AltButton;
import me.thekirkayt.client.gui.account.component.Button;
import me.thekirkayt.client.gui.account.screen.Screen;
import me.thekirkayt.client.gui.account.screen.ScreenAddAccount;
import me.thekirkayt.client.gui.account.screen.ScreenEditAccount;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

public class AccountScreen
extends GuiScreen {
    private static AccountScreen instance;
    private List<AltButton> buttonList = new ArrayList<AltButton>();
    private DynamicTexture viewportTexture;
    private ResourceLocation panoramaLoc;
    private float panoramaTimer;
    private static final int GENERAL_BUTTON_WIDTH = 80;
    private static final int GENERAL_BUTTON_HEIGHT = 26;
    private static final int ALT_BUTTON_WIDTH = 120;
    private static final int ALT_BUTTON_HEIGHT = 26;
    private static final int WIDTH_BUFFER = 10;
    private static final int HEIGHT_BUFFER = 10;
    private static final int X_BASE = 20;
    private static final int Y_BASE = 40;
    private int scrollOffset = 0;
    private int scrollVelocity = 0;
    private int buttonsPerColumn = 5;
    private int prevWidth;
    private int prevHeight;
    public AltButton selectedButton;
    public Screen currentScreen;
    public String info = "\u00a7bWaiting...";
    public Alt toRemove = null;
    public Alt lastAlt;
    Button addAltButton;
    Button delAltButton;
    Button editAltButton;
    Button randomAltButton;
    Button lastAltButton;

    @Override
    public void initGui() {
        instance = this;
        AccountManager.load();
        this.scrollOffset = 0;
        this.scrollVelocity = 0;
        this.viewportTexture = new DynamicTexture(256, 256);
        this.panoramaLoc = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
        this.addAltButton = new AddButton();
        this.delAltButton = new DelButton();
        this.editAltButton = new EditButton();
        this.randomAltButton = new RandomButton();
        this.lastAltButton = new LastButton();
        this.addAltButtons();
        this.currentScreen = null;
        AccountManager.save();
    }

    @Override
    public void handleKeyboardInput() throws IOException {
        if (Keyboard.getEventKey() == 1 && this.currentScreen != null) {
            this.currentScreen = null;
        } else if (Keyboard.getEventKeyState()) {
            if (this.currentScreen != null) {
                this.currentScreen.onKeyPress(Keyboard.getEventCharacter(), Keyboard.getEventKey());
            }
            super.handleKeyboardInput();
        }
    }

    @Override
    protected void mouseClicked(int x, int y, int mouseButton) throws IOException {
        if (this.currentScreen != null) {
            this.currentScreen.onClick(x, y, mouseButton);
            return;
        }
        for (AltButton button : this.buttonList) {
            if (!button.isOver()) continue;
            if (this.selectedButton != null && this.selectedButton.equals(button)) {
                button.onClick(mouseButton);
                continue;
            }
            this.selectedButton = button;
        }
        if (this.addAltButton.isOver()) {
            this.addAltButton.onClick(mouseButton);
        }
        if (this.editAltButton.isOver()) {
            this.editAltButton.onClick(mouseButton);
        }
        if (this.delAltButton.isOver()) {
            this.delAltButton.onClick(mouseButton);
        }
        if (this.randomAltButton.isOver()) {
            this.randomAltButton.onClick(mouseButton);
        }
        if (this.lastAltButton.isOver()) {
            this.lastAltButton.onClick(mouseButton);
        }
        super.mouseClicked(x, y, mouseButton);
    }

    public void drawCenteredString(String text, float scale, int xOffset, int yOffset) {
        boolean tooLong = false;
        while ((float)ClientUtils.clientFont().getStringWidth(text) * scale > (float)width) {
            text = text.substring(0, text.length() - 1);
            tooLong = true;
        }
        if (tooLong) {
            text = text.substring(0, text.length() - 4);
            text = String.valueOf(String.valueOf(text)) + "...";
        }
        GL11.glScalef((float)scale, (float)scale, (float)scale);
        int strWidth = ClientUtils.clientFont().getStringWidth(text);
        strWidth = (int)((float)strWidth * scale);
        int x = width / 2 - strWidth / 2 + xOffset;
        int y = 4 + yOffset;
        x = (int)((float)x / scale);
        y = (int)((float)y / scale);
        ClientUtils.clientFont().drawStringWithShadow(text, x, y, -1);
        GL11.glScalef((float)(1.0f / scale), (float)(1.0f / scale), (float)(1.0f / scale));
    }

    @Override
    public void drawScreen(int x, int y, float par3) {
        GL11.glDisable((int)3008);
        this.renderSkybox(x, y, par3);
        GL11.glEnable((int)3008);
        int mouseX = Mouse.getEventX() * width / this.mc.displayWidth;
        int mouseY = height - Mouse.getEventY() * height / this.mc.displayHeight - 1;
        if (this.buttonList.size() > 0) {
            if (this.prevWidth != width || this.prevHeight != height) {
                this.initGui();
                this.prevWidth = width;
                this.prevHeight = height;
            }
            if (Mouse.hasWheel() && this.buttonList.get(this.buttonList.size() - 1).getX2() - this.buttonList.get(0).getX1() > width && this.currentScreen == null) {
                int wheel = Mouse.getDWheel();
                if (wheel < 0) {
                    this.scrollVelocity += 8;
                } else if (wheel > 0) {
                    this.scrollVelocity -= 8;
                }
                if (this.scrollVelocity > 40) {
                    this.scrollVelocity = 40;
                }
                if (this.scrollVelocity < -40) {
                    this.scrollVelocity = -40;
                }
                this.scrollOffset -= this.scrollVelocity;
                if (this.scrollOffset > 0 - this.scrollVelocity) {
                    this.scrollOffset = 0 - this.scrollVelocity;
                }
                if (this.buttonList.get(this.buttonList.size() - 1).getX2() - width + 20 < 0) {
                    this.scrollOffset = (this.buttonList.get(this.buttonList.size() - 1).getX2() - width + 20 - this.scrollOffset) * -1;
                }
            }
            if (this.scrollVelocity < 0) {
                ++this.scrollVelocity;
            } else if (this.scrollVelocity > 0) {
                --this.scrollVelocity;
            }
            this.drawAltButtons(mouseX, mouseY);
        }
        this.drawCenteredString(this.info, 1.2f, 0, 2);
        this.drawCenteredString("\u00a7b" + AccountManager.accountList.size() + " Alt" + (AccountManager.accountList.size() != 1 ? "s" : ""), 1.0f, 0, 14);
        this.addAltButton.draw(mouseX, mouseY);
        this.delAltButton.draw(mouseX, mouseY);
        this.editAltButton.draw(mouseX, mouseY);
        this.randomAltButton.draw(mouseX, mouseY);
        this.lastAltButton.draw(mouseX, mouseY);
        if (this.currentScreen != null) {
            this.currentScreen.draw(mouseX, mouseY);
        }
    }

    public void addAltButtons() {
        this.buttonList.clear();
        for (Alt alt : AccountManager.accountList) {
            AltButton altButton = new AltButton(alt.getUsername().length() > 0 ? alt.getUsername() : alt.getEmail(), 20, 140, 40, 66, -15921907, -16777216, alt);
            this.buttonList.add(altButton);
        }
        AccountManager.save();
    }

    public void drawAltButtons(int mouseX, int mouseY) {
        this.buttonsPerColumn = (height - 40) / 36 - 1;
        int index = 0;
        int x = 20;
        int y = 40;
        if (this.toRemove != null && AccountManager.accountList.contains(this.toRemove)) {
            AccountManager.removeAlt(this.toRemove);
            AccountManager.save();
            this.addAltButtons();
            this.toRemove = null;
        }
        for (AltButton altButton : this.buttonList) {
            Alt alt = altButton.getAlt();
            altButton.setText(alt.getUsername().length() < 1 ? alt.getEmail() : alt.getUsername());
            altButton.setX1(this.scrollOffset + x);
            altButton.setX2(this.scrollOffset + x + 120);
            altButton.setY1(y);
            altButton.setY2(y + 26);
            altButton.draw(mouseX, mouseY);
            y += 36;
            x = 20 + 130 * (int)((double)(++index / this.buttonsPerColumn) + 0.5);
            if (index % this.buttonsPerColumn != 0) continue;
            y = 40;
        }
    }

    public static AccountScreen getInstance() {
        return instance;
    }

    @Override
    public void updateScreen() {
        this.panoramaTimer += 1.0f;
        if (this.currentScreen != null) {
            this.currentScreen.update();
        }
    }

    private void drawPanorama(int p_73970_1_, int p_73970_2_, float p_73970_3_) {
        Tessellator var4 = Tessellator.getInstance();
        WorldRenderer var5 = var4.getWorldRenderer();
        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        Project.gluPerspective((float)120.0f, (float)1.0f, (float)0.05f, (float)10.0f);
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableCull();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        int var6 = 8;
        for (int var7 = 0; var7 < 64; ++var7) {
            GlStateManager.pushMatrix();
            float var8 = ((float)(var7 % 8 / 8) - 0.5f) / 64.0f;
            float var9 = ((float)(var7 / 8 / 8) - 0.5f) / 64.0f;
            float var10 = 0.0f;
            GlStateManager.translate(var8, var9, 0.0f);
            GlStateManager.rotate(MathHelper.sin((this.panoramaTimer + p_73970_3_) / 400.0f) * 25.0f + 20.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(-(this.panoramaTimer + p_73970_3_) * 0.1f, 0.0f, 1.0f, 0.0f);
            for (int var11 = 0; var11 < 6; ++var11) {
                GlStateManager.pushMatrix();
                if (var11 == 1) {
                    GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (var11 == 2) {
                    GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
                }
                if (var11 == 3) {
                    GlStateManager.rotate(-90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (var11 == 4) {
                    GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (var11 == 5) {
                    GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                }
                this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/kirka b11 account manager.png"));
                var5.startDrawingQuads();
                var5.setColorRGBA_I(16777215, 255 / (var7 + 1));
                float var12 = 0.0f;
                var5.addVertexWithUV(-1.0, -1.0, 1.0, 0.0, 0.0);
                var5.addVertexWithUV(1.0, -1.0, 1.0, 1.0, 0.0);
                var5.addVertexWithUV(1.0, 1.0, 1.0, 1.0, 1.0);
                var5.addVertexWithUV(-1.0, 1.0, 1.0, 0.0, 1.0);
                var4.draw();
                GlStateManager.popMatrix();
            }
            GlStateManager.popMatrix();
            GlStateManager.colorMask(true, true, true, false);
        }
        var5.setTranslation(0.0, 0.0, 0.0);
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
    }

    private void rotateAndBlurSkybox(float p_73968_1_) {
        this.mc.getTextureManager().bindTexture(this.panoramaLoc);
        GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        GL11.glCopyTexSubImage2D((int)3553, (int)0, (int)0, (int)0, (int)0, (int)0, (int)256, (int)256);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.colorMask(true, true, true, false);
        Tessellator var2 = Tessellator.getInstance();
        WorldRenderer var3 = var2.getWorldRenderer();
        var3.startDrawingQuads();
        GlStateManager.disableAlpha();
        int var4 = 3;
        for (int var5 = 0; var5 < 3; ++var5) {
            var3.setColorRGBA_F(1.0f, 1.0f, 1.0f, 1.0f / (float)(var5 + 1));
            int var6 = width;
            int var7 = height;
            float var8 = (float)(var5 - 1) / 256.0f;
            var3.addVertexWithUV(var6, var7, this.zLevel, 0.0f + var8, 1.0);
            var3.addVertexWithUV(var6, 0.0, this.zLevel, 1.0f + var8, 1.0);
            var3.addVertexWithUV(0.0, 0.0, this.zLevel, 1.0f + var8, 0.0);
            var3.addVertexWithUV(0.0, var7, this.zLevel, 0.0f + var8, 0.0);
        }
        var2.draw();
        GlStateManager.enableAlpha();
        GlStateManager.colorMask(true, true, true, true);
    }

    private void renderSkybox(int p_73971_1_, int p_73971_2_, float p_73971_3_) {
        this.mc.getFramebuffer().unbindFramebuffer();
        GlStateManager.viewport(0, 0, 256, 256);
        this.drawPanorama(p_73971_1_, p_73971_2_, p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        this.mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        Tessellator var4 = Tessellator.getInstance();
        WorldRenderer var5 = var4.getWorldRenderer();
        var5.startDrawingQuads();
        float var6 = width > height ? 120.0f / (float)width : 120.0f / (float)height;
        float var7 = (float)height * var6 / 256.0f;
        float var8 = (float)width * var6 / 256.0f;
        var5.setColorRGBA_F(1.0f, 1.0f, 1.0f, 1.0f);
        int var9 = width;
        int var10 = height;
        var5.addVertexWithUV(0.0, var10, this.zLevel, 0.5f - var7, 0.5f + var8);
        var5.addVertexWithUV(var9, var10, this.zLevel, 0.5f - var7, 0.5f - var8);
        var5.addVertexWithUV(var9, 0.0, this.zLevel, 0.5f + var7, 0.5f - var8);
        var5.addVertexWithUV(0.0, 0.0, this.zLevel, 0.5f + var7, 0.5f + var8);
        var4.draw();
    }

    private class AddButton
    extends Button {
        public AddButton() {
            super("Add Alt", width / 6 - 40, width / 6 + 40, height - 20 - 13, height - 20 + 13, -15921907, -16777216);
            this.setBorderColor(1280595028);
        }

        @Override
        public void onClick(int button) {
            AccountScreen.this.currentScreen = new ScreenAddAccount();
        }
    }

    private class DelButton
    extends Button {
        public DelButton() {
            super("Remove Alt", width / 6 * 3 - 40, width / 6 * 3 + 40, height - 20 - 13, height - 20 + 13, -15921907, -16777216);
            this.setBorderColor(1280595028);
        }

        @Override
        public void onClick(int button) {
            if (AccountScreen.this.selectedButton != null) {
                AccountManager.removeAlt(AccountScreen.this.selectedButton.getAlt());
                AccountManager.save();
                AccountScreen.this.initGui();
                AccountScreen.this.info = "\u00a7cRemoved Account";
            }
        }
    }

    private class EditButton
    extends Button {
        public EditButton() {
            super("Edit Alt", width / 6 * 2 - 40, width / 6 * 2 + 40, height - 20 - 13, height - 20 + 13, -15921907, -16777216);
            this.setBorderColor(1280595028);
        }

        @Override
        public void onClick(int button) {
            if (AccountScreen.this.selectedButton != null) {
                AccountScreen.this.currentScreen = new ScreenEditAccount(AccountScreen.this.selectedButton.getAlt());
            }
        }
    }

    private class LastButton
    extends Button {
        public LastButton() {
            super("Last Alt", width / 6 * 5 - 40, width / 6 * 5 + 40, height - 20 - 13, height - 20 + 13, -15921907, -16777216);
            this.setBorderColor(1280595028);
        }

        @Override
        public void onClick(int button) {
            if (AccountScreen.this.lastAlt == null) {
                AccountScreen.this.info = "\u00a7cLast Alt Empty";
            } else {
                LoginThread thread = new LoginThread(AccountScreen.this.lastAlt);
                thread.start();
            }
        }
    }

    private class RandomButton
    extends Button {
        public RandomButton() {
            super("Random Alt", width / 6 * 4 - 40, width / 6 * 4 + 40, height - 20 - 13, height - 20 + 13, -15921907, -16777216);
            this.setBorderColor(1280595028);
        }

        @Override
        public void onClick(int button) {
            if (AccountManager.accountList.size() < 1) {
                return;
            }
            Random random = new Random();
            int randomInt = AccountManager.accountList.size() == 1 ? 0 : random.nextInt(AccountManager.accountList.size() - 1);
            Alt alt = AccountManager.accountList.get(randomInt);
            LoginThread thread = new LoginThread(alt);
            thread.start();
        }
    }

}

