package rina.turok.bope.bopemod.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.BopeFriend;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.guiscreen.render.components.BopeFrame;
import rina.turok.bope.bopemod.guiscreen.render.pinnables.BopePinnable;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;

public class BopeConfigManager {
   public String tag;
   public String BOPE_FILE_COMBOBOXS = "comboboxs.txt";
   public String BOPE_FILE_INTEGERS = "integers.txt";
   public String BOPE_FOLDER_CONFIG = "B.O.P.E/";
   public String BOPE_FILE_FRIENDS = "friends.txt";
   public String BOPE_FILE_BUTTONS = "buttons.txt";
   public String BOPE_FILE_DOUBLES = "doubles.txt";
   public String BOPE_FILE_CLIENT = "client.json";
   public String BOPE_FILE_LABELS = "labels.txt";
   public String BOPE_FOLDER_LOG = "logs/";
   public String BOPE_FOLDER_VALUES = "values/";
   public String BOPE_FOLDER_MODULES = "modules/";
   public String BOPE_FILE_BINDS = "binds.txt";
   public String BOPE_FILE_HUD = "HUD.json";
   public String BOPE_FILE_LOG = "log";
   public String BOPE_ABS_FOLDER_LOG;
   public String BOPE_ABS_COMBOBOXS;
   public String BOPE_ABS_INTEGERS;
   public String BOPE_ABS_DOUBLES;
   public String BOPE_ABS_BUTTONS;
   public String BOPE_ABS_FRIENDS;
   public String BOPE_ABS_LABELS;
   public String BOPE_ABS_FOLDER;
   public String BOPE_ABS_CLIENT;
   public String BOPE_ABS_BINDS;
   public String BOPE_ABS_HUD;
   public String BOPE_ABS_LOG;
   public Path PATH_FOLDER_LOG;
   public Path PATH_COMBOBOXS;
   public Path PATH_INTEGERS;
   public Path PATH_DOUBLES;
   public Path PATH_FRIENDS;
   public Path PATH_BUTTONS;
   public Path PATH_CLIENT;
   public Path PATH_LABELS;
   public Path PATH_FOLDER;
   public Path PATH_BINDS;
   public Path PATH_HUD;
   public Path PATH_LOG;
   public StringBuilder log;

   public BopeConfigManager(String tag) {
      this.BOPE_ABS_FOLDER_LOG = this.BOPE_FOLDER_CONFIG + this.BOPE_FOLDER_LOG;
      this.BOPE_ABS_COMBOBOXS = this.BOPE_FOLDER_CONFIG + this.BOPE_FOLDER_VALUES + this.BOPE_FILE_COMBOBOXS;
      this.BOPE_ABS_INTEGERS = this.BOPE_FOLDER_CONFIG + this.BOPE_FOLDER_VALUES + this.BOPE_FILE_INTEGERS;
      this.BOPE_ABS_DOUBLES = this.BOPE_FOLDER_CONFIG + this.BOPE_FOLDER_VALUES + this.BOPE_FILE_DOUBLES;
      this.BOPE_ABS_BUTTONS = this.BOPE_FOLDER_CONFIG + this.BOPE_FOLDER_VALUES + this.BOPE_FILE_BUTTONS;
      this.BOPE_ABS_FRIENDS = this.BOPE_FOLDER_CONFIG + this.BOPE_FILE_FRIENDS;
      this.BOPE_ABS_LABELS = this.BOPE_FOLDER_CONFIG + this.BOPE_FOLDER_VALUES + this.BOPE_FILE_LABELS;
      this.BOPE_ABS_FOLDER = this.BOPE_FOLDER_CONFIG;
      this.BOPE_ABS_CLIENT = this.BOPE_FOLDER_CONFIG + this.BOPE_FILE_CLIENT;
      this.BOPE_ABS_BINDS = this.BOPE_FOLDER_CONFIG + this.BOPE_FOLDER_MODULES + this.BOPE_FILE_BINDS;
      this.BOPE_ABS_HUD = this.BOPE_FOLDER_CONFIG + this.BOPE_FILE_HUD;
      this.BOPE_ABS_LOG = this.BOPE_ABS_FOLDER_LOG + this.BOPE_FILE_LOG;
      this.PATH_FOLDER_LOG = Paths.get(this.BOPE_ABS_FOLDER_LOG);
      this.PATH_COMBOBOXS = Paths.get(this.BOPE_ABS_COMBOBOXS);
      this.PATH_INTEGERS = Paths.get(this.BOPE_ABS_INTEGERS);
      this.PATH_DOUBLES = Paths.get(this.BOPE_ABS_DOUBLES);
      this.PATH_FRIENDS = Paths.get(this.BOPE_ABS_FRIENDS);
      this.PATH_BUTTONS = Paths.get(this.BOPE_ABS_BUTTONS);
      this.PATH_CLIENT = Paths.get(this.BOPE_ABS_CLIENT);
      this.PATH_LABELS = Paths.get(this.BOPE_ABS_LABELS);
      this.PATH_FOLDER = Paths.get(this.BOPE_ABS_FOLDER);
      this.PATH_BINDS = Paths.get(this.BOPE_ABS_BINDS);
      this.PATH_HUD = Paths.get(this.BOPE_ABS_HUD);
      this.tag = tag;
      this.log = new StringBuilder();
      Date hora = new Date();
      String data = (new SimpleDateFormat("dd/MM/yyyy' - 'HH:mm:ss:")).format(hora);
      this.send_log("****** Files have started. ******");
      this.send_log("- The author of this log is SrRina.");
      this.send_log("- Any crash or problem its here.");
      this.send_log("- (GoT) Rina#8637");
      this.send_log("****** File information. ******");
      this.send_log("- Client name: " + Bope.get_name());
      this.send_log("- Client version: " + Bope.get_version());
      this.send_log("- File created in: " + data);
      this.send_log("- ");
      this.send_log("- >");
   }

