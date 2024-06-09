package net.minecraft.client.gui;

import com.google.gson.JsonParseException;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IChatComponent.Serializer;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.input.Keyboard;

public class GuiScreenBook extends GuiScreen
{
  private static final org.apache.logging.log4j.Logger logger = ;
  private static final ResourceLocation bookGuiTextures = new ResourceLocation("textures/gui/book.png");
  

  private final EntityPlayer editingPlayer;
  

  private final ItemStack bookObj;
  

  private final boolean bookIsUnsigned;
  

  private boolean bookIsModified;
  

  private boolean bookGettingSigned;
  
  private int updateCount;
  
  private int bookImageWidth = 192;
  private int bookImageHeight = 192;
  private int bookTotalPages = 1;
  private int currPage;
  private NBTTagList bookPages;
  private String bookTitle = "";
  private List field_175386_A;
  private int field_175387_B = -1;
  
  private NextPageButton buttonNextPage;
  
  private NextPageButton buttonPreviousPage;
  private GuiButton buttonDone;
  private GuiButton buttonSign;
  private GuiButton buttonFinalize;
  private GuiButton buttonCancel;
  private static final String __OBFID = "CL_00000744";
  
  public GuiScreenBook(EntityPlayer p_i1080_1_, ItemStack p_i1080_2_, boolean p_i1080_3_)
  {
    editingPlayer = p_i1080_1_;
    bookObj = p_i1080_2_;
    bookIsUnsigned = p_i1080_3_;
    
    if (p_i1080_2_.hasTagCompound())
    {
      NBTTagCompound var4 = p_i1080_2_.getTagCompound();
      bookPages = var4.getTagList("pages", 8);
      
      if (bookPages != null)
      {
        bookPages = ((NBTTagList)bookPages.copy());
        bookTotalPages = bookPages.tagCount();
        
        if (bookTotalPages < 1)
        {
          bookTotalPages = 1;
        }
      }
    }
    
    if ((bookPages == null) && (p_i1080_3_))
    {
      bookPages = new NBTTagList();
      bookPages.appendTag(new NBTTagString(""));
      bookTotalPages = 1;
    }
  }
  



  public void updateScreen()
  {
    super.updateScreen();
    updateCount += 1;
  }
  



  public void initGui()
  {
    buttonList.clear();
    Keyboard.enableRepeatEvents(true);
    
    if (bookIsUnsigned)
    {
      buttonList.add(this.buttonSign = new GuiButton(3, width / 2 - 100, 4 + bookImageHeight, 98, 20, I18n.format("book.signButton", new Object[0])));
      buttonList.add(this.buttonDone = new GuiButton(0, width / 2 + 2, 4 + bookImageHeight, 98, 20, I18n.format("gui.done", new Object[0])));
      buttonList.add(this.buttonFinalize = new GuiButton(5, width / 2 - 100, 4 + bookImageHeight, 98, 20, I18n.format("book.finalizeButton", new Object[0])));
      buttonList.add(this.buttonCancel = new GuiButton(4, width / 2 + 2, 4 + bookImageHeight, 98, 20, I18n.format("gui.cancel", new Object[0])));
    }
    else
    {
      buttonList.add(this.buttonDone = new GuiButton(0, width / 2 - 100, 4 + bookImageHeight, 200, 20, I18n.format("gui.done", new Object[0])));
    }
    
    int var1 = (width - bookImageWidth) / 2;
    byte var2 = 2;
    buttonList.add(this.buttonNextPage = new NextPageButton(1, var1 + 120, var2 + 154, true));
    buttonList.add(this.buttonPreviousPage = new NextPageButton(2, var1 + 38, var2 + 154, false));
    updateButtons();
  }
  



  public void onGuiClosed()
  {
    Keyboard.enableRepeatEvents(false);
  }
  
  private void updateButtons()
  {
    buttonNextPage.visible = ((!bookGettingSigned) && ((currPage < bookTotalPages - 1) || (bookIsUnsigned)));
    buttonPreviousPage.visible = ((!bookGettingSigned) && (currPage > 0));
    buttonDone.visible = ((!bookIsUnsigned) || (!bookGettingSigned));
    
    if (bookIsUnsigned)
    {
      buttonSign.visible = (!bookGettingSigned);
      buttonCancel.visible = bookGettingSigned;
      buttonFinalize.visible = bookGettingSigned;
      buttonFinalize.enabled = (bookTitle.trim().length() > 0);
    }
  }
  
