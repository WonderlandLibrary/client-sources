/*     */ package nightmare.utils.staffanalyser;
/*     */ 
/*     */ import com.google.gson.annotations.SerializedName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class BanQuantityListJSON
/*     */ {
/*     */   @SerializedName("success")
/*     */   boolean success;
/*     */   @SerializedName("watchdog_lastMinute")
/*     */   int watchdogLastMinute;
/*     */   @SerializedName("staff_rollingDaily")
/*     */   int staffRollingDaily;
/*     */   @SerializedName("watchdog_total")
/*     */   int watchdogTotal;
/*     */   @SerializedName("watchdog_rollingDaily")
/*     */   int watchdogRollingDaily;
/*     */   @SerializedName("staff_total")
/*     */   int staffTotal;
/*     */   
/*     */   public boolean isSuccess() {
/*  24 */     return this.success;
/*     */   }
/*     */   
/*     */   public int getWatchdogLastMinute() {
/*  28 */     return this.watchdogLastMinute;
/*     */   }
/*     */   
/*     */   public int getStaffRollingDaily() {
/*  32 */     return this.staffRollingDaily;
/*     */   }
/*     */   
/*     */   public int getWatchdogTotal() {
/*  36 */     return this.watchdogTotal;
/*     */   }
/*     */   
/*     */   public int getWatchdogRollingDaily() {
/*  40 */     return this.watchdogRollingDaily;
/*     */   }
/*     */   
/*     */   public int getStaffTotal() {
/*  44 */     return this.staffTotal;
/*     */   }
/*     */   
/*     */   public void setSuccess(boolean success) {
/*  48 */     this.success = success;
/*     */   }
/*     */   
/*     */   public void setWatchdogLastMinute(int watchdogLastMinute) {
/*  52 */     this.watchdogLastMinute = watchdogLastMinute;
/*     */   }
/*     */   
/*     */   public void setStaffRollingDaily(int staffRollingDaily) {
/*  56 */     this.staffRollingDaily = staffRollingDaily;
/*     */   }
/*     */   
/*     */   public void setWatchdogTotal(int watchdogTotal) {
/*  60 */     this.watchdogTotal = watchdogTotal;
/*     */   }
/*     */   
/*     */   public void setWatchdogRollingDaily(int watchdogRollingDaily) {
/*  64 */     this.watchdogRollingDaily = watchdogRollingDaily;
/*     */   }
/*     */   
/*     */   public void setStaffTotal(int staffTotal) {
/*  68 */     this.staffTotal = staffTotal;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  73 */     if (o == this) {
/*  74 */       return true;
/*     */     }
/*  76 */     if (!(o instanceof BanQuantityListJSON)) {
/*  77 */       return false;
/*     */     }
/*  79 */     BanQuantityListJSON other = (BanQuantityListJSON)o;
/*  80 */     return (other.canEqual(this) && isSuccess() == other.isSuccess() && getWatchdogLastMinute() == other.getWatchdogLastMinute() && getStaffRollingDaily() == other.getStaffRollingDaily() && getWatchdogTotal() == other.getWatchdogTotal() && getWatchdogRollingDaily() == other.getWatchdogRollingDaily() && getStaffTotal() == other.getStaffTotal());
/*     */   }
/*     */   
/*     */   protected boolean canEqual(Object other) {
/*  84 */     return other instanceof BanQuantityListJSON;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  89 */     int result = 1;
/*  90 */     result = result * 59 + (isSuccess() ? 79 : 97);
/*  91 */     result = result * 59 + getWatchdogLastMinute();
/*  92 */     result = result * 59 + getStaffRollingDaily();
/*  93 */     result = result * 59 + getWatchdogTotal();
/*  94 */     result = result * 59 + getWatchdogRollingDaily();
/*  95 */     result = result * 59 + getStaffTotal();
/*  96 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 101 */     return "BanQuantityListJSON(success=" + isSuccess() + ", watchdogLastMinute=" + getWatchdogLastMinute() + ", staffRollingDaily=" + getStaffRollingDaily() + ", watchdogTotal=" + getWatchdogTotal() + ", watchdogRollingDaily=" + getWatchdogRollingDaily() + ", staffTotal=" + getStaffTotal() + ")";
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmar\\utils\staffanalyser\BanQuantityListJSON.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */