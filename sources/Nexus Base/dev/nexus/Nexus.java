package dev.nexus;

import dev.nexus.events.EventManager;
import dev.nexus.modules.ModuleManager;
import dev.nexus.utils.rotation.RotationManager;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StringUtils;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public final class Nexus {
    public static Nexus INSTANCE;
    private final ModuleManager moduleManager;
    private final EventManager eventManager;
    private final RotationManager rotationManager;
    private final String clientName = "Nexus";
    private final String version = "BETA";

    public Nexus() {
        INSTANCE = this;
        moduleManager = new ModuleManager();
        eventManager = new EventManager();
        rotationManager = new RotationManager();
        eventManager.subscribe(rotationManager);
        Display.setTitle(clientName + " | " + version);
    }
}