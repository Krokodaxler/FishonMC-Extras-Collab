package io.github.markassk.fishonmcextras.handler;

import io.github.markassk.fishonmcextras.FOMC.Enums.PlayerRank;
import io.github.markassk.fishonmcextras.FOMC.Types.Defaults;
import io.github.markassk.fishonmcextras.FishOnMCExtras;
import io.github.markassk.fishonmcextras.config.FishOnMCExtrasConfig;
import io.github.markassk.fishonmcextras.mixin.PlayerListHudAccessor;
import io.github.markassk.fishonmcextras.util.TextHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.*;

public class TabHandler {
    private static TabHandler INSTANCE = new TabHandler();
    private final FishOnMCExtrasConfig config = FishOnMCExtrasConfig.getConfig();

    public Text player = Text.empty();
    public PlayerRank rank = PlayerRank.UNKNOWN;
    public String instance = "";
    public boolean isInstance = false;

    private Collection<PlayerListEntry> playerListEntries = new ArrayList<>();

    public static TabHandler instance() {
        if (INSTANCE == null) {
            INSTANCE = new TabHandler();
        }
        return INSTANCE;
    }

    public void tick(MinecraftClient minecraftClient) {
        if (LoadingHandler.instance().isLoadingDone) {
            try {
                PlayerListHud playerListHud = minecraftClient.inGameHud.getPlayerListHud();
                if (minecraftClient.player != null) {
                    this.player = playerListHud
                            .getPlayerName(Objects.requireNonNull(minecraftClient.getNetworkHandler())
                                    .getPlayerListEntry(minecraftClient.player.getUuid()));
                    this.rank = PlayerRank.LOOKUP.findContainingTag(this.player.getString());
                }

                if (((PlayerListHudAccessor) playerListHud).getFooter() != null) {
                    if (((PlayerListHudAccessor) playerListHud).getFooter().getString().contains("ɪɴꜱᴛᴀɴᴄᴇ")) {
                        this.isInstance = true;
                        String footer = ((PlayerListHudAccessor) playerListHud).getFooter().getString();
                        this.instance = footer.substring(footer.indexOf("ɪɴꜱᴛᴀɴᴄᴇ") + 8, footer.lastIndexOf("("))
                                .trim();
                    } else {
                        this.isInstance = false;
                    }
                }

                Collection<PlayerListEntry> currentEntries = Objects.requireNonNull(minecraftClient.getNetworkHandler())
                    .getListedPlayerListEntries();
                List<PlayerListEntry> previousEntries = new ArrayList<>(playerListEntries);

                if (config.crewTracker.notifyCrewOnJoin
                    && currentEntries.size() > previousEntries.size()
                    && !previousEntries.isEmpty()) {
                    List<PlayerListEntry> differences = new ArrayList<>(currentEntries);
                    differences.removeAll(previousEntries);

                    if (differences.size() == 1) {
                        PlayerListEntry player = differences.getFirst();
                        Text displayName = player.getDisplayName();
                        if (displayName != null
                                && Defaults.foeDevs.containsKey(player.getProfile().getId().toString())) {
                            if (config.fun.isFoeTagPrefix) {
                                displayName = PlayerRank.FOE.TAG.copy()
                                        .append(Text.literal(" " + player.getProfile().getName()).withColor(0x00AF0E));
                            } else {
                                displayName = displayName.copy().append(Text.literal(" ").append(PlayerRank.FOE.TAG));
                            }
                        }
                        if (ProfileDataHandler.instance().profileData.crewMembers
                                .contains(player.getProfile().getId())) {
                            minecraftClient.inGameHud.getChatHud().addMessage(TextHelper.concat(
                                    Text.literal("CREWS ").withColor(0x70aa6e).formatted(Formatting.BOLD),
                                    Text.literal("» ").withColor(0x545454),
                                    Text.literal("FoE ").formatted(Formatting.DARK_GREEN, Formatting.BOLD),
                                    Text.literal("| ").formatted(Formatting.DARK_GRAY),
                                    displayName,
                                    Text.literal(" joined").formatted(Formatting.GREEN),
                                    Text.literal(" the server").withColor(0xa8a8a8)));
                        }
                    }
                } else if (config.crewTracker.notifyCrewOnLeave
                    && currentEntries.size() < previousEntries.size()) {
                    List<PlayerListEntry> differences = new ArrayList<>(previousEntries);
                    differences.removeAll(currentEntries);

                    if (differences.size() == 1) {
                        PlayerListEntry player = differences.getFirst();
                        Text displayName = player.getDisplayName();
                        if (displayName != null
                                && Defaults.foeDevs.containsKey(player.getProfile().getId().toString())) {
                            if (config.fun.isFoeTagPrefix) {
                                displayName = PlayerRank.FOE.TAG.copy()
                                        .append(Text.literal(" " + player.getProfile().getName()).withColor(0x00AF0E));
                            } else {
                                displayName = displayName.copy().append(Text.literal(" ").append(PlayerRank.FOE.TAG));
                            }
                        }
                        if (ProfileDataHandler.instance().profileData.crewMembers
                                .contains(player.getProfile().getId())) {
                            minecraftClient.inGameHud.getChatHud().addMessage(TextHelper.concat(
                                    Text.literal("CREWS ").withColor(0x70aa6e).formatted(Formatting.BOLD),
                                    Text.literal("» ").withColor(0x545454),
                                    Text.literal("FoE ").formatted(Formatting.DARK_GREEN, Formatting.BOLD),
                                    Text.literal("| ").formatted(Formatting.DARK_GRAY),
                                    displayName,
                                    Text.literal(" left").formatted(Formatting.RED),
                                    Text.literal(" the server").withColor(0xa8a8a8)));
                        }
                    }
                }

                // Friend Tracker
                if (config.friendTracker.notifyFriendOnJoin
                    && currentEntries.size() > previousEntries.size()
                    && !previousEntries.isEmpty()) {
                    List<PlayerListEntry> differences = new ArrayList<>(currentEntries);
                    differences.removeAll(previousEntries);

                    if (differences.size() == 1) {
                        PlayerListEntry player = differences.getFirst();
                        Text displayName = player.getDisplayName();
                        if (displayName != null
                                && Defaults.foeDevs.containsKey(player.getProfile().getId().toString())) {
                            if (config.fun.isFoeTagPrefix) {
                                displayName = PlayerRank.FOE.TAG.copy()
                                        .append(Text.literal(" " + player.getProfile().getName()).withColor(0x00AF0E));
                            } else {
                                displayName = displayName.copy().append(Text.literal(" ").append(PlayerRank.FOE.TAG));
                            }
                        }
                        if (ProfileDataHandler.instance().profileData.friends
                                .contains(player.getProfile().getId())) {
                            minecraftClient.inGameHud.getChatHud().addMessage(TextHelper.concat(
                                    Text.literal("FRIENDS ").withColor(0x70aa6e).formatted(Formatting.BOLD),
                                    Text.literal("» ").withColor(0x545454),
                                    Text.literal("FoE ").formatted(Formatting.DARK_GREEN, Formatting.BOLD),
                                    Text.literal("| ").formatted(Formatting.DARK_GRAY),
                                    displayName,
                                    Text.literal(" joined").formatted(Formatting.GREEN),
                                    Text.literal(" the server").withColor(0xa8a8a8)));
                        }
                    }
                } else if (config.friendTracker.notifyFriendOnLeave
                    && currentEntries.size() < previousEntries.size()) {
                    List<PlayerListEntry> differences = new ArrayList<>(previousEntries);
                    differences.removeAll(currentEntries);

                    if (differences.size() == 1) {
                        PlayerListEntry player = differences.getFirst();
                        Text displayName = player.getDisplayName();
                        if (displayName != null
                                && Defaults.foeDevs.containsKey(player.getProfile().getId().toString())) {
                            if (config.fun.isFoeTagPrefix) {
                                displayName = PlayerRank.FOE.TAG.copy()
                                        .append(Text.literal(" " + player.getProfile().getName()).withColor(0x00AF0E));
                            } else {
                                displayName = displayName.copy().append(Text.literal(" ").append(PlayerRank.FOE.TAG));
                            }
                        }
                        if (ProfileDataHandler.instance().profileData.friends
                                .contains(player.getProfile().getId())) {
                            minecraftClient.inGameHud.getChatHud().addMessage(TextHelper.concat(
                                    Text.literal("FRIENDS ").withColor(0x70aa6e).formatted(Formatting.BOLD),
                                    Text.literal("» ").withColor(0x545454),
                                    Text.literal("FoE ").formatted(Formatting.DARK_GREEN, Formatting.BOLD),
                                    Text.literal("| ").formatted(Formatting.DARK_GRAY),
                                    displayName,
                                    Text.literal(" left").formatted(Formatting.RED),
                                    Text.literal(" the server").withColor(0xa8a8a8)));
                        }
                    }
                }

                // CREWS » Crew Chat has been enabled (/crew chat)
                if (currentEntries.size() != previousEntries.size()) {
                    playerListEntries = new ArrayList<>(currentEntries);
                }
            } catch (Exception e) {
                FishOnMCExtras.LOGGER.error("TabHandler: {}", e.getMessage());
            }
        }
    }

    public String getPlayer(UUID uuid) {
        if (MinecraftClient.getInstance().getNetworkHandler() != null) {
            PlayerListEntry playerListEntry = MinecraftClient.getInstance().getNetworkHandler()
                    .getPlayerListEntry(uuid);
            return playerListEntry != null ? playerListEntry.getProfile().getName() : null;
        }
        return null;
    }
}
