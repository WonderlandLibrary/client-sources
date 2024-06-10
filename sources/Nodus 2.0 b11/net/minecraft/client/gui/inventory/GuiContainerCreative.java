/*    1:     */ package net.minecraft.client.gui.inventory;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import java.util.Iterator;
/*    5:     */ import java.util.List;
/*    6:     */ import java.util.Map;
/*    7:     */ import java.util.Set;
/*    8:     */ import me.connorm.Nodus.ui.NodusGuiButton;
/*    9:     */ import net.minecraft.client.Minecraft;
/*   10:     */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*   11:     */ import net.minecraft.client.gui.FontRenderer;
/*   12:     */ import net.minecraft.client.gui.GuiTextField;
/*   13:     */ import net.minecraft.client.gui.achievement.GuiAchievements;
/*   14:     */ import net.minecraft.client.gui.achievement.GuiStats;
/*   15:     */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*   16:     */ import net.minecraft.client.renderer.InventoryEffectRenderer;
/*   17:     */ import net.minecraft.client.renderer.RenderHelper;
/*   18:     */ import net.minecraft.client.renderer.entity.RenderItem;
/*   19:     */ import net.minecraft.client.renderer.texture.TextureManager;
/*   20:     */ import net.minecraft.client.resources.I18n;
/*   21:     */ import net.minecraft.client.settings.GameSettings;
/*   22:     */ import net.minecraft.creativetab.CreativeTabs;
/*   23:     */ import net.minecraft.enchantment.Enchantment;
/*   24:     */ import net.minecraft.enchantment.EnchantmentHelper;
/*   25:     */ import net.minecraft.entity.player.EntityPlayer;
/*   26:     */ import net.minecraft.entity.player.InventoryPlayer;
/*   27:     */ import net.minecraft.init.Items;
/*   28:     */ import net.minecraft.inventory.Container;
/*   29:     */ import net.minecraft.inventory.IInventory;
/*   30:     */ import net.minecraft.inventory.InventoryBasic;
/*   31:     */ import net.minecraft.inventory.Slot;
/*   32:     */ import net.minecraft.item.EnumRarity;
/*   33:     */ import net.minecraft.item.Item;
/*   34:     */ import net.minecraft.item.ItemEnchantedBook;
/*   35:     */ import net.minecraft.item.ItemStack;
/*   36:     */ import net.minecraft.util.EnumChatFormatting;
/*   37:     */ import net.minecraft.util.IIcon;
/*   38:     */ import net.minecraft.util.RegistryNamespaced;
/*   39:     */ import net.minecraft.util.ResourceLocation;
/*   40:     */ import org.lwjgl.input.Keyboard;
/*   41:     */ import org.lwjgl.input.Mouse;
/*   42:     */ import org.lwjgl.opengl.GL11;
/*   43:     */ 
/*   44:     */ public class GuiContainerCreative
/*   45:     */   extends InventoryEffectRenderer
/*   46:     */ {
/*   47:  39 */   private static final ResourceLocation field_147061_u = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
/*   48:  40 */   private static InventoryBasic field_147060_v = new InventoryBasic("tmp", true, 45);
/*   49:  41 */   private static int field_147058_w = CreativeTabs.tabBlock.getTabIndex();
/*   50:     */   private float field_147067_x;
/*   51:     */   private boolean field_147066_y;
/*   52:     */   private boolean field_147065_z;
/*   53:     */   private GuiTextField field_147062_A;
/*   54:     */   private List field_147063_B;
/*   55:     */   private Slot field_147064_C;
/*   56:     */   private boolean field_147057_D;
/*   57:     */   private CreativeCrafting field_147059_E;
/*   58:     */   private static final String __OBFID = "CL_00000752";
/*   59:     */   
/*   60:     */   public GuiContainerCreative(EntityPlayer par1EntityPlayer)
/*   61:     */   {
/*   62:  54 */     super(new ContainerCreative(par1EntityPlayer));
/*   63:  55 */     par1EntityPlayer.openContainer = this.field_147002_h;
/*   64:  56 */     this.field_146291_p = true;
/*   65:  57 */     this.field_147000_g = 136;
/*   66:  58 */     this.field_146999_f = 195;
/*   67:     */   }
/*   68:     */   
/*   69:     */   public void updateScreen()
/*   70:     */   {
/*   71:  66 */     if (!this.mc.playerController.isInCreativeMode()) {
/*   72:  68 */       this.mc.displayGuiScreen(new GuiInventory(this.mc.thePlayer));
/*   73:     */     }
/*   74:     */   }
/*   75:     */   
/*   76:     */   protected void func_146984_a(Slot p_146984_1_, int p_146984_2_, int p_146984_3_, int p_146984_4_)
/*   77:     */   {
/*   78:  74 */     this.field_147057_D = true;
/*   79:  75 */     boolean var5 = p_146984_4_ == 1;
/*   80:  76 */     p_146984_4_ = (p_146984_2_ == -999) && (p_146984_4_ == 0) ? 4 : p_146984_4_;
/*   81:  80 */     if ((p_146984_1_ == null) && (field_147058_w != CreativeTabs.tabInventory.getTabIndex()) && (p_146984_4_ != 5))
/*   82:     */     {
/*   83:  82 */       InventoryPlayer var11 = this.mc.thePlayer.inventory;
/*   84:  84 */       if (var11.getItemStack() != null)
/*   85:     */       {
/*   86:  86 */         if (p_146984_3_ == 0)
/*   87:     */         {
/*   88:  88 */           this.mc.thePlayer.dropPlayerItemWithRandomChoice(var11.getItemStack(), true);
/*   89:  89 */           this.mc.playerController.sendPacketDropItem(var11.getItemStack());
/*   90:  90 */           var11.setItemStack(null);
/*   91:     */         }
/*   92:  93 */         if (p_146984_3_ == 1)
/*   93:     */         {
/*   94:  95 */           ItemStack var7 = var11.getItemStack().splitStack(1);
/*   95:  96 */           this.mc.thePlayer.dropPlayerItemWithRandomChoice(var7, true);
/*   96:  97 */           this.mc.playerController.sendPacketDropItem(var7);
/*   97:  99 */           if (var11.getItemStack().stackSize == 0) {
/*   98: 101 */             var11.setItemStack(null);
/*   99:     */           }
/*  100:     */         }
/*  101:     */       }
/*  102:     */     }
/*  103: 110 */     else if ((p_146984_1_ == this.field_147064_C) && (var5))
/*  104:     */     {
/*  105: 112 */       for (int var10 = 0; var10 < this.mc.thePlayer.inventoryContainer.getInventory().size(); var10++) {
/*  106: 114 */         this.mc.playerController.sendSlotPacket(null, var10);
/*  107:     */       }
/*  108:     */     }
/*  109: 121 */     else if (field_147058_w == CreativeTabs.tabInventory.getTabIndex())
/*  110:     */     {
/*  111: 123 */       if (p_146984_1_ == this.field_147064_C)
/*  112:     */       {
/*  113: 125 */         this.mc.thePlayer.inventory.setItemStack(null);
/*  114:     */       }
/*  115: 127 */       else if ((p_146984_4_ == 4) && (p_146984_1_ != null) && (p_146984_1_.getHasStack()))
/*  116:     */       {
/*  117: 129 */         ItemStack var6 = p_146984_1_.decrStackSize(p_146984_3_ == 0 ? 1 : p_146984_1_.getStack().getMaxStackSize());
/*  118: 130 */         this.mc.thePlayer.dropPlayerItemWithRandomChoice(var6, true);
/*  119: 131 */         this.mc.playerController.sendPacketDropItem(var6);
/*  120:     */       }
/*  121: 133 */       else if ((p_146984_4_ == 4) && (this.mc.thePlayer.inventory.getItemStack() != null))
/*  122:     */       {
/*  123: 135 */         this.mc.thePlayer.dropPlayerItemWithRandomChoice(this.mc.thePlayer.inventory.getItemStack(), true);
/*  124: 136 */         this.mc.playerController.sendPacketDropItem(this.mc.thePlayer.inventory.getItemStack());
/*  125: 137 */         this.mc.thePlayer.inventory.setItemStack(null);
/*  126:     */       }
/*  127:     */       else
/*  128:     */       {
/*  129: 141 */         this.mc.thePlayer.inventoryContainer.slotClick(p_146984_1_ == null ? p_146984_2_ : ((CreativeSlot)p_146984_1_).field_148332_b.slotNumber, p_146984_3_, p_146984_4_, this.mc.thePlayer);
/*  130: 142 */         this.mc.thePlayer.inventoryContainer.detectAndSendChanges();
/*  131:     */       }
/*  132:     */     }
/*  133: 145 */     else if ((p_146984_4_ != 5) && (p_146984_1_.inventory == field_147060_v))
/*  134:     */     {
/*  135: 147 */       InventoryPlayer var11 = this.mc.thePlayer.inventory;
/*  136: 148 */       ItemStack var7 = var11.getItemStack();
/*  137: 149 */       ItemStack var8 = p_146984_1_.getStack();
/*  138: 152 */       if (p_146984_4_ == 2)
/*  139:     */       {
/*  140: 154 */         if ((var8 != null) && (p_146984_3_ >= 0) && (p_146984_3_ < 9))
/*  141:     */         {
/*  142: 156 */           ItemStack var9 = var8.copy();
/*  143: 157 */           var9.stackSize = var9.getMaxStackSize();
/*  144: 158 */           this.mc.thePlayer.inventory.setInventorySlotContents(p_146984_3_, var9);
/*  145: 159 */           this.mc.thePlayer.inventoryContainer.detectAndSendChanges();
/*  146:     */         }
/*  147: 162 */         return;
/*  148:     */       }
/*  149: 165 */       if (p_146984_4_ == 3)
/*  150:     */       {
/*  151: 167 */         if ((var11.getItemStack() == null) && (p_146984_1_.getHasStack()))
/*  152:     */         {
/*  153: 169 */           ItemStack var9 = p_146984_1_.getStack().copy();
/*  154: 170 */           var9.stackSize = var9.getMaxStackSize();
/*  155: 171 */           var11.setItemStack(var9);
/*  156:     */         }
/*  157: 174 */         return;
/*  158:     */       }
/*  159: 177 */       if (p_146984_4_ == 4)
/*  160:     */       {
/*  161: 179 */         if (var8 != null)
/*  162:     */         {
/*  163: 181 */           ItemStack var9 = var8.copy();
/*  164: 182 */           var9.stackSize = (p_146984_3_ == 0 ? 1 : var9.getMaxStackSize());
/*  165: 183 */           this.mc.thePlayer.dropPlayerItemWithRandomChoice(var9, true);
/*  166: 184 */           this.mc.playerController.sendPacketDropItem(var9);
/*  167:     */         }
/*  168: 187 */         return;
/*  169:     */       }
/*  170: 190 */       if ((var7 != null) && (var8 != null) && (var7.isItemEqual(var8)))
/*  171:     */       {
/*  172: 192 */         if (p_146984_3_ == 0)
/*  173:     */         {
/*  174: 194 */           if (var5) {
/*  175: 196 */             var7.stackSize = var7.getMaxStackSize();
/*  176: 198 */           } else if (var7.stackSize < var7.getMaxStackSize()) {
/*  177: 200 */             var7.stackSize += 1;
/*  178:     */           }
/*  179:     */         }
/*  180: 203 */         else if (var7.stackSize <= 1) {
/*  181: 205 */           var11.setItemStack(null);
/*  182:     */         } else {
/*  183: 209 */           var7.stackSize -= 1;
/*  184:     */         }
/*  185:     */       }
/*  186: 212 */       else if ((var8 != null) && (var7 == null))
/*  187:     */       {
/*  188: 214 */         var11.setItemStack(ItemStack.copyItemStack(var8));
/*  189: 215 */         var7 = var11.getItemStack();
/*  190: 217 */         if (var5) {
/*  191: 219 */           var7.stackSize = var7.getMaxStackSize();
/*  192:     */         }
/*  193:     */       }
/*  194:     */       else
/*  195:     */       {
/*  196: 224 */         var11.setItemStack(null);
/*  197:     */       }
/*  198:     */     }
/*  199:     */     else
/*  200:     */     {
/*  201: 229 */       this.field_147002_h.slotClick(p_146984_1_ == null ? p_146984_2_ : p_146984_1_.slotNumber, p_146984_3_, p_146984_4_, this.mc.thePlayer);
/*  202: 231 */       if (Container.func_94532_c(p_146984_3_) == 2)
/*  203:     */       {
/*  204: 233 */         for (int var10 = 0; var10 < 9; var10++) {
/*  205: 235 */           this.mc.playerController.sendSlotPacket(this.field_147002_h.getSlot(45 + var10).getStack(), 36 + var10);
/*  206:     */         }
/*  207:     */       }
/*  208: 238 */       else if (p_146984_1_ != null)
/*  209:     */       {
/*  210: 240 */         ItemStack var6 = this.field_147002_h.getSlot(p_146984_1_.slotNumber).getStack();
/*  211: 241 */         this.mc.playerController.sendSlotPacket(var6, p_146984_1_.slotNumber - this.field_147002_h.inventorySlots.size() + 9 + 36);
/*  212:     */       }
/*  213:     */     }
/*  214:     */   }
/*  215:     */   
/*  216:     */   public void initGui()
/*  217:     */   {
/*  218: 253 */     if (this.mc.playerController.isInCreativeMode())
/*  219:     */     {
/*  220: 255 */       super.initGui();
/*  221: 256 */       this.buttonList.clear();
/*  222: 257 */       Keyboard.enableRepeatEvents(true);
/*  223: 258 */       this.field_147062_A = new GuiTextField(this.fontRendererObj, this.field_147003_i + 82, this.field_147009_r + 6, 89, this.fontRendererObj.FONT_HEIGHT);
/*  224: 259 */       this.field_147062_A.func_146203_f(15);
/*  225: 260 */       this.field_147062_A.func_146185_a(false);
/*  226: 261 */       this.field_147062_A.func_146189_e(false);
/*  227: 262 */       this.field_147062_A.func_146193_g(16777215);
/*  228: 263 */       int var1 = field_147058_w;
/*  229: 264 */       field_147058_w = -1;
/*  230: 265 */       func_147050_b(CreativeTabs.creativeTabArray[var1]);
/*  231: 266 */       this.field_147059_E = new CreativeCrafting(this.mc);
/*  232: 267 */       this.mc.thePlayer.inventoryContainer.addCraftingToCrafters(this.field_147059_E);
/*  233:     */     }
/*  234:     */     else
/*  235:     */     {
/*  236: 271 */       this.mc.displayGuiScreen(new GuiInventory(this.mc.thePlayer));
/*  237:     */     }
/*  238:     */   }
/*  239:     */   
/*  240:     */   public void onGuiClosed()
/*  241:     */   {
/*  242: 280 */     super.onGuiClosed();
/*  243: 282 */     if ((this.mc.thePlayer != null) && (this.mc.thePlayer.inventory != null)) {
/*  244: 284 */       this.mc.thePlayer.inventoryContainer.removeCraftingFromCrafters(this.field_147059_E);
/*  245:     */     }
/*  246: 287 */     Keyboard.enableRepeatEvents(false);
/*  247:     */   }
/*  248:     */   
/*  249:     */   protected void keyTyped(char par1, int par2)
/*  250:     */   {
/*  251: 295 */     if (field_147058_w != CreativeTabs.tabAllSearch.getTabIndex())
/*  252:     */     {
/*  253: 297 */       if (GameSettings.isKeyDown(this.mc.gameSettings.keyBindChat)) {
/*  254: 299 */         func_147050_b(CreativeTabs.tabAllSearch);
/*  255:     */       } else {
/*  256: 303 */         super.keyTyped(par1, par2);
/*  257:     */       }
/*  258:     */     }
/*  259:     */     else
/*  260:     */     {
/*  261: 308 */       if (this.field_147057_D)
/*  262:     */       {
/*  263: 310 */         this.field_147057_D = false;
/*  264: 311 */         this.field_147062_A.setText("");
/*  265:     */       }
/*  266: 314 */       if (!func_146983_a(par2)) {
/*  267: 316 */         if (this.field_147062_A.textboxKeyTyped(par1, par2)) {
/*  268: 318 */           func_147053_i();
/*  269:     */         } else {
/*  270: 322 */           super.keyTyped(par1, par2);
/*  271:     */         }
/*  272:     */       }
/*  273:     */     }
/*  274:     */   }
/*  275:     */   
/*  276:     */   private void func_147053_i()
/*  277:     */   {
/*  278: 330 */     ContainerCreative var1 = (ContainerCreative)this.field_147002_h;
/*  279: 331 */     var1.field_148330_a.clear();
/*  280: 332 */     Iterator var2 = Item.itemRegistry.iterator();
/*  281: 334 */     while (var2.hasNext())
/*  282:     */     {
/*  283: 336 */       Item var3 = (Item)var2.next();
/*  284: 338 */       if ((var3 != null) && (var3.getCreativeTab() != null)) {
/*  285: 340 */         var3.getSubItems(var3, null, var1.field_148330_a);
/*  286:     */       }
/*  287:     */     }
/*  288: 344 */     Enchantment[] var8 = Enchantment.enchantmentsList;
/*  289: 345 */     int var9 = var8.length;
/*  290: 347 */     for (int var4 = 0; var4 < var9; var4++)
/*  291:     */     {
/*  292: 349 */       Enchantment var5 = var8[var4];
/*  293: 351 */       if ((var5 != null) && (var5.type != null)) {
/*  294: 353 */         Items.enchanted_book.func_92113_a(var5, var1.field_148330_a);
/*  295:     */       }
/*  296:     */     }
/*  297: 357 */     var2 = var1.field_148330_a.iterator();
/*  298: 358 */     String var10 = this.field_147062_A.getText().toLowerCase();
/*  299: 360 */     while (var2.hasNext())
/*  300:     */     {
/*  301: 362 */       ItemStack var11 = (ItemStack)var2.next();
/*  302: 363 */       boolean var12 = false;
/*  303: 364 */       Iterator var6 = var11.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips).iterator();
/*  304: 368 */       while (var6.hasNext())
/*  305:     */       {
/*  306: 370 */         String var7 = (String)var6.next();
/*  307: 372 */         if (var7.toLowerCase().contains(var10)) {
/*  308: 377 */           var12 = true;
/*  309:     */         }
/*  310:     */       }
/*  311: 380 */       if (!var12) {
/*  312: 382 */         var2.remove();
/*  313:     */       }
/*  314:     */     }
/*  315: 389 */     this.field_147067_x = 0.0F;
/*  316: 390 */     var1.func_148329_a(0.0F);
/*  317:     */   }
/*  318:     */   
/*  319:     */   protected void func_146979_b(int p_146979_1_, int p_146979_2_)
/*  320:     */   {
/*  321: 395 */     CreativeTabs var3 = CreativeTabs.creativeTabArray[field_147058_w];
/*  322: 397 */     if (var3.drawInForegroundOfTab())
/*  323:     */     {
/*  324: 399 */       GL11.glDisable(3042);
/*  325: 400 */       this.fontRendererObj.drawString(I18n.format(var3.getTranslatedTabLabel(), new Object[0]), 8, 6, 4210752);
/*  326:     */     }
/*  327:     */   }
/*  328:     */   
/*  329:     */   protected void mouseClicked(int par1, int par2, int par3)
/*  330:     */   {
/*  331: 409 */     if (par3 == 0)
/*  332:     */     {
/*  333: 411 */       int var4 = par1 - this.field_147003_i;
/*  334: 412 */       int var5 = par2 - this.field_147009_r;
/*  335: 413 */       CreativeTabs[] var6 = CreativeTabs.creativeTabArray;
/*  336: 414 */       int var7 = var6.length;
/*  337: 416 */       for (int var8 = 0; var8 < var7; var8++)
/*  338:     */       {
/*  339: 418 */         CreativeTabs var9 = var6[var8];
/*  340: 420 */         if (func_147049_a(var9, var4, var5)) {
/*  341: 422 */           return;
/*  342:     */         }
/*  343:     */       }
/*  344:     */     }
/*  345: 427 */     super.mouseClicked(par1, par2, par3);
/*  346:     */   }
/*  347:     */   
/*  348:     */   protected void mouseMovedOrUp(int p_146286_1_, int p_146286_2_, int p_146286_3_)
/*  349:     */   {
/*  350: 432 */     if (p_146286_3_ == 0)
/*  351:     */     {
/*  352: 434 */       int var4 = p_146286_1_ - this.field_147003_i;
/*  353: 435 */       int var5 = p_146286_2_ - this.field_147009_r;
/*  354: 436 */       CreativeTabs[] var6 = CreativeTabs.creativeTabArray;
/*  355: 437 */       int var7 = var6.length;
/*  356: 439 */       for (int var8 = 0; var8 < var7; var8++)
/*  357:     */       {
/*  358: 441 */         CreativeTabs var9 = var6[var8];
/*  359: 443 */         if (func_147049_a(var9, var4, var5))
/*  360:     */         {
/*  361: 445 */           func_147050_b(var9);
/*  362: 446 */           return;
/*  363:     */         }
/*  364:     */       }
/*  365:     */     }
/*  366: 451 */     super.mouseMovedOrUp(p_146286_1_, p_146286_2_, p_146286_3_);
/*  367:     */   }
/*  368:     */   
/*  369:     */   private boolean func_147055_p()
/*  370:     */   {
/*  371: 456 */     return (field_147058_w != CreativeTabs.tabInventory.getTabIndex()) && (CreativeTabs.creativeTabArray[field_147058_w].shouldHidePlayerInventory()) && (((ContainerCreative)this.field_147002_h).func_148328_e());
/*  372:     */   }
/*  373:     */   
/*  374:     */   private void func_147050_b(CreativeTabs p_147050_1_)
/*  375:     */   {
/*  376: 461 */     int var2 = field_147058_w;
/*  377: 462 */     field_147058_w = p_147050_1_.getTabIndex();
/*  378: 463 */     ContainerCreative var3 = (ContainerCreative)this.field_147002_h;
/*  379: 464 */     this.field_147008_s.clear();
/*  380: 465 */     var3.field_148330_a.clear();
/*  381: 466 */     p_147050_1_.displayAllReleventItems(var3.field_148330_a);
/*  382: 468 */     if (p_147050_1_ == CreativeTabs.tabInventory)
/*  383:     */     {
/*  384: 470 */       Container var4 = this.mc.thePlayer.inventoryContainer;
/*  385: 472 */       if (this.field_147063_B == null) {
/*  386: 474 */         this.field_147063_B = var3.inventorySlots;
/*  387:     */       }
/*  388: 477 */       var3.inventorySlots = new ArrayList();
/*  389: 479 */       for (int var5 = 0; var5 < var4.inventorySlots.size(); var5++)
/*  390:     */       {
/*  391: 481 */         CreativeSlot var6 = new CreativeSlot((Slot)var4.inventorySlots.get(var5), var5);
/*  392: 482 */         var3.inventorySlots.add(var6);
/*  393: 487 */         if ((var5 >= 5) && (var5 < 9))
/*  394:     */         {
/*  395: 489 */           int var7 = var5 - 5;
/*  396: 490 */           int var8 = var7 / 2;
/*  397: 491 */           int var9 = var7 % 2;
/*  398: 492 */           var6.xDisplayPosition = (9 + var8 * 54);
/*  399: 493 */           var6.yDisplayPosition = (6 + var9 * 27);
/*  400:     */         }
/*  401: 495 */         else if ((var5 >= 0) && (var5 < 5))
/*  402:     */         {
/*  403: 497 */           var6.yDisplayPosition = -2000;
/*  404: 498 */           var6.xDisplayPosition = -2000;
/*  405:     */         }
/*  406: 500 */         else if (var5 < var4.inventorySlots.size())
/*  407:     */         {
/*  408: 502 */           int var7 = var5 - 9;
/*  409: 503 */           int var8 = var7 % 9;
/*  410: 504 */           int var9 = var7 / 9;
/*  411: 505 */           var6.xDisplayPosition = (9 + var8 * 18);
/*  412: 507 */           if (var5 >= 36) {
/*  413: 509 */             var6.yDisplayPosition = 112;
/*  414:     */           } else {
/*  415: 513 */             var6.yDisplayPosition = (54 + var9 * 18);
/*  416:     */           }
/*  417:     */         }
/*  418:     */       }
/*  419: 518 */       this.field_147064_C = new Slot(field_147060_v, 0, 173, 112);
/*  420: 519 */       var3.inventorySlots.add(this.field_147064_C);
/*  421:     */     }
/*  422: 521 */     else if (var2 == CreativeTabs.tabInventory.getTabIndex())
/*  423:     */     {
/*  424: 523 */       var3.inventorySlots = this.field_147063_B;
/*  425: 524 */       this.field_147063_B = null;
/*  426:     */     }
/*  427: 527 */     if (this.field_147062_A != null) {
/*  428: 529 */       if (p_147050_1_ == CreativeTabs.tabAllSearch)
/*  429:     */       {
/*  430: 531 */         this.field_147062_A.func_146189_e(true);
/*  431: 532 */         this.field_147062_A.func_146205_d(false);
/*  432: 533 */         this.field_147062_A.setFocused(true);
/*  433: 534 */         this.field_147062_A.setText("");
/*  434: 535 */         func_147053_i();
/*  435:     */       }
/*  436:     */       else
/*  437:     */       {
/*  438: 539 */         this.field_147062_A.func_146189_e(false);
/*  439: 540 */         this.field_147062_A.func_146205_d(true);
/*  440: 541 */         this.field_147062_A.setFocused(false);
/*  441:     */       }
/*  442:     */     }
/*  443: 545 */     this.field_147067_x = 0.0F;
/*  444: 546 */     var3.func_148329_a(0.0F);
/*  445:     */   }
/*  446:     */   
/*  447:     */   public void handleMouseInput()
/*  448:     */   {
/*  449: 554 */     super.handleMouseInput();
/*  450: 555 */     int var1 = Mouse.getEventDWheel();
/*  451: 557 */     if ((var1 != 0) && (func_147055_p()))
/*  452:     */     {
/*  453: 559 */       int var2 = ((ContainerCreative)this.field_147002_h).field_148330_a.size() / 9 - 5 + 1;
/*  454: 561 */       if (var1 > 0) {
/*  455: 563 */         var1 = 1;
/*  456:     */       }
/*  457: 566 */       if (var1 < 0) {
/*  458: 568 */         var1 = -1;
/*  459:     */       }
/*  460: 571 */       this.field_147067_x = ((float)(this.field_147067_x - var1 / var2));
/*  461: 573 */       if (this.field_147067_x < 0.0F) {
/*  462: 575 */         this.field_147067_x = 0.0F;
/*  463:     */       }
/*  464: 578 */       if (this.field_147067_x > 1.0F) {
/*  465: 580 */         this.field_147067_x = 1.0F;
/*  466:     */       }
/*  467: 583 */       ((ContainerCreative)this.field_147002_h).func_148329_a(this.field_147067_x);
/*  468:     */     }
/*  469:     */   }
/*  470:     */   
/*  471:     */   public void drawScreen(int par1, int par2, float par3)
/*  472:     */   {
/*  473: 592 */     boolean var4 = Mouse.isButtonDown(0);
/*  474: 593 */     int var5 = this.field_147003_i;
/*  475: 594 */     int var6 = this.field_147009_r;
/*  476: 595 */     int var7 = var5 + 175;
/*  477: 596 */     int var8 = var6 + 18;
/*  478: 597 */     int var9 = var7 + 14;
/*  479: 598 */     int var10 = var8 + 112;
/*  480: 600 */     if ((!this.field_147065_z) && (var4) && (par1 >= var7) && (par2 >= var8) && (par1 < var9) && (par2 < var10)) {
/*  481: 602 */       this.field_147066_y = func_147055_p();
/*  482:     */     }
/*  483: 605 */     if (!var4) {
/*  484: 607 */       this.field_147066_y = false;
/*  485:     */     }
/*  486: 610 */     this.field_147065_z = var4;
/*  487: 612 */     if (this.field_147066_y)
/*  488:     */     {
/*  489: 614 */       this.field_147067_x = ((par2 - var8 - 7.5F) / (var10 - var8 - 15.0F));
/*  490: 616 */       if (this.field_147067_x < 0.0F) {
/*  491: 618 */         this.field_147067_x = 0.0F;
/*  492:     */       }
/*  493: 621 */       if (this.field_147067_x > 1.0F) {
/*  494: 623 */         this.field_147067_x = 1.0F;
/*  495:     */       }
/*  496: 626 */       ((ContainerCreative)this.field_147002_h).func_148329_a(this.field_147067_x);
/*  497:     */     }
/*  498: 629 */     super.drawScreen(par1, par2, par3);
/*  499: 630 */     CreativeTabs[] var11 = CreativeTabs.creativeTabArray;
/*  500: 631 */     int var12 = var11.length;
/*  501: 633 */     for (int var13 = 0; var13 < var12; var13++)
/*  502:     */     {
/*  503: 635 */       CreativeTabs var14 = var11[var13];
/*  504: 637 */       if (func_147052_b(var14, par1, par2)) {
/*  505:     */         break;
/*  506:     */       }
/*  507:     */     }
/*  508: 643 */     if ((this.field_147064_C != null) && (field_147058_w == CreativeTabs.tabInventory.getTabIndex()) && (func_146978_c(this.field_147064_C.xDisplayPosition, this.field_147064_C.yDisplayPosition, 16, 16, par1, par2))) {
/*  509: 645 */       func_146279_a(I18n.format("inventory.binSlot", new Object[0]), par1, par2);
/*  510:     */     }
/*  511: 648 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  512: 649 */     GL11.glDisable(2896);
/*  513:     */   }
/*  514:     */   
/*  515:     */   protected void func_146285_a(ItemStack p_146285_1_, int p_146285_2_, int p_146285_3_)
/*  516:     */   {
/*  517: 654 */     if (field_147058_w == CreativeTabs.tabAllSearch.getTabIndex())
/*  518:     */     {
/*  519: 656 */       List var4 = p_146285_1_.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
/*  520: 657 */       CreativeTabs var5 = p_146285_1_.getItem().getCreativeTab();
/*  521: 659 */       if ((var5 == null) && (p_146285_1_.getItem() == Items.enchanted_book))
/*  522:     */       {
/*  523: 661 */         Map var6 = EnchantmentHelper.getEnchantments(p_146285_1_);
/*  524: 663 */         if (var6.size() == 1)
/*  525:     */         {
/*  526: 665 */           Enchantment var7 = Enchantment.enchantmentsList[((java.lang.Integer)var6.keySet().iterator().next()).intValue()];
/*  527: 666 */           CreativeTabs[] var8 = CreativeTabs.creativeTabArray;
/*  528: 667 */           int var9 = var8.length;
/*  529: 669 */           for (int var10 = 0; var10 < var9; var10++)
/*  530:     */           {
/*  531: 671 */             CreativeTabs var11 = var8[var10];
/*  532: 673 */             if (var11.func_111226_a(var7.type))
/*  533:     */             {
/*  534: 675 */               var5 = var11;
/*  535: 676 */               break;
/*  536:     */             }
/*  537:     */           }
/*  538:     */         }
/*  539:     */       }
/*  540: 682 */       if (var5 != null) {
/*  541: 684 */         var4.add(1, EnumChatFormatting.BOLD + EnumChatFormatting.BLUE + I18n.format(var5.getTranslatedTabLabel(), new Object[0]));
/*  542:     */       }
/*  543: 687 */       for (int var12 = 0; var12 < var4.size(); var12++) {
/*  544: 689 */         if (var12 == 0) {
/*  545: 691 */           var4.set(var12, p_146285_1_.getRarity().rarityColor + (String)var4.get(var12));
/*  546:     */         } else {
/*  547: 695 */           var4.set(var12, EnumChatFormatting.GRAY + (String)var4.get(var12));
/*  548:     */         }
/*  549:     */       }
/*  550: 699 */       func_146283_a(var4, p_146285_2_, p_146285_3_);
/*  551:     */     }
/*  552:     */     else
/*  553:     */     {
/*  554: 703 */       super.func_146285_a(p_146285_1_, p_146285_2_, p_146285_3_);
/*  555:     */     }
/*  556:     */   }
/*  557:     */   
/*  558:     */   protected void func_146976_a(float p_146976_1_, int p_146976_2_, int p_146976_3_)
/*  559:     */   {
/*  560: 709 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  561: 710 */     RenderHelper.enableGUIStandardItemLighting();
/*  562: 711 */     CreativeTabs var4 = CreativeTabs.creativeTabArray[field_147058_w];
/*  563: 712 */     CreativeTabs[] var5 = CreativeTabs.creativeTabArray;
/*  564: 713 */     int var6 = var5.length;
/*  565: 716 */     for (int var7 = 0; var7 < var6; var7++)
/*  566:     */     {
/*  567: 718 */       CreativeTabs var8 = var5[var7];
/*  568: 719 */       this.mc.getTextureManager().bindTexture(field_147061_u);
/*  569: 721 */       if (var8.getTabIndex() != field_147058_w) {
/*  570: 723 */         func_147051_a(var8);
/*  571:     */       }
/*  572:     */     }
/*  573: 727 */     this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/creative_inventory/tab_" + var4.getBackgroundImageName()));
/*  574: 728 */     drawTexturedModalRect(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
/*  575: 729 */     this.field_147062_A.drawTextBox();
/*  576: 730 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  577: 731 */     int var9 = this.field_147003_i + 175;
/*  578: 732 */     var6 = this.field_147009_r + 18;
/*  579: 733 */     var7 = var6 + 112;
/*  580: 734 */     this.mc.getTextureManager().bindTexture(field_147061_u);
/*  581: 736 */     if (var4.shouldHidePlayerInventory()) {
/*  582: 738 */       drawTexturedModalRect(var9, var6 + (int)((var7 - var6 - 17) * this.field_147067_x), 'è' + (func_147055_p() ? 0 : 12), 0, 12, 15);
/*  583:     */     }
/*  584: 741 */     func_147051_a(var4);
/*  585: 743 */     if (var4 == CreativeTabs.tabInventory) {
/*  586: 745 */       GuiInventory.func_147046_a(this.field_147003_i + 43, this.field_147009_r + 45, 20, this.field_147003_i + 43 - p_146976_2_, this.field_147009_r + 45 - 30 - p_146976_3_, this.mc.thePlayer);
/*  587:     */     }
/*  588:     */   }
/*  589:     */   
/*  590:     */   protected boolean func_147049_a(CreativeTabs p_147049_1_, int p_147049_2_, int p_147049_3_)
/*  591:     */   {
/*  592: 751 */     int var4 = p_147049_1_.getTabColumn();
/*  593: 752 */     int var5 = 28 * var4;
/*  594: 753 */     byte var6 = 0;
/*  595: 755 */     if (var4 == 5) {
/*  596: 757 */       var5 = this.field_146999_f - 28 + 2;
/*  597: 759 */     } else if (var4 > 0) {
/*  598: 761 */       var5 += var4;
/*  599:     */     }
/*  600:     */     int var7;
/*  601:     */     int var7;
/*  602: 766 */     if (p_147049_1_.isTabInFirstRow()) {
/*  603: 768 */       var7 = var6 - 32;
/*  604:     */     } else {
/*  605: 772 */       var7 = var6 + this.field_147000_g;
/*  606:     */     }
/*  607: 775 */     return (p_147049_2_ >= var5) && (p_147049_2_ <= var5 + 28) && (p_147049_3_ >= var7) && (p_147049_3_ <= var7 + 32);
/*  608:     */   }
/*  609:     */   
/*  610:     */   protected boolean func_147052_b(CreativeTabs p_147052_1_, int p_147052_2_, int p_147052_3_)
/*  611:     */   {
/*  612: 780 */     int var4 = p_147052_1_.getTabColumn();
/*  613: 781 */     int var5 = 28 * var4;
/*  614: 782 */     byte var6 = 0;
/*  615: 784 */     if (var4 == 5) {
/*  616: 786 */       var5 = this.field_146999_f - 28 + 2;
/*  617: 788 */     } else if (var4 > 0) {
/*  618: 790 */       var5 += var4;
/*  619:     */     }
/*  620:     */     int var7;
/*  621:     */     int var7;
/*  622: 795 */     if (p_147052_1_.isTabInFirstRow()) {
/*  623: 797 */       var7 = var6 - 32;
/*  624:     */     } else {
/*  625: 801 */       var7 = var6 + this.field_147000_g;
/*  626:     */     }
/*  627: 804 */     if (func_146978_c(var5 + 3, var7 + 3, 23, 27, p_147052_2_, p_147052_3_))
/*  628:     */     {
/*  629: 806 */       func_146279_a(I18n.format(p_147052_1_.getTranslatedTabLabel(), new Object[0]), p_147052_2_, p_147052_3_);
/*  630: 807 */       return true;
/*  631:     */     }
/*  632: 811 */     return false;
/*  633:     */   }
/*  634:     */   
/*  635:     */   protected void func_147051_a(CreativeTabs p_147051_1_)
/*  636:     */   {
/*  637: 817 */     boolean var2 = p_147051_1_.getTabIndex() == field_147058_w;
/*  638: 818 */     boolean var3 = p_147051_1_.isTabInFirstRow();
/*  639: 819 */     int var4 = p_147051_1_.getTabColumn();
/*  640: 820 */     int var5 = var4 * 28;
/*  641: 821 */     int var6 = 0;
/*  642: 822 */     int var7 = this.field_147003_i + 28 * var4;
/*  643: 823 */     int var8 = this.field_147009_r;
/*  644: 824 */     byte var9 = 32;
/*  645: 826 */     if (var2) {
/*  646: 828 */       var6 += 32;
/*  647:     */     }
/*  648: 831 */     if (var4 == 5) {
/*  649: 833 */       var7 = this.field_147003_i + this.field_146999_f - 28;
/*  650: 835 */     } else if (var4 > 0) {
/*  651: 837 */       var7 += var4;
/*  652:     */     }
/*  653: 840 */     if (var3)
/*  654:     */     {
/*  655: 842 */       var8 -= 28;
/*  656:     */     }
/*  657:     */     else
/*  658:     */     {
/*  659: 846 */       var6 += 64;
/*  660: 847 */       var8 += this.field_147000_g - 4;
/*  661:     */     }
/*  662: 850 */     GL11.glDisable(2896);
/*  663: 851 */     drawTexturedModalRect(var7, var8, var5, var6, 28, var9);
/*  664: 852 */     zLevel = 100.0F;
/*  665: 853 */     itemRender.zLevel = 100.0F;
/*  666: 854 */     var7 += 6;
/*  667: 855 */     var8 += 8 + (var3 ? 1 : -1);
/*  668: 856 */     GL11.glEnable(2896);
/*  669: 857 */     GL11.glEnable(32826);
/*  670: 858 */     ItemStack var10 = p_147051_1_.getIconItemStack();
/*  671: 859 */     itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var10, var7, var8);
/*  672: 860 */     itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var10, var7, var8);
/*  673: 861 */     GL11.glDisable(2896);
/*  674: 862 */     itemRender.zLevel = 0.0F;
/*  675: 863 */     zLevel = 0.0F;
/*  676:     */   }
/*  677:     */   
/*  678:     */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  679:     */   {
/*  680: 868 */     if (p_146284_1_.id == 0) {
/*  681: 870 */       this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.func_146107_m()));
/*  682:     */     }
/*  683: 873 */     if (p_146284_1_.id == 1) {
/*  684: 875 */       this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.func_146107_m()));
/*  685:     */     }
/*  686:     */   }
/*  687:     */   
/*  688:     */   public int func_147056_g()
/*  689:     */   {
/*  690: 881 */     return field_147058_w;
/*  691:     */   }
/*  692:     */   
/*  693:     */   class CreativeSlot
/*  694:     */     extends Slot
/*  695:     */   {
/*  696:     */     private final Slot field_148332_b;
/*  697:     */     private static final String __OBFID = "CL_00000754";
/*  698:     */     
/*  699:     */     public CreativeSlot(Slot par2Slot, int par3)
/*  700:     */     {
/*  701: 891 */       super(par3, 0, 0);
/*  702: 892 */       this.field_148332_b = par2Slot;
/*  703:     */     }
/*  704:     */     
/*  705:     */     public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
/*  706:     */     {
/*  707: 897 */       this.field_148332_b.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
/*  708:     */     }
/*  709:     */     
/*  710:     */     public boolean isItemValid(ItemStack par1ItemStack)
/*  711:     */     {
/*  712: 902 */       return this.field_148332_b.isItemValid(par1ItemStack);
/*  713:     */     }
/*  714:     */     
/*  715:     */     public ItemStack getStack()
/*  716:     */     {
/*  717: 907 */       return this.field_148332_b.getStack();
/*  718:     */     }
/*  719:     */     
/*  720:     */     public boolean getHasStack()
/*  721:     */     {
/*  722: 912 */       return this.field_148332_b.getHasStack();
/*  723:     */     }
/*  724:     */     
/*  725:     */     public void putStack(ItemStack par1ItemStack)
/*  726:     */     {
/*  727: 917 */       this.field_148332_b.putStack(par1ItemStack);
/*  728:     */     }
/*  729:     */     
/*  730:     */     public void onSlotChanged()
/*  731:     */     {
/*  732: 922 */       this.field_148332_b.onSlotChanged();
/*  733:     */     }
/*  734:     */     
/*  735:     */     public int getSlotStackLimit()
/*  736:     */     {
/*  737: 927 */       return this.field_148332_b.getSlotStackLimit();
/*  738:     */     }
/*  739:     */     
/*  740:     */     public IIcon getBackgroundIconIndex()
/*  741:     */     {
/*  742: 932 */       return this.field_148332_b.getBackgroundIconIndex();
/*  743:     */     }
/*  744:     */     
/*  745:     */     public ItemStack decrStackSize(int par1)
/*  746:     */     {
/*  747: 937 */       return this.field_148332_b.decrStackSize(par1);
/*  748:     */     }
/*  749:     */     
/*  750:     */     public boolean isSlotInInventory(IInventory par1IInventory, int par2)
/*  751:     */     {
/*  752: 942 */       return this.field_148332_b.isSlotInInventory(par1IInventory, par2);
/*  753:     */     }
/*  754:     */   }
/*  755:     */   
/*  756:     */   static class ContainerCreative
/*  757:     */     extends Container
/*  758:     */   {
/*  759: 948 */     public List field_148330_a = new ArrayList();
/*  760:     */     private static final String __OBFID = "CL_00000753";
/*  761:     */     
/*  762:     */     public ContainerCreative(EntityPlayer par1EntityPlayer)
/*  763:     */     {
/*  764: 953 */       InventoryPlayer var2 = par1EntityPlayer.inventory;
/*  765: 956 */       for (int var3 = 0; var3 < 5; var3++) {
/*  766: 958 */         for (int var4 = 0; var4 < 9; var4++) {
/*  767: 960 */           addSlotToContainer(new Slot(GuiContainerCreative.field_147060_v, var3 * 9 + var4, 9 + var4 * 18, 18 + var3 * 18));
/*  768:     */         }
/*  769:     */       }
/*  770: 964 */       for (var3 = 0; var3 < 9; var3++) {
/*  771: 966 */         addSlotToContainer(new Slot(var2, var3, 9 + var3 * 18, 112));
/*  772:     */       }
/*  773: 969 */       func_148329_a(0.0F);
/*  774:     */     }
/*  775:     */     
/*  776:     */     public boolean canInteractWith(EntityPlayer par1EntityPlayer)
/*  777:     */     {
/*  778: 974 */       return true;
/*  779:     */     }
/*  780:     */     
/*  781:     */     public void func_148329_a(float p_148329_1_)
/*  782:     */     {
/*  783: 979 */       int var2 = this.field_148330_a.size() / 9 - 5 + 1;
/*  784: 980 */       int var3 = (int)(p_148329_1_ * var2 + 0.5D);
/*  785: 982 */       if (var3 < 0) {
/*  786: 984 */         var3 = 0;
/*  787:     */       }
/*  788: 987 */       for (int var4 = 0; var4 < 5; var4++) {
/*  789: 989 */         for (int var5 = 0; var5 < 9; var5++)
/*  790:     */         {
/*  791: 991 */           int var6 = var5 + (var4 + var3) * 9;
/*  792: 993 */           if ((var6 >= 0) && (var6 < this.field_148330_a.size())) {
/*  793: 995 */             GuiContainerCreative.field_147060_v.setInventorySlotContents(var5 + var4 * 9, (ItemStack)this.field_148330_a.get(var6));
/*  794:     */           } else {
/*  795: 999 */             GuiContainerCreative.field_147060_v.setInventorySlotContents(var5 + var4 * 9, null);
/*  796:     */           }
/*  797:     */         }
/*  798:     */       }
/*  799:     */     }
/*  800:     */     
/*  801:     */     public boolean func_148328_e()
/*  802:     */     {
/*  803:1007 */       return this.field_148330_a.size() > 45;
/*  804:     */     }
/*  805:     */     
/*  806:     */     protected void retrySlotClick(int par1, int par2, boolean par3, EntityPlayer par4EntityPlayer) {}
/*  807:     */     
/*  808:     */     public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
/*  809:     */     {
/*  810:1014 */       if ((par2 >= this.inventorySlots.size() - 9) && (par2 < this.inventorySlots.size()))
/*  811:     */       {
/*  812:1016 */         Slot var3 = (Slot)this.inventorySlots.get(par2);
/*  813:1018 */         if ((var3 != null) && (var3.getHasStack())) {
/*  814:1020 */           var3.putStack(null);
/*  815:     */         }
/*  816:     */       }
/*  817:1024 */       return null;
/*  818:     */     }
/*  819:     */     
/*  820:     */     public boolean func_94530_a(ItemStack par1ItemStack, Slot par2Slot)
/*  821:     */     {
/*  822:1029 */       return par2Slot.yDisplayPosition > 90;
/*  823:     */     }
/*  824:     */     
/*  825:     */     public boolean canDragIntoSlot(Slot par1Slot)
/*  826:     */     {
/*  827:1034 */       return ((par1Slot.inventory instanceof InventoryPlayer)) || ((par1Slot.yDisplayPosition > 90) && (par1Slot.xDisplayPosition <= 162));
/*  828:     */     }
/*  829:     */   }
/*  830:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.inventory.GuiContainerCreative
 * JD-Core Version:    0.7.0.1
 */