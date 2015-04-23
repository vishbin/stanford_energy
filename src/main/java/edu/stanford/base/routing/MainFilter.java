package edu.stanford.base.routing;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class MainFilter implements Filter {


  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    String path = req.getRequestURI();
    String topfolder = path.substring(1);
    int gad001HitCount=10;
    int gad002HitCount=5;
    int gad003HitCount=12;
    
    System.out.println(path);
    
    if (topfolder.contains("/")) {
      
      topfolder = topfolder.substring(0, topfolder.indexOf("/"));
    }

    if (topfolder.contains("gad")) {
      
      request.getRequestDispatcher("/gad002.html").forward(request, response);
    
    }
    else if (topfolder.endsWith(":")) {
      
      request.getRequestDispatcher(path.replaceFirst(":", "")).forward(request, response);
    
    } else {
      chain.doFilter(request, response);
   
    }

  }

  @Override
  public void destroy() {

  }

  @Override
  public void init(FilterConfig arg0) throws ServletException {

  }


}
