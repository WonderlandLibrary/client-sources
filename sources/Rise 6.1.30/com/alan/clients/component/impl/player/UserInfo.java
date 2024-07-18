package com.alan.clients.component.impl.player;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.ResourceLocation;

public class UserInfo {
    private UserInfoStatus status;
    @Getter
    private String MinecraftNickname, Username;
    @Getter @Setter
    private String[] CustomCapeUrlArray;
    @Getter @Setter
    private ResourceLocation[] capeResourceLocationArray;
    @Getter @Setter
    private boolean downloading = false;

    @Getter @Setter
    private int fpt = 0;

    public UserInfo(String name) {
        Username = name;
    }
    public ResourceLocation getCapeResourceLocation() {
        return capeResourceLocationArray[fpt%capeResourceLocationArray.length];
    }
    public boolean hasCape() {
        return !(CustomCapeUrlArray.length == 0);
    }
    public void setStatus(UserInfoStatus ee) {
        this.status = ee;
    }

    public void setMinecraftNickname(String ee) {
        this.MinecraftNickname = ee;
    }

    @Getter
    public enum UserInfoStatus {
        Regular("b"),
        Admin("a"),
        Developer("c"),
        Gato("b");

        private final String colorNick;

        UserInfoStatus(String colorNick) {
            this.colorNick = colorNick;
        }
    }

    public String getColorNick() {
        return status.getColorNick();
    }

    public UserInfoStatus getStatus() {
        return status;
    }
}
