package net.hendersondaniel.gu_daoism.item;

import net.hendersondaniel.gu_daoism.GuDaoism;
import net.hendersondaniel.gu_daoism.item.custom.gu_items.JadeSkinGuItem;
import net.hendersondaniel.gu_daoism.item.custom.interactables.GamblingRockItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GuDaoism.MOD_ID);



    public static final RegistryObject<Item> PRIMEVAL_STONE = ITEMS.register("primeval_stone",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> JADE_SKIN_GU_ITEM = ITEMS.register("jade_skin_gu_item",
            () -> new JadeSkinGuItem(new Item.Properties()
                    .stacksTo(1)
            ));

    public static final RegistryObject<Item> GAMBLING_ROCK_ITEM = ITEMS.register("gambling_rock_item",
            () -> new GamblingRockItem(new Item.Properties()
                    .stacksTo(1)
            ));



    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }







}
