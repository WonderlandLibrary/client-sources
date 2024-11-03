package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import java.io.IOException;

public interface ToNumberStrategy {
   Number readNumber(JsonReader var1) throws IOException;
}
