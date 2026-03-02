package com.srcfur.diaperpants.item.custom;

import com.srcfur.diaperpants.util.StringUtility;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class DiaperBagBlockItem extends BlockItem {
    public DiaperBagBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        String pamp = "";
        NbtCompound nbt = stack.getOrCreateNbt().getCompound("BlockEntityTag");
        if(nbt.getInt("DiapersHeld") > 0){
            NbtList inv = nbt.getList("Inventory", NbtElement.COMPOUND_TYPE);
            if(inv != null && !inv.isEmpty()){
                ItemStack diaper = ItemStack.fromNbt((NbtCompound) inv.get(0));
                pamp = "." + StringUtility.getTranslationIDOfItem(diaper.getItem());
            }
        }
        return new TranslatableText("block.diaperpants.diaper_bag" + pamp);
    }
}
