package dev.nexus.utils.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StatsClass {
    String name;
    String wins;
    String losses;
    String gamesPlayed;
    String winLossRatio;
    String kdr;
    String fkdr;
    String bblr;
    String ws;
}
