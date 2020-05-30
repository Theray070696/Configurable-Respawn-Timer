package io.github.Theray070696.respawn;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

/**
 * Created by Theray070696 on 9/12/2017.
 */
public class ClientEventHandler
{
    @SubscribeEvent
    public void onGuiOpened(GuiOpenEvent event)
    {
        if(event.getGui() instanceof GuiGameOver && !(event.getGui() instanceof GuiGameOverTimer))
        {
            event.setGui(new GuiGameOverTimer(ObfuscationReflectionHelper.getPrivateValue(GuiGameOver.class, (GuiGameOver) event.getGui(),
                    "field_184871_f", "bkt", "causeOfDeath")));
        }
    }

    @SubscribeEvent
    public void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event)
    {
        RespawnTimer.respawnTimer = RespawnTimer.respawnTimerOld;
    }

    private static class GuiGameOverTimer extends GuiGameOver
    {
        private boolean shouldRun = true;
        private int enableUpdateTimer;

        public GuiGameOverTimer(ITextComponent causeOfDeath)
        {
            super(causeOfDeath);
            enableUpdateTimer = (RespawnTimer.respawnTimer * 20);
        }

        public void updateScreen()
        {
            if(shouldRun)
            {
                this.enableUpdateTimer--;
            }

            if(this.enableUpdateTimer <= 0)
            {
                shouldRun = false;
                for(GuiButton guibutton : this.buttonList)
                {
                    guibutton.enabled = true;
                }
            }
        }
    }
}
