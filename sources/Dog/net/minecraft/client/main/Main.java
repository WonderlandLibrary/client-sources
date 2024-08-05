package net.minecraft.client.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.properties.PropertyMap.Serializer;

import java.io.File;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.List;

import dev.liticane.transpiler.Native;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import fr.dog.util.backend.HWIDUtil;

@Native
public class Main {
    public static void main(String[] args) {
        System.setProperty("java.net.preferIPv4Stack", "true");

        OptionParser optionparser = new OptionParser();
        optionparser.allowsUnrecognizedOptions();

        optionparser.accepts("fullscreen");

        OptionSpec<String> server = optionparser.accepts("server").withRequiredArg();
        OptionSpec<Integer> port = optionparser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(25565);
        OptionSpec<File> gameDir = optionparser.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo(new File("."));
        OptionSpec<File> assetsDir = optionparser.accepts("assetsDir").withRequiredArg().ofType(File.class);
        OptionSpec<File> resourcePackDir = optionparser.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
        OptionSpec<String> proxyHost = optionparser.accepts("proxyHost").withRequiredArg();
        OptionSpec<Integer> proxyPort = optionparser.accepts("proxyPort").withRequiredArg().defaultsTo("8080", new String[0]).ofType(Integer.class);
        OptionSpec<String> proxyUser = optionparser.accepts("proxyUser").withRequiredArg();
        OptionSpec<String> proxyPass = optionparser.accepts("proxyPass").withRequiredArg();
        OptionSpec<String> username = optionparser.accepts("username").withRequiredArg().defaultsTo("DogClient" + Minecraft.getSystemTime() % 100L);
        OptionSpec<String> uuid = optionparser.accepts("uuid").withRequiredArg();
        OptionSpec<String> accessToken = optionparser.accepts("accessToken").withRequiredArg().required();
        OptionSpec<String> version = optionparser.accepts("version").withRequiredArg().required();
        OptionSpec<Integer> width = optionparser.accepts("width").withRequiredArg().ofType(Integer.class).defaultsTo(854);
        OptionSpec<Integer> height = optionparser.accepts("height").withRequiredArg().ofType(Integer.class).defaultsTo(480);
        OptionSpec<String> userProperties = optionparser.accepts("userProperties").withRequiredArg().defaultsTo("{}");
        OptionSpec<String> profileProperties = optionparser.accepts("profileProperties").withRequiredArg().defaultsTo("{}");
        OptionSpec<String> assetIndex = optionparser.accepts("assetIndex").withRequiredArg();
        OptionSpec<String> userType = optionparser.accepts("userType").withRequiredArg().defaultsTo("legacy");
        OptionSpec<String> uid = optionparser.accepts("uid").withRequiredArg();
        OptionSpec<String> nonOptions = optionparser.nonOptions();

        OptionSet optionset = optionparser.parse(args);
        List<String> list = optionset.valuesOf(nonOptions);

        if (!list.isEmpty()) {
            System.out.println("Completely ignored arguments: " + list);
        }

        String proxyHostValue = optionset.valueOf(proxyHost);
        Proxy proxy = Proxy.NO_PROXY;

        if (proxyHostValue != null) {
            try {
                proxy = new Proxy(Type.SOCKS, new InetSocketAddress(proxyHostValue, optionset.valueOf(proxyPort)));
            } catch (Exception ignored) { /* */ }
        }

        final String proxyUserValue = optionset.valueOf(proxyUser);
        final String proxyPassValue = optionset.valueOf(proxyPass);

        if (!proxy.equals(Proxy.NO_PROXY) && isNullOrEmpty(proxyUserValue) && isNullOrEmpty(proxyPassValue)) {
            Authenticator.setDefault(new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(proxyUserValue, proxyPassValue.toCharArray());
                }
            });
        }

        int widthValue = optionset.valueOf(width);
        int heightValue = optionset.valueOf(height);
        boolean isFullscreen = optionset.has("fullscreen");

        String versionValue = optionset.valueOf(version);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(PropertyMap.class, new Serializer())
                .create();

        PropertyMap userPropertyMap = gson.fromJson(optionset.valueOf(userProperties), PropertyMap.class);
        PropertyMap profilePropertyMap = gson.fromJson(optionset.valueOf(profileProperties), PropertyMap.class);

        File gameDirectory = optionset.valueOf(gameDir);
        File assetsDirectory = optionset.has(assetsDir) ? optionset.valueOf(assetsDir) : new File(gameDirectory, "assets/");
        File resourcePackDirectory = optionset.has(resourcePackDir) ? optionset.valueOf(resourcePackDir) : new File(gameDirectory, "resourcepacks/");

        String uuidValue = optionset.has(uuid) ? uuid.value(optionset) : username.value(optionset);
        String assetIndexValue = optionset.has(assetIndex) ? assetIndex.value(optionset) : null;
        String serverValue = optionset.valueOf(server);

        Integer integer = optionset.valueOf(port);
        Session session = new Session(username.value(optionset), uuidValue, accessToken.value(optionset), userType.value(optionset));

        GameConfiguration gameconfiguration = new GameConfiguration(
                new GameConfiguration.UserInformation(session, userPropertyMap, profilePropertyMap, proxy),
                new GameConfiguration.DisplayInformation(widthValue, heightValue, isFullscreen, false),
                new GameConfiguration.FolderInformation(gameDirectory, resourcePackDirectory, assetsDirectory, assetIndexValue),
                new GameConfiguration.GameInformation(false, versionValue),
                new GameConfiguration.ServerInformation(serverValue, integer)
        );

        Runtime.getRuntime().addShutdownHook(new Thread("Client Shutdown Thread") {
            public void run() {
                Minecraft.stopIntegratedServer();
            }
        });

        String uidValue = optionset.valueOf(uid);

        System.out.printf(
                    """
                    Your HWID is:
                    %s
                    If you cannot launch the client, send it to @getrektnerds on Discord.
                    """, HWIDUtil.getHWID()
        );

        Thread.currentThread().setName("Client thread");
        new Minecraft(gameconfiguration).run(uidValue,"on");
    }

    private static boolean isNullOrEmpty(String str) {
        return str != null && !str.isEmpty();
    }
}