package net.silentclient.client.mixin.ducks;

import net.silentclient.client.cosmetics.*;
import net.silentclient.client.cosmetics.dynamiccurved.DynamicCape;
import net.silentclient.client.utils.types.PlayerResponse;

public interface AbstractClientPlayerExt {
    void silent$setShield(ShieldData a);
    ShieldData silent$getShield();
    void silent$setNeck(HatData a);
    HatData silent$getNeck();
    void silent$setHat(HatData a);
    HatData silent$getHat();
    void silent$setMask(HatData a);
    HatData silent$getMask();
    void silent$setAccount(PlayerResponse.Account a);
    PlayerResponse.Account silent$getAccount();
    void silent$setCapeType(String a);
    String silent$getCapeType();
    void silent$setShoulders(boolean a);
    boolean silent$getShoulders();
    void silent$setCapeShoulders(StaticResourceLocation a);
    StaticResourceLocation silent$getCapeShoulders();
    void silent$setBandana(AnimatedResourceLocation a);
    AnimatedResourceLocation silent$getBandana();
    StaticCape silent$getCurvedCape();
    StaticCape silent$getStaticCape();
    DynamicCape silent$getDynamicCape();
    void silent$setPlayerIcon(StaticResourceLocation a);
    StaticResourceLocation silent$getPlayerIcon();
    void silent$setWings(AnimatedResourceLocation a);
    AnimatedResourceLocation silent$getWings();
    void silent$setCape(AnimatedResourceLocation a);
    AnimatedResourceLocation silent$getCape();
    String silent$getNameClear();
    void silent$setNameClear(String a);
    Object silent$getPlayerInfo();
}
