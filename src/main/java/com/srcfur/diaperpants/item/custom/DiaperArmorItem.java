package com.srcfur.diaperpants.item.custom;

import com.srcfur.diaperpants.effects.ModEffects;
import com.srcfur.diaperpants.statistics.ModStatistics;
import com.srcfur.diaperpants.util.DiaperFamily;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import com.srcfur.diaperpants.item.ModItems;
import com.srcfur.diaperpants.util.IEntityDiapered;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiaperArmorItem extends ArmorItem implements IAnimatable, Trinket {
    public String DiaperTexture = "";
    public int MaxUses = 1;
    /// Not implemented yet, but will be used to differentiate behaviour so you can have washable diapers.
    public boolean IsClothDiaper = false;
    public DiaperFamily family = DiaperFamily.NONE;
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public DiaperArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }
    public DiaperArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings, String texturename, Integer maxUse){
        super(material, slot, settings);
        this.MaxUses = maxUse;
        this.DiaperTexture = texturename;
        //THIS IS THE DUMBEST LINE KNOWN TO MAN AND I WILL KILL MYSELF IF THIS DOESN'T WORK CAUSE I HAD TO WASTE MY TIME WRITING IT
        //AND THIS DUMB ASS COMMENT >:(
        TrinketsApi.registerTrinket(this, this);
    }

    //After some great trials and tribulations, we have now adapted to the trinkets system for observing our state.

    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if(stack.getDamage() == stack.getMaxDamage()){
            if(!entity.hasStatusEffect(ModEffects.FULL_DIAPER_EFFECT)){
                entity.addStatusEffect(new StatusEffectInstance(ModEffects.FULL_DIAPER_EFFECT, 1, 0));
                if(entity.isPlayer()){
                    if(!entity.world.isClient){
                        ((ServerPlayerEntity)entity).incrementStat(ModStatistics.SAGGING_DIAPER_TIME);
                    }
                }
            }
        }
        if(!entity.world.isClient){
            //OnWearerEffect((ServerPlayerEntity) entity);
            if(entity.isSubmergedInWater() && isWaterLoggable()){
                if(entity.world.random.nextInt(0, stack.getMaxDamage()) >= stack.getDamage()){
                    stack.damage(1, entity.world.getRandom(), (ServerPlayerEntity)entity);
                }
            }
        }
    }
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if(!entity.world.isClient){
            stack.getOrCreateNbt().putString("WornBy", entity.getDisplayName().getString());
        }
    }
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if(stack.getNbt() != null){
            if(!stack.getNbt().getString("WornBy").isEmpty()){
                if(entity.isPlayer()){
                    if(entity.isPlayer()){
                        //We need to expose selected item from the player entity class using a mix in
                        //PlayerInventoryExposerMixin plr = (PlayerInventoryExposerMixin) entity;
                        //plr.diaperpants$setSelectedItem(GenerateTrashDiaper((PlayerEntity) entity, plr.diaperpants$getSelectedItem()));
                        if(!entity.world.isClient){
                            ServerPlayerEntity player = (ServerPlayerEntity) entity;
                            player.currentScreenHandler.setCursorStack(GenerateTrashDiaper(player, player.currentScreenHandler.getCursorStack()));
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return -(super.getItemBarStep(stack) - 13);
    }

    @Override
    public Text getName(ItemStack stack) {
        if(stack.getDamage() == getMaxDamage()){
            return Text.of("Soiled " + getName().getString());
        }
        return super.getName(stack);
    }

    public boolean isWaterLoggable(){
        return true;
    }

    public ItemStack GenerateTrashDiaper(PlayerEntity entity, ItemStack stack){

        String wearer = stack.getOrCreateNbt().getString("WornBy");
        ItemStack trashdiaper = new ItemStack(ModItems.DIAPERTRASH);
        trashdiaper.setCount(1);
        if(stack.getDamage() == 0){
            trashdiaper.setCustomName(Text.of(wearer + "'s worn " + stack.getItem().getName().getString()));
        }else{
            trashdiaper.setCustomName(Text.of(wearer + "'s used " + stack.getItem().getName().getString()));
        }
        //Do this here to swap the diapers
        return trashdiaper;
    }
    public void OnWearerEffect(ServerPlayerEntity player) {

    }

    public void OnHitEffect(ServerPlayerEntity player, ItemStack item, DamageSource source, int DamageAmount){

    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(entity.isPlayer()){
            ServerPlayerEntity spe = (ServerPlayerEntity)entity;
            if(IEntityDiapered.checkDiapered(spe)){
                ItemStack entityDiaper = spe.getInventory().getArmorStack(1);
                if(entityDiaper.getDamage() != 0){
                    spe.getInventory().armor.set(1, stack);
                    int slot = user.getInventory().getSlotWithStack(stack);
                    user.getInventory().removeStack(slot);
                    user.getInventory().setStack(slot, GenerateTrashDiaper((PlayerEntity)entity, entityDiaper));
                }
            }
        }
        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        if(stack.getNbt() != null){
            String Prefix = "Dry";
            int UsedAmount = stack.getNbt().getInt("Used");
            if(((float)stack.getDamage() / (float)stack.getMaxDamage()) > 0f){
                if(((float)stack.getDamage() / (float)stack.getMaxDamage()) > 0.7f){
                    if(((float)stack.getDamage() / (float)stack.getMaxDamage()) >= 0.99f){
                        Prefix = "Spent";
                    }else{
                        Prefix = "Soaked";
                    }
                }else{
                    Prefix = "Wet";
                }
            }
            tooltip.add(1, Text.of("Status: " + Prefix));
            String pe = stack.getNbt().getString("WornBy");
            if(!pe.isEmpty()) {
                tooltip.add(2, Text.of("Worn By: " + pe));
            }
        }else{
            tooltip.add(1, Text.of("Status: Clean"));
        }
    }
    @Override
    public Text getName() {
        String Status = "Spent";
        return new TranslatableText(this.getTranslationKey());
    }
    // Predicate runs every frame
    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        // This is all the extradata this event carries. The livingentity is the entity
        // that's wearing the armor. The itemstack and equipmentslottype are
        // explanatory.
        LivingEntity livingEntity = event.getExtraDataOfType(LivingEntity.class).get(0);

        // Always loop the animation
        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP));

        // If the living entity is an armorstand just play the animation nonstop
        if (livingEntity instanceof ArmorStandEntity) {
            return PlayState.CONTINUE;
        }

        // Make sure the player is wearing all the armor. If they are, continue playing
        // the animation, otherwise stop
        Optional<TrinketComponent> comp = TrinketsApi.getTrinketComponent(livingEntity);
        if(comp.isPresent()){
            boolean isWearingAll = comp.get().isEquipped(this);
            return isWearingAll ? PlayState.CONTINUE : PlayState.STOP;
        }
        return PlayState.STOP;
    }

    @Override
    public boolean damage(DamageSource source) {
        return super.damage(source);
    }

    @Override
    public int getProtection() {
        return super.getProtection();
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 20, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}