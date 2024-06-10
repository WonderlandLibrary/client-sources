/*   1:    */ package net.minecraft.creativetab;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.List;
/*   5:    */ import net.minecraft.enchantment.Enchantment;
/*   6:    */ import net.minecraft.enchantment.EnchantmentData;
/*   7:    */ import net.minecraft.enchantment.EnumEnchantmentType;
/*   8:    */ import net.minecraft.init.Blocks;
/*   9:    */ import net.minecraft.init.Items;
/*  10:    */ import net.minecraft.item.Item;
/*  11:    */ import net.minecraft.item.ItemEnchantedBook;
/*  12:    */ import net.minecraft.item.ItemStack;
/*  13:    */ import net.minecraft.util.RegistryNamespaced;
/*  14:    */ 
/*  15:    */ public abstract class CreativeTabs
/*  16:    */ {
/*  17: 15 */   public static final CreativeTabs[] creativeTabArray = new CreativeTabs[12];
/*  18: 16 */   public static final CreativeTabs tabBlock = new CreativeTabs(0, "buildingBlocks")
/*  19:    */   {
/*  20:    */     private static final String __OBFID = "CL_00000010";
/*  21:    */     
/*  22:    */     public Item getTabIconItem()
/*  23:    */     {
/*  24: 21 */       return Item.getItemFromBlock(Blocks.brick_block);
/*  25:    */     }
/*  26:    */   };
/*  27: 24 */   public static final CreativeTabs tabDecorations = new CreativeTabs(1, "decorations")
/*  28:    */   {
/*  29:    */     private static final String __OBFID = "CL_00000011";
/*  30:    */     
/*  31:    */     public Item getTabIconItem()
/*  32:    */     {
/*  33: 29 */       return Item.getItemFromBlock(Blocks.double_plant);
/*  34:    */     }
/*  35:    */     
/*  36:    */     public int func_151243_f()
/*  37:    */     {
/*  38: 33 */       return 5;
/*  39:    */     }
/*  40:    */   };
/*  41: 36 */   public static final CreativeTabs tabRedstone = new CreativeTabs(2, "redstone")
/*  42:    */   {
/*  43:    */     private static final String __OBFID = "CL_00000012";
/*  44:    */     
/*  45:    */     public Item getTabIconItem()
/*  46:    */     {
/*  47: 41 */       return Items.redstone;
/*  48:    */     }
/*  49:    */   };
/*  50: 44 */   public static final CreativeTabs tabTransport = new CreativeTabs(3, "transportation")
/*  51:    */   {
/*  52:    */     private static final String __OBFID = "CL_00000014";
/*  53:    */     
/*  54:    */     public Item getTabIconItem()
/*  55:    */     {
/*  56: 49 */       return Item.getItemFromBlock(Blocks.golden_rail);
/*  57:    */     }
/*  58:    */   };
/*  59: 52 */   public static final CreativeTabs tabMisc = new CreativeTabs(4, "misc")
/*  60:    */   {
/*  61:    */     private static final String __OBFID = "CL_00000015";
/*  62:    */     
/*  63:    */     public Item getTabIconItem()
/*  64:    */     {
/*  65: 57 */       return Items.lava_bucket;
/*  66:    */     }
/*  67: 59 */   }.func_111229_a(new EnumEnchantmentType[] { EnumEnchantmentType.all });
/*  68: 60 */   public static final CreativeTabs tabAllSearch = new CreativeTabs(5, "search")
/*  69:    */   {
/*  70:    */     private static final String __OBFID = "CL_00000016";
/*  71:    */     
/*  72:    */     public Item getTabIconItem()
/*  73:    */     {
/*  74: 65 */       return Items.compass;
/*  75:    */     }
/*  76: 67 */   }.setBackgroundImageName("item_search.png");
/*  77: 68 */   public static final CreativeTabs tabFood = new CreativeTabs(6, "food")
/*  78:    */   {
/*  79:    */     private static final String __OBFID = "CL_00000017";
/*  80:    */     
/*  81:    */     public Item getTabIconItem()
/*  82:    */     {
/*  83: 73 */       return Items.apple;
/*  84:    */     }
/*  85:    */   };
/*  86: 76 */   public static final CreativeTabs tabTools = new CreativeTabs(7, "tools")
/*  87:    */   {
/*  88:    */     private static final String __OBFID = "CL_00000018";
/*  89:    */     
/*  90:    */     public Item getTabIconItem()
/*  91:    */     {
/*  92: 81 */       return Items.iron_axe;
/*  93:    */     }
/*  94: 83 */   }.func_111229_a(new EnumEnchantmentType[] { EnumEnchantmentType.digger, EnumEnchantmentType.fishing_rod, EnumEnchantmentType.breakable });
/*  95: 84 */   public static final CreativeTabs tabCombat = new CreativeTabs(8, "combat")
/*  96:    */   {
/*  97:    */     private static final String __OBFID = "CL_00000007";
/*  98:    */     
/*  99:    */     public Item getTabIconItem()
/* 100:    */     {
/* 101: 89 */       return Items.golden_sword;
/* 102:    */     }
/* 103: 91 */   }.func_111229_a(new EnumEnchantmentType[] { EnumEnchantmentType.armor, EnumEnchantmentType.armor_feet, EnumEnchantmentType.armor_head, EnumEnchantmentType.armor_legs, EnumEnchantmentType.armor_torso, EnumEnchantmentType.bow, EnumEnchantmentType.weapon });
/* 104: 92 */   public static final CreativeTabs tabBrewing = new CreativeTabs(9, "brewing")
/* 105:    */   {
/* 106:    */     private static final String __OBFID = "CL_00000008";
/* 107:    */     
/* 108:    */     public Item getTabIconItem()
/* 109:    */     {
/* 110: 97 */       return Items.potionitem;
/* 111:    */     }
/* 112:    */   };
/* 113:100 */   public static final CreativeTabs tabMaterials = new CreativeTabs(10, "materials")
/* 114:    */   {
/* 115:    */     private static final String __OBFID = "CL_00000009";
/* 116:    */     
/* 117:    */     public Item getTabIconItem()
/* 118:    */     {
/* 119:105 */       return Items.stick;
/* 120:    */     }
/* 121:    */   };
/* 122:108 */   public static final CreativeTabs tabInventory = new CreativeTabs(11, "inventory")
/* 123:    */   {
/* 124:    */     private static final String __OBFID = "CL_00000006";
/* 125:    */     
/* 126:    */     public Item getTabIconItem()
/* 127:    */     {
/* 128:113 */       return Item.getItemFromBlock(Blocks.chest);
/* 129:    */     }
/* 130:115 */   }.setBackgroundImageName("inventory.png").setNoScrollbar().setNoTitle();
/* 131:    */   private final int tabIndex;
/* 132:    */   private final String tabLabel;
/* 133:120 */   private String backgroundImageName = "items.png";
/* 134:121 */   private boolean hasScrollbar = true;
/* 135:124 */   private boolean drawTitle = true;
/* 136:    */   private EnumEnchantmentType[] field_111230_s;
/* 137:    */   private ItemStack field_151245_t;
/* 138:    */   private static final String __OBFID = "CL_00000005";
/* 139:    */   
/* 140:    */   public CreativeTabs(int par1, String par2Str)
/* 141:    */   {
/* 142:131 */     this.tabIndex = par1;
/* 143:132 */     this.tabLabel = par2Str;
/* 144:133 */     creativeTabArray[par1] = this;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public int getTabIndex()
/* 148:    */   {
/* 149:138 */     return this.tabIndex;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public String getTabLabel()
/* 153:    */   {
/* 154:143 */     return this.tabLabel;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public String getTranslatedTabLabel()
/* 158:    */   {
/* 159:151 */     return "itemGroup." + getTabLabel();
/* 160:    */   }
/* 161:    */   
/* 162:    */   public ItemStack getIconItemStack()
/* 163:    */   {
/* 164:156 */     if (this.field_151245_t == null) {
/* 165:158 */       this.field_151245_t = new ItemStack(getTabIconItem(), 1, func_151243_f());
/* 166:    */     }
/* 167:161 */     return this.field_151245_t;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public abstract Item getTabIconItem();
/* 171:    */   
/* 172:    */   public int func_151243_f()
/* 173:    */   {
/* 174:168 */     return 0;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public String getBackgroundImageName()
/* 178:    */   {
/* 179:173 */     return this.backgroundImageName;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public CreativeTabs setBackgroundImageName(String par1Str)
/* 183:    */   {
/* 184:178 */     this.backgroundImageName = par1Str;
/* 185:179 */     return this;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public boolean drawInForegroundOfTab()
/* 189:    */   {
/* 190:184 */     return this.drawTitle;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public CreativeTabs setNoTitle()
/* 194:    */   {
/* 195:189 */     this.drawTitle = false;
/* 196:190 */     return this;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public boolean shouldHidePlayerInventory()
/* 200:    */   {
/* 201:195 */     return this.hasScrollbar;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public CreativeTabs setNoScrollbar()
/* 205:    */   {
/* 206:200 */     this.hasScrollbar = false;
/* 207:201 */     return this;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public int getTabColumn()
/* 211:    */   {
/* 212:209 */     return this.tabIndex % 6;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public boolean isTabInFirstRow()
/* 216:    */   {
/* 217:217 */     return this.tabIndex < 6;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public EnumEnchantmentType[] func_111225_m()
/* 221:    */   {
/* 222:222 */     return this.field_111230_s;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public CreativeTabs func_111229_a(EnumEnchantmentType... par1ArrayOfEnumEnchantmentType)
/* 226:    */   {
/* 227:227 */     this.field_111230_s = par1ArrayOfEnumEnchantmentType;
/* 228:228 */     return this;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public boolean func_111226_a(EnumEnchantmentType par1EnumEnchantmentType)
/* 232:    */   {
/* 233:233 */     if (this.field_111230_s == null) {
/* 234:235 */       return false;
/* 235:    */     }
/* 236:239 */     EnumEnchantmentType[] var2 = this.field_111230_s;
/* 237:240 */     int var3 = var2.length;
/* 238:242 */     for (int var4 = 0; var4 < var3; var4++)
/* 239:    */     {
/* 240:244 */       EnumEnchantmentType var5 = var2[var4];
/* 241:246 */       if (var5 == par1EnumEnchantmentType) {
/* 242:248 */         return true;
/* 243:    */       }
/* 244:    */     }
/* 245:252 */     return false;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void displayAllReleventItems(List par1List)
/* 249:    */   {
/* 250:261 */     Iterator var2 = Item.itemRegistry.iterator();
/* 251:263 */     while (var2.hasNext())
/* 252:    */     {
/* 253:265 */       Item var3 = (Item)var2.next();
/* 254:267 */       if ((var3 != null) && (var3.getCreativeTab() == this)) {
/* 255:269 */         var3.getSubItems(var3, this, par1List);
/* 256:    */       }
/* 257:    */     }
/* 258:273 */     if (func_111225_m() != null) {
/* 259:275 */       addEnchantmentBooksToList(par1List, func_111225_m());
/* 260:    */     }
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void addEnchantmentBooksToList(List par1List, EnumEnchantmentType... par2ArrayOfEnumEnchantmentType)
/* 264:    */   {
/* 265:284 */     Enchantment[] var3 = Enchantment.enchantmentsList;
/* 266:285 */     int var4 = var3.length;
/* 267:287 */     for (int var5 = 0; var5 < var4; var5++)
/* 268:    */     {
/* 269:289 */       Enchantment var6 = var3[var5];
/* 270:291 */       if ((var6 != null) && (var6.type != null))
/* 271:    */       {
/* 272:293 */         boolean var7 = false;
/* 273:295 */         for (int var8 = 0; (var8 < par2ArrayOfEnumEnchantmentType.length) && (!var7); var8++) {
/* 274:297 */           if (var6.type == par2ArrayOfEnumEnchantmentType[var8]) {
/* 275:299 */             var7 = true;
/* 276:    */           }
/* 277:    */         }
/* 278:303 */         if (var7) {
/* 279:305 */           par1List.add(Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(var6, var6.getMaxLevel())));
/* 280:    */         }
/* 281:    */       }
/* 282:    */     }
/* 283:    */   }
/* 284:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.creativetab.CreativeTabs
 * JD-Core Version:    0.7.0.1
 */