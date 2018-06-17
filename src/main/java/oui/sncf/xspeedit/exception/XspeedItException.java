package oui.sncf.xspeedit.exception;

public class XspeedItException extends Exception {

    /**
     * Constructeur avec message.
     *
     * @param message
     */
    public XspeedItException(String message) {
        super(message);
    }

    /**
     * Constructeur avec message et stacktrace.
     *
     * @param message
     * @param stackTraceElements
     */
    public XspeedItException(String message, StackTraceElement[] stackTraceElements) {
        super(message);
        this.setStackTrace(stackTraceElements);
    }
}
