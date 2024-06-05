package xyz.charlie35.mcauth_server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class AuthManagerWebServer {
    static void log(String s) {
        System.out.println(s);
    }

    public static ConcurrentHashMap<UUID, AuthInfo> authCache = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, Long> lastRequestTime = new ConcurrentHashMap<>();

    public static final int THREADS = 50;

    public static String CLIENT_ID = "";
    public static String CLIENT_SECRET = "";

    public static String REDIRECT_URI = "";

    public static final int TOKEN_STORE_TIME_MS = 30 * 1000;
    public static final int TIME_BETWEEN_REQS = 4000;

    public static void main(String[] args) throws IOException {
        log("Starting MCAUTH webserver -- Copyright charlie353535");

        if (args.length!=4) {
            System.out.println("Usage: ./thisfile <client ID> <client secret> <redirect URI> <port>");
            System.exit(-1);
        }

        CLIENT_ID = args[0];
        CLIENT_SECRET = args[1];
        REDIRECT_URI = args[2];

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREADS);

        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", Integer.parseInt(args[3])), 0);
        server.createContext("/auth", new OAuthHandler());
        server.createContext("/userpass", new UserPassHandler());
        server.createContext("/get", new CachedTokenHandler());
        server.setExecutor(threadPoolExecutor);
        server.start();
        log("Server started on port "+args[3]+" ["+REDIRECT_URI+"]");
    }

    public static boolean handleRatelimit(String client) {
        lastRequestTime.putIfAbsent(client, 0L);
        if (System.currentTimeMillis()-lastRequestTime.get(client) <= TIME_BETWEEN_REQS) {
            //lastRequestTime.put(client, System.currentTimeMillis());
            return true;
        }
        lastRequestTime.put(client, System.currentTimeMillis());
        return false;
    }

    public static long timeToNoRateLimit(String client) {
        if (!lastRequestTime.containsKey(client))
            return 0;
        return 4000L-(System.currentTimeMillis()-lastRequestTime.get(client));
    }

    static class AuthInfo {
        public long time;
        public String info;
        public String addr;
        public AuthInfo(long a, String b, String c) {
            time=a;
            info=b;
            addr=c;
        }
    }
}
