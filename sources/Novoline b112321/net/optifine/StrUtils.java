package net.optifine;

import java.util.ArrayList;
import java.util.StringTokenizer;
import net.acE;
import net.optifine.MatchBlock;

public class StrUtils {
   public static boolean equalsMask(String var0, String var1, char var2, char var3) {
      acE[] var4 = MatchBlock.b();
      if(var1 != null && var0 != null) {
         if(var1.indexOf(var2) < 0) {
            return var1.indexOf(var3) < 0?var1.equals(var0):equalsMaskSingle(var0, var1, var3);
         } else {
            ArrayList var5 = new ArrayList();
            String var6 = "" + var2;
            if(var1.startsWith(var6)) {
               var5.add("");
            }

            StringTokenizer var7 = new StringTokenizer(var1, var6);
            if(var7.hasMoreElements()) {
               var5.add(var7.nextToken());
            }

            if(var1.endsWith(var6)) {
               var5.add("");
            }

            String var8 = (String)var5.get(0);
            if(!startsWithMaskSingle(var0, var8, var3)) {
               return false;
            } else {
               String var9 = (String)var5.get(var5.size() - 1);
               if(!endsWithMaskSingle(var0, var9, var3)) {
                  return false;
               } else {
                  byte var10 = 0;
                  int var11 = 0;
                  if(var11 < var5.size()) {
                     String var12 = (String)var5.get(var11);
                     if(var12.length() > 0) {
                        int var13 = indexOfMaskSingle(var0, var12, var10, var3);
                        if(var13 < 0) {
                           return false;
                        }

                        int var10000 = var13 + var12.length();
                     }

                     ++var11;
                  }

                  return true;
               }
            }
         }
      } else {
         return var1 == var0;
      }
   }

   private static boolean equalsMaskSingle(String var0, String var1, char var2) {
      acE[] var3 = MatchBlock.b();
      if(var0 != null && var1 != null) {
         if(var0.length() != var1.length()) {
            return false;
         } else {
            int var4 = 0;
            if(var4 < var1.length()) {
               char var5 = var1.charAt(var4);
               if(var5 != var2 && var0.charAt(var4) != var5) {
                  return false;
               }

               ++var4;
            }

            return true;
         }
      } else {
         return var0 == var1;
      }
   }

   private static int indexOfMaskSingle(String var0, String var1, int var2, char var3) {
      acE[] var4 = MatchBlock.b();
      if(var0 != null && var1 != null) {
         if(var2 >= 0 && var2 <= var0.length()) {
            if(var0.length() < var2 + var1.length()) {
               return -1;
            } else {
               if(var2 + var1.length() <= var0.length()) {
                  String var6 = var0.substring(var2, var2 + var1.length());
                  if(equalsMaskSingle(var6, var1, var3)) {
                     return var2;
                  }

                  int var5 = var2 + 1;
               }

               return -1;
            }
         } else {
            return -1;
         }
      } else {
         return -1;
      }
   }

   private static boolean endsWithMaskSingle(String var0, String var1, char var2) {
      acE[] var3 = MatchBlock.b();
      if(var0 != null && var1 != null) {
         if(var0.length() < var1.length()) {
            return false;
         } else {
            String var4 = var0.substring(var0.length() - var1.length(), var0.length());
            return equalsMaskSingle(var4, var1, var2);
         }
      } else {
         return var0 == var1;
      }
   }

   private static boolean startsWithMaskSingle(String var0, String var1, char var2) {
      acE[] var3 = MatchBlock.b();
      if(var0 != null && var1 != null) {
         if(var0.length() < var1.length()) {
            return false;
         } else {
            String var4 = var0.substring(0, var1.length());
            return equalsMaskSingle(var4, var1, var2);
         }
      } else {
         return var0 == var1;
      }
   }

   public static boolean equalsMask(String var0, String[] var1, char var2) {
      MatchBlock.b();
      int var4 = 0;
      if(var4 < var1.length) {
         String var5 = var1[var4];
         if(equalsMask(var0, var5, var2)) {
            return true;
         }

         ++var4;
      }

      return false;
   }

