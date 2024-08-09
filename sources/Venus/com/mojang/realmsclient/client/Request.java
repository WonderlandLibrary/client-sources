/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.client;

import com.mojang.realmsclient.client.RealmsClientConfig;
import com.mojang.realmsclient.exception.RealmsHttpException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

public abstract class Request<T extends Request<T>> {
    protected HttpURLConnection field_224968_a;
    private boolean field_224970_c;
    protected String field_224969_b;

    public Request(String string, int n, int n2) {
        try {
            this.field_224969_b = string;
            Proxy proxy = RealmsClientConfig.func_224895_a();
            this.field_224968_a = proxy != null ? (HttpURLConnection)new URL(string).openConnection(proxy) : (HttpURLConnection)new URL(string).openConnection();
            this.field_224968_a.setConnectTimeout(n);
            this.field_224968_a.setReadTimeout(n2);
        } catch (MalformedURLException malformedURLException) {
            throw new RealmsHttpException(malformedURLException.getMessage(), malformedURLException);
        } catch (IOException iOException) {
            throw new RealmsHttpException(iOException.getMessage(), iOException);
        }
    }

    public void func_224962_a(String string, String string2) {
        Request.func_224967_a(this.field_224968_a, string, string2);
    }

    public static void func_224967_a(HttpURLConnection httpURLConnection, String string, String string2) {
        String string3 = httpURLConnection.getRequestProperty("Cookie");
        if (string3 == null) {
            httpURLConnection.setRequestProperty("Cookie", string + "=" + string2);
        } else {
            httpURLConnection.setRequestProperty("Cookie", string3 + ";" + string + "=" + string2);
        }
    }

    public int func_224957_a() {
        return Request.func_224964_a(this.field_224968_a);
    }

    public static int func_224964_a(HttpURLConnection httpURLConnection) {
        String string = httpURLConnection.getHeaderField("Retry-After");
        try {
            return Integer.valueOf(string);
        } catch (Exception exception) {
            return 0;
        }
    }

    public int func_224958_b() {
        try {
            this.func_224955_d();
            return this.field_224968_a.getResponseCode();
        } catch (Exception exception) {
            throw new RealmsHttpException(exception.getMessage(), exception);
        }
    }

    public String func_224963_c() {
        try {
            this.func_224955_d();
            String string = null;
            string = this.func_224958_b() >= 400 ? this.func_224954_a(this.field_224968_a.getErrorStream()) : this.func_224954_a(this.field_224968_a.getInputStream());
            this.func_224950_f();
            return string;
        } catch (IOException iOException) {
            throw new RealmsHttpException(iOException.getMessage(), iOException);
        }
    }

    private String func_224954_a(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return "";
        }
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        StringBuilder stringBuilder = new StringBuilder();
        int n = inputStreamReader.read();
        while (n != -1) {
            stringBuilder.append((char)n);
            n = inputStreamReader.read();
        }
        return stringBuilder.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void func_224950_f() {
        byte[] byArray = new byte[1024];
        try {
            InputStream inputStream = this.field_224968_a.getInputStream();
            while (inputStream.read(byArray) > 0) {
            }
            inputStream.close();
            return;
        } catch (Exception exception) {
            try {
                InputStream inputStream = this.field_224968_a.getErrorStream();
                if (inputStream != null) {
                    while (inputStream.read(byArray) > 0) {
                    }
                    inputStream.close();
                    return;
                }
            } catch (IOException iOException) {
                return;
            }
        } finally {
            if (this.field_224968_a != null) {
                this.field_224968_a.disconnect();
            }
        }
    }

    protected T func_224955_d() {
        if (this.field_224970_c) {
            return (T)this;
        }
        T t = this.func_223626_e_();
        this.field_224970_c = true;
        return t;
    }

    protected abstract T func_223626_e_();

    public static Request<?> func_224953_a(String string) {
        return new Get(string, 5000, 60000);
    }

