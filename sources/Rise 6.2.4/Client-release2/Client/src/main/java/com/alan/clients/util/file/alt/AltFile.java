package com.alan.clients.util.file.alt;

import com.alan.clients.Client;
import com.alan.clients.util.account.Account;
import com.alan.clients.util.account.AccountType;
import com.alan.clients.util.account.impl.CrackedAccount;
import com.alan.clients.util.account.impl.MicrosoftAccount;
import com.alan.clients.util.file.FileType;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
public class AltFile extends com.alan.clients.util.file.File {

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd.MM.yyyy");

    public AltFile(final File file, final FileType fileType) {
        super(file, fileType);
    }

    @Override
    public boolean read() {
        if (!this.getFile().exists()) {
            return false;
        }

        try {
            // reads file to a json object
            final FileReader fileReader = new FileReader(this.getFile());
            final BufferedReader bufferedReader = new BufferedReader(fileReader);
            final JsonObject jsonObject = getGSON().fromJson(bufferedReader, JsonObject.class);

            // closes both readers
            bufferedReader.close();
            fileReader.close();

            // checks if there was data read
            if (jsonObject == null) {
                return false;
            }

            // get account list ref and clear first
            List<Account> accounts = Client.INSTANCE.getAltManager().getAccounts();
            accounts.clear();

            // load all accounts
            JsonArray array = jsonObject.getAsJsonArray("data");
            if (array != null) {
                for (int i = 0; i < array.size(); ++i) {
                    JsonObject object = array.get(i).getAsJsonObject();
                    Account account = new Account(AccountType.CRACKED, "", "", "");
                    account.parseJson(object);

                    switch (account.getType()) {
                        case CRACKED: {
                            account = new CrackedAccount("");
                            account.parseJson(object);
                            break;
                        }
                        case MICROSOFT: {
                            account = new MicrosoftAccount("", "", "", "");
                            account.parseJson(object);
                            break;
                        }
                    }
                    accounts.add(account);
                    System.out.println("loading account: " + account.getName());
                }
            }
        } catch (final IOException ignored) {
            return false;
        }

        return true;
    }

    @Override
    public boolean write() {
        try {
            // creates the file
            if (!this.getFile().exists()) {
                this.getFile().createNewFile();
            }

            // creates a new json object where all data is stored in
            List<Account> accounts = Client.INSTANCE.getAltManager().getAccounts();
            if (accounts.isEmpty()) {
                return true;
            }

            // Add some extra information to the config
            final JsonObject jsonObject = new JsonObject();
            final JsonObject metadataJsonObject = new JsonObject();
            metadataJsonObject.addProperty("version", Client.VERSION);
            metadataJsonObject.addProperty("creationDate", DATE_FORMATTER.format(new Date()));
            jsonObject.add("Metadata", metadataJsonObject);

            // converts accounts to json objects and puts inside array
            JsonArray array = new JsonArray();
            for (Account account : accounts) {
                array.add(account.toJson());
                System.out.println("writing account: " + account.getName());
            }

            jsonObject.add("data", array);

            // writes json object data to a file
            final FileWriter fileWriter = new FileWriter(getFile());
            final BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            getGSON().toJson(jsonObject, bufferedWriter);

            // closes the writer
            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.flush();
            fileWriter.close();
        } catch (final IOException ignored) {
            return false;
        }

        return true;
    }
}