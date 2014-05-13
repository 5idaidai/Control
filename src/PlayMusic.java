import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class PlayMusic {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 单一播放声音方法
		try {
			FileInputStream fis = new FileInputStream("music/b1.au");
			AudioStream stream = new AudioStream(fis);
			stream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 循环播放音乐方法
//		URL cb = null;
//		File f = new File("music/s1.au");
//		try {
//			// 先转换为uri.在转为url
//			cb = f.toURI().toURL();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
//		AudioClip aau;
//		aau = Applet.newAudioClip(cb);
//		// aau.play();
//		aau.loop();
	}

}
