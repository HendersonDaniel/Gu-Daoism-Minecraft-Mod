package net.hendersondaniel.gu_daoism.aperture.primeval_essence;

import net.minecraft.nbt.CompoundTag;

public class PlayerStats {
    private int talent = 0;
    private double primevalEssence = 0.0;
    private double attackBuff = 0.0;
    private double defenseBuff = 0.0;
    private int cultivationProgress = 0;


    public void copyFrom(PlayerStats source) {
        this.talent = source.getTalent();
        this.primevalEssence = source.getPrimevalEssence();
        this.attackBuff = source.getAttackBuff();
        this.defenseBuff = source.getDefenseBuff();
        this.cultivationProgress = source.getCultivationProgress();
    }

    public void setTalent(int talent) {
        this.talent = talent;
    }


    public int getTalent() {
        return talent;
    }


    public void addCultivationProgress(int progress){
        cultivationProgress += progress;
    }

    public void setCultivationProgress(int progress){
        this.cultivationProgress = progress;
    }

    public int getCultivationProgress() {
        return cultivationProgress;
    }


    public void setRawStage(int rawStage) {
        double total = 0;
        for (int i = 0; i < rawStage; i++) {
            total += 500 * Math.pow(1.5, i);
        }
        cultivationProgress = (int) Math.round(total);
    }

    public int getRawStage() {
        double total = 0;
        int stage = 0;
        while (true) {
            double cost = 500 * Math.pow(1.5, stage);
            if (Math.round(total + cost) > cultivationProgress) {
                break;
            }
            total += cost;
            stage++;
        }
        return stage;
    }


    public void setPrimevalEssence(double essence) {
        this.primevalEssence = essence;
    }

    public void addPrimevalEssence(double essence){
        primevalEssence = Math.min(primevalEssence+essence,getMaxPrimevalEssence());
    }

    public boolean subPrimevalEssence(double essence){
        if((primevalEssence - essence) < 0.0){
            return false;
        }
        primevalEssence -= essence;
        return true;
    }

    public double getMaxPrimevalEssence() {
        return talent*Math.pow(2,getRawStage());
    }

    public double getPrimevalEssence() {
        return primevalEssence;
    }


    public void setAttackBuff(double attackBuff) {
        this.attackBuff = attackBuff;
    }


    public double getAttackBuff() {
        return attackBuff;
    }


    public void setDefenseBuff(double defenseBuff) {
        this.defenseBuff = defenseBuff;
    }


    public double getDefenseBuff() {
        return defenseBuff;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("Talent", talent);
        nbt.putDouble("PrimevalEssence",primevalEssence);
        nbt.putInt("CultivationProgress",cultivationProgress);
        nbt.putDouble("AttackBuff",attackBuff);
        nbt.putDouble("DefenseBuff",defenseBuff);



    }

    public void loadNBTData(CompoundTag nbt) {
        talent = nbt.getInt("Talent");
        primevalEssence = nbt.getDouble("PrimevalEssence");
        cultivationProgress = nbt.getInt("CultivationProgress");
        attackBuff = nbt.getDouble("AttackBuff");
        defenseBuff = nbt.getDouble("DefenseBuff");
    }
}
