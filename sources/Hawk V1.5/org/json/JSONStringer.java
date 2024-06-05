package org.json;

import java.io.StringWriter;

public class JSONStringer extends JSONWriter {
   public String toString() {
      return this.mode == 'd' ? this.writer.toString() : null;
   }

   public JSONStringer() {
      super(new StringWriter());
   }
}
