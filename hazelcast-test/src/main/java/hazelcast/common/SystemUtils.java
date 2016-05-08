package hazelcast.common;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jettison.json.JSONObject;


public class SystemUtils {
 
    @SuppressWarnings( "unchecked" )
    public static JSONObject recursiveBuildJSONObject( Map<String,Object> fieldsData ) {
        
        JSONObject Result = new JSONObject();
        
        try {
        
            for ( Entry<String, Object> entry : fieldsData.entrySet() ) {
                
                if ( entry.getValue() instanceof LinkedHashMap ) {
                    
                    Result.put( entry.getKey(), recursiveBuildJSONObject( (Map<String,Object>) entry.getValue() ) );
                    
                }
                else if ( entry.getValue() instanceof ArrayList ) {
                    
                    Result.put( entry.getKey(), recursiveBuildJSONArray( (ArrayList<Map<String,Object>>) entry.getValue() ) );
                    
                } 
                else {
                    
                    Result.put( entry.getKey(), entry.getValue() );
                    
                }
                
            }
        
        }
        catch ( Exception ex ) {
            
            
        }
        
        return Result;
        
    }

    public static ArrayList<JSONObject> recursiveBuildJSONArray( ArrayList<Map<String,Object>> listObjects ) {
        
        ArrayList<JSONObject> Result = new ArrayList<JSONObject>();
        
        try {
                    
            for ( int intIndex = 0; intIndex < listObjects.size(); intIndex++ ) {
                
                Result.add( recursiveBuildJSONObject( listObjects.get( intIndex ) ) );
                
            }
        
        }
        catch ( Exception ex ) {
            
            
        }
        
        return Result;
        
    }
    
    @SuppressWarnings( "unchecked" )
    public static String buildJSON( Map<String,Object> fieldsData ) {
        
        String strResult = "";
        
        try {
            
            JSONObject object = new JSONObject();
            
            for ( Entry<String, Object> entry : fieldsData.entrySet() ) {
                
                if ( entry.getValue() instanceof Map ) {

                    object.put( entry.getKey(), recursiveBuildJSONObject( (Map<String,Object>) entry.getValue() ) );
                    
                }
                else if ( entry.getValue() instanceof ArrayList ) {
                    
                    object.put( entry.getKey(), recursiveBuildJSONArray( (ArrayList<Map<String,Object>>) entry.getValue() ) );
                    
                }
                else {
                
                    object.put( entry.getKey(), entry.getValue() );
                
                }
                
            }
            
            strResult = object.toString();
            
        }
        catch ( Exception ex ) {
            
            
        }
        
        return strResult;
        
    }
    
    public static String buildSimpleResponse( String strCode, String strDescription ) {

        String Result = ""; 
        
        try {
            
            JSONObject jsonObject = new JSONObject();
            
            jsonObject.put( SystemConstants._Response_Code, strCode );
            jsonObject.put( SystemConstants._Response_Description, strDescription );
            
            Result = jsonObject.toString();
            
        }
        catch ( Exception ex ) {
            
            
        }
        
        return Result;
        
    }
    
}
