package pl.jcommerce.bridge;

import java.util.Date;

import org.apache.lucene.document.DateTools;
import org.hibernate.search.bridge.StringBridge;

public class MyCustomDateBridge implements StringBridge{

    @Override
    public String objectToString(Object object) {
        
        return DateTools.dateToString( 
                (Date)object,
                DateTools.Resolution.DAY );
    }

}
