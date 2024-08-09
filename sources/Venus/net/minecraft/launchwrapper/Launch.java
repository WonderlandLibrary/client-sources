/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.launchwrapper;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.NonOptionArgumentSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraft.launchwrapper.LogWrapper;
import org.apache.logging.log4j.Level;

public class Launch {
    private static final String DEFAULT_TWEAK = "net.minecraft.launchwrapper.VanillaTweaker";
    public static File minecraftHome;
    public static File assetsDir;
    public static Map<String, Object> blackboard;
    public static LaunchClassLoader classLoader;

    public static void main(String[] stringArray) {
        new Launch().launch(stringArray);
    }

    private Launch() {
        if (this.getClass().getClassLoader() instanceof URLClassLoader) {
            URLClassLoader uRLClassLoader = (URLClassLoader)this.getClass().getClassLoader();
            classLoader = new LaunchClassLoader(uRLClassLoader.getURLs());
        } else {
            classLoader = new LaunchClassLoader(this.getURLs());
        }
        blackboard = new HashMap<String, Object>();
        Thread.currentThread().setContextClassLoader(classLoader);
    }

    private URL[] getURLs() {
        String string = System.getProperty("java.class.path");
        String[] stringArray = string.split(File.pathSeparator);
        if (stringArray.length == 0) {
            stringArray = new String[]{""};
        }
        URL[] uRLArray = new URL[stringArray.length];
        for (int i = 0; i < stringArray.length; ++i) {
            try {
                URL uRL;
                uRLArray[i] = uRL = new File(stringArray[i]).toURI().toURL();
                continue;
            } catch (MalformedURLException malformedURLException) {
                // empty catch block
            }
        }
        return uRLArray;
    }

    private void launch(String[] stringArray) {
        OptionParser optionParser = new OptionParser();
        optionParser.allowsUnrecognizedOptions();
        ArgumentAcceptingOptionSpec<String> argumentAcceptingOptionSpec = optionParser.accepts("version", "The version we launched with").withRequiredArg();
        ArgumentAcceptingOptionSpec<File> argumentAcceptingOptionSpec2 = optionParser.accepts("gameDir", "Alternative game directory").withRequiredArg().ofType(File.class);
        ArgumentAcceptingOptionSpec<File> argumentAcceptingOptionSpec3 = optionParser.accepts("assetsDir", "Assets directory").withRequiredArg().ofType(File.class);
        ArgumentAcceptingOptionSpec<String> argumentAcceptingOptionSpec4 = optionParser.accepts("tweakClass", "Tweak class(es) to load").withRequiredArg().defaultsTo(DEFAULT_TWEAK, (String[])new String[0]);
        NonOptionArgumentSpec<String> nonOptionArgumentSpec = optionParser.nonOptions();
        OptionSet optionSet = optionParser.parse(stringArray);
        minecraftHome = optionSet.valueOf(argumentAcceptingOptionSpec2);
        assetsDir = optionSet.valueOf(argumentAcceptingOptionSpec3);
        String string = optionSet.valueOf(argumentAcceptingOptionSpec);
        ArrayList<String> arrayList = new ArrayList<String>(optionSet.valuesOf(argumentAcceptingOptionSpec4));
        ArrayList<String> arrayList2 = new ArrayList<String>();
        blackboard.put("TweakClasses", arrayList);
        blackboard.put("ArgumentList", arrayList2);
        HashSet<String> hashSet = new HashSet<String>();
        ArrayList<ITweaker> arrayList3 = new ArrayList<ITweaker>();
        try {
            Object object;
            Object object3;
            ArrayList<Object> arrayList4 = new ArrayList<Object>(arrayList.size() + 1);
            blackboard.put("Tweaks", arrayList4);
            Object object4 = null;
            do {
                object3 = arrayList.iterator();
                while (object3.hasNext()) {
                    String string2 = (String)object3.next();
                    if (hashSet.contains(string2)) {
                        LogWrapper.log(Level.WARN, "Tweak class name %s has already been visited -- skipping", string2);
                        object3.remove();
                        continue;
                    }
                    hashSet.add(string2);
                    LogWrapper.log(Level.INFO, "Loading tweak class name %s", string2);
                    classLoader.addClassLoaderExclusion(string2.substring(0, string2.lastIndexOf(46)));
                    object = (ITweaker)Class.forName(string2, true, classLoader).newInstance();
                    arrayList4.add(object);
                    object3.remove();
                    if (object4 != null) continue;
                    LogWrapper.log(Level.INFO, "Using primary tweak class name %s", string2);
                    object4 = object;
                }
                object3 = arrayList4.iterator();
                while (object3.hasNext()) {
                    ITweaker iTweaker = (ITweaker)object3.next();
                    LogWrapper.log(Level.INFO, "Calling tweak class %s", iTweaker.getClass().getName());
                    iTweaker.acceptOptions(optionSet.valuesOf(nonOptionArgumentSpec), minecraftHome, assetsDir, string);
                    iTweaker.injectIntoClassLoader(classLoader);
                    arrayList3.add(iTweaker);
                    object3.remove();
                }
            } while (!arrayList.isEmpty());
            for (ITweaker iTweaker : arrayList3) {
                arrayList2.addAll(Arrays.asList(iTweaker.getLaunchArguments()));
            }
            object3 = object4.getLaunchTarget();
            Class<?> clazz = Class.forName((String)object3, false, classLoader);
            object = clazz.getMethod("main", String[].class);
            LogWrapper.info("Launching wrapped minecraft {%s}", object3);
            ((Method)object).invoke(null, new Object[]{arrayList2.toArray(new String[arrayList2.size()])});
        } catch (Exception exception) {
            LogWrapper.log(Level.ERROR, exception, "Unable to launch", new Object[0]);
            System.exit(1);
        }
    }
}

