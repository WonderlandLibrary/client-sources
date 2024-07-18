package com.alan.clients.script;

import com.alan.clients.Client;
import com.alan.clients.component.impl.render.NotificationComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.other.WorldChangeEvent;
import com.alan.clients.script.api.*;
import com.alan.clients.script.util.ScriptClassFilter;
import com.alan.clients.util.Accessor;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.file.FileManager;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.openjdk.nashorn.api.scripting.ClassFilter;
import org.openjdk.nashorn.api.scripting.NashornScriptEngineFactory;

import javax.script.*;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Getter
public final class ScriptManager implements Accessor {

    public static final File SCRIPT_DIRECTORY = new File(FileManager.DIRECTORY, "scripts");

    private static final FilenameFilter SCRIPT_FILE_FILTER =
            (file, name) -> name.toLowerCase(Locale.ENGLISH).endsWith(".js");

    private static final ClassFilter SCRIPT_CLASS_FILTER = new ScriptClassFilter();

    private NashornScriptEngineFactory engineFactory;

    private Bindings globalBindings;

    private boolean securityMeasures = true;

    private final List<Script> scripts = new ArrayList<>();

    @EventLink
    public final Listener<WorldChangeEvent> onWorldChange = event -> loadBindings();

    public void init() {
        // Unload any scripts in case the client was reloaded
        this.unloadScripts();

        // Create a new nashorn engine factory
        this.engineFactory = new NashornScriptEngineFactory();

        this.loadBindings();

        try {
            this.loadScripts();
        } catch (final IOException ex) {
            ex.printStackTrace();
        }

        Client.INSTANCE.getEventBus().register(this);
    }

    public Script getScript(final String name) {
        return this.scripts.stream()
                .filter(module -> module.getName().equalsIgnoreCase(name))
                .findAny().orElse(null);
    }

    public void loadScripts() throws IOException {
        this.loadScriptFiles();

        this.scripts.removeIf(script -> {
            try {
                script.load();
                return false;
            } catch (final ScriptException ex) {
                ex.printStackTrace();

                ChatUtil.displayNoPrefix("");
                ChatUtil.display("Syntax error!");
                ChatUtil.displayNoPrefix("");

                ChatUtil.display(ex.getMessage());
                ChatUtil.displayNoPrefix("");
                return true;
            }
        });
    }

    public void loadBindings() {
        // Create new global bindings
        this.globalBindings = new SimpleBindings() {{
            this.put("mc", new MinecraftAPI());
            this.put("rise", new RiseAPI());
            this.put("player", new PlayerAPI());
            this.put("world", new WorldAPI());
            this.put("network", new NetworkAPI());
            this.put("render", new RenderAPI());
            this.put("packet", new PacketAPI());
            this.put("input", new GameSettingsAPI());
            this.put("web", new WebAPI());
        }};
    }

    public void reloadScripts() {
        System.out.println("Reloaded Scripts");
        this.unloadScripts();

        try {
            this.loadScriptFiles();
        } catch (final IOException ex) {
            ex.printStackTrace();
        }

        this.scripts.removeIf(script -> {
            try {
                script.reload();
                return false;
            } catch (final ScriptException ex) {
                ex.printStackTrace();
                ChatUtil.displayNoPrefix("");
                ChatUtil.display("Syntax error!");
                ChatUtil.displayNoPrefix("");

                ChatUtil.display(ex.getMessage());
                ChatUtil.displayNoPrefix("");
                return true;
            }
        });
    }

    public void unloadScripts() {
        this.scripts.forEach(script -> {
            try {
                script.unload();
            } catch (final ScriptException ex) {
                ex.printStackTrace();

                NotificationComponent.post(
                        "Script \"" + script.getName() + "\" unloaded incorrectly",
                        "More details have been printed in a stacktrace."
                );
            }
        });

        this.scripts.clear();
    }

    public void setSecurityMeasures(final boolean securityMeasures) {
        this.securityMeasures = securityMeasures;

        // Completely reload the system
        this.init();
    }

    private void loadScriptFiles() throws IOException {
        if (!SCRIPT_DIRECTORY.exists()) SCRIPT_DIRECTORY.mkdirs();

        for (final File file : Objects.requireNonNull(SCRIPT_DIRECTORY.listFiles(SCRIPT_FILE_FILTER))) {
            if (!file.canRead()) continue;

            final String name = this.getName(file);
            final boolean exists = this.scripts.stream().anyMatch(script -> script.getName().equals(name));

            if (!exists) {
                final Script script = this.parseScript(FileUtils.readFileToString(file, (Charset) null), file);
                this.scripts.add(script);
            }
        }
    }

    public ScriptEngine createEngine() {
        // Get a new ScriptEngine that uses our ClassFilter
        final ScriptEngine engine = this.isSecurityMeasures()
                ? this.engineFactory.getScriptEngine(SCRIPT_CLASS_FILTER)
                : this.engineFactory.getScriptEngine();

        // Give the engine our global bindings
        engine.setBindings(this.globalBindings, ScriptContext.GLOBAL_SCOPE);

        return engine;
    }

    public Script parseScript(final String code, final File file) {
        // Provide defaults for the optional metadata values
        String author = "Unknown author";
        String version = "Unknown version";
        String description = "No description provided";
        boolean noSecurity = false;

        // Parse some optional metadata about the script if present
        for (String line : code.split("\n")) {
            line = line.trim();

            if (line.startsWith("//@")) {
                final String unprefixedLine = line.substring(3).trim();

                final String key = unprefixedLine.toLowerCase(Locale.ENGLISH).split(" ")[0];
                final String value = unprefixedLine.substring(key.length()).trim();

                switch (key) {
                    case "author": {
                        author = value;
                        break;
                    }

                    case "version": {
                        version = value;
                        break;
                    }

                    case "description": {
                        description = value;
                        break;
                    }

                    case "nosecurity": {
                        noSecurity = true;
                        break;
                    }
                }
            }
        }

        if (noSecurity && this.securityMeasures) {
            // TODO: Properly handle this scenario
            throw new IllegalStateException("Script requires no security measures!");
        }

        // Create a new script out of the data we've gotten
        return new Script(this.getName(file), author, version, description, code, file);
    }

    private String getName(final File file) {
        return file.getName().replace(".js", "");
    }
}
