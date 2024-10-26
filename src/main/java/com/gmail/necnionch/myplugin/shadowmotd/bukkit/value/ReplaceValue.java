package com.gmail.necnionch.myplugin.shadowmotd.bukkit.value;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

public class ReplaceValue {

    private final @NotNull String input;
    private final @Nullable Pattern pattern;
    private final @Nullable String outputTrue;
    private final @Nullable String outputFalse;

    public ReplaceValue(@NotNull String input, @Nullable Pattern pattern, @Nullable String outputTrue, @Nullable String outputFalse) {
        this.input = input;
        this.pattern = pattern;
        this.outputTrue = outputTrue;
        this.outputFalse = outputFalse;
    }

    @NotNull
    public String getInput() {
        return input;
    }

    @Nullable
    public Pattern getPattern() {
        return pattern;
    }

    @Nullable
    public String getOutputTrue() {
        return outputTrue;
    }

    @Nullable
    public String getOutputFalse() {
        return outputFalse;
    }

    /**
     * replacerを使い、値を置換して処理します
     * <p>
     * パターンが null の時は常に outputTrue として出力します
     * <p>
     * outputTrue または outputFalse が null の時は常に置換された input の値を出力します
     */
    public String replace(Function<String, String> replacer) {
        String value = replacer.apply(input);
        String output = (pattern == null || pattern.matcher(value).find()) ? outputTrue : outputFalse;
        return output == null ? value : replacer.apply(output);
    }


    public static ReplaceValue deserialize(Map<Object, Object> data) {
        try {
            String input = (String) data.get("input");
            String outputTrue = data.containsKey("output-true") ? (String) data.get("output-true") : null;
            String outputFalse = data.containsKey("output-false") ? (String) data.get("output-false") : null;
            Pattern pattern = data.containsKey("pattern") ? Pattern.compile((String) data.get("pattern")) : null;
            return new ReplaceValue(input, pattern, outputTrue, outputFalse);
        } catch (ClassCastException | IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
