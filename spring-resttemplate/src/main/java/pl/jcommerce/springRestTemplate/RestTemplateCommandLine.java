package pl.jcommerce.springRestTemplate;

import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateCommandLine implements CommandLineRunner  {

    private static final Logger LOG = Logger.getLogger(RestTemplateCommandLine.class);
    
    @Override
    public void run(String... arg0) throws Exception {
        
        HttpComponentsClientHttpRequestFactory factory = 
                new HttpComponentsClientHttpRequestFactory();
        
        RestTemplate restTemplate = new RestTemplate(factory);
        
        ResponseEntity<Quote> q = restTemplate.getForEntity("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
        
        Quote quote = q.getBody();
    
        LOG.info(quote);
    }

}
