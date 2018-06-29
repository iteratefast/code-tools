package test;

import org.junit.Test;
import top.iteratefast.codetool.project_generator.PathUtils;

/**
 * Created by cz on 2018-6-29.
 */
public class PathUtilsTest {

    @Test
    public void test(){
//        System.out.println(PathUtils.initTmpDir().getPath());
        System.out.println(PathUtils.getProjectDir(PathUtils.class));
    }
}
