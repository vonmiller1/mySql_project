package com.demo.myshop.helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AdminPassword {
	public static void main(String[] args) throws IOException {
		Path imagesDir = Paths.get("src/main/resources/static/images");
		Files.createDirectories(imagesDir);
	}
}
