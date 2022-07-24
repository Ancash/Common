package de.ancash.misc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class DownloadFileUtil {

	private DownloadFileUtil() {}
	
	public static void download(String url, String to) throws IOException {
		download(new URL(url), to);
	}
	
	public static void download(URL url, String to) throws IOException {
		download(url, new File(to));
	}
	
	public static void download(String url, File to) throws IOException {
		download(url, to);
	}
	
	public static void download(URL url, File to) throws IOException {
		ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
		FileOutputStream fileOutputStream = new FileOutputStream(to);
		fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
		fileOutputStream.close();
		readableByteChannel.close();
	}
}