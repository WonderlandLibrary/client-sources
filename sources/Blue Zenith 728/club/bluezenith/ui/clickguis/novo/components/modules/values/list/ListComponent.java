package club.bluezenith.ui.clickguis.novo.components.modules.values.list;

import club.bluezenith.module.value.types.ListValue;
import club.bluezenith.ui.clickguis.novo.components.Component;
import club.bluezenith.ui.clickgui.ClickGui;
import club.bluezenith.util.font.FontUtil;
import club.bluezenith.util.font.TFontRenderer;
import net.minecraft.client.gui.ScaledResolution;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.stream.Collectors.toList;
import static net.minecraft.util.EnumChatFormatting.GRAY;

public class ListComponent extends Component {
    private static final TFontRenderer font = FontUtil.createFont("helvetica", 34);

    public final ListValue parent;
    private final List<ListChildComponent> children;

    public boolean expanded;

    public ListComponent(ListValue parent, float x, float y) {
        super(x, y);
        this.parent = parent;

        final AtomicReference<Float> height = new AtomicReference<>(y);

        this.children = parent.getOptions()
                .stream()
                .map(string -> new ListChildComponent(
                                    parent,
                                    font,
                                    string,
                                 x + 2,
                                    height.getAndSet(5F)
                )).collect(toList());
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public boolean shouldUpdateWidth() {
        return true;
    }

    @Override
    public float getHeight() {
        if(this.expanded) {
            float height = this.height;

            for (ListChildComponent child : this.children) {
                height += child.getHeight();
            }

            return height;
        }
        return height - 2;
    }

    @Override
    public void draw(int mouseX, int mouseY, ScaledResolution scaledResolution) {
        font.drawString(GRAY + parent.name + (expanded ? "" : " ..."), x + 3, y + 2, -1, true);

        if(this.expanded) {
            float y = this.y + 17;
            for (ListChildComponent child : this.children) {
                child.moveTo(x, y);
                child.draw(mouseX, mouseY, scaledResolution);
                y += child.getHeight();
            }
        }
        //RenderUtil.rect(x + 3, y, x + width, y + height - 2, -1);
    }

    @Override
    public void moveTo(float x, float y) {
        super.moveTo(x, y);
        this.children.forEach(child -> child.moveTo(x, y));
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(ClickGui.i(mouseX, mouseY, x + 3, y + 2, x + width, y + font.getHeight(parent.name) + 3)) {
            this.expanded = !this.expanded;
        } else if(expanded) for (ListChildComponent child : this.children) {
            if(child.isMouseOver(mouseX, mouseY))
                child.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }


    @Override
    public void setWidth(float width) {
        super.setWidth(width);
        this.children.forEach(child -> child.setWidth(width));
    }

    @Override
    public void setHeight(float height) {
        super.setHeight(height);
        this.children.forEach(child -> child.setHeight(height - 2));
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    @Override
    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {

    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return ClickGui.i(mouseX, mouseY, x, y, x + width, y + getHeight() - 2);
    }
}
