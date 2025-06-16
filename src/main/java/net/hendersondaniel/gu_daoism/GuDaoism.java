package net.hendersondaniel.gu_daoism;

import com.mojang.logging.LogUtils;
import net.hendersondaniel.gu_daoism.block.ModBlocks;
import net.hendersondaniel.gu_daoism.commands.SetPrimevalEssenceCommand;
import net.hendersondaniel.gu_daoism.commands.SetRawStageCommand;
import net.hendersondaniel.gu_daoism.commands.SetTalentCommand;
import net.hendersondaniel.gu_daoism.config.ClientConfigs;
import net.hendersondaniel.gu_daoism.config.CommonConfigs;
import net.hendersondaniel.gu_daoism.effect.ModEffects;
import net.hendersondaniel.gu_daoism.entity.ModEntities;
import net.hendersondaniel.gu_daoism.entity.client.JadeSkinGuEntityRenderer;
import net.hendersondaniel.gu_daoism.fluid.ModFluidTypes;
import net.hendersondaniel.gu_daoism.fluid.ModFluids;
import net.hendersondaniel.gu_daoism.item.ModItems;
import net.hendersondaniel.gu_daoism.networking.ModMessages;
import net.hendersondaniel.gu_daoism.sounds.ModSounds;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(GuDaoism.MOD_ID)
public class GuDaoism
{
    public static final String MOD_ID = "gu_daoism";
    public static final Logger LOGGER = LogUtils.getLogger();



    public GuDaoism(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();
        ModEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModFluids.register(modEventBus);
        ModFluidTypes.register(modEventBus);

        ModSounds.register(modEventBus);
        ModEffects.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        context.registerConfig(ModConfig.Type.CLIENT, ClientConfigs.SPEC, "gu_daoism-client.toml");
        context.registerConfig(ModConfig.Type.COMMON, CommonConfigs.SPEC, "gu_daoism-common.toml");
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            ModMessages.register();


        });

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        SetRawStageCommand.register(event.getDispatcher());
        SetPrimevalEssenceCommand.register(event.getDispatcher());
        SetTalentCommand.register(event.getDispatcher());


    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_PRIMEVAL_ESSENCE_LIQUID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_PRIMEVAL_ESSENCE_FLUID.get(), RenderType.translucent());
            EntityRenderers.register(ModEntities.JADE_SKIN_GU_ENTITY.get(), JadeSkinGuEntityRenderer::new);

        }
    }
}
