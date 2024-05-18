package net.minecraft.src;

import org.lwjgl.opengl.*;

public class OpenGlCapsChecker
{
    public static boolean checkARBOcclusion() {
        return GLContext.getCapabilities().GL_ARB_occlusion_query;
    }
}
