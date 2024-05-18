package de.labystudio.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.io.IOUtils;

public class Utils
{
    public static String download(String urlStr)
    {
        try
        {
            URL url = new URL(urlStr);
            HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection();
            HttpURLConnection.setFollowRedirects(true);
            httpurlconnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            String s = httpurlconnection.getContentEncoding();
            InputStream inputstream = null;

            if (s != null && s.equalsIgnoreCase("gzip"))
            {
                inputstream = new GZIPInputStream(httpurlconnection.getInputStream());
            }
            else if (s != null && s.equalsIgnoreCase("deflate"))
            {
                inputstream = new InflaterInputStream(httpurlconnection.getInputStream(), new Inflater(true));
            }
            else
            {
                inputstream = httpurlconnection.getInputStream();
            }

            return IOUtils.toString(inputstream);
        }
        catch (Exception var5)
        {
            return null;
        }
    }

    public static String getContentString(String page)
    {
        try
        {
            URLConnection urlconnection = (new URL(page)).openConnection();
            urlconnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            urlconnection.connect();
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), Charset.forName("UTF-8")));
            String s;
            String s1;

            for (s1 = ""; (s = bufferedreader.readLine()) != null; s1 = s1 + s)
            {
                ;
            }

            return s1;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return "";
        }
    }

    public static ArrayList<String> getContentList(String page)
    {
        try
        {
            URLConnection urlconnection = (new URL(page)).openConnection();
            urlconnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            urlconnection.connect();
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), Charset.forName("UTF-8")));
            ArrayList<String> arraylist = new ArrayList();
            String s;

            while ((s = bufferedreader.readLine()) != null)
            {
                arraylist.add(s);
            }

            return arraylist;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return new ArrayList();
        }
    }

    public static void openWebpage(URI uri)
    {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;

        if (desktop != null && desktop.isSupported(Action.BROWSE))
        {
            try
            {
                desktop.browse(uri);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }

    public static String parseTimeNormal(long time)
    {
        long i = time / 600L % 60L;
        long j = time / 60L % 60L;
        long k = time % 60L;
        long l = time / 600L / 60L % 24L;
        long i1 = time / 600L / 60L / 24L;
        String s = "";

        if (i1 != 0L)
        {
            s = s + i1 + "d ";
        }

        if (l != 0L)
        {
            s = s + l + "h ";
        }

        if (j != 0L)
        {
            s = s + j + "m ";
        }

        if (k != 0L)
        {
            s = s + k + "s";
        }

        return s;
    }

    public static String performPost(URL url, String parameters, String contentType, boolean returnErrorPage) throws IOException
    {
        HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection();
        byte[] abyte = parameters.getBytes(Charset.forName("UTF-8"));
        httpurlconnection.setConnectTimeout(15000);
        httpurlconnection.setReadTimeout(15000);
        httpurlconnection.setRequestMethod("POST");
        httpurlconnection.setRequestProperty("Content-Type", contentType + "; charset=utf-8");
        httpurlconnection.setRequestProperty("Content-Length", "" + abyte.length);
        httpurlconnection.setRequestProperty("Content-Language", "en-US");
        httpurlconnection.setUseCaches(false);
        httpurlconnection.setDoInput(true);
        httpurlconnection.setDoOutput(true);
        DataOutputStream dataoutputstream = new DataOutputStream(httpurlconnection.getOutputStream());
        dataoutputstream.write(abyte);
        dataoutputstream.flush();
        dataoutputstream.close();
        BufferedReader bufferedreader;

        try
        {
            bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()));
        }
        catch (IOException ioexception)
        {
            if (!returnErrorPage)
            {
                throw ioexception;
            }

            InputStream inputstream = httpurlconnection.getErrorStream();

            if (inputstream == null)
            {
                throw ioexception;
            }

            bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
        }

        StringBuilder stringbuilder = new StringBuilder();
        String s;

        while ((s = bufferedreader.readLine()) != null)
        {
            stringbuilder.append(s);
            stringbuilder.append('\r');
        }

        bufferedreader.close();
        return stringbuilder.toString();
    }

    public static URL constantURL(String input)
    {
        try
        {
            return new URL(input);
        }
        catch (MalformedURLException var2)
        {
            return null;
        }
    }

    public static String jsonPost(String urlStr, String json) throws Exception
    {
        URL url = new URL(urlStr);
        HttpsURLConnection httpsurlconnection = (HttpsURLConnection)url.openConnection();
        httpsurlconnection.setDoOutput(true);
        httpsurlconnection.setDoInput(true);
        httpsurlconnection.setRequestProperty("Content-Type", "application/json");
        httpsurlconnection.setRequestMethod("POST");
        OutputStreamWriter outputstreamwriter = new OutputStreamWriter(httpsurlconnection.getOutputStream());
        outputstreamwriter.write(json);
        outputstreamwriter.close();
        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(httpsurlconnection.getInputStream()));
        StringBuffer stringbuffer = new StringBuffer();

        for (String s = bufferedreader.readLine(); s != null; s = bufferedreader.readLine())
        {
            stringbuffer.append(s);
        }

        bufferedreader.close();
        return stringbuffer.toString();
    }

    public static String normalizeString(String input)
    {
        char[] achar = input.toLowerCase().toCharArray();
        achar[0] = Character.toUpperCase(achar[0]);
        return new String(achar);
    }

    public static ArrayList<String> extractDomains(String value)
    {
        value = value.replaceAll(Color.c + "[a-z0-9]", "");
        ArrayList<String> arraylist = new ArrayList();

        if (value == null)
        {
            return arraylist;
        }
        else
        {
            String s = "(?i)\\b((?:[a-z][\\w-]+:(?:\\/{1,3}|[a-z0-9%])|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}\\/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:\'\".,<>?\u00ab\u00bb\u201c\u201d\u2018\u2019]))";
            Pattern pattern = Pattern.compile(s, 2);
            Matcher matcher = pattern.matcher(value);

            while (matcher.find())
            {
                arraylist.add(value.substring(matcher.start(0), matcher.end(0)));
            }

            return arraylist;
        }
    }

    public static class ConvertJsonToObject
    {
        private static Gson gson = (new GsonBuilder()).create();

        public static final <T> T getFromJSON(String json, Class<T> clazz)
        {
            return (T)gson.fromJson(json, clazz);
        }

        public static final <T> String toJSON(T clazz)
        {
            return gson.toJson(clazz);
        }
    }
}
