package ClassSub;

import java.util.*;
import joptsimple.internal.*;
import com.google.gson.*;
import java.util.regex.*;
import java.net.*;
import java.io.*;
import org.apache.http.impl.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.util.*;
import org.apache.http.*;

public enum Class164
{
    INSTANCE;
    
    private static final Class164[] $VALUES;
    
    
    public ArrayList<Class296> getSongs(final String s) {
        final String request = this.request(s);
        final ArrayList<Class296> list = new ArrayList<Class296>();
        final JsonObject jsonObject = (JsonObject)new JsonParser().parse(request);
        if (!jsonObject.get("code").getAsString().contains("200")) {
            System.out.println("解析时出现问�?,请检查歌单ID是否正确");
            return list;
        }
        final JsonArray asJsonArray = jsonObject.get("result").getAsJsonObject().get("tracks").getAsJsonArray();
        for (int i = 0; i < asJsonArray.size(); ++i) {
            final JsonObject asJsonObject = asJsonArray.get(i).getAsJsonObject();
            final String asString = asJsonObject.get("name").getAsString();
            final String asString2 = asJsonObject.get("id").getAsString();
            String asString3;
            try {
                asString3 = asJsonObject.get("album").getAsJsonObject().get("blurPicUrl").getAsString();
            }
            catch (Exception ex) {
                asString3 = "";
            }
            final JsonArray asJsonArray2 = asJsonObject.get("artists").getAsJsonArray();
            final ArrayList<String> list2 = new ArrayList<String>();
            for (int j = 0; j < asJsonArray2.size(); ++j) {
                list2.add(asJsonArray2.get(j).getAsJsonObject().get("name").getAsString());
            }
            list.add(new Class296(asString, Strings.join((String[])list2.toArray(new String[0]), "/"), Long.valueOf(asString2), asString3));
        }
        return list;
    }
    
    public String[] requestLyric(final String s) {
        final String s2 = "http://music.163.com/api/song/lyric";
        final String string = "os=pc&id=" + s + "&lv=-1&kv=-1&tv=-1";
        String asString = "";
        String asString2 = "";
        try {
            final JsonObject jsonObject = (JsonObject)new JsonParser().parse(this.sendGet(s2, string));
            if (!jsonObject.get("code").getAsString().contains("200")) {
                System.out.println("解析时出现问�?,请检查歌曲ID是否正确");
                return new String[] { "", "" };
            }
            if (jsonObject.get("nolyric") != null && jsonObject.get("nolyric").getAsBoolean()) {
                return new String[] { "NO LYRIC", "NO LYRIC" };
            }
            if (!jsonObject.get("lrc").getAsJsonObject().get("lyric").isJsonNull()) {
                asString = jsonObject.get("lrc").getAsJsonObject().get("lyric").getAsString();
            }
            else {
                asString = "";
            }
            try {
                if (!jsonObject.get("tlyric").getAsJsonObject().get("lyric").isJsonNull()) {
                    asString2 = jsonObject.get("tlyric").getAsJsonObject().get("lyric").getAsString();
                }
                else {
                    asString2 = "";
                }
            }
            catch (Exception ex2) {
                asString2 = "";
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return new String[] { asString, asString2 };
    }
    
    public ArrayList<Class63> analyzeLyric(final String s) {
        final ArrayList<Class63> list = new ArrayList<Class63>();
        try {
            final InputStreamReader inputStreamReader = new InputStreamReader(new ByteArrayInputStream(s.getBytes()), Class286.lyricCoding ? "GBK" : "UTF-8");
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            final Pattern compile = Pattern.compile("\\[([0-9]{2}):([0-9]{2}).([0-9]{1,3})\\]");
            final Pattern compile2 = Pattern.compile("\\[([0-9]{2}):([0-9]{2})\\]");
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                final Matcher matcher = compile.matcher(line);
                final Matcher matcher2 = compile2.matcher(line);
                if (matcher.find()) {
                    list.add(new Class63(line.substring(matcher.end()), this.strToLong(matcher.group(1), matcher.group(2), matcher.group(3))));
                }
                else {
                    if (!matcher2.find()) {
                        continue;
                    }
                    list.add(new Class63(line.substring(matcher2.end()), this.strToLong(matcher2.group(1), matcher2.group(2), "000")));
                }
            }
            inputStreamReader.close();
            return list;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("解析歌词时发生错�?");
            return null;
        }
    }
    
    public long strToLong(final String s, final String s2, final String s3) {
        return Integer.parseInt(s) * 60 * 1000 + Integer.parseInt(s2) * 1000 + Integer.parseInt(s3) * ((s3.length() == 2) ? 10 : 1);
    }
    
    public String getDownloadUrl(final String s) {
        return "http://music.163.com/song/media/outer/url?id=" + s + ".mp3";
    }
    
    public String request(final String s) {
        try {
            return this.sendGet("http://music.163.com/api/playlist/detail", "id=" + s);
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
    }
    
    public boolean isFileExist(final String s, final String s2) {
        return new File(s + "\\" + s2 + ".mp3").exists();
    }
    
    public void downLoadSong(final String s, final String s2, final String s3) {
        try {
            this.downLoadFromUrl(s3, s2 + ".mp3", s);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void downLoadFromUrl(final String s, final String s2, final String s3) throws IOException, URISyntaxException {
        final HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(s).openConnection();
        httpURLConnection.setConnectTimeout(3000);
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36");
        final InputStream inputStream = httpURLConnection.getInputStream();
        final byte[] inputStream2 = this.readInputStream(inputStream, httpURLConnection.getContentLength());
        final File file = new File(s3);
        if (!file.exists()) {
            file.mkdir();
        }
        final FileOutputStream fileOutputStream = new FileOutputStream(new File(file + File.separator + s2));
        fileOutputStream.write(inputStream2);
        if (fileOutputStream != null) {
            fileOutputStream.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
    }
    
    public byte[] readInputStream(final InputStream inputStream, final int n) throws IOException {
        final byte[] array = new byte[1024];
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        long n2 = 0L;
        int read;
        while ((read = inputStream.read(array)) != -1) {
            n2 += read;
            Class344.INSTANCE.downloadProgress = String.valueOf(n2 * 100L / n);
            byteArrayOutputStream.write(array, 0, read);
        }
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }
    
    public String sendGet(final String s, final String s2) throws IOException {
        return this.send((HttpRequestBase)new HttpGet(s + "?" + s2));
    }
    
    private String send(final HttpRequestBase httpRequestBase) throws IOException {
        String string = "";
        httpRequestBase.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) ...");
        httpRequestBase.setHeader("accept", "*/*");
        httpRequestBase.setHeader("connection", "Keep-Alive");
        final HttpEntity entity = HttpClients.createDefault().execute((HttpUriRequest)httpRequestBase).getEntity();
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (entity != null) {
            final long contentLength = entity.getContentLength();
            if (contentLength != -1L && contentLength < 2048L) {
                string = EntityUtils.toString(entity);
            }
            else {
                final InputStream content = entity.getContent();
                final byte[] array = new byte[4096];
                int read;
                while ((read = content.read(array, 0, 4096)) != -1) {
                    byteArrayOutputStream.write(array, 0, read);
                }
                string = new String(byteArrayOutputStream.toByteArray(), "UTF-8");
            }
        }
        return string;
    }
    
    static {
        $VALUES = new Class164[] { Class164.INSTANCE };
    }
}
