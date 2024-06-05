package com.google.common.io;

import com.google.common.annotations.*;
import java.util.regex.*;
import com.google.common.base.*;
import java.io.*;
import javax.annotation.*;

@Beta
public final class PatternFilenameFilter implements FilenameFilter
{
    private final Pattern pattern;
    
    public PatternFilenameFilter(final String patternStr) {
        this(Pattern.compile(patternStr));
    }
    
    public PatternFilenameFilter(final Pattern pattern) {
        this.pattern = Preconditions.checkNotNull(pattern);
    }
    
    @Override
    public boolean accept(@Nullable final File dir, final String fileName) {
        return this.pattern.matcher(fileName).matches();
    }
}
