package dev.excellent.client.screen.account.component;

import dev.excellent.Excellent;
import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.api.interfaces.screen.IScreen;
import dev.excellent.api.user.UsernameManager;
import dev.excellent.client.module.impl.misc.NameProtect;
import dev.excellent.client.screen.account.AltManagerScreen;
import dev.excellent.client.screen.account.api.Account;
import dev.excellent.impl.client.theme.Themes;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.math.Interpolator;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.util.Session;
import net.mojang.blaze3d.matrix.MatrixStack;
import org.joml.Vector2f;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class AccountComponent implements IAccess, IScreen {
    public final Account account;
    private final AltManagerScreen parent;
    @Setter
    private Vector2f position = new Vector2f();

    @Override
    public void init() {

    }

    private float btnDarkness = 0.15F;

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Font font = Fonts.INTER_BOLD.get(16);
        float marginX = 14;
        float margin = 4;
        float x = position.x;
        float y = position.y;

        String username = account.getUsername();

        float width = 320 - (marginX);
        float height = margin + font.getHeight() + margin;

        float radius = 1;

        boolean check = mc.session.getUsername().equals(username);
        if (NameProtect.singleton.get().isEnabled() && username.equals(mc.session.getUsername()))
            username = username.replaceAll(mc.session.getUsername(), NameProtect.singleton.get().name.getValue());

        boolean isHovered = !parent.getAccountSwitcher().isExpand() && isHover(mouseX, mouseY, x, y, width, height);

        btnDarkness = Interpolator.lerp(btnDarkness, isHovered ? 0.3F : 0.15F, 0.05);
        Themes theme = Excellent.getInst().getThemeManager().getTheme();
        int color1 = ColorUtil.multDark(theme.getClientColor(0), btnDarkness);
        int color2 = ColorUtil.multDark(theme.getClientColor(90), btnDarkness);
        int color3 = ColorUtil.multDark(theme.getClientColor(180), btnDarkness);
        int color4 = ColorUtil.multDark(theme.getClientColor(270), btnDarkness);

        RectUtil.drawRoundedRectShadowed(matrixStack, x + marginX, y, x + width, y + height, radius, 4F, color1, color2, color3, color4, true, true, true, true);
        RectUtil.drawRoundedRectShadowed(matrixStack, x + marginX, y, x + width, y + height, radius, 4F, color1, color2, color3, color4, true, true, false, true);
        font.draw(matrixStack, username, x + marginX + margin, y + (height / 2F) - (font.getHeight() / 2F), check ? ColorUtil.getColor(255, 255, 255, 255) : ColorUtil.getColor(150, 150, 150, 255));
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Font font = Fonts.INTER_BOLD.get(16);
        float margin = 4;
        float x = position.x;
        float y = position.y;

        String username = account.getUsername();

        float width = 320 - (margin + margin);
        float height = margin + font.getHeight() + margin;

        if (isHover(mouseX, mouseY, x, y, width, height) && isLClick(button)) {
            mc.session = new Session(username, String.valueOf(UUID.randomUUID()), "", "mojang");
            UsernameManager.saveUsername(mc.session.getUsername());
        }
        if (isHover(mouseX, mouseY, x, y, width, height) && isRClick(button)) {
            excellent.getAccountManager().removeAccount(username);
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        return false;
    }

    @Override
    public void onClose() {

    }
}