/*   1:    */ package net.minecraft.stats;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.item.Item;
/*   6:    */ import net.minecraft.item.ItemStack;
/*   7:    */ import net.minecraft.util.ChatComponentTranslation;
/*   8:    */ import net.minecraft.util.ChatStyle;
/*   9:    */ import net.minecraft.util.EnumChatFormatting;
/*  10:    */ import net.minecraft.util.IChatComponent;
/*  11:    */ import net.minecraft.util.StatCollector;
/*  12:    */ 
/*  13:    */ public class Achievement
/*  14:    */   extends StatBase
/*  15:    */ {
/*  16:    */   public final int displayColumn;
/*  17:    */   public final int displayRow;
/*  18:    */   public final Achievement parentAchievement;
/*  19:    */   private final String achievementDescription;
/*  20:    */   private IStatStringFormat statStringFormatter;
/*  21:    */   public final ItemStack theItemStack;
/*  22:    */   private boolean isSpecial;
/*  23:    */   private static final String __OBFID = "CL_00001466";
/*  24:    */   
/*  25:    */   public Achievement(String p_i45300_1_, String p_i45300_2_, int p_i45300_3_, int p_i45300_4_, Item p_i45300_5_, Achievement p_i45300_6_)
/*  26:    */   {
/*  27: 53 */     this(p_i45300_1_, p_i45300_2_, p_i45300_3_, p_i45300_4_, new ItemStack(p_i45300_5_), p_i45300_6_);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Achievement(String p_i45301_1_, String p_i45301_2_, int p_i45301_3_, int p_i45301_4_, Block p_i45301_5_, Achievement p_i45301_6_)
/*  31:    */   {
/*  32: 58 */     this(p_i45301_1_, p_i45301_2_, p_i45301_3_, p_i45301_4_, new ItemStack(p_i45301_5_), p_i45301_6_);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Achievement(String p_i45302_1_, String p_i45302_2_, int p_i45302_3_, int p_i45302_4_, ItemStack p_i45302_5_, Achievement p_i45302_6_)
/*  36:    */   {
/*  37: 63 */     super(p_i45302_1_, new ChatComponentTranslation("achievement." + p_i45302_2_, new Object[0]));
/*  38: 64 */     this.theItemStack = p_i45302_5_;
/*  39: 65 */     this.achievementDescription = ("achievement." + p_i45302_2_ + ".desc");
/*  40: 66 */     this.displayColumn = p_i45302_3_;
/*  41: 67 */     this.displayRow = p_i45302_4_;
/*  42: 69 */     if (p_i45302_3_ < AchievementList.minDisplayColumn) {
/*  43: 71 */       AchievementList.minDisplayColumn = p_i45302_3_;
/*  44:    */     }
/*  45: 74 */     if (p_i45302_4_ < AchievementList.minDisplayRow) {
/*  46: 76 */       AchievementList.minDisplayRow = p_i45302_4_;
/*  47:    */     }
/*  48: 79 */     if (p_i45302_3_ > AchievementList.maxDisplayColumn) {
/*  49: 81 */       AchievementList.maxDisplayColumn = p_i45302_3_;
/*  50:    */     }
/*  51: 84 */     if (p_i45302_4_ > AchievementList.maxDisplayRow) {
/*  52: 86 */       AchievementList.maxDisplayRow = p_i45302_4_;
/*  53:    */     }
/*  54: 89 */     this.parentAchievement = p_i45302_6_;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Achievement initIndependentStat()
/*  58:    */   {
/*  59: 98 */     this.isIndependent = true;
/*  60: 99 */     return this;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Achievement setSpecial()
/*  64:    */   {
/*  65:108 */     this.isSpecial = true;
/*  66:109 */     return this;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Achievement registerStat()
/*  70:    */   {
/*  71:117 */     super.registerStat();
/*  72:118 */     AchievementList.achievementList.add(this);
/*  73:119 */     return this;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean isAchievement()
/*  77:    */   {
/*  78:127 */     return true;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public IChatComponent func_150951_e()
/*  82:    */   {
/*  83:132 */     IChatComponent var1 = super.func_150951_e();
/*  84:133 */     var1.getChatStyle().setColor(getSpecial() ? EnumChatFormatting.DARK_PURPLE : EnumChatFormatting.GREEN);
/*  85:134 */     return var1;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Achievement func_150953_b(Class p_150958_1_)
/*  89:    */   {
/*  90:139 */     return (Achievement)super.func_150953_b(p_150958_1_);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getDescription()
/*  94:    */   {
/*  95:147 */     return this.statStringFormatter != null ? this.statStringFormatter.formatString(StatCollector.translateToLocal(this.achievementDescription)) : StatCollector.translateToLocal(this.achievementDescription);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Achievement setStatStringFormatter(IStatStringFormat par1IStatStringFormat)
/*  99:    */   {
/* 100:155 */     this.statStringFormatter = par1IStatStringFormat;
/* 101:156 */     return this;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean getSpecial()
/* 105:    */   {
/* 106:165 */     return this.isSpecial;
/* 107:    */   }
/* 108:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.stats.Achievement
 * JD-Core Version:    0.7.0.1
 */