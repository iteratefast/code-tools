package top.iteratefast.codetool.project_generator;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.apache.commons.beanutils.BeanUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cz on 2018-5-11.
 */
public class FileRender {
    final static Pattern FILE_LIST_PATTERN = Pattern.compile("^(#\\[(.+)\\]\\[(.+)\\]).*");

    public static void render(Map<String, Object> data, File sourceFile,File targetDir) throws IOException {
        String fileName = sourceFile.getName();
        String tpl = Files.toString(sourceFile, Charsets.UTF_8);
        if (fileName.startsWith("#[")) {
            renderFileList(fileName,tpl,data,targetDir);
        } else {
            System.out.println("    Render File : " + targetDir.toPath().toString());
            Files.write(StringRender.render(tpl, data), new File(targetDir,fileName), Charsets.UTF_8);
        }
    }

    public static void renderFileList(String srcFileName,String srcContent,Map<String, Object> data,File targetDir) {
        Matcher fileListMatcher = FILE_LIST_PATTERN.matcher(srcFileName);
        if(fileListMatcher.matches()){
            String nameVarPlaceHolder = fileListMatcher.group(1);
            String fileListVar = fileListMatcher.group(2);
            String fileNameVar = fileListMatcher.group(3);
            System.out.println(String.format("    Render File list : fileListVar : %s , fileNameVar : %s",fileListVar,fileNameVar));

            Object fileListData = data.get(fileListVar);
            if(fileListData!=null || fileListData instanceof Collection){
                Collection listData = (Collection)fileListData;

                for(Object listItemData:listData){
                    data.put("_fileContext",listItemData);
                    try {
                        String fileNamePlaceHolderValue = String.valueOf(BeanUtils.getProperty(listItemData,fileNameVar));
                        String fileName = srcFileName.replace(nameVarPlaceHolder,fileNamePlaceHolderValue);
                        if(fileName.endsWith(".java")){
                            data.put("_className",fileName.substring(0,fileName.length()-5));
                        }
                        File outputFile = new File(targetDir,fileName);
                        System.out.println("    Render file : " + outputFile.toPath().toString());
                        StringRender.render(srcContent,data,outputFile);
                    } catch (Exception e) {
                        throw new IllegalArgumentException("在渲染文件列表时，期望得文件名变量(请检查数据类是不是public并且提供了getter):" + fileNameVar,e);
                    }
                }
            }else{
                throw new IllegalArgumentException("在渲染文件列表时，期望得到一个集合:" + fileListVar);
            }
        }
    }
}
