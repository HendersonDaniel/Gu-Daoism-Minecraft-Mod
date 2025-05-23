package net.hendersondaniel.gu_daoism.block;

import net.hendersondaniel.gu_daoism.GuDaoism;
import net.hendersondaniel.gu_daoism.fluid.ModFluids;
import net.hendersondaniel.gu_daoism.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, GuDaoism.MOD_ID);

    public static final RegistryObject<LiquidBlock> PRIMEVAL_ESSENCE_FLUID_BLOCK = BLOCKS.register("primeval_essence_fluid_block",
            () -> new LiquidBlock(ModFluids.SOURCE_PRIMEVAL_ESSENCE_LIQUID, BlockBehaviour.Properties.copy(Blocks.WATER).lightLevel(state -> 15)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }


}
