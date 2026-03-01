package com.srcfur.diaperpants.util;

import net.minecraft.item.Item;

public class StringUtility {
    /// Used to get the string that occurs after the last '.' in a translation key. Useful for auto-translation functions.
    public static String getTranslationIDOfItem(Item item){
        return item.getTranslationKey().substring(item.getTranslationKey().lastIndexOf('.') + 1);
    }
}
