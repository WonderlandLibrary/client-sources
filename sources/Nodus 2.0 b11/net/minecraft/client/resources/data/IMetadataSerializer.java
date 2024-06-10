/*  1:   */ package net.minecraft.client.resources.data;
/*  2:   */ 
/*  3:   */ import com.google.gson.Gson;
/*  4:   */ import com.google.gson.GsonBuilder;
/*  5:   */ import com.google.gson.JsonElement;
/*  6:   */ import com.google.gson.JsonObject;
/*  7:   */ import net.minecraft.util.IRegistry;
/*  8:   */ import net.minecraft.util.RegistrySimple;
/*  9:   */ 
/* 10:   */ public class IMetadataSerializer
/* 11:   */ {
/* 12:11 */   private final IRegistry metadataSectionSerializerRegistry = new RegistrySimple();
/* 13:12 */   private final GsonBuilder gsonBuilder = new GsonBuilder();
/* 14:   */   private Gson gson;
/* 15:   */   private static final String __OBFID = "CL_00001101";
/* 16:   */   
/* 17:   */   public void registerMetadataSectionType(IMetadataSectionSerializer par1MetadataSectionSerializer, Class par2Class)
/* 18:   */   {
/* 19:22 */     this.metadataSectionSerializerRegistry.putObject(par1MetadataSectionSerializer.getSectionName(), new Registration(par1MetadataSectionSerializer, par2Class, null));
/* 20:23 */     this.gsonBuilder.registerTypeAdapter(par2Class, par1MetadataSectionSerializer);
/* 21:24 */     this.gson = null;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public IMetadataSection parseMetadataSection(String par1Str, JsonObject par2JsonObject)
/* 25:   */   {
/* 26:29 */     if (par1Str == null) {
/* 27:31 */       throw new IllegalArgumentException("Metadata section name cannot be null");
/* 28:   */     }
/* 29:33 */     if (!par2JsonObject.has(par1Str)) {
/* 30:35 */       return null;
/* 31:   */     }
/* 32:37 */     if (!par2JsonObject.get(par1Str).isJsonObject()) {
/* 33:39 */       throw new IllegalArgumentException("Invalid metadata for '" + par1Str + "' - expected object, found " + par2JsonObject.get(par1Str));
/* 34:   */     }
/* 35:43 */     Registration var3 = (Registration)this.metadataSectionSerializerRegistry.getObject(par1Str);
/* 36:45 */     if (var3 == null) {
/* 37:47 */       throw new IllegalArgumentException("Don't know how to handle metadata section '" + par1Str + "'");
/* 38:   */     }
/* 39:51 */     return (IMetadataSection)getGson().fromJson(par2JsonObject.getAsJsonObject(par1Str), var3.field_110500_b);
/* 40:   */   }
/* 41:   */   
/* 42:   */   private Gson getGson()
/* 43:   */   {
/* 44:61 */     if (this.gson == null) {
/* 45:63 */       this.gson = this.gsonBuilder.create();
/* 46:   */     }
/* 47:66 */     return this.gson;
/* 48:   */   }
/* 49:   */   
/* 50:   */   class Registration
/* 51:   */   {
/* 52:   */     final IMetadataSectionSerializer field_110502_a;
/* 53:   */     final Class field_110500_b;
/* 54:   */     private static final String __OBFID = "CL_00001103";
/* 55:   */     
/* 56:   */     private Registration(IMetadataSectionSerializer par2MetadataSectionSerializer, Class par3Class)
/* 57:   */     {
/* 58:77 */       this.field_110502_a = par2MetadataSectionSerializer;
/* 59:78 */       this.field_110500_b = par3Class;
/* 60:   */     }
/* 61:   */     
/* 62:   */     Registration(IMetadataSectionSerializer par2MetadataSectionSerializer, Class par3Class, Object par4MetadataSerializerEmptyAnon)
/* 63:   */     {
/* 64:83 */       this(par2MetadataSectionSerializer, par3Class);
/* 65:   */     }
/* 66:   */   }
/* 67:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.data.IMetadataSerializer
 * JD-Core Version:    0.7.0.1
 */