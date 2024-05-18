package de.labystudio.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MultipartUtility
{
    private final String boundary;
    private static final String LINE_FEED = "\r\n";
    private HttpURLConnection httpConn;
    private String charset;
    private OutputStream outputStream;
    private PrintWriter writer;

    public MultipartUtility(String requestURL, String charset) throws IOException
    {
        this.charset = charset;
        this.boundary = "" + System.currentTimeMillis() + "";
        URL url = new URL(requestURL);
        this.httpConn = (HttpURLConnection)url.openConnection();
        this.httpConn.setUseCaches(false);
        this.httpConn.setDoOutput(true);
        this.httpConn.setDoInput(true);
        this.httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + this.boundary);
        this.httpConn.setRequestProperty("User-Agent", "curl/7.49.0");
        this.httpConn.setRequestMethod("PUT");
    }

    public void addRequestProperty(String key, String value)
    {
        this.httpConn.setRequestProperty(key, value);
    }

    public void open()
    {
        try
        {
            this.outputStream = this.httpConn.getOutputStream();
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }

        try
        {
            this.writer = new PrintWriter(new OutputStreamWriter(this.outputStream, this.charset), true);
        }
        catch (UnsupportedEncodingException unsupportedencodingexception)
        {
            unsupportedencodingexception.printStackTrace();
        }
    }

    public void addFormField(String name, String value)
    {
        this.writer.append("--" + this.boundary).append("\r\n");
        this.writer.append("Content-Disposition: form-data; name=\"" + name + "\"").append("\r\n");
        this.writer.append("\r\n");
        this.writer.append(value).append("\r\n");
        this.writer.flush();
    }

    public void addFilePart(String fieldName, File uploadFile) throws IOException
    {
        String s = uploadFile.getName();
        this.writer.append("--" + this.boundary).append("\r\n");
        this.writer.append("Content-Disposition: form-data; name=\"" + fieldName + "\"; filename=\"" + s + "\"").append("\r\n");
        this.writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(s)).append("\r\n");
        this.writer.append("\r\n");
        this.writer.flush();
        FileInputStream fileinputstream = new FileInputStream(uploadFile);
        byte[] abyte = new byte[4096];
        int i = -1;

        while ((i = fileinputstream.read(abyte)) != -1)
        {
            this.outputStream.write(abyte, 0, i);
        }

        this.outputStream.flush();
        fileinputstream.close();
        this.writer.append("\r\n");
        this.writer.flush();
    }

    public void addHeaderField(String name, String value)
    {
        this.writer.append(name + ": " + value).append("\r\n");
        this.writer.flush();
    }

    public List<String> finish() throws IOException
    {
        List<String> list = new ArrayList();
        this.writer.append("\r\n").flush();
        this.writer.append("--" + this.boundary + "--").append("\r\n");
        this.writer.close();
        int i = this.httpConn.getResponseCode();

        if (i == 200)
        {
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(this.httpConn.getInputStream()));
            String s = null;

            while ((s = bufferedreader.readLine()) != null)
            {
                list.add(s);
            }

            bufferedreader.close();
            this.httpConn.disconnect();
        }
        else
        {
            try
            {
                BufferedReader bufferedreader1 = new BufferedReader(new InputStreamReader(this.httpConn.getErrorStream()));
                String s1 = null;

                while ((s1 = bufferedreader1.readLine()) != null)
                {
                    list.add(s1);
                }

                bufferedreader1.close();
                this.httpConn.disconnect();
            }
            catch (Exception var5)
            {
                System.out.println(i);
                return list;
            }
        }

        return list;
    }
}
