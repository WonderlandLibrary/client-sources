package us.dev.direkt.module.internal.render.esp.modes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.CustomItems;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import us.dev.api.timing.Timer;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.render.EventRender2DNoScale;
import us.dev.direkt.event.internal.events.game.world.EventLoadWorld;
import us.dev.direkt.event.internal.filters.WorldLoadFilter;
import us.dev.direkt.gui.color.ClientColors;
import us.dev.direkt.module.internal.render.esp.ESP;
import us.dev.direkt.module.internal.render.esp.ESPMode;
import us.dev.direkt.util.render.OGLRender;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * @author Foundry
 */
public class ShaderMode extends ESPMode {

    private final double radius = 1;
    private final int quality = 3;

    private Framebuffer entityFBO;
    private Outline entityOutline;
    
    private Timer timer = new Timer();

    public ShaderMode() {
        super("Shader");
    }

    @Listener
    protected Link<EventRender2DNoScale> onRender2D = new Link<>(event -> {
        CustomItems.setUseGlint(false);
        if(entityFBO != null)
            entityFBO.framebufferClear();

        if (entityFBO == null)
            entityFBO = new Framebuffer(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight, false);
        else {
            if (entityFBO.framebufferWidth != Minecraft.getMinecraft().displayWidth || entityFBO.framebufferHeight != Minecraft.getMinecraft().displayHeight) {
                entityFBO.unbindFramebuffer();
                entityFBO = new Framebuffer(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight, false);

                if (entityOutline != null) {
                    entityOutline.delete();
                    entityOutline = new Outline(entityFBO.framebufferTexture, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight, Wrapper.getMinecraft().displayWidth / 2, Wrapper.getMinecraft().displayHeight / 2, (float) radius, Integer.valueOf(String.valueOf(quality).substring(0, 1)));
                }
            }
        }

        if (entityOutline == null)
            entityOutline = new Outline(entityFBO.framebufferTexture, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight, Wrapper.getMinecraft().displayWidth / 2, Wrapper.getMinecraft().displayHeight / 2, (float) radius, Integer.valueOf(String.valueOf(quality).substring(0, 1)));

        entityOutline.setOutlineSize((float) radius);

        final float partialTicks = Minecraft.getMinecraft().getTimer().renderPartialTicks;

        // Set up 3D entity rendering
        Minecraft.getMinecraft().entityRenderer.setupCameraTransform(partialTicks, 0, 0);

        RenderHelper.enableStandardItemLighting();

        final double[] polPosP = OGLRender.interpolate(Minecraft.getMinecraft().thePlayer);
        final double polPosXP = polPosP[0];
        final double polPosYP = polPosP[1];
        final double polPosZP = polPosP[2];

        // Bind entity FBO before rendering
        entityFBO.bindFramebuffer(false);

        // Clear entity FBO texture
        GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT);

        // Render entities to entity FBO
        for (Entity entity : Wrapper.getLoadedEntities()) {
            if (!Direkt.getInstance().getModuleManager().getModule(ESP.class).doesQualify(entity)
                    || (entity instanceof EntityPlayer && Direkt.getInstance().getFriendManager().isFriend((EntityPlayer)entity))) {
                continue;
            }

            Minecraft.getMinecraft().entityRenderer.disableLightmap();
            RenderHelper.disableStandardItemLighting();

            final double[] polPosE = OGLRender.interpolate(entity);
            final double polPosXE = polPosE[0];
            final double polPosYE = polPosE[1];
            final double polPosZE = polPosE[2];

            GL11.glPushMatrix();
            final Render<Entity> entityRender = Minecraft.getMinecraft().getRenderManager().getEntityRenderObject(entity);
            if (entityRender != null) {
                LayerArmorBase.shouldRenderArmorEffect(false);
                Wrapper.getMinecraft().getRenderItem().isRenderingEffectsInGUI(false);
                entityRender.doRender(entity, polPosXE - polPosXP, polPosYE - polPosYP, polPosZE - polPosZP, 0, partialTicks);
                Wrapper.getMinecraft().getRenderItem().isRenderingEffectsInGUI(true);
                LayerArmorBase.shouldRenderArmorEffect(true);

            }
            GL11.glPopMatrix();
        }

        // Set up 2D rendering
        Minecraft.getMinecraft().entityRenderer.disableLightmap();
        RenderHelper.disableStandardItemLighting();
        Minecraft.getMinecraft().entityRenderer.setupOverlayRendering();

        // Updates the overlay texture
        entityOutline.update();

        // Bind MC FBO
        entityFBO.unbindFramebuffer();
        Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(true);

        GL11.glPushMatrix();
        // Render overlay FBO texture
        GL11.glColor3f(0F, ClientColors.getNarrowFadingHue(), 0F);

        GlStateManager.bindTexture(entityOutline.getTextureID());
        GL11.glBegin(GL11.GL_POLYGON);
        GL11.glTexCoord2d(0, 1);
        GL11.glVertex2d(0, 0);
        GL11.glTexCoord2d(0, 0);
        GL11.glVertex2d(0, Wrapper.getMinecraft().displayHeight);
        GL11.glTexCoord2d(1, 0);
        GL11.glVertex2d(Wrapper.getMinecraft().displayWidth, Wrapper.getMinecraft().displayHeight);
        GL11.glTexCoord2d(1, 0);
        GL11.glVertex2d(Wrapper.getMinecraft().displayWidth, Wrapper.getMinecraft().displayHeight);
        GL11.glTexCoord2d(1, 1);
        GL11.glVertex2d(Wrapper.getMinecraft().displayWidth, 0);
        GL11.glTexCoord2d(0, 1);
        GL11.glVertex2d(0, 0);
        GL11.glEnd();

        GL11.glColor4f(1, 1, 1, 1);
        GL11.glPopMatrix();

        // Set up 3D entity rendering
        Minecraft.getMinecraft().entityRenderer.setupCameraTransform(partialTicks, 0, 0);

        RenderHelper.enableStandardItemLighting();

        // Bind entity FBO before rendering
        entityFBO.bindFramebuffer(false);

        // Clear entity FBO texture
        GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT);

