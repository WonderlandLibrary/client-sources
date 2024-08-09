/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.Nullable;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Callback;
import org.lwjgl.system.CallbackI;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Configuration;
import org.lwjgl.system.MemoryAccessJNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.StackWalkUtil;
import org.lwjgl.system.dyncall.DynCallback;
import org.lwjgl.system.libc.LibCStdlib;

final class MemoryManage {
    private MemoryManage() {
    }

    static MemoryUtil.MemoryAllocator getInstance() {
        Object object = Configuration.MEMORY_ALLOCATOR.get();
        if (object instanceof MemoryUtil.MemoryAllocator) {
            return (MemoryUtil.MemoryAllocator)object;
        }
        if (!"system".equals(object)) {
            String string = object == null || "jemalloc".equals(object) ? "org.lwjgl.system.jemalloc.JEmallocAllocator" : ("rpmalloc".equals(object) ? "org.lwjgl.system.rpmalloc.RPmallocAllocator" : object.toString());
            try {
                Class<?> clazz = Class.forName(string);
                return (MemoryUtil.MemoryAllocator)clazz.getConstructor(new Class[0]).newInstance(new Object[0]);
            } catch (Throwable throwable) {
                if (Checks.DEBUG && object != null) {
                    throwable.printStackTrace(APIUtil.DEBUG_STREAM);
                }
                APIUtil.apiLog(String.format("Warning: Failed to instantiate memory allocator: %s. Using the system default.", string));
            }
        }
        return new StdlibAllocator(null);
    }

