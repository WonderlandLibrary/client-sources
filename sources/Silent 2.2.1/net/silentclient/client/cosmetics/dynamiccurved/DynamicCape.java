package net.silentclient.client.cosmetics.dynamiccurved;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public class DynamicCape {
	private long COUNTER = 0L;
    private long lastNanoTime = 0L;
    private int vertexVbo;
    private int textureVbo;
    private FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(294);
    private FloatBuffer textureBuffer;
    private int vertexCount = 0;
    private float lastHorz = 0.0F;
    private float lastVert = 0.0F;
    private float lastAmplitude = 0.0F;
    private boolean deleted = false;

    public DynamicCape() {
        this.vertexBuffer.flip();
        this.vertexVbo = GL15.glGenBuffers();
        GL15.glBindBuffer(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, this.vertexVbo);
        GL15.glBufferData(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, (FloatBuffer) this.vertexBuffer, ARBVertexBufferObject.GL_DYNAMIC_DRAW_ARB);
        this.textureBuffer = BufferUtils.createFloatBuffer(196);
        this.textureBuffer.flip();
        this.textureVbo = GL15.glGenBuffers();
        GL15.glBindBuffer(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, this.textureVbo);
        GL15.glBufferData(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, (FloatBuffer) this.textureBuffer, ARBVertexBufferObject.GL_STATIC_DRAW_ARB);
        GL15.glBindBuffer(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, 0);
    }

    public void ticks(long i) {
        if (i - lastNanoTime > 16666666L) {
            lastNanoTime = i;
            ++COUNTER;
        }
    }

    public void update(float f, float f1, boolean flag) {
        f = f / 55.0F;
        float f2 = (float) COUNTER / 10.0F;
        float f3 = f;
        float f4 = 1.5F;

        if (!this.deleted) {
            if (flag || f != this.lastHorz || f1 != this.lastVert || this.lastAmplitude != f || f != 0.0F) {
                this.lastAmplitude = f;
                this.vertexBuffer.clear();
                this.vertexCount = 0;

                if (flag) {
                    this.textureBuffer.clear();
                }

                float f5 = 22.0F;
                float f6 = 23.0F;
                float f7 = 1.0F / f6;
                float f8 = 17.0F / f6;
                float f9 = 1.0F / f5;
                float f10 = 11.0F / f5;
                float f11 = 12.0F / f5;
                float f12 = 22.0F / f5;
                Box b = new Box(0.0F, 0.0F, 5.0F);
                Box box1 = new Box(0.0F, -16.0F, 5.0F);
                Box box2 = new Box(0.0F, -16.0F, -5.0F);
                Box box3 = new Box(0.0F, 0.0F, -5.0F);
                Box box4 = new Box(1.0F, 0.0F, 5.0F);
                Box box5 = new Box(1.0F, -16.0F, 5.0F);
                Box box6 = new Box(1.0F, -16.0F, -5.0F);
                Box box7 = new Box(1.0F, 0.0F, -5.0F);
                Box box8 = new Box(0.0F, -10.0F, -5.0F);
                Box box9 = new Box(0.0F, -10.0F, -5.0F);
                float f14 = 10.0F;
                boolean flag1 = true;
                float f15 = (float) Math.sin(Math.PI * (double) f4 + (double) f2) * f;
                float f16 = -f15;

                for (int i = 0; (float) i <= f14; ++i) {
                    float f17 = (float) i / f14;
                    float f18 = (1.0F - f17) * (1.0F - f17) * box6.a + 2.0F * (1.0F - f17) * f17 * box8.a + f17 * f17 * box7.a;
                    float f19 = (1.0F - f17) * (1.0F - f17) * box6.b + 2.0F * (1.0F - f17) * f17 * box8.b + f17 * f17 * box7.b;

                    if (flag1) {
                        f18 = (float) Math.sin((double) f17 * Math.PI * (double) f4 + (double) f2) * f3 + 1.0F + f16;
                    }

                    if (flag) {
                        this.textureBuffer.put(new float[]{f9, f8 - (f8 - f7) * f17});
                        this.textureBuffer.put(new float[]{f10, f8 - (f8 - f7) * f17});
                    }

                    this.vertexBuffer.put(new float[]{f18, f19, box4.c});
                    this.vertexBuffer.put(new float[]{f18, f19, box7.c});
                    this.vertexCount += 2;
                }

                if (flag) {
                    this.textureBuffer.put(new float[]{0.045454547F, 0.04347826F});
                    this.textureBuffer.put(new float[]{0.5F, 0.04347826F});
                    this.textureBuffer.put(new float[]{0.045454547F, 0.0F});
                    this.textureBuffer.put(new float[]{0.5F, 0.0F});
                }

                this.vertexBuffer.put(new float[]{box4.a, box4.b, box4.c});
                this.vertexBuffer.put(new float[]{box7.a, box7.b, box7.c});
                this.vertexBuffer.put(new float[]{b.a, b.b, b.c});
                this.vertexBuffer.put(new float[]{box3.a, box3.b, box3.c});
                this.vertexCount += 4;

                for (int j = 0; (float) j <= f14; ++j) {
                    float f22 = (float) j / f14;
                    float f26 = (1.0F - f22) * (1.0F - f22) * box3.b + 2.0F * (1.0F - f22) * f22 * box9.b + f22 * f22 * box2.b;
                    float f23 = (float) Math.sin((double) (1.0F - f22) * Math.PI * (double) f4 + (double) f2) * f3 + f16;

                    if (flag) {
                        this.textureBuffer.put(new float[]{f11, f7 + (f8 - f7) * f22});
                        this.textureBuffer.put(new float[]{f10, f7 + (f8 - f7) * f22});
                    }

                    this.vertexBuffer.put(new float[]{f23, f26, box7.c});
                    f23 = (1.0F - f22) * (1.0F - f22) * box7.a + 2.0F * (1.0F - f22) * f22 * box8.a + f22 * f22 * box6.a;
                    f26 = (1.0F - f22) * (1.0F - f22) * box7.b + 2.0F * (1.0F - f22) * f22 * box8.b + f22 * f22 * box6.b;

                    if (flag1) {
                        f23 = (float) Math.sin((double) (1.0F - f22) * Math.PI * (double) f4 + (double) f2) * f3 + 1.0F + f16;
                    }

                    this.vertexBuffer.put(new float[]{f23, f26, box7.c});
                    this.vertexCount += 2;
                }

                if (flag) {
                    this.textureBuffer.put(new float[]{0.95454544F, 0.0F});
                    this.textureBuffer.put(new float[]{0.5F, 0.0F});
                    this.textureBuffer.put(new float[]{0.95454544F, 0.04347826F});
                    this.textureBuffer.put(new float[]{0.5F, 0.04347826F});
                }

                float f21 = 0.0F;

                if (flag1) {
                    f21 = (float) Math.sin(0.0D * (double) f4 + (double) f2) * f3 + f16;
                }

                this.vertexBuffer.put(new float[]{box6.a + f21, box6.b, box6.c});
                this.vertexBuffer.put(new float[]{box5.a + f21, box5.b, box5.c});
                this.vertexBuffer.put(new float[]{box2.a + f21, box2.b, box2.c});
                this.vertexBuffer.put(new float[]{box1.a + f21, box1.b, box1.c});
                this.vertexCount += 4;

                for (int k = 0; (float) k <= f14; ++k) {
                    float f24 = (float) k / f14;
                    float f27 = (1.0F - f24) * (1.0F - f24) * box2.a + 2.0F * (1.0F - f24) * f24 * box9.a + f24 * f24 * box3.a;
                    float f20 = (1.0F - f24) * (1.0F - f24) * box2.b + 2.0F * (1.0F - f24) * f24 * box9.b + f24 * f24 * box3.b;

                    if (flag1) {
                        f27 = (float) Math.sin((double) f24 * Math.PI * (double) f4 + (double) f2) * f3 + f16;
                    }

                    if (flag) {
                        this.textureBuffer.put(new float[]{f11, f8 - (f8 - f7) * f24});
                        this.textureBuffer.put(new float[]{f12, f8 - (f8 - f7) * f24});
                    }

                    this.vertexBuffer.put(new float[]{f27, f20, box7.c});
                    this.vertexBuffer.put(new float[]{f27, f20, box4.c});
                    this.vertexCount += 2;
                }

                for (int l = 0; (float) l <= f14; ++l) {
                    float f25 = (float) l / f14;

                    if (l == 0) {
                        float f28 = (1.0F - f25) * (1.0F - f25) * box3.a + 2.0F * (1.0F - f25) * f25 * box9.a + f25 * f25 * box2.a;
                        float f30 = (1.0F - f25) * (1.0F - f25) * box3.b + 2.0F * (1.0F - f25) * f25 * box9.b + f25 * f25 * box2.b;

                        if (flag) {
                            this.textureBuffer.put(new float[]{0.0F, f7 + (f8 - f7) * f25});
                            this.textureBuffer.put(new float[]{0.0F, f7 + (f8 - f7) * f25});
                        }

                        this.vertexBuffer.put(new float[]{f28, f30, box4.c});
                        this.vertexBuffer.put(new float[]{f28, f30, box4.c});
                        this.vertexCount += 2;
                    }

                    float f29 = (float) Math.sin((double) (1.0F - f25) * Math.PI * (double) f4 + (double) f2) * f3 + 1.0F + f16;
                    float f31 = (1.0F - f25) * (1.0F - f25) * box7.b + 2.0F * (1.0F - f25) * f25 * box8.b + f25 * f25 * box6.b;

                    if (flag) {
                        this.textureBuffer.put(new float[]{f9, f7 + (f8 - f7) * f25});
                        this.textureBuffer.put(new float[]{0.0F, f7 + (f8 - f7) * f25});
                    }

                    this.vertexBuffer.put(new float[]{f29, f31, box4.c});
                    f29 = (1.0F - f25) * (1.0F - f25) * box3.a + 2.0F * (1.0F - f25) * f25 * box9.a + f25 * f25 * box2.a;
                    f31 = (1.0F - f25) * (1.0F - f25) * box3.b + 2.0F * (1.0F - f25) * f25 * box9.b + f25 * f25 * box2.b;

                    if (flag1) {
                        f29 = (float) Math.sin((double) (1.0F - f25) * Math.PI * (double) f4 + (double) f2) * f3 + f16;
                    }

                    this.vertexBuffer.put(new float[]{f29, f31, box4.c});
                    this.vertexCount += 2;
                }

                this.vertexBuffer.flip();
                GL15.glBindBuffer(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, this.vertexVbo);
                GL15.glBufferData(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, (FloatBuffer) this.vertexBuffer, ARBVertexBufferObject.GL_DYNAMIC_DRAW_ARB);

                if (flag) {
                    this.textureBuffer.flip();
                    GL15.glBindBuffer(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, this.textureVbo);
                    GL15.glBufferData(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, (FloatBuffer) this.textureBuffer, ARBVertexBufferObject.GL_STATIC_DRAW_ARB);
                }

                GL15.glBindBuffer(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, 0);
                this.lastHorz = f;
                this.lastVert = f1;
            }
        }
    }

    public void renderDynamicCape() {
        if (!this.deleted) {

            GL15.glBindBuffer(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, this.vertexVbo);
            GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0L);
            GL15.glBindBuffer(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, this.textureVbo);
            GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0L);
            GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
            GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, this.vertexCount);
            GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
            GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
            GL15.glBindBuffer(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, 0);

        }
    }

    public void deleteData() {
        this.deleted = true;
        GL15.glDeleteBuffers(this.vertexVbo);
        GL15.glDeleteBuffers(this.textureVbo);
    }
}
