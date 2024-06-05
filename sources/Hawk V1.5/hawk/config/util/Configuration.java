package hawk.config.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class Configuration {
   public Map<String, Object> options;
   private File file;

   public void set(String var1, Object var2) {
      this.options.put(var1, var2);
   }

   public Object get(String var1) {
      return this.options.get(var1);
   }

   public Configuration(File var1) {
      this.file = var1;
      this.options = new HashMap();
   }

   public File getFile() {
      return this.file;
   }

   public void save() throws IOException {
      JSONObject var1 = new JSONObject(this.options);
      this.file.createNewFile();
      FileWriter var2 = new FileWriter(this.file);
      var2.write(var1.toString());
      var2.flush();
      var2.close();
   }

   public Configuration(File var1, Map<String, Object> var2) {
      this.file = var1;
      this.options = var2;
   }
}
