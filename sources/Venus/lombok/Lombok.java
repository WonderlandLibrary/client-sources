/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package lombok;

public class Lombok {
    public static RuntimeException sneakyThrow(Throwable throwable) {
        if (throwable == null) {
            throw new NullPointerException("t");
        }
        return (RuntimeException)Lombok.sneakyThrow0(throwable);
    }

    private static <T extends Throwable> T sneakyThrow0(Throwable throwable) throws T {
        throw throwable;
    }

    public static <T> T preventNullAnalysis(T t) {
        return t;
    }

    public static <T> T checkNotNull(T t, String string) {
        if (t == null) {
            throw new NullPointerException(string);
        }
        return t;
    }
}

