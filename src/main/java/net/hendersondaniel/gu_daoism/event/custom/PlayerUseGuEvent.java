package net.hendersondaniel.gu_daoism.event.custom;

import net.hendersondaniel.gu_daoism.item.custom.gu_items.AbstractGuItem;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class PlayerUseGuEvent extends Event {

    private AbstractGuItem abstractGuItem;
    private Player player;
    private int amplifier;

    public PlayerUseGuEvent(AbstractGuItem abstractGuItem, Player player, int amplifier){
        this.abstractGuItem = abstractGuItem;
        this.player = player;
        this.amplifier=amplifier;
    }

    public AbstractGuItem getAbstractGuItem() {
        return abstractGuItem;
    }

    public void setAbstractGuItem(AbstractGuItem abstractGuItem) {
        this.abstractGuItem = abstractGuItem;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getAmplifier() {
        return amplifier;
    }

    public void setAmplifier(int amplifier) {
        this.amplifier = amplifier;
    }
}
