package xyz.mathax.mathaxclient.gui.renderer;

import xyz.mathax.mathaxclient.utils.gui.Cell;
import xyz.mathax.mathaxclient.gui.widgets.WWidget;
import xyz.mathax.mathaxclient.gui.widgets.containers.WContainer;
import xyz.mathax.mathaxclient.renderer.DrawMode;
import xyz.mathax.mathaxclient.renderer.Mesh;
import xyz.mathax.mathaxclient.renderer.ShaderMesh;
import xyz.mathax.mathaxclient.renderer.Shaders;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import net.minecraft.client.util.math.MatrixStack;

public class GuiDebugRenderer {
    private final Mesh mesh = new ShaderMesh(Shaders.POS_COLOR, DrawMode.Lines, Mesh.Attrib.Vec2, Mesh.Attrib.Color);

    private static final Color CELL_COLOR = new Color(25, 225, 25);
    private static final Color WIDGET_COLOR = new Color(25, 25, 225);

    public void render(WWidget widget, MatrixStack matrixStack) {
        if (widget == null) {
            return;
        }

        mesh.begin();

        renderWidget(widget);

        mesh.end();
        mesh.render(matrixStack);
    }

    private void renderWidget(WWidget widget) {
        lineBox(widget.x, widget.y, widget.width, widget.height, WIDGET_COLOR);

        if (widget instanceof WContainer) {
            for (Cell<?> cell : ((WContainer) widget).cells) {
                lineBox(cell.x, cell.y, cell.width, cell.height, CELL_COLOR);
                renderWidget(cell.widget());
            }
        }
    }

    public void lineBox(double x, double y, double width, double height, Color color) {
        line(x, y, x + width, y, color);
        line(x + width, y, x + width, y + height, color);
        line(x, y, x, y + height, color);
        line(x, y + height, x + width, y + height, color);
    }

    private void line(double x1, double y1, double x2, double y2, Color color) {
        int i1 = mesh.vec2(x1, y1).color(color).next();
        int i2 = mesh.vec2(x2, y2).color(color).next();
        mesh.line(i1, i2);
    }
}
