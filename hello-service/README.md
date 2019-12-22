# hello-service
Service that generates hello messages.

## API
The `hello-service` exposes the following endpoints:

### hello
Endpoint that returns one-to-many hello messages.

- Method: `hello`
- Arguments:

    * `HelloRequest` - JSON document containing the list of names for which to generate messages.

