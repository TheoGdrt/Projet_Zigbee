import com.rapplogic.xbee.api.ApiId;
import com.rapplogic.xbee.api.AtCommand;
import com.rapplogic.xbee.api.AtCommandResponse;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress16;
import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.XBeeRequest;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.util.ByteUtils;
import com.rapplogic.xbee.api.PacketListener;
import com.rapplogic.xbee.api.RemoteAtRequest;


public class HelloWorld {

    public static void main(String[] args) {
        // Prints "Hello, World" to the terminal window.
        System.out.println("Hello, World");
        
        XBee xbee = new XBee();
        try {
			xbee.open("COM6", 9600);
			
			 XBeeAddress64 remoteAddress = new XBeeAddress64(0, 0, 0, 0, 0, 0, 0, 0);
		        RemoteAtRequest request = new RemoteAtRequest(XBeeRequest.DEFAULT_FRAME_ID, remoteAddress, XBeeAddress16.BROADCAST, false, "NI");
		        xbee.sendAsynchronous(request);
		        
		        xbee.addPacketListener(new PacketListener() {
		            public void processResponse(XBeeResponse response) {
		            	if (response.getApiId() == ApiId.REMOTE_AT_RESPONSE) {
		            		   // since this API ID is AT_RESPONSE, we know to cast to AtCommandResponse
		            		    AtCommandResponse atResponse = (AtCommandResponse) response;

		            		    if (atResponse.isOk()) {
		            		        // command was successful
		            		        System.out.println("Command returned " + ByteUtils.toString(atResponse.getValue()));
		            		    } else {
		            		        // command failed!
		            		    	System.out.println("Command returned ");
		            		    }
		            		    
		            		}
		            	
//		            	AtCommandResponse atResponse = (AtCommandResponse) response;
//		            	 System.out.println("Command returned " + ByteUtils.toString(atResponse.getValue()));
		            }
		        });
		} catch (XBeeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
    }
    
}