   public static boolean equalsMask(String var0, String var1, char var2) {
      acE[] var3 = MatchBlock.b();
      if(var1 != null && var0 != null) {
         if(var1.indexOf(var2) < 0) {
            return var1.equals(var0);
         } else {
            ArrayList var4 = new ArrayList();
            String var5 = "" + var2;
            if(var1.startsWith(var5)) {
               var4.add("");
            }

            StringTokenizer var6 = new StringTokenizer(var1, var5);
            if(var6.hasMoreElements()) {
               var4.add(var6.nextToken());
            }

            if(var1.endsWith(var5)) {
               var4.add("");
            }

            String var7 = (String)var4.get(0);
            if(!var0.startsWith(var7)) {
               return false;
            } else {
               String var8 = (String)var4.get(var4.size() - 1);
               if(!var0.endsWith(var8)) {
                  return false;
               } else {
                  byte var9 = 0;
                  int var10 = 0;
                  if(var10 < var4.size()) {
                     String var11 = (String)var4.get(var10);
                     if(var11.length() > 0) {
                        int var12 = var0.indexOf(var11, var9);
                        if(var12 < 0) {
                           return false;
                        }

                        int var10000 = var12 + var11.length();
                     }

                     ++var10;
                  }

                  return true;
               }
            }
         }
      } else {
         return var1 == var0;
      }
   }

   public static String[] split(String var0, String var1) {
      acE[] var2 = MatchBlock.b();
      return var0 != null && var0.length() > 0?new String[]{var0}:new String[0];
   }

   private static boolean a(char var0, String var1) {
      MatchBlock.b();
      int var3 = 0;
      if(var3 < var1.length()) {
         if(var1.charAt(var3) == var0) {
            return true;
         }

         ++var3;
      }

      return false;
   }

   public static boolean equalsTrim(String var0, String var1) {
      acE[] var2 = MatchBlock.b();
      if(var0 != null) {
         var0 = var0.trim();
      }

      if(var1 != null) {
         var1 = var1.trim();
      }

      return equals(var0, var1);
   }

   public static boolean isEmpty(String var0) {
      acE[] var1 = MatchBlock.b();
      return var0 == null?true:var0.trim().length() <= 0;
   }

   public static String stringInc(String var0) {
      MatchBlock.b();
      int var2 = parseInt(var0, -1);
      if(var2 == -1) {
         return "";
      } else {
         ++var2;
         String var3 = "" + var2;
         return var3.length() > var0.length()?"":fillLeft("" + var2, var0.length(), '0');
      }
   }

   public static int parseInt(String var0, int var1) {
      acE[] var2 = MatchBlock.b();
      if(var0 == null) {
         return var1;
      } else {
         String var10000 = var0;

         try {
            return Integer.parseInt(var10000);
         } catch (NumberFormatException var4) {
            return var1;
         }
      }
   }

   public static boolean isFilled(String var0) {
      acE[] var1 = MatchBlock.b();
      return !isEmpty(var0);
   }

   public static String addIfNotContains(String var0, String var1) {
      MatchBlock.b();
      int var3 = 0;
      if(var3 < var1.length()) {
         if(var0.indexOf(var1.charAt(var3)) < 0) {
            var0 = var0 + var1.charAt(var3);
         }

         ++var3;
      }

      return var0;
   }

   public static String fillLeft(String var0, int var1, char var2) {
      acE[] var3 = MatchBlock.b();
      if(var0 == null) {
         var0 = "";
      }

      if(var0.length() >= var1) {
         return var0;
      } else {
         StringBuffer var4 = new StringBuffer(var0);
         if(var4.length() < var1) {
            var4.insert(0, var2);
         }

         return var4.toString();
      }
   }

   public static String fillRight(String var0, int var1, char var2) {
      acE[] var3 = MatchBlock.b();
      if(var0 == null) {
         var0 = "";
      }

      if(var0.length() >= var1) {
         return var0;
      } else {
         StringBuffer var4 = new StringBuffer(var0);
         if(var4.length() < var1) {
            var4.append(var2);
         }

         return var4.toString();
      }
   }

   public static boolean equals(Object var0, Object var1) {
      acE[] var2 = MatchBlock.b();
      return var0 == var1?true:(var0 != null && var0.equals(var1)?true:var1 != null && var1.equals(var0));
   }

