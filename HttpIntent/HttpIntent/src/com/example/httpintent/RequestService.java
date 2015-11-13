package com.example.httpintent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.example.httpintent.MainActivity.RequestReceiver;
import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class RequestService extends IntentService {

	private String url=null;
	public RequestService() {
		super("RequestService");
		// TODO Auto-generated constructor stub
	}

	@SuppressLint("ShowToast")
	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		double val;
		String st;
		String toastMsg;
		String responseMessage= null;
		val= intent.getDoubleExtra("VALUE", -1);
		Log.d("TAMIM", "adas "+ val);
		int start= intent.getIntExtra("START", 0);
		int end=intent.getIntExtra("END", 0);
		
		Log.d("TAMIM", "Before try block");
		try{
			url="www.hrhafiz.com/converter/index.php";
			HttpClient httpClient=new DefaultHttpClient();HttpParams params=httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(params, 3000);
			HttpConnectionParams.setSoTimeout(params, 30000);
			ConnManagerParams.setTimeout(params, 30000);
			HttpGet httpGet= new HttpGet("http://www.hrhafiz.com/converter/index.php");
			HttpResponse response = httpClient.execute(httpGet);
			StatusLine statusLine=response.getStatusLine();
			if(statusLine.getStatusCode()== HttpStatus.SC_OK){
				ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseMessage = out.toString();
			}else{
				response.getEntity().getContent().close();
			}
			
			
		}catch (ClientProtocolException e) {
            Log.d("TAMIM", e.getMessage());
        } catch (IOException e) {
        	Log.d("TAMIM", e.getMessage());
        }catch (Exception e) {
        	Log.d("TAMIM", e.getMessage());
        }
		
		Intent bIntent=new Intent();
		bIntent.setAction(RequestReceiver.PROCESS_RESPONSE);
		bIntent.addCategory(Intent.CATEGORY_DEFAULT);
		
		if(responseMessage != null){
			
			if(val >= 0){
				String data= responseMessage.substring(start, end);
				double ans= Double.parseDouble(data)*val;
				DecimalFormat df = new DecimalFormat("#.###");
				st= df.format(ans);
				Log.d("TAMIM", "ANS: "+val+"  " + ans +"  "+data);
				toastMsg="Successfull";
			}else{
				st="";
				toastMsg="Wrong Input!";
			}
			
		}else{
			
			st="";
			toastMsg = "Internet Connetion Error!";
			
		}
		bIntent.putExtra("RESPONSE", st);
		bIntent.putExtra("TOAST", toastMsg);
		sendBroadcast(bIntent);
			
		
		
		
	}

}
