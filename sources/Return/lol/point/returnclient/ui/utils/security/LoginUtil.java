package lol.point.returnclient.ui.utils.security;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import lol.point.returnclient.ui.LoginMenu;
import lol.point.returnclient.util.system.OSUtil;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;

public class LoginUtil {

    public static String hwid;
    public static String uid;
    public static String username;
    private static boolean passable;

    public static boolean authentication(String username) {
        try {
            String uri = "mongodb+srv://Authenthication:potatolvgist@verticdatabase.gaohpfr.mongodb.net/";
            LoginMenu.status = "§fConnecting to database§f";
            MongoClient mongoClient = MongoClients.create(uri);
            MongoDatabase database = mongoClient.getDatabase("vertic_users");
            MongoCollection<Document> collection = database.getCollection("normal_users");

            Bson projectionFields = Projections.fields(
                    Projections.include("uid", "username", "uuid"),
                    Projections.excludeId());


            Document doc = collection.find(eq("username", username))
                    .projection(projectionFields)
                    .first();

            if (doc == null) {
                System.out.println("No results found.");
            } else {
                LoginUtil.hwid = doc.getString("uuid");
                System.out.println("Backend: " + hwid);
                LoginUtil.uid = doc.getString("uid");
                LoginUtil.username = doc.getString("username");
                if (Objects.equals(hwid, OSUtil.getHWID())) {
                    passable = true;
                } else {
                    passable = false;
                }
            }
        } catch (Exception e) {
            LoginMenu.status = "§cFailed to fetch information from database§c";
        }
        return passable;
    }
}

