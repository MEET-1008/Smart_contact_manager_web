
package com.codewithbrinda.scm.scm0_2.exception;


import com.codewithbrinda.scm.scm0_2.helper.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHendaler {
//
//	@ExceptionHandler(ResouecenotfoundException.class)
//	public ResponseEntity<ApiResponse> ResouecenotfoundExceptionHandler (ResouecenotfoundException ex){
//		String message = ex.getMessage();
//		ApiResponse apiResponse = new ApiResponse(message , false );
//		return new ResponseEntity<ApiResponse>(apiResponse ,HttpStatus.NOT_FOUND);
//	}
//
//	public ResponseEntity<Map<String, String >> hendalmethodargnotvalidate(){
//		return null;
//
//	}
//

}
