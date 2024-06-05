package digital.rbq.addon.api.module;

import lombok.Getter;
import digital.rbq.Lycoris;
import digital.rbq.addon.LycorisAPI;
import digital.rbq.addon.api.LycorisAddon;
import digital.rbq.addon.api.exception.NoSuchModuleException;
import digital.rbq.gui.clickgui.skeet.SkeetClickGUI;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleManager;
import digital.rbq.utility.ChatUtils;

import java.util.ArrayList;
import java.util.List;

public class AddonModuleManager {
    @Getter
    public List<AddonModule> addonModules = new ArrayList<>();
    private List<AddonModule> allModules;

    public AddonModuleManager() {
        ModuleManager.apiModules.clear();

        for (LycorisAddon fluxAddon : LycorisAPI.FLUX_API.getAddonManager().getEnabledLycorisAddonList()) {
            for (AddonModule module : fluxAddon.getModules()) {
                try {
                    ModuleManager.apiModules.add(module.module);
                    addonModules.add(module);
                    ChatUtils.sendOutputMessage(String.format("[Lycoris API] \2472[Module] \247a[%s] \2477Loaded!", module.getName()));
                } catch (Exception e) {
                    e.printStackTrace();
                    ChatUtils.sendErrorToPlayer(String.format("[Lycoris API] \2472[Module] \247a[%s] \2477Error: %s", module.getName(), e.getMessage()));
                }
            }
        }

        Lycoris.INSTANCE.setSkeetClickGUI(new SkeetClickGUI());
        Lycoris.INSTANCE.getClickGUI().setup();
    }
    
    public AddonModule getModule(String moduleName) throws NoSuchModuleException {
        Module module = Lycoris.INSTANCE.getModuleManager().getModuleByName(moduleName);
        if (module != null)
            return new AddonModule(module);
        else
            throw new NoSuchModuleException("Can't found module called " + moduleName);
    }

    
    public List<AddonModule> getAllModules() {
        if (allModules == null) {
            List<AddonModule> modules = new ArrayList<>();
            for (Module module : ModuleManager.getModList()) {
                modules.add(new AddonModule(module));
            }
            allModules = modules;
        }
        return allModules;
    }
}
