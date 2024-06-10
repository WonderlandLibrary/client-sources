/*   1:    */ package net.minecraft.client.model;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.Entity;
/*   4:    */ import net.minecraft.util.MathHelper;
/*   5:    */ import org.lwjgl.opengl.GL11;
/*   6:    */ 
/*   7:    */ public class ModelChicken
/*   8:    */   extends ModelBase
/*   9:    */ {
/*  10:    */   public ModelRenderer head;
/*  11:    */   public ModelRenderer body;
/*  12:    */   public ModelRenderer rightLeg;
/*  13:    */   public ModelRenderer leftLeg;
/*  14:    */   public ModelRenderer rightWing;
/*  15:    */   public ModelRenderer leftWing;
/*  16:    */   public ModelRenderer bill;
/*  17:    */   public ModelRenderer chin;
/*  18:    */   private static final String __OBFID = "CL_00000835";
/*  19:    */   
/*  20:    */   public ModelChicken()
/*  21:    */   {
/*  22: 21 */     byte var1 = 16;
/*  23: 22 */     this.head = new ModelRenderer(this, 0, 0);
/*  24: 23 */     this.head.addBox(-2.0F, -6.0F, -2.0F, 4, 6, 3, 0.0F);
/*  25: 24 */     this.head.setRotationPoint(0.0F, -1 + var1, -4.0F);
/*  26: 25 */     this.bill = new ModelRenderer(this, 14, 0);
/*  27: 26 */     this.bill.addBox(-2.0F, -4.0F, -4.0F, 4, 2, 2, 0.0F);
/*  28: 27 */     this.bill.setRotationPoint(0.0F, -1 + var1, -4.0F);
/*  29: 28 */     this.chin = new ModelRenderer(this, 14, 4);
/*  30: 29 */     this.chin.addBox(-1.0F, -2.0F, -3.0F, 2, 2, 2, 0.0F);
/*  31: 30 */     this.chin.setRotationPoint(0.0F, -1 + var1, -4.0F);
/*  32: 31 */     this.body = new ModelRenderer(this, 0, 9);
/*  33: 32 */     this.body.addBox(-3.0F, -4.0F, -3.0F, 6, 8, 6, 0.0F);
/*  34: 33 */     this.body.setRotationPoint(0.0F, var1, 0.0F);
/*  35: 34 */     this.rightLeg = new ModelRenderer(this, 26, 0);
/*  36: 35 */     this.rightLeg.addBox(-1.0F, 0.0F, -3.0F, 3, 5, 3);
/*  37: 36 */     this.rightLeg.setRotationPoint(-2.0F, 3 + var1, 1.0F);
/*  38: 37 */     this.leftLeg = new ModelRenderer(this, 26, 0);
/*  39: 38 */     this.leftLeg.addBox(-1.0F, 0.0F, -3.0F, 3, 5, 3);
/*  40: 39 */     this.leftLeg.setRotationPoint(1.0F, 3 + var1, 1.0F);
/*  41: 40 */     this.rightWing = new ModelRenderer(this, 24, 13);
/*  42: 41 */     this.rightWing.addBox(0.0F, 0.0F, -3.0F, 1, 4, 6);
/*  43: 42 */     this.rightWing.setRotationPoint(-4.0F, -3 + var1, 0.0F);
/*  44: 43 */     this.leftWing = new ModelRenderer(this, 24, 13);
/*  45: 44 */     this.leftWing.addBox(-1.0F, 0.0F, -3.0F, 1, 4, 6);
/*  46: 45 */     this.leftWing.setRotationPoint(4.0F, -3 + var1, 0.0F);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/*  50:    */   {
/*  51: 53 */     setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
/*  52: 55 */     if (this.isChild)
/*  53:    */     {
/*  54: 57 */       float var8 = 2.0F;
/*  55: 58 */       GL11.glPushMatrix();
/*  56: 59 */       GL11.glTranslatef(0.0F, 5.0F * par7, 2.0F * par7);
/*  57: 60 */       this.head.render(par7);
/*  58: 61 */       this.bill.render(par7);
/*  59: 62 */       this.chin.render(par7);
/*  60: 63 */       GL11.glPopMatrix();
/*  61: 64 */       GL11.glPushMatrix();
/*  62: 65 */       GL11.glScalef(1.0F / var8, 1.0F / var8, 1.0F / var8);
/*  63: 66 */       GL11.glTranslatef(0.0F, 24.0F * par7, 0.0F);
/*  64: 67 */       this.body.render(par7);
/*  65: 68 */       this.rightLeg.render(par7);
/*  66: 69 */       this.leftLeg.render(par7);
/*  67: 70 */       this.rightWing.render(par7);
/*  68: 71 */       this.leftWing.render(par7);
/*  69: 72 */       GL11.glPopMatrix();
/*  70:    */     }
/*  71:    */     else
/*  72:    */     {
/*  73: 76 */       this.head.render(par7);
/*  74: 77 */       this.bill.render(par7);
/*  75: 78 */       this.chin.render(par7);
/*  76: 79 */       this.body.render(par7);
/*  77: 80 */       this.rightLeg.render(par7);
/*  78: 81 */       this.leftLeg.render(par7);
/*  79: 82 */       this.rightWing.render(par7);
/*  80: 83 */       this.leftWing.render(par7);
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
/*  85:    */   {
/*  86: 94 */     this.head.rotateAngleX = (par5 / 57.295776F);
/*  87: 95 */     this.head.rotateAngleY = (par4 / 57.295776F);
/*  88: 96 */     this.bill.rotateAngleX = this.head.rotateAngleX;
/*  89: 97 */     this.bill.rotateAngleY = this.head.rotateAngleY;
/*  90: 98 */     this.chin.rotateAngleX = this.head.rotateAngleX;
/*  91: 99 */     this.chin.rotateAngleY = this.head.rotateAngleY;
/*  92:100 */     this.body.rotateAngleX = 1.570796F;
/*  93:101 */     this.rightLeg.rotateAngleX = (MathHelper.cos(par1 * 0.6662F) * 1.4F * par2);
/*  94:102 */     this.leftLeg.rotateAngleX = (MathHelper.cos(par1 * 0.6662F + 3.141593F) * 1.4F * par2);
/*  95:103 */     this.rightWing.rotateAngleZ = par3;
/*  96:104 */     this.leftWing.rotateAngleZ = (-par3);
/*  97:    */   }
/*  98:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.model.ModelChicken
 * JD-Core Version:    0.7.0.1
 */