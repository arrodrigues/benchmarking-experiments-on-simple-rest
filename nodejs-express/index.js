const express = require('express');
const process = require('process');
const app = express();
const PORT = 3000;
app.disable('x-powered-by');
app.disable('etag');

app.get("/", (req, resp) => {
    const responseContent = "The request for user '" + req.query['userName'] + "' was processed";
    resp.sendDate = false;
    resp.setHeader('Content-Type', 'text/plain; charset=utf-8');
    resp.setHeader('content-length', responseContent.length);
    resp.write('');
    resp.end(responseContent);
});

app.listen(PORT);

console.log("The 'NodeJS Express' service of PID["+process.pid+"] is listening on port " + PORT);