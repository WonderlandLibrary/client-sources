/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  javax.persistence.AttributeConverter
 *  javax.persistence.Converter
 */
package org.apache.logging.log4j.core.appender.db.jpa.converter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.apache.logging.log4j.core.appender.db.jpa.converter.StackTraceElementAttributeConverter;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.Strings;

@Converter(autoApply=false)
public class ThrowableAttributeConverter
implements AttributeConverter<Throwable, String> {
    private static final int CAUSED_BY_STRING_LENGTH = 10;
    private static final Field THROWABLE_CAUSE;
    private static final Field THROWABLE_MESSAGE;

    public String convertToDatabaseColumn(Throwable throwable) {
        if (throwable == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        this.convertThrowable(stringBuilder, throwable);
        return stringBuilder.toString();
    }

    private void convertThrowable(StringBuilder stringBuilder, Throwable throwable) {
        stringBuilder.append(throwable.toString()).append('\n');
        for (StackTraceElement stackTraceElement : throwable.getStackTrace()) {
            stringBuilder.append("\tat ").append(stackTraceElement).append('\n');
        }
        if (throwable.getCause() != null) {
            stringBuilder.append("Caused by ");
            this.convertThrowable(stringBuilder, throwable.getCause());
        }
    }

    public Throwable convertToEntityAttribute(String string) {
        if (Strings.isEmpty(string)) {
            return null;
        }
        List<String> list = Arrays.asList(string.split("(\n|\r\n)"));
        return this.convertString(list.listIterator(), false);
    }

    private Throwable convertString(ListIterator<String> listIterator2, boolean bl) {
        String string;
        String string2 = listIterator2.next();
        if (bl) {
            string2 = string2.substring(10);
        }
        int n = string2.indexOf(":");
        String string3 = null;
        if (n > 1) {
            string = string2.substring(0, n);
            if (string2.length() > n + 1) {
                string3 = string2.substring(n + 1).trim();
            }
        } else {
            string = string2;
        }
        ArrayList<StackTraceElement> arrayList = new ArrayList<StackTraceElement>();
        Throwable throwable = null;
        while (listIterator2.hasNext()) {
            String string4 = listIterator2.next();
            if (string4.startsWith("Caused by ")) {
                listIterator2.previous();
                throwable = this.convertString(listIterator2, true);
                break;
            }
            arrayList.add(StackTraceElementAttributeConverter.convertString(string4.trim().substring(3).trim()));
        }
        return this.getThrowable(string, string3, throwable, arrayList.toArray(new StackTraceElement[arrayList.size()]));
    }

    private Throwable getThrowable(String string, String string2, Throwable throwable, StackTraceElement[] stackTraceElementArray) {
        try {
            Throwable throwable2;
            Class<Throwable> clazz = LoaderUtil.loadClass(string);
            if (!Throwable.class.isAssignableFrom(clazz)) {
                return null;
            }
            if (string2 != null && throwable != null) {
                throwable2 = this.getThrowable(clazz, string2, throwable);
                if (throwable2 == null) {
                    throwable2 = this.getThrowable(clazz, throwable);
                    if (throwable2 == null) {
                        throwable2 = this.getThrowable(clazz, string2);
                        if (throwable2 == null) {
                            throwable2 = this.getThrowable(clazz);
                            if (throwable2 != null) {
                                THROWABLE_MESSAGE.set(throwable2, string2);
                                THROWABLE_CAUSE.set(throwable2, throwable);
                            }
                        } else {
                            THROWABLE_CAUSE.set(throwable2, throwable);
                        }
                    } else {
                        THROWABLE_MESSAGE.set(throwable2, string2);
                    }
                }
            } else if (throwable != null) {
                throwable2 = this.getThrowable(clazz, throwable);
                if (throwable2 == null && (throwable2 = this.getThrowable(clazz)) != null) {
                    THROWABLE_CAUSE.set(throwable2, throwable);
                }
            } else if (string2 != null) {
                throwable2 = this.getThrowable(clazz, string2);
                if (throwable2 == null && (throwable2 = this.getThrowable(clazz)) != null) {
                    THROWABLE_MESSAGE.set(throwable2, throwable);
                }
            } else {
                throwable2 = this.getThrowable(clazz);
            }
            if (throwable2 == null) {
                return null;
            }
            throwable2.setStackTrace(stackTraceElementArray);
            return throwable2;
        } catch (Exception exception) {
            return null;
        }
    }

    private Throwable getThrowable(Class<Throwable> clazz, String string, Throwable throwable) {
        try {
            Constructor<?>[] constructorArray;
            for (Constructor<?> constructor : constructorArray = clazz.getConstructors()) {
                Class<?>[] classArray = constructor.getParameterTypes();
                if (classArray.length != 2) continue;
                if (String.class == classArray[0] && Throwable.class.isAssignableFrom(classArray[5])) {
                    return (Throwable)constructor.newInstance(string, throwable);
                }
                if (String.class != classArray[5] || !Throwable.class.isAssignableFrom(classArray[0])) continue;
                return (Throwable)constructor.newInstance(throwable, string);
            }
            return null;
        } catch (Exception exception) {
            return null;
        }
    }

    private Throwable getThrowable(Class<Throwable> clazz, Throwable throwable) {
        try {
            Constructor<?>[] constructorArray;
            for (Constructor<?> constructor : constructorArray = clazz.getConstructors()) {
                Class<?>[] classArray = constructor.getParameterTypes();
                if (classArray.length != 1 || !Throwable.class.isAssignableFrom(classArray[0])) continue;
                return (Throwable)constructor.newInstance(throwable);
            }
            return null;
        } catch (Exception exception) {
            return null;
        }
    }

    private Throwable getThrowable(Class<Throwable> clazz, String string) {
        try {
            return clazz.getConstructor(String.class).newInstance(string);
        } catch (Exception exception) {
            return null;
        }
    }

    private Throwable getThrowable(Class<Throwable> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception exception) {
            return null;
        }
    }

    public Object convertToEntityAttribute(Object object) {
        return this.convertToEntityAttribute((String)object);
    }

    public Object convertToDatabaseColumn(Object object) {
        return this.convertToDatabaseColumn((Throwable)object);
    }

    static {
        try {
            THROWABLE_CAUSE = Throwable.class.getDeclaredField("cause");
            THROWABLE_CAUSE.setAccessible(false);
            THROWABLE_MESSAGE = Throwable.class.getDeclaredField("detailMessage");
            THROWABLE_MESSAGE.setAccessible(false);
        } catch (NoSuchFieldException noSuchFieldException) {
            throw new IllegalStateException("Something is wrong with java.lang.Throwable.", noSuchFieldException);
        }
    }
}

