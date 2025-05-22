package net.hendersondaniel.gu_daoism.util;

public class FormattingMethods {


    public static String formatTicksToTime(long ticks) {
        long totalSeconds = ticks / 20;  // Convert ticks to seconds
        long seconds = totalSeconds % 60;
        long totalMinutes = totalSeconds / 60;
        long minutes = totalMinutes % 60;
        long totalHours = totalMinutes / 60;
        long hours = totalHours % 24;
        long days = totalHours / 24;

        return days + " days " + hours + " hours " + minutes + " minutes " + seconds + " seconds";
    }

    public static String stageIntToStageName(int stage){
        switch(stage){
            case 0:
                return "Initial";
            case 1:
                return "Middle";
            case 2:
                return "Upper";
            case 3:
                return "Peak";
            default:
                return "Idk how you got to this...";
        }
    }

    public static int rawStageToRealm(int rawStage){
        return rawStage/4 + 1;
    }
}
