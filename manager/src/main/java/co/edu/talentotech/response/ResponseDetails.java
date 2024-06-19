package co.edu.talentotech.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(value = {
    "stackTrace",
    "suppressed",
    "localizedMessage",
    "cause"
})
public class ResponseDetails extends Exception implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;
    private String message;
    private LocalDateTime timestamp;

    public ResponseDetails(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public ResponseDetails(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public ResponseDetails(String code, Throwable cause) {
        super(cause);
        this.code = code;
        this.timestamp = LocalDateTime.now();
    }

    public ResponseDetails() {
        super();
        this.timestamp = LocalDateTime.now();
    }

    public ResponseDetails(Throwable cause) {
        super(cause);
        this.timestamp = LocalDateTime.now();
    }

    public ResponseDetails(String message) {
        super(message);
        this.timestamp = LocalDateTime.now();
    }
}