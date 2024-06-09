package org.spongepowered.asm.util.perf;

import com.google.common.base.*;
import org.spongepowered.asm.util.*;
import java.text.*;
import java.util.*;

public final class Profiler
{
    public static final int ROOT = 1;
    public static final int FINE = 2;
    private final Map<String, Section> sections;
    private final List<String> phases;
    private final Deque<Section> stack;
    private boolean active;
    
    public Profiler() {
        super();
        this.sections = new TreeMap<String, Section>();
        this.phases = new ArrayList<String>();
        this.stack = new LinkedList<Section>();
        this.phases.add("Initial");
    }
    
    public void setActive(final boolean active) {
        if ((!this.active && active) || !active) {
            this.reset();
        }
        this.active = active;
    }
    
    public void reset() {
        final Iterator<Section> iterator = this.sections.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().invalidate();
        }
        this.sections.clear();
        this.phases.clear();
        this.phases.add("Initial");
        this.stack.clear();
    }
    
    public Section get(final String s) {
        Section section = this.sections.get(s);
        if (section == null) {
            section = (this.active ? new LiveSection(s, this.phases.size() - 1) : new Section(s));
            this.sections.put(s, section);
        }
        return section;
    }
    
    private Section getSubSection(final String s, final String s2, final Section section) {
        Section section2 = this.sections.get(s);
        if (section2 == null) {
            section2 = new SubSection(s, this.phases.size() - 1, s2, section);
            this.sections.put(s, section2);
        }
        return section2;
    }
    
    boolean isHead(final Section section) {
        return this.stack.peek() == section;
    }
    
    public Section begin(final String... array) {
        return this.begin(0, array);
    }
    
    public Section begin(final int n, final String... array) {
        return this.begin(n, Joiner.on('.').join((Object[])array));
    }
    
    public Section begin(final String s) {
        return this.begin(0, s);
    }
    
    public Section begin(final int n, String string) {
        boolean root = (n & 0x1) != 0x0;
        final boolean fine = (n & 0x2) != 0x0;
        String string2 = string;
        final Section section = this.stack.peek();
        if (section != null) {
            string2 = section.getName() + (root ? " -> " : ".") + string2;
            if (section.isRoot() && !root) {
                final int lastIndex = section.getName().lastIndexOf(" -> ");
                string = ((lastIndex > -1) ? section.getName().substring(lastIndex + 4) : section.getName()) + "." + string;
                root = true;
            }
        }
        Section section2 = this.get(root ? string : string2);
        if (root && section != null && this.active) {
            section2 = this.getSubSection(string2, section.getName(), section2);
        }
        section2.setFine(fine).setRoot(root);
        this.stack.push(section2);
        return section2.start();
    }
    
    void end(final Section section) {
        try {
            Section section3;
            final Section section2 = section3 = this.stack.pop();
            while (section3 != section) {
                if (section3 == null && this.active) {
                    if (section2 == null) {
                        throw new IllegalStateException("Attempted to pop " + section + " but the stack is empty");
                    }
                    throw new IllegalStateException("Attempted to pop " + section + " which was not in the stack, head was " + section2);
                }
                else {
                    section3 = this.stack.pop();
                }
            }
        }
        catch (NoSuchElementException ex) {
            if (this.active) {
                throw new IllegalStateException("Attempted to pop " + section + " but the stack is empty");
            }
        }
    }
    
    public void mark(final String s) {
        long n = 0L;
        final Iterator<Section> iterator = this.sections.values().iterator();
        while (iterator.hasNext()) {
            n += iterator.next().getTime();
        }
        if (n == 0L) {
            this.phases.set(this.phases.size() - 1, s);
            return;
        }
        this.phases.add(s);
        final Iterator<Section> iterator2 = this.sections.values().iterator();
        while (iterator2.hasNext()) {
            iterator2.next().mark();
        }
    }
    
    public Collection<Section> getSections() {
        return Collections.unmodifiableCollection((Collection<? extends Section>)this.sections.values());
    }
    
    public PrettyPrinter printer(final boolean b, final boolean b2) {
        final PrettyPrinter prettyPrinter = new PrettyPrinter();
        final int n = this.phases.size() + 4;
        final int[] array = { 0, 1, 2, n - 2, n - 1 };
        final Object[] array2 = new Object[n * 2];
        int i = 0;
        int n2 = 0;
        while (i < n) {
            array2[n2 + 1] = PrettyPrinter.Alignment.RIGHT;
            if (i == array[0]) {
                array2[n2] = (b2 ? "" : "  ") + "Section";
                array2[n2 + 1] = PrettyPrinter.Alignment.LEFT;
            }
            else if (i == array[1]) {
                array2[n2] = "    TOTAL";
            }
            else if (i == array[3]) {
                array2[n2] = "    Count";
            }
            else if (i == array[4]) {
                array2[n2] = "Avg. ";
            }
            else if (i - array[2] < this.phases.size()) {
                array2[n2] = this.phases.get(i - array[2]);
            }
            else {
                array2[n2] = "";
            }
            n2 = ++i * 2;
        }
        prettyPrinter.table(array2).th().hr().add();
        for (final Section section : this.sections.values()) {
            if (!section.isFine() || b) {
                if (b2 && section.getDelegate() != section) {
                    continue;
                }
                this.printSectionRow(prettyPrinter, n, array, section, b2);
                if (!b2) {
                    continue;
                }
                for (final Section section2 : this.sections.values()) {
                    final Section delegate = section2.getDelegate();
                    if ((!section2.isFine() || b) && delegate == section) {
                        if (delegate == section2) {
                            continue;
                        }
                        this.printSectionRow(prettyPrinter, n, array, section2, b2);
                    }
                }
            }
        }
        return prettyPrinter.add();
    }
    
    private void printSectionRow(final PrettyPrinter prettyPrinter, final int n, final int[] array, final Section section, final boolean b) {
        final boolean b2 = section.getDelegate() != section;
        final Object[] array2 = new Object[n];
        int n2 = 1;
        if (b) {
            array2[0] = (b2 ? ("  > " + section.getBaseName()) : section.getName());
        }
        else {
            array2[0] = (b2 ? "+ " : "  ") + section.getName();
        }
        for (final long n3 : section.getTimes()) {
            if (n2 == array[1]) {
                array2[n2++] = section.getTotalTime() + " ms";
            }
            if (n2 >= array[2] && n2 < array2.length) {
                array2[n2++] = n3 + " ms";
            }
        }
        array2[array[3]] = section.getTotalCount();
        array2[array[4]] = new DecimalFormat("   ###0.000 ms").format(section.getTotalAverageTime());
        for (int j = 0; j < array2.length; ++j) {
            if (array2[j] == null) {
                array2[j] = "-";
            }
        }
        prettyPrinter.tr(array2);
    }
    
    public class Section
    {
        static final String SEPARATOR_ROOT = " -> ";
        static final String SEPARATOR_CHILD = ".";
        private final String name;
        private boolean root;
        private boolean fine;
        protected boolean invalidated;
        private String info;
        final Profiler this$0;
        
        Section(final Profiler this$0, final String s) {
            this.this$0 = this$0;
            super();
            this.name = s;
            this.info = s;
        }
        
        Section getDelegate() {
            return this;
        }
        
        Section invalidate() {
            this.invalidated = true;
            return this;
        }
        
        Section setRoot(final boolean root) {
            this.root = root;
            return this;
        }
        
        public boolean isRoot() {
            return this.root;
        }
        
        Section setFine(final boolean fine) {
            this.fine = fine;
            return this;
        }
        
        public boolean isFine() {
            return this.fine;
        }
        
        public String getName() {
            return this.name;
        }
        
        public String getBaseName() {
            return this.name;
        }
        
        public void setInfo(final String info) {
            this.info = info;
        }
        
        public String getInfo() {
            return this.info;
        }
        
        Section start() {
            return this;
        }
        
        protected Section stop() {
            return this;
        }
        
        public Section end() {
            if (!this.invalidated) {
                this.this$0.end(this);
            }
            return this;
        }
        
        public Section next(final String s) {
            this.end();
            return this.this$0.begin(s);
        }
        
        void mark() {
        }
        
        public long getTime() {
            return 0L;
        }
        
        public long getTotalTime() {
            return 0L;
        }
        
        public double getSeconds() {
            return 0.0;
        }
        
        public double getTotalSeconds() {
            return 0.0;
        }
        
        public long[] getTimes() {
            return new long[1];
        }
        
        public int getCount() {
            return 0;
        }
        
        public int getTotalCount() {
            return 0;
        }
        
        public double getAverageTime() {
            return 0.0;
        }
        
        public double getTotalAverageTime() {
            return 0.0;
        }
        
        @Override
        public final String toString() {
            return this.name;
        }
    }
    
    class LiveSection extends Section
    {
        private int cursor;
        private long[] times;
        private long start;
        private long time;
        private long markedTime;
        private int count;
        private int markedCount;
        final Profiler this$0;
        
        LiveSection(final Profiler this$0, final String s, final int cursor) {
            this.this$0 = this$0.super(s);
            this.cursor = 0;
            this.times = new long[0];
            this.start = 0L;
            this.cursor = cursor;
        }
        
        @Override
        Section start() {
            this.start = System.currentTimeMillis();
            return this;
        }
        
        @Override
        protected Section stop() {
            if (this.start > 0L) {
                this.time += System.currentTimeMillis() - this.start;
            }
            this.start = 0L;
            ++this.count;
            return this;
        }
        
        @Override
        public Section end() {
            this.stop();
            if (!this.invalidated) {
                this.this$0.end(this);
            }
            return this;
        }
        
        @Override
        void mark() {
            if (this.cursor >= this.times.length) {
                this.times = Arrays.copyOf(this.times, this.cursor + 4);
            }
            this.times[this.cursor] = this.time;
            this.markedTime += this.time;
            this.markedCount += this.count;
            this.time = 0L;
            this.count = 0;
            ++this.cursor;
        }
        
        @Override
        public long getTime() {
            return this.time;
        }
        
        @Override
        public long getTotalTime() {
            return this.time + this.markedTime;
        }
        
        @Override
        public double getSeconds() {
            return this.time * 0.001;
        }
        
        @Override
        public double getTotalSeconds() {
            return (this.time + this.markedTime) * 0.001;
        }
        
        @Override
        public long[] getTimes() {
            final long[] array = new long[this.cursor + 1];
            System.arraycopy(this.times, 0, array, 0, Math.min(this.times.length, this.cursor));
            array[this.cursor] = this.time;
            return array;
        }
        
        @Override
        public int getCount() {
            return this.count;
        }
        
        @Override
        public int getTotalCount() {
            return this.count + this.markedCount;
        }
        
        @Override
        public double getAverageTime() {
            return (this.count > 0) ? (this.time / (double)this.count) : 0.0;
        }
        
        @Override
        public double getTotalAverageTime() {
            return (this.count > 0) ? ((this.time + this.markedTime) / (double)(this.count + this.markedCount)) : 0.0;
        }
    }
    
    class SubSection extends LiveSection
    {
        private final String baseName;
        private final Section root;
        final Profiler this$0;
        
        SubSection(final Profiler this$0, final String s, final int n, final String baseName, final Section root) {
            this.this$0 = this$0.super(s, n);
            this.baseName = baseName;
            this.root = root;
        }
        
        @Override
        Section invalidate() {
            this.root.invalidate();
            return super.invalidate();
        }
        
        @Override
        public String getBaseName() {
            return this.baseName;
        }
        
        @Override
        public void setInfo(final String s) {
            this.root.setInfo(s);
            super.setInfo(s);
        }
        
        @Override
        Section getDelegate() {
            return this.root;
        }
        
        @Override
        Section start() {
            this.root.start();
            return super.start();
        }
        
        @Override
        public Section end() {
            this.root.stop();
            return super.end();
        }
        
        @Override
        public Section next(final String s) {
            super.stop();
            return this.root.next(s);
        }
    }
}
