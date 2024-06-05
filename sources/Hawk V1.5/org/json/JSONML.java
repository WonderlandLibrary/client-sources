package org.json;

import java.util.Iterator;

public class JSONML {
   public static JSONObject toJSONObject(XMLTokener var0, boolean var1) throws JSONException {
      return (JSONObject)parse(var0, false, (JSONArray)null, var1);
   }

   public static JSONObject toJSONObject(XMLTokener var0) throws JSONException {
      return (JSONObject)parse(var0, false, (JSONArray)null, false);
   }

   public static JSONArray toJSONArray(XMLTokener var0) throws JSONException {
      return (JSONArray)parse(var0, true, (JSONArray)null, false);
   }

   public static String toString(JSONObject var0) throws JSONException {
      StringBuilder var1 = new StringBuilder();
      String var6 = var0.optString("tagName");
      if (var6 == null) {
         return XML.escape(var0.toString());
      } else {
         XML.noSpace(var6);
         var6 = XML.escape(var6);
         var1.append('<');
         var1.append(var6);
         Iterator var8 = var0.keySet().iterator();

         while(var8.hasNext()) {
            String var9 = (String)var8.next();
            if (!"tagName".equals(var9) && !"childNodes".equals(var9)) {
               XML.noSpace(var9);
               Object var7 = var0.opt(var9);
               if (var7 != null) {
                  var1.append(' ');
                  var1.append(XML.escape(var9));
                  var1.append('=');
                  var1.append('"');
                  var1.append(XML.escape(var7.toString()));
                  var1.append('"');
               }
            }
         }

         JSONArray var3 = var0.optJSONArray("childNodes");
         if (var3 == null) {
            var1.append('/');
            var1.append('>');
         } else {
            var1.append('>');
            int var4 = var3.length();

            for(int var2 = 0; var2 < var4; ++var2) {
               Object var5 = var3.get(var2);
               if (var5 != null) {
                  if (var5 instanceof String) {
                     var1.append(XML.escape(var5.toString()));
                  } else if (var5 instanceof JSONObject) {
                     var1.append(toString((JSONObject)var5));
                  } else if (var5 instanceof JSONArray) {
                     var1.append(toString((JSONArray)var5));
                  } else {
                     var1.append(var5.toString());
                  }
               }
            }

            var1.append('<');
            var1.append('/');
            var1.append(var6);
            var1.append('>');
         }

         return String.valueOf(var1);
      }
   }

   public static JSONObject toJSONObject(String var0) throws JSONException {
      return (JSONObject)parse(new XMLTokener(var0), false, (JSONArray)null, false);
   }

   public static JSONArray toJSONArray(String var0) throws JSONException {
      return (JSONArray)parse(new XMLTokener(var0), true, (JSONArray)null, false);
   }

