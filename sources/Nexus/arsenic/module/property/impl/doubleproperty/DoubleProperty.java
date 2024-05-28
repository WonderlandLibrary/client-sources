package arsenic.module.property.impl.doubleproperty;

import arsenic.gui.click.impl.PropertyComponent;
import arsenic.module.property.SerializableProperty;
import arsenic.module.property.impl.DisplayMode;
import arsenic.utils.render.DrawUtils;
import arsenic.utils.render.RenderInfo;
import arsenic.utils.render.RenderUtils;
import arsenic.utils.timer.AnimationTimer;
import arsenic.utils.timer.TickMode;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class DoubleProperty extends SerializableProperty<DoubleValue> {

    private final DisplayMode displayMode;

    public DoubleProperty(String name, DoubleValue value) {
        super(name, value);
        this.displayMode = DisplayMode.NORMAL;
    }

    @Override
    public JsonObject saveInfoToJson(@NotNull JsonObject obj) {
        obj.add("value", new JsonPrimitive(value.getInput()));
        return obj;
    }

    @Override
    public void loadFromJson(@NotNull JsonObject obj) {
        value.setInput(obj.get("value").getAsDouble());
    }

    public final @NotNull String getValueString() { return value.getInput() + getDisplayMode().getSuffix(); }

    public DisplayMode getDisplayMode() { return displayMode; }

    @Override
    public PropertyComponent<DoubleProperty> createComponent() {
        return new PropertyComponent<DoubleProperty>(this) {

            private boolean hovered;
            private float lineWidth, lineX1, radius, lineXChangePoint;
            private boolean clicked;
            private final AnimationTimer animationTimer = new AnimationTimer(120, () -> hovered || clicked, TickMode.ROOT);

            @Override
            protected float draw(RenderInfo ri) {

                float percent = (float) ((getValue().getInput() - getValue().getMinBound())/(getValue().getMaxBound() - getValue().getMinBound()));

                //draws lines
                lineX1 = x2 - width/2f;
                float lineX2 = x2 - width / 5f;
                lineWidth = lineX2 - lineX1;
                lineXChangePoint = (lineX1 + (percent * lineWidth));

                //draws value
                ri.getFr().drawString(
                        self.getValueString(),
                        x2 - ((x2 -lineX2)/2f),
                        midPointY,
                        0xFFFFFFFE, ri.getFr().CENTREX, ri.getFr().CENTREY);

                //draws first bit (colored) of line
                DrawUtils.drawRect(lineX1, midPointY - 0.5f, lineXChangePoint, midPointY + 0.5f, getEnabledColor());

                //draws second bit (uncolored) of the line
                DrawUtils.drawRect(lineXChangePoint, midPointY - 0.5f, lineX2, midPointY + 0.5f, getDisabledColor());

                //draws the circle
                radius = height/5f;
                Color color = new Color(RenderUtils.interpolateColoursInt(getDisabledColor(), getEnabledColor(), percent));
                DrawUtils.drawBorderedCircle(lineXChangePoint, midPointY, radius, radius/3f, color.darker().getRGB(), color.getRGB());
                if(animationTimer.getPercent() > 0) {
                    DrawUtils.drawCircleOutline(lineXChangePoint, midPointY, radius * animationTimer.getPercent(), radius/3f, 0xFFFFFFFE);
                }
                return height;
            }

            @Override
            protected void click(int mouseX, int mouseY, int mouseButton) {
                clicked = true;
            }

            @Override
            public void mouseReleased(int mouseX, int mouseY, int state) {
                clicked = false;
            }

            @Override
            public void mouseUpdate(int mouseX, int mouseY) {
                handleMovement(mouseX, mouseY);
                handleHover(mouseX, mouseY);
            }

            private void handleHover(int mouseX, int mouseY) {
                double xDiff = Math.pow((lineXChangePoint - mouseX), 2);
                double yDiff = Math.pow((midPointY - mouseY), 2);
                double tDiff = Math.sqrt(xDiff + yDiff);
                hovered = tDiff < radius;
            }

            private void handleMovement(int mouseX, int mouseY) {
                if(!clicked)
                    return;
                float mousePercent = (mouseX - lineX1) / lineWidth;
                getValue().setInput(getValue().getMinBound() + (mousePercent * (getValue().getMaxBound() - getValue().getMinBound())));
            }
        };
    }

}
