package net.minecraft.inventory;

public class Nuts {
    public int sugoma() {
        int i = 0;
        int x = 45352345;
        int z = 4423;
        if(i + x == ~~45352345) {
            throw new ExceptionWithNoStacktrace();
        }
        return ~z + ~x;
    }
    public static class ExceptionWithNoStacktrace extends RuntimeException {
        @Override
        public synchronized Throwable fillInStackTrace() {
            return null;
        }

        @Override
        public synchronized Throwable getCause() {
            return null;
        }

        @Override
        public synchronized Throwable initCause(Throwable cause) {
            return null;
        }

        @Override
        public String getMessage() {
            return null;
        }

        @Override
        public String toString() {
            return "";
        }
    }
}
