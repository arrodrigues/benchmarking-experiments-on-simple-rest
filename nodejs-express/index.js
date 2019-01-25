const express = require('express');
const app = express();
const PORT = 3000;
app.disable('x-powered-by');
app.disable('etag');

app.get("/", (req, resp) => {
    resp.sendDate = false;
    resp.setHeader('Content-Type', 'text/plain; charset=utf-8');
    resp.write('');
    resp.end("The request for user '" + req.query['userName'] + "' was processed");
});

app.listen(PORT);

console.log("The 'NodeJS Express' service is listening on port " + PORT);