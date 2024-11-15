// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.function.Supplier;

public final class Font {
    private final java.awt.Font field608;
    private final boolean field609;
    private final boolean field610;
    private final HashMap field611;
    private int field606;
    private int field607;
    private BufferedImage field612;
    private AbstractTexture field613;

    public Font(final java.awt.Font font, final boolean antiAliasing, final boolean fractionalMetrics) {
        this.field607 = -1;
        this.field611 = new HashMap();
        this.field608 = font;
        this.field609 = antiAliasing;
        this.field610 = fractionalMetrics;
    }

    public void method553(final char[] chars) {
        double width = -1.0;
        double height = -1.0;
        final FontRenderContext frc = new FontRenderContext(new AffineTransform(), this.field609, this.field610);
        for (int length = chars.length, i = 0; i < length; ++i) {
            final Rectangle2D stringBounds = this.field608.getStringBounds(Character.toString(chars[i]), frc);
            if (width < stringBounds.getWidth()) {
                width = stringBounds.getWidth();
            }
            if (height < stringBounds.getHeight()) {
                height = stringBounds.getHeight();
            }
        }
        final double a = width + 2.0;
        final double b = height + 2.0;
        this.field606 = (int) Math.ceil(Math.max(Math.ceil(Math.sqrt(a * a * chars.length) / a), Math.ceil(Math.sqrt(b * b * chars.length) / b)) * Math.max(a, b)) + 1;
        this.field612 = new BufferedImage(this.field606, this.field606, 2);
        final Graphics2D graphics = this.field612.createGraphics();
        graphics.setFont(this.field608);
        graphics.setColor(new Color(255, 255, 255, 0));
        graphics.fillRect(0, 0, this.field606, this.field606);
        graphics.setColor(Color.white);
        graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, this.field610 ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, this.field609 ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, this.field609 ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        final FontMetrics fontMetrics = graphics.getFontMetrics();
        int field513 = 0;
        int field514 = 0;
        int field515 = 1;
        for (final char c : chars) {
            final Rectangle value = new Rectangle();
            final Rectangle2D stringBounds2 = fontMetrics.getStringBounds(Character.toString(c), graphics);
            value.width = stringBounds2.getBounds().width + 8;
            value.height = stringBounds2.getBounds().height;
            if (field514 + value.width >= this.field606) {
                field514 = 0;
                field515 += field513;
                field513 = 0;
            }
            value.x = field514;
            value.y = field515;
            if (value.height > this.field607) {
                this.field607 = value.height;
            }
            if (value.height > field513) {
                field513 = value.height;
            }
            graphics.drawString(Character.toString(c), field514 + 2, field515 + fontMetrics.getAscent());
            field514 += value.width;
            this.field611.put(c, value);
        }
    }

    public void method554() throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(this.field612, ".ttf", output);
        final byte[] byteArray = output.toByteArray();
        final ByteBuffer put = BufferUtils.createByteBuffer(byteArray.length).put(byteArray);
        put.flip();
        this.field613 = new NativeImageBackedTexture(NativeImage.read(put));
    }

    public void method555() {
        RenderSystem.setShaderTexture(0, this.field613.getGlId());
    }

    public void method556() {
        RenderSystem.setShaderTexture(0, 0);
    }

    public float method557(final MatrixStack stack, final char ch, final float x, final float y, final float red, final float blue, final float green, final float alpha) {
        final int n = 0;
        final Rectangle class111 = (Rectangle) this.field611.get(ch);
        final int n2 = n;
        final Rectangle class112 = class111;
        if (n2 == 0 && class112 == null) {
            return 0.0f;
        }
        final float n3 = class112.x / (float) this.field606;
        final float n4 = class111.y / (float) this.field606;
        final float n5 = class111.width / (float) this.field606;
        final float n6 = class111.height / (float) this.field606;
        final float n7 = (float) class111.width;
        final float n8 = (float) class111.height;
        final BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        RenderSystem.setShader((Supplier) GameRenderer::getPositionColorTexProgram);
        this.method555();
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
        buffer.vertex(stack.peek().getPositionMatrix(), x, y + n8, 0.0f).color(red, green, blue, alpha).texture(n3, n4 + n6).next();
        buffer.vertex(stack.peek().getPositionMatrix(), x + n7, y + n8, 0.0f).color(red, green, blue, alpha).texture(n3 + n5, n4 + n6).next();
        buffer.vertex(stack.peek().getPositionMatrix(), x + n7, y, 0.0f).color(red, green, blue, alpha).texture(n3 + n5, n4).next();
        buffer.vertex(stack.peek().getPositionMatrix(), x, y, 0.0f).color(red, green, blue, alpha).texture(n3, n4).next();
        BufferRenderer.drawWithGlobalProgram(buffer.end());
        this.method556();
        return n7 - 8.0f;
    }

    public float method558(final char ch) {
        return (float) ((Rectangle) this.field611.get(ch)).width;
    }

    public boolean method559() {
        return this.field609;
    }

    public boolean method560() {
        return this.field610;
    }

    public int method561() {
        return this.field607;
    }
}