   public void BOPE_VERIFY_FOLDER(Path path) throws IOException {
      if (!Files.exists(path, new LinkOption[0])) {
         Files.createDirectories(path);
      }

   }

   public void BOPE_VERIFY_FILES(Path path) throws IOException {
      if (!Files.exists(path, new LinkOption[0])) {
         Files.createFile(path);
      }

   }

   public void BOPE_DELETE_FILES(String abs_path) throws IOException {
      File file = new File(abs_path);
      file.delete();
   }

   public void BOPE_SAVE_SETTINGS() {
      File file;
      BufferedWriter buffer;
      Iterator iterator;
      BopeSetting settings;
      try {
         this.BOPE_DELETE_FILES(this.BOPE_ABS_BUTTONS);
         this.BOPE_VERIFY_FILES(this.PATH_BUTTONS);
         file = new File(this.BOPE_ABS_BUTTONS);
         buffer = new BufferedWriter(new FileWriter(file));
         iterator = Bope.get_setting_manager().get_array_settings().iterator();

         while(iterator.hasNext()) {
            settings = (BopeSetting)iterator.next();
            if (this.is(settings, "button")) {
               buffer.write(settings.get_tag() + ":" + settings.get_value(true) + ":" + settings.get_master().get_tag() + "\r\n");
            }
         }

         buffer.close();
      } catch (Exception var10) {
      }

      try {
         this.BOPE_DELETE_FILES(this.BOPE_ABS_COMBOBOXS);
         this.BOPE_VERIFY_FILES(this.PATH_COMBOBOXS);
         file = new File(this.BOPE_ABS_COMBOBOXS);
         buffer = new BufferedWriter(new FileWriter(file));
         iterator = Bope.get_setting_manager().get_array_settings().iterator();

         while(iterator.hasNext()) {
            settings = (BopeSetting)iterator.next();
            if (this.is(settings, "combobox")) {
               buffer.write(settings.get_tag() + ":" + settings.get_current_value() + ":" + settings.get_master().get_tag() + "\r\n");
            }
         }

         buffer.close();
      } catch (Exception var9) {
      }

      try {
         this.BOPE_DELETE_FILES(this.BOPE_ABS_LABELS);
         this.BOPE_VERIFY_FILES(this.PATH_LABELS);
         file = new File(this.BOPE_ABS_LABELS);
         buffer = new BufferedWriter(new FileWriter(file));
         iterator = Bope.get_setting_manager().get_array_settings().iterator();

         while(iterator.hasNext()) {
            settings = (BopeSetting)iterator.next();
            if (this.is(settings, "label")) {
               buffer.write(settings.get_tag() + ":" + settings.get_value("label") + ":" + settings.get_master().get_tag() + "\r\n");
            }
         }

         buffer.close();
      } catch (Exception var8) {
      }

      try {
         this.BOPE_DELETE_FILES(this.BOPE_ABS_DOUBLES);
         this.BOPE_VERIFY_FILES(this.PATH_DOUBLES);
         file = new File(this.BOPE_ABS_DOUBLES);
         buffer = new BufferedWriter(new FileWriter(file));
         iterator = Bope.get_setting_manager().get_array_settings().iterator();

         while(iterator.hasNext()) {
            settings = (BopeSetting)iterator.next();
            if (this.is(settings, "doubleslider")) {
               buffer.write(settings.get_tag() + ":" + settings.get_value(1.0D) + ":" + settings.get_master().get_tag() + "\r\n");
            }
         }

         buffer.close();
      } catch (Exception var7) {
      }

      try {
         this.BOPE_DELETE_FILES(this.BOPE_ABS_INTEGERS);
         this.BOPE_VERIFY_FILES(this.PATH_INTEGERS);
         file = new File(this.BOPE_ABS_INTEGERS);
         buffer = new BufferedWriter(new FileWriter(file));
         iterator = Bope.get_setting_manager().get_array_settings().iterator();

         while(iterator.hasNext()) {
            settings = (BopeSetting)iterator.next();
            if (this.is(settings, "integerslider")) {
               buffer.write(settings.get_tag() + ":" + settings.get_value(1) + ":" + settings.get_master().get_tag() + "\r\n");
            }
         }

         buffer.close();
      } catch (Exception var6) {
      }

   }

