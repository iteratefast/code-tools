package top.iteratefast.codetool.project_generator;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by cz on 2018-5-11.
 */
public class StringRender {
    final static GroupTemplate TPL_FACTORY = initGroupTeplete();

    public static String render(String source,Map<String,Object> data){
        Template tpl = TPL_FACTORY.getTemplate(source);
        tpl.binding(data);
        return tpl.render();
    }

    public static void render(String source,Map<String,Object> data,File outputFile) throws IOException {
        String result = render(source,data);
        Files.write(result,outputFile, Charsets.UTF_8);
    }

    static GroupTemplate initGroupTeplete(){
        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
        try {
            Configuration cfg = Configuration.defaultConfiguration();
            cfg.setPlaceholderStart("#{");
            cfg.setPlaceholderEnd("}");
            return new GroupTemplate(resourceLoader, cfg);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
