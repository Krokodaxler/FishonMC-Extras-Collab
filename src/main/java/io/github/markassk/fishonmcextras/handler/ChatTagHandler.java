package io.github.markassk.fishonmcextras.handler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.github.markassk.fishonmcextras.FOMC.Enums.*;
import io.github.markassk.fishonmcextras.FOMC.Types.Defaults;
import io.github.markassk.fishonmcextras.config.FishOnMCExtrasConfig;
import io.github.markassk.fishonmcextras.util.TextHelper;
import net.minecraft.text.Text;

import static org.apache.commons.lang3.StringUtils.startsWith;

public class ChatTagHandler {

    private static ChatTagHandler INSTANCE = new ChatTagHandler();

    public static ChatTagHandler instance() {
        if (INSTANCE == null) {
            INSTANCE = new ChatTagHandler();
        }
        return INSTANCE;
    }

    private record Tag(Text text, int color) {}

    private static final Map<String, Tag> KNOWN_TAGS = new LinkedHashMap<>();

    private static void registerTags(EnumLookup<? extends EnumConstant> enumLookup, String format) {
        enumLookup.getAll().forEach(value -> registerTag(String.format(format, value.name()), value.tag(), value.color()));
    }

    private static void registerTags(EnumLookup<? extends EnumConstant> enumLookup) {
        registerTags(enumLookup, "%s");
    }

    private static void registerTag(EnumConstant value) {
        registerTag(value.name(), value.tag(), value.color());
    }

    private static void registerTag(String id, Text tag, int color) {
        KNOWN_TAGS.put(id.toUpperCase(), new Tag(tag, color));
    }

    static {
        registerTag(Location.CYPRESS_LAKE);
        registerTag(Location.KENAI_RIVER);
        registerTag(Location.LAKE_BIWA);
        registerTag(Location.MURRAY_RIVER);
        registerTag(Location.EVERGLADES);
        registerTag(Location.KEY_WEST);
        registerTag(Location.TOLEDO_BEND);
        registerTag(Location.GREAT_LAKES);
        registerTag(Location.DANUBE_RIVER);
        registerTag(Location.OIL_RIG);
        registerTag(Location.AMAZON_RIVER);
        registerTag(Location.MEDITERRANEAN_SEA);
        registerTag(Location.CAPE_COD);
        registerTag(Location.HAWAII);
        registerTag(Location.LOFOTEN_ISLANDS);
        registerTag(Location.CAIRNS);

        registerTag(FishVariant.NORMAL);
        registerTag(FishVariant.ALBINO);
        registerTag(FishVariant.MELANISTIC);
        registerTag(FishVariant.TROPHY);
        registerTag(FishVariant.FABLED);

        registerTag(PlayerRank.ANGLER);
        registerTag(PlayerRank.SAILOR);
        registerTag(PlayerRank.MARINER);
        registerTag(PlayerRank.CAPTAIN);
        registerTag(PlayerRank.ADMIRAL);
        registerTag(PlayerRank.FOE);

        registerTag(WaterType.FRESHWATER);
        registerTag(WaterType.SALTWATER);

        registerTags(Climate.LOOKUP);
        registerTags(Rarity.LOOKUP);
        registerTags(FishSize.LOOKUP);
        registerTags(RareCatch.LOOKUP);
        registerTags(PetType.LOOKUP);
        registerTags(PetRating.LOOKUP);

        registerTag("luck", Text.literal("♣ Luck").withColor(0x80DAC3), 0x80DAC3);
        registerTag("scale", Text.literal("⚓ Scale").withColor(0x4C88F1), 0x4C88F1);
    }

    private static final Gson GSON = new Gson();
    private static final Pattern TAG_PATTERN = Pattern.compile("\\[([A-Z0-9_]+)\\]");

    private static final EnumConstant[] PET_RARITY_CONSTANTS = new EnumConstant[] {
            Rarity.COMMON,
            Rarity.RARE,
            Rarity.EPIC,
            Rarity.LEGENDARY,
            Rarity.MYTHICAL
    };

    private final FishOnMCExtrasConfig config = FishOnMCExtrasConfig.getConfig();

    public Text displayTags(Text message) {
        String messageString = message.getString();
        Matcher matcher = TAG_PATTERN.matcher(messageString);
        if (startsWith(messageString, "FOE » ")) {
            return message;
        }
        if (!matcher.find()) {
            return message;
        }

        String json = TextHelper.textToJson(message.copy());
        JsonElement root;
        try {
            root = JsonParser.parseString(json);
        } catch (Exception ignored) {
            return message;
        }

        boolean changed = replaceTagsInJson(root);
        if (!changed) {
            return message;
        }

        return TextHelper.jsonToText(GSON.toJson(root));
    }

