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
		// ��һ������������
		try {
			FileInputStream fis = new FileInputStream("music/b1.au");
			AudioStream stream = new AudioStream(fis);
			stream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ѭ���������ַ���
//		URL cb = null;
//		File f = new File("music/s1.au");
//		try {
//			// ��ת��Ϊuri.��תΪurl
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
