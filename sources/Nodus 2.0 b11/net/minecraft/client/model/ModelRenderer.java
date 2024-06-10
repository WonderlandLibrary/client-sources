/*   1:    */ package net.minecraft.client.model;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import net.minecraft.client.renderer.GLAllocation;
/*   6:    */ import net.minecraft.client.renderer.Tessellator;
/*   7:    */ import org.lwjgl.opengl.GL11;
/*   8:    */ 
/*   9:    */ public class ModelRenderer
/*  10:    */ {
/*  11:    */   public float textureWidth;
/*  12:    */   public float textureHeight;
/*  13:    */   private int textureOffsetX;
/*  14:    */   private int textureOffsetY;
/*  15:    */   public float rotationPointX;
/*  16:    */   public float rotationPointY;
/*  17:    */   public float rotationPointZ;
/*  18:    */   public float rotateAngleX;
/*  19:    */   public float rotateAngleY;
/*  20:    */   public float rotateAngleZ;
/*  21:    */   private boolean compiled;
/*  22:    */   private int displayList;
/*  23:    */   public boolean mirror;
/*  24:    */   public boolean showModel;
/*  25:    */   public boolean isHidden;
/*  26:    */   public List cubeList;
/*  27:    */   public List childModels;
/*  28:    */   public final String boxName;
/*  29:    */   private ModelBase baseModel;
/*  30:    */   public float offsetX;
/*  31:    */   public float offsetY;
/*  32:    */   public float offsetZ;
/*  33:    */   private static final String __OBFID = "CL_00000874";
/*  34:    */   
/*  35:    */   public ModelRenderer(ModelBase par1ModelBase, String par2Str)
/*  36:    */   {
/*  37: 48 */     this.textureWidth = 64.0F;
/*  38: 49 */     this.textureHeight = 32.0F;
/*  39: 50 */     this.showModel = true;
/*  40: 51 */     this.cubeList = new ArrayList();
/*  41: 52 */     this.baseModel = par1ModelBase;
/*  42: 53 */     par1ModelBase.boxList.add(this);
/*  43: 54 */     this.boxName = par2Str;
/*  44: 55 */     setTextureSize(par1ModelBase.textureWidth, par1ModelBase.textureHeight);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public ModelRenderer(ModelBase par1ModelBase)
/*  48:    */   {
/*  49: 60 */     this(par1ModelBase, null);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public ModelRenderer(ModelBase par1ModelBase, int par2, int par3)
/*  53:    */   {
/*  54: 65 */     this(par1ModelBase);
/*  55: 66 */     setTextureOffset(par2, par3);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void addChild(ModelRenderer par1ModelRenderer)
/*  59:    */   {
/*  60: 74 */     if (this.childModels == null) {
/*  61: 76 */       this.childModels = new ArrayList();
/*  62:    */     }
/*  63: 79 */     this.childModels.add(par1ModelRenderer);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public ModelRenderer setTextureOffset(int par1, int par2)
/*  67:    */   {
/*  68: 84 */     this.textureOffsetX = par1;
/*  69: 85 */     this.textureOffsetY = par2;
/*  70: 86 */     return this;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public ModelRenderer addBox(String par1Str, float par2, float par3, float par4, int par5, int par6, int par7)
/*  74:    */   {
/*  75: 91 */     par1Str = this.boxName + "." + par1Str;
/*  76: 92 */     TextureOffset var8 = this.baseModel.getTextureOffset(par1Str);
/*  77: 93 */     setTextureOffset(var8.textureOffsetX, var8.textureOffsetY);
/*  78: 94 */     this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, par2, par3, par4, par5, par6, par7, 0.0F).func_78244_a(par1Str));
/*  79: 95 */     return this;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public ModelRenderer addBox(float par1, float par2, float par3, int par4, int par5, int par6)
/*  83:    */   {
/*  84:100 */     this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, par1, par2, par3, par4, par5, par6, 0.0F));
/*  85:101 */     return this;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void addBox(float par1, float par2, float par3, int par4, int par5, int par6, float par7)
/*  89:    */   {
/*  90:109 */     this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, par1, par2, par3, par4, par5, par6, par7));
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setRotationPoint(float par1, float par2, float par3)
/*  94:    */   {
/*  95:114 */     this.rotationPointX = par1;
/*  96:115 */     this.rotationPointY = par2;
/*  97:116 */     this.rotationPointZ = par3;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void render(float par1)
/* 101:    */   {
/* 102:121 */     if (!this.isHidden) {
/* 103:123 */       if (this.showModel)
/* 104:    */       {
/* 105:125 */         if (!this.compiled) {
/* 106:127 */           compileDisplayList(par1);
/* 107:    */         }
/* 108:130 */         GL11.glTranslatef(this.offsetX, this.offsetY, this.offsetZ);
/* 109:133 */         if ((this.rotateAngleX == 0.0F) && (this.rotateAngleY == 0.0F) && (this.rotateAngleZ == 0.0F))
/* 110:    */         {
/* 111:135 */           if ((this.rotationPointX == 0.0F) && (this.rotationPointY == 0.0F) && (this.rotationPointZ == 0.0F))
/* 112:    */           {
/* 113:137 */             GL11.glCallList(this.displayList);
/* 114:139 */             if (this.childModels != null) {
/* 115:141 */               for (int var2 = 0; var2 < this.childModels.size(); var2++) {
/* 116:143 */                 ((ModelRenderer)this.childModels.get(var2)).render(par1);
/* 117:    */               }
/* 118:    */             }
/* 119:    */           }
/* 120:    */           else
/* 121:    */           {
/* 122:149 */             GL11.glTranslatef(this.rotationPointX * par1, this.rotationPointY * par1, this.rotationPointZ * par1);
/* 123:150 */             GL11.glCallList(this.displayList);
/* 124:152 */             if (this.childModels != null) {
/* 125:154 */               for (int var2 = 0; var2 < this.childModels.size(); var2++) {
/* 126:156 */                 ((ModelRenderer)this.childModels.get(var2)).render(par1);
/* 127:    */               }
/* 128:    */             }
/* 129:160 */             GL11.glTranslatef(-this.rotationPointX * par1, -this.rotationPointY * par1, -this.rotationPointZ * par1);
/* 130:    */           }
/* 131:    */         }
/* 132:    */         else
/* 133:    */         {
/* 134:165 */           GL11.glPushMatrix();
/* 135:166 */           GL11.glTranslatef(this.rotationPointX * par1, this.rotationPointY * par1, this.rotationPointZ * par1);
/* 136:168 */           if (this.rotateAngleZ != 0.0F) {
/* 137:170 */             GL11.glRotatef(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
/* 138:    */           }
/* 139:173 */           if (this.rotateAngleY != 0.0F) {
/* 140:175 */             GL11.glRotatef(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
/* 141:    */           }
/* 142:178 */           if (this.rotateAngleX != 0.0F) {
/* 143:180 */             GL11.glRotatef(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
/* 144:    */           }
/* 145:183 */           GL11.glCallList(this.displayList);
/* 146:185 */           if (this.childModels != null) {
/* 147:187 */             for (int var2 = 0; var2 < this.childModels.size(); var2++) {
/* 148:189 */               ((ModelRenderer)this.childModels.get(var2)).render(par1);
/* 149:    */             }
/* 150:    */           }
/* 151:193 */           GL11.glPopMatrix();
/* 152:    */         }
/* 153:196 */         GL11.glTranslatef(-this.offsetX, -this.offsetY, -this.offsetZ);
/* 154:    */       }
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void renderWithRotation(float par1)
/* 159:    */   {
/* 160:203 */     if (!this.isHidden) {
/* 161:205 */       if (this.showModel)
/* 162:    */       {
/* 163:207 */         if (!this.compiled) {
/* 164:209 */           compileDisplayList(par1);
/* 165:    */         }
/* 166:212 */         GL11.glPushMatrix();
/* 167:213 */         GL11.glTranslatef(this.rotationPointX * par1, this.rotationPointY * par1, this.rotationPointZ * par1);
/* 168:215 */         if (this.rotateAngleY != 0.0F) {
/* 169:217 */           GL11.glRotatef(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
/* 170:    */         }
/* 171:220 */         if (this.rotateAngleX != 0.0F) {
/* 172:222 */           GL11.glRotatef(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
/* 173:    */         }
/* 174:225 */         if (this.rotateAngleZ != 0.0F) {
/* 175:227 */           GL11.glRotatef(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
/* 176:    */         }
/* 177:230 */         GL11.glCallList(this.displayList);
/* 178:231 */         GL11.glPopMatrix();
/* 179:    */       }
/* 180:    */     }
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void postRender(float par1)
/* 184:    */   {
/* 185:241 */     if (!this.isHidden) {
/* 186:243 */       if (this.showModel)
/* 187:    */       {
/* 188:245 */         if (!this.compiled) {
/* 189:247 */           compileDisplayList(par1);
/* 190:    */         }
/* 191:250 */         if ((this.rotateAngleX == 0.0F) && (this.rotateAngleY == 0.0F) && (this.rotateAngleZ == 0.0F))
/* 192:    */         {
/* 193:252 */           if ((this.rotationPointX != 0.0F) || (this.rotationPointY != 0.0F) || (this.rotationPointZ != 0.0F)) {
/* 194:254 */             GL11.glTranslatef(this.rotationPointX * par1, this.rotationPointY * par1, this.rotationPointZ * par1);
/* 195:    */           }
/* 196:    */         }
/* 197:    */         else
/* 198:    */         {
/* 199:259 */           GL11.glTranslatef(this.rotationPointX * par1, this.rotationPointY * par1, this.rotationPointZ * par1);
/* 200:261 */           if (this.rotateAngleZ != 0.0F) {
/* 201:263 */             GL11.glRotatef(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
/* 202:    */           }
/* 203:266 */           if (this.rotateAngleY != 0.0F) {
/* 204:268 */             GL11.glRotatef(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
/* 205:    */           }
/* 206:271 */           if (this.rotateAngleX != 0.0F) {
/* 207:273 */             GL11.glRotatef(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
/* 208:    */           }
/* 209:    */         }
/* 210:    */       }
/* 211:    */     }
/* 212:    */   }
/* 213:    */   
/* 214:    */   private void compileDisplayList(float par1)
/* 215:    */   {
/* 216:285 */     this.displayList = GLAllocation.generateDisplayLists(1);
/* 217:286 */     GL11.glNewList(this.displayList, 4864);
/* 218:287 */     Tessellator var2 = Tessellator.instance;
/* 219:289 */     for (int var3 = 0; var3 < this.cubeList.size(); var3++) {
/* 220:291 */       ((ModelBox)this.cubeList.get(var3)).render(var2, par1);
/* 221:    */     }
/* 222:294 */     GL11.glEndList();
/* 223:295 */     this.compiled = true;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public ModelRenderer setTextureSize(int par1, int par2)
/* 227:    */   {
/* 228:303 */     this.textureWidth = par1;
/* 229:304 */     this.textureHeight = par2;
/* 230:305 */     return this;
/* 231:    */   }
/* 232:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelRenderer
 * JD-Core Version:    0.7.0.1
 */