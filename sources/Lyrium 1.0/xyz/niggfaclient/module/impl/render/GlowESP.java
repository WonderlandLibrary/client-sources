// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.render;

import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import xyz.niggfaclient.utils.Utils;
import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL20;
import xyz.niggfaclient.utils.other.MathUtils;
import org.lwjgl.BufferUtils;
import java.awt.Color;
import org.lwjgl.opengl.GL13;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.GlStateManager;
import xyz.niggfaclient.utils.render.RenderUtils;
import xyz.niggfaclient.utils.other.Printer;
import optifine.Config;
import java.util.ArrayList;
import xyz.niggfaclient.events.impl.Render2DEvent;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.Render3DEvent;
import xyz.niggfaclient.eventbus.Listener;
import net.minecraft.entity.Entity;
import java.util.List;
import net.minecraft.client.shader.Framebuffer;
import xyz.niggfaclient.utils.shader.ShaderUtil;
import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "GlowESP", description = "Renders a glow around players through walls", cat = Category.RENDER)
public class GlowESP extends Module
{
    private final DoubleProperty radius;
    private final DoubleProperty exposure;
    private final Property<Boolean> separateTexture;
    private final ShaderUtil outlineShader;
    private final ShaderUtil glowShader;
    public Framebuffer framebuffer;
    public Framebuffer outlineFrameBuffer;
    public Framebuffer glowFrameBuffer;
    private final List<Entity> entities;
    @EventLink
    private final Listener<Render3DEvent> render3DEventListener;
    @EventLink
    private final Listener<Render2DEvent> render2DEventListener;
    
    public GlowESP() {
        this.radius = new DoubleProperty("Radius", 5.0, 2.0, 30.0, 2.0);
        this.exposure = new DoubleProperty("Exposure", 2.0, 0.5, 4.0, 0.1);
        this.separateTexture = new Property<Boolean>("Separate Texture", false);
        this.outlineShader = new ShaderUtil("client/shaders/outline.frag");
        this.glowShader = new ShaderUtil("client/shaders/glow.frag");
        this.entities = new ArrayList<Entity>();
        this.render3DEventListener = (e -> {
            if (Config.isShaders()) {
                Printer.addMessage("Turn off shaders to prevent crashes!");
                this.toggle();
            }
            this.framebuffer = RenderUtils.createFrameBuffer(this.framebuffer);
            this.outlineFrameBuffer = RenderUtils.createFrameBuffer(this.outlineFrameBuffer);
            this.glowFrameBuffer = RenderUtils.createFrameBuffer(this.glowFrameBuffer);
            this.collectEntities();
            this.framebuffer.framebufferClear();
            this.framebuffer.bindFramebuffer(true);
            this.entities.forEach(entity -> this.mc.getRenderManager().renderEntityStaticNoShadow(entity, e.getPartialTicks(), false));
            this.framebuffer.unbindFramebuffer();
            this.mc.getFramebuffer().bindFramebuffer(true);
            GlStateManager.disableLighting();
            return;
        });
        this.render2DEventListener = (e -> {
            if (Config.isShaders()) {
                Printer.addMessage("Turn off shaders to prevent crashes!");
                this.toggle();
            }
            if (this.framebuffer != null && this.outlineFrameBuffer != null && this.entities.size() > 0) {
                GlStateManager.enableAlpha();
                GlStateManager.alphaFunc(516, 0.0f);
                GlStateManager.enableBlend();
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                this.outlineFrameBuffer.framebufferClear();
                this.outlineFrameBuffer.bindFramebuffer(true);
                this.outlineShader.init();
                this.setupOutlineUniforms(0.0f, 1.0f);
                RenderUtils.bindTexture(this.framebuffer.framebufferTexture);
                ShaderUtil.drawQuads();
                this.outlineShader.init();
                this.setupOutlineUniforms(1.0f, 0.0f);
                RenderUtils.bindTexture(this.framebuffer.framebufferTexture);
                ShaderUtil.drawQuads();
                this.outlineShader.unload();
                this.outlineFrameBuffer.unbindFramebuffer();
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                this.glowFrameBuffer.framebufferClear();
                this.glowFrameBuffer.bindFramebuffer(true);
                this.glowShader.init();
                this.setupGlowUniforms(1.0f, 0.0f);
                RenderUtils.bindTexture(this.outlineFrameBuffer.framebufferTexture);
                ShaderUtil.drawQuads();
                this.glowShader.unload();
                this.glowFrameBuffer.unbindFramebuffer();
                this.mc.getFramebuffer().bindFramebuffer(true);
                this.glowShader.init();
                this.setupGlowUniforms(0.0f, 1.0f);
                if (this.separateTexture.getValue()) {
                    GL13.glActiveTexture(34000);
                    RenderUtils.bindTexture(this.framebuffer.framebufferTexture);
                }
                GL13.glActiveTexture(33984);
                RenderUtils.bindTexture(this.glowFrameBuffer.framebufferTexture);
                ShaderUtil.drawQuads();
                this.glowShader.unload();
            }
        });
    }
    
    public void setupGlowUniforms(final float direction1, final float direction2) {
        this.glowShader.setUniformi("texture", 0);
        if (this.separateTexture.getValue()) {
            this.glowShader.setUniformi("textureToCheck", 16);
        }
        this.glowShader.setUniformf("radius", this.radius.getValue().floatValue());
        this.glowShader.setUniformf("texelSize", 1.0f / this.mc.displayWidth, 1.0f / this.mc.displayHeight);
        this.glowShader.setUniformf("direction", direction1, direction2);
        this.glowShader.setUniformf("color", new Color(HUD.hudColor.getValue()).getRed() / 255.0f, new Color(HUD.hudColor.getValue()).getGreen() / 255.0f, new Color(HUD.hudColor.getValue()).getBlue() / 255.0f);
        this.glowShader.setUniformf("exposure", this.exposure.getValue().floatValue());
        this.glowShader.setUniformi("avoidTexture", (int)(Object)this.separateTexture.getValue());
        final FloatBuffer buffer = BufferUtils.createFloatBuffer(256);
        for (int i = 1; i <= this.radius.getValue(); ++i) {
            buffer.put(MathUtils.calculateGaussianValue((float)i, (float)(this.radius.getValue() / 2.0)));
        }
        buffer.rewind();
        GL20.glUniform1(this.glowShader.getUniform("weights"), buffer);
    }
    
    public void setupOutlineUniforms(final float direction1, final float direction2) {
        this.outlineShader.setUniformi("texture", 0);
        this.outlineShader.setUniformf("radius", (float)(this.radius.getValue() / 1.5));
        this.outlineShader.setUniformf("texelSize", 1.0f / this.mc.displayWidth, 1.0f / this.mc.displayHeight);
        this.outlineShader.setUniformf("direction", direction1, direction2);
        this.outlineShader.setUniformf("color", new Color(HUD.hudColor.getValue()).getRed() / 255.0f, new Color(HUD.hudColor.getValue()).getGreen() / 255.0f, new Color(HUD.hudColor.getValue()).getBlue() / 255.0f);
    }
    
    public void collectEntities() {
        this.entities.clear();
        for (final Entity entity : this.mc.theWorld.getLoadedEntityList()) {
            if (this.mc.thePlayer != null && this.mc.theWorld != null) {
                if (entity.getDistanceToEntity(this.mc.thePlayer) < 1.0f && !Utils.isInThirdPerson()) {
                    continue;
                }
                if (!(entity instanceof EntityPlayer)) {
                    continue;
                }
                this.entities.add(entity);
            }
        }
    }
}
