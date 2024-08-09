package dev.excellent.client.screen.account;

import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.api.interfaces.client.IMouse;
import dev.excellent.api.interfaces.shader.IShader;
import dev.excellent.client.module.impl.misc.NameProtect;
import dev.excellent.client.screen.account.api.Account;
import dev.excellent.client.screen.account.component.AccountComponent;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.math.ScaleMath;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.util.render.ScissorUtil;
import dev.excellent.impl.util.render.ScrollUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import lombok.Getter;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.mojang.blaze3d.matrix.MatrixStack;
import org.joml.Vector2d;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AltManagerScreen extends Screen implements IAccess, IMouse, IShader {
    public AltManagerScreen() {
        super(StringTextComponent.EMPTY);
        for (Account account : excellent.getAccountManager()) {
            accounts.add(new AccountComponent(account, this));
        }
    }

    private final ScrollUtil scroll = new ScrollUtil();
    @Getter
    private final AccountSwitchWidget accountSwitcher = new AccountSwitchWidget(this, new Vector2f(5, 5));
    @Getter
    private final List<AccountComponent> accounts = new ArrayList<>();
    private final ResourceLocation logo = new ResourceLocation(excellent.getInfo().getNamespace(), "texture/logo.png");

    @Override
    protected void init() {
        super.init();
        accountSwitcher.init();
        for (AccountComponent account : accounts) {
            account.init();
        }
    }


    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        Vector2d mouse = ScaleMath.getMouse(mouseX, mouseY);
        mouseX = (int) mouse.x;
        mouseY = (int) mouse.y;
        ScaleMath.scalePre();
        MAIN_MENU.draw(mouseX, mouseY, excellent.getInitTime());

        float logoSize = 48;
        float logoX = 0;
        float logoY = 0;

        float alpha = 1F;

        int color1 = getTheme().getClientColor(0, alpha);
        int color2 = getTheme().getClientColor(90, alpha);
        int color3 = getTheme().getClientColor(180, alpha);
        int color4 = getTheme().getClientColor(270, alpha);
        RectUtil.bindTexture(logo);
        RectUtil.drawRect(matrixStack, logoX, logoY, logoX + logoSize, logoY + logoSize, color1, color2, color3, color4, true, true);

        if (accounts.size() != excellent.getAccountManager().size()) {
            accounts.clear();
            for (Account account : excellent.getAccountManager()) {
                accounts.add(new AccountComponent(account, this));
            }
        }

        float x, y, width, height;
        width = 330;
        height = 290;
        x = (float) ((scaled().x / 2F) - (width / 2F));
        y = (float) ((scaled().y / 2F) - (height / 2F));

        RenderUtil.renderClientRect(matrixStack, x, y, width, height, false, height);

        String username = mc.session.getUsername();
        if (NameProtect.singleton.get().isEnabled() && username.equals(mc.session.getUsername()))
            username = username.replaceAll(mc.session.getUsername(), NameProtect.singleton.get().name.getValue());

        Fonts.INTER_BOLD.get(24).drawCenter(matrixStack, "Выберите аккаунт", scaled().x / 2F, y + 24, -1);
        Fonts.INTER_REGULAR.get(16).drawCenter(matrixStack, "Вы авторизированы как " + TextFormatting.WHITE + "'" + username + "'", scaled().x / 2F, y + 24 + 20, ColorUtil.getColor(128, 128, 128));

        width = 320;
        x = (float) ((scaled().x / 2F) - (width / 2F));

        y = y + 64 + Fonts.INTER_MEDIUM.get(16).getHeight();

        Font font = Fonts.INTER_BOLD.get(16);

        float margin = 4;

        ScissorUtil.enable();
        ScissorUtil.scissor(mc.getMainWindow(), x, y + (margin / 2F), width, (height - (16 + Fonts.INTER_MEDIUM.get(16).getHeight() + 48 * 2F)) - margin);

        scroll.setEnabled(true);
        scroll.update();

        if (accounts.isEmpty()) {
            Fonts.INTER_BOLD.get(24).drawCenter(matrixStack, "Список аккаунтов пуст.", x + width / 2F, y + margin, ColorUtil.getColor(128, 128, 128, 200));
        }

        float i = 0;
        for (AccountComponent accountComponent : accounts) {
            accountComponent.getPosition().set(x, y + scroll.getScroll() + margin + i);
            accountComponent.render(matrixStack, mouseX, mouseY, partialTicks);
            i += margin + font.getHeight() + margin + 6;
        }

        scroll.setMax(i, (height - (16 + Fonts.INTER_MEDIUM.get(16).getHeight() + 48 * 2F)) - (margin * 2) + 2);

        ScissorUtil.disable();

        accountSwitcher.getPosition().set(x + width / 2F, y + (height - (16 + Fonts.INTER_MEDIUM.get(16).getHeight() + 48 * 2F)) + 16);
        accountSwitcher.render(matrixStack, mouseX, mouseY, partialTicks);
        ScaleMath.scalePost();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Vector2d mouse = ScaleMath.getMouse(mouseX, mouseY);
        mouseX = mouse.x;
        mouseY = mouse.y;
        accountSwitcher.mouseClicked(mouseX, mouseY, button);
        if (accountSwitcher.isExpand()) return false;

        height = 290;
        width = 320;
        float y = (float) ((scaled().y / 2F) - (height / 2F));
        float x = (float) ((scaled().x / 2F) - (width / 2F));

        y = y + 64 + Fonts.INTER_MEDIUM.get(16).getHeight();
        if (isHover(mouseX, mouseY, x, y, width, (height - (16 + Fonts.INTER_MEDIUM.get(16).getHeight() + 48 * 2F)))) {
            for (AccountComponent accountComponent : accounts) {
                accountComponent.mouseClicked(mouseX, mouseY, button);
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        Vector2d mouse = ScaleMath.getMouse(mouseX, mouseY);
        mouseX = mouse.x;
        mouseY = mouse.y;
        accountSwitcher.mouseReleased(mouseX, mouseY, button);
        if (accountSwitcher.isExpand()) return false;
        for (AccountComponent accountComponent : accounts) {
            accountComponent.mouseReleased(mouseX, mouseY, button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        accountSwitcher.keyPressed(keyCode, scanCode, modifiers);
        if (accountSwitcher.isExpand()) return false;
        for (AccountComponent accountComponent : accounts) {
            accountComponent.keyPressed(keyCode, scanCode, modifiers);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private boolean isAllowedCharacter(char codePoint) {
        String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
                + "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя"
                + "0123456789_";
        return String.valueOf(codePoint).matches("[" + Pattern.quote(allowedCharacters) + "]");
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (isAllowedCharacter(codePoint)) {
            accountSwitcher.charTyped(codePoint, modifiers);
        }
        if (accountSwitcher.isExpand()) return false;
        for (AccountComponent accountComponent : accounts) {
            accountComponent.charTyped(codePoint, modifiers);
        }
        return super.charTyped(codePoint, modifiers);
    }

    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        super.onClose();
        accountSwitcher.onClose();
        for (AccountComponent accountComponent : accounts) {
            accountComponent.onClose();
        }
    }
}
