package me.protocol_client.ui.tabgui;

import java.util.ArrayList;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class UglyTabGui
{
  private ArrayList<String> category = new ArrayList();
  private FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
  private int selectedTab;
  private int selectedMod;
  private boolean mainMenu = true;
  
  public UglyTabGui()
  {
    Category[] arrayOfModCategory;
    int j = (arrayOfModCategory = Category.values()).length;
    for (int i = 0; i < j; i++)
    {
      Category mc = arrayOfModCategory[i];
      if(mc.name().equalsIgnoreCase("Other") || mc.name().equalsIgnoreCase("ProtocolButtons"))
  		continue;
      this.category.add(mc.toString().substring(0, 1) + mc.toString().substring(1, mc.toString().length()).toLowerCase());
    }
  }
  
  public void drawTabGui()
  {
	  Wrapper.drawBorderRect(1,1, 75, 32 + this.category.size() * 11, Protocol.getColor().getRGB(), 0x90000000, 1);
	  Wrapper.drawBorderRect(1, selectedTab * 13 + 16, 75, selectedTab * 13 + 32, 0xcb, Protocol.getColor().getRGB(), 1);
	  Wrapper.drawBorderRect(1, 1, 75, 17, Protocol.getColor().getRGB(), Protocol.getColor().getRGB(), 1);
	 
    Gui.drawString(Wrapper.mc().fontRendererObj, Protocol.name + " v" + Protocol.version, 12, 5, 0xFFFFFFFF);
    int categoryCount = 0;
    for (String s : this.category)
    {
    	if(s.equalsIgnoreCase("Other") || s.equalsIgnoreCase("ProtocolButtons"))
    		continue;
      Gui.drawCenteredString(fr, categoryCount == this.selectedTab ? "§f" + s :"\247f" + s, 37, 20 + categoryCount * 13, -1);
      categoryCount++;
    }
    if (!this.mainMenu)
    {
    	//Gui.drawRect(left, top, right, bottom, color);
    	if(selectedTab == 0){
    		Wrapper.drawBorderRect(77, 17, 170, 2 + getModsForCategory().size() * 13 + 17, Protocol.getColor().getRGB(), 0x90000000, 1);
     		Wrapper.drawBorderRect(77, selectedMod * 13 + 17, 170, selectedMod * 13 + 33, 0xcb, Protocol.getColor().getRGB(), 1);
    	}
    	if(selectedTab == 1){
    		Wrapper.drawBorderRect(77, 30, 170, 2 + getModsForCategory().size() * 13 + 30, Protocol.getColor().getRGB(), 0x90000000, 1);
     		Wrapper.drawBorderRect(77, selectedMod * 13 + 30, 170, selectedMod * 13 + 46, 0xcb, Protocol.getColor().getRGB(), 1);
    	}
    	if(selectedTab == 2){
    		Wrapper.drawBorderRect(77, 43, 170, 2 + getModsForCategory().size() * 13 + 43, Protocol.getColor().getRGB(), 0x90000000, 1);
     		Wrapper.drawBorderRect(77, selectedMod * 13 + 43, 170, selectedMod * 13 + 59, 0xcb, Protocol.getColor().getRGB(), 1);
    	}
    	if(selectedTab == 3){
    		Wrapper.drawBorderRect(77, 56, 170, 2 + getModsForCategory().size() * 13 + 56, Protocol.getColor().getRGB(), 0x90000000, 1);
     		Wrapper.drawBorderRect(77, selectedMod * 13 + 56, 170, selectedMod * 13 + 72, 0xcb, Protocol.getColor().getRGB(), 1);
    	}
    	if(selectedTab == 4){
    		Wrapper.drawBorderRect(77, 69, 170, 2 + getModsForCategory().size() * 13 + 69, Protocol.getColor().getRGB(), 0x90000000, 1);
     		Wrapper.drawBorderRect(77, selectedMod * 13 + 69, 170, selectedMod * 13 + 85, 0xcb, Protocol.getColor().getRGB(), 1);
    	}
    	if(selectedTab == 5){
    		Wrapper.drawBorderRect(77, 82, 170, 2 + getModsForCategory().size() * 13 + 82, Protocol.getColor().getRGB(), 0x90000000, 1);
     		Wrapper.drawBorderRect(77, selectedMod * 13 + 82, 170, selectedMod * 13 + 98, 0xcb, Protocol.getColor().getRGB(), 1);
    	}
    	if(selectedTab == 6){
    		Wrapper.drawBorderRect(77, 95, 170, 2 + getModsForCategory().size() * 13 + 95, Protocol.getColor().getRGB(), 0x90000000, 1);
     		Wrapper.drawBorderRect(77, selectedMod * 13 + 95, 170, selectedMod * 13 + 111, 0xcb, Protocol.getColor().getRGB(), 1);
    	}
    	//Wrapper.drawBorderRect(77, 1, 170, 2 + getModsForCategory().size() * 13, Protocol.getColor().getRGB(), 0x90000000, 1);
 		//Wrapper.drawBorderRect(77, selectedMod * 13, 170, selectedMod * 13 + 16, 0xcb, Protocol.getColor().getRGB(), 1);
      //GuiUtils.drawBorderedRect(58, 2, 61 + getLongestModWidth(), 2 + getModsForCategory().size() * 11, -1442840576, Integer.MIN_VALUE);
      int modCount = 0;
      for (Module mod : getModsForCategory())
      {
    	  String color;
    	  if(mod.isToggled()){
      		color = "\247e";
      	}else
      	color = "\2477";
        String name = modCount == this.selectedMod ?  color + mod.getName() + "§f" : color + mod.getName();
        //Gui.drawRect(left, top, right, bottom, color);
        if(selectedTab == 0){
            Gui.drawString(fr, " \247b" + name, 78, 21 + modCount * 13, 0xFFFFFFFF);
            }
        if(selectedTab == 1){
            Gui.drawString(fr, " \247b" + name, 78, 34 + modCount * 13, 0xFFFFFFFF);
            }
        if(selectedTab == 2){
            Gui.drawString(fr, " \247b" + name, 78, 47 + modCount * 13, 0xFFFFFFFF);
            }
        if(selectedTab == 3){
            Gui.drawString(fr, " \247b" + name, 78, 60 + modCount * 13, 0xFFFFFFFF);
            }
        if(selectedTab == 4){
            Gui.drawString(fr, " \247b" + name, 78, 73 + modCount * 13, 0xFFFFFFFF);
            }
        if(selectedTab == 5){
            Gui.drawString(fr, " \247b" + name, 78, 86 + modCount * 13, 0xFFFFFFFF);
            }
        if(selectedTab == 6){
            Gui.drawString(fr, " \247b" + name, 78, 99 + modCount * 13, 0xFFFFFFFF);
            }
        modCount++;
      }
    }
  }
  
  public void down()
  {
    if (this.mainMenu)
    {
      if (this.selectedTab >= this.category.size() - 1) {
        this.selectedTab = -1;
      }
      this.selectedTab += 1;
    }
    else
    {
      if (this.selectedMod >= getModsForCategory().size() - 1) {
        this.selectedMod = -1;
      }
      this.selectedMod += 1;
    }
  }
  
  public void up()
  {
    if (this.mainMenu)
    {
      if (this.selectedTab <= 0) {
        this.selectedTab = this.category.size();
      }
      this.selectedTab -= 1;
    }
    else
    {
      if (this.selectedMod <= 0) {
        this.selectedMod = getModsForCategory().size();
      }
      this.selectedMod -= 1;
    }
  }
  
  public void left()
  {
    this.mainMenu = true;
  }
  
  public void right()
  {
    if (!this.mainMenu)
    {
    	enter();
      ((Module)getModsForCategory().get(this.selectedMod)).onToggle();
    }
    else
    {
      this.selectedMod = 0;
      this.mainMenu = false;
    }
  }
  
  public void enter()
  {
    if (!this.mainMenu) {
      ((Module)getModsForCategory().get(this.selectedMod)).toggle();
    }
  }
  
  private ArrayList<Module> getModsForCategory()
  {
    ArrayList<Module> mods = new ArrayList();
    for (Module mod : Protocol.getModules()) {
      if (mod.getCategory() == Category.valueOf(((String)this.category.get(this.selectedTab)).toUpperCase())) {
        mods.add(mod);
      }
    }
    return mods;
  }
  
  private int getLongestModWidth()
  {
    int longest = 0;
    for (Module mod : getModsForCategory()) {
      if (this.fr.getStringWidth(mod.getName()) > longest) {
        longest = this.fr.getStringWidth(mod.getName());
      }
    }
    return longest;
  }
}
