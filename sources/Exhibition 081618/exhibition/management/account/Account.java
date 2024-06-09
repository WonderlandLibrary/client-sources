package exhibition.management.account;

import com.google.gson.annotations.Expose;
import exhibition.management.Saveable;
import exhibition.management.account.relation.UserStatus;
import exhibition.util.security.Crypto;
import java.util.HashMap;
import javax.crypto.spec.SecretKeySpec;

public class Account extends Saveable {
   @Expose
   private String id;
   @Expose
   private String capeURL;
   @Expose
   private boolean premium;
   @Expose
   private HashMap relationships = new HashMap();
   @Expose
   private HashMap aliases = new HashMap();
   private String loginUser;
   private String loginPass;
   private String display;
   @Expose
   private String cryptoUser;
   @Expose
   private String cryptoPass;
   @Expose
   private String cryptoDisplay;

   public Account(String id) {
      this.id = id;
      this.load();
   }

   public Account(String loginUser, String loginPass) {
      this.loginUser = loginUser;
      this.loginPass = loginPass;
      this.display = loginUser;
      this.premium = true;
      this.id = loginUser.contains("@") ? loginUser.substring(0, loginUser.indexOf("@")) : loginUser;
      this.updateCrypto();
   }

   public Saveable load() {
      Account loaded = (Account)super.load();

      try {
         this.id = loaded.getID();
         this.loginUser = Crypto.decrypt(this.getSecret(), loaded.getCryptoUser());
         this.loginPass = Crypto.decrypt(this.getSecret(), loaded.getCryptoPass());
         this.display = Crypto.decrypt(this.getSecret(), loaded.getCryptoDisplay());
         this.premium = loaded.premium;
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      this.updateCrypto();
      return this;
   }

   private SecretKeySpec getSecret() {
      byte[] secret = Crypto.getUserKey(16);
      return new SecretKeySpec(secret, 0, secret.length, "AES");
   }

   public String getUser() {
      return this.loginUser;
   }

   public String getPass() {
      return this.loginPass;
   }

   public String getDisplay() {
      return this.display;
   }

   public String getID() {
      return this.id;
   }

   public String getCapeURL() {
      return this.capeURL;
   }

   public boolean hasCape() {
      return this.capeURL != null && this.capeURL.length() > 7;
   }

   public void setPremium(boolean premium) {
      this.premium = premium;
   }

   public boolean isPremium() {
      return this.premium;
   }

   public HashMap getAliases() {
      return this.aliases;
   }

   public HashMap getRelationships() {
      return this.relationships;
   }

   public void setAlias(String username, String newName) {
      if (this.aliases.containsKey(username)) {
         this.aliases.remove(username);
      }

      this.aliases.put(username, newName);
   }

   public String getAliasedText(String username, String text) {
      return text.replace(username, (CharSequence)this.aliases.get(username));
   }

   public UserStatus getRelation(String username) {
      return !this.relationships.containsKey(username) ? (UserStatus)this.relationships.get(username) : null;
   }

   public void setRelationships(HashMap relationships) {
      this.relationships = relationships;
   }

   public void setPass(String loginPass) {
      this.loginPass = loginPass;
   }

   public String getCryptoUser() {
      return this.cryptoUser;
   }

   public String getCryptoPass() {
      return this.cryptoPass;
   }

   public String getCryptoDisplay() {
      return this.cryptoDisplay;
   }

   public void setUser(String loginUser) {
      this.loginUser = loginUser;
   }

   public void setDisplay(String display) {
      this.display = display;
   }

   public void updateCrypto() {
      try {
         this.cryptoUser = Crypto.encrypt(this.getSecret(), this.loginUser);
         this.cryptoPass = Crypto.encrypt(this.getSecret(), this.loginPass);
         this.cryptoDisplay = Crypto.encrypt(this.getSecret(), this.display);
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public String getFileName() {
      return this.id + ".json";
   }
}
