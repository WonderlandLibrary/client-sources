package net.minecraft.client.renderer;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import optifine.Config;
import optifine.Reflector;
import org.lwjgl.opengl.GL11;
import shadersmod.client.SVertexBuilder;

public class WorldVertexBufferUploader {
   public int draw(WorldRenderer var1, int var2) {
      if (var2 > 0) {
         VertexFormat var3 = var1.func_178973_g();
         int var4 = var3.func_177338_f();
         ByteBuffer var5 = var1.func_178966_f();
         List var6 = var3.func_177343_g();
         Iterator var7 = var6.iterator();
         boolean var8 = Reflector.ForgeVertexFormatElementEnumUseage_preDraw.exists();
         boolean var9 = Reflector.ForgeVertexFormatElementEnumUseage_postDraw.exists();

         VertexFormatElement var10;
         VertexFormatElement.EnumUseage var11;
         int var12;
         while(var7.hasNext()) {
            var10 = (VertexFormatElement)var7.next();
            var11 = var10.func_177375_c();
            if (var8) {
               Reflector.callVoid(var11, Reflector.ForgeVertexFormatElementEnumUseage_preDraw, var10, var4, var5);
            } else {
               var12 = var10.func_177367_b().func_177397_c();
               int var13 = var10.func_177369_e();
               switch(var11) {
               case POSITION:
                  var5.position(var10.func_177373_a());
                  GL11.glVertexPointer(var10.func_177370_d(), var12, var4, var5);
                  GL11.glEnableClientState(32884);
                  break;
               case UV:
                  var5.position(var10.func_177373_a());
                  OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + var13);
                  GL11.glTexCoordPointer(var10.func_177370_d(), var12, var4, var5);
                  GL11.glEnableClientState(32888);
                  OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                  break;
               case COLOR:
                  var5.position(var10.func_177373_a());
                  GL11.glColorPointer(var10.func_177370_d(), var12, var4, var5);
                  GL11.glEnableClientState(32886);
                  break;
               case NORMAL:
                  var5.position(var10.func_177373_a());
                  GL11.glNormalPointer(var12, var4, var5);
                  GL11.glEnableClientState(32885);
               }
            }
         }

         if (var1.isMultiTexture()) {
            var1.drawMultiTexture();
         } else if (Config.isShaders()) {
            SVertexBuilder.drawArrays(var1.getDrawMode(), 0, var1.func_178989_h(), var1);
         } else {
            GL11.glDrawArrays(var1.getDrawMode(), 0, var1.func_178989_h());
         }

         var7 = var6.iterator();

         while(var7.hasNext()) {
            var10 = (VertexFormatElement)var7.next();
            var11 = var10.func_177375_c();
            if (var9) {
               Reflector.callVoid(var11, Reflector.ForgeVertexFormatElementEnumUseage_postDraw, var10, var4, var5);
            } else {
               var12 = var10.func_177369_e();
               switch(var11) {
               case POSITION:
                  GL11.glDisableClientState(32884);
                  break;
               case UV:
                  OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + var12);
                  GL11.glDisableClientState(32888);
                  OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                  break;
               case COLOR:
                  GL11.glDisableClientState(32886);
                  GlStateManager.func_179117_G();
                  break;
               case NORMAL:
                  GL11.glDisableClientState(32885);
               }
            }
         }
      }

      var1.reset();
      return var2;
   }

   static final class SwitchEnumUseage {
      static final int[] field_178958_a = new int[VertexFormatElement.EnumUseage.values().length];

      static {
         try {
            field_178958_a[VertexFormatElement.EnumUseage.POSITION.ordinal()] = 1;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_178958_a[VertexFormatElement.EnumUseage.UV.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_178958_a[VertexFormatElement.EnumUseage.COLOR.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_178958_a[VertexFormatElement.EnumUseage.NORMAL.ordinal()] = 4;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
