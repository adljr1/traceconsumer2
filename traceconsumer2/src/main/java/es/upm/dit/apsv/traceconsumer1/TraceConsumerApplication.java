package es.upm.dit.apsv.traceconsumer1;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.hibernate.cfg.Environment;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import es.upm.dit.apsv.traceconsumer1.Repository.TraceRepository;
import es.upm.dit.apsv.traceconsumer1.model.Trace;
import es.upm.dit.apsv.traceconsumer1.model.TransportationOrder;

import java.util.function.Consumer;

@SpringBootApplication
public class TraceConsumerApplication {
    public static final Logger log = LoggerFactory.getLogger(TraceConsumerApplication.class);
    @Autowired
    private  TraceRepository traceRepository;
    @Autowired
    private  Environment env;
    @Bean("consumer")

    public Consumer<Trace> checkTrace() {
            return t -> {
                    t.setTraceId(t.getTruck() + t.getLastSeen());
                    tr.save(t);
                String uri = env.getProperty("orders.server");
                    RestTemplate restTemplate = new RestTemplate();
                    TransportationOrder result = null;
                    try {                        
                      result = restTemplate.getForObject(uri
                       + t.getTruck(), TransportationOrder.class);
                    } catch (HttpClientErrorException.NotFound ex)   {
                            result = null;
                    }
                    if (result != null && result.getSt() == 0) {
                            result.setLastDate(t.getLastSeen());
                            result.setLastLat(t.getLat());
                            result.setLastLong(t.getLng());
                            if (result.distanceToDestination() < 10)
                                    result.setSt(1);
                            restTemplate.put(uri
                             , result,TransportationOrder.class);
                            log.info("Order updated: "+ result);
                    }
            };
    }
}

