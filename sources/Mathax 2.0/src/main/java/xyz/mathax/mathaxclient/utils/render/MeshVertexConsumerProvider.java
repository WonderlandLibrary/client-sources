package xyz.mathax.mathaxclient.utils.render;

import xyz.mathax.mathaxclient.renderer.Mesh;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;

public class MeshVertexConsumerProvider implements IVertexConsumerProvider {
    private final MeshVertexConsumer vertexConsumer;

    public MeshVertexConsumerProvider(Mesh mesh) {
        vertexConsumer = new MeshVertexConsumer(mesh);
    }

    @Override
    public VertexConsumer getBuffer(RenderLayer layer) {
        return vertexConsumer;
    }

    public void setColor(Color color) {
        vertexConsumer.fixedColor(color.r, color.g, color.b, color.a);
    }

    @Override
    public void setOffset(double offsetX, double offsetY, double offsetZ) {
        vertexConsumer.setOffset(offsetX, offsetY, offsetZ);
    }

    private static class MeshVertexConsumer implements VertexConsumer {
        private final Mesh mesh;

        private double offsetX, offsetY, offsetZ;

        private final double[] xs = new double[4];
        private final double[] ys = new double[4];
        private final double[] zs = new double[4];
        private final Color color = new Color();

        private int i;

        public MeshVertexConsumer(Mesh mesh) {
            this.mesh = mesh;
        }

        public void setOffset(double offsetX, double offsetY, double offsetZ) {
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.offsetZ = offsetZ;
        }

        @Override
        public VertexConsumer vertex(double x, double y, double z) {
            xs[i] = offsetX + x;
            ys[i] = offsetY + y;
            zs[i] = offsetZ + z;

            return this;
        }

        @Override
        public VertexConsumer color(int red, int green, int blue, int alpha) {
            return this;
        }

        @Override
        public VertexConsumer texture(float u, float v) {
            return this;
        }

        @Override
        public VertexConsumer overlay(int u, int v) {
            return this;
        }

        @Override
        public VertexConsumer light(int u, int v) {
            return this;
        }

        @Override
        public VertexConsumer normal(float x, float y, float z) {
            return null;
        }

        @Override
        public void next() {
            if (++i >= 4) {
                int i1 = mesh.vec3(xs[0], ys[0], zs[0]).color(color).next();
                int i2 = mesh.vec3(xs[1], ys[1], zs[1]).color(color).next();
                int i3 = mesh.vec3(xs[2], ys[2], zs[2]).color(color).next();
                int i4 = mesh.vec3(xs[3], ys[3], zs[3]).color(color).next();
                mesh.quad(i1, i2, i3, i4);

                i = 0;
            }
        }

        @Override
        public void fixedColor(int red, int green, int blue, int alpha) {
            color.set(red, green, blue, alpha);
        }

        @Override
        public void unfixColor() {}
    }
}
