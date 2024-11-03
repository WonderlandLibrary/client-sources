package net.augustus.modules.render;

import java.awt.Color;

import net.augustus.Augustus;
import net.augustus.clickgui.ClickGui;
import net.augustus.events.EventClickGui;
import net.augustus.events.EventTick;
import net.augustus.latestCGUI.Clickgui;
import net.augustus.material.themes.Dark;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.StringValue;
import net.lenni0451.eventapi.manager.EventManager;
import net.lenni0451.eventapi.reflection.EventTarget;

public class ClickGUI extends Module {
   public StringValue sorting = new StringValue(1, "Sorting", this, "Random", new String[]{"Random", "Length", "Alphabet"});
   public StringValue mode = new StringValue(25, "Mode", this, "Default", new String[]{"Default", "Clean", "New", "Panel"});

   public Clickgui cgui;
   
   public ClickGUI() {
      super("ClickGui", Color.BLACK, Categorys.RENDER);
   }

   @Override
   public void onEnable() {
      Augustus.getInstance().getConverter().moduleSaver(mm.getModules());
      Augustus.getInstance().getConverter().settingSaver(sm.getStgs());
      if(mode.getSelected().equalsIgnoreCase("Default")) {
         mc.displayGuiScreen(Augustus.getInstance().getClickGui());
      //} else if(mode.getSelected().equalsIgnoreCase("Clean")) {
//         mc.displayGuiScreen(Augustus.getInstance().getCleanClickGui());
      } else if(mode.getSelected().equalsIgnoreCase("New")) {
         EventManager.call(new EventClickGui());
         mc.displayGuiScreen(new Dark());
      } else if(mode.getSelected().equalsIgnoreCase("Panel")){
    	  if(cgui == null)
    		  cgui = new Clickgui();
    	  mc.displayGuiScreen(cgui);
//    	  mc.displayGuiScreen(Augustus.getInstance().getClickGui());
      }else {
    	  mc.displayGuiScreen(Augustus.getInstance().getClickGui());
      }
   }

   @Override
   public void onDisable() {
      Augustus.getInstance().getConverter().moduleSaver(mm.getModules());
      Augustus.getInstance().getConverter().settingSaver(sm.getStgs());
   }

   @EventTarget
   public void onEventTick(EventTick eventTick) {
      if (!(mc.currentScreen instanceof ClickGui) && this.isToggled()) {
         this.toggle();
      }
   }
}
