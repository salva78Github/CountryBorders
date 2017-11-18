package it.polito.tdp.country.model;

public class Country {

	private final String stateAbb;
	private final int code;
	private final String name;
	/**
	 * @param stateAbb
	 * @param code
	 * @param name
	 */
	public Country(String stateAbb, int code, String name) {
		super();
		this.stateAbb = stateAbb;
		this.code = code;
		this.name = name;
	}
	/**
	 * @return the stateAbb
	 */
	public String getStateAbb() {
		return stateAbb;
	}
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return stateAbb + " [" + name + "]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + code;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((stateAbb == null) ? 0 : stateAbb.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (code != other.code)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (stateAbb == null) {
			if (other.stateAbb != null)
				return false;
		} else if (!stateAbb.equals(other.stateAbb))
			return false;
		return true;
	}
	
	
	
}
