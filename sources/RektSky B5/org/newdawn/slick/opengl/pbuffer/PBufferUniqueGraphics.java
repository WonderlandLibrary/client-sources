/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.opengl.pbuffer;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.Pbuffer;
import org.lwjgl.opengl.PixelFormat;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.opengl.SlickCallable;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.util.Log;

public class PBufferUniqueGraphics
extends Graphics {
    private Pbuffer pbuffer;
    private Image image;

    public PBufferUniqueGraphics(Image image) throws SlickException {
        super(image.getTexture().getTextureWidth(), image.getTexture().getTextureHeight());
        this.image = image;
        Log.debug("Creating pbuffer(unique) " + image.getWidth() + "x" + image.getHeight());
        if ((Pbuffer.getCapabilities() & 1) == 0) {
            throw new SlickException("Your OpenGL card does not support PBuffers and hence can't handle the dynamic images required for this application.");
        }
        this.init();
    }

    private void init() throws SlickException {
        try {
            Texture tex = InternalTextureLoader.get().createTexture(this.image.getWidth(), this.image.getHeight(), this.image.getFilter());
            this.pbuffer = new Pbuffer(this.screenWidth, this.screenHeight, new PixelFormat(8, 0, 0), null, null);
            this.pbuffer.makeCurrent();
            this.initGL();
            this.image.draw(0.0f, 0.0f);
            GL11.glBindTexture(3553, tex.getTextureID());
            GL11.glCopyTexImage2D(3553, 0, 6408, 0, 0, tex.getTextureWidth(), tex.getTextureHeight(), 0);
            this.image.setTexture(tex);
            Display.makeCurrent();
        }
        catch (Exception e2) {
            Log.error(e2);
            throw new SlickException("Failed to create PBuffer for dynamic image. OpenGL driver failure?");
        }
    }

    protected void disable() {
        GL11.glBindTexture(3553, this.image.getTexture().getTextureID());
        GL11.glCopyTexImage2D(3553, 0, 6408, 0, 0, this.image.getTexture().getTextureWidth(), this.image.getTexture().getTextureHeight(), 0);
        try {
            Display.makeCurrent();
        }
        catch (LWJGLException e2) {
            Log.error(e2);
        }
        SlickCallable.leaveSafeBlock();
    }

    protected void enable() {
        SlickCallable.enterSafeBlock();
        try {
            if (this.pbuffer.isBufferLost()) {
                this.pbuffer.destroy();
                this.init();
            }
            this.pbuffer.makeCurrent();
        }
        catch (Exception e2) {
            Log.error("Failed to recreate the PBuffer");
            Log.error(e2);
            throw new RuntimeException(e2);
        }
        TextureImpl.unbind();
        this.initGL();
    }

    protected void initGL() {
        GL11.glEnable(3553);
        GL11.glShadeModel(7425);
        GL11.glDisable(2929);
        GL11.glDisable(2896);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClearDepth(1.0);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glViewport(0, 0, this.screenWidth, this.screenHeight);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        this.enterOrtho();
    }

    protected void enterOrtho() {
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, this.screenWidth, 0.0, this.screenHeight, 1.0, -1.0);
        GL11.glMatrixMode(5888);
    }

    public void destroy() {
        super.destroy();
        this.pbuffer.destroy();
    }

    public void flush() {
        super.flush();
        this.image.flushPixelData();
    }
}

