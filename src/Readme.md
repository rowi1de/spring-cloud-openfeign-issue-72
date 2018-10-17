# Spring Cloud Feign Issue 72
Example for https://github.com/spring-cloud/spring-cloud-openfeign/issues/72

## Run Local
- `docker pull rodolpheche/wiremock`
- `docker run -it --rm -p 8080:8080 rodolpheche/wiremock`

```bash
curl -X POST \
--data '{ "request": { "url": "/a", "method": "GET" }, "response": { "status": 500, "body": "A Error" }}' \
http://localhost:8080/__admin/mappings/new
```

```bash
curl -X POST \
--data '{ "request": { "url": "/b", "method": "GET" }, "response": { "status": 500, "body": "B Error" }}' \
http://localhost:8080/__admin/mappings/new
```
