package test;

import org.junit.Test;
import top.iteratefast.codetool.project_generator.PathUtils;
import top.iteratefast.codetool.project_generator.ProjectRender;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by cz on 2018-6-29.
 */
public class ProjectRenderTest {
    @Test
    public void test() throws IOException {
        List<DaoData> daos = new ArrayList<DaoData>();
        daos.add(new DaoData().setClassName("User"));
        daos.add(new DaoData().setClassName("Product"));
        daos.add(new DaoData().setClassName("Login"));
        daos.add(new DaoData().setClassName("Order"));

        Map<String,Object> data = new HashMap<String,Object>();
        data.put("base_pkg_path","com/test/path");
        data.put("daos",daos);
        data.put("groupId","top.rx");
        data.put("artifactId","spring-boot-mvc-tpl");
        data.put("basePkg","top.rx.tpl");
        data.put("author","cz");
        data.put("time",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        ProjectRender.render(
                data,
                new File(PathUtils.getProjectDir(ProjectRender.class),"src/test/resources/spring-boot-mvc-tpl/"),
                new File(PathUtils.getProjectDir(ProjectRender.class),"target/spring-boot-mvc-out/")
                ,true);
    }

    public static class DaoData {
        String className;

        public String getClassName() {
            return className;
        }

        public DaoData setClassName(String className) {
            this.className = className;
            return this;
        }
    }
}
