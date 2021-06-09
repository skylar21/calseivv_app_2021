package com.calseivv.project.security;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {"*.jsf"})
public class AuthenticationFilter implements Filter {

    public AuthenticationFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            HttpSession ses = req.getSession(true);
            String reqURI = req.getRequestURI();

            //fix redirect unknown URLs
            if ((ses.getAttribute("username") != null && reqURI.equals("/")) || (ses.getAttribute("username") != null && reqURI.equals("/login.jsf"))
                    || (ses.getAttribute("username") != null && reqURI.equals("/forget.jsf")) || (ses.getAttribute("username") != null && reqURI.equals("/registration.jsf"))) {
                res.sendRedirect("/secured/home.jsf");
            }

            System.out.println("Request URI: " + reqURI);

            //allow user in publicly accessible paths
            if (reqURI.indexOf("/login.jsf") >= 0 || reqURI.indexOf("/registration.jsf") >= 0 || reqURI.indexOf("/public/") >= 0
                    || reqURI.indexOf("/resources/css/") >= 0 || reqURI.indexOf("/resources/images/") >= 0 || reqURI.indexOf("/resources/script/") >= 0
                    || reqURI.indexOf("/forget.jsf") >= 0 || reqURI.indexOf("/botdetectcaptcha") >= 0
                    || (ses != null && ses.getAttribute("username") != null) || reqURI.contains("favicon")
                    || reqURI.contains("javax.faces.resource")) {
                chain.doFilter(request, response);
            } else {
                System.out.println("Redirecting to Login, Accessed " + reqURI);
                res.sendRedirect(req.getContextPath() + "/login.jsf"); // Anonymous user. Redirect to login page
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }

}