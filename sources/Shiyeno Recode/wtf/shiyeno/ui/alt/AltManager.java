package wtf.shiyeno.ui.alt;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import wtf.shiyeno.util.IMinecraft;
import wtf.shiyeno.util.font.Fonts;
import wtf.shiyeno.util.render.*;
import wtf.shiyeno.util.render.animation.AnimationMath;
import org.apache.commons.lang3.RandomStringUtils;
import org.joml.Vector4i;
import wtf.shiyeno.util.render.shader.ShaderUtils;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import static wtf.shiyeno.util.IMinecraft.mc;

public class AltManager extends Screen {
    public ArrayList<Account> accounts = new ArrayList();
    public float scroll;
    public float scrollAn;
    public boolean hoveredFirst;
    public boolean hoveredSecond;
    public float hoveredFirstAn;
    public float hoveredSecondAn;
    private String altName = "";
    private boolean typing;

    public AltManager() {
        super(new StringTextComponent(""));
    }

    protected void init() {
        super.init();
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return super.mouseReleased(mouseX, mouseY, button);
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 259 && !this.altName.isEmpty()) {
            this.altName = this.altName.substring(0, this.altName.length() - 1);
        }

        if (keyCode == 257) {
            if (!this.altName.isEmpty()) {
                this.accounts.add(new Account(this.altName));
            }

            this.typing = false;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public boolean charTyped(char codePoint, int modifiers) {
        String var10001 = this.altName;
        this.altName = var10001 + Character.toString(codePoint);
        return super.charTyped(codePoint, modifiers);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Vec2i fixed = ScaleMath.getMouse((int)mouseX, (int)mouseY);
        mouseX = (double)fixed.getX();
        mouseY = (double)fixed.getY();
        float x = 110.0F;
        float y = 163.5F;
        /*if (RenderUtil.isInRegion(mouseX, mouseY, 370.0F, 400.5F, 100F, 23)) {
            AltConfig.updateFile();
            this.accounts.add(new Account(RandomStringUtils.randomAlphabetic(8)));
        }*/

        /*if (RenderUtil.isInRegion(mouseX, mouseY, 480.0F, 400.5F, 100, 23.0F)) {
            if (!this.altName.isEmpty() && this.altName.length() < 20) {
                this.accounts.add(new Account(this.altName));
                AltConfig.updateFile();
            }

            this.typing = false;
        }*/

        if (RenderUtil.isInRegion(mouseX, mouseY, x + 260.0F, y + 140.0F + 30.0F, 209.5F, 23.0F)) {
            this.typing = !this.typing;
        }

        final float altX = 389.0F;
        final float altY = 149.0F;
        float iter = this.scrollAn;

        for(Iterator<Account> iterator = this.accounts.iterator(); iterator.hasNext(); ++ iter) {
            Account account = (Account)iterator.next();
            float panWidth = 98.5F;
            float acX = altX + 15.0F + iter * (panWidth + 10.0F);
            if (RenderUtil.isInRegion(mouseX, mouseY, acX - 130, 180.0F, panWidth, 50.5F)) {
                if (button == 0) {
                    IMinecraft.mc.session = new Session(account.accountName, "", "", "mojang");
                } else {
                    iterator.remove();
                    AltConfig.updateFile();
                }
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    public void init(Minecraft minecraft, int width, int height) {
        super.init(minecraft, width, height);
        this.addButton(new MainMenuScreen.Button(370, 400, 100, 23, new StringTextComponent("Рандомный ник"), onPress -> {
            AltConfig.updateFile();
            this.accounts.add(new Account(RandomStringUtils.randomAlphabetic(8)));
        }));
        this.addButton(new MainMenuScreen.Button(480, 400, 100, 23, new StringTextComponent("Добавить"), onPress -> {
            if (!this.altName.isEmpty() && this.altName.length() < 20) {
                this.accounts.add(new Account(this.altName));
                AltConfig.updateFile();
            }
            this.typing = false;
        }));
    }

    public void tick() {
        super.tick();
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        this.scroll = (float)((double)this.scroll + delta * 1.0);
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.scrollAn = AnimationMath.lerp(this.scrollAn, this.scroll, 5.0F);
        this.hoveredFirst = RenderUtil.isInRegion((double)mouseX, (double)mouseY, 380.0F, 470.5F, 124.5F, 23.0F);
        this.hoveredSecond = RenderUtil.isInRegion((double)mouseX, (double)mouseY, 490.0F, 470.5F, 124.5F, 23.0F);
        this.hoveredFirstAn = AnimationMath.lerp(this.hoveredFirstAn, this.hoveredFirst ? 1.0F : 0.0F, 10.0F);
        this.hoveredSecondAn = AnimationMath.lerp(this.hoveredSecondAn, this.hoveredSecond ? 1.0F : 0.0F, 10.0F);
        IMinecraft.mc.gameRenderer.setupOverlayRendering(2);
        float width = (float)IMinecraft.mc.getMainWindow().scaledWidth();
        float height = (float)IMinecraft.mc.getMainWindow().scaledHeight();
        final float x = 110.0F;
        final float y = 163.5F;

        float windowWidth = mc.getMainWindow().scaledWidth();
        float windowHeight = mc.getMainWindow().scaledHeight();

        ShaderUtils.MAIN_MENU.begin();
        ShaderUtils.MAIN_MENU.setUniform("time", mc.timer.getPartialTicks(1) / 20.0F);
        ShaderUtils.MAIN_MENU.setUniform("resolution", windowWidth, windowHeight);
        ShaderUtils.MAIN_MENU.setUniform("mouse", mouseX, mouseY);
        ShaderUtils.MAIN_MENU.drawQuads(0,0, mc.getMainWindow().getScaledWidth(), mc.getMainWindow().getScaledHeight());
        ShaderUtils.MAIN_MENU.end();

        RenderUtil.Render2D.drawRoundedRect(350.0F, 300.5F, 251.0F, 145.5F, 15F, RenderUtil.reAlphaInt(ColorUtil.rgba(30, 30, 30, 255), 200));
        RenderUtil.Render2D.drawRoundOutline(350.0F, 300.5F, 251.0F, 145.5F, 15F, 0.0F, ColorUtil.rgba(25, 26, 33, 0), new Vector4i(ColorUtil.rgba(255.0, 255.0, 255.0, 84.15)));
        Fonts.msBold[14].drawCenteredString(matrixStack, "Ваш текущий никнейм: " + IMinecraft.mc.getSession().getUsername(), (double)(x + 360.0F), (double)(y + 200.5F + 10.0F), ColorUtil.rgba(161, 164, 177, 255));
        Fonts.msBold[20].drawString(matrixStack, "Менеджер аккаунтов", (double)(x + 314.0F), (double)(y + 150.0F), -1);
        Fonts.msBold[14].drawString(matrixStack, "Никнейм", (double)(x + 260.0F), (double)(y + 140.0F + 22.0F), ColorUtil.rgba(161, 164, 177, 255));
        RenderUtil.Render2D.drawRoundedRect(x + 260.0F, y + 140.0F + 30.0F, 209.5F, 23.0F, 5.0F, ColorUtil.rgba(0.0, 0.0, 0.0, 56.1));
        SmartScissor.push();
        SmartScissor.setFromComponentCoordinates((double)(x - 21.0F), (double)(y + 25.0F + 30.0F), 204.5, 23.0);
        SmartScissor.unset();
        SmartScissor.pop();
        //RenderUtil.Render2D.drawRoundedRect(370.0F, 400.5F, 100F, 23.0F, 5.0F, ColorUtil.rgba(0.0, 0.0, 0.0, 56.1));
        RenderUtil.Render2D.drawRoundedRect(480.0F, 400.5F, 100, 23.0F, 5.0F, ColorUtil.rgba(0.0, 0.0, 0.0, 56.1));

        //RenderUtil.Render2D.drawRoundedOutline(480, 400.5f, 100, 23, 0, 5, ColorUtil.rgba(255, 0,0,255),
        //       ColorUtil.rgba(255,0,0,255));

        BloomHelper.registerRenderCall(() -> {
            Fonts.msBold[18].drawCenteredString(matrixStack, "Рандомный ник", 417.0F, 410.5F, ColorUtil.interpolateColor(ColorUtil.rgba(161, 164, 177, 0), -1, this.hoveredFirstAn));
            Fonts.msBold[18].drawCenteredString(matrixStack, "Войти", 530.0F, 410.5F, ColorUtil.interpolateColor(ColorUtil.rgba(161, 164, 177, 0), -1, this.hoveredSecondAn));
        });
        //Fonts.msBold[18].drawCenteredString(matrixStack, "Рандомный ник", 417.0F, 410.5F, ColorUtil.interpolateColor(ColorUtil.rgba(161, 164, 177, 255), -1, this.hoveredFirstAn));
        Fonts.msBold[18].drawCenteredString(matrixStack, "Войти", 530.0F, 410.5F, ColorUtil.interpolateColor(ColorUtil.rgba(161, 164, 177, 255), -1, this.hoveredSecondAn));
        float altX = 389.0F;
        float altY = 149.0F;
        RenderUtil.Render2D.drawRoundedRect(260.0F, 150.0F, 461.5F, 100.5F, 15F, RenderUtil.reAlphaInt(ColorUtil.rgba(30, 30, 30, 255), 200));
        RenderUtil.Render2D.drawRoundOutline(260.0F, 150.0F, 461.5F, 100.5F, 15F, 0.0F, ColorUtil.rgba(25, 26, 33, 0), new Vector4i(ColorUtil.rgba(255, 255, 255, 84.15)));
        Fonts.msBold[20].drawString(matrixStack, "Список аккаунтов", (double)(altX + 50.0F), (double)(altY + 15.0F), -1);
        Fonts.msBold[15].drawString(matrixStack, this.altName + (this.typing ? (System.currentTimeMillis() % 1000L > 500L ? "_" : "") : ""), (double)(x + 267.0F), (double)(y + 158.0F + 22.0F), ColorUtil.rgba(161, 164, 177, 255));

        super.render(matrixStack, mouseX, mouseY, partialTicks);

        float iter = this.scrollAn;
        float size = 0.0F;
        SmartScissor.push();
        SmartScissor.setFromComponentCoordinates(258.5, 149.0, 461.5, 269.5);

        for(Iterator var13 = this.accounts.iterator(); var13.hasNext(); ++size) {
            Account account = (Account)var13.next();
            final float panWidth = 100F;
            final float acX = altX + 15.0F + iter * (panWidth + 10.0F);
            final float acY = 180.0F;
            if (account.accountName.equalsIgnoreCase(IMinecraft.mc.session.getUsername())) {
                StencilUtil.initStencilToWrite();
                RenderUtil.Render2D.drawRoundedRect(acX - 125.5F, acY + 5, 37.0F, 37.0F, 7.0F, Color.BLACK.getRGB());
                StencilUtil.readStencilBuffer(1);
                IMinecraft.mc.getTextureManager().bindTexture(account.skin);
                AbstractGui.drawScaledCustomSizeModalRect(acX - 125.5F, acY + 5, 8.0F, 8.0F, 8.0F, 8.0F, 37.0F, 37.0F, 64.0F, 64.0F);
                StencilUtil.uninitStencilBuffer();
                GaussianBlur.startBlur();
                GaussianBlur.endBlur(20.0F, 1.0F);
            }
            RenderUtil.Render2D.drawRoundedRect(acX - 130, 180.0F, panWidth, 50.5F, 8.0F, ColorUtil.rgba(0.0, 0.0, 0.0, 56.1));
            SmartScissor.push();
            BloomHelper.registerRenderCall(() -> {
                SmartScissor.push();
                SmartScissor.setFromComponentCoordinates((double)(acX - 20.0F), 200, (double)(panWidth - 8.0F), 130.5);
                Fonts.msBold[14].drawCenteredString(matrixStack, account.accountName, (double)(acX - 130 + panWidth / 1.5F), 257, ColorUtil.rgba(161, 164, 177, account.accountName.equalsIgnoreCase(IMinecraft.mc.session.getUsername()) ? 255 : 0));
                SmartScissor.unset();
                SmartScissor.pop();
            });
            Fonts.msBold[16].drawCenteredString(matrixStack, account.accountName, (double)(acX - 130 + panWidth / 1.5F), 190, ColorUtil.rgba(161, 164, 177, 255));
            Date dateAdded = new Date(account.dateAdded);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(dateAdded);
            Fonts.msBold[14].drawCenteredString(matrixStack, formattedDate, (double)(acX - 130 + panWidth / 1.5F), 205, ColorUtil.rgba(100, 100, 100, 255));
            SmartScissor.unset();
            SmartScissor.pop();
            StencilUtil.initStencilToWrite();
            RenderUtil.Render2D.drawRoundedRect(acX - 125.5F, acY + 5, 37.0F, 37.0F, 7.0F, Color.BLACK.getRGB());
            StencilUtil.readStencilBuffer(1);
            IMinecraft.mc.getTextureManager().bindTexture(account.skin);
            AbstractGui.drawScaledCustomSizeModalRect(acX - 125.5F, acY + 5, 8.0F, 8.0F, 8.0F, 8.0F, 37.0F, 37.0F, 64.0F, 64.0F);
            StencilUtil.uninitStencilBuffer();
            ++iter;
        }

        this.scroll = MathHelper.clamp(this.scroll, size > 4.0F ? -size + 4.0F : 0.0F, 0.0F);
        SmartScissor.unset();
        SmartScissor.pop();
        IMinecraft.mc.gameRenderer.setupOverlayRendering();
    }
}