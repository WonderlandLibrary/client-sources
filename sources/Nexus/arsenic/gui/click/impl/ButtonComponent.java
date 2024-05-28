package arsenic.gui.click.impl;

import arsenic.gui.click.Component;
import arsenic.utils.render.DrawUtils;
import arsenic.utils.render.RenderInfo;
import arsenic.utils.render.RenderUtils;
import arsenic.utils.timer.AnimationTimer;
import arsenic.utils.timer.TickMode;

import java.awt.*;

public abstract class ButtonComponent extends Component {

    private final Component parentComponent;
    protected ButtonComponent(Component parentComponent) {
        this.parentComponent = parentComponent;
    }
    private final AnimationTimer animationTimer = new AnimationTimer(350, this::isEnabled, TickMode.SINE);
    protected abstract boolean isEnabled();
    protected abstract void setEnabled(boolean enabled);
    @Override
    protected float drawComponent(RenderInfo ri) {
        float radius = height/5f;
        float midPointY = (y2 - height/2f);
        float buttonY1 = midPointY - radius;
        float buttonY2 = midPointY + radius;
        float buttonWidth = radius * 2.5f;
        float buttonX = x2 - buttonWidth;

        float percent =  animationTimer.getPercent();
        Color color = new Color(RenderUtils.interpolateColoursInt(getDisabledColor(), getEnabledColor(), percent));
        int darkerColor = color.darker().darker().getRGB();
        int normalColour = color.getRGB();

        //oval
        DrawUtils.drawBorderedRoundedRect(x2 - buttonWidth, buttonY1, x2, buttonY2, radius * 2, radius/3f, normalColour, darkerColor);

        //circle
        float circleOffset = buttonWidth * ((percent - .5f) * 0.8f);
        DrawUtils.drawBorderedCircle(
                buttonX + buttonWidth/2f + circleOffset,
                midPointY,
                radius * 1.1f,
                radius/3f,
                normalColour,
                darkerColor
        );

        return height;
    }

    @Override
    protected void clickComponent(int mouseX, int mouseY, int mouseButton) {
        setEnabled(!isEnabled());
    }

    @Override
    public int getHeight(int i) {
        return parentComponent.getHeight(i);
    }

    @Override
    public int getWidth(int i) {
        return parentComponent.getWidth(i);
    }
}
