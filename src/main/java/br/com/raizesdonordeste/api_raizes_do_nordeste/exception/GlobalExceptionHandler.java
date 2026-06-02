package br.com.raizesdonordeste.api_raizes_do_nordeste.exception;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.ErrorResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;



@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleValidationErrors(
            MethodArgumentNotValidException exception,
            HttpServletRequest request) {

        List<ErrorResponseDTO.ErrorDetail> details = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map((FieldError error) -> new ErrorResponseDTO.ErrorDetail(
                        error.getField(),
                        error.getDefaultMessage()
                ))
                .toList();

        return new ErrorResponseDTO(
                "VALIDATION_ERROR",
                "Erro de validação nos campos enviados",
                details,
                LocalDateTime.now(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDTO handleEntityNotFound(
            EntityNotFoundException exception,
            HttpServletRequest request) {
        return new ErrorResponseDTO(
                "NOT_FOUND",
                exception.getMessage(),
                List.of(),
                LocalDateTime.now(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDTO handleGenericError(
            Exception exception,
            HttpServletRequest request) {
        return new ErrorResponseDTO(
                "INTERNAL_ERROR",
                "Erro interno do servidor",
                List.of(),
                LocalDateTime.now(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDTO handleIllegalStateException(
            IllegalStateException exception, HttpServletRequest request) {
        return new ErrorResponseDTO(
                "CONFLICT",
                exception.getMessage(),
                List.of(),
                LocalDateTime.now(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(PaymentRefusedException.class)
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    public ErrorResponseDTO handlePaymentRefusedException(
            PaymentRefusedException exception, HttpServletRequest request) {
        return new ErrorResponseDTO(
                "PAYMENT_REQUIRED",
                exception.getMessage(),
                List.of(),
                LocalDateTime.now(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponseDTO handleAccessDeniedException(
            AccessDeniedException exception, HttpServletRequest request) {
        //log para auditoria
        log.warn("Acesso negado | path={} | mensagem={}",
                request.getRequestURI(), exception.getMessage());
        return new ErrorResponseDTO(
                "FORBIDDEN",
                "Acesso negado",
                List.of(),
                LocalDateTime.now(),
                request.getRequestURI()
        );
    }



}
