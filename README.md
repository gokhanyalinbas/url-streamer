# url-streamer

This a springboot project. After mvn clean-install command, you can run and test the application with this link:http://localhost:8080/swagger-ui.html


I have created a repository interface for storing data in order and uniq.
In util service , you can validate your url is valid or not. And also you can make a rest call for the input urls.
In the servis layer, i have a interface and you can process urls here. The service validates the url and if is valid, gets the response from url and store it in repository.
In the presentaion layer, i have a controller and it receives the input and turn back application's response.
If thre is any exception, exception handler servis can handle this error and creates a meaningfull response.


In the project description, parameter size not specified. I have limited this size with 10. You can send max 10 u parameter. Because if you send parameter more than 10 the applicaiton can be timeout. That's why i have limited this valur. 10 number is symbolic number. Of course it can be 20 or 30, it depends your application server performance.

Maybe we can use cache mechanism for urls. If url call fail, we can read from cache the latest value. But i didn't implement it. Because i can not be sure about url's response always same or not. 

We can also implement multi-threading approach in this project. If parametter size increase we can create a thread pool and process urls with threads.


