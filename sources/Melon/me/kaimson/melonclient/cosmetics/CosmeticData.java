package me.kaimson.melonclient.cosmetics;

import java.util.*;

public class CosmeticData
{
    private final List<CosmeticManager.CosmeticList> cosmetics;
    private final String data;
    private final jy capeTexture;
    
    public List<CosmeticManager.CosmeticList> getCosmetics() {
        return this.cosmetics;
    }
    
    public String getData() {
        return this.data;
    }
    
    public jy getCapeTexture() {
        return this.capeTexture;
    }
    
    public CosmeticData(final List<CosmeticManager.CosmeticList> cosmetics, final String data, final jy capeTexture) {
        this.cosmetics = cosmetics;
        this.data = data;
        this.capeTexture = capeTexture;
    }
}
