package com.stefanini.gatlingdemo;


import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.rampUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class Application extends Simulation {

	HttpProtocolBuilder httpProtocol = http.baseUrl("https://computer-database.gatling.io")
			.acceptHeader("application/json").contentTypeHeader("application/json")
			.userAgentHeader("Pruebas de Carga y Rendimiento con Gatling");
	
	
	 ScenarioBuilder scn2 = scenario("Prueba de carga con Rampa de Usuarios")
		        .exec(http("Get Homepage")
		        .get("/")) // Cambia esto por el endpoint que desees probar
		        .pause(2);  // Pausa de 2 segundos entre peticiones

		    {
		        setUp(
		            scn2.injectOpen( 
		            	atOnceUsers(2),                   		// Comenzar con 2 usuarios
		            	rampUsersPerSec(10).to(100).during(60), //sube a 100 en 1 minuto
		            	rampUsersPerSec(100).to(10).during(60), //baja a 100 en 1 minuto
		                constantUsersPerSec(2).during(20) 		// Mantener 2 usuarios por 20 segundos
		            )
		        ).protocols(httpProtocol);
		    }
	 
}
