package net.hendersondaniel.gu_daoism.item.custom.gu_items;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

import static net.hendersondaniel.gu_daoism.item.custom.interactables.GamblingRockItem.createGamblingRockWithNBT;
import static net.hendersondaniel.gu_daoism.util.FormattingMethods.formatTicksToTime;

public class JadeSkinGuItem extends Item {
    public JadeSkinGuItem(Properties properties) {
        super(properties);
    }




    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isClientSide && entity instanceof Player) {
            CompoundTag nbtData = stack.getTag();

            long currentWorldTime = entity.getLevel().getGameTime();

            if(nbtData == null || !nbtData.contains("LastFedTime")){
                stack.getOrCreateTag().putLong("LastFedTime", currentWorldTime);
            }

            if (nbtData != null && nbtData.contains("LastFedTime")) {
                long lastFedTime = nbtData.getLong("LastFedTime");

                long ticksSinceFed = currentWorldTime - lastFedTime;
                long ticksRemaining = Math.max(0, 24000 * 10 - ticksSinceFed);

                if(ticksRemaining <= 0){
                    //replace with rock with chance of getting jade skin gu item
                    ItemStack newStack = createGamblingRockWithNBT("jade_skin_gu_item",entity.getLevel().random.nextFloat());

                    Player player = (Player) entity;
                    player.getInventory().setItem(itemSlot, newStack);


                }
            }
        }

        super.inventoryTick(stack, world, entity, itemSlot, isSelected);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {

            //TODO: shoots something here


            //cooldown
            player.getCooldowns().addCooldown(this, 20);
        }

        return super.use(level, player, hand);
    }

    //TODO: useOn to get the entity to spawn so it goes towards food

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            CompoundTag nbtData = stack.getTag();
            components.add(Component.literal("Rank 1").withStyle(ChatFormatting.DARK_GREEN));

            if (nbtData != null && nbtData.contains("LastFedTime")) {
                long lastFedTime = nbtData.getLong("LastFedTime");
                long currentWorldTime = level != null ? level.getGameTime() : 0;  // Get current world time
                long ticksSinceFed = currentWorldTime - lastFedTime;
                long ticksRemaining = Math.max(0, 24000 * 10 - ticksSinceFed);

                String timeString = formatTicksToTime(ticksRemaining);
                if(ticksRemaining <= 0){
                    components.add(Component.literal("Starving.").withStyle(ChatFormatting.RED));
                } else {
                    components.add(Component.literal("Starvation in: " + timeString + ".").withStyle(ChatFormatting.GRAY));
                }
                components.add(Component.literal("Eats: Jade").withStyle(ChatFormatting.GRAY));
            }
        } else {
            components.add(Component.literal("Hold SHIFT for more info").withStyle(ChatFormatting.YELLOW));
        }

        super.appendHoverText(stack, level, components, flag);
    }






}
