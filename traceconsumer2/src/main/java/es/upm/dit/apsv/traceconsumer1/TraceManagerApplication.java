package es.upm.dit.apsv.traceconsumer1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import es.upm.dit.apsv.traceconsumer1.Repository.TraceRepository;
import es.upm.dit.apsv.traceconsumer1.model.Trace;

@SpringBootApplication
public class TraceManagerApplication {

	public static final Logger log = LoggerFactory.getLogger(TraceManagerApplication.class);

	//@Autowired
	//private TransportationOrderRepository torderRepository;

	public static void main(String[] args) {
		SpringApplication.run(TraceManagerApplication.class, args);
	}


@Component
class DemoCommandLineRunner implements CommandLineRunner{

	@Autowired
	private TraceRepository torderRepository;

	@Override
	public void run(String... args) throws Exception {

		Trace t = new Trace();
		t.setTraceId("0");
		t.setTruck("MATRICULA"+ System.currentTimeMillis());
		t.setLastSeen(0);
		t.setLat(44);
		t.setLng(0.0);
		torderRepository.save(t);

	}
}

}
