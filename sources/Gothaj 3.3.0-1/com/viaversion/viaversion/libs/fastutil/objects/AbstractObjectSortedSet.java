package com.viaversion.viaversion.libs.fastutil.objects;

public abstract class AbstractObjectSortedSet<K> extends AbstractObjectSet<K> implements ObjectSortedSet<K> {
   protected AbstractObjectSortedSet() {
   }

   @Override
   public abstract ObjectBidirectionalIterator<K> iterator();
}