   private static Object parse(XMLTokener var0, boolean var1, JSONArray var2, boolean var3) throws JSONException {
      String var6 = null;
      JSONArray var8 = null;
      JSONObject var9 = null;
      String var11 = null;

      while(true) {
         while(var0.more()) {
            Object var10 = var0.nextContent();
            if (var10 == XML.LT) {
               var10 = var0.nextToken();
               if (var10 instanceof Character) {
                  if (var10 == XML.SLASH) {
                     var10 = var0.nextToken();
                     if (!(var10 instanceof String)) {
                        throw new JSONException(String.valueOf((new StringBuilder()).append("Expected a closing name instead of '").append(var10).append("'.")));
                     }

                     if (var0.nextToken() != XML.GT) {
                        throw var0.syntaxError("Misshaped close tag");
                     }

                     return var10;
                  }

                  if (var10 != XML.BANG) {
                     if (var10 != XML.QUEST) {
                        throw var0.syntaxError("Misshaped tag");
                     }

                     var0.skipPast("?>");
                  } else {
                     char var5 = var0.next();
                     if (var5 == '-') {
                        if (var0.next() == '-') {
                           var0.skipPast("-->");
                        } else {
                           var0.back();
                        }
                     } else if (var5 == '[') {
                        var10 = var0.nextToken();
                        if (!var10.equals("CDATA") || var0.next() != '[') {
                           throw var0.syntaxError("Expected 'CDATA['");
                        }

                        if (var2 != null) {
                           var2.put((Object)var0.nextCDATA());
                        }
                     } else {
                        int var7 = 1;

                        while(true) {
                           var10 = var0.nextMeta();
                           if (var10 == null) {
                              throw var0.syntaxError("Missing '>' after '<!'.");
                           }

                           if (var10 == XML.LT) {
                              ++var7;
                           } else if (var10 == XML.GT) {
                              --var7;
                           }

                           if (var7 <= 0) {
                              break;
                           }
                        }
                     }
                  }
               } else {
                  if (!(var10 instanceof String)) {
                     throw var0.syntaxError(String.valueOf((new StringBuilder()).append("Bad tagName '").append(var10).append("'.")));
                  }

                  var11 = (String)var10;
                  var8 = new JSONArray();
                  var9 = new JSONObject();
                  if (var1) {
                     var8.put((Object)var11);
                     if (var2 != null) {
                        var2.put((Object)var8);
                     }
                  } else {
                     var9.put("tagName", (Object)var11);
                     if (var2 != null) {
                        var2.put((Object)var9);
                     }
                  }

                  var10 = null;

                  while(true) {
                     if (var10 == null) {
                        var10 = var0.nextToken();
                     }

                     if (var10 == null) {
                        throw var0.syntaxError("Misshaped tag");
                     }

                     if (!(var10 instanceof String)) {
                        if (var1 && var9.length() > 0) {
                           var8.put((Object)var9);
                        }

                        if (var10 == XML.SLASH) {
                           if (var0.nextToken() != XML.GT) {
                              throw var0.syntaxError("Misshaped tag");
                           }

                           if (var2 == null) {
                              if (var1) {
                                 return var8;
                              }

                              return var9;
                           }
                        } else {
                           if (var10 != XML.GT) {
                              throw var0.syntaxError("Misshaped tag");
                           }

                           var6 = (String)parse(var0, var1, var8, var3);
                           if (var6 != null) {
                              if (!var6.equals(var11)) {
                                 throw var0.syntaxError(String.valueOf((new StringBuilder()).append("Mismatched '").append(var11).append("' and '").append(var6).append("'")));
                              }

                              var11 = null;
                              if (!var1 && var8.length() > 0) {
                                 var9.put("childNodes", (Object)var8);
                              }

                              if (var2 == null) {
                                 if (var1) {
                                    return var8;
                                 }

                                 return var9;
                              }
                           }
                        }
                        break;
                     }

                     String var4 = (String)var10;
                     if (!var1 && ("tagName".equals(var4) || "childNode".equals(var4))) {
                        throw var0.syntaxError("Reserved attribute.");
                     }

                     var10 = var0.nextToken();
                     if (var10 == XML.EQ) {
                        var10 = var0.nextToken();
                        if (!(var10 instanceof String)) {
                           throw var0.syntaxError("Missing value");
                        }

                        var9.accumulate(var4, var3 ? (String)var10 : XML.stringToValue((String)var10));
                        var10 = null;
                     } else {
                        var9.accumulate(var4, "");
                     }
                  }
               }
            } else if (var2 != null) {
               var2.put(var10 instanceof String ? (var3 ? XML.unescape((String)var10) : XML.stringToValue((String)var10)) : var10);
            }
         }

         throw var0.syntaxError("Bad XML");
      }
   }

   public static JSONArray toJSONArray(String var0, boolean var1) throws JSONException {
      return (JSONArray)parse(new XMLTokener(var0), true, (JSONArray)null, var1);
   }

   public static JSONArray toJSONArray(XMLTokener var0, boolean var1) throws JSONException {
      return (JSONArray)parse(var0, true, (JSONArray)null, var1);
   }

   public static String toString(JSONArray var0) throws JSONException {
      StringBuilder var5 = new StringBuilder();
      String var6 = var0.getString(0);
      XML.noSpace(var6);
      var6 = XML.escape(var6);
      var5.append('<');
      var5.append(var6);
      Object var4 = var0.opt(1);
      int var1;
      if (var4 instanceof JSONObject) {
         var1 = 2;
         JSONObject var2 = (JSONObject)var4;
         Iterator var7 = var2.keySet().iterator();

         while(var7.hasNext()) {
            String var8 = (String)var7.next();
            Object var9 = var2.opt(var8);
            XML.noSpace(var8);
            if (var9 != null) {
               var5.append(' ');
               var5.append(XML.escape(var8));
               var5.append('=');
               var5.append('"');
               var5.append(XML.escape(var9.toString()));
               var5.append('"');
            }
         }
      } else {
         var1 = 1;
      }

      int var3 = var0.length();
      if (var1 >= var3) {
         var5.append('/');
         var5.append('>');
      } else {
         var5.append('>');

         do {
            var4 = var0.get(var1);
            ++var1;
            if (var4 != null) {
               if (var4 instanceof String) {
                  var5.append(XML.escape(var4.toString()));
               } else if (var4 instanceof JSONObject) {
                  var5.append(toString((JSONObject)var4));
               } else if (var4 instanceof JSONArray) {
                  var5.append(toString((JSONArray)var4));
               } else {
                  var5.append(var4.toString());
               }
            }
         } while(var1 < var3);

         var5.append('<');
         var5.append('/');
         var5.append(var6);
         var5.append('>');
      }

      return String.valueOf(var5);
   }

   public static JSONObject toJSONObject(String var0, boolean var1) throws JSONException {
      return (JSONObject)parse(new XMLTokener(var0), false, (JSONArray)null, var1);
   }
}
