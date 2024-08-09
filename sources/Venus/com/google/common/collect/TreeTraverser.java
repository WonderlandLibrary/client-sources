/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;
import com.google.common.collect.UnmodifiableIterator;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Queue;
import java.util.function.Consumer;

@Beta
@GwtCompatible
public abstract class TreeTraverser<T> {
    public static <T> TreeTraverser<T> using(Function<T, ? extends Iterable<T>> function) {
        Preconditions.checkNotNull(function);
        return new TreeTraverser<T>(function){
            final Function val$nodeToChildrenFunction;
            {
                this.val$nodeToChildrenFunction = function;
            }

            @Override
            public Iterable<T> children(T t) {
                return (Iterable)this.val$nodeToChildrenFunction.apply(t);
            }
        };
    }

    public abstract Iterable<T> children(T var1);

    public final FluentIterable<T> preOrderTraversal(T t) {
        Preconditions.checkNotNull(t);
        return new FluentIterable<T>(this, t){
            final Object val$root;
            final TreeTraverser this$0;
            {
                this.this$0 = treeTraverser;
                this.val$root = object;
            }

            @Override
            public UnmodifiableIterator<T> iterator() {
                return this.this$0.preOrderIterator(this.val$root);
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
                        this.val$action.accept(t);
                        this.this$1.this$0.children(t).forEach(this);
                    }
                }.accept(this.val$root);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }

    UnmodifiableIterator<T> preOrderIterator(T t) {
        return new PreOrderIterator(this, t);
    }

    public final FluentIterable<T> postOrderTraversal(T t) {
        Preconditions.checkNotNull(t);
        return new FluentIterable<T>(this, t){
            final Object val$root;
            final TreeTraverser this$0;
            {
                this.this$0 = treeTraverser;
                this.val$root = object;
            }

            @Override
            public UnmodifiableIterator<T> iterator() {
                return this.this$0.postOrderIterator(this.val$root);
            }

            @Override
            public void forEach(Consumer<? super T> consumer) {
                Preconditions.checkNotNull(consumer);
                new Consumer<T>(this, consumer){
                    final Consumer val$action;
                    final 3 this$1;
                    {
                        this.this$1 = var1_1;
                        this.val$action = consumer;
                    }

                    @Override
                    public void accept(T t) {
                        this.this$1.this$0.children(t).forEach(this);
                        this.val$action.accept(t);
                    }
                }.accept(this.val$root);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }

    UnmodifiableIterator<T> postOrderIterator(T t) {
        return new PostOrderIterator(this, t);
    }

    public final FluentIterable<T> breadthFirstTraversal(T t) {
        Preconditions.checkNotNull(t);
        return new FluentIterable<T>(this, t){
            final Object val$root;
            final TreeTraverser this$0;
            {
                this.this$0 = treeTraverser;
                this.val$root = object;
            }

            @Override
            public UnmodifiableIterator<T> iterator() {
                return new BreadthFirstIterator(this.this$0, this.val$root);
            }

            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }

    private final class BreadthFirstIterator
    extends UnmodifiableIterator<T>
    implements PeekingIterator<T> {
        private final Queue<T> queue;
        final TreeTraverser this$0;

        BreadthFirstIterator(TreeTraverser treeTraverser, T t) {
            this.this$0 = treeTraverser;
            this.queue = new ArrayDeque();
            this.queue.add(t);
        }

        @Override
        public boolean hasNext() {
            return !this.queue.isEmpty();
        }

        @Override
        public T peek() {
            return this.queue.element();
        }

        @Override
        public T next() {
            Object t = this.queue.remove();
            Iterables.addAll(this.queue, this.this$0.children(t));
            return t;
        }
    }

    private final class PostOrderIterator
    extends AbstractIterator<T> {
        private final ArrayDeque<PostOrderNode<T>> stack;
        final TreeTraverser this$0;

        PostOrderIterator(TreeTraverser treeTraverser, T t) {
            this.this$0 = treeTraverser;
            this.stack = new ArrayDeque();
            this.stack.addLast(this.expand(t));
        }

        @Override
        protected T computeNext() {
            while (!this.stack.isEmpty()) {
                PostOrderNode postOrderNode = this.stack.getLast();
                if (postOrderNode.childIterator.hasNext()) {
                    Object t = postOrderNode.childIterator.next();
                    this.stack.addLast(this.expand(t));
                    continue;
                }
                this.stack.removeLast();
                return postOrderNode.root;
            }
            return this.endOfData();
        }

        private PostOrderNode<T> expand(T t) {
            return new PostOrderNode(t, this.this$0.children(t).iterator());
        }
    }

    private static final class PostOrderNode<T> {
        final T root;
        final Iterator<T> childIterator;

        PostOrderNode(T t, Iterator<T> iterator2) {
            this.root = Preconditions.checkNotNull(t);
            this.childIterator = Preconditions.checkNotNull(iterator2);
        }
    }

    private final class PreOrderIterator
    extends UnmodifiableIterator<T> {
        private final Deque<Iterator<T>> stack;
        final TreeTraverser this$0;

        PreOrderIterator(TreeTraverser treeTraverser, T t) {
            this.this$0 = treeTraverser;
            this.stack = new ArrayDeque();
            this.stack.addLast(Iterators.singletonIterator(Preconditions.checkNotNull(t)));
        }

        @Override
        public boolean hasNext() {
            return !this.stack.isEmpty();
        }

        @Override
        public T next() {
            Iterator iterator2;
            Iterator iterator3 = this.stack.getLast();
            Object t = Preconditions.checkNotNull(iterator3.next());
            if (!iterator3.hasNext()) {
                this.stack.removeLast();
            }
            if ((iterator2 = this.this$0.children(t).iterator()).hasNext()) {
                this.stack.addLast(iterator2);
            }
            return t;
        }
    }
}

