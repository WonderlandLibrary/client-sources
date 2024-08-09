package dev.darkmoon.client.ui.menu.altmanager.alt;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Alt {
    @Getter
    private final String username;
    @Getter
    @Setter
    private String mask;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private Status status;
    @Getter
    @Setter
    private String date;
    @Getter
    @Setter
    private ResourceLocation skin;

    public Alt(String username, String password, String date) {
        this(username, password, Status.Unchecked, date);
    }

    public Alt(String username, String password, Status status, String date) {
        this(username, password, "", status, date);
    }

    public Alt(String username, String password, String mask, Status status,  String date) {
        this.username = username;
        this.password = password;
        this.mask = mask;
        this.status = status;
        this.date = date;
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
    }

    public enum Status {
        Working(TextFormatting.GREEN + "Working"),
        Banned(TextFormatting.RED + "Banned"),
        Unchecked(TextFormatting.YELLOW + "Unchecked"),
        NotWorking(TextFormatting.RED + "Not Working");

        private final String formatted;

        Status(String string) {
            this.formatted = string;
        }

        public String toFormatted() {
            return this.formatted;
        }
    }
}
