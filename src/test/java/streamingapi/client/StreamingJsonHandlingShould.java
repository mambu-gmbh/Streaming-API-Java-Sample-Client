package streamingapi.client;

import streamingapi.client.helper.EventModelObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import streamingapi.client.model.Batch;
import streamingapi.client.model.Cursor;
import streamingapi.client.model.Event;
import streamingapi.client.model.Metadata;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 */
public class StreamingJsonHandlingShould {

    private EventModelObjectMapper eventModelObjectMapper;

    @BeforeEach
    public void setup() {

        eventModelObjectMapper = new EventModelObjectMapper();
    }

    @Test
    public void validateThatBatchObjectCanBeConstructedFromJson() {

        final String validMessage = "{\"cursor\":{\"partition\":\"2\",\"offset\":\"001-0001-000000000000000388\",\"event_type\":\"mrn.event.demotenant.streamingapi.savings_deposit\",\"cursor_token\":\"750e05a7-f78a-42d6-acd1-c5bbaef25ba8\"},\"events\":[{\"metadata\":{\"eid\":\"d0c69543-e5c3-4180-9ec4-733103694c26\",\"occurred_at\":\"2023-05-10T10:49:36.599Z\",\"content_type\":\"application/json; charset=UTF-8\",\"category\":\"DATA\",\"event_type\":\"mrn.event.demotenant.streamingapi.savings_deposit\"},\"body\": \"{key:value}\",\"template_name\":\"Alex-Test3\"}],\"info\":{\"debug\":\"Stream started\"}}";

        final Batch validObject = eventModelObjectMapper.deserialize(validMessage, Batch.class);

        assertNotNull(validObject);
        assertEquals(1, validObject.getEvents().size());
        final Cursor cursor = validObject.getCursor();
        assertEquals("750e05a7-f78a-42d6-acd1-c5bbaef25ba8", cursor.getCursorToken());
        assertEquals("001-0001-000000000000000388", cursor.getOffset());
        assertEquals("2", cursor.getPartition());
        assertEquals("mrn.event.demotenant.streamingapi.savings_deposit", cursor.getEventType());

        assertEquals(1, validObject.getEvents().size());
        final Event actualEvent = validObject.getEvents().get(0);
        assertNotNull(actualEvent);
        assertEquals("{key:value}", actualEvent.getBody());
        assertEquals("Alex-Test3", actualEvent.getTemplateName());

        final Metadata metadata = actualEvent.getMetadata();
        assertNotNull(metadata);
        assertEquals("mrn.event.demotenant.streamingapi.savings_deposit", metadata.getEventType());
        assertEquals("DATA", metadata.getCategory());
        assertEquals("d0c69543-e5c3-4180-9ec4-733103694c26", metadata.getEid());
        assertEquals("application/json; charset=UTF-8", metadata.getContentType());

    }

    @Test
    public void extractCursorInfoFromInvalidJson() {

        final String invalidMessage = "{\"cursor\":{\"partition\":\"1\",\"offset\":\"001-0001-000000000000035653\",\"event_type\":\"mrn.event.obkesp.streamingapi.cards_authorisation_hold_reversed\",\"cursor_token\":\"cef97ac7-3b5b-465f-b780-2b6cfa509111\"},\"events\":[SJTJGEECAFWNJJBPSUVAVEYPSOKTOFCVMHLRKREDEUOCJZMVHHGOZTAHGLNJGNIAZSFQNVCZUPRIAEYTXVWLKIZPSVNLOQRZIQEKBNHVXIJAHVAHRSAXMMVGSHIDOFQTYZQESAOLAFVZSRUOZUXTRLOLISQCMWRDRZNUGXPKECERDFVZWHGSFIHEPMUEBSEDTKJYVHHTIWYVCQEXUZUPTXPQZYATUHDVREXBTKOECVXESADSJJMUZYVKWARGZRDBWYWBNFGUQTIGRIKVIPHYFZHWBYNVXGHMBOEQCTSOTOJRXJEVWTNQXCRLBAEOYOCEOAZEYXZCZUFNDCGGKBNEBSHYJLBJGAYPILUIKOQJBCRAJCZZBNFYEPDVCDCJFSJSFYZQWPXUXWKVCEBFCCRIWQTPZDMWUAHLGREGFZATEADJKKASSCAQKIAXJWMUJCUUDJQZJSOBMUHADVDZJVQVLNUXBHEYVRKDBCGJRDORSERFJQXPHPKLLEASUVYKWXWHNOIWFLLWPQKLOKUFPOFNLJPDOAMXODAPKUARVIDWGZPTUGGHAIQPIWTPAHFJIUTEXGXARRCWCPILDGNBDUWDSLPYSEIMWFEYNPXNTXZBIKUZZPFBMOQQZGRTRHDYTTXYNGLPVLNBVWAXJIUQKAWSFKRPJXUBBKJHGSYMPUCNWTHSNAFKCYSFKTRQZGZCCTCLZQHQBLUFAULGUCHLFCOBTGVXQPRFCQESXQFJTZIMULKHOUKIZHKOJCJPPBPVERZVRDHYZKDKBCWGNFCYALCNQQMTFQTGGSPCDHNQTXFHLXHXASBCXSWTHBJHVUIAHCKFIYNASZZRNYKKJSLBRCLMZRXNCQCYKFFWKSFKUVELDAFGVUCDFZPQQFWIVGVOJPJLILFSGAGUAJZTRZFUCEOBNOWDRAIYJFXKSIBUCYKJCNQVXBIRTVRPURHHHYLKPEFWZHPFJWKWUWSLTZIBJOINORABYUXWVARCOYIUXPEOBWIFFUIHEHJZHKYBJEADXCWM],\"info\":{\"debug\":\"Stream started\"}}";

        final String regex = "\"cursor\"\\s*:\\s*(\\{.*?\\})";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(invalidMessage);

        assertTrue(matcher.find());
        final String cursorInfo = matcher.group(1);
        final Cursor actualCursor = eventModelObjectMapper.deserialize(cursorInfo, Cursor.class);

        assertNotNull(actualCursor);
        assertEquals("cef97ac7-3b5b-465f-b780-2b6cfa509111", actualCursor.getCursorToken());
        assertEquals("001-0001-000000000000035653", actualCursor.getOffset());
        assertEquals("1", actualCursor.getPartition());
        assertEquals("mrn.event.obkesp.streamingapi.cards_authorisation_hold_reversed", actualCursor.getEventType());

    }
}
