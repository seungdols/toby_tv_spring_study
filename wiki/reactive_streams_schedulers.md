# Toby TV Reative Streams (3) 

 ```java
 @Slf4j
public class SchedulerEx {

    //Reactive Streams
    public static void main(String[] args) {
        Publisher<Integer> pub = sub -> {
            sub.onSubscribe(new Subscription() {
                @Override
                public void request(long n) {
                    sub.onNext(1);
                    sub.onNext(2);
                    sub.onNext(3);
                    sub.onNext(4);
                    sub.onNext(5);
                    sub.onComplete();
                }

                @Override
                public void cancel() {

                }
            });
        };

        pub.subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                log.debug("onSubscribe()");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                log.debug("onNext: {}", integer);
            }

            @Override
            public void onError(Throwable t) {
                log.debug("onError: {}", t.getMessage());
            }

            @Override
            public void onComplete() {
                log.debug("onComplete()");
            }
        });


    }
}
 ```
 
 위 코드를 돌려보면, 실질적으로는 `main` 쓰레드에서 모두 동작하게 된다. 그렇게 되면, 코드가 절차적으로 실행이 된다. 
 
이렇게 동작하게 하는건 실질적으로 쓰는데 문제가 된다. 


그래서 해결책은, `publisher` 혹은 `subsciber`를 다른 쓰레드에서 동작하게끔 하는 것이다. 

