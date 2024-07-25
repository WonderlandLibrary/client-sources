package club.bluezenith.core.data.font.parsing;

import com.google.common.collect.Lists;

import java.io.File;
import java.util.List;

import static club.bluezenith.BlueZenith.getBlueZenith;

public class ConfigParser {

    public static String configReadme = "//This file is used by the client to acknowledge custom fonts correctly.\n" +
            "//Do not delete or rename it!\n" +
            "//Comments (lines that start with //) are ignored by the client.\n" +
            "//\n" +
            "//Syntax is as follows: (do not use < >) \n" +
            "//\n" +
            "// {\n" +
            "//   filename:<file_name.extension>,\n" +
            "//   displayname:<font_name_in_client>,\n" +
            "//   size:<size (must be a valid number)>\n" +
            "// };\n" +
            "//\n" +
            "// \n" +
            "// You can add multiple sizes of the same font, within one entry. Like this:\n" +
            "// Multiple size values should be separated by dots.\n" +
            "//\n" +
            "// {\n" +
            "//   filename:<file_name.extension>,\n" +
            "//   displayname:<font_name_in_client>,\n" +
            "//   size:<size1.size2.size3> \n" +
            "// }; \n" +
            "//\n" +
            "//\n" +
            "// It is not necessary to do the formatting correctly, here's an example of another valid entry:\n" +
            "//  {filename:<file.extension>,displayname:<font_name>,size:<number>};\n" +
            "// \n" +
            "// NOTE: SPACES (FONT AND FILE NAMES) ARE NOT SUPPORTED\n" +
            "//\n" +
            "// The client currently supports fonts in the TTF and OTF formats.\n" +
            "//\n" +
            "// Please note that spaces before and after * (path and font name separator) are mandatory.\n" +
            "//\n" +
            "// If the font file is not in the /fonts folder, but in a folder like /fonts/innerfolder\n" +
            "// the path to the file should look like this:\n" +
            "//\n" +
            "// {\n" +
            "//   filename:<innerfolder/file_name.extension>,\n" +
            "//   displayname:<font_name_in_client>,\n" +
            "//   size:<size (must be a valid number)>\n" +
            "// };";

    public List<ParseResult> parse(String content) {
      if(!content.startsWith("{") || !content.endsWith("};")) {
         if(!content.isEmpty())
         System.err.println("Font config does not start/end with a correct character: \n " + content);
         return Lists.newArrayList();
      }
      final List<ParseResult> results = Lists.newArrayList();
      final String[] entries = content.split(";");
      for(final String entry : entries) {
          results.add(fromLJson(entry));
      }
      return results;
    }

    ParseResult fromLJson(String ljson) {
        ljson = ljson.substring(1, ljson.length() - 1).replace("/", File.separator);
        final String[] entryValuePairs = ljson.split(",");
        final List<Integer> val = Lists.newArrayList();
        final ParseResult result = new ParseResult(null, null, 0);
        for(final String pair : entryValuePairs) {
            final String[] split = pair.split(":");
            final String valueName = split[0];
            final String value = split[1];
            switch (valueName.toLowerCase()) {
                case "filename":
                    if(value.endsWith(".ttf") || value.endsWith(".otf"))
                    result.fileLocation = getBlueZenith().getResourceRepository().getFilePath("fonts", value);
                break;

                case "displayname":
                    result.fontName = value;
                break;

                case "size":
                    try {
                        if(value.matches("^(?:\\d+.)*\\d+$")) {
                            if(value.contains(".")) {
                              final String[] arrays = value.split("\\.");
                              for(String a : arrays) {
                                   val.add(Integer.valueOf(a));
                              }
                              result.setArrayOfSizes(val);
                            } else result.size = Integer.valueOf(value);
                        }
                    } catch (NumberFormatException exception) {
                        result.size = null;
                    }
                break;
            }
        }
        return result;
    }

    public static void parse() {
        new ConfigParser().parse("{\n" +
                "filename:<innerfolder/cum>,\n" +
                "displayname:<mogus>,\n" +
                "size:<size>;\n" +
                "}\n" +
                "{\n" +
                "filename:a,\n" +
                "displayname:a1,\n" +
                "size:a2;\n" +
                "}\n" +
                "{\n" +
                "filename:b,\n" +
                "displayname:b2,\n" +
                "size:b3;\n" +
                "}");
    }
}
