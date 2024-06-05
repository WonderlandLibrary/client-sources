package hawk.config.util;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

public class ConfigurationAPI {
   public static Configuration newConfiguration(File var0) {
      return new Configuration(var0);
   }

   public static Configuration loadExistingConfiguration(File var0) throws IOException {
      JSONObject var1 = new JSONObject(FileUtils.readFileToString(var0, Charsets.UTF_8));
      return new Configuration(var0, var1.toMap());
   }
}
