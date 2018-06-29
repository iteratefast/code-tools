package top.iteratefast.codetool.project_generator;

import java.io.File;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

/**
 * Created by cz on 2018-6-29.
 */
public class PathUtils {
    public static File getUserHomeDir(){
        return new File(System.getProperty("user.home"));
    }

    public static File initTmpDir() {
        File targetDir = getUserHomeDir();
        File tmpDir = new File(targetDir,".iteratorfast/tmp/"+ UUID.randomUUID().toString());
        tmpDir.mkdirs();
        return tmpDir;
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir
     *            将要删除的文件目录
     * @return
     */
    public static boolean deleteDir(File dir) {
        if (!dir.exists()) return false;
        if (dir.isDirectory()) {
            String[] childrens = dir.list();
            // 递归删除目录中的子目录下
            for (String child : childrens) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) return false;
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    public static Set<String> getRelativePaths(File file) {
        return getRelativePaths(file.toPath().toString().length(),file);
    }

    private static Set<String> getRelativePaths(int fromIdx, File file) {
        Set<String> allFiles = new TreeSet<String>();
        String fullPath = file.toPath().toString();
        if(fullPath.length()>fromIdx){
            String path = fullPath.substring(fromIdx+1).replaceAll("\\\\","/");
            allFiles.add(path);
        }
        if(file.isDirectory()){
            String[] list = file.list();
            for(String subFileName:list){
                allFiles.addAll(getRelativePaths(fromIdx,new File(file,subFileName)));
            }
        }
        return allFiles;
    }

    public static File getProjectDir(Class classInProject) {
        File classPath = new File(classInProject.getClassLoader().getResource("").getPath());
        File dir = classPath.getParentFile();
        String pkg = classInProject.getPackage().getName().replaceAll("\\.","/");
        String javaFile = "src/main/java/" + pkg + File.separator + classInProject.getSimpleName() + ".java";
        while (true){
            if(dir==null || !dir.isDirectory()){
                break;
            }

            if(new File(dir,javaFile).exists()){
                return dir;
            }
            dir = dir.getParentFile();
        }
        return null;
    }

}
