//defines where this class belongs to 
package main.java.com.example.weatherapi.service;

//imports filter chain which lets the request continue to the controller 
import jakarta.servlet.FilterChain; 

//imports the exception type used by servlet filters
import jakrata.servlet.ServletException; 

//imports the http request object
import jakarta.servlet.http.HttpServeletRequest; 

//imports the resonse object 
import jakarta.servlet.http.HttpServletResponse; 

//imports the spring annotation that reisters this class as a spring component
import org.springframework.stereotype.Component; 

//imports spring base class for fileters that run once p[er request 
import org.springframework.web.filter.OncePerRequestFilter; 

//imports ioexception which can happen while wiritng the respnse 
import java.io.Exception; 

//imports instant so we can get the current unix timestamp
import java.time.Instant; 

//imports map for storing request counts by ip address
import java.util.Map; 

//import concurrenthaspmap for thread-safe request tracking
import java.util.concurrent.ConcurrentHashMap; 

//tells spring to create and resiter this filer automatically 
@Component
public class RateLimitFilter extends OncePerRequestFilter{
    //store request count info for each ip address
    private final Map<String, RequestInfo> requestCounts = new ConcurrentHashMap(); 

    //sets the max number of request allowed per window 
    private static final int LIMIT = 20; 

    //sets teh size of the rate-limit window in seconds
    private static final long WINDOW_SECONDS = 60; 

    //this functions runs once for every http request 
    @Override 
    protected void doFilterInternal(
        //incoming http request 
        HttpServletRequest request, 
        //response
        HttpServletResponse response, 
        //represent sthe next filter / controller in the requst pipeline
        FilterChain filterChain
    ) throws ServletException, IOException{
        //gets the ip address of the clinet making the requst
        String ipAddress = request.getRemoteAddr(); 

        //gits the current time
        long now = Instant.now().getEpochSecond(); 

        //gets the current request infor for this ip address - or creates a fresh one 
        RequestInfo info = requestCounts.getOrDefault(
            ipAddress, 
            new RequestInfo(0, now)
        ); 

        //checks if the current rate-limit window has expired 
        if(now - info.windowStart > WINDOW_SECONDS){
            info = new RequestInfo(0, now); 
        }

        //adds one request ot htis ip address count 
        info.count++; 

        //saves the updated requst info back into the map
        requestCounts.put(ipAddress, info); 

        if(info.count > LIMIT){
            //sets the thttp status code to 429 too many requests 
            response.setStatus(429); 
            //write an error resposne to the clinet 
            response.getWriter().write("too many requst - try again later");
            //stops trhe request from continuing to the controller 
            return; 
        }
        //allows the request to continue normally 
        filterChain.doFilter(request, response); 
    }

    //small helper class used to store couont and time-window data 
    private static class RequestInfo{
        //stores how many request this ip has made in the current window 
        int count;

        //staores when this rate - limit window started
        long windowStart; 

        //constructor used to create a requestinfo object 
        RequestInfo(int count, long windowStart){
            //saves the request count 
            this.count = count; 
            //saves the start time of the window
            this.windowStart = windowStart; 
        }
    }
}
