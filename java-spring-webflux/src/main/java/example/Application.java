package example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableWebFlux
public class Application {

    private static final int PORT = 3000;

    public static void main(String[] args) throws Exception {
        System.setProperty("reactor.ipc.netty.workerCount", "1"); //Just one thread will process the incoming requests

        new SpringApplicationBuilder(Application.class)
                .properties("server.port=" + PORT)
                .run(args);

        System.out.println("The 'Java Spring Webflux' service is listening on port " + PORT);
    }

    @Bean
    public RouterFunction<ServerResponse> route() {
        return RouterFunctions
                .route(RequestPredicates.GET("/")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), this::handle);

    }

    public Mono<ServerResponse> handle(ServerRequest request) {
        String userName = request.queryParam("userName").orElse("");
        return ServerResponse.ok()
                .header("Content-Type", "text/plain; charset=utf-8")
                .header("Connection", "keep-alive")
                .body(BodyInserters.fromObject("The request for user '" + userName + "' was processed"));

    }
}