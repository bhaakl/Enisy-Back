package kg.bhaakl.enisy.exceptions;

public class MyUnAuthorizedException extends RuntimeException{
    public MyUnAuthorizedException(String msg) {
        super(msg);
    }
}
