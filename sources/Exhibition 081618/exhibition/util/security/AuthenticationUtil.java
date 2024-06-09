package exhibition.util.security;

import exhibition.Client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.Consumer;
import javax.crypto.spec.SecretKeySpec;

public class AuthenticationUtil {
   public static int authListPos;

   public static String getHwid() throws Exception {
      String s = "";
      String main = (System.getenv(Crypto.decryptPrivate("C1KvKeE8A2WKZ90JD4HBy0PLTIwGdQ9SstTcxiaUikc=")) + System.getenv(Crypto.decryptPrivate("SgSyMcFiuys0Jwjdoz6bSw==")) + System.getProperty(Crypto.decryptPrivate("+uP3OUfHhbnQH28rONuMNw=="))).trim();
      byte[] bytes = main.getBytes("UTF-8");
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] md2 = md.digest(bytes);
      int i = 0;
      byte[] array = md2;
      int length = md2.length;

      for(int j = 0; j < length; ++j) {
         byte b = array[j];
         s = s + Integer.toHexString(b & 255 | 256).substring(0, 3);
         if (i != md2.length - 1) {
            s = s + "";
         }

         ++i;
      }

      return s;
   }

   public static SecretKeySpec getDecrypt() {
      byte[] secret = Crypto.getUserKeySetOLD(16);
      return new SecretKeySpec(secret, 0, secret.length, "AES");
   }

   public static SecretKeySpec getSecret() {
      byte[] secret = Crypto.getUserKeyOLD(16);
      return new SecretKeySpec(secret, 0, secret.length, "AES");
   }

   public static SecretKeySpec getDecryptNew() {
      byte[] secret = Crypto.getUserKeySet(16);
      return new SecretKeySpec(secret, 0, secret.length, "AES");
   }

   public static SecretKeySpec getSecretNew() {
      byte[] secret = Crypto.getUserKey(16);
      return new SecretKeySpec(secret, 0, secret.length, "AES");
   }

   public static String isAuth(String encryptedUsername, String encryptedPassword, String hashedUsername, String hashedPassword) {
	  Client.authUser = new AuthenticatedUser(new String[]{Crypto.decryptPublicNew(encryptedUsername), Crypto.decryptPublicNew(encryptedPassword), encryptedUsername, encryptedPassword});
	  AuthenticatedUser authenticatedUser = Client.authUser;
      return authenticatedUser != null && authenticatedUser.equals(Client.authUser) ? Client.authUser.getDecryptedUsername() + Client.authUser.getDecryptedPassword() : null;
   }

   public static String getMD5Hash(String text) throws NoSuchAlgorithmException {
      MessageDigest digest = MessageDigest.getInstance("MD5");
      byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
      return bytesToHex(hash);
   }

   private static String bytesToHex(byte[] hash) {
      StringBuffer hexString = new StringBuffer();

      for(int i = 0; i < hash.length; ++i) {
         String hex = Integer.toHexString(255 & hash[i]);
         if (hex.length() == 1) {
            hexString.append('0');
         }

         hexString.append(hex);
      }

      return hexString.toString();
   }
}
