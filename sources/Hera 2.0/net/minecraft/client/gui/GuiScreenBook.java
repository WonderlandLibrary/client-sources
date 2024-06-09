/*     */ package net.minecraft.client.gui;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.gson.JsonParseException;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.event.ClickEvent;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemEditableBook;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTTagString;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*     */ import net.minecraft.util.ChatAllowedCharacters;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiScreenBook extends GuiScreen {
/*  32 */   private static final Logger logger = LogManager.getLogger();
/*  33 */   private static final ResourceLocation bookGuiTextures = new ResourceLocation("textures/gui/book.png");
/*     */ 
/*     */   
/*     */   private final EntityPlayer editingPlayer;
/*     */ 
/*     */   
/*     */   private final ItemStack bookObj;
/*     */ 
/*     */   
/*     */   private final boolean bookIsUnsigned;
/*     */ 
/*     */   
/*     */   private boolean bookIsModified;
/*     */ 
/*     */   
/*     */   private boolean bookGettingSigned;
/*     */   
/*     */   private int updateCount;
/*     */   
/*  52 */   private int bookImageWidth = 192;
/*  53 */   private int bookImageHeight = 192;
/*  54 */   private int bookTotalPages = 1;
/*     */   private int currPage;
/*     */   private NBTTagList bookPages;
/*  57 */   private String bookTitle = "";
/*     */   private List<IChatComponent> field_175386_A;
/*  59 */   private int field_175387_B = -1;
/*     */   
/*     */   private NextPageButton buttonNextPage;
/*     */   
/*     */   private NextPageButton buttonPreviousPage;
/*     */   
/*     */   private GuiButton buttonDone;
/*     */   private GuiButton buttonSign;
/*     */   private GuiButton buttonFinalize;
/*     */   private GuiButton buttonCancel;
/*     */   
/*     */   public GuiScreenBook(EntityPlayer player, ItemStack book, boolean isUnsigned) {
/*  71 */     this.editingPlayer = player;
/*  72 */     this.bookObj = book;
/*  73 */     this.bookIsUnsigned = isUnsigned;
/*     */     
/*  75 */     if (book.hasTagCompound()) {
/*     */       
/*  77 */       NBTTagCompound nbttagcompound = book.getTagCompound();
/*  78 */       this.bookPages = nbttagcompound.getTagList("pages", 8);
/*     */       
/*  80 */       if (this.bookPages != null) {
/*     */         
/*  82 */         this.bookPages = (NBTTagList)this.bookPages.copy();
/*  83 */         this.bookTotalPages = this.bookPages.tagCount();
/*     */         
/*  85 */         if (this.bookTotalPages < 1)
/*     */         {
/*  87 */           this.bookTotalPages = 1;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  92 */     if (this.bookPages == null && isUnsigned) {
/*     */       
/*  94 */       this.bookPages = new NBTTagList();
/*  95 */       this.bookPages.appendTag((NBTBase)new NBTTagString(""));
/*  96 */       this.bookTotalPages = 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 105 */     super.updateScreen();
/* 106 */     this.updateCount++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/* 115 */     this.buttonList.clear();
/* 116 */     Keyboard.enableRepeatEvents(true);
/*     */     
/* 118 */     if (this.bookIsUnsigned) {
/*     */       
/* 120 */       this.buttonList.add(this.buttonSign = new GuiButton(3, this.width / 2 - 100, 4 + this.bookImageHeight, 98, 20, I18n.format("book.signButton", new Object[0])));
/* 121 */       this.buttonList.add(this.buttonDone = new GuiButton(0, this.width / 2 + 2, 4 + this.bookImageHeight, 98, 20, I18n.format("gui.done", new Object[0])));
/* 122 */       this.buttonList.add(this.buttonFinalize = new GuiButton(5, this.width / 2 - 100, 4 + this.bookImageHeight, 98, 20, I18n.format("book.finalizeButton", new Object[0])));
/* 123 */       this.buttonList.add(this.buttonCancel = new GuiButton(4, this.width / 2 + 2, 4 + this.bookImageHeight, 98, 20, I18n.format("gui.cancel", new Object[0])));
/*     */     }
/*     */     else {
/*     */       
/* 127 */       this.buttonList.add(this.buttonDone = new GuiButton(0, this.width / 2 - 100, 4 + this.bookImageHeight, 200, 20, I18n.format("gui.done", new Object[0])));
/*     */     } 
/*     */     
/* 130 */     int i = (this.width - this.bookImageWidth) / 2;
/* 131 */     int j = 2;
/* 132 */     this.buttonList.add(this.buttonNextPage = new NextPageButton(1, i + 120, j + 154, true));
/* 133 */     this.buttonList.add(this.buttonPreviousPage = new NextPageButton(2, i + 38, j + 154, false));
/* 134 */     updateButtons();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 142 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateButtons() {
/* 147 */     this.buttonNextPage.visible = (!this.bookGettingSigned && (this.currPage < this.bookTotalPages - 1 || this.bookIsUnsigned));
/* 148 */     this.buttonPreviousPage.visible = (!this.bookGettingSigned && this.currPage > 0);
/* 149 */     this.buttonDone.visible = !(this.bookIsUnsigned && this.bookGettingSigned);
/*     */     
/* 151 */     if (this.bookIsUnsigned) {
/*     */       
/* 153 */       this.buttonSign.visible = !this.bookGettingSigned;
/* 154 */       this.buttonCancel.visible = this.bookGettingSigned;
/* 155 */       this.buttonFinalize.visible = this.bookGettingSigned;
/* 156 */       this.buttonFinalize.enabled = (this.bookTitle.trim().length() > 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void sendBookToServer(boolean publish) throws IOException {
/* 162 */     if (this.bookIsUnsigned && this.bookIsModified)
/*     */     {
/* 164 */       if (this.bookPages != null) {
/*     */         
/* 166 */         while (this.bookPages.tagCount() > 1) {
/*     */           
/* 168 */           String s = this.bookPages.getStringTagAt(this.bookPages.tagCount() - 1);
/*     */           
/* 170 */           if (s.length() != 0) {
/*     */             break;
/*     */           }
/*     */ 
/*     */           
/* 175 */           this.bookPages.removeTag(this.bookPages.tagCount() - 1);
/*     */         } 
/*     */         
/* 178 */         if (this.bookObj.hasTagCompound()) {
/*     */           
/* 180 */           NBTTagCompound nbttagcompound = this.bookObj.getTagCompound();
/* 181 */           nbttagcompound.setTag("pages", (NBTBase)this.bookPages);
/*     */         }
/*     */         else {
/*     */           
/* 185 */           this.bookObj.setTagInfo("pages", (NBTBase)this.bookPages);
/*     */         } 
/*     */         
/* 188 */         String s2 = "MC|BEdit";
/*     */         
/* 190 */         if (publish) {
/*     */           
/* 192 */           s2 = "MC|BSign";
/* 193 */           this.bookObj.setTagInfo("author", (NBTBase)new NBTTagString(this.editingPlayer.getName()));
/* 194 */           this.bookObj.setTagInfo("title", (NBTBase)new NBTTagString(this.bookTitle.trim()));
/*     */           
/* 196 */           for (int i = 0; i < this.bookPages.tagCount(); i++) {
/*     */             
/* 198 */             String s1 = this.bookPages.getStringTagAt(i);
/* 199 */             ChatComponentText chatComponentText = new ChatComponentText(s1);
/* 200 */             s1 = IChatComponent.Serializer.componentToJson((IChatComponent)chatComponentText);
/* 201 */             this.bookPages.set(i, (NBTBase)new NBTTagString(s1));
/*     */           } 
/*     */           
/* 204 */           this.bookObj.setItem(Items.written_book);
/*     */         } 
/*     */         
/* 207 */         PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
/* 208 */         packetbuffer.writeItemStackToBuffer(this.bookObj);
/* 209 */         this.mc.getNetHandler().addToSendQueue((Packet)new C17PacketCustomPayload(s2, packetbuffer));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 219 */     if (button.enabled) {
/*     */       
/* 221 */       if (button.id == 0) {
/*     */         
/* 223 */         this.mc.displayGuiScreen(null);
/* 224 */         sendBookToServer(false);
/*     */       }
/* 226 */       else if (button.id == 3 && this.bookIsUnsigned) {
/*     */         
/* 228 */         this.bookGettingSigned = true;
/*     */       }
/* 230 */       else if (button.id == 1) {
/*     */         
/* 232 */         if (this.currPage < this.bookTotalPages - 1)
/*     */         {
/* 234 */           this.currPage++;
/*     */         }
/* 236 */         else if (this.bookIsUnsigned)
/*     */         {
/* 238 */           addNewPage();
/*     */           
/* 240 */           if (this.currPage < this.bookTotalPages - 1)
/*     */           {
/* 242 */             this.currPage++;
/*     */           }
/*     */         }
/*     */       
/* 246 */       } else if (button.id == 2) {
/*     */         
/* 248 */         if (this.currPage > 0)
/*     */         {
/* 250 */           this.currPage--;
/*     */         }
/*     */       }
/* 253 */       else if (button.id == 5 && this.bookGettingSigned) {
/*     */         
/* 255 */         sendBookToServer(true);
/* 256 */         this.mc.displayGuiScreen(null);
/*     */       }
/* 258 */       else if (button.id == 4 && this.bookGettingSigned) {
/*     */         
/* 260 */         this.bookGettingSigned = false;
/*     */       } 
/*     */       
/* 263 */       updateButtons();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void addNewPage() {
/* 269 */     if (this.bookPages != null && this.bookPages.tagCount() < 50) {
/*     */       
/* 271 */       this.bookPages.appendTag((NBTBase)new NBTTagString(""));
/* 272 */       this.bookTotalPages++;
/* 273 */       this.bookIsModified = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 283 */     super.keyTyped(typedChar, keyCode);
/*     */     
/* 285 */     if (this.bookIsUnsigned)
/*     */     {
/* 287 */       if (this.bookGettingSigned) {
/*     */         
/* 289 */         keyTypedInTitle(typedChar, keyCode);
/*     */       }
/*     */       else {
/*     */         
/* 293 */         keyTypedInBook(typedChar, keyCode);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void keyTypedInBook(char typedChar, int keyCode) {
/* 303 */     if (GuiScreen.isKeyComboCtrlV(keyCode)) {
/*     */       
/* 305 */       pageInsertIntoCurrent(GuiScreen.getClipboardString());
/*     */     } else {
/*     */       String s;
/*     */       
/* 309 */       switch (keyCode) {
/*     */         
/*     */         case 14:
/* 312 */           s = pageGetCurrent();
/*     */           
/* 314 */           if (s.length() > 0)
/*     */           {
/* 316 */             pageSetCurrent(s.substring(0, s.length() - 1));
/*     */           }
/*     */           return;
/*     */ 
/*     */         
/*     */         case 28:
/*     */         case 156:
/* 323 */           pageInsertIntoCurrent("\n");
/*     */           return;
/*     */       } 
/*     */       
/* 327 */       if (ChatAllowedCharacters.isAllowedCharacter(typedChar))
/*     */       {
/* 329 */         pageInsertIntoCurrent(Character.toString(typedChar));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void keyTypedInTitle(char p_146460_1_, int p_146460_2_) throws IOException {
/* 340 */     switch (p_146460_2_) {
/*     */       
/*     */       case 14:
/* 343 */         if (!this.bookTitle.isEmpty()) {
/*     */           
/* 345 */           this.bookTitle = this.bookTitle.substring(0, this.bookTitle.length() - 1);
/* 346 */           updateButtons();
/*     */         } 
/*     */         return;
/*     */ 
/*     */       
/*     */       case 28:
/*     */       case 156:
/* 353 */         if (!this.bookTitle.isEmpty()) {
/*     */           
/* 355 */           sendBookToServer(true);
/* 356 */           this.mc.displayGuiScreen(null);
/*     */         } 
/*     */         return;
/*     */     } 
/*     */ 
/*     */     
/* 362 */     if (this.bookTitle.length() < 16 && ChatAllowedCharacters.isAllowedCharacter(p_146460_1_)) {
/*     */       
/* 364 */       this.bookTitle = String.valueOf(this.bookTitle) + Character.toString(p_146460_1_);
/* 365 */       updateButtons();
/* 366 */       this.bookIsModified = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String pageGetCurrent() {
/* 376 */     return (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount()) ? this.bookPages.getStringTagAt(this.currPage) : "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void pageSetCurrent(String p_146457_1_) {
/* 384 */     if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount()) {
/*     */       
/* 386 */       this.bookPages.set(this.currPage, (NBTBase)new NBTTagString(p_146457_1_));
/* 387 */       this.bookIsModified = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void pageInsertIntoCurrent(String p_146459_1_) {
/* 396 */     String s = pageGetCurrent();
/* 397 */     String s1 = String.valueOf(s) + p_146459_1_;
/* 398 */     int i = this.fontRendererObj.splitStringWidth(String.valueOf(s1) + EnumChatFormatting.BLACK + "_", 118);
/*     */     
/* 400 */     if (i <= 128 && s1.length() < 256)
/*     */     {
/* 402 */       pageSetCurrent(s1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 411 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 412 */     this.mc.getTextureManager().bindTexture(bookGuiTextures);
/* 413 */     int i = (this.width - this.bookImageWidth) / 2;
/* 414 */     int j = 2;
/* 415 */     drawTexturedModalRect(i, j, 0, 0, this.bookImageWidth, this.bookImageHeight);
/*     */     
/* 417 */     if (this.bookGettingSigned) {
/*     */       
/* 419 */       String s = this.bookTitle;
/*     */       
/* 421 */       if (this.bookIsUnsigned)
/*     */       {
/* 423 */         if (this.updateCount / 6 % 2 == 0) {
/*     */           
/* 425 */           s = String.valueOf(s) + EnumChatFormatting.BLACK + "_";
/*     */         }
/*     */         else {
/*     */           
/* 429 */           s = String.valueOf(s) + EnumChatFormatting.GRAY + "_";
/*     */         } 
/*     */       }
/*     */       
/* 433 */       String s1 = I18n.format("book.editTitle", new Object[0]);
/* 434 */       int k = this.fontRendererObj.getStringWidth(s1);
/* 435 */       this.fontRendererObj.drawString(s1, i + 36 + (116 - k) / 2, j + 16 + 16, 0);
/* 436 */       int l = this.fontRendererObj.getStringWidth(s);
/* 437 */       this.fontRendererObj.drawString(s, i + 36 + (116 - l) / 2, j + 48, 0);
/* 438 */       String s2 = I18n.format("book.byAuthor", new Object[] { this.editingPlayer.getName() });
/* 439 */       int i1 = this.fontRendererObj.getStringWidth(s2);
/* 440 */       this.fontRendererObj.drawString(EnumChatFormatting.DARK_GRAY + s2, i + 36 + (116 - i1) / 2, j + 48 + 10, 0);
/* 441 */       String s3 = I18n.format("book.finalizeWarning", new Object[0]);
/* 442 */       this.fontRendererObj.drawSplitString(s3, i + 36, j + 80, 116, 0);
/*     */     }
/*     */     else {
/*     */       
/* 446 */       String s4 = I18n.format("book.pageIndicator", new Object[] { Integer.valueOf(this.currPage + 1), Integer.valueOf(this.bookTotalPages) });
/* 447 */       String s5 = "";
/*     */       
/* 449 */       if (this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount())
/*     */       {
/* 451 */         s5 = this.bookPages.getStringTagAt(this.currPage);
/*     */       }
/*     */       
/* 454 */       if (this.bookIsUnsigned) {
/*     */         
/* 456 */         if (this.fontRendererObj.getBidiFlag())
/*     */         {
/* 458 */           s5 = String.valueOf(s5) + "_";
/*     */         }
/* 460 */         else if (this.updateCount / 6 % 2 == 0)
/*     */         {
/* 462 */           s5 = String.valueOf(s5) + EnumChatFormatting.BLACK + "_";
/*     */         }
/*     */         else
/*     */         {
/* 466 */           s5 = String.valueOf(s5) + EnumChatFormatting.GRAY + "_";
/*     */         }
/*     */       
/* 469 */       } else if (this.field_175387_B != this.currPage) {
/*     */         
/* 471 */         if (ItemEditableBook.validBookTagContents(this.bookObj.getTagCompound())) {
/*     */           
/*     */           try
/*     */           {
/* 475 */             IChatComponent ichatcomponent = IChatComponent.Serializer.jsonToComponent(s5);
/* 476 */             this.field_175386_A = (ichatcomponent != null) ? GuiUtilRenderComponents.func_178908_a(ichatcomponent, 116, this.fontRendererObj, true, true) : null;
/*     */           }
/* 478 */           catch (JsonParseException var13)
/*     */           {
/* 480 */             this.field_175386_A = null;
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 485 */           ChatComponentText chatcomponenttext = new ChatComponentText(String.valueOf(EnumChatFormatting.DARK_RED.toString()) + "* Invalid book tag *");
/* 486 */           this.field_175386_A = Lists.newArrayList((Iterable)chatcomponenttext);
/*     */         } 
/*     */         
/* 489 */         this.field_175387_B = this.currPage;
/*     */       } 
/*     */       
/* 492 */       int j1 = this.fontRendererObj.getStringWidth(s4);
/* 493 */       this.fontRendererObj.drawString(s4, i - j1 + this.bookImageWidth - 44, j + 16, 0);
/*     */       
/* 495 */       if (this.field_175386_A == null) {
/*     */         
/* 497 */         this.fontRendererObj.drawSplitString(s5, i + 36, j + 16 + 16, 116, 0);
/*     */       }
/*     */       else {
/*     */         
/* 501 */         int k1 = Math.min(128 / this.fontRendererObj.FONT_HEIGHT, this.field_175386_A.size());
/*     */         
/* 503 */         for (int l1 = 0; l1 < k1; l1++) {
/*     */           
/* 505 */           IChatComponent ichatcomponent2 = this.field_175386_A.get(l1);
/* 506 */           this.fontRendererObj.drawString(ichatcomponent2.getUnformattedText(), i + 36, j + 16 + 16 + l1 * this.fontRendererObj.FONT_HEIGHT, 0);
/*     */         } 
/*     */         
/* 509 */         IChatComponent ichatcomponent1 = func_175385_b(mouseX, mouseY);
/*     */         
/* 511 */         if (ichatcomponent1 != null)
/*     */         {
/* 513 */           handleComponentHover(ichatcomponent1, mouseX, mouseY);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 518 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 526 */     if (mouseButton == 0) {
/*     */       
/* 528 */       IChatComponent ichatcomponent = func_175385_b(mouseX, mouseY);
/*     */       
/* 530 */       if (handleComponentClick(ichatcomponent)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 536 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleComponentClick(IChatComponent p_175276_1_) {
/* 544 */     ClickEvent clickevent = (p_175276_1_ == null) ? null : p_175276_1_.getChatStyle().getChatClickEvent();
/*     */     
/* 546 */     if (clickevent == null)
/*     */     {
/* 548 */       return false;
/*     */     }
/* 550 */     if (clickevent.getAction() == ClickEvent.Action.CHANGE_PAGE) {
/*     */       
/* 552 */       String s = clickevent.getValue();
/*     */ 
/*     */       
/*     */       try {
/* 556 */         int i = Integer.parseInt(s) - 1;
/*     */         
/* 558 */         if (i >= 0 && i < this.bookTotalPages && i != this.currPage)
/*     */         {
/* 560 */           this.currPage = i;
/* 561 */           updateButtons();
/* 562 */           return true;
/*     */         }
/*     */       
/* 565 */       } catch (Throwable throwable) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 570 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 574 */     boolean flag = super.handleComponentClick(p_175276_1_);
/*     */     
/* 576 */     if (flag && clickevent.getAction() == ClickEvent.Action.RUN_COMMAND)
/*     */     {
/* 578 */       this.mc.displayGuiScreen(null);
/*     */     }
/*     */     
/* 581 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IChatComponent func_175385_b(int p_175385_1_, int p_175385_2_) {
/* 587 */     if (this.field_175386_A == null)
/*     */     {
/* 589 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 593 */     int i = p_175385_1_ - (this.width - this.bookImageWidth) / 2 - 36;
/* 594 */     int j = p_175385_2_ - 2 - 16 - 16;
/*     */     
/* 596 */     if (i >= 0 && j >= 0) {
/*     */       
/* 598 */       int k = Math.min(128 / this.fontRendererObj.FONT_HEIGHT, this.field_175386_A.size());
/*     */       
/* 600 */       if (i <= 116 && j < this.mc.fontRendererObj.FONT_HEIGHT * k + k) {
/*     */         
/* 602 */         int l = j / this.mc.fontRendererObj.FONT_HEIGHT;
/*     */         
/* 604 */         if (l >= 0 && l < this.field_175386_A.size()) {
/*     */           
/* 606 */           IChatComponent ichatcomponent = this.field_175386_A.get(l);
/* 607 */           int i1 = 0;
/*     */           
/* 609 */           for (IChatComponent ichatcomponent1 : ichatcomponent) {
/*     */             
/* 611 */             if (ichatcomponent1 instanceof ChatComponentText) {
/*     */               
/* 613 */               i1 += this.mc.fontRendererObj.getStringWidth(((ChatComponentText)ichatcomponent1).getChatComponentText_TextValue());
/*     */               
/* 615 */               if (i1 > i)
/*     */               {
/* 617 */                 return ichatcomponent1;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 623 */         return null;
/*     */       } 
/*     */ 
/*     */       
/* 627 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 632 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   static class NextPageButton
/*     */     extends GuiButton
/*     */   {
/*     */     private final boolean field_146151_o;
/*     */ 
/*     */     
/*     */     public NextPageButton(int p_i46316_1_, int p_i46316_2_, int p_i46316_3_, boolean p_i46316_4_) {
/* 643 */       super(p_i46316_1_, p_i46316_2_, p_i46316_3_, 23, 13, "");
/* 644 */       this.field_146151_o = p_i46316_4_;
/*     */     }
/*     */ 
/*     */     
/*     */     public void drawButton(Minecraft mc, int mouseX, int mouseY) {
/* 649 */       if (this.visible) {
/*     */         
/* 651 */         boolean flag = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
/* 652 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 653 */         mc.getTextureManager().bindTexture(GuiScreenBook.bookGuiTextures);
/* 654 */         int i = 0;
/* 655 */         int j = 192;
/*     */         
/* 657 */         if (flag)
/*     */         {
/* 659 */           i += 23;
/*     */         }
/*     */         
/* 662 */         if (!this.field_146151_o)
/*     */         {
/* 664 */           j += 13;
/*     */         }
/*     */         
/* 667 */         drawTexturedModalRect(this.xPosition, this.yPosition, i, j, 23, 13);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiScreenBook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */