package com.srcfur.diaperpants.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ItemStack.class)
public interface ItemStackMixin {
    @Invoker("getItem")
    public Item ourItem();
    @Invoker("setDamage")
    public void setStackDamage(int damage);
    @Invoker("getDamage")
    public int getStackDamage();
}