![다이어그램](https://raw.githubusercontent.com/reactor/reactor-core/v3.1.3.RELEASE/src/docs/marble/flux.png)

![subscribeOn](https://raw.githubusercontent.com/reactor/reactor-core/v3.1.3.RELEASE/src/docs/marble/subscribeon.png)

메인 쓰레드에서 실행 하지 않고, 다른 쓰레드에서 생성하여 처리 할 수 있도록 한다.

```java
@Slf4j
public class SchedulerEx {

    //Reactive Streams
    public static void main(String[] args) {
        Publisher<Integer> pub = sub -> {
            sub.onSubscribe(new Subscription() {
                @Override
                public void request(long n) {
                    sub.onNext(1);
                    sub.onNext(2);
                    sub.onNext(3);
                    sub.onNext(4);
                    sub.onNext(5);
                    sub.onComplete();
                }

                @Override
                public void cancel() {

                }
            });
        };

        //SubscribeOn 구현
        Publisher<Integer> subOnPub = sub -> {
            ExecutorService es = Executors.newSingleThreadExecutor();
            es.execute(() -> pub.subscribe(sub));
        };

        subOnPub.subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                log.debug("onSubscribe()");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                log.debug("onNext: {}", integer);
            }

            @Override
            public void onError(Throwable t) {
                log.debug("onError: {}", t.getMessage());
            }

            @Override
            public void onComplete() {
                log.debug("onComplete()");
            }
        });

    }
}
```

```text
00:52:25.573 [pool-1-thread-1] DEBUG com.tistory.seungdols.generics.reactive3.SchedulerEx - onSubscribe()
00:52:25.577 [pool-1-thread-1] DEBUG com.tistory.seungdols.generics.reactive3.SchedulerEx - onNext: 1
00:52:25.578 [pool-1-thread-1] DEBUG com.tistory.seungdols.generics.reactive3.SchedulerEx - onNext: 2
00:52:25.579 [pool-1-thread-1] DEBUG com.tistory.seungdols.generics.reactive3.SchedulerEx - onNext: 3
00:52:25.579 [pool-1-thread-1] DEBUG com.tistory.seungdols.generics.reactive3.SchedulerEx - onNext: 4
00:52:25.579 [pool-1-thread-1] DEBUG com.tistory.seungdols.generics.reactive3.SchedulerEx - onNext: 5
00:52:25.579 [pool-1-thread-1] DEBUG com.tistory.seungdols.generics.reactive3.SchedulerEx - onComplete()
```

![publishOn](https://raw.githubusercontent.com/reactor/reactor-core/v3.1.3.RELEASE/src/docs/marble/publishon.png)

```java
@Slf4j
public class SchedulerEx {

    //Reactive Streams
    public static void main(String[] args) {
        Publisher<Integer> pub = sub -> {
            sub.onSubscribe(new Subscription() {
                @Override
                public void request(long n) {
                    log.debug("request()");
                    sub.onNext(1);
                    sub.onNext(2);
                    sub.onNext(3);
                    sub.onNext(4);
                    sub.onNext(5);
                    sub.onComplete();
                }

                @Override
                public void cancel() {

                }
            });
        };

        //SubscribeOn 구현
//        Publisher<Integer> subOnPub = sub -> {
//            ExecutorService es = Executors.newSingleThreadExecutor();
//            es.execute(() -> pub.subscribe(sub));
//        };

        Publisher pubOnPub = sub -> {
            pub.subscribe(new Subscriber<Integer>() {
                ExecutorService es = Executors.newSingleThreadExecutor();

                @Override
                public void onSubscribe(Subscription s) {
                    sub.onSubscribe(s);
                }

                @Override
                public void onNext(Integer integer) {
                    es.execute(() -> sub.onNext(integer));
                }

                @Override
                public void onError(Throwable t) {
                    es.execute(() -> sub.onError(t));
                }

                @Override
                public void onComplete() {
                    es.execute(() -> sub.onComplete());
                }
            });
        };

        pubOnPub.subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                log.debug("onSubscribe()");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                log.debug("onNext: {}", integer);
            }

            @Override
            public void onError(Throwable t) {
                log.debug("onError: {}", t.getMessage());
            }

            @Override
            public void onComplete() {
                log.debug("onComplete()");
            }
        });

        System.out.println("exit main");

    }
}
```

```bash
01:05:08.111 [main] DEBUG com.tistory.seungdols.generics.reactive3.SchedulerEx - onSubscribe()
01:05:08.121 [main] DEBUG com.tistory.seungdols.generics.reactive3.SchedulerEx - request()
exit main
01:05:08.124 [pool-1-thread-1] DEBUG com.tistory.seungdols.generics.reactive3.SchedulerEx - onNext: 1
01:05:08.126 [pool-1-thread-1] DEBUG com.tistory.seungdols.generics.reactive3.SchedulerEx - onNext: 2
01:05:08.126 [pool-1-thread-1] DEBUG com.tistory.seungdols.generics.reactive3.SchedulerEx - onNext: 3
01:05:08.127 [pool-1-thread-1] DEBUG com.tistory.seungdols.generics.reactive3.SchedulerEx - onNext: 4
01:05:08.127 [pool-1-thread-1] DEBUG com.tistory.seungdols.generics.reactive3.SchedulerEx - onNext: 5
01:05:08.127 [pool-1-thread-1] DEBUG com.tistory.seungdols.generics.reactive3.SchedulerEx - onComplete()
```

이렇게 구현한 방식이 `publishOn`에 대한 구현이고, 소비가 느릴 경우에 해당하는 경우이다. 

요청이 동시에 날라와도, 쓰레드 자체가 싱글로 하나여서 나머지 요청들은 큐에 들어가고 대기상태로 존재한다. 

```java
@Slf4j
public class SchedulerEx {

    //Reactive Streams
    public static void main(String[] args) {
        Publisher<Integer> pub = sub -> {
            sub.onSubscribe(new Subscription() {
                @Override
                public void request(long n) {
                    log.debug("request()");
                    sub.onNext(1);
                    sub.onNext(2);
                    sub.onNext(3);
                    sub.onNext(4);
                    sub.onNext(5);
                    sub.onComplete();
                }

                @Override
                public void cancel() {

                }
            });
        };

        //SubscribeOn 구현
        Publisher<Integer> subOnPub = sub -> {
            ExecutorService es = Executors.newSingleThreadExecutor();
            es.execute(() -> pub.subscribe(sub));
        };

        Publisher pubOnPub = sub -> {
            subOnPub.subscribe(new Subscriber<Integer>() {
                ExecutorService es = Executors.newSingleThreadExecutor();

                @Override
                public void onSubscribe(Subscription s) {
                    sub.onSubscribe(s);
                }

                @Override
                public void onNext(Integer integer) {
                    es.execute(() -> sub.onNext(integer));
                }

                @Override
                public void onError(Throwable t) {
                    es.execute(() -> sub.onError(t));
                }

                @Override
                public void onComplete() {
                    es.execute(() -> sub.onComplete());
                }
            });
        };

        pubOnPub.subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                log.debug("onSubscribe()");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                log.debug("onNext: {}", integer);
            }

            @Override
            public void onError(Throwable t) {
                log.debug("onError: {}", t.getMessage());
            }

            @Override
            public void onComplete() {
                log.debug("onComplete()");
            }
        });

        System.out.println("exit main");

    }
}
```

위처럼 하게 되면, `publishOn`, `subscribeOn` 둘 다 별도의 쓰레드에서 동작 하게 된다. 

```bash
exit main
18:04:06.785 [pool-2-thread-1] DEBUG com.tistory.seungdols.generics.reactive3.SchedulerEx - onSubscribe()
18:04:06.788 [pool-2-thread-1] DEBUG com.tistory.seungdols.generics.reactive3.SchedulerEx - request()
18:04:06.789 [pool-1-thread-1] DEBUG com.tistory.seungdols.generics.reactive3.SchedulerEx - onNext: 1
18:04:06.791 [pool-1-thread-1] DEBUG com.tistory.seungdols.generics.reactive3.SchedulerEx - onNext: 2
18:04:06.791 [pool-1-thread-1] DEBUG com.tistory.seungdols.generics.reactive3.SchedulerEx - onNext: 3
18:04:06.791 [pool-1-thread-1] DEBUG com.tistory.seungdols.generics.reactive3.SchedulerEx - onNext: 4
18:04:06.791 [pool-1-thread-1] DEBUG com.tistory.seungdols.generics.reactive3.SchedulerEx - onNext: 5
18:04:06.791 [pool-1-thread-1] DEBUG com.tistory.seungdols.generics.reactive3.SchedulerEx - onComplete()
```

결과에서 확인 할 수 있다. 


### Flux 사용한 예제

```java
public class FluxScEx {

    public static void main(String[] args) {
        Flux.range(1, 10)
            .publishOn(Schedulers.newSingle("pub"))
            .log()
            .subscribeOn(Schedulers.newSingle("sub"))
            .subscribe(System.out::println);

        System.out.println("EXIT");
    }
}
```

---

```java
@Slf4j
public class SchedulerEx {

    //Reactive Streams
    public static void main(String[] args) {
        Publisher<Integer> pub = sub -> {
            sub.onSubscribe(new Subscription() {
                @Override
                public void request(long n) {
                    log.debug("request()");
                    sub.onNext(1);
                    sub.onNext(2);
                    sub.onNext(3);
                    sub.onNext(4);
                    sub.onNext(5);
                    sub.onComplete();
                }

                @Override
                public void cancel() {

                }
            });
        };

        //SubscribeOn 구현
        Publisher<Integer> subOnPub = sub -> {
            ExecutorService es = Executors.newSingleThreadExecutor(new CustomizableThreadFactory() {
                @Override
                public String getThreadNamePrefix() {
                    return "subOn - ";
                }
            });
//            es.execute(() -> pub.subscribe(sub));
            es.execute(() -> {
                pub.subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        sub.onSubscribe(s);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        es.execute(() -> sub.onNext(integer));
                    }

                    @Override
                    public void onError(Throwable t) {
                        es.execute(() -> sub.onError(t));
                        es.shutdown();
                    }

                    @Override
                    public void onComplete() {
                        es.execute(() -> sub.onComplete());
                        es.shutdown();
                    }
                });
            });
        };

        Publisher pubOnPub = sub -> {
            subOnPub.subscribe(new Subscriber<Integer>() {
                ExecutorService es = Executors.newSingleThreadExecutor(new CustomizableThreadFactory() {
                    @Override
                    public String getThreadNamePrefix() {
                        return "pubOn - ";
                    }
                });

                @Override
                public void onSubscribe(Subscription s) {
                    sub.onSubscribe(s);
                }

                @Override
                public void onNext(Integer integer) {
                    es.execute(() -> sub.onNext(integer));
                }

                @Override
                public void onError(Throwable t) {
                    es.execute(() -> sub.onError(t));
                    es.shutdown();
                }

                @Override
                public void onComplete() {
                    es.execute(() -> sub.onComplete());
                    es.shutdown();
                }
            });
        };

        pubOnPub.subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                log.debug("onSubscribe()");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                log.debug("onNext: {}", integer);
            }

            @Override
            public void onError(Throwable t) {
                log.debug("onError: {}", t.getMessage());
            }

            @Override
            public void onComplete() {
                log.debug("onComplete()");
            }
        });

        System.out.println("exit main");

    }
}
```

이렇게 쓰레드를 생성할 경우 쓰레드 종료가 안되는데, 정상적인 경우와 오류 상황에서 종료 할 수 있도록, `onError()`, `onComplete()` 내에서 쓰레드를 종료시키는 시그널을 보내는 메소드를 호출해주면 된다. 

```java
        Flux.interval(Duration.ofMillis(500))
            .take(10)
            .log()
            .subscribe(System.out::println);

        TimeUnit.SECONDS.sleep(10);
        System.out.println("EXIT");
```

`take`라는 메소드는 몇개만 가져와서 처리 할 때, 사용하는데 이러한 기능도 구현해볼 수 있다. 


```java
@Slf4j
public class IntervalEx {

    public static void main(String[] args) {
        Publisher<Integer> pub = sub -> {
            sub.onSubscribe(new Subscription() {
                int no = 0;
                boolean cancelled = false;

                @Override
                public void request(long n) {
                    ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
                    exec.scheduleAtFixedRate(() -> {
                        if (cancelled) {
                            exec.shutdown();
                            return;
                        }
                        sub.onNext(no++);
                    }, 0, 500, TimeUnit.MILLISECONDS);
                }

                @Override
                public void cancel() {
                    cancelled = true;
                    log.debug("cancel()");
                }
            });
        };

        Publisher<Integer> takePub = sub -> {
            pub.subscribe(new Subscriber<Integer>() {
                int count = 0;
                Subscription subsc;

                @Override
                public void onSubscribe(Subscription s) {
                    subsc = s;
                    sub.onSubscribe(s);
                }

                @Override
                public void onNext(Integer integer) {
                    sub.onNext(integer);
                    if (++count > 5) {
                        subsc.cancel();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    sub.onError(t);
                }

                @Override
                public void onComplete() {
                    sub.onComplete();
                }
            });
        };

        takePub.subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                log.debug("onSubscribe()");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                log.debug("onNext: {}", integer);
            }

            @Override
            public void onError(Throwable t) {
                log.debug("onError: {}", t.getMessage());
            }

            @Override
            public void onComplete() {
                log.debug("onComplete()");
            }
        });
    }

}
```
