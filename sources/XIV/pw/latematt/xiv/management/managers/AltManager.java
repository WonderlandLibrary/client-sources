package pw.latematt.xiv.management.managers;

import com.google.common.io.Files;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.file.XIVFile;
import pw.latematt.xiv.management.ListManager;
import pw.latematt.xiv.ui.managers.alt.AltAccount;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Rederpz
 */
public class AltManager extends ListManager<AltAccount> {
    public AltManager() {
        super(new ArrayList<>());
    }

    @Override
    public void setup() {
        XIV.getInstance().getLogger().info(String.format("Starting to setup %s.", getClass().getSimpleName()));
        new XIVFile("alts", "txt") {
            @Override
            public void load() throws IOException {
                XIV.getInstance().getAltManager().getContents().clear();
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] account = line.split(":");
                    if (account.length > 2) {
                        XIV.getInstance().getAltManager().add(account[0], account[1], account[2]);
                    } else {
                        XIV.getInstance().getAltManager().add(account[0], account[1]);
                    }

                }
            }

            @Override
            public void save() throws IOException {
                StringBuilder builder = new StringBuilder();
                for (AltAccount account : XIV.getInstance().getAltManager().getContents()) {
                    builder.append(account.getUsername()).append(":").append(account.getPassword()).append((account.getKeyword().length() > 0 ? (":" + account.getKeyword()) : "")).append("\r\n");
                }
                Files.write(builder.toString().getBytes("UTF-8"), file);
            }
        };
        XIV.getInstance().getLogger().info(String.format("Successfully setup %s.", getClass().getSimpleName()));
    }

    public void add(String username, String password) {
        contents.add(new AltAccount(username, password));
    }

    public void add(String username, String password, String keyword) {
        contents.add(new AltAccount(username, password, keyword));
    }

    public void remove(String username) {
        contents.remove(find(username));
    }

    public AltAccount find(String username) {
        for (AltAccount account : getContents()) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }

        return null;
    }
}
