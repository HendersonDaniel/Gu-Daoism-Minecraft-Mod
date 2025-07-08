package net.hendersondaniel.gu_daoism.event;

import net.hendersondaniel.gu_daoism.GuDaoism;
import net.hendersondaniel.gu_daoism.entity.custom.AbstractGuEntity;
import net.hendersondaniel.gu_daoism.item.custom.gu_items.AbstractGuItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import static net.hendersondaniel.gu_daoism.item.custom.interactables.GamblingRockItem.createGamblingRockWithNBT;

@Mod.EventBusSubscriber(modid = GuDaoism.MOD_ID)
public class GuManipulationEvents {

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {

        if(event.getLevel().isClientSide()) return;

        if (!(event.getEntity() instanceof ItemEntity itemEntity)) return;

        if (!(itemEntity.getItem().getItem() instanceof AbstractGuItem abstractGuItem)) return;

        Level level = event.getLevel();
        Vec3 position = itemEntity.position();


        EntityType<?> guEntityType = abstractGuItem.getEntityType();
        AbstractGuEntity guEntity = (AbstractGuEntity) guEntityType.create(level);

        if(guEntity != null){

            CompoundTag tag = itemEntity.getItem().getOrCreateTag();
            if (!tag.contains("LastFedTime")) {
                tag.putLong("LastFedTime", itemEntity.getLevel().getGameTime());
            }
            guEntity.setLastFedTime(tag.getLong("LastFedTime"));

            if(itemEntity.getOwner() != null){
                guEntity.setOwnerUUID(itemEntity.getOwner().getUUID());
                guEntity.setTame(true);
            }

            guEntity.setPos(position);
            level.addFreshEntity(guEntity);
            itemEntity.discard();
        }

        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onPlayerInteractWithGu(PlayerInteractEvent.EntityInteract event){
        if(event.getTarget() instanceof AbstractGuEntity abstractGuEntity){
            Player player = event.getEntity();
            if(abstractGuEntity.isOwnedBy(player) || !abstractGuEntity.isTame()){
                if(!event.getLevel().isClientSide()){
                    ItemStack stack = abstractGuEntity.getAsItem();
                    if (!player.getInventory().add(stack)) {
                        return;
                    }
                    abstractGuEntity.discard();
                    event.setCanceled(true);
                }

            }
        }
    }

    // Starvation logic
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        // Only run on server side and END phase
        if (player.getLevel().isClientSide() || event.phase != TickEvent.Phase.END) return;
        if (event.player.getLevel().getGameTime() % 10 != 0) return; // throttle

        long currentWorldTime = player.getLevel().getGameTime();

        // Loop through inventory
        for (int i = 0; i < player.getInventory().items.size(); i++) {
            ItemStack stack = player.getInventory().getItem(i);



            if (!(stack.getItem() instanceof AbstractGuItem item)) continue;

            CompoundTag tag = stack.getOrCreateTag();

            if (!tag.contains("LastFedTime")) {
                tag.putLong("LastFedTime", currentWorldTime);
                continue;
            }

            long lastFedTime = tag.getLong("LastFedTime");
            long ticksSinceFed = currentWorldTime - lastFedTime;
            long ticksRemaining = Math.max(0, item.getMaxSatiationTime() - ticksSinceFed);

            if (ticksRemaining <= 0) {
                ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
                if(id == null) return;
                ItemStack newStack = createGamblingRockWithNBT(id.toString(), player.getLevel().random.nextFloat());
                player.getInventory().setItem(i, newStack);
            }
        }
    }

}
