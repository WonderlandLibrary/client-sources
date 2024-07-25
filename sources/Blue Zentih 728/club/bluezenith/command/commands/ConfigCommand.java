package club.bluezenith.command.commands;

import club.bluezenith.BlueZenith;
import club.bluezenith.command.Command;
import club.bluezenith.util.client.Chat;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import security.auth.https.TrustedConnector;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.ScheduledFuture;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

@SuppressWarnings("unused")
public final class ConfigCommand extends Command {

    public ConfigCommand() {
        super("Config", "Manage your configs.","Usage: .config <load/save> <name> (binds) (norender)", "cfg");
    }

    private ScheduledFuture<?> task;
    @Override
    @SuppressWarnings("all")
    public void execute(String[] args) {
        if(args.length < 2) {
            chat("Usage: .config <load/save> <name> (binds) (norender)");
            return;
        }

        switch (args[1].toLowerCase()) {
            case "list":
                printConfigs();
                break;

            case "clear":
                clearConfigsWithDelay();
                break;

            case "load":
                final String regex = "(http(s)?:\\/\\/.)?(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[A-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";
                final String name = args[2];
                if(name.matches(regex)) {
                    new Thread(() -> {
                        try (final CloseableHttpClient client = HttpClients.createDefault();) {
                            Chat.bz("Loading an online config... ");
                            final HttpGet get = new HttpGet(name);
                            get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:95.0) Gecko/20100101 Firefox/95.0");

                            final File the = new File("online-config-" + randomAlphanumeric(5) + ".json");
                            final FileOutputStream configObject = new FileOutputStream(the);
                            final byte[] data = TrustedConnector.readNBytes(client.execute(get).getEntity().getContent(), Integer.MAX_VALUE);
                            configObject.write(data);
                            configObject.close();

                            getBlueZenith().getConfigManager().loadConfigFromFile(the, "online-config",
                                    args.length >= 4 && "binds".equals(args[3].toLowerCase())
                                            || args.length >= 5 && "binds".equals(args[4].toLowerCase()),
                                    args.length >= 4 && "norender".equals(args[3].toLowerCase())
                                            || args.length >= 5 && "norender".equals(args[4].toLowerCase()),
                                    true);
                            the.delete();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                            Chat.bzf("Failed to download config: %s", exception.getClass().getSimpleName());
                        }
                    }).start();
                } else {
                    getBlueZenith().getConfigManager().loadConfigFromName(args[2],
                            args.length >= 4 && "binds".equals(args[3].toLowerCase())
                                    || args.length >= 5 && "binds".equals(args[4].toLowerCase()),
                            args.length >= 4 && "norender".equals(args[3].toLowerCase())
                                    || args.length >= 5 && "norender".equals(args[4].toLowerCase()),
                            true);
                }
            break;

            case "save":
                getBlueZenith().getConfigManager().saveConfig(args[2], true);
            break;
        }
    }

    private void printConfigs() {
        final String[] configs = getBlueZenith().getConfigManager().getConfigs();
        if(configs == null || configs.length == 0) {
            getBlueZenith().getNotificationPublisher().postError("Config Manager", "Couldn't find any configs", 2500);
            return;
        }
        int amount = 1;
        for(String cfg : getBlueZenith().getConfigManager().getConfigs()) {
            chat(String.format("%s: %s", amount++, FilenameUtils.removeExtension(cfg)));
        }
    }

    private void clearConfigsWithDelay() {
        getBlueZenith().getNotificationPublisher().postWarning("Config Manager", "Your configs will be deleted in 10 seconds.\nType .cfg cancel to cancel deletion", 3000);
        task = BlueZenith.scheduledExecutorService.schedule(() -> {
            getBlueZenith().getConfigManager().clearConfigs();
            getBlueZenith().getNotificationPublisher().postSuccess("Config Manager", "Deleted all configs", 2500);
        }, 10, SECONDS);
    }
}
