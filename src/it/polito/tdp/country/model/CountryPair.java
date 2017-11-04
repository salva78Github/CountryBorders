package it.polito.tdp.country.model;

public class CountryPair {

	private final Country c1;
	private final Country c2;
	private final int year;
	private final int contiguityType;
	

	/**
	 * @param c1
	 * @param c2
	 * @param year
	 * @param contiguityType
	 */
	public CountryPair(Country c1, Country c2, int year, int contiguityType) {
		super();
		this.c1 = c1;
		this.c2 = c2;
		this.year = year;
		this.contiguityType = contiguityType;
	}
	/**
	 * @return the c1
	 */
	public Country getC1() {
		return c1;
	}
	/**
	 * @return the c2
	 */
	public Country getC2() {
		return c2;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * @return the contiguityType
	 */
	public int getContiguityType() {
		return contiguityType;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CountryPair [c1=" + c1 + ", c2=" + c2 + ", year=" + year + ", contiguityType=" + contiguityType + "]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((c1 == null) ? 0 : c1.hashCode());
		result = prime * result + ((c2 == null) ? 0 : c2.hashCode());
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
		CountryPair other = (CountryPair) obj;
		if (c1 == null) {
			if (other.c1 != null)
				return false;
		} else if (!c1.equals(other.c1))
			return false;
		if (c2 == null) {
			if (other.c2 != null)
				return false;
		} else if (!c2.equals(other.c2))
			return false;
		return true;
	}
	
	
	
}
