package dev.stephen.nexus.utils.bedwars;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class StatsClass {
    @Setter
    String name;
    String winstreak;
    String winLoseR;
    String killDeathR;
    String fkdr;
    String wins;
    String losses;

    public String getWinstreakColor() {
        if (winstreak.isEmpty()) {
            return "";
        }
        double ws = Double.parseDouble(winstreak);
        String color = "§";
        if (ws >= 50.0D) {
            color = color + "4";
        } else if (ws >= 10.0D) {
            color = color + "c";
        } else if (ws >= 7.0D) {
            color = color + "6";
        } else if (ws >= 4.0D) {
            color = color + "e";
        } else if (ws >= 1.0D) {
            color = color + "a";
        } else {
            color = color + "2";
        }
        return color + ws;
    }

    public String getFKDRColor() {
        if (fkdr.isEmpty()) {
            return "";
        }
        String color = "§";
        double fkdr = Double.parseDouble(this.fkdr);
        if (fkdr >= 50.0D) {
            color = color + "4";
        } else if (fkdr >= 10.0D) {
            color = color + "c";
        } else if (fkdr >= 7.0D) {
            color = color + "6";
        } else if (fkdr >= 4.0D) {
            color = color + "e";
        } else if (fkdr >= 1.0D) {
            color = color + "a";
        } else {
            color = color + "2";
        }
        return color + fkdr;
    }
}