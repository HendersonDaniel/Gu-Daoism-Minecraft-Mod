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

    public static float[] rawStageToApertureWallRGB(int rawStage) {
        final float[][] wallColors = {
                // Yellow (case 0)
                {1.0f, 216/255f, 0.0f},
                // Blue (case 1)
                {0.0f, 105/255f, 1.0f},
                // Gray (case 2)
                {128/255f, 128/255f, 128/255f},
                // Light lilac (case 3)
                {159/255f, 167/255f, 1.0f},
                // Black (default)
                {0.0f, 0.0f, 0.0f}
        };

        int colorIndex = rawStage % 4;
        if (colorIndex < 0) {
            colorIndex = 4;
        }

        return wallColors[colorIndex].clone();
    }

    public static float[] rawStageToPrimevalSeaRGB(int rawStage) {
        final float[][] seaColors = {
                // Green (case 0)
                {144/255f, 238/255f, 144/255f},
                // Red (case 1)
                {1.0f, 0.0f, 0.0f},
                // Silver/White (case 2)
                {245/255f, 245/255f, 245/255f},
                // Gold/Yellow (case 3)
                {244/255f, 224/255f, 0.0f},
                // Purple (case 4)
                {178/255f, 118/255f, 242/255f},
                // Black (default)
                {0.0f, 0.0f, 0.0f}
        };

        int colorIndex = rawStage / 4;
        if (colorIndex < 0 || colorIndex >= 5) {
            colorIndex = 5;
        }

        return seaColors[colorIndex].clone();
    }
}
