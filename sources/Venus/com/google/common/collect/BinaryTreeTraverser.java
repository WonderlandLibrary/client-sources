/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.PeekingIterator;
import com.google.common.collect.TreeTraverser;
import com.google.common.collect.UnmodifiableIterator;
import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.Deque;
import java.util.Iterator;
import java.util.function.Consumer;

@Beta
@GwtCompatible
public abstract class BinaryTreeTraverser<T>
extends TreeTraverser<T> {
    public abstract Optional<T> leftChild(T var1);

    public abstract Optional<T> rightChild(T var1);

    @Override
    public final Iterable<T> children(T t) {
        Preconditions.checkNotNull(t);
        return new FluentIterable<T>(this, t){
            final Object val$root;
            final BinaryTreeTraverser this$0;
            {
                this.this$0 = binaryTreeTraverser;
                this.val$root = object;
            }

            @Override
            public Iterator<T> iterator() {
                return new AbstractIterator<T>(this){
                    boolean doneLeft;
                    boolean doneRight;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                    }

                    @Override
                    protected T computeNext() {
                        Optional<Object> optional;
                        if (!this.doneLeft) {
                            this.doneLeft = true;
                            optional = this.this$1.this$0.leftChild(this.this$1.val$root);
                            if (optional.isPresent()) {
                                return optional.get();
                            }
                        }
                        if (!this.doneRight) {
                            this.doneRight = true;
                            optional = this.this$1.this$0.rightChild(this.this$1.val$root);
                            if (optional.isPresent()) {
                                return optional.get();
                            }
                        }
                        return this.endOfData();
                    }
                };
            }

            @Override
            public void forEach(Consumer<? super T> consumer) {
                BinaryTreeTraverser.access$000(consumer, this.this$0.leftChild(this.val$root));
                BinaryTreeTraverser.access$000(consumer, this.this$0.rightChild(this.val$root));
            }
        };
    }

    @Override
    UnmodifiableIterator<T> preOrderIterator(T t) {
        return new PreOrderIterator(this, t);
    }

    @Override
    UnmodifiableIterator<T> postOrderIterator(T t) {
        return new PostOrderIterator(this, t);
    }

    public final FluentIterable<T> inOrderTraversal(T t) {
        Preconditions.checkNotNull(t);
        return new FluentIterable<T>(this, t){
            final Object val$root;
            final BinaryTreeTraverser this$0;
            {
                this.this$0 = binaryTreeTraverser;
                this.val$root = object;
            }

            @Override
            public UnmodifiableIterator<T> iterator() {
                return new InOrderIterator(this.this$0, this.val$root);
            }

            @Override
            public void forEach(Consumer<? super T> consumer) {
                Preconditions.checkNotNull(consumer);
                new Consumer<T>(this, consumer){
                    final Consumer val$action;
                    final 2 this$1;
                    {
                        this.this$1 = var1_1;
                        this.val$action = consumer;
                    }

                    @Override
                    public void accept(T t) {
                        BinaryTreeTraverser.access$000(this, this.this$1.this$0.leftChild(t));
                        this.val$action.accept(t);
                        BinaryTreeTraverser.access$000(this, this.this$1.this$0.rightChild(t));
                    }
                }.accept(this.val$root);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }

    private static <T> void pushIfPresent(Deque<T> deque, Optional<T> optional) {
        if (optional.isPresent()) {
            deque.addLast(optional.get());
        }
    }

    private static <T> void acceptIfPresent(Consumer<? super T> consumer, Optional<T> optional) {
        if (optional.isPresent()) {
            consumer.accept(optional.get());
        }
    }

    static void access$000(Consumer consumer, Optional optional) {
        BinaryTreeTraverser.acceptIfPresent(consumer, optional);
    }

    static void access$100(Deque deque, Optional optional) {
        BinaryTreeTraverser.pushIfPresent(deque, optional);
    }

    private final class InOrderIterator
    extends AbstractIterator<T> {
        private final Deque<T> stack;
        private final BitSet hasExpandedLeft;
        final BinaryTreeTraverser this$0;

        InOrderIterator(BinaryTreeTraverser binaryTreeTraverser, T t) {
            this.this$0 = binaryTreeTraverser;
            this.stack = new ArrayDeque(8);
            this.hasExpandedLeft = new BitSet();
            this.stack.addLast(t);
        }

        @Override
        protected T computeNext() {
            while (!this.stack.isEmpty()) {
                Object t = this.stack.getLast();
                if (this.hasExpandedLeft.get(this.stack.size() - 1)) {
                    this.stack.removeLast();
                    this.hasExpandedLeft.clear(this.stack.size());
                    BinaryTreeTraverser.access$100(this.stack, this.this$0.rightChild(t));
                    return t;
                }
                this.hasExpandedLeft.set(this.stack.size() - 1);
                BinaryTreeTraverser.access$100(this.stack, this.this$0.leftChild(t));
            }
            return this.endOfData();
        }
    }

    private final class PostOrderIterator
    extends UnmodifiableIterator<T> {
        private final Deque<T> stack;
        private final BitSet hasExpanded;
        final BinaryTreeTraverser this$0;

        PostOrderIterator(BinaryTreeTraverser binaryTreeTraverser, T t) {
            this.this$0 = binaryTreeTraverser;
            this.stack = new ArrayDeque(8);
            this.stack.addLast(t);
            this.hasExpanded = new BitSet();
        }

        @Override
        public boolean hasNext() {
            return !this.stack.isEmpty();
        }

        @Override
        public T next() {
            while (true) {
                Object t = this.stack.getLast();
                boolean bl = this.hasExpanded.get(this.stack.size() - 1);
                if (bl) {
                    this.stack.removeLast();
                    this.hasExpanded.clear(this.stack.size());
                    return t;
                }
                this.hasExpanded.set(this.stack.size() - 1);
                BinaryTreeTraverser.access$100(this.stack, this.this$0.rightChild(t));
                BinaryTreeTraverser.access$100(this.stack, this.this$0.leftChild(t));
            }
        }
    }

    private final class PreOrderIterator
    extends UnmodifiableIterator<T>
    implements PeekingIterator<T> {
        private final Deque<T> stack;
        final BinaryTreeTraverser this$0;

        PreOrderIterator(BinaryTreeTraverser binaryTreeTraverser, T t) {
            this.this$0 = binaryTreeTraverser;
            this.stack = new ArrayDeque(8);
            this.stack.addLast(t);
        }

        @Override
        public boolean hasNext() {
            return !this.stack.isEmpty();
        }

        @Override
        public T next() {
            Object t = this.stack.removeLast();
            BinaryTreeTraverser.access$100(this.stack, this.this$0.rightChild(t));
            BinaryTreeTraverser.access$100(this.stack, this.this$0.leftChild(t));
            return t;
        }

        @Override
        public T peek() {
            return this.stack.getLast();
        }
    }
}

