package net.hendersondaniel.gu_daoism.aperture.primeval_essence;

import net.minecraft.nbt.CompoundTag;

public class PlayerStats {
    private int talent = 0;
    private double primevalEssence = 0.0;
    private double attackBuff = 0.0;
    private double defenseBuff = 0.0;
    private int cultivationProgress = 0;
    private int rawStage = 0;


    public void copyFrom(PlayerStats source) {
        this.talent = source.getTalent();
        this.primevalEssence = source.getPrimevalEssence();
        this.attackBuff = source.getAttackBuff();
        this.defenseBuff = source.getDefenseBuff();
        this.cultivationProgress = source.getCultivationProgress();
        this.rawStage=source.getRawStage();
    }

    public void setTalent(int talent) {
        this.talent = talent;
    }


    public int getTalent() {
        return talent;
    }


    public void addCultivationProgress(int progress){
        cultivationProgress += progress;
        if(cultivationProgress >= 500*Math.pow(1.5,rawStage)){
            rawStage++;
            cultivationProgress=0;
        }
    }

    public int getCultivationProgress() {
        return cultivationProgress;
    }


    public void setRawStage(int rawStage) {
        this.rawStage = rawStage;
    }


    public int getRawStage() {
        return rawStage;
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
        return talent*Math.pow(2,rawStage);
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
        nbt.putInt("RawStage",rawStage);
        nbt.putInt("CultivationProgress",cultivationProgress);
        nbt.putDouble("AttackBuff",attackBuff);
        nbt.putDouble("DefenseBuff",defenseBuff);



    }

    public void loadNBTData(CompoundTag nbt) {
        talent = nbt.getInt("Talent");
        primevalEssence = nbt.getDouble("PrimevalEssence");
        rawStage = nbt.getInt("RawStage");
        cultivationProgress = nbt.getInt("CultivationProgress");
        attackBuff = nbt.getDouble("AttackBuff");
        defenseBuff = nbt.getDouble("DefenseBuff");
    }
}
