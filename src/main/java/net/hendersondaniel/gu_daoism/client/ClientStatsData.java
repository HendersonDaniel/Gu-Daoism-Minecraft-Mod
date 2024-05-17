package net.hendersondaniel.gu_daoism.client;

public class ClientStatsData {

    private static int talent = 0;
    private static double primevalEssence = 0.0;
    private static double attackBuff = 0.0;
    private static double defenseBuff = 0.0;
    private static int cultivationProgress = 0;
    private static int rawStage = 0; //TODO: change to -1 to start with no cultivation

    public static void setTalent(int talent) {
        ClientStatsData.talent = talent;
    }

    public static void setPrimevalEssence(double primevalEssence) {
        ClientStatsData.primevalEssence = primevalEssence;
    }

    public static void setAttackBuff(double attackBuff) {
        ClientStatsData.attackBuff = attackBuff;
    }

    public static void setDefenseBuff(double defenseBuff) {
        ClientStatsData.defenseBuff = defenseBuff;
    }

    public static void setCultivationProgress(int cultivationProgress) {
        ClientStatsData.cultivationProgress = cultivationProgress;
    }

    public static void setRawStage(int rawStage) {
        ClientStatsData.rawStage = rawStage;
    }

    public static double getPrimevalEssence() {
        return primevalEssence;
    }

    public static int getTalent() {
        return talent;
    }

    public static double getAttackBuff() {
        return attackBuff;
    }

    public static double getDefenseBuff() {
        return defenseBuff;
    }

    public static int getCultivationProgress() {
        return cultivationProgress;
    }

    public static int getRawStage() {
        return rawStage;
    }
}
