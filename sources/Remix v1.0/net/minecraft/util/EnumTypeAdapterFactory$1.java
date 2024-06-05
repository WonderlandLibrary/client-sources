package net.minecraft.util;

import com.google.gson.*;
import java.util.*;
import java.io.*;
import com.google.gson.stream.*;

class EnumTypeAdapterFactory$1 extends TypeAdapter {
    final /* synthetic */ HashMap val$var4;
    
    public void write(final JsonWriter p_write_1_, final Object p_write_2_) throws IOException {
        if (p_write_2_ == null) {
            p_write_1_.nullValue();
        }
        else {
            p_write_1_.value(EnumTypeAdapterFactory.access$000(EnumTypeAdapterFactory.this, p_write_2_));
        }
    }
    
    public Object read(final JsonReader p_read_1_) throws IOException {
        if (p_read_1_.peek() == JsonToken.NULL) {
            p_read_1_.nextNull();
            return null;
        }
        return this.val$var4.get(p_read_1_.nextString());
    }
}