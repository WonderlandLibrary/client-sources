package xyz.cucumber.base.utils.packet;

public class CLUtils {
   public static String getClientRoleByID(int id) {
      switch (id) {
         case -2:
            return "§cYouTuber";
         case -1:
            return "§dMedia";
         case 0:
            return "§bUser";
         case 1:
            return "§eBeta";
         case 2:
            return "§aDev";
         case 10:
            return "§6CL Admin";
         case Integer.MAX_VALUE:
            return "§7Waiting";
         default:
            return "§4Developer";
      }
   }
}
