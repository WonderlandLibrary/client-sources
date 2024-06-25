package net.optifine.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.optifine.render.VboRange;

public class LinkedListTest {
   public static void main(String[] args) throws Exception {
      LinkedList<VboRange> linkedlist = new LinkedList();
      List<VboRange> list = new ArrayList();
      List<VboRange> list1 = new ArrayList();
      Random random = new Random();
      int i = 100;

      int k;
      VboRange vborange3;
      for(k = 0; k < i; ++k) {
         vborange3 = new VboRange();
         vborange3.setPosition(k);
         list.add(vborange3);
      }

      for(k = 0; k < 100000; ++k) {
         checkLists(list, list1, i);
         checkLinkedList(linkedlist, list1.size());
         if (k % 5 == 0) {
            dbgLinkedList(linkedlist);
         }

         LinkedList.Node node2;
         if (random.nextBoolean()) {
            if (!list.isEmpty()) {
               vborange3 = (VboRange)list.get(random.nextInt(list.size()));
               node2 = vborange3.getNode();
               if (random.nextBoolean()) {
                  linkedlist.addFirst(node2);
                  dbg("Add first: " + vborange3.getPosition());
               } else if (random.nextBoolean()) {
                  linkedlist.addLast(node2);
                  dbg("Add last: " + vborange3.getPosition());
               } else {
                  if (list1.isEmpty()) {
                     continue;
                  }

                  VboRange vborange1 = (VboRange)list1.get(random.nextInt(list1.size()));
                  LinkedList.Node<VboRange> node1 = vborange1.getNode();
                  linkedlist.addAfter(node1, node2);
                  dbg("Add after: " + vborange1.getPosition() + ", " + vborange3.getPosition());
               }

               list.remove(vborange3);
               list1.add(vborange3);
            }
         } else if (!list1.isEmpty()) {
            vborange3 = (VboRange)list1.get(random.nextInt(list1.size()));
            node2 = vborange3.getNode();
            linkedlist.remove(node2);
            dbg("Remove: " + vborange3.getPosition());
            list1.remove(vborange3);
            list.add(vborange3);
         }
      }

   }

   private static void dbgLinkedList(LinkedList<VboRange> linkedList) {
      StringBuffer stringbuffer = new StringBuffer();
      linkedList.iterator().forEachRemaining((vboRangeNode) -> {
         if (vboRangeNode.getItem() != null) {
            VboRange vborange = (VboRange)vboRangeNode.getItem();
            if (stringbuffer.length() > 0) {
               stringbuffer.append(", ");
            }

            stringbuffer.append(vborange.getPosition());
         }
      });
      dbg("List: " + stringbuffer);
   }

   private static void checkLinkedList(LinkedList<VboRange> linkedList, int used) {
      if (linkedList.getSize() != used) {
         throw new RuntimeException("Wrong size, linked: " + linkedList.getSize() + ", used: " + used);
      } else {
         int i = 0;

         for(LinkedList.Node node = linkedList.getFirst(); node != null; node = node.getNext()) {
            ++i;
         }

         if (linkedList.getSize() != i) {
            throw new RuntimeException("Wrong count, linked: " + linkedList.getSize() + ", count: " + i);
         } else {
            int j = 0;

            for(LinkedList.Node node1 = linkedList.getLast(); node1 != null; node1 = node1.getPrev()) {
               ++j;
            }

            if (linkedList.getSize() != j) {
               throw new RuntimeException("Wrong count back, linked: " + linkedList.getSize() + ", count: " + j);
            }
         }
      }
   }

   private static void checkLists(List<VboRange> listFree, List<VboRange> listUsed, int count) {
      int i = listFree.size() + listUsed.size();
      if (i != count) {
         throw new RuntimeException("Total size: " + i);
      }
   }

   private static void dbg(String str) {
      System.out.println(str);
   }
}
