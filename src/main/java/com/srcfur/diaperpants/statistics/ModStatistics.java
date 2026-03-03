package com.srcfur.diaperpants.statistics;

import com.srcfur.diaperpants.DiaperPants;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModStatistics {
    public static Identifier BLADDER_FAILIURE_STAT;
    public static Identifier BEDWETTING_STAT;
    public static Identifier SAGGING_DIAPER_TIME;

    public static void RegisterStats(){
        SAGGING_DIAPER_TIME = register("saggingdiaper", StatFormatter.TIME);
        BEDWETTING_STAT = register("bedwetter", StatFormatter.DEFAULT);
        BLADDER_FAILIURE_STAT = register("bladderfailstat", StatFormatter.DEFAULT);
    }

    private static Identifier register(String name, StatFormatter sf){
        Identifier identifier = new Identifier(name);
        Registry.register(Registry.CUSTOM_STAT, name, identifier);
        Stats.CUSTOM.getOrCreateStat(identifier, sf);
        return identifier;
    }
}
