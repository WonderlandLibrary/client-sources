/*   1:    */ package net.minecraft.client.resources;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Function;
/*   4:    */ import com.google.common.base.Joiner;
/*   5:    */ import com.google.common.collect.Iterables;
/*   6:    */ import com.google.common.collect.Lists;
/*   7:    */ import com.google.common.collect.Maps;
/*   8:    */ import com.google.common.collect.Sets;
/*   9:    */ import java.io.FileNotFoundException;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import java.util.Set;
/*  15:    */ import net.minecraft.client.resources.data.IMetadataSerializer;
/*  16:    */ import net.minecraft.util.ResourceLocation;
/*  17:    */ import org.apache.logging.log4j.LogManager;
/*  18:    */ import org.apache.logging.log4j.Logger;
/*  19:    */ 
/*  20:    */ public class SimpleReloadableResourceManager
/*  21:    */   implements IReloadableResourceManager
/*  22:    */ {
/*  23: 22 */   private static final Logger logger = ;
/*  24: 23 */   private static final Joiner joinerResourcePacks = Joiner.on(", ");
/*  25: 24 */   private final Map domainResourceManagers = Maps.newHashMap();
/*  26: 25 */   private final List reloadListeners = Lists.newArrayList();
/*  27: 26 */   private final Set setResourceDomains = Sets.newLinkedHashSet();
/*  28:    */   private final IMetadataSerializer rmMetadataSerializer;
/*  29:    */   private static final String __OBFID = "CL_00001091";
/*  30:    */   
/*  31:    */   public SimpleReloadableResourceManager(IMetadataSerializer par1MetadataSerializer)
/*  32:    */   {
/*  33: 32 */     this.rmMetadataSerializer = par1MetadataSerializer;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void reloadResourcePack(IResourcePack par1ResourcePack)
/*  37:    */   {
/*  38:    */     FallbackResourceManager var4;
/*  39: 39 */     for (Iterator var2 = par1ResourcePack.getResourceDomains().iterator(); var2.hasNext(); var4.addResourcePack(par1ResourcePack))
/*  40:    */     {
/*  41: 41 */       String var3 = (String)var2.next();
/*  42: 42 */       this.setResourceDomains.add(var3);
/*  43: 43 */       var4 = (FallbackResourceManager)this.domainResourceManagers.get(var3);
/*  44: 45 */       if (var4 == null)
/*  45:    */       {
/*  46: 47 */         var4 = new FallbackResourceManager(this.rmMetadataSerializer);
/*  47: 48 */         this.domainResourceManagers.put(var3, var4);
/*  48:    */       }
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Set getResourceDomains()
/*  53:    */   {
/*  54: 55 */     return this.setResourceDomains;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public IResource getResource(ResourceLocation par1ResourceLocation)
/*  58:    */     throws IOException
/*  59:    */   {
/*  60: 60 */     IResourceManager var2 = (IResourceManager)this.domainResourceManagers.get(par1ResourceLocation.getResourceDomain());
/*  61: 62 */     if (var2 != null) {
/*  62: 64 */       return var2.getResource(par1ResourceLocation);
/*  63:    */     }
/*  64: 68 */     throw new FileNotFoundException(par1ResourceLocation.toString());
/*  65:    */   }
/*  66:    */   
/*  67:    */   public List getAllResources(ResourceLocation par1ResourceLocation)
/*  68:    */     throws IOException
/*  69:    */   {
/*  70: 74 */     IResourceManager var2 = (IResourceManager)this.domainResourceManagers.get(par1ResourceLocation.getResourceDomain());
/*  71: 76 */     if (var2 != null) {
/*  72: 78 */       return var2.getAllResources(par1ResourceLocation);
/*  73:    */     }
/*  74: 82 */     throw new FileNotFoundException(par1ResourceLocation.toString());
/*  75:    */   }
/*  76:    */   
/*  77:    */   private void clearResources()
/*  78:    */   {
/*  79: 88 */     this.domainResourceManagers.clear();
/*  80: 89 */     this.setResourceDomains.clear();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void reloadResources(List par1List)
/*  84:    */   {
/*  85: 94 */     clearResources();
/*  86: 95 */     logger.info("Reloading ResourceManager: " + joinerResourcePacks.join(Iterables.transform(par1List, new Function()
/*  87:    */     {
/*  88:    */       private static final String __OBFID = "CL_00001092";
/*  89:    */       
/*  90:    */       public String apply(IResourcePack par1ResourcePack)
/*  91:    */       {
/*  92:100 */         return par1ResourcePack.getPackName();
/*  93:    */       }
/*  94:    */       
/*  95:    */       public Object apply(Object par1Obj)
/*  96:    */       {
/*  97:104 */         return apply((IResourcePack)par1Obj);
/*  98:    */       }
/*  99:106 */     })));
/* 100:107 */     Iterator var2 = par1List.iterator();
/* 101:109 */     while (var2.hasNext())
/* 102:    */     {
/* 103:111 */       IResourcePack var3 = (IResourcePack)var2.next();
/* 104:112 */       reloadResourcePack(var3);
/* 105:    */     }
/* 106:115 */     notifyReloadListeners();
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void registerReloadListener(IResourceManagerReloadListener par1ResourceManagerReloadListener)
/* 110:    */   {
/* 111:120 */     this.reloadListeners.add(par1ResourceManagerReloadListener);
/* 112:121 */     par1ResourceManagerReloadListener.onResourceManagerReload(this);
/* 113:    */   }
/* 114:    */   
/* 115:    */   private void notifyReloadListeners()
/* 116:    */   {
/* 117:126 */     Iterator var1 = this.reloadListeners.iterator();
/* 118:128 */     while (var1.hasNext())
/* 119:    */     {
/* 120:130 */       IResourceManagerReloadListener var2 = (IResourceManagerReloadListener)var1.next();
/* 121:131 */       var2.onResourceManagerReload(this);
/* 122:    */     }
/* 123:    */   }
/* 124:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.SimpleReloadableResourceManager
 * JD-Core Version:    0.7.0.1
 */