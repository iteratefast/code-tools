package top.iteratefast.codetool.project_generator;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by cz on 2018-5-10.
 */
public class ProjectRender {
    public static void render(Map<String,Object> data,File templateFolder,File outPutFolder,boolean overwrite) throws IOException {
        File tmpDir = PathUtils.initTmpDir();
        try{
            Map<String,String> pathMapping = getPathMapping(templateFolder,data);

            for(Map.Entry<String,String> entry:pathMapping.entrySet()){
                System.out.println("Generate : " + entry.getKey() + "   >   " +entry.getValue());
                File src = new File(templateFolder,entry.getKey());
                File target = new File(tmpDir,entry.getValue());
                if(src.isDirectory()){
                    System.out.println("    mkdir : " + target.toPath().toString());
                    target.mkdirs();
                    continue;
                }
                if(src.isFile()){
                    FileRender.render(data,src,target.getParentFile());
                }
            }

            copy(tmpDir,outPutFolder,overwrite);
        }finally {
            PathUtils.deleteDir(tmpDir);
        }

    }

    private static void copy(File tmpDir, File outPutFolder, boolean overwrite) {
        Set<String> sourceFiles = PathUtils.getRelativePaths(tmpDir);
        if(!outPutFolder.exists()){
            outPutFolder.mkdirs();
        }else if(!overwrite){
            Set<String> targetFiles = PathUtils.getRelativePaths(outPutFolder);
            for(String sourceFile:sourceFiles) {
                if(targetFiles.contains(sourceFile)){
                    if(new File(sourceFile).isFile()) {
                        System.err.println("[Error] Generate code field !!! target file exist : " + sourceFile);
                        return;
                    }
                }
            }
        }

        for(String sourceFile:sourceFiles){
            try {
                File src = new File(tmpDir,sourceFile);
                File target = new File(outPutFolder,sourceFile);
                if(src.isDirectory()) {
                    System.out.println("mkdir : " + target.getPath());
                    target.mkdirs();
                }else{
                    System.out.println("copy form " + src.getPath() + " to " + target.getPath());
                    Files.copy(src,target);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    static Map<String,String> getPathMapping(File templateFolder,Map<String,Object> data){
        Set<String> allFiles = PathUtils.getRelativePaths(templateFolder);
        Map<String,String> pathMapping = new TreeMap<String,String>();
        for(String path:allFiles){
            pathMapping.put(path,StringRender.render(path,data));
        }
        return pathMapping;
    }
}
