package vestige.shaders;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.TextureUtil;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import vestige.util.render.RenderUtils2;

public abstract class Shader {
   public int identifier = -1;
   public float width;
   public float height;
   public final int level;
   public final boolean antiAlias;
   public final boolean multithreading;
   public int texture;
   private int lastIdentifier = -1;
   private boolean disposing = false;
   private final ExecutorService executorService = Executors.newCachedThreadPool();

   public Shader(int level, boolean antiAlias, boolean multithreading) {
      this.level = level;
      this.antiAlias = antiAlias;
      this.multithreading = multithreading;
      this.texture = GL11.glGenTextures();
   }

   public abstract int dispose(int var1, int var2, float var3, float var4);

   @Nullable
   public abstract Object[] params();

   public void setWidth(float width) {
      this.width = width * (float)this.level;
   }

   public void setHeight(float height) {
      this.height = height * (float)this.level;
   }

   public float getRealWidth() {
      return this.width / (float)this.level;
   }

   public float getRealHeight() {
      return this.height / (float)this.level;
   }

   public void render(float x, float y, int color) {
      this.identifier = Arrays.deepHashCode(this.params());
      if (this.identifier != this.lastIdentifier && !this.disposing) {
         this.lastIdentifier = this.identifier;
         this.disposing = true;
         this.compile();
      }

      RenderUtils2.drawImage(this.texture, x, y, this.getRealWidth(), this.getRealHeight(), color);
   }

   public BufferedImage generate() {
      int width = (int)this.width;
      int height = (int)this.height;
      int size = width * height;
      ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
      BufferedImage image = new BufferedImage(width, height, 7);

      for(int i = 0; i < size; ++i) {
         int x = i % width;
         int y = i / width;
         image.setRGB(x, y, this.dispose(x, y, (float)sr.getScaledWidth(), (float)sr.getScaledHeight()));
      }

      image = this.antiAlias ? (new GaussianFilter((float)this.level / 2.0F)).filter(image, (BufferedImage)null) : image;
      return image;
   }

   private void compile() {
      Runnable connect = () -> {
         BufferedImage finalImage = this.generate();
         Runnable task = () -> {
            TextureUtil.uploadTextureImageAllocate(this.texture, finalImage, true, false);
         };
         if (this.multithreading) {
            this.executorService.execute(task);
         } else {
            task.run();
         }

         this.disposing = false;
      };
      if (this.multithreading) {
         (new Thread(connect)).start();
      } else {
         connect.run();
      }

   }

   public static Shader.vec2 vec2(double x, double y) {
      return new Shader.vec2(x, y);
   }

   public static Shader.vec3 vec3(double x, double y, double z) {
      return new Shader.vec3(x, y, z);
   }

   public static Shader.vec3 vec3(Shader.vec2 v, double z) {
      return new Shader.vec3(v.x, v.y, z);
   }

   public static Shader.vec4 vec4(double x, double y, double z, double w) {
      return new Shader.vec4(x, y, z, w);
   }

   public static Shader.vec4 vec4(Shader.vec3 v, double w) {
      return new Shader.vec4(v.x, v.y, v.z, w);
   }

   private static double atan2(double y, double x) {
      double ax = x >= 0.0D ? x : -x;
      double ay = y >= 0.0D ? y : -y;
      double a = Math.min(ax, ay) / Math.max(ax, ay);
      double s = a * a;
      double r = ((-0.0464964749D * s + 0.15931422D) * s - 0.327622764D) * s * a + a;
      if (ay > ax) {
         r = 1.57079637D - r;
      }

      if (x < 0.0D) {
         r = 3.14159274D - r;
      }

      return y >= 0.0D ? r : -r;
   }

   public static int mod(int a, int b) {
      return a % b;
   }

   public static double mod(double a, double b) {
      return a % b;
   }

   public static Shader.vec2 mod(Shader.vec2 a, double b) {
      return vec2(mod(a.x, b), mod(a.y, b));
   }

   public static double fma(double a, double b, double c) {
      return a * b + c;
   }

   public static double dot(Shader.vec2 v1, Shader.vec2 v2) {
      return atan2(v1.x * v2.x + v1.y * v2.y, v1.x * v2.y - v1.y * v2.x);
   }

   public static double dot(Shader.vec3 v1, Shader.vec3 v2) {
      return fma(v1.x, v2.x, fma(v1.y, v2.y, v1.z * v2.z));
   }

   public static double dot(Shader.vec4 v1, Shader.vec4 v2) {
      return fma(v1.x, v2.x, fma(v1.y, v2.y, fma(v1.z, v2.z, v1.w * v2.w)));
   }

   public static double mix(double v1, double v2, double a) {
      return v1 * (1.0D - a) + v2 * a;
   }

   public static Shader.vec2 mix(Shader.vec2 v1, Shader.vec2 v2, double a) {
      return vec2(mix(v1.x, v2.x, a), mix(v1.y, v2.y, a));
   }

   public static Shader.vec3 mix(Shader.vec3 v1, Shader.vec3 v2, double a) {
      return vec3(mix(v1.x, v2.x, a), mix(v1.y, v2.y, a), mix(v1.z, v2.z, a));
   }

   public static Shader.vec4 mix(Shader.vec4 v1, Shader.vec4 v2, double a) {
      return vec4(mix(v1.x, v2.x, a), mix(v1.y, v2.y, a), mix(v1.z, v2.z, a), mix(v1.w, v2.w, a));
   }

   public static double length(Shader.vec2 vec2) {
      return length(vec2.x, vec2.y);
   }

   public static double length(double x, double y) {
      return Math.sqrt(x * x + y * y);
   }

   public static double exp(double x) {
      return Math.exp(x);
   }

   public static float fract(float x) {
      return x - (float)((int)x);
   }

   public static Shader.vec3 floor(Shader.vec3 vec3) {
      return vec3(Math.floor(vec3.x), Math.floor(vec3.y), Math.floor(vec3.z));
   }

   public static int color(float r, float g, float b, float a) {
      int red = (int)(r * 255.0F);
      int green = (int)(g * 255.0F);
      int blue = (int)(b * 255.0F);
      int alpha = (int)(a * 255.0F);
      red = Math.min(red, 255);
      green = Math.min(green, 255);
      blue = Math.min(blue, 255);
      alpha = Math.min(alpha, 255);
      return color(red, green, blue, alpha);
   }

   public static int color(int r, int g, int b, int a) {
      return (a & 255) << 24 | (r & 255) << 16 | (g & 255) << 8 | b & 255;
   }

   public static double clamp(double value, double min, double max) {
      return value < min ? min : Math.min(value, max);
   }

   public static class vec2 {
      public double x;
      public double y;

      public vec2(double x, double y) {
         this.x = x;
         this.y = y;
      }
   }

   public static class vec3 {
      public double x;
      public double y;
      public double z;

      public vec3(double x, double y, double z) {
         this.x = x;
         this.y = y;
         this.z = z;
      }
   }

   public static class vec4 {
      public double x;
      public double y;
      public double z;
      public double w;

      public vec4(double x, double y, double z, double w) {
         this.x = x;
         this.y = y;
         this.z = z;
         this.w = w;
      }
   }
}
