package com.google.common.collect;

import java.util.*;

interface SortedMultisetBridge<E> extends Multiset<E>
{
    SortedSet<E> elementSet();
}
