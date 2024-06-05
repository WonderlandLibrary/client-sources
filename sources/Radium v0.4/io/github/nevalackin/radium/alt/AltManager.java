package io.github.nevalackin.radium.alt;

import com.thealtening.api.retriever.BasicDataRetriever;
import com.thealtening.auth.TheAlteningAuthentication;
import io.github.nevalackin.radium.RadiumClient;
import io.github.nevalackin.radium.utils.FileUtils;
import io.github.nevalackin.radium.utils.handler.Manager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class AltManager extends Manager<Alt> {

    public static final File ALT_FILE = new File(RadiumClient.NAME, "alts.txt");
    public static final File ALTENING_TOKEN_FILE = new File(RadiumClient.NAME, "token.txt");
    private final BasicDataRetriever alteningAltFetcher;
    private final TheAlteningAuthentication alteningAuth;
    private String apiKey = "api-0000-0000-0000";
    public AltManager() {
        super(new ArrayList<>());
        this.alteningAuth = TheAlteningAuthentication.mojang();
        this.alteningAltFetcher = new BasicDataRetriever(apiKey);

        if (!ALT_FILE.exists()) {
            try {
                ALT_FILE.createNewFile();
            } catch (IOException ignored) {
            }
        }

        Set<Alt> alts = new HashSet<>();

        for (String line : FileUtils.getLines(ALT_FILE))
            alts.add(parseAlt(line));

        getElements().addAll(alts);

        if (!ALTENING_TOKEN_FILE.exists()) {
            try {
                ALTENING_TOKEN_FILE.createNewFile();
            } catch (IOException ignored) {
            }
        } else {
            List<String> lines = FileUtils.getLines(ALTENING_TOKEN_FILE);
            if (!lines.isEmpty())
                this.setAPIKey(lines.get(0));
        }
    }

    public static Alt parseAlt(String line) {
        if (line == null) return null;
        if (line.length() == 0) return null;
        String[] userPass = line.split(":", 2);
        if (userPass.length == 2)
            return new Alt(userPass[0], userPass[1]);
        return null;
    }

    public void addAlt(Alt alt) {
        getElements().add(alt);
    }

    public BasicDataRetriever getAlteningAltFetcher() {
        return alteningAltFetcher;
    }

    public TheAlteningAuthentication getAlteningAuth() {
        return alteningAuth;
    }

    public String getAPIKey() {
        return apiKey;
    }

    public void setAPIKey(String apiKey) {
        this.apiKey = apiKey;
        this.alteningAltFetcher.updateKey(apiKey);
    }

    public void saveAPIKey(String apiKey) {
        if (apiKey == null) return;
        setAPIKey(apiKey);

        if (!ALTENING_TOKEN_FILE.exists()) {
            try {
                if (!ALTENING_TOKEN_FILE.createNewFile())
                    return;
            } catch (IOException ignored) {
                return;
            }
        }

        try {
            FileWriter fileWriter = new FileWriter(ALTENING_TOKEN_FILE);
            fileWriter.write(apiKey);
            fileWriter.close();
        } catch (IOException ignored) {
        }
    }
}
