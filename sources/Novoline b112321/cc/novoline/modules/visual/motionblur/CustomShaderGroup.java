package cc.novoline.modules.visual.motionblur;

import cc.novoline.Novoline;
import cc.novoline.modules.visual.Camera;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.util.Iterator;
import net.acE;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class CustomShaderGroup extends ShaderGroup {
   private static acE[] l;

   public CustomShaderGroup(TextureManager var1, IResourceManager var2, Framebuffer var3, ResourceLocation var4) throws IOException, JsonSyntaxException {
      b();
      super(var1, var2, var3, var4);
      if(acE.b() == null) {
         b(new acE[1]);
      }

   }

   public void parseGroup(TextureManager var1, ResourceLocation var2) throws IOException, JsonSyntaxException {
      b();
      JsonObject var4 = ((Camera)Novoline.getInstance().getModuleManager().getModule(Camera.class)).getJsonObject();
      if(JsonUtils.isJsonArray(var4, "targets")) {
         JsonArray var5 = var4.getAsJsonArray("targets");
         int var6 = 0;
         Iterator var7 = var5.iterator();
         if(var7.hasNext()) {
            JsonElement var8 = (JsonElement)var7.next();
            CustomShaderGroup var10000 = this;
            JsonElement var10001 = var8;

            try {
               var10000.initTarget(var10001);
            } catch (Exception var12) {
               JsonException var10 = JsonException.func_151379_a(var12);
               var10.func_151380_a("targets[" + var6 + "]");
               throw var10;
            }

            ++var6;
         }
      }

      if(JsonUtils.isJsonArray(var4, "passes")) {
         JsonArray var13 = var4.getAsJsonArray("passes");
         int var15 = 0;
         Iterator var17 = var13.iterator();
         if(var17.hasNext()) {
            JsonElement var18 = (JsonElement)var17.next();
            CustomShaderGroup var20 = this;
            TextureManager var21 = var1;
            JsonElement var10002 = var18;

            try {
               var20.a(var21, var10002);
            } catch (Exception var11) {
               JsonException var19 = JsonException.func_151379_a(var11);
               var19.func_151380_a("passes[" + var15 + "]");
               throw var19;
            }

            ++var15;
         }
      }

   }

   public static void b(acE[] var0) {
      l = var0;
   }

   public static acE[] b() {
      return l;
   }

   private static Exception a(Exception var0) {
      return var0;
   }

   static {
      b((acE[])null);
   }
}
