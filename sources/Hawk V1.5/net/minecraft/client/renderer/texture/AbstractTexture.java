package net.minecraft.client.renderer.texture;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import shadersmod.client.MultiTexID;
import shadersmod.client.ShadersTex;

public abstract class AbstractTexture implements ITextureObject {
   protected boolean field_174939_e;
   protected boolean field_174941_c;
   protected int glTextureId = -1;
   private static final String __OBFID = "CL_00001047";
   protected boolean field_174940_b;
   protected boolean field_174938_d;
   public MultiTexID multiTex;

   public int getGlTextureId() {
      if (this.glTextureId == -1) {
         this.glTextureId = TextureUtil.glGenTextures();
      }

      return this.glTextureId;
   }

   public void func_174935_a() {
      this.func_174937_a(this.field_174938_d, this.field_174939_e);
   }

   public void func_174937_a(boolean var1, boolean var2) {
      this.field_174940_b = var1;
      this.field_174941_c = var2;
      boolean var3 = true;
      boolean var4 = true;
      int var5;
      short var6;
      if (var1) {
         var5 = var2 ? 9987 : 9729;
         var6 = 9729;
      } else {
         var5 = var2 ? 9986 : 9728;
         var6 = 9728;
      }

      GlStateManager.func_179144_i(this.getGlTextureId());
      GL11.glTexParameteri(3553, 10241, var5);
      GL11.glTexParameteri(3553, 10240, var6);
   }

   public void deleteGlTexture() {
      ShadersTex.deleteTextures(this, this.glTextureId);
      if (this.glTextureId != -1) {
         TextureUtil.deleteTexture(this.glTextureId);
         this.glTextureId = -1;
      }

   }

   public MultiTexID getMultiTexID() {
      return ShadersTex.getMultiTexID(this);
   }

   public void func_174936_b(boolean var1, boolean var2) {
      this.field_174938_d = this.field_174940_b;
      this.field_174939_e = this.field_174941_c;
      this.func_174937_a(var1, var2);
   }
}
