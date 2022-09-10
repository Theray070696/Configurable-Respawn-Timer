package io.github.Theray070696.respawn;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * Created by Theray070696 on 9/12/2017.
 */
@Mod("respawntimer")
public class RespawnTimer
{
    //public static SimpleNetworkWrapper network;

    public RespawnTimer()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, RespawnTimerConfig.CONFIG_SPEC, "respawntimer-common.toml");

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::preInit);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientPreInit);
    }

    public void preInit(FMLCommonSetupEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientPreInit(FMLClientSetupEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }
}
