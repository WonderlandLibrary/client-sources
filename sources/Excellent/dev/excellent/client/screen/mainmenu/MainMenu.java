package dev.excellent.client.screen.mainmenu;

import dev.excellent.Excellent;
import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.api.interfaces.client.IMouse;
import dev.excellent.api.interfaces.shader.IShader;
import dev.excellent.client.screen.mainmenu.button.MainMenuButton;
import dev.excellent.impl.client.theme.Themes;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.font.Icon;
import dev.excellent.impl.util.animation.Animation;
import dev.excellent.impl.util.animation.Easing;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.math.ScaleMath;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import i.gishreloaded.protection.annotation.Native;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.mojang.blaze3d.matrix.MatrixStack;
import org.joml.Vector2d;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;

public class MainMenu extends Screen implements IAccess, IMouse {
    private ArrayList<MainMenuButton> buttonList;
    @Getter
    private Animation alphaAnimation;
    @Getter
    @Setter
    private boolean exit;
    @Getter
    @Setter
    private Runnable toExecute;
    private ResourceLocation logo;

    public MainMenu() {
        super(StringTextComponent.EMPTY);
        cppInit();
    }

    @Native
    private void cppInit() {
        buttonList = new ArrayList<>();
        alphaAnimation = new Animation(Easing.LINEAR, 500);
        logo = new ResourceLocation(excellent.getInfo().getNamespace(), "texture/logo.png");
    }

    @Native
    @Override
    protected void init() {
        super.init();
        float width = 80;
        float height = 15;
        float x = (float) ((scaled().x / 2F) - (width / 2F));
        float y = (float) ((scaled().y / 2F) - (60 / 2F));

        buttonList.clear();
        buttonList.add(new MainMenuButton("singleplayer", new Vector2d(x, y), new Vector2d(width, height), this, 0));
        buttonList.add(new MainMenuButton("multiplayer", new Vector2d(x, y + 20), new Vector2d(width, height), this, 1));
        buttonList.add(new MainMenuButton("manager", new Vector2d(x, y + 40), new Vector2d(width, height), this, 2));
        buttonList.add(new MainMenuButton("options", new Vector2d(x, y + 60), new Vector2d((width / 2F) - 2.5F, height), this, 3));
        buttonList.add(new MainMenuButton("exit", new Vector2d(x + (width / 2F) + 2.5F, y + 60), new Vector2d((width / 2F) - 2.5F, height), this, 4));

        for (MainMenuButton menuButton : buttonList) {
            menuButton.init();
        }
        alphaAnimation.setValue(0);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        Vector2d mouse = ScaleMath.getMouse(mouseX, mouseY);
        mouseX = (int) mouse.x;
        mouseY = (int) mouse.y;
        if (toExecute != null && alphaAnimation.isFinished() && alphaAnimation.getValue() == 0) {
            toExecute.run();
        }
        alphaAnimation.setDuration(exit ? 100 : 300);
        alphaAnimation.run(exit ? 0 : 1);

        ScaleMath.scalePre();

        IShader.MAIN_MENU.draw(mouseX, mouseY, excellent.getInitTime());

        float logoSize = 48;
        float logoX = 0;
        float logoY = 0;

        float alpha = (float) Mathf.clamp(0, 1, alphaAnimation.getValue());

        int color1 = getTheme().getClientColor(0, alpha);
        int color2 = getTheme().getClientColor(90, alpha);
        int color3 = getTheme().getClientColor(180, alpha);
        int color4 = getTheme().getClientColor(270, alpha);

        RectUtil.bindTexture(logo);
        RectUtil.drawRect(matrixStack, logoX, logoY, logoX + logoSize, logoY + logoSize, color1, color2, color3, color4, true, true);

        renderSocial(matrixStack);
        renderButtons(matrixStack, mouseX, mouseY, partialTicks);
        renderFooter(matrixStack);

        ScaleMath.scalePost();
    }


    private void renderButtons(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        float width = 100;
        float height = 115;
        float rectX = (float) ((scaled().x / 2F) - (width / 2F));
        float rectY = (float) ((scaled().y / 2F) - 60);

        float alphaPC = (int) Mathf.clamp(0, 200, alphaAnimation.getValue() * 255) / 255F;

        float btnDarkness = 0.135F;
        Themes theme = Excellent.getInst().getThemeManager().getTheme();
        int color1 = ColorUtil.multDark(theme.getClientColor(0, alphaPC), btnDarkness);
        int color2 = ColorUtil.multDark(theme.getClientColor(90, alphaPC), btnDarkness);
        int color3 = ColorUtil.multDark(theme.getClientColor(180, alphaPC), btnDarkness);
        int color4 = ColorUtil.multDark(theme.getClientColor(270, alphaPC), btnDarkness);

        RenderUtil.renderClientRect(matrixStack, rectX, rectY, width, height, false, height, (int) Mathf.clamp(0, 200, alphaAnimation.getValue() * 255));

        RectUtil.drawRoundedRectShadowed(matrixStack, rectX, rectY, rectX + width, rectY + height, 2, 5, color1, color2, color3, color4, true, true, true, true);
        RectUtil.drawRoundedRectShadowed(matrixStack, rectX, rectY, rectX + width, rectY + height, 2, 5, color1, color2, color3, color4, true, true, false, true);

        for (MainMenuButton menuButton : buttonList) {
            menuButton.render(matrixStack, mouseX, mouseY, partialTicks);
        }
    }

