package de.ancash.libs.org.simpleyaml.configuration.implementation.snakeyaml;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import de.ancash.libs.org.simpleyaml.configuration.implementation.api.QuoteStyle;
import de.ancash.libs.org.yaml.snakeyaml.DumperOptions;

public class SnakeYamlQuoteValue {

    private static final Map<QuoteStyle, DumperOptions.ScalarStyle> QUOTE_SCALAR_STYLES = mapQuoteScalarStyles();

    public static DumperOptions.ScalarStyle getQuoteScalarStyle(final QuoteStyle quoteStyle) {
        return QUOTE_SCALAR_STYLES.get(quoteStyle);
    }

    private static Map<QuoteStyle, DumperOptions.ScalarStyle> mapQuoteScalarStyles() {
        final Map<QuoteStyle, DumperOptions.ScalarStyle> map = new HashMap<>();
        map.put(null, DumperOptions.ScalarStyle.PLAIN);
        map.put(QuoteStyle.PLAIN, DumperOptions.ScalarStyle.PLAIN);
        map.put(QuoteStyle.SINGLE, DumperOptions.ScalarStyle.SINGLE_QUOTED);
        map.put(QuoteStyle.DOUBLE, DumperOptions.ScalarStyle.DOUBLE_QUOTED);
        map.put(QuoteStyle.LITERAL, DumperOptions.ScalarStyle.LITERAL);
        map.put(QuoteStyle.FOLDED, DumperOptions.ScalarStyle.FOLDED);
        return Collections.unmodifiableMap(map);
    }
}
