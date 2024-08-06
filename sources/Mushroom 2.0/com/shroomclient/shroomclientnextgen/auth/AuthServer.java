package com.shroomclient.shroomclientnextgen.auth;

import com.shroomclient.shroomclientnextgen.util.C;
import com.sun.net.httpserver.HttpServer;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.session.Session;

// https://login.live.com/oauth20_authorize.srf?client_id=54fd49e4-2103-4044-9603-2b028c814ec3&response_type=code&scope=XboxLive.signin%20XboxLive.offline_access&redirect_uri=http://localhost:59125/
public class AuthServer {

    private final Pattern codePattern = Pattern.compile("\\?code=(.+?)$", 0);

    public AuthServer() {
        new Thread(this::start).start();
    }

    private void start() {
        try {
            HttpServer s = HttpServer.create(
                new InetSocketAddress("127.0.0.1", 59125),
                0
            );
            s.createContext("/", exchange -> {
                OutputStream out = exchange.getResponseBody();
                exchange.sendResponseHeaders(200, "".getBytes().length);
                out.write("".getBytes());
                out.flush();
                out.close();

                Matcher m = codePattern.matcher(
                    exchange.getRequestURI().toString()
                );
                if (m.find()) {
                    String code = m.group(1);
                    try {
                        MSAuth.MSASession session = MSAuth.authWithCode(code);
                        SessionManager.sessionOverride = new Session(
                            session.username,
                            UUID.fromString(
                                session.uuid.replaceFirst(
                                    "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
                                    "$1-$2-$3-$4-$5"
                                )
                            ),
                            session.accessToken,
                            Optional.empty(),
                            Optional.empty(),
                            Session.AccountType.MOJANG
                        );
                        System.out.println(SessionManager.sessionOverride);
                    } catch (Exception ex) {
                        C.logger.error("Failed to login with code");
                        ex.printStackTrace();
                    }
                }
            });
            s.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
