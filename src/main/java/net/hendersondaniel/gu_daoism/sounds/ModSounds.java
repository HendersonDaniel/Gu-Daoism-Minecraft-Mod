package net.hendersondaniel.gu_daoism.sounds;


import net.hendersondaniel.gu_daoism.GuDaoism;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, GuDaoism.MOD_ID);

    public static final RegistryObject<SoundEvent> OUT_OF_ESSENCE_SOUND = registerSoundEvent("out_of_essence");
    public static final RegistryObject<SoundEvent> JADE_SKIN_GU_SOUND= registerSoundEvent("jade_skin_gu_sound");


    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(GuDaoism.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
