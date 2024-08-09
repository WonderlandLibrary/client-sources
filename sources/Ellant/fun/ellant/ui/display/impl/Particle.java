package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class Particle {
    private float x;
    private float y;
    private float velocityX;
    private float velocityY;
    private float scale;
    private float alpha;
    private boolean isSliding = false;
    private final ResourceLocation texture;
    private final int lifetime;
    private int age;
    private int fadeColor;

    public Particle(float x, float y, float velocityX, float velocityY, float scale, ResourceLocation texture, int lifetime, int fadeColor) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.scale = scale;
        this.texture = texture;
        this.lifetime = lifetime;
        this.alpha = 1.0F;
        this.age = 0;
        this.fadeColor = fadeColor;
    }

    public void setFadeColor(int color) {
        this.fadeColor = color;
    }
    public void setSliding(boolean bl) {
        this.isSliding = bl;
    }
    public boolean isAlive() {
        return this.age < this.lifetime;
    }

    public void update() {
        if (this.isSliding) {
            this.x += this.velocityX;
            this.y += this.velocityY;
        }
            ++this.age;
            this.alpha = Math.max(0.0F, 1.0F - (float) this.age / (float) this.lifetime);
        }

        public void render () {
            if (this.isAlive()) {
                GlStateManager.pushMatrix();
                GlStateManager.translatef(this.x, this.y, 0.0F);
                GlStateManager.scalef(this.scale, this.scale, this.scale);
                Minecraft.getInstance().getTextureManager().bindTexture(this.texture);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, this.alpha);
                this.renderTexturedQuad(0, 0, 16, 16);
                GlStateManager.popMatrix();
            }
        }

        private void renderTexturedQuad ( int x, int y, int width, int height) {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();
            buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            buffer.pos((double) x, (double) (y + height), 0.0D).tex(0.0F, 1.0F).endVertex();
            buffer.pos((double) (x + width), (double) (y + height), 0.0D).tex(1.0F, 1.0F).endVertex();
            buffer.pos((double) (x + width), (double) y, 0.0D).tex(1.0F, 0.0F).endVertex();
            buffer.pos((double) x, (double) y, 0.0D).tex(0.0F, 0.0F).endVertex();
            tessellator.draw();
        }
    }