    private void renderSocial(MatrixStack matrixStack) {
        Font social = Fonts.SOCIAL_ICON.get(20);

        float socX = (float) (scaled().x - 10);
        float socY = (10);

        float discordWidth = social.getWidth("A");
        float telegramWidth = social.getWidth("B");
        float youtubeWidth = social.getWidth("C");

        float margin = 8;

        float socialWidth = telegramWidth + margin + youtubeWidth + margin + discordWidth + margin;

        RenderUtil.renderClientRect(matrixStack, socX - socialWidth + margin / 2F, socY - 2, socialWidth, 3 + social.getHeight(), true, 0);

        social.drawRight(matrixStack, "A", socX - telegramWidth - margin - youtubeWidth - margin, socY, ColorUtil.getColor(76, 112, 255));
        social.drawRight(matrixStack, "B", socX - youtubeWidth - margin, socY, ColorUtil.getColor(0, 187, 255));
        social.drawRight(matrixStack, "C", socX, socY, ColorUtil.getColor(250, 0, 0));
    }

    private void renderFooter(MatrixStack matrixStack) {

        double y = (scaled().y / 2F) - (50);
        String name = excellent.getInfo().getName();

        int alpha = (int) Mathf.clamp(5, 255, alphaAnimation.getValue() * 255);

        Fonts.INTER_MEDIUM.get(18).drawCenter(matrixStack, name, scaled().x / 2F, y, ColorUtil.getColor(200, 200, 200, alpha));

        int color = ColorUtil.replAlpha(Color.GRAY.brighter().hashCode(), alpha);

        Fonts.INTER_BOLD.get(12).draw(matrixStack, "Ref " + TextFormatting.WHITE + excellent.getInfo().getGitCommit(), 3, scaled().y - Fonts.INTER_BOLD.get(12).getHeight() * 2F - 2, color);
        Fonts.INTER_BOLD.get(12).draw(matrixStack, "Made by " + TextFormatting.WHITE + "Excellent Team" + TextFormatting.RESET + ", with ", 3, scaled().y - Fonts.INTER_BOLD.get(12).getHeight() - 2, color);
        Fonts.ICON.get(10).draw(matrixStack, Icon.HEART.getCharacter(), 3 + Fonts.INTER_BOLD.get(12).getWidth("Made by " + TextFormatting.WHITE + "Excellent Team, with ") + 2, scaled().y - Fonts.INTER_BOLD.get(12).getHeight() - 0.5F, ColorUtil.replAlpha(Color.RED.brighter().hashCode(), alpha));

        Fonts.INTER_BOLD.get(12).drawRight(matrixStack, "Build at " + TextFormatting.WHITE + excellent.getInfo().getBuild(), scaled().x - 3, scaled().y - Fonts.INTER_BOLD.get(12).getHeight() * 3F - 2, color);
        Fonts.INTER_BOLD.get(12).drawRight(matrixStack, "Subscription expiry " + TextFormatting.WHITE + excellent.getFormattedExpireDate(), scaled().x - 3, scaled().y - Fonts.INTER_BOLD.get(12).getHeight() * 2F - 2, color);
        Fonts.INTER_BOLD.get(12).drawRight(matrixStack, "Copyright " + TextFormatting.WHITE + "Mojang AB." + TextFormatting.RESET + " Do not distribute!", scaled().x - 3, scaled().y - Fonts.INTER_BOLD.get(12).getHeight() - 2, color);
    }

    @SneakyThrows
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Vector2d mouse = ScaleMath.getMouse(mouseX, mouseY);
        mouseX = mouse.x;
        mouseY = mouse.y;
        Font social = Fonts.SOCIAL_ICON.get(20);

        float socX = (float) (scaled().x - 10);
        float socY = 10;

        float discordWidth = social.getWidth("A");
        float telegramWidth = social.getWidth("B");
        float youtubeWidth = social.getWidth("C");

        float margin = 8;

        if (isLClick(button)) {
            // discord
            if (isHover(mouseX, mouseY, socX - telegramWidth - margin - youtubeWidth - margin - discordWidth - margin / 2F, socY - margin / 2F, discordWidth + margin, social.getHeight() + margin)) {
                Util.getOSType().openURI(new URI("https://discord.gg/EahYtazjtd"));
            }
            // telegram
            if (isHover(mouseX, mouseY, socX - youtubeWidth - margin - discordWidth - margin / 2F, socY - margin / 2F, discordWidth + margin, social.getHeight() + margin)) {
                Util.getOSType().openURI(new URI("https://t.me/excellent_client"));
            }
            // youtube
            if (isHover(mouseX, mouseY, socX - youtubeWidth - margin / 2F, socY - margin / 2F, discordWidth + margin, social.getHeight() + margin)) {
                Util.getOSType().openURI(new URI("https://www.youtube.com/@fusurt"));
            }
        }


        for (MainMenuButton menuButton : buttonList) {
            menuButton.mouseClicked(mouseX, mouseY, button);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        Vector2d mouse = ScaleMath.getMouse(mouseX, mouseY);
        mouseX = mouse.x;
        mouseY = mouse.y;
        for (MainMenuButton menuButton : buttonList) {
            menuButton.mouseReleased(mouseX, mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        for (MainMenuButton menuButton : buttonList) {
            menuButton.keyPressed(keyCode, scanCode, modifiers);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        for (MainMenuButton menuButton : buttonList) {
            menuButton.charTyped(codePoint, modifiers);
        }
        return super.charTyped(codePoint, modifiers);
    }

    @Override
    public void onClose() {
        super.onClose();
        for (MainMenuButton menuButton : buttonList) {
            menuButton.onClose();
        }
        setToExecute(null);
        exit = false;
    }

    public boolean isPauseScreen() {
        return false;
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }
}