   public void BOPE_LAOD_SETTINGS() {
      File file;
      FileInputStream input_stream;
      DataInputStream data_stream;
      BufferedReader buffer;
      String line;
      String colune;
      String tag;
      String value;
      String module;
      try {
         file = new File(this.BOPE_ABS_BUTTONS);
         input_stream = new FileInputStream(file.getAbsolutePath());
         data_stream = new DataInputStream(input_stream);
         buffer = new BufferedReader(new InputStreamReader(data_stream));

         while((line = buffer.readLine()) != null) {
            colune = line.trim();
            tag = colune.split(":")[0];
            value = colune.split(":")[1];
            module = colune.split(":")[2];
            Bope.get_setting_manager().get_setting_with_tag(module, tag).set_value(Boolean.parseBoolean(value));
         }

         buffer.close();
      } catch (Exception var16) {
      }

      try {
         file = new File(this.BOPE_ABS_COMBOBOXS);
         input_stream = new FileInputStream(file.getAbsolutePath());
         data_stream = new DataInputStream(input_stream);
         buffer = new BufferedReader(new InputStreamReader(data_stream));

         while((line = buffer.readLine()) != null) {
            colune = line.trim();
            tag = colune.split(":")[0];
            value = colune.split(":")[1];
            module = colune.split(":")[2];
            Bope.get_setting_manager().get_setting_with_tag(module, tag).set_current_value(value);
         }

         buffer.close();
      } catch (Exception var15) {
      }

      try {
         file = new File(this.BOPE_ABS_LABELS);
         input_stream = new FileInputStream(file.getAbsolutePath());
         data_stream = new DataInputStream(input_stream);
         buffer = new BufferedReader(new InputStreamReader(data_stream));

         while((line = buffer.readLine()) != null) {
            colune = line.trim();
            tag = colune.split(":")[0];
            value = colune.split(":")[1];
            module = colune.split(":")[2];
            Bope.get_setting_manager().get_setting_with_tag(module, tag).set_value(value);
         }

         buffer.close();
      } catch (Exception var14) {
      }

      try {
         file = new File(this.BOPE_ABS_DOUBLES);
         input_stream = new FileInputStream(file.getAbsolutePath());
         data_stream = new DataInputStream(input_stream);
         buffer = new BufferedReader(new InputStreamReader(data_stream));

         while((line = buffer.readLine()) != null) {
            colune = line.trim();
            tag = colune.split(":")[0];
            value = colune.split(":")[1];
            module = colune.split(":")[2];
            Bope.get_setting_manager().get_setting_with_tag(module, tag).set_value(Double.parseDouble(value));
         }

         buffer.close();
      } catch (Exception var13) {
      }

      try {
         file = new File(this.BOPE_ABS_INTEGERS);
         input_stream = new FileInputStream(file.getAbsolutePath());
         data_stream = new DataInputStream(input_stream);
         buffer = new BufferedReader(new InputStreamReader(data_stream));

         while((line = buffer.readLine()) != null) {
            colune = line.trim();
            tag = colune.split(":")[0];
            value = colune.split(":")[1];
            module = colune.split(":")[2];
            Bope.get_setting_manager().get_setting_with_tag(module, tag).set_value(Integer.parseInt(value));
         }

         buffer.close();
      } catch (Exception var12) {
      }

   }

