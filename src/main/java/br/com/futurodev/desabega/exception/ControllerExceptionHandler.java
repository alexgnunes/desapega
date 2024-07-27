package br.com.futurodev.desabega.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    protected ResponseEntity<ProblemDetail> handlePersonNotFoundException(UserAlreadyRegisteredException e,
                                                                          HttpServletRequest request) {
        String detail = e.getMessage();
        HttpStatus conflict = HttpStatus.CONFLICT;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(conflict, detail);
        problemDetail.setTitle(e.getMessage());
        return ResponseEntity.status(conflict).body(problemDetail);
    }
}
