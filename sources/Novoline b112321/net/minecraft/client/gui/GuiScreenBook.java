package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.gson.JsonParseException;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBook$NextPageButton;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IChatComponent$Serializer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

public class GuiScreenBook extends GuiScreen {
   private static final ResourceLocation bookGuiTextures = new ResourceLocation("textures/gui/book.png");
   private final EntityPlayer editingPlayer;
   private final ItemStack bookObj;
   private final boolean bookIsUnsigned;
   private boolean bookIsModified;
   private boolean bookGettingSigned;
   private int updateCount;
   private final int bookImageWidth = 192;
   private final int bookImageHeight = 192;
   private int bookTotalPages = 1;
   private int currPage;
   private NBTTagList bookPages;
   private String bookTitle = "";
   private List field_175386_A;
   private int field_175387_B = -1;
   private GuiScreenBook$NextPageButton buttonNextPage;
   private GuiScreenBook$NextPageButton buttonPreviousPage;
   private GuiButton buttonDone;
   private GuiButton buttonSign;
   private GuiButton buttonFinalize;
   private GuiButton buttonCancel;

   public GuiScreenBook(EntityPlayer var1, ItemStack var2, boolean var3) {
      this.editingPlayer = var1;
      this.bookObj = var2;
      this.bookIsUnsigned = var3;
      if(var2.hasTagCompound()) {
         NBTTagCompound var4 = var2.getTagCompound();
         this.bookPages = var4.getTagList("pages", 8);
         if(this.bookPages != null) {
            this.bookPages = (NBTTagList)this.bookPages.copy();
            this.bookTotalPages = this.bookPages.tagCount();
            if(this.bookTotalPages < 1) {
               this.bookTotalPages = 1;
            }
         }
      }

      if(this.bookPages == null) {
         this.bookPages = new NBTTagList();
         this.bookPages.appendTag(new NBTTagString(""));
         this.bookTotalPages = 1;
      }

   }

   public void updateScreen() {
      super.updateScreen();
      ++this.updateCount;
   }

   public void initGui() {
      // $FF: Couldn't be decompiled
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
   }

   private void updateButtons() {
      this.buttonNextPage.visible = !this.bookGettingSigned && (this.currPage < this.bookTotalPages - 1 || this.bookIsUnsigned);
      this.buttonPreviousPage.visible = !this.bookGettingSigned && this.currPage > 0;
      this.buttonDone.visible = !this.bookIsUnsigned || !this.bookGettingSigned;
      if(this.bookIsUnsigned) {
         this.buttonSign.visible = !this.bookGettingSigned;
         this.buttonCancel.visible = this.bookGettingSigned;
         this.buttonFinalize.visible = this.bookGettingSigned;
         this.buttonFinalize.enabled = !this.bookTitle.trim().isEmpty();
      }

   }

   private void sendBookToServer(boolean var1) throws IOException {
      if(this.bookIsUnsigned && this.bookIsModified && this.bookPages != null) {
         while(this.bookPages.tagCount() > 1) {
            String var2 = this.bookPages.getStringTagAt(this.bookPages.tagCount() - 1);
            if(!var2.isEmpty()) {
               break;
            }

            this.bookPages.removeTag(this.bookPages.tagCount() - 1);
         }

         if(this.bookObj.hasTagCompound()) {
            NBTTagCompound var6 = this.bookObj.getTagCompound();
            var6.setTag("pages", this.bookPages);
         } else {
            this.bookObj.setTagInfo("pages", this.bookPages);
         }

         String var7 = "MC|BEdit";
         var7 = "MC|BSign";
         this.bookObj.setTagInfo("author", new NBTTagString(this.editingPlayer.getName()));
         this.bookObj.setTagInfo("title", new NBTTagString(this.bookTitle.trim()));

         for(int var3 = 0; var3 < this.bookPages.tagCount(); ++var3) {
            String var4 = this.bookPages.getStringTagAt(var3);
            ChatComponentText var5 = new ChatComponentText(var4);
            var4 = IChatComponent$Serializer.componentToJson(var5);
            this.bookPages.set(var3, new NBTTagString(var4));
         }

         this.bookObj.setItem(Items.written_book);
         PacketBuffer var9 = new PacketBuffer(Unpooled.buffer());
         var9.a(this.bookObj);
         this.mc.getNetHandler().b(new C17PacketCustomPayload(var7, var9));
      }

   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if(var1.enabled) {
         if(var1.id == 0) {
            this.mc.displayGuiScreen((GuiScreen)null);
            this.sendBookToServer(false);
         } else if(var1.id == 3 && this.bookIsUnsigned) {
            this.bookGettingSigned = true;
         } else if(var1.id == 1) {
            if(this.currPage < this.bookTotalPages - 1) {
               ++this.currPage;
            } else if(this.bookIsUnsigned) {
               this.addNewPage();
               if(this.currPage < this.bookTotalPages - 1) {
                  ++this.currPage;
               }
            }
         } else if(var1.id == 2) {
            if(this.currPage > 0) {
               --this.currPage;
            }
         } else if(var1.id == 5 && this.bookGettingSigned) {
            this.sendBookToServer(true);
            this.mc.displayGuiScreen((GuiScreen)null);
         } else if(var1.id == 4 && this.bookGettingSigned) {
            this.bookGettingSigned = false;
         }

         this.updateButtons();
      }

   }

