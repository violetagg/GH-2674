package com.example.demo;

import org.slf4j.LoggerFactory;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class MyController {

	@PostMapping(value = "/", consumes = {"text/plain"})
	public Flux<DataBuffer> test(ServerWebExchange exchange) {
		return Flux.defer(() -> exchange.getRequest().getBody()
				.delayElements(Duration.ofMinutes(5))
				.doOnError(t -> LoggerFactory.getLogger(MyController.class).error("Error received", t)));
	}
}
