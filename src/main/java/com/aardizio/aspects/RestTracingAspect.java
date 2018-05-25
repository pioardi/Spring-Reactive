package com.aardizio.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import brave.Span;
import brave.Tracer;
import brave.Tracer.SpanInScope;

@Aspect
@Component
public class RestTracingAspect {
    
    @Autowired
    private Tracer tracer;

    @Pointcut("within(com.aardizio.resource..*)")
    public void reactiveCrudRepositoryClassMethods() {
    };

    @Around("reactiveCrudRepositoryClassMethods()")
    public Object nextSpanForRepositoryMethod(ProceedingJoinPoint pjp) throws Throwable {
        Object retval = null;
        Span newSpan = tracer.nextSpan().name("rest").start();
        try (SpanInScope ws = tracer.withSpanInScope(newSpan)) {
            retval = pjp.proceed();
        } finally {
            newSpan.annotate("Rest request end").finish();
        }

        return retval;
    }



}