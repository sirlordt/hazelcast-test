package hazelcast.services.test;

import hazelcast.application.CApplication;
import hazelcast.common.SystemUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

@Path( "/test" )
public class CServiceTest {
    
    //http://192.168.2.106:9090/hazelcast-test/test/listdata
    //http://192.168.2.106:9090/hazelcast-test/test/adddata?key=tomas&value=1000
    
    // HTTP Post Method
    @GET
    // Path: http://localhost/<appln-folder-name>/test/adddata
    @Path( "/adddata" )
    // Produces JSON as response
    @Produces( MediaType.APPLICATION_JSON ) 
    public String addData( @QueryParam( "key" ) String strKey, @QueryParam( "value" ) String strValue ) {

        String strResult = "";
        
        try {
            
            HazelcastInstance hazelcastInstance  = CApplication.getHazelcastInstance();
            
            final IMap<String,Long> sharedMap = hazelcastInstance.getMap( "sharedMap" );
            
            sharedMap.put( strKey, Long.parseLong( strValue ) );
            
            strResult = SystemUtils.buildSimpleResponse( "1", "Success to add the map entry" );
            
        }
        catch ( Exception ex ) {
            
            strResult = SystemUtils.buildSimpleResponse( "-1", "Failed to delete the map entry" );
            
        }
        
        return strResult;
        
    }

    // HTTP Post Method
    @GET
    // Path: http://localhost/<appln-folder-name>/test/listdata
    @Path( "/listdata" )
    // Produces JSON as response
    @Produces( MediaType.APPLICATION_JSON ) 
    public String listData() {

        String strResponse = "";

        HazelcastInstance hazelcastInstance  = CApplication.getHazelcastInstance();
        
        final IMap<String,Long> sharedMap = hazelcastInstance.getMap( "sharedMap" );
        
        LinkedHashMap<String, Object> responseData = new LinkedHashMap<String, Object>();

        ArrayList<LinkedHashMap<String, Object>> responseList = new ArrayList<LinkedHashMap<String, Object>>(); 
        
        for ( Entry<String,Long> entry : sharedMap.entrySet() ) {
            
            LinkedHashMap<String, Object> responseMapEntry = new LinkedHashMap<String, Object>();
            
            responseMapEntry.put( "Key", entry.getKey() );
            responseMapEntry.put( "Value", entry.getValue() );
            
            responseList.add( responseMapEntry );
            
        }
        
        responseData.put( "MapEntriesCount", responseList.size() );
        responseData.put( "MapEntries", responseList );
        
        strResponse = SystemUtils.buildJSON( responseData );
        
        return strResponse;
        
    }
    
}
