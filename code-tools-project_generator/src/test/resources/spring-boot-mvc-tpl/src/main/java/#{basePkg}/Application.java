package #{basePkg};

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * 程序入口
 * Created by #{author} on #{time}.
 */
@SpringBootApplication
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {
        logger.info("Application start ...");
        SpringApplication.run(Application.class, args);
        logger.info("Application start ... [SUCCESS]");
    }
}
