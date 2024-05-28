package arsenic.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import arsenic.utils.interfaces.IConfig;
import arsenic.utils.interfaces.ISerializable;

import arsenic.main.Nexus;

public class ClientConfig implements IConfig<ISerializable> {


    private final File config;
    private final List<ISerializable> contents = new ArrayList<>();

    public ClientConfig(File config) {
        this.config = config;
        contents.add(Nexus.getNexus().getConfigManager());
        contents.add(Nexus.getInstance().getThemeManager());
    }

    @Override
    public void loadConfig() {
        System.out.println("loadingConfig");
        IConfig.super.loadConfig();
    }

    @Override
    public File getDirectory() {
        return config;
    }

    @Override
    public Collection<ISerializable> getContents() {
        return contents;
    }
}
