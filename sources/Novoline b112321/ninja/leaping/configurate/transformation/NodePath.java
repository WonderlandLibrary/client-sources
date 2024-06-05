package ninja.leaping.configurate.transformation;

import java.util.Iterator;

public interface NodePath extends Iterable {
   Object get(int var1);

   int size();

   Object[] getArray();

   Iterator iterator();
}
