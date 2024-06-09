/*     */ package net.minecraft.client.resources;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.resources.data.IMetadataSection;
/*     */ import net.minecraft.client.resources.data.IMetadataSerializer;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ public class SimpleResource
/*     */   implements IResource
/*     */ {
/*  18 */   private final Map<String, IMetadataSection> mapMetadataSections = Maps.newHashMap();
/*     */   
/*     */   private final String resourcePackName;
/*     */   private final ResourceLocation srResourceLocation;
/*     */   private final InputStream resourceInputStream;
/*     */   private final InputStream mcmetaInputStream;
/*     */   private final IMetadataSerializer srMetadataSerializer;
/*     */   private boolean mcmetaJsonChecked;
/*     */   private JsonObject mcmetaJson;
/*     */   
/*     */   public SimpleResource(String resourcePackNameIn, ResourceLocation srResourceLocationIn, InputStream resourceInputStreamIn, InputStream mcmetaInputStreamIn, IMetadataSerializer srMetadataSerializerIn) {
/*  29 */     this.resourcePackName = resourcePackNameIn;
/*  30 */     this.srResourceLocation = srResourceLocationIn;
/*  31 */     this.resourceInputStream = resourceInputStreamIn;
/*  32 */     this.mcmetaInputStream = mcmetaInputStreamIn;
/*  33 */     this.srMetadataSerializer = srMetadataSerializerIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getResourceLocation() {
/*  38 */     return this.srResourceLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream getInputStream() {
/*  43 */     return this.resourceInputStream;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasMetadata() {
/*  48 */     return (this.mcmetaInputStream != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends IMetadataSection> T getMetadata(String p_110526_1_) {
/*  53 */     if (!hasMetadata())
/*     */     {
/*  55 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  59 */     if (this.mcmetaJson == null && !this.mcmetaJsonChecked) {
/*     */       
/*  61 */       this.mcmetaJsonChecked = true;
/*  62 */       BufferedReader bufferedreader = null;
/*     */ 
/*     */       
/*     */       try {
/*  66 */         bufferedreader = new BufferedReader(new InputStreamReader(this.mcmetaInputStream));
/*  67 */         this.mcmetaJson = (new JsonParser()).parse(bufferedreader).getAsJsonObject();
/*     */       }
/*     */       finally {
/*     */         
/*  71 */         IOUtils.closeQuietly(bufferedreader);
/*     */       } 
/*     */     } 
/*     */     
/*  75 */     IMetadataSection iMetadataSection = this.mapMetadataSections.get(p_110526_1_);
/*     */     
/*  77 */     if (iMetadataSection == null)
/*     */     {
/*  79 */       iMetadataSection = this.srMetadataSerializer.parseMetadataSection(p_110526_1_, this.mcmetaJson);
/*     */     }
/*     */     
/*  82 */     return (T)iMetadataSection;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getResourcePackName() {
/*  88 */     return this.resourcePackName;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  93 */     if (this == p_equals_1_)
/*     */     {
/*  95 */       return true;
/*     */     }
/*  97 */     if (!(p_equals_1_ instanceof SimpleResource))
/*     */     {
/*  99 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 103 */     SimpleResource simpleresource = (SimpleResource)p_equals_1_;
/*     */     
/* 105 */     if (this.srResourceLocation != null) {
/*     */       
/* 107 */       if (!this.srResourceLocation.equals(simpleresource.srResourceLocation))
/*     */       {
/* 109 */         return false;
/*     */       }
/*     */     }
/* 112 */     else if (simpleresource.srResourceLocation != null) {
/*     */       
/* 114 */       return false;
/*     */     } 
/*     */     
/* 117 */     if (this.resourcePackName != null) {
/*     */       
/* 119 */       if (!this.resourcePackName.equals(simpleresource.resourcePackName))
/*     */       {
/* 121 */         return false;
/*     */       }
/*     */     }
/* 124 */     else if (simpleresource.resourcePackName != null) {
/*     */       
/* 126 */       return false;
/*     */     } 
/*     */     
/* 129 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 135 */     int i = (this.resourcePackName != null) ? this.resourcePackName.hashCode() : 0;
/* 136 */     i = 31 * i + ((this.srResourceLocation != null) ? this.srResourceLocation.hashCode() : 0);
/* 137 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\resources\SimpleResource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */