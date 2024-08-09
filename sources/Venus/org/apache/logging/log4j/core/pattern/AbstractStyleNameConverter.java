/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.pattern.AnsiEscape;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternFormatter;
import org.apache.logging.log4j.core.pattern.PatternParser;
import org.apache.logging.log4j.util.PerformanceSensitive;

public abstract class AbstractStyleNameConverter
extends LogEventPatternConverter {
    private final List<PatternFormatter> formatters;
    private final String style;

    protected AbstractStyleNameConverter(String string, List<PatternFormatter> list, String string2) {
        super(string, "style");
        this.formatters = list;
        this.style = string2;
    }

    protected static <T extends AbstractStyleNameConverter> T newInstance(Class<T> clazz, String string, Configuration configuration, String[] stringArray) {
        List<PatternFormatter> list = AbstractStyleNameConverter.toPatternFormatterList(configuration, stringArray);
        if (list == null) {
            return null;
        }
        try {
            Constructor<T> constructor = clazz.getConstructor(List.class, String.class);
            return (T)((AbstractStyleNameConverter)constructor.newInstance(list, AnsiEscape.createSequence(string)));
        } catch (SecurityException securityException) {
            LOGGER.error(securityException.toString(), (Throwable)securityException);
        } catch (NoSuchMethodException noSuchMethodException) {
            LOGGER.error(noSuchMethodException.toString(), (Throwable)noSuchMethodException);
        } catch (IllegalArgumentException illegalArgumentException) {
            LOGGER.error(illegalArgumentException.toString(), (Throwable)illegalArgumentException);
        } catch (InstantiationException instantiationException) {
            LOGGER.error(instantiationException.toString(), (Throwable)instantiationException);
        } catch (IllegalAccessException illegalAccessException) {
            LOGGER.error(illegalAccessException.toString(), (Throwable)illegalAccessException);
        } catch (InvocationTargetException invocationTargetException) {
            LOGGER.error(invocationTargetException.toString(), (Throwable)invocationTargetException);
        }
        return null;
    }

    private static List<PatternFormatter> toPatternFormatterList(Configuration configuration, String[] stringArray) {
        if (stringArray.length == 0 || stringArray[0] == null) {
            LOGGER.error("No pattern supplied on style for config=" + configuration);
            return null;
        }
        PatternParser patternParser = PatternLayout.createPatternParser(configuration);
        if (patternParser == null) {
            LOGGER.error("No PatternParser created for config=" + configuration + ", options=" + Arrays.toString(stringArray));
            return null;
        }
        return patternParser.parse(stringArray[0]);
    }

    @Override
    @PerformanceSensitive(value={"allocation"})
    public void format(LogEvent logEvent, StringBuilder stringBuilder) {
        int n = stringBuilder.length();
        for (int i = 0; i < this.formatters.size(); ++i) {
            PatternFormatter patternFormatter = this.formatters.get(i);
            patternFormatter.format(logEvent, stringBuilder);
        }
        if (stringBuilder.length() > n) {
            stringBuilder.insert(n, this.style);
            stringBuilder.append(AnsiEscape.getDefaultStyle());
        }
    }

    @Plugin(name="yellow", category="Converter")
    @ConverterKeys(value={"yellow"})
    public static final class Yellow
    extends AbstractStyleNameConverter {
        protected static final String NAME = "yellow";

        public Yellow(List<PatternFormatter> list, String string) {
            super(NAME, list, string);
        }

        public static Yellow newInstance(Configuration configuration, String[] stringArray) {
            return Yellow.newInstance(Yellow.class, NAME, configuration, stringArray);
        }
    }

    @Plugin(name="white", category="Converter")
    @ConverterKeys(value={"white"})
    public static final class White
    extends AbstractStyleNameConverter {
        protected static final String NAME = "white";

        public White(List<PatternFormatter> list, String string) {
            super(NAME, list, string);
        }

        public static White newInstance(Configuration configuration, String[] stringArray) {
            return White.newInstance(White.class, NAME, configuration, stringArray);
        }
    }

    @Plugin(name="red", category="Converter")
    @ConverterKeys(value={"red"})
    public static final class Red
    extends AbstractStyleNameConverter {
        protected static final String NAME = "red";

        public Red(List<PatternFormatter> list, String string) {
            super(NAME, list, string);
        }

        public static Red newInstance(Configuration configuration, String[] stringArray) {
            return Red.newInstance(Red.class, NAME, configuration, stringArray);
        }
    }

    @Plugin(name="magenta", category="Converter")
    @ConverterKeys(value={"magenta"})
    public static final class Magenta
    extends AbstractStyleNameConverter {
        protected static final String NAME = "magenta";

        public Magenta(List<PatternFormatter> list, String string) {
            super(NAME, list, string);
        }

        public static Magenta newInstance(Configuration configuration, String[] stringArray) {
            return Magenta.newInstance(Magenta.class, NAME, configuration, stringArray);
        }
    }

    @Plugin(name="green", category="Converter")
    @ConverterKeys(value={"green"})
    public static final class Green
    extends AbstractStyleNameConverter {
        protected static final String NAME = "green";

        public Green(List<PatternFormatter> list, String string) {
            super(NAME, list, string);
        }

        public static Green newInstance(Configuration configuration, String[] stringArray) {
            return Green.newInstance(Green.class, NAME, configuration, stringArray);
        }
    }

    @Plugin(name="cyan", category="Converter")
    @ConverterKeys(value={"cyan"})
    public static final class Cyan
    extends AbstractStyleNameConverter {
        protected static final String NAME = "cyan";

        public Cyan(List<PatternFormatter> list, String string) {
            super(NAME, list, string);
        }

        public static Cyan newInstance(Configuration configuration, String[] stringArray) {
            return Cyan.newInstance(Cyan.class, NAME, configuration, stringArray);
        }
    }

    @Plugin(name="blue", category="Converter")
    @ConverterKeys(value={"blue"})
    public static final class Blue
    extends AbstractStyleNameConverter {
        protected static final String NAME = "blue";

        public Blue(List<PatternFormatter> list, String string) {
            super(NAME, list, string);
        }

        public static Blue newInstance(Configuration configuration, String[] stringArray) {
            return Blue.newInstance(Blue.class, NAME, configuration, stringArray);
        }
    }

    @Plugin(name="black", category="Converter")
    @ConverterKeys(value={"black"})
    public static final class Black
    extends AbstractStyleNameConverter {
        protected static final String NAME = "black";

        public Black(List<PatternFormatter> list, String string) {
            super(NAME, list, string);
        }

        public static Black newInstance(Configuration configuration, String[] stringArray) {
            return Black.newInstance(Black.class, NAME, configuration, stringArray);
        }
    }
}

