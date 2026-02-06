package com.srcfur.diaperpants;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import com.srcfur.diaperpants.block.ModBlockEntities;
import com.srcfur.diaperpants.block.ModBlocks;
import com.srcfur.diaperpants.fluids.ModFluids;
import com.srcfur.diaperpants.item.ModItems;
import com.srcfur.diaperpants.networking.ModMessages;
import com.srcfur.diaperpants.server.BladderManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiaperPants implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("diaperpants");
	public static final String MOD_ID = "diaperpants";

	@Override
	public void onInitialize() {
		//Registration
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerAllBlockEntities();
		ModFluids.registerFluids();

		//Networking
		ModMessages.registerC2SPackets();
		ModMessages.registerS2CPackets();

		//Events
		ServerTickEvents.START_WORLD_TICK.register(new BladderManager());
	}
}
