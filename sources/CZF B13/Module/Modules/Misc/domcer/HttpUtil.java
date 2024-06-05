package gq.vapu.czfclient.Module.Modules.Misc.domcer;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

public class HttpUtil {
    private static final String BOUNDARY_PREFIX = "--";
    private static final String LINE_END = "\r\n";

    public static HttpResponse postFormData(String urlStr, Map<String, byte[]> filePathMap, Map<String, Object> keyValues, Map<String, Object> headers) throws IOException {
        HttpURLConnection conn = HttpUtil.getHttpURLConnection(urlStr, headers);
        String boundary = "MyBoundary" + System.currentTimeMillis();
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        try (DataOutputStream out = new DataOutputStream(conn.getOutputStream())) {
            if (keyValues != null && !keyValues.isEmpty()) {
                for (Map.Entry<String, Object> entry : keyValues.entrySet()) {
                    HttpUtil.writeSimpleFormField(boundary, out, entry);
                }
            }
            if (filePathMap != null && !filePathMap.isEmpty()) {
                for (Map.Entry<String, byte[]> entry : filePathMap.entrySet()) {
                    HttpUtil.writeFile(entry.getKey(), entry.getValue(), boundary, out);
                }
            }
            String endStr = BOUNDARY_PREFIX + boundary + BOUNDARY_PREFIX + LINE_END;
            out.write(endStr.getBytes());
        } catch (Exception e) {
            HttpResponse response = new HttpResponse(500, e.getMessage());
            return response;
        }
        return HttpUtil.getHttpResponse(conn);
    }

    private static HttpURLConnection getHttpURLConnection(String urlStr, Map<String, Object> headers) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(50000);
        conn.setReadTimeout(50000);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("connection", "keep-alive");
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, Object> header : headers.entrySet()) {
                conn.setRequestProperty(header.getKey(), header.getValue().toString());
            }
        }
        return conn;
    }

    private static HttpResponse getHttpResponse(HttpURLConnection conn) {
        HttpResponse response;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            int responseCode = conn.getResponseCode();
            StringBuilder responseContent = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            response = new HttpResponse(responseCode, responseContent.toString());
        } catch (Exception e) {
            response = new HttpResponse(500, e.getMessage());
        }
        return response;
    }

    private static void writeFile(String paramName, byte[] bytes, String boundary, DataOutputStream out) {
        try {
            String boundaryStr = BOUNDARY_PREFIX + boundary + LINE_END;
            out.write(boundaryStr.getBytes());
            String fileName = UUID.randomUUID() + ".jpg";
            String contentDispositionStr = String.format("Content-Disposition: form-data; name=\"%s\"; filename=\"%s\"", paramName, fileName) + LINE_END;
            out.write(contentDispositionStr.getBytes());
            String contentType = "Content-Type: application/octet-stream\r\n\r\n";
            out.write(contentType.getBytes());
            out.write(bytes);
            out.write(LINE_END.getBytes());
        } catch (Exception exception) {
            // empty catch block
        }
    }

    private static void writeSimpleFormField(String boundary, DataOutputStream out, Map.Entry<String, Object> entry) throws IOException {
        String boundaryStr = BOUNDARY_PREFIX + boundary + LINE_END;
        out.write(boundaryStr.getBytes());
        String contentDispositionStr = String.format("Content-Disposition: form-data; name=\"%s\"", entry.getKey()) + LINE_END + LINE_END;
        out.write(contentDispositionStr.getBytes());
        String valueStr = entry.getValue().toString() + LINE_END;
        out.write(valueStr.getBytes());
    }

    public static HttpResponse postText(String urlStr, String filePath) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "text/plain");
        conn.setDoOutput(true);
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
             BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                writer.write(line);
            }
        } catch (Exception e) {
            HttpResponse response = new HttpResponse(500, e.getMessage());
            return response;
        }
        return HttpUtil.getHttpResponse(conn);
    }

    public static class HttpResponse {
        private int code;
        private String content;

        public HttpResponse(int status, String content) {
            this.code = status;
            this.content = content;
        }

        public int getCode() {
            return this.code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getContent() {
            return this.content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String toString() {
            return "[ code = " + this.code + " , content = " + this.content + " ]";
        }
    }
}
