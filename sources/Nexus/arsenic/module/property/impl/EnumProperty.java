package arsenic.module.property.impl;

import arsenic.gui.click.impl.PropertyComponent;
import arsenic.main.Nexus;
import arsenic.module.property.IReliable;
import arsenic.module.property.SerializableProperty;
import arsenic.utils.interfaces.IAlwaysClickable;
import arsenic.utils.render.DrawUtils;
import arsenic.utils.render.RenderInfo;
import arsenic.utils.render.ScissorUtils;
import arsenic.utils.timer.AnimationTimer;
import arsenic.utils.timer.TickMode;
import com.google.gson.JsonObject;

import java.util.function.Supplier;

public class EnumProperty<T extends Enum<?>> extends SerializableProperty<T> implements IReliable {

    private T[] modes;

    @SuppressWarnings("unchecked")
    public EnumProperty(String name, T value) {
        super(name, value);
        try {
            this.modes = (T[]) value.getClass().getMethod("values").invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public JsonObject saveInfoToJson(JsonObject obj) {
        obj.addProperty("mode", value.toString());
        return obj;
    }

    @Override
    public void loadFromJson(JsonObject obj) {
        String mode = obj.get("mode").getAsString();
        for (T opt : modes)
            if (opt.toString().equals(mode))
                setValueSilently(opt);
    }

    public void nextMode() {
        value = modes[(value.ordinal() + 1) % modes.length];
    }

    public void prevMode() {
        value = modes[(value.ordinal() == 0 ? modes.length : value.ordinal()) - 1];
    }

    @Override
    public Supplier<Boolean> valueCheck(String value) {
        return () -> value.equals(this.value.name()) && isVisible();
    }

    @Override
    public PropertyComponent<EnumProperty<?>> createComponent() {
        return new EnumComponent(this);
    }

    private class EnumComponent extends PropertyComponent<EnumProperty<?>> implements IAlwaysClickable {
        private boolean open;
        private final AnimationTimer animationTimer = new AnimationTimer(350, () -> open, TickMode.SINE);
        private float boxY1;
        private float boxX1;
        private float boxHeight;
        public EnumComponent(EnumProperty<?> p) {
            super(p);
        }

        @Override
        protected float draw(RenderInfo ri) {
            boxX1 = x2 - width/3f;
            float borderWidth = height/15f;
            boxY1 = midPointY - height/3f;
            float boxY2 = midPointY + height/3f;
            boxHeight = boxY2 - boxY1;
            float maxBoxHeight = animationTimer.getPercent() * ((modes.length)  * boxHeight);

            Runnable render = () -> {
                //box
                DrawUtils.drawBorderedRoundedRect(
                        boxX1,
                        boxY1,
                        x2,
                        boxY2 + maxBoxHeight,
                        boxHeight / 2f,
                        borderWidth,
                        getEnabledColor(),
                        getDisabledColor()
                );

                //Other value that aren't selected
                if (animationTimer.getPercent() > 0) {
                    DrawUtils.drawRect(boxX1, boxY2, x2, boxY2 + 1, getEnabledColor());

                    ScissorUtils.subScissor((int) boxX1, (int) boxY2, (int) x2, (int) (boxY2 + maxBoxHeight), 2);

                    for (int i = 0; i < modes.length; i++) {
                        T m = modes[i];
                        ri.getFr().drawString(m.name(), boxX1 + (borderWidth * 2), midPointY + ((i + 1) * boxHeight), 0xFFFFFFFE, ri.getFr().CENTREY);
                    }

                    ScissorUtils.endSubScissor();
                }

                //name in box
                ri.getFr().drawString(getValue().name(), boxX1 + (borderWidth * 2), midPointY, 0xFFFFFFFE, ri.getFr().CENTREY);

                //triangle in box
                float triangleLength = (boxHeight - (borderWidth * 2f));
                DrawUtils.drawTriangle(
                        x2 - boxHeight - (borderWidth * 2),
                        boxY1 + (borderWidth * 2) + ((boxHeight - (borderWidth * 4)) * animationTimer.getPercent()),
                        triangleLength,
                        (-(animationTimer.getPercent() - .5f) * 2) * triangleLength,
                        getEnabledColor()
                );

            };
            //so that it draws over the other properties
            if(animationTimer.getPercent() > 0) {
                Nexus.getNexus().getClickGuiScreen().addToRenderLastList(render);
            } else {
                render.run();
            }

            return height;
        }

        @Override
        protected void click(int mouseX, int mouseY, int mouseButton) {
            open = !open;
            Nexus.getNexus().getClickGuiScreen().setAlwaysClickedComponent(open ? this : null);
        }

        @Override
        public boolean clickAlwaysClickable(int mouseX, int mouseY, int mouseButton) {
            if(mouseX > x2 || mouseX < boxX1)
                return false;
            float mouseOffset = mouseY - boxY1;
            if(mouseOffset < 0 || mouseOffset > ((modes.length + 1) * boxHeight))
                return false;
            int box = (int) (mouseOffset/boxHeight);
            if(box == 0) {
                click(mouseX, mouseY, mouseButton);
                return true;
            }
            setValue(modes[box - 1]);
            open = !open;
            Nexus.getNexus().getClickGuiScreen().setAlwaysClickedComponent(null);
            return true;
        }

        @Override
        public void setNotAlwaysClickable() {
            open = false;
        }
    }
}