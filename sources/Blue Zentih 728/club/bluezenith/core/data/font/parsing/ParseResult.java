package club.bluezenith.core.data.font.parsing;

import club.bluezenith.util.font.TFontRenderer;
import com.google.common.collect.Lists;

import java.io.File;
import java.util.List;

import static club.bluezenith.util.font.FontUtil.getUnepicFont;

public class ParseResult {

    public String fileLocation, fontName;
    public Integer size;
    public List<Integer> arrayOfSizes;

    public ParseResult(String location, String name, Integer size) {
        this.fileLocation = location;
        this.fontName = name;
        this.size = size;
    }

    public void setArrayOfSizes(List<Integer> arrayOfSizes) {
        arrayOfSizes.remove(size);
        this.arrayOfSizes = arrayOfSizes;
    }
    public boolean verify() {
        return fileLocation != null
                && new File(fileLocation).exists()
                && fontName != null
                && !fontName.isEmpty()
                && size != null
                && size > 0 || arrayOfSizes != null;
    }

    @Override
    public String toString() {
        return "ParseResult {" +
                "fileLocation = '" + fileLocation + '\'' +
                ", fontName = '" + fontName + '\'' +
                ", size = " + size +
                " }";
    }

    public List<TFontRenderer> generateFontRenderers() {
        final List<TFontRenderer> tooMuchFuckingLists = Lists.newArrayList();
        if(arrayOfSizes != null) {
             for(int a : arrayOfSizes) {
                 tooMuchFuckingLists.add(generateSingleFontRenderer(a, true));
             }
        } else tooMuchFuckingLists.add(generateSingleFontRenderer(size, false));
        return tooMuchFuckingLists;
    }

    TFontRenderer generateSingleFontRenderer(int size1, boolean showSizeInName) {
        return new TFontRenderer(getUnepicFont(fileLocation, size1),
                showSizeInName ? fontName + "-" + size1 : fontName,
                true);
    }
}
