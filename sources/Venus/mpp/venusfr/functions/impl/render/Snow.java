/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.ModeSetting;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

@FunctionRegister(name="Snow", type=Category.Visual)
public class Snow
extends Function {
    private final ModeSetting mode = new ModeSetting("Mode", "SnowFlake", "SnowFlake", "Stars", "Hearts", "Dollars", "Bloom", "Potion");
    private final SliderSetting count = new SliderSetting("Count", 100.0f, 20.0f, 800.0f, 1.0f);
    private final SliderSetting size = new SliderSetting("Size", 1.0f, 0.1f, 6.0f, 0.1f);
    private static final ArrayList<ParticleBase> particles = new ArrayList();

    public Snow() {
        this.addSettings(this.mode, this.count, this.size);
    }

    @Subscribe
    public void onUpdate(EventUpdate eventUpdate) {
        particles.removeIf(ParticleBase::tick);
        int n = particles.size();
        while ((float)n < ((Float)this.count.get()).floatValue()) {
            particles.add(new ParticleBase(this, (float)(Snow.mc.player.getPosX() + (double)MathUtil.random(-48.0f, 48.0f)), (float)(Snow.mc.player.getPosY() + (double)MathUtil.random(2.0f, 48.0f)), (float)(Snow.mc.player.getPosZ() + (double)MathUtil.random(-48.0f, 48.0f)), MathUtil.random(-0.4f, 0.4f), MathUtil.random(-0.1f, 0.1f), MathUtil.random(-0.4f, 0.4f)));
            ++n;
        }
    }

    public static void onPreRender3D(MatrixStack matrixStack) {
        matrixStack.push();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(false);
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR_TEX_LIGHTMAP);
        particles.forEach(arg_0 -> Snow.lambda$onPreRender3D$0(bufferBuilder, arg_0));
        bufferBuilder.finishDrawing();
        WorldVertexBufferUploader.draw(bufferBuilder);
        RenderSystem.depthMask(true);
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
        matrixStack.pop();
    }

    private static void lambda$onPreRender3D$0(BufferBuilder bufferBuilder, ParticleBase particleBase) {
        particleBase.render(bufferBuilder);
    }

    public class ParticleBase {
        protected float prevposX;
        protected float prevposY;
        protected float prevposZ;
        protected float posX;
        protected float posY;
        protected float posZ;
        protected float motionX;
        protected float motionY;
        protected float motionZ;
        protected int age;
        protected int maxAge;
        final Snow this$0;

        public ParticleBase(Snow snow, float f, float f2, float f3, float f4, float f5, float f6) {
            this.this$0 = snow;
            this.posX = f;
            this.posY = f2;
            this.posZ = f3;
            this.prevposX = f;
            this.prevposY = f2;
            this.prevposZ = f3;
            this.motionX = f4;
            this.motionY = f5;
            this.motionZ = f6;
            this.maxAge = this.age = (int)MathUtil.random(100.0f, 300.0f);
        }

        public boolean tick() {
            this.age = IMinecraft.mc.player.getDistanceSq(this.posX, this.posY, this.posZ) > 4096.0 ? (this.age -= 8) : --this.age;
            if (this.age < 0) {
                return false;
            }
            this.prevposX = this.posX;
            this.prevposY = this.posY;
            this.prevposZ = this.posZ;
            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            this.motionX *= 0.9f;
            this.motionY *= 0.9f;
            this.motionZ *= 0.9f;
            this.motionY -= 0.001f;
            return true;
        }

        public void render(BufferBuilder bufferBuilder) {
            if (this.this$0.mode.is("Bloom")) {
                IMinecraft.mc.getTextureManager().bindTexture(new ResourceLocation("venusfr/images/firefly.png"));
            } else if (this.this$0.mode.is("SnowFlake")) {
                IMinecraft.mc.getTextureManager().bindTexture(new ResourceLocation("venusfr/images/snowflake.png"));
            } else if (this.this$0.mode.is("Dollars")) {
                IMinecraft.mc.getTextureManager().bindTexture(new ResourceLocation("venusfr/images/dollar.png"));
            } else if (this.this$0.mode.is("Hearts")) {
                IMinecraft.mc.getTextureManager().bindTexture(new ResourceLocation("venusfr/images/heart.png"));
            } else if (this.this$0.mode.is("Stars")) {
                IMinecraft.mc.getTextureManager().bindTexture(new ResourceLocation("venusfr/images/star.png"));
            } else if (this.this$0.mode.is("Potion")) {
                IMinecraft.mc.getTextureManager().bindTexture(new ResourceLocation("venusfr/images/potion.png"));
            }
            ActiveRenderInfo activeRenderInfo = IMinecraft.mc.gameRenderer.getActiveRenderInfo();
            int n = ColorUtils.getColor(this.age * 2);
            Vector3d vector3d = DisplayUtils.interpolatePos(this.prevposX, this.prevposY, this.prevposZ, this.posX, this.posY, this.posZ);
            MatrixStack matrixStack = new MatrixStack();
            matrixStack.rotate(Vector3f.XP.rotationDegrees(activeRenderInfo.getPitch()));
            matrixStack.rotate(Vector3f.YP.rotationDegrees(activeRenderInfo.getYaw() + 180.0f));
            matrixStack.translate(vector3d.x, vector3d.y, vector3d.z);
            matrixStack.rotate(Vector3f.YP.rotationDegrees(-activeRenderInfo.getYaw()));
            matrixStack.rotate(Vector3f.XP.rotationDegrees(activeRenderInfo.getPitch()));
            Matrix4f matrix4f = matrixStack.getLast().getMatrix();
            bufferBuilder.pos(matrix4f, 0.0f, -((Float)this.this$0.size.get()).floatValue(), 0.0f).color(n).tex(0.0f, 0.99f).lightmap(0, 240).endVertex();
            bufferBuilder.pos(matrix4f, -((Float)this.this$0.size.get()).floatValue(), -((Float)this.this$0.size.get()).floatValue(), 0.0f).color(n).tex(1.0f, 0.99f).lightmap(0, 240).endVertex();
            bufferBuilder.pos(matrix4f, -((Float)this.this$0.size.get()).floatValue(), 0.0f, 0.0f).color(n).tex(1.0f, 0.0f).lightmap(0, 240).endVertex();
            bufferBuilder.pos(matrix4f, 0.0f, 0.0f, 0.0f).color(n).tex(0.0f, 0.0f).lightmap(0, 240).endVertex();
        }
    }
}

