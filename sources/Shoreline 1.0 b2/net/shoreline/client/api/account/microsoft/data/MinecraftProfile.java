/* November.lol © 2023 */
package net.shoreline.client.api.account.microsoft.data;

/**
 *
 *
 * @param username     The username of the minecraft user
 * @param id           The UUID of the minecraft user
 * @param accessToken  The access token for the microsoft account
 * @param refreshToken The refresh token for the microsoft account
 *
 * @author Gavin
 * @since 1.0
 */
public record MinecraftProfile(String username, String id,
                               String accessToken, String refreshToken)
{

}
