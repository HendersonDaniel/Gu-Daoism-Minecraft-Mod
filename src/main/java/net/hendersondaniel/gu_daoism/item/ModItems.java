package net.hendersondaniel.gu_daoism.item;

import net.hendersondaniel.gu_daoism.GuDaoism;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GuDaoism.MOD_ID);



    public static final RegistryObject<Item> PRIMEVAL_STONE = ITEMS.register("primeval_stone", () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.GU_MATERIALS_TAB)));



    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }


}
