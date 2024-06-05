package net.minecraft.client.renderer.texture;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.shadersmod.client.MultiTexID;
import net.shadersmod.client.ShadersTex;
import org.lwjgl.opengl.GL11;

public abstract class AbstractTexture implements ITextureObject {
   protected int glTextureId = -1;
   protected boolean blur;
   protected boolean mipmap;
   protected boolean blurLast;
   protected boolean mipmapLast;
   private static final String a = "CL_00001047";
   public MultiTexID multiTex;

   public void setBlurMipmapDirect(boolean var1, boolean var2) {
      this.blur = var1;
      this.mipmap = var2;
      boolean var3 = true;
      boolean var4 = true;
      short var5 = 9987;
      short var6 = 9729;
      GlStateManager.bindTexture(this.getGlTextureId());
      GL11.glTexParameteri(3553, 10241, var5);
      GL11.glTexParameteri(3553, 10240, var6);
   }

   public void setBlurMipmap(boolean var1, boolean var2) {
      this.blurLast = this.blur;
      this.mipmapLast = this.mipmap;
      this.setBlurMipmapDirect(var1, var2);
   }

   public void restoreLastBlurMipmap() {
      this.setBlurMipmapDirect(this.blurLast, this.mipmapLast);
   }

   public int getGlTextureId() {
      if(this.glTextureId == -1) {
         this.glTextureId = TextureUtil.glGenTextures();
      }

      return this.glTextureId;
   }

   public void deleteGlTexture() {
      ShadersTex.deleteTextures(this, this.glTextureId);
      if(this.glTextureId != -1) {
         TextureUtil.deleteTexture(this.glTextureId);
         this.glTextureId = -1;
      }

   }

   public MultiTexID getMultiTexID() {
      return ShadersTex.getMultiTexID(this);
   }
}