   private void addNewPage() {
      if(this.bookPages != null && this.bookPages.tagCount() < 50) {
         this.bookPages.appendTag(new NBTTagString(""));
         ++this.bookTotalPages;
         this.bookIsModified = true;
      }

   }

   protected void keyTyped(char var1, int var2) throws IOException {
      super.keyTyped(var1, var2);
      if(this.bookIsUnsigned) {
         if(this.bookGettingSigned) {
            this.keyTypedInTitle(var1, var2);
         } else {
            this.keyTypedInBook(var1, var2);
         }
      }

   }

   private void keyTypedInBook(char var1, int var2) {
      if(GuiScreen.isKeyComboCtrlV(var2)) {
         this.pageInsertIntoCurrent(GuiScreen.getClipboardString());
      } else {
         switch(var2) {
         case 14:
            String var3 = this.pageGetCurrent();
            if(!var3.isEmpty()) {
               this.pageSetCurrent(var3.substring(0, var3.length() - 1));
            }

            return;
         case 28:
         case 156:
            this.pageInsertIntoCurrent("\n");
            return;
         default:
            if(ChatAllowedCharacters.isAllowedCharacter(var1)) {
               this.pageInsertIntoCurrent(Character.toString(var1));
            }
         }
      }

   }

   private void keyTypedInTitle(char var1, int var2) throws IOException {
      switch(var2) {
      case 14:
         if(!this.bookTitle.isEmpty()) {
            this.bookTitle = this.bookTitle.substring(0, this.bookTitle.length() - 1);
            this.updateButtons();
         }

         return;
      case 28:
      case 156:
         if(!this.bookTitle.isEmpty()) {
            this.sendBookToServer(true);
            this.mc.displayGuiScreen((GuiScreen)null);
         }

         return;
      default:
         if(this.bookTitle.length() < 16 && ChatAllowedCharacters.isAllowedCharacter(var1)) {
            this.bookTitle = this.bookTitle + var1;
            this.updateButtons();
            this.bookIsModified = true;
         }

      }
   }

   private String pageGetCurrent() {
      return this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount()?this.bookPages.getStringTagAt(this.currPage):"";
   }

   private void pageSetCurrent(String var1) {
      if(this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount()) {
         this.bookPages.set(this.currPage, new NBTTagString(var1));
         this.bookIsModified = true;
      }

   }