    private static boolean replaceTagsInJson(JsonElement element) {
        if (element == null || element.isJsonNull()) {
            return false;
        }

        boolean changed = false;

        if (element.isJsonArray()) {
            for (JsonElement child : element.getAsJsonArray()) {
                changed |= replaceTagsInJson(child);
            }
            return changed;
        }

        if (!element.isJsonObject()) {
            return false;
        }

        JsonObject obj = element.getAsJsonObject();

        if (obj.has("extra") && obj.get("extra").isJsonArray()) {
            for (JsonElement child : obj.getAsJsonArray("extra")) {
                changed |= replaceTagsInJson(child);
            }
        }
        if (obj.has("with") && obj.get("with").isJsonArray()) {
            for (JsonElement child : obj.getAsJsonArray("with")) {
                changed |= replaceTagsInJson(child);
            }
        }

        if (!obj.has("text") || !obj.get("text").isJsonPrimitive()) {
            return changed;
        }

        String text = obj.get("text").getAsString();
        Matcher matcher = TAG_PATTERN.matcher(text);
        if (!matcher.find()) {
            return changed;
        }

        JsonObject styleTemplate = new JsonObject();
        for (var entry : obj.entrySet()) {
            String key = entry.getKey();
            if (key.equals("text") || key.equals("extra")) {
                continue;
            }
            styleTemplate.add(key, entry.getValue().deepCopy());
        }

        int firstStart = matcher.start();
        int lastEnd = matcher.end();

        JsonArray newExtra = new JsonArray();
        boolean replacedAnyAllowed = false;

        Tag tag = KNOWN_TAGS.get(matcher.group(1));
        if (tag != null) {
            newExtra.add(JsonParser.parseString(TextHelper.textToJson(tag.text)));
            replacedAnyAllowed = true;
        } else {
            JsonObject literal = new JsonObject();
            copyJsonObject(styleTemplate, literal);
            literal.addProperty("text", text.substring(matcher.start(), matcher.end()));
            newExtra.add(literal);
        }

        while (matcher.find()) {
            String between = text.substring(lastEnd, matcher.start());
            if (!between.isEmpty()) {
                JsonObject betweenObj = new JsonObject();
                copyJsonObject(styleTemplate, betweenObj);
                betweenObj.addProperty("text", between);
                newExtra.add(betweenObj);
            }

            tag = KNOWN_TAGS.get(matcher.group(1));
            if (tag != null) {
                newExtra.add(JsonParser.parseString(TextHelper.textToJson(tag.text)));
                replacedAnyAllowed = true;
            } else {
                JsonObject literal = new JsonObject();
                copyJsonObject(styleTemplate, literal);
                literal.addProperty("text", text.substring(matcher.start(), matcher.end()));
                newExtra.add(literal);
            }

            lastEnd = matcher.end();
        }

        String suffix = text.substring(lastEnd);
        if (!suffix.isEmpty()) {
            JsonObject suffixObj = new JsonObject();
            copyJsonObject(styleTemplate, suffixObj);
            suffixObj.addProperty("text", suffix);
            newExtra.add(suffixObj);
        }

        if (!replacedAnyAllowed) {
            return changed;
        }

        String prefix = text.substring(0, firstStart);
        obj.addProperty("text", prefix);

        if (obj.has("extra") && obj.get("extra").isJsonArray()) {
            JsonArray existingExtra = obj.getAsJsonArray("extra");
            for (JsonElement existing : existingExtra) {
                newExtra.add(existing);
            }
        }
        obj.add("extra", newExtra);

        return true;
    }

    private static void copyJsonObject(JsonObject from, JsonObject to) {
        for (var entry : from.entrySet()) {
            to.add(entry.getKey(), entry.getValue().deepCopy());
        }
    }

    public Text changePetTags(Text message) {
        String messageString = message.getString();
        if (!startsWith(messageString, "PET DROP! You pulled a ")) {
            return message;
        }

        if (!config.chatconfig.makeSomeTagsCopyPastable) {
            return message;
        }

        String json = TextHelper.textToJson(message.copy());
        JsonElement root;
        try {
            root = JsonParser.parseString(json);
        } catch (Exception ignored) {
            return message;
        }

        boolean changed = replaceTagsInJson(root);

        boolean changedPetRarity = replacePetRarityIconsInJson(root);
        if (!changed && !changedPetRarity) {
            return message;
        }

        return TextHelper.jsonToText(GSON.toJson(root));
    }

