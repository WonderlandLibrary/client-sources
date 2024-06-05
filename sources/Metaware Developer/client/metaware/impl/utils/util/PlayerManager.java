package client.metaware.impl.utils.util;

import net.minecraft.entity.player.EntityPlayer;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class PlayerManager {

    public static final File PLAYERS_FILE = new File("Felix", "players.txt");

    private final Map<String, Relationship> relationshipMap = new HashMap<>();

    public PlayerManager()  {
        if (!PLAYERS_FILE.exists()) {
            try {
                boolean ignored = PLAYERS_FILE.createNewFile();
            } catch (IOException ignored) {
               // RadiumClient.getInstance().getNotificationManager().add("[IOException] Failed to create players.txt file.", NotificationType.ERROR);
            }
        } else {
            try {
                this.load();
            } catch (IOException e) {
                //RadiumClient.getInstance().getNotificationManager().add("[IOException] Unable to read players.txt file.", NotificationType.ERROR);
            }
        }
    }

    private void load() throws IOException {
        for (final String line : FileUtils.readLines(PLAYERS_FILE)) {
            final String[] split = line.split(":");

            if (split.length != 2)
                continue;

            Relationship status = null;

            switch (split[1]) {
                case "FRIEND":
                    status = Relationship.FRIEND;
                    break;
                case "ENEMY":
                    status = Relationship.ENEMY;
                    break;
            }

            if (status != null)
                this.setRelationship(split[0], status, false);
        }
    }

    private void save() {
        final StringBuilder builder = new StringBuilder();

        for (final Map.Entry<String, Relationship> entry : this.relationshipMap.entrySet()) {
            builder.append(entry.getKey()).append(':').append(entry.getValue().name()).append('\n');
        }

        try {
            FileUtils.write(PLAYERS_FILE, builder.toString());
        } catch (IOException e) {
            //RadiumClient.getInstance().getNotificationManager().add("[IOException] Unable to save players.txt file.", NotificationType.ERROR);
        }
    }

    public Relationship getRelationship(final String username) {
        return this.relationshipMap.get(username);
    }

    public boolean setRelationship(final String username, final Relationship status) {
        return this.setRelationship(username, status, true);
    }

    public boolean setRelationship(final String username, final Relationship status, boolean save) {
        if (status == Relationship.NEUTRAL && this.relationshipMap.containsKey(username)) {
            this.relationshipMap.remove(username);
            return true;
        }

        try {
            return this.relationshipMap.put(username, status) != status;
        } finally {
            if (save) this.save();
        }
    }

    public boolean isFriend(EntityPlayer player) {
        return this.getRelationship(player.getGameProfile().getName()) == Relationship.FRIEND;
    }

    public boolean isEnemy(EntityPlayer player) {
        return this.getRelationship(player.getGameProfile().getName()) == Relationship.ENEMY;
    }
}