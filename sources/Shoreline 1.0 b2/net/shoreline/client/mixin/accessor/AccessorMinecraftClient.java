package net.shoreline.client.mixin.accessor;

import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.SocialInteractionsManager;
import net.minecraft.client.report.AbuseReportContext;
import net.minecraft.client.texture.PlayerSkinProvider;
import net.minecraft.client.util.ProfileKeys;
import net.minecraft.client.util.Session;
import net.minecraft.network.encryption.SignatureVerifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 * @see MinecraftClient
 */
@Mixin(MinecraftClient.class)
public interface AccessorMinecraftClient
{
    /**
     *
     *
     * @return
     */
    @Accessor("authenticationService")
    @Mutable
    YggdrasilAuthenticationService getAuthenticationService();

    /**
     *
     *
     * @param apiService
     */
    @Accessor
    @Mutable
    void setUserApiService(UserApiService apiService);

    /**
     *
     *
     * @param session
     */
    @Accessor("session")
    @Mutable
    void setSession(Session session);

    /**
     *
     *
     * @param profileKeys
     */
    @Accessor("profileKeys")
    @Mutable
    void setProfileKeys(ProfileKeys profileKeys);

    /**
     *
     *
     * @param socialInteractionsManager
     */
    @Accessor("socialInteractionsManager")
    @Mutable
    void setSocialInteractionsManager(SocialInteractionsManager socialInteractionsManager);

    /**
     *
     *
     * @param authenticationService
     */
    @Accessor("authenticationService")
    @Mutable
    void setAuthenticationService(YggdrasilAuthenticationService authenticationService);

    /**
     *
     *
     * @param servicesSignatureVerifier
     */
    @Accessor("servicesSignatureVerifier")
    @Mutable
    void setServicesSignatureVerifier(SignatureVerifier servicesSignatureVerifier);

    /**
     *
     * @param skinProvider
     */
    @Accessor("skinProvider")
    @Mutable
    void setSkinProvider(PlayerSkinProvider skinProvider);

    /**
     *
     *
     * @param sessionService
     */
    @Accessor("sessionService")
    @Mutable
    void setSessionService(MinecraftSessionService sessionService);

    /**
     *
     *
     * @param abuseReportContext
     */
    @Accessor("abuseReportContext")
    void setAbuseReportContext(AbuseReportContext abuseReportContext);

    /**
     *
     * @param itemUseCooldown
     */
    @Accessor("itemUseCooldown")
    void hookSetItemUseCooldown(int itemUseCooldown);

    /**
     *
     * @return
     */
    @Accessor("itemUseCooldown")
    int hookGetItemUseCooldown();

    /**
     *
     * @param attackCooldown
     */
    @Accessor("attackCooldown")
    void hookSetAttackCooldown(int attackCooldown);
}
