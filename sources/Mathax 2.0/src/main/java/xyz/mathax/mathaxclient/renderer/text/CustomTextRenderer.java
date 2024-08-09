package xyz.mathax.mathaxclient.renderer.text;

import xyz.mathax.mathaxclient.renderer.*;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.BufferUtils;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.List;

public class CustomTextRenderer implements TextRenderer {
    public static final Color SHADOW_COLOR = new Color(60, 60, 60, 180);

    private final Mesh mesh = new ShaderMesh(Shaders.TEXT, DrawMode.Triangles, Mesh.Attrib.Vec2, Mesh.Attrib.Vec2, Mesh.Attrib.Color);

    public final FontFace fontFace;

    private final Font[] fonts;
    private Font font;

    public boolean shadow;

    private boolean built;
    private boolean building;
    private boolean scaleOnly;
    private double scale = 1;

    public CustomTextRenderer(FontFace fontFace) {
        this.fontFace = fontFace;

        byte[] bytes = Utils.readBytes(fontFace.toStream());
        ByteBuffer buffer = BufferUtils.createByteBuffer(bytes.length).put(bytes);

        fonts = new Font[5];
        for (int i = 0; i < fonts.length; i++) {
            ((Buffer) buffer).flip();
            fonts[i] = new Font(buffer, (int) Math.round(18 * ((i * 0.5) + 1)));
        }
    }

    @Override
    public double getScale() {
        return scale;
    }

    @Override
    public void setScale(double scale) {
        this.scale = scale;
    }

    @Override
    public double getAlpha() {
        return mesh.alpha;
    }

    @Override
    public void setAlpha(double alpha) {
        mesh.alpha = alpha;
    }

    @Override
    public boolean getShadow() {
        return shadow;
    }

    @Override
    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }

    @Override
    public void begin(double scale, boolean scaleOnly, boolean big, boolean shadow) {
        if (building) {
            throw new RuntimeException("CustomTextRenderer.begin() called twice");
        }

        built = false;

        if (!scaleOnly) {
            mesh.begin();
        }

        if (big) {
            font = fonts[fonts.length - 1];
        } else {
            double scaleA = Math.floor(scale * 10) / 10;
            int scaleI;
            if (scaleA >= 3) {
                scaleI = 5;
            } else if (scaleA >= 2.5) {
                scaleI = 4;
            } else if (scaleA >= 2) {
                scaleI = 3;
            } else if (scaleA >= 1.5) {
                scaleI = 2;
            } else {
                scaleI = 1;
            }

            font = fonts[scaleI - 1];
        }

        building = true;
        this.scaleOnly = scaleOnly;

        double fontScale = font.getHeight() / 18.0;
        this.scale = 1 + (scale - fontScale) / fontScale;
        this.shadow = shadow;
    }

    @Override
    public double getWidth(String text, int length, boolean shadow) {
        if (text.isEmpty()) {
            return 0;
        }

        Font font = building ? this.font : fonts[0];
        return (font.getWidth(text, length) + (shadow ? 1 : 0)) * scale + (shadow ? 1 : 0);
    }

    @Override
    public double getHeight(boolean shadow) {
        Font font = building ? this.font : fonts[0];
        return (font.getHeight() + 1 + (shadow ? 1 : 0)) * scale;
    }

    @Override
    public double render(String text, double x, double y, Color color, boolean shadow) {
        boolean wasBuilding = building;
        if (!wasBuilding) {
            begin();
        }

        double width;
        if (shadow) {
            int preShadowA = SHADOW_COLOR.a;
            SHADOW_COLOR.a = (int) (color.a / 255.0 * preShadowA);

            width = font.render(mesh, text, x + 1, y + 1, SHADOW_COLOR, scale);
            font.render(mesh, text, x, y, color, scale);

            SHADOW_COLOR.a = preShadowA;
        } else {
            width = font.render(mesh, text, x, y, color, scale);
        }

        if (!wasBuilding) {
            end();
        }

        return width;
    }

    @Override
    public double render(List<Section> sections, double x, double y) {
        boolean wasBuilding = building;
        if (!wasBuilding) {
            begin();
        }

        double width = 0;
        boolean notFirst = false;
        for (Section section : sections) {
            boolean shadow;
            if (section.shadow == SectionShadow.Undefined) {
                shadow = this.shadow;
            } else {
                shadow = section.shadow == SectionShadow.Render;
            }

            if (shadow) {
                int preShadowA = SHADOW_COLOR.a;
                SHADOW_COLOR.a = (int) (section.color.a / 255.0 * preShadowA);

                font.render(mesh, section.text, x + 1, y + 1, SHADOW_COLOR, scale);
                width = font.render(mesh, section.text, (notFirst ? width : x + width) - (notFirst ? 2 * scale : 0), y, section.color, scale);

                SHADOW_COLOR.a = preShadowA;
            } else {
                width = font.render(mesh, section.text, (notFirst ? width : x + width) - (notFirst ? 2 * scale : 0), y, section.color, scale);
            }

            notFirst = true;
        }

        if (!wasBuilding) {
            end();
        }

        return width;
    }

    @Override
    public double render(String text, double x, double y, Color color) {
        return render(text, x, y, color, shadow);
    }

    @Override
    public boolean isBuilding() {
        return building;
    }

    @Override
    public boolean isBuilt() {
        return building;
    }

    @Override
    public void end(MatrixStack matrixStack) {
        if (!building) {
            throw new RuntimeException("CustomTextRenderer.end() called without calling begin()");
        }

        if (!scaleOnly) {
            mesh.end();

            GL.bindTexture(font.texture.getGlId());
            mesh.render(matrixStack);
        }

        building = false;
        built = true;
        scale = 1;
    }
}
