package de.ancash.libs.org.simpleyaml.configuration.implementation.snakeyaml;

import java.util.LinkedHashMap;
import java.util.Map;

import de.ancash.libs.org.simpleyaml.configuration.ConfigurationSection;
import de.ancash.libs.org.simpleyaml.configuration.implementation.api.QuoteValue;
import de.ancash.libs.org.simpleyaml.configuration.serialization.ConfigurationSerializable;
import de.ancash.libs.org.simpleyaml.configuration.serialization.ConfigurationSerialization;
import de.ancash.libs.org.yaml.snakeyaml.DumperOptions;
import de.ancash.libs.org.yaml.snakeyaml.nodes.Node;
import de.ancash.libs.org.yaml.snakeyaml.nodes.Tag;
import de.ancash.libs.org.yaml.snakeyaml.representer.Represent;
import de.ancash.libs.org.yaml.snakeyaml.representer.Representer;

/**
 * @author Bukkit
 * @author Carleslc
 * @see <a href="https://github.com/Bukkit/Bukkit/tree/master/src/main/java/org/bukkit/configuration/file/YamlRepresenter.java">Bukkit Source</a>
 */
public class SnakeYamlRepresenter extends Representer {

    public SnakeYamlRepresenter() {
        this.multiRepresenters.put(ConfigurationSection.class, new RepresentConfigurationSection());
        this.multiRepresenters.put(ConfigurationSerializable.class, new RepresentConfigurationSerializable());
        this.multiRepresenters.put(QuoteValue.class, new RepresentQuoteValue());
    }

    private final class RepresentConfigurationSection extends RepresentMap {

        @Override
        public Node representData(final Object data) {
            return super.representData(((ConfigurationSection) data).getValues(false));
        }

    }

    private final class RepresentConfigurationSerializable extends RepresentMap {

        @Override
        public Node representData(final Object data) {
            final ConfigurationSerializable serializable = (ConfigurationSerializable) data;
            final Map<String, Object> values = new LinkedHashMap<>();
            values.put(ConfigurationSerialization.SERIALIZED_TYPE_KEY, ConfigurationSerialization.getAlias(serializable.getClass()));
            values.putAll(serializable.serialize());

            return super.representData(values);
        }

    }

    private final class RepresentQuoteValue implements Represent {

        @Override
        public Node representData(final Object data) {
            final QuoteValue<?> quoteValue = (QuoteValue<?>) data;

            final DumperOptions.ScalarStyle quoteScalarStyle = SnakeYamlQuoteValue.getQuoteScalarStyle(quoteValue.getQuoteStyle());
            final Object value = quoteValue.getValue();

            if (value == null) {
                return representScalar(Tag.NULL, "", quoteScalarStyle);
            }

            DumperOptions.ScalarStyle defaultScalarStyle = getDefaultScalarStyle();
            setDefaultScalarStyle(quoteScalarStyle); // change default scalar style

            final Node node = SnakeYamlRepresenter.this.representData(value);

            setDefaultScalarStyle(defaultScalarStyle); // restore default scalar style

            return node;
        }

    }
}
