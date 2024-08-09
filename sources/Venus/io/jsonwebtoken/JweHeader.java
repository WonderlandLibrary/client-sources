/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.ProtectedHeader;
import io.jsonwebtoken.security.PublicJwk;

public interface JweHeader
extends ProtectedHeader {
    public String getEncryptionAlgorithm();

    public PublicJwk<?> getEphemeralPublicKey();

    public byte[] getAgreementPartyUInfo();

    public byte[] getAgreementPartyVInfo();

    public byte[] getInitializationVector();

    public byte[] getAuthenticationTag();

    public Integer getPbes2Count();

    public byte[] getPbes2Salt();
}

