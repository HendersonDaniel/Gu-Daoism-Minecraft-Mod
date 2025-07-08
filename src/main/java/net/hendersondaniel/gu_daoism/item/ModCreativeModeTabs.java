package net.hendersondaniel.gu_daoism.item;

import net.hendersondaniel.gu_daoism.GuDaoism;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GuDaoism.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTabs {


    public static CreativeModeTab GU_TAB;
    public static CreativeModeTab GU_MATERIALS_TAB;

    @SubscribeEvent
    public static void registerCreativeTabs(CreativeModeTabEvent.Register event) {
        GU_TAB = event.registerCreativeModeTab(
                ResourceLocation.fromNamespaceAndPath(GuDaoism.MOD_ID,"gu_tab"),
                builder -> builder
                        .title(Component.translatable("itemGroup.gu_tab"))
                        .icon(() -> new ItemStack(ModItems.JADE_SKIN_GU_ITEM.get()))
                        .displayItems((params, output) -> {
                            output.accept(ModItems.JADE_SKIN_GU_ITEM.get());
                            // Add more items to GU_TAB here
                        })
        );

        GU_MATERIALS_TAB = event.registerCreativeModeTab(
                ResourceLocation.fromNamespaceAndPath(GuDaoism.MOD_ID,"gu_materials_tab"),
                builder -> builder
                        .title(Component.translatable("itemGroup.gu_materials_tab"))
                        .icon(() -> new ItemStack(ModItems.PRIMEVAL_STONE.get()))
                        .displayItems((params, output) -> {
                            output.accept(ModItems.PRIMEVAL_STONE.get());
                            output.accept(ModItems.PRIMEVAL_ESSENCE_BUCKET.get());
                            // Add more materials here
                        })
        );
    }


}
