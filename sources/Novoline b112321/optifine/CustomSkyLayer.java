package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.Properties;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.world.World;
import optifine.Blender;
import optifine.Config;
import optifine.ConnectedParser;
import optifine.MatchBlock;
import optifine.RangeListInt;
import optifine.TextureUtils;

public class CustomSkyLayer {
   public String source = null;
   private int startFadeIn = -1;
   private int endFadeIn = -1;
   private int startFadeOut = -1;
   private int endFadeOut = -1;
   private int blend = 1;
   private boolean rotate = false;
   private float speed = 1.0F;
   private float[] axis;
   private RangeListInt days;
   private int daysLoop;
   public int textureId;
   public static final float[] DEFAULT_AXIS = new float[]{1.0F, 0.0F, 0.0F};

   public CustomSkyLayer(Properties var1, String var2) {
      this.axis = DEFAULT_AXIS;
      this.days = null;
      this.daysLoop = 8;
      this.textureId = -1;
      ConnectedParser var3 = new ConnectedParser("CustomSky");
      this.source = var1.getProperty("source", var2);
      this.startFadeIn = this.parseTime(var1.getProperty("startFadeIn"));
      this.endFadeIn = this.parseTime(var1.getProperty("endFadeIn"));
      this.startFadeOut = this.parseTime(var1.getProperty("startFadeOut"));
      this.endFadeOut = this.parseTime(var1.getProperty("endFadeOut"));
      this.blend = Blender.parseBlend(var1.getProperty("blend"));
      this.rotate = this.parseBoolean(var1.getProperty("rotate"), true);
      this.speed = this.parseFloat(var1.getProperty("speed"), 1.0F);
      this.axis = this.parseAxis(var1.getProperty("axis"), DEFAULT_AXIS);
      this.days = var3.f(var1.getProperty("days"));
      this.daysLoop = var3.parseInt(var1.getProperty("daysLoop"), 8);
   }

   private int parseTime(String var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(var1 == null) {
         return -1;
      } else {
         String[] var3 = Config.tokenize(var1, ":");
         if(var3.length != 2) {
            Config.warn("Invalid time: " + var1);
            return -1;
         } else {
            String var4 = var3[0];
            String var5 = var3[1];
            int var6 = Config.parseInt(var4, -1);
            int var7 = Config.parseInt(var5, -1);
            if(var6 >= 0 && var6 <= 23 && var7 >= 0 && var7 <= 59) {
               var6 = var6 - 6;
               if(var6 < 0) {
                  var6 += 24;
               }

               int var8 = var6 * 1000 + (int)((double)var7 / 60.0D * 1000.0D);
               return var8;
            } else {
               Config.warn("Invalid time: " + var1);
               return -1;
            }
         }
      }
   }

   private boolean parseBoolean(String var1, boolean var2) {
      PacketRemapper[] var3 = MatchBlock.b();
      if(var1 == null) {
         return var2;
      } else if(var1.toLowerCase().equals("true")) {
         return true;
      } else if(var1.toLowerCase().equals("false")) {
         return false;
      } else {
         Config.warn("Unknown boolean: " + var1);
         return var2;
      }
   }

   private float parseFloat(String var1, float var2) {
      PacketRemapper[] var3 = MatchBlock.b();
      if(var1 == null) {
         return var2;
      } else {
         float var4 = Config.parseFloat(var1, Float.MIN_VALUE);
         if(var4 == Float.MIN_VALUE) {
            Config.warn("Invalid value: " + var1);
            return var2;
         } else {
            return var4;
         }
      }
   }

   private float[] parseAxis(String var1, float[] var2) {
      PacketRemapper[] var3 = MatchBlock.b();
      if(var1 == null) {
         return var2;
      } else {
         String[] var4 = Config.tokenize(var1, " ");
         if(var4.length != 3) {
            Config.warn("Invalid axis: " + var1);
            return var2;
         } else {
            float[] var5 = new float[3];
            int var6 = 0;
            if(var6 < var4.length) {
               var5[var6] = Config.parseFloat(var4[var6], Float.MIN_VALUE);
               if(var5[var6] == Float.MIN_VALUE) {
                  Config.warn("Invalid axis: " + var1);
                  return var2;
               }

               if(var5[var6] < -1.0F || var5[var6] > 1.0F) {
                  Config.warn("Invalid axis values: " + var1);
                  return var2;
               }

               ++var6;
            }

            float var11 = var5[0];
            float var7 = var5[1];
            float var8 = var5[2];
            if(var11 * var11 + var7 * var7 + var8 * var8 < 1.0E-5F) {
               Config.warn("Invalid axis values: " + var1);
               return var2;
            } else {
               float[] var9 = new float[]{var8, var7, -var11};
               return var9;
            }
         }
      }
   }

