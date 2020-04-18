# Basic usage

(Temporary documentation while API is subject to such large changes)

---

* Let's also create a simple base payload that implements the Payload interface.
* Note: the payload **must** have an empty constructor (*due to how Genson parses JSON*), but you
can use a non-empty constructor if you need to.
```java
public class TestPayload implements Payload {
    public String name;

    public TestPayload(String name) {
        this.name = name;
    }

    public TestPayload() {

    }
}
```

---

* Now we can register a simple server module.
* Within the module, we also register the
```java
public class SimpleModule implements ServerModule {

    public void onInit(PgsfServer server) {
        server.router.register(new RegisteredHandler<TestPayload>(TestPayload.class, tp -> {
            System.out.println("TestPayload received: " + tp.payload.name);
        }));
    }

    public void onDestroy() {
    }
}
```

---

* We will now initialize the PgsfServer on a suitable port, and register the module we just created.
```java
    PgsfServer server = new PgsfServer(8084);

    server.register(new SimpleModule());
    server.start();
```

---

* Incoming messages should be formatted with a @class tag as the first element
so the JSON parser knows which payload is being used.

* If you send `{"@class":"payloads.TestPayload","name":"test payload"}` to the server,
you should hopefully get a printout on the server.