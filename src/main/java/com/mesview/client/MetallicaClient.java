package com.mesview.client;


import org.atmosphere.wasync.*;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Calendar;


public class MetallicaClient {

    public static void main(String[] args) {
        try {

            Client client = ClientFactory.getDefault().newClient();

            RequestBuilder request = client.newRequestBuilder()
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
                            if (type.equals(Event.MESSAGE)) {
                                System.out.println("---- Message received ----");
                                System.out.println("---- Date and time: " + Calendar.getInstance().getTime().toString() + " ----");
                            }
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}