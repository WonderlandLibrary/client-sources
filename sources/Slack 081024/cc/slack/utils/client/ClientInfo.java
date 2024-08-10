package cc.slack.utils.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClientInfo {
    public final String name;
    public final String version;
    public final VersionType type;

    public enum VersionType {
        RELEASE, BETA, DEVELOPER, ALPHA
    }

    // Getters

    public String getType() {
        return type.toString().charAt(0) + type.toString().substring(1).toLowerCase();
    }
}