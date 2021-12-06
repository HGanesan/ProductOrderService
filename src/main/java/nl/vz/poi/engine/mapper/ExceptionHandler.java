package nl.vz.poi.engine.mapper;

import nl.vz.poi.engine.core.ErrorMessage;
import nl.vz.poi.engine.exception.DatabaseErrorException;
import nl.vz.poi.engine.exception.EmailIdNotFoundException;
import nl.vz.poi.engine.exception.InvalidEmailIdException;
import nl.vz.poi.engine.exception.PoiServiceException;
import nl.vz.poi.engine.exception.ProductAlreadyOrderedException;
import nl.vz.poi.engine.exception.ReqresClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServiceUnavailableException;
import java.net.URISyntaxException;


@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorMessage> getExceptionMsg(Exception exception) {

        LOGGER.debug("inside ExceptionHandler with error:{}", exception.getMessage());

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorResponse = "Error occurred while processing your request";

        if (exception instanceof ReqresClientException) {
            errorResponse = "Reqres client error";
        } else if (exception instanceof EmailIdNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            errorResponse = "No details found for this emailId";
        } else if (exception instanceof InvalidEmailIdException) {
            status = HttpStatus.BAD_REQUEST;
            errorResponse = exception.getMessage();
        } else if (exception instanceof ProductAlreadyOrderedException) {
            status = HttpStatus.BAD_REQUEST;
            errorResponse = "User have already ordered this product";
        } else if (exception instanceof PoiServiceException) {
            errorResponse = "Something went wrong while processing the request";
        } else if (exception instanceof DatabaseErrorException) {
            errorResponse = exception.getMessage();
        } else if (exception instanceof NotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (exception instanceof BadRequestException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (exception instanceof ServiceUnavailableException || exception instanceof URISyntaxException) {
            status = HttpStatus.SERVICE_UNAVAILABLE;
        }

        ErrorMessage errorMsg = new ErrorMessage(status.value(), status, errorResponse);
        return new ResponseEntity<>(errorMsg, new HttpHeaders(), status);
    }
}
