package com.example.demo;

import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.tcp.TcpClient;

import java.net.InetSocketAddress;


public class ClientApplication {

	public static void main(String[] args) throws InterruptedException {
		Connection connection =
				TcpClient.create()
						.remoteAddress(() -> new InetSocketAddress("localhost", 8080))
						.connectNow();

		connection.outbound()
				.sendString(Mono.just("POST http://localhost:8080/ HTTP/1.1\r\nHost: localhost:8080\r\n" +
						"Content-Type: text/plain\r\nContent-Length: 10\r\n\r\nhello"))
				.then()
				.subscribe();

		Thread.sleep(1000);

		connection.disposeNow();
	}
}
