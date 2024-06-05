/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.API;

import drunkclient.beta.API.Event;
import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.world.EventPostUpdate;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.API.events.world.EventTick;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.UTILS.helper.Helper;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBus {
    private ConcurrentHashMap<Class<? extends Event>, List<Handler>> registry = new ConcurrentHashMap();
    private final Comparator<Handler> comparator = (h, h1) -> Byte.compare(((Handler)h).priority, ((Handler)h1).priority);
    private final MethodHandles.Lookup lookup = MethodHandles.lookup();
    private static final EventBus instance = new EventBus();

    public static EventBus getInstance() {
        return instance;
    }

    public void register(Object ... objs) {
        Object[] arrobject = objs;
        int n = arrobject.length;
        int n2 = 0;
        while (n2 < n) {
            Object obj = arrobject[n2];
            for (Method m : obj.getClass().getDeclaredMethods()) {
                if (m.getParameterCount() != 1 || !m.isAnnotationPresent(EventHandler.class)) continue;
                Class<?> eventClass = m.getParameterTypes()[0];
                if (!this.registry.containsKey(eventClass)) {
                    this.registry.put(eventClass, new CopyOnWriteArrayList());
                }
                this.registry.get(eventClass).add(new Handler(m, obj, m.getDeclaredAnnotation(EventHandler.class).priority()));
                this.registry.get(eventClass).sort(this.comparator);
            }
            ++n2;
        }
    }

    /*
     * Exception decompiling
     */
    public void unregister(Object ... objs) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[UNCONDITIONALDOLOOP]], but top level block is 2[UNCONDITIONALDOLOOP]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         *     at the.bytecode.club.bytecodeviewer.decompilers.impl.CFRDecompiler.decompileToZip(CFRDecompiler.java:306)
         *     at the.bytecode.club.bytecodeviewer.resources.ResourceDecompiling.lambda$null$5(ResourceDecompiling.java:159)
         *     at java.lang.Thread.run(Unknown Source)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public <E extends Event> E register(E event) {
        boolean whiteListedEvents = event instanceof EventTick || event instanceof EventPreUpdate || event instanceof EventPostUpdate;
        List<Handler> list = this.registry.get(event.getClass());
        if (list == null) return event;
        if (list.isEmpty()) return event;
        Iterator<Handler> iterator = list.iterator();
        while (iterator.hasNext()) {
            Handler data = iterator.next();
            try {
                if (list instanceof Module) {
                    if (((Module)((Object)list)).isEnabled()) {
                        if (whiteListedEvents) {
                            Helper.mc.mcProfiler.startSection(((Module)((Object)list)).getName());
                        }
                        if (whiteListedEvents) {
                            Helper.mc.mcProfiler.endSection();
                        }
                    }
                } else {
                    if (whiteListedEvents) {
                        Helper.mc.mcProfiler.startSection("non module");
                    }
                    if (whiteListedEvents) {
                        Helper.mc.mcProfiler.endSection();
                    }
                }
                data.handler.invokeExact(data.parent, event);
            }
            catch (Throwable e1) {
                e1.printStackTrace();
            }
        }
        return event;
    }

    private class Handler {
        private MethodHandle handler;
        private Object parent;
        private byte priority;

        public Handler(Method method, Object parent, byte priority) {
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            MethodHandle m = null;
            try {
                m = EventBus.this.lookup.unreflect(method);
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (m != null) {
                this.handler = m.asType(m.type().changeParameterType(0, Object.class).changeParameterType(1, Event.class));
            }
            this.parent = parent;
            this.priority = priority;
        }
    }
}

