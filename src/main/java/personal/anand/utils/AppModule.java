package personal.anand.utils;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class AppModule extends AbstractModule {
@Override
    protected void configure() {
        bind(ScenarioStorage.class).in(Singleton.class);
    }
}
