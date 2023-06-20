package streamingapi.client.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Custom configuration for POJO mapper to JSON and vice-versa.
 */
public class EventModelObjectMapper extends ObjectMapper {

    private static final long serialVersionUID = -899875794153750345L;

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public EventModelObjectMapper() {

        super();

        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        setPropertyNamingStrategy(PropertyNamingStrategy.SnakeCaseStrategy.SNAKE_CASE);
        setDateFormat(dateFormat);
        setSerializationInclusion(NON_NULL);
        registerModule(new JavaTimeModule());
    }

    public <T> String serialize(T pojo) {

        try {
            return this.writeValueAsString(pojo);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException(
                    "Failed to generate JSON string from an " + pojo.getClass().getName() +
                            " object: " + ex.getMessage());
        }
    }

    public <T> T deserialize(String content, Class<T> typeClass) {

        try {
            return this.readValue(content, typeClass);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException(
                    "Failed to create " + typeClass.getName() + " object from JSON: " + ex.getMessage());
        }
    }
}
