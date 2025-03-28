Echo server utilizing Java 21's lightweight virtual threading. Run server with
```
gradlew run
```

One simple way to test the server is with `netcat`:
```
echo helllooo | nc localhost 8080
```