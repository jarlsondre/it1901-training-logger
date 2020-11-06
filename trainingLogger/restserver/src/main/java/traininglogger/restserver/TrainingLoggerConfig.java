package traininglogger.restserver;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import traininglogger.core.SessionLogger;
import traininglogger.json.TrainingLoggerPersistence;
import traininglogger.restapi.TrainingLoggerService;

public class TrainingLoggerConfig extends ResourceConfig {

  private SessionLogger sessionLogger;
  private static String backupFile = "server-sessionlogger.json";

  /**
   * Initialize this TrainingLoggerConfig.
   *
   * @param sessionLogger sessionLogger instance to serve
   */
  public TrainingLoggerConfig(SessionLogger sessionLogger) {
    setSessionLogger(sessionLogger);
    register(TrainingLoggerService.class);
    register(TrainingLoggerModuleObjectMapperProvider.class);
    register(JacksonFeature.class);
    register(new AbstractBinder() {
      @Override
      protected void configure() {
        bind(TrainingLoggerConfig.this.sessionLogger);
      }
    });
  }

  /**
   * Initialize this TrainingLoggerConfig with a default SessionLogger.
   */
  public TrainingLoggerConfig() {
    this(getInitialSessionLogger());
  }

  public SessionLogger getSessionLogger() {
    return this.sessionLogger;
  }

  public void setSessionLogger(SessionLogger sessionLogger) {
    this.sessionLogger = sessionLogger;
  }

  private static SessionLogger getInitialSessionLogger() {
    Reader reader = null;
    TrainingLoggerPersistence trainingLoggerPersistence = new TrainingLoggerPersistence();
    // let etter backup-data først:
    try {
      reader = new FileReader(Paths.get(System.getProperty("user.home"), backupFile).toFile(), StandardCharsets.UTF_8);
    } catch (IOException ioex) {
      System.err.println("Fant ingen " + backupFile + " på hjemmeområdet");
    }
    // Hvis det ikke funka, bruk medølgende eksempelfil:
    URL url = TrainingLoggerConfig.class.getResource("default-sessionlogger.json");
    if ((reader == null) && (url != null)) {
      try {
        reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8);
      } catch (IOException e) {
        System.out.println("Kunne ikke lese default-sessionlogger.json. (" + e + ")");
      }
    }

    if (reader != null) {
      SessionLogger initialSessionLogger;
      try {
        initialSessionLogger = trainingLoggerPersistence.readSessionLogger(reader);
        reader.close();
        return initialSessionLogger;
      } catch (IOException e) {
        System.out.println("Kunne ikke deserialisere fra Reader, så returnerer en helt ny SessionLogger (" + e + ")");
      }
    }
    return new SessionLogger();
  }
}