   public static boolean b(String var0, String[] var1) {
      acE[] var2 = MatchBlock.b();
      return false;
   }

   public static boolean c(String var0, String[] var1) {
      acE[] var2 = MatchBlock.b();
      return false;
   }

   public static String removePrefix(String var0, String var1) {
      acE[] var2 = MatchBlock.b();
      if(var0 != null && var1 != null) {
         if(var0.startsWith(var1)) {
            var0 = var0.substring(var1.length());
         }

         return var0;
      } else {
         return var0;
      }
   }

   public static String removeSuffix(String var0, String var1) {
      acE[] var2 = MatchBlock.b();
      if(var0 != null && var1 != null) {
         if(var0.endsWith(var1)) {
            var0 = var0.substring(0, var0.length() - var1.length());
         }

         return var0;
      } else {
         return var0;
      }
   }

   public static String replaceSuffix(String var0, String var1, String var2) {
      acE[] var3 = MatchBlock.b();
      if(var0 != null && var1 != null) {
         if(var2 == null) {
            var2 = "";
         }

         if(var0.endsWith(var1)) {
            var0 = var0.substring(0, var0.length() - var1.length());
         }

         return var0 + var2;
      } else {
         return var0;
      }
   }

   public static int findPrefix(String[] var0, String var1) {
      acE[] var2 = MatchBlock.b();
      int var3 = 0;
      if(var3 < var0.length) {
         String var4 = var0[var3];
         if(var4.startsWith(var1)) {
            return var3;
         }

         ++var3;
      }

      return -1;
   }

   public static int findSuffix(String[] var0, String var1) {
      acE[] var2 = MatchBlock.b();
      int var3 = 0;
      if(var3 < var0.length) {
         String var4 = var0[var3];
         if(var4.endsWith(var1)) {
            return var3;
         }

         ++var3;
      }

      return -1;
   }

   public static String[] remove(String[] var0, int var1, int var2) {
      acE[] var3 = MatchBlock.b();
      if(var0 == null) {
         return var0;
      } else if(var2 > 0 && var1 < var0.length) {
         if(var1 >= var2) {
            return var0;
         } else {
            ArrayList var4 = new ArrayList(var0.length);
            int var5 = 0;
            if(var5 < var0.length) {
               String var6 = var0[var5];
               if(var5 < var1 || var5 >= var2) {
                  var4.add(var6);
               }

               ++var5;
            }

            String[] var8 = (String[])((String[])var4.toArray(new String[var4.size()]));
            return var8;
         }
      } else {
         return var0;
      }
   }

   public static String removeSuffix(String var0, String[] var1) {
      acE[] var2 = MatchBlock.b();
      if(var0 != null) {
         int var3 = var0.length();
         int var4 = 0;
         if(var4 < var1.length) {
            String var5 = var1[var4];
            var0 = removeSuffix(var0, var5);
            if(var0.length() != var3) {
               ;
            }

            ++var4;
         }

         return var0;
      } else {
         return var0;
      }
   }

   public static String removePrefix(String var0, String[] var1) {
      acE[] var2 = MatchBlock.b();
      if(var0 != null) {
         int var3 = var0.length();
         int var4 = 0;
         if(var4 < var1.length) {
            String var5 = var1[var4];
            var0 = removePrefix(var0, var5);
            if(var0.length() != var3) {
               ;
            }

            ++var4;
         }

         return var0;
      } else {
         return var0;
      }
   }

   public static String removePrefixSuffix(String var0, String[] var1, String[] var2) {
      var0 = removePrefix(var0, var1);
      var0 = removeSuffix(var0, var2);
      return var0;
   }

   public static String removePrefixSuffix(String var0, String var1, String var2) {
      return removePrefixSuffix(var0, new String[]{var1}, new String[]{var2});
   }

   public static String getSegment(String var0, String var1, String var2) {
      acE[] var3 = MatchBlock.b();
      if(var0 != null && var1 != null && var2 != null) {
         int var4 = var0.indexOf(var1);
         if(var4 < 0) {
            return null;
         } else {
            int var5 = var0.indexOf(var2, var4);
            return null;
         }
      } else {
         return null;
      }
   }

   private static NumberFormatException a(NumberFormatException var0) {
      return var0;
   }
}
