/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.optifine.render.VboRange;
import net.optifine.util.LinkedList;

public class LinkedListTest {
    public static void main(String[] stringArray) throws Exception {
        VboRange vboRange;
        int n;
        LinkedList<VboRange> linkedList = new LinkedList<VboRange>();
        ArrayList<VboRange> arrayList = new ArrayList<VboRange>();
        ArrayList<VboRange> arrayList2 = new ArrayList<VboRange>();
        Random random2 = new Random();
        int n2 = 100;
        for (n = 0; n < n2; ++n) {
            vboRange = new VboRange();
            vboRange.setPosition(n);
            arrayList.add(vboRange);
        }
        for (n = 0; n < 100000; ++n) {
            LinkedList.Node<VboRange> node;
            LinkedListTest.checkLists(arrayList, arrayList2, n2);
            LinkedListTest.checkLinkedList(linkedList, arrayList2.size());
            if (n % 5 == 0) {
                LinkedListTest.dbgLinkedList(linkedList);
            }
            if (random2.nextBoolean()) {
                if (arrayList.isEmpty()) continue;
                vboRange = (VboRange)arrayList.get(random2.nextInt(arrayList.size()));
                node = vboRange.getNode();
                if (random2.nextBoolean()) {
                    linkedList.addFirst(node);
                    LinkedListTest.dbg("Add first: " + vboRange.getPosition());
                } else if (random2.nextBoolean()) {
                    linkedList.addLast(node);
                    LinkedListTest.dbg("Add last: " + vboRange.getPosition());
                } else {
                    if (arrayList2.isEmpty()) continue;
                    VboRange vboRange2 = (VboRange)arrayList2.get(random2.nextInt(arrayList2.size()));
                    LinkedList.Node<VboRange> node2 = vboRange2.getNode();
                    linkedList.addAfter(node2, node);
                    LinkedListTest.dbg("Add after: " + vboRange2.getPosition() + ", " + vboRange.getPosition());
                }
                arrayList.remove(vboRange);
                arrayList2.add(vboRange);
                continue;
            }
            if (arrayList2.isEmpty()) continue;
            vboRange = (VboRange)arrayList2.get(random2.nextInt(arrayList2.size()));
            node = vboRange.getNode();
            linkedList.remove(node);
            LinkedListTest.dbg("Remove: " + vboRange.getPosition());
            arrayList2.remove(vboRange);
            arrayList.add(vboRange);
        }
    }

    private static void dbgLinkedList(LinkedList<VboRange> linkedList) {
        StringBuffer stringBuffer = new StringBuffer();
        Iterator<LinkedList.Node<VboRange>> iterator2 = linkedList.iterator();
        while (iterator2.hasNext()) {
            LinkedList.Node<VboRange> node = iterator2.next();
            VboRange vboRange = node.getItem();
            if (stringBuffer.length() > 0) {
                stringBuffer.append(", ");
            }
            stringBuffer.append(vboRange.getPosition());
        }
        LinkedListTest.dbg("List: " + stringBuffer);
    }

    private static void checkLinkedList(LinkedList<VboRange> linkedList, int n) {
        if (linkedList.getSize() != n) {
            throw new RuntimeException("Wrong size, linked: " + linkedList.getSize() + ", used: " + n);
        }
        int n2 = 0;
        for (LinkedList.Node<VboRange> node = linkedList.getFirst(); node != null; node = node.getNext()) {
            ++n2;
        }
        if (linkedList.getSize() != n2) {
            throw new RuntimeException("Wrong count, linked: " + linkedList.getSize() + ", count: " + n2);
        }
        int n3 = 0;
        for (LinkedList.Node<VboRange> node = linkedList.getLast(); node != null; node = node.getPrev()) {
            ++n3;
        }
        if (linkedList.getSize() != n3) {
            throw new RuntimeException("Wrong count back, linked: " + linkedList.getSize() + ", count: " + n3);
        }
    }

    private static void checkLists(List<VboRange> list, List<VboRange> list2, int n) {
        int n2 = list.size() + list2.size();
        if (n2 != n) {
            throw new RuntimeException("Total size: " + n2);
        }
    }

    private static void dbg(String string) {
        System.out.println(string);
    }
}

