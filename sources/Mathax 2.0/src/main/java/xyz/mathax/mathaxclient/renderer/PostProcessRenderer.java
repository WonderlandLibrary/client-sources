package xyz.mathax.mathaxclient.renderer;

import xyz.mathax.mathaxclient.init.PreInit;
import net.minecraft.client.util.math.MatrixStack;

public class PostProcessRenderer {
    private static Mesh mesh;

    private static final MatrixStack matrixStack = new MatrixStack();

    @PreInit
    public static void init() {
        mesh = new Mesh(DrawMode.Triangles, Mesh.Attrib.Vec2);
        mesh.begin();

        int i1 = mesh.vec2(-1, -1).next();
        int i2 = mesh.vec2(-1, 1).next();
        int i3 = mesh.vec2(1, 1).next();
        int i4 = mesh.vec2(1, -1).next();
        mesh.quad(i1, i2, i3, i4);

        mesh.end();
    }

    public static void beginRender() {
        mesh.beginRender(matrixStack);
    }

    public static void render() {
        mesh.render(matrixStack);
    }

    public static void endRender() {
        mesh.endRender();
    }
}
