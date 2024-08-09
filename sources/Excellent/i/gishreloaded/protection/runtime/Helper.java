package i.gishreloaded.protection.runtime;

public class Helper {
    public static String getNativeDir() {
        return "C:\\Excellent\\client\\natives\\DeadCodeProtection.dll";
    }

    public static String getOutput() {
        try {
            return (String) Class.forName("i.gishreloaded.protection.runtime.Protection").getMethod("getOutput").invoke(null);
        } catch (Exception ignored) {
        }
        return null;
    }
}
