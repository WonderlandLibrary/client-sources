/*
 * Decompiled with CFR 0.150.
 */
package viamcp.exemple;

import com.github.creeper123123321.viafabric.ViaFabric;

public class MainExemple {
    public static MainExemple instance = new MainExemple();

    public void startClient() {
        try {
            new ViaFabric().onInitialize();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopClient() {
    }
}

