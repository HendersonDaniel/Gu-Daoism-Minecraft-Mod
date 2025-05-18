package net.hendersondaniel.gu_daoism.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfigs {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> GU_SATIATION_MODIFIER;



    static {
        BUILDER.push("Configs for Tutorial Mod");

        GU_SATIATION_MODIFIER = BUILDER.comment("How slow Gu starve to death. Higher means slower starvation.")
                .define("Gu Satiation Modifier", 10);


        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}
