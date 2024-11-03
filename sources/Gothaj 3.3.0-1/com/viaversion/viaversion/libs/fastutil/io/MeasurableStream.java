package com.viaversion.viaversion.libs.fastutil.io;

import java.io.IOException;

public interface MeasurableStream {
   long length() throws IOException;

   long position() throws IOException;
}