    static class DebugAllocator
    implements MemoryUtil.MemoryAllocator {
        private static final ConcurrentMap<Long, Allocation> ALLOCATIONS = new ConcurrentHashMap<Long, Allocation>();
        private static final ConcurrentMap<Long, String> THREADS = new ConcurrentHashMap<Long, String>();
        private final MemoryUtil.MemoryAllocator allocator;
        private final long[] callbacks;

        DebugAllocator(MemoryUtil.MemoryAllocator memoryAllocator) {
            this.allocator = memoryAllocator;
            this.callbacks = new long[]{new CallbackI.P(this){
                final DebugAllocator this$0;
                {
                    this.this$0 = debugAllocator;
                }

                @Override
                public String getSignature() {
                    return "(p)p";
                }

                @Override
                public long callback(long l) {
                    long l2 = DynCallback.dcbArgPointer(l);
                    return this.this$0.malloc(l2);
                }
            }.address(), new CallbackI.P(this){
                final DebugAllocator this$0;
                {
                    this.this$0 = debugAllocator;
                }

                @Override
                public String getSignature() {
                    return "(pp)p";
                }

                @Override
                public long callback(long l) {
                    long l2 = DynCallback.dcbArgPointer(l);
                    long l3 = DynCallback.dcbArgPointer(l);
                    return this.this$0.calloc(l2, l3);
                }
            }.address(), new CallbackI.P(this){
                final DebugAllocator this$0;
                {
                    this.this$0 = debugAllocator;
                }

                @Override
                public String getSignature() {
                    return "(pp)p";
                }

                @Override
                public long callback(long l) {
                    long l2 = DynCallback.dcbArgPointer(l);
                    long l3 = DynCallback.dcbArgPointer(l);
                    return this.this$0.realloc(l2, l3);
                }
            }.address(), new CallbackI.V(this){
                final DebugAllocator this$0;
                {
                    this.this$0 = debugAllocator;
                }

                @Override
                public String getSignature() {
                    return "(p)v";
                }

                @Override
                public void callback(long l) {
                    long l2 = DynCallback.dcbArgPointer(l);
                    this.this$0.free(l2);
                }
            }.address(), new CallbackI.P(this){
                final DebugAllocator this$0;
                {
                    this.this$0 = debugAllocator;
                }

                @Override
                public String getSignature() {
                    return "(pp)p";
                }

                @Override
                public long callback(long l) {
                    long l2 = DynCallback.dcbArgPointer(l);
                    long l3 = DynCallback.dcbArgPointer(l);
                    return this.this$0.aligned_alloc(l2, l3);
                }
            }.address(), new CallbackI.V(this){
                final DebugAllocator this$0;
                {
                    this.this$0 = debugAllocator;
                }

                @Override
                public String getSignature() {
                    return "(p)v";
                }

                @Override
                public void callback(long l) {
                    long l2 = DynCallback.dcbArgPointer(l);
                    this.this$0.aligned_free(l2);
                }
            }.address()};
            Runtime.getRuntime().addShutdownHook(new Thread(this::lambda$new$0));
        }

        @Override
        public long getMalloc() {
            return this.callbacks[0];
        }

        @Override
        public long getCalloc() {
            return this.callbacks[1];
        }

        @Override
        public long getRealloc() {
            return this.callbacks[2];
        }

        @Override
        public long getFree() {
            return this.callbacks[3];
        }

        @Override
        public long getAlignedAlloc() {
            return this.callbacks[4];
        }

        @Override
        public long getAlignedFree() {
            return this.callbacks[5];
        }

        @Override
        public long malloc(long l) {
            return DebugAllocator.track(this.allocator.malloc(l), l);
        }

        @Override
        public long calloc(long l, long l2) {
            return DebugAllocator.track(this.allocator.calloc(l, l2), l * l2);
        }

        @Override
        public long realloc(long l, long l2) {
            long l3 = DebugAllocator.untrack(l);
            long l4 = this.allocator.realloc(l, l2);
            if (l4 != 0L) {
                DebugAllocator.track(l4, l2);
            } else if (l2 != 0L) {
                DebugAllocator.track(l, l3);
            }
            return l4;
        }

        @Override
        public void free(long l) {
            DebugAllocator.untrack(l);
            this.allocator.free(l);
        }

        @Override
        public long aligned_alloc(long l, long l2) {
            return DebugAllocator.track(this.allocator.aligned_alloc(l, l2), l2);
        }

        @Override
        public void aligned_free(long l) {
            DebugAllocator.untrack(l);
            this.allocator.aligned_free(l);
        }

        static long track(long l, long l2) {
            if (l != 0L) {
                Allocation allocation;
                Thread thread2 = Thread.currentThread();
                Long l3 = thread2.getId();
                if (!THREADS.containsKey(l3)) {
                    THREADS.put(l3, thread2.getName());
                }
                if ((allocation = ALLOCATIONS.put(l, new Allocation(StackWalkUtil.stackWalkGetTrace(), l2))) != null) {
                    throw new IllegalStateException("The memory address specified is already being tracked: 0x" + Long.toHexString(l).toUpperCase());
                }
            }
            return l;
        }

        static long untrack(long l) {
            if (l == 0L) {
                return 0L;
            }
            Allocation allocation = (Allocation)ALLOCATIONS.remove(l);
            if (allocation == null) {
                throw new IllegalStateException("The memory address specified is not being tracked: 0x" + Long.toHexString(l).toUpperCase());
            }
            return allocation.size;
        }

        static void report(MemoryUtil.MemoryAllocationReport memoryAllocationReport) {
            for (Map.Entry entry : ALLOCATIONS.entrySet()) {
                Allocation allocation = (Allocation)entry.getValue();
                memoryAllocationReport.invoke((Long)entry.getKey(), allocation.size, allocation.threadId, (String)THREADS.get(allocation.threadId), Allocation.access$100(allocation));
            }
        }

        private static <T> void aggregate(T t, long l, Map<T, AtomicLong> map) {
            AtomicLong atomicLong = map.computeIfAbsent(t, DebugAllocator::lambda$aggregate$1);
            atomicLong.set(atomicLong.get() + l);
        }

        static void report(MemoryUtil.MemoryAllocationReport memoryAllocationReport, MemoryUtil.MemoryAllocationReport.Aggregate aggregate, boolean bl) {
            switch (1.$SwitchMap$org$lwjgl$system$MemoryUtil$MemoryAllocationReport$Aggregate[aggregate.ordinal()]) {
                case 1: {
                    if (bl) {
                        HashMap hashMap = new HashMap();
                        for (Allocation object : ALLOCATIONS.values()) {
                            DebugAllocator.aggregate(object.threadId, object.size, hashMap);
                        }
                        for (Map.Entry object : hashMap.entrySet()) {
                            memoryAllocationReport.invoke(0L, ((AtomicLong)object.getValue()).get(), (Long)object.getKey(), (String)THREADS.get(object.getKey()), null);
                        }
                        break;
                    }
                    long l = 0L;
                    for (Allocation allocation : ALLOCATIONS.values()) {
                        l += allocation.size;
                    }
                    memoryAllocationReport.invoke(0L, l, 0L, null, null);
                    break;
                }
                case 2: {
                    if (bl) {
                        HashMap<Long, Map> hashMap = new HashMap<Long, Map>();
                        for (Allocation object : ALLOCATIONS.values()) {
                            Map map = hashMap.computeIfAbsent(object.threadId, DebugAllocator::lambda$report$2);
                            DebugAllocator.aggregate(Allocation.access$100(object)[0], object.size, map);
                        }
                        for (Map.Entry entry : hashMap.entrySet()) {
                            long l = (Long)entry.getKey();
                            Map map = (Map)entry.getValue();
                            for (Map.Entry entry2 : map.entrySet()) {
                                memoryAllocationReport.invoke(0L, ((AtomicLong)entry2.getValue()).get(), l, (String)THREADS.get(l), (StackTraceElement)entry2.getKey());
                            }
                        }
                    } else {
                        HashMap hashMap = new HashMap();
                        for (Allocation allocation : ALLOCATIONS.values()) {
                            DebugAllocator.aggregate(Allocation.access$100(allocation)[0], allocation.size, hashMap);
                        }
                        for (Map.Entry entry : hashMap.entrySet()) {
                            memoryAllocationReport.invoke(0L, ((AtomicLong)entry.getValue()).get(), 0L, null, (StackTraceElement)entry.getKey());
                        }
                    }
                    break;
                }
                case 3: {
                    if (bl) {
                        HashMap<Long, Map> hashMap = new HashMap<Long, Map>();
                        for (Allocation allocation : ALLOCATIONS.values()) {
                            Map map = hashMap.computeIfAbsent(allocation.threadId, DebugAllocator::lambda$report$3);
                            DebugAllocator.aggregate(allocation, allocation.size, map);
                        }
                        for (Map.Entry entry : hashMap.entrySet()) {
                            long l = (Long)entry.getKey();
                            Map map = (Map)entry.getValue();
                            for (Map.Entry entry3 : map.entrySet()) {
                                memoryAllocationReport.invoke(0L, ((AtomicLong)entry3.getValue()).get(), l, (String)THREADS.get(l), Allocation.access$100((Allocation)entry3.getKey()));
                            }
                        }
                    } else {
                        HashMap hashMap = new HashMap();
                        for (Allocation allocation : ALLOCATIONS.values()) {
                            DebugAllocator.aggregate(allocation, allocation.size, hashMap);
                        }
                        for (Map.Entry entry : hashMap.entrySet()) {
                            memoryAllocationReport.invoke(0L, ((AtomicLong)entry.getValue()).get(), 0L, null, Allocation.access$100((Allocation)entry.getKey()));
                        }
                    }
                    break;
                }
            }
        }

        private static Map lambda$report$3(Long l) {
            return new HashMap();
        }

        private static Map lambda$report$2(Long l) {
            return new HashMap();
        }

        private static AtomicLong lambda$aggregate$1(Object object) {
            return new AtomicLong();
        }

        private void lambda$new$0() {
            for (long l : this.callbacks) {
                Callback.free(l);
            }
            if (ALLOCATIONS.isEmpty()) {
                return;
            }
            Object object = ALLOCATIONS.entrySet().iterator();
            while (object.hasNext()) {
                Map.Entry entry = (Map.Entry)object.next();
                Long l = (Long)entry.getKey();
                Allocation allocation = (Allocation)entry.getValue();
                APIUtil.DEBUG_STREAM.format("[LWJGL] %d bytes leaked, thread %d (%s), address: 0x%s\n", allocation.size, allocation.threadId, THREADS.get(allocation.threadId), Long.toHexString(l).toUpperCase());
                for (Object object2 : Allocation.access$200(allocation)) {
                    APIUtil.DEBUG_STREAM.format("\tat %s\n", object2.toString());
                }
            }
        }

        private static class Allocation {
            private final Object[] stackTrace;
            @Nullable
            private StackTraceElement[] elements;
            final long size;
            final long threadId;

            Allocation(Object[] objectArray, long l) {
                this.stackTrace = objectArray;
                this.size = l;
                this.threadId = Thread.currentThread().getId();
            }

            private StackTraceElement[] getElements() {
                if (this.elements == null) {
                    this.elements = StackWalkUtil.stackWalkArray(this.stackTrace);
                }
                return this.elements;
            }

            public boolean equals(Object object) {
                if (this == object) {
                    return false;
                }
                if (object == null || this.getClass() != object.getClass()) {
                    return true;
                }
                Allocation allocation = (Allocation)object;
                return Arrays.equals(this.getElements(), allocation.getElements());
            }

            public int hashCode() {
                return Arrays.hashCode(this.getElements());
            }

            static StackTraceElement[] access$100(Allocation allocation) {
                return allocation.getElements();
            }

            static Object[] access$200(Allocation allocation) {
                return allocation.stackTrace;
            }
        }
    }

