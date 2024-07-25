package club.bluezenith.module.modules.fun.zombies.components;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.module.modules.fun.zombies.Zombies;
import club.bluezenith.ui.draggables.Draggable;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static club.bluezenith.util.MinecraftInstance.mc;

public class StrategyComponent implements Draggable {
    private float x, y, width, height, scroll;

    private final Zombies zombies;
    private final List<String> strategyLines = new ArrayList<>();
    private String strategy;

    public StrategyComponent(Zombies zombies) {
        this.zombies = zombies;
    }

    @Override
    public void draw(Render2DEvent event) {
        if(strategyLines.isEmpty())
            return;

        float y;
        final int lines = strategyLines.size();

    }

    public void resize(float width, float height) {
        if(this.width != width) {
            this.strategyLines.clear();
            this.strategyLines.addAll(mc.fontRendererObj.listFormattedStringToWidth(strategy, (int) width));
            this.width = width;
        }
        this.height = height;
    }

    public void fetch() {
        BlueZenith.scheduledExecutorService.execute(() -> {
            try (final CloseableHttpClient client = HttpClients.createDefault()) {
                final HttpGet get = new HttpGet(zombies.strategyURL.get());
                final CloseableHttpResponse response = client.execute(get);

                if(response.getStatusLine().getStatusCode() / 100 != 2) {
                    BlueZenith.getBlueZenith().getNotificationPublisher().postError(
                            "Strategy",
                            "Couldn't fetch the text. \n " +
                                    "Reason: " + response.getStatusLine().getReasonPhrase(),
                            3000
                    );
                    return;
                }

                final BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;
                StringBuilder builder = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    builder.append(line).append("\n");
                }

                strategy = builder.toString();
                this.strategyLines.clear();
                this.strategyLines.addAll(mc.fontRendererObj.listFormattedStringToWidth(strategy, zombies.strategyWidth.get()));
                response.close();
            } catch (Exception exception) {
                BlueZenith.getBlueZenith().getNotificationPublisher().postError(
                        "Strategy",
                        "Couldn't fetch URL. \n See console for info.",
                        2500
                );
                exception.printStackTrace();
            }
        });
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    @Override
    public boolean shouldBeRendered() {
        return zombies.getState() && zombies.showStrategy.get();
    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return checkMouseBounds(mouseX, mouseY, x, y, x + width, y + height);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void moveTo(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
