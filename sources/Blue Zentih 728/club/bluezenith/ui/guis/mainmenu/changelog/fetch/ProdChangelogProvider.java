package club.bluezenith.ui.guis.mainmenu.changelog.fetch;

import club.bluezenith.ui.guis.mainmenu.changelog.fetch.ChangelogEntry.EntryType;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import security.auth.loader.SimpleLoader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static club.bluezenith.ui.guis.mainmenu.changelog.fetch.ChangelogEntry.EntryType.values;
import static java.util.Arrays.stream;

public class ProdChangelogProvider implements ChangelogProvider {

    List<ChangelogEntry> changelogEntries = new ArrayList<>();
    private volatile boolean done;

    @Override
    public void fetch() {
        final HttpGet get = new HttpGet("https://service.bluezenith.club/v3/changelog/" + SimpleLoader.version);

        try(CloseableHttpClient client = HttpClients.createDefault()) {
            final CloseableHttpResponse response = client.execute(get);
            final BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) !=  null)
                stringBuilder.append(line);

            final JsonArray array = new JsonParser().parse(stringBuilder.toString()).getAsJsonArray();

            changelogEntries.clear();

            for (JsonElement jsonElement : array) {
                final JsonObject object = jsonElement.getAsJsonObject();
                changelogEntries.add(new ChangelogEntry(
                        object.get("content").getAsString(),
                        stream(values()).filter(type -> type.getCharacter().equals(object.get("type").getAsString())).findFirst().orElse(EntryType.OTHER)
                ));
            }

            done = true;
            response.close();
            reader.close();

        } catch (Exception exception) {
            System.err.println("Failed to retrieve production changelog");
            exception.printStackTrace();
        }
    }

    @Override
    public List<ChangelogEntry> getChangelogEntries() {
        return changelogEntries;
    }

    @Override
    public boolean hasFetchedChangelog() {
        return done;
    }
}
