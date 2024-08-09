package dev.excellent.client.screen.clickgui.component.value;

import dev.excellent.api.interfaces.client.IMouse;
import dev.excellent.api.interfaces.client.IRenderAccess;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.animation.Animation;
import dev.excellent.impl.util.animation.Easing;
import dev.excellent.impl.value.Value;
import lombok.Getter;
import lombok.Setter;
import net.mojang.blaze3d.matrix.MatrixStack;
import org.joml.Vector2f;

@Getter
public abstract class ValueComponent implements IRenderAccess, IMouse {
    public final Font font16 = Fonts.INTER_SEMIBOLD.get(16);
    public final Font font14 = Fonts.INTER_SEMIBOLD.get(14);
    public final Font font12 = Fonts.INTER_SEMIBOLD.get(12);
    public final Font icon = Fonts.ICON.get(10);
    public float width;
    public float height = 0;
    @SuppressWarnings("FieldMayBeFinal")
    public boolean hovered = false;

    @Setter
    public Vector2f position;
    public Value<?> value;
    public final Animation alphaAnimation = new Animation(Easing.LINEAR, 300);
    public final Animation animation = new Animation(Easing.LINEAR, 100);

    public ValueComponent(final Value<?> value, float width) {
        this.value = value;
        this.width = width;
    }

    public boolean isHover(int mouseX, int mouseY) {
        return isHover(mouseX, mouseY, position.x, position.y, width + 10, height);
    }

    public abstract void init();

    public abstract void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks);

    public abstract void mouseClicked(double mouseX, double mouseY, int button);

    public abstract void mouseReleased(double mouseX, double mouseY, int button);

    public abstract void keyPressed(int keyCode, int scanCode, int modifiers);

    public abstract void charTyped(char codePoint, int modifiers);

    public abstract void onClose();
}