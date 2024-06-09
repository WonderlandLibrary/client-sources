package org.spongepowered.asm.mixin.transformer.ext.extensions;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.util.*;
import org.apache.commons.io.*;
import java.io.*;
import java.util.regex.*;
import org.spongepowered.asm.mixin.transformer.ext.*;
import org.spongepowered.asm.util.perf.*;
import org.apache.logging.log4j.*;

public class ExtensionClassExporter implements IExtension
{
    private static final String DECOMPILER_CLASS = "org.spongepowered.asm.mixin.transformer.debug.RuntimeDecompiler";
    private static final String EXPORT_CLASS_DIR = "class";
    private static final String EXPORT_JAVA_DIR = "java";
    private static final Logger logger;
    private final File classExportDir;
    private final IDecompiler decompiler;
    
    public ExtensionClassExporter(final MixinEnvironment mixinEnvironment) {
        super();
        this.classExportDir = new File(Constants.DEBUG_OUTPUT_DIR, "class");
        this.decompiler = this.initDecompiler(mixinEnvironment, new File(Constants.DEBUG_OUTPUT_DIR, "java"));
        try {
            FileUtils.deleteDirectory(this.classExportDir);
        }
        catch (IOException ex) {
            ExtensionClassExporter.logger.warn("Error cleaning class output directory: {}", new Object[] { ex.getMessage() });
        }
    }
    
    public boolean isDecompilerActive() {
        return this.decompiler != null;
    }
    
    private IDecompiler initDecompiler(final MixinEnvironment mixinEnvironment, final File file) {
        if (!mixinEnvironment.getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE)) {
            return null;
        }
        try {
            final boolean option = mixinEnvironment.getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE_THREADED);
            ExtensionClassExporter.logger.info("Attempting to load Fernflower decompiler{}", new Object[] { option ? " (Threaded mode)" : "" });
            final IDecompiler decompiler = (IDecompiler)Class.forName("org.spongepowered.asm.mixin.transformer.debug.RuntimeDecompiler" + (option ? "Async" : "")).getDeclaredConstructor(File.class).newInstance(file);
            ExtensionClassExporter.logger.info("Fernflower decompiler was successfully initialised, exported classes will be decompiled{}", new Object[] { option ? " in a separate thread" : "" });
            return decompiler;
        }
        catch (Throwable t) {
            ExtensionClassExporter.logger.info("Fernflower could not be loaded, exported classes will not be decompiled. {}: {}", new Object[] { t.getClass().getSimpleName(), t.getMessage() });
            return null;
        }
    }
    
    private String prepareFilter(String string) {
        string = "^\\Q" + string.replace("**", "\u0081").replace("*", "\u0082").replace("?", "\u0083") + "\\E$";
        return string.replace("\u0081", "\\E.*\\Q").replace("\u0082", "\\E[^\\.]+\\Q").replace("\u0083", "\\E.\\Q").replace("\\Q\\E", "");
    }
    
    private boolean applyFilter(final String s, final String s2) {
        return Pattern.compile(this.prepareFilter(s), 2).matcher(s2).matches();
    }
    
    @Override
    public boolean checkActive(final MixinEnvironment mixinEnvironment) {
        return true;
    }
    
    @Override
    public void preApply(final ITargetClassContext targetClassContext) {
    }
    
    @Override
    public void postApply(final ITargetClassContext targetClassContext) {
    }
    
    @Override
    public void export(final MixinEnvironment mixinEnvironment, final String s, final boolean b, final byte[] array) {
        if (b || mixinEnvironment.getOption(MixinEnvironment.Option.DEBUG_EXPORT)) {
            final String optionValue = mixinEnvironment.getOptionValue(MixinEnvironment.Option.DEBUG_EXPORT_FILTER);
            if (b || optionValue == null || this.applyFilter(optionValue, s)) {
                final Profiler.Section begin = MixinEnvironment.getProfiler().begin("debug.export");
                final File dumpClass = this.dumpClass(s.replace('.', '/'), array);
                if (this.decompiler != null) {
                    this.decompiler.decompile(dumpClass);
                }
                begin.end();
            }
        }
    }
    
    public File dumpClass(final String s, final byte[] data) {
        final File file = new File(this.classExportDir, s + ".class");
        try {
            FileUtils.writeByteArrayToFile(file, data);
        }
        catch (IOException ex) {}
        return file;
    }
    
    static {
        logger = LogManager.getLogger("mixin");
    }
}
