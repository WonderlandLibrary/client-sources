package net.minecraft.client.main;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

final class Main$1 extends Authenticator {
   final String val$s1;
   final String val$s2;

   Main$1(String var1, String var2) {
      this.val$s1 = var1;
      this.val$s2 = var2;
   }

   protected PasswordAuthentication getPasswordAuthentication() {
      return new PasswordAuthentication(this.val$s1, this.val$s2.toCharArray());
   }
}
