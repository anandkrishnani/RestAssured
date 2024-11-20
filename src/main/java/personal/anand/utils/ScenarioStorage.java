package personal.anand.utils;

import com.google.inject.Singleton;

import java.util.HashMap;
import java.util.Map;
@Singleton
public class ScenarioStorage {
    Map<String, String> MAP = new HashMap<>();

    public void put(String key, String value) {
        MAP.put(key, value);
    }

    public String get(String key) {
        return MAP.get(key);
    }

}
