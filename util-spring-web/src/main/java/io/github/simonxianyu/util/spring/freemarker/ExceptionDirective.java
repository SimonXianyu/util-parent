package io.github.simonxianyu.util.spring.freemarker;

import freemarker.core.Environment;
import freemarker.ext.beans.StringModel;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Use this to export exception in error.
 * Created by simon on 15/2/22.
 */
public class ExceptionDirective implements TemplateDirectiveModel {
    @Override
    public void execute(Environment environment, Map params, TemplateModel[] templateModels,
                        TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        TemplateModel eobj = environment.getDataModel().get("exception");
        if (eobj instanceof StringModel) {
            Object wobj = ((StringModel) eobj).getWrappedObject();
            if (wobj instanceof Throwable) {
                ((Throwable) wobj).printStackTrace(new PrintWriter(environment.getOut()));
            }
        }
    }
}
