package com.accenture.trafficDataTransform;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("trafficDataTransform")
public class TrafficDataTransformProperties {
	   
	   public String userName;
	   public String travellingTo; // only two allowed entries "home", "office"

	       public String getTravellingTo() {
	    	   return travellingTo;
	       }

	       public void setTravellingTo(String travellingTo) {
	    	   this.travellingTo = travellingTo;
	       }

		public String getuserName() {
	           return userName;
	       }

	       public void setuserName(String userName) {
	           this.userName = userName;
	       }

	}



