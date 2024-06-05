package org.json;

import java.util.*;

public static class Builder
{
    private final List<String> refTokens;
    
    public Builder() {
        this.refTokens = new ArrayList<String>();
    }
    
    public JSONPointer build() {
        return new JSONPointer(this.refTokens);
    }
    
    public Builder append(final String token) {
        if (token == null) {
            throw new NullPointerException("token cannot be null");
        }
        this.refTokens.add(token);
        return this;
    }
    
    public Builder append(final int arrayIndex) {
        this.refTokens.add(String.valueOf(arrayIndex));
        return this;
    }
}
