/*  1:   */ package net.minecraft.client.resources;
/*  2:   */ 
/*  3:   */ import com.google.common.base.Charsets;
/*  4:   */ import com.google.gson.JsonElement;
/*  5:   */ import com.google.gson.JsonObject;
/*  6:   */ import com.google.gson.JsonParser;
/*  7:   */ import java.awt.image.BufferedImage;
/*  8:   */ import java.io.BufferedReader;
/*  9:   */ import java.io.File;
/* 10:   */ import java.io.IOException;
/* 11:   */ import java.io.InputStream;
/* 12:   */ import java.io.InputStreamReader;
/* 13:   */ import java.net.URI;
/* 14:   */ import javax.imageio.ImageIO;
/* 15:   */ import net.minecraft.client.resources.data.IMetadataSection;
/* 16:   */ import net.minecraft.client.resources.data.IMetadataSerializer;
/* 17:   */ import net.minecraft.util.ResourceLocation;
/* 18:   */ import org.apache.commons.io.IOUtils;
/* 19:   */ import org.apache.logging.log4j.LogManager;
/* 20:   */ import org.apache.logging.log4j.Logger;
/* 21:   */ 
/* 22:   */ public abstract class AbstractResourcePack
/* 23:   */   implements IResourcePack
/* 24:   */ {
/* 25:22 */   private static final Logger resourceLog = ;
/* 26:   */   public final File resourcePackFile;
/* 27:   */   private static final String __OBFID = "CL_00001072";
/* 28:   */   
/* 29:   */   public AbstractResourcePack(File par1File)
/* 30:   */   {
/* 31:28 */     this.resourcePackFile = par1File;
/* 32:   */   }
/* 33:   */   
/* 34:   */   private static String locationToName(ResourceLocation par0ResourceLocation)
/* 35:   */   {
/* 36:33 */     return String.format("%s/%s/%s", new Object[] { "assets", par0ResourceLocation.getResourceDomain(), par0ResourceLocation.getResourcePath() });
/* 37:   */   }
/* 38:   */   
/* 39:   */   protected static String getRelativeName(File par0File, File par1File)
/* 40:   */   {
/* 41:38 */     return par0File.toURI().relativize(par1File.toURI()).getPath();
/* 42:   */   }
/* 43:   */   
/* 44:   */   public InputStream getInputStream(ResourceLocation par1ResourceLocation)
/* 45:   */     throws IOException
/* 46:   */   {
/* 47:43 */     return getInputStreamByName(locationToName(par1ResourceLocation));
/* 48:   */   }
/* 49:   */   
/* 50:   */   public boolean resourceExists(ResourceLocation par1ResourceLocation)
/* 51:   */   {
/* 52:48 */     return hasResourceName(locationToName(par1ResourceLocation));
/* 53:   */   }
/* 54:   */   
/* 55:   */   protected abstract InputStream getInputStreamByName(String paramString)
/* 56:   */     throws IOException;
/* 57:   */   
/* 58:   */   protected abstract boolean hasResourceName(String paramString);
/* 59:   */   
/* 60:   */   protected void logNameNotLowercase(String par1Str)
/* 61:   */   {
/* 62:57 */     resourceLog.warn("ResourcePack: ignored non-lowercase namespace: %s in %s", new Object[] { par1Str, this.resourcePackFile });
/* 63:   */   }
/* 64:   */   
/* 65:   */   public IMetadataSection getPackMetadata(IMetadataSerializer par1MetadataSerializer, String par2Str)
/* 66:   */     throws IOException
/* 67:   */   {
/* 68:62 */     return readMetadata(par1MetadataSerializer, getInputStreamByName("pack.mcmeta"), par2Str);
/* 69:   */   }
/* 70:   */   
/* 71:   */   static IMetadataSection readMetadata(IMetadataSerializer par0MetadataSerializer, InputStream par1InputStream, String par2Str)
/* 72:   */   {
/* 73:67 */     JsonObject var3 = null;
/* 74:68 */     BufferedReader var4 = null;
/* 75:   */     try
/* 76:   */     {
/* 77:72 */       var4 = new BufferedReader(new InputStreamReader(par1InputStream, Charsets.UTF_8));
/* 78:73 */       var3 = new JsonParser().parse(var4).getAsJsonObject();
/* 79:   */     }
/* 80:   */     finally
/* 81:   */     {
/* 82:77 */       IOUtils.closeQuietly(var4);
/* 83:   */     }
/* 84:80 */     return par0MetadataSerializer.parseMetadataSection(par2Str, var3);
/* 85:   */   }
/* 86:   */   
/* 87:   */   public BufferedImage getPackImage()
/* 88:   */     throws IOException
/* 89:   */   {
/* 90:85 */     return ImageIO.read(getInputStreamByName("pack.png"));
/* 91:   */   }
/* 92:   */   
/* 93:   */   public String getPackName()
/* 94:   */   {
/* 95:90 */     return this.resourcePackFile.getName();
/* 96:   */   }
/* 97:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.AbstractResourcePack
 * JD-Core Version:    0.7.0.1
 */