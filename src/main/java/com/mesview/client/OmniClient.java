package com.mesview.client;

import org.atmosphere.wasync.*;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Created by hector on 16/02/2016.
 */
public class OmniClient {
    public static void main(String[] args) {
        try {

            // =========
            // Client 1
            // =========

            Client client = ClientFactory.getDefault().newClient();

            RequestBuilder request = client.newRequestBuilder()
                    .method(Request.METHOD.GET)
                    .uri("http://localhost:8080/ramones/suscripcion")
                    .encoder(new Encoder<String, Reader>() {        // Stream the request body
                        @Override
                        public Reader encode(String s) {
                            System.out.println("Encode: " + s);
                            return new StringReader(s);
                        }
                    })
                    .decoder(new Decoder<String, Reader>() {
                        @Override
                        public Reader decode(Event type, String s) {
                            if(type.equals(Event.MESSAGE))
                                System.out.println("---- Mensaje recibido ----");
                            System.out.println("Decode: " + s);
                            return new StringReader(s);
                        }
                    })
                    .transport(Request.TRANSPORT.WEBSOCKET)                        // Try WebSocket
                    .transport(Request.TRANSPORT.LONG_POLLING);                    // Fallback to Long-Polling

            Socket socket = client.create();
            socket.on("message", new Function<String>() {
                @Override
                public void on(String s) {
                    System.out.println("Respuesta: " + s);
                }
            }).on(new Function<IOException>() {

                @Override
                public void on(IOException e) {
                    e.printStackTrace();
                }
            }).open(request.build());

            // =========
            // Client 2
            // =========

            Client client2 = ClientFactory.getDefault().newClient();

            RequestBuilder request2 = client2.newRequestBuilder()
                    .method(Request.METHOD.GET)
                    .uri("http://localhost:8080/metallica/suscripcion")
                    .encoder(new Encoder<String, Reader>() {        // Stream the request body
                        @Override
                        public Reader encode(String s) {
                            System.out.println("Encode: " + s);
                            return new StringReader(s);
                        }
                    })
                    .decoder(new Decoder<String, Reader>() {
                        @Override
                        public Reader decode(Event type, String s) {
                            if(type.equals(Event.MESSAGE))
                                System.out.println("---- Mensaje recibido ----");
                            System.out.println("Decode: " + s);
                            return new StringReader(s);
                        }
                    })
                    .transport(Request.TRANSPORT.WEBSOCKET)                        // Try WebSocket
                    .transport(Request.TRANSPORT.LONG_POLLING);                    // Fallback to Long-Polling

            Socket socket2 = client2.create();
            socket2.on("message", new Function<String>() {
                @Override
                public void on(String s) {
                    System.out.println("Respuesta: " + s);
                }
            }).on(new Function<IOException>() {

                @Override
                public void on(IOException e) {
                    e.printStackTrace();
                }
            }).open(request2.build());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