        // Render entities to entity FBO
        for (EntityPlayer entity : Wrapper.getLoadedPlayersNoNPCs()) {
            if (!Direkt.getInstance().getModuleManager().getModule(ESP.class).doesQualify(entity)) {
                continue;
            }

            Minecraft.getMinecraft().entityRenderer.disableLightmap();
            RenderHelper.disableStandardItemLighting();

            final double[] polPosE = OGLRender.interpolate(entity);
            final double polPosXE = polPosE[0];
            final double polPosYE = polPosE[1];
            final double polPosZE = polPosE[2];

            GL11.glPushMatrix();
            final Render<Entity> entityRender = Minecraft.getMinecraft().getRenderManager().getEntityRenderObject(entity);
            if (entityRender != null && Direkt.getInstance().getFriendManager().isFriend(entity)) {
                LayerArmorBase.shouldRenderArmorEffect(false);
                Wrapper.getMinecraft().getRenderItem().isRenderingEffectsInGUI(false);
                entityRender.doRender(entity, polPosXE - polPosXP, polPosYE - polPosYP, polPosZE - polPosZP, 0, partialTicks);
                Wrapper.getMinecraft().getRenderItem().isRenderingEffectsInGUI(true);
                LayerArmorBase.shouldRenderArmorEffect(true);
            }
            GL11.glPopMatrix();
        }

        // Set up 2D rendering
        Minecraft.getMinecraft().entityRenderer.disableLightmap();
        RenderHelper.disableStandardItemLighting();
        Minecraft.getMinecraft().entityRenderer.setupOverlayRendering();

        // Updates the overlay texture
        entityOutline.update();