   public void BOPE_SAVE_BINDS() {
      try {
         this.BOPE_DELETE_FILES(this.BOPE_ABS_BINDS);
         this.BOPE_VERIFY_FILES(this.PATH_BINDS);
         File file = new File(this.BOPE_ABS_BINDS);
         BufferedWriter buffer = new BufferedWriter(new FileWriter(file));
         Iterator iterator = Bope.get_module_manager().get_array_modules().iterator();

         while(iterator.hasNext()) {
            BopeModule modules = (BopeModule)iterator.next();
            buffer.write(modules.get_tag() + ":" + modules.get_bind(1) + ":" + modules.is_active() + ":" + modules.alert() + "\r\n");
         }

         buffer.close();
      } catch (Exception var6) {
      }

   }

   public void BOPE_LOAD_BINDS() {
      try {
         File file = new File(this.BOPE_ABS_BINDS);
         FileInputStream input_stream = new FileInputStream(file.getAbsolutePath());
         DataInputStream data_stream = new DataInputStream(input_stream);
         BufferedReader buffer = new BufferedReader(new InputStreamReader(data_stream));

         String line;
         while((line = buffer.readLine()) != null) {
            String colune = line.trim();
            String tag = colune.split(":")[0];
            String bind = colune.split(":")[1];
            String active = colune.split(":")[2];
            String alert = colune.split(":")[3];
            BopeModule module = Bope.get_module_manager().get_module_with_tag(tag);
            module.set_bind(Integer.parseInt(bind));
            module.alert(Boolean.parseBoolean(alert));
            module.set_active(Boolean.parseBoolean(active));
         }

         buffer.close();
      } catch (Exception var12) {
      }

   }

   public void BOPE_SAVE_FRIENDS() {
      try {
         this.BOPE_DELETE_FILES(this.BOPE_ABS_FRIENDS);
         this.BOPE_VERIFY_FILES(this.PATH_FRIENDS);
         File file = new File(this.BOPE_ABS_FRIENDS);
         BufferedWriter buffer = new BufferedWriter(new FileWriter(file));
         Iterator iterator = Bope.get_friend_manager().get_array_friends().iterator();

         while(iterator.hasNext()) {
            BopeFriend friends = (BopeFriend)iterator.next();
            buffer.write(friends.get_name() + "\r\n");
         }

         buffer.close();
      } catch (Exception var6) {
      }

   }

   public void BOPE_LOAD_FRIENDS() {
      Bope.get_friend_manager().clear();

      try {
         File file = new File(this.BOPE_ABS_FRIENDS);
         FileInputStream input_stream = new FileInputStream(file.getAbsolutePath());
         DataInputStream data_stream = new DataInputStream(input_stream);
         BufferedReader buffer = new BufferedReader(new InputStreamReader(data_stream));

         String line;
         while((line = buffer.readLine()) != null) {
            Bope.get_friend_manager().add_friend(line);
         }

         buffer.close();
      } catch (Exception var7) {
      }

   }

