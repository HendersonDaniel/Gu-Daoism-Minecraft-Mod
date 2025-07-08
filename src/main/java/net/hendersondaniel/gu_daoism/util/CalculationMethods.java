package net.hendersondaniel.gu_daoism.util;

import net.hendersondaniel.gu_daoism.aperture.primeval_essence.PlayerStats;

public class CalculationMethods {

    public static boolean clampPrimevalEssence(PlayerStats stats) {
        int stage = stats.getRawStage();
        int maxAperture = 100 * (int) Math.pow(2, stage);
        double currentEssence = stats.getPrimevalEssence();
        int essencePercentage = (int) (100 * (currentEssence / maxAperture));
        int talent = stats.getTalent();

        if (essencePercentage > talent) {
            stats.setPrimevalEssence(talent * (int) Math.pow(2, stage));
            return true;
        }
        return false;
    }

}
