package gomoku.achievement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * 
 * @author luck
 * @version 2013.4.2.15.06
 * 用于读存游戏数据
 */
public class Data {
	private static String[] result;
	
	/**
	 * @author luck
	 * @throws IOException
	 * @version 2013.4.2.15.07
	 * 加载游戏数据
	 * @throws ClassNotFoundException 
	 * 
	 */
	@SuppressWarnings("resource")
	public static void load() throws IOException, ClassNotFoundException {
		ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("Achievements.dat"));
		result = (String[])(inputStream.readObject());
//		setData(result);
	}
	public static void setData(String[] result){
		PlayTimes.setPlayTimes(Integer.parseInt(result[0]));
	}
	/**
	 * @author luck 	
	 * @throws IOException
	 * @version 2013.4.2.15.07
	 * 存储游戏数据
	 */
	public static void set() throws IOException {
		ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("Achievements.dat",true));
		outputStream.writeObject(result);
		outputStream.close();
	}
	/**
	 * @author
	 * @version 2013.4.2.15:08
	 * 初妾匿
	 * 
	 */
	public static void initial() {
		result = null;
	}
	

}
