package by.com.lifetech.exception;

public class WeatherException extends RuntimeException{
    public WeatherException(String message) {
        super(message);
    }

    public WeatherException(String message, Exception e){
        super(message, e);
    }
}
