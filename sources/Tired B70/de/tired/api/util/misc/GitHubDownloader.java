package de.tired.api.util.misc;

import de.tired.interfaces.IHook;
import de.tired.tired.Tired;

import lombok.SneakyThrows;

public enum GitHubDownloader implements IHook {

    GIT_HUB_DOWNLOADER("https://github.com/FelixH2012/TiredConfigs");

    private final String mainPath = MC.mcDataDir + "/" + Tired.INSTANCE.CLIENT_NAME + "/onlineconfigs";


    @SneakyThrows
    public void download() {
    }

    final String LINK;

    GitHubDownloader(String link) {
        this.LINK = link;
    }

}
