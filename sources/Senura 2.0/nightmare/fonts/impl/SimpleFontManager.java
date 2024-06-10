/*    */ package nightmare.fonts.impl;
/*    */ 
/*    */ import java.awt.Font;
/*    */ import java.awt.FontFormatException;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.EnumMap;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.resources.IResource;
/*    */ import net.minecraft.client.resources.IResourceManager;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import nightmare.fonts.SneakyThrowing;
/*    */ import nightmare.fonts.api.FontFamily;
/*    */ import nightmare.fonts.api.FontManager;
/*    */ import nightmare.fonts.api.FontType;
/*    */ 
/*    */ public final class SimpleFontManager
/*    */   implements FontManager {
/*    */   private static final String FONT_DIRECTORY = "nightmare/fonts/";
/*    */   
/*    */   public static FontManager create() {
/* 22 */     return new SimpleFontManager();
/*    */   }
/*    */ 
/*    */   
/* 26 */   private final FontRegistry fonts = new FontRegistry();
/*    */ 
/*    */   
/*    */   public FontFamily fontFamily(FontType fontType) {
/* 30 */     return this.fonts.fontFamily(fontType);
/*    */   }
/*    */   
/*    */   private static final class FontRegistry extends EnumMap<FontType, FontFamily> {
/*    */     private static final long serialVersionUID = 1L;
/*    */     
/*    */     private FontRegistry() {
/* 37 */       super(FontType.class);
/*    */     }
/*    */     private FontFamily fontFamily(FontType fontType) {
/* 40 */       return computeIfAbsent(fontType, ignored -> {
/*    */             try {
/*    */               return SimpleFontFamily.create(fontType, readFontFromResources(fontType));
/* 43 */             } catch (IOException e) {
/*    */               throw SneakyThrowing.sneakyThrow(e);
/*    */             } 
/*    */           });
/*    */     }
/*    */     private static Font readFontFromResources(FontType fontType) throws IOException {
/*    */       IResource resource;
/* 50 */       IResourceManager resourceManager = Minecraft.func_71410_x().func_110442_L();
/* 51 */       ResourceLocation location = new ResourceLocation("nightmare/fonts/" + fontType.fileName());
/*    */ 
/*    */       
/*    */       try {
/* 55 */         resource = resourceManager.func_110536_a(location);
/* 56 */       } catch (IOException e) {
/* 57 */         throw new IOException("Couldn't find resource: " + location, e);
/*    */       } 
/*    */       
/* 60 */       try (InputStream resourceInputStream = resource.func_110527_b()) {
/* 61 */         return readFont(resourceInputStream);
/*    */       } 
/*    */     }
/*    */ 
/*    */     
/*    */     private static Font readFont(InputStream resource) {
/*    */       Font font;
/*    */       try {
/* 69 */         font = Font.createFont(0, resource);
/* 70 */       } catch (FontFormatException e) {
/* 71 */         throw new RuntimeException("Resource does not contain the required font tables for the specified format", e);
/* 72 */       } catch (IOException e) {
/* 73 */         throw new RuntimeException("Couldn't completely read font resource", e);
/*    */       } 
/*    */       
/* 76 */       return font;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\fonts\impl\SimpleFontManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */