package streamingapi.client.helper;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper class for JSON manipulation
 */
public class JsonExtractorHelper {

    private JsonExtractorHelper() {

    }

    /**
     * Extract cursor JSON object from invalid message
     *
     * @param invalidMessage to be used
     * @return a valid JSON representing the cursor info
     */
    public static Optional<String> extractCursorInfoFrom(String invalidMessage) {

        final String regex = "\"cursor\"\\s*:\\s*(\\{.*?\\})";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(invalidMessage);

        if (matcher.find()) {
            return Optional.of(matcher.group(1));
        }
        return Optional.empty();
    }

}
