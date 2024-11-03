package com.viaversion.viaversion.api.legacy.bossbar;

import com.viaversion.viaversion.api.connection.UserConnection;
import java.util.Set;
import java.util.UUID;

public interface BossBar {
   String getTitle();

   BossBar setTitle(String var1);

   float getHealth();

   BossBar setHealth(float var1);

   BossColor getColor();

   BossBar setColor(BossColor var1);

   BossStyle getStyle();

   BossBar setStyle(BossStyle var1);

   BossBar addPlayer(UUID var1);

   BossBar addConnection(UserConnection var1);

   BossBar removePlayer(UUID var1);

   BossBar removeConnection(UserConnection var1);

   BossBar addFlag(BossFlag var1);

   BossBar removeFlag(BossFlag var1);

   boolean hasFlag(BossFlag var1);

   Set<UUID> getPlayers();

   Set<UserConnection> getConnections();

   BossBar show();

   BossBar hide();

   boolean isVisible();

   UUID getId();
}
