package domain;

public class Events {
	
	private int avenue;
	private int street;
	private String event;
	private String result;

	public Events() {
		// TODO Auto-generated constructor stub
	}

	public Events(int avenue, int street, String event, String result) {
		super();
		this.avenue = avenue;
		this.street = street;
		this.event = event;
		this.result = result;
	}

	public int getAvenue() {
		return avenue;
	}

	public void setAvenue(int avenue) {
		this.avenue = avenue;
	}

	public int getStreet() {
		return street;
	}

	public void setStreet(int street) {
		this.street = street;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	public  String[] getDataName(){
		String[] dataName = {"avenue","street","event","result"};
		
		return dataName;
	}
	
	public  String[] getData(){
		String[] data = {String.valueOf(this.avenue),String.valueOf(this.street),this.event,this.result};
		return data;
	}
	@Override
	public String toString() {
		return avenue +"-"+street +"-" + event +"-"+result;
	}

}
