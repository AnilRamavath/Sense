package org.scorelab.sense;

import java.util.Calendar;

import android.app.Service;
import android.content.Intent;

import android.os.IBinder;
import android.widget.Toast;


import org.scorelab.sense.dataHandler.Collector;
import org.scorelab.sense.util.SenseLog;
import org.scorelab.sense.util.SenseWakeLock;
import org.scorelab.sense.writer.DBWriter;

public class Sense extends Service {

	//private final IBinder senseBinder = new SenseBinder();
	
	@Override
    public void onCreate() {
        super.onCreate();
        SenseWakeLock.acquireCPUWakeLoack(this);
      
    }

	@Override
	public IBinder onBind(Intent arg0) {
		//return senseBinder;
		return null;
	}
	

	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		SenseLog.i("Start Sense");
		SenseLog.i("Time: "+ Calendar.getInstance().getTimeInMillis() + "");
		SenseLog.i("Db");
		/*DBWriter db=new DBWriter(this);
		String query="";
		query+=db.createTableFromClass("org.scorelab.sense.dataCollector.Process.ProcessData")+" \n";
		query+=db.createTableFromClass("org.scorelab.sense.dataCollector.Process.ServiceData")+" \n";
		query+=db.createTableFromClass("org.scorelab.sense.dataCollector.Sensor.SensorData")+" \n";
		query+=db.createTableFromClass("org.scorelab.sense.dataCollector.Wifi.WifiData")+" \n";
		SenseLog.i(query);*/
		
		Collector Sensor=new Collector(this,Collector.DataType.SENSOR);
		Collector App=new Collector(this,Collector.DataType.APP);
		//Collector Wifi=new Collector(this,Collector.DataType.WIFI);
		
		SenseLog.i("End Sense");
		
		Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
		
		//service will not be stopped until we manually stop the service
		return Service.START_NOT_STICKY;
	}

	/*public class SenseBinder extends Binder {
		public Sense getService() {
			return Sense.this;
		}

	}*/

	@Override
	public void onDestroy() {
		SenseWakeLock.releaseCPUWakeLoack();
		SenseLog.i("Sense wake up lock release");
		
		 super.onDestroy();
	      Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();

	}
}
