package com.cstav.evenmoreinstruments.item.emirecord;

import com.cstav.evenmoreinstruments.EMIMain;
import com.cstav.genshinstrument.util.CommonUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RecordRepository {
    public static void load() {}

    // idk of thread safety is important here, idk how minecraft handles data loading. better be safe than sorry.
    private static final ConcurrentHashMap<ResourceLocation, CompoundTag> RECORDS = new ConcurrentHashMap<>();
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String DATA_DIR = EMIMain.MODID+"/records";

    static {
        subscribeResourcesReloadEvent();
    }

    public static CompoundTag getRecord(final ResourceLocation loc) {
        return RECORDS.get(loc).copy();
    }


    private static void subscribeResourcesReloadEvent() {
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public ResourceLocation getFabricId() {
                return new ResourceLocation(EMIMain.MODID, "burned_record_resources");
            }

            @Override
            public void onResourceManagerReload(ResourceManager resourceManager) {
                reloadRecords(resourceManager.listResources(DATA_DIR, (path) -> path.getPath().endsWith(".json")));
            }
        });
    }

    private static void reloadRecords(Map<ResourceLocation, Resource> pObject) {
        RECORDS.clear();

        pObject.forEach((loc, tag) -> {
            final JsonElement channelObj;
            try (final BufferedReader reader = pObject.get(loc).openAsReader()) {
                channelObj = JsonParser.parseReader(reader);
            } catch (Exception e) {
                LOGGER.error("Met an exception upon loading burned record "+loc, e);
                return;
            }

            RECORDS.put(stripFullPath(loc), (CompoundTag) JsonOps.INSTANCE.convertTo(NbtOps.INSTANCE, channelObj));
            LOGGER.info("Successfully loaded burned record {}", loc);
        });
    }

    private static ResourceLocation stripFullPath(final ResourceLocation loc) {
        final String[] paths = loc.getPath().split("/");
        final String path = paths[paths.length - 1];
        return CommonUtil.withPath(loc, path.substring(0, path.lastIndexOf('.')));
    }
}
