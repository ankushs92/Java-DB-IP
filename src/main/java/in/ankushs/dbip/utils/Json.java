package in.ankushs.dbip.utils;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


/**
 * Helper class to serialize/deserialize POJOs/Maps to JSON and vice versa
 * Created by Ankush on 17/07/17.
 */
public class Json {

    //Reasons for static initialization, check out this link:
    //https://stackoverflow.com/questions/18611565/how-do-i-correctly-reuse-jackson-objectmapper
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static{
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * Convert JSON to POJO
     * @param json The JSON to deserialize to a POJO
     * @param clazz The Class that the JSON will be deserialized to a POJO
     * @return An instance of class T
     * @throws Exception
     */
    public static <T> T toObject(final String json, final Class<T> clazz) {
        try {
            return objectMapper
                    .readValue(json, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Serialize a POJO/Map to a pretty JSON String
     * @param object The Object to convert to a pretty JSON string
     * @return The JSON representation of @param object
     * @throws Exception
     */
    public static String encodePretty(final Object object) throws Exception {
        return objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(object);
    }

    /**
     * Serialize a POJO/Map to JSON String
     * @param object The Object to convert to a JSON string
     * @return The JSON representation of @param object
     * @throws Exception
     */
    public static String encode(final Object object) {
        try {
            return objectMapper
                    .writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
