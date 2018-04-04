package csv.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

public class FileUtils {

	public static String read(File file) throws FileNotFoundException, IOException {
		StringBuilder content = new StringBuilder();
		try (FileInputStream in = new FileInputStream(file)) {
			int c;
			while ((c = in.read()) != -1)
				content.append((char) c);
		}
		return content.toString();
	}
	
	public static File write(String filePath, String content) throws IOException {
		File file = new File(filePath);
		write(file, content, false);
		return file;
	}
	
	public static void write(File file, String content) throws IOException {
		write(file, content, false);
	}
	
	public static void write(File file, String content, boolean append) throws IOException {
		checkWriteFile(file);
		try (FileOutputStream fos = new FileOutputStream(file, append)) {
			fos.write(content.getBytes());
		}
	}
	
	private static void checkWriteFile(File file) throws FileAlreadyExistsException {
		if (file.exists() && file.isDirectory())
			throw new FileAlreadyExistsException(file.getAbsolutePath() + " is a Directory");
		if (!file.exists()) {
			file.getParentFile().mkdirs();
		}
	}
	
}
