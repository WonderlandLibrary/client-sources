/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.client.UploadStatus;
import com.mojang.realmsclient.dto.UploadInfo;
import com.mojang.realmsclient.gui.screens.UploadResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import net.minecraft.util.Session;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileUpload {
    private static final Logger field_224883_a = LogManager.getLogger();
    private final File field_224884_b;
    private final long field_224885_c;
    private final int field_224886_d;
    private final UploadInfo field_224887_e;
    private final String field_224888_f;
    private final String field_224889_g;
    private final String field_224890_h;
    private final UploadStatus field_224891_i;
    private final AtomicBoolean field_224892_j = new AtomicBoolean(false);
    private CompletableFuture<UploadResult> field_224893_k;
    private final RequestConfig field_224894_l = RequestConfig.custom().setSocketTimeout((int)TimeUnit.MINUTES.toMillis(10L)).setConnectTimeout((int)TimeUnit.SECONDS.toMillis(15L)).build();

    public FileUpload(File file, long l, int n, UploadInfo uploadInfo, Session session, String string, UploadStatus uploadStatus) {
        this.field_224884_b = file;
        this.field_224885_c = l;
        this.field_224886_d = n;
        this.field_224887_e = uploadInfo;
        this.field_224888_f = session.getSessionID();
        this.field_224889_g = session.getUsername();
        this.field_224890_h = string;
        this.field_224891_i = uploadStatus;
    }

    public void func_224874_a(Consumer<UploadResult> consumer) {
        if (this.field_224893_k == null) {
            this.field_224893_k = CompletableFuture.supplyAsync(this::lambda$func_224874_a$0);
            this.field_224893_k.thenAccept((Consumer)consumer);
        }
    }

    public void func_224878_a() {
        this.field_224892_j.set(false);
        if (this.field_224893_k != null) {
            this.field_224893_k.cancel(true);
            this.field_224893_k = null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private UploadResult func_224879_a(int n) {
        UploadResult uploadResult;
        UploadResult.Builder builder = new UploadResult.Builder();
        if (this.field_224892_j.get()) {
            return builder.func_225174_a();
        }
        this.field_224891_i.field_224979_b = this.field_224884_b.length();
        HttpPost httpPost = new HttpPost(this.field_224887_e.func_243089_b().resolve("/upload/" + this.field_224885_c + "/" + this.field_224886_d));
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().setDefaultRequestConfig(this.field_224894_l).build();
        try {
            this.func_224872_a(httpPost);
            CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpPost);
            long l = this.func_224880_a(closeableHttpResponse);
            if (!this.func_224882_a(l, n)) {
                this.func_224875_a(closeableHttpResponse, builder);
                UploadResult uploadResult2 = builder.func_225174_a();
                return uploadResult2;
            }
            uploadResult = this.func_224876_b(l, n);
        } catch (Exception exception) {
            if (!this.field_224892_j.get()) {
                field_224883_a.error("Caught exception while uploading: ", (Throwable)exception);
            }
            UploadResult uploadResult3 = builder.func_225174_a();
            return uploadResult3;
        } finally {
            this.func_224877_a(httpPost, closeableHttpClient);
        }
        return uploadResult;
    }

    private void func_224877_a(HttpPost httpPost, CloseableHttpClient closeableHttpClient) {
        httpPost.releaseConnection();
        if (closeableHttpClient != null) {
            try {
                closeableHttpClient.close();
            } catch (IOException iOException) {
                field_224883_a.error("Failed to close Realms upload client");
            }
        }
    }

    private void func_224872_a(HttpPost httpPost) throws FileNotFoundException {
        httpPost.setHeader("Cookie", "sid=" + this.field_224888_f + ";token=" + this.field_224887_e.func_230795_a_() + ";user=" + this.field_224889_g + ";version=" + this.field_224890_h);
        CustomInputStreamEntity customInputStreamEntity = new CustomInputStreamEntity((InputStream)new FileInputStream(this.field_224884_b), this.field_224884_b.length(), this.field_224891_i);
        customInputStreamEntity.setContentType("application/octet-stream");
        httpPost.setEntity(customInputStreamEntity);
    }

    private void func_224875_a(HttpResponse httpResponse, UploadResult.Builder builder) throws IOException {
        String string;
        int n = httpResponse.getStatusLine().getStatusCode();
        if (n == 401) {
            field_224883_a.debug("Realms server returned 401: " + httpResponse.getFirstHeader("WWW-Authenticate"));
        }
        builder.func_225175_a(n);
        if (httpResponse.getEntity() != null && (string = EntityUtils.toString(httpResponse.getEntity(), "UTF-8")) != null) {
            try {
                JsonParser jsonParser = new JsonParser();
                JsonElement jsonElement = jsonParser.parse(string).getAsJsonObject().get("errorMsg");
                Optional<String> optional = Optional.ofNullable(jsonElement).map(JsonElement::getAsString);
                builder.func_225176_a(optional.orElse(null));
            } catch (Exception exception) {
                // empty catch block
            }
        }
    }

    private boolean func_224882_a(long l, int n) {
        return l > 0L && n + 1 < 5;
    }

    private UploadResult func_224876_b(long l, int n) throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(l).toMillis());
        return this.func_224879_a(n + 1);
    }

    private long func_224880_a(HttpResponse httpResponse) {
        return Optional.ofNullable(httpResponse.getFirstHeader("Retry-After")).map(NameValuePair::getValue).map(Long::valueOf).orElse(0L);
    }

    public boolean func_224881_b() {
        return this.field_224893_k.isDone() || this.field_224893_k.isCancelled();
    }

    private UploadResult lambda$func_224874_a$0() {
        return this.func_224879_a(0);
    }

    static class CustomInputStreamEntity
    extends InputStreamEntity {
        private final long field_224869_a;
        private final InputStream field_224870_b;
        private final UploadStatus field_224871_c;

        public CustomInputStreamEntity(InputStream inputStream, long l, UploadStatus uploadStatus) {
            super(inputStream);
            this.field_224870_b = inputStream;
            this.field_224869_a = l;
            this.field_224871_c = uploadStatus;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void writeTo(OutputStream outputStream) throws IOException {
            block7: {
                Args.notNull(outputStream, "Output stream");
                try (InputStream inputStream = this.field_224870_b;){
                    int n;
                    byte[] byArray = new byte[4096];
                    if (this.field_224869_a < 0L) {
                        int n2;
                        while ((n2 = inputStream.read(byArray)) != -1) {
                            outputStream.write(byArray, 0, n2);
                            this.field_224871_c.field_224978_a += (long)n2;
                        }
                        break block7;
                    }
                    for (long i = this.field_224869_a; i > 0L; i -= (long)n) {
                        n = inputStream.read(byArray, 0, (int)Math.min(4096L, i));
                        if (n == -1) {
                            break;
                        }
                        outputStream.write(byArray, 0, n);
                        this.field_224871_c.field_224978_a += (long)n;
                        outputStream.flush();
                    }
                }
            }
        }
    }
}

