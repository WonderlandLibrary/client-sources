package wtf.diablo.client.scripting.impl.management;

import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.scripting.api.Script;
import wtf.diablo.client.scripting.api.ScriptRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

public final class ScriptRepositoryImpl implements ScriptRepository {
    private final File file;
    private final Collection<Script> scriptCollection;

    public ScriptRepositoryImpl(final File file) {
        this.scriptCollection = new ArrayList<>();
        this.file = file;
    }


    @Override
    public void begin() {
        final File scriptingDirectory = Diablo.getInstance().getScriptingDirectory();
        final Stream<File> scripts = Arrays.stream(Objects.requireNonNull(scriptingDirectory.listFiles())).filter(file -> file.getName().endsWith(".js"));

        scripts.forEach(file -> {
            final Script script = new ScriptImpl(file);
            script.begin();
            this.scriptCollection.add(script);
        });
    }

    @Override
    public void reloadSavedScripts() {
        this.scriptCollection.forEach(Script::stop);
        this.scriptCollection.clear();
        this.begin();
    }
}