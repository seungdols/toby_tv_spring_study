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
