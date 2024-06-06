package domain;

public class City {

	
	private int size;
	private int building;
	private int trees;
	private int aliens;
	private int zombies;
	private int humans;
	private int potion;
	
	public City() {
		// TODO Auto-generated constructor stub
	}

	
	public City(int size, int building, int trees, int aliens, int zombies, int humans, int potion) {
		super();
		this.size = size;
		this.building = building;
		this.trees = trees;
		this.aliens = aliens;
		this.zombies = zombies;
		this.humans = humans;
		this.potion = potion;
	}


	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getBuilding() {
		return building;
	}

	public void setBuilding(int building) {
		this.building = building;
	}

	public int getTrees() {
		return trees;
	}

	public void setTrees(int trees) {
		this.trees = trees;
	}

	public int getAliens() {
		return aliens;
	}

	public void setAliens(int aliens) {
		this.aliens = aliens;
	}

	public int getZombies() {
		return zombies;
	}

	public void setZombies(int zombies) {
		this.zombies = zombies;
	}

	public int getHumans() {
		return humans;
	}

	public void setHumans(int humans) {
		this.humans = humans;
	}

	public int getPotion() {
		return potion;
	}

	public void setPotion(int potion) {
		this.potion = potion;
	}

	public  String[] getDataName(){
		String[] dataName = {"size","building","trees","aliens","zombies","humans","potion"};
		
		return dataName;
	}
	
	public  String[] getData(){
		String[] data = {String.valueOf(this.size),String.valueOf(this.building),String.valueOf(this.trees),
				String.valueOf(this.aliens),String.valueOf(this.zombies),String.valueOf(this.humans),
				String.valueOf(this.potion)};
		
		return data;
	}

	@Override
	public String toString() {
		return getSize()+"-"+getBuilding()+"-"+getTrees()+"-"+getAliens()+"-"+getZombies()+"-"+getHumans()+"-"+getPotion();
	}

	
}
