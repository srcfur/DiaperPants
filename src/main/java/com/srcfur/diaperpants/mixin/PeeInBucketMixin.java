package com.srcfur.diaperpants.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import com.srcfur.diaperpants.fluids.ModFluids;
import com.srcfur.diaperpants.server.BladderManager;
import com.srcfur.diaperpants.util.IEntityDataSaver;
import com.srcfur.diaperpants.util.IEntityDiapered;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
public class PeeInBucketMixin {
    @Inject(method = "use", at = @At("TAIL"))
    public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> result) {
        ItemStack stack = user.getInventory().getStack(user.getInventory().selectedSlot);
        IEntityDataSaver ds = (IEntityDataSaver) user;
        if(IEntityDiapered.getBladderLevel(user) >= 36 && ds.getPersistentData().getInt("PottyUseDelay") <= 0){
            IEntityDiapered.setBladderLevel(user, IEntityDiapered.getBladderLevel(user) - 36);
            stack = ItemUsage.exchangeStack(stack, user, new ItemStack(ModFluids.URINE_BUCKET));
            if(!world.isClient()){
                BladderManager.syncBladder((ServerPlayerEntity) user);
            }
            user.getInventory().setStack(user.getInventory().selectedSlot, stack);
            ds.getPersistentData().putInt("PottyUseDelay", 10);
            //result.setReturnValue(TypedActionResult.success(stack));
        }
    }
}
