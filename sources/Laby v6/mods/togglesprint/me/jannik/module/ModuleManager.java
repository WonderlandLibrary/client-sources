package mods.togglesprint.me.jannik.module;

import java.util.ArrayList;
import java.util.Comparator;
import mods.togglesprint.me.jannik.module.modules.bedwars.Eagle;
import mods.togglesprint.me.jannik.module.modules.bedwars.FastPlace;
import mods.togglesprint.me.jannik.module.modules.bedwars.SafeWalk;
import mods.togglesprint.me.jannik.module.modules.combat.AutoClicker;
import mods.togglesprint.me.jannik.module.modules.combat.HitBox;
import mods.togglesprint.me.jannik.module.modules.combat.Range;
import mods.togglesprint.me.jannik.module.modules.combat.TriggerBot;
import mods.togglesprint.me.jannik.module.modules.movement.KeepSprint;
import mods.togglesprint.me.jannik.module.modules.movement.NoToggleSneak;
import mods.togglesprint.me.jannik.module.modules.movement.Sprint;
import mods.togglesprint.me.jannik.module.modules.movement.Velocity;
import mods.togglesprint.me.jannik.module.modules.player.AutoMLG;
import mods.togglesprint.me.jannik.module.modules.player.AutoSoup;
import mods.togglesprint.me.jannik.module.modules.player.ChestStealer;
import mods.togglesprint.me.jannik.module.modules.player.NameProtect;
import mods.togglesprint.me.jannik.module.modules.render.ChestEsp;
import mods.togglesprint.me.jannik.module.modules.render.ClickGui;
import mods.togglesprint.me.jannik.module.modules.render.CustomBackGround;
import mods.togglesprint.me.jannik.module.modules.render.ExternalGui;
import mods.togglesprint.me.jannik.module.modules.render.FullBright;
import mods.togglesprint.me.jannik.module.modules.render.Hud;
import mods.togglesprint.me.jannik.module.modules.render.NameTags;
import mods.togglesprint.me.jannik.module.modules.render.TransparentChat;
import mods.togglesprint.me.jannik.module.modules.settings.Panic;
import mods.togglesprint.me.jannik.module.modules.settings.Uninstall;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
//LabyClient src by Exeptiq
public class ModuleManager
{
  FontRenderer f = Minecraft.getMinecraft().fontRendererObj;
  private ArrayList<Module> modules = new ArrayList();
  
  private void addModules(Module modules)
  {
    this.modules.add(modules);
  }
  
  public ModuleManager()
  {
    addModules(new AutoClicker());
    addModules(new HitBox());
    addModules(new Range());
    addModules(new TriggerBot());
    
    addModules(new KeepSprint());
    addModules(new NoToggleSneak());
    addModules(new Sprint());
    addModules(new Velocity());
    
    addModules(new ChestEsp());
    addModules(new ClickGui());
    addModules(new CustomBackGround());
    addModules(new ExternalGui());
    addModules(new FullBright());
    addModules(new Hud());
    addModules(new NameTags());
    addModules(new TransparentChat());
    
    addModules(new AutoMLG());
    addModules(new AutoSoup());
    addModules(new ChestStealer());
    addModules(new NameProtect());
    
    addModules(new Eagle());
    addModules(new FastPlace());
    addModules(new SafeWalk());
    
    addModules(new Panic());
    addModules(new Uninstall());
  }
  
  public ArrayList<Module> getModules()
  {
    return this.modules;
  }
  
  public ArrayList<Module> getModulesSorted()
  {
    ArrayList<Module> modulesSorted = new ArrayList();
    modulesSorted.addAll(this.modules);
    modulesSorted.sort(new Comparator()
    {
      public int compare(Module m1, Module m2)
      {
        return ModuleManager.this.f.getStringWidth(m2.getName()) - ModuleManager.this.f.getStringWidth(m1.getName());
      }

	@Override
	public int compare(Object o1, Object o2) {
		return 0;
	}
    });
    return modulesSorted;
  }
  
  public Module getModuleByClass(Class clazz)
  {
    for (Module m : this.modules) {
      if (m.getClass() == clazz) {
        return m;
      }
    }
    return null;
  }
  
  public Module getModuleByName(String name)
  {
    for (Module m : this.modules) {
      if (name.equals(m.getName())) {
        return m;
      }
    }
    return null;
  }
}