   public void BOPE_SAVE_CLIENT() throws IOException {
      Gson BOPE_GSON = (new GsonBuilder()).setPrettyPrinting().create();
      JsonParser BOPE_PARSER = new JsonParser();
      JsonObject BOPE_MAIN_JSON = new JsonObject();
      JsonObject BOPE_MAIN_CONFIGS = new JsonObject();
      JsonObject BOPE_MAIN_GUI = new JsonObject();
      BOPE_MAIN_CONFIGS.add("name", new JsonPrimitive(Bope.get_name()));
      BOPE_MAIN_CONFIGS.add("version", new JsonPrimitive(Bope.get_version()));
      BOPE_MAIN_CONFIGS.add("user", new JsonPrimitive(Bope.get_actual_user()));
      Bope.get_command_manager();
      BOPE_MAIN_CONFIGS.add("prefix", new JsonPrimitive(BopeCommandManager.get_prefix()));
      BOPE_MAIN_CONFIGS.add("font", new JsonPrimitive(Bope.font_name));
      Iterator var6 = Bope.click_gui.get_array_frames().iterator();

      while(var6.hasNext()) {
         BopeFrame frames_gui = (BopeFrame)var6.next();
         JsonObject BOPE_FRAMES_INFO = new JsonObject();
         BOPE_FRAMES_INFO.add("name", new JsonPrimitive(frames_gui.get_name()));
         BOPE_FRAMES_INFO.add("tag", new JsonPrimitive(frames_gui.get_tag()));
         BOPE_FRAMES_INFO.add("x", new JsonPrimitive(frames_gui.get_x()));
         BOPE_FRAMES_INFO.add("y", new JsonPrimitive(frames_gui.get_y()));
         BOPE_MAIN_GUI.add(frames_gui.get_tag(), BOPE_FRAMES_INFO);
      }

      BOPE_MAIN_JSON.add("configuration", BOPE_MAIN_CONFIGS);
      BOPE_MAIN_JSON.add("gui", BOPE_MAIN_GUI);
      JsonElement BOPE_MAIN_PRETTY_JSON = BOPE_PARSER.parse(BOPE_MAIN_JSON.toString());
      String BOPE_JSON = BOPE_GSON.toJson(BOPE_MAIN_PRETTY_JSON);
      this.BOPE_DELETE_FILES(this.BOPE_ABS_CLIENT);
      this.BOPE_VERIFY_FILES(this.PATH_CLIENT);
      OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream(this.BOPE_ABS_CLIENT), "UTF-8");
      file.write(BOPE_JSON);
      file.close();
   }

   public boolean true_font(String font_name) {
      GraphicsEnvironment g = null;
      g = GraphicsEnvironment.getLocalGraphicsEnvironment();
      String[] fonts = g.getAvailableFontFamilyNames();

      for(int i = 0; i < fonts.length; ++i) {
         if (fonts[i].equals(font_name)) {
            return true;
         }
      }

      return false;
   }

   public void BOPE_LOAD_CLIENT() throws IOException {
      InputStream BOPE_JSON_FILE = Files.newInputStream(this.PATH_CLIENT);
      JsonObject BOPE_MAIN_CLIENT = (new JsonParser()).parse(new InputStreamReader(BOPE_JSON_FILE)).getAsJsonObject();
      JsonObject BOPE_MAIN_CONFIGURATION = BOPE_MAIN_CLIENT.get("configuration").getAsJsonObject();
      Bope.get_command_manager();
      BopeCommandManager.set_prefix(BOPE_MAIN_CONFIGURATION.get("prefix").getAsString());

      try {
         if (BOPE_MAIN_CONFIGURATION.get("font").getAsString() != null && this.true_font(BOPE_MAIN_CONFIGURATION.get("font").getAsString())) {
            Bope.font_name = BOPE_MAIN_CONFIGURATION.get("font").getAsString();
         }
      } catch (Exception var5) {
      }

      BOPE_JSON_FILE.close();
   }

   public void BOPE_LOAD_CLIENT_GUI() throws IOException {
      InputStream BOPE_JSON_FILE = Files.newInputStream(this.PATH_CLIENT);
      JsonObject BOPE_MAIN_CLIENT = (new JsonParser()).parse(new InputStreamReader(BOPE_JSON_FILE)).getAsJsonObject();
      JsonObject BOPE_MAIN_GUI = BOPE_MAIN_CLIENT.get("gui").getAsJsonObject();
      Iterator var4 = Bope.click_gui.get_array_frames().iterator();

      while(var4.hasNext()) {
         BopeFrame frames = (BopeFrame)var4.next();
         JsonObject BOPE_FRAME_INFO = BOPE_MAIN_GUI.get(frames.get_tag()).getAsJsonObject();
         BopeFrame frame_requested = Bope.click_gui.get_frame_with_tag(BOPE_FRAME_INFO.get("tag").getAsString());
         frame_requested.set_x(BOPE_FRAME_INFO.get("x").getAsInt());
         frame_requested.set_y(BOPE_FRAME_INFO.get("y").getAsInt());
      }

      BOPE_JSON_FILE.close();
   }

   public void BOPE_SAVE_HUD() throws IOException {
      Gson BOPE_GSON = (new GsonBuilder()).setPrettyPrinting().create();
      JsonParser BOPE_PARSER = new JsonParser();
      JsonObject BOPE_MAIN_JSON = new JsonObject();
      JsonObject BOPE_MAIN_FRAME = new JsonObject();
      JsonObject BOPE_MAIN_HUD = new JsonObject();
      BOPE_MAIN_FRAME.add("name", new JsonPrimitive(Bope.click_hud.get_frame_hud().get_name()));
      BOPE_MAIN_FRAME.add("tag", new JsonPrimitive(Bope.click_hud.get_frame_hud().get_tag()));
      BOPE_MAIN_FRAME.add("x", new JsonPrimitive(Bope.click_hud.get_frame_hud().get_x()));
      BOPE_MAIN_FRAME.add("y", new JsonPrimitive(Bope.click_hud.get_frame_hud().get_y()));
      Iterator var6 = Bope.get_hud_manager().get_array_huds().iterator();

      while(var6.hasNext()) {
         BopePinnable pinnables_hud = (BopePinnable)var6.next();
         JsonObject BOPE_FRAMES_INFO = new JsonObject();
         BOPE_FRAMES_INFO.add("title", new JsonPrimitive(pinnables_hud.get_title()));
         BOPE_FRAMES_INFO.add("tag", new JsonPrimitive(pinnables_hud.get_tag()));
         BOPE_FRAMES_INFO.add("state", new JsonPrimitive(pinnables_hud.is_active()));
         BOPE_FRAMES_INFO.add("dock", new JsonPrimitive(pinnables_hud.get_dock()));
         BOPE_FRAMES_INFO.add("x", new JsonPrimitive(pinnables_hud.get_x()));
         BOPE_FRAMES_INFO.add("y", new JsonPrimitive(pinnables_hud.get_y()));
         BOPE_MAIN_HUD.add(pinnables_hud.get_tag(), BOPE_FRAMES_INFO);
      }

      BOPE_MAIN_JSON.add("frame", BOPE_MAIN_FRAME);
      BOPE_MAIN_JSON.add("hud", BOPE_MAIN_HUD);
      JsonElement BOPE_MAIN_PRETTY_JSON = BOPE_PARSER.parse(BOPE_MAIN_JSON.toString());
      String BOPE_JSON = BOPE_GSON.toJson(BOPE_MAIN_PRETTY_JSON);
      this.BOPE_DELETE_FILES(this.BOPE_ABS_HUD);
      this.BOPE_VERIFY_FILES(this.PATH_HUD);
      OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream(this.BOPE_ABS_HUD), "UTF-8");
      file.write(BOPE_JSON);
      file.close();
   }

   public void BOPE_LOAD_HUD() throws IOException {
      InputStream BOPE_JSON_FILE = Files.newInputStream(this.PATH_HUD);
      JsonObject BOPE_MAIN_HUD = (new JsonParser()).parse(new InputStreamReader(BOPE_JSON_FILE)).getAsJsonObject();
      JsonObject BOPE_MAIN_FRAME = BOPE_MAIN_HUD.get("frame").getAsJsonObject();
      JsonObject BOPE_MAIN_HUDS = BOPE_MAIN_HUD.get("hud").getAsJsonObject();
      Bope.click_hud.get_frame_hud().set_x(BOPE_MAIN_FRAME.get("x").getAsInt());
      Bope.click_hud.get_frame_hud().set_y(BOPE_MAIN_FRAME.get("y").getAsInt());
      Iterator var5 = Bope.get_hud_manager().get_array_huds().iterator();

      while(var5.hasNext()) {
         BopePinnable pinnables = (BopePinnable)var5.next();
         JsonObject BOPE_HUD_INFO = BOPE_MAIN_HUDS.get(pinnables.get_tag()).getAsJsonObject();
         BopePinnable pinnable_requested = Bope.get_hud_manager().get_pinnable_with_tag(BOPE_HUD_INFO.get("tag").getAsString());
         pinnable_requested.set_active(BOPE_HUD_INFO.get("state").getAsBoolean());
         pinnable_requested.set_dock(BOPE_HUD_INFO.get("dock").getAsBoolean());
         pinnable_requested.set_x(BOPE_HUD_INFO.get("x").getAsInt());
         pinnable_requested.set_y(BOPE_HUD_INFO.get("y").getAsInt());
      }

      BOPE_JSON_FILE.close();
   }

   public void BOPE_SAVE_LOG() throws IOException {
      Date hora = new Date();
      String cache = "-";
      String year = (new SimpleDateFormat("yyyy")).format(hora);
      String month = (new SimpleDateFormat("MM")).format(hora);
      String day = (new SimpleDateFormat("dd")).format(hora);
      String hour = (new SimpleDateFormat("HH")).format(hora);
      String mm = (new SimpleDateFormat("mm")).format(hora);
      String ss = (new SimpleDateFormat("ss")).format(hora);
      String path = this.BOPE_ABS_LOG + cache + year + cache + month + cache + day + cache + hour + cache + mm + cache + ss + cache + ".txt";
      this.PATH_LOG = Paths.get(path);
      this.BOPE_VERIFY_FILES(this.PATH_LOG);
      OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream(path), "UTF-8");
      file.write(this.log.toString());
      file.close();
   }

   public void save_settings() {
      try {
         this.BOPE_VERIFY_FOLDER(this.PATH_FOLDER);
         this.BOPE_VERIFY_FOLDER(Paths.get(this.BOPE_ABS_FOLDER + this.BOPE_FOLDER_VALUES));
         this.BOPE_SAVE_SETTINGS();
      } catch (IOException var2) {
      }

   }

   public void load_settings() {
      this.BOPE_LAOD_SETTINGS();
   }

   public void save_binds() {
      try {
         this.BOPE_VERIFY_FOLDER(this.PATH_FOLDER);
         this.BOPE_VERIFY_FOLDER(Paths.get(this.BOPE_ABS_FOLDER + this.BOPE_FOLDER_MODULES));
         this.BOPE_SAVE_BINDS();
      } catch (IOException var2) {
      }

   }

   public void load_binds() {
      this.BOPE_LOAD_BINDS();
   }

   public void save_friends() {
      try {
         this.BOPE_VERIFY_FOLDER(this.PATH_FOLDER);
         this.BOPE_SAVE_FRIENDS();
      } catch (IOException var2) {
      }

   }

   public void load_friends() {
      this.BOPE_LOAD_FRIENDS();
   }

   public void save_client() {
      try {
         this.BOPE_VERIFY_FOLDER(this.PATH_FOLDER);
         this.BOPE_SAVE_CLIENT();
         this.BOPE_SAVE_HUD();
      } catch (IOException var2) {
      }

   }

   public void load_client() {
      try {
         this.BOPE_LOAD_CLIENT_GUI();
         this.BOPE_LOAD_CLIENT();
         this.BOPE_LOAD_HUD();
      } catch (IOException var2) {
      }

   }

   public void load_client(String client) {
      try {
         this.BOPE_LOAD_CLIENT();
      } catch (IOException var3) {
         var3.printStackTrace();
      }

   }

   public void save_log() {
      try {
         this.BOPE_VERIFY_FOLDER(this.PATH_FOLDER);
         this.BOPE_VERIFY_FOLDER(this.PATH_FOLDER_LOG);
         this.BOPE_SAVE_LOG();
      } catch (IOException var2) {
         var2.printStackTrace();
      }

   }

   public boolean is(BopeSetting setting, String type) {
      return setting.get_type().equalsIgnoreCase(type);
   }

   public void send_log(String log) {
      this.log.append(log + "\n");
   }
}