        // Bind MC FBO
        entityFBO.unbindFramebuffer();
        Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(true);

        GL11.glPushMatrix();
        // Render overlay FBO texture
        GL11.glColor3f(0.27F, 0.7F, 1F);

        GlStateManager.bindTexture(entityOutline.getTextureID());
        GL11.glBegin(GL11.GL_POLYGON);
        GL11.glTexCoord2d(0, 1);
        GL11.glVertex2d(0, 0);
        GL11.glTexCoord2d(0, 0);
        GL11.glVertex2d(0, Wrapper.getMinecraft().displayHeight);
        GL11.glTexCoord2d(1, 0);
        GL11.glVertex2d(Wrapper.getMinecraft().displayWidth, Wrapper.getMinecraft().displayHeight);
        GL11.glTexCoord2d(1, 0);
        GL11.glVertex2d(Wrapper.getMinecraft().displayWidth, Wrapper.getMinecraft().displayHeight);
        GL11.glTexCoord2d(1, 1);
        GL11.glVertex2d(Wrapper.getMinecraft().displayWidth, 0);
        GL11.glTexCoord2d(0, 1);
        GL11.glVertex2d(0, 0);
        GL11.glEnd();

        GL11.glColor4f(1, 1, 1, 1);
        GL11.glPopMatrix();
        CustomItems.setUseGlint(true);
    }, Link.LOW_PRIORITY, new WorldLoadFilter<>());

    @Listener
    protected Link<EventLoadWorld> onWorldLoad = new Link<>(event -> {
        if (entityFBO != null) {
            entityFBO.unbindFramebuffer();
        }
        entityFBO = null;
        if (entityOutline != null) {
            entityOutline.delete();
        }
        entityOutline = null;
    });

    @Override
    public void onDisable(){
        if (entityFBO != null) {
            entityFBO.unbindFramebuffer();
        }
        entityFBO = null;
        if (entityOutline != null) {
            entityOutline.delete();
        }
        entityOutline = null;
    }

    private static class Outline {
        private float outlineSize;
        private int sampleRadius;

        //Texture
        private final int targetTextureID;
        private final int textureWidth;
        private final int textureHeight;
        private final int renderWidth;
        private final int renderHeight;

        //FBO
        private int fboTextureID = -1;
        private int fboID = -1;
        private int renderBufferID = -1;

        //Shader
        private static int vertexShaderID = -1;
        private static int fragmentShaderID = -1;
        private static int shaderProgramID = -1;
        private static int diffuseSamperUniformID = -1;
        private static int texelSizeUniformID = -1;
        private static int sampleRadiusUniformID = -1;

        Outline(int targetTextureID, int textureWidth, int textureHeight, int dispWidth, int dispHeight, float outlineSize, int sampleRadius) {
            this.targetTextureID = targetTextureID;
            this.textureWidth = textureWidth;
            this.textureHeight = textureHeight;
            this.renderWidth = dispWidth;
            this.renderHeight = dispHeight;
            this.outlineSize = outlineSize;
            this.sampleRadius = sampleRadius;
            this.generateFBO();
            this.initShaders();
        }

        /**
         * Sets the outline size
         *
         * @param size
         * @return
         */
        Outline setOutlineSize(float size) {
            this.outlineSize = size;
            return this;
        }

        /**
         * Sets the sampling radius
         *
         * @param radius
         * @return
         */
        public Outline setSampleRadius(int radius) {
            this.sampleRadius = radius;
            return this;
        }

        /**
         * Returns the overlay texture ID
         *
         * @return
         */
        int getTextureID() {
            return this.fboTextureID;
        }

        /**
         * Deletes the overlay FBO and texture
         */
        public void delete() {
            if (this.renderBufferID > -1) {
                EXTFramebufferObject.glDeleteRenderbuffersEXT(this.renderBufferID);
            }
            if (this.fboID > -1) {
                EXTFramebufferObject.glDeleteFramebuffersEXT(this.fboID);
            }
            if (this.fboTextureID > -1) {
                GL11.glDeleteTextures(this.fboTextureID);
            }
        }

        /**
         * Updates the overlay texture
         *
         * @return
         */
        public Outline update() {
            if (this.fboID == -1 || this.renderBufferID == -1 || shaderProgramID == -1) {
                throw new RuntimeException("Invalid IDs!");
            }

            //Bind FBO
            EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, this.fboID);

            //Clear FBO buffers
            GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT);

            //Use shader
            ARBShaderObjects.glUseProgramObjectARB(shaderProgramID);

            //Upload sampler uniform (FBO texture)
            ARBShaderObjects.glUniform1iARB(diffuseSamperUniformID, 0);
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.targetTextureID);

            //Upload texel size uniform
            FloatBuffer texelSizeBuffer = BufferUtils.createFloatBuffer(2);
            texelSizeBuffer.position(0);
            texelSizeBuffer.put(1.0F / (float) (this.textureWidth) * this.outlineSize);
            texelSizeBuffer.put(1.0F / (float) (this.textureHeight) * this.outlineSize);
            texelSizeBuffer.flip();
            ARBShaderObjects.glUniform2ARB(texelSizeUniformID, texelSizeBuffer);

            //Upload sample radius uniform
            IntBuffer sampleRadiusBuffer = BufferUtils.createIntBuffer(1);
            sampleRadiusBuffer.position(0);
            sampleRadiusBuffer.put(this.sampleRadius);
            sampleRadiusBuffer.flip();
            ARBShaderObjects.glUniform1ARB(sampleRadiusUniformID, sampleRadiusBuffer);

            final int sizeMod = 2;
            
            //Render to FBO texture with shader
            GL11.glPushMatrix();
            GL11.glColor4f(1, 1, 1, 1);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_SRC_COLOR);
            GL11.glBegin(GL11.GL_POLYGON);
            GL11.glTexCoord2d(0, 1);
            GL11.glVertex2d(0, 0);
            GL11.glTexCoord2d(0, 0);
            GL11.glVertex2d(0, this.renderHeight * sizeMod);
            GL11.glTexCoord2d(1, 0);
            GL11.glVertex2d(this.renderWidth * sizeMod, this.renderHeight * sizeMod);
            GL11.glTexCoord2d(1, 0);
            GL11.glVertex2d(this.renderWidth * sizeMod, this.renderHeight * sizeMod);
            GL11.glTexCoord2d(1, 1);
            GL11.glVertex2d(this.renderWidth * sizeMod, 0);
            GL11.glTexCoord2d(0, 1);
            GL11.glVertex2d(0, 0);
            GL11.glEnd();
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
            
            //Unbind shader (bind default)
            ARBShaderObjects.glUseProgramObjectARB(0);

            return this;
        }


        /**
         * Generates the overlay FBO
         */
        private void generateFBO() {
            this.fboID = EXTFramebufferObject.glGenFramebuffersEXT();
            this.fboTextureID = GL11.glGenTextures();
            this.renderBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.fboTextureID);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10496.0F);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10496.0F);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.fboTextureID);
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, this.textureWidth, this.textureHeight, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
            EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, this.fboID);
            EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT, GL11.GL_TEXTURE_2D, this.fboTextureID, 0);

            EXTFramebufferObject.glBindRenderbufferEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, this.renderBufferID);
            EXTFramebufferObject.glRenderbufferStorageEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, EXTPackedDepthStencil.GL_DEPTH_STENCIL_EXT, this.textureWidth, this.textureHeight);
            EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_STENCIL_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, this.renderBufferID);
        }

        /**
         * Initializes the shaders
         */
        @SuppressWarnings("static-access")
        private void initShaders() {
            if (this.shaderProgramID == -1) {
                this.shaderProgramID = ARBShaderObjects.glCreateProgramObjectARB();
                try {
                    if (this.vertexShaderID == -1) {
                        String vertexShaderCode =
                                "#version 120 \n" +
                                        "void main() { \n" +
                                        "gl_TexCoord[0] = gl_MultiTexCoord0; \n" +
                                        "gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex; \n" +
                                        "}";
                        //this.vertexShaderID = OGLRender.createShader(vertexShaderCode, ARBVertexShader.GL_VERTEX_SHADER_ARB);
                    }
                    if (this.fragmentShaderID == -1) {
                        //Closest Sampler
                        String fragmentShaderCode =
                                "#version 120 \n" +
                        
                                "uniform sampler2D DiffuseSamper; \n" +
                                "uniform vec2 TexelSize; \n" +
                                "uniform int SampleRadius; \n" +
                                
                                "void main(){ \n" +
                                	"vec4 centerCol = texture2D(DiffuseSamper, gl_TexCoord[0].st); \n" +
                                
                                    "if(centerCol.a != 0.0F) { \n" +
                                    	"gl_FragColor = vec4(0, 0, 0, 0); \n" +
                                    	"return; \n" +
                                    "} \n" +
                                    
                                    "float closest = SampleRadius * 2.0F + 2.0F; \n" +
                                    "for(int xo = -SampleRadius; xo <= SampleRadius; xo++) { \n" +
                                    	"for(int yo = -SampleRadius; yo <= SampleRadius; yo++) { \n" +
                                        	"vec4 currCol = texture2D(DiffuseSamper, gl_TexCoord[0].st + vec2(xo * TexelSize.x, yo * TexelSize.y)); \n" +
                                    	
                                        	"if(currCol.r != 0.0F || currCol.g != 0.0F || currCol.b != 0.0F || currCol.a != 0.0F) { \n" +
                                        		"float currentDist = sqrt(xo*xo*1.0f + yo*yo*1.0f); \n" +
                                        	
                                        		"if(currentDist < closest) { \n" +
                                        			"closest = currentDist; \n" + 
                                        		"} \n" +
                                        		
                                        	"} \n" +
                                        "} \n" +
                                    "} \n" +
                                    
                                    "gl_FragColor = vec4(1, 1, 1, max(0, ((SampleRadius*1.0F) - (closest - 1)) / (SampleRadius*1.0F))); \n" +
                                "}";

                        this.fragmentShaderID = OGLRender.createShader(fragmentShaderCode, ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
                    }
                } catch (Exception ex) {
                    this.shaderProgramID = -1;
                    this.vertexShaderID = -1;
                    this.fragmentShaderID = -1;
                    ex.printStackTrace();
                }
                if (this.shaderProgramID != -1) {
                    ARBShaderObjects.glAttachObjectARB(this.shaderProgramID, this.fragmentShaderID);
                    ARBShaderObjects.glLinkProgramARB(this.shaderProgramID);
                    if (ARBShaderObjects.glGetObjectParameteriARB(this.shaderProgramID, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE) {
                        System.err.println(OGLRender.getLogInfo(this.shaderProgramID));
                        return;
                    }
                    ARBShaderObjects.glValidateProgramARB(this.shaderProgramID);
                    if (ARBShaderObjects.glGetObjectParameteriARB(this.shaderProgramID, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE) {
                        System.err.println(OGLRender.getLogInfo(this.shaderProgramID));
                        return;
                    }
                    ARBShaderObjects.glUseProgramObjectARB(0);
                    this.diffuseSamperUniformID = ARBShaderObjects.glGetUniformLocationARB(this.shaderProgramID, "DiffuseSamper");
                    this.texelSizeUniformID = ARBShaderObjects.glGetUniformLocationARB(this.shaderProgramID, "TexelSize");
                    this.sampleRadiusUniformID = ARBShaderObjects.glGetUniformLocationARB(this.shaderProgramID, "SampleRadius");
                }
            }
        }
    }
}
