package com.client.glowclient;

import net.minecraft.client.entity.*;
import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import com.client.glowclient.utils.*;
import com.google.common.collect.*;
import java.util.*;
import com.google.gson.*;

public class R
{
    private final UUID G;
    private static JsonParser d;
    private static Gson L;
    private final boolean A;
    private final UUID B;
    private final List<n> b;
    
    public R(final String string) {
        super();
        final JsonArray jsonArray;
        (jsonArray = new JsonArray()).add(string);
        UUID b = UUID.randomUUID();
        Object o = Collections.emptyList();
        boolean b2 = true;
        try {
            final UUID m;
            Objects.requireNonNull(m = r.M(M(new URL("https://api.mojang.com/profiles/minecraft"), "POST", jsonArray).getAsJsonArray().get(0).getAsJsonObject().get("id").getAsString()));
            o = ImmutableList.copyOf((Collection<?>)M(b = m));
            final boolean a;
            b2 = (a = false);
            final List<n> b3 = (List<n>)o;
            this.B = b;
            this.b = b3;
            this.G = EntityPlayerSP.getOfflineUUID(this.D());
            this.A = a;
        }
        catch (Throwable t) {
            b = EntityPlayerSP.getOfflineUUID(string);
            o = Collections.singletonList(new n(string));
            final boolean a2;
            b2 = (a2 = true);
            final List<n> b4 = (List<n>)o;
            this.B = b;
            this.b = b4;
            this.G = EntityPlayerSP.getOfflineUUID(this.D());
            this.A = a2;
        }
        finally {
            final boolean a3 = b2;
            final List<n> b5 = (List<n>)o;
            this.B = b;
            this.b = b5;
            this.G = EntityPlayerSP.getOfflineUUID(this.D());
            this.A = a3;
        }
    }
    
    static {
        R.L = new Gson();
        R.d = new JsonParser();
    }
    
    public boolean D() {
        return this.A;
    }
    
    public List<n> M() {
        return this.b;
    }
    
    private static JsonElement M(final URL url, String parse, final JsonElement jsonElement) throws Exception {
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpsURLConnection)url.openConnection();
            final String s = "\u000e>#%(?9|\u0019(=4";
            final HttpURLConnection httpURLConnection2 = httpURLConnection;
            final String requestMethod = parse;
            httpURLConnection.setDoOutput(true);
            httpURLConnection2.setRequestMethod(requestMethod);
            httpURLConnection2.setRequestProperty(HC.M(s), "application/json");
            if (jsonElement != null) {
                final DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                dataOutputStream.writeBytes(R.L.toJson(jsonElement));
                dataOutputStream.close();
            }
            final Scanner scanner = new Scanner(httpURLConnection.getInputStream());
            final StringBuilder sb = new StringBuilder();
            Scanner scanner2 = scanner;
            while (scanner2.hasNextLine()) {
                sb.append((scanner2 = scanner).nextLine());
                sb.append('\n');
            }
            scanner.close();
            parse = (String)R.d.parse(sb.toString());
            if (httpURLConnection != null) {
                final String s2 = parse;
                httpURLConnection.disconnect();
                return (JsonElement)s2;
            }
        }
        finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return (JsonElement)parse;
    }
    
    public String D() {
        if (!this.b.isEmpty()) {
            return this.b.get(0).M();
        }
        return null;
    }
    
    public boolean M() {
        return String.CASE_INSENSITIVE_ORDER.compare(this.D(), Wrapper.mc.player.getName()) == 0;
    }
    
    public static List<n> M(final UUID uuid) {
        final ArrayList<n> arrayList = Lists.newArrayList();
        try {
            final JsonArray asJsonArray = M(new URL(new StringBuilder().insert(0, "https://api.mojang.com/user/profiles/").append(r.M(uuid)).append("/names").toString()), "GET").getAsJsonArray();
            final ArrayList<Object> arrayList2 = Lists.newArrayList();
            final Iterator<JsonElement> iterator2;
            Iterator<JsonElement> iterator = iterator2 = asJsonArray.iterator();
            while (iterator.hasNext()) {
                final JsonObject asJsonObject;
                final JsonObject jsonObject = asJsonObject = iterator2.next().getAsJsonObject();
                final String asString = jsonObject.get("name").getAsString();
                final long n = jsonObject.has("changedToAt") ? asJsonObject.get("changedToAt").getAsLong() : 0L;
                iterator = iterator2;
                arrayList2.add(new n(asString, n));
            }
            Collections.sort((List<Comparable>)arrayList2);
            return (List<n>)arrayList2;
        }
        catch (Exception ex) {
            return arrayList;
        }
    }
    
    public R(final UUID b) {
        super();
        this.B = b;
        Object elements;
        R r;
        try {
            elements = M(b);
            r = this;
        }
        catch (Throwable t) {
            elements = Collections.emptyList();
            r = this;
        }
        r.b = (List<n>)ImmutableList.copyOf((Collection<?>)elements);
        final boolean a = false;
        this.G = EntityPlayerSP.getOfflineUUID(this.D());
        this.A = a;
    }
    
    public UUID D() {
        return this.G;
    }
    
    @Override
    public String toString() {
        return this.B.toString();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof R && this.B.equals(((R)o).B);
    }
    
    public UUID M() {
        return this.B;
    }
    
    private static JsonElement M(final URL url, final String s) throws Exception {
        return M(url, s, null);
    }
    
    public String M() {
        final StringBuilder sb = new StringBuilder();
        if (!this.b.isEmpty()) {
            final Iterator<n> iterator;
            (iterator = this.b.iterator()).next();
            while (iterator.hasNext()) {
                final n n = iterator.next();
                final Iterator<n> iterator2 = iterator;
                sb.append(n.M());
                if (iterator2.hasNext()) {
                    sb.append(", ");
                }
            }
        }
        return sb.toString();
    }
    
    @Override
    public int hashCode() {
        return this.B.hashCode();
    }
}
