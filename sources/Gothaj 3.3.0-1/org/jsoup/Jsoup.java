package org.jsoup;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.annotation.Nullable;
import javax.annotation.WillClose;
import org.jsoup.helper.DataUtil;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Safelist;

public class Jsoup {
   private Jsoup() {
   }

   public static Document parse(String html, String baseUri) {
      return Parser.parse(html, baseUri);
   }

   public static Document parse(String html, String baseUri, Parser parser) {
      return parser.parseInput(html, baseUri);
   }

   public static Document parse(String html, Parser parser) {
      return parser.parseInput(html, "");
   }

   public static Document parse(String html) {
      return Parser.parse(html, "");
   }

   public static Connection connect(String url) {
      return HttpConnection.connect(url);
   }

   public static Connection newSession() {
      return new HttpConnection();
   }

   public static Document parse(File file, @Nullable String charsetName, String baseUri) throws IOException {
      return DataUtil.load(file, charsetName, baseUri);
   }

   public static Document parse(File file, @Nullable String charsetName) throws IOException {
      return DataUtil.load(file, charsetName, file.getAbsolutePath());
   }

   public static Document parse(File file) throws IOException {
      return DataUtil.load(file, null, file.getAbsolutePath());
   }

   public static Document parse(File file, @Nullable String charsetName, String baseUri, Parser parser) throws IOException {
      return DataUtil.load(file, charsetName, baseUri, parser);
   }

   public static Document parse(@WillClose InputStream in, @Nullable String charsetName, String baseUri) throws IOException {
      return DataUtil.load(in, charsetName, baseUri);
   }

   public static Document parse(InputStream in, @Nullable String charsetName, String baseUri, Parser parser) throws IOException {
      return DataUtil.load(in, charsetName, baseUri, parser);
   }

   public static Document parseBodyFragment(String bodyHtml, String baseUri) {
      return Parser.parseBodyFragment(bodyHtml, baseUri);
   }

   public static Document parseBodyFragment(String bodyHtml) {
      return Parser.parseBodyFragment(bodyHtml, "");
   }

   public static Document parse(URL url, int timeoutMillis) throws IOException {
      Connection con = HttpConnection.connect(url);
      con.timeout(timeoutMillis);
      return con.get();
   }

   public static String clean(String bodyHtml, String baseUri, Safelist safelist) {
      Document dirty = parseBodyFragment(bodyHtml, baseUri);
      Cleaner cleaner = new Cleaner(safelist);
      Document clean = cleaner.clean(dirty);
      return clean.body().html();
   }

   public static String clean(String bodyHtml, Safelist safelist) {
      return clean(bodyHtml, "", safelist);
   }

   public static String clean(String bodyHtml, String baseUri, Safelist safelist, Document.OutputSettings outputSettings) {
      Document dirty = parseBodyFragment(bodyHtml, baseUri);
      Cleaner cleaner = new Cleaner(safelist);
      Document clean = cleaner.clean(dirty);
      clean.outputSettings(outputSettings);
      return clean.body().html();
   }

   public static boolean isValid(String bodyHtml, Safelist safelist) {
      return new Cleaner(safelist).isValidBodyHtml(bodyHtml);
   }
}
