package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;
import control.FileManager;
import control.FlowManager;
import control.Handler;
import control.PropertyFileManager;
import control.ZipFileManager;
import data.Flow;
import data.Model;
import data.Status;

@SuppressWarnings("unused")
public class PerfUtil {

    public static void logTimeDifference(String logMsg, Date start) {
	/*DateTime dt1 = new DateTime(start);
	DateTime dt2 = new DateTime(new Date());

	System.out.print(logMsg + " ");
	System.out.print(Days.daysBetween(dt1, dt2).getDays() + " days, ");
	System.out.print(Hours.hoursBetween(dt1, dt2).getHours() % 24 + " hours, ");
	System.out.print(Minutes.minutesBetween(dt1, dt2).getMinutes() % 60 + " minutes, ");
	System.out.print(Seconds.secondsBetween(dt1, dt2).getSeconds() % 60 + " seconds.");
	System.out.println(" ");*/
    }

    public static void main(String[] args) throws InterruptedException {
	FileOutputStream fos;
	try {
	    fos = new FileOutputStream("c:/myconfig.properties");

	    Properties prop = new Properties();
	    prop.put("com.app.port", "8080");
	    prop.put("com.app.ip", "127.0.0.1");

	    prop.store(fos, "A Test to write properties");
	    fos.flush();

	      prop  = new Properties();
	    prop .put("com.app.another", "Hello World");
	    prop .store(fos, "Where does this go?");
	    fos.flush();

	    fos.close();

	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
