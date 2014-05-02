import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ��ʱ����ʾ������ʱ
 * 
 * @author Administrator
 * 
 */
public class TimerDemo {

	public static void main(String[] args) {
		// min 5����
		int min = 5;
		long start = System.currentTimeMillis();
		// end �������ʱ��
		final long end = start + min * 60 * 1000;

		final Timer timer = new Timer();
		// �ӳ�0���루������ִ�У���ʼ��ÿ��1000����ִ��һ��
		timer.schedule(new TimerTask() {
			public void run() {
				// show��ʣ��ʱ�䣬��Ҫ��ʾ��ʱ��
				long show = end - System.currentTimeMillis();
				long h = show / 1000 / 60 / 60;// ʱ
				long m = show / 1000 / 60 % 60;// ��
				long s = show / 1000 % 60;// ��
				System.out.println("����ʱ�䣺" + h + "ʱ" + m + "��" + s + "��");
			}
		}, 0, 1000);
		// ��ʱ����ʱ��ֹͣȫ��timer��ʱ�ƻ�����
		timer.schedule(new TimerTask() {
			public void run() {
				timer.cancel();
			}

		}, new Date(end));

	}
}