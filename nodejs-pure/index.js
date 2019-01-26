const http = require('http');
const url = require('url');
const querystring = require('querystring');
const process = require('process');
const PORT = 3000;

const server = http.createServer(
    (req, resp) => {
        const queryParams = extractQueryParameters(req);
        const userName = queryParams.userName;
        const response = "The request for user '" + userName + "' was processed";
        setDefaultHeadersOn(resp, response.length);
        resp.end(response);
    }
);


function setDefaultHeadersOn(resp, contentLength) {
    resp.removeHeader('Date');
    resp.writeHead(200, {'Content-Type': 'text/plain; charset=utf-8', 'content-length': contentLength});
}

function extractQueryParameters(req) {
    const parsedUrl = url.parse(req.url);
    const urlQueryString = parsedUrl.query;
    const queryString = querystring.parse(urlQueryString);
    return queryString;
}

server.listen(PORT);
console.log("The 'NodeJS Pure' service of PID["+process.pid+"] is listening on port " + PORT);


