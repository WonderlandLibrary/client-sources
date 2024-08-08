package me.foo.lizardclient.module;
import java.util.ArrayList;

import me.foo.lizardclient.modules.*;
 
 public class ModuleManager {
   public ArrayList<Module> moduleList = new ArrayList<>();
 
   
   public void Init() {
	   	this.moduleList.add(new AutoMLG());
	   	/*this.moduleList.add(new AutoSprint());
	   	this.moduleList.add(new Bhop());
	   	this.moduleList.add(new CrystalAura());
	   	this.moduleList.add(new EntityESP());
	   	this.moduleList.add(new Fly());
	   	this.moduleList.add(new Fullbright());
	   	this.moduleList.add(new Glide());
	   	this.moduleList.add(new KillAura());
	   	this.moduleList.add(new Nofall());
		this.moduleList.add(new Speed());*/
   }
 
   
   public ArrayList<Module> getEnabledModules() {
     ArrayList<Module> toggledModules = new ArrayList<>();
	for (Module module : this.moduleList) {
	if (module.isEnabled().booleanValue()) {
		toggledModules.add(module);
         continue;
       } 
			if (!module.disable.booleanValue()) {
			module.onDisable();
       }
     } 
     
		return toggledModules;
   }
 }