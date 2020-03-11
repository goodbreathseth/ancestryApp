package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DefaultHandler implements HttpHandler {
    /**
     * Handle the given request and generate an appropriate response.
     * See {@link HttpExchange} for a description of the steps
     * involved in handling an exchange.
     *
     * @param exchange the exchange containing the request from the
     *                 client and used to send the response
     * @throws NullPointerException if exchange is <code>null</code>
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        String FILE_ROOT_DIR = "web";

        try {
            //Get the request. If it is a "get" request, do the following
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                //Create a url string to where the index.html web file is located.
                //urlPath will be everything after "/web"
                String urlPath = exchange.getRequestURI().getPath();
                String filepath;

                //How to evaluate handle either: blank request, index.html request, or .css requests
                if (urlPath.equals("/"))
                    filepath = FILE_ROOT_DIR + urlPath + "index.html";
                else
                    filepath = FILE_ROOT_DIR + urlPath;

                //Using that string, create a file
                File file = new File(filepath);
                if (file.exists() && file.canRead()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream responseBody = exchange.getResponseBody();

                    //Copy and write files to an outputstream
                    Files.copy(Paths.get(file.getPath()), responseBody);
                    responseBody.close();
                    success = true;
                }
            }
            if (!success) {
                // The HTTP request was invalid somehow, so we return a "bad request"
                // status code to the client.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                // Since the client request was invalid, they will not receive the
                // list of games, so we close the response body output stream,
                // indicating that the response is complete.
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            // Some kind of internal error has occurred inside the server (not the
            // client's fault), so we return an "internal server error" status code
            // to the client.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            // Since the server is unable to complete the request, the client will
            // not receive the list of games, so we close the response body output stream,
            // indicating that the response is complete.
            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        }
    }
}