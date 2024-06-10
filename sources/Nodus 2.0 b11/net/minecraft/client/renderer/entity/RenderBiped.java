/*   1:    */ package net.minecraft.client.renderer.entity;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Maps;
/*   4:    */ import java.util.Map;
/*   5:    */ import net.minecraft.block.Block;
/*   6:    */ import net.minecraft.client.model.ModelBase;
/*   7:    */ import net.minecraft.client.model.ModelBiped;
/*   8:    */ import net.minecraft.client.model.ModelRenderer;
/*   9:    */ import net.minecraft.client.renderer.ItemRenderer;
/*  10:    */ import net.minecraft.client.renderer.RenderBlocks;
/*  11:    */ import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
/*  12:    */ import net.minecraft.entity.Entity;
/*  13:    */ import net.minecraft.entity.EntityLiving;
/*  14:    */ import net.minecraft.entity.EntityLivingBase;
/*  15:    */ import net.minecraft.init.Items;
/*  16:    */ import net.minecraft.item.Item;
/*  17:    */ import net.minecraft.item.ItemArmor;
/*  18:    */ import net.minecraft.item.ItemArmor.ArmorMaterial;
/*  19:    */ import net.minecraft.item.ItemBlock;
/*  20:    */ import net.minecraft.item.ItemStack;
/*  21:    */ import net.minecraft.nbt.NBTTagCompound;
/*  22:    */ import net.minecraft.util.ResourceLocation;
/*  23:    */ import org.lwjgl.opengl.GL11;
/*  24:    */ 
/*  25:    */ public class RenderBiped
/*  26:    */   extends RenderLiving
/*  27:    */ {
/*  28:    */   protected ModelBiped modelBipedMain;
/*  29:    */   protected float field_77070_b;
/*  30:    */   protected ModelBiped field_82423_g;
/*  31:    */   protected ModelBiped field_82425_h;
/*  32: 26 */   private static final Map field_110859_k = ;
/*  33: 29 */   private static final String[] bipedArmorFilenamePrefix = { "leather", "chainmail", "iron", "diamond", "gold" };
/*  34:    */   private static final String __OBFID = "CL_00001001";
/*  35:    */   
/*  36:    */   public RenderBiped(ModelBiped par1ModelBiped, float par2)
/*  37:    */   {
/*  38: 34 */     this(par1ModelBiped, par2, 1.0F);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public RenderBiped(ModelBiped par1ModelBiped, float par2, float par3)
/*  42:    */   {
/*  43: 39 */     super(par1ModelBiped, par2);
/*  44: 40 */     this.modelBipedMain = par1ModelBiped;
/*  45: 41 */     this.field_77070_b = par3;
/*  46: 42 */     func_82421_b();
/*  47:    */   }
/*  48:    */   
/*  49:    */   protected void func_82421_b()
/*  50:    */   {
/*  51: 47 */     this.field_82423_g = new ModelBiped(1.0F);
/*  52: 48 */     this.field_82425_h = new ModelBiped(0.5F);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static ResourceLocation func_110857_a(ItemArmor par0ItemArmor, int par1)
/*  56:    */   {
/*  57: 53 */     return func_110858_a(par0ItemArmor, par1, null);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static ResourceLocation func_110858_a(ItemArmor par0ItemArmor, int par1, String par2Str)
/*  61:    */   {
/*  62: 58 */     String var3 = String.format("textures/models/armor/%s_layer_%d%s.png", new Object[] { bipedArmorFilenamePrefix[par0ItemArmor.renderIndex], Integer.valueOf(par1 == 2 ? 2 : 1), par2Str == null ? "" : String.format("_%s", new Object[] { par2Str }) });
/*  63: 59 */     ResourceLocation var4 = (ResourceLocation)field_110859_k.get(var3);
/*  64: 61 */     if (var4 == null)
/*  65:    */     {
/*  66: 63 */       var4 = new ResourceLocation(var3);
/*  67: 64 */       field_110859_k.put(var3, var4);
/*  68:    */     }
/*  69: 67 */     return var4;
/*  70:    */   }
/*  71:    */   
/*  72:    */   protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
/*  73:    */   {
/*  74: 75 */     ItemStack var4 = par1EntityLiving.func_130225_q(3 - par2);
/*  75: 77 */     if (var4 != null)
/*  76:    */     {
/*  77: 79 */       Item var5 = var4.getItem();
/*  78: 81 */       if ((var5 instanceof ItemArmor))
/*  79:    */       {
/*  80: 83 */         ItemArmor var6 = (ItemArmor)var5;
/*  81: 84 */         bindTexture(func_110857_a(var6, par2));
/*  82: 85 */         ModelBiped var7 = par2 == 2 ? this.field_82425_h : this.field_82423_g;
/*  83: 86 */         var7.bipedHead.showModel = (par2 == 0);
/*  84: 87 */         var7.bipedHeadwear.showModel = (par2 == 0);
/*  85: 88 */         var7.bipedBody.showModel = ((par2 == 1) || (par2 == 2));
/*  86: 89 */         var7.bipedRightArm.showModel = (par2 == 1);
/*  87: 90 */         var7.bipedLeftArm.showModel = (par2 == 1);
/*  88: 91 */         var7.bipedRightLeg.showModel = ((par2 == 2) || (par2 == 3));
/*  89: 92 */         var7.bipedLeftLeg.showModel = ((par2 == 2) || (par2 == 3));
/*  90: 93 */         setRenderPassModel(var7);
/*  91: 94 */         var7.onGround = this.mainModel.onGround;
/*  92: 95 */         var7.isRiding = this.mainModel.isRiding;
/*  93: 96 */         var7.isChild = this.mainModel.isChild;
/*  94: 98 */         if (var6.getArmorMaterial() == ItemArmor.ArmorMaterial.CLOTH)
/*  95:    */         {
/*  96:100 */           int var8 = var6.getColor(var4);
/*  97:101 */           float var9 = (var8 >> 16 & 0xFF) / 255.0F;
/*  98:102 */           float var10 = (var8 >> 8 & 0xFF) / 255.0F;
/*  99:103 */           float var11 = (var8 & 0xFF) / 255.0F;
/* 100:104 */           GL11.glColor3f(var9, var10, var11);
/* 101:106 */           if (var4.isItemEnchanted()) {
/* 102:108 */             return 31;
/* 103:    */           }
/* 104:111 */           return 16;
/* 105:    */         }
/* 106:114 */         GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 107:116 */         if (var4.isItemEnchanted()) {
/* 108:118 */           return 15;
/* 109:    */         }
/* 110:121 */         return 1;
/* 111:    */       }
/* 112:    */     }
/* 113:125 */     return -1;
/* 114:    */   }
/* 115:    */   
/* 116:    */   protected void func_82408_c(EntityLiving par1EntityLivingBase, int par2, float par3)
/* 117:    */   {
/* 118:130 */     ItemStack var4 = par1EntityLivingBase.func_130225_q(3 - par2);
/* 119:132 */     if (var4 != null)
/* 120:    */     {
/* 121:134 */       Item var5 = var4.getItem();
/* 122:136 */       if ((var5 instanceof ItemArmor))
/* 123:    */       {
/* 124:138 */         bindTexture(func_110858_a((ItemArmor)var5, par2, "overlay"));
/* 125:139 */         float var6 = 1.0F;
/* 126:140 */         GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 127:    */       }
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
/* 132:    */   {
/* 133:153 */     GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 134:154 */     ItemStack var10 = par1EntityLiving.getHeldItem();
/* 135:155 */     func_82420_a(par1EntityLiving, var10);
/* 136:156 */     double var11 = par4 - par1EntityLiving.yOffset;
/* 137:158 */     if (par1EntityLiving.isSneaking()) {
/* 138:160 */       var11 -= 0.125D;
/* 139:    */     }
/* 140:163 */     super.doRender(par1EntityLiving, par2, var11, par6, par8, par9);
/* 141:164 */     this.field_82423_g.aimedBow = (this.field_82425_h.aimedBow = this.modelBipedMain.aimedBow = 0);
/* 142:165 */     this.field_82423_g.isSneak = (this.field_82425_h.isSneak = this.modelBipedMain.isSneak = 0);
/* 143:166 */     this.field_82423_g.heldItemRight = (this.field_82425_h.heldItemRight = this.modelBipedMain.heldItemRight = 0);
/* 144:    */   }
/* 145:    */   
/* 146:    */   protected ResourceLocation getEntityTexture(EntityLiving par1EntityLiving)
/* 147:    */   {
/* 148:174 */     return null;
/* 149:    */   }
/* 150:    */   
/* 151:    */   protected void func_82420_a(EntityLiving par1EntityLiving, ItemStack par2ItemStack)
/* 152:    */   {
/* 153:179 */     this.field_82423_g.heldItemRight = (this.field_82425_h.heldItemRight = this.modelBipedMain.heldItemRight = par2ItemStack != null ? 1 : 0);
/* 154:180 */     this.field_82423_g.isSneak = (this.field_82425_h.isSneak = this.modelBipedMain.isSneak = par1EntityLiving.isSneaking());
/* 155:    */   }
/* 156:    */   
/* 157:    */   protected void renderEquippedItems(EntityLiving par1EntityLiving, float par2)
/* 158:    */   {
/* 159:185 */     GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 160:186 */     super.renderEquippedItems(par1EntityLiving, par2);
/* 161:187 */     ItemStack var3 = par1EntityLiving.getHeldItem();
/* 162:188 */     ItemStack var4 = par1EntityLiving.func_130225_q(3);
/* 163:192 */     if (var4 != null)
/* 164:    */     {
/* 165:194 */       GL11.glPushMatrix();
/* 166:195 */       this.modelBipedMain.bipedHead.postRender(0.0625F);
/* 167:196 */       Item var5 = var4.getItem();
/* 168:198 */       if ((var5 instanceof ItemBlock))
/* 169:    */       {
/* 170:200 */         if (RenderBlocks.renderItemIn3d(Block.getBlockFromItem(var5).getRenderType()))
/* 171:    */         {
/* 172:202 */           float var6 = 0.625F;
/* 173:203 */           GL11.glTranslatef(0.0F, -0.25F, 0.0F);
/* 174:204 */           GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/* 175:205 */           GL11.glScalef(var6, -var6, -var6);
/* 176:    */         }
/* 177:208 */         this.renderManager.itemRenderer.renderItem(par1EntityLiving, var4, 0);
/* 178:    */       }
/* 179:210 */       else if (var5 == Items.skull)
/* 180:    */       {
/* 181:212 */         float var6 = 1.0625F;
/* 182:213 */         GL11.glScalef(var6, -var6, -var6);
/* 183:214 */         String var7 = "";
/* 184:216 */         if ((var4.hasTagCompound()) && (var4.getTagCompound().func_150297_b("SkullOwner", 8))) {
/* 185:218 */           var7 = var4.getTagCompound().getString("SkullOwner");
/* 186:    */         }
/* 187:221 */         TileEntitySkullRenderer.field_147536_b.func_147530_a(-0.5F, 0.0F, -0.5F, 1, 180.0F, var4.getItemDamage(), var7);
/* 188:    */       }
/* 189:224 */       GL11.glPopMatrix();
/* 190:    */     }
/* 191:227 */     if ((var3 != null) && (var3.getItem() != null))
/* 192:    */     {
/* 193:229 */       Item var5 = var3.getItem();
/* 194:230 */       GL11.glPushMatrix();
/* 195:232 */       if (this.mainModel.isChild)
/* 196:    */       {
/* 197:234 */         float var6 = 0.5F;
/* 198:235 */         GL11.glTranslatef(0.0F, 0.625F, 0.0F);
/* 199:236 */         GL11.glRotatef(-20.0F, -1.0F, 0.0F, 0.0F);
/* 200:237 */         GL11.glScalef(var6, var6, var6);
/* 201:    */       }
/* 202:240 */       this.modelBipedMain.bipedRightArm.postRender(0.0625F);
/* 203:241 */       GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
/* 204:243 */       if (((var5 instanceof ItemBlock)) && (RenderBlocks.renderItemIn3d(Block.getBlockFromItem(var5).getRenderType())))
/* 205:    */       {
/* 206:245 */         float var6 = 0.5F;
/* 207:246 */         GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
/* 208:247 */         var6 *= 0.75F;
/* 209:248 */         GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
/* 210:249 */         GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
/* 211:250 */         GL11.glScalef(-var6, -var6, var6);
/* 212:    */       }
/* 213:252 */       else if (var5 == Items.bow)
/* 214:    */       {
/* 215:254 */         float var6 = 0.625F;
/* 216:255 */         GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
/* 217:256 */         GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
/* 218:257 */         GL11.glScalef(var6, -var6, var6);
/* 219:258 */         GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
/* 220:259 */         GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
/* 221:    */       }
/* 222:261 */       else if (var5.isFull3D())
/* 223:    */       {
/* 224:263 */         float var6 = 0.625F;
/* 225:265 */         if (var5.shouldRotateAroundWhenRendering())
/* 226:    */         {
/* 227:267 */           GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/* 228:268 */           GL11.glTranslatef(0.0F, -0.125F, 0.0F);
/* 229:    */         }
/* 230:271 */         func_82422_c();
/* 231:272 */         GL11.glScalef(var6, -var6, var6);
/* 232:273 */         GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
/* 233:274 */         GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
/* 234:    */       }
/* 235:    */       else
/* 236:    */       {
/* 237:278 */         float var6 = 0.375F;
/* 238:279 */         GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
/* 239:280 */         GL11.glScalef(var6, var6, var6);
/* 240:281 */         GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
/* 241:282 */         GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
/* 242:283 */         GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
/* 243:    */       }
/* 244:290 */       if (var3.getItem().requiresMultipleRenderPasses())
/* 245:    */       {
/* 246:292 */         for (int var12 = 0; var12 <= 1; var12++)
/* 247:    */         {
/* 248:294 */           int var11 = var3.getItem().getColorFromItemStack(var3, var12);
/* 249:295 */           float var8 = (var11 >> 16 & 0xFF) / 255.0F;
/* 250:296 */           float var9 = (var11 >> 8 & 0xFF) / 255.0F;
/* 251:297 */           float var10 = (var11 & 0xFF) / 255.0F;
/* 252:298 */           GL11.glColor4f(var8, var9, var10, 1.0F);
/* 253:299 */           this.renderManager.itemRenderer.renderItem(par1EntityLiving, var3, var12);
/* 254:    */         }
/* 255:    */       }
/* 256:    */       else
/* 257:    */       {
/* 258:304 */         int var12 = var3.getItem().getColorFromItemStack(var3, 0);
/* 259:305 */         float var13 = (var12 >> 16 & 0xFF) / 255.0F;
/* 260:306 */         float var8 = (var12 >> 8 & 0xFF) / 255.0F;
/* 261:307 */         float var9 = (var12 & 0xFF) / 255.0F;
/* 262:308 */         GL11.glColor4f(var13, var8, var9, 1.0F);
/* 263:309 */         this.renderManager.itemRenderer.renderItem(par1EntityLiving, var3, 0);
/* 264:    */       }
/* 265:312 */       GL11.glPopMatrix();
/* 266:    */     }
/* 267:    */   }
/* 268:    */   
/* 269:    */   protected void func_82422_c()
/* 270:    */   {
/* 271:318 */     GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
/* 272:    */   }
/* 273:    */   
/* 274:    */   protected void func_82408_c(EntityLivingBase par1EntityLivingBase, int par2, float par3)
/* 275:    */   {
/* 276:323 */     func_82408_c((EntityLiving)par1EntityLivingBase, par2, par3);
/* 277:    */   }
/* 278:    */   
/* 279:    */   protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
/* 280:    */   {
/* 281:331 */     return shouldRenderPass((EntityLiving)par1EntityLivingBase, par2, par3);
/* 282:    */   }
/* 283:    */   
/* 284:    */   protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
/* 285:    */   {
/* 286:336 */     renderEquippedItems((EntityLiving)par1EntityLivingBase, par2);
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 290:    */   {
/* 291:347 */     doRender((EntityLiving)par1Entity, par2, par4, par6, par8, par9);
/* 292:    */   }
/* 293:    */   
/* 294:    */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 295:    */   {
/* 296:355 */     return getEntityTexture((EntityLiving)par1Entity);
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 300:    */   {
/* 301:366 */     doRender((EntityLiving)par1Entity, par2, par4, par6, par8, par9);
/* 302:    */   }
/* 303:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderBiped
 * JD-Core Version:    0.7.0.1
 */