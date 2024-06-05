/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 */
package com.thealtening.api.retriever;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.thealtening.api.TheAlteningException;
import com.thealtening.api.response.Account;
import com.thealtening.api.response.License;
import com.thealtening.api.retriever.AsynchronousDataRetriever;
import com.thealtening.api.retriever.DataRetriever;

public class BasicDataRetriever
implements DataRetriever {
    private String apiKey;

    public BasicDataRetriever(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public void updateKey(String newApiKey) {
        this.apiKey = newApiKey;
    }

    @Override
    public License getLicense() throws TheAlteningException {
        JsonObject jsonObject = this.retrieveData("http://api.thealtening.com/v1/license?token=" + this.apiKey);
        return (License)gson.fromJson((JsonElement)jsonObject, License.class);
    }

    @Override
    public Account getAccount() throws TheAlteningException {
        JsonObject jsonObject = this.retrieveData("http://api.thealtening.com/v1/generate?info=true&token=" + this.apiKey);
        return (Account)gson.fromJson((JsonElement)jsonObject, Account.class);
    }

    @Override
    public boolean isPrivate(String token) throws TheAlteningException {
        JsonObject jsonObject = this.retrieveData("http://api.thealtening.com/v1/private?acctoken=" + token + "&token=" + this.apiKey);
        return this.isSuccess(jsonObject);
    }

    @Override
    public boolean isFavorite(String token) throws TheAlteningException {
        JsonObject jsonObject = this.retrieveData("http://api.thealtening.com/v1/favorite?acctoken=" + token + "&token=" + this.apiKey);
        return this.isSuccess(jsonObject);
    }

    public AsynchronousDataRetriever toAsync() {
        return new AsynchronousDataRetriever(this.apiKey);
    }
}

