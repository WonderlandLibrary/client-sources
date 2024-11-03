package org.jsoup.helper;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import javax.annotation.Nullable;
import javax.annotation.WillClose;
import org.jsoup.UncheckedIOException;
import org.jsoup.internal.ConstrainableInputStream;
import org.jsoup.internal.Normalizer;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.XmlDeclaration;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public final class DataUtil {
   private static final Pattern charsetPattern = Pattern.compile("(?i)\\bcharset=\\s*(?:[\"'])?([^\\s,;\"']*)");
   public static final Charset UTF_8 = Charset.forName("UTF-8");
   static final String defaultCharsetName = UTF_8.name();
   private static final int firstReadBufferSize = 5120;
   static final int bufferSize = 32768;
   private static final char[] mimeBoundaryChars = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
   static final int boundaryLength = 32;

   private DataUtil() {
   }

   public static Document load(File file, @Nullable String charsetName, String baseUri) throws IOException {
      return load(file, charsetName, baseUri, Parser.htmlParser());
   }

   public static Document load(File file, @Nullable String charsetName, String baseUri, Parser parser) throws IOException {
      InputStream stream = new FileInputStream(file);
      String name = Normalizer.lowerCase(file.getName());
      if (name.endsWith(".gz") || name.endsWith(".z")) {
         boolean zipped;
         try {
            zipped = stream.read() == 31 && stream.read() == 139;
         } finally {
            stream.close();
         }

         stream = (InputStream)(zipped ? new GZIPInputStream(new FileInputStream(file)) : new FileInputStream(file));
      }

      return parseInputStream(stream, charsetName, baseUri, parser);
   }

   public static Document load(@WillClose InputStream in, @Nullable String charsetName, String baseUri) throws IOException {
      return parseInputStream(in, charsetName, baseUri, Parser.htmlParser());
   }

   public static Document load(@WillClose InputStream in, @Nullable String charsetName, String baseUri, Parser parser) throws IOException {
      return parseInputStream(in, charsetName, baseUri, parser);
   }

   static void crossStreams(InputStream in, OutputStream out) throws IOException {
      byte[] buffer = new byte[32768];

      int len;
      while ((len = in.read(buffer)) != -1) {
         out.write(buffer, 0, len);
      }
   }

   static Document parseInputStream(@Nullable @WillClose InputStream input, @Nullable String charsetName, String baseUri, Parser parser) throws IOException {
      if (input == null) {
         return new Document(baseUri);
      } else {
         InputStream var27 = ConstrainableInputStream.wrap(input, 32768, 0);
         Document doc = null;

         try {
            var27.mark(32768);
            ByteBuffer firstBytes = readToByteBuffer(var27, 5119);
            boolean fullyRead = var27.read() == -1;
            var27.reset();
            DataUtil.BomCharset bomCharset = detectCharsetFromBom(firstBytes);
            if (bomCharset != null) {
               charsetName = bomCharset.charset;
            }

            if (charsetName != null) {
               Validate.notEmpty(charsetName, "Must set charset arg to character set of file to parse. Set to null to attempt to detect from HTML");
            } else {
               try {
                  CharBuffer defaultDecoded = UTF_8.decode(firstBytes);
                  if (defaultDecoded.hasArray()) {
                     doc = parser.parseInput(new CharArrayReader(defaultDecoded.array(), defaultDecoded.arrayOffset(), defaultDecoded.limit()), baseUri);
                  } else {
                     doc = parser.parseInput(defaultDecoded.toString(), baseUri);
                  }
               } catch (UncheckedIOException var25) {
                  throw var25.ioException();
               }

               Elements metaElements = doc.select("meta[http-equiv=content-type], meta[charset]");
               String foundCharset = null;

               for (Element meta : metaElements) {
                  if (meta.hasAttr("http-equiv")) {
                     foundCharset = getCharsetFromContentType(meta.attr("content"));
                  }

                  if (foundCharset == null && meta.hasAttr("charset")) {
                     foundCharset = meta.attr("charset");
                  }

                  if (foundCharset != null) {
                     break;
                  }
               }

               if (foundCharset == null && doc.childNodeSize() > 0) {
                  Node first = doc.childNode(0);
                  XmlDeclaration decl = null;
                  if (first instanceof XmlDeclaration) {
                     decl = (XmlDeclaration)first;
                  } else if (first instanceof Comment) {
                     Comment comment = (Comment)first;
                     if (comment.isXmlDeclaration()) {
                        decl = comment.asXmlDeclaration();
                     }
                  }

                  if (decl != null && decl.name().equalsIgnoreCase("xml")) {
                     foundCharset = decl.attr("encoding");
                  }
               }

               foundCharset = validateCharset(foundCharset);
               if (foundCharset != null && !foundCharset.equalsIgnoreCase(defaultCharsetName)) {
                  foundCharset = foundCharset.trim().replaceAll("[\"']", "");
                  charsetName = foundCharset;
                  doc = null;
               } else if (!fullyRead) {
                  doc = null;
               }
            }

            if (doc == null) {
               if (charsetName == null) {
                  charsetName = defaultCharsetName;
               }

               BufferedReader reader = new BufferedReader(new InputStreamReader(var27, Charset.forName(charsetName)), 32768);

               try {
                  if (bomCharset != null && bomCharset.offset) {
                     long skipped = reader.skip(1L);
                     Validate.isTrue(skipped == 1L);
                  }

                  try {
                     doc = parser.parseInput(reader, baseUri);
                  } catch (UncheckedIOException var23) {
                     throw var23.ioException();
                  }

                  Charset charset = charsetName.equals(defaultCharsetName) ? UTF_8 : Charset.forName(charsetName);
                  doc.outputSettings().charset(charset);
                  if (!charset.canEncode()) {
                     doc.charset(UTF_8);
                  }
               } finally {
                  reader.close();
               }
            }
         } finally {
            var27.close();
         }

         return doc;
      }
   }

   public static ByteBuffer readToByteBuffer(InputStream inStream, int maxSize) throws IOException {
      Validate.isTrue(maxSize >= 0, "maxSize must be 0 (unlimited) or larger");
      ConstrainableInputStream input = ConstrainableInputStream.wrap(inStream, 32768, maxSize);
      return input.readToByteBuffer(maxSize);
   }

   static ByteBuffer emptyByteBuffer() {
      return ByteBuffer.allocate(0);
   }

   @Nullable
   static String getCharsetFromContentType(@Nullable String contentType) {
      if (contentType == null) {
         return null;
      } else {
         Matcher m = charsetPattern.matcher(contentType);
         if (m.find()) {
            String charset = m.group(1).trim();
            charset = charset.replace("charset=", "");
            return validateCharset(charset);
         } else {
            return null;
         }
      }
   }

   @Nullable
   private static String validateCharset(@Nullable String cs) {
      if (cs != null && cs.length() != 0) {
         cs = cs.trim().replaceAll("[\"']", "");

         try {
            if (Charset.isSupported(cs)) {
               return cs;
            }

            cs = cs.toUpperCase(Locale.ENGLISH);
            if (Charset.isSupported(cs)) {
               return cs;
            }
         } catch (IllegalCharsetNameException var2) {
         }

         return null;
      } else {
         return null;
      }
   }

   static String mimeBoundary() {
      StringBuilder mime = StringUtil.borrowBuilder();
      Random rand = new Random();

      for (int i = 0; i < 32; i++) {
         mime.append(mimeBoundaryChars[rand.nextInt(mimeBoundaryChars.length)]);
      }

      return StringUtil.releaseBuilder(mime);
   }

   @Nullable
   private static DataUtil.BomCharset detectCharsetFromBom(ByteBuffer byteData) {
      ((Buffer)byteData).mark();
      byte[] bom = new byte[4];
      if (byteData.remaining() >= bom.length) {
         byteData.get(bom);
         ((Buffer)byteData).rewind();
      }

      if ((bom[0] != 0 || bom[1] != 0 || bom[2] != -2 || bom[3] != -1) && (bom[0] != -1 || bom[1] != -2 || bom[2] != 0 || bom[3] != 0)) {
         if ((bom[0] != -2 || bom[1] != -1) && (bom[0] != -1 || bom[1] != -2)) {
            return bom[0] == -17 && bom[1] == -69 && bom[2] == -65 ? new DataUtil.BomCharset("UTF-8", true) : null;
         } else {
            return new DataUtil.BomCharset("UTF-16", false);
         }
      } else {
         return new DataUtil.BomCharset("UTF-32", false);
      }
   }

   private static class BomCharset {
      private final String charset;
      private final boolean offset;

      public BomCharset(String charset, boolean offset) {
         this.charset = charset;
         this.offset = offset;
      }
   }
}
