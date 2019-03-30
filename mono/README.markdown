# Mono 

* Mono에 데이터를 담을 떄, `New`연산자로 만들지 않아도 된다.
 * `Mono.just()`함수를 사용하면 된다.
 
 
```java
@GetMapping("/")
	Mono<String> hello() {
		return Mono.just("Hello WebFlux").log();
	}
```
```bash
2019-03-30 16:58:54.435  INFO 54886 --- [ctor-http-nio-2] reactor.Mono.Just.1                      : | onSubscribe([Synchronous Fuseable] Operators.ScalarSubscription)
2019-03-30 16:58:54.438  INFO 54886 --- [ctor-http-nio-2] reactor.Mono.Just.1                      : | request(unbounded)
2019-03-30 16:58:54.438  INFO 54886 --- [ctor-http-nio-2] reactor.Mono.Just.1                      : | onNext(Hello WebFlux)
2019-03-30 16:58:54.441  INFO 54886 --- [ctor-http-nio-2] reactor.Mono.Just.1                      : | cancel()
```

```java
	@GetMapping("/")
	Mono<String> hello() {
	    log.info("pos1");
		Mono<String> hello_webFlux = Mono.fromSupplier(() -> generateHello()).doOnNext(c -> log.info(c)).log();
		hello_webFlux.subscribe();
		log.info("pos2");
		return hello_webFlux;
	}
```

위 처럼 해도 정상적으로 동작한다. 즉, 하나 이상의 subscriber가 요청을 할 때마다 Publisher가 처음부터 가지고 있던 데이터를 다시 보내준다. 
똑같이 보내준다. (Replay한다고 생각하면 된다.)

위와 같은 것을 Cold Source라고 한다. 

외부에서 데이터가 오는 경우, 외부에 대한 이벤트에 대한 데이터는 Hot Source라고 한다. 그럴 경우 위와 동작이 다르다. 