package digital.rbq.addon;

import lombok.Getter;
import digital.rbq.addon.api.command.AddonCommandManager;
import digital.rbq.addon.api.module.AddonModuleManager;
import digital.rbq.addon.api.value.AddonValueManager;
import digital.rbq.config.Config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class LycorisAPI {
    @Getter
    private final AddonManager addonManager;
    @Getter
    private final AddonModuleManager moduleManager;
    @Getter
    private final AddonValueManager valueManager;
    @Getter
    private final AddonEventManager eventManager;
    @Getter
    private final AddonCommandManager commandManager;

    public static LycorisAPI FLUX_API;

    public LycorisAPI() {
        FLUX_API = this;

        this.loadAddons();

        this.eventManager = new AddonEventManager();
        this.addonManager = new AddonManager();
        this.valueManager = new AddonValueManager();
        this.addonManager.loadAllAPI();

        this.moduleManager = new AddonModuleManager();
        this.commandManager = new AddonCommandManager();
    }

    public void saveAddons() {
        try {
            final FileWriter fileWriter = new FileWriter(Config.ROOT_DIR + "/enabledAddons.txt");
            final BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (String enabledAddon : AddonManager.getEnabledAddonsName()) {
                bufferedWriter.write(enabledAddon + "\n");
            }
            bufferedWriter.close();
        } catch (Exception ex) {
        }
    }

    public boolean loadAddons() {
        try {
            AddonManager.getEnabledAddonsName().clear();
            String line = null;
            final FileReader fileReader = new FileReader(Config.ROOT_DIR + "/enabledAddons.txt");
            final BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                AddonManager.getEnabledAddonsName().add(line);
            }
            bufferedReader.close();
        } catch (Exception ex) {
            return false;
        }

        return true;
    }
}
