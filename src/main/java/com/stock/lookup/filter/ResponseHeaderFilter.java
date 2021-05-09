package com.stock.lookup.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@javax.servlet.annotation.WebFilter(urlPatterns = {"/*"})
@Component
public class ResponseHeaderFilter implements Filter {
  @Override
  public void doFilter(
          ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
          throws IOException, ServletException {
    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
    httpServletResponse.setHeader(
            "X-My-Correlation-ID", UUID.randomUUID().toString().toUpperCase().replace("-", ""));
    filterChain.doFilter(servletRequest, servletResponse);
  }

  @Override
  public void destroy() {
    // nothing
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // nothing
  }
}