   public boolean isValid(String var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(this.source == null) {
         Config.warn("No source texture: " + var1);
         return false;
      } else {
         this.source = TextureUtils.fixResourcePath(this.source, TextureUtils.getBasePath(var1));
         if(this.startFadeIn >= 0 && this.endFadeIn >= 0 && this.endFadeOut >= 0) {
            int var3 = this.a(this.endFadeIn - this.startFadeIn);
            if(this.startFadeOut < 0) {
               this.startFadeOut = this.a(this.endFadeOut - var3);
               if(this.timeBetween(this.startFadeOut, this.startFadeIn, this.endFadeIn)) {
                  this.startFadeOut = this.endFadeIn;
               }
            }

            int var4 = this.a(this.startFadeOut - this.endFadeIn);
            int var5 = this.a(this.endFadeOut - this.startFadeOut);
            int var6 = this.a(this.startFadeIn - this.endFadeOut);
            int var7 = var3 + var4 + var5 + var6;
            if(var7 != 24000) {
               Config.warn("Invalid fadeIn/fadeOut times, sum is not 24h: " + var7);
               return false;
            } else if(this.speed < 0.0F) {
               Config.warn("Invalid speed: " + this.speed);
               return false;
            } else if(this.daysLoop <= 0) {
               Config.warn("Invalid daysLoop: " + this.daysLoop);
               return false;
            } else {
               return true;
            }
         } else {
            Config.warn("Invalid times, required are: startFadeIn, endFadeIn and endFadeOut.");
            return false;
         }
      }
   }

   private int a(int var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(var1 >= 24000) {
         var1 -= 24000;
      }

      var1 = var1 + 24000;
      return var1;
   }

   public void render(int var1, float var2, float var3) {
      MatchBlock.b();
      float var5 = var3 * this.getFadeBrightness(var1);
      var5 = Config.a(var5, 0.0F, 1.0F);
      if(var5 >= 1.0E-4F) {
         GlStateManager.bindTexture(this.textureId);
         Blender.setupBlend(this.blend, var5);
         GlStateManager.pushMatrix();
         if(this.rotate) {
            GlStateManager.rotate(var2 * 360.0F * this.speed, this.axis[0], this.axis[1], this.axis[2]);
         }

         Tessellator var6 = Tessellator.getInstance();
         GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
         this.renderSide(var6, 4);
         GlStateManager.pushMatrix();
         GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
         this.renderSide(var6, 1);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
         this.renderSide(var6, 0);
         GlStateManager.popMatrix();
         GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
         this.renderSide(var6, 5);
         GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
         this.renderSide(var6, 2);
         GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
         this.renderSide(var6, 3);
         GlStateManager.popMatrix();
      }

   }

   private float getFadeBrightness(int var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(this.timeBetween(var1, this.startFadeIn, this.endFadeIn)) {
         int var5 = this.a(this.endFadeIn - this.startFadeIn);
         int var6 = this.a(var1 - this.startFadeIn);
         return (float)var6 / (float)var5;
      } else if(this.timeBetween(var1, this.endFadeIn, this.startFadeOut)) {
         return 1.0F;
      } else if(this.timeBetween(var1, this.startFadeOut, this.endFadeOut)) {
         int var3 = this.a(this.endFadeOut - this.startFadeOut);
         int var4 = this.a(var1 - this.startFadeOut);
         return 1.0F - (float)var4 / (float)var3;
      } else {
         return 0.0F;
      }
   }

   private void renderSide(Tessellator var1, int var2) {
      WorldRenderer var3 = var1.getWorldRenderer();
      double var4 = (double)(var2 % 3) / 3.0D;
      double var6 = (double)(var2 / 3) / 2.0D;
      var3.begin(7, DefaultVertexFormats.POSITION_TEX);
      var3.pos(-100.0D, -100.0D, -100.0D).tex(var4, var6).endVertex();
      var3.pos(-100.0D, -100.0D, 100.0D).tex(var4, var6 + 0.5D).endVertex();
      var3.pos(100.0D, -100.0D, 100.0D).tex(var4 + 0.3333333333333333D, var6 + 0.5D).endVertex();
      var3.pos(100.0D, -100.0D, -100.0D).tex(var4 + 0.3333333333333333D, var6).endVertex();
      var1.draw();
   }

   public boolean isActive(World var1, int var2) {
      PacketRemapper[] var3 = MatchBlock.b();
      if(this.timeBetween(var2, this.endFadeOut, this.startFadeIn)) {
         return false;
      } else {
         if(this.days != null) {
            long var4 = var1.getWorldTime();
            long var6 = var4 - (long)this.startFadeIn;
            if(var6 < 0L) {
               var6 += (long)(24000 * this.daysLoop);
            }

            int var8 = (int)(var6 / 24000L);
            int var9 = var8 % this.daysLoop;
            if(!this.days.isInRange(var9)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean timeBetween(int var1, int var2, int var3) {
      PacketRemapper[] var4 = MatchBlock.b();
      return var2 <= var3?var1 >= var2 && var1 <= var3:var1 >= var2 || var1 <= var3;
   }
}
