package net.hendersondaniel.gu_daoism.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTabs {

    public static final CreativeModeTab GU_MATERIALS_TAB = new CreativeModeTab("gu_materials_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.PRIMEVAL_STONE.get());
        }
    };

    public static final CreativeModeTab GU_TAB = new CreativeModeTab("gu_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.JADE_SKIN_GU_ITEM.get());
        }
    };

}
