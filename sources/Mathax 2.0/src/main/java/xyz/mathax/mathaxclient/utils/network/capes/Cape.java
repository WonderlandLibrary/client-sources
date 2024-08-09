package xyz.mathax.mathaxclient.utils.network.capes;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import xyz.mathax.mathaxclient.utils.misc.MatHaxIdentifier;
import xyz.mathax.mathaxclient.utils.network.Executor;
import xyz.mathax.mathaxclient.utils.network.Http;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import static xyz.mathax.mathaxclient.MatHax.mc;

public class Cape extends MatHaxIdentifier {
    public final String name;
    private final String url;

    private boolean downloading;
    private boolean downloaded;

    private NativeImage img;

    private int retryTimer;

    public Cape(String name, String url) {
        super("capes/" + name.toLowerCase(Locale.ROOT));

        this.name = name;
        this.url = url;
    }

    public void download() {
        if (downloaded || downloading || retryTimer > 0) {
            return;
        }

        downloading = true;

        Executor.execute(() -> {
            try {
                InputStream in = Http.get(url).sendInputStream();
                if (in == null) {
                    synchronized (Capes.TO_RETRY) {
                        Capes.TO_RETRY.add(this);
                        retryTimer = 10 * 20;
                        downloading = false;
                        return;
                    }
                }

                img = NativeImage.read(in);

                synchronized (Capes.TO_REGISTER) {
                    Capes.TO_REGISTER.add(this);
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
    }

    public void register() {
        mc.getTextureManager().registerTexture(this, new NativeImageBackedTexture(img));
        img = null;

        downloading = false;
        downloaded = true;
    }

    public boolean tick() {
        if (retryTimer > 0) {
            retryTimer--;
        } else {
            download();
            return true;
        }

        return false;
    }

    public boolean isDownloaded() {
        return downloaded;
    }
}