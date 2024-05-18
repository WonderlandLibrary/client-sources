package com.kilo.alt.handler;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.util.Session;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Foundry
 */
@FileData(fileName = "accounts")
public final class AccountsFile extends AbstractClientFile {
    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .setPrettyPrinting()
            .create();

    private AccountManager manager = new AccountManager();
    public static AccountsFile instance = new AccountsFile();
    
    @Override
    public void load() {
		try (Reader reader = new BufferedReader(new FileReader(this.getFile()))) {
            final Type serializedObject = new TypeToken<ArrayList<SerializedAccountData>>(){}.getType();
            final List<SerializedAccountData> serializedData = gson.fromJson(reader, serializedObject);
            if (serializedData != null){
                for (SerializedAccountData options : serializedData) {
                	manager.addAccount(options.getSession(), options.getAccountName(), options.getUsername(), options.getPassword(), false);
                }
            }
		} catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        final List<SerializedAccountData> serializedData = Lists.newArrayList();

        for (int i = 0; i < manager.getAccounts().size(); i++){
        	serializedData.add(new SerializedAccountData(manager.getAccounts().get(i).getSession(),
        			manager.getAccounts().get(i).getAccountName(),
        			manager.getAccounts().get(i).getUsername(),
        			manager.getAccounts().get(i).getPassword()));
        }
        try {
			Files.write(gson.toJson(serializedData).getBytes("UTF-8"), this.getFile());
		} catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private class SerializedAccountData {
        private final String accountname;
        private final String username;
        private final String password;
        private final String playerId;
        private final String token;

        SerializedAccountData(Session session, String accountname, String username, String password) {
            this.accountname = accountname;
            this.username = username;
            this.password = password;
            this.playerId = session.getPlayerID();
            this.token = session.getToken();
        }

        String getAccountName() {
            return accountname;
        }

        String getUsername() {
            return username;
        }
        
        String getPassword() {
            return password;
        }
        
        Session getSession(){
        	return new Session(accountname, playerId, token, "mojang");
        }
    }
}

