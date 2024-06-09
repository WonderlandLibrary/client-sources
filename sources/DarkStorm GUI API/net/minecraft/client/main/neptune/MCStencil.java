package net.minecraft.client.main.neptune;

//import net.client.ttf.FontUtils;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.*;
import net.minecraft.block.state.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.network.*;
import net.minecraft.client.settings.*;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

import java.util.*;

import org.lwjgl.opengl.EXTFramebufferObject;

import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.network.play.client.*;

public class MCStencil
{
    public static void checkSetupFBO() {
        final Framebuffer fbo = Minecraft.getMinecraft().getFramebuffer();
        if (fbo != null && fbo.depthBuffer > -1) {
            setupFBO(fbo);
            fbo.depthBuffer = -1;
        }
    }
    
    public static void setupFBO(final Framebuffer fbo) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
        final int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, stencil_depth_buffer_ID);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencil_depth_buffer_ID);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencil_depth_buffer_ID);
    }
}
