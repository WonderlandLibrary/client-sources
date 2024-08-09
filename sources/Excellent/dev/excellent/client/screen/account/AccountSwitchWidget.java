package dev.excellent.client.screen.account;

import dev.excellent.Excellent;
import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.api.interfaces.client.IMouse;
import dev.excellent.api.interfaces.screen.IScreen;
import dev.excellent.api.user.UsernameManager;
import dev.excellent.client.screen.account.api.Account;
import dev.excellent.client.screen.account.component.AccountComponent;
import dev.excellent.impl.client.theme.Themes;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.animation.Animation;
import dev.excellent.impl.util.animation.Easing;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.render.GLUtils;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.render.text.TextAlign;
import dev.excellent.impl.util.render.text.TextBox;
import dev.excellent.impl.util.render.text.TextUtils;
import lombok.Data;
import net.minecraft.util.Session;
import net.mojang.blaze3d.matrix.MatrixStack;
import org.apache.commons.lang3.RandomStringUtils;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;
import java.util.UUID;

@Data
public class AccountSwitchWidget implements IAccess, IMouse, IScreen {
    private final AltManagerScreen parent;
    private Vector2f position;
    private boolean expand;
    private final Animation alphaAnimation = new Animation(Easing.EASE_OUT_QUART, 100);
    private final Animation borderAnimation = new Animation(Easing.EASE_OUT_QUART, 500);
    private final Animation scaleAnimation = new Animation(Easing.EASE_OUT_QUART, 200);

    private final TextBox textbox = new TextBox(new Vector2f(), Fonts.INTER_MEDIUM.get(14), ColorUtil.getColor(255, 255, 255), TextAlign.LEFT, "Введите никнейм..", 100);

    public AccountSwitchWidget(AltManagerScreen parent, Vector2f position) {
        this.parent = parent;
        this.position = position;
    }

