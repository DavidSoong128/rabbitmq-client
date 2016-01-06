package com.horizon.mqriver.ec.scan;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.horizon.mqriver.ec.utils.StringUtils;

public final class PackageUtil {

	/** 日志 */
	private static final Logger	logger	= LoggerFactory.getLogger(PackageUtil.class);

	/**
	 * 私有构造函数 不允许初始化
	 */
	private PackageUtil() {
	}

	/**
	 * 获取指定包下所有类
	 * 
	 * @param packageName
	 *            包名
	 * @param childPackage
	 *            是否遍历子包
	 * @return 类的完整名称
	 */
	public static List<String> getClassName(String packageName, boolean childPackage) {
		List<String> classes = new ArrayList<String>();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		String packagePath = packageName.replace(".", "/");
		Enumeration<URL> urls = null;
		try {
			urls = loader.getResources(packagePath);
		} catch (IOException e) {
			logger.error("获取某包下所有类", e);
		}

		// 将jar包符合条件的类添入当中
		classes.addAll(getClassNameByJars(((URLClassLoader) loader).getURLs(), packagePath, childPackage));

		if (urls == null || !urls.hasMoreElements()) {
			return classes;
		}

		List<File> dirs = new ArrayList<File>();
		while (urls.hasMoreElements()) {
			URL urlResource = urls.nextElement();
			dirs.add(new File(urlResource.getFile()));
		}

		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName, childPackage));
		}
		return classes;
	}

	/**
	 * 从项目文件获取某包下所有类
	 * 
	 * @param filePath
	 *            文件路径
	 * @param className
	 *            类名集合
	 * @param packageName
	 *            包名
	 * @return 类的完整名称列表
	 */
	private static List<String> findClasses(File directory, String packageName, boolean childPackage) {
		List<String> classes = new ArrayList<String>();
		if (!directory.isDirectory()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			String fileName = file.getName();
			if (file.isDirectory()) {
				if (childPackage) {
					classes.addAll(findClasses(file, packageName + "." + file.getName(), childPackage));
				}
			} else if (fileName.endsWith(".class")) {
				String className = packageName + "." + fileName.substring(0, fileName.lastIndexOf("."));
				classes.add(className);
			}
		}
		return classes;
	}

	/**
	 * 从jar获取某包下所有类
	 * 
	 * @param jarPath
	 *            jar文件路径
	 * @param childPackage
	 *            是否遍历子包
	 * @return 类的完整名称
	 */
	private static List<String> getClassNameByJar(String jarPath, boolean childPackage) {
		List<String> myClassName = new ArrayList<String>();
		String[] jarInfo = jarPath.split("!");
		String jarFilePath = null;
		try {
			jarFilePath = URLDecoder.decode(jarInfo[0].substring(jarInfo[0].indexOf("/")), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("扫描jar包中class名编码不匹配异常", e);
		}
		if (StringUtils.isEmpty(jarFilePath) || !jarFilePath.endsWith(".jar")) {
			return myClassName;
		}
		
		String packagePath = jarInfo[1].substring(1);
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(jarFilePath);
			Enumeration<JarEntry> entrys = jarFile.entries();
			while (entrys.hasMoreElements()) {
				JarEntry jarEntry = entrys.nextElement();
				String entryName = jarEntry.getName();
				if (entryName.endsWith(".class")) {
					if (childPackage) {
						if (entryName.startsWith(packagePath)) {
							entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
							myClassName.add(entryName);
						}
					} else {
						int index = entryName.lastIndexOf("/");
						String myPackagePath;
						if (index != -1) {
							myPackagePath = entryName.substring(0, index);
						} else {
							myPackagePath = entryName;
						}
						if (myPackagePath.equals(packagePath)) {
							entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
							myClassName.add(entryName);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("扫描jar包中class名异常", e);
		} finally {
			if (jarFile != null) {
				try {
					jarFile.close();
				} catch (IOException e) {
					logger.error("扫描jar包中class名关闭jar文件异常", e);
				}
			}
		}
		return myClassName;
	}

	/**
	 * 从所有jar中搜索该包，并获取该包下所有类
	 * 
	 * @param urls
	 *            URL集合
	 * @param packagePath
	 *            包路径
	 * @param childPackage
	 *            是否遍历子包
	 * @return 类的完整名称
	 */
	private static List<String> getClassNameByJars(URL[] urls, String packagePath, boolean childPackage) {
		List<String> myClassName = new ArrayList<String>();
		if (urls != null) {
			for (int i = 0; i < urls.length; i++) {
				URL url = urls[i];
				String urlPath = url.getPath();
				// 不必搜索classes文件夹
				if (urlPath.endsWith("classes/")) {
					continue;
				}
				String jarPath = urlPath + "!/" + packagePath;
				myClassName.addAll(getClassNameByJar(jarPath, childPackage));
			}
		}
		return myClassName;
	}
}
