/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import java.util.Iterator;

public class LinkedList<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public void addFirst(Node<T> node) {
        this.checkNoParent(node);
        if (this.isEmpty()) {
            this.first = node;
            this.last = node;
        } else {
            Node<T> node2 = this.first;
            node.setNext(node2);
            node2.setPrev(node);
            this.first = node;
        }
        node.setParent(this);
        ++this.size;
    }

    public void addLast(Node<T> node) {
        this.checkNoParent(node);
        if (this.isEmpty()) {
            this.first = node;
            this.last = node;
        } else {
            Node<T> node2 = this.last;
            node.setPrev(node2);
            node2.setNext(node);
            this.last = node;
        }
        node.setParent(this);
        ++this.size;
    }

    public void addAfter(Node<T> node, Node<T> node2) {
        if (node == null) {
            this.addFirst(node2);
        } else if (node == this.last) {
            this.addLast(node2);
        } else {
            this.checkParent(node);
            this.checkNoParent(node2);
            Node<T> node3 = node.getNext();
            node.setNext(node2);
            node2.setPrev(node);
            node3.setPrev(node2);
            node2.setNext(node3);
            node2.setParent(this);
            ++this.size;
        }
    }

    public Node<T> remove(Node<T> node) {
        this.checkParent(node);
        Node<T> node2 = node.getPrev();
        Node<T> node3 = node.getNext();
        if (node2 != null) {
            node2.setNext(node3);
        } else {
            this.first = node3;
        }
        if (node3 != null) {
            node3.setPrev(node2);
        } else {
            this.last = node2;
        }
        node.setPrev(null);
        node.setNext(null);
        node.setParent(null);
        --this.size;
        return node;
    }

    public void moveAfter(Node<T> node, Node<T> node2) {
        this.remove(node2);
        this.addAfter(node, node2);
    }

    public boolean find(Node<T> node, Node<T> node2, Node<T> node3) {
        Node<T> node4;
        this.checkParent(node2);
        if (node3 != null) {
            this.checkParent(node3);
        }
        for (node4 = node2; node4 != null && node4 != node3; node4 = node4.getNext()) {
            if (node4 != node) continue;
            return false;
        }
        if (node4 != node3) {
            throw new IllegalArgumentException("Sublist is not linked, from: " + node2 + ", to: " + node3);
        }
        return true;
    }

    private void checkParent(Node<T> node) {
        if (node.parent != this) {
            throw new IllegalArgumentException("Node has different parent, node: " + node + ", parent: " + node.parent + ", this: " + this);
        }
    }

    private void checkNoParent(Node<T> node) {
        if (node.parent != null) {
            throw new IllegalArgumentException("Node has different parent, node: " + node + ", parent: " + node.parent + ", this: " + this);
        }
    }

    public boolean contains(Node<T> node) {
        return node.parent == this;
    }

    public Iterator<Node<T>> iterator() {
        return new Iterator<Node<T>>(this){
            Node<T> node;
            final LinkedList this$0;
            {
                this.this$0 = linkedList;
                this.node = this.this$0.getFirst();
            }

            @Override
            public boolean hasNext() {
                return this.node != null;
            }

            @Override
            public Node<T> next() {
                Node node = this.node;
                if (this.node != null) {
                    this.node = this.node.next;
                }
                return node;
            }

            @Override
            public Object next() {
                return this.next();
            }
        };
    }

    public Node<T> getFirst() {
        return this.first;
    }

    public Node<T> getLast() {
        return this.last;
    }

    public int getSize() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size <= 0;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        Iterator<Node<T>> iterator2 = this.iterator();
        while (iterator2.hasNext()) {
            Node<T> node = iterator2.next();
            if (stringBuffer.length() > 0) {
                stringBuffer.append(", ");
            }
            stringBuffer.append(node.getItem());
        }
        return this.size + " [" + stringBuffer.toString() + "]";
    }

    public static class Node<T> {
        private final T item;
        private Node<T> prev;
        private Node<T> next;
        private LinkedList<T> parent;

        public Node(T t) {
            this.item = t;
        }

        public T getItem() {
            return this.item;
        }

        public Node<T> getPrev() {
            return this.prev;
        }

        public Node<T> getNext() {
            return this.next;
        }

        private void setPrev(Node<T> node) {
            this.prev = node;
        }

        private void setNext(Node<T> node) {
            this.next = node;
        }

        private void setParent(LinkedList<T> linkedList) {
            this.parent = linkedList;
        }

        public String toString() {
            return "" + this.item;
        }
    }
}

