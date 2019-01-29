# Benchmarking Experiments On Simple Rest

This project has the only purpose of backing a post I wrote on my blog <http://antoniorodrigues.site/posts/2019/01/24/performance-comparision-for-rest.html>.
On each directory of the project,  there is a simple application that exposes a HTTP GET method on localhost:3000, that expects a query string parameter userName. Eg:
`GET http://localhost:3000/?userName=Antonio`

And each application, responds to this given request with the very same HTTP response.
```
HTTP/1.1 200 OK
transfer-encoding: chunked
Content-Type: text/plain; charset=utf-8
Connection: keep-alive

The request for user 'Antonio' was processed 
```

To make sure that all the applications send back the same response, I issued the following curl line `curl  http://localhost:3000/?userName=Antonio --trace-ascii -` to each application, and I verified that the response was always exactly this:
```
== Info:   Trying 127.0.0.1...
== Info: TCP_NODELAY set
== Info: Connected to localhost (127.0.0.1) port 3000 (#0)
=> Send header, 95 bytes (0x5f)
0000: GET /?userName=Antonio HTTP/1.1
0021: Host: localhost:3000
0037: User-Agent: curl/7.58.0
0050: Accept: */*
005d: 
<= Recv header, 17 bytes (0x11)
0000: HTTP/1.1 200 OK
<= Recv header, 41 bytes (0x29)
0000: Content-Type: text/plain; charset=utf-8
<= Recv header, 20 bytes (0x14)
0000: content-length: 44
<= Recv header, 24 bytes (0x18)
0000: Connection: keep-alive
<= Recv header, 2 bytes (0x2)
0000: 
<= Recv data, 44 bytes (0x2c)
0000: The request for user 'Antonio' was processed
== Info: Connection #0 to host localhost left intact
```
There may be some minor differences, like upper and lowercase or ordering of header items. But nothing that could affect performance. The most important is that the response of all applications has the exact same size in bytes.