    private static boolean replacePetRarityIconsInJson(JsonElement element) {
        if (element == null || element.isJsonNull()) {
            return false;
        }

        boolean changed = false;

        if (element.isJsonArray()) {
            for (JsonElement child : element.getAsJsonArray()) {
                changed |= replacePetRarityIconsInJson(child);
            }
            return changed;
        }

        if (!element.isJsonObject()) {
            return false;
        }

        JsonObject obj = element.getAsJsonObject();

        if (obj.has("extra") && obj.get("extra").isJsonArray()) {
            for (JsonElement child : obj.getAsJsonArray("extra")) {
                changed |= replacePetRarityIconsInJson(child);
            }
        }
        if (obj.has("with") && obj.get("with").isJsonArray()) {
            for (JsonElement child : obj.getAsJsonArray("with")) {
                changed |= replacePetRarityIconsInJson(child);
            }
        }

        if (!obj.has("text") || !obj.get("text").isJsonPrimitive()) {
            return changed;
        }

        String text = obj.get("text").getAsString();

        int firstIndex = indexOfRarityUnicode(text);
        if (firstIndex < 0) {
            return changed;
        }

        JsonObject styleTemplate = new JsonObject();
        for (var entry : obj.entrySet()) {
            String key = entry.getKey();
            if (key.equals("text") || key.equals("extra")) {
                continue;
            }
            styleTemplate.add(key, entry.getValue().deepCopy());
        }

        String prefix = text.substring(0, firstIndex);
        obj.addProperty("text", prefix);

        com.google.gson.JsonArray newExtra = new com.google.gson.JsonArray();

        int cursor = firstIndex;
        while (cursor < text.length()) {
            int nextIndex = indexOfRarityUnicode(text, cursor);
            if (nextIndex < 0) {
                String tail = text.substring(cursor);
                if (!tail.isEmpty()) {
                    JsonObject tailObj = new JsonObject();
                    copyJsonObject(styleTemplate, tailObj);
                    tailObj.addProperty("text", tail);
                    newExtra.add(tailObj);
                }
                break;
            }

            if (nextIndex > cursor) {
                String between = text.substring(cursor, nextIndex);
                if (!between.isEmpty()) {
                    JsonObject betweenObj = new JsonObject();
                    copyJsonObject(styleTemplate, betweenObj);
                    betweenObj.addProperty("text", between);
                    newExtra.add(betweenObj);
                }
            }

            EnumConstant rarity = constantFromUnicode(text.charAt(nextIndex));
            if (rarity != null) {
                Text bracketTag = Text.literal("[" + rarity.name() + "]").withColor(rarity.color());
                newExtra.add(JsonParser.parseString(TextHelper.textToJson(bracketTag)));
                changed = true;
                cursor = nextIndex + 1;
            } else {
                JsonObject literal = new JsonObject();
                copyJsonObject(styleTemplate, literal);
                literal.addProperty("text", String.valueOf(text.charAt(nextIndex)));
                newExtra.add(literal);
                cursor = nextIndex + 1;
            }
        }

        if (obj.has("extra") && obj.get("extra").isJsonArray()) {
            for (JsonElement existing : obj.getAsJsonArray("extra")) {
                newExtra.add(existing);
            }
        }
        obj.add("extra", newExtra);

        return changed;
    }

    private static int indexOfRarityUnicode(String text) {
        return indexOfRarityUnicode(text, 0);
    }

    private static int indexOfRarityUnicode(String text, int fromIndex) {
        if (text == null || text.isEmpty()) {
            return -1;
        }
        for (int i = Math.max(0, fromIndex); i < text.length(); i++) {
            if (constantFromUnicode(text.charAt(i)) != null) {
                return i;
            }
        }
        return -1;
    }

    private static EnumConstant constantFromUnicode(char unicode) {
        String unicodeString = String.valueOf(unicode);
        for (EnumConstant rarity : PET_RARITY_CONSTANTS) {
            if (rarity.tag().getString().equals(unicodeString)) {
                return rarity;
            }
        }
        return null;
    }

    public static CompletionList findCompletions(String rawText, int cursor) {
        if (rawText == null) {
            return null;
        }
        cursor = Math.max(0, Math.min(cursor, rawText.length()));

        String beforeCursor = rawText.substring(0, cursor);

        int open = beforeCursor.lastIndexOf('[');
        if (open < 0) {
            return null;
        }

        if (beforeCursor.indexOf(']', open) != -1) {
            return null;
        }

        if (open > 0 && !Character.isWhitespace(beforeCursor.charAt(open - 1))) {
            return null;
        }

        String typed = beforeCursor.substring(open + 1);
        String typedUpper = typed.toUpperCase();

        java.util.ArrayList<Suggestion> matches = new java.util.ArrayList<>();
        for (var e : KNOWN_TAGS.entrySet()) {
            if (typedUpper.isEmpty() || e.getKey().startsWith(typedUpper)) {
                matches.add(new Suggestion(e.getKey(), e.getValue().color));
            }
        }

        if (typedUpper.isEmpty() || "ITEM".startsWith(typedUpper)) {
            matches.add(new Suggestion("item", Defaults.DEFAULT_COLOR));
        }

        if (matches.isEmpty()) {
            return null;
        }

        return new CompletionList(open, cursor, typed, matches);
    }

    public static Completion findCompletion(String rawText, int cursor) {
        CompletionList list = findCompletions(rawText, cursor);
        if (list == null || list.matches().isEmpty()) {
            return null;
        }
        Suggestion best = list.matches().getFirst();
        String full = "[" + best.tag() + "]";
        return new Completion(list.replaceFrom(), list.replaceTo(), full);
    }

    public record CompletionList(int replaceFrom, int replaceTo, String typed, java.util.List<Suggestion> matches) {
    }

    public record Completion(int replaceFrom, int replaceTo, String fullText) {
    }

    public record Suggestion(String tag, int color) {
    }
}
