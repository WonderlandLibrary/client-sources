package win.sightclient.utils.security;

public enum Hash {

    MD5("MD5"),
    SHA1("SHA1"),
    SHA256("SHA-256"),
    SHA512("SHA-512");

    private String name;

    Hash(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String checksum(java.io.File input) {
    	if (!input.isFile()) {
    		return "";
    	}
        try (java.io.InputStream in = new java.io.FileInputStream(input)) {
        	java.security.MessageDigest digest = java.security.MessageDigest.getInstance(getName());
            byte[] block = new byte[4096];
            int length;
            while ((length = in.read(block)) > 0) {
                digest.update(block, 0, length);
            }
            return javax.xml.bind.DatatypeConverter.printHexBinary(digest.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
