package exhibition.util.security;

import java.security.Key;
import javax.crypto.Cipher;
import org.apache.commons.codec.binary.Base64;

public class Crypto {
   public static String encrypt(Key key, String text) throws Exception {
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(1, key);
      byte[] encrypted = cipher.doFinal(text.getBytes());
      byte[] encryptedValue = Base64.encodeBase64(encrypted);
      return new String(encryptedValue);
   }

   public static String decrypt(Key key, String text) throws Exception {
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(2, key);
      byte[] decodedBytes = Base64.decodeBase64(text.getBytes());
      byte[] original = cipher.doFinal(decodedBytes);
      return new String(original);
   }

   public static String decryptPrivate(String str) {
      try {
         return decrypt(AuthenticationUtil.getDecrypt(), str);
      } catch (Exception var2) {
         var2.printStackTrace();
         return null;
      }
   }

   public static String decryptPublic(String str) {
      try {
         return decrypt(AuthenticationUtil.getSecret(), str);
      } catch (Exception var2) {
         var2.printStackTrace();
         return null;
      }
   }

   public static String decryptPrivateNew(String str) {
      try {
         return decrypt(AuthenticationUtil.getDecryptNew(), str);
      } catch (Exception var2) {
         var2.printStackTrace();
         return null;
      }
   }

   public static String decryptPublicNew(String str) {
      try {
         return decrypt(AuthenticationUtil.getSecretNew(), str);
      } catch (Exception var2) {
         var2.printStackTrace();
         return null;
      }
   }

   public static byte[] getUserKey(int size) {
      byte[] ret = new byte[size];

      for(int i = 0; i < size; ++i) {
         String yourMom = new String(new byte[]{115, 62, 53, 83, 110, 122, 72, 119, 119, 65, 110, 113, 69, 53, 47, 101, 86, 57, 62, 59, 50, 60, 44, 112, 104, 82, 114, 47, 37, 97, 37, 116, 41, 98, 101, 60, 42, 112, 87, 61, 115, 69, 88, 70, 104, 62, 52, 64, 85, 56, 67, 112, 56, 36, 77, 102, 115, 64, 117, 62, 63, 93, 113, 115, 123, 58, 52, 66, 72, 98, 87, 76, 50, 39, 40, 122, 87, 51, 98, 72, 113, 102, 115, 78, 115, 99, 95, 122, 83, 57, 70, 121, 80, 66, 109, 58, 118, 72, 62, 104, 83, 91, 68, 109, 41, 77, 41, 95, 91, 121, 41, 93, 33, 63, 45, 78, 40, 62, 87, 99, 45, 123, 95, 85, 109, 43, 125});
         ret[i] = (byte)(yourMom.split("(?<=\\G.{4})")[i].hashCode() % 256);
      }

      return ret;
   }

   public static byte[] getUserKeySet(int size) {
      byte[] ret = new byte[size];

      for(int i = 0; i < size; ++i) {
         String str = new String(new byte[]{94, 74, 42, 116, 69, 104, 68, 52, 69, 88, 70, 75, 83, 54, 109, 37, 110, 74, 118, 97, 66, 49, 51, 71, 101, 80, 85, 42, 84, 64, 98, 85, 100, 110, 42, 78, 68, 64, 42, 75, 42, 53, 78, 35, 48, 33, 49, 112, 69, 85, 53, 87, 83, 98, 78, 71, 103, 94, 120, 119, 99, 83, 106, 78, 78, 94, 42, 94, 72, 55, 85, 98, 77, 67, 120, 120, 116, 120, 53, 102, 117, 42, 81, 53, 35, 98, 35, 77, 74, 118, 36, 33, 33, 118, 52, 89, 54, 77, 89, 116, 98, 98, 81, 97, 85, 37, 114, 121, 78, 37, 53, 116, 112, 118, 48, 121, 42, 102, 118, 57});
         ret[i] = (byte)(str.split("(?<=\\G.{4})")[i].hashCode() % 256);
      }

      return ret;
   }

   public static byte[] getUserKeyOLD(int size) {
      byte[] ret = new byte[size];

      for(int i = 0; i < size; ++i) {
         String yourMom = new String(new byte[]{79, 121, 91, 87, 112, 77, 45, 107, 88, 62, 80, 85, 64, 45, 120, 99, 64, 52, 61, 66, 37, 69, 100, 45, 90, 71, 103, 71, 98, 120, 106, 72, 97, 101, 104, 64, 64, 79, 121, 91, 87, 112, 77, 45, 107, 88, 62, 80, 85, 64, 45, 120, 99, 64, 52, 61, 66, 37, 69, 100, 45, 90, 71, 103, 71, 98, 120, 106, 72, 97, 101, 104, 64, 64});
         ret[i] = (byte)(yourMom.split("(?<=\\G.{4})")[i].hashCode() % 256);
      }

      return ret;
   }

   public static byte[] getUserKeySetOLD(int size) {
      byte[] ret = new byte[size];

      for(int i = 0; i < size; ++i) {
         String str = new String(new byte[]{65, 52, 51, 115, 49, 65, 83, 68, 97, 45, 97, 115, 100, 97, 51, 50, 61, 50, 61, 51, 102, 115, 102, 50, 52, 97, 83, 65, 68, 65, 109, 79, 80, 43, 45, 97, 69, 122, 120, 49, 65, 83, 68, 77, 83, 43, 115, 97, 115, 100, 100, 97, 48, 45, 97, 57, 97, 117, 106, 115, 100, 48, 97, 45, 115, 97, 100, 48, 57, 97, 115, 95, 65, 83, 65, 83, 68, 45, 97, 100, 48, 45, 97, 102, 107, 97, 115, 102, 45, 75, 70, 95, 97, 48, 65, 115, 45, 48, 100, 95, 74, 95, 95, 111, 111, 112, 53, 49, 119, 57, 49, 50});
         ret[i] = (byte)(str.split("(?<=\\G.{4})")[i].hashCode() % 256);
      }

      return ret;
   }

   public static Crypto.EnumOS getOSType() {
      String var0 = System.getProperty("os.name").toLowerCase();
      return var0.contains("win") ? Crypto.EnumOS.WINDOWS : (var0.contains("mac") ? Crypto.EnumOS.OSX : (var0.contains("solaris") ? Crypto.EnumOS.SOLARIS : (var0.contains("sunos") ? Crypto.EnumOS.SOLARIS : (var0.contains("linux") ? Crypto.EnumOS.LINUX : (var0.contains("unix") ? Crypto.EnumOS.LINUX : Crypto.EnumOS.UNKNOWN)))));
   }

   public static enum EnumOS {
      LINUX("LINUX", 0),
      SOLARIS("SOLARIS", 1),
      WINDOWS("WINDOWS", 2),
      OSX("OSX", 3),
      UNKNOWN("UNKNOWN", 4);

      private static final Crypto.EnumOS[] $VALUES = new Crypto.EnumOS[]{LINUX, SOLARIS, WINDOWS, OSX, UNKNOWN};

      private EnumOS(String p_i1357_1_, int p_i1357_2_) {
      }
   }
}
