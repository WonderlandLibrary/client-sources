/*   1:    */ package net.minecraft.client.resources;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.ImmutableSet;
/*   4:    */ import com.google.common.collect.Maps;
/*   5:    */ import java.awt.image.BufferedImage;
/*   6:    */ import java.io.File;
/*   7:    */ import java.io.FileInputStream;
/*   8:    */ import java.io.FileNotFoundException;
/*   9:    */ import java.io.IOException;
/*  10:    */ import java.io.InputStream;
/*  11:    */ import java.util.Map;
/*  12:    */ import java.util.Set;
/*  13:    */ import javax.imageio.ImageIO;
/*  14:    */ import net.minecraft.client.resources.data.IMetadataSection;
/*  15:    */ import net.minecraft.client.resources.data.IMetadataSerializer;
/*  16:    */ import net.minecraft.util.ResourceLocation;
/*  17:    */ 
/*  18:    */ public class DefaultResourcePack
/*  19:    */   implements IResourcePack
/*  20:    */ {
/*  21: 20 */   public static final Set defaultResourceDomains = ImmutableSet.of("minecraft");
/*  22: 21 */   private final Map mapResourceFiles = Maps.newHashMap();
/*  23:    */   private final File fileAssets;
/*  24:    */   private static final String __OBFID = "CL_00001073";
/*  25:    */   
/*  26:    */   public DefaultResourcePack(File par1File)
/*  27:    */   {
/*  28: 27 */     this.fileAssets = par1File;
/*  29: 28 */     readAssetsDir(this.fileAssets);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public InputStream getInputStream(ResourceLocation par1ResourceLocation)
/*  33:    */     throws IOException
/*  34:    */   {
/*  35: 33 */     InputStream var2 = getResourceStream(par1ResourceLocation);
/*  36: 35 */     if (var2 != null) {
/*  37: 37 */       return var2;
/*  38:    */     }
/*  39: 41 */     File var3 = (File)this.mapResourceFiles.get(par1ResourceLocation.toString());
/*  40: 43 */     if (var3 != null) {
/*  41: 45 */       return new FileInputStream(var3);
/*  42:    */     }
/*  43: 49 */     throw new FileNotFoundException(par1ResourceLocation.getResourcePath());
/*  44:    */   }
/*  45:    */   
/*  46:    */   private InputStream getResourceStream(ResourceLocation par1ResourceLocation)
/*  47:    */   {
/*  48: 56 */     return DefaultResourcePack.class.getResourceAsStream("/assets/minecraft/" + par1ResourceLocation.getResourcePath());
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void addResourceFile(String par1Str, File par2File)
/*  52:    */   {
/*  53: 61 */     this.mapResourceFiles.put(new ResourceLocation(par1Str).toString(), par2File);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public boolean resourceExists(ResourceLocation par1ResourceLocation)
/*  57:    */   {
/*  58: 66 */     return (getResourceStream(par1ResourceLocation) != null) || (this.mapResourceFiles.containsKey(par1ResourceLocation.toString()));
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Set getResourceDomains()
/*  62:    */   {
/*  63: 71 */     return defaultResourceDomains;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void readAssetsDir(File par1File)
/*  67:    */   {
/*  68: 76 */     if (par1File.isDirectory())
/*  69:    */     {
/*  70: 78 */       File[] var2 = par1File.listFiles();
/*  71: 79 */       int var3 = var2.length;
/*  72: 81 */       for (int var4 = 0; var4 < var3; var4++)
/*  73:    */       {
/*  74: 83 */         File var5 = var2[var4];
/*  75: 84 */         readAssetsDir(var5);
/*  76:    */       }
/*  77:    */     }
/*  78:    */     else
/*  79:    */     {
/*  80: 89 */       addResourceFile(AbstractResourcePack.getRelativeName(this.fileAssets, par1File), par1File);
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public IMetadataSection getPackMetadata(IMetadataSerializer par1MetadataSerializer, String par2Str)
/*  85:    */     throws IOException
/*  86:    */   {
/*  87:    */     try
/*  88:    */     {
/*  89: 97 */       return AbstractResourcePack.readMetadata(par1MetadataSerializer, new FileInputStream(new File(this.fileAssets, "pack.mcmeta")), par2Str);
/*  90:    */     }
/*  91:    */     catch (FileNotFoundException var4) {}
/*  92:101 */     return null;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public BufferedImage getPackImage()
/*  96:    */     throws IOException
/*  97:    */   {
/*  98:107 */     return ImageIO.read(DefaultResourcePack.class.getResourceAsStream("/" + new ResourceLocation("pack.png").getResourcePath()));
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String getPackName()
/* 102:    */   {
/* 103:112 */     return "Default";
/* 104:    */   }
/* 105:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.DefaultResourcePack
 * JD-Core Version:    0.7.0.1
 */