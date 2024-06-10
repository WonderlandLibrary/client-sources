 package me.vene.skilled.utilities;
 
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Scanner;
 import javax.vecmath.Vector2f;
 import javax.vecmath.Vector4f;
 import net.minecraft.client.Minecraft;
 import net.minecraft.client.renderer.GlStateManager;
 import net.minecraft.client.renderer.Tessellator;
 import net.minecraft.client.renderer.WorldRenderer;
 import net.minecraft.client.renderer.texture.TextureManager;
 import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
 import net.minecraft.util.ResourceLocation;
 import org.lwjgl.opengl.GL11;

 public class TextUtil
 {
     public final String fontName;
     public double size;
     private final WorldRenderer wr;
     private final int fontScale;
     private final HashMap<Integer, ArrayList<Integer>> charData;
     private final HashMap<int[], Integer> kerningData;
     private final TextureManager tm;
     
     public TextUtil(final String fontName, final int fontScale) {
         this.wr = Tessellator.getInstance().getWorldRenderer();
         this.charData = new HashMap<Integer, ArrayList<Integer>>();
         this.kerningData = new HashMap<int[], Integer>();
         this.tm = Minecraft.getMinecraft().getTextureManager();
         this.fontName = fontName;
         this.fontScale = fontScale;
         this.loadFontData();
     }
   private void loadFontData() {
    final Scanner readIn = new Scanner(this.getClass().getClassLoader().getResourceAsStream("assets/minecraft/font/" + this.fontName + ".fnt"), "UTF-8");
    while (readIn.hasNextLine()) {
        final String line = readIn.nextLine();
        this.processLine(line);
    }
}
   private void processLine(String line) {
     ArrayList<Integer> data = new ArrayList<Integer>();
     int key = 0;
     int first = 0;
     int second = 0;
     for (String part : line.split(" ")) {
      String[] set = part.split("=");
       if ((line.contains("kerning ") || line.contains("char id")) && set.length == 2) {
         if (set[0].equalsIgnoreCase("id")) {
           key = Integer.parseInt(set[1]);
         }
         else if (set[0].equalsIgnoreCase("first")) {
           first = Integer.parseInt(set[1]);
         }
        else if (set[0].equalsIgnoreCase("second")) {
          second = Integer.parseInt(set[1]);
         } else {
           data.add(Integer.valueOf(set[1]));
         } 
       }
     } 
     if (line.contains("kerning ") && !data.isEmpty()) {
       this.kerningData.put(new int[] { first, second }, data.get(0));
     } else if (line.contains("char id") && !data.isEmpty()) {
       this.charData.put(Integer.valueOf(key), data);
     } 
   }
   
   public void renderCenteredText(String text, Vector2f center, Vector4f color, double size) {
     int w = getStringWidth(text, size);
     int h = getStringHeight(text, size);
     renderText(text, new Vector2f(center.x - (w / 2), center.y - (h / 2)), color, size);
   }
   
   public void renderText(String text, Vector2f pos, Vector4f color, double size) {
     this.size = size;    
     GL11.glColor4f(color.x, color.y, color.z, color.w);
     int cursor = 0;
     int i = 0;
     int j = 0;
     int k = 0;
     GlStateManager.pushMatrix();
     GlStateManager.pushAttrib();
     GL11.glEnable(3042);
     GL11.glBlendFunc(770, 771);
     String previous = "";
     for (char ch : text.toCharArray()) {
       int xOffset, yOffset, prev, kerning;       
       if (!previous.equals("")) {
         prev = previous.charAt(0);
       } else {
         prev = -1;
       } 
      if (i != 0) {
        xOffset = ((Integer)((ArrayList<Integer>)this.charData.get(Integer.valueOf(ch))).get(4)).intValue() - j;
         yOffset = ((Integer)((ArrayList<Integer>)this.charData.get(Integer.valueOf(ch))).get(5)).intValue() - k;
       } else {
         j = ((Integer)((ArrayList<Integer>)this.charData.get(Integer.valueOf(ch))).get(4)).intValue();
         k = ((Integer)((ArrayList<Integer>)this.charData.get(Integer.valueOf(ch))).get(5)).intValue();
         xOffset = 0;
         yOffset = 0;
       } 
       try {
         kerning = (prev == -1) ? 0 : ((Integer)this.kerningData.get(new int[] { prev, ch })).intValue();
      } catch (NullPointerException e) {
         kerning = -1;
       } 
       Vector2f relativePos = new Vector2f((cursor + xOffset + kerning), yOffset);
       renderChar(pos, relativePos, ch);
       previous = String.valueOf(ch);
       cursor += ((Integer)((ArrayList<Integer>)this.charData.get(Integer.valueOf(ch))).get(6)).intValue();
       i++;
     } 
     GlStateManager.popMatrix();
     GlStateManager.popAttrib();
   }
   
  private void renderChar(Vector2f pos, Vector2f relativePos, char ch) {
     int u = ((Integer)((ArrayList<Integer>)this.charData.get(Integer.valueOf(ch))).get(0)).intValue();
    int v = ((Integer)((ArrayList<Integer>)this.charData.get(Integer.valueOf(ch))).get(1)).intValue();
     int w = ((Integer)((ArrayList<Integer>)this.charData.get(Integer.valueOf(ch))).get(2)).intValue();
     int h = ((Integer)((ArrayList<Integer>)this.charData.get(Integer.valueOf(ch))).get(3)).intValue();
     double newSize = this.size / 50.0D;
     GL11.glTranslated(pos.x, pos.y, 0.0D);
     GL11.glScaled(newSize, newSize, newSize);
     ResourceLocation font = new ResourceLocation("autogg", "font/" + this.fontName + ".png");
     this.tm.bindTexture(font);
     float factor = 1.0F / this.fontScale;
     Tessellator tessellator = Tessellator.getInstance();
     this.wr.begin(7, DefaultVertexFormats.POSITION_TEX);
     this.wr.pos(relativePos.x, (relativePos.y + h), 0.0D).tex((u * factor), ((v + h) * factor)).endVertex();
     this.wr.pos((relativePos.x + w), (relativePos.y + h), 0.0D).tex(((u + w) * factor), ((v + h) * factor)).endVertex();
     this.wr.pos((relativePos.x + w), relativePos.y, 0.0D).tex(((u + w) * factor), (v * factor)).endVertex();
     this.wr.pos(relativePos.x, relativePos.y, 0.0D).tex((u * factor), (v * factor)).endVertex();
     tessellator.draw();
     GL11.glScaled(1.0D / newSize, 1.0D / newSize, 1.0D / newSize);
     GL11.glTranslated(-pos.x, -pos.y, 0.0D);
   }

   public int getStringWidth(String text, double size) {
    double width = 0.0D;
     int i = 0;
     for (char ch : text.toCharArray()) {
       if (i != text.length() - 1) {
         width += ((Integer)((ArrayList<Integer>)this.charData.get(Integer.valueOf(ch))).get(6)).intValue();
       } else {
         width += ((Integer)((ArrayList<Integer>)this.charData.get(Integer.valueOf(ch))).get(2)).intValue();
       } 
       i++;
     } 
     return (int)(size / 50.0D * width);
   }   
   public int getStringHeight(String text, double size) {
     double height = 0.0D;
     for (char ch : text.toCharArray()) {
       if (((Integer)((ArrayList<Integer>)this.charData.get(Integer.valueOf(ch))).get(3)).intValue() > height) {
         height = ((Integer)((ArrayList<Integer>)this.charData.get(Integer.valueOf(ch))).get(3)).intValue();
       }
     } 
     return (int)(size / 50.0D * height);
   }
 }
