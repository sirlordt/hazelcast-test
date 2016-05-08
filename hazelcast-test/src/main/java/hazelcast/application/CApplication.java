package hazelcast.application;

import hazelcast.common.SystemConstants;

import java.io.File;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class CApplication extends ResourceConfig {
    
    protected static ServletContext mainServletContext = null;
    
    protected static String strRunningPath = null;
        
    protected static java.util.Date appServerStartup = null; 
    
    protected static HazelcastInstance hazelcastInstance = null;

    protected static Random random;
    
    //protected static HazelcastInstance client = null;
    
    public static ServletContext getMainServletContext() {
        
        return mainServletContext;
        
    }
    
    public static String getRunningPath() {

        return strRunningPath;
        
    }

    public static java.util.Date getAppServerStartup() {
        
        return appServerStartup;
    }
    
    public CApplication( @Context ServletContext servletContext ) {

        appServerStartup = new java.util.Date();
        
        register( JacksonFeature.class );

        mainServletContext = servletContext;
        
        strRunningPath = mainServletContext.getRealPath( SystemConstants._Dir_WEB_INF ) + File.separator;
        
        Config serverConfig = new Config();
        
        serverConfig.setConfigurationFile( new File( strRunningPath + SystemConstants._Dir_Config + File.separator + "hazelcast.xml" ) );
        
        hazelcastInstance = Hazelcast.newHazelcastInstance( serverConfig );

        random = new Random();
        
        final IMap<String, Long> sharedMap = hazelcastInstance.getMap( "sharedMap" );
        
        if ( sharedMap.size() == 0 ) {
        
            for ( int intIndex = 0; intIndex < 10; intIndex++ ) {
                
                sharedMap.put( "" + intIndex, random.nextLong() );
                
            }
        
        }
        
    }
    
    public static HazelcastInstance getHazelcastInstance() {
    
        return hazelcastInstance;
        
    }
    
}