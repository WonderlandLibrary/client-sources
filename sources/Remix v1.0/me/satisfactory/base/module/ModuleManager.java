package me.satisfactory.base.module;

import java.io.*;
import me.mees.remix.modules.render.*;
import me.mees.remix.modules.player.*;
import me.mees.remix.modules.world.*;
import me.mees.remix.modules.exploit.*;
import me.mees.remix.modules.movement.*;
import me.mees.remix.modules.combat.*;
import xyz.vladymyr.commons.*;
import me.satisfactory.base.utils.file.*;
import me.satisfactory.base.*;
import java.util.*;
import me.satisfactory.base.gui.tabgui.*;
import me.satisfactory.base.setting.*;

public class ModuleManager
{
    private static final File MODULE_DIR;
    public static Map<String, Module> modules;
    
    public ModuleManager() {
        Maps.putAll(ModuleManager.modules = new HashMap<String, Module>(), Module::getName, new Resolver(), new EventTest(), new AntiBot(), new AntiGuardianBan(), new FastUse(), new AntiCopyright(), new AntiKnockback(), new AutoArmor(), new AutoClicker(), new AutoPotion(), new BowAimbot(), new ChestStealer(), new ClickGui(), new Crack(), new CreativeDrop(), new Criticals(), new CSGOGui(), new Dab(), new ESP(), new Eagle(), new FastPlace(), new Fastbow(), new Flight(), new Fucker(), new Fullbright(), new HUD(), new InvCleaner(), new InvMove(), new ItemPhysics(), new Killaura(), new LagSign(), new Longjump(), new MCF(), new Nametags(), new NameProtect(), new NoHurtCam(), new NoSlowDown(), new NoSwing(), new Nofall(), new PotionSaver(), new Phase(), new Regen(), new Scaffold(), new ServerCrasher(), new Speed(), new Sprint(), new TPAura(), new WTap());
    }
    
    public static void load() {
        final List<String> fileContent = FileUtils.read(ModuleManager.MODULE_DIR);
        for (final String line : fileContent) {
            try {
                final String[] split = line.split(":");
                final String name = split[0];
                final String bind = split[1];
                final String enable = split[2];
                final int key = Integer.parseInt(bind);
                Base.INSTANCE.getModuleManager();
                for (final Module m : ModuleManager.modules.values()) {
                    if (!name.equalsIgnoreCase(m.getName())) {
                        continue;
                    }
                    m.setKeybind(key);
                    if (!enable.equalsIgnoreCase("true")) {
                        continue;
                    }
                    if (m.isEnabled()) {
                        continue;
                    }
                    if (m.getName().equalsIgnoreCase("clickGuiFont") || m.getName().equalsIgnoreCase("CSGOGui")) {
                        continue;
                    }
                    m.toggle();
                }
            }
            catch (Exception split2) {
                System.err.println(split2);
            }
        }
    }
    
    public static void save() {
        final ArrayList<String> fileContent = new ArrayList<String>();
        Base.INSTANCE.getModuleManager();
        for (final Module m : ModuleManager.modules.values()) {
            fileContent.add(String.valueOf(m.getName()) + ":" + m.getKeybind() + ":" + m.isEnabled());
        }
        FileUtils.write(ModuleManager.MODULE_DIR, fileContent, true);
    }
    
    public List<Module> getModsInCategory(final Category Category) {
        final List<Module> modList = new ArrayList<Module>();
        for (final Module mod : TabGUI.getSortedModuleArray()) {
            if (mod.getCategory() == Category) {
                modList.add(mod);
            }
        }
        return modList;
    }
    
    private void addModule(final Module module) {
        ModuleManager.modules.put(module.getName(), module);
    }
    
    public Module getModByName(final String theMod) {
        for (final Module mod : ModuleManager.modules.values()) {
            if (mod.getName().equalsIgnoreCase(theMod)) {
                return mod;
            }
        }
        return null;
    }
    
    public boolean hasDoubleValue(final Module mod) {
        for (final Setting s : Base.INSTANCE.getSettingManager().getSettingsByMod(mod)) {
            final Double doub = s.doubleValue();
            final Boolean bool = s.booleanValue();
            final String stri = s.getValStringForSaving();
            if (doub != null && s.getMax() != 0.0) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasStringVal(final Module mod) {
        try {
            for (final Setting s : Base.INSTANCE.getSettingManager().getSettingsByMod(mod)) {
                final Double doub = s.doubleValue();
                final Boolean bool = s.booleanValue();
                final String stri = s.getValStringForSaving();
                if (stri != null) {
                    return true;
                }
            }
            return false;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    public boolean hasBooleanVal(final Module mod) {
        for (final Setting s : Base.INSTANCE.getSettingManager().getSettingsByMod(mod)) {
            final Double doub = s.doubleValue();
            final Boolean bool = s.booleanValue();
            final String stri = s.getValStringForSaving();
            if (bool != null && s.getMax() == 0.0 && s.getValStringForSaving() == null) {
                return true;
            }
        }
        return false;
    }
    
    static {
        MODULE_DIR = FileUtils.getConfigFile("Mods");
        ModuleManager.modules = new HashMap<String, Module>();
    }
}