  private void sendBookToServer(boolean p_146462_1_) throws IOException
  {
    if ((bookIsUnsigned) && (bookIsModified))
    {
      if (bookPages != null)
      {


        while (bookPages.tagCount() > 1)
        {
          String var2 = bookPages.getStringTagAt(bookPages.tagCount() - 1);
          
          if (var2.length() != 0) {
            break;
          }
          

          bookPages.removeTag(bookPages.tagCount() - 1);
        }
        
        if (bookObj.hasTagCompound())
        {
          NBTTagCompound var6 = bookObj.getTagCompound();
          var6.setTag("pages", bookPages);
        }
        else
        {
          bookObj.setTagInfo("pages", bookPages);
        }
        
        String var2 = "MC|BEdit";
        
        if (p_146462_1_)
        {
          var2 = "MC|BSign";
          bookObj.setTagInfo("author", new NBTTagString(editingPlayer.getName()));
          bookObj.setTagInfo("title", new NBTTagString(bookTitle.trim()));
          
          for (int var3 = 0; var3 < bookPages.tagCount(); var3++)
          {
            String var4 = bookPages.getStringTagAt(var3);
            ChatComponentText var5 = new ChatComponentText(var4);
            var4 = IChatComponent.Serializer.componentToJson(var5);
            bookPages.set(var3, new NBTTagString(var4));
          }
          
          bookObj.setItem(net.minecraft.init.Items.written_book);
        }
        
        PacketBuffer var7 = new PacketBuffer(Unpooled.buffer());
        var7.writeItemStackToBuffer(bookObj);
        mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload(var2, var7));
      }
    }
  }
  
  protected void actionPerformed(GuiButton button) throws IOException
  {
    if (enabled)
    {
      if (id == 0)
      {
        mc.displayGuiScreen(null);
        sendBookToServer(false);
      }
      else if ((id == 3) && (bookIsUnsigned))
      {
        bookGettingSigned = true;
      }
      else if (id == 1)
      {
        if (currPage < bookTotalPages - 1)
        {
          currPage += 1;
        }
        else if (bookIsUnsigned)
        {
          addNewPage();
          
          if (currPage < bookTotalPages - 1)
          {
            currPage += 1;
          }
        }
      }
      else if (id == 2)
      {
        if (currPage > 0)
        {
          currPage -= 1;
        }
      }
      else if ((id == 5) && (bookGettingSigned))
      {
        sendBookToServer(true);
        mc.displayGuiScreen(null);
      }
      else if ((id == 4) && (bookGettingSigned))
      {
        bookGettingSigned = false;
      }
      
      updateButtons();
    }
  }
  
  private void addNewPage()
  {
    if ((bookPages != null) && (bookPages.tagCount() < 50))
    {
      bookPages.appendTag(new NBTTagString(""));
      bookTotalPages += 1;
      bookIsModified = true;
    }
  }
  



  protected void keyTyped(char typedChar, int keyCode)
    throws IOException
  {
    super.keyTyped(typedChar, keyCode);
    
    if (bookIsUnsigned)
    {
      if (bookGettingSigned)
      {
        keyTypedInTitle(typedChar, keyCode);
      }
      else
      {
        keyTypedInBook(typedChar, keyCode);
      }
    }
  }
  



  private void keyTypedInBook(char p_146463_1_, int p_146463_2_)
  {
    if (GuiScreen.func_175279_e(p_146463_2_))
    {
      pageInsertIntoCurrent(GuiScreen.getClipboardString());
    }
    else
    {
      switch (p_146463_2_)
      {
      case 14: 
        String var3 = pageGetCurrent();
        
        if (var3.length() > 0)
        {
          pageSetCurrent(var3.substring(0, var3.length() - 1));
        }
        
        return;
      
      case 28: 
      case 156: 
        pageInsertIntoCurrent("\n");
        return;
      }
      
      if (ChatAllowedCharacters.isAllowedCharacter(p_146463_1_))
      {
        pageInsertIntoCurrent(Character.toString(p_146463_1_));
      }
    }
  }
  



  private void keyTypedInTitle(char p_146460_1_, int p_146460_2_)
    throws IOException
  {
    switch (p_146460_2_)
    {
    case 14: 
      if (!bookTitle.isEmpty())
      {
        bookTitle = bookTitle.substring(0, bookTitle.length() - 1);
        updateButtons();
      }
      
      return;
    
    case 28: 
    case 156: 
      if (!bookTitle.isEmpty())
      {
        sendBookToServer(true);
        mc.displayGuiScreen(null);
      }
      
      return;
    }
    
    if ((bookTitle.length() < 16) && (ChatAllowedCharacters.isAllowedCharacter(p_146460_1_)))
    {
      bookTitle += Character.toString(p_146460_1_);
      updateButtons();
      bookIsModified = true;
    }
  }
  




  private String pageGetCurrent()
  {
    return (bookPages != null) && (currPage >= 0) && (currPage < bookPages.tagCount()) ? bookPages.getStringTagAt(currPage) : "";
  }
  



  private void pageSetCurrent(String p_146457_1_)
  {
    if ((bookPages != null) && (currPage >= 0) && (currPage < bookPages.tagCount()))
    {
      bookPages.set(currPage, new NBTTagString(p_146457_1_));
      bookIsModified = true;
    }
  }
  



  private void pageInsertIntoCurrent(String p_146459_1_)
  {
    String var2 = pageGetCurrent();
    String var3 = var2 + p_146459_1_;
    int var4 = fontRendererObj.splitStringWidth(var3 + EnumChatFormatting.BLACK + "_", 118);
    
    if ((var4 <= 128) && (var3.length() < 256))
    {
      pageSetCurrent(var3);
    }
  }
  



  public void drawScreen(int mouseX, int mouseY, float partialTicks)
  {
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    mc.getTextureManager().bindTexture(bookGuiTextures);
    int var4 = (width - bookImageWidth) / 2;
    byte var5 = 2;
    drawTexturedModalRect(var4, var5, 0, 0, bookImageWidth, bookImageHeight);
    




    if (bookGettingSigned)
    {
      String var6 = bookTitle;
      
      if (bookIsUnsigned)
      {
        if (updateCount / 6 % 2 == 0)
        {
          var6 = var6 + EnumChatFormatting.BLACK + "_";
        }
        else
        {
          var6 = var6 + EnumChatFormatting.GRAY + "_";
        }
      }
      
      String var7 = I18n.format("book.editTitle", new Object[0]);
      int var8 = fontRendererObj.getStringWidth(var7);
      fontRendererObj.drawString(var7, var4 + 36 + (116 - var8) / 2, var5 + 16 + 16, 0);
      int var9 = fontRendererObj.getStringWidth(var6);
      fontRendererObj.drawString(var6, var4 + 36 + (116 - var9) / 2, var5 + 48, 0);
      String var10 = I18n.format("book.byAuthor", new Object[] { editingPlayer.getName() });
      int var11 = fontRendererObj.getStringWidth(var10);
      fontRendererObj.drawString(EnumChatFormatting.DARK_GRAY + var10, var4 + 36 + (116 - var11) / 2, var5 + 48 + 10, 0);
      String var12 = I18n.format("book.finalizeWarning", new Object[0]);
      fontRendererObj.drawSplitString(var12, var4 + 36, var5 + 80, 116, 0);
    }
    else
    {
      String var6 = I18n.format("book.pageIndicator", new Object[] { Integer.valueOf(currPage + 1), Integer.valueOf(bookTotalPages) });
      String var7 = "";
      
      if ((bookPages != null) && (currPage >= 0) && (currPage < bookPages.tagCount()))
      {
        var7 = bookPages.getStringTagAt(currPage);
      }
      
      if (bookIsUnsigned)
      {
        if (fontRendererObj.getBidiFlag())
        {
          var7 = var7 + "_";
        }
        else if (updateCount / 6 % 2 == 0)
        {
          var7 = var7 + EnumChatFormatting.BLACK + "_";
        }
        else
        {
          var7 = var7 + EnumChatFormatting.GRAY + "_";
        }
      }
      else if (field_175387_B != currPage)
      {
        if (net.minecraft.item.ItemEditableBook.validBookTagContents(bookObj.getTagCompound()))
        {
          try
          {
            IChatComponent var14 = IChatComponent.Serializer.jsonToComponent(var7);
            field_175386_A = (var14 != null ? GuiUtilRenderComponents.func_178908_a(var14, 116, fontRendererObj, true, true) : null);
          }
          catch (JsonParseException var13)
          {
            field_175386_A = null;
          }
        }
        else
        {
          ChatComponentText var15 = new ChatComponentText(EnumChatFormatting.DARK_RED.toString() + "* Invalid book tag *");
          field_175386_A = com.google.common.collect.Lists.newArrayList(var15);
        }
        
        field_175387_B = currPage;
      }
      
      int var8 = fontRendererObj.getStringWidth(var6);
      fontRendererObj.drawString(var6, var4 - var8 + bookImageWidth - 44, var5 + 16, 0);
      
      if (field_175386_A == null)
      {
        fontRendererObj.drawSplitString(var7, var4 + 36, var5 + 16 + 16, 116, 0);
      }
      else
      {
        int var9 = Math.min(128 / fontRendererObj.FONT_HEIGHT, field_175386_A.size());
        
        for (int var16 = 0; var16 < var9; var16++)
        {
          IChatComponent var18 = (IChatComponent)field_175386_A.get(var16);
          fontRendererObj.drawString(var18.getUnformattedText(), var4 + 36, var5 + 16 + 16 + var16 * fontRendererObj.FONT_HEIGHT, 0);
        }
        
        IChatComponent var17 = func_175385_b(mouseX, mouseY);
        
        if (var17 != null)
        {
          func_175272_a(var17, mouseX, mouseY);
        }
      }
    }
    
    super.drawScreen(mouseX, mouseY, partialTicks);
  }
  


  protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    throws IOException
  {
    if (mouseButton == 0)
    {
      IChatComponent var4 = func_175385_b(mouseX, mouseY);
      
      if (func_175276_a(var4))
      {
        return;
      }
    }
    
    super.mouseClicked(mouseX, mouseY, mouseButton);
  }
  
  protected boolean func_175276_a(IChatComponent p_175276_1_)
  {
    ClickEvent var2 = p_175276_1_ == null ? null : p_175276_1_.getChatStyle().getChatClickEvent();
    
    if (var2 == null)
    {
      return false;
    }
    if (var2.getAction() == ClickEvent.Action.CHANGE_PAGE)
    {
      String var6 = var2.getValue();
      
      try
      {
        int var4 = Integer.parseInt(var6) - 1;
        
        if ((var4 >= 0) && (var4 < bookTotalPages) && (var4 != currPage))
        {
          currPage = var4;
          updateButtons();
          return true;
        }
      }
      catch (Throwable localThrowable) {}
      



      return false;
    }
    

    boolean var3 = super.func_175276_a(p_175276_1_);
    
    if ((var3) && (var2.getAction() == ClickEvent.Action.RUN_COMMAND))
    {
      mc.displayGuiScreen(null);
    }
    
    return var3;
  }
  

  public IChatComponent func_175385_b(int p_175385_1_, int p_175385_2_)
  {
    if (field_175386_A == null)
    {
      return null;
    }
    

    int var3 = p_175385_1_ - (width - bookImageWidth) / 2 - 36;
    int var4 = p_175385_2_ - 2 - 16 - 16;
    
    if ((var3 >= 0) && (var4 >= 0))
    {
      int var5 = Math.min(128 / fontRendererObj.FONT_HEIGHT, field_175386_A.size());
      
      if ((var3 <= 116) && (var4 < mc.fontRendererObj.FONT_HEIGHT * var5 + var5))
      {
        int var6 = var4 / mc.fontRendererObj.FONT_HEIGHT;
        
        if ((var6 >= 0) && (var6 < field_175386_A.size()))
        {
          IChatComponent var7 = (IChatComponent)field_175386_A.get(var6);
          int var8 = 0;
          Iterator var9 = var7.iterator();
          
          while (var9.hasNext())
          {
            IChatComponent var10 = (IChatComponent)var9.next();
            
            if ((var10 instanceof ChatComponentText))
            {
              var8 += mc.fontRendererObj.getStringWidth(((ChatComponentText)var10).getChatComponentText_TextValue());
              
              if (var8 > var3)
              {
                return var10;
              }
            }
          }
        }
        
        return null;
      }
      

      return null;
    }
    


    return null;
  }
  

  static class NextPageButton
    extends GuiButton
  {
    private final boolean field_146151_o;
    private static final String __OBFID = "CL_00000745";
    
    public NextPageButton(int p_i46316_1_, int p_i46316_2_, int p_i46316_3_, boolean p_i46316_4_)
    {
      super(p_i46316_2_, p_i46316_3_, 23, 13, "");
      field_146151_o = p_i46316_4_;
    }
    
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
      if (visible)
      {
        boolean var4 = (mouseX >= xPosition) && (mouseY >= yPosition) && (mouseX < xPosition + width) && (mouseY < yPosition + height);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(GuiScreenBook.bookGuiTextures);
        int var5 = 0;
        int var6 = 192;
        
        if (var4)
        {
          var5 += 23;
        }
        
        if (!field_146151_o)
        {
          var6 += 13;
        }
        
        drawTexturedModalRect(xPosition, yPosition, var5, var6, 23, 13);
      }
    }
  }
}
