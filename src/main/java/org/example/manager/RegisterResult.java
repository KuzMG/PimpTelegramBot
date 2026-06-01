package org.example.manager;

public final class RegisterResult {

    private final boolean success;
    private final String message;

    private RegisterResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static RegisterResult ok(String message) {
        return new RegisterResult(true, message);
    }

    public static RegisterResult fail(String message) {
        return new RegisterResult(false, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
