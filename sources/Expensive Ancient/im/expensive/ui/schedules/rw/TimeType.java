package im.expensive.ui.schedules.rw;

import lombok.Getter;

@Getter
public enum TimeType {
    FOUR("4:00", 240),
    FIVE("5:00", 300),
    SEVEN_THIRTY_FIVE("7:35", 455),
    TEN_THIRTY_FIVE("10:35", 635),
    THIRTEEN_THIRTY_FIVE("13:35", 815),
    SIXTEEN_THIRTY_FIVE("16:35", 995),
    NINETEEN_THIRTY_FIVE("19:35", 1175),
    TWENTY_TWO_THIRTY_FIVE("22:35", 1355),
    ONE_FORTY_FIVE("1:45", 1545),
    EIGHT("8:00", 480),
    NINE("9:00", 540),
    TEN("10:00", 600),
    ELEVEN("11:00", 660),
    TWELVE("12:00", 720),
    THIRTEEN("13:00", 780),
    FOURTEEN("14:00", 840),
    FIFTEEN("15:00", 900),
    FIFTEEN_HALF("15:30", 930),
    SIXTEEN("16:00", 960),
    SEVENTEEN("17:00", 1020),
    EIGHTEEN("18:00", 1080),
    NINETEEN("19:00", 1140),
    NINETEEN_HALF("19:30", 1170),
    TWENTY("20:00", 1200),
    TWENTY_ONE("21:00", 1260),
    TWENTY_TWO("22:00", 1320),
    TWENTY_THREE("23:00", 1380),
    TWENTY_FOUR("24:00", 1440),
    ONE("1:00", 1500);

    private final String time;
    private final int minutesSinceMidnight;

    TimeType(String time, int n2) {
        this.time = time;
        this.minutesSinceMidnight = n2;
    }
}