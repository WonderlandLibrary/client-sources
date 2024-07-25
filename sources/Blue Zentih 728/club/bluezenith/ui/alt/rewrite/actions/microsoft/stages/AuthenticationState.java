package club.bluezenith.ui.alt.rewrite.actions.microsoft.stages;

public enum AuthenticationState {
    GETTING_AUTHORIZATION_CODE,
    GETTING_LIVE_ACCESS_TOKEN,
    GETTING_XBL_TOKEN,
    GETTING_XSTS_TOKEN,
    GETTING_MS_MC_TOKEN,
    VERIFYING_GAME_OWNERSHIP,
    GETTING_GAME_PROFILE;
}
