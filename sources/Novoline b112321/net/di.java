package net;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class di {
   public static CompletableFuture a(Supplier var0, Executor var1) {
      return CompletableFuture.supplyAsync(var0, var1);
   }

   public static CompletableFuture b(CompletableFuture var0, BiConsumer var1) {
      return var0.whenCompleteAsync(var1);
   }

   public static Object b(CompletableFuture var0) {
      return var0.get();
   }

   public static boolean a(CompletableFuture var0) {
      return var0.isDone();
   }

   public static CompletableFuture a(Runnable var0, Executor var1) {
      return CompletableFuture.runAsync(var0, var1);
   }

   public static CompletableFuture a(CompletableFuture var0, Function var1) {
      return var0.exceptionally(var1);
   }

   public static CompletableFuture a(CompletableFuture var0, BiConsumer var1, Executor var2) {
      return var0.whenCompleteAsync(var1, var2);
   }

   public static CompletableFuture a(CompletableFuture var0, BiConsumer var1) {
      return var0.whenComplete(var1);
   }

   public static boolean a(CompletableFuture var0, Object var1) {
      return var0.complete(var1);
   }
}
