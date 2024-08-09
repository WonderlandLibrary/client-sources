/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.text.filter;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.concurrent.DelegatedTaskExecutor;
import net.minecraft.util.text.filter.IChatFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChatFilterClient
implements AutoCloseable {
    private static final Logger field_244549_a = LogManager.getLogger();
    private static final AtomicInteger field_244550_b = new AtomicInteger(1);
    private static final ThreadFactory field_244551_c = ChatFilterClient::lambda$static$0;
    private final URL field_244552_d = null;
    private final URL field_244553_e = null;
    private final URL field_244554_f = null;
    private final String field_244555_g;
    private final int field_244556_h = 0;
    private final String field_244557_i;
    private final IIgnoreTest field_244558_j = null;
    private final ExecutorService field_244559_k = null;

    private void func_244568_a(GameProfile gameProfile, URL uRL, Executor executor) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("server", this.field_244557_i);
        jsonObject.addProperty("room", "Chat");
        jsonObject.addProperty("user_id", gameProfile.getId().toString());
        jsonObject.addProperty("user_display_name", gameProfile.getName());
        executor.execute(() -> this.lambda$func_244568_a$1(jsonObject, uRL, gameProfile));
    }

    private CompletableFuture<Optional<String>> func_244567_a(GameProfile gameProfile, String string, IIgnoreTest iIgnoreTest, Executor executor) {
        if (string.isEmpty()) {
            return CompletableFuture.completedFuture(Optional.of(""));
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("rule", this.field_244556_h);
        jsonObject.addProperty("server", this.field_244557_i);
        jsonObject.addProperty("room", "Chat");
        jsonObject.addProperty("player", gameProfile.getId().toString());
        jsonObject.addProperty("player_display_name", gameProfile.getName());
        jsonObject.addProperty("text", string);
        return CompletableFuture.supplyAsync(() -> this.lambda$func_244567_a$2(jsonObject, string, iIgnoreTest), executor);
    }

    @Override
    public void close() {
        this.field_244559_k.shutdownNow();
    }

    private void func_244569_a(InputStream inputStream) throws IOException {
        byte[] byArray = new byte[1024];
        while (inputStream.read(byArray) != -1) {
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private JsonObject func_244564_a(JsonObject jsonObject, URL uRL) throws IOException {
        JsonObject jsonObject2;
        HttpURLConnection httpURLConnection = this.func_244575_c(jsonObject, uRL);
        try (InputStream inputStream = httpURLConnection.getInputStream();){
            if (httpURLConnection.getResponseCode() != 204) {
                try {
                    JsonObject jsonObject3 = Streams.parse(new JsonReader(new InputStreamReader(inputStream))).getAsJsonObject();
                    return jsonObject3;
                } finally {
                    this.func_244569_a(inputStream);
                }
            }
            jsonObject2 = new JsonObject();
        }
        return jsonObject2;
    }

    private void func_244573_b(JsonObject jsonObject, URL uRL) throws IOException {
        HttpURLConnection httpURLConnection = this.func_244575_c(jsonObject, uRL);
        try (InputStream inputStream = httpURLConnection.getInputStream();){
            this.func_244569_a(inputStream);
        }
    }

    private HttpURLConnection func_244575_c(JsonObject jsonObject, URL uRL) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
        httpURLConnection.setConnectTimeout(15000);
        httpURLConnection.setReadTimeout(2000);
        httpURLConnection.setUseCaches(true);
        httpURLConnection.setDoOutput(false);
        httpURLConnection.setDoInput(false);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setRequestProperty("Authorization", "Basic " + this.field_244555_g);
        httpURLConnection.setRequestProperty("User-Agent", "Minecraft server" + SharedConstants.getVersion().getName());
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream(), StandardCharsets.UTF_8);
             JsonWriter jsonWriter = new JsonWriter(outputStreamWriter);){
            Streams.write(jsonObject, jsonWriter);
        }
        int n = httpURLConnection.getResponseCode();
        if (n >= 200 && n < 300) {
            return httpURLConnection;
        }
        throw new ConnectionException(n + " " + httpURLConnection.getResponseMessage());
    }

    public IChatFilter func_244566_a(GameProfile gameProfile) {
        return new ProfileFilter(this, gameProfile);
    }

    private ChatFilterClient() {
        this.field_244555_g = null;
        this.field_244557_i = null;
        throw new RuntimeException("Synthetic constructor added by MCP, do not call");
    }

    private Optional lambda$func_244567_a$2(JsonObject jsonObject, String string, IIgnoreTest iIgnoreTest) {
        try {
            JsonObject jsonObject2 = this.func_244564_a(jsonObject, this.field_244552_d);
            boolean bl = JSONUtils.getBoolean(jsonObject2, "response", false);
            if (bl) {
                return Optional.of(string);
            }
            String string2 = JSONUtils.getString(jsonObject2, "hashed", null);
            if (string2 == null) {
                return Optional.empty();
            }
            int n = JSONUtils.getJsonArray(jsonObject2, "hashes").size();
            return iIgnoreTest.shouldIgnore(string2, n) ? Optional.empty() : Optional.of(string2);
        } catch (Exception exception) {
            field_244549_a.warn("Failed to validate message '{}'", (Object)string, (Object)exception);
            return Optional.empty();
        }
    }

    private void lambda$func_244568_a$1(JsonObject jsonObject, URL uRL, GameProfile gameProfile) {
        try {
            this.func_244573_b(jsonObject, uRL);
        } catch (Exception exception) {
            field_244549_a.warn("Failed to send join/leave packet to {} for player {}", (Object)uRL, (Object)gameProfile, (Object)exception);
        }
    }

    private static Thread lambda$static$0(Runnable runnable) {
        Thread thread2 = new Thread(runnable);
        thread2.setName("Chat-Filter-Worker-" + field_244550_b.getAndIncrement());
        return thread2;
    }

    @FunctionalInterface
    public static interface IIgnoreTest {
        public static final IIgnoreTest field_244577_a = IIgnoreTest::lambda$static$0;
        public static final IIgnoreTest field_244578_b = IIgnoreTest::lambda$static$1;

        public boolean shouldIgnore(String var1, int var2);

        private static boolean lambda$static$1(String string, int n) {
            return string.length() == n;
        }

        private static boolean lambda$static$0(String string, int n) {
            return true;
        }
    }

    public static class ConnectionException
    extends RuntimeException {
        private ConnectionException(String string) {
            super(string);
        }
    }

    class ProfileFilter
    implements IChatFilter {
        private final GameProfile field_244585_b;
        private final Executor field_244586_c;
        final ChatFilterClient this$0;

        private ProfileFilter(ChatFilterClient chatFilterClient, GameProfile gameProfile) {
            this.this$0 = chatFilterClient;
            this.field_244585_b = gameProfile;
            DelegatedTaskExecutor<Runnable> delegatedTaskExecutor = DelegatedTaskExecutor.create(chatFilterClient.field_244559_k, "chat stream for " + gameProfile.getName());
            this.field_244586_c = delegatedTaskExecutor::enqueue;
        }

        @Override
        public void func_244800_a() {
            this.this$0.func_244568_a(this.field_244585_b, this.this$0.field_244553_e, this.field_244586_c);
        }

        @Override
        public void func_244434_b() {
            this.this$0.func_244568_a(this.field_244585_b, this.this$0.field_244554_f, this.field_244586_c);
        }

        @Override
        public CompletableFuture<Optional<List<String>>> func_244433_a(List<String> list) {
            List list2 = list.stream().map(this::lambda$func_244433_a$0).collect(ImmutableList.toImmutableList());
            return ((CompletableFuture)Util.gather(list2).thenApply(ProfileFilter::lambda$func_244433_a$2)).exceptionally(ProfileFilter::lambda$func_244433_a$3);
        }

        @Override
        public CompletableFuture<Optional<String>> func_244432_a(String string) {
            return this.this$0.func_244567_a(this.field_244585_b, string, this.this$0.field_244558_j, this.field_244586_c);
        }

        private static Optional lambda$func_244433_a$3(Throwable throwable) {
            return Optional.empty();
        }

        private static Optional lambda$func_244433_a$2(List list) {
            return Optional.of((List)list.stream().map(ProfileFilter::lambda$func_244433_a$1).collect(ImmutableList.toImmutableList()));
        }

        private static String lambda$func_244433_a$1(Optional optional) {
            return optional.orElse("");
        }

        private CompletableFuture lambda$func_244433_a$0(String string) {
            return this.this$0.func_244567_a(this.field_244585_b, string, this.this$0.field_244558_j, this.field_244586_c);
        }
    }
}

