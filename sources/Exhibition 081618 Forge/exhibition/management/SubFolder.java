package exhibition.management;

public enum SubFolder {
   ModuleJars("Plugins"),
   Configs("Configs"),
   Other("Other");

   private final String folderName;

   private SubFolder(String folderName) {
      this.folderName = folderName;
   }

   public String getFolderName() {
      return this.folderName;
   }
}
