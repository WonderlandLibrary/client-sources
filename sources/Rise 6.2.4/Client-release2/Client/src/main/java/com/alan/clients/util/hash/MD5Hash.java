package com.alan.clients.util.hash;

import com.alan.clients.util.ReflectionUtil;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteSource;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class MD5Hash {
    public static String getMD5() {
        try {
            File file = new File(ReflectionUtil.path());
            ByteSource byteSource = com.google.common.io.Files.asByteSource(file);
            HashCode hc = byteSource.hash(Hashing.md5());
            String checksum = hc.toString();

            return checksum;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return "";
    }
}
