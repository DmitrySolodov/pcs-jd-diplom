import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Collection;

public class JsonFormatOutputConverter implements CollectionConversion<String, PageEntry> {
    @Override
    public String convert(Collection<PageEntry> collection) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        return gson.toJson(collection);
    }
}
