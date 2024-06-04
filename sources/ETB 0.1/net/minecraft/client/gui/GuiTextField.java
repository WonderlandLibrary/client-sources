package net.minecraft.client.gui;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.MathHelper;




public class GuiTextField
  extends Gui
{
  private final int field_175208_g;
  private final FontRenderer fontRendererInstance;
  public int xPosition;
  public int yPosition;
  private final int width;
  private final int height;
  private String text = "";
  private int maxStringLength = 32;
  private int cursorCounter;
  private boolean enableBackgroundDrawing = true;
  



  private boolean canLoseFocus = true;
  



  private boolean isFocused;
  



  private boolean isEnabled = true;
  

  private int lineScrollOffset;
  

  private int cursorPosition;
  
  private int selectionEnd;
  
  private int enabledColor = 14737632;
  private int disabledColor = 7368816;
  

  private boolean visible = true;
  private GuiPageButtonList.GuiResponder field_175210_x;
  private Predicate field_175209_y = Predicates.alwaysTrue();
  private static final String __OBFID = "CL_00000670";
  
  public GuiTextField(int p_i45542_1_, FontRenderer p_i45542_2_, int p_i45542_3_, int p_i45542_4_, int p_i45542_5_, int p_i45542_6_)
  {
    field_175208_g = p_i45542_1_;
    fontRendererInstance = p_i45542_2_;
    xPosition = p_i45542_3_;
    yPosition = p_i45542_4_;
    width = p_i45542_5_;
    height = p_i45542_6_;
  }
  
  public void func_175207_a(GuiPageButtonList.GuiResponder p_175207_1_)
  {
    field_175210_x = p_175207_1_;
  }
  



  public void updateCursorCounter()
  {
    cursorCounter += 1;
  }
  



  public void setText(String p_146180_1_)
  {
    if (field_175209_y.apply(p_146180_1_))
    {
      if (p_146180_1_.length() > maxStringLength)
      {
        text = p_146180_1_.substring(0, maxStringLength);
      }
      else
      {
        text = p_146180_1_;
      }
      
      setCursorPositionEnd();
    }
  }
  



  public String getText()
  {
    return text;
  }
  



  public String getSelectedText()
  {
    int var1 = cursorPosition < selectionEnd ? cursorPosition : selectionEnd;
    int var2 = cursorPosition < selectionEnd ? selectionEnd : cursorPosition;
    return text.substring(var1, var2);
  }
  
  public void func_175205_a(Predicate p_175205_1_)
  {
    field_175209_y = p_175205_1_;
  }
  



  public void writeText(String p_146191_1_)
  {
    String var2 = "";
    String var3 = ChatAllowedCharacters.filterAllowedCharacters(p_146191_1_);
    int var4 = cursorPosition < selectionEnd ? cursorPosition : selectionEnd;
    int var5 = cursorPosition < selectionEnd ? selectionEnd : cursorPosition;
    int var6 = maxStringLength - text.length() - (var4 - var5);
    boolean var7 = false;
    
    if (text.length() > 0)
    {
      var2 = var2 + text.substring(0, var4);
    }
    
    int var8;
    int var8;
    if (var6 < var3.length())
    {
      var2 = var2 + var3.substring(0, var6);
      var8 = var6;
    }
    else
    {
      var2 = var2 + var3;
      var8 = var3.length();
    }
    
    if ((text.length() > 0) && (var5 < text.length()))
    {
      var2 = var2 + text.substring(var5);
    }
    
    if (field_175209_y.apply(var2))
    {
      text = var2;
      moveCursorBy(var4 - selectionEnd + var8);
      
      if (field_175210_x != null)
      {
        field_175210_x.func_175319_a(field_175208_g, text);
      }
    }
  }
  




  public void deleteWords(int p_146177_1_)
  {
    if (text.length() != 0)
    {
      if (selectionEnd != cursorPosition)
      {
        writeText("");
      }
      else
      {
        deleteFromCursor(getNthWordFromCursor(p_146177_1_) - cursorPosition);
      }
    }
  }
  



  public void deleteFromCursor(int p_146175_1_)
  {
    if (text.length() != 0)
    {
      if (selectionEnd != cursorPosition)
      {
        writeText("");
      }
      else
      {
        boolean var2 = p_146175_1_ < 0;
        int var3 = var2 ? cursorPosition + p_146175_1_ : cursorPosition;
        int var4 = var2 ? cursorPosition : cursorPosition + p_146175_1_;
        String var5 = "";
        
        if (var3 >= 0)
        {
          var5 = text.substring(0, var3);
        }
        
        if (var4 < text.length())
        {
          var5 = var5 + text.substring(var4);
        }
        
        text = var5;
        
        if (var2)
        {
          moveCursorBy(p_146175_1_);
        }
        
        if (field_175210_x != null)
        {
          field_175210_x.func_175319_a(field_175208_g, text);
        }
      }
    }
  }
  
  public int func_175206_d()
  {
    return field_175208_g;
  }
  



  public int getNthWordFromCursor(int p_146187_1_)
  {
    return getNthWordFromPos(p_146187_1_, getCursorPosition());
  }
  



  public int getNthWordFromPos(int p_146183_1_, int p_146183_2_)
  {
    return func_146197_a(p_146183_1_, p_146183_2_, true);
  }
  
  public int func_146197_a(int p_146197_1_, int p_146197_2_, boolean p_146197_3_)
  {
    int var4 = p_146197_2_;
    boolean var5 = p_146197_1_ < 0;
    int var6 = Math.abs(p_146197_1_);
    
    for (int var7 = 0; var7 < var6; var7++)
    {
      if (var5)
      {
        for (;;)
        {
          var4--;
          if ((p_146197_3_) && (var4 > 0)) { if (text.charAt(var4 - 1) != ' ') {
              break;
            }
          }
        }
        do
        {
          var4--;
          if (var4 <= 0) break; } while (text.charAt(var4 - 1) != ' ');


      }
      else
      {

        int var8 = text.length();
        var4 = text.indexOf(' ', var4);
        
        if (var4 == -1)
        {
          var4 = var8;
        }
        else
        {
          while ((p_146197_3_) && (var4 < var8) && (text.charAt(var4) == ' '))
          {
            var4++;
          }
        }
      }
    }
    
    return var4;
  }
  



  public void moveCursorBy(int p_146182_1_)
  {
    setCursorPosition(selectionEnd + p_146182_1_);
  }
  



  public void setCursorPosition(int p_146190_1_)
  {
    cursorPosition = p_146190_1_;
    int var2 = text.length();
    cursorPosition = MathHelper.clamp_int(cursorPosition, 0, var2);
    setSelectionPos(cursorPosition);
  }
  



  public void setCursorPositionZero()
  {
    setCursorPosition(0);
  }
  



  public void setCursorPositionEnd()
  {
    setCursorPosition(text.length());
  }
  



  public boolean textboxKeyTyped(char p_146201_1_, int p_146201_2_)
  {
    if (!isFocused)
    {
      return false;
    }
    if (GuiScreen.func_175278_g(p_146201_2_))
    {
      setCursorPositionEnd();
      setSelectionPos(0);
      return true;
    }
    if (GuiScreen.func_175280_f(p_146201_2_))
    {
      GuiScreen.setClipboardString(getSelectedText());
      return true;
    }
    if (GuiScreen.func_175279_e(p_146201_2_))
    {
      if (isEnabled)
      {
        writeText(GuiScreen.getClipboardString());
      }
      
      return true;
    }
    if (GuiScreen.func_175277_d(p_146201_2_))
    {
      GuiScreen.setClipboardString(getSelectedText());
      
      if (isEnabled)
      {
        writeText("");
      }
      
      return true;
    }
    

    switch (p_146201_2_)
    {
    case 14: 
      if (GuiScreen.isCtrlKeyDown())
      {
        if (isEnabled)
        {
          deleteWords(-1);
        }
      }
      else if (isEnabled)
      {
        deleteFromCursor(-1);
      }
      
      return true;
    
    case 199: 
      if (GuiScreen.isShiftKeyDown())
      {
        setSelectionPos(0);
      }
      else
      {
        setCursorPositionZero();
      }
      
      return true;
    
    case 203: 
      if (GuiScreen.isShiftKeyDown())
      {
        if (GuiScreen.isCtrlKeyDown())
        {
          setSelectionPos(getNthWordFromPos(-1, getSelectionEnd()));
        }
        else
        {
          setSelectionPos(getSelectionEnd() - 1);
        }
      }
      else if (GuiScreen.isCtrlKeyDown())
      {
        setCursorPosition(getNthWordFromCursor(-1));
      }
      else
      {
        moveCursorBy(-1);
      }
      
      return true;
    
    case 205: 
      if (GuiScreen.isShiftKeyDown())
      {
        if (GuiScreen.isCtrlKeyDown())
        {
          setSelectionPos(getNthWordFromPos(1, getSelectionEnd()));
        }
        else
        {
          setSelectionPos(getSelectionEnd() + 1);
        }
      }
      else if (GuiScreen.isCtrlKeyDown())
      {
        setCursorPosition(getNthWordFromCursor(1));
      }
      else
      {
        moveCursorBy(1);
      }
      
      return true;
    
    case 207: 
      if (GuiScreen.isShiftKeyDown())
      {
        setSelectionPos(text.length());
      }
      else
      {
        setCursorPositionEnd();
      }
      
      return true;
    
    case 211: 
      if (GuiScreen.isCtrlKeyDown())
      {
        if (isEnabled)
        {
          deleteWords(1);
        }
      }
      else if (isEnabled)
      {
        deleteFromCursor(1);
      }
      
      return true;
    }
    
    if (ChatAllowedCharacters.isAllowedCharacter(p_146201_1_))
    {
      if (isEnabled)
      {
        writeText(Character.toString(p_146201_1_));
      }
      
      return true;
    }
    

    return false;
  }
  






  public void mouseClicked(int p_146192_1_, int p_146192_2_, int p_146192_3_)
  {
    boolean var4 = (p_146192_1_ >= xPosition) && (p_146192_1_ < xPosition + width) && (p_146192_2_ >= yPosition) && (p_146192_2_ < yPosition + height);
    
    if (canLoseFocus)
    {
      setFocused(var4);
    }
    
    if ((isFocused) && (var4) && (p_146192_3_ == 0))
    {
      int var5 = p_146192_1_ - xPosition;
      
      if (enableBackgroundDrawing)
      {
        var5 -= 4;
      }
      
      String var6 = fontRendererInstance.trimStringToWidth(text.substring(lineScrollOffset), getWidth());
      setCursorPosition(fontRendererInstance.trimStringToWidth(var6, var5).length() + lineScrollOffset);
    }
  }
  



  public void drawTextBox()
  {
    if (getVisible())
    {
      if (getEnableBackgroundDrawing())
      {
        drawRect(xPosition - 1, yPosition - 1, xPosition + width + 1, yPosition + height + 1, -6250336);
        drawRect(xPosition, yPosition, xPosition + width, yPosition + height, -16777216);
      }
      
      int var1 = isEnabled ? enabledColor : disabledColor;
      int var2 = cursorPosition - lineScrollOffset;
      int var3 = selectionEnd - lineScrollOffset;
      String var4 = fontRendererInstance.trimStringToWidth(text.substring(lineScrollOffset), getWidth());
      boolean var5 = (var2 >= 0) && (var2 <= var4.length());
      boolean var6 = (isFocused) && (cursorCounter / 6 % 2 == 0) && (var5);
      int var7 = enableBackgroundDrawing ? xPosition + 4 : xPosition;
      int var8 = enableBackgroundDrawing ? yPosition + (height - 8) / 2 : yPosition;
      int var9 = var7;
      
      if (var3 > var4.length())
      {
        var3 = var4.length();
      }
      
      if (var4.length() > 0)
      {
        String var10 = var5 ? var4.substring(0, var2) : var4;
        var9 = fontRendererInstance.drawStringWithShadow(var10, var7, var8, var1);
      }
      
      boolean var13 = (cursorPosition < text.length()) || (text.length() >= getMaxStringLength());
      int var11 = var9;
      
      if (!var5)
      {
        var11 = var2 > 0 ? var7 + width : var7;
      }
      else if (var13)
      {
        var11 = var9 - 1;
        var9--;
      }
      
      if ((var4.length() > 0) && (var5) && (var2 < var4.length()))
      {
        var9 = fontRendererInstance.drawStringWithShadow(var4.substring(var2), var9, var8, var1);
      }
      
      if (var6)
      {
        if (var13)
        {
          Gui.drawRect(var11, var8 - 1, var11 + 1, var8 + 1 + fontRendererInstance.FONT_HEIGHT, -3092272);
        }
        else
        {
          fontRendererInstance.drawStringWithShadow("_", var11, var8, var1);
        }
      }
      
      if (var3 != var2)
      {
        int var12 = var7 + fontRendererInstance.getStringWidth(var4.substring(0, var3));
        drawCursorVertical(var11, var8 - 1, var12 - 1, var8 + 1 + fontRendererInstance.FONT_HEIGHT);
      }
    }
  }
  





  private void drawCursorVertical(int p_146188_1_, int p_146188_2_, int p_146188_3_, int p_146188_4_)
  {
    if (p_146188_1_ < p_146188_3_)
    {
      int var5 = p_146188_1_;
      p_146188_1_ = p_146188_3_;
      p_146188_3_ = var5;
    }
    
    if (p_146188_2_ < p_146188_4_)
    {
      int var5 = p_146188_2_;
      p_146188_2_ = p_146188_4_;
      p_146188_4_ = var5;
    }
    
    if (p_146188_3_ > xPosition + width)
    {
      p_146188_3_ = xPosition + width;
    }
    
    if (p_146188_1_ > xPosition + width)
    {
      p_146188_1_ = xPosition + width;
    }
    
    Tessellator var7 = Tessellator.getInstance();
    WorldRenderer var6 = var7.getWorldRenderer();
    GlStateManager.color(0.0F, 0.0F, 255.0F, 255.0F);
    GlStateManager.func_179090_x();
    GlStateManager.enableColorLogic();
    GlStateManager.colorLogicOp(5387);
    var6.startDrawingQuads();
    var6.addVertex(p_146188_1_, p_146188_4_, 0.0D);
    var6.addVertex(p_146188_3_, p_146188_4_, 0.0D);
    var6.addVertex(p_146188_3_, p_146188_2_, 0.0D);
    var6.addVertex(p_146188_1_, p_146188_2_, 0.0D);
    var7.draw();
    GlStateManager.disableColorLogic();
    GlStateManager.func_179098_w();
  }
  
  public void setMaxStringLength(int p_146203_1_)
  {
    maxStringLength = p_146203_1_;
    
    if (text.length() > p_146203_1_)
    {
      text = text.substring(0, p_146203_1_);
    }
  }
  



  public int getMaxStringLength()
  {
    return maxStringLength;
  }
  



  public int getCursorPosition()
  {
    return cursorPosition;
  }
  



  public boolean getEnableBackgroundDrawing()
  {
    return enableBackgroundDrawing;
  }
  



  public void setEnableBackgroundDrawing(boolean p_146185_1_)
  {
    enableBackgroundDrawing = p_146185_1_;
  }
  



  public void setTextColor(int p_146193_1_)
  {
    enabledColor = p_146193_1_;
  }
  
  public void setDisabledTextColour(int p_146204_1_)
  {
    disabledColor = p_146204_1_;
  }
  



  public void setFocused(boolean p_146195_1_)
  {
    if ((p_146195_1_) && (!isFocused))
    {
      cursorCounter = 0;
    }
    
    isFocused = p_146195_1_;
  }
  



  public boolean isFocused()
  {
    return isFocused;
  }
  
  public void setEnabled(boolean p_146184_1_)
  {
    isEnabled = p_146184_1_;
  }
  



  public int getSelectionEnd()
  {
    return selectionEnd;
  }
  



  public int getWidth()
  {
    return getEnableBackgroundDrawing() ? width - 8 : width;
  }
  



  public void setSelectionPos(int p_146199_1_)
  {
    int var2 = text.length();
    
    if (p_146199_1_ > var2)
    {
      p_146199_1_ = var2;
    }
    
    if (p_146199_1_ < 0)
    {
      p_146199_1_ = 0;
    }
    
    selectionEnd = p_146199_1_;
    
    if (fontRendererInstance != null)
    {
      if (lineScrollOffset > var2)
      {
        lineScrollOffset = var2;
      }
      
      int var3 = getWidth();
      String var4 = fontRendererInstance.trimStringToWidth(text.substring(lineScrollOffset), var3);
      int var5 = var4.length() + lineScrollOffset;
      
      if (p_146199_1_ == lineScrollOffset)
      {
        lineScrollOffset -= fontRendererInstance.trimStringToWidth(text, var3, true).length();
      }
      
      if (p_146199_1_ > var5)
      {
        lineScrollOffset += p_146199_1_ - var5;
      }
      else if (p_146199_1_ <= lineScrollOffset)
      {
        lineScrollOffset -= lineScrollOffset - p_146199_1_;
      }
      
      lineScrollOffset = MathHelper.clamp_int(lineScrollOffset, 0, var2);
    }
  }
  



  public void setCanLoseFocus(boolean p_146205_1_)
  {
    canLoseFocus = p_146205_1_;
  }
  



  public boolean getVisible()
  {
    return visible;
  }
  



  public void setVisible(boolean p_146189_1_)
  {
    visible = p_146189_1_;
  }
}
