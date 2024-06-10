/*    */ package nightmare.utils.render;
/*    */ 
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ public class Render3DUtils
/*    */ {
/*    */   public static void drawBox(double x, double y, double z, double width, double height, float lineWidth, float red, float green, float blue, float alpha) {
/* 13 */     GL11.glPushMatrix();
/* 14 */     GL11.glEnable(3042);
/* 15 */     GL11.glBlendFunc(770, 771);
/* 16 */     GL11.glDisable(3553);
/* 17 */     GL11.glEnable(2848);
/* 18 */     GL11.glDisable(2929);
/* 19 */     GL11.glDepthMask(false);
/* 20 */     GL11.glLineWidth(lineWidth);
/* 21 */     GL11.glColor4f(red, green, blue, alpha);
/* 22 */     drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
/* 23 */     GL11.glDisable(2848);
/* 24 */     GL11.glEnable(3553);
/* 25 */     GL11.glEnable(2929);
/* 26 */     GL11.glDepthMask(true);
/* 27 */     GL11.glDisable(3042);
/* 28 */     GL11.glPopMatrix();
/*    */   }
/*    */   
/*    */   public static void drawOutlinedBoundingBox(AxisAlignedBB aa) {
/* 32 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 33 */     WorldRenderer worldRenderer = tessellator.func_178180_c();
/* 34 */     worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
/* 35 */     worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c).func_181675_d();
/* 36 */     worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c).func_181675_d();
/* 37 */     worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f).func_181675_d();
/* 38 */     worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f).func_181675_d();
/* 39 */     worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c).func_181675_d();
/* 40 */     tessellator.func_78381_a();
/* 41 */     worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
/* 42 */     worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c).func_181675_d();
/* 43 */     worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c).func_181675_d();
/* 44 */     worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f).func_181675_d();
/* 45 */     worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f).func_181675_d();
/* 46 */     worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c).func_181675_d();
/* 47 */     tessellator.func_78381_a();
/* 48 */     worldRenderer.func_181668_a(1, DefaultVertexFormats.field_181705_e);
/* 49 */     worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c).func_181675_d();
/* 50 */     worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c).func_181675_d();
/* 51 */     worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c).func_181675_d();
/* 52 */     worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c).func_181675_d();
/* 53 */     worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f).func_181675_d();
/* 54 */     worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f).func_181675_d();
/* 55 */     worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f).func_181675_d();
/* 56 */     worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f).func_181675_d();
/* 57 */     tessellator.func_78381_a();
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmar\\utils\render\Render3DUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */