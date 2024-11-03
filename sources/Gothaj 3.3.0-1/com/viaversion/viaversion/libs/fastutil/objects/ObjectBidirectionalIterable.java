package com.viaversion.viaversion.libs.fastutil.objects;

public interface ObjectBidirectionalIterable<K> extends ObjectIterable<K> {
   ObjectBidirectionalIterator<K> iterator();
}
