package verticles;

import io.vertx.core.*;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

public class Application extends AbstractVerticle {


    public static final int PORT = 3000;

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Application());
        System.out.println("The 'Java Vert.X' service is listening on port " + PORT);
    }


    @Override
    public void start(Future<Void> fut) {
        Router router = Router.router(vertx);

        router.route(HttpMethod.GET,"/").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            String userName = routingContext.request().getParam("userName");
            response
                    .putHeader("Content-type", "text/plain; charset=utf-8")
                    .putHeader("Connection", "keep-alive")
                    .setChunked(false)
                    .end("The request for user '" + userName + "' was processed");
        });

        vertx
                .createHttpServer()
                .requestHandler(router::accept)
                .listen(
                        config().getInteger("http.port", PORT),
                        result -> {
                            if (result.succeeded()) {
                                fut.complete();
                            } else {
                                fut.fail(result.cause());
                            }
                        }
                );
    }
}
