package cc.novoline.gui.screen.alt.repository.tclient;

import cc.novoline.gui.screen.alt.repository.hypixel.HypixelProfile;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TClient {
   private static final String b = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36 OPR/65.0.3467.62";
   private static final String g = "application/json";
   private static final String a = "YWjDcNZfiryPSQBosg07bC4EVXkpI9GHT5MUnqwtmDdMTXgRENCkoSeatncvKiWq";
   private static final int TIMEOUT = 5000;
   private final Logger logger;
   private final JsonParser parser;
   private static boolean f;

   public TClient() {
      a();
      super();
      this.logger = LogManager.getLogger();
      this.parser = new JsonParser();
   }

   @Nullable
   public HypixelProfile find(@NotNull UUID param1) {
      // $FF: Couldn't be decompiled
   }

   public static void b(boolean var0) {
      f = var0;
   }

   public static boolean b() {
      return f;
   }

   public static boolean a() {
      boolean var0 = b();
      return true;
   }

   private static IOException a(IOException var0) {
      return var0;
   }

   static {
      b(false);
   }
}
