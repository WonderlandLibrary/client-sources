package me.napoleon.napoline.utils.client;

import net.minecraft.util.HttpUtil;

import java.io.IOException;
import java.net.URL;

import me.napoleon.napoline.junk.values.Value;
import me.napoleon.napoline.junk.values.type.Bool;
import me.napoleon.napoline.junk.values.type.Mode;
import me.napoleon.napoline.junk.values.type.Num;
import me.napoleon.napoline.manager.ModuleManager;
import me.napoleon.napoline.modules.Mod;

/**
 * @author: QianXia
 * @description: 云配置
 * @create: 2021/01/12-16:26
 */
public class CloudConfigsUtil {
    public static final String CONFIG_LIST_URL = "https://jewish-tricks.netlify.app/p/nap/d/Web/git/raw/master/Config/ConfigList";
    public static final String CONFIG_BASIC_URL = "https://jewish-tricks.netlify.app/p/nap/d/Web/git/raw/master/Config/Configs/";

    public static String[] getAllConfigsNameList() throws IOException{
        String list = HttpUtil.get(new URL(CONFIG_LIST_URL));
        return list.split("\r");
    }

    public static boolean loadCloudConfig(String configName) throws IOException {
        String[] allConfigs = CloudConfigsUtil.getAllConfigsNameList();
        for (String config : allConfigs) {
            if (!config.equalsIgnoreCase(configName)) {
                continue;
            }

            String configContent = HttpUtil.get(new URL(CONFIG_BASIC_URL + config));
            // Enabled, Values
            String[] splitConfig = configContent.split("ILOVESUPERSKIDDERILOVESUPERSKIDDERILOVESUPERSKIDDER");

            for (Mod v : ModuleManager.modList) {
                if(v.getState()){
                    v.setStage(false);
                }
            }

            // Enabled
            String[] enabled = splitConfig[0].split(",");
            for (String v : enabled) {
                Mod m = ModuleManager.getModsByName(v);
                if (m == null) {
                    continue;
                }
                m.setStage(true);
            }

            // Values
            String[] lines = splitConfig[1].split(",");
            for (String line : lines) {
                String name = line.split(":")[0];
                String values = line.split(":")[1];
                Mod m = ModuleManager.getModsByName(name);
                if (m == null) {
                    continue;
                }
                for (Value v : m.getValues()) {
                    if (!v.getName().equalsIgnoreCase(values)) {
                        continue;
                    }

                    if (v instanceof Bool) {
                        v.setValue(Boolean.parseBoolean(line.split(":")[2]));
                    } else if (v instanceof Num) {
                        v.setValue(Double.parseDouble(line.split(":")[2]));
                    } else if (v instanceof Mode) {
                        ((Mode) v).setMode(line.split(":")[2]);
                    }

                }
            }
            return true;
        }
        return false;
    }
}
