package exhibition.management.account.relation;

import com.google.gson.annotations.Expose;

public class UserStatus {
   @Expose
   private final String userID;
   private int timesKilled;
   private int timesKilledBy;
   @Expose
   private EnumRelationship relationship;

   public UserStatus(String userID, EnumRelationship relationship, int timesKilled, int timesKilledBy) {
      this.userID = userID;
      this.relationship = relationship;
      this.timesKilled = timesKilled;
      this.timesKilledBy = timesKilledBy;
   }

   public UserStatus(String userID, EnumRelationship relationship) {
      this(userID, relationship, 0, 0);
   }

   public String getUserID() {
      return this.userID;
   }

   public int getTimesKilled() {
      return this.timesKilled;
   }

   public void addTimesKilled() {
      ++this.timesKilled;
   }

   public int getTimesKilledBy() {
      return this.timesKilledBy;
   }

   public void addTimesKilledBy() {
      this.timesKilledBy += this.timesKilledBy;
   }

   public EnumRelationship getRelationship() {
      return this.relationship;
   }

   public boolean isRival() {
      return this.relationship.equals(EnumRelationship.Rival);
   }

   public boolean isFriend() {
      return this.relationship.equals(EnumRelationship.Friend);
   }

   public boolean isNeutral() {
      return this.relationship.equals(EnumRelationship.Neutral);
   }

   public void cycleRelationship() {
      switch(this.relationship) {
      case Friend:
         this.relationship = EnumRelationship.Rival;
         break;
      case Neutral:
         this.relationship = EnumRelationship.Friend;
         break;
      case Rival:
         this.relationship = EnumRelationship.Neutral;
      }

   }

   public void setRelationship(EnumRelationship relationship) {
      this.relationship = relationship;
   }
}