   private void pageInsertIntoCurrent(String var1) {
      String var2 = this.pageGetCurrent();
      String var3 = var2 + var1;
      int var4 = this.fontRendererObj.splitStringWidth(var3 + "" + EnumChatFormatting.BLACK + "_", 118);
      if(var4 <= 128 && var3.length() < 256) {
         this.pageSetCurrent(var3);
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(bookGuiTextures);
      int var10000 = this.width;
      this.getClass();
      int var4 = (var10000 - 192) / 2;
      boolean var5 = true;
      this.getClass();
      this.getClass();
      this.drawTexturedModalRect(var4, 2, 0, 0, 192, 192);
      if(this.bookGettingSigned) {
         String var6 = this.bookTitle;
         if(this.bookIsUnsigned) {
            if(this.updateCount / 6 % 2 == 0) {
               var6 = var6 + "" + EnumChatFormatting.BLACK + "_";
            } else {
               var6 = var6 + "" + EnumChatFormatting.GRAY + "_";
            }
         }

         String var7 = I18n.format("book.editTitle", new Object[0]);
         int var8 = this.fontRendererObj.d(var7);
         this.fontRendererObj.drawString(var7, (float)(var4 + 36 + (116 - var8) / 2), 34.0F, 0);
         int var9 = this.fontRendererObj.d(var6);
         this.fontRendererObj.drawString(var6, (float)(var4 + 36 + (116 - var9) / 2), 50.0F, 0);
         String var10 = I18n.format("book.byAuthor", new Object[]{this.editingPlayer.getName()});
         int var11 = this.fontRendererObj.d(var10);
         this.fontRendererObj.drawString(EnumChatFormatting.DARK_GRAY + var10, (float)(var4 + 36 + (116 - var11) / 2), 60.0F, 0);
         String var12 = I18n.format("book.finalizeWarning", new Object[0]);
         this.fontRendererObj.drawSplitString(var12, var4 + 36, 82, 116, 0);
      } else {
         String var14 = I18n.format("book.pageIndicator", new Object[]{Integer.valueOf(this.currPage + 1), Integer.valueOf(this.bookTotalPages)});
         String var15 = "";
         if(this.bookPages != null && this.currPage >= 0 && this.currPage < this.bookPages.tagCount()) {
            var15 = this.bookPages.getStringTagAt(this.currPage);
         }

         if(this.bookIsUnsigned) {
            if(this.fontRendererObj.getBidiFlag()) {
               var15 = var15 + "_";
            } else if(this.updateCount / 6 % 2 == 0) {
               var15 = var15 + "" + EnumChatFormatting.BLACK + "_";
            } else {
               var15 = var15 + "" + EnumChatFormatting.GRAY + "_";
            }
         } else if(this.field_175387_B != this.currPage) {
            if(ItemEditableBook.validBookTagContents(this.bookObj.getTagCompound())) {
               try {
                  IChatComponent var16 = IChatComponent$Serializer.jsonToComponent(var15);
                  this.field_175386_A = GuiUtilRenderComponents.func_178908_a(var16, 116, (FontRenderer)this.fontRendererObj, true, true);
               } catch (JsonParseException var13) {
                  this.field_175386_A = null;
               }
            } else {
               ChatComponentText var17 = new ChatComponentText(EnumChatFormatting.DARK_RED.toString() + "* Invalid book tag *");
               this.field_175386_A = Lists.newArrayList(var17);
            }

            this.field_175387_B = this.currPage;
         }

         int var18 = this.fontRendererObj.d(var14);
         FontRenderer var23 = this.fontRendererObj;
         int var10002 = var4 - var18;
         this.getClass();
         var23.drawString(var14, (float)(var10002 + 192 - 44), 18.0F, 0);
         if(this.field_175386_A == null) {
            this.fontRendererObj.drawSplitString(var15, var4 + 36, 34, 116, 0);
         } else {
            int var19 = Math.min(128 / this.fontRendererObj.getHeight(), this.field_175386_A.size());

            for(int var20 = 0; var20 < var19; ++var20) {
               IChatComponent var22 = (IChatComponent)this.field_175386_A.get(var20);
               this.fontRendererObj.drawString(var22.getUnformattedText(), (float)(var4 + 36), (float)(34 + var20 * this.fontRendererObj.getHeight()), 0);
            }

            IChatComponent var21 = this.func_175385_b(var1, var2);
            this.handleComponentHover(var21, var1, var2);
         }
      }

      super.drawScreen(var1, var2, var3);
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      IChatComponent var4 = this.func_175385_b(var1, var2);
      if(!this.a(var4)) {
         super.mouseClicked(var1, var2, var3);
      }
   }

   protected boolean a(IChatComponent var1) {
      Object var2 = null;
      return false;
   }

   public IChatComponent func_175385_b(int var1, int var2) {
      if(this.field_175386_A == null) {
         return null;
      } else {
         int var10001 = this.width;
         this.getClass();
         int var3 = var1 - (var10001 - 192) / 2 - 36;
         int var4 = var2 - 2 - 16 - 16;
         int var5 = Math.min(128 / this.fontRendererObj.getHeight(), this.field_175386_A.size());
         if(var3 <= 116 && var4 < this.mc.fontRendererObj.getHeight() * var5 + var5) {
            int var6 = var4 / this.mc.fontRendererObj.getHeight();
            if(var6 < this.field_175386_A.size()) {
               IChatComponent var7 = (IChatComponent)this.field_175386_A.get(var6);
               int var8 = 0;

               for(IChatComponent var10 : var7) {
                  if(var10 instanceof ChatComponentText) {
                     var8 += this.mc.fontRendererObj.d(((ChatComponentText)var10).getChatComponentText_TextValue());
                     if(var8 > var3) {
                        return var10;
                     }
                  }
               }
            }
         }

         return null;
      }
   }

   static ResourceLocation access$000() {
      return bookGuiTextures;
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
