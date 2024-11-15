package exhibition.management.account;

import exhibition.management.AbstractManager;
import java.io.File;

public class AccountManager extends AbstractManager {
   private Account current;

   public AccountManager(Class clazz) {
      super(clazz, 0);
   }

   public void setup() {
      File accountDir = this.getAccountDir();
      if (accountDir.isDirectory()) {
         File[] accountFiles = accountDir.listFiles();
         int accounts = accountFiles.length;
         int i = 0;
         this.reset(accounts);
         File[] var5 = accountFiles;
         int var6 = accountFiles.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            File accountFile = var5[var7];
            Account account = new Account(accountFile.getName().substring(0, accountFile.getName().indexOf(".")));
            account.load();
            ((Account[])this.array)[i] = account;
            ++i;
         }
      }

   }

   public void reload() {
      Account[] var1 = (Account[])this.array;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Account account = var1[var3];
         if (account != null) {
            File accountFile = account.getFile();
            if (accountFile.exists()) {
               account.load();
            }
         }
      }

   }

   public Account getCurrent() {
      if (this.current == null) {
         Account account = new Account("ERROR", "ERROR");
         account.setPremium(false);
         this.current = account;
      }

      return this.current;
   }

   public void setCurrent(Account account) {
      this.current = account;
   }

   public void remove(Account account) {
      File accountFile = account.getFile();
      if (accountFile.exists()) {
         accountFile.delete();
      }

      super.remove(account);
   }

   public Account getAccount(String username) {
      if (((Account[])this.array).length == 0) {
         return null;
      } else {
         Account[] var2 = (Account[])this.array;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Account account = var2[var4];
            if (account != null && account.getDisplay().equals(username)) {
               return account;
            }
         }

         return null;
      }
   }

   private File getAccountDir() {
      return null;
   }
}
