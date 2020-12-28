package io.github.Theray070696.respawn;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Theray070696 on 9/12/2017.
 */
@Mod(modid = "respawntimer", name = "Configurable Respawn Timer", version = "$version")
public class RespawnTimer
{
    public static float respawnTimer;
    public static float respawnTimerOld;
    
    public static boolean instantRespawn;

    public static SimpleNetworkWrapper network;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        loadConfig(event);

        if(event.getSide().equals(Side.CLIENT))
        {
            MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        }

        MinecraftForge.EVENT_BUS.register(this);

        network = NetworkRegistry.INSTANCE.newSimpleChannel("respawntimer");
        network.registerMessage(PacketSyncRespawnTimer.Handler.class, PacketSyncRespawnTimer.class, 0, Side.CLIENT);
    }

    private void loadConfig(FMLPreInitializationEvent event)
    {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();

        respawnTimer = config.getFloat("Respawn Timer", "General", 1f, 0f, 60f, "How long the respawn button should be disabled for (in seconds).");
        respawnTimerOld = respawnTimer;
        
        instantRespawn = config.getBoolean("Respawn Instantly", "General", false, "Whether to respawn instantly once the timer reaches 0.");

        config.save();
    }

    @SubscribeEvent
    public void onConnect(PlayerEvent.PlayerLoggedInEvent event)
    {
        network.sendTo(new PacketSyncRespawnTimer(respawnTimer), (EntityPlayerMP) event.player);
    }
}
