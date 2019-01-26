package verticles;

import io.vertx.core.*;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

import java.lang.management.ManagementFactory;

public class Application extends AbstractVerticle {


    public static final int PORT = 3000;

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Application());
        System.out.println("The 'Java Vert.X' service of PID["+getPID()+"] is listening on port " + PORT );
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


    private static String getPID(){
        String pid = "";
        try {
            // This is a brittle way of getting the PID.
            // If you are running on Java 9, use 'ProcessHandle.current().pid()' instead.
            String pidName = ManagementFactory.getRuntimeMXBean().getName();
            pid = pidName.split("@")[0];
        }catch (Exception e){
            // ¯\_(ツ)_/¯
        }
        return pid;
    }
}
