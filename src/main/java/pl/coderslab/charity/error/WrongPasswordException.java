package pl.coderslab.charity.error;

public class WrongPasswordException extends RuntimeException {
    private static final long serialVersionUID = 5861310537366287163L;

    public WrongPasswordException() {
        super();
    }

    public WrongPasswordException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public WrongPasswordException(final String message) {
        super(message);
    }

    public WrongPasswordException(final Throwable cause) {
        super(cause);
    }
}
