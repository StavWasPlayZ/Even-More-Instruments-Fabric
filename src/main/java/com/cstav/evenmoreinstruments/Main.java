package com.cstav.evenmoreinstruments;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("evenmoreinstruments");

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
	}
}