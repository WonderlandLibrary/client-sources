package client.metaware.api.account;

public enum AccountRank {
    DEFAULT(""), VIP("§a[VIP] "), VIP_PLUS("§a[VIP§c+&a] "), MVP("§b[MVP] "), MVP_PLUS("§b[MVP§c+§b] "), ULTRA("§bULTRA "), HERO("§5HERO "), LEGEND("§aLEGEND "), TITAN("§cTITAN "), ETERNAL("§3ETERNAL "), IMMORTAL("§eIMMORTAL ");

    private String representation;

    AccountRank(String representation) {
        this.representation = representation;
    }

    public String representation() {
        return representation;
    }
}
