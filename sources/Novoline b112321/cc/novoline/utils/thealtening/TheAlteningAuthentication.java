package cc.novoline.utils.thealtening;

import cc.novoline.utils.thealtening.SSLController;
import cc.novoline.utils.thealtening.TheAlteningAuthentication$1;
import cc.novoline.utils.thealtening.service.AlteningServiceType;
import cc.novoline.utils.thealtening.service.ServiceSwitcher;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public final class TheAlteningAuthentication {
   private static TheAlteningAuthentication instance;
   private final ServiceSwitcher serviceSwitcher = new ServiceSwitcher();
   private final SSLController sslController = new SSLController();
   private AlteningServiceType service;

   private TheAlteningAuthentication(AlteningServiceType var1) throws Throwable {
      this.updateService(var1);
   }

   public void updateService(AlteningServiceType var1) {
      if(this.service != var1) {
         switch(TheAlteningAuthentication$1.$SwitchMap$cc$novoline$utils$thealtening$service$AlteningServiceType[var1.ordinal()]) {
         case 1:
            this.sslController.enableCertificateValidation();
            break;
         case 2:
            this.sslController.disableCertificateValidation();
         }

         this.service = this.serviceSwitcher.switchToService(var1);
      }
   }

   public static TheAlteningAuthentication mojang() {
      return withService(AlteningServiceType.MOJANG);
   }

   public static TheAlteningAuthentication theAltening() {
      return withService(AlteningServiceType.THEALTENING);
   }

   private static TheAlteningAuthentication withService(AlteningServiceType var0) {
      if(instance == null) {
         try {
            instance = new TheAlteningAuthentication(var0);
         } catch (KeyManagementException | NoSuchAlgorithmException var2) {
            SSLController.log.warn(var2);
         } catch (Throwable var3) {
            SSLController.log.warn("Unexpected error occurred while executing...", var3);
         }
      } else if(instance.getService() != var0) {
         instance.updateService(var0);
      }

      return instance;
   }

   public AlteningServiceType getService() {
      return this.service;
   }

   private static NoSuchAlgorithmException a(NoSuchAlgorithmException var0) {
      return var0;
   }
}
