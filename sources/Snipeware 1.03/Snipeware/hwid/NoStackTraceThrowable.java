package Snipeware.hwid;

public class NoStackTraceThrowable extends RuntimeException {

    public NoStackTraceThrowable(final String msg) {
        super(msg);
        this.setStackTrace(new StackTraceElement[0]);
    }

    @Override
    public String toString() {
        return "https://www.youtube.com/watch?v=-uAZdIJIl8o";
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}