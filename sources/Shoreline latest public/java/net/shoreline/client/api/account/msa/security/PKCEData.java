package net.shoreline.client.api.account.msa.security;

/**
 * @author xgraza
 * @since 03/31/24
 * @param challenge
 * @param verifier
 */
public record PKCEData(String challenge, String verifier)
{ }
