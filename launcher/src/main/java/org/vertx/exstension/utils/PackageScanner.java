package org.vertx.exstension.utils;

import org.vertx.exstension.route.Route;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * scanner for scan all the class from blow the class path package.
 * Created by kam on 2017/12/4.
 */
public class PackageScanner<T> {
    private static final boolean SCAN_SUB_PACKAGE = true;
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");

    /**
     * 扫描包中符合指定类型的类
     *
     * @param packageName
     * @param tClass
     * @return
     */
    public Set<T> scan(String packageName, Class<T> tClass) {
        String mainFileName = packageName.replace(".", "/");
        String mainFilePath = PackageScanner.class.getClassLoader().getResource(mainFileName).getPath();
        return instantiation(classNames(mainFilePath, mainFileName), tClass);
    }

    /**
     * 实例化所有Route
     *
     * @param classNames
     * @return
     */
    private Set<T> instantiation(Set<String> classNames, Class<T> tClass) {
        Set<T> objects = new HashSet<>(classNames.size(), 1.0f);
        classNames.forEach(className -> {
            try {
                Class<?> clazz = Class.forName(className);
                if (!clazz.isInterface() && !clazz.isEnum()) {
                    Object o = clazz.newInstance();
                    if (tClass.isAssignableFrom(o.getClass()))
                        objects.add((T) o);
                }
            } catch (Exception e) {
                e.getCause().printStackTrace();
            }
        });
        return objects;
    }

    /**
     * 扫描文件夹，加载class文件名
     *
     * @param mainFilePath
     * @param mainFileName
     * @return
     */
    private Set<String> classNames(String mainFilePath, String mainFileName) {
        Set<String> classNames = new HashSet<>();
        File mainFile = new File(mainFilePath);
        File[] subFiles = mainFile.listFiles();
        for (File subFile : subFiles) {
            if (subFile.isDirectory()) {
                if (SCAN_SUB_PACKAGE)
                    classNames.addAll(classNames(subFile.getPath(), mainFileName.concat(".").concat(subFile.getName())));
            } else {
                String subFilePath = subFile.getPath();
                subFilePath = subFilePath
                        .substring(subFilePath.lastIndexOf(FILE_SEPARATOR) + 1);
                subFilePath = subFilePath.substring(0, subFilePath.lastIndexOf("."));
                classNames.add(mainFileName.concat(".").concat(subFilePath).replace("/", "."));
            }
        }
        return classNames;
    }
}
