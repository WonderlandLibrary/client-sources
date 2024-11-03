package vestige.util.accounts;

public enum AccountType {
   CRACKED("Cracked"),
   MICROSOFT("Microsoft");

   private final String name;

   private AccountType(String param3) {
      this.name = param3;
   }

   public static AccountType getByName(String name) {
      AccountType[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         AccountType type = var1[var3];
         if (type.getName().equalsIgnoreCase(name)) {
            return type;
         }
      }

      return null;
   }

   public String getName() {
      return this.name;
   }

   // $FF: synthetic method
   private static AccountType[] $values() {
      return new AccountType[]{CRACKED, MICROSOFT};
   }
}
