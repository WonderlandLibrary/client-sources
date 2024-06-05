package alphentus.config;

import alphentus.config.configs.*;

import java.util.ArrayList;

/**
 * @author avox | lmao
 * @since on 07.08.2020.
 */
public class ConfigManager {

    final ArrayList<Config> configs = new ArrayList<Config>();

    public ConfigManager() {
        addConfig(new HiveMC());
        addConfig(new MCCentral());
        addConfig(new NeruxVace());
        addConfig(new BlocksMC());
        addConfig(new Mineplex());
        addConfig(new Timolia());
        addConfig(new Intave());
        addConfig(new CubeCraft());
    }

    private void addConfig(Config config) {
        configs.add(config);
    }

    public ArrayList<Config> getConfigs() {
        return configs;
    }
}
