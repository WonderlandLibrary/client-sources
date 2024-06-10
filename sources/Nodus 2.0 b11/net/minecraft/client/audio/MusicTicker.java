/*  1:   */ package net.minecraft.client.audio;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.client.Minecraft;
/*  5:   */ import net.minecraft.server.gui.IUpdatePlayerListBox;
/*  6:   */ import net.minecraft.util.MathHelper;
/*  7:   */ import net.minecraft.util.ResourceLocation;
/*  8:   */ 
/*  9:   */ public class MusicTicker
/* 10:   */   implements IUpdatePlayerListBox
/* 11:   */ {
/* 12:11 */   private final Random field_147679_a = new Random();
/* 13:   */   private final Minecraft field_147677_b;
/* 14:   */   private ISound field_147678_c;
/* 15:14 */   private int field_147676_d = 100;
/* 16:   */   private static final String __OBFID = "CL_00001138";
/* 17:   */   
/* 18:   */   public MusicTicker(Minecraft p_i45112_1_)
/* 19:   */   {
/* 20:19 */     this.field_147677_b = p_i45112_1_;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void update()
/* 24:   */   {
/* 25:27 */     MusicType var1 = this.field_147677_b.func_147109_W();
/* 26:29 */     if (this.field_147678_c != null)
/* 27:   */     {
/* 28:31 */       if (!var1.func_148635_a().equals(this.field_147678_c.func_147650_b()))
/* 29:   */       {
/* 30:33 */         this.field_147677_b.getSoundHandler().func_147683_b(this.field_147678_c);
/* 31:34 */         this.field_147676_d = MathHelper.getRandomIntegerInRange(this.field_147679_a, 0, var1.func_148634_b() / 2);
/* 32:   */       }
/* 33:37 */       if (!this.field_147677_b.getSoundHandler().func_147692_c(this.field_147678_c))
/* 34:   */       {
/* 35:39 */         this.field_147678_c = null;
/* 36:40 */         this.field_147676_d = Math.min(MathHelper.getRandomIntegerInRange(this.field_147679_a, var1.func_148634_b(), var1.func_148633_c()), this.field_147676_d);
/* 37:   */       }
/* 38:   */     }
/* 39:44 */     if ((this.field_147678_c == null) && (this.field_147676_d-- <= 0))
/* 40:   */     {
/* 41:46 */       this.field_147678_c = PositionedSoundRecord.func_147673_a(var1.func_148635_a());
/* 42:47 */       this.field_147677_b.getSoundHandler().playSound(this.field_147678_c);
/* 43:48 */       this.field_147676_d = 2147483647;
/* 44:   */     }
/* 45:   */   }
/* 46:   */   
/* 47:   */   public static enum MusicType
/* 48:   */   {
/* 49:54 */     MENU("MENU", 0, new ResourceLocation("minecraft:music.menu"), 20, 600),  GAME("GAME", 1, new ResourceLocation("minecraft:music.game"), 12000, 24000),  CREATIVE("CREATIVE", 2, new ResourceLocation("minecraft:music.game.creative"), 1200, 3600),  CREDITS("CREDITS", 3, new ResourceLocation("minecraft:music.game.end.credits"), 2147483647, 2147483647),  NETHER("NETHER", 4, new ResourceLocation("minecraft:music.game.nether"), 1200, 3600),  END_BOSS("END_BOSS", 5, new ResourceLocation("minecraft:music.game.end.dragon"), 0, 0),  END("END", 6, new ResourceLocation("minecraft:music.game.end"), 6000, 24000);
/* 50:   */     
/* 51:   */     private final ResourceLocation field_148645_h;
/* 52:   */     private final int field_148646_i;
/* 53:   */     private final int field_148643_j;
/* 54:65 */     private static final MusicType[] $VALUES = { MENU, GAME, CREATIVE, CREDITS, NETHER, END_BOSS, END };
/* 55:   */     private static final String __OBFID = "CL_00001139";
/* 56:   */     
/* 57:   */     private MusicType(String p_i45111_1_, int p_i45111_2_, ResourceLocation p_i45111_3_, int p_i45111_4_, int p_i45111_5_)
/* 58:   */     {
/* 59:70 */       this.field_148645_h = p_i45111_3_;
/* 60:71 */       this.field_148646_i = p_i45111_4_;
/* 61:72 */       this.field_148643_j = p_i45111_5_;
/* 62:   */     }
/* 63:   */     
/* 64:   */     public ResourceLocation func_148635_a()
/* 65:   */     {
/* 66:77 */       return this.field_148645_h;
/* 67:   */     }
/* 68:   */     
/* 69:   */     public int func_148634_b()
/* 70:   */     {
/* 71:82 */       return this.field_148646_i;
/* 72:   */     }
/* 73:   */     
/* 74:   */     public int func_148633_c()
/* 75:   */     {
/* 76:87 */       return this.field_148643_j;
/* 77:   */     }
/* 78:   */   }
/* 79:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.audio.MusicTicker
 * JD-Core Version:    0.7.0.1
 */