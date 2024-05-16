package net.hendersondaniel.gu_daoism.item.custom.interactables;

import net.hendersondaniel.gu_daoism.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class GamblingRockItem extends Item {


    public GamblingRockItem(Properties p_41383_) {
        super(p_41383_);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
            CompoundTag nbtData = itemStack.getOrCreateTag();
            float successChance = nbtData.getFloat("SuccessChance");
            if (level.random.nextFloat() < successChance) {
                String rewardItemRegistryName = nbtData.getString("RewardItem");
                Item rewardItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(rewardItemRegistryName));
                if (rewardItem != null) {

                    ItemStack newItemStack = new ItemStack(rewardItem);
                    player.setItemInHand(hand, newItemStack);

                    //TODO: add success sound here
                }
            } else {
                player.setItemInHand(hand, ItemStack.EMPTY);
                //TODO: add fail sound here
            }
        }
        //return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemStack);
        return super.use(level, player, hand);
    }



    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            CompoundTag nbtData = stack.getTag();
            if (nbtData != null) {
                float chance = nbtData.getFloat("SuccessChance") * 100;
                components.add(Component.literal(String.format("Has a %.1f%% chance of containing a Gu Worm", chance)).withStyle(ChatFormatting.GRAY));
            }
        } else {
            components.add(Component.literal("Hold SHIFT for more info").withStyle(ChatFormatting.YELLOW));
        }

        super.appendHoverText(stack, level, components, flag);
    }






    public static ItemStack createGamblingRockWithNBT(String registry, float chance) {
        ItemStack stack = new ItemStack(ModItems.GAMBLING_ROCK_ITEM.get());
        CompoundTag nbt = new CompoundTag();
        nbt.putString("RewardItem", "gu_daoism:"+ registry);
        nbt.putFloat("SuccessChance", chance);
        stack.setTag(nbt);
        return stack;
    }



}
