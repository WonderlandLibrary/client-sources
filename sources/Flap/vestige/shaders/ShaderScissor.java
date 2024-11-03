package vestige.shaders;

import java.awt.image.BufferedImage;
import org.jetbrains.annotations.Nullable;

public class ShaderScissor extends Shader {
   private final int x;
   private final int y;
   private final int width;
   private final int height;
   private final BufferedImage in;

   public ShaderScissor(int x, int y, int width, int height, BufferedImage in, int level, boolean antiAlias, boolean multithreading) {
      super(level, antiAlias, multithreading);
      this.x = x * level;
      this.y = y * level;
      this.width = width * level;
      this.height = height * level;
      this.in = in;
      this.setWidth((float)in.getWidth());
      this.setHeight((float)in.getHeight());
   }

   public int dispose(int relativeX, int relativeY, float screenWidth, float screenHeight) {
      return relativeX > this.x && relativeY > this.y && relativeX < this.x + this.width && relativeY < this.y + this.height ? 0 : this.in.getRGB(relativeX / this.level, relativeY / this.level);
   }

   @Nullable
   public Object[] params() {
      return new Object[]{this.in, this.x, this.y, this.width, this.height, this.level, this.antiAlias};
   }
}
