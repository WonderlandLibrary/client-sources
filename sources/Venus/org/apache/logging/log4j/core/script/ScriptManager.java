/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.script;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.script.AbstractScript;
import org.apache.logging.log4j.core.script.ScriptFile;
import org.apache.logging.log4j.core.util.FileWatcher;
import org.apache.logging.log4j.core.util.WatchManager;
import org.apache.logging.log4j.status.StatusLogger;

public class ScriptManager
implements FileWatcher,
Serializable {
    private static final long serialVersionUID = -2534169384971965196L;
    private static final String KEY_THREADING = "THREADING";
    private static final Logger logger = StatusLogger.getLogger();
    private final Configuration configuration;
    private final ScriptEngineManager manager = new ScriptEngineManager();
    private final ConcurrentMap<String, ScriptRunner> scriptRunners = new ConcurrentHashMap<String, ScriptRunner>();
    private final String languages;
    private final WatchManager watchManager;

    public ScriptManager(Configuration configuration, WatchManager watchManager) {
        this.configuration = configuration;
        this.watchManager = watchManager;
        List<ScriptEngineFactory> list = this.manager.getEngineFactories();
        if (logger.isDebugEnabled()) {
            StringBuilder stringBuilder = new StringBuilder();
            logger.debug("Installed script engines");
            for (ScriptEngineFactory scriptEngineFactory : list) {
                String string = (String)scriptEngineFactory.getParameter(KEY_THREADING);
                if (string == null) {
                    string = "Not Thread Safe";
                }
                StringBuilder stringBuilder2 = new StringBuilder();
                for (String string2 : scriptEngineFactory.getNames()) {
                    if (stringBuilder2.length() > 0) {
                        stringBuilder2.append(", ");
                    }
                    stringBuilder2.append(string2);
                }
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append((CharSequence)stringBuilder2);
                boolean bl = scriptEngineFactory.getScriptEngine() instanceof Compilable;
                logger.debug(scriptEngineFactory.getEngineName() + " Version: " + scriptEngineFactory.getEngineVersion() + ", Language: " + scriptEngineFactory.getLanguageName() + ", Threading: " + string + ", Compile: " + bl + ", Names: {" + stringBuilder2.toString() + "}");
            }
            this.languages = stringBuilder.toString();
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (ScriptEngineFactory scriptEngineFactory : list) {
                for (String string : scriptEngineFactory.getNames()) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append(string);
                }
            }
            this.languages = stringBuilder.toString();
        }
    }

    public void addScript(AbstractScript abstractScript) {
        ScriptEngine scriptEngine = this.manager.getEngineByName(abstractScript.getLanguage());
        if (scriptEngine == null) {
            logger.error("No ScriptEngine found for language " + abstractScript.getLanguage() + ". Available languages are: " + this.languages);
            return;
        }
        if (scriptEngine.getFactory().getParameter(KEY_THREADING) == null) {
            this.scriptRunners.put(abstractScript.getName(), new ThreadLocalScriptRunner(this, abstractScript));
        } else {
            this.scriptRunners.put(abstractScript.getName(), new MainScriptRunner(this, scriptEngine, abstractScript));
        }
        if (abstractScript instanceof ScriptFile) {
            ScriptFile scriptFile = (ScriptFile)abstractScript;
            Path path = scriptFile.getPath();
            if (scriptFile.isWatched() && path != null) {
                this.watchManager.watchFile(path.toFile(), this);
            }
        }
    }

    public Bindings createBindings(AbstractScript abstractScript) {
        return this.getScriptRunner(abstractScript).createBindings();
    }

    public AbstractScript getScript(String string) {
        ScriptRunner scriptRunner = (ScriptRunner)this.scriptRunners.get(string);
        return scriptRunner != null ? scriptRunner.getScript() : null;
    }

    @Override
    public void fileModified(File file) {
        ScriptRunner scriptRunner = (ScriptRunner)this.scriptRunners.get(file.toString());
        if (scriptRunner == null) {
            logger.info("{} is not a running script");
            return;
        }
        ScriptEngine scriptEngine = scriptRunner.getScriptEngine();
        AbstractScript abstractScript = scriptRunner.getScript();
        if (scriptEngine.getFactory().getParameter(KEY_THREADING) == null) {
            this.scriptRunners.put(abstractScript.getName(), new ThreadLocalScriptRunner(this, abstractScript));
        } else {
            this.scriptRunners.put(abstractScript.getName(), new MainScriptRunner(this, scriptEngine, abstractScript));
        }
    }

    public Object execute(String string, Bindings bindings) {
        ScriptRunner scriptRunner = (ScriptRunner)this.scriptRunners.get(string);
        if (scriptRunner == null) {
            logger.warn("No script named {} could be found");
            return null;
        }
        return AccessController.doPrivileged(new PrivilegedAction<Object>(this, scriptRunner, bindings){
            final ScriptRunner val$scriptRunner;
            final Bindings val$bindings;
            final ScriptManager this$0;
            {
                this.this$0 = scriptManager;
                this.val$scriptRunner = scriptRunner;
                this.val$bindings = bindings;
            }

            @Override
            public Object run() {
                return this.val$scriptRunner.execute(this.val$bindings);
            }
        });
    }

    private ScriptRunner getScriptRunner(AbstractScript abstractScript) {
        return (ScriptRunner)this.scriptRunners.get(abstractScript.getName());
    }

    static Configuration access$000(ScriptManager scriptManager) {
        return scriptManager.configuration;
    }

    static Logger access$100() {
        return logger;
    }

    static ScriptEngineManager access$400(ScriptManager scriptManager) {
        return scriptManager.manager;
    }

    private class ThreadLocalScriptRunner
    extends AbstractScriptRunner {
        private final AbstractScript script;
        private final ThreadLocal<MainScriptRunner> runners;
        final ScriptManager this$0;

        public ThreadLocalScriptRunner(ScriptManager scriptManager, AbstractScript abstractScript) {
            this.this$0 = scriptManager;
            super(scriptManager, null);
            this.runners = new ThreadLocal<MainScriptRunner>(this){
                final ThreadLocalScriptRunner this$1;
                {
                    this.this$1 = threadLocalScriptRunner;
                }

                @Override
                protected MainScriptRunner initialValue() {
                    ScriptEngine scriptEngine = ScriptManager.access$400(this.this$1.this$0).getEngineByName(ThreadLocalScriptRunner.access$300(this.this$1).getLanguage());
                    return new MainScriptRunner(this.this$1.this$0, scriptEngine, ThreadLocalScriptRunner.access$300(this.this$1));
                }

                @Override
                protected Object initialValue() {
                    return this.initialValue();
                }
            };
            this.script = abstractScript;
        }

        @Override
        public Object execute(Bindings bindings) {
            return this.runners.get().execute(bindings);
        }

        @Override
        public AbstractScript getScript() {
            return this.script;
        }

        @Override
        public ScriptEngine getScriptEngine() {
            return this.runners.get().getScriptEngine();
        }

        static AbstractScript access$300(ThreadLocalScriptRunner threadLocalScriptRunner) {
            return threadLocalScriptRunner.script;
        }
    }

    private class MainScriptRunner
    extends AbstractScriptRunner {
        private final AbstractScript script;
        private final CompiledScript compiledScript;
        private final ScriptEngine scriptEngine;
        final ScriptManager this$0;

        public MainScriptRunner(ScriptManager scriptManager, ScriptEngine scriptEngine, AbstractScript abstractScript) {
            this.this$0 = scriptManager;
            super(scriptManager, null);
            this.script = abstractScript;
            this.scriptEngine = scriptEngine;
            CompiledScript compiledScript = null;
            if (scriptEngine instanceof Compilable) {
                ScriptManager.access$100().debug("Script {} is compilable", (Object)abstractScript.getName());
                compiledScript = AccessController.doPrivileged(new PrivilegedAction<CompiledScript>(this, scriptManager, scriptEngine, abstractScript){
                    final ScriptManager val$this$0;
                    final ScriptEngine val$scriptEngine;
                    final AbstractScript val$script;
                    final MainScriptRunner this$1;
                    {
                        this.this$1 = mainScriptRunner;
                        this.val$this$0 = scriptManager;
                        this.val$scriptEngine = scriptEngine;
                        this.val$script = abstractScript;
                    }

                    @Override
                    public CompiledScript run() {
                        try {
                            return ((Compilable)((Object)this.val$scriptEngine)).compile(this.val$script.getScriptText());
                        } catch (Throwable throwable) {
                            ScriptManager.access$100().warn("Error compiling script", throwable);
                            return null;
                        }
                    }

                    @Override
                    public Object run() {
                        return this.run();
                    }
                });
            }
            this.compiledScript = compiledScript;
        }

        @Override
        public ScriptEngine getScriptEngine() {
            return this.scriptEngine;
        }

        @Override
        public Object execute(Bindings bindings) {
            if (this.compiledScript != null) {
                try {
                    return this.compiledScript.eval(bindings);
                } catch (ScriptException scriptException) {
                    ScriptManager.access$100().error("Error running script " + this.script.getName(), (Throwable)scriptException);
                    return null;
                }
            }
            try {
                return this.scriptEngine.eval(this.script.getScriptText(), bindings);
            } catch (ScriptException scriptException) {
                ScriptManager.access$100().error("Error running script " + this.script.getName(), (Throwable)scriptException);
                return null;
            }
        }

        @Override
        public AbstractScript getScript() {
            return this.script;
        }
    }

    private static interface ScriptRunner {
        public Bindings createBindings();

        public Object execute(Bindings var1);

        public AbstractScript getScript();

        public ScriptEngine getScriptEngine();
    }

    private abstract class AbstractScriptRunner
    implements ScriptRunner {
        private static final String KEY_STATUS_LOGGER = "statusLogger";
        private static final String KEY_CONFIGURATION = "configuration";
        final ScriptManager this$0;

        private AbstractScriptRunner(ScriptManager scriptManager) {
            this.this$0 = scriptManager;
        }

        @Override
        public Bindings createBindings() {
            SimpleBindings simpleBindings = new SimpleBindings();
            simpleBindings.put(KEY_CONFIGURATION, (Object)ScriptManager.access$000(this.this$0));
            simpleBindings.put(KEY_STATUS_LOGGER, (Object)ScriptManager.access$100());
            return simpleBindings;
        }

        AbstractScriptRunner(ScriptManager scriptManager, 1 var2_2) {
            this(scriptManager);
        }
    }
}

