package io.github.Theray070696.respawn;

import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

/**
 * Created by Theray070696 on 9/12/2017.
 */
public class ClientEventHandler
{
    @SubscribeEvent
    public void onGuiOpened(GuiOpenEvent event)
    {
        if(event.getGui() instanceof DeathScreen && !(event.getGui() instanceof GuiGameOverTimer))
        {
            event.setGui(new GuiGameOverTimer(ObfuscationReflectionHelper.getPrivateValue(DeathScreen.class, (DeathScreen) event.getGui(),
                    "field_184871_f"), ObfuscationReflectionHelper.getPrivateValue(DeathScreen.class, (DeathScreen) event.getGui(),
                    "field_213023_c")));
        }
    }

    private static class GuiGameOverTimer extends DeathScreen
    {
        private boolean shouldRun = true;
        private double enableUpdateTimer;

        public GuiGameOverTimer(ITextComponent causeOfDeath, boolean hardcore)
        {
            super(causeOfDeath, hardcore);
            enableUpdateTimer = (RespawnTimerConfig.respawnTimer.get() * 20d);
        }

        public void tick()
        {
            if(shouldRun)
            {
                this.enableUpdateTimer--;

                // What could go wrong assuming the respawn button is at index 0?
                this.buttons.get(0).message = new TranslationTextComponent("deathScreen.respawn").append(" in " + String.format("%.2f", this.enableUpdateTimer / 20d) + " seconds");

                if(this.enableUpdateTimer <= 0.0d)
                {
                    shouldRun = false;
                    
                    if (RespawnTimerConfig.instantRespawn.get()) {
                        this.minecraft.player.respawn();
                        this.minecraft.setScreen((Screen) null);
                        return;
                    }
                    
                    for(Widget guibutton : this.buttons)
                    {
                        guibutton.active = true;
                    }

                    this.buttons.get(0).message = new TranslationTextComponent("deathScreen.respawn");
                }
            }
        }
    }
}
