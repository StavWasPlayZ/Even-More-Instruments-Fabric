package com.cstav.evenmoreinstruments.item.emirecord;

import com.cstav.evenmoreinstruments.EMIMain;
import com.cstav.evenmoreinstruments.server.MCServerInstance;
import com.google.common.collect.Streams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.FileUtil;
import net.minecraft.ResourceLocationException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.storage.LevelResource;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Stream;

public class RecordRepository {
    public static void load() {}

    // idk of thread safety is important here, idk how minecraft handles data loading. better be safe than sorry.
    private static final ConcurrentHashMap<ResourceLocation, CompoundTag> RECORDS = new ConcurrentHashMap<>();
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String RECORDS_DIR = "records";
    private static final String DATA_DIR = EMIMain.MODID+"/"+RECORDS_DIR;

    static {
        subscribeResourcesReloadEvent();
    }

    public static Optional<CompoundTag> getRecord(final ResourceLocation loc) {
        return RECORDS.containsKey(loc)
            ? Optional.of(RECORDS.get(loc).copy())
            : tryGetRecordFromGen(loc);
    }
    public static Set<ResourceLocation> getRecords() {
        return Collections.unmodifiableSet(RECORDS.keySet());
    }

    private static Optional<CompoundTag> tryGetRecordFromGen(final ResourceLocation loc) {
        try {
            final Path file = getRecordPath(loc, true);

            try (final BufferedReader reader = Files.newBufferedReader(file)) {
                loadRecord(loc, JsonParser.parseReader(reader));
            } catch (Exception e) {
                return Optional.empty();
            }

            return Optional.of(RECORDS.get(loc).copy());
        } catch (Exception e) {
            return Optional.empty();
        }
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

            loadRecord(loc, channelObj);
        });
    }
    private static void loadRecord(final ResourceLocation loc, final JsonElement channelObj) {
        RECORDS.put(stripFullPath(loc), (CompoundTag) JsonOps.INSTANCE.convertTo(NbtOps.INSTANCE, channelObj));
        LOGGER.info("Successfully loaded burned record {}", loc);
    }

    private static ResourceLocation stripFullPath(final ResourceLocation loc) {
        final String[] paths = loc.getPath().split("/");
        final String path = paths[paths.length - 1];
        return loc.withPath(path.substring(0, path.lastIndexOf('.')));
    }



    public static Stream<ResourceLocation> listRecords(final boolean includeBuiltIn) {
        try {
            return Streams.concat(
                // Datapacks
                getRecords().stream()
                    // Only non-built-ins
                    .filter((loc) -> !loc.getNamespace().equals(EMIMain.MODID) || includeBuiltIn),
                // Generated
                Files.list(getGenPath())
                    .filter(Files::isDirectory)
                    .flatMap(RecordRepository::listGeneratedInNamespace)
            ).distinct();
        } catch (Exception e) {
            return Stream.empty();
        }
    }

    public static void saveRecord(final ResourceLocation name, final CompoundTag channel) throws IOException {
        final Path path = getRecordPath(name, false);
        Files.createDirectories(path.getParent());

        try (final FileWriter outStream = new FileWriter(path.toFile())) {
            final JsonElement jsonData = NbtOps.INSTANCE.convertTo(JsonOps.INSTANCE, channel);

            final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonData, outStream);
        }

        RECORDS.put(name, channel);
    }

    public static void removeRecord(final ResourceLocation name) throws IOException {
        final Path path = getRecordPath(name, true);

        if (!Files.isRegularFile(path))
            throw new ResourceLocationException("Could not find resource "+name);

        Files.delete(path);
        RECORDS.remove(name);
    }


    /**
     * Queries the {@code generated} level directory
     */
    private static Path getGenPath(final boolean failIfNone) throws IOException {
        final Path path = MCServerInstance.get()
            .getWorldPath(LevelResource.GENERATED_DIR)
            .normalize();

        if (!Files.isDirectory(path)) {
            if (failIfNone)
                throw new IOException("Directory not found: "+path);
        } else {
            Files.createDirectories(path);
        }

        return path;
    }
    private static Path getGenPath() throws IOException {
        return getGenPath(false);
    }

    /**
     * Queries the record path from the {@code generated} level directory
     */
    private static Path getRecordPath(ResourceLocation name, boolean failIfNone) throws IOException {
        final Path genPath = getGenPath(failIfNone)
            .resolve(name.getNamespace()).resolve(EMIMain.MODID).resolve(RECORDS_DIR);

        final Path path = FileUtil.createPathToResource(genPath, name.getPath(), ".json");

        // Copied from StructureTemplateManager#createAndValidatePathToStructure
        if (!(path.startsWith(genPath) && FileUtil.isPathNormalized(path) && FileUtil.isPathPortable(path)))
            throw new ResourceLocationException("Invalid resource path: " + path);

        if (!Files.isRegularFile(path)) {
            if (failIfNone)
                throw new ResourceLocationException("Could not find resource "+name);
        }

        return path;
    }


    // Copied from StructureTemplateManager#listGeneratedInNamespace etc.
    private static Stream<ResourceLocation> listGeneratedInNamespace(Path pPath) {
        Path path = pPath.resolve(EMIMain.MODID).resolve(RECORDS_DIR);
        return listFolderContents(path, pPath.getFileName().toString(), ".json");
    }
    private static Stream<ResourceLocation> listFolderContents(Path pFolder, String pNamespace, String pPath) {
        if (!Files.isDirectory(pFolder)) {
            return Stream.empty();
        } else {
            int i = pPath.length();
            Function<String, String> function = (p_230358_) -> {
                return p_230358_.substring(0, p_230358_.length() - i);
            };

            try {
                return Files.walk(pFolder).filter((p_230381_) -> {
                    return p_230381_.toString().endsWith(pPath);
                }).mapMulti((p_230386_, p_230387_) -> {
                    try {
                        p_230387_.accept(new ResourceLocation(pNamespace, function.apply(relativize(pFolder, p_230386_))));
                    } catch (ResourceLocationException resourcelocationexception) {
                        LOGGER.error("Invalid location while listing pack contents", (Throwable)resourcelocationexception);
                    }

                });
            } catch (IOException ioexception) {
                LOGGER.error("Failed to list folder contents", (Throwable)ioexception);
                return Stream.empty();
            }
        }
    }
    private static String relativize(Path pRoot, Path pPath) {
        return pRoot.relativize(pPath).toString().replace(File.separator, "/");
    }

}
