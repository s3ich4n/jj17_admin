package jj17.yubicycle.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.server.UID;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 * RFID 값 읽어오는 루틴.
 * @author Administrator
 *
 */
public class getRFIDvalue
{

  public String getUid()
  {
	int sleepCount = 0;
	String uid = null;

    try
    {
      System.out.println("tr]");

      while(uid == null) {
    	  if(sleepCount > 10) { break; }
	      Process p = Runtime.getRuntime().exec("python Read.py");
	      BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
	      uid = in.readLine();
	      Thread.sleep(500);
	      sleepCount++;

      }
      System.out.println("try]" + uid);
      return uid;
    }
    catch (Exception e)
    {
      System.out.println("catch");
    }
    return null;
  }
}