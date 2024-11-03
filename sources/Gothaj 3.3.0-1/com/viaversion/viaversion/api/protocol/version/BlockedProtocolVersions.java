package com.viaversion.viaversion.api.protocol.version;

import com.viaversion.viaversion.libs.fastutil.ints.IntSet;

public interface BlockedProtocolVersions {
   boolean contains(int var1);

   int blocksBelow();

   int blocksAbove();

   IntSet singleBlockedVersions();
}
