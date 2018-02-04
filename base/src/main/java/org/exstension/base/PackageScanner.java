package org.exstension.base;

import org.demo.AbstractDemo;

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
     * scanning the package if the target class are we want.
     *
     * @param packageName
     * @param tClass
     * @return
     */
    public Set<T> scan(String packageName, Class<T> tClass) {
        String mainFileName = packageName.replace(".", "/");
        String mainFilePath = null;
        try {
            mainFilePath = PackageScanner.class.getClassLoader().getResource(mainFileName).getPath();
        } catch (Exception e) {
            if (e instanceof NullPointerException) {
                throw new RuntimeException(packageName.concat(" not exist, please check this package in class path."));
            }
        }
        return instantiation(classNames(mainFilePath, mainFileName), tClass);
    }

    /**
     * instantiation all the route object.
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
     * scanning folder and get the class name.
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

    public static void main(String[] args) {
        PackageScanner<AbstractDemo> scanner = new PackageScanner<>();
        Set<AbstractDemo> scan = scanner.scan("org.demo", AbstractDemo.class);
        scan.forEach(System.out::println);
    }
}
