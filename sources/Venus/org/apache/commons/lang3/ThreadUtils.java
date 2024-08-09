/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ThreadUtils {
    public static final AlwaysTruePredicate ALWAYS_TRUE_PREDICATE = new AlwaysTruePredicate(null);

    public static Thread findThreadById(long l, ThreadGroup threadGroup) {
        if (threadGroup == null) {
            throw new IllegalArgumentException("The thread group must not be null");
        }
        Thread thread2 = ThreadUtils.findThreadById(l);
        if (thread2 != null && threadGroup.equals(thread2.getThreadGroup())) {
            return thread2;
        }
        return null;
    }

    public static Thread findThreadById(long l, String string) {
        if (string == null) {
            throw new IllegalArgumentException("The thread group name must not be null");
        }
        Thread thread2 = ThreadUtils.findThreadById(l);
        if (thread2 != null && thread2.getThreadGroup() != null && thread2.getThreadGroup().getName().equals(string)) {
            return thread2;
        }
        return null;
    }

    public static Collection<Thread> findThreadsByName(String string, ThreadGroup threadGroup) {
        return ThreadUtils.findThreads(threadGroup, false, new NamePredicate(string));
    }

    public static Collection<Thread> findThreadsByName(String string, String string2) {
        if (string == null) {
            throw new IllegalArgumentException("The thread name must not be null");
        }
        if (string2 == null) {
            throw new IllegalArgumentException("The thread group name must not be null");
        }
        Collection<ThreadGroup> collection = ThreadUtils.findThreadGroups(new NamePredicate(string2));
        if (collection.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<Thread> arrayList = new ArrayList<Thread>();
        NamePredicate namePredicate = new NamePredicate(string);
        for (ThreadGroup threadGroup : collection) {
            arrayList.addAll(ThreadUtils.findThreads(threadGroup, false, namePredicate));
        }
        return Collections.unmodifiableCollection(arrayList);
    }

    public static Collection<ThreadGroup> findThreadGroupsByName(String string) {
        return ThreadUtils.findThreadGroups(new NamePredicate(string));
    }

    public static Collection<ThreadGroup> getAllThreadGroups() {
        return ThreadUtils.findThreadGroups(ALWAYS_TRUE_PREDICATE);
    }

    public static ThreadGroup getSystemThreadGroup() {
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        while (threadGroup.getParent() != null) {
            threadGroup = threadGroup.getParent();
        }
        return threadGroup;
    }

    public static Collection<Thread> getAllThreads() {
        return ThreadUtils.findThreads(ALWAYS_TRUE_PREDICATE);
    }

    public static Collection<Thread> findThreadsByName(String string) {
        return ThreadUtils.findThreads(new NamePredicate(string));
    }

    public static Thread findThreadById(long l) {
        Collection<Thread> collection = ThreadUtils.findThreads(new ThreadIdPredicate(l));
        return collection.isEmpty() ? null : collection.iterator().next();
    }

    public static Collection<Thread> findThreads(ThreadPredicate threadPredicate) {
        return ThreadUtils.findThreads(ThreadUtils.getSystemThreadGroup(), true, threadPredicate);
    }

    public static Collection<ThreadGroup> findThreadGroups(ThreadGroupPredicate threadGroupPredicate) {
        return ThreadUtils.findThreadGroups(ThreadUtils.getSystemThreadGroup(), true, threadGroupPredicate);
    }

    public static Collection<Thread> findThreads(ThreadGroup threadGroup, boolean bl, ThreadPredicate threadPredicate) {
        Thread[] threadArray;
        if (threadGroup == null) {
            throw new IllegalArgumentException("The group must not be null");
        }
        if (threadPredicate == null) {
            throw new IllegalArgumentException("The predicate must not be null");
        }
        int n = threadGroup.activeCount();
        while ((n = threadGroup.enumerate(threadArray = new Thread[n + n / 2 + 1], bl)) >= threadArray.length) {
        }
        ArrayList<Thread> arrayList = new ArrayList<Thread>(n);
        for (int i = 0; i < n; ++i) {
            if (!threadPredicate.test(threadArray[i])) continue;
            arrayList.add(threadArray[i]);
        }
        return Collections.unmodifiableCollection(arrayList);
    }

    public static Collection<ThreadGroup> findThreadGroups(ThreadGroup threadGroup, boolean bl, ThreadGroupPredicate threadGroupPredicate) {
        ThreadGroup[] threadGroupArray;
        if (threadGroup == null) {
            throw new IllegalArgumentException("The group must not be null");
        }
        if (threadGroupPredicate == null) {
            throw new IllegalArgumentException("The predicate must not be null");
        }
        int n = threadGroup.activeGroupCount();
        while ((n = threadGroup.enumerate(threadGroupArray = new ThreadGroup[n + n / 2 + 1], bl)) >= threadGroupArray.length) {
        }
        ArrayList<ThreadGroup> arrayList = new ArrayList<ThreadGroup>(n);
        for (int i = 0; i < n; ++i) {
            if (!threadGroupPredicate.test(threadGroupArray[i])) continue;
            arrayList.add(threadGroupArray[i]);
        }
        return Collections.unmodifiableCollection(arrayList);
    }

    public static class ThreadIdPredicate
    implements ThreadPredicate {
        private final long threadId;

        public ThreadIdPredicate(long l) {
            if (l <= 0L) {
                throw new IllegalArgumentException("The thread id must be greater than zero");
            }
            this.threadId = l;
        }

        @Override
        public boolean test(Thread thread2) {
            return thread2 != null && thread2.getId() == this.threadId;
        }
    }

    public static class NamePredicate
    implements ThreadPredicate,
    ThreadGroupPredicate {
        private final String name;

        public NamePredicate(String string) {
            if (string == null) {
                throw new IllegalArgumentException("The name must not be null");
            }
            this.name = string;
        }

        @Override
        public boolean test(ThreadGroup threadGroup) {
            return threadGroup != null && threadGroup.getName().equals(this.name);
        }

        @Override
        public boolean test(Thread thread2) {
            return thread2 != null && thread2.getName().equals(this.name);
        }
    }

    private static final class AlwaysTruePredicate
    implements ThreadPredicate,
    ThreadGroupPredicate {
        private AlwaysTruePredicate() {
        }

        @Override
        public boolean test(ThreadGroup threadGroup) {
            return false;
        }

        @Override
        public boolean test(Thread thread2) {
            return false;
        }

        AlwaysTruePredicate(1 var1_1) {
            this();
        }
    }

    public static interface ThreadGroupPredicate {
        public boolean test(ThreadGroup var1);
    }

    public static interface ThreadPredicate {
        public boolean test(Thread var1);
    }
}

