package i.gishreloaded.deadcode.api;

import i.gishreloaded.protection.annotation.Native;
import lombok.Getter;

import java.util.Date;

@Getter
public class DeadCodeProfile {
    private final int id;
    private final String name;
    private final DeadCodeRole role;
    private final Date expireDate;

    public DeadCodeProfile(int id, String name, DeadCodeRole role, Date expireDate) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.expireDate = expireDate;
    }

    @Native
    public static DeadCodeProfile create() {
        return new DeadCodeProfile(1, "sheluvparis", DeadCodeRole.USER, null);
    }

}
