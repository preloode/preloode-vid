package com.preloode.vid.component;

import com.preloode.vid.configuration.Setting;
import com.preloode.vid.configuration.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ExceptionResolverHandler implements HandlerExceptionResolver {


    @Autowired
    private Setting setting;

    @Autowired
    private Url url;

    @Autowired
    private Head head;

    @Autowired
    private Log log;


    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) {

        this.log.exception(request, exception);

        this.head.initialize("Internal Server Error", request.getServerName());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("setting", this.setting);
        modelAndView.addObject("url", this.url);
        modelAndView.setViewName("internal-server-error");

        return modelAndView;

    }


}
