package com.srcfur.diaperpants.mixin;

import net.minecraft.entity.player.HungerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HungerManager.class)
public interface IFoodMixin {
    @Accessor("saturationLevel")
    public float getdpsaturationLevel();
    @Accessor("exhaustion")
    public float getdpexhaustion();
}