    private static class StdlibAllocator
    implements MemoryUtil.MemoryAllocator {
        private StdlibAllocator() {
        }

        @Override
        public long getMalloc() {
            return MemoryAccessJNI.malloc;
        }

        @Override
        public long getCalloc() {
            return MemoryAccessJNI.calloc;
        }

        @Override
        public long getRealloc() {
            return MemoryAccessJNI.realloc;
        }

        @Override
        public long getFree() {
            return MemoryAccessJNI.free;
        }

        @Override
        public long getAlignedAlloc() {
            return MemoryAccessJNI.aligned_alloc;
        }

        @Override
        public long getAlignedFree() {
            return MemoryAccessJNI.aligned_free;
        }

        @Override
        public long malloc(long l) {
            return LibCStdlib.nmalloc(l);
        }

        @Override
        public long calloc(long l, long l2) {
            return LibCStdlib.ncalloc(l, l2);
        }

        @Override
        public long realloc(long l, long l2) {
            return LibCStdlib.nrealloc(l, l2);
        }

        @Override
        public void free(long l) {
            LibCStdlib.nfree(l);
        }

        @Override
        public long aligned_alloc(long l, long l2) {
            return LibCStdlib.naligned_alloc(l, l2);
        }

        @Override
        public void aligned_free(long l) {
            LibCStdlib.naligned_free(l);
        }

        StdlibAllocator(1 var1_1) {
            this();
        }
    }
}

