import java.util.Date;

public class Route {
	private int seqNumber;
	private Date timeStamp;
	private String destAddress;
	private int destCost;
	private int portNumber;
	private boolean activeFlag;
	
	public Route(int routeNumber, String address, int port, boolean active) {
		seqNumber = routeNumber;
		timeStamp = new Date();
		destAddress = address;
		destCost = (int) Math.ceil(Math.random() * 10);
		portNumber = port;
		activeFlag = active;
	}

	public int getSeqNumber() {
		return seqNumber;
	}


	public void setSeqNumber(int theSeqNumber) {
		seqNumber = theSeqNumber;
	}


	public Date getTimeStamp() {
		return timeStamp;
	}


	public void setTimeStamp(Date theTimeStamp) {
		timeStamp = theTimeStamp;
	}


	public String getDestAddress() {
		return destAddress;
	}


	public void setDestAddress(String theDestAddress) {
		destAddress = theDestAddress;
	}


	public int getDestCost() {
		return destCost;
	}


	public void setDestCost(int theDestCost) {
		destCost = theDestCost;
	}


	public int getPortNumber() {
		return portNumber;
	}


	public void setPortNumber(int thePortNumber) {
		portNumber = thePortNumber;
	}


	public boolean isActiveFlag() {
		return activeFlag;
	}


	public void setActiveFlag(boolean theActiveFlag) {
		activeFlag = theActiveFlag;
	}
	
	public String toString() {
		return "[Sequence Number: " + seqNumber + ", Time/Date: " + timeStamp.toString() + ", Destination Address: " + destAddress + 
				", Number of Hops: " + destCost + ", Port Number: " + portNumber + ", Active: " + activeFlag + "]";
	}
}
