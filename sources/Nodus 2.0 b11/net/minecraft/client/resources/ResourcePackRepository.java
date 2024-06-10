/*   1:    */ package net.minecraft.client.resources;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.ImmutableList;
/*   4:    */ import com.google.common.collect.Lists;
/*   5:    */ import com.google.common.collect.Maps;
/*   6:    */ import java.awt.image.BufferedImage;
/*   7:    */ import java.io.Closeable;
/*   8:    */ import java.io.File;
/*   9:    */ import java.io.FileFilter;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.Arrays;
/*  13:    */ import java.util.Collections;
/*  14:    */ import java.util.HashMap;
/*  15:    */ import java.util.Iterator;
/*  16:    */ import java.util.List;
/*  17:    */ import net.minecraft.client.Minecraft;
/*  18:    */ import net.minecraft.client.gui.GuiScreenWorking;
/*  19:    */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*  20:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  21:    */ import net.minecraft.client.resources.data.IMetadataSerializer;
/*  22:    */ import net.minecraft.client.resources.data.PackMetadataSection;
/*  23:    */ import net.minecraft.client.settings.GameSettings;
/*  24:    */ import net.minecraft.util.EnumChatFormatting;
/*  25:    */ import net.minecraft.util.HttpUtil;
/*  26:    */ import net.minecraft.util.HttpUtil.DownloadListener;
/*  27:    */ import net.minecraft.util.ResourceLocation;
/*  28:    */ import net.minecraft.util.Session;
/*  29:    */ import org.apache.commons.io.IOUtils;
/*  30:    */ 
/*  31:    */ public class ResourcePackRepository
/*  32:    */ {
/*  33: 31 */   protected static final FileFilter resourcePackFilter = new FileFilter()
/*  34:    */   {
/*  35:    */     private static final String __OBFID = "CL_00001088";
/*  36:    */     
/*  37:    */     public boolean accept(File par1File)
/*  38:    */     {
/*  39: 36 */       boolean var2 = (par1File.isFile()) && (par1File.getName().endsWith(".zip"));
/*  40: 37 */       boolean var3 = (par1File.isDirectory()) && (new File(par1File, "pack.mcmeta").isFile());
/*  41: 38 */       return (var2) || (var3);
/*  42:    */     }
/*  43:    */   };
/*  44:    */   private final File dirResourcepacks;
/*  45:    */   public final IResourcePack rprDefaultResourcePack;
/*  46:    */   private final File field_148534_e;
/*  47:    */   public final IMetadataSerializer rprMetadataSerializer;
/*  48:    */   private IResourcePack field_148532_f;
/*  49:    */   private boolean field_148533_g;
/*  50: 47 */   private List repositoryEntriesAll = Lists.newArrayList();
/*  51: 48 */   private List repositoryEntries = Lists.newArrayList();
/*  52:    */   private static final String __OBFID = "CL_00001087";
/*  53:    */   
/*  54:    */   public ResourcePackRepository(File p_i45101_1_, File p_i45101_2_, IResourcePack p_i45101_3_, IMetadataSerializer p_i45101_4_, GameSettings p_i45101_5_)
/*  55:    */   {
/*  56: 53 */     this.dirResourcepacks = p_i45101_1_;
/*  57: 54 */     this.field_148534_e = p_i45101_2_;
/*  58: 55 */     this.rprDefaultResourcePack = p_i45101_3_;
/*  59: 56 */     this.rprMetadataSerializer = p_i45101_4_;
/*  60: 57 */     fixDirResourcepacks();
/*  61: 58 */     updateRepositoryEntriesAll();
/*  62: 59 */     Iterator var6 = p_i45101_5_.resourcePacks.iterator();
/*  63: 61 */     while (var6.hasNext())
/*  64:    */     {
/*  65: 63 */       String var7 = (String)var6.next();
/*  66: 64 */       Iterator var8 = this.repositoryEntriesAll.iterator();
/*  67: 66 */       while (var8.hasNext())
/*  68:    */       {
/*  69: 68 */         Entry var9 = (Entry)var8.next();
/*  70: 70 */         if (var9.getResourcePackName().equals(var7))
/*  71:    */         {
/*  72: 72 */           this.repositoryEntries.add(var9);
/*  73: 73 */           break;
/*  74:    */         }
/*  75:    */       }
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   private void fixDirResourcepacks()
/*  80:    */   {
/*  81: 81 */     if (!this.dirResourcepacks.isDirectory())
/*  82:    */     {
/*  83: 83 */       this.dirResourcepacks.delete();
/*  84: 84 */       this.dirResourcepacks.mkdirs();
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   private List getResourcePackFiles()
/*  89:    */   {
/*  90: 90 */     return this.dirResourcepacks.isDirectory() ? Arrays.asList(this.dirResourcepacks.listFiles(resourcePackFilter)) : Collections.emptyList();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void updateRepositoryEntriesAll()
/*  94:    */   {
/*  95: 95 */     ArrayList var1 = Lists.newArrayList();
/*  96: 96 */     Iterator var2 = getResourcePackFiles().iterator();
/*  97: 98 */     while (var2.hasNext())
/*  98:    */     {
/*  99:100 */       File var3 = (File)var2.next();
/* 100:101 */       Entry var4 = new Entry(var3, null);
/* 101:103 */       if (!this.repositoryEntriesAll.contains(var4))
/* 102:    */       {
/* 103:    */         try
/* 104:    */         {
/* 105:107 */           var4.updateResourcePack();
/* 106:108 */           var1.add(var4);
/* 107:    */         }
/* 108:    */         catch (Exception var6)
/* 109:    */         {
/* 110:112 */           var1.remove(var4);
/* 111:    */         }
/* 112:    */       }
/* 113:    */       else
/* 114:    */       {
/* 115:117 */         int var5 = this.repositoryEntriesAll.indexOf(var4);
/* 116:119 */         if ((var5 > -1) && (var5 < this.repositoryEntriesAll.size())) {
/* 117:121 */           var1.add(this.repositoryEntriesAll.get(var5));
/* 118:    */         }
/* 119:    */       }
/* 120:    */     }
/* 121:126 */     this.repositoryEntriesAll.removeAll(var1);
/* 122:127 */     var2 = this.repositoryEntriesAll.iterator();
/* 123:129 */     while (var2.hasNext())
/* 124:    */     {
/* 125:131 */       Entry var7 = (Entry)var2.next();
/* 126:132 */       var7.closeResourcePack();
/* 127:    */     }
/* 128:135 */     this.repositoryEntriesAll = var1;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public List getRepositoryEntriesAll()
/* 132:    */   {
/* 133:140 */     return ImmutableList.copyOf(this.repositoryEntriesAll);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public List getRepositoryEntries()
/* 137:    */   {
/* 138:145 */     return ImmutableList.copyOf(this.repositoryEntries);
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void func_148527_a(List p_148527_1_)
/* 142:    */   {
/* 143:150 */     this.repositoryEntries.clear();
/* 144:151 */     this.repositoryEntries.addAll(p_148527_1_);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public File getDirResourcepacks()
/* 148:    */   {
/* 149:156 */     return this.dirResourcepacks;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void func_148526_a(String p_148526_1_)
/* 153:    */   {
/* 154:161 */     String var2 = p_148526_1_.substring(p_148526_1_.lastIndexOf("/") + 1);
/* 155:163 */     if (var2.contains("?")) {
/* 156:165 */       var2 = var2.substring(0, var2.indexOf("?"));
/* 157:    */     }
/* 158:168 */     if (var2.endsWith(".zip"))
/* 159:    */     {
/* 160:170 */       File var3 = new File(this.field_148534_e, var2.replaceAll("\\W", ""));
/* 161:171 */       func_148529_f();
/* 162:172 */       func_148528_a(p_148526_1_, var3);
/* 163:    */     }
/* 164:    */   }
/* 165:    */   
/* 166:    */   private void func_148528_a(String p_148528_1_, File p_148528_2_)
/* 167:    */   {
/* 168:178 */     HashMap var3 = Maps.newHashMap();
/* 169:179 */     GuiScreenWorking var4 = new GuiScreenWorking();
/* 170:180 */     var3.put("X-Minecraft-Username", Minecraft.getMinecraft().getSession().getUsername());
/* 171:181 */     var3.put("X-Minecraft-UUID", Minecraft.getMinecraft().getSession().getPlayerID());
/* 172:182 */     var3.put("X-Minecraft-Version", "1.7.2");
/* 173:183 */     this.field_148533_g = true;
/* 174:184 */     Minecraft.getMinecraft().displayGuiScreen(var4);
/* 175:185 */     HttpUtil.func_151223_a(p_148528_2_, p_148528_1_, new HttpUtil.DownloadListener()
/* 176:    */     {
/* 177:    */       private static final String __OBFID = "CL_00001089";
/* 178:    */       
/* 179:    */       public void func_148522_a(File p_148522_1_)
/* 180:    */       {
/* 181:190 */         if (ResourcePackRepository.this.field_148533_g)
/* 182:    */         {
/* 183:192 */           ResourcePackRepository.this.field_148533_g = false;
/* 184:193 */           ResourcePackRepository.this.field_148532_f = new FileResourcePack(p_148522_1_);
/* 185:194 */           Minecraft.getMinecraft().scheduleResourcesRefresh();
/* 186:    */         }
/* 187:    */       }
/* 188:197 */     }, var3, 52428800, var4, Minecraft.getMinecraft().getProxy());
/* 189:    */   }
/* 190:    */   
/* 191:    */   public IResourcePack func_148530_e()
/* 192:    */   {
/* 193:202 */     return this.field_148532_f;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void func_148529_f()
/* 197:    */   {
/* 198:207 */     this.field_148532_f = null;
/* 199:208 */     this.field_148533_g = false;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public class Entry
/* 203:    */   {
/* 204:    */     private final File resourcePackFile;
/* 205:    */     private IResourcePack reResourcePack;
/* 206:    */     private PackMetadataSection rePackMetadataSection;
/* 207:    */     private BufferedImage texturePackIcon;
/* 208:    */     private ResourceLocation locationTexturePackIcon;
/* 209:    */     private static final String __OBFID = "CL_00001090";
/* 210:    */     
/* 211:    */     private Entry(File par2File)
/* 212:    */     {
/* 213:222 */       this.resourcePackFile = par2File;
/* 214:    */     }
/* 215:    */     
/* 216:    */     public void updateResourcePack()
/* 217:    */       throws IOException
/* 218:    */     {
/* 219:227 */       this.reResourcePack = (this.resourcePackFile.isDirectory() ? new FolderResourcePack(this.resourcePackFile) : new FileResourcePack(this.resourcePackFile));
/* 220:228 */       this.rePackMetadataSection = ((PackMetadataSection)this.reResourcePack.getPackMetadata(ResourcePackRepository.this.rprMetadataSerializer, "pack"));
/* 221:    */       try
/* 222:    */       {
/* 223:232 */         this.texturePackIcon = this.reResourcePack.getPackImage();
/* 224:    */       }
/* 225:    */       catch (IOException localIOException) {}
/* 226:239 */       if (this.texturePackIcon == null) {
/* 227:241 */         this.texturePackIcon = ResourcePackRepository.this.rprDefaultResourcePack.getPackImage();
/* 228:    */       }
/* 229:244 */       closeResourcePack();
/* 230:    */     }
/* 231:    */     
/* 232:    */     public void bindTexturePackIcon(TextureManager par1TextureManager)
/* 233:    */     {
/* 234:249 */       if (this.locationTexturePackIcon == null) {
/* 235:251 */         this.locationTexturePackIcon = par1TextureManager.getDynamicTextureLocation("texturepackicon", new DynamicTexture(this.texturePackIcon));
/* 236:    */       }
/* 237:254 */       par1TextureManager.bindTexture(this.locationTexturePackIcon);
/* 238:    */     }
/* 239:    */     
/* 240:    */     public void closeResourcePack()
/* 241:    */     {
/* 242:259 */       if ((this.reResourcePack instanceof Closeable)) {
/* 243:261 */         IOUtils.closeQuietly((Closeable)this.reResourcePack);
/* 244:    */       }
/* 245:    */     }
/* 246:    */     
/* 247:    */     public IResourcePack getResourcePack()
/* 248:    */     {
/* 249:267 */       return this.reResourcePack;
/* 250:    */     }
/* 251:    */     
/* 252:    */     public String getResourcePackName()
/* 253:    */     {
/* 254:272 */       return this.reResourcePack.getPackName();
/* 255:    */     }
/* 256:    */     
/* 257:    */     public String getTexturePackDescription()
/* 258:    */     {
/* 259:277 */       return this.rePackMetadataSection == null ? EnumChatFormatting.RED + "Invalid pack.mcmeta (or missing 'pack' section)" : this.rePackMetadataSection.getPackDescription();
/* 260:    */     }
/* 261:    */     
/* 262:    */     public boolean equals(Object par1Obj)
/* 263:    */     {
/* 264:282 */       return this == par1Obj;
/* 265:    */     }
/* 266:    */     
/* 267:    */     public int hashCode()
/* 268:    */     {
/* 269:287 */       return toString().hashCode();
/* 270:    */     }
/* 271:    */     
/* 272:    */     public String toString()
/* 273:    */     {
/* 274:292 */       return String.format("%s:%s:%d", new Object[] { this.resourcePackFile.getName(), this.resourcePackFile.isDirectory() ? "folder" : "zip", Long.valueOf(this.resourcePackFile.lastModified()) });
/* 275:    */     }
/* 276:    */     
/* 277:    */     Entry(File par2File, Object par3ResourcePackRepositoryFilter)
/* 278:    */     {
/* 279:297 */       this(par2File);
/* 280:    */     }
/* 281:    */   }
/* 282:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.ResourcePackRepository
 * JD-Core Version:    0.7.0.1
 */