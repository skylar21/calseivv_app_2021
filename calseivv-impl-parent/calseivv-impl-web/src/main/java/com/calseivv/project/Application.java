package com.calseivv.project;

import com.calseivv.project.security.AuthenticationFilter;
import com.captcha.botdetect.web.servlet.CaptchaServlet;
import org.ocpsoft.rewrite.servlet.RewriteFilter;
import org.primefaces.webapp.filter.FileUploadFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.faces.application.ProjectStage;
import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import java.util.EnumSet;

//@SpringBootApplication(scanBasePackages = "com.calseivv.project")
//@EnableJpaRepositories({"com.calseivv.project.persistence"})
//@EntityScan({"com.calseivv.project.persistence.model"})
@EnableAutoConfiguration
@ComponentScan({"com.calseivv.project"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean(ServletContext servletContext) {
        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
        servletContext.setInitParameter("primefaces.UPLOADER", "commons");
        servletContext.setInitParameter(ProjectStage.PROJECT_STAGE_PARAM_NAME, ProjectStage.Production.name());
        FacesServlet servlet = new FacesServlet();
        return new ServletRegistrationBean(servlet, "*.jsf");
    }

    @Bean
    public ServletRegistrationBean captchaRegistrationBean(ServletContext servletContext) {
        CaptchaServlet servlet = new CaptchaServlet();
        return new ServletRegistrationBean(servlet, "/botdetectcaptcha");
    }

    @Bean
    public FilterRegistrationBean rewriteFilter() {
        FilterRegistrationBean rwFilter = new FilterRegistrationBean(new RewriteFilter());
        FilterRegistrationBean fileFilter = new FilterRegistrationBean(new FileUploadFilter());
        rwFilter.setDispatcherTypes(EnumSet.of(DispatcherType.FORWARD, DispatcherType.REQUEST,
                DispatcherType.ASYNC, DispatcherType.ERROR));
        rwFilter.setFilter(new AuthenticationFilter());
        rwFilter.addUrlPatterns("/*");
        return rwFilter;
    }

    @Bean
    public FilterRegistrationBean fileFilter() {
        FilterRegistrationBean fileFilter = new FilterRegistrationBean(new FileUploadFilter());
        return fileFilter;
    }

}
