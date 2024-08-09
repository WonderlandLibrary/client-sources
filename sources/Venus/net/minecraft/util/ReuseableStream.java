/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ReuseableStream<T> {
    private final List<T> cachedValues = Lists.newArrayList();
    private final Spliterator<T> spliterator;

    public ReuseableStream(Stream<T> stream) {
        this.spliterator = stream.spliterator();
    }

    public Stream<T> createStream() {
        return StreamSupport.stream(new Spliterators.AbstractSpliterator<T>(this, Long.MAX_VALUE, 0){
            private int nextIdx;
            final ReuseableStream this$0;
            {
                this.this$0 = reuseableStream;
                super(l, n);
            }

            @Override
            public boolean tryAdvance(Consumer<? super T> consumer) {
                while (this.nextIdx >= this.this$0.cachedValues.size()) {
                    if (this.this$0.spliterator.tryAdvance(this.this$0.cachedValues::add)) continue;
                    return true;
                }
                consumer.accept(this.this$0.cachedValues.get(this.nextIdx++));
                return false;
            }
        }, false);
    }
}

