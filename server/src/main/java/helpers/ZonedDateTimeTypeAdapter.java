package helpers;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeTypeAdapter implements JsonSerializer<ZonedDateTime>, JsonDeserializer<ZonedDateTime> {
    /**
     *
     *
     * @author frizyyu
     * @version 1.0
     */

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu-kk`mm`ss");

    @Override
    public JsonElement serialize(ZonedDateTime zonedDateTime, Type srcType,
                                 JsonSerializationContext context) {

        return new JsonPrimitive(formatter.format(zonedDateTime));
    }

    @Override
    public ZonedDateTime deserialize(JsonElement json, Type typeOfT,
                                     JsonDeserializationContext context) throws JsonParseException {
        LocalDateTime date = LocalDateTime.parse(json.getAsString(), formatter);
        ZonedDateTime res = date.atZone(ZoneId.systemDefault());
        return res;
    }
}