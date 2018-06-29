package test;

import org.junit.Test;
import top.iteratefast.codetool.project_generator.PathUtils;
import top.iteratefast.codetool.project_generator.StringRender;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cz on 2018-6-29.
 */
public class StringRenderTest {

    @Test
    public void test(){
        Map<String,Object> data = new HashMap<String,Object>();
        data.put("basePkg","top.rx.tpl");

        System.out.println(StringRender.render("java/#{basePkg}/dao",data));
    }
}
