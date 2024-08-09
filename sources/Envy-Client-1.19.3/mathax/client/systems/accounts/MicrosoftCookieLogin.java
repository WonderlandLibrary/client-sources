package mathax.client.systems.accounts;

import mathax.client.MatHax;
import mathax.client.utils.network.HTTP;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.net.URI;
import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class MicrosoftCookieLogin {
    @Nullable
    public static String getRefreshTokenFromCookie(File cookieFile) {
        try {
            Map<String, String> cookies = getCookies(cookieFile);

            // first step: get first form
            HttpResponse<String> first = HTTP.get(MicrosoftLogin.getUrl())
                .header("Cookie", getCookieHeader(cookies))
                ._sendRaw("*/*", HttpResponse.BodyHandlers.ofString());

            addCookies(first.headers(), cookies);

            // parse page and get form to send
            Document firstPage = Jsoup.parse(first.body());
            Element form = firstPage.getElementsByTag("form").get(0);
            String action = form.attr("action");
            StringBuilder formBuilder = new StringBuilder();
            for (Element input : form.getElementsByTag("input")) {
                formBuilder
                    .append(input.attr("id"))
                    .append("=")
                    .append(input.attr("value"))
                    .append("&");
            }
            formBuilder.deleteCharAt(formBuilder.length() - 1); // last &

            HttpResponse<String> second = HTTP.post(action)
                .header("Cookie", getCookieHeader(cookies))
                .bodyForm(formBuilder.toString())
                ._sendRaw("*/*", HttpResponse.BodyHandlers.ofString());

            addCookies(second.headers(), cookies);
            MatHax.LOG.info(second.toString());
            MatHax.LOG.info(second.body());

            Document secondPage = Jsoup.parse(second.body());
            if (secondPage.getElementById("errorMessage") != null) {
                throw new RuntimeException("Something went wrong!");
            }
            form = secondPage.getElementsByTag("form").get(0);
            formBuilder = new StringBuilder();
            for (Element input : form.getElementsByTag("input")) {
                formBuilder
                    .append(input.attr("id"))
                    .append("=")
                    .append(input.attr("value"))
                    .append("&");
            }
            formBuilder.deleteCharAt(formBuilder.length() - 1); // last &

            HttpResponse<InputStream> third = HTTP.post(action)
                .header("Cookie", getCookieHeader(cookies))
                .bodyForm(formBuilder.toString())
                ._sendRaw("*/*", HttpResponse.BodyHandlers.ofInputStream());

            HttpHeaders headers = third.headers();
            if (third.statusCode() != 302 || headers.firstValue("Location").isEmpty()) return null; // we want a redirect

            String redirectUrl = headers.firstValue("Location").get();

            addCookies(headers, cookies);
            HttpResponse<String> fourth = HTTP.get(redirectUrl)
                .header("Cookie", getCookieHeader(cookies))
                ._sendRaw("*/*", HttpResponse.BodyHandlers.ofString());

            headers = fourth.headers();
            if (fourth.statusCode() != 302 || headers.firstValue("Location").isEmpty())
                return null; // we want a redirect

            redirectUrl = headers.firstValue("Location").get();

            addCookies(headers, cookies);

            MatHax.LOG.info(fourth.toString());
            MatHax.LOG.info(fourth.body());

            MatHax.LOG.info(redirectUrl);
            // last step: get refresh token
            List<NameValuePair> query = URLEncodedUtils.parse(URI.create(redirectUrl), StandardCharsets.UTF_8.name());
            for (NameValuePair param : query) {
                if (param.getName().equals("code")) {
                    return MicrosoftLogin.handleCode(param.getValue());
                }
                else if (param.getName().equals("error") || param.getName().equals("error_description")) {
                    MatHax.LOG.error(param.getValue());
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Map<String, String> getCookies(File file) throws FileNotFoundException {
        Map<String, String> cookies = new HashMap<>();

        // read file
        FileInputStream stream = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        // netscape format
        for (String line : reader.lines().toList()) {
            String[] parts = line.split("\t");
            if (parts.length != 7) throw new RuntimeException("File is not valid Netscape format!");

            cookies.put(parts[5], parts[6]);
        }

        return cookies;
    }

    private static String getCookieHeader(Map<String, String> cookies) {
        StringBuilder sb = new StringBuilder();

        boolean didAdd = false;
        for (Map.Entry<String, String> cookie : cookies.entrySet()) {
            if (didAdd) sb.append("; ");
            sb.append(cookie.getKey()).append("=").append(cookie.getValue());
            didAdd = true;
        }

        return sb.toString();
    }

    private static void addCookies(HttpHeaders headers, Map<String, String> cookies) {
        for (String cookie : headers.allValues("Set-cookie")) {
            String cookieRaw = cookie.split(";")[0];
            cookies.put(cookieRaw.split("=")[0], cookieRaw.endsWith("=") ? "" : cookieRaw.split("=")[1]);
        }
    }
}
