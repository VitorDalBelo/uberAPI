package com.example.UberApi.erroHandle;

import java.util.ArrayList;
import java.util.List;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorValidationHandler {
	@Autowired
	private MessageSource msgSource;
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErrorDto> handler (MethodArgumentNotValidException exception){
		List<ErrorDto> errorsDTO = new ArrayList<ErrorDto>();
		List<FieldError> fieldErrors = exception.getFieldErrors();
		fieldErrors.forEach(i ->{
			String msg = msgSource.getMessage(i, LocaleContextHolder.getLocale());
			ErrorDto error = new ErrorDto(i.getField(),msg);
			errorsDTO.add(error);
		});		
		return errorsDTO;
	}
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ErroDependenciaNaoEncontrada.class)
	public List<ErrorDto> handler2 (ErroDependenciaNaoEncontrada exception){
		List<ErrorDto> errorsDTO = new ArrayList<ErrorDto>();
		List<FieldError> fieldErrors = exception.getFieldErrors();
		fieldErrors.forEach(i ->{
			String msg = msgSource.getMessage(i, LocaleContextHolder.getLocale());
			ErrorDto error = new ErrorDto(i.getField(),msg);
			errorsDTO.add(error);
		});
		return errorsDTO;
	}
	
	@ExceptionHandler(JdbcSQLIntegrityConstraintViolationException.class)
	public ResponseEntity<List<ErrorDto>> handlerConflict (JdbcSQLIntegrityConstraintViolationException exception){
		int state =  exception.getErrorCode();
		List<ErrorDto> erros = new ArrayList<ErrorDto>();
		if(state==23505) {
			int start=exception.toString().indexOf("(");
			int end=exception.toString().indexOf(")");
			String field = exception.toString().substring(start+1, end).toLowerCase();
			ErrorDto error = new ErrorDto(field,"conteudo já cadastrado");
			erros.add(error);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(erros);
		}

		return ResponseEntity.internalServerError().build();
	}
	
		
		
		
}
