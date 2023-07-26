package ru.iliya132.exception;

public class InvalidMonitorException extends RuntimeException {
    public InvalidMonitorException() {
        super();
    }

    public InvalidMonitorException(Exception e) {
        super(e);
    }

    public InvalidMonitorException(String message) {
        super(message);
    }
}
