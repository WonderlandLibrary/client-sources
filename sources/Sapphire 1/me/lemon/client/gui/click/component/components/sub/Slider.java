package me.lemon.client.gui.click.component.components.sub;

import me.lemon.client.gui.click.component.Component;
import me.lemon.client.gui.click.component.components.Button;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Slider extends Component {

    private final Consumer<Number> val;
    private final Button parent;
    String name;
    Supplier<? extends Number> value2;
    Number min;
    Number max;
    private boolean hovered;
    private int offset;
    private int x;
    private int y;
    private boolean dragging = false;
    private double renderWidth;

    public Slider(Consumer<Number> value, Supplier<? extends Number> value2, String name, Number min, Number max, Button button, int offset) {
        this.val = value;
        this.value2 = value2;
        this.name = name;
        this.min = min;
        this.max = max;

        this.parent = button;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
    }

    private static double roundToPlace(double value, int places, RoundingMode mode) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, mode);
        return bd.doubleValue();
    }

    @Override
    public void renderComponent() {
        Color toggledHovered = new Color(23, 23, 23, 125);

        Color toggledNoHover = new Color(14, 14, 14, 125);

        Gui.drawRect(parent.parent.getX() + 2, parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset + 12, this.hovered ? toggledHovered.getRGB() : toggledNoHover.getRGB());
        Gui.drawRect(parent.parent.getX() + 2, parent.parent.getY() + offset, parent.parent.getX() + (int) renderWidth, parent.parent.getY() + offset + 12, hovered ? 0xFF555555 : 0xFF444444);
        Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + 2, parent.parent.getY() + offset + 12, new Color(0, 0, 0, 125).getRGB());
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(name + ": " + value2.get(), (parent.parent.getX() * 2 + 15), (parent.parent.getY() + offset + 2) * 2 + 5, -1);

        GL11.glPopMatrix();
    }

    @Override
    public void setOff(int newOff) {
        offset = newOff;
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {
        this.hovered = isMouseOnButtonD(mouseX, mouseY) || isMouseOnButtonI(mouseX, mouseY);
        this.y = parent.parent.getY() + offset;
        this.x = parent.parent.getX();

        double diff = Math.min(88, Math.max(0, mouseX - this.x));

        Number min = this.min;
        Number max = this.max;
        Number value = value2.get();

        renderWidth = (88) * (value.doubleValue() - min.doubleValue()) / (max.doubleValue() - min.doubleValue());

        if (dragging) {
            if (diff == 0) {
                val.accept(min);
            } else {
                double newValue = roundToPlace(((diff / 88) * (max.doubleValue() - min.doubleValue()) + min.doubleValue()), 2, RoundingMode.HALF_UP);
                val.accept(newValue);
            }
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButtonD(mouseX, mouseY) && button == 0 && this.parent.open) {
            dragging = true;
        }
        if (isMouseOnButtonI(mouseX, mouseY) && button == 0 && this.parent.open) {
            dragging = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        dragging = false;
    }

    public boolean isMouseOnButtonD(int x, int y) {
        return x > this.x && x < this.x + (parent.parent.getWidth() / 2 + 1) && y > this.y && y < this.y + 12;
    }

    public boolean isMouseOnButtonI(int x, int y) {
        return x > this.x + parent.parent.getWidth() / 2 && x < this.x + parent.parent.getWidth() && y > this.y && y < this.y + 12;
    }
}
