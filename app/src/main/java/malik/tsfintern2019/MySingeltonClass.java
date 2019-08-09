package malik.tsfintern2019;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

public class MySingeltonClass {
private  static  MySingeltonClass mySingeltonClass;
private RequestQueue requestQueue;
private static Context context;
      MySingeltonClass(Context mcontext){
      context=mcontext;
      requestQueue=getRequestQueue();
      }

    private RequestQueue getRequestQueue() {

              if(requestQueue==null){
                  Cache cache = new DiskBasedCache(context.getCacheDir(), 10 * 1024 * 1024);
                  Network network = new BasicNetwork(new HurlStack());
                  requestQueue = new RequestQueue(cache, network);
                  // Don't forget to start the volley request queue
                  requestQueue.start();



          }
         return requestQueue;
    }
    public static synchronized MySingeltonClass getInstance(Context context){
          if(mySingeltonClass==null){
              mySingeltonClass=new MySingeltonClass(context);
          }
          return  mySingeltonClass;
    }
public <T> void addtoRequestQueue(Request<T> request){
          requestQueue.add(request);
}
}

