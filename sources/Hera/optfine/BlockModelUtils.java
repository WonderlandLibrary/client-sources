/*    */ package optfine;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*    */ import net.minecraft.client.renderer.block.model.BlockFaceUV;
/*    */ import net.minecraft.client.renderer.block.model.BlockPartFace;
/*    */ import net.minecraft.client.renderer.block.model.BlockPartRotation;
/*    */ import net.minecraft.client.renderer.block.model.FaceBakery;
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.client.resources.model.IBakedModel;
/*    */ import net.minecraft.client.resources.model.ModelRotation;
/*    */ import net.minecraft.client.resources.model.SimpleBakedModel;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import org.lwjgl.util.vector.Vector3f;
/*    */ 
/*    */ 
/*    */ public class BlockModelUtils
/*    */ {
/*    */   public static IBakedModel makeModelCube(String p_makeModelCube_0_, int p_makeModelCube_1_) {
/* 22 */     TextureAtlasSprite textureatlassprite = Config.getMinecraft().getTextureMapBlocks().getAtlasSprite(p_makeModelCube_0_);
/* 23 */     return makeModelCube(textureatlassprite, p_makeModelCube_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public static IBakedModel makeModelCube(TextureAtlasSprite p_makeModelCube_0_, int p_makeModelCube_1_) {
/* 28 */     List list = new ArrayList();
/* 29 */     EnumFacing[] aenumfacing = EnumFacing.values();
/* 30 */     List<List<BakedQuad>> list1 = new ArrayList(aenumfacing.length);
/*    */     
/* 32 */     for (int i = 0; i < aenumfacing.length; i++) {
/*    */       
/* 34 */       EnumFacing enumfacing = aenumfacing[i];
/* 35 */       List<BakedQuad> list2 = new ArrayList();
/* 36 */       list2.add(makeBakedQuad(enumfacing, p_makeModelCube_0_, p_makeModelCube_1_));
/* 37 */       list1.add(list2);
/*    */     } 
/*    */     
/* 40 */     return (IBakedModel)new SimpleBakedModel(list, list1, true, true, p_makeModelCube_0_, ItemCameraTransforms.DEFAULT);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static BakedQuad makeBakedQuad(EnumFacing p_makeBakedQuad_0_, TextureAtlasSprite p_makeBakedQuad_1_, int p_makeBakedQuad_2_) {
/* 46 */     Vector3f vector3f = new Vector3f(0.0F, 0.0F, 0.0F);
/* 47 */     Vector3f vector3f1 = new Vector3f(16.0F, 16.0F, 16.0F);
/* 48 */     BlockFaceUV blockfaceuv = new BlockFaceUV(new float[] { 0.0F, 0.0F, 16.0F, 16.0F }, 0);
/* 49 */     BlockPartFace blockpartface = new BlockPartFace(p_makeBakedQuad_0_, p_makeBakedQuad_2_, "#" + p_makeBakedQuad_0_.getName(), blockfaceuv);
/* 50 */     ModelRotation modelrotation = ModelRotation.X0_Y0;
/* 51 */     BlockPartRotation blockpartrotation = null;
/* 52 */     boolean flag = false;
/* 53 */     boolean flag1 = true;
/* 54 */     FaceBakery facebakery = new FaceBakery();
/* 55 */     BakedQuad bakedquad = facebakery.makeBakedQuad(vector3f, vector3f1, blockpartface, p_makeBakedQuad_1_, p_makeBakedQuad_0_, modelrotation, blockpartrotation, flag, flag1);
/* 56 */     return bakedquad;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\optfine\BlockModelUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */