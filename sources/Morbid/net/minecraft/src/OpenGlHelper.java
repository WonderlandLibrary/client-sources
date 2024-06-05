package net.minecraft.src;

import org.lwjgl.opengl.*;

public class OpenGlHelper
{
    public static int defaultTexUnit;
    public static int lightmapTexUnit;
    private static boolean useMultitextureARB;
    
    static {
        OpenGlHelper.useMultitextureARB = false;
    }
    
    public static void initializeTextures() {
        OpenGlHelper.useMultitextureARB = (GLContext.getCapabilities().GL_ARB_multitexture && !GLContext.getCapabilities().OpenGL13);
        if (OpenGlHelper.useMultitextureARB) {
            OpenGlHelper.defaultTexUnit = 33984;
            OpenGlHelper.lightmapTexUnit = 33985;
        }
        else {
            OpenGlHelper.defaultTexUnit = 33984;
            OpenGlHelper.lightmapTexUnit = 33985;
        }
    }
    
    public static void setActiveTexture(final int par0) {
        if (OpenGlHelper.useMultitextureARB) {
            ARBMultitexture.glActiveTextureARB(par0);
        }
        else {
            GL13.glActiveTexture(par0);
        }
    }
    
    public static void setClientActiveTexture(final int par0) {
        if (OpenGlHelper.useMultitextureARB) {
            ARBMultitexture.glClientActiveTextureARB(par0);
        }
        else {
            GL13.glClientActiveTexture(par0);
        }
    }
    
    public static void setLightmapTextureCoords(final int par0, final float par1, final float par2) {
        if (OpenGlHelper.useMultitextureARB) {
            ARBMultitexture.glMultiTexCoord2fARB(par0, par1, par2);
        }
        else {
            GL13.glMultiTexCoord2f(par0, par1, par2);
        }
    }
}
