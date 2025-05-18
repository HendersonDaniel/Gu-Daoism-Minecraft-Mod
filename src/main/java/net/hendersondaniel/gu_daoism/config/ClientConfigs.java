package net.hendersondaniel.gu_daoism.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfigs {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    static {
        BUILDER.push("Configs for Gu Daoism");
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
