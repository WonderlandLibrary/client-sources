package xyz.charlie35.mcauth_server;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class UserAuthRequestor {
    public static String getTokenFor(String u, String p, String client) throws AuthenticationException {
        String cookie = "";
        String PPFT = "";
        String urlPost = "";

        System.out.println("> Requesting initial parameters for "+client);
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://login.live.com/oauth20_authorize.srf?redirect_uri=https://login.live.com/oauth20_desktop.srf&scope=service::user.auth.xboxlive.com::MBI_SSL&display=touch&response_type=code&locale=en&client_id=00000000402b5328").openConnection();
            connection.setDoInput(true);
            try (InputStream in = connection.getResponseCode() == 200 ? connection.getInputStream() : connection.getErrorStream()) {
                cookie = connection.getHeaderField("set-cookie");
                String body = new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.joining());
                Matcher m = Pattern.compile("sFTTag:[ ]?'.*value=\"(.*)\"/>'").matcher(body);
                if (m.find()) {
                    PPFT = m.group(1);
                } else {
                    throw new AuthenticationException("U/P Auth error: Unable to parse response! [1]");
                }

                m = Pattern.compile("urlPost:[ ]?'(.+?(?='))").matcher(body);
                if (m.find()) {
                    urlPost = m.group(1);
                } else {
                    throw new AuthenticationException("U/P Auth error: Unable to parse response! [2]");
                }
            }
        } catch (IOException e) {
            throw new AuthenticationException("U/P Auth error: generic IO error");
        }

        if (cookie.isEmpty() || PPFT.isEmpty() || urlPost.isEmpty()) {
            throw new AuthenticationException("U/P Auth error: Invalid response, missing one or more of cookie, PPFT or urlPost");
        }

        Map<String, String> map = new HashMap<>();

        map.put("login", u);
        map.put("loginfmt",u);
        map.put("passwd", p);
        map.put("PPFT", PPFT);

        String postData = urlEncodeUTF8(map);
        String code;

        System.out.println("> Logging in with user/password for "+client);

        try {
            byte[] bytes = postData.getBytes(StandardCharsets.UTF_8);

            HttpURLConnection connection = (HttpURLConnection) new URL(urlPost).openConnection();
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            connection.setRequestProperty("Content-Length", String.valueOf(bytes.length));
            connection.setRequestProperty("Cookie", cookie);

            connection.setDoInput(true);
            connection.setDoOutput(true);

            try(OutputStream out = connection.getOutputStream()) {
                out.write(bytes);
            }

            if (connection.getResponseCode() != 200 || connection.getURL().toString().equals(urlPost)) {
                throw new AuthenticationException("Invalid username and/or password");
            }

            Matcher m = Pattern.compile("[?|&]code=([\\w.-]+)").matcher(URLDecoder.decode(connection.getURL().toString(), StandardCharsets.UTF_8.name()));
            if (m.find()) {
                code = m.group(1);
            } else {
                throw new AuthenticationException("U/P Phase 2: Could not parse response of '" + urlPost + "'.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new AuthenticationException("U/P Phase 2:  Could not make request!");
        }

        return code;
    }

    static String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }
    static String urlEncodeUTF8(Map<?,?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    urlEncodeUTF8(entry.getKey().toString()),
                    urlEncodeUTF8(entry.getValue().toString())
            ));
        }
        return sb.toString();
    }
}
