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
}
