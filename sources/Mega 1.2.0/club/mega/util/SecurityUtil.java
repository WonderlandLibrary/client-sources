package club.mega.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.sql.*;

public final class SecurityUtil {

    private static String userName;

    public static boolean checkHWID(final String UID) {
        final String url = "jdbc:mysql://u2wkay0ehjdxr4b7:IoJZ20Ko1N5IHiHj7SNj@bmcpfmgrngib4xy0ccz2-mysql.services.clever-cloud.com:3306/bmcpfmgrngib4xy0ccz2" + "?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false";
        final String user = "u2wkay0ehjdxr4b7";
        final String password = "IoJZ20Ko1N5IHiHj7SNj";
        boolean correct = false;

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Successfully connected to the Server!");

            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM users WHERE HWID='" + getHWID() + "' AND UID='" + UID + "'";
            ResultSet result = statement.executeQuery(SQL);

            while (result.next() && !correct) {
                System.out.println("Logged in as: " + result.getString("name"));
                userName = result.getString("name");
                correct = true;
            }

        } catch (Exception exception) {
            System.out.println("ERROR, Can't connect! " + exception.getMessage());
        }
        return correct;
    }

    public static String getUserName() {
        return userName;
    }

    public static String getHWID() {
        try{
            String toEncrypt = getSerialNumber() + System.getenv("COMPUTERNAME") + System.getProperty("user.name") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_LEVEL");
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(toEncrypt.getBytes());
            StringBuffer hexString = new StringBuffer();

            byte byteData[] = md.digest();

            for (byte aByteData : byteData) {
                String hex = Integer.toHexString(0xff & aByteData);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    private static String getSerialNumber() {
        try {
            ProcessBuilder pb = new ProcessBuilder("wmic", "baseboard", "get", "serialnumber");
            Process process = pb.start();
            process.waitFor();
            String serialNumber = "";
            try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                for (String line = br.readLine(); line != null; line = br.readLine())
                {
                    if (line.length() < 1 || line.startsWith("SerialNumber")) continue;

                    serialNumber = line;
                    break;
                }
            }
            return serialNumber;
        } catch (Exception exception) {
            return null;
        }
    }

}
