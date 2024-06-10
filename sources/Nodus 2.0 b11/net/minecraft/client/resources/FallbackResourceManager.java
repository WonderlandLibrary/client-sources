/*  1:   */ package net.minecraft.client.resources;
/*  2:   */ 
/*  3:   */ import com.google.common.collect.Lists;
/*  4:   */ import java.io.FileNotFoundException;
/*  5:   */ import java.io.IOException;
/*  6:   */ import java.io.InputStream;
/*  7:   */ import java.util.ArrayList;
/*  8:   */ import java.util.Iterator;
/*  9:   */ import java.util.List;
/* 10:   */ import java.util.Set;
/* 11:   */ import net.minecraft.client.resources.data.IMetadataSerializer;
/* 12:   */ import net.minecraft.util.ResourceLocation;
/* 13:   */ 
/* 14:   */ public class FallbackResourceManager
/* 15:   */   implements IResourceManager
/* 16:   */ {
/* 17:16 */   protected final List resourcePacks = new ArrayList();
/* 18:   */   private final IMetadataSerializer frmMetadataSerializer;
/* 19:   */   private static final String __OBFID = "CL_00001074";
/* 20:   */   
/* 21:   */   public FallbackResourceManager(IMetadataSerializer par1MetadataSerializer)
/* 22:   */   {
/* 23:22 */     this.frmMetadataSerializer = par1MetadataSerializer;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void addResourcePack(IResourcePack par1ResourcePack)
/* 27:   */   {
/* 28:27 */     this.resourcePacks.add(par1ResourcePack);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Set getResourceDomains()
/* 32:   */   {
/* 33:32 */     return null;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public IResource getResource(ResourceLocation par1ResourceLocation)
/* 37:   */     throws IOException
/* 38:   */   {
/* 39:37 */     IResourcePack var2 = null;
/* 40:38 */     ResourceLocation var3 = getLocationMcmeta(par1ResourceLocation);
/* 41:40 */     for (int var4 = this.resourcePacks.size() - 1; var4 >= 0; var4--)
/* 42:   */     {
/* 43:42 */       IResourcePack var5 = (IResourcePack)this.resourcePacks.get(var4);
/* 44:44 */       if ((var2 == null) && (var5.resourceExists(var3))) {
/* 45:46 */         var2 = var5;
/* 46:   */       }
/* 47:49 */       if (var5.resourceExists(par1ResourceLocation))
/* 48:   */       {
/* 49:51 */         InputStream var6 = null;
/* 50:53 */         if (var2 != null) {
/* 51:55 */           var6 = var2.getInputStream(var3);
/* 52:   */         }
/* 53:58 */         return new SimpleResource(par1ResourceLocation, var5.getInputStream(par1ResourceLocation), var6, this.frmMetadataSerializer);
/* 54:   */       }
/* 55:   */     }
/* 56:62 */     throw new FileNotFoundException(par1ResourceLocation.toString());
/* 57:   */   }
/* 58:   */   
/* 59:   */   public List getAllResources(ResourceLocation par1ResourceLocation)
/* 60:   */     throws IOException
/* 61:   */   {
/* 62:67 */     ArrayList var2 = Lists.newArrayList();
/* 63:68 */     ResourceLocation var3 = getLocationMcmeta(par1ResourceLocation);
/* 64:69 */     Iterator var4 = this.resourcePacks.iterator();
/* 65:71 */     while (var4.hasNext())
/* 66:   */     {
/* 67:73 */       IResourcePack var5 = (IResourcePack)var4.next();
/* 68:75 */       if (var5.resourceExists(par1ResourceLocation))
/* 69:   */       {
/* 70:77 */         InputStream var6 = var5.resourceExists(var3) ? var5.getInputStream(var3) : null;
/* 71:78 */         var2.add(new SimpleResource(par1ResourceLocation, var5.getInputStream(par1ResourceLocation), var6, this.frmMetadataSerializer));
/* 72:   */       }
/* 73:   */     }
/* 74:82 */     if (var2.isEmpty()) {
/* 75:84 */       throw new FileNotFoundException(par1ResourceLocation.toString());
/* 76:   */     }
/* 77:88 */     return var2;
/* 78:   */   }
/* 79:   */   
/* 80:   */   static ResourceLocation getLocationMcmeta(ResourceLocation par0ResourceLocation)
/* 81:   */   {
/* 82:94 */     return new ResourceLocation(par0ResourceLocation.getResourceDomain(), par0ResourceLocation.getResourcePath() + ".mcmeta");
/* 83:   */   }
/* 84:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.FallbackResourceManager
 * JD-Core Version:    0.7.0.1
 */