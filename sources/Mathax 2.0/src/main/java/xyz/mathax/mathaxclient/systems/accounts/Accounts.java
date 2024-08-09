package xyz.mathax.mathaxclient.systems.accounts;

import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.systems.System;
import xyz.mathax.mathaxclient.systems.Systems;
import xyz.mathax.mathaxclient.systems.accounts.types.CrackedAccount;
import xyz.mathax.mathaxclient.systems.accounts.types.MicrosoftAccount;
import xyz.mathax.mathaxclient.systems.accounts.types.TheAlteningAccount;
import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.network.Executor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Accounts extends System<Accounts> implements Iterable<Account<?>> {
    private List<Account<?>> accounts = new ArrayList<>();

    public Accounts() {
        super("Accounts", MatHax.VERSION_FOLDER);
    }

    public static Accounts get() {
        return Systems.get(Accounts.class);
    }

    public void add(Account<?> account) {
        accounts.add(account);
        save();
    }

    public boolean exists(Account<?> account) {
        return accounts.contains(account);
    }

    public void remove(Account<?> account) {
        if (accounts.remove(account)) {
            save();
        }
    }

    public int size() {
        return accounts.size();
    }

    @Override
    public Iterator<Account<?>> iterator() {
        return accounts.iterator();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("accounts", new JSONArray());

        accounts.forEach(account -> json.append("accounts", account.toJson()));

        return json;
    }

    @Override
    public Accounts fromJson(JSONObject json) {
        if (json.has("accounts")) {
            Executor.execute(() -> {
                if (!JSONUtils.isValidJSONArray(json, "accounts")) {
                    return;
                }

                for (Object object : json.getJSONArray("accounts")) {
                    if (object instanceof JSONObject accountJson) {
                        if (!accountJson.has("type")) {
                            return;
                        }

                        AccountType type = AccountType.valueOf(accountJson.getString("type"));
                        try {
                            Account<?> account = switch (type) {
                                case Cracked -> new CrackedAccount(null).fromJson(accountJson);
                                case Microsoft -> new MicrosoftAccount(null).fromJson(accountJson);
                                case The_Altening -> new TheAlteningAccount(null).fromJson(accountJson);
                            };

                            if (account.fetchInfo()) {
                                accounts.add(account);
                            }
                        } catch (Exception exception) {
                            return;
                        }

                        return;
                    }
                }
            });
        }

        return this;
    }
}