    @Override
    public void init() {
        expand = false;
        borderAnimation.setValue(180);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        float margin = 8;
        float iwidth = margin + Fonts.INTER_BOLD.get(14).getWidth("Добавить аккаунт") + margin;


        boolean isHovered = isHover(mouseX, mouseY, position.x - iwidth / 2F, position.y, iwidth, 16);

        scaleAnimation.run(isExpand() ? 1 : 0.5F);
        alphaAnimation.run(isExpand() ? 255 : 0);
        borderAnimation.run(!isExpand() && isHovered ? 255 : 180);
        int alpha = (int) alphaAnimation.getValue();

        RectUtil.drawRect(matrixStack, (float) 0, (float) 0, (float) scaled().x, (float) scaled().y, ColorUtil.getColor(0, (int) Mathf.clamp(0, 255, alpha / 2F)));

        float btnDarkness = 0.15F;
        Themes theme = Excellent.getInst().getThemeManager().getTheme();
        int color1 = ColorUtil.multDark(theme.getClientColor(0), btnDarkness);
        int color2 = ColorUtil.multDark(theme.getClientColor(90), btnDarkness);
        int color3 = ColorUtil.multDark(theme.getClientColor(180), btnDarkness);
        int color4 = ColorUtil.multDark(theme.getClientColor(270), btnDarkness);

        RectUtil.drawRoundedRectShadowed(matrixStack, position.x - iwidth / 2F, position.y, position.x - iwidth / 2 + iwidth, position.y + 16, 3, 4, color1, color2, color3, color4, true, true, true, true);
        RectUtil.drawRoundedRectShadowed(matrixStack, position.x - iwidth / 2F, position.y, position.x - iwidth / 2 + iwidth, position.y + 16, 3, 4, color1, color2, color3, color4, true, true, false, true);
        Fonts.INTER_BOLD.get(14).drawCenter(matrixStack, "Добавить аккаунт", position.x, position.y + (16 / 2F) - (Fonts.INTER_BOLD.get(14).getHeight() / 2F), ColorUtil.getColor(255, 255, 255, (int) borderAnimation.getValue()));


        float width = 140;
        float height = 60;
        float x = (float) ((scaled().x / 2F) - (width / 2F));
        float y = (float) ((scaled().y / 2F) - 32);

        GLUtils.scaleStart(x + width / 2F, y + height / 2F, (float) scaleAnimation.getValue());
        if (scaleAnimation.getValue() > 0.75) {

//            int color = ColorUtil.getColor(10, 10, 10, (int) Mathf.clamp(0, 255, alphaAnimation.getValue() / 3F));
            RectUtil.drawRoundedRectShadowed(matrixStack, x, y, x + width, y + height, 4, 5, color1, color2, color3, color4, true, true, true, true);
            RectUtil.drawRoundedRectShadowed(matrixStack, x, y, x + width, y + height, 4, 5, color1, color2, color3, color4, true, true, false, true);

            Fonts.INTER_BOLD.get(16).drawCenter(matrixStack, "Добавление аккаунта ", x + width / 2F, y + 8, ColorUtil.getColor(255, 255, 255, alpha));

            RectUtil.drawRoundedRectShadowed(matrixStack, x + 10, y + 24, x + 10 + width - 20, y + 24 + 15, 2, 5F, color1, color2, color3, color4, true, true, true, true);
            RectUtil.drawRoundedRectShadowed(matrixStack, x + 10, y + 24, x + 10 + width - 20, y + 24 + 15, 2, 5F, color1, color2, color3, color4, true, true, false, true);

            this.textbox.setText(TextUtils.removeForbiddenCharacters(textbox.getText(), TextUtils.ALLOWED_TO_SESSION));
            this.textbox.setPosition(new Vector2f(x + 15, y + 24 + 3.5F));
            this.textbox.setWidth(width - 30);
            this.textbox.draw(matrixStack);

            String button = textbox.isEmpty() ? "Рандом" : "Логин";

            RectUtil.drawRoundedRectShadowed(matrixStack, x + (width / 2F) - (Fonts.INTER_BOLD.get(14).getWidth(button) / 2F) - 4, y + 44, x + (width / 2F) - (Fonts.INTER_BOLD.get(14).getWidth(button) / 2F) - 4 + Fonts.INTER_BOLD.get(14).getWidth(button) + 8, y + 44 + 12, 2, 2, color1, color2, color3, color4, true, true, true, true);
            RectUtil.drawRoundedRectShadowed(matrixStack, x + (width / 2F) - (Fonts.INTER_BOLD.get(14).getWidth(button) / 2F) - 4, y + 44, x + (width / 2F) - (Fonts.INTER_BOLD.get(14).getWidth(button) / 2F) - 4 + Fonts.INTER_BOLD.get(14).getWidth(button) + 8, y + 44 + 12, 2, 2, color1, color2, color3, color4, true, true, false, true);
            Fonts.INTER_BOLD.get(14).drawCenter(matrixStack, button, x + width / 2F, y + 44 + (12 / 2F) - (Fonts.INTER_BOLD.get(14).getHeight() / 2F), ColorUtil.getColor(255, 255, 255, alpha));

        }
        GLUtils.scaleEnd();

    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        float margin = 16;
        float iwidth = margin + Fonts.INTER_BOLD.get(14).getWidth("Добавить аккаунт") + margin;
        if (isHover(mouseX, mouseY, position.x - iwidth / 2F, position.y, iwidth, 16) && isLClick(button)) {
            setExpand(!isExpand());
            textbox.setText("");
        }

        float width = 140;
        float height = 60;
        float x = (float) ((scaled().x / 2F) - (width / 2F));
        float y = (float) ((scaled().y / 2F) - 32);

        String btn = textbox.isEmpty() ? "Рандом" : "Логин";

        if (isExpand() && isLClick(button))
            textbox.mouse(mouseX, mouseY, button);
        if (isExpand() && isHover(mouseX, mouseY, x + (width / 2F) - (Fonts.INTER_BOLD.get(14).getWidth(btn) / 2F) - 4, y + 44, Fonts.INTER_BOLD.get(14).getWidth(btn) + 8, 12) && isLClick(button)) {
            String usr;
            if (textbox.isEmpty()) {
                usr = RandomStringUtils.randomAlphanumeric((int) Mathf.getRandom(4, 12));
            } else {
                usr = textbox.getText();
                setExpand(false);
            }
            mc.session = new Session(usr, String.valueOf(UUID.randomUUID()), "", "mojang");
            excellent.getAccountManager().addAccount(usr);
            UsernameManager.saveUsername(mc.session.getUsername());
            parent.getAccounts().clear();
            for (Account account : excellent.getAccountManager()) {
                parent.getAccounts().add(new AccountComponent(account, parent));
            }
        }
        if (!(isHover(mouseX, mouseY, x, y, width, height)) && isLClick(button) && !(isHover(mouseX, mouseY, position.x - iwidth / 2F, position.y, iwidth, 16))) {
            setExpand(false);
            textbox.setText("");
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }


    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        textbox.keyPressed(keyCode);

        if (isExpand() && keyCode == GLFW.GLFW_KEY_ENTER && !textbox.getText().isEmpty() && !Objects.equals(mc.session.getProfile().getName(), textbox.getText())) {
            mc.session = new Session(textbox.getText(), String.valueOf(UUID.randomUUID()), "", "mojang");
            excellent.getAccountManager().addAccount(textbox.getText());
            UsernameManager.saveUsername(mc.session.getUsername());
            parent.getAccounts().clear();
            for (Account account : excellent.getAccountManager()) {
                parent.getAccounts().add(new AccountComponent(account, parent));
            }
            textbox.setText("");
            setExpand(false);
        }

        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            setExpand(false);
        }
        return false;
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        textbox.charTyped(codePoint);
        return false;
    }

    @Override
    public void onClose() {
        textbox.selected = false;
        setExpand(false);
    }
}
