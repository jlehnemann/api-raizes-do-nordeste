package br.com.raizesdonordeste.api_raizes_do_nordeste.exception;

public class PaymentRefusedException extends RuntimeException {
    public PaymentRefusedException(String message) {
        super(message);
    }
}
