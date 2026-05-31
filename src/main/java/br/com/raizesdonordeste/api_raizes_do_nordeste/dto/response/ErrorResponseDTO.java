package br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponseDTO(
        String error,
        String message,
        List<ErrorDetail> details,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime timestamp,
        String path
) {
    public record ErrorDetail(
            String field,
            String issue) {}
}
