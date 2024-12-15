package com.alan.clients.ui.click.standard.screen.impl.communityscreen;

import com.alan.clients.script.ScriptManager;
import com.alan.clients.util.file.config.ConfigManager;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.File;

@AllArgsConstructor
@Getter
public enum Type {
    CONFIG("https://199.247.6.233/getconfig?id=", ConfigManager.CONFIG_DIRECTORY + File.separator, ".json"),
    SCRIPT("https://199.247.6.233/getscript?id=", ScriptManager.SCRIPT_DIRECTORY + File.separator, ".js");

    private final String url, path, type;
}
