/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.raphimc.mcauth;

import net.raphimc.mcauth.step.AbstractStep;
import net.raphimc.mcauth.step.OptionalMergeStep;
import net.raphimc.mcauth.step.bedrock.StepMCChain;
import net.raphimc.mcauth.step.bedrock.StepPlayFabToken;
import net.raphimc.mcauth.step.java.StepGameOwnership;
import net.raphimc.mcauth.step.java.StepMCProfile;
import net.raphimc.mcauth.step.java.StepMCToken;
import net.raphimc.mcauth.step.java.StepPlayerCertificates;
import net.raphimc.mcauth.step.msa.MsaCodeStep;
import net.raphimc.mcauth.step.msa.StepCredentialsMsaCode;
import net.raphimc.mcauth.step.msa.StepExternalBrowser;
import net.raphimc.mcauth.step.msa.StepExternalBrowserMsaCode;
import net.raphimc.mcauth.step.msa.StepMsaDeviceCode;
import net.raphimc.mcauth.step.msa.StepMsaDeviceCodeMsaCode;
import net.raphimc.mcauth.step.msa.StepMsaToken;
import net.raphimc.mcauth.step.xbl.StepXblDeviceToken;
import net.raphimc.mcauth.step.xbl.StepXblSisuAuthentication;
import net.raphimc.mcauth.step.xbl.StepXblTitleToken;
import net.raphimc.mcauth.step.xbl.StepXblUserToken;
import net.raphimc.mcauth.step.xbl.StepXblXstsToken;
import net.raphimc.mcauth.step.xbl.session.StepFullXblSession;
import net.raphimc.mcauth.step.xbl.session.StepInitialXblSession;
import net.raphimc.mcauth.util.logging.ConsoleLogger;
import net.raphimc.mcauth.util.logging.ILogger;

public class MinecraftAuth {
    public static ILogger LOGGER = new ConsoleLogger();
    public static final AbstractStep<?, StepMCProfile.MCProfile> JAVA_DEVICE_CODE_LOGIN = MinecraftAuth.builder().withClientId("00000000402b5328").withScope("service::user.auth.xboxlive.com::MBI_SSL").deviceCode().withDeviceToken("Win32").sisuTitleAuthentication("rp://api.minecraftservices.com/").buildMinecraftJavaProfileStep();
    public static final AbstractStep<?, StepMCProfile.MCProfile> JAVA_CREDENTIALS_LOGIN = MinecraftAuth.builder().withClientId("00000000402b5328").withScope("service::user.auth.xboxlive.com::MBI_SSL").credentials().withDeviceToken("Win32").sisuTitleAuthentication("rp://api.minecraftservices.com/").buildMinecraftJavaProfileStep();
    public static final AbstractStep<?, StepMCChain.MCChain> BEDROCK_DEVICE_CODE_LOGIN = MinecraftAuth.builder().withClientId("00000000441cc96b").withScope("service::user.auth.xboxlive.com::MBI_SSL").deviceCode().withDeviceToken("Nintendo").sisuTitleAuthentication("https://multiplayer.minecraft.net/").buildMinecraftBedrockChainStep();
    public static final AbstractStep<?, StepMCChain.MCChain> BEDROCK_CREDENTIALS_LOGIN = MinecraftAuth.builder().withClientId("00000000441cc96b").withScope("service::user.auth.xboxlive.com::MBI_SSL").credentials().withDeviceToken("Nintendo").sisuTitleAuthentication("https://multiplayer.minecraft.net/").buildMinecraftBedrockChainStep();
    public static final StepPlayerCertificates JAVA_PLAYER_CERTIFICATES = new StepPlayerCertificates(null);
    private static final StepXblXstsToken BEDROCK_PLAY_FAB_XSTS_TOKEN = new StepXblXstsToken(null, "https://b980a380.minecraft.playfabapi.com/");
    public static final StepPlayFabToken BEDROCK_PLAY_FAB_TOKEN = new StepPlayFabToken(BEDROCK_PLAY_FAB_XSTS_TOKEN);

    public static MsaTokenBuilder builder() {
        return new MsaTokenBuilder();
    }

    public static class MinecraftBuilder {
        private final AbstractStep<?, StepXblXstsToken.XblXsts<?>> xblXstsTokenStep;

        private MinecraftBuilder(XblXstsTokenBuilder xblXstsTokenBuilder) {
            this.xblXstsTokenStep = xblXstsTokenBuilder.build();
        }

        public AbstractStep<StepGameOwnership.GameOwnership, StepMCProfile.MCProfile> buildMinecraftJavaProfileStep() {
            return new StepMCProfile(new StepGameOwnership(new StepMCToken(this.xblXstsTokenStep)));
        }

        public AbstractStep<StepXblXstsToken.XblXsts<?>, StepMCChain.MCChain> buildMinecraftBedrockChainStep() {
            return new StepMCChain(this.xblXstsTokenStep);
        }

        MinecraftBuilder(XblXstsTokenBuilder xblXstsTokenBuilder, 1 var2_2) {
            this(xblXstsTokenBuilder);
        }
    }

    public static class XblXstsTokenBuilder {
        private final OptionalMergeStep<StepMsaToken.MsaToken, StepXblDeviceToken.XblDeviceToken, StepInitialXblSession.InitialXblSession> initialXblSessionStep;
        private AbstractStep<?, StepXblXstsToken.XblXsts<?>> xblXstsTokenStep;