    public static Request<?> func_224960_a(String string, int n, int n2) {
        return new Get(string, n, n2);
    }

    public static Request<?> func_224951_b(String string, String string2) {
        return new Post(string, string2, 5000, 60000);
    }

    public static Request<?> func_224959_a(String string, String string2, int n, int n2) {
        return new Post(string, string2, n, n2);
    }

    public static Request<?> func_224952_b(String string) {
        return new Delete(string, 5000, 60000);
    }

    public static Request<?> func_224965_c(String string, String string2) {
        return new Put(string, string2, 5000, 60000);
    }

    public static Request<?> func_224966_b(String string, String string2, int n, int n2) {
        return new Put(string, string2, n, n2);
    }

    public String func_224956_c(String string) {
        return Request.func_224961_a(this.field_224968_a, string);
    }

    public static String func_224961_a(HttpURLConnection httpURLConnection, String string) {
        try {
            return httpURLConnection.getHeaderField(string);
        } catch (Exception exception) {
            return "";
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Get
    extends Request<Get> {
        public Get(String string, int n, int n2) {
            super(string, n, n2);
        }

        @Override
        public Get func_223626_e_() {
            try {
                this.field_224968_a.setDoInput(false);
                this.field_224968_a.setDoOutput(false);
                this.field_224968_a.setUseCaches(true);
                this.field_224968_a.setRequestMethod("GET");
                return this;
            } catch (Exception exception) {
                throw new RealmsHttpException(exception.getMessage(), exception);
            }
        }

        @Override
        public Request func_223626_e_() {
            return this.func_223626_e_();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Post
    extends Request<Post> {
        private final String field_224971_c;

        public Post(String string, String string2, int n, int n2) {
            super(string, n, n2);
            this.field_224971_c = string2;
        }

        @Override
        public Post func_223626_e_() {
            try {
                if (this.field_224971_c != null) {
                    this.field_224968_a.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                }
                this.field_224968_a.setDoInput(false);
                this.field_224968_a.setDoOutput(false);
                this.field_224968_a.setUseCaches(true);
                this.field_224968_a.setRequestMethod("POST");
                OutputStream outputStream = this.field_224968_a.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                outputStreamWriter.write(this.field_224971_c);
                outputStreamWriter.close();
                outputStream.flush();
                return this;
            } catch (Exception exception) {
                throw new RealmsHttpException(exception.getMessage(), exception);
            }
        }

        @Override
        public Request func_223626_e_() {
            return this.func_223626_e_();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Delete
    extends Request<Delete> {
        public Delete(String string, int n, int n2) {
            super(string, n, n2);
        }

        @Override
        public Delete func_223626_e_() {
            try {
                this.field_224968_a.setDoOutput(false);
                this.field_224968_a.setRequestMethod("DELETE");
                this.field_224968_a.connect();
                return this;
            } catch (Exception exception) {
                throw new RealmsHttpException(exception.getMessage(), exception);
            }
        }

        @Override
        public Request func_223626_e_() {
            return this.func_223626_e_();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Put
    extends Request<Put> {
        private final String field_224972_c;

        public Put(String string, String string2, int n, int n2) {
            super(string, n, n2);
            this.field_224972_c = string2;
        }

        @Override
        public Put func_223626_e_() {
            try {
                if (this.field_224972_c != null) {
                    this.field_224968_a.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                }
                this.field_224968_a.setDoOutput(false);
                this.field_224968_a.setDoInput(false);
                this.field_224968_a.setRequestMethod("PUT");
                OutputStream outputStream = this.field_224968_a.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                outputStreamWriter.write(this.field_224972_c);
                outputStreamWriter.close();
                outputStream.flush();
                return this;
            } catch (Exception exception) {
                throw new RealmsHttpException(exception.getMessage(), exception);
            }
        }

        @Override
        public Request func_223626_e_() {
            return this.func_223626_e_();
        }
    }
}

