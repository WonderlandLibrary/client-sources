/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.plugins.convert;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Provider;
import java.security.Security;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.xml.bind.DatatypeConverter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.rolling.action.Duration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.convert.TypeConverter;
import org.apache.logging.log4j.core.config.plugins.convert.TypeConverterRegistry;
import org.apache.logging.log4j.core.util.CronExpression;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.LoaderUtil;

public final class TypeConverters {
    public static final String CATEGORY = "TypeConverter";
    private static final Logger LOGGER = StatusLogger.getLogger();

    public static <T> T convert(String string, Class<? extends T> clazz, Object object) {
        TypeConverter<?> typeConverter = TypeConverterRegistry.getInstance().findCompatibleConverter(clazz);
        if (string == null) {
            return (T)TypeConverters.parseDefaultValue(typeConverter, object);
        }
        try {
            return (T)typeConverter.convert(string);
        } catch (Exception exception) {
            LOGGER.warn("Error while converting string [{}] to type [{}]. Using default value [{}].", (Object)string, (Object)clazz, object, (Object)exception);
            return (T)TypeConverters.parseDefaultValue(typeConverter, object);
        }
    }

    private static <T> T parseDefaultValue(TypeConverter<T> typeConverter, Object object) {
        if (object == null) {
            return null;
        }
        if (!(object instanceof String)) {
            return (T)object;
        }
        try {
            return typeConverter.convert((String)object);
        } catch (Exception exception) {
            LOGGER.debug("Can't parse default value [{}] for type [{}].", object, (Object)typeConverter.getClass(), (Object)exception);
            return null;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Plugin(name="UUID", category="TypeConverter")
    public static class UuidConverter
    implements TypeConverter<UUID> {
        @Override
        public UUID convert(String string) throws Exception {
            return UUID.fromString(string);
        }

        @Override
        public Object convert(String string) throws Exception {
            return this.convert(string);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Plugin(name="URL", category="TypeConverter")
    public static class UrlConverter
    implements TypeConverter<URL> {
        @Override
        public URL convert(String string) throws MalformedURLException {
            return new URL(string);
        }

        @Override
        public Object convert(String string) throws Exception {
            return this.convert(string);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Plugin(name="URI", category="TypeConverter")
    public static class UriConverter
    implements TypeConverter<URI> {
        @Override
        public URI convert(String string) throws URISyntaxException {
            return new URI(string);
        }

        @Override
        public Object convert(String string) throws Exception {
            return this.convert(string);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Plugin(name="String", category="TypeConverter")
    public static class StringConverter
    implements TypeConverter<String> {
        @Override
        public String convert(String string) {
            return string;
        }

        @Override
        public Object convert(String string) throws Exception {
            return this.convert(string);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Plugin(name="Short", category="TypeConverter")
    public static class ShortConverter
    implements TypeConverter<Short> {
        @Override
        public Short convert(String string) {
            return Short.valueOf(string);
        }

        @Override
        public Object convert(String string) throws Exception {
            return this.convert(string);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Plugin(name="SecurityProvider", category="TypeConverter")
    public static class SecurityProviderConverter
    implements TypeConverter<Provider> {
        @Override
        public Provider convert(String string) {
            return Security.getProvider(string);
        }

        @Override
        public Object convert(String string) throws Exception {
            return this.convert(string);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Plugin(name="Pattern", category="TypeConverter")
    public static class PatternConverter
    implements TypeConverter<Pattern> {
        @Override
        public Pattern convert(String string) {
            return Pattern.compile(string);
        }

        @Override
        public Object convert(String string) throws Exception {
            return this.convert(string);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Plugin(name="Path", category="TypeConverter")
    public static class PathConverter
    implements TypeConverter<Path> {
        @Override
        public Path convert(String string) throws Exception {
            return Paths.get(string, new String[0]);
        }

        @Override
        public Object convert(String string) throws Exception {
            return this.convert(string);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Plugin(name="Long", category="TypeConverter")
    public static class LongConverter
    implements TypeConverter<Long> {
        @Override
        public Long convert(String string) {
            return Long.valueOf(string);
        }

        @Override
        public Object convert(String string) throws Exception {
            return this.convert(string);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Plugin(name="Level", category="TypeConverter")
    public static class LevelConverter
    implements TypeConverter<Level> {
        @Override
        public Level convert(String string) {
            return Level.valueOf(string);
        }

        @Override
        public Object convert(String string) throws Exception {
            return this.convert(string);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Plugin(name="Integer", category="TypeConverter")
    public static class IntegerConverter
    implements TypeConverter<Integer> {
        @Override
        public Integer convert(String string) {
            return Integer.valueOf(string);
        }

        @Override
        public Object convert(String string) throws Exception {
            return this.convert(string);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Plugin(name="InetAddress", category="TypeConverter")
    public static class InetAddressConverter
    implements TypeConverter<InetAddress> {
        @Override
        public InetAddress convert(String string) throws Exception {
            return InetAddress.getByName(string);
        }

        @Override
        public Object convert(String string) throws Exception {
            return this.convert(string);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Plugin(name="Float", category="TypeConverter")
    public static class FloatConverter
    implements TypeConverter<Float> {
        @Override
        public Float convert(String string) {
            return Float.valueOf(string);
        }

        @Override
        public Object convert(String string) throws Exception {
            return this.convert(string);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Plugin(name="File", category="TypeConverter")
    public static class FileConverter
    implements TypeConverter<File> {
        @Override
        public File convert(String string) {
            return new File(string);
        }

        @Override
        public Object convert(String string) throws Exception {
            return this.convert(string);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Plugin(name="Duration", category="TypeConverter")
    public static class DurationConverter
    implements TypeConverter<Duration> {
        @Override
        public Duration convert(String string) {
            return Duration.parse(string);
        }

        @Override
        public Object convert(String string) throws Exception {
            return this.convert(string);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Plugin(name="Double", category="TypeConverter")
    public static class DoubleConverter
    implements TypeConverter<Double> {
        @Override
        public Double convert(String string) {
            return Double.valueOf(string);
        }

        @Override
        public Object convert(String string) throws Exception {
            return this.convert(string);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Plugin(name="CronExpression", category="TypeConverter")
    public static class CronExpressionConverter
    implements TypeConverter<CronExpression> {
        @Override
        public CronExpression convert(String string) throws Exception {
            return new CronExpression(string);
        }

        @Override
        public Object convert(String string) throws Exception {
            return this.convert(string);
        }
    }

    @Plugin(name="Class", category="TypeConverter")
    public static class ClassConverter
    implements TypeConverter<Class<?>> {
        @Override
        public Class<?> convert(String string) throws ClassNotFoundException {
            switch (string.toLowerCase()) {
                case "boolean": {
                    return Boolean.TYPE;
                }
                case "byte": {
                    return Byte.TYPE;
                }
                case "char": {
                    return Character.TYPE;
                }
                case "double": {
                    return Double.TYPE;
                }
                case "float": {
                    return Float.TYPE;
                }
                case "int": {
                    return Integer.TYPE;
                }
                case "long": {
                    return Long.TYPE;
                }
                case "short": {
                    return Short.TYPE;
                }
                case "void": {
                    return Void.TYPE;
                }
            }
            return LoaderUtil.loadClass(string);
        }

        @Override
        public Object convert(String string) throws Exception {
            return this.convert(string);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Plugin(name="Charset", category="TypeConverter")
    public static class CharsetConverter
    implements TypeConverter<Charset> {
        @Override
        public Charset convert(String string) {
            return Charset.forName(string);
        }

        @Override
        public Object convert(String string) throws Exception {
            return this.convert(string);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Plugin(name="CharacterArray", category="TypeConverter")
    public static class CharArrayConverter
    implements TypeConverter<char[]> {
        @Override
        public char[] convert(String string) {
            return string.toCharArray();
        }

        @Override
        public Object convert(String string) throws Exception {
            return this.convert(string);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Plugin(name="Character", category="TypeConverter")
    public static class CharacterConverter
    implements TypeConverter<Character> {
        @Override
        public Character convert(String string) {
            if (string.length() != 1) {
                throw new IllegalArgumentException("Character string must be of length 1: " + string);
            }
            return Character.valueOf(string.toCharArray()[0]);
        }

        @Override
        public Object convert(String string) throws Exception {
            return this.convert(string);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Plugin(name="Byte", category="TypeConverter")
    public static class ByteConverter
    implements TypeConverter<Byte> {
        @Override
        public Byte convert(String string) {
            return Byte.valueOf(string);
        }

        @Override
        public Object convert(String string) throws Exception {
            return this.convert(string);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Plugin(name="ByteArray", category="TypeConverter")
    public static class ByteArrayConverter
    implements TypeConverter<byte[]> {
        private static final String PREFIX_0x = "0x";
        private static final String PREFIX_BASE64 = "Base64:";

        @Override
        public byte[] convert(String string) {
            byte[] byArray;
            if (string == null || string.isEmpty()) {
                byArray = new byte[]{};
            } else if (string.startsWith(PREFIX_BASE64)) {
                String string2 = string.substring(7);
                byArray = DatatypeConverter.parseBase64Binary(string2);
            } else if (string.startsWith(PREFIX_0x)) {
                String string3 = string.substring(2);
                byArray = DatatypeConverter.parseHexBinary(string3);
            } else {
                byArray = string.getBytes(Charset.defaultCharset());
            }
            return byArray;
        }

        @Override
        public Object convert(String string) throws Exception {
            return this.convert(string);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Plugin(name="Boolean", category="TypeConverter")
    public static class BooleanConverter
    implements TypeConverter<Boolean> {
        @Override
        public Boolean convert(String string) {
            return Boolean.valueOf(string);
        }

        @Override
        public Object convert(String string) throws Exception {
            return this.convert(string);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Plugin(name="BigInteger", category="TypeConverter")
    public static class BigIntegerConverter
    implements TypeConverter<BigInteger> {
        @Override
        public BigInteger convert(String string) {
            return new BigInteger(string);
        }

        @Override
        public Object convert(String string) throws Exception {
            return this.convert(string);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    @Plugin(name="BigDecimal", category="TypeConverter")
    public static class BigDecimalConverter
    implements TypeConverter<BigDecimal> {
        @Override
        public BigDecimal convert(String string) {
            return new BigDecimal(string);
        }

        @Override
        public Object convert(String string) throws Exception {
            return this.convert(string);
        }
    }
}