        private XblXstsTokenBuilder(InitialXblSessionBuilder initialXblSessionBuilder) {
            this.initialXblSessionStep = initialXblSessionBuilder.build();
        }

        public MinecraftBuilder sisuTitleAuthentication(String string) {
            this.xblXstsTokenStep = new StepXblSisuAuthentication(this.initialXblSessionStep, string);
            return new MinecraftBuilder(this, null);
        }

        public MinecraftBuilder titleAuthentication(String string) {
            this.xblXstsTokenStep = new StepXblXstsToken(new StepFullXblSession(new StepXblUserToken((AbstractStep<?, StepInitialXblSession.InitialXblSession>)this.initialXblSessionStep), new StepXblTitleToken((AbstractStep<?, StepInitialXblSession.InitialXblSession>)this.initialXblSessionStep)), string);
            return new MinecraftBuilder(this, null);
        }

        public MinecraftBuilder regularAuthentication(String string) {
            this.xblXstsTokenStep = new StepXblXstsToken(new StepFullXblSession(new StepXblUserToken((AbstractStep<?, StepInitialXblSession.InitialXblSession>)this.initialXblSessionStep), null), string);
            return new MinecraftBuilder(this, null);
        }

        public AbstractStep<?, StepXblXstsToken.XblXsts<?>> build() {
            return this.xblXstsTokenStep;
        }

        XblXstsTokenBuilder(InitialXblSessionBuilder initialXblSessionBuilder, 1 var2_2) {
            this(initialXblSessionBuilder);
        }
    }

    public static class InitialXblSessionBuilder {
        private final AbstractStep<MsaCodeStep.MsaCode, StepMsaToken.MsaToken> msaTokenStep;
        private OptionalMergeStep<StepMsaToken.MsaToken, StepXblDeviceToken.XblDeviceToken, StepInitialXblSession.InitialXblSession> initialXblSessionStep;

        private InitialXblSessionBuilder(MsaTokenBuilder msaTokenBuilder) {
            this.msaTokenStep = msaTokenBuilder.build();
        }

        public XblXstsTokenBuilder withDeviceToken(String string) {
            this.initialXblSessionStep = new StepInitialXblSession(this.msaTokenStep, new StepXblDeviceToken(string));
            return new XblXstsTokenBuilder(this, null);
        }

        public XblXstsTokenBuilder withoutDeviceToken() {
            this.initialXblSessionStep = new StepInitialXblSession(this.msaTokenStep, null);
            return new XblXstsTokenBuilder(this, null);
        }

        public OptionalMergeStep<StepMsaToken.MsaToken, StepXblDeviceToken.XblDeviceToken, StepInitialXblSession.InitialXblSession> build() {
            return this.initialXblSessionStep;
        }

        InitialXblSessionBuilder(MsaTokenBuilder msaTokenBuilder, 1 var2_2) {
            this(msaTokenBuilder);
        }
    }

    public static class MsaTokenBuilder {
        private String clientId = "00000000402b5328";
        private String scope = "XboxLive.signin XboxLive.offline_access";
        private String clientSecret = null;
        private int timeout = 60;
        private String redirectUri = null;
        private AbstractStep<?, MsaCodeStep.MsaCode> msaCodeStep;

        public MsaTokenBuilder withClientId(String string) {
            this.clientId = string;
            return this;
        }

        public MsaTokenBuilder withScope(String string) {
            this.scope = string;
            return this;
        }

        public MsaTokenBuilder withClientSecret(String string) {
            this.clientSecret = string;
            return this;
        }

        public MsaTokenBuilder withTimeout(int n) {
            this.timeout = n;
            return this;
        }

        public MsaTokenBuilder withRedirectUri(String string) {
            this.redirectUri = string;
            return this;
        }

        public InitialXblSessionBuilder deviceCode() {
            this.msaCodeStep = new StepMsaDeviceCodeMsaCode(new StepMsaDeviceCode(this.clientId, this.scope), this.clientId, this.scope, this.clientSecret, this.timeout * 1000);
            return new InitialXblSessionBuilder(this, null);
        }

        public InitialXblSessionBuilder externalBrowser() {
            if (this.redirectUri == null) {
                this.redirectUri = "http://localhost";
            }
            this.msaCodeStep = new StepExternalBrowserMsaCode(new StepExternalBrowser(this.clientId, this.scope, this.redirectUri), this.clientId, this.scope, this.clientSecret, this.timeout * 1000);
            return new InitialXblSessionBuilder(this, null);
        }

        public InitialXblSessionBuilder credentials() {
            if (this.redirectUri == null) {
                this.redirectUri = "https://login.live.com/oauth20_desktop.srf";
            }
            this.msaCodeStep = new StepCredentialsMsaCode(this.clientId, this.scope, this.clientSecret, this.redirectUri);
            return new InitialXblSessionBuilder(this, null);
        }

        public InitialXblSessionBuilder customMsaCodeStep(AbstractStep<?, MsaCodeStep.MsaCode> abstractStep) {
            this.msaCodeStep = abstractStep;
            return new InitialXblSessionBuilder(this, null);
        }

        public AbstractStep<MsaCodeStep.MsaCode, StepMsaToken.MsaToken> build() {
            return new StepMsaToken(this.msaCodeStep);
        }
    }
}

