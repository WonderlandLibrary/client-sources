/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Lists;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.lang.reflect.Method;
/*   7:    */ import java.net.URI;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Collections;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.List;
/*  12:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*  13:    */ import net.minecraft.client.Minecraft;
/*  14:    */ import net.minecraft.client.resources.I18n;
/*  15:    */ import net.minecraft.client.resources.ResourcePackListEntry;
/*  16:    */ import net.minecraft.client.resources.ResourcePackListEntryDefault;
/*  17:    */ import net.minecraft.client.resources.ResourcePackListEntryFound;
/*  18:    */ import net.minecraft.client.resources.ResourcePackRepository;
/*  19:    */ import net.minecraft.client.resources.ResourcePackRepository.Entry;
/*  20:    */ import net.minecraft.client.settings.GameSettings;
/*  21:    */ import net.minecraft.util.Util;
/*  22:    */ import net.minecraft.util.Util.EnumOS;
/*  23:    */ import org.apache.logging.log4j.LogManager;
/*  24:    */ import org.apache.logging.log4j.Logger;
/*  25:    */ import org.lwjgl.Sys;
/*  26:    */ 
/*  27:    */ public class GuiScreenResourcePacks
/*  28:    */   extends GuiScreen
/*  29:    */ {
/*  30: 27 */   private static final Logger logger = ;
/*  31:    */   private GuiScreen field_146965_f;
/*  32:    */   private List field_146966_g;
/*  33:    */   private List field_146969_h;
/*  34:    */   private GuiResourcePackAvailable field_146970_i;
/*  35:    */   private GuiResourcePackSelected field_146967_r;
/*  36:    */   private static final String __OBFID = "CL_00000820";
/*  37:    */   
/*  38:    */   public GuiScreenResourcePacks(GuiScreen p_i45050_1_)
/*  39:    */   {
/*  40: 37 */     this.field_146965_f = p_i45050_1_;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void initGui()
/*  44:    */   {
/*  45: 45 */     this.buttonList.add(new GuiOptionButton(2, width / 2 - 154, height - 48, I18n.format("resourcePack.openFolder", new Object[0])));
/*  46: 46 */     this.buttonList.add(new GuiOptionButton(1, width / 2 + 4, height - 48, I18n.format("gui.done", new Object[0])));
/*  47: 47 */     this.field_146966_g = new ArrayList();
/*  48: 48 */     this.field_146969_h = new ArrayList();
/*  49: 49 */     ResourcePackRepository var1 = this.mc.getResourcePackRepository();
/*  50: 50 */     var1.updateRepositoryEntriesAll();
/*  51: 51 */     ArrayList var2 = Lists.newArrayList(var1.getRepositoryEntriesAll());
/*  52: 52 */     var2.removeAll(var1.getRepositoryEntries());
/*  53: 53 */     Iterator var3 = var2.iterator();
/*  54: 56 */     while (var3.hasNext())
/*  55:    */     {
/*  56: 58 */       ResourcePackRepository.Entry var4 = (ResourcePackRepository.Entry)var3.next();
/*  57: 59 */       this.field_146966_g.add(new ResourcePackListEntryFound(this, var4));
/*  58:    */     }
/*  59: 62 */     var3 = Lists.reverse(var1.getRepositoryEntries()).iterator();
/*  60: 64 */     while (var3.hasNext())
/*  61:    */     {
/*  62: 66 */       ResourcePackRepository.Entry var4 = (ResourcePackRepository.Entry)var3.next();
/*  63: 67 */       this.field_146969_h.add(new ResourcePackListEntryFound(this, var4));
/*  64:    */     }
/*  65: 70 */     this.field_146969_h.add(new ResourcePackListEntryDefault(this));
/*  66: 71 */     this.field_146970_i = new GuiResourcePackAvailable(this.mc, 200, height, this.field_146966_g);
/*  67: 72 */     this.field_146970_i.func_148140_g(width / 2 - 4 - 200);
/*  68: 73 */     this.field_146970_i.func_148134_d(7, 8);
/*  69: 74 */     this.field_146967_r = new GuiResourcePackSelected(this.mc, 200, height, this.field_146969_h);
/*  70: 75 */     this.field_146967_r.func_148140_g(width / 2 + 4);
/*  71: 76 */     this.field_146967_r.func_148134_d(7, 8);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public boolean func_146961_a(ResourcePackListEntry p_146961_1_)
/*  75:    */   {
/*  76: 81 */     return this.field_146969_h.contains(p_146961_1_);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public List func_146962_b(ResourcePackListEntry p_146962_1_)
/*  80:    */   {
/*  81: 86 */     return func_146961_a(p_146962_1_) ? this.field_146969_h : this.field_146966_g;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public List func_146964_g()
/*  85:    */   {
/*  86: 91 */     return this.field_146966_g;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public List func_146963_h()
/*  90:    */   {
/*  91: 96 */     return this.field_146969_h;
/*  92:    */   }
/*  93:    */   
/*  94:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  95:    */   {
/*  96:101 */     if (p_146284_1_.enabled) {
/*  97:103 */       if (p_146284_1_.id == 2)
/*  98:    */       {
/*  99:105 */         File var2 = this.mc.getResourcePackRepository().getDirResourcepacks();
/* 100:106 */         String var3 = var2.getAbsolutePath();
/* 101:108 */         if (Util.getOSType() == Util.EnumOS.MACOS)
/* 102:    */         {
/* 103:    */           try
/* 104:    */           {
/* 105:112 */             logger.info(var3);
/* 106:113 */             Runtime.getRuntime().exec(new String[] { "/usr/bin/open", var3 });
/* 107:114 */             return;
/* 108:    */           }
/* 109:    */           catch (IOException var9)
/* 110:    */           {
/* 111:118 */             logger.error("Couldn't open file", var9);
/* 112:    */           }
/* 113:    */         }
/* 114:121 */         else if (Util.getOSType() == Util.EnumOS.WINDOWS)
/* 115:    */         {
/* 116:123 */           String var4 = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[] { var3 });
/* 117:    */           try
/* 118:    */           {
/* 119:127 */             Runtime.getRuntime().exec(var4);
/* 120:128 */             return;
/* 121:    */           }
/* 122:    */           catch (IOException var8)
/* 123:    */           {
/* 124:132 */             logger.error("Couldn't open file", var8);
/* 125:    */           }
/* 126:    */         }
/* 127:136 */         boolean var12 = false;
/* 128:    */         try
/* 129:    */         {
/* 130:140 */           Class var5 = Class.forName("java.awt.Desktop");
/* 131:141 */           Object var6 = var5.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 132:142 */           var5.getMethod("browse", new Class[] { URI.class }).invoke(var6, new Object[] { var2.toURI() });
/* 133:    */         }
/* 134:    */         catch (Throwable var7)
/* 135:    */         {
/* 136:146 */           logger.error("Couldn't open link", var7);
/* 137:147 */           var12 = true;
/* 138:    */         }
/* 139:150 */         if (var12)
/* 140:    */         {
/* 141:152 */           logger.info("Opening via system class!");
/* 142:153 */           Sys.openURL("file://" + var3);
/* 143:    */         }
/* 144:    */       }
/* 145:156 */       else if (p_146284_1_.id == 1)
/* 146:    */       {
/* 147:158 */         ArrayList var10 = Lists.newArrayList();
/* 148:159 */         Iterator var11 = this.field_146969_h.iterator();
/* 149:161 */         while (var11.hasNext())
/* 150:    */         {
/* 151:163 */           ResourcePackListEntry var13 = (ResourcePackListEntry)var11.next();
/* 152:165 */           if ((var13 instanceof ResourcePackListEntryFound)) {
/* 153:167 */             var10.add(((ResourcePackListEntryFound)var13).func_148318_i());
/* 154:    */           }
/* 155:    */         }
/* 156:171 */         Collections.reverse(var10);
/* 157:172 */         this.mc.getResourcePackRepository().func_148527_a(var10);
/* 158:173 */         this.mc.gameSettings.resourcePacks.clear();
/* 159:174 */         var11 = var10.iterator();
/* 160:176 */         while (var11.hasNext())
/* 161:    */         {
/* 162:178 */           ResourcePackRepository.Entry var14 = (ResourcePackRepository.Entry)var11.next();
/* 163:179 */           this.mc.gameSettings.resourcePacks.add(var14.getResourcePackName());
/* 164:    */         }
/* 165:182 */         this.mc.gameSettings.saveOptions();
/* 166:183 */         this.mc.refreshResources();
/* 167:184 */         this.mc.displayGuiScreen(this.field_146965_f);
/* 168:    */       }
/* 169:    */     }
/* 170:    */   }
/* 171:    */   
/* 172:    */   protected void mouseClicked(int par1, int par2, int par3)
/* 173:    */   {
/* 174:194 */     super.mouseClicked(par1, par2, par3);
/* 175:195 */     this.field_146970_i.func_148179_a(par1, par2, par3);
/* 176:196 */     this.field_146967_r.func_148179_a(par1, par2, par3);
/* 177:    */   }
/* 178:    */   
/* 179:    */   protected void mouseMovedOrUp(int p_146286_1_, int p_146286_2_, int p_146286_3_)
/* 180:    */   {
/* 181:201 */     super.mouseMovedOrUp(p_146286_1_, p_146286_2_, p_146286_3_);
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void drawScreen(int par1, int par2, float par3)
/* 185:    */   {
/* 186:209 */     func_146278_c(0);
/* 187:210 */     this.field_146970_i.func_148128_a(par1, par2, par3);
/* 188:211 */     this.field_146967_r.func_148128_a(par1, par2, par3);
/* 189:212 */     drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.title", new Object[0]), width / 2, 16, 16777215);
/* 190:213 */     drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.folderInfo", new Object[0]), width / 2 - 77, height - 26, 8421504);
/* 191:214 */     super.drawScreen(par1, par2, par3);
/* 192:    */   }
/* 193:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiScreenResourcePacks
 * JD-Core Version:    0.7.0.1
 */