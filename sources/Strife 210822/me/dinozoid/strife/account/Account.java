package me.dinozoid.strife.account;

import com.thealtening.auth.service.AlteningServiceType;
import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.util.MinecraftUtil;
import me.dinozoid.strife.util.player.AltService;
import me.dinozoid.strife.util.player.PlayerUtil;
import me.dinozoid.strife.util.render.DynamicTextureUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.Sys;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class Account extends MinecraftUtil {

    private AccountInfo info;
    private String email, password;
    private DynamicTexture avatar;
    private BufferedImage image;

    private float y, width;

    public Account(AlteningServiceType type, String combo) {
        this(type, combo.split(":")[0], combo.split(":")[1]);
    }

    public Account(AlteningServiceType type, String email, String password) {
        this.email = email;
        this.password = password;
        info = new AccountInfo(type);
        try {
            avatar = new DynamicTexture(ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/minecraft/strife/gui/accountmanager/steve.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CompletableFuture<Boolean> login() {
        CompletableFuture<Boolean> successful = AltService.login(email, password);
        successful.whenCompleteAsync((success, throwable) -> {
            if (success) {
                info.parse().whenCompleteAsync((parseSuccess, throwable1) -> {
                    loadAvatar();
                    StrifeClient.INSTANCE.accountRepository().currentAccount(this);
                });
            }
        });
        return successful;
    }

    private void loadAvatar() {
        if (info.uuid() != null) {
            try {
                CompletableFuture<BufferedImage> toComplete = new CompletableFuture<>();
                StrifeClient.INSTANCE.executorService().submit(() -> {
                    try {
                        toComplete.complete(ImageIO.read(new URL("https://crafatar.com/avatars/" + info.uuid() + "?size=64&overlay")));
                    } catch (IOException e) {
                        toComplete.complete(null);
                    }
                });
                toComplete.whenCompleteAsync((image, throwable) -> {
                    if (image != null) {
                        this.image = image;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public float width() {
        return width;
    }

    public void width(float width) {
        this.width = width;
    }

    public float y() {
        return y;
    }

    public void y(float y) {
        this.y = y;
    }

    public String email() {
        return this.email;
    }

    public void email(String email) {
        this.email = email;
    }

    public String password() {
        return this.password;
    }

    public void password(String password) {
        this.password = password;
    }

    public AccountInfo info() {
        return this.info;
    }

    public void info(AccountInfo info) {
        this.info = info;
    }

    public DynamicTexture avatar() {
        if(this.image != null) avatar = DynamicTextureUtil.addTexture(info.username(), image);
        return this.avatar;
    }

    public void avatar(DynamicTexture avatar) {
        this.avatar = avatar;
    }
}
