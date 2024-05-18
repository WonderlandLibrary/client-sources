package net.minecraft.util;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;




public abstract class ChatComponentStyle
  implements IChatComponent
{
  protected List siblings = Lists.newArrayList();
  
  private ChatStyle style;
  private static final String __OBFID = "CL_00001257";
  
  public ChatComponentStyle() {}
  
  public IChatComponent appendSibling(IChatComponent component)
  {
    component.getChatStyle().setParentStyle(getChatStyle());
    siblings.add(component);
    return this;
  }
  



  public List getSiblings()
  {
    return siblings;
  }
  



  public IChatComponent appendText(String text)
  {
    return appendSibling(new ChatComponentText(text));
  }
  
  public IChatComponent setChatStyle(ChatStyle style)
  {
    this.style = style;
    Iterator var2 = siblings.iterator();
    
    while (var2.hasNext())
    {
      IChatComponent var3 = (IChatComponent)var2.next();
      var3.getChatStyle().setParentStyle(getChatStyle());
    }
    
    return this;
  }
  
  public ChatStyle getChatStyle()
  {
    if (style == null)
    {
      style = new ChatStyle();
      Iterator var1 = siblings.iterator();
      
      while (var1.hasNext())
      {
        IChatComponent var2 = (IChatComponent)var1.next();
        var2.getChatStyle().setParentStyle(style);
      }
    }
    
    return style;
  }
  
  public Iterator iterator()
  {
    return Iterators.concat(Iterators.forArray(new ChatComponentStyle[] { this }), createDeepCopyIterator(siblings));
  }
  




  public final String getUnformattedText()
  {
    StringBuilder var1 = new StringBuilder();
    Iterator var2 = iterator();
    
    while (var2.hasNext())
    {
      IChatComponent var3 = (IChatComponent)var2.next();
      var1.append(var3.getUnformattedTextForChat());
    }
    
    return var1.toString();
  }
  



  public final String getFormattedText()
  {
    StringBuilder var1 = new StringBuilder();
    Iterator var2 = iterator();
    
    while (var2.hasNext())
    {
      IChatComponent var3 = (IChatComponent)var2.next();
      var1.append(var3.getChatStyle().getFormattingCode());
      var1.append(var3.getUnformattedTextForChat());
      var1.append(EnumChatFormatting.RESET);
    }
    
    return var1.toString();
  }
  




  public static Iterator createDeepCopyIterator(Iterable components)
  {
    Iterator var1 = Iterators.concat(Iterators.transform(components.iterator(), new Function()
    {
      private static final String __OBFID = "CL_00001258";
      
      public Iterator apply(IChatComponent p_apply_1_) {
        return p_apply_1_.iterator();
      }
      
      public Object apply(Object p_apply_1_) {
        return apply((IChatComponent)p_apply_1_);
      }
    }));
    var1 = Iterators.transform(var1, new Function()
    {
      private static final String __OBFID = "CL_00001259";
      
      public IChatComponent apply(IChatComponent p_apply_1_) {
        IChatComponent var2 = p_apply_1_.createCopy();
        var2.setChatStyle(var2.getChatStyle().createDeepCopy());
        return var2;
      }
      
      public Object apply(Object p_apply_1_) {
        return apply((IChatComponent)p_apply_1_);
      }
    });
    return var1;
  }
  
  public boolean equals(Object p_equals_1_)
  {
    if (this == p_equals_1_)
    {
      return true;
    }
    if (!(p_equals_1_ instanceof ChatComponentStyle))
    {
      return false;
    }
    

    ChatComponentStyle var2 = (ChatComponentStyle)p_equals_1_;
    return (siblings.equals(siblings)) && (getChatStyle().equals(var2.getChatStyle()));
  }
  

  public int hashCode()
  {
    return 31 * style.hashCode() + siblings.hashCode();
  }
  
  public String toString()
  {
    return "BaseComponent{style=" + style + ", siblings=" + siblings + '}';
  }
}
