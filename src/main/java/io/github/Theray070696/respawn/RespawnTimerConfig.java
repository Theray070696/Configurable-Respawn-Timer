package io.github.Theray070696.respawn;

import net.minecraftforge.common.ForgeConfigSpec;

public class RespawnTimerConfig
{
    public static final ForgeConfigSpec.DoubleValue respawnTimer;
    public static final ForgeConfigSpec.BooleanValue instantRespawn;

    public static final ForgeConfigSpec CONFIG_SPEC;

    static
    {
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("General");
        respawnTimer = builder.comment("How long the respawn button should be disabled for (in seconds).").defineInRange("Respawn Timer", 1.0d, 0.0d, 600d);
        instantRespawn = builder.comment("Whether to respawn instantly once the timer reaches 0.").define("Respawn Instantly", false);
        builder.pop();

        CONFIG_SPEC = builder.build();
    }
}
