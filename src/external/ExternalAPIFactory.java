package external;

public class ExternalAPIFactory {
	
	 private static final String DEFAULT_PIPELINE = "ticketmaster";

	  // Start different APIs based on the pipeline.
	 /* return type is an instance(obj) of externalAPI interface, by having this instance,
	 we can call all the method inside the interface */
	 
	 /* if i change the pipeline, i can call other API beside ticketmaster API */
	 
	  public static ExternalAPI getExternalAPI(String pipeline) {
	    switch (pipeline) {
	      case "ticketmaster":
	        return new TicketMasterAPI();
	      default:
	        throw new IllegalArgumentException("Invalid pipeline " + pipeline);
	    }
	  }
	  //overload
	  public static ExternalAPI getExternalAPI() {
	    return getExternalAPI(DEFAULT_PIPELINE);
	  }

}
