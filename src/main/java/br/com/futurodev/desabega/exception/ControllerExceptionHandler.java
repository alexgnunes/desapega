package br.com.futurodev.desabega.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    protected ResponseEntity<ProblemDetail> handleUserAlreadyRegisteredException(UserAlreadyRegisteredException e,
                                                                          HttpServletRequest request) {
        String detail = e.getMessage();
        HttpStatus conflict = HttpStatus.CONFLICT;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(conflict, detail);
        problemDetail.setTitle(e.getMessage());
        return ResponseEntity.status(conflict).body(problemDetail);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<ProblemDetail> handleUsernameNotFoundException(UsernameNotFoundException e,
                                                                          HttpServletRequest request) {
        String detail = e.getMessage();
        HttpStatus conflict = HttpStatus.NOT_FOUND;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(conflict, detail);
        problemDetail.setTitle(e.getMessage());
        return ResponseEntity.status(conflict).body(problemDetail);
    }

    @ExceptionHandler(PersonNotFoundException.class)
    protected ResponseEntity<ProblemDetail> handlePersonNotFoundException(PersonNotFoundException e,
                                                                          HttpServletRequest request) {
        String detail = e.getMessage();
        HttpStatus conflict = HttpStatus.NOT_FOUND;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(conflict, detail);
        problemDetail.setTitle(e.getMessage());
        return ResponseEntity.status(conflict).body(problemDetail);
    }
}
