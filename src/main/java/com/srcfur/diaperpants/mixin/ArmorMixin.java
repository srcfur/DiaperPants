package com.srcfur.diaperpants.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import com.srcfur.diaperpants.item.custom.DiaperArmorItem;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ItemStack.class)
public abstract class ArmorMixin implements ItemStackMixin {
	@Inject(method = "damage", at = @At("HEAD"), cancellable = true)
	public void damage(int amount, Random random, @Nullable ServerPlayerEntity player, CallbackInfoReturnable returnable) {
		if(ourItem().getClass() == DiaperArmorItem.class || DiaperArmorItem.class.isAssignableFrom(ourItem().getClass())){
			DiaperArmorItem diaper = (DiaperArmorItem)ourItem();
			int newdamage = getStackDamage() + amount;
			newdamage = Math.min(newdamage, diaper.getMaxDamage());
			setStackDamage(newdamage);

			returnable.setReturnValue(false);
		}
	}
}
