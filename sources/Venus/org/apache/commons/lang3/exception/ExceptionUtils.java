/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

public class ExceptionUtils {
    static final String WRAPPED_MARKER = " [wrapped] ";
    private static final String[] CAUSE_METHOD_NAMES = new String[]{"getCause", "getNextException", "getTargetException", "getException", "getSourceException", "getRootCause", "getCausedByException", "getNested", "getLinkedException", "getNestedException", "getLinkedCause", "getThrowable"};

    @Deprecated
    public static String[] getDefaultCauseMethodNames() {
        return ArrayUtils.clone(CAUSE_METHOD_NAMES);
    }

    @Deprecated
    public static Throwable getCause(Throwable throwable) {
        return ExceptionUtils.getCause(throwable, null);
    }

    @Deprecated
    public static Throwable getCause(Throwable throwable, String[] stringArray) {
        if (throwable == null) {
            return null;
        }
        if (stringArray == null) {
            Throwable object = throwable.getCause();
            if (object != null) {
                return object;
            }
            stringArray = CAUSE_METHOD_NAMES;
        }
        for (String string : stringArray) {
            Throwable throwable2;
            if (string == null || (throwable2 = ExceptionUtils.getCauseUsingMethodName(throwable, string)) == null) continue;
            return throwable2;
        }
        return null;
    }

    public static Throwable getRootCause(Throwable throwable) {
        List<Throwable> list = ExceptionUtils.getThrowableList(throwable);
        return list.size() < 2 ? null : list.get(list.size() - 1);
    }

    private static Throwable getCauseUsingMethodName(Throwable throwable, String string) {
        Method method = null;
        try {
            method = throwable.getClass().getMethod(string, new Class[0]);
        } catch (NoSuchMethodException noSuchMethodException) {
        } catch (SecurityException securityException) {
            // empty catch block
        }
        if (method != null && Throwable.class.isAssignableFrom(method.getReturnType())) {
            try {
                return (Throwable)method.invoke(throwable, new Object[0]);
            } catch (IllegalAccessException illegalAccessException) {
            } catch (IllegalArgumentException illegalArgumentException) {
            } catch (InvocationTargetException invocationTargetException) {
                // empty catch block
            }
        }
        return null;
    }

    public static int getThrowableCount(Throwable throwable) {
        return ExceptionUtils.getThrowableList(throwable).size();
    }

    public static Throwable[] getThrowables(Throwable throwable) {
        List<Throwable> list = ExceptionUtils.getThrowableList(throwable);
        return list.toArray(new Throwable[list.size()]);
    }

    public static List<Throwable> getThrowableList(Throwable throwable) {
        ArrayList<Throwable> arrayList = new ArrayList<Throwable>();
        while (throwable != null && !arrayList.contains(throwable)) {
            arrayList.add(throwable);
            throwable = ExceptionUtils.getCause(throwable);
        }
        return arrayList;
    }

    public static int indexOfThrowable(Throwable throwable, Class<?> clazz) {
        return ExceptionUtils.indexOf(throwable, clazz, 0, false);
    }

    public static int indexOfThrowable(Throwable throwable, Class<?> clazz, int n) {
        return ExceptionUtils.indexOf(throwable, clazz, n, false);
    }

    public static int indexOfType(Throwable throwable, Class<?> clazz) {
        return ExceptionUtils.indexOf(throwable, clazz, 0, true);
    }

    public static int indexOfType(Throwable throwable, Class<?> clazz, int n) {
        return ExceptionUtils.indexOf(throwable, clazz, n, true);
    }

    private static int indexOf(Throwable throwable, Class<?> clazz, int n, boolean bl) {
        Throwable[] throwableArray;
        if (throwable == null || clazz == null) {
            return 1;
        }
        if (n < 0) {
            n = 0;
        }
        if (n >= (throwableArray = ExceptionUtils.getThrowables(throwable)).length) {
            return 1;
        }
        if (bl) {
            for (int i = n; i < throwableArray.length; ++i) {
                if (!clazz.isAssignableFrom(throwableArray[i].getClass())) continue;
                return i;
            }
        } else {
            for (int i = n; i < throwableArray.length; ++i) {
                if (!clazz.equals(throwableArray[i].getClass())) continue;
                return i;
            }
        }
        return 1;
    }

    public static void printRootCauseStackTrace(Throwable throwable) {
        ExceptionUtils.printRootCauseStackTrace(throwable, System.err);
    }

    public static void printRootCauseStackTrace(Throwable throwable, PrintStream printStream) {
        String[] stringArray;
        if (throwable == null) {
            return;
        }
        if (printStream == null) {
            throw new IllegalArgumentException("The PrintStream must not be null");
        }
        for (String string : stringArray = ExceptionUtils.getRootCauseStackTrace(throwable)) {
            printStream.println(string);
        }
        printStream.flush();
    }

