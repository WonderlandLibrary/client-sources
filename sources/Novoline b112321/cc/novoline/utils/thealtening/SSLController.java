package cc.novoline.utils.thealtening;

import cc.novoline.utils.thealtening.SSLController$1;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class SSLController {
   static final Logger log = LogManager.getLogger();
   private final SSLSocketFactory allTrustingFactory;
   private final SSLSocketFactory originalFactory;
   private final HostnameVerifier originalHostVerifier;
   private static final TrustManager[] ALL_TRUSTING_TRUST_MANAGER = new TrustManager[]{new SSLController$1()};
   private static final HostnameVerifier ALTENING_HOSTING_VERIFIER = SSLController::lambda$static$0;
   private static int e;

   public SSLController() throws KeyManagementException, NoSuchAlgorithmException {
      SSLContext var1 = SSLContext.getInstance("SSL");
      var1.init((KeyManager[])null, ALL_TRUSTING_TRUST_MANAGER, new SecureRandom());
      this.allTrustingFactory = var1.getSocketFactory();
      this.originalFactory = HttpsURLConnection.getDefaultSSLSocketFactory();
      this.originalHostVerifier = HttpsURLConnection.getDefaultHostnameVerifier();
   }

   public void enableCertificateValidation() {
      this.updateCertificateValidation(this.originalFactory, this.originalHostVerifier);
   }

   public void disableCertificateValidation() {
      this.updateCertificateValidation(this.allTrustingFactory, ALTENING_HOSTING_VERIFIER);
   }

   private void updateCertificateValidation(SSLSocketFactory var1, HostnameVerifier var2) {
      HttpsURLConnection.setDefaultSSLSocketFactory(var1);
      HttpsURLConnection.setDefaultHostnameVerifier(var2);
   }

   private static boolean lambda$static$0(String var0, SSLSession var1) {
      int var2 = b();
      return var0.equals("authserver.thealtening.com") || var0.equals("sessionserver.thealtening.com");
   }

   static {
      b(15);
   }

   public static void b(int var0) {
      e = var0;
   }

   public static int b() {
      return e;
   }

   public static int e() {
      int var0 = b();
      return 29;
   }
}
