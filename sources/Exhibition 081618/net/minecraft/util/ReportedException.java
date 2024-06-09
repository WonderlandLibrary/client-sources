package net.minecraft.util;

import exhibition.Client;
import exhibition.gui.screen.impl.mainmenu.GuiModdedMainMenu;
import net.minecraft.crash.CrashReport;

public class ReportedException extends RuntimeException {
   private static final long serialVersionUID = 1L;
   private final CrashReport theReportedExceptionCrashReport;

   public ReportedException(CrashReport p_i1356_1_) {
      this.theReportedExceptionCrashReport = p_i1356_1_;
   }

   public CrashReport getCrashReport() {
      String str = this.theReportedExceptionCrashReport.getCauseStackTraceOrString();
      return !str.contains(GuiModdedMainMenu.class.getName()) && !str.contains(Client.class.getName()) ? this.theReportedExceptionCrashReport : null;
   }

   public Throwable getCause() {
      String str = this.theReportedExceptionCrashReport.getCauseStackTraceOrString();
      return !str.contains(GuiModdedMainMenu.class.getName()) && !str.contains(Client.class.getName()) ? this.theReportedExceptionCrashReport.getCrashCause() : null;
   }

   public String getMessage() {
      String str = this.theReportedExceptionCrashReport.getCauseStackTraceOrString();
      return !str.contains(GuiModdedMainMenu.class.getName()) && !str.contains(Client.class.getName()) ? this.theReportedExceptionCrashReport.getDescription() : "";
   }
}
