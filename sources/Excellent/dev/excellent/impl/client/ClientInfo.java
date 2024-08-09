package dev.excellent.impl.client;

import dev.excellent.Constants;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ClientInfo {
    private final String
            name = Constants.name,
            namespace = Constants.namespace,
            version,
            gitCommit,
            build;
    private final Release release;

    public String getTitle() {
        return String.format("%s %s (%s) -> (Ушли на рекод, ожидайте!)", getName(), getVersion(), getRelease().getName());
    }

}