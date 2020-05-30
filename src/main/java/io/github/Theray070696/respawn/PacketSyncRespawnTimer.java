package io.github.Theray070696.respawn;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by Theray070696 on 9/12/2017.
 */
public class PacketSyncRespawnTimer implements IMessage
{
    float timer;

    public PacketSyncRespawnTimer()
    {
    }

    public PacketSyncRespawnTimer(float timer)
    {
        this.timer = timer;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.timer = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeFloat(this.timer);
    }

    public static class Handler implements IMessageHandler<PacketSyncRespawnTimer, IMessage>
    {
        @Override
        public IMessage onMessage(PacketSyncRespawnTimer message, MessageContext ctx)
        {
            Minecraft.getMinecraft().addScheduledTask(new Runnable()
            {
                @Override
                public void run()
                {
                    RespawnTimer.respawnTimerOld = RespawnTimer.respawnTimer;
                    RespawnTimer.respawnTimer = message.timer;
                }
            });

            return null;
        }
    }
}
