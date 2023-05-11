package hu.lev.bead.neptun.controller;

import hu.lev.bead.neptun.dto.DtoRoom;
import hu.lev.bead.neptun.dto.DtoSubject;
import hu.lev.bead.neptun.exception.DefaultErrorException;
import hu.lev.bead.neptun.exception.InDatabaseException;
import hu.lev.bead.neptun.exception.NotFoundException;
import hu.lev.bead.neptun.model.DefaultError;
import hu.lev.bead.neptun.model.FormatError;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLDataException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<DefaultError> handleNotFoundException(NotFoundException ex) {
        DefaultError defaultError = new DefaultError(new Date(), HttpStatus.NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(defaultError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DefaultErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<DefaultError> handelDefaultErrorException(DefaultErrorException ex) {
        DefaultError defaultError = new DefaultError(new Date(), HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(defaultError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<FormatError> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errorList = new ArrayList<>();
        ex.getConstraintViolations().forEach(c -> errorList.add(c.getPropertyPath() + ": " + c.getMessage()));
        FormatError formatError = new FormatError(
                new Date(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Validation error!",
                errorList);
        return new ResponseEntity<>(formatError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<DefaultError> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex){
        DefaultError defaultError = new DefaultError(new Date(),HttpStatus.INTERNAL_SERVER_ERROR,ex.getLocalizedMessage());
        System.out.println(ex.getSQLState());
        return new ResponseEntity<>(defaultError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InDatabaseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<DefaultError> handleInDatabaseException(InDatabaseException ex){
        DefaultError defaultError = new DefaultError(new Date(), HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage() + " already in database!");
        return new ResponseEntity<>(defaultError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SQLDataException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<DefaultError> handelSQLDataException(SQLDataException ex){
        DefaultError defaultError = new DefaultError(new Date(), HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage());
        return new ResponseEntity<>(defaultError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
