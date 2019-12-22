# springboot-rsocketsetup-example
An example of sending a `SETUP` payload with [RSocket](http://rsocket.io) and Spring Boot.

RSocket allows for data to be sent on connection setup. This is handy for one-time connection configuration tasks your 
application may need.

## Building the Example
Run the following command to build the example:

    ./gradlew clean build
    
## Running the Example
Follow the steps below to run the example:

1. Run the following command to start the `hello-service`:

        ./gradlew :hello-service:bootRun
        
2. In a new terminal, run the following command to execute the `hello-client` and ask for hello messages for `Bob`, `Susan`, `Mary`, and `Tom` in United States English:

        ./gradlew :hello-client:bootRun --args="en US Bob Susan Mary Tom"
        
    If successful, you will see the following in the terminal:
    
        : Connecting to hello-service and configuring locale [language: 'en', country: 'US']
        : Requesting '4' hello message(s)...
        : Response: Hello, Bob!
        : Response: Hello, Susan!
        : Response: Hello, Mary!
        : Response: Hello, Tom!
        : Completed!
        
3. Next, run the following command to execute the `hello-client` for French messages:

        ./gradlew :hello-client:bootRun --args="fr FR Bob Susan Mary Tom"
        
    If successful, you will see the following in the terminal:
    
        : Connecting to hello-service and configuring locale [language: 'fr', country: 'FR']
        : Requesting '4' hello message(s)...
        : Response: Bonjour, Bob!
        : Response: Bonjour, Susan!
        : Response: Bonjour, Mary!
        : Response: Bonjour, Tom!
        : Completed!

## Bugs and Feedback
For bugs, questions, and discussions please use the [Github Issues](https://github.com/gregwhitaker/springboot-rsocketsetup-example/issues).

## License
MIT License

Copyright (c) 2019 Greg Whitaker

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.