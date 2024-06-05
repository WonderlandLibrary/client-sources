/* November.lol © 2023 */
package net.shoreline.client.api.account.microsoft;

/**
 * @author Gavin
 * @since 1.0
 */
public class MicrosoftAuthException extends Exception
{
  /**
   * Creates a {@link MicrosoftAuthException}
   *
   * @param data the data of why something failed
   */
  public MicrosoftAuthException(String data)
  {
    super(data);
  }
}
