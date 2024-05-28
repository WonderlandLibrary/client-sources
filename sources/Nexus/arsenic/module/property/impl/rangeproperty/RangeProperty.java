package arsenic.module.property.impl.rangeproperty;

import arsenic.gui.click.impl.PropertyComponent;
import arsenic.module.property.SerializableProperty;
import arsenic.module.property.impl.DisplayMode;
import arsenic.utils.render.DrawUtils;
import arsenic.utils.render.RenderInfo;
import arsenic.utils.render.RenderUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

import java.util.function.BiConsumer;

public class RangeProperty extends SerializableProperty<RangeValue> {

    private final DisplayMode displayMode;

    public RangeProperty(String name, RangeValue value) {
        super(name, value);
        this.displayMode = DisplayMode.NORMAL;
    }

    @Override
    public JsonObject saveInfoToJson(JsonObject obj) {
        obj.add("min", new JsonPrimitive(value.getMin()));
        obj.add("max", new JsonPrimitive(value.getMax()));
        return obj;
    }

    @Override
    public void loadFromJson(@NotNull JsonObject obj) {
        value.setMaxSilently(obj.get("max").getAsDouble());
        value.setMinSilently(obj.get("min").getAsDouble());
    }

    public final @NotNull String getValueString() {
        return value.getMin() + " - " + value.getMax() + displayMode.getSuffix();
    }

    public DisplayMode getDisplayMode() { return displayMode; }

    @Override
    public PropertyComponent<RangeProperty> createComponent() {
        return new PropertyComponent<RangeProperty>(this) {
            private boolean clicked;
            private Helping helping;
            private float lineX1;
            private float lineWidth;

            @Override
            protected float draw(RenderInfo ri) {

                float percentMax = (float) ((getValue().getMax() - getValue().getMinBound()) / (getValue().getMaxBound() - getValue().getMinBound()));
                float percentMin = (float) ((getValue().getMin() - getValue().getMinBound()) / (getValue().getMaxBound() - getValue().getMinBound()));

                lineX1 = x2 - width/2f;
                float lineX2 = x2 - width / 5f;
                lineWidth = lineX2 - lineX1;
                float lineXChangePoint1 = (lineX1 + (percentMin * lineWidth));
                float lineXChangePoint2 = (lineX1 + (percentMax * lineWidth));
                float lineY = y1 + height / 2f;


                //draws value
                ri.getFr().drawString(
                        self.getValueString(),
                        x2 - ((x2 -lineX2)/2f),
                        midPointY,
                        0xFFFFFFFE,
                        ri.getFr().getScaleModifier(0.8f), ri.getFr().CENTREY
                );


                //draws first bit (uncolored) of line
                DrawUtils.drawRect(lineX1, lineY - 0.5f, lineXChangePoint1, lineY + 0.5f, getDisabledColor());

                //draws third bit (uncolored) of the line
                DrawUtils.drawRect(lineXChangePoint2, lineY - 0.5f, lineX2, lineY + 0.5f, getDisabledColor());

                //draws second bit (colored) of the line
                DrawUtils.drawRect(lineXChangePoint1, lineY - 0.5f, lineXChangePoint2, lineY + 0.5f, getEnabledColor());

                //draws the < signs
                float thickness = height/3f;
                customDraw(lineXChangePoint1, lineY, -thickness, -thickness/4f, RenderUtils.interpolateColoursInt(getDisabledColor(), getEnabledColor(), percentMin));
                customDraw(lineXChangePoint2, lineY, thickness, thickness/4f, RenderUtils.interpolateColoursInt(getDisabledColor(), getEnabledColor(), percentMax));
                return height;
            }

            @Override
            protected void click(int mouseX, int mouseY, int mouseButton) {
                clicked = true;
                float mousePercent = (mouseX - lineX1) / lineWidth;
                float minPercent = (float) ((getValue().getMax() - getValue().getMinBound()) / (getValue().getMaxBound() - getValue().getMinBound()));
                float maxPercent = (float) ((getValue().getMin() - getValue().getMinBound()) / (getValue().getMaxBound() - getValue().getMinBound()));
                helping = getValue().getMax() > getValue().getMinBound() ? (Math.abs(mousePercent - minPercent) >= Math.abs(mousePercent - maxPercent)) ? Helping.MIN : Helping.MAX : Helping.MAX;
            }

            @Override
            public void mouseUpdate(int mouseX, int mouseY) {
                if(!clicked)
                    return;
                float mousePercent = (mouseX - lineX1) / lineWidth;
                helping.setValue(self, getValue().getMinBound() + (mousePercent * (getValue().getMaxBound() - getValue().getMinBound())));
            }

            @Override
            public void mouseReleased(int mouseX, int mouseY, int state) {
                clicked = false;
            }

            //draws a <
            private void customDraw(float x1, float y1, float halfHeight, float width, int color) {
                final float finalX1 = x1 * 2;
                final float finalY1 = y1 * 2;
                final float finalHalfHeight = halfHeight * 2;
                final float finalWidth = width * 2;
                DrawUtils.drawCustom(color, () -> {
                    GL11.glVertex2d(finalX1, finalY1);
                    GL11.glVertex2d(finalX1 + finalHalfHeight, finalY1 + finalHalfHeight);
                    GL11.glVertex2d(finalX1 + finalHalfHeight, finalY1 + finalHalfHeight - finalWidth);
                    GL11.glVertex2d(finalX1 + finalWidth, finalY1);
                    GL11.glVertex2d(finalX1 + finalHalfHeight, finalY1 - finalHalfHeight + finalWidth);
                    GL11.glVertex2d(finalX1 + finalHalfHeight, finalY1 - finalHalfHeight);
                });
            }
        };
    }

    public enum Helping {
        MIN((rangeProperty, value) -> rangeProperty.getValue().setMin(value)),
        MAX((rangeProperty, value) -> rangeProperty.getValue().setMax(value));

        private final BiConsumer<RangeProperty, Double> v;

        Helping(BiConsumer<RangeProperty, Double> f) {
            v = f;
        }

        private void setValue(RangeProperty r, double value) {
            v.accept(r,value);
        }
    }
}


