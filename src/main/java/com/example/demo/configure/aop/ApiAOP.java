package com.example.demo.configure.aop;


import com.example.demo.configure.exception.CustomException;
import com.example.demo.common.StatusCodeEnum;
import com.example.demo.common.util.ErrorResponse;
import com.example.demo.common.util.Response;
import com.example.demo.configure.jwt.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Slf4j
@Aspect
@Component
public class ApiAOP {
    @Value("${jwt.token.header}")
    private String header;

    @Autowired
    TokenService tokenService;

    @Pointcut("execution(* com.example.demo.controller.*.*(..))")
    public void controllerPointcut(){}

//    @AfterReturning(pointcut = "controllerPointcut()", returning = "result")

    /**
     * AOP의 Around 어노테이션의 경우 시작과 종료시점에 사용하며,
     * ProceedingJoinPoin의 proceed메소드로 실제 실행되어야할 클래스(컨트롤러, 서비스 등)에 실행을 시킨다.
     * 즉, proceed 메소드가 중요함.
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around(value = "controllerPointcut()" )
    public Object aroundController(ProceedingJoinPoint pjp) throws Throwable {
        String type = pjp.getSignature().getDeclaringTypeName();
        String method = pjp.getSignature().getName();

        // request 추출
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();


        //API KEY 확인
//        String apiKey = request.getHeader("apikey");
//        if (!StringUtils.hasText(apiKey)) {
//            ApiMessage apiMessage = ApiMessage.builder()
//                    .errorMessage("API KEY 확인 요망")
//                    .build();
//            return new ResponseEntity(apiMessage, HttpStatus.OK);
//        }

        Object result = null;
        try {
            result = pjp.proceed();
        } catch (CustomException e){
            log.info("API 오류:[{}], CODE : {}", e.getErrorMessage(), e.getStatusCodeEnum());
            Enumeration params = request.getParameterNames();
            while (params.hasMoreElements()) {
                String name = (String) params.nextElement();
                System.out.println(name + " : " + request.getParameter(name));
            }
            if(!request.getRequestURI().isEmpty()){
                log.info("request URI : {}", request.getRequestURI());
            }
           /* if(!request.getPathInfo().isEmpty()){
                log.info("request pathInfo : {}", request.getPathInfo());
            }*/

            StatusCodeEnum errorCode = StatusCodeEnum.getByValue(e.getStatusCodeEnum().getCode());
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .errorCode(errorCode.getCode())
                    .errorMessage(errorCode.getName())
                    .build();
            return new ResponseEntity(errorResponse, HttpStatus.valueOf(errorCode.getCode()));
        } catch(Exception e){
            log.error("API 심각한 오류발생 : {}",e);
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .errorCode(500)
                    .errorMessage("서버 오류 발생")
                    .build();
            return ResponseEntity.status(500).build();
        }
        HttpStatus statusCode = ((ResponseEntity) result).getStatusCode();

        StatusCodeEnum statusCodeEnum = StatusCodeEnum.getByValue(statusCode.value());

        Object data = ((ResponseEntity) result).getBody();

        HashMap<String, Object> dataMap = new HashMap<>();

        if(data instanceof List) {
            dataMap.put("list", data);
            dataMap.put("count", ((List<?>)data).size());
            data = dataMap;
        }else if(data instanceof Page){
            List<Page> content = new ArrayList<Page>((Collection<? extends Page>) ((Page<?>) data).getContent());
            dataMap.put("list", content);
            dataMap.put("count", ((Page<?>) data).getContent().size());
            dataMap.put("totalCount", ((Page<?>) data).getTotalElements());
            dataMap.put("totalPage", ((Page<?>) data).getTotalPages());
            data = dataMap;
        }
        Response response = Response.builder()
                .data(data)
                .status(statusCode.value())
                .message(statusCodeEnum.getName())
                .build();
        return new ResponseEntity(response, HttpStatus.OK);

    }
}
