package com.viaversion.viaversion.libs.fastutil.io;

import java.io.IOException;

public interface RepositionableStream {
   void position(long var1) throws IOException;

   long position() throws IOException;
}
