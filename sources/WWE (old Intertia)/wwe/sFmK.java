package wwe;

import java.io.*;

class sFmK implements FilenameFilter
{
    final DqTb YUxS;
    
    sFmK(final DqTb yUxS) {
        this.YUxS = yUxS;
        super();
    }
    
    @Override
    public boolean accept(final File file, final String s) {
        return new File(file, s).isDirectory();
    }
}
