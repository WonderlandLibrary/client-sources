package pw.latematt.xiv.main;

import com.google.gson.GsonBuilder;
import com.mojang.authlib.properties.PropertyMap;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.NonOptionArgumentSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.util.Session;

import java.io.File;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.util.List;

/**
 * @author Matthew
 */
public class XIVMain {
    public static void main(String[] arguments) {
        System.setProperty("java.net.preferIPv4Stack", "true");

        /* parsing arguments */
        OptionParser optionParser = new OptionParser();
        optionParser.allowsUnrecognizedOptions();
        optionParser.accepts("demo");
        optionParser.accepts("fullscreen");
        optionParser.accepts("checkGlErrors");
        ArgumentAcceptingOptionSpec server = optionParser.accepts("server").withRequiredArg();
        ArgumentAcceptingOptionSpec port = optionParser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(25565);
        ArgumentAcceptingOptionSpec gameDir = optionParser.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo(new File("."));
        ArgumentAcceptingOptionSpec assetsDir = optionParser.accepts("assetsDir").withRequiredArg().ofType(File.class);
        ArgumentAcceptingOptionSpec resourcePackDir = optionParser.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
        ArgumentAcceptingOptionSpec proxyHost = optionParser.accepts("proxyHost").withRequiredArg();
        ArgumentAcceptingOptionSpec proxyPort = optionParser.accepts("proxyPort").withRequiredArg().defaultsTo("8080").ofType(Integer.class);
        ArgumentAcceptingOptionSpec proxyUser = optionParser.accepts("proxyUser").withRequiredArg();
        ArgumentAcceptingOptionSpec proxyPass = optionParser.accepts("proxyPass").withRequiredArg();
        ArgumentAcceptingOptionSpec username = optionParser.accepts("username").withRequiredArg().defaultsTo("Player" + Minecraft.getSystemTime() % 1000L);
        ArgumentAcceptingOptionSpec uuid = optionParser.accepts("uuid").withRequiredArg();
        ArgumentAcceptingOptionSpec accessToken = optionParser.accepts("accessToken").withRequiredArg().required();
        ArgumentAcceptingOptionSpec version = optionParser.accepts("version").withRequiredArg().required();
        ArgumentAcceptingOptionSpec width = optionParser.accepts("width").withRequiredArg().ofType(Integer.class).defaultsTo(854);
        ArgumentAcceptingOptionSpec height = optionParser.accepts("height").withRequiredArg().ofType(Integer.class).defaultsTo(480);
        ArgumentAcceptingOptionSpec userProperties = optionParser.accepts("userProperties").withRequiredArg().required();
        ArgumentAcceptingOptionSpec assetIndex = optionParser.accepts("assetIndex").withRequiredArg();
        ArgumentAcceptingOptionSpec userType = optionParser.accepts("userType").withRequiredArg().defaultsTo("legacy");
        NonOptionArgumentSpec nonOptions = optionParser.nonOptions();
        OptionSet optionSet = optionParser.parse(arguments);
        List valuesOfNonOptions = optionSet.valuesOf(nonOptions);

        if (!valuesOfNonOptions.isEmpty())
            System.out.println("Completely ignored arguments: " + valuesOfNonOptions);

        /* proxy support */
        String proxyHostString = (String) optionSet.valueOf(proxyHost);
        final String proxyUserString = (String) optionSet.valueOf(proxyUser);
        final String proxyPassString = (String) optionSet.valueOf(proxyPass);
        Proxy proxy = Proxy.NO_PROXY;

        if (proxyHostString != null) {
            try {
                proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(proxyHostString, (Integer) optionSet.valueOf(proxyPort)));
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!proxy.equals(Proxy.NO_PROXY) && func_110121_a(proxyUserString) && func_110121_a(proxyPassString)) {
                Authenticator.setDefault(new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(proxyUserString, proxyPassString.toCharArray());
                    }
                });
            }
        }

        /* option variables */
        int widthInt = (Integer) optionSet.valueOf(width);
        int heightInt = (Integer) optionSet.valueOf(height);
        boolean fullscreen = optionSet.has("fullscreen");
        boolean checkGlErrors = optionSet.has("checkGlErrors");
        boolean demo = optionSet.has("demo");
        String versionString = (String) optionSet.valueOf(version);
        PropertyMap userPropertiesMap = (new GsonBuilder()).registerTypeAdapter(PropertyMap.class, new PropertyMap.Serializer()).create().fromJson((String) optionSet.valueOf(userProperties), PropertyMap.class);
        File gameDirFile = (File) optionSet.valueOf(gameDir);
        File assetsDirFile = optionSet.has(assetsDir) ? (File) optionSet.valueOf(assetsDir) : new File(gameDirFile, "assets/");
        File resourcePackDirFile = optionSet.has(resourcePackDir) ? (File) optionSet.valueOf(resourcePackDir) : new File(gameDirFile, "resourcepacks/");
        String uuidString = optionSet.has(uuid) ? (String) uuid.value(optionSet) : (String) username.value(optionSet);
        String uuidIndex = optionSet.has(assetIndex) ? (String) assetIndex.value(optionSet) : null;
        String serverString = (String) optionSet.valueOf(server);
        Integer portInteger = (Integer) optionSet.valueOf(port);
        Session session = new Session((String) username.value(optionSet), uuidString, (String) accessToken.value(optionSet), (String) userType.value(optionSet));

        /* game runs now */
        GameConfiguration gameConfiguration = new GameConfiguration(new GameConfiguration.UserInformation(session, userPropertiesMap, proxy), new GameConfiguration.DisplayInformation(widthInt, heightInt, fullscreen, checkGlErrors), new GameConfiguration.FolderInformation(gameDirFile, resourcePackDirFile, assetsDirFile, uuidIndex), new GameConfiguration.GameInformation(demo, versionString), new GameConfiguration.ServerInformation(serverString, portInteger.intValue()));
        Thread.currentThread().setName("Client Thread");
        Runtime.getRuntime().addShutdownHook(new Thread(Minecraft::stopIntegratedServer, "Client Shutdown Thread"));
        new Minecraft(gameConfiguration).run();
    }

    private static boolean func_110121_a(String p_110121_0_) {
        return p_110121_0_ != null && !p_110121_0_.isEmpty();
    }
}
