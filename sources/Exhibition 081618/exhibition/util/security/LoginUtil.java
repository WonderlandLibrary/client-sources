package exhibition.util.security;

import exhibition.util.FileUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LoginUtil {
   private static final File LOGIN = FileUtils.getConfigFile("Data");

   public static void saveLogin(String encryptedUsername, String encryptedPassword) {
      List fileContent = new ArrayList();
      fileContent.add(encryptedUsername);
      fileContent.add(encryptedPassword);
      FileUtils.write(LOGIN, fileContent, true);
   }

   public static List getLoginInformation() {
      return FileUtils.read(LOGIN);
   }
}
