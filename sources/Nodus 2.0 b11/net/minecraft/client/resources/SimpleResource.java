/*  1:   */ package net.minecraft.client.resources;
/*  2:   */ 
/*  3:   */ import com.google.common.collect.Maps;
/*  4:   */ import com.google.gson.JsonElement;
/*  5:   */ import com.google.gson.JsonObject;
/*  6:   */ import com.google.gson.JsonParser;
/*  7:   */ import java.io.BufferedReader;
/*  8:   */ import java.io.InputStream;
/*  9:   */ import java.io.InputStreamReader;
/* 10:   */ import java.util.Map;
/* 11:   */ import net.minecraft.client.resources.data.IMetadataSection;
/* 12:   */ import net.minecraft.client.resources.data.IMetadataSerializer;
/* 13:   */ import net.minecraft.util.ResourceLocation;
/* 14:   */ import org.apache.commons.io.IOUtils;
/* 15:   */ 
/* 16:   */ public class SimpleResource
/* 17:   */   implements IResource
/* 18:   */ {
/* 19:17 */   private final Map mapMetadataSections = Maps.newHashMap();
/* 20:   */   private final ResourceLocation srResourceLocation;
/* 21:   */   private final InputStream resourceInputStream;
/* 22:   */   private final InputStream mcmetaInputStream;
/* 23:   */   private final IMetadataSerializer srMetadataSerializer;
/* 24:   */   private boolean mcmetaJsonChecked;
/* 25:   */   private JsonObject mcmetaJson;
/* 26:   */   private static final String __OBFID = "CL_00001093";
/* 27:   */   
/* 28:   */   public SimpleResource(ResourceLocation par1ResourceLocation, InputStream par2InputStream, InputStream par3InputStream, IMetadataSerializer par4MetadataSerializer)
/* 29:   */   {
/* 30:28 */     this.srResourceLocation = par1ResourceLocation;
/* 31:29 */     this.resourceInputStream = par2InputStream;
/* 32:30 */     this.mcmetaInputStream = par3InputStream;
/* 33:31 */     this.srMetadataSerializer = par4MetadataSerializer;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public InputStream getInputStream()
/* 37:   */   {
/* 38:36 */     return this.resourceInputStream;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public boolean hasMetadata()
/* 42:   */   {
/* 43:41 */     return this.mcmetaInputStream != null;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public IMetadataSection getMetadata(String par1Str)
/* 47:   */   {
/* 48:46 */     if (!hasMetadata()) {
/* 49:48 */       return null;
/* 50:   */     }
/* 51:52 */     if ((this.mcmetaJson == null) && (!this.mcmetaJsonChecked))
/* 52:   */     {
/* 53:54 */       this.mcmetaJsonChecked = true;
/* 54:55 */       BufferedReader var2 = null;
/* 55:   */       try
/* 56:   */       {
/* 57:59 */         var2 = new BufferedReader(new InputStreamReader(this.mcmetaInputStream));
/* 58:60 */         this.mcmetaJson = new JsonParser().parse(var2).getAsJsonObject();
/* 59:   */       }
/* 60:   */       finally
/* 61:   */       {
/* 62:64 */         IOUtils.closeQuietly(var2);
/* 63:   */       }
/* 64:   */     }
/* 65:68 */     IMetadataSection var6 = (IMetadataSection)this.mapMetadataSections.get(par1Str);
/* 66:70 */     if (var6 == null) {
/* 67:72 */       var6 = this.srMetadataSerializer.parseMetadataSection(par1Str, this.mcmetaJson);
/* 68:   */     }
/* 69:75 */     return var6;
/* 70:   */   }
/* 71:   */   
/* 72:   */   public boolean equals(Object par1Obj)
/* 73:   */   {
/* 74:81 */     if (this == par1Obj) {
/* 75:83 */       return true;
/* 76:   */     }
/* 77:85 */     if ((par1Obj instanceof SimpleResource))
/* 78:   */     {
/* 79:87 */       SimpleResource var2 = (SimpleResource)par1Obj;
/* 80:88 */       return var2.srResourceLocation == null ? true : this.srResourceLocation != null ? this.srResourceLocation.equals(var2.srResourceLocation) : false;
/* 81:   */     }
/* 82:92 */     return false;
/* 83:   */   }
/* 84:   */   
/* 85:   */   public int hashCode()
/* 86:   */   {
/* 87:98 */     return this.srResourceLocation == null ? 0 : this.srResourceLocation.hashCode();
/* 88:   */   }
/* 89:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.SimpleResource
 * JD-Core Version:    0.7.0.1
 */