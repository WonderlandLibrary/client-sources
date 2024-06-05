package net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

public final class tp {
   private static final JsonParser a = new JsonParser();
   private static final Gson b = a().create();

   public static Gson c() {
      return b;
   }

   public static GsonBuilder a() {
      return new GsonBuilder();
   }

   public static JsonParser b() {
      return a;
   }
}
