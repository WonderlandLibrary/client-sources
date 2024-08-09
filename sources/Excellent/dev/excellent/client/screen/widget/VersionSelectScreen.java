package dev.excellent.client.screen.widget;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import dev.excellent.Excellent;
import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.api.interfaces.client.IMouse;
import dev.excellent.api.interfaces.screen.IScreen;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.font.Icon;
import dev.excellent.impl.util.animation.Animation;
import dev.excellent.impl.util.animation.Easing;
import dev.excellent.impl.util.math.ScaleMath;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.util.render.ScrollUtil;
import dev.excellent.impl.util.render.StencilBuffer;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.luvbeeq.via.ViaLoadingBase;
import lombok.Data;
import net.minecraft.client.gui.screen.MultiplayerScreen;
import net.mojang.blaze3d.matrix.MatrixStack;
import org.joml.Vector2d;
import org.joml.Vector2f;

@Data
public class VersionSelectScreen implements IAccess, IScreen, IMouse {
    private Vector2f position;
    private final ScrollUtil scroll = new ScrollUtil();
    private boolean isExpand;
    private Animation rotateAnimation = new Animation(Easing.LINEAR, 150);
    private Animation expandAnimation = new Animation(Easing.LINEAR, 150);
    private Animation alphaAnimation = new Animation(Easing.LINEAR, 150);
    private Font bold = Fonts.INTER_BOLD.get(15);
    private Font regular = Fonts.INTER_MEDIUM.get(15);

    public VersionSelectScreen(Vector2f position) {
        this.position = position;
    }

    @Override
    public void init() {
        isExpand = false;
        float margin = bold.getHeight() * 2F;
        expandAnimation.setValue(margin);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Vector2d mouse = ScaleMath.getMouse(mouseX, mouseY);
        mouseX = (int) mouse.x;
        mouseY = (int) mouse.y;
        ScaleMath.scalePre();
        if (mc.currentScreen instanceof MultiplayerScreen) {
            float x = position.x;
            float y = position.y;

            float margin = bold.getHeight() * 2F;
            float width = 90;
            expandAnimation.setDuration(150);
            expandAnimation.run(isExpand ? 160 : margin);
            float height = (float) expandAnimation.getValue();

            scroll.update();
            scroll.setEnabled(isHover(mouseX, mouseY, x, y + margin, width, height - margin));

            float fontX = x + 5;
            float fontY = y + (margin / 2F) - (bold.getHeight() / 2F);

            int alpha = 255;

            RenderUtil.renderClientRect(matrixStack, x, y, width, isExpand ? height + 2 : height, height > margin, margin, alpha);
            bold.drawCenter(matrixStack, "Выберите версию", x + width / 2F, fontY, ColorUtil.getColor(255, 255, 255, alpha));

            float padding = 8;

            float scrollX = x + width - 2;
            float scrollY = y + ((margin) + 2) + padding / 2F;

            if (isExpand && expandAnimation.isFinished() || !isExpand && !expandAnimation.isFinished() || isExpand) {
                RectUtil.drawRect(matrixStack, scrollX - 0.25F, scrollY, scrollX - 0.25F + 1, scrollY + height - ((margin) + 2) - padding / 2F, ColorUtil.getColor(40, 40, 40, alpha));
                scroll.render(matrixStack, new Vector2f(scrollX, scrollY), height - ((margin) + 2) - padding / 2F, alpha);
            }

            StencilBuffer.init();
            RectUtil.drawRect(matrixStack, x, y + margin, x + width, y + margin + height - margin, ColorUtil.getColor(40, 40, 40, alpha));
            StencilBuffer.read(StencilBuffer.Action.OUTSIDE.getAction());

            float positionY = (float) (y + (margin) + padding + scroll.getScroll());
            int i = 0;
            for (ProtocolVersion protocol : Excellent.getInst().getViaMCP().getPROTOCOLS()) {
                boolean selected = ViaLoadingBase.getInstance().getTargetVersion().getVersion() == protocol.getVersion();
                int protocolColor = selected ? ColorUtil.replAlpha(getTheme().getClientColor().hashCode(), 120) : ColorUtil.getColor(100, 100, 100, 30);

                RectUtil.drawRoundedRectShadowed(matrixStack, x + 2, positionY - (regular.getHeight() / 2F) + 2, x + 2 + width - 6, positionY - (regular.getHeight() / 2F) + 2 + (regular.getHeight() * 2) - 4, 1, selected ? 2 : 1, protocolColor, protocolColor, protocolColor, protocolColor, true, true, true, true);

                if (selected) {
                    Fonts.ICON.get(12).drawRight(matrixStack, Icon.CHECKMARK.getCharacter(), x + width - 8, positionY + 1, ColorUtil.getColor(255, 255, 255, alpha));
                }
                regular.drawCenter(matrixStack, protocol.getName(), x + width / 2F, positionY, ColorUtil.getColor(255, 255, 255, alpha));
                positionY += 20;
                i += 20;
            }
            scroll.setMax(height - i - ((padding * 2) + (padding / 2F)));
            StencilBuffer.cleanup();

        }
        ScaleMath.scalePost();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Vector2d mouse = ScaleMath.getMouse(mouseX, mouseY);
        mouseX = mouse.x;
        mouseY = mouse.y;

        selectVersion(mouseX, mouseY, button);
        return false;
    }

    private void selectVersion(double mouseX, double mouseY, int button) {
        Vector2d mouse = ScaleMath.getMouse(mouseX, mouseY);
        mouseX = mouse.x;
        mouseY = mouse.y;

        double x = position.x;
        double y = position.y;

        float margin = bold.getHeight() * 2F;

        float height = (float) expandAnimation.getValue();
        float width = 100;
        if (isHover(mouseX, mouseY, x, y, width, margin) && (isLClick(button) || isRClick(button))) {
            isExpand = !isExpand;
        } else if (isHover(mouseX, mouseY, x, y + margin + (2.5 + 2), width, height - margin - (2.5 + 2))) {
            double fontX = x + 5;
            double padding = 8;
            double positionY = y + ((bold.getHeight() * 2)) + padding + scroll.getScroll();

            for (ProtocolVersion protocol : Excellent.getInst().getViaMCP().getPROTOCOLS()) {
                if (isHover(mouseX, mouseY, fontX, positionY - regular.getHeight() / 2F, width - 10, regular.getHeight() * 2) && isLClick(button)) {
                    ViaLoadingBase.getInstance().reload(ViaLoadingBase.fromProtocolId(protocol.getVersion()));
                }
                positionY += 20;
            }
        } else {
            isExpand = false;
        }
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
        isExpand = false;
    }
}
