/* November.lol © 2023 */
package net.shoreline.client.api.account.microsoft.data;

/**
 *
 *
 * @param sfttTag The SFTT tag gotten from the original microsoft request
 * @param postUrl The new URL to post to after making the original microsoft
 *                request
 * @param cookie  The "Set-Cookie" parameter <b>required</b> for the next
 *                authentication step
 *
 * @author Gavin
 * @since 1.0
 */
public record OAuthData(String sfttTag, String postUrl, String cookie)
{

}
