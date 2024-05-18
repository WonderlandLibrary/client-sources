package baritone.api.utils;

import java.util.function.*;
import java.nio.file.*;
import baritone.api.*;
import java.util.regex.*;
import java.util.*;
import java.io.*;

public class SettingsUtil
{
    private static final Path SETTINGS_PATH;
    private static final Pattern SETTING_PATTERN;
    private static final Map<Class<?>, SettingsUtil$SettingsIO> map;
    
    public SettingsUtil() {
        super();
    }
    
    private static boolean isComment(final String s) {
        return s.startsWith("#") || s.startsWith("//");
    }
    
    private static void forEachLine(Path bufferedReader, final Consumer<String> consumer) {
        bufferedReader = (Path)Files.newBufferedReader(bufferedReader);
        Throwable t = null;
        try {
            String line;
            while ((line = ((BufferedReader)bufferedReader).readLine()) != null) {
                if (!line.isEmpty() && !isComment(line)) {
                    consumer.accept(line);
                }
            }
            if (bufferedReader != null) {
                ((BufferedReader)bufferedReader).close();
            }
        }
        catch (Throwable t3) {
            final Throwable t2 = t3;
            t = t3;
            throw t2;
        }
        finally {
            if (bufferedReader != null) {
                if (t != null) {
                    try {
                        ((BufferedReader)bufferedReader).close();
                    }
                    catch (Throwable t4) {
                        t.addSuppressed(t4);
                    }
                }
                else {
                    ((BufferedReader)bufferedReader).close();
                }
            }
        }
    }
    
    public static void readAndApply(final Settings settings) {
        try {
            forEachLine(SettingsUtil.SETTINGS_PATH, SettingsUtil::lambda$readAndApply$0);
        }
        catch (Exception ex) {
            System.out.println("Exception while reading Baritone settings, some settings may be reset to default values!");
            ex.printStackTrace();
        }
    }
    
    public static synchronized void save(final Settings settings) {
        try {
            final BufferedWriter bufferedWriter = Files.newBufferedWriter(SettingsUtil.SETTINGS_PATH, new OpenOption[0]);
            Throwable t = null;
            try {
                final Iterator<Settings$Setting> iterator = modifiedSettings(settings).iterator();
                while (iterator.hasNext()) {
                    bufferedWriter.write(settingToString(iterator.next()) + "\n");
                }
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            }
            catch (Throwable t3) {
                final Throwable t2 = t3;
                t = t3;
                throw t2;
            }
            finally {
                if (bufferedWriter != null) {
                    if (t != null) {
                        try {
                            bufferedWriter.close();
                        }
                        catch (Throwable t4) {
                            t.addSuppressed(t4);
                        }
                    }
                    else {
                        bufferedWriter.close();
                    }
                }
            }
        }
        catch (Exception ex) {
            System.out.println("Exception thrown while saving Baritone settings!");
            ex.printStackTrace();
        }
    }
    
    public static List<Settings$Setting> modifiedSettings(final Settings settings) {
        final ArrayList<Settings$Setting<?>> list = (ArrayList<Settings$Setting<?>>)new ArrayList<Settings$Setting>();
        final Iterator<Settings$Setting<?>> iterator = settings.allSettings.iterator();
        while (iterator.hasNext()) {
            final Settings$Setting<?> settings$Setting;
            if ((settings$Setting = iterator.next()).value == null) {
                System.out.println("NULL SETTING?" + settings$Setting.getName());
            }
            else {
                if (settings$Setting.getName().equals("logger") || settings$Setting.value == settings$Setting.defaultValue) {
                    continue;
                }
                list.add(settings$Setting);
            }
        }
        return (List<Settings$Setting>)list;
    }
    
    public static String settingToString(final Settings$Setting settings$Setting) {
        if (settings$Setting.getName().equals("logger")) {
            return "logger";
        }
        final SettingsUtil$SettingsIO settingsUtil$SettingsIO;
        if ((settingsUtil$SettingsIO = SettingsUtil.map.get(settings$Setting.getValueClass())) == null) {
            throw new IllegalStateException("Missing " + settings$Setting.getValueClass() + " " + settings$Setting.getName());
        }
        return settings$Setting.getName() + " " + settingsUtil$SettingsIO.toString.apply(settings$Setting.value);
    }
    
    public static void parseAndApply(final Settings settings, final String s, final String s2) {
        final Settings$Setting<?> settings$Setting;
        if ((settings$Setting = settings.byLowerName.get(s)) == null) {
            throw new IllegalStateException("No setting by that name");
        }
        final Class<?> valueClass = settings$Setting.getValueClass();
        final SettingsUtil$SettingsIO settingsUtil$SettingsIO;
        final Object apply = (settingsUtil$SettingsIO = SettingsUtil.map.get(valueClass)).parser.apply(s2);
        if (!valueClass.isInstance(apply)) {
            throw new IllegalStateException(settingsUtil$SettingsIO + " parser returned incorrect type, expected " + valueClass + " got " + apply + " which is " + apply.getClass());
        }
        settings$Setting.value = apply;
    }
    
    private static void lambda$readAndApply$0(final Settings settings, final String s) {
        final Matcher matcher;
        if (!(matcher = SettingsUtil.SETTING_PATTERN.matcher(s)).matches()) {
            System.out.println("Invalid syntax in setting file: ".concat(String.valueOf(s)));
            return;
        }
        final String lowerCase = matcher.group("setting").toLowerCase();
        final String group = matcher.group("value");
        try {
            parseAndApply(settings, lowerCase, group);
        }
        catch (Exception ex) {
            System.out.println("Unable to parse line ".concat(String.valueOf(s)));
            ex.printStackTrace();
        }
    }
    
    static {
        SETTINGS_PATH = bib.z().w.toPath().resolve("baritone").resolve("settings.txt");
        SETTING_PATTERN = Pattern.compile("^(?<setting>[^ ]+) +(?<value>[^ ]+)");
        final HashMap<Class<?>, SettingsUtil$SettingsIO> hashMap = new HashMap<Class<?>, SettingsUtil$SettingsIO>();
        SettingsUtil$SettingsIO[] values;
        for (int length = (values = SettingsUtil$SettingsIO.values()).length, i = 0; i < length; ++i) {
            final SettingsUtil$SettingsIO settingsUtil$SettingsIO = values[i];
            hashMap.put(settingsUtil$SettingsIO.klass, settingsUtil$SettingsIO);
        }
        map = Collections.unmodifiableMap((Map<?, ?>)hashMap);
    }
}
