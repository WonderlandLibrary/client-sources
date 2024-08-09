/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.util;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.Constants;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.apache.logging.log4j.util.PropertiesUtil;

@PerformanceSensitive(value={"allocation"})
public class Unbox {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private static final int BITS_PER_INT = 32;
    private static final int RINGBUFFER_MIN_SIZE = 32;
    private static final int RINGBUFFER_SIZE = Unbox.calculateRingBufferSize("log4j.unbox.ringbuffer.size");
    private static final int MASK = RINGBUFFER_SIZE - 1;
    private static ThreadLocal<State> threadLocalState = new ThreadLocal();
    private static WebSafeState webSafeState = new WebSafeState(null);

    private Unbox() {
    }

    private static int calculateRingBufferSize(String string) {
        String string2 = PropertiesUtil.getProperties().getStringProperty(string, String.valueOf(32));
        try {
            int n = Integer.parseInt(string2);
            if (n < 32) {
                n = 32;
                LOGGER.warn("Invalid {} {}, using minimum size {}.", (Object)string, (Object)string2, (Object)32);
            }
            return Unbox.ceilingNextPowerOfTwo(n);
        } catch (Exception exception) {
            LOGGER.warn("Invalid {} {}, using default size {}.", (Object)string, (Object)string2, (Object)32);
            return 1;
        }
    }

    private static int ceilingNextPowerOfTwo(int n) {
        return 1 << 32 - Integer.numberOfLeadingZeros(n - 1);
    }

    @PerformanceSensitive(value={"allocation"})
    public static StringBuilder box(float f) {
        return Unbox.getSB().append(f);
    }

    @PerformanceSensitive(value={"allocation"})
    public static StringBuilder box(double d) {
        return Unbox.getSB().append(d);
    }

    @PerformanceSensitive(value={"allocation"})
    public static StringBuilder box(short s) {
        return Unbox.getSB().append(s);
    }

    @PerformanceSensitive(value={"allocation"})
    public static StringBuilder box(int n) {
        return Unbox.getSB().append(n);
    }

    @PerformanceSensitive(value={"allocation"})
    public static StringBuilder box(char c) {
        return Unbox.getSB().append(c);
    }

    @PerformanceSensitive(value={"allocation"})
    public static StringBuilder box(long l) {
        return Unbox.getSB().append(l);
    }

    @PerformanceSensitive(value={"allocation"})
    public static StringBuilder box(byte by) {
        return Unbox.getSB().append(by);
    }

    @PerformanceSensitive(value={"allocation"})
    public static StringBuilder box(boolean bl) {
        return Unbox.getSB().append(bl);
    }

    private static State getState() {
        State state = threadLocalState.get();
        if (state == null) {
            state = new State();
            threadLocalState.set(state);
        }
        return state;
    }

    private static StringBuilder getSB() {
        return Constants.ENABLE_THREADLOCALS ? Unbox.getState().getStringBuilder() : webSafeState.getStringBuilder();
    }

    static int getRingbufferSize() {
        return RINGBUFFER_SIZE;
    }

    static int access$000() {
        return RINGBUFFER_SIZE;
    }

    static int access$100() {
        return MASK;
    }

    static class 1 {
    }

    private static class State {
        private final StringBuilder[] ringBuffer = new StringBuilder[Unbox.access$000()];
        private int current;

        State() {
            for (int i = 0; i < this.ringBuffer.length; ++i) {
                this.ringBuffer[i] = new StringBuilder(21);
            }
        }

        public StringBuilder getStringBuilder() {
            StringBuilder stringBuilder = this.ringBuffer[Unbox.access$100() & this.current++];
            stringBuilder.setLength(0);
            return stringBuilder;
        }

        public boolean isBoxedPrimitive(StringBuilder stringBuilder) {
            for (int i = 0; i < this.ringBuffer.length; ++i) {
                if (stringBuilder != this.ringBuffer[i]) continue;
                return false;
            }
            return true;
        }
    }

    private static class WebSafeState {
        private final ThreadLocal<StringBuilder[]> ringBuffer = new ThreadLocal();
        private final ThreadLocal<int[]> current = new ThreadLocal();

        private WebSafeState() {
        }

        public StringBuilder getStringBuilder() {
            StringBuilder[] stringBuilderArray = this.ringBuffer.get();
            if (stringBuilderArray == null) {
                stringBuilderArray = new StringBuilder[Unbox.access$000()];
                for (int i = 0; i < stringBuilderArray.length; ++i) {
                    stringBuilderArray[i] = new StringBuilder(21);
                }
                this.ringBuffer.set(stringBuilderArray);
                this.current.set(new int[1]);
            }
            int[] nArray = this.current.get();
            int n = nArray[0];
            nArray[0] = n + 1;
            StringBuilder stringBuilder = stringBuilderArray[Unbox.access$100() & n];
            stringBuilder.setLength(0);
            return stringBuilder;
        }

        public boolean isBoxedPrimitive(StringBuilder stringBuilder) {
            StringBuilder[] stringBuilderArray = this.ringBuffer.get();
            if (stringBuilderArray == null) {
                return true;
            }
            for (int i = 0; i < stringBuilderArray.length; ++i) {
                if (stringBuilder != stringBuilderArray[i]) continue;
                return false;
            }
            return true;
        }

        WebSafeState(1 var1_1) {
            this();
        }
    }
}

