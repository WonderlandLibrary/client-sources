package club.bluezenith.ui.alt.rewrite.iptracker;

import club.bluezenith.BlueZenith;
import club.bluezenith.core.data.ClientResourceRepository;
import club.bluezenith.core.data.preferences.DataHandler;
import club.bluezenith.core.user.ClientUser;
import club.bluezenith.util.MinecraftInstance;
import club.bluezenith.util.client.ClientUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.lwjgl.Sys;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class IpTracker implements DataHandler {

    private List<IpAddress> history;

    private boolean errorDuringCheckingIP;
    private boolean isIpBanned;
    private volatile String lastIP;

    public int secondsLeftUntilNextCheck = 10;

    @Override
    public void deserialize() {
        BlueZenith.getBlueZenith().registerStartupTask(bz -> {
            final ClientResourceRepository repo = bz.getResourceRepository();
            if(repo.fileExists("tracker.history")) {
                try {
                    history = new ArrayList<>();
                    final BufferedReader reader = repo.getReaderForFile("tracker.history");
                    String line;
                    while ((line = reader.readLine()) != null) {
                        history.add(new IpAddress(line));
                    }

                    BlueZenith.scheduledExecutorService.scheduleAtFixedRate(this::checkCurrentIP, 10, 10, TimeUnit.SECONDS);

                    BlueZenith.scheduledExecutorService.scheduleAtFixedRate(
                            () -> secondsLeftUntilNextCheck = Math.max(0, secondsLeftUntilNextCheck - 1),
                            1,
                            1,
                            TimeUnit.SECONDS
                    );

                } catch (Exception exception) {
                    ClientUtils.getLogger().error("Failed to read tracker history.", exception);
                }
            } else {
                repo.createFileInDirectory("tracker.history", false);
                history = new ArrayList<>();
            }
        });
    }

    @Override
    public void serialize() {
        final StringBuilder builder = new StringBuilder();
        history.stream().distinct().forEach(ip -> builder.append(ip.getIpHash()).append("\n"));
        BlueZenith.getBlueZenith().getResourceRepository().writeToFile("tracker.history", builder.toString());
    }

    private void checkCurrentIP() {
        lastIP = grabIP();
        isIpBanned = history.stream().anyMatch(ip -> ip.compareIP(lastIP));
        secondsLeftUntilNextCheck = 10;
    }

    public boolean isIpBanned() {
        return isIpBanned;
    }

    public boolean failedToCheckIP() {
        return errorDuringCheckingIP;
    }

    private String grabIP() {
        errorDuringCheckingIP = false;
        try(CloseableHttpClient client = HttpClients.createDefault()) {
            final HttpGet get = new HttpGet("https://checkip.amazonaws.com/");
            final CloseableHttpResponse response = client.execute(get);

            final BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            final String ip = reader.readLine();

            response.close();
            reader.close();

            return lastIP = ip;
        } catch (Exception exception) {
            errorDuringCheckingIP = true;
            exception.printStackTrace();
            BlueZenith.getBlueZenith().getNotificationPublisher().postError(
                    "IP Tracker",
                    "Failed to check your IP address. \n See console for more.",
                    2500
            );
            secondsLeftUntilNextCheck = 10;
            return lastIP;
        }
    }

    public void markIpAsBanned() {
        if(lastIP == null) {
            BlueZenith.scheduledExecutorService.execute(() -> {
                grabIP();
                markIpAsBanned();
            });
            return;
        }
        history.add(new IpAddress(lastIP, true));
        checkCurrentIP();
    }
}
