package io.github.markassk.fishonmcextras.handler;

import io.github.markassk.fishonmcextras.FOMC.Enums.Location;
import net.minecraft.client.MinecraftClient;

public class LocationHandler {
    private static LocationHandler INSTANCE = new LocationHandler();

    public static LocationHandler instance() {
        if (INSTANCE == null) {
            INSTANCE = new LocationHandler();
        }
        return INSTANCE;
    }

    public Location getLocation(MinecraftClient minecraftClient, String text, Location currentLocation) {
        if(minecraftClient.player != null) {
            // Check Dimension
            String dimensionName = minecraftClient.player.getWorld().getRegistryKey().getValue().toString();
            if (!dimensionName.isEmpty()) {
                if (dimensionName.contains("crew")){
                    return Location.CREW_ISLAND;
                }
            }

            // Check Side Location
            Location sideLocation = findSideLocation((int) minecraftClient.player.getPos().x, (int) minecraftClient.player.getPos().z);
            if(sideLocation != Location.UNKNOWN) {
                return sideLocation;
            }

            // Check Normal Locations
            sideLocation = getLocation(text);
            if(sideLocation != Location.UNKNOWN) {
                return sideLocation;
            }
        }
        return currentLocation;
    }

    private Location getLocation(String bossText) {
        // todo: use Location.LOOKUP
        for (Location location : Location.values()) {
            if (location == Location.UNKNOWN || location == Location.SPAWNHUB || location == Location.CREW_ISLAND) {
                continue; // skip internal
            }
            if (bossText.contains(location.TAG.getString())) {
                return location;
            }
        }
        return Location.UNKNOWN;
    }

    private Location findSideLocation(int pX, int pZ) {
        for (SideLocations location : SideLocations.values()){
            if ((pX >= location.x1 && pX <= location.x2) && (pZ >= location.z1 && pZ <= location.z2)) {
                return location.sidelocation;
            }
        }
        return Location.UNKNOWN;
    }

    private enum SideLocations {
        SPAWNHUB(95, -58, 145, 38, Location.SPAWNHUB);

        public final int x1;
        public final int z1;
        public final int x2;
        public final int z2;
        public final Location sidelocation;

        SideLocations(int x1, int z1, int x2, int z2, Location sidelocation) {
            this.x1 = x1;
            this.z1 = z1;
            this.x2 = x2;
            this.z2 = z2;
            this.sidelocation = sidelocation;
        }
    }
}
