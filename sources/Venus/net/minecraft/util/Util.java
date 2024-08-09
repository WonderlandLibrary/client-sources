/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.MoreExecutors;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.DataResult;
import it.unimi.dsi.fastutil.Hash;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.client.util.ICharacterPredicate;
import net.minecraft.crash.ReportedException;
import net.minecraft.state.Property;
import net.minecraft.util.DefaultUncaughtExceptionHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Bootstrap;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Util {
    private static final AtomicInteger NEXT_SERVER_WORKER_ID = new AtomicInteger(1);
    private static final ExecutorService BOOTSTRAP_SERVICE = Util.createNamedService("Bootstrap");
    private static final ExecutorService SERVER_EXECUTOR = Util.createNamedService("Main");
    private static final ExecutorService RENDERING_SERVICE = Util.startThreadedService();
    public static LongSupplier nanoTimeSupplier = System::nanoTime;
    public static final UUID DUMMY_UUID = new UUID(0L, 0L);
    private static final Logger LOGGER = LogManager.getLogger();
    private static Exception exceptionOpenUrl;
    private static final ExecutorService CAPE_EXECUTOR;

    public static <K, V> Collector<Map.Entry<? extends K, ? extends V>, ?, Map<K, V>> toMapCollector() {
        return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);
    }

    public static <T extends Comparable<T>> String getValueName(Property<T> property, Object object) {
        return property.getName((Comparable)object);
    }

    public static String makeTranslationKey(String string, @Nullable ResourceLocation resourceLocation) {
        return resourceLocation == null ? string + ".unregistered_sadface" : string + "." + resourceLocation.getNamespace() + "." + resourceLocation.getPath().replace('/', '.');
    }

    public static long milliTime() {
        return Util.nanoTime() / 1000000L;
    }

    public static long nanoTime() {
        return nanoTimeSupplier.getAsLong();
    }

    public static long millisecondsSinceEpoch() {
        return Instant.now().toEpochMilli();
    }

    private static ExecutorService createNamedService(String string) {
        int n = MathHelper.clamp(Runtime.getRuntime().availableProcessors() - 1, 1, 7);
        ExecutorService executorService = n <= 0 ? MoreExecutors.newDirectExecutorService() : new ForkJoinPool(n, arg_0 -> Util.lambda$createNamedService$0(string, arg_0), Util::printException, true);
        return executorService;
    }

    public static Executor getBootstrapService() {
        return BOOTSTRAP_SERVICE;
    }

    public static Executor getServerExecutor() {
        return SERVER_EXECUTOR;
    }

    public static Executor getRenderingService() {
        return RENDERING_SERVICE;
    }

    public static void shutdown() {
        Util.shutdownService(SERVER_EXECUTOR);
        Util.shutdownService(RENDERING_SERVICE);
        Util.shutdownService(CAPE_EXECUTOR);
    }

    private static void shutdownService(ExecutorService executorService) {
        boolean bl;
        executorService.shutdown();
        try {
            bl = executorService.awaitTermination(3L, TimeUnit.SECONDS);
        } catch (InterruptedException interruptedException) {
            bl = false;
        }
        if (!bl) {
            executorService.shutdownNow();
        }
    }

    private static ExecutorService startThreadedService() {
        return Executors.newCachedThreadPool(Util::lambda$startThreadedService$1);
    }

    public static <T> CompletableFuture<T> completedExceptionallyFuture(Throwable throwable) {
        CompletableFuture completableFuture = new CompletableFuture();
        completableFuture.completeExceptionally(throwable);
        return completableFuture;
    }

    public static void toRuntimeException(Throwable throwable) {
        throw throwable instanceof RuntimeException ? (RuntimeException)throwable : new RuntimeException(throwable);
    }

    private static void printException(Thread thread2, Throwable throwable) {
        Util.pauseDevMode(throwable);
        if (throwable instanceof CompletionException) {
            throwable = throwable.getCause();
        }
        if (throwable instanceof ReportedException) {
            Bootstrap.printToSYSOUT(((ReportedException)throwable).getCrashReport().getCompleteReport());
            System.exit(-1);
        }
        LOGGER.error(String.format("Caught exception in thread %s", thread2), throwable);
    }

    @Nullable
    public static Type<?> attemptDataFix(DSL.TypeReference typeReference, String string) {
        return !SharedConstants.useDatafixers ? null : Util.attemptDataFixInternal(typeReference, string);
    }

    @Nullable
    private static Type<?> attemptDataFixInternal(DSL.TypeReference typeReference, String string) {
        Type<?> type;
        block2: {
            type = null;
            try {
                type = DataFixesManager.getDataFixer().getSchema(DataFixUtils.makeKey(SharedConstants.getVersion().getWorldVersion())).getChoiceType(typeReference, string);
            } catch (IllegalArgumentException illegalArgumentException) {
                LOGGER.debug("No data fixer registered for {}", (Object)string);
                if (!SharedConstants.developmentMode) break block2;
                throw illegalArgumentException;
            }
        }
        return type;
    }

    public static OS getOSType() {
        String string = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        if (string.contains("win")) {
            return OS.WINDOWS;
        }
        if (string.contains("mac")) {
            return OS.OSX;
        }
        if (string.contains("solaris")) {
            return OS.SOLARIS;
        }
        if (string.contains("sunos")) {
            return OS.SOLARIS;
        }
        if (string.contains("linux")) {
            return OS.LINUX;
        }
        return string.contains("unix") ? OS.LINUX : OS.UNKNOWN;
    }

    public static Stream<String> getJvmFlags() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return runtimeMXBean.getInputArguments().stream().filter(Util::lambda$getJvmFlags$2);
    }

    public static <T> T getLast(List<T> list) {
        return list.get(list.size() - 1);
    }

    public static <T> T getElementAfter(Iterable<T> iterable, @Nullable T t) {
        Iterator<T> iterator2 = iterable.iterator();
        T t2 = iterator2.next();
        if (t != null) {
            T t3 = t2;
            while (t3 != t) {
                if (!iterator2.hasNext()) continue;
                t3 = iterator2.next();
            }
            if (iterator2.hasNext()) {
                return iterator2.next();
            }
        }
        return t2;
    }

    public static <T> T getElementBefore(Iterable<T> iterable, @Nullable T t) {
        Iterator<T> iterator2 = iterable.iterator();
        T t2 = null;
        while (iterator2.hasNext()) {
            T t3 = iterator2.next();
            if (t3 == t) {
                if (t2 != null) break;
                t2 = iterator2.hasNext() ? Iterators.getLast(iterator2) : t;
                break;
            }
            t2 = t3;
        }
        return t2;
    }

    public static <T> T make(Supplier<T> supplier) {
        return supplier.get();
    }

    public static <T> T make(T t, Consumer<T> consumer) {
        consumer.accept(t);
        return t;
    }

    public static <K> Hash.Strategy<K> identityHashStrategy() {
        return IdentityStrategy.INSTANCE;
    }

    public static <V> CompletableFuture<List<V>> gather(List<? extends CompletableFuture<? extends V>> list) {
        ArrayList arrayList = Lists.newArrayListWithCapacity(list.size());
        CompletableFuture[] completableFutureArray = new CompletableFuture[list.size()];
        CompletableFuture completableFuture = new CompletableFuture();
        list.forEach(arg_0 -> Util.lambda$gather$4(arrayList, completableFutureArray, completableFuture, arg_0));
        return CompletableFuture.allOf(completableFutureArray).applyToEither((CompletionStage)completableFuture, arg_0 -> Util.lambda$gather$5(arrayList, arg_0));
    }

    public static <T> Stream<T> streamOptional(Optional<? extends T> optional) {
        return optional.isPresent() ? Stream.of(optional.get()) : Stream.empty();
    }

    public static Exception getExceptionOpenUrl() {
        return exceptionOpenUrl;
    }

    public static void setExceptionOpenUrl(Exception exception) {
        exceptionOpenUrl = exception;
    }

    public static ExecutorService getCapeExecutor() {
        return CAPE_EXECUTOR;
    }

    public static <T> Optional<T> acceptOrElse(Optional<T> optional, Consumer<T> consumer, Runnable runnable) {
        if (optional.isPresent()) {
            consumer.accept(optional.get());
        } else {
            runnable.run();
        }
        return optional;
    }

    public static Runnable namedRunnable(Runnable runnable, Supplier<String> supplier) {
        return runnable;
    }

    public static <T extends Throwable> T pauseDevMode(T t) {
        if (SharedConstants.developmentMode) {
            LOGGER.error("Trying to throw a fatal exception, pausing in IDE", t);
            try {
                while (true) {
                    Thread.sleep(1000L);
                    LOGGER.error("paused");
                }
            } catch (InterruptedException interruptedException) {
                return t;
            }
        }
        return t;
    }

    public static String getMessage(Throwable throwable) {
        if (throwable.getCause() != null) {
            return Util.getMessage(throwable.getCause());
        }
        return throwable.getMessage() != null ? throwable.getMessage() : throwable.toString();
    }

    public static <T> T getRandomObject(T[] TArray, Random random2) {
        return TArray[random2.nextInt(TArray.length)];
    }

    public static int getRandomInt(int[] nArray, Random random2) {
        return nArray[random2.nextInt(nArray.length)];
    }

    private static BooleanSupplier func_244363_a(Path path, Path path2) {
        return new BooleanSupplier(){
            final Path val$p_244363_0_;
            final Path val$p_244363_1_;
            {
                this.val$p_244363_0_ = path;
                this.val$p_244363_1_ = path2;
            }

            @Override
            public boolean getAsBoolean() {
                try {
                    Files.move(this.val$p_244363_0_, this.val$p_244363_1_, new CopyOption[0]);
                    return true;
                } catch (IOException iOException) {
                    LOGGER.error("Failed to rename", (Throwable)iOException);
                    return true;
                }
            }

            public String toString() {
                return "rename " + this.val$p_244363_0_ + " to " + this.val$p_244363_1_;
            }
        };
    }

    private static BooleanSupplier func_244362_a(Path path) {
        return new BooleanSupplier(path){
            final Path val$p_244362_0_;
            {
                this.val$p_244362_0_ = path;
            }

            @Override
            public boolean getAsBoolean() {
                try {
                    Files.deleteIfExists(this.val$p_244362_0_);
                    return true;
                } catch (IOException iOException) {
                    LOGGER.warn("Failed to delete", (Throwable)iOException);
                    return true;
                }
            }

            public String toString() {
                return "delete old " + this.val$p_244362_0_;
            }
        };
    }

    private static BooleanSupplier func_244366_b(Path path) {
        return new BooleanSupplier(path){
            final Path val$p_244366_0_;
            {
                this.val$p_244366_0_ = path;
            }

            @Override
            public boolean getAsBoolean() {
                return !Files.exists(this.val$p_244366_0_, new LinkOption[0]);
            }

            public String toString() {
                return "verify that " + this.val$p_244366_0_ + " is deleted";
            }
        };
    }

    private static BooleanSupplier func_244367_c(Path path) {
        return new BooleanSupplier(path){
            final Path val$p_244367_0_;
            {
                this.val$p_244367_0_ = path;
            }

            @Override
            public boolean getAsBoolean() {
                return Files.isRegularFile(this.val$p_244367_0_, new LinkOption[0]);
            }

            public String toString() {
                return "verify that " + this.val$p_244367_0_ + " is present";
            }
        };
    }

    private static boolean func_244365_a(BooleanSupplier ... booleanSupplierArray) {
        for (BooleanSupplier booleanSupplier : booleanSupplierArray) {
            if (booleanSupplier.getAsBoolean()) continue;
            LOGGER.warn("Failed to execute {}", (Object)booleanSupplier);
            return true;
        }
        return false;
    }

    private static boolean func_244359_a(int n, String string, BooleanSupplier ... booleanSupplierArray) {
        for (int i = 0; i < n; ++i) {
            if (Util.func_244365_a(booleanSupplierArray)) {
                return false;
            }
            LOGGER.error("Failed to {}, retrying {}/{}", (Object)string, (Object)i, (Object)n);
        }
        LOGGER.error("Failed to {}, aborting, progress might be lost", (Object)string);
        return true;
    }

    public static void backupThenUpdate(File file, File file2, File file3) {
        Util.func_244364_a(file.toPath(), file2.toPath(), file3.toPath());
    }

    public static void func_244364_a(Path path, Path path2, Path path3) {
        int n = 10;
        if ((!Files.exists(path, new LinkOption[0]) || Util.func_244359_a(10, "create backup " + path3, Util.func_244362_a(path3), Util.func_244363_a(path, path3), Util.func_244367_c(path3))) && Util.func_244359_a(10, "remove old " + path, Util.func_244362_a(path), Util.func_244366_b(path)) && !Util.func_244359_a(10, "replace " + path + " with " + path2, Util.func_244363_a(path2, path), Util.func_244367_c(path))) {
            Util.func_244359_a(10, "restore " + path + " from " + path3, Util.func_244363_a(path3, path), Util.func_244367_c(path));
        }
    }

    public static int func_240980_a_(String string, int n, int n2) {
        int n3 = string.length();
        if (n2 >= 0) {
            for (int i = 0; n < n3 && i < n2; ++i) {
                if (!Character.isHighSurrogate(string.charAt(n++)) || n >= n3 || !Character.isLowSurrogate(string.charAt(n))) continue;
                ++n;
            }
        } else {
            for (int i = n2; n > 0 && i < 0; ++i) {
                if (!Character.isLowSurrogate(string.charAt(--n)) || n <= 0 || !Character.isHighSurrogate(string.charAt(n - 1))) continue;
                --n;
            }
        }
        return n;
    }

    public static Consumer<String> func_240982_a_(String string, Consumer<String> consumer) {
        return arg_0 -> Util.lambda$func_240982_a_$6(consumer, string, arg_0);
    }

    public static DataResult<int[]> validateIntStreamSize(IntStream intStream, int n) {
        int[] nArray = intStream.limit(n + 1).toArray();
        if (nArray.length != n) {
            String string = "Input is not a list of " + n + " ints";
            return nArray.length >= n ? DataResult.error(string, Arrays.copyOf(nArray, n)) : DataResult.error(string);
        }
        return DataResult.success(nArray);
    }

    public static void func_240994_l_() {
        Thread thread2 = new Thread("Timer hack thread"){

            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(Integer.MAX_VALUE);
                    }
                } catch (InterruptedException interruptedException) {
                    LOGGER.warn("Timer hack thread interrupted, that really should not happen");
                    return;
                }
            }
        };
        thread2.setDaemon(false);
        thread2.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LOGGER));
        thread2.start();
    }

    public static void func_240984_a_(Path path, Path path2, Path path3) throws IOException {
        Path path4 = path.relativize(path3);
        Path path5 = path2.resolve(path4);
        Files.copy(path3, path5, new CopyOption[0]);
    }

    public static String func_244361_a(String string, ICharacterPredicate iCharacterPredicate) {
        return string.toLowerCase(Locale.ROOT).chars().mapToObj(arg_0 -> Util.lambda$func_244361_a$7(iCharacterPredicate, arg_0)).collect(Collectors.joining());
    }

    private static String lambda$func_244361_a$7(ICharacterPredicate iCharacterPredicate, int n) {
        return iCharacterPredicate.test((char)n) ? Character.toString((char)n) : "_";
    }

    private static void lambda$func_240982_a_$6(Consumer consumer, String string, String string2) {
        consumer.accept(string + string2);
    }

    private static List lambda$gather$5(List list, Void void_) {
        return list;
    }

    private static void lambda$gather$4(List list, CompletableFuture[] completableFutureArray, CompletableFuture completableFuture, CompletableFuture completableFuture2) {
        int n = list.size();
        list.add(null);
        completableFutureArray[n] = completableFuture2.whenComplete((arg_0, arg_1) -> Util.lambda$gather$3(completableFuture, list, n, arg_0, arg_1));
    }

    private static void lambda$gather$3(CompletableFuture completableFuture, List list, int n, Object object, Throwable throwable) {
        if (throwable != null) {
            completableFuture.completeExceptionally(throwable);
        } else {
            list.set(n, object);
        }
    }

    private static boolean lambda$getJvmFlags$2(String string) {
        return string.startsWith("-X");
    }

    private static Thread lambda$startThreadedService$1(Runnable runnable) {
        Thread thread2 = new Thread(runnable);
        thread2.setName("IO-Worker-" + NEXT_SERVER_WORKER_ID.getAndIncrement());
        thread2.setUncaughtExceptionHandler(Util::printException);
        return thread2;
    }

    private static ForkJoinWorkerThread lambda$createNamedService$0(String string, ForkJoinPool forkJoinPool) {
        ForkJoinWorkerThread forkJoinWorkerThread = new ForkJoinWorkerThread(forkJoinPool){

            @Override
            protected void onTermination(Throwable throwable) {
                if (throwable != null) {
                    LOGGER.warn("{} died", (Object)this.getName(), (Object)throwable);
                } else {
                    LOGGER.debug("{} shutdown", (Object)this.getName());
                }
                super.onTermination(throwable);
            }
        };
        forkJoinWorkerThread.setName("Worker-" + string + "-" + NEXT_SERVER_WORKER_ID.getAndIncrement());
        return forkJoinWorkerThread;
    }

    static {
        CAPE_EXECUTOR = Util.createNamedService("Cape");
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    public static enum OS {
        LINUX,
        SOLARIS,
        WINDOWS{

            @Override
            protected String[] getOpenCommandLine(URL uRL) {
                return new String[]{"rundll32", "url.dll,FileProtocolHandler", uRL.toString()};
            }
        }
        ,
        OSX{

            @Override
            protected String[] getOpenCommandLine(URL uRL) {
                return new String[]{"open", uRL.toString()};
            }
        }
        ,
        UNKNOWN;


        public void openURL(URL uRL) {
            try {
                Process process = AccessController.doPrivileged(() -> this.lambda$openURL$0(uRL));
                for (String string : IOUtils.readLines(process.getErrorStream())) {
                    LOGGER.error(string);
                }
                process.getInputStream().close();
                process.getErrorStream().close();
                process.getOutputStream().close();
            } catch (IOException | PrivilegedActionException exception) {
                LOGGER.error("Couldn't open url '{}'", (Object)uRL, (Object)exception);
                exceptionOpenUrl = exception;
            }
        }

        public void openURI(URI uRI) {
            try {
                this.openURL(uRI.toURL());
            } catch (MalformedURLException malformedURLException) {
                LOGGER.error("Couldn't open uri '{}'", (Object)uRI, (Object)malformedURLException);
            }
        }

        public void openFile(File file) {
            try {
                this.openURL(file.toURI().toURL());
            } catch (MalformedURLException malformedURLException) {
                LOGGER.error("Couldn't open file '{}'", (Object)file, (Object)malformedURLException);
            }
        }

        protected String[] getOpenCommandLine(URL uRL) {
            String string = uRL.toString();
            if ("file".equals(uRL.getProtocol())) {
                string = string.replace("file:", "file://");
            }
            return new String[]{"xdg-open", string};
        }

        public void openURI(String string) {
            try {
                this.openURL(new URI(string).toURL());
            } catch (IllegalArgumentException | MalformedURLException | URISyntaxException exception) {
                LOGGER.error("Couldn't open uri '{}'", (Object)string, (Object)exception);
            }
        }

        private Process lambda$openURL$0(URL uRL) throws Exception {
            return Runtime.getRuntime().exec(this.getOpenCommandLine(uRL));
        }
    }

    static enum IdentityStrategy implements Hash.Strategy<Object>
    {
        INSTANCE;


        @Override
        public int hashCode(Object object) {
            return System.identityHashCode(object);
        }

        @Override
        public boolean equals(Object object, Object object2) {
            return object == object2;
        }
    }
}

