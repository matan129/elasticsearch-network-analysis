package org.elasticsearch.index.analysis;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Patterns {

    public static final String MAC_PART = "[a-fA-F0-9][a-fA-F0-9]";
    public static final String IP_PART = "25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?";

    public static String repeat(String base, String separator, int count) {
        return combinePatterns(Collections.nCopies(count, base), separator);
    }

    public static String combinePatterns(String... patterns) {
        return combinePatterns(Lists.newArrayList(patterns));
    }

    public static String combinePatterns(List<String> patterns) {
        return Joiner.on("|").join(patterns.stream()
                                           .map(Patterns::toNonCapturingGroup)
                                           .collect(Collectors.toList()));
    }

    public static String combinePatterns(List<String> patterns, String separator) {
        return Joiner.on(separator).join(patterns.stream()
                                                 .map(Patterns::toNonCapturingGroup)
                                                 .collect(Collectors.toList()));
    }

    public static String toGroup(String pattern) {
        return "(" + pattern + ")";
    }

    public static String toNonCapturingGroup(String pattern) {
        return toGroup("?:" + pattern);
    }
}