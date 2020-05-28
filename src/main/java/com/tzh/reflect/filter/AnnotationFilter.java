package com.tzh.reflect.filter;

import java.lang.annotation.Annotation;

public interface AnnotationFilter {

    public boolean doFilter(Annotation annotation);

}
