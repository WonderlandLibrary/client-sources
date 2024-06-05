package tk.rektsky.Utils;

import org.yaml.snakeyaml.*;
import tk.rektsky.Files.*;
import tk.rektsky.*;
import tk.rektsky.Module.Settings.*;
import tk.rektsky.Module.*;
import java.util.*;

public class YamlUtil
{
    public static ArrayList<ConfiguredModule> getModuleSetting() {
        final ArrayList<ConfiguredModule> output = new ArrayList<ConfiguredModule>();
        final Map<String, Object> modules = (Map<String, Object>)new Yaml().load(FileManager.getFile("settings.yml"));
        for (final String m : modules.keySet()) {
            try {
                output.add(new ConfiguredModule(m, modules.get(m)));
            }
            catch (InvalidConfigurationError invalidConfigurationError) {
                RektLogger.error("Could not get Configuration for ", m, "!  Skipping...");
            }
        }
        return output;
    }
    
    public static class ConfiguredModule
    {
        private int keybind;
        private Module module;
        private Map<Setting, Object> settings;
        private boolean enabled;
        
        public ConfiguredModule(final String moduleName, final Map<String, Object> module) throws InvalidConfigurationError {
            this.settings = new HashMap<Setting, Object>();
            if (!(module.get("key") instanceof Integer) || !(module.get("enabled") instanceof Boolean) || !(module.get("settings") instanceof Map)) {
                throw new InvalidConfigurationError();
            }
            this.module = ModulesManager.getModuleByName(moduleName);
            if (this.module == null) {
                throw new InvalidConfigurationError();
            }
            this.keybind = module.get("key");
            this.enabled = module.get("enabled");
            final Map<String, Object> setting = module.get("settings");
            for (final String key : setting.keySet()) {
                for (final Setting s : this.module.settings) {
                    if (key.equals(s.name)) {
                        s.setValue(setting.get(key));
                        break;
                    }
                }
            }
        }
        
        public int getKeybind() {
            return this.keybind;
        }
        
        public Module getModule() {
            return this.module;
        }
        
        public Map<Setting, Object> getSettings() {
            return this.settings;
        }
        
        public boolean isEnabled() {
            return this.enabled;
        }
    }
}
