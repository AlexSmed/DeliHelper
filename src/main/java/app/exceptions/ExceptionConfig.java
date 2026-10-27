package app.exceptions;

import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionConfig {
    private static final Logger logger =
            LoggerFactory.getLogger(ExceptionConfig.class);

    public static void configure(Javalin app) {
        app.exception(ApiException.class, (e, ctx) -> {
            logger.warn("API error (code={}): {}",
                    e.getCode(), e.getMessage());

            ctx.status(e.getCode());
            ctx.json(new ErrorResponse(e.getMessage()));
        });

        app.exception(Exception.class, (e, ctx) -> {
            logger.error("Unexpected server error", e);

            ctx.status(500);
            ctx.json(new ErrorResponse("Internal server error"));
        });
    }

    public record ErrorResponse(String message) {}
}
