package cc.slack.ui.altmanager;


import cc.slack.ui.altmanager.auth.Account;
import com.google.gson.*;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class AccountManager {
  private static final Minecraft mc = Minecraft.getMinecraft();
  private static final ArrayList<Account> accounts = new ArrayList<>();
  private static final File file = new File(mc.mcDataDir, "/" + "SlackClient" + "/accounts");
  private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();


  public static void start() {
    if (!file.exists()) {
      try {
        if (file.getParentFile().exists() || file.getParentFile().mkdirs()) {
          if (file.createNewFile()) {
            System.out.print("Successfully created accounts.json!");
          }
        }
      } catch (IOException e) {
        System.err.print("Couldn't create accounts.json!");
      }
    }
  }

  public static void load() {
    accounts.clear();
    try {
      JsonElement json = new JsonParser().parse(
        new BufferedReader(new FileReader(file))
      );
      if (json instanceof JsonArray) {
        JsonArray jsonArray = json.getAsJsonArray();
        for (JsonElement jsonElement : jsonArray) {
          JsonObject jsonObject = jsonElement.getAsJsonObject();
          accounts.add(new Account(
            Optional.ofNullable(jsonObject.get("refreshToken")).map(JsonElement::getAsString).orElse(""),
            Optional.ofNullable(jsonObject.get("accessToken")).map(JsonElement::getAsString).orElse(""),
            Optional.ofNullable(jsonObject.get("username")).map(JsonElement::getAsString).orElse(""),
            Optional.ofNullable(jsonObject.get("timestamp")).map(JsonElement::getAsLong).orElse(System.currentTimeMillis())
          ));
        }
      }
    } catch (FileNotFoundException e) {
      System.err.print("Couldn't find accounts.json!");
    }
  }

  public static void save() {
    try {
      JsonArray jsonArray = new JsonArray();
      for (Account account : accounts) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("refreshToken", account.getRefreshToken());
        jsonObject.addProperty("accessToken", account.getAccessToken());
        jsonObject.addProperty("username", account.getUsername());
        jsonObject.addProperty("timestamp", account.getTimestamp());
        jsonArray.add(jsonObject);
      }
      PrintWriter printWriter = new PrintWriter(new FileWriter(file));
      printWriter.println(gson.toJson(jsonArray));
      printWriter.close();
    } catch (IOException e) {
      System.err.print("Couldn't save accounts.json!");
    }
  }

  public static Account get(int index) {
    return accounts.get(index);
  }

  public static void add(Account account) {
    accounts.add(account);
  }

  public static void remove(int index) {
    accounts.remove(index);
  }

  public static int size() {
    return accounts.size();
  }

  public static void swap(int i, int j) {
    Collections.swap(accounts, i, j);
  }
}
