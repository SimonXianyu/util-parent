package io.github.simonxianyu.util.spring.web;

import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Override to handle exceptions
 * Created by simon on 15/2/22.
 */
public class MyFreeMarkerView extends FreeMarkerView {
    @Override
    protected void processTemplate(Template template, SimpleHash model, HttpServletResponse response) throws IOException, TemplateException {
        PrintWriter writer = response.getWriter();

        try {
            template.process(model, writer);
        } catch (TemplateException e) {
            e.printStackTrace(writer);
        } catch (IOException e) {
//            e.printStackTrace();
            throw e;
        }
    }
}
