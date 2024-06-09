package exhibition.util.render;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.Locale;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class TTFFontRenderer {
   private Font font;
   private boolean fractionalMetrics;
   private TTFFontRenderer.CharacterData[] regularData;
   private TTFFontRenderer.CharacterData[] boldData;
   private TTFFontRenderer.CharacterData[] italicsData;
   private int[] colorCodes;
   private static final int MARGIN = 4;
   private static final char COLOR_INVOKER = '§';
   private static int RANDOM_OFFSET = 1;

   public TTFFontRenderer(Font font, boolean antialias) {
      this(font, 256, antialias);
   }

   public TTFFontRenderer(Font font, int characterCount, boolean antialias) {
      this(font, characterCount, true, antialias);
   }

   public TTFFontRenderer(Font font, int characterCount, boolean fractionalMetrics, boolean antialias) {
      this.fractionalMetrics = false;
      this.colorCodes = new int[32];
      this.font = font;
      this.fractionalMetrics = fractionalMetrics;
      this.regularData = this.setup(new TTFFontRenderer.CharacterData[characterCount], 0, antialias);
      this.boldData = this.setup(new TTFFontRenderer.CharacterData[characterCount], 1, antialias);
      this.italicsData = this.setup(new TTFFontRenderer.CharacterData[characterCount], 2, antialias);
   }

   private TTFFontRenderer.CharacterData[] setup(TTFFontRenderer.CharacterData[] characterData, int type, boolean antialias) {
      this.generateColors();
      Font font = this.font.deriveFont(type);
      BufferedImage utilityImage = new BufferedImage(1, 1, 2);
      Graphics2D utilityGraphics = (Graphics2D)utilityImage.getGraphics();
      utilityGraphics.setFont(font);
      FontMetrics fontMetrics = utilityGraphics.getFontMetrics();

      for(int index = 0; index < characterData.length; ++index) {
         char character = (char)index;
         Rectangle2D characterBounds = fontMetrics.getStringBounds(character + "", utilityGraphics);
         float width = (float)characterBounds.getWidth() + 8.0F;
         float height = (float)characterBounds.getHeight();
         BufferedImage characterImage = new BufferedImage(MathHelper.ceiling_double_int((double)width), MathHelper.ceiling_double_int((double)height), 2);
         Graphics2D graphics = (Graphics2D)characterImage.getGraphics();
         graphics.setFont(font);
         graphics.setColor(new Color(255, 255, 255, 0));
         graphics.fillRect(0, 0, characterImage.getWidth(), characterImage.getHeight());
         graphics.setColor(Color.WHITE);
         if (antialias) {
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, this.fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
         }

         graphics.drawString(character + "", 4, fontMetrics.getAscent());
         int textureId = GL11.glGenTextures();
         this.createTexture(textureId, characterImage);
         characterData[index] = new TTFFontRenderer.CharacterData(character, (float)characterImage.getWidth(), (float)characterImage.getHeight(), textureId);
      }

      return characterData;
   }

   private void createTexture(int textureId, BufferedImage image) {
      int[] pixels = new int[image.getWidth() * image.getHeight()];
      image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
      ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

      for(int y = 0; y < image.getHeight(); ++y) {
         for(int x = 0; x < image.getWidth(); ++x) {
            int pixel = pixels[y * image.getWidth() + x];
            buffer.put((byte)(pixel >> 16 & 255));
            buffer.put((byte)(pixel >> 8 & 255));
            buffer.put((byte)(pixel & 255));
            buffer.put((byte)(pixel >> 24 & 255));
         }
      }

      buffer.flip();
      GlStateManager.func_179144_i(textureId);
      GL11.glTexParameteri(3553, 10241, 9728);
      GL11.glTexParameteri(3553, 10240, 9728);
      GL11.glTexImage2D(3553, 0, 6408, image.getWidth(), image.getHeight(), 0, 6408, 5121, buffer);
   }

   public void drawString(String text, float x, float y, int color) {
      this.renderString(text, x, y, color, false);
   }

   public void drawCenteredString(String text, float x, float y, int color) {
      float width = this.getWidth(text) / 2.0F;
      float height = this.getHeight(text) / 2.0F;
      this.renderString(text, x - width, y - height, color, false);
   }

   public void drawStringWithShadow(String text, float x, float y, int color) {
      GL11.glTranslated(0.5D, 0.5D, 0.0D);
      this.renderString(text, x, y, Colors.getColor(0, color >> 24 & 255), true);
      GL11.glTranslated(-0.5D, -0.5D, 0.0D);
      this.renderString(text, x, y, color, false);
   }

   public void drawBorderedString(String text, float x, float y, int color) {
      GlStateManager.pushMatrix();
      GL11.glTranslated(0.5D, 0.0D, 0.0D);
      this.renderString(text, x, y, Colors.getColor(0, color >> 24 & 255), false);
      GL11.glTranslated(1.0D, 0.0D, 0.0D);
      this.renderString(text, x, y, Colors.getColor(0, color >> 24 & 255), false);
      GL11.glTranslated(-0.5D, 0.5D, 0.0D);
      this.renderString(text, x, y, Colors.getColor(0, color >> 24 & 255), false);
      GL11.glTranslated(0.0D, -1.0D, 0.0D);
      this.renderString(text, x, y, Colors.getColor(0, color >> 24 & 255), false);
      GL11.glTranslated(0.0D, 0.5D, 0.0D);
      this.renderString(text, x, y, color, false);
      GL11.glTranslated(-1.0D, -1.0D, 0.0D);
      GlStateManager.popMatrix();
   }

   private void renderString(String text, float x, float y, int color, boolean shadow) {
      if (text != "" && text.length() != 0) {
         GL11.glPushMatrix();
         GlStateManager.scale(0.5D, 0.5D, 1.0D);
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(770, 771);
         x -= 2.0F;
         y -= 2.0F;
         x += 0.5F;
         y += 0.5F;
         x *= 2.0F;
         y *= 2.0F;
         TTFFontRenderer.CharacterData[] characterData = this.regularData;
         boolean underlined = false;
         boolean strikethrough = false;
         boolean obfuscated = false;
         int length = text.length();
         double multiplier = 255.0D * (double)(shadow ? 4 : 1);
         Color c = new Color(color);
         GL11.glColor4d((double)c.getRed() / multiplier, (double)c.getGreen() / multiplier, (double)c.getBlue() / multiplier, (double)(color >> 24 & 255) / 255.0D);

         for(int i = 0; i < length; ++i) {
            char character = text.charAt(i);
            char previous = i > 0 ? text.charAt(i - 1) : 46;
            if (previous != '§') {
               if (character == '§' && i < length) {
                  int index = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));
                  if (index >= 16) {
                     if (index == 16) {
                        obfuscated = true;
                     } else if (index == 17) {
                        characterData = this.boldData;
                     } else if (index == 18) {
                        strikethrough = true;
                     } else if (index == 19) {
                        underlined = true;
                     } else if (index == 20) {
                        characterData = this.italicsData;
                     } else if (index == 21) {
                        obfuscated = false;
                        strikethrough = false;
                        underlined = false;
                        characterData = this.regularData;
                        GL11.glColor4d(1.0D * (shadow ? 0.25D : 1.0D), 1.0D * (shadow ? 0.25D : 1.0D), 1.0D * (shadow ? 0.25D : 1.0D), 1.0D);
                     }
                  } else {
                     obfuscated = false;
                     strikethrough = false;
                     underlined = false;
                     characterData = this.regularData;
                     if (index < 0 || index > 15) {
                        index = 15;
                     }

                     if (shadow) {
                        index += 16;
                     }

                     int textColor = this.colorCodes[index];
                     GL11.glColor4d((double)(textColor >> 16) / 255.0D, (double)(textColor >> 8 & 255) / 255.0D, (double)(textColor & 255) / 255.0D, (double)(color >> 24 & 255) / 255.0D);
                  }
               } else if (character <= 'ÿ') {
                  if (obfuscated) {
                     character = (char)(character + RANDOM_OFFSET);
                  }

                  this.drawChar(character, characterData, x, y);
                  TTFFontRenderer.CharacterData charData = characterData[character];
                  if (strikethrough) {
                     this.drawLine(new Vector2f(0.0F, charData.height / 2.0F), new Vector2f(charData.width, charData.height / 2.0F), 3.0F);
                  }

                  if (underlined) {
                     this.drawLine(new Vector2f(0.0F, charData.height - 15.0F), new Vector2f(charData.width, charData.height - 15.0F), 3.0F);
                  }

                  x += charData.width - 8.0F;
               }
            }
         }

         GlStateManager.disableBlend();
         GL11.glPopMatrix();
         GlStateManager.func_179144_i(0);
         GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);
      }
   }

   public float getWidth(String text) {
      float width = 0.0F;
      TTFFontRenderer.CharacterData[] characterData = this.regularData;
      int length = text.length();

      for(int i = 0; i < length; ++i) {
         char character = text.charAt(i);
         char previous = i > 0 ? text.charAt(i - 1) : 46;
         if (previous != '§') {
            if (character == '§' && i < length) {
               int index = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));
               if (index == 17) {
                  characterData = this.boldData;
               } else if (index == 20) {
                  characterData = this.italicsData;
               } else if (index == 21) {
                  characterData = this.regularData;
               }
            } else if (character <= 'ÿ') {
               TTFFontRenderer.CharacterData charData = characterData[character];
               width += (charData.width - 8.0F) / 2.0F;
            }
         }
      }

      return width + 2.0F;
   }

   public float getHeight(String text) {
      float height = 0.0F;
      TTFFontRenderer.CharacterData[] characterData = this.regularData;
      int length = text.length();

      for(int i = 0; i < length; ++i) {
         char character = text.charAt(i);
         char previous = i > 0 ? text.charAt(i - 1) : 46;
         if (previous != '§') {
            if (character == '§' && i < length) {
               int index = "0123456789abcdefklmnor".indexOf(text.toLowerCase(Locale.ENGLISH).charAt(i + 1));
               if (index == 17) {
                  characterData = this.boldData;
               } else if (index == 20) {
                  characterData = this.italicsData;
               } else if (index == 21) {
                  characterData = this.regularData;
               }
            } else if (character <= 'ÿ') {
               TTFFontRenderer.CharacterData charData = characterData[character];
               height = Math.max(height, charData.height);
            }
         }
      }

      return height / 2.0F - 2.0F;
   }

   private void drawChar(char character, TTFFontRenderer.CharacterData[] characterData, float x, float y) {
      TTFFontRenderer.CharacterData charData = characterData[character];
      charData.bind();
      GL11.glBegin(7);
      GL11.glTexCoord2f(0.0F, 0.0F);
      GL11.glVertex2d((double)x, (double)y);
      GL11.glTexCoord2f(0.0F, 1.0F);
      GL11.glVertex2d((double)x, (double)(y + charData.height));
      GL11.glTexCoord2f(1.0F, 1.0F);
      GL11.glVertex2d((double)(x + charData.width), (double)(y + charData.height));
      GL11.glTexCoord2f(1.0F, 0.0F);
      GL11.glVertex2d((double)(x + charData.width), (double)y);
      GL11.glEnd();
   }

   private void drawLine(Vector2f start, Vector2f end, float width) {
      GL11.glDisable(3553);
      GL11.glLineWidth(width);
      GL11.glBegin(1);
      GL11.glVertex2f(start.x, start.y);
      GL11.glVertex2f(end.x, end.y);
      GL11.glEnd();
      GL11.glEnable(3553);
   }

   private void generateColors() {
      for(int i = 0; i < 32; ++i) {
         int thingy = (i >> 3 & 1) * 85;
         int red = (i >> 2 & 1) * 170 + thingy;
         int green = (i >> 1 & 1) * 170 + thingy;
         int blue = (i >> 0 & 1) * 170 + thingy;
         if (i == 6) {
            red += 85;
         }

         if (i >= 16) {
            red /= 4;
            green /= 4;
            blue /= 4;
         }

         this.colorCodes[i] = (red & 255) << 16 | (green & 255) << 8 | blue & 255;
      }

   }

   class CharacterData {
      public char character;
      public float width;
      public float height;
      private int textureId;

      public CharacterData(char character, float width, float height, int textureId) {
         this.character = character;
         this.width = width;
         this.height = height;
         this.textureId = textureId;
      }

      public void bind() {
         GL11.glBindTexture(3553, this.textureId);
      }
   }
}