    public static void printRootCauseStackTrace(Throwable throwable, PrintWriter printWriter) {
        String[] stringArray;
        if (throwable == null) {
            return;
        }
        if (printWriter == null) {
            throw new IllegalArgumentException("The PrintWriter must not be null");
        }
        for (String string : stringArray = ExceptionUtils.getRootCauseStackTrace(throwable)) {
            printWriter.println(string);
        }
        printWriter.flush();
    }

    public static String[] getRootCauseStackTrace(Throwable throwable) {
        if (throwable == null) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        Throwable[] throwableArray = ExceptionUtils.getThrowables(throwable);
        int n = throwableArray.length;
        ArrayList<String> arrayList = new ArrayList<String>();
        List<String> list = ExceptionUtils.getStackFrameList(throwableArray[n - 1]);
        int n2 = n;
        while (--n2 >= 0) {
            List<String> list2 = list;
            if (n2 != 0) {
                list = ExceptionUtils.getStackFrameList(throwableArray[n2 - 1]);
                ExceptionUtils.removeCommonFrames(list2, list);
            }
            if (n2 == n - 1) {
                arrayList.add(throwableArray[n2].toString());
            } else {
                arrayList.add(WRAPPED_MARKER + throwableArray[n2].toString());
            }
            for (int i = 0; i < list2.size(); ++i) {
                arrayList.add(list2.get(i));
            }
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    public static void removeCommonFrames(List<String> list, List<String> list2) {
        if (list == null || list2 == null) {
            throw new IllegalArgumentException("The List must not be null");
        }
        int n = list.size() - 1;
        for (int i = list2.size() - 1; n >= 0 && i >= 0; --n, --i) {
            String string;
            String string2 = list.get(n);
            if (!string2.equals(string = list2.get(i))) continue;
            list.remove(n);
        }
    }

    public static String getStackTrace(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter, true);
        throwable.printStackTrace(printWriter);
        return stringWriter.getBuffer().toString();
    }

    public static String[] getStackFrames(Throwable throwable) {
        if (throwable == null) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        return ExceptionUtils.getStackFrames(ExceptionUtils.getStackTrace(throwable));
    }

    static String[] getStackFrames(String string) {
        String string2 = SystemUtils.LINE_SEPARATOR;
        StringTokenizer stringTokenizer = new StringTokenizer(string, string2);
        ArrayList<String> arrayList = new ArrayList<String>();
        while (stringTokenizer.hasMoreTokens()) {
            arrayList.add(stringTokenizer.nextToken());
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    static List<String> getStackFrameList(Throwable throwable) {
        String string = ExceptionUtils.getStackTrace(throwable);
        String string2 = SystemUtils.LINE_SEPARATOR;
        StringTokenizer stringTokenizer = new StringTokenizer(string, string2);
        ArrayList<String> arrayList = new ArrayList<String>();
        boolean bl = false;
        while (stringTokenizer.hasMoreTokens()) {
            String string3 = stringTokenizer.nextToken();
            int n = string3.indexOf("at");
            if (n != -1 && string3.substring(0, n).trim().isEmpty()) {
                bl = true;
                arrayList.add(string3);
                continue;
            }
            if (!bl) continue;
            break;
        }
        return arrayList;
    }

    public static String getMessage(Throwable throwable) {
        if (throwable == null) {
            return "";
        }
        String string = ClassUtils.getShortClassName(throwable, null);
        String string2 = throwable.getMessage();
        return string + ": " + StringUtils.defaultString(string2);
    }

    public static String getRootCauseMessage(Throwable throwable) {
        Throwable throwable2 = ExceptionUtils.getRootCause(throwable);
        throwable2 = throwable2 == null ? throwable : throwable2;
        return ExceptionUtils.getMessage(throwable2);
    }

    public static <R> R rethrow(Throwable throwable) {
        return ExceptionUtils.typeErasure(throwable);
    }

    private static <R, T extends Throwable> R typeErasure(Throwable throwable) throws T {
        throw throwable;
    }

    public static <R> R wrapAndThrow(Throwable throwable) {
        if (throwable instanceof RuntimeException) {
            throw (RuntimeException)throwable;
        }
        if (throwable instanceof Error) {
            throw (Error)throwable;
        }
        throw new UndeclaredThrowableException(throwable);
    }

    public static boolean hasCause(Throwable throwable, Class<? extends Throwable> clazz) {
        if (throwable instanceof UndeclaredThrowableException) {
            throwable = throwable.getCause();
        }
        return clazz.isInstance(throwable);
    }